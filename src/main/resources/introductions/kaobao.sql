/*
SQLyog Ultimate v11.24 (32 bit)
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

insert  into `answer_db`(`id`,`db_id`,`answerdb_key`,`answerdb_detail`,`answerdb_detail_pic_path`,`answerdb_dbtype`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,1,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(2,2,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(3,3,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(4,4,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(5,5,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(6,6,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(7,7,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(8,8,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(9,9,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(10,10,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(11,11,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(12,12,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(13,13,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(14,14,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(15,15,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(16,16,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(17,17,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(18,18,'1','1',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(19,19,'1','2',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(20,20,'1','3',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(21,21,'1','4',NULL,0,0,'杨晨曦','2018-04-09',NULL,NULL),(22,22,'1','1',NULL,0,1,'杨晨曦','2018-04-09',NULL,NULL),(23,23,'1','2',NULL,0,1,'杨晨曦','2018-04-09',NULL,NULL),(24,24,'1','3',NULL,0,1,'杨晨曦','2018-04-09',NULL,NULL),(25,25,'1','4',NULL,0,1,'杨晨曦','2018-04-09',NULL,NULL);

/*Table structure for table `bill` */

DROP TABLE IF EXISTS `bill`;

CREATE TABLE `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `code` varchar(50) DEFAULT NULL COMMENT '商品编号',
  `transaction` double DEFAULT NULL COMMENT '交易金额',
  `supplier_id` int(11) DEFAULT NULL COMMENT '供应商id',
  `status` int(11) DEFAULT NULL COMMENT '状态 0 删除 1 进货 2售货',
  `updater` varchar(50) DEFAULT NULL,
  `update_time` varchar(50) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `bill` */

insert  into `bill`(`id`,`code`,`transaction`,`supplier_id`,`status`,`updater`,`update_time`,`creater`,`create_time`) values (6,'121',31.5,1,0,'admin','2018-12-23','admin','2018-12-08'),(7,'12121',31.5,2,0,'admin','2018-12-23','admin','2018-12-08'),(8,'PU1545690153135075',1000,4,1,NULL,NULL,'admin','2018-12-22'),(9,'PU3340366067785042',20100,3,0,'admin','2018-12-23','admin','2018-12-23'),(10,'PU0991232874403209',200,2,1,NULL,NULL,'admin','2018-12-22'),(11,'PU5926430044303164',10100,3,1,NULL,NULL,'admin','2018-12-23');

/*Table structure for table `bill_detail` */

DROP TABLE IF EXISTS `bill_detail`;

CREATE TABLE `bill_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `bill_code` varchar(50) DEFAULT NULL COMMENT '账单编码',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品id',
  `title` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `purchase_price` double DEFAULT NULL COMMENT '商品进价（按每箱）',
  `sale_branch_price` double DEFAULT NULL COMMENT '商品支售价',
  `sale_box_price` double DEFAULT NULL COMMENT '商品箱售价',
  `branch_num` int(11) DEFAULT NULL COMMENT '支数量',
  `box_num` int(11) DEFAULT NULL COMMENT '箱数量',
  `transaction` double DEFAULT NULL COMMENT '总金额',
  `status` int(11) DEFAULT NULL COMMENT '状态 0 删除 1 正常',
  `updater` varchar(50) DEFAULT NULL,
  `update_time` varchar(50) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  `brand_title` varchar(50) DEFAULT NULL,
  `specification` int(11) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL COMMENT '品牌id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `bill_detail` */

insert  into `bill_detail`(`id`,`bill_code`,`goods_code`,`title`,`purchase_price`,`sale_branch_price`,`sale_box_price`,`branch_num`,`box_num`,`transaction`,`status`,`updater`,`update_time`,`creater`,`create_time`,`brand_title`,`specification`,`brand_id`) values (8,'PU1545690153135075','10101','000',100,100,100,NULL,10,1000,1,NULL,NULL,'admin','2018-12-22','测试1',NULL,NULL),(10,'PU0991232874403209','1000','10',10,10,10,NULL,10,100,1,NULL,NULL,'admin','2018-12-22','测试1',NULL,NULL),(11,'PU0991232874403209','101tyrre','10',10,10,10,NULL,10,100,1,NULL,NULL,'admin','2018-12-22','测试1',NULL,NULL),(12,'PU5926430044303164','12000','10',10,10,10,NULL,10,100,1,NULL,NULL,'admin','2018-12-23','测试1',NULL,NULL),(13,'PU5926430044303164','12q000','100',100,100,100,NULL,100,10000,1,NULL,NULL,'admin','2018-12-23','测试1',NULL,NULL);

/*Table structure for table `brand` */

DROP TABLE IF EXISTS `brand`;

