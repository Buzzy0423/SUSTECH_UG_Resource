import Reversed_Reversi_MCT
import Reversed_Reversi_NN
import Reversed_Reversi_MCT_raw
import numpy as np
from Reversed_Reversi_MCT import terminal, get_result

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


def get_AI(arg):
    if arg == 'MCT':
        return Reversed_Reversi_MCT.AI(
            8, Reversed_Reversi_MCT.COLOR_BLACK, 1), Reversed_Reversi_MCT.AI(
            8, Reversed_Reversi_MCT.COLOR_WHITE, 1)
    elif arg == 'RAW_MCT':
        return Reversed_Reversi_MCT_raw.AI(
            8, Reversed_Reversi_MCT.COLOR_BLACK, 1), Reversed_Reversi_MCT_raw.AI(
            8, Reversed_Reversi_MCT.COLOR_WHITE, 1)
    else:
        return Reversed_Reversi_NN.AI(
            8, Reversed_Reversi_MCT.COLOR_BLACK, 1), Reversed_Reversi_NN.AI(
            8, Reversed_Reversi_MCT.COLOR_WHITE, 1)


def play(player_black, player_white):
    loop1, loop2 = [], []
    board = chessboard.copy()
    while not terminal(board=board):
        board, loop = player_black.next(board)
        loop1.append(loop)
        if terminal(board):
            break
        board, loop = player_white.next(board)
        loop2.append(loop)
    return get_result(board, -1), np.mean(loop1), np.mean(loop2)


def test(algorithm1, algorithm2):
    p1, p2 = 0, 0
    loop1, loop2 = [], []
    for _ in range(3):
        player1_black, player1_white = get_AI(algorithm1)
        player2_black, player2_white = get_AI(algorithm2)
        result, l1, l2 = play(player1_black, player2_white)
        loop1.append(l1)
        loop2.append(l2)
        if result == 1:
            p1 += 1
        else:
            p2 += 1
        result, l1, l2 = play(player2_black, player1_white)
        loop1.append(l2)
        loop2.append(l1)
        if result == 1:
            p2 += 1
        else:
            p1 += 1
    print(f'{algorithm1} vs {algorithm2} : {p1} : {p2}')
    print(
        f'loop_time_{algorithm1} vs loop_time_{algorithm2} : {np.mean(loop1)} : {np.mean(loop2)}')


if __name__ == '__main__':
    arg = [('MCT', 'NN'), ('MCT', 'RAW_MCT'), ('RAW_MCT', 'NN')]
    for i in range(3):
        algorithm1, algorithm2 = arg[i]
        test(algorithm1, algorithm2)
