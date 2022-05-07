/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - orvba
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`orvba` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `orvba`;

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `Complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `User_id` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Complaints` varchar(600) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`Complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`Complaint_id`,`User_id`,`Date`,`Complaints`,`latitude`,`longitude`) values 
(1,4,'2021-01-11','in ci',0,0),
(2,4,'2021-01-11','urg',0,0),
(3,4,'2021-01-11','kkk',11.3433888,75.7387815),
(4,4,'2021-02-27','tcc6gu',11.2587267,75.7831139),
(5,2,'2022-03-18','hi',11.2577323,75.7845044),
(6,2,'2022-03-18','not ok',11.2577323,75.7845044);

/*Table structure for table `fee` */

DROP TABLE IF EXISTS `fee`;

CREATE TABLE `fee` (
  `Fee_id` int(11) NOT NULL AUTO_INCREMENT,
  `Mechanic_id` int(11) NOT NULL,
  `Fee` bigint(20) NOT NULL,
  PRIMARY KEY (`Fee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `fee` */

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `Feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `User_id` varchar(23) NOT NULL,
  `Date` date NOT NULL,
  `Feedback` varchar(600) NOT NULL,
  PRIMARY KEY (`Feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`Feedback_id`,`User_id`,`Date`,`Feedback`) values 