CREATE TABLE `brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '品牌名称',
  `status` int(11) DEFAULT NULL COMMENT '状态 0 删除 1 正常',
  `updater` varchar(50) DEFAULT NULL,
  `update_time` varchar(50) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `brand` */

insert  into `brand`(`id`,`title`,`status`,`updater`,`update_time`,`creater`,`create_time`) values (1,'蒙牛2',1,'admin','2018-11-25','admin','2018-11-25'),(2,'蒙牛3',1,'admin','2018-11-25','21232f297a57a5a743894a0e4a801fc3','2018-11-25'),(3,'测试数据',1,'admin','2018-11-25','admin','2018-11-25'),(4,'测试1',1,NULL,NULL,'admin','2018-12-02');

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_estonian_ci;

/*Data for the table `course` */

insert  into `course`(`id`,`course_name`,`status`,`creater`,`create_time`,`updater`,`update_time`,`pid`) values (1,'数据结构',1,'admin','2018-03-22 09:53:42',NULL,NULL,0),(2,'java',1,'admin','2018-03-22 09:53:50',NULL,NULL,0),(3,'第一章',1,'admin','2018-03-22',NULL,NULL,1),(4,'第二章',0,'admin',NULL,'杨晨曦','2018-04-09',1),(5,'第一章第一节',1,'admin',NULL,'杨晨曦','2018-05-17',3),(6,'第一章第一节第一节',1,NULL,NULL,NULL,NULL,5),(7,'第二章',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09',1),(8,'第三章',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09',1),(9,'第四章',0,'杨晨曦','2018-04-09',NULL,NULL,1),(10,'第二章',1,'杨晨曦','2018-04-09',NULL,NULL,1),(11,'第三章',1,'杨晨曦','2018-04-09',NULL,NULL,1),(12,'第四章',1,'杨晨曦','2018-04-09',NULL,NULL,1),(13,'收到',1,'杨','2019-08-04',NULL,NULL,2);

/*Table structure for table `db_backup` */

DROP TABLE IF EXISTS `db_backup`;

CREATE TABLE `db_backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '备份名称',
  `comment` varchar(50) DEFAULT NULL COMMENT '备份说明',
  `backup_path` varchar(50) DEFAULT NULL COMMENT '备份路径',
  `creater` varchar(50) DEFAULT NULL COMMENT '备份者',
  `create_time` varchar(50) DEFAULT NULL COMMENT '备份时间',
  `restorer` varchar(50) DEFAULT NULL COMMENT '恢复者',
  `restore_time` varchar(50) DEFAULT NULL COMMENT '恢复时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `db_backup` */

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `brand_id` int(11) DEFAULT NULL COMMENT '品牌id',
  `brand_title` varchar(50) DEFAULT NULL COMMENT '品牌名称',
  `title` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `purchase_price` double DEFAULT NULL COMMENT '商品进价（按每箱）',
  `sale_branch_price` double DEFAULT NULL COMMENT '商品支售价',
  `sale_box_price` double DEFAULT NULL COMMENT '商品箱售价',
  `status` int(11) DEFAULT NULL COMMENT '状态 0 删除 1 正常',
  `updater` varchar(50) DEFAULT NULL,
  `update_time` varchar(50) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `specification` int(11) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `goods` */

insert  into `goods`(`id`,`goods_code`,`brand_id`,`brand_title`,`title`,`purchase_price`,`sale_branch_price`,`sale_box_price`,`status`,`updater`,`update_time`,`creater`,`specification`,`create_time`) values (1,'121',0,'12121','2121',1,1,1,1,'admin','2018-11-25','1',NULL,'1'),(2,'GO9843023325659118',0,NULL,'测试',1,1,1,1,'admin','2018-11-25','admin',NULL,'2018-11-25'),(3,'GO7271181336583385',0,NULL,'你好',10,10,10,1,NULL,NULL,'admin',NULL,'2018-12-01'),(7,'测试1',2,'测试2','测试1',31.5,2.5,46,NULL,'admin',NULL,NULL,NULL,NULL),(8,'测试1',2,'测试2','测试1',31.5,2.5,46,1,NULL,NULL,'admin',NULL,'2018-12-08'),(9,'1000000',4,'蒙牛2','10000',1000,1010,1000,1,'admin',NULL,'admin',NULL,'2018-12-22'),(10,'1000000',1,'蒙牛2','10000',1000,1010,1000,1,NULL,NULL,'admin',NULL,'2018-12-22'),(11,'1000',4,'测试1','10',10,10,10,1,NULL,NULL,'admin',NULL,'2018-12-22'),(12,'101tyrre',4,'测试1','10',10,10,10,1,NULL,NULL,'admin',NULL,'2018-12-22'),(13,'12000',4,'测试1','10',10,10,10,1,NULL,NULL,'admin',NULL,'2018-12-23'),(14,'12q000',4,'测试1','100',100,100,100,1,NULL,NULL,'admin',NULL,'2018-12-23'),(15,'10000121',4,'测试1','b编辑测试',10,10,10,1,NULL,NULL,'admin',NULL,'2018-12-23');

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
  `name` varchar(500) DEFAULT NULL COMMENT '作业名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

