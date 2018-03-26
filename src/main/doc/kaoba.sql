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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `answer_db` */

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_estonian_ci;

/*Data for the table `course` */

insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (1,'数据结构',1,'admin','2018-03-22 09:53:42',NULL,NULL,0);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (2,'java',1,'admin','2018-03-22 09:53:50',NULL,NULL,0);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (3,'第一章',1,'admin','2018-03-22',NULL,NULL,1);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (4,'第二章',1,'admin',NULL,NULL,NULL,1);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (5,'第一章第一节',1,'admin',NULL,NULL,NULL,3);
insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (6,'第一章第一节第一节',1,NULL,NULL,NULL,NULL,5);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `single_db` */

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `stu_tea_cou` */

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `student` */

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,'20151308137','杨','21232f297a57a5a743894a0e4a801fc3',1,NULL,'2018:03:20:09:11','admin','2018-03-22 20:13');
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (2,'20151307138','发张','e10adc3949ba59abbe56e057f20f883e',1,NULL,'2018-03-20:09:36',NULL,NULL);
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (4,'123','杨','202cb962ac59075b964b07152d234b70',1,NULL,'2018-03-20 10:30',NULL,'2018-03-20 10:34');
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (5,'1','2','e10adc3949ba59abbe56e057f20f883e',1,NULL,'2018-03-20 10:35',NULL,NULL);
insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `teacher_course` */

DROP TABLE IF EXISTS `teacher_course`;

CREATE TABLE `teacher_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tea_id` int(11) DEFAULT NULL COMMENT '老师id',
  `course_id` varchar(500) DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `teacher_course` */

insert  into `teacher_course`(`id`,`tea_id`,`course_id`) values (22,1,'1,2');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
