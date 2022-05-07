from flask import  *
from src.dbconn import *

app=Flask(__name__)
app.secret_key="ab"
path="static\\files"
import functools
def login_required(func):
    @functools.wraps(func)
    def secure_function():
        if "mech_id" not in session:
            return redirect ("/")
        return func()
    return secure_function

@app.route("/logout")
# @login_required

def logout():
    session.clear()
    return render_template("login.html")

# @app.route('/')
# def home():
#     return render_template('index1.html')

@app.route('/')
def login1():
    return render_template('login1.html')

@app.route('/enter',methods=['POST'])
def enter():
    username=request.form['textfield']
    password=request.form['textfield2']
    qry="SELECT * FROM login WHERE Username=%s"
    val=(username)
    res=select(qry,val)
    print(res)
    if (res == None):
        return '''<script>alert("invalid username.");window.location='/'</script>'''
    else:
        pascheck = res[2]
        if (pascheck != password):
            return '''<script>alert("Password  Invalid");window.location='/'</script>'''
        if(res[3]=="admin"):
            session['mech_id']=res[0]
            return '''<script>alert("Login successfull.");window.location='/adminhome'</script>'''
        elif(res[3]=="mechanic"):
            session['mech_id']=res[0]
            return  '''<script>alert("Login successfull.");window.location='/mechanichome'</script>'''

@app.route('/adminhome')
def adminhome():
    return render_template('adminhome.html')

@app.route('/managemech')
@login_required
def managemech():
    qry="SELECT mechanic.*,service_center.name FROM mechanic JOIN service_center  WHERE service_center.`service_id`=mechanic.`Service_id`"
    res=selectall(qry)
    print(res)
    return render_template('mngmechanic.html',val=res)
@app.route('/mngemech',methods=['get','post'])
@login_required
def mngemech():
    id = request.args.get('id')
    qry="SELECT mechanic.*,service_center.name,service_center.service_id FROM mechanic JOIN service_center  WHERE service_center.`service_id`=mechanic.`Service_id` AND `mechanic`.`Login_id`=%s"
    val=(str(id))
    session['uid'] = id
    res=select(qry,val)
    print( session['uid'])
    qrys = "SELECT * FROM service_center"
    res2 = selectall(qrys)
    return render_template('editmech.html',val=res,vals=res2)
@app.route('/editmech',methods=['get','post'])
@login_required
def editmech():
    try:
        fname=request.form['fname']
        lname=request.form['lname']
        scname=request.form['select']

        phone=request.form['ph']
        email=request.form['email']
        qry="UPDATE `mechanic` SET `Fname`=%s,`Lname`=%s,`Phone`=%s,`Email`=%s,`Service_id`=%s WHERE `Login_id`=%s"
        val=(fname,lname,phone,email,scname,str(session['uid']))
        print(session['uid'])
        insert(qry,val)
        return '''<script>alert("Mechanic details updated successfully");window.location='/managemech'</script>'''
    except Exception as e:
        return '''<script>alert("Already Exist");window.location='/mngemech'</script>'''


@app.route('/deletemech',methods=['get','post'])
@login_required
def deletemech():
    id=request.args.get('id')
    print(str(id))
    qry = "DELETE FROM `mechanic` WHERE Login_id=%s"
    val = (str(id))
    insert(qry, val)
    qry = "DELETE FROM login WHERE Login_id=%s"
    val=(str(id))
    insert(qry, val)
    return '''<script>alert("user deleted.");window.location='/managemech'</script>'''
@app.route('/addmechanic')
@login_required
def addmechanic():
    qry="SELECT * FROM service_center"
    res=selectall(qry)
    return render_template('addmechanic.html',val=res)
