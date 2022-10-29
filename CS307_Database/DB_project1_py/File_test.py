import pandas
import time
import psycopg2

if __name__ == '__main__':
    connection = psycopg2.connect(database='db_project1', user='lee', password='buzz10161', host='localhost',
                                  port=5432)
    con = connection.cursor()
    sql = "INSERT INTO student VALUES (%s, %s, %s, %s);"
    delete = 'DELETE FROM student WHERE student_id = %s;'
    update = "update student set student_name = 'LZN' where student_name = '李子南';"
    for i in range(1, 1001):
        con.execute(sql, (i, '李子南', 'M', '格兰芬多(Gryffindor)'))
    connection.commit()
    start = time.time()
    con.execute(update)
    connection.commit()
    end = time.time()
    for i in range(1, 1001):
        con.execute(con.mogrify(delete, (i,)))
    connection.commit()
    con.close()
    connection.close()
    print(end - start)
    file = pandas.read_csv('clean_data.csv')
    for i in range(1, 1001):
        file.append([{'name': '李子南', 'sex': 'M', 'college': '格兰芬多(Gryffindor)', 'student_id': i}],
                    ignore_index=True)
    start2 = time.time()
    for i in range(1, 1001):
        file['name'][file['student_id'] == i] = 'LZN'
    end2 = time.time()
    file = file[~file['student_id'].isin(['LZN'])]
    print(end2 - start2)
