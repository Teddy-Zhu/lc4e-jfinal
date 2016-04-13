/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50709
 Source Host           : localhost
 Source Database       : lc4e

 Target Server Type    : MySQL
 Target Server Version : 50709
 File Encoding         : utf-8

 Date: 04/13/2016 17:25:01 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin_log`
-- ----------------------------
DROP TABLE IF EXISTS `admin_log`;
CREATE TABLE `admin_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `logDetail` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_address`
-- ----------------------------
DROP TABLE IF EXISTS `sys_address`;
CREATE TABLE `sys_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(11) COLLATE utf8_bin NOT NULL,
  `description` varchar(20) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_area`
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL,
  `css` varchar(30) COLLATE utf8_bin NOT NULL,
  `icon` varchar(30) COLLATE utf8_bin NOT NULL,
  `areaStatusId` int(11) NOT NULL DEFAULT '1',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_area`
-- ----------------------------
BEGIN;
INSERT INTO `sys_area` VALUES ('1', '0', 'LC4E', 'root', '根域', '', '', '1', '2015-08-10 11:05:23', '2015-08-10 11:05:25'), ('2', '1', 'program', 'Program', '编程语言相关', '', '', '1', '2015-09-21 16:46:49', '2015-09-21 16:46:52'), ('3', '2', 'java', 'JAVA', 'java', '', '', '1', '2015-09-21 16:45:19', '2015-09-21 16:45:21'), ('4', '2', 'c', 'C/C++', 'C', '', '', '1', '2015-09-21 16:47:48', '2015-09-21 16:47:50');
COMMIT;

-- ----------------------------
--  Table structure for `sys_area_status`
-- ----------------------------
DROP TABLE IF EXISTS `sys_area_status`;
CREATE TABLE `sys_area_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL,
  `visible` tinyint(4) NOT NULL DEFAULT '1',
  `close` tinyint(4) NOT NULL DEFAULT '1',
  `move` tinyint(4) NOT NULL DEFAULT '1',
  `pubTopic` tinyint(4) NOT NULL DEFAULT '1',
  `pubComment` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` datetime NOT NULL,
  `updaetTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_area_status`
-- ----------------------------
BEGIN;
INSERT INTO `sys_area_status` VALUES ('1', 'online', '正常', '此分区目前正常', '1', '0', '0', '1', '1', '2015-08-10 11:03:51', '2015-08-10 11:03:52');
COMMIT;

-- ----------------------------
--  Table structure for `sys_comment`
-- ----------------------------
DROP TABLE IF EXISTS `sys_comment`;
CREATE TABLE `sys_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `order` int(11) NOT NULL,
  `title` varchar(50) COLLATE utf8_bin NOT NULL,
  `body` varchar(1000) COLLATE utf8_bin NOT NULL,
  `tcStatusId` int(11) NOT NULL DEFAULT '2',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_comment`
-- ----------------------------
BEGIN;
INSERT INTO `sys_comment` VALUES ('1', '1', '1', '1', 'ttmax', 'test', '2', '2015-09-22 09:37:02', '2015-09-22 09:37:04'), ('2', '1', '1', '2', 'test', '123', '2', '2015-09-22 09:51:47', '2015-09-22 09:51:50'), ('3', '2', '1', '1', 'asda', 'sads', '2', '2015-09-23 09:50:05', '2015-09-23 09:50:07');
COMMIT;

