import pymysql
def select(qry,val):
    con = pymysql.Connect(host="localhost", port=3306, user="root", password="", db="orvba")
    cmd=con.cursor()
    cmd.execute(qry,val)
    r=cmd.fetchone()
    con.close()
    return r
def select1(qry):
    con=pymysql.connect(host="localhost",port=3306,user="root",password="",db="orvba")
    cmd=con.cursor()
    cmd.execute(qry)
    r=cmd.fetchone()
    con.close()
    return r

def selectall(qry):
    con=pymysql.connect(host="localhost",port=3306,user="root",password="",db="orvba")
    cmd=con.cursor()
    cmd.execute(qry)
    r=cmd.fetchall()
    con.close()
    return r
def selectall1(qry,val):
    con=pymysql.connect(host="localhost",port=3306,user="root",password="",db="orvba")
    cmd=con.cursor()
    cmd.execute(qry,val)
    r=cmd.fetchall()
    con.close()
    return r
def selectall1s(qry):
    con=pymysql.connect(host="localhost",port=3306,user="root",password="",db="orvba")
    cmd=con.cursor()
    cmd.execute(qry)
    r=cmd.fetchall()
    con.close()
    return r
def insert(qry,val):
    con = pymysql.Connect(host="localhost", port=3306, user="root", password="", db="orvba")
    cmd = con.cursor()
    cmd.execute(qry, val)
    id=cmd.lastrowid
    con.commit()
    con.close()
    return id



def and_selectall(qry):
    con = pymysql.Connect(host="localhost", port=3306, user="root", password="", db="orvba")
    cmd = con.cursor()
    cmd.execute(qry)
    con.commit()
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    data = []
    for result in results:
        data.append(dict(zip(row_headers, result)))
    con.commit()
    return data
def and_select(qry,val):
    con = pymysql.Connect(host="localhost", port=3306, user="root", password="", db="orvba")
    cmd = con.cursor()
    cmd.execute(qry,val)
    con.commit()
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchone()
    data = []
    for result in results:
        data.append(dict(zip(row_headers, result)))
    con.commit()
    return data