/*Data for the table `homework` */

insert  into `homework`(`id`,`teacher_id`,`course_id`,`photo_url`,`status`,`create_time`,`update_time`,`name`) values (47,1,1,'2019-08-04/72ee4b1d-d8a7-4e0d-a76d-1855de26f812.png',1,'2019-08-04','2019-08-04','期中作业'),(48,1,2,'2018-05-19/18e0244b-f77e-40f8-8d85-850d8f8ef112.png',1,'2018-05-19','2018-06-20','期中作业'),(49,1,2,NULL,1,'2018-06-20','2018-06-20','dd');

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

insert  into `single_db`(`id`,`course_id`,`singledb_title`,`singledb_optionA`,`singledb_optionB`,`singledb_optionC`,`singledb_optionD`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(2,3,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(3,3,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(4,3,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(5,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(6,3,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(7,3,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(8,3,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(9,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(10,3,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(11,3,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(12,3,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(13,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(14,3,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(15,3,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(16,3,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(17,3,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(18,1,'1','李四','1','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(19,1,'2','李四','2','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(20,1,'3','李四','3','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(21,1,'4','李四','4','1','1',0,'杨晨曦','2018-04-09','杨晨曦','2018-04-09'),(22,10,'1','李四','1','1','1',1,'杨晨曦','2018-04-09',NULL,NULL),(23,10,'2','李四','2','1','1',1,'杨晨曦','2018-04-09',NULL,NULL),(24,10,'3','李四','3','1','1',1,'杨晨曦','2018-04-09',NULL,NULL),(25,10,'4','李四','4','1','1',1,'杨晨曦','2018-04-09',NULL,NULL);

/*Table structure for table `store` */

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增 主键',
  `goods_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `title` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `remain_branch_num` int(11) DEFAULT NULL COMMENT '剩余商品支数量',
  `sell_branch_num` int(11) DEFAULT '0' COMMENT '销售商品支数量',
  `remain_box_num` int(11) DEFAULT NULL COMMENT '剩余商品箱数量',
  `sell_box_num` int(11) DEFAULT '0' COMMENT '销售商品箱数量',
  `purchase_transaction` double DEFAULT NULL COMMENT '进货总额',
  `sale_transaction` double DEFAULT NULL COMMENT '销售总额',
  `creater` varchar(50) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  `updater` varchar(50) DEFAULT NULL,
  `update_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `store` */

insert  into `store`(`id`,`goods_code`,`title`,`remain_branch_num`,`sell_branch_num`,`remain_box_num`,`sell_box_num`,`purchase_transaction`,`sale_transaction`,`creater`,`create_time`,`updater`,`update_time`) values (5,'测试1','测试1',0,0,0,0,0,0,'admin','2018-12-08','admin','2018-12-23'),(6,'10101','000',0,0,10,0,1000,0,'admin','2018-12-22',NULL,NULL),(7,'1000000','10000',0,0,0,0,0,0,'admin','2018-12-22','admin','2018-12-23'),(8,'1000','10',0,0,10,0,100,0,'admin','2018-12-22',NULL,NULL),(9,'101tyrre','10',0,0,10,0,100,0,'admin','2018-12-22',NULL,NULL),(10,'12000','10',0,0,10,0,100,0,'admin','2018-12-23',NULL,NULL),(11,'12q000','100',0,0,100,0,10000,0,'admin','2018-12-23',NULL,NULL),(12,'10000121','b编辑测试',0,0,0,0,0,0,'admin','2018-12-23','admin','2018-12-23');

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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `stu_tea_cou` */

insert  into `stu_tea_cou`(`id`,`stu_id`,`tea_id`,`cou_id`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (28,3,1,1,1,'ya','2018-05-11',NULL,NULL),(29,2,1,1,1,NULL,'2018-03-12',NULL,NULL),(30,1,1,1,1,NULL,'2018-03-17',NULL,NULL),(31,1,1,2,1,NULL,'2018-03-17',NULL,NULL);

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

insert  into `student`(`id`,`login_id`,`stu_name`,`stu_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,'2018','杨阳','14e1b600b1fd579f47433b88e8d85291',1,NULL,'2018-03-27 17:58',NULL,'2018-03-27 18:00'),(2,'admin','杨帆','21232f297a57a5a743894a0e4a801fc3',1,NULL,'2018-03-29 19:40',NULL,'2018-04-09 14:25'),(3,'20151308','张戴鹏','e10adc3949ba59abbe56e057f20f883e',1,NULL,'2018-03-29 19:55',NULL,NULL);

/*Table structure for table `submit_homework` */

DROP TABLE IF EXISTS `submit_homework`;

CREATE TABLE `submit_homework` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stu_id` int(11) DEFAULT NULL COMMENT '学生id',
  `photo_url` varchar(500) DEFAULT NULL COMMENT '提交作业照片路径',
  `grade` int(11) DEFAULT NULL COMMENT '成绩',
  `homework_id` int(11) DEFAULT NULL COMMENT '作业外键',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `create_time` varchar(50) DEFAULT NULL COMMENT '提交时间',
  `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `submit_homework` */

insert  into `submit_homework`(`id`,`stu_id`,`photo_url`,`grade`,`homework_id`,`status`,`create_time`,`update_time`) values (1,3,'2018-05-19/18e0244b-f77e-40f8-8d85-850d8f8ef112.png,2018-05-19/18e0244b-f77e-40f8-8d85-850d8f8ef112.png',30,47,1,'2018-05-17','2018-05-19 17:59:50'),(2,3,'2018-05-19/18e0244b-f77e-40f8-8d85-850d8f8ef112.png,2018-05-19/18e0244b-f77e-40f8-8d85-850d8f8ef112.png',80,48,1,NULL,'2018-05-19 18:05:04');

/*Table structure for table `supplier` */

DROP TABLE IF EXISTS `supplier`;

CREATE TABLE `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '客户姓名/企业名称',
  `address` varchar(50) DEFAULT NULL COMMENT '联系地址',
  `phone` varchar(50) NOT NULL COMMENT '电话',
  `status` int(11) DEFAULT NULL COMMENT '状态 0 删除 1 正常',
  `updater` varchar(50) DEFAULT NULL,
  `update_time` varchar(50) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `supplier` */

insert  into `supplier`(`id`,`name`,`address`,`phone`,`status`,`updater`,`update_time`,`creater`,`create_time`) values (1,'测试1','测试1','12345678',1,'admin','2018-11-25',NULL,NULL),(2,'测试2','测试3','12345',1,'admin','2018-11-25','21232f297a57a5a743894a0e4a801fc3','2018-11-25'),(3,'测试3','测试3','123456',1,'admin','2018-11-25','admin','2018-11-25'),(4,'测试5','测试5','12312',1,'admin','2018-11-25','admin','2018-11-25');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_id` varchar(50) NOT NULL COMMENT '登录账号',
  `user_password` varchar(50) NOT NULL COMMENT '登录密码',
  `user_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `status` int(50) DEFAULT NULL COMMENT '状态 0 删除 1 管理员 2 销售员  3 采购员',
  `updater` varchar(50) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(50) DEFAULT NULL COMMENT '更新时间',
  `creater` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`login_id`,`user_password`,`user_name`,`status`,`updater`,`update_time`,`creater`,`create_time`) values (1,'admin','21232f297a57a5a743894a0e4a801fc3','张戴鹏',1,'admin','2018-11-24','admin','2019-01-01'),(2,'shy','e10adc3949ba59abbe56e057f20f883e','张三',0,'admin','2018-11-24','admin','2018-11-24'),(3,'admin2','e10adc3949ba59abbe56e057f20f883e','123456',0,'admin','2018-11-24','admin','2018-11-24');

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

insert  into `teacher`(`id`,`login_id`,`tea_name`,`tea_password`,`status`,`creater`,`create_time`,`updater`,`update_time`) values (1,'admin','杨','21232f297a57a5a743894a0e4a801fc3',1,NULL,'2018:03:20:09:11','admin','2018-04-12 16:13'),(2,'20151307138','发张','e10adc3949ba59abbe56e057f20f883e',1,NULL,'2018-03-20:09:36',NULL,NULL),(4,'123','杨','202cb962ac59075b964b07152d234b70',1,NULL,'2018-03-20 10:30',NULL,'2018-03-20 10:34'),(5,'1','2','e10adc3949ba59abbe56e057f20f883e',1,NULL,'2018-03-20 10:35',NULL,NULL),(6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,'120','杨','e10adc3949ba59abbe56e057f20f883e',1,'admin','2018-03-27 21:00',NULL,NULL),(8,'admin4','杨晨曦','21232f297a57a5a743894a0e4a801fc3',1,'admin','2018-04-09 14:29','admin','2018-04-09 14:36');

/*Table structure for table `teacher_course` */

DROP TABLE IF EXISTS `teacher_course`;

CREATE TABLE `teacher_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tea_id` int(11) DEFAULT NULL COMMENT '老师id',
  `course_id` varchar(500) DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `teacher_course` */

insert  into `teacher_course`(`id`,`tea_id`,`course_id`) values (22,1,'1,2'),(23,8,'1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