-- ----------------------------
--  Table structure for `sys_comment_attach`
-- ----------------------------
DROP TABLE IF EXISTS `sys_comment_attach`;
CREATE TABLE `sys_comment_attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commentId` int(11) NOT NULL,
  `attachPath` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_common_variable`
-- ----------------------------
DROP TABLE IF EXISTS `sys_common_variable`;
CREATE TABLE `sys_common_variable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_bin NOT NULL,
  `value` varchar(50) COLLATE utf8_bin NOT NULL,
  `error` varchar(100) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_common_variable`
-- ----------------------------
BEGIN;
INSERT INTO `sys_common_variable` VALUES ('1', 'SiteName', 'Light Community', '', '站点名称', '2015-08-02 22:44:43', '2015-08-02 22:44:45'), ('2', 'IndexPageSize', '20', '', '首页每页帖子数', '2015-08-02 22:45:29', '2015-08-02 22:45:32'), ('3', 'Register', '1', 'Register is forbid', '是否开放注册', '2015-08-02 22:45:55', '2015-08-02 22:45:58'), ('4', 'SimpleRegister', '0', '', '是否开启简单注册', '2015-08-02 22:46:14', '2015-08-02 22:46:16'), ('5', 'CaptchaCaseSensitive', '1', '', '验证码大小写敏感', '2015-08-11 10:31:49', '2015-08-11 10:31:51'), ('6', 'Captcha', '0', '验证码错误', '是否开启注册登录验证码', '2015-08-11 10:33:20', '2015-08-11 10:33:23'), ('7', 'UserInitBalances', '10', '', '用户初始化货币数量', '2015-08-22 11:20:32', '2015-08-22 11:20:34'), ('8', 'AreaPageSize', '20', '', '分区每页帖子数目', '2015-09-21 15:57:00', '2015-09-21 15:57:02'), ('9', 'UserTagPercent', '2', '', '用户标签权重', '2015-09-23 19:59:48', '2015-09-23 19:59:50'), ('10', 'TopicStatusPercent', '10', '', '主题状态权重', '2015-09-24 15:51:45', '2015-09-24 15:51:50'), ('11', 'CommentCountPCT', '0.5', '', '', '2015-09-24 15:52:22', '2015-09-24 15:52:24'), ('12', 'DefaultTheme', 'default', '', '主题目录', '2015-10-10 14:23:41', '2015-10-10 14:23:43');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dynamic_info`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dynamic_info`;
CREATE TABLE `sys_dynamic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `info` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_history_top`
-- ----------------------------
DROP TABLE IF EXISTS `sys_history_top`;
CREATE TABLE `sys_history_top` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicIds` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_ip_forbit`
-- ----------------------------
DROP TABLE IF EXISTS `sys_ip_forbit`;
CREATE TABLE `sys_ip_forbit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(30) COLLATE utf8_bin NOT NULL,
  `description` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_job`
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `order` int(11) NOT NULL,
  `cron` varchar(255) COLLATE utf8_bin NOT NULL,
  `className` varchar(255) COLLATE utf8_bin NOT NULL,
  `group` varchar(50) COLLATE utf8_bin NOT NULL,
  `enable` tinyint(4) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operateTypeId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `description` varchar(255) COLLATE utf8_bin NOT NULL,
  `ip` varchar(30) COLLATE utf8_bin NOT NULL,
  `agant` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL,
  `order` int(11) NOT NULL,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `css` varchar(30) COLLATE utf8_bin NOT NULL,
  `icon` varchar(30) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '0', '1', 'Menu', 'Menu', 'basic', 'browser', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('2', '1', '1', '/', 'Home', 'basic', 'home', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('3', '1', '2', '/a/think', 'Think', 'basic', 'mail', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('4', '1', '3', '/a/random', 'Random', 'basic', 'user', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('5', '1', '4', '/a/lan', 'Language', 'basic', 'font', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('6', '5', '1', '/a/cc', 'C/C++', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('7', '5', '3', '/a/jvm', 'Java', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('8', '5', '2', '/a/js', 'Javascript', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('9', '5', '6', '/a/sh', 'Script', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('10', '9', '1', '/a/py', 'Python', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('11', '9', '2', '/a/rb', 'Ruby', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('12', '1', '5', '/a/py1', 'Python1', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('13', '9', '2', '/a/py2', 'Python2', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27'), ('14', '13', '3', '/a/test', 'testtitle', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
COMMIT;

-- ----------------------------
--  Table structure for `sys_operate_type`
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_type`;
CREATE TABLE `sys_operate_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL,
  `available` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_permission`
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES ('1', 'view', 'view', 'asd', '1', '2015-08-07 13:06:37', '2015-08-07 13:06:40'), ('2', 'add', 'add', 'asxxawed', '1', '2015-08-07 13:06:50', '2015-08-07 13:06:53'), ('3', 'delete', 'delete', 'xckkxz', '1', '2015-08-07 13:07:06', '2015-08-07 13:07:08'), ('4', 'query', 'query', 'dsdd', '1', '2015-08-07 13:07:17', '2015-08-07 13:07:20'), ('5', 'clearcache', 'clearcache', '清缓存', '1', '2015-08-12 11:34:30', '2015-08-12 11:34:32');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL,
  `available` tinyint(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', 'admin', 'admin', 'assssdas', '1'), ('2', 'member', 'meber', 'assxx', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1', '2015-08-07 13:07:36', '2015-08-07 13:07:39'), ('2', '1', '3', '2015-08-07 13:07:45', '2015-08-07 13:07:48'), ('3', '1', '4', '2015-08-07 13:07:56', '2015-08-07 13:07:58'), ('4', '2', '3', '2015-08-07 13:08:05', '2015-08-07 13:08:07'), ('5', '2', '2', '2015-08-07 13:08:15', '2015-08-07 13:08:19');
