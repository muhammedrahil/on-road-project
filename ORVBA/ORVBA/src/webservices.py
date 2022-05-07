from flask import *
import pymysql
from src.dbconn import *
app =  Flask(__name__)

app.secret_key="key"

@app.route('/login', methods=['GET','POST'])
def login():
    username = request.form['username']
    password = request.form['password']
    qry = "SELECT * FROM `login` WHERE `Username`=%s AND `Password`=%s"
    val = (username, password)
    result = select(qry,val)

    if result is None:
        return jsonify({'result': "invalid"})
    else:
        lid = str(result[0])

        # print(lid)
        ty = result[3]
        return jsonify({'result': lid,'type':ty})


@app.route('/userreg',methods=['get','post'])
def userreg():
    try:
        fname = request.form['fname']
        lname = request.form['lname']
        gender = request.form['gender']
        phone = request.form['phone']
        email = request.form['email']
        place = request.form['place']
        post = request.form['post']
        pin = request.form['pin']
        username=request.form['username']
        password = request.form['password']
        qry="INSERT INTO `login` VALUES(NULL,%s,%s,'user')"
        val=(username,password)
        id = insert(qry,val)
        qry="INSERT INTO `user` VALUES(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        val=(fname,lname,gender,phone,place,post,pin,email,str(id))
        insert(qry,val)
        return jsonify({"task": "success"})
    except Exception as e:
        return jsonify({"task": "error"})

@app.route('/fdbks',methods=['post'])
def fdbks():
    lid=request.form['lid']
    fdbk=request.form['feedback']
    qry="INSERT INTO `feedback` VALUES(null,%s,current_date ,%s)"
    val=(str(lid),fdbk)
    insert(qry,val)
    return jsonify({"result":"success"})
@app.route('/pfdbks',methods=['post'])
def pfdbks():
    lid=request.form['lid']
    fdbk=request.form['feedback']
    qry="INSERT INTO `feedback` VALUES(null,%s,current_date ,%s)"
    val=(str(lid),fdbk)
    insert(qry,val)
    return jsonify({"result":"success"})
@app.route('/req1',methods=['post'])
def req1():
    uid=request.form['lid']
    rqst=request.form['request']
    lat=request.form['lat']
    lon=request.form['lon']
    m_id=request.form['mid']
    qry="INSERT INTO `request` VALUES(NULL,%s,%s,CURRENT_DATE,CURRENT_TIME ,%s,%s,%s,'pending')"
    val=(str(uid),rqst,str(lat),str(lon),str(m_id))
    insert(qry,val)
    return jsonify({"result":"success"})

# @app.route('/viewpetrol',methods=['post'])
# def viewpetrol():
#     qry="select * from petrol_pump"
#     res=and_selectall(qry)
#     print(res)
#     return jsonify(res)

# @app.route('/viewservice',methods=['post'])
# def viewservice():
#     qry="SELECT `service_center`.`name`,`service_center`.`phone`,`service_center`.`latitude`,`service_center`.`longitude`, (3959 * ACOS ( COS ( RADIANS('11.874477') ) * COS( RADIANS( `service_center`.`latitude`) ) * COS( RADIANS( `service_center`.`longitude` ) - RADIANS('75.370369') ) + SIN ( RADIANS('11.874477') ) * SIN( RADIANS( `service_center`.`latitude` ) ))) AS user_distance FROM `service_center` HAVING user_distance  < 6.2137"
#     res=and_selectall(qry)
#     return jsonify(res)

# @app.route('/updateuser',methods=['post'])
# def updateuser():
#     uid=request.form['uid']
#     qry="selct * from users_reg where user_id=%s"
#     val=(str(uid))
#     res=and_select(qry,val)
#     qry="select * from login where user_id=%s"
#     val=(str(uid))
#     result=and_select(qry,val)
#     return jsonify(res,result)
# @app.route('/upuser',methods=['post'])
# def upuser():
#     uid=request.form['uid']
#     name = request.form['name']
#     phnumber = request.form['ph']
#     e_mail = request.form['e_mail']
#     username = request.form['u_name']
#     passw = request.form['passw']
#     qry = "update login set username=%s,passwd=%s where user_id=%s "
#     val = (username, passw,str(uid))
#     insert(qry, val)
#     qry = "update user_reg set name=%s,e_mail=%s,phone=%s where user_id=%s"
#     val = (name, e_mail, str(phnumber), str(uid))
#     insert(qry, val)
#     return jsonify({"result": "success"})

