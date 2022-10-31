import numpy as np
from time import time
from numba import jit, njit
import sys
import math
import torch
import torch.nn as nn
import torch.nn.functional as F
from collections import OrderedDict
from torch import tensor
import zlib

sys.path.append('..')

COLOR_BLACK = -1
COLOR_WHITE = 1
COLOR_NONE = 0
EPS = 1e-8


class OthelloNNet(nn.Module):
    def __init__(self, N=8, num_channels=32):
        # game params
        self.board_x, self.board_y = 8, 8
        self.action_size = 8 * 8 + 1
        self.num_channels = num_channels
        super(OthelloNNet, self).__init__()
        self.conv1 = nn.Conv2d(1, num_channels, 3, stride=1, padding=1)
        self.conv2 = nn.Conv2d(
            num_channels, num_channels, 3, stride=1, padding=1)
        self.conv3 = nn.Conv2d(
            num_channels, num_channels, 3, stride=1)
        self.conv4 = nn.Conv2d(
            num_channels, num_channels, 3, stride=1)

        self.bn1 = nn.BatchNorm2d(num_channels)
        self.bn2 = nn.BatchNorm2d(num_channels)
        self.bn3 = nn.BatchNorm2d(num_channels)
        self.bn4 = nn.BatchNorm2d(num_channels)

        self.fc1 = nn.Linear(
            num_channels * (self.board_x - 4) * (self.board_y - 4), 1024)
        self.fc_bn1 = nn.BatchNorm1d(1024)

        self.fc2 = nn.Linear(1024, 512)
        self.fc_bn2 = nn.BatchNorm1d(512)

        self.fc3 = nn.Linear(512, self.action_size)

        self.fc4 = nn.Linear(512, 1)

    def forward(self, s):
        #                                                           s: batch_size x board_x x board_y
        # batch_size x 1 x board_x x board_y
        s = s.view(-1, 1, self.board_x, self.board_y)
        # batch_size x num_channels x board_x x board_y
        s = F.relu(self.bn1(self.conv1(s)))
        # batch_size x num_channels x board_x x board_y
        s = F.relu(self.bn2(self.conv2(s)))
        # batch_size x num_channels x (board_x-2) x (board_y-2)
        s = F.relu(self.bn3(self.conv3(s)))
        # batch_size x num_channels x (board_x-4) x (board_y-4)
        s = F.relu(self.bn4(self.conv4(s)))
        s = s.view(-1, self.num_channels *
                   (self.board_x - 4) * (self.board_y - 4))

        s = F.dropout(F.relu(self.fc_bn1(self.fc1(s))), p=0.3,
                      training=self.training)  # batch_size x 1024
        s = F.dropout(F.relu(self.fc_bn2(self.fc2(s))), p=0.3,
                      training=self.training)  # batch_size x 512

        # batch_size x action_size
        pi = self.fc3(s)
        # batch_size x 1
        v = self.fc4(s)

        return F.log_softmax(pi, dim=1), torch.tanh(v)


def predict(nnet, board):
    # timing
    start = time()

    # preparing input
    board = torch.FloatTensor(board.astype(np.float64))
    board = board.view(1, 8, 8)
    nnet.eval()
    with torch.no_grad():
        pi, v = nnet(board)

    # print('PREDICTION TIME TAKEN : {0:03f}'.format(time.time()-start))
    return torch.exp(pi).data.cpu().numpy()[0], v.data.cpu().numpy()[0]


def terminal(board):
    return len(get_valid_moves(board, -1)) == 0 and len(get_valid_moves(board, 1)) == 0


@njit
def get_valid_moves(chessboard, color):
    idx = []
    for i in range(8):
        for j in range(8):
            if chessboard[i][j] == 0:
                idx.append((i, j))
    if len(idx) == 0:
        return idx
    moves = []
    for i in idx:
        if valid(chessboard, i, color):
            moves.append(i)
    return moves


def get_valid_mask(chessboard, color):
    moves = get_valid_moves(chessboard, color)
    valids = [0] * 65
    if len(moves) == 0:
        valids[-1] = 1
        return np.array(valids)
    for x, y in moves:
        valids[8 * x + y] = 1
    return np.array(valids)