COMMIT;

-- ----------------------------
--  Table structure for `sys_tag`
-- ----------------------------
DROP TABLE IF EXISTS `sys_tag`;
CREATE TABLE `sys_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(10) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_tag`
-- ----------------------------
BEGIN;
INSERT INTO `sys_tag` VALUES ('1', 'java', '', '2015-09-22 09:52:44', '2015-09-22 09:52:46'), ('2', 'c', '', '2015-09-22 09:52:55', '2015-09-22 09:52:57'), ('3', 'python', '', '2015-09-22 09:53:05', '2015-09-22 09:53:08'), ('4', 'like', '', '2015-09-23 11:17:47', '2015-09-23 11:17:50');
COMMIT;

-- ----------------------------
--  Table structure for `sys_topic`
-- ----------------------------
DROP TABLE IF EXISTS `sys_topic`;
CREATE TABLE `sys_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `areaId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `url` varchar(40) COLLATE utf8_bin NOT NULL,
  `title` varchar(50) COLLATE utf8_bin NOT NULL,
  `body` varchar(5000) COLLATE utf8_bin NOT NULL,
  `tcStatusId` int(11) NOT NULL DEFAULT '1',
  `count` int(11) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_topic`
-- ----------------------------
BEGIN;
INSERT INTO `sys_topic` VALUES ('1', '3', '1', 'hello-lc4e-java', 'hello,lc4e,java', 'it\'s demo topic for test java', '1', '0', '2015-09-21 16:51:30', '2015-09-21 16:51:33'), ('2', '4', '1', '', 'hello,lc4e,c', 'it\'s demo topic in c', '1', '0', '2015-09-22 10:53:28', '2015-09-22 10:53:33'), ('3', '3', '1', '', 'test', 'adsad', '1', '0', '2015-09-23 11:17:31', '2015-09-23 11:17:33');
COMMIT;

-- ----------------------------
--  Table structure for `sys_topic_attach`
-- ----------------------------
DROP TABLE IF EXISTS `sys_topic_attach`;
CREATE TABLE `sys_topic_attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) NOT NULL,
  `attachPath` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_topic_comment_status`
-- ----------------------------
DROP TABLE IF EXISTS `sys_topic_comment_status`;
CREATE TABLE `sys_topic_comment_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL,
  `visible` tinyint(4) NOT NULL DEFAULT '1',
  `editable` tinyint(4) NOT NULL DEFAULT '1',
  `lock` tinyint(4) NOT NULL DEFAULT '0',
  `release` tinyint(4) NOT NULL DEFAULT '1',
  `delete` tinyint(4) NOT NULL DEFAULT '0',
  `move` tinyint(4) NOT NULL DEFAULT '1',
  `comment` tinyint(4) NOT NULL DEFAULT '1',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_topic_comment_status`
-- ----------------------------
BEGIN;
INSERT INTO `sys_topic_comment_status` VALUES ('1', 'normal', 'Normal', '正常状态/默认状态', '1', '1', '0', '1', '0', '1', '1', '2015-09-21 16:59:56', '2015-09-21 16:59:58'), ('2', 'normal', 'Normal', '正常状态/评论默认状态', '1', '1', '0', '1', '0', '0', '0', '2015-09-22 09:38:25', '2015-09-22 09:38:27');
COMMIT;

-- ----------------------------
--  Table structure for `sys_topic_status`
-- ----------------------------
DROP TABLE IF EXISTS `sys_topic_status`;
CREATE TABLE `sys_topic_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(20) COLLATE utf8_bin NOT NULL,
  `name` varchar(20) COLLATE utf8_bin NOT NULL,
  `pw` decimal(10,4) NOT NULL DEFAULT '1.0000',
  `icon` varchar(20) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `sys_topic_status`