(1,'2','2021-01-11','ok'),
(2,'4','2021-01-11','dd'),
(3,'6','2021-01-11','nice'),
(4,'2','2022-03-18','good'),
(5,'2','2022-03-18','good'),
(6,'2','2022-03-18','good'),
(7,'36','2022-03-18','goooood'),
(8,'public','2022-03-18','shsh'),
(9,'public','2022-03-18','hjmkk'),
(10,'public','2022-03-18','hhj'),
(11,'public','2022-03-18','good'),
(12,'public','2022-03-18','good'),
(13,'public','2022-03-18','good'),
(14,'public','2022-03-18','good');

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `Location_id` int(11) NOT NULL AUTO_INCREMENT,
  `User_id` int(11) NOT NULL,
  `Latitude` float NOT NULL,
  `Longitude` float NOT NULL,
  PRIMARY KEY (`Location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `location` */

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `Login_id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(300) NOT NULL,
  `Password` varchar(300) NOT NULL,
  `User_type` varchar(300) NOT NULL,
  PRIMARY KEY (`Login_id`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`Login_id`,`Username`,`Password`,`User_type`) values 
(1,'admin','admin','admin'),
(2,'ram','123','user'),
(3,'thanveer','1111','mechanic'),
(4,'amal','111','user'),
(6,'trf','123','traffic'),
(8,'tr','32','mechanic'),
(12,'mechanic','1111','mechanic'),
(20,'hisana','1111','mechanic'),
(22,'sabin','1111','mechanic'),
(23,'shahid cv','1111','mechanic'),
(24,'vishnu','1111','mechanic'),
(25,'arshad','1111','mechanic'),
(26,'suneer','1111','mechanic'),
(27,'suhaila','1111','mechanic'),
(28,'shega','1111','mechanic'),
(31,'fuhad','1111','mechanic'),
(32,'rishad','1111','mechanic'),
(33,'abayjith','1111','mechanic'),
(34,'labeeb','1111','mechanic'),
(35,'V','123456','mechanic'),
(36,'anu','anu','traffic');

/*Table structure for table `mechanic` */

DROP TABLE IF EXISTS `mechanic`;

CREATE TABLE `mechanic` (
  `Mechanic_id` int(11) NOT NULL AUTO_INCREMENT,
  `Fname` varchar(300) NOT NULL,
  `Lname` varchar(300) NOT NULL,
  `Phone` bigint(20) NOT NULL,
  `Email` varchar(300) NOT NULL,
  `Service_id` int(11) NOT NULL,
  `Login_id` int(11) NOT NULL,
  PRIMARY KEY (`Mechanic_id`),
  UNIQUE KEY `Phone` (`Phone`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

/*Data for the table `mechanic` */

insert  into `mechanic`(`Mechanic_id`,`Fname`,`Lname`,`Phone`,`Email`,`Service_id`,`Login_id`) values 
(1,'Thanveer','ahmad',8789088977,'thanveer@gmail.com',15,3),
(4,'Shabeeb','T',8088374121,'shabeeb@gmail.com',4,12),
(5,'hisana','kp',9463748364,'hisushebi@gmail.com',5,20),
(6,'sabin','pp',9865789878,'sabin002@gmail.com',6,22),
(7,'shahid','cv',9526848332,'shahidcv0@gmail.com',7,23),
(8,'vishnu','T',8088374141,'vishnu@gmail.com',13,24),
(9,'arshad','p',8129230640,'arshu@gmail.com',8,25),
(10,'suneer','kp',8129233232,'suneer009@gmail.com',15,26),
(11,'suhaila','pp',8088376586,'suhailapp09@gmai.com',19,27),
(12,'shega','rahoof',9526849898,'shegarahoof@gmail.com',20,28),
(13,'fuhad','R',8129239563,'fuhad45@gmail.com',21,31),
(14,'Rishad','pp',8129239856,'rishad98@gmai.com',22,32),
(15,'Abayjith','T',8129236895,'abayjith87@gmail.com',24,33),
(16,'Labeeb','pp',8129231234,'labeebtta@gmail.com',25,34),
(17,'swaswa','a',9087654321,'n@gmail.com',1,35);

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `Payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `User_id` int(11) NOT NULL,
  `Mechanic_id` int(11) NOT NULL,
  `Work_id` int(11) NOT NULL,
  `Amount` bigint(20) NOT NULL,
  PRIMARY KEY (`Payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

/*Table structure for table `petrol_pump` */

DROP TABLE IF EXISTS `petrol_pump`;

CREATE TABLE `petrol_pump` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `phone_no` bigint(20) NOT NULL,
  `latitude` varchar(300) NOT NULL,
  `longitude` varchar(300) NOT NULL,
  PRIMARY KEY (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `petrol_pump` */

insert  into `petrol_pump`(`p_id`,`name`,`phone_no`,`latitude`,`longitude`) values 
(1,'BHARATH',9834578909,'56.33','11.12'),
(2,'INDIAN  OIL',9846111111,'11.2570701','75.7845671'),
(3,'ESSAR',9846222222,'11.2570701','75.7845671'),
(4,'RELIANCE',9745666666,'11.2570701','75.7845671'),
(5,'HP PETROLE',9846222222,'11.2570701','61.112');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `Login_id` int(11) NOT NULL,
  `request` varchar(300) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `latitude` varchar(300) NOT NULL,
  `longitude` varchar(300) NOT NULL,
  `m_id` int(11) NOT NULL,
  `status` varchar(300) NOT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`r_id`,`Login_id`,`request`,`date`,`time`,`latitude`,`longitude`,`m_id`,`status`) values 
(1,2,'rest','2021-01-11','02:00:00','11.2588','75.8',12,'pending'),
(2,4,'need','2021-01-11','16:47:20','11.3433908','75.73878',3,'accepted'),
(3,6,'urgnt','2021-01-11','17:11:56','11.3433894','75.7387817',3,'onprocess'),
(4,6,'gbb','2021-01-11','17:34:22','11.3433903','75.7387793',3,'pending'),
(5,4,'pp','2021-01-11','18:26:46','11.3433903','75.7387784',3,'pending'),
(6,6,'jbv','2021-01-11','18:32:34','11.3433891','75.7387817',3,'pending'),
(7,2,'tttt','2022-03-18','09:24:44','11.2577326','75.784495',3,'onprocess'),
(8,36,'hhj','2022-03-18','15:14:12','11.2577263','75.7844881',3,'pending'),
(9,36,'yyy','2022-03-18','15:16:47','11.2577315','75.7844974',35,'pending');

/*Table structure for table `service_center` */

DROP TABLE IF EXISTS `service_center`;

CREATE TABLE `service_center` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) NOT NULL,
  `latitude` varchar(300) NOT NULL,
  `longitude` varchar(300) NOT NULL,
  `phone` varchar(300) NOT NULL,
  `email` varchar(300) NOT NULL,
  PRIMARY KEY (`service_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

/*Data for the table `service_center` */

insert  into `service_center`(`service_id`,`name`,`latitude`,`longitude`,`phone`,`email`) values 
(1,'Renault','75.78456','11.12','9878909890','abc@gmail.com'),
(2,'FORD','75.738','21.253','9846111111','fordtirur@gmail.com'),
(4,'Maruthi Suzuki','11.221','76.881','9544288828','maruthisuzuki@gmail.com'),
(5,'Hundayi','11.987','22.553','9745666666','hundayi001@gmail.com'),
(6,'BMW India','28.225','87.225','9746948488','bmwindia@gmail.com'),
(7,'Porche','12.225','21.225','9846222222','porche@gmail.com'),
(8,'TATA','85.225','36.225','9746228822','tataindia@gmail.com'),
(13,'fiat','21.225','23.456','9745666685','fiatindia@gmail.com'),
(15,'TOYOTA','32.225','32.225','9526848332','toyota@gmail.com'),
(19,'KIA','21.225','32.114','9876543210','kia@gmail.com'),
(20,'Nissan','23.114','23.116','9746228845','nissan@gmail.com'),
(21,'HONDA Pvt','32.445','32.478','9746228548','honda@gmail.com'),
(22,'BHARATH BENZ','35.224','12.225','9746228423','bharathbenz@gmail.com'),
(24,'ASHOK LYLAND','52.141','65.224','9526848447','ashoklyland@gmail.com'),
(25,'VOLVO','21.445','32.114','9526848395','volvo@gmail.com'),
(26,'bharath benz','11.587','25.335','9856235855','shahidcv0@gmail.com'),
(29,'regional','11.587','25.225','9856235811','shahidv0@gmail.com');

/*Table structure for table `traffic` */

DROP TABLE IF EXISTS `traffic`;

CREATE TABLE `traffic` (
  `Traffic_id` int(11) NOT NULL AUTO_INCREMENT,
  `Fname` varchar(200) NOT NULL,
  `Lname` varchar(200) NOT NULL,
  `Phone` bigint(20) NOT NULL,
  `Control_room` varchar(200) NOT NULL,
  `Police_station` varchar(200) NOT NULL,
  `Login_id` int(11) NOT NULL,
  `Gender` varchar(30) NOT NULL,
  PRIMARY KEY (`Traffic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `traffic` */

insert  into `traffic`(`Traffic_id`,`Fname`,`Lname`,`Phone`,`Control_room`,`Police_station`,`Login_id`,`Gender`) values 
(1,'trf','q',8906789908,'clt','elthr',6,'Female'),
(2,'trf','q',8906789908,'clt','elthr',7,'Female'),
(3,'anu','k',9087456321,'fghj','fhjk',36,'Male');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `User_id` int(11) NOT NULL AUTO_INCREMENT,
  `Fname` varchar(300) NOT NULL,
  `Lname` varchar(300) NOT NULL,
  `Gender` varchar(30) NOT NULL,
  `Phone` bigint(20) NOT NULL,
  `Place` varchar(300) NOT NULL,
  `Post` varchar(300) NOT NULL,
  `PIN` bigint(20) NOT NULL,
  `Email` varchar(300) NOT NULL,
  `Login_id` int(11) NOT NULL,
  PRIMARY KEY (`User_id`),
  UNIQUE KEY `Phone` (`Phone`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`User_id`,`Fname`,`Lname`,`Gender`,`Phone`,`Place`,`Post`,`PIN`,`Email`,`Login_id`) values 
(1,'ramu','k','male',0,'','',0,'',2),
(2,'amal','kish','Male',8689789890,'clt','clt',678907,'amal@gmail.com',4),
(3,'fghxc','cgjj','Male',9085321447,'hhjkk','jkkf',685326,'a@gmail.com',37);

/*Table structure for table `work` */

DROP TABLE IF EXISTS `work`;

CREATE TABLE `work` (
  `Work_id` int(11) NOT NULL AUTO_INCREMENT,
  `Login_id` int(11) NOT NULL,
  `Mechanic_id` int(11) NOT NULL,
  `Help` varchar(600) NOT NULL,
  `Status` varchar(600) NOT NULL,
  PRIMARY KEY (`Work_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `work` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
