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
    sql = "INSERT INTO student VALUES (%s, %s, %s, %s);"
    delete = 'DELETE FROM student WHERE student_id = %s;'
    update = "update student set student_name = 'LZN' where student_id = %s;"
    for i in range(1, 10001):
        con.execute(sql, (i, '李子南', 'M', '格兰芬多(Gryffindor)'))
    connection.commit()
    start = time.time()
    for i in range(1, 10001):
        con.execute(con.mogrify(update, (i,)))
    connection.commit()
    end = time.time()
    for i in range(1, 10001):
        con.execute(con.mogrify(delete, (i,)))
    connection.commit()
    con.close()
    connection.close()
    print(end - start)