@app.route('/addmech',methods=['get','post'])
@login_required
def addmech():
    try:
        fname = request.form['fname']
        lname = request.form['lname']
        sid = request.form['sid']
        phone = request.form['number']
        email = request.form['email']
        user=request.form['user']
        passwd=request.form['passwd']
        qry = "INSERT INTO login VALUES(NULL,%s,%s,'mechanic')"
        val = (user,str(passwd))
        lid=insert(qry,val)
        print(lid)
        qry="INSERT INTO `mechanic` VALUES(NULL,%s,%s,%s,%s,%s,%s)"
        val=(fname,lname,phone,email,str(sid),str(lid))
        insert(qry,val)
        return '''<script>alert("New Mechanic Added");window.location='/managemech'</script>'''
    except Exception as e:
        return '''<script>alert("Already Exist");window.location='/addmechanic'</script>'''

@app.route('/manageservicec')
@login_required
def manageservicec():
    qry = "SELECT * FROM service_center"
    res = selectall(qry)
    return render_template('mngservicec.html',val=res)
@app.route('/mngservice',methods=['get','post'])
@login_required
def mngservice():
    id = request.args.get('id')
    qry="SELECT * FROM `service_center` WHERE `service_id`=%s"
    val=str(id)
    session['sid'] = val
    res=select(qry,val)
    return render_template('editservice.html',val=res)
@app.route('/editservice',methods=['get','post'])
@login_required
def editservice():
    try:
        name=request.form['name']
        lat=request.form['lat']
        lon=request.form['lon']
        phone=request.form['no']
        email=request.form['email']
        qry="UPDATE `service_center` SET `name`=%s,`latitude`=%s,`longitude`=%s,`phone`=%s,`email`=%s WHERE `service_id`=%s"
        val=(name,lon,lat,phone,email,str(session['sid']))
        print(session['sid'])
        insert(qry,val)
        return '''<script>alert("Service center details updated successfully");window.location='/manageservicec'</script>'''
    except Exception as e:
        return '''<script>alert("Already Exist");window.location='/mngservice'</script>'''
@app.route('/deleteservice',methods=['get','post'])
def deleteservice():
    id=request.args.get('id')
    qry = "DELETE FROM `service_center` WHERE `service_id`=%s"
    val = (str(id))
    insert(qry, val)
    return '''<script>alert("sc deleted.");window.location='/manageservicec'</script>'''
@app.route('/addservicecenter')
@login_required
def addservicecenter():
    return render_template('addservice.html')
@app.route('/addservice',methods=['get','post'])
@login_required
def addservice():
    try:
        name = request.form['name']
        lat = request.form['lat']
        lon = request.form['lon']
        phone = request.form['no']
        email = request.form['email']
        qry = "INSERT INTO `service_center` VALUES(NULL,%s,%s,%s,%s,%s)"
        val = (name, str(lat), str(lon),phone, email)
        insert(qry,val)
        return '''<script>alert("New Service center added");window.location='/manageservicec'</script>'''
    except Exception as e:
        print(e)
        return '''<script>alert("Already Exist");window.location='/addservicecenter'</script>'''

@app.route('/managepetrolp')
@login_required
def managepetrolp():
    qry = "SELECT * FROM petrol_pump"
    res = selectall(qry)
    return render_template('mngpetrolp.html',val=res)
@app.route('/mngpetrolp',methods=['get','post'])
@login_required
def mngpetrolp():
    id = request.args.get('id')
    qry = "SELECT * FROM petrol_pump where p_id=%s"
    val = str(id)
    session['pid'] = val
    res = select(qry, val)
    return render_template('editpetrolp.html', val=res)
@app.route('/editpetrolp',methods=['get','post'])
@login_required
def editpetrolp():
    name = request.form['name']
    lat = request.form['lat']
    lon = request.form['lon']
    phone = request.form['no']
    qry = "UPDATE `petrol_pump` SET `name`=%s,`phone_no`=%s,`latitude`=%s,`longitude`=%s WHERE `p_id`=%s"
    val = (name, phone, lon, lat, str(session['pid']))
    print(session['pid'])
    insert(qry, val)
    return '''<script>alert("petrol pump details updated successfully");window.location='/managepetrolp'</script>'''
