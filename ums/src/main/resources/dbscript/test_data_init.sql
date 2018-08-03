# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.21)
# Database: shirojwt
# Generation Time: 2018-04-24 11:23:47 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table application
# ------------------------------------------------------------

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;

INSERT INTO `application` (`id`, `application_name`, `application_key`, `create_time`, `create_user_id`, `update_time`, `update_user_id`)
VALUES
	(1,'urule','urule','2017-01-01 12:12:12',1,'2017-01-01 12:12:12',1),
	(2,'test','test','2018-04-20 17:07:19',1,'2018-04-20 17:07:19',1),
	(3,'test1','test1','2018-04-20 17:08:20',2,'2018-04-20 17:08:20',3),
	(4,'yf系统','yf','2018-04-20 17:10:47',1,'2018-04-20 17:10:47',2);

/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table resource
# ------------------------------------------------------------

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;

INSERT INTO `resource` (`id`, `parent_id`, `application_id`, `name`, `code`, `url`, `description`, `type`, `icon`, `order`, `status`, `create_time`, `create_user_id`, `update_time`, `update_user_id`)
VALUES
	(1,0,1,'urule','DSQ','/urule','urule',0,'tbd',2,1,'2018-04-20 17:14:34',1,'2018-04-20 17:14:34',1),
	(2,0,3,'test1系统','DSQ','/test1','test1系统',0,'tbd',2,1,'2018-04-20 17:14:34',1,'2018-04-20 17:14:34',1),
	(3,0,4,'yf系统','QYWX','/urule/webqq','元方系统',0,'tbd',3,1,'2018-04-20 17:14:34',1,'2018-04-20 17:14:34',1),
	(4,1,1,'用户管理','userManager','/urule/user','urule用户管理',1,'tbd',0,1,'2018-04-24 18:39:51',1,'2018-04-24 18:39:51',1),
	(5,4,1,'用户新增','userAdd','/urule/user/add','urule用户新增',2,'tbd',0,1,'2018-04-24 18:46:15',1,'2018-04-24 18:46:15',1),
	(6,4,1,'用户修改','userUpdate','/urule/user/update','urule用户修改',2,'tbd',0,1,'2018-04-24 18:48:12',1,'2018-04-24 18:48:12',1),
	(7,2,3,'用户管理','userManage','/test1/user','test1系统用户管理',1,'tbd',0,1,'2018-04-24 18:48:56',1,'2018-04-24 18:48:56',1),
	(8,7,3,'用户新增','userAdd','/test1/user/add','test1系统用户新增',2,'tbd',0,1,'2018-04-24 18:50:31',1,'2018-04-24 18:50:31',1),
	(9,7,3,'用户修改','userUpdate','/test1/user/update','test1系统用户修改',2,'tbd',0,1,'2018-04-24 19:17:48',1,'2018-04-24 19:17:48',1);

/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_desc`, `status`, `application_key`, `create_time`, `create_user_id`, `update_time`, `update_user_id`)
VALUES
	(1,'urule超级管理员','admin','urule超级管理员',1,'urule','2018-04-20 17:16:18',1,'2018-04-20 17:16:18',2),
	(2,'urule测试角色','test','urule测试角色',1,'urule','2018-04-20 17:16:18',1,'2018-04-20 17:16:18',3),
	(3,'test1超级管理员','admin','test1超级管理员',1,'test1','2018-04-20 17:16:18',1,'2018-04-20 17:16:18',3),
	(4,'test1测试角色','test','test1测试角色',1,'test1','2018-04-24 18:55:45',0,'2018-04-24 18:55:45',0);

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_resource
# ------------------------------------------------------------

LOCK TABLES `role_resource` WRITE;
/*!40000 ALTER TABLE `role_resource` DISABLE KEYS */;

INSERT INTO `role_resource` (`id`, `role_id`, `resource_id`, `create_time`, `create_user_id`, `update_time`, `update_user_id`)
VALUES
	(1,2,1,'2018-04-20 17:16:47',1,'2018-04-20 17:16:47',1),
	(2,2,4,'2018-04-20 17:16:47',1,'2018-04-20 17:16:47',1),
	(3,2,5,'2018-04-20 17:16:47',1,'2018-04-20 17:16:47',1),
	(4,4,2,'2018-04-24 19:00:18',1,'2018-04-24 19:00:18',1),
	(5,4,7,'2018-04-24 19:00:33',1,'2018-04-24 19:00:33',1),
	(6,4,8,'2018-04-24 19:01:02',1,'2018-04-24 19:01:02',1),
	(7,4,9,'2018-04-24 19:19:33',1,'2018-04-24 19:19:33',1);

/*!40000 ALTER TABLE `role_resource` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `username`, `password`, `company`, `email`, `secret`, `status`, `create_time`, `create_user_id`, `update_time`, `update_user_id`)
VALUES
	(1,'askerlve','123456','test','yy8309417@sina.com','yy8309417',1,'2018-04-19 11:33:48','1','2018-04-19 11:33:48','1'),
	(2,'admin1','admin','test','kuainiu@email.com','abc',1,'2018-04-20 17:14:09','3','2018-04-20 17:14:09','1'),
	(3,'admin2','admin','test','kuainiu@email.com','bcd',1,'2018-04-20 17:14:09','3','2018-04-20 17:14:09','1'),
	(4,'admin3','admin','test','kuainiu@email.com','bcd',1,'2018-04-20 17:14:09','3','2018-04-20 17:14:09','1'),
	(5,'tester','admin','test','kuainiu@email.com','bcd',1,'2018-04-20 17:14:09','3','2018-04-20 17:14:09','1');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_resource
# ------------------------------------------------------------

LOCK TABLES `user_resource` WRITE;
/*!40000 ALTER TABLE `user_resource` DISABLE KEYS */;

INSERT INTO `user_resource` (`id`, `user_id`, `resource_id`, `create_time`, `create_user_id`, `update_time`, `update_user_id`)
VALUES
	(1,3,1,'2018-04-20 17:15:59',1,'2018-04-20 17:15:59',1),
	(2,3,2,'2018-04-20 17:15:59',1,'2018-04-20 17:15:59',1),
	(3,3,3,'2018-04-20 17:15:59',1,'2018-04-20 17:15:59',1),
	(4,3,4,'2018-04-20 17:15:59',1,'2018-04-20 17:15:59',1);

/*!40000 ALTER TABLE `user_resource` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_role
# ------------------------------------------------------------

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;

INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `create_time`, `create_user_id`, `update_time`, `update_user_id`)
VALUES
	(1,1,1,'2018-04-20 17:16:36',1,'2018-04-20 17:16:36',1),
	(2,1,2,'2018-04-20 17:16:36',1,'2018-04-20 17:16:36',1),
	(3,1,4,'2018-04-20 17:16:36',1,'2018-04-20 17:16:36',1),
	(4,2,2,'2018-04-24 19:04:02',0,'2018-04-24 19:04:02',0),
	(5,2,3,'2018-04-24 19:04:45',0,'2018-04-24 19:04:45',0);

/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
