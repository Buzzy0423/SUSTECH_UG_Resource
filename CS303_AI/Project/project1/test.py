import Reversed_Reversi_MCT
import Reversed_Reversi_NN_with_weight
import Reversed_Reversi_MCT_backup
import numpy as np
import random
import torch
from json import dumps

chessboard = np.array([
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, -1, 1, 0, 0, 0],
    [0, 0, 0, 1, -1, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
])

# chessboard = np.array([[ 0  0  0  0  0  0  0  0]
#  [ 0  0  0  0  1  0  0  0]
#  [ 0  0  0  1  1  1 -1  0]
#  [ 0  1  1  1  1  1  1  1]
#  [ 1  1  1 -1 -1 -1  1  1]
#  [ 1  1 -1  1 -1  1 -1  1]
#  [ 1  1  1  1  1  1 -1  1]
#  [ 0  0  0 -1 -1 -1 -1 -1]])

# chessboard = np.array([[0,  0, 0, 0, 0, 0, 0, 0],
#                        [0, 0, -1, -1, -1, -1, -1, -1],
#                        [0, 1, -1, -1, 1, 1, -1, -1],
#                        [0, -1, -1, -1, -1,  1, 1, -1],
#                        [0, -1, -1,  1, -1, -1, 1, -1],
#                        [0, -1, 1, -1, 1, 1, -1, -1],
#                        [0, -1, -1,  1, 1, -1, -1, -1],
#                        [0, -1, -1, -1, -1, -1, -1,  0]])


def _to_json_dict_with_strings(dictionary):
    """
    Convert dict to dict with leafs only being strings. So it recursively makes keys to strings
    if they are not dictionaries.

    Use case:
        - saving dictionary of tensors (convert the tensors to strins!)
        - saving arguments from script (e.g. argparse) for it to be pretty

    e.g.

    """
    if type(dictionary) != dict:
        return str(dictionary)
    d = {k: _to_json_dict_with_strings(v) for k, v in dictionary.items()}
    return d


if __name__ == '__main__':
    # ai_black_NN = Reversed_Reversi_NN_with_weight.AI(
    #     8, Reversed_Reversi_MCT.COLOR_BLACK, 5)
    # ai_white_NN = Reversed_Reversi_NN_with_weight.AI(
    #     8, Reversed_Reversi_MCT.COLOR_WHITE, 5)
    ai_black_R = Reversed_Reversi_MCT.AI(
        8, Reversed_Reversi_MCT.COLOR_BLACK, 5)
    ai_white_R = Reversed_Reversi_MCT.AI(
        8, Reversed_Reversi_MCT.COLOR_WHITE, 5)
    ai_black = Reversed_Reversi_MCT_backup.AI(
        8, Reversed_Reversi_MCT.COLOR_BLACK, 5)
    ai_white = Reversed_Reversi_MCT_backup.AI(
        8, Reversed_Reversi_MCT.COLOR_WHITE, 5)

    # NN, UCT = 0, 0
    # for i in range(3):
    #     board = chessboard.copy()
    #     while not Reversed_Reversi_MCT.terminal(board=board):
    #         board = ai_black_NN.next(board)
    #         if Reversed_Reversi_MCT.terminal(board):
    #             break
    #         board = ai_white.next(board, 1, 'uct')
    #     if Reversed_Reversi_NN_with_weight.get_result(board, Reversed_Reversi_NN_with_weight.COLOR_BLACK) == 1:
    #         NN += 1
    #     elif Reversed_Reversi_NN_with_weight.get_result(board, Reversed_Reversi_NN_with_weight.COLOR_BLACK) == -1:
    #         UCT += 1
    #     board = chessboard.copy()
    #     while not Reversed_Reversi_NN_with_weight.terminal(board=board):
    #         board = ai_black.next(board, 1, 'uct')
    #         if Reversed_Reversi_NN_with_weight.terminal(board):
    #             break
    #         board = ai_white_NN.next(board)
    #     if Reversed_Reversi_NN_with_weight.get_result(board, Reversed_Reversi_NN_with_weight.COLOR_BLACK) == 1:
    #         UCT += 1
    #     elif Reversed_Reversi_NN_with_weight.get_result(board, Reversed_Reversi_NN_with_weight.COLOR_BLACK) == -1:
    #         NN += 1
    #     print(f'uct vs NN : {UCT} : {NN}')

    r, m = 0, 0
    board = chessboard.copy()
    while not Reversed_Reversi_MCT.terminal(board=board):
        board = ai_black_R.next(board)
        if Reversed_Reversi_MCT.terminal(board):
            break
        board = ai_white.next(board)
    if Reversed_Reversi_MCT.get_result(board, -1) == 1:
        r+=1
    else:
        m+=1
    board = chessboard.copy()
    while not Reversed_Reversi_MCT.terminal(board=board):
        board = ai_black.next(board)
        if Reversed_Reversi_MCT.terminal(board):
            break
        board = ai_white_R.next(board)
    if Reversed_Reversi_MCT.get_result(board, -1) == 1:
        m+=1
    else:
        r+=1
    print(f'uct_R vs UCT : {r} : {m}')
