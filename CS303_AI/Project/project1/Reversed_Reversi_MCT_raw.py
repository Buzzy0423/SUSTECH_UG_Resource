from math import inf
from webbrowser import get
import numpy as np
from random import choice
from random import shuffle
from time import time
from numba import jit, njit

COLOR_BLACK = -1
COLOR_WHITE = 1
COLOR_NONE = 0

w_map = np.array([[500, -125, 10, 5, 5, 10, -125, 500],
                  [-125, -45, 1, 1, 1, 1, -145, -125],
                  [10, 1, 3, 2, 2, 3, 1, 10],
                  [5, 1, 2, 1, 1, 2, 1, 5],
                  [5, 1, 2, 1, 1, 2, 1, 5],
                  [10, 1, 3, 2, 2, 3, 1, 10],
                  [-125, -145, 1, 1, 1, 1, -145, -125],
                  [500, -125, 10, 5, 5, 10, -125, 500]])


# don't change the class name

def terminal(board):
    return len(get_valid_moves(board, -1)) == 0 and len(get_valid_moves(board, 1)) == 0


def eval_diff(board, color):
    my_ = len(np.where(board == color)[0])
    op_ = len(np.where(board == -color)[0])
    return 100 * (my_ - op_) / (my_ + op_)


def eval_corner(board, color):
    my_, op_ = 0, 0
    if board[0][0] == color:
        my_ += 1
    if board[0][7] == color:
        my_ += 1
    if board[7][0] == color:
        my_ += 1
    if board[7][7] == color:
        my_ += 1
    if board[0][0] == -color:
        my_ += 1
    if board[0][7] == -color:
        my_ += 1
    if board[7][0] == -color:
        my_ += 1
    if board[7][7] == -color:
        my_ += 1
    return 100 * (my_ - op_) / (my_ + op_ + 1)


def eval_parity(board):
    tmp = len(np.where(board == 0)[0])
    if tmp % 2 == 0:
        return -1
    else:
        return 1


def get_map_weight(board, color):
    my_ = sum(sum(board*w_map))*color
    op_ = sum(sum(board*w_map))*color
    if (my_ + op_) == 0:
        my_ += 1
    return 100 * (my_ - op_) / (my_ + op_)



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


def any_valid(board, color):
    idx = np.where(board == 0)
    idx = list(zip(idx[0], idx[1]))
    for i in idx:
        if valid(board, i, color):
            return True
    return False


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


def get_game_phase(board):
    idx = np.where(board != 0)
    count = len(idx[0])
    if count < 20:
        return 0  # ealy
    elif count <= 58:
        return 1  # mid
    else:
        return 2  # late


def ucb(node, scalar=1.0):
    best_score = -float('inf')
    best_children = []
    for c in node.children:
        if c.visits == 0:
            best_children = [c]
            break
        exploit = c.reward / c.visits
        explore = np.sqrt(np.log(node.visits) / c.visits)
        score = exploit + scalar * explore
        if score == best_score:
            best_children.append(c)
        if score > best_score:
            best_children = [c]
            best_score = score
    if len(best_children) == 0:
        return node.parent
    return choice(best_children)


@njit
def get_result(board, color):
    black, white = 0, 0
    for i in range(len(board)):
        for j in range(len(board[0])):
            if board[i][j] == -1:
                black += 1
            elif board[i][j] == 1:
                white += 1
    if black == white:
        return 0
    elif (black < white and color == -1) or (black > white and color == 1):
        return 100 + abs(white - black)
    else:
        return -(100 + abs(white - black))


def eval_stable(board, color):
    my_ = get_stable(board, color)
    op_ = get_stable(board, -color)
    return 100 * (my_ - op_) / (my_ + op_ + 1)


def get_stable(board, color):
    count = 0
    for x in range(7):
        for y in range(7):
            if stable(board, (x, y), color):
                count += 1
    return count


def get_stable_table(board, color):
    table = np.zeros((8,8))
    table[:,np.sum(abs(board), axis = 0) == 8] = 1
    table[np.sum(abs(board), axis = 1) == 8,:] = 1

def stable(board, idx, color):
    direction = [(-1, 0), (1, 0), (0, 1), (0, -1),
                 (1, 1), (-1, -1), (-1, 1), (1, -1)]
    for i in range(4):
        direction1 = direction[i*2]
        direction2 = direction[i*2+1]
        if not (judge(board, idx, direction1, color) or judge(board, idx, direction2, color)):
            return False
    return True


def judge(board, idx, direction, color):
    x, y = idx
    while x in range(0, 8) and y in range(0, 8):
        x, y = x + direction[0], y + direction[1]
        if x in range(0, 7) and y in range(0, 7) and board[x][y] != color:
            return False
    return True



