import numpy as np
from time import time
import argparse
import threading
import random
import copy
import heapq

parser = argparse.ArgumentParser(description='command args')
parser.add_argument('file', type=str,
                    help='Absolute path of the test CARP instance file', default='sample.dat')
parser.add_argument('-t', type=float,
                    help='Termination condition of the algorithm', default=5)
parser.add_argument('-s', type=float,
                    help='Seed of alforithm', default=100)


def extract_args(line):
    return line[line.index(':') + 2:]


def preprocess(file, seed, time, size):
    data = {}
    with open(file, 'r') as f:
        data['name'] = extract_args(f.readline())
        data['vertices'] = int(extract_args(f.readline()))
        data['depot'] = int(extract_args(f.readline()))
        data['r_edges'] = int(extract_args(f.readline()))
        data['nr_edges'] = int(extract_args(f.readline()))
        data['vehicles'] = int(extract_args(f.readline()))
        data['capacity'] = int(extract_args(f.readline()))
        data['total_cost'] = int(extract_args(f.readline()))
        f.readline()  # header
        edges, required_edges = {}, {}  # not include reverse, mention!
        dis = np.zeros((data['vertices'] + 1, data['vertices'] + 1))
        dis.fill(float('inf'))
        for _ in range(data['r_edges'] + data['nr_edges']):
            par = list(
                map(int, list(filter(None, f.readline().split(' ')))))
            edges[(par[0], par[1])] = (par[2], par[3])
            if par[3] != 0:
                required_edges[(par[0], par[1])] = (par[2], par[3])
            dis[par[0]][par[1]] = par[2]
            dis[par[1]][par[0]] = par[2]
        for k in range(1, data['vertices'] + 1):
            for i in range(1, data['vertices'] + 1):
                for j in range(1, data['vertices'] + 1):
                    dis[i, j] = min(
                        dis[i, j], dis[i, k] + dis[k, j])
        for i in range(1, data['vertices'] + 1):
            dis[i, i] = 0

    return (seed, data, edges, required_edges, dis, time, size)


