from pathlib import Path
import os
import time

# time_limit_seconds = 30
time_limit_seconds = 10
random_seed = 0
accepts_random_seed = False
solver_location = "CARP_solver.py"
data_location = "test/CARP_samples"
if __name__ == '__main__':
    cwd = Path('.')
    cwd = cwd.absolute()
    cwd /= data_location
    costs = [0]*7
    dataset_names = [0]*7
    times = []
    dats = list(cwd.rglob("*.dat"))
    for i, dat in enumerate(dats):
        # name = dat.stem.split("_")[1]  # 要求命名按照 i_name 的形式。
        name = dat.stem  # 正常命名
        print(f"Running {i}/{len(dats) - 1} instance {name}. ")
        param = f'"{dat}" '
        param += f'-t {time_limit_seconds} '
        param += f'-s {random_seed} '
        if accepts_random_seed:
            param += f'-r '
        with os.popen(f'python "{solver_location}" {param}', 'r') as p:
            start = time.time()
            result = p.readlines()  # 这一步就结束了
            time_used = time.time() - start
            times.append(time_used)
            dataset_names[int(name[0])] = name
            # dataset_names.append(name)
            cost = int(result[1].split()[1])
            # costs.append(cost)
            costs[int(name[0])] = cost
            print(f"Instance {name} finished with time {time_used:e}s and cost {cost}.")
            print()

    print(dataset_names)
    print(costs)
    print(f"Total cost is {sum(costs)}. Total time used is {sum(times)}s. ")

    # baseline 是        [309, 370, 6446, 4188, 344, 482, 212], 12351.
    # 使用 Ulusoy split   [309, 368, 6446, 4188, 344, 472, 212] 优化了一点点

    # 五秒模拟退火效果：     [285, 323, 6446, 4193, 349, 486, 206]
    # 五秒SSP 50, 116, 0, 5,1， 4，18
    #                   [300, 323, 6446, 4193, 349, 486, 206]  # 这些操作对6446没有用
    # n立方SSP大幅优化为n方：133, 230, 7, 30, 14, 16, 58
    # 五秒DP              228, 593, 0, 1, 1, 4, 6
    #                   [292, 323, 6416, 4159, 349, 482, 186]
    # 五秒混合加缓存       196, 403, 0, 32, 14, 16, 58
    # 60s 退火效果      [283, 323, 5790, 3682, 318, 464, 177]

    # 五秒 liu_ray: [299, 343, 6446, 4373, 343, 526, 212]
    # 五秒 liu_ray+退火 [283, 316, 5825, 3846, 306, 449, 182]
    # [283, 316, 5789, 3971, 328, 461, 190]

    # 同学级最优解        [275, 316, 5018, 3548, 279, 400, 173]