def next_state(board, color, action):
    if action == 64:
        return (board, -color)
    tmp = (action // 8, action % 8)
    new_board = play(board, tmp, color)
    return (new_board, -color)


def get_canonical_board(board, player):
    return player * board


@njit
def play(board, move, color):
    new_board = board.copy()
    direction = [(-1, 0), (1, 0), (0, 1), (0, -1),
                 (1, 1), (-1, -1), (-1, 1), (1, -1)]
    for xi, yi in direction:
        x, y = move
        while x in range(0, 8) and y in range(0, 8) and new_board[x][y] != color:
            x, y = x + xi, y + yi
        while x in range(0, 8) and y in range(0, 8) and (x - xi, y - yi) != move:
            x, y = x - xi, y - yi
            if x in range(0, 8) and y in range(0, 8):
                new_board[x][y] = color
    new_board[move[0]][move[1]] = color
    return new_board


@njit
def valid(chessboard, index, color):
    direction = [(-1, 0), (1, 0), (0, 1), (0, -1),
                 (1, 1), (-1, -1), (-1, 1), (1, -1)]
    for xi, yi in direction:
        x, y = index
        x, y = x + xi, y + yi
        if x in range(0, 8) and y in range(0, 8) and chessboard[x, y] == -color:
            x, y = x + xi, y + yi
            while x in range(0, 8) and y in range(0, 8):
                if chessboard[x, y] == 0:
                    break
                if chessboard[x, y] == color:
                    return True
                x, y = x + xi, y + yi
    return False


def get_result(board, color):
    if not terminal(board):
        return 0
    black, white = 0, 0
    for i in range(8):
        for j in range(8):
            if board[i][j] == COLOR_BLACK:
                black += 1
            else:
                white += 1
    if (black < white and color == -1) or (black > white and color == 1):
        return 1
    else:
        return -1


class AI(object):
    # chessboard_size, color, time_out passed from agent
    def __init__(self, chessboard_size, color, time_out, scalar=1):
        self.chessboard_size = chessboard_size
        # You are white or black
        self.color = color
        # the max time you should use, your algorithm's run time must not exceed the time limit.
        self.time_out = time_out
        self.nnet = OthelloNNet()
        checkpoint = torch.load('32_best.pth.tar', map_location='cpu')
        self.nnet.load_state_dict(checkpoint['state_dict'])
        # w = bytes.fromhex(weight)
        # w1 = zlib.decompress(w)
        # ws = w1.decode('utf-8')
        # self.nnet.load_state_dict(OrderedDict(eval(ws)))
        self.candidate_list = []
        self.scalar = scalar
        self.iter = 200
        self.Qsa = {}  # stores Q values for s,a (as defined in the paper)
        self.Nsa = {}  # stores #times edge s,a was visited
        self.Ns = {}  # stores #times board s was visited
        self.Ps = {}  # stores initial policy (returned by neural net)

        self.Es = {}  # stores game.getGameEnded ended for board s
        self.Vs = {}  # stores game.getValidMoves for board s

    # The input is the current chessboard. Chessboard is a numpy array.

    def go(self, chessboard):
        start_time = time()
        self.candidate_list.clear()
        self.candidate_list = get_valid_moves(chessboard, self.color)
        if len(self.candidate_list) == 0:
            return 0
        loop_time = 0
        canonical_board = get_canonical_board(chessboard, self.color)
        while time() - start_time < 0.8*self.time_out:
            self.search(canonical_board)
            loop_time += 1

        s = canonical_board.tostring()
        reward = -float('inf')
        act = None
        for x, y in self.candidate_list:
            if (s, x * 8 + y) in self.Qsa.keys() and self.Qsa[(s, x * 8 + y)] > reward:
                act = (x, y)
                reward = self.Qsa[(s, x * 8 + y)]

        if act:
            self.candidate_list.append(act)

        return loop_time

    def next(self, board):
        try:
            loop_time = self.go(board)
            if len(self.candidate_list) == 0:
                return board, loop_time
            return play(board, self.candidate_list[-1], self.color), loop_time
        except RecursionError:
            print(self.color)
            print(board)
            return None

    def search(self, board):

        s = board.tostring()

        if s not in self.Es:
            self.Es[s] = get_result(board, 1)
        if self.Es[s] != 0:
            # terminal node
            return -self.Es[s]

        # select policy
        if s not in self.Ps:
            # leaf node
            self.Ps[s], v = predict(self.nnet, board)
            mask = get_valid_mask(board, 1)
            self.Ps[s] = self.Ps[s] * mask  # masking invalid moves
            sum_Ps_s = np.sum(self.Ps[s])
            if sum_Ps_s > 0:
                self.Ps[s] /= sum_Ps_s
            else:
                print("All valid moves were masked, doing a workaround.")
                self.Ps[s] = self.Ps[s] + mask
                self.Ps[s] /= np.sum(self.Ps[s])

            self.Vs[s] = mask
            self.Ns[s] = 0
            return -v

        mask = self.Vs[s]
        score = -float('inf')
        act = -1

        # ucb
        for i in range(65):
            if mask[i]:
                if (s, i) in self.Qsa:
                    u = self.Qsa[(s, i)] + self.scalar * self.Ps[s][i] * \
                        math.sqrt(self.Ns[s]) / (1 + self.Nsa[(s, i)])
                else:
                    u = self.scalar * self.Ps[s][i] * \
                        math.sqrt(self.Ns[s] + EPS)

                if u > score:
                    score = u
                    act = i

        i = act
        next_board, next_player = next_state(board, 1, i)
        next_board = get_canonical_board(next_board, next_player)

        v = self.search(next_board)

        if (s, i) in self.Qsa:
            self.Qsa[(s, i)] = (self.Nsa[(s, i)] *
                                self.Qsa[(s, i)] + v) / (self.Nsa[(s, i)] + 1)
            self.Nsa[(s, i)] += 1
        else:
            self.Qsa[(s, i)] = v
            self.Nsa[(s, i)] = 1

        self.Ns[s] += 1
        return -v