class CARP_SOLVER:
    def __init__(self, seed, size, data, edges, required_edges, dis):
        self.seed = seed
        self.population_size = size
        self.name = data['name']
        self.vertices = data['vertices']
        self.depot = data['depot']
        self.r_edges = data['r_edges']
        self.nr_edges = data['nr_edges']
        self.vehicles = data['vehicles']
        self.capacity = data['capacity']
        self.total_cost = data['total_cost']
        self.gen, self.evolution, self.avg_ = 0, [], []
        self.best, self.best_score = None, float('inf')
        self.edges, self.required_edges = edges, required_edges
        self.dis = dis

    def get_cost(self, arc):
        if arc in self.edges:
            return self.edges[arc][0]
        else:
            return self.edges[(arc[1], arc[0])][0]

    def get_demand(self, arc):
        if arc in self.required_edges:
            return self.required_edges[arc][1]
        else:
            return self.required_edges[(arc[1], arc[0])][1]

    def check_path_demand(self, path):
        tmp = 0
        for arc in path:
            tmp += self.get_demand(arc)
        return tmp <= self.capacity

    def recalculate_path_cost(self, path):
        new_cost = self.dis[self.depot][path[0][0]]
        for i in range(len(path) - 1):
            new_cost += self.dis[path[i][1]][path[i + 1][0]]
        for arc in path:
            new_cost += self.get_cost(arc)
        new_cost += self.dis[path[-1][1]][self.depot]
        return new_cost

    def calculate_solution_cost(self, solution):
        cost = 0
        for path in solution:
            cost += self.recalculate_path_cost(path)
        return cost

    def find_able_arc(self, free, start, remain):
        tmp = []
        min_d = float('inf')
        for arc in free.keys():
            if free[arc][1] <= remain:
                if self.dis[start][arc[0]] == min_d:
                    tmp.append((arc, False))
                if self.dis[start][arc[1]] == min_d:
                    tmp.append((arc, True))
                if self.dis[start][arc[0]] < min_d:
                    min_d = self.dis[start][arc[0]]
                    tmp.clear()
                    tmp.append((arc, False))
                if self.dis[start][arc[1]] < min_d:
                    min_d = self.dis[start][arc[1]]
                    tmp.clear()
                    tmp.append((arc, True))
        if len(tmp) == 0:
            return None, remain
        c_arc = random.choice(tmp)
        free.pop(c_arc[0])
        if c_arc[1]:
            return (c_arc[0][1], c_arc[0][0]), remain - self.edges[c_arc[0]][1]
        else:
            return c_arc[0], remain - self.edges[c_arc[0]][1]

    def find_paths(self, free):
        remain, start = self.capacity, self.depot
        paths = []
        arc, remain = self.find_able_arc(free, start, remain)
        while arc is not None:
            paths.append(arc)
            start = arc[1]
            arc, remain = self.find_able_arc(free, start, remain)
        return paths

    def find_solution(self):
        free = copy.deepcopy(self.required_edges)
        solution = []
        paths = self.find_paths(free)
        while len(paths) != 0:
            solution.append(paths)
            paths = self.find_paths(free)
        return solution

    def random_index_solution(self, solution):
        i = random.randint(0, len(solution) - 1)
        j = random.randint(0, len(solution[i]) - 1)
        return i, j

    def flip(self, solution):
        new_solution = copy.deepcopy(solution)
        i, j = self.random_index_solution(new_solution)
        new_solution[i][j] = (new_solution[i][j][1], new_solution[i][j][0])
        new_solution = list(filter(None, new_solution))
        return new_solution

    def single_insertion(self, solution):
        new_solution = copy.deepcopy(solution)
        # arc to move
        i1, j1 = self.random_index_solution(new_solution)
        # index to insert
        i2, j2 = self.random_index_solution(new_solution)
        arc = new_solution[i1][j1]
        new_solution[i1].remove(arc)
        new_solution[i2].insert(j2, arc)
        if self.check_path_demand(new_solution[i2]):
            new_solution = list(filter(None, new_solution))
            return new_solution
        else:
            return None

    def double_insertion(self, solution):
        new_solution = copy.deepcopy(solution)
        # arc to move
        i1, j1 = self.random_index_solution(new_solution)
        for _ in range(4):
            if len(solution[i1]) == 1 or j1 == len(new_solution[i1]) - 1:
                i1, j1 = self.random_index_solution(new_solution)
                continue
            break
        if len(solution[i1]) == 1 or j1 == len(new_solution[i1]) - 1:
            return self.flip(solution)
        # index to insert
        i2, j2 = self.random_index_solution(new_solution)
        arc1 = new_solution[i1][j1]
        arc2 = new_solution[i1][j1 + 1]
        new_solution[i1].remove(arc1)
        new_solution[i1].remove(arc2)
        new_solution[i2].insert(j2, arc1)
        new_solution[i2].insert(j2 + 1, arc2)
        if self.check_path_demand(new_solution[i2]):
            new_solution = list(filter(None, new_solution))
            return new_solution
        else:
            return None

    def get_reverse_path(self, subpath):
        s = copy.deepcopy(subpath)
        for i, arc in enumerate(s):
            s[i] = (arc[1], arc[0])
        #s.reverse()
        return s


    def single_opt_2(self, solution):
        s = copy.deepcopy(solution)
        i = random.randint(0, len(s)-1)
        path = s[i]
        p_cost = self.recalculate_path_cost(path)
        for _ in range(4):
            p = copy.deepcopy(path)
            j, k = random.randint(0, len(path)-1), random.randint(1, len(path))
            p[j:k] = self.get_reverse_path(p[j:k])
            c = self.recalculate_path_cost(p)
            if c <= p_cost:
                s[i] = p
                break
        return s

    def double_opt_2(self, solution):
        s = copy.deepcopy(solution)
        i1, i2 = random.randint(0, len(s)-1), random.randint(0, len(s)-1)
        tmp = 0
        while i1 == i2:
            tmp+=1
            if tmp == 5:
                return solution
            i1, i2 = random.randint(0, len(s)-1), random.randint(0, len(s)-1)
        p1, p2 = s[i1], s[i2]
        i, j = random.randint(0, len(p1)-1), random.randint(0, len(p2)-1)
        if self.check_path_demand(p1[:i]+p2[j:]) and self.check_path_demand(p2[:j]+p1[i:]):
            arr1 = [p1[:i]+p2[j:], p1[:i] +
                    self.get_reverse_path(p2[j:]), self.get_reverse_path(p1[:i])+p2[j:]]
            arr2 = [p2[:j]+p1[i:], p2[:j] +
                    self.get_reverse_path(p1[i:]), self.get_reverse_path(p2[:j])+p1[i:]]
            s[i1] = min(arr1, key=lambda s: self.recalculate_path_cost(s))
            s[i2] = min(arr2, key=lambda s: self.recalculate_path_cost(s))
            return s
        else:
            return solution

    def init_population(self):
        population = []
        solution = self.find_solution()
        for _ in range(100):
            solution = self.find_solution()
            if solution not in population:
                population.append(solution)
        return population


    def solution_valid(self, solution):
        arcs = self.required_edges.keys()
        tmp = 0
        for i, path in enumerate(solution):
            for j, arc in enumerate(path):
                if arc in arcs or (arc[1], arc[0]) in arcs:
                    tmp += 1
                else:
                    return False
        if tmp == len(arcs):
            return True
        else:
            return False

    def local_search(self, solution):
        r = random.randint(0, 4)
        if r == 0:
            new_solution = self.flip(solution)
        elif r == 1:
            new_solution = self.single_insertion(solution)
        elif r == 2:
            new_solution = self.double_insertion(solution)
        elif r == 3:
            new_solution = self.single_opt_2(solution)
        else:
            ss = self.double_opt_2(solution)
            if self.solution_valid(ss):
                new_solution = ss
            else:
                print(solution)
                print('!')
                new_solution = None
        if new_solution is None:
            return solution
        return new_solution

    def step(self, population):
        for _ in range(2*self.population_size):
            x = random.choice(population)
            x_s = self.local_search(x)
            if x_s not in population and self.calculate_solution_cost(x_s) <= self.calculate_solution_cost(x):
                population.append(x_s)
        new_population = heapq.nsmallest(
            self.population_size, population, key=lambda s: self.calculate_solution_cost(s))
        score = self.calculate_solution_cost(new_population[0])
        if score < self.best_score:
            self.best = copy.deepcopy(new_population[0])
            self.best_score = score
        # avg_score = [self.calculate_solution_cost(s) for s in population]
        # self.avg_.append(np.average(avg_score))
        self.evolution.append(self.best_score)
        self.gen += 1
        return new_population

    def find_best(self, population):
        tmp = 0
        for i, solution in enumerate(population):
            s = self.calculate_solution_cost(solution)
            tmp += s
            if s < self.best_score:
                self.best = solution
                self.best_score = s
        return tmp/len(population)

    def solve(self, t):
        start_time = time()
        population = self.init_population()
        st = time()
        while time() - start_time < t - 1:
            population = self.step(population)
        self.t = time() - st

    def __str__(self):
        str_solution = 's ' + \
            self.best.__str__()[
                1:-1].replace('[', '0,').replace(']', ',0').replace(' ', '')
        cost = f'q {int(self.best_score)}'
        return str_solution + '\n' + cost


def genetic(seed, data, edges, required_edges, dis, time, size):
    carp = CARP_SOLVER(seed=seed, size=size, data=data,
                       edges=edges, required_edges=required_edges, dis=dis)
    carp.solve(time)
    return carp.__str__(), carp.best_score, carp.gen, carp.evolution, carp.t, carp.avg_


class MYTHREAD(threading.Thread):
    def __init__(self, func, args=()):
        super(MYTHREAD, self).__init__()
        self.func = func
        self.args = args

    def run(self):
        self.soulution, self.score, self.gen, self.evolution = self.func(*self.args)

    def get_result(self):
        threading.Thread.join(self)
        return self.soulution, self.score


if __name__ == '__main__':
    args = vars(parser.parse_args())
    args = preprocess(args['file'], args['s'], args['t'], 10)
    threads = []
    for _ in range(8):
        threads.append(MYTHREAD(genetic, args))
    for thread in threads:
        thread.start()
    for thread in threads:
        thread.join()
    s, tmp = None, float('inf')
    for thread in threads:
        solution, score = thread.get_result()
        if score < tmp:
            tmp = score
            s = solution
    print(s)