class AI(object):
    # chessboard_size, color, time_out passed from agent
    def __init__(self, chessboard_size, color, time_out):
        self.chessboard_size = chessboard_size
        # You are white or black
        self.color = color
        # the max time you should use, your algorithm's run time must not exceed the time limit.
        self.time_out = time_out
        self.roxanne_weight = [
            [1, 5, 3, 3, 3, 3, 5, 1],
            [5, 5, 4, 4, 4, 4, 5, 5],
            [3, 4, 2, 2, 2, 2, 4, 3],
            [3, 4, 2, 2, 2, 2, 4, 3],
            [3, 4, 2, 2, 2, 2, 4, 3],
            [3, 4, 2, 2, 2, 2, 4, 3],
            [5, 5, 4, 4, 4, 4, 5, 5],
            [1, 5, 3, 3, 3, 3, 5, 1]
        ]
        self.candidate_list = []

    # The input is the current chessboard. Chessboard is a numpy array.

    def go(self, chessboard, scalar=0.5, depth=4, mode='uct'):
        self.candidate_list.clear()
        self.candidate_list = get_valid_moves(chessboard, self.color)
        if mode == 'minmax':
            tmp = self.go_minmax(chessboard, depth)
            if tmp is not None:
                self.candidate_list.append(tmp)
        else:
            self.candidate_list = self.uct(chessboard, scalar)

    def next(self, board):
            self.go(board)
            if len(self.candidate_list) == 0:
                return board
            return play(board, self.candidate_list[-1], self.color)

    def go_minmax(self, board, depth):
        root_node = Node(board, self.color)
        self.expand(root_node)
        score = -inf
        best_move = None
        for c in root_node.children:
            tmp = self.min_max(c, depth, -inf, inf)
            if tmp > score:
                score = tmp
                best_move = c.move
        return best_move

    def min_max(self, node, depth, alpha, beta):
        board = node.board.copy()
        if depth == 0 or terminal(board):
            return self.eval(board, node.parent.color)  # ?

        self.expand(node)
        if node.color == self.color:  # max
            score = -inf
            for c in node.children:
                c_score = self.min_max(c, depth - 1, alpha, beta)
                score = max(c_score, score)
                alpha = max(score, alpha)
                if beta <= alpha:
                    break
        else:  # min
            score = inf
            for c in node.children:
                c_score = self.min_max(c, depth - 1, alpha, beta)
                score = min(c_score, score)
                beta = min(score, beta)
                if beta <= alpha:
                    break
        return score

    def eval(self, board, color):
        if terminal(board):
            return -(1000 * eval_diff(board, color))
        phase = get_game_phase(board)
        if phase == 0:
            return -(1000 * get_map_weight(board, color) + 50 * self.eval_mobility(board, color) + 500 * eval_stable(board, color))
        elif phase == 1:
            return -(500 * get_map_weight(board, color) + 20 * self.eval_mobility(board, color) + 10 * eval_diff(board, color) + 100 * eval_parity(board) + 1000 * eval_stable(board, color))
        else:
            return -(500 * get_map_weight(board, color) + 100 * self.eval_mobility(board, color) + 500 * eval_diff(board, color) + 500 * eval_parity(board) + 2000 * eval_stable(board, color))

    def eval_mobility(self, board, color):
        my_, op_ = len(get_valid_moves(board, color)), len(
            get_valid_moves(board, -color))
        return 100 * (my_ - op_) / (my_ + op_ + 1)

    def roxanne_play(self, chessboard, color):
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

    def uct(self, board, scalar=1.0):
        start_time = time()
        result = get_valid_moves(board, self.color)
        loop_time = 0
        if len(result) == 0:
            return []
        root_node = Node(board, self.color)
        self.expand(root_node)
        while (time() - start_time) < (self.time_out * 0.76):
            node = self.select_policy(root_node, scalar)
            reward = self.random_simulation(node)
            self.backup(node, reward)
            loop_time += 1
        result.append(ucb(root_node, 0).move)
        #print(loop_time)
        return result

    def select_policy(self, node, scalar=1.0):
        while not terminal(node.board):
            if len(node.children) == 0:
                if node.visits == 0:
                    return node
                self.expand(node)
                if len(node.children) == 0:
                    return node
                return node.children[0]
            else:
                node = ucb(node, scalar)
        return node

    def expand(self, node):
        color = node.color
        moves = get_valid_moves(node.board, color)
        for m in moves:
            new_board = play(node.board, m, color)
            if any_valid(new_board, -color):
                node.add_child(new_board, m, -color)
            else:
                node.add_child(new_board, m, color)

    def roxanne_simulation(self, node):
        board = node.board.copy()
        color = node.color
        while not terminal(board):
            move = self.roxanne_play(board, color)
            if move is None:
                color = -color
                continue
            board = play(board, move, color)
            color = -color
        return get_result(board, self.color)

    def random_simulation(self, node):
        board = node.board.copy()
        color = node.color
        while not terminal(board):
            moves = get_valid_moves(board, color)
            if len(moves) == 0:
                color = -color
                continue
            move = choice(moves)
            board = play(board, move, color)
            color = -color
        return get_result(board, self.color)

    def backup(self, node, reward):
        while node.parent is not None:
            node.visits += 1
            if node.parent.color == self.color:
                node.reward += reward
            else:
                node.reward -= reward
            node = node.parent
        node.visits += 1


class Node:
    def __init__(self, board, color, move=None, parent=None):
        self.visits = 0
        self.reward = 0.0
        self.board = board
        self.children = []
        self.parent = parent
        self.color = color
        self.move = move

    def add_child(self, child_state, move, color):
        child = Node(child_state, color, move, self)
        self.children.append(child)

    def update(self, reward):
        self.reward += reward
        self.visits += 1
