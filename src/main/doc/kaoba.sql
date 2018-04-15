/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.16-log : Database - kaoba
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kaoba` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `kaoba`;

/*Table structure for table `answer_db` */

DROP TABLE IF EXISTS `answer_db`;

CREATE TABLE `answer_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `db_id` int(11) DEFAULT NULL COMMENT '题库id',
  `answerdb_key` varchar(500) DEFAULT NULL COMMENT '答案',
  `answerdb_detail` varchar(700) DEFAULT NULL COMMENT '详解',
  `answerdb_detail_pic_path` varchar(500) DEFAULT NULL COMMENT '详解图片',
  `answerdb_dbtype` int(11) DEFAULT NULL COMMENT '0是single_db,1是jfs_db',
  `status` int(11) DEFAULT NULL COMMENT '0为删除，1为正常',
  `creater` varchar(15) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(15) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `answer_db` */

insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,1,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (2,2,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (3,3,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (4,4,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (5,5,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (6,6,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (7,7,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (8,8,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (9,9,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (10,10,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (11,11,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (12,12,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (13,13,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (14,14,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (15,15,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (16,16,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (17,17,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (18,18,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (19,19,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (20,20,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (21,21,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (22,22,'1','1',NULL,0,1,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (23,23,'1','2',NULL,0,1,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (24,24,'1','3',NULL,0,1,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (25,25,'1','4',NULL,0,1,'杨晨曦','2018-04-09',NULL,NULL);

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_name` varchar(50) COLLATE utf8_estonian_ci DEFAULT NULL COMMENT '课程名',
  `status` int(11) DEFAULT NULL COMMENT '0删除,1正常',
  `creater` varchar(15) COLLATE utf8_estonian_ci DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(50) COLLATE utf8_estonian_ci DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(15) COLLATE utf8_estonian_ci DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(50) COLLATE utf8_estonian_ci DEFAULT NULL COMMENT '更新时间',
  `pid` int(11) DEFAULT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_estonian_ci;

/*Data for the table `course` */

insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (1,'数据结构',1,'admin','2018-03-22 09:53:42',NULL,NULL,0);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (2,'java',1,'admin','2018-03-22 09:53:50',NULL,NULL,0);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (3,'第一章',1,'admin','2018-03-22',NULL,NULL,1);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (4,'第二章',0,'admin',NULL,'杨晨曦','2018-04-09',1);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (5,'第一章第一节',1,'admin',NULL,NULL,NULL,3);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (6,'第一章第一节第一节',1,NULL,NULL,NULL,NULL,5);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (7,'第二章',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09',1);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (8,'第三章',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09',1);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (9,'第四章',0,'杨晨曦','2018-04-09',NULL,NULL,1);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (10,'第二章',1,'杨晨曦','2018-04-09',NULL,NULL,1);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (11,'第三章',1,'杨晨曦','2018-04-09',NULL,NULL,1);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (12,'第四章',1,'杨晨曦','2018-04-09',NULL,NULL,1);

/*Table structure for table `homework` */

DROP TABLE IF EXISTS `homework`;

CREATE TABLE `homework` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `teacher_id` int(11) DEFAULT NULL COMMENT '老师id',
  `course_id` int(11) DEFAULT NULL COMMENT '课程id',
  `photo_url` varchar(500) DEFAULT NULL COMMENT '作业照片路径',
  `status` int(11) DEFAULT NULL COMMENT '0删除,1正常',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `homework` */

insert  into `homework`(`id`,`teacher_id`,`course_id`,`photo_url`,`status`,`create_time`,`update_time`) values (1,8,10,'2018-33-15/280c938a-b379-419f-8d21-c8b3da179a3f.jpg',1,'2018-33-15',NULL);
insert  into `homework`(`id`,`teacher_id`,`course_id`,`photo_url`,`status`,`create_time`,`update_time`) values (2,8,10,'2018-34-15/4b34cb01-09a1-4172-9a96-d8b593021698.jpg',1,'2018-34-15',NULL);
insert  into `homework`(`id`,`teacher_id`,`course_id`,`photo_url`,`status`,`create_time`,`update_time`) values (3,8,10,'2018-35-15/d07deb17-f361-4af1-a275-0bf0ab90a920.jpg',1,'2018-35-15',NULL);
insert  into `homework`(`id`,`teacher_id`,`course_id`,`photo_url`,`status`,`create_time`,`update_time`) values (4,8,10,'2018-28-15/ba71f908-aed4-4e2c-b177-454805051244.jpg',1,'2018-28-15',NULL);

/*Table structure for table `single_db` */

DROP TABLE IF EXISTS `single_db`;

CREATE TABLE `single_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` int(11) DEFAULT NULL COMMENT '所属章节id',
  `singledb_title` varchar(800) DEFAULT NULL COMMENT '选择题题目',
  `singledb_optionA` varchar(400) DEFAULT NULL COMMENT '选项A',
  `singledb_optionB` varchar(400) DEFAULT NULL COMMENT '选项B',
  `singledb_optionC` varchar(400) DEFAULT NULL COMMENT '选项C',
  `singledb_optionD` varchar(400) DEFAULT NULL COMMENT '选项D',
  `status` int(11) DEFAULT NULL COMMENT '0为删除，1为正常',
  `creater` varchar(15) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(15) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `single_db` */

insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (2,3,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (3,3,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (4,3,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (5,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (6,3,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (7,3,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (8,3,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (9,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (10,3,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (11,3,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (12,3,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (13,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (14,3,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (15,3,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (16,3,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (17,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (18,1,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (19,1,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (20,1,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (21,1,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09');
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (22,10,'1','李四','1','1','1',1,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (23,10,'2','李四','2','1','1',1,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (24,10,'3','李四','3','1','1',1,'杨晨曦','2018-04-09',NULL,NULL);
insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (25,10,'4','李四','4','1','1',1,'杨晨曦','2018-04-09',NULL,NULL);

/*Table structure for table `stu_error` */

DROP TABLE IF EXISTS `stu_error`;

CREATE TABLE `stu_error` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stu_id` int(11) DEFAULT NULL COMMENT '学生主键',
  `course_id` int(11) DEFAULT NULL COMMENT '课程主键',
  `db_id` int(11) DEFAULT NULL COMMENT '题库主键',
  `stu_error_dbtype` int(11) DEFAULT NULL COMMENT '题库类型,0是single_db,1是jfs_db',
  `status` int(11) DEFAULT NULL COMMENT '0是删除,1是正常',
  `creater` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(50) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `stu_error` */

/*Table structure for table `stu_pro` */

DROP TABLE IF EXISTS `stu_pro`;

CREATE TABLE `stu_pro` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stu_id` int(11) DEFAULT NULL COMMENT '学生主键',
  `course_id` int(11) DEFAULT NULL COMMENT '课程主键',
  `db_id` int(11) NOT NULL COMMENT '题库主键',
  `stu_pro_dbtype` int(11) DEFAULT NULL COMMENT '题库类型,0是single_db,1是jfs_db',
  `stu_pro_type` int(11) DEFAULT NULL COMMENT '0是练习,1是背题',
  `status` int(11) DEFAULT NULL COMMENT '0是删除,1是正常',
  `creater` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(50) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`db_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `stu_pro` */

/*Table structure for table `stu_tea_cou` */

DROP TABLE IF EXISTS `stu_tea_cou`;

CREATE TABLE `stu_tea_cou` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stu_id` int(11) DEFAULT NULL COMMENT '学生主键',
  `tea_id` int(11) DEFAULT NULL COMMENT '老师主键',
  `cou_id` int(11) DEFAULT NULL COMMENT '课程主键',
  `status` int(11) DEFAULT NULL COMMENT '状态,0是删除,1是正常',
  `creater` varchar(50) CHARACTER SET utf32 DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(20) CHARACTER SET utf32 DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(50) CHARACTER SET utf32 DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(20) CHARACTER SET utf32 DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

/*Data for the table `stu_tea_cou` */

insert  into `stu_tea_cou`(`id`,`stu_id`,`tea_id`,`cou_id`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (28,3,NULL,1,1,'ya','2018-45-03',NULL,NULL);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_id` varchar(15) DEFAULT NULL COMMENT '账号',
  `stu_name` varchar(15) DEFAULT NULL COMMENT '姓名',
  `stu_password` varchar(50) DEFAULT NULL COMMENT '密码',
  `status` int(11) DEFAULT NULL COMMENT '0删除,1正常',
  `creater` varchar(15) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(15) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`id`,`login_id`,`stu_name`,`stu_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,'2018','杨阳','14e1b600b1fd579f47433b88e8d85291',1,NULL,'2018-03-27 17:58',NULL,'2018-03-27 18:00');
insert  into `student`(`id`,`login_id`,`stu_name`,`stu_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (2,'admin','yang','21232f297a57a5a743894a0e4a801fc3',1,NULL,'2018-03-29 19:40',NULL,'2018-04-09 14:25');
insert  into `student`(`id`,`login_id`,`stu_name`,`stu_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (3,'ad','ya','e10adc3949ba59abbe56e057f20f883e',1,NULL,'2018-03-29 19:55',NULL,NULL);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_id` varchar(15) DEFAULT NULL COMMENT '账号',
  `user_name` varchar(15) DEFAULT NULL COMMENT '姓名',
  `user_password` varchar(50) DEFAULT NULL COMMENT '密码',
  `status` int(11) DEFAULT NULL COMMENT '0删除,1超级管理员,2普通管理员',
  `creater` varchar(15) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(15) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`login_id`,`user_name`,`user_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,'admin','admin','21232f297a57a5a743894a0e4a801fc3',1,NULL,NULL,NULL,NULL);

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_id` varchar(15) DEFAULT NULL COMMENT '账号',
  `tea_name` varchar(15) DEFAULT NULL COMMENT '姓名',
  `tea_password` varchar(50) DEFAULT NULL COMMENT '密码',
  `status` int(11) DEFAULT NULL COMMENT '0删除,1正常',
  `creater` varchar(15) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(15) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,'20151308137','杨','21232f297a57a5a743894a0e4a801fc3',1,NULL,'2018:03:20:09:11','admin','2018-04-12 16:13');
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (2,'20151307138','发张','e10adc3949ba59abbe56e057f20f883e',1,NULL,'2018-03-20:09:36',NULL,NULL);
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (4,'123','杨','202cb962ac59075b964b07152d234b70',1,NULL,'2018-03-20 10:30',NULL,'2018-03-20 10:34');
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (5,'1','2','e10adc3949ba59abbe56e057f20f883e',1,NULL,'2018-03-20 10:35',NULL,NULL);
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (7,'120','杨','e10adc3949ba59abbe56e057f20f883e',1,'admin','2018-03-27 21:00',NULL,NULL);
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (8,'admin','杨晨曦','21232f297a57a5a743894a0e4a801fc3',1,'admin','2018-04-09 14:29','admin','2018-04-09 14:36');

/*Table structure for table `teacher_course` */

DROP TABLE IF EXISTS `teacher_course`;

CREATE TABLE `teacher_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tea_id` int(11) DEFAULT NULL COMMENT '老师id',
  `course_id` varchar(500) DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `teacher_course` */

insert  into `teacher_course`(`id`,`tea_id`,`course_id`) values (22,1,'1,2');
insert  into `teacher_course`(`id`,`tea_id`,`course_id`) values (23,8,'1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