@app.route('/deletepetrol',methods=['get','post'])
@login_required
def deletepetrol():
    id=request.args.get('pid')
    qry = "DELETE FROM petrol_pump WHERE p_id=%s"
    val = (str(id))
    insert(qry, val)
    return '''<script>alert("pp deleted.");window.location='/managepetrolp'</script>'''
@app.route('/addpetrolp')
@login_required
def addpetrolp():
    return render_template('addpetrol.html')
@app.route('/addpetrol',methods=['get','post'])
@login_required
def addpetrol():
    name = request.form['name']
    lat = request.form['lat']
    lon = request.form['lon']
    phone = request.form['no']
    qry = "insert into petrol_pump values(NULL,%s,%s,%s,%s)"
    val = (name, phone, lat,lon)
    insert(qry, val)
    return '''<script>alert("New petrol pump added successfully");window.location='/managepetrolp'</script>'''
@app.route('/works')
@login_required
def works():
    qry="SELECT * from mechanic"
    res=selectall(qry)
    return render_template('workview.html',val=res)
@app.route('/workmng',methods=['get'])
@login_required
def workmng():
    id = request.args.get('id')
    qry="SELECT * FROM `mechanic` WHERE `login_id`=%s"
    val=(str(id))
    print(val)
    resu=select(qry,val)
    qrys="SELECT `request`.*,`user`.`Fname`,`user`.`Lname`  FROM `user` JOIN `request` ON `user`.`login_id`=`request`.`login_id` WHERE request.m_id='"+val+"'  UNION SELECT `request`.*,`traffic`.`Fname`,`traffic`.`Lname`  FROM `traffic` JOIN `request` ON `traffic`.`login_id`=`request`.`login_id` WHERE request.m_id='"+val+"'"
    res=selectall1s(qrys)
    print(res)
    return render_template('workmng.html', val=res,name=resu)

@app.route('/viewfeed')
@login_required
def viewfeed():
    # qry="SELECT `login`.`Username`,`login`.`User_type`,`feedback`.* FROM `feedback` JOIN `login` ON `feedback`.`User_id`=`login`.`Login_id`"
    # res=selectall(qry)
    return render_template('viewfeed.html')

@app.route('/viewfeed1',methods=['post'])
@login_required
def viewfeed1():
    type=request.form['select']

    if(type=='User'):
        qry="SELECT `login`.`Username`,`login`.`User_type`,`feedback`.* FROM `feedback` JOIN `login` ON `feedback`.`User_id`=`login`.`Login_id`  WHERE `feedback`.`User_id`!='public'"
        res=selectall(qry)
        return render_template('viewfeed.html',val=res)
    else:
        qry="SELECT `feedback`.`User_id`,`Feedback_id`,`Feedback_id`,`Feedback_id`,`Date`,`Feedback` FROM `feedback` WHERE  `User_id`='public'"
        res = selectall(qry)
        return render_template('viewfeed.html', val=res)



@app.route('/mechanichome')
def mechanichome():
    return render_template('mechanichome.html')

@app.route('/viewprof',methods=['get'])
@login_required
def viewprof():
    qry = "SELECT `mechanic`.*,`service_center`.`name`,`service_center`.`phone`FROM `mechanic` JOIN `service_center` ON `mechanic`.`Service_id`=`service_center`.`service_id` AND  `mechanic`.`Login_id`=%s"
    val = (str(session['mech_id']))
    vals = select(qry,val)
    print(val)
    qry = "SELECT * FROM `login` WHERE `Login_id`=%s"
    val = (str(session['mech_id']))
    res = select(qry,val)
    return render_template('upmech.html',val=vals,value=res)
