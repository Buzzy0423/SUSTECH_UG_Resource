import random
import time

import psycopg2


class Ms:
    connect = []

    def login(self, user_name, password):
        self.connect.append(user_name)
        self.connect.append(password)
        try:
            connection = psycopg2.connect(database='db_project1', user=self.connect[0], password=self.connect[1],
                                          host='localhost', port=5432)
            print('Login successfully.')
        except Exception as e:
            print('Login failed, please check your password and username.')
        print('Login successfully.')

    def createuser(self, user_name, password):
        try:
            connection = psycopg2.connect(database='db_project1', user='lee', password='buzz10161', host='localhost',
                                          port=5432)
            con = connection.cursor()
            con.execute('create user ' + user_name + ' with password \'' + password + '\'')
            con.execute('grant insert,delete on student_course to ' + user_name)
            con.execute('grant select on course,class,class_detail,class_teacher,course_prerequisite to ' + user_name)
            print('Create user successfully.')
            con.close
        except Exception as e:
            print(e)
            print('------Please check your username or password!------')
            con.close
        finally:
            con.close

    def searchcourse_info(self, coursename):
        try:
            connection = psycopg2.connect(database='db_project1', user=self.connect[0], password=self.connect[1],
                                          host='localhost', port=5432)
            con = connection.cursor()
            con.execute('select * from course c join class c2 on c.course_id = c2.couse_id')
        except Exception as e:
            print(e)
            print('Please try again.')


if __name__ == '__main__':
    connection = psycopg2.connect(database='db_project1', user='lee', password='buzz10161', host='localhost',
                                  port=5432)
    con = connection.cursor()
    select = 'select * from student where student_id > %s;'
    insert = "INSERT INTO student VALUES (%s, %s, %s, %s);"
    delete = 'DELETE FROM student WHERE student_id = %s;'
    update = "UPDATE student SET student_name = 'LZN' WHERE student_id = %s;"
    start = time.time()
    # for i in range(1, 10001):
    #     rand = random.randint(1, 5)
    #     if rand == 1:
    #         con.execute(con.mogrify(select, (i,)))
    #     elif rand == 2:
    #         con.execute(insert, (i, '李子南', 'M', '格兰芬多(Gryffindor)'))
    #     elif rand == 3:
    #         con.execute(con.mogrify(delete, ((i - 4), )))
    #     else:
    #         con.execute(con.mogrify(update, ((i - 4), )))
    for i in range(11000001, 11000011):
        con.execute(con.mogrify(select, (i,)))
        print(con.fetchone())
    connection.commit()
    end = time.time()
    con.execute("DELETE FROM student WHERE student_name LIKE '%李子南%' OR student_name LIKE '%LZN%';")
    connection.commit()
    con.close()
    connection.close()
    print(end - start)