@app.route('/nearestservice',methods=['post'])
def nearestservice():
    latitude = request.form['lati']
    longitude = request.form['longi']
    print(latitude)
    print(longitude)
    qry="SELECT `service_center`.`service_id`,`service_center`.`name`,`service_center`.`phone`,`service_center`.`email`,`service_center`.`latitude`,`service_center`.`longitude`, (3959 * ACOS ( COS ( RADIANS('" + str(latitude) + "') ) * COS( RADIANS( service_center.latitude) ) * COS( RADIANS( service_center.longitude ) - RADIANS('" + str(longitude) + "') ) + SIN ( RADIANS('" + str(latitude) + "') ) * SIN( RADIANS( service_center.latitude ) ))) AS user_distance FROM service_center HAVING user_distance  < 1000"
    print(qry)
    res=and_selectall(qry)
    print(res)
    return jsonify(res)

@app.route('/nearestpetrol',methods=['get','post'])
def nearestpetrol():
    latitude = request.form['lati']
    longitude = request.form['longi']
    print(latitude)
    print(longitude)
    qry="SELECT petrol_pump.name,petrol_pump.phone_no,petrol_pump.latitude,petrol_pump.longitude , (3959 * ACOS ( COS ( RADIANS('" + str(latitude) + "') ) * COS( RADIANS( petrol_pump.latitude) ) * COS( RADIANS( petrol_pump.longitude ) - RADIANS('" + str(longitude) + "') ) + SIN ( RADIANS('" + str(latitude) + "') ) * SIN( RADIANS( petrol_pump.latitude ) ))) AS user_distance FROM petrol_pump HAVING user_distance  < 6.2137"
    print(qry)
    res=and_selectall(qry)
    print(res)
    return jsonify(res)

@app.route('/view_status',methods=['get','post'])
def view_status():
    uid=request.form['lid']
    print(uid)
    # print("hy")
    qry="SELECT `mechanic`.`Fname`,`mechanic`.`Lname`,`mechanic`.`Phone`,`request`.`status` FROM `mechanic` JOIN `request` JOIN `user` ON `request`.`m_id`=`mechanic`.`login_id` AND `request`.`Login_id`=`user`.`Login_id` AND `request`.`Login_id`='"+str(uid)+"'"
    res=and_selectall(qry)
    print(res)
    return jsonify(res)

@app.route('/view_mechanic',methods=['post'])
def view_mechanic():
    qry="SELECT * FROM `mechanic`"
    res=and_selectall(qry)
    return jsonify(res)

@app.route('/complaint',methods=['post'])
def complaint():
    complaint=request.form['complaint']
    lid=request.form['lid']
    latitude=request.form['lati']
    longitude = request.form['longi']
    qry="INSERT INTO `complaints` VALUES(NULL ,%s,CURRENT_DATE,%s,%s,%s)"
    values=(str(lid),complaint,str(latitude),str(longitude))
    print(lid)
    insert(qry,values)
    return jsonify({"result": "success"})


@app.route('/traffic',methods=['get','post'])
def traffic():
    try:
        fname = request.form['fname']
        lname = request.form['lname']
        control_room = request.form['control_room']
        police_station = request.form['police_station']
        phone = request.form['phone']
        gender = request.form['gender']
        username = request.form['username']
        password = request.form['password']
        qry = "INSERT INTO `login` VALUES(NULL,%s,%s,'traffic')"
        val = (username, password)
        id = insert(qry, val)
        qry = "INSERT INTO `traffic` VALUES(NULL,%s,%s,%s,%s,%s,%s,%s)"
        val = (fname, lname, phone, control_room, police_station, str(id),gender)
        insert(qry, val)
        return jsonify({"result": "success"})
    except Exception as e:
        return jsonify({"result": "error"})
@app.route('/view_complaints',methods=['get','post'])
def view_complaints():
    qry="SELECT `complaints`.*,`user`.`Fname`,`user`.`Lname` FROM  `user` JOIN `complaints` WHERE `complaints`.`User_id`=`user`.`login_id` "
    res = and_selectall(qry)
    print(res)
    return jsonify(res)

if __name__=='__main__':
    app.run(host='0.0.0.0',port=5000)

