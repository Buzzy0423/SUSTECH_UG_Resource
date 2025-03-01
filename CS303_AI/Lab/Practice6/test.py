# Look The Note Below!!!

import numpy as np
import time
from numba import njit

N = 16  # You shoule change the test size here !!!


def my_range(start, end):
    if start <= end:
        return range(start, end + 1)
    else:
        return range(start, end - 1, -1)


class Problem:
    char_mapping = ('·', 'Q')

    def __init__(self, n=4):
        self.n = n

    def is_valid(self, state):
        """
        check the state satisfy condition or not.
        :param state: list of points (in (row id, col id) tuple form)
        :return: bool value of valid or not
        """
        board = self.get_board(state)
        res = True
        for point in state:
            i, j = point
            condition1 = board[:, j].sum() <= 1
            condition2 = board[i, :].sum() <= 1
            condition3 = self.pos_slant_condition(board, point)
            condition4 = self.neg_slant_condition(board, point)
            res = res and condition1 and condition2 and condition3 and condition4
            if not res:
                break
        return res

    def is_satisfy(self, state):
        return self.is_valid(state) and len(state) == self.n

    def pos_slant_condition(self, board, point):
        i, j = point
        tmp = min(self.n - i - 1, j)
        start = (i + tmp, j - tmp)
        tmp = min(i, self.n - j - 1)
        end = (i - tmp, j + tmp)
        rows = my_range(start[0], end[0])
        cols = my_range(start[1], end[1])
        return board[rows, cols].sum() <= 1

    def neg_slant_condition(self, board, point):
        i, j = point
        tmp = min(i, j)
        start = (i - tmp, j - tmp)
        tmp = min(self.n - i - 1, self.n - j - 1)
        end = (i + tmp, j + tmp)
        rows = my_range(start[0], end[0])
        cols = my_range(start[1], end[1])
        return board[rows, cols].sum() <= 1

    def get_board(self, state):
        board = np.zeros([self.n, self.n], dtype=int)
        for point in state:
            board[point] = 1
        return board

    def print_state(self, state):
        board = self.get_board(state)
        print('_' * (2 * self.n + 1))
        for row in board:
            for item in row:
                print(f'|{Problem.char_mapping[item]}', end='')
            print('|')
        print('-' * (2 * self.n + 1))


def check(idx, n):
    for i in range(len(idx) - 1, -1, -1):
        if idx[i] == n:
            idx[i] = 0
            idx[i - 1] += 1
        else:
            break


def bts(problem):
    n = 4
    action_stack = []
    idx = [0] * n
    while not problem.is_satisfy(action_stack):
        yield action_stack
        try:
            action_stack = []
            for count in range(n):
                action_stack.append((count, idx[count]))
            if not problem.is_valid(action_stack):
                action_stack.pop()
                idx[n - 1] += 1
                check(idx, n)
        except IndexError:
            print(i)
            print(idx)
            print(action_stack)


def valid(idx, pt):
    for i in range(pt[1]):
        if pt[0] == idx[i] + pt[1] - i or pt[0] == idx[i] - pt[1] + i or idx[i] == pt[0]:
            return False
    return True


def improving_bts(problem):
    action_stack = []
    n = 16
    idx = [0] * n
    i = 0
    while not problem.is_satisfy(action_stack):
        yield action_stack
        while i in range(n):
            if valid(idx, (idx[i], i)):
                i += 1
            else:
                idx[i] += 1
                while idx[i] == n:
                    idx[i] = 0
                    idx[i - 1] += 1
                    i -= 1
        for c in range(n):
            action_stack.append((c, idx[c]))


if __name__ == '__main__':
    # test_block
    n = N  # Do not modify this parameter, if you want to change the size, go to the first line of whole program.
    render = True  # here to select GUI or not
    method = improving_bts  # here to change your method; bts or improving_bts
    p = Problem(n)
    if render:
        import pygame

        w, h = 90 * n + 10, 90 * n + 10
        screen = pygame.display.set_mode((w, h))
        screen.fill('white')
        action_generator = method(p)
        clk = pygame.time.Clock()
        queen_img = pygame.image.load('./queen.png')
        while True:
            for event in pygame.event.get():
                if event == pygame.QUIT:
                    exit()
            try:
                actions = next(action_generator)
                screen.fill('white')
                for i in range(n + 1):
                    pygame.draw.rect(screen, 'black', (i * 90, 0, 10, h))
                    pygame.draw.rect(screen, 'black', (0, i * 90, w, 10))
                for action in actions:
                    i, j = action
                    screen.blit(queen_img, (10 + 90 * j, 10 + 90 * i))
                pygame.display.flip()
            except StopIteration:
                pass
            clk.tick(5)
        pass
    else:
        start_time = time.time()
        for actions in method(p):
            pass
        p.print_state(actions)
        print(time.time() - start_time)