-- ----------------------------
BEGIN;
INSERT INTO `sys_topic_status` VALUES ('1', 'lock', 'Lock', '1.0000', 'lock', '2015-09-21 16:23:37', '2015-09-21 16:23:40'), ('2', 'top', 'Top', '2.0000', 'top', '2015-09-21 16:42:35', '2015-09-21 16:42:38'), ('3', 'ano', 'annoument', '3.0000', 'a', '2015-09-21 16:44:11', '2015-09-21 16:44:13');
COMMIT;

-- ----------------------------
--  Table structure for `sys_url_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_url_permission`;
CREATE TABLE `sys_url_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actionKey` varchar(255) COLLATE utf8_bin NOT NULL,
  `permission` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `sys_url_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_url_role`;
CREATE TABLE `sys_url_role` (
  `id` int(11) NOT NULL,
  `actionKey` varchar(255) COLLATE utf8_bin NOT NULL,
  `role` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `topic_status`
-- ----------------------------
DROP TABLE IF EXISTS `topic_status`;
CREATE TABLE `topic_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) NOT NULL,
  `statusId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `topic_status`
-- ----------------------------
BEGIN;
INSERT INTO `topic_status` VALUES ('1', '2', '1', '2015-09-22 11:00:48', '2015-09-22 11:00:50'), ('2', '2', '2', '2015-09-22 11:28:02', '2015-09-22 11:28:04'), ('3', '1', '1', '2015-09-22 12:36:48', '2015-09-22 12:36:50');
COMMIT;

-- ----------------------------
--  Table structure for `topic_tag`
-- ----------------------------
DROP TABLE IF EXISTS `topic_tag`;
CREATE TABLE `topic_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagId` int(11) NOT NULL,
  `topicId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `topic_tag`
-- ----------------------------
BEGIN;
INSERT INTO `topic_tag` VALUES ('1', '3', '1', '2015-09-22 10:03:39', '2015-09-22 10:03:41'), ('2', '1', '1', '2015-09-22 10:28:36', '2015-09-22 10:28:40'), ('3', '2', '2', '2015-09-22 10:54:02', '2015-09-22 10:54:04'), ('4', '4', '3', '2015-09-23 11:18:07', '2015-09-23 11:18:09');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(12) COLLATE utf8_bin NOT NULL,
  `mail` varchar(30) COLLATE utf8_bin NOT NULL,
  `nick` varchar(12) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  `passsalt` varchar(255) COLLATE utf8_bin NOT NULL,
  `locked` tinyint(1) NOT NULL DEFAULT '1',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'teddy', 'test@test.com', 'teddy', '26eb6c521db4577f34c0d1b286e5f1a1', '8d5a6dbfa7c36444c72fcb3c3ab88a83', '0', '2015-08-08 15:23:49', '2015-08-08 15:23:49'), ('2', 'test', 'test@aaa.com', 'asd', 'assaasd', 'asdsa', '0', '2015-09-23 09:38:40', '2015-09-23 09:38:42');
COMMIT;

-- ----------------------------
--  Table structure for `user_address`
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `province` int(11) NOT NULL,
  `city` int(11) DEFAULT NULL,
  `region` int(11) DEFAULT NULL,
  `street` int(11) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `user_area_collected`
-- ----------------------------
DROP TABLE IF EXISTS `user_area_collected`;
CREATE TABLE `user_area_collected` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `areaId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `user_basicinfo`
-- ----------------------------
DROP TABLE IF EXISTS `user_basicinfo`;
CREATE TABLE `user_basicinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `phoneNumber` varchar(11) COLLATE utf8_bin NOT NULL,
  `sign` varchar(100) COLLATE utf8_bin NOT NULL,
  `avatar` varchar(50) COLLATE utf8_bin NOT NULL,
  `webSite` varchar(50) COLLATE utf8_bin NOT NULL,
  `birth` datetime DEFAULT NULL,
  `balance` decimal(10,4) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `user_basicinfo`
-- ----------------------------
BEGIN;
INSERT INTO `user_basicinfo` VALUES ('1', '1', '17712877316', '1', '/img/a.jpg', 'http://www.lc4e.com', '2015-09-22 09:56:03', '12.1200', '2015-09-22 09:56:06', '2015-09-22 09:56:07');
COMMIT;

-- ----------------------------
--  Table structure for `user_blocked`
-- ----------------------------
DROP TABLE IF EXISTS `user_blocked`;
CREATE TABLE `user_blocked` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `blockedUserId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `user_extend`
-- ----------------------------
DROP TABLE IF EXISTS `user_extend`;
CREATE TABLE `user_extend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `webSite` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `github` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `twitter` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `facebook` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `google` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `qq` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `user_followed`
-- ----------------------------
DROP TABLE IF EXISTS `user_followed`;
CREATE TABLE `user_followed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `followedUserId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `user_message`
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `destUser` int(11) NOT NULL,
  `read` tinyint(4) NOT NULL DEFAULT '0',
  `title` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `body` varchar(100) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `endTime` datetime NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `user_role`
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('1', '1', '1', '2015-08-16 11:58:07', '2015-08-07 11:58:09', '2015-08-07 11:59:55'), ('2', '1', '2', '2015-08-08 13:05:04', '2015-08-07 13:05:13', '2015-08-07 13:05:16');
COMMIT;

-- ----------------------------
--  Table structure for `user_tag`
-- ----------------------------
DROP TABLE IF EXISTS `user_tag`;
CREATE TABLE `user_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `tagId` varchar(10) COLLATE utf8_bin NOT NULL,
  `pw` decimal(10,4) NOT NULL DEFAULT '1.0000',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `user_tag`
-- ----------------------------
BEGIN;
INSERT INTO `user_tag` VALUES ('1', '1', '1', '1.5000', '2015-09-22 09:53:18', '2015-09-22 09:53:21'), ('2', '1', '3', '1.0000', '2015-09-22 09:53:30', '2015-09-22 09:53:32'), ('3', '2', '1', '1.0000', '2015-09-23 09:39:01', '2015-09-23 09:39:03'), ('4', '2', '4', '10.0000', '2015-09-23 11:19:56', '2015-09-23 11:19:58');
COMMIT;

-- ----------------------------
--  Table structure for `user_topic_blocked`
-- ----------------------------
DROP TABLE IF EXISTS `user_topic_blocked`;
CREATE TABLE `user_topic_blocked` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `user_topic_blocked`
-- ----------------------------
BEGIN;
INSERT INTO `user_topic_blocked` VALUES ('1', '2', '2', '2015-09-23 10:08:30', '2015-09-23 10:08:33');
COMMIT;

-- ----------------------------
--  Table structure for `user_topic_collected`
-- ----------------------------
DROP TABLE IF EXISTS `user_topic_collected`;
CREATE TABLE `user_topic_collected` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `user_topic_view_attitude`
-- ----------------------------
DROP TABLE IF EXISTS `user_topic_view_attitude`;
CREATE TABLE `user_topic_view_attitude` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `attitude` int(11) NOT NULL DEFAULT '0' COMMENT '0-default,1-agree,-1-disagree',
  `createTime` datetime NOT NULL,
  `updaeTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  View structure for `vw_topic`
-- ----------------------------
DROP VIEW IF EXISTS `vw_topic`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_topic` AS select `vw_topic_no_pw`.`id` AS `id`,`vw_topic_no_pw`.`areaId` AS `areaId`,`vw_topic_no_pw`.`authorId` AS `authorId`,`vw_topic_no_pw`.`title` AS `title`,`vw_topic_no_pw`.`pubTime` AS `pubTime`,`vw_topic_no_pw`.`areaAbbr` AS `areaAbbr`,`vw_topic_no_pw`.`areaName` AS `areaName`,`vw_topic_no_pw`.`author` AS `author`,`vw_topic_no_pw`.`authorAvatar` AS `authorAvatar`,`vw_topic_no_pw`.`count` AS `count`,`vw_topic_no_pw`.`lastCommentId` AS `lastCommentId`,`user`.`id` AS `lastUser`,`user`.`nick` AS `lastUserNick`,`sys_comment`.`order` AS `lastCommentOrder`,`sys_comment`.`createTime` AS `lastCommentTime` from (((`vw_topic_no_pw` left join `sys_comment` on((`vw_topic_no_pw`.`lastCommentId` = `sys_comment`.`id`))) left join `user` on((`sys_comment`.`userId` = `user`.`id`))) left join `user_basicinfo` on((`user`.`id` = `user_basicinfo`.`userId`)));

-- ----------------------------
--  View structure for `vw_topic_no_pw`
-- ----------------------------
DROP VIEW IF EXISTS `vw_topic_no_pw`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_topic_no_pw` AS select `sys_topic`.`id` AS `id`,`sys_topic`.`areaId` AS `areaId`,`sys_topic`.`userId` AS `authorId`,`sys_topic`.`title` AS `title`,`sys_topic`.`createTime` AS `pubTime`,`sys_area`.`abbr` AS `areaAbbr`,`sys_area`.`name` AS `areaName`,`user`.`nick` AS `author`,`user_basicinfo`.`avatar` AS `authorAvatar`,count(`sys_comment`.`id`) AS `count`,max(`sys_comment`.`id`) AS `lastCommentId` from (((((((`sys_topic` left join `sys_area` on((`sys_topic`.`areaId` = `sys_area`.`id`))) left join `sys_area_status` on((`sys_area`.`areaStatusId` = `sys_area_status`.`id`))) left join `user` on((`sys_topic`.`userId` = `user`.`id`))) left join `user_basicinfo` on((`user`.`id` = `user_basicinfo`.`userId`))) left join `sys_topic_comment_status` `tStatus` on((`sys_topic`.`tcStatusId` = `tStatus`.`id`))) left join `sys_comment` on((`sys_topic`.`id` = `sys_comment`.`topicId`))) left join `sys_topic_comment_status` `cStatus` on((`sys_comment`.`tcStatusId` = `cStatus`.`id`))) where ((`tStatus`.`visible` = 1) and (`tStatus`.`delete` = 0) and (`tStatus`.`release` = 1) and ((`cStatus`.`visible` = 1) or isnull(`cStatus`.`visible`)) and ((`cStatus`.`release` = 1) or isnull(`cStatus`.`release`)) and ((`cStatus`.`delete` = 0) or isnull(`cStatus`.`delete`)) and (`sys_area_status`.`close` = 0)) group by `sys_topic`.`id`,`sys_topic`.`areaId`,`sys_topic`.`userId`,`sys_topic`.`title`,`sys_topic`.`createTime`,`sys_area`.`abbr`,`sys_area`.`name`,`user`.`nick`,`user_basicinfo`.`avatar`;

-- ----------------------------
--  View structure for `vw_topic_pw`
-- ----------------------------
DROP VIEW IF EXISTS `vw_topic_pw`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_topic_pw` AS select `vw_topic_tag_count_pw`.`id` AS `id`,`vw_topic_tag_count_pw`.`areaAbbr` AS `areaAbbr`,`vw_topic_tag_count_pw`.`areaName` AS `areaName`,`vw_topic_tag_count_pw`.`title` AS `title`,`vw_topic_tag_count_pw`.`author` AS `author`,`vw_topic_tag_count_pw`.`authorId` AS `authorId`,`vw_topic_tag_count_pw`.`curTagUser` AS `curTagUser`,`user`.`nick` AS `lastUserNick`,`user`.`id` AS `lastUser`,`sys_comment`.`order` AS `lastCommentOrder`,`vw_topic_tag_count_pw`.`lastCommentId` AS `lastCommentId`,`vw_topic_status_pw`.`statusPw` AS `tsPw`,sum(`vw_topic_tag_count_pw`.`utPw`) AS `utPw`,`vw_topic_user_attitude_pw`.`utvaPw` AS `utvaPw`,`vw_topic_tag_count_pw`.`count` AS `count`,`vw_topic_tag_count_pw`.`pubTime` AS `pubTime`,`vw_topic_tag_count_pw`.`areaId` AS `areaId`,`vw_topic_tag_count_pw`.`authorAvatar` AS `authorAvatar` from ((((`vw_topic_tag_count_pw` left join `vw_topic_status_pw` on((`vw_topic_tag_count_pw`.`id` = `vw_topic_status_pw`.`id`))) left join `sys_comment` on((`vw_topic_tag_count_pw`.`id` = `sys_comment`.`topicId`))) left join `user` on((`sys_comment`.`userId` = `user`.`id`))) left join `vw_topic_user_attitude_pw` on((`vw_topic_tag_count_pw`.`id` = `vw_topic_user_attitude_pw`.`id`))) group by `vw_topic_tag_count_pw`.`id`,`vw_topic_tag_count_pw`.`curTagUser`,`vw_topic_tag_count_pw`.`areaAbbr`,`vw_topic_tag_count_pw`.`areaName`,`vw_topic_tag_count_pw`.`title`,`vw_topic_tag_count_pw`.`author`,`vw_topic_tag_count_pw`.`authorId`,`user`.`nick`,`user`.`id`,`sys_comment`.`order`,`vw_topic_tag_count_pw`.`lastCommentId`,`vw_topic_status_pw`.`statusPw`,`vw_topic_user_attitude_pw`.`utvaPw`,`vw_topic_tag_count_pw`.`count`,`vw_topic_tag_count_pw`.`pubTime`,`vw_topic_tag_count_pw`.`areaId`,`vw_topic_tag_count_pw`.`authorAvatar`;

-- ----------------------------
--  View structure for `vw_topic_status_pw`
-- ----------------------------
DROP VIEW IF EXISTS `vw_topic_status_pw`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_topic_status_pw` AS select `sys_topic`.`id` AS `id`,ifnull(sum(`sys_topic_status`.`pw`),0) AS `statusPw` from ((`sys_topic` left join `topic_status` on((`sys_topic`.`id` = `topic_status`.`topicId`))) left join `sys_topic_status` on((`topic_status`.`statusId` = `sys_topic_status`.`id`))) group by `sys_topic`.`id`;

-- ----------------------------
--  View structure for `vw_topic_tag_count_pw`
-- ----------------------------
DROP VIEW IF EXISTS `vw_topic_tag_count_pw`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_topic_tag_count_pw` AS select `sys_topic`.`id` AS `id`,`sys_area`.`abbr` AS `areaAbbr`,`sys_area`.`name` AS `areaName`,`sys_topic`.`title` AS `title`,`user`.`nick` AS `author`,ifnull(`user_tag`.`pw`,0) AS `utPw`,count(`sys_comment`.`id`) AS `count`,max(`sys_comment`.`id`) AS `lastCommentId`,`user_tag`.`userId` AS `curTagUser`,`user_basicinfo`.`avatar` AS `authorAvatar`,`sys_topic`.`createTime` AS `pubTime`,`sys_topic`.`areaId` AS `areaId`,`sys_topic`.`userId` AS `authorId` from (((((((((`sys_topic` left join `sys_topic_comment_status` `tStatus` on((`sys_topic`.`tcStatusId` = `tStatus`.`id`))) left join `sys_area` on((`sys_topic`.`areaId` = `sys_area`.`id`))) left join `user` on((`sys_topic`.`userId` = `user`.`id`))) left join `sys_comment` on((`sys_comment`.`topicId` = `sys_topic`.`id`))) left join `topic_tag` on((`sys_topic`.`id` = `topic_tag`.`topicId`))) left join `user_tag` on((`topic_tag`.`tagId` = `user_tag`.`tagId`))) left join `sys_topic_comment_status` `cStatus` on((`sys_comment`.`tcStatusId` = `cStatus`.`id`))) left join `user_basicinfo` on((`user`.`id` = `user_basicinfo`.`userId`))) left join `sys_area_status` on((`sys_area`.`areaStatusId` = `sys_area_status`.`id`))) where ((`tStatus`.`visible` = 1) and (`tStatus`.`delete` = 0) and (`tStatus`.`release` = 1) and ((`cStatus`.`visible` = 1) or isnull(`cStatus`.`visible`)) and ((`cStatus`.`release` = 1) or isnull(`cStatus`.`release`)) and ((`cStatus`.`delete` = 0) or isnull(`cStatus`.`delete`)) and (`sys_area_status`.`close` = 0)) group by `sys_topic`.`id`,`user_tag`.`id`,`sys_area`.`abbr`,`sys_area`.`name`,`sys_topic`.`title`,`user`.`nick`,`user_tag`.`userId`,`user_basicinfo`.`avatar`,`sys_topic`.`createTime`,`sys_topic`.`areaId`,`sys_topic`.`userId`;

-- ----------------------------
--  View structure for `vw_topic_user_attitude_pw`
-- ----------------------------
DROP VIEW IF EXISTS `vw_topic_user_attitude_pw`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_topic_user_attitude_pw` AS select `sys_topic`.`id` AS `id`,ifnull(`user_topic_view_attitude`.`attitude`,0) AS `utvaPw` from (`sys_topic` left join `user_topic_view_attitude` on((`sys_topic`.`id` = `user_topic_view_attitude`.`topicId`))) group by `sys_topic`.`id`,`user_topic_view_attitude`.`attitude`;

-- ----------------------------
--  View structure for `vw_user_role_permission`
-- ----------------------------
DROP VIEW IF EXISTS `vw_user_role_permission`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_user_role_permission` AS select `user`.`id` AS `id`,`user`.`name` AS `name`,`user`.`mail` AS `mail`,`user`.`nick` AS `nick`,`user`.`password` AS `password`,`user`.`passsalt` AS `passsalt`,`user`.`locked` AS `locked`,`user`.`createTime` AS `createTime`,`user`.`updateTime` AS `updateTime`,`sys_role`.`abbr` AS `roleAbbr`,`sys_permission`.`abbr` AS `permissionAbbr`,`sys_role`.`name` AS `roleName`,`sys_role`.`description` AS `roleDescription`,`sys_role`.`available` AS `roleAvailable`,`sys_permission`.`name` AS `permissionName`,`sys_permission`.`description` AS `permissionDescription`,`sys_permission`.`available` AS `permissionAvailable`,`user_role`.`endTime` AS `roleEndTime` from ((((`user` left join `user_role` on((`user`.`id` = `user_role`.`userId`))) left join `sys_role` on((`user_role`.`roleId` = `sys_role`.`id`))) left join `sys_role_permission` on((`sys_role`.`id` = `sys_role_permission`.`roleId`))) left join `sys_permission` on((`sys_role_permission`.`permissionId` = `sys_permission`.`id`)));

SET FOREIGN_KEY_CHECKS = 1;
