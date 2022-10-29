import numpy as np
from random import choice
from random import shuffle
from time import time
from numba import njit

COLOR_BLACK = -1
COLOR_WHITE = 1
COLOR_NONE = 0


# don't change the class name

@njit
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


def get_valid_moves_1d(board, color):
    move_2d = get_valid_moves(board, color)
    moves = []
    for idx in move_2d:
        moves.append(8 * idx[0] + idx[1])
    return moves


def next_state(board, color, action):
    if action == -1:
        return board, -color
    tmp = (action // 8, action % 8)
    new_board = play(board, tmp, color)
    return new_board, -color


def any_valid(board, color):
    idx = np.where(board == 0)
    idx = list(zip(idx[0], idx[1]))
    for i in idx:
        if valid(board, i, color):
            return True
    return False


def get_canonical_board(board, player):  # all 1
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

@njit
def get_result(board, color):
    if not terminal(board):
        return 0
    black, white = 0, 0
    for i in range(len(board)):
        for j in range(len(board[0])):
            if board[i][j] == -1:
                black += 1
            elif board[i][j] == 1:
                white += 1
    if (black < white and color == -1) or (black > white and color == 1):
        return 1
    else:
        return -1


def roxanne_play(chessboard, color):
    roxanne_table = [
        [(0, 1), (7, 1), (0, 6), (7, 6), (1, 0), (6, 0), (1, 7), (6, 7)],
        [(1, 1), (6, 1), (1, 6), (6, 6)],
        [(1, 3), (6, 3), (1, 4), (6, 4), (3, 1), (4, 1), (3, 6), (4, 6)],
        [(1, 2), (6, 2), (1, 5), (6, 5), (2, 1), (5, 1), (2, 6), (5, 6)],
        [(0, 3), (7, 3), (0, 4), (7, 4), (3, 0), (4, 0), (3, 7), (4, 7)],
        [(0, 2), (7, 2), (0, 5), (7, 5), (2, 0), (5, 0), (2, 7), (5, 7)],
        [(2, 3), (5, 3), (2, 4), (5, 4), (3, 2), (4, 2), (3, 5), (4, 5)],
        [(2, 2), (5, 2), (2, 5), (5, 5)],
        [(0, 0), (7, 0), (0, 7), (7, 7)],
        [(3, 3), (3, 4), (4, 3), (4, 4)],
    ]
    valid_moves = get_valid_moves(chessboard, color)
    if len(valid_moves) == 0:
        return None
    for i in roxanne_table:
        shuffle(i)
        for move in i:
            if move in valid_moves:
                return move


def roxanne_simulation(board, color):
    b = board.copy()
    c = color
    while not terminal(b):
        move = roxanne_play(b, c)
        if move is None:
            c = -c
            continue
        b = play(b, move, c)
        c = -c
    return get_result(b, color)


def random_simulation(board, color):
    b = board.copy()
    c = color
    while not terminal(b):
        moves = get_valid_moves(b, c)
        if len(moves) == 0:
            c = -c
            continue
        move = choice(moves)
        b = play(b, move, c)
        c = -c
    return get_result(b, color)


class AI(object):
    # chessboard_size, color, time_out passed from agent
    def __init__(self, chessboard_size, color, time_out):
        self.chessboard_size = chessboard_size
        # You are white or black
        self.color = color
        # the max time you should use, your algorithm's run time must not exceed the time limit.
        self.time_out = time_out
        self.saclar = 1
        self.candidate_list = []
        self.Qsa = {}  # stores Q values for s,a (as defined in the paper)
        self.Nsa = {}  # stores #times edge s,a was visited
        self.Ns = {}  # stores #times board s was visited

        self.Es = {}  # stores game.getGameEnded ended for board s
        self.Vs = {}  # stores game.getValidMoves for board s

    # The input is the current chessboard. Chessboard is a numpy array.

    def go(self, chessboard):
        start_time = time()
        self.candidate_list.clear()
        self.candidate_list = get_valid_moves(chessboard, self.color)

        if len(self.candidate_list) == 0:
            return

        board = get_canonical_board(chessboard, self.color)
        #loop_time = 0
        while (time() - start_time) < (self.time_out * 0.88):
            self.search(board)
            #loop_time += 1

        s = board.tostring()
        reward = -float('inf')
        act = None
        for x, y in self.candidate_list:
            if (s, x * 8 + y) in self.Qsa.keys() and self.Qsa[(s, x * 8 + y)] > reward:
                act = (x, y)
                reward = self.Qsa[(s, x * 8 + y)]

        if act:
            self.candidate_list.append(act)

        #print(loop_time)

    def next(self, board):
        self.go(board)
        if len(self.candidate_list) == 0:
            return board
        return play(board, self.candidate_list[-1], self.color)

    def search(self, board):
        s = board.tostring()
        # game end?
        if s not in self.Es:
            self.Es[s] = get_result(board, 1)
            self.Vs[s] = get_valid_moves_1d(board, 1)  # all valid moves for s
            self.Ns[s] = 0
        if self.Es[s] != 0:
            return -self.Es[s]

        self.Ns[s] += 1

        # fully_expand ?
        m = self.fully_expand(s)

        # leaf node roll out
        if m is not None:
            nxt = play(board, (m // 8, m % 8), 1)
            reward = random_simulation(nxt, -1)
            self.Qsa[(s, m)] = reward
            self.Nsa[(s, m)] = 1
            return reward

        score = -float('inf')
        act = -1

        # ucb
        for i in self.Vs[s]:
            if (s, i) in self.Qsa:
                u = self.Qsa[(s, i)] + self.saclar * \
                    np.sqrt(2 * np.log(self.Ns[s]) / self.Nsa[(s, i)])
                if u > score:
                    score = u
                    act = i

        # back propagation
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

        return -v

    def fully_expand(self, s):
        for i in self.Vs[s]:
            if (s, i) not in self.Qsa:
                return i
        return None
