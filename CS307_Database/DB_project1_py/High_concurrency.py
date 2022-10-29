import random
import threading
import time

import threadpool
import psycopg2

tmp = []


def select():
    s = "select * from student where student_name = '李谢恩';"
    connection = psycopg2.connect(database='db_project1', user='lee', password='buzz10161', host='localhost',
                                  port=5432)
    con = connection.cursor()
    start = time.time()
    con.execute(s)
    connection.commit()
    con.fetchone()
    end = time.time()
    con.close()
    connection.close()
    tmp.append((end - start))


if __name__ == '__main__':
    threads = []
    for i in range(1, 501):
        threads.append(threading.Thread(target=select()))
    for t in threads:
        t.start()
    tt = 0
    for i in tmp:
        tt = tt + i
    print('Average time: ' + str(round((tt / 100), 4)))
