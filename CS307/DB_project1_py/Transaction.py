import psycopg2
import time

if __name__ == '__main__':
    connection = psycopg2.connect(database='db_project1', user='lee', password='buzz10161', host='localhost',
                                  port=5432)
    con = connection.cursor()
    select = 'select * from student where student_id = %s;'
    con.execute('begin transaction;')
    con.execute('set transaction isolation level read uncommitted;')
    start = time.time()
    for i in range(11000001, 11100001):
        con.execute(con.mogrify(select, (i,)))
    con.execute('end transaction;')
    connection.commit()
    end = time.time()
    print(end - start)
    con.execute('begin transaction;')
    con.execute('set transaction isolation level read committed;')
    start = time.time()
    for i in range(11000001, 11100001):
        con.execute(con.mogrify(select, (i,)))
    con.execute('end transaction;')
    connection.commit()
    end = time.time()
    print(end - start)
    con.execute('begin transaction;')
    con.execute('set transaction isolation level repeatable read;')
    start = time.time()
    for i in range(11000001, 11100001):
        con.execute(con.mogrify(select, (i,)))
    con.execute('end transaction;')
    connection.commit()
    end = time.time()
    print(end - start)
    con.execute('begin transaction;')
    con.execute('set transaction isolation level serializable;')
    start = time.time()
    for i in range(11000001, 11100001):
        con.execute(con.mogrify(select, (i,)))
    con.execute('end transaction;')
    connection.commit()
    end = time.time()
    print(end - start)