@app.route('/upmech',methods=['get','post'])
@login_required
def upmech():
    fname = request.form['fname']
    lname = request.form['lname']
    phone = request.form['number']
    email = request.form['email']
    user = request.form['user']
    passwd = request.form['passwd']
    qry = "UPDATE `login` SET `Username`=%s,`Password`=%s WHERE `Login_id`=%s"
    val = (user, passwd, str(session['mech_id']))
    lid = insert(qry, val)
    print(str(session['mech_id']))
    qry = "UPDATE `mechanic` SET `Fname`=%s,`Lname`=%s,`Phone`=%s,`Email`=%s WHERE `Login_id`=%s"
    val = (fname, lname, phone, email, str(session['mech_id']))
    res=insert(qry, val)
    print(res)
    return '''<script>alert("Profile updated.");window.location='/viewprof'</script>'''


@app.route('/viewrequest')
@login_required
def viewrequest():
    val = (str(session['mech_id']))
    qry="SELECT `user`.`Fname`,`user`.`Lname`,`user`.`Phone`,request.* FROM USER JOIN request WHERE `user`.`Login_id`=`request`.`Login_id` and request.`m_id`='"+val+"' AND `request`.`status`='pending' union SELECT `traffic`.`Fname`,`traffic`.`Lname`,`traffic`.`Phone`,request.* FROM traffic JOIN request WHERE `traffic`.`Login_id`=`request`.`Login_id` and request.`m_id`='"+val+"' AND `request`.`status`='pending'"
    res=selectall1s(qry)
    return render_template('viewrequest.html',value=res)
@app.route('/acceptr')
@login_required
def acceptr():
    id=request.args.get('id')
    qry="UPDATE `request` SET `status`='accepted' WHERE `r_id`=%s"
    val=(str(id))
    insert(qry,val)
    return '''<script>alert("Request Accepted!!!");window.location='/viewrequest'</script>'''
@app.route('/rejectr')
@login_required
def rejectr():
    id=request.args.get('id')
    qry="UPDATE `request` SET `status`='rejected' WHERE `r_id`=%s"
    val=(str(id))
    insert(qry,val)
    return '''<script>alert("Request Rejected!!!");window.location='/viewrequest'</script>'''
@app.route('/track')
def track():
    id = request.args.get('id')
    qry="select * from service_center where "

@app.route('/updatework')
@login_required
def updatework():
    vals = (str(session['mech_id']))
    qry="SELECT request.*,user.`Fname` FROM request JOIN USER ON request.`Login_id`=`user`.`Login_id`  WHERE request.`m_id`='"+vals+"' AND request.status='accepted' union SELECT request.*,traffic.`Fname` FROM request JOIN traffic ON request.`Login_id`=`traffic`.`Login_id`  WHERE request.`m_id`='"+vals+"' AND request.status='accepted'"
    res=selectall1s(qry)
    return render_template('updatework.html',val=res)
@app.route('/upwork',methods=['get','post'])
@login_required
def upwork():
    work=request.form['r_id']
    status = request.form['textfield2']
    qry="UPDATE request SET STATUS=%s WHERE r_id=%s"
    val=(status,str(work))
    insert(qry,val)
    return '''<script>alert("work status updated");window.location='/updatework'</script>'''

@app.route('/viewpetrol')
@login_required
def viewpetrol():
    qry="select * from petrol_pump"
    res=selectall(qry)
    return render_template('viewpetrolp.html',val=res)
@app.route('/viewfeedmech')
@login_required
def viewfeedmech():

    qry="SELECT `login`.`Username`,`login`.`User_type`,`feedback`.* FROM `feedback` JOIN `login` ON `login`.`Login_id`=`feedback`.`User_id`"
    res=selectall(qry)
    return render_template('viewfeedmech.html',val=res)



@app.route('/login', methods=['GET','POST'])
def login():

    username = request.args.get('username')
    password = request.args.get('password')
    qry = "SELECT * FROM `login` WHERE `Username`=%s AND `Password`=%s"
    val = (username, password)
    result = select(qry,val)
    print(result)

    print(id)
    ty=result[3]
    if result is None:
        return jsonify({'task': "invalid"})
    else:
        id = result[0]
        return jsonify({'task': id+"#"+ty})



if __name__== '__main__' :
    app.run(debug=True)