/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : lc4e

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2015-09-04 21:46:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_log
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
-- Records of admin_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_address
-- ----------------------------
DROP TABLE IF EXISTS `sys_address`;
CREATE TABLE `sys_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(11) COLLATE utf8_bin NOT NULL,
  `description` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_address
-- ----------------------------

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL,
  `css` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '',
  `icon` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '',
  `areaStatusId` int(11) NOT NULL DEFAULT '1',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('1', '0', 'LC4E', 'root', '根域', '', '', '1', '2015-08-10 11:05:23', '2015-08-10 11:05:25');

-- ----------------------------
-- Table structure for sys_area_status
-- ----------------------------
DROP TABLE IF EXISTS `sys_area_status`;
CREATE TABLE `sys_area_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
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
-- Records of sys_area_status
-- ----------------------------
INSERT INTO `sys_area_status` VALUES ('1', 'online', '正常', '此分区目前正常', '1', '1', '0', '1', '1', '2015-08-10 11:03:51', '2015-08-10 11:03:52');

-- ----------------------------
-- Table structure for sys_comment
-- ----------------------------
DROP TABLE IF EXISTS `sys_comment`;
CREATE TABLE `sys_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `order` int(11) NOT NULL,
  `title` varchar(50) COLLATE utf8_bin DEFAULT '',
  `body` varchar(1000) COLLATE utf8_bin NOT NULL,
  `attachs` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `tcStatusId` int(11) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_comment
-- ----------------------------

-- ----------------------------
-- Table structure for sys_common_variable
-- ----------------------------
DROP TABLE IF EXISTS `sys_common_variable`;
CREATE TABLE `sys_common_variable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_bin NOT NULL,
  `value` varchar(50) COLLATE utf8_bin NOT NULL,
  `error` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `description` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_common_variable
-- ----------------------------
INSERT INTO `sys_common_variable` VALUES ('1', 'SiteName', 'Light Community', '', '', '2015-08-02 22:44:43', '2015-08-02 22:44:45');
INSERT INTO `sys_common_variable` VALUES ('2', 'IndexPageSize', '20', '', '', '2015-08-02 22:45:29', '2015-08-02 22:45:32');
INSERT INTO `sys_common_variable` VALUES ('3', 'Register', 'true', 'Register is forbid', '', '2015-08-02 22:45:55', '2015-08-02 22:45:58');
INSERT INTO `sys_common_variable` VALUES ('4', 'SimpleRegister', 'true', '', '', '2015-08-02 22:46:14', '2015-08-02 22:46:16');
INSERT INTO `sys_common_variable` VALUES ('5', 'CaptchaCaseSensitive', 'true', '', '', '2015-08-11 10:31:49', '2015-08-11 10:31:51');
INSERT INTO `sys_common_variable` VALUES ('6', 'Captcha', 'false', '验证码错误', '', '2015-08-11 10:33:20', '2015-08-11 10:33:23');
INSERT INTO `sys_common_variable` VALUES ('7', 'UserInitBalances', '10', '', '', '2015-08-22 11:20:32', '2015-08-22 11:20:34');

-- ----------------------------
-- Table structure for sys_dynamic_info
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
-- Records of sys_dynamic_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_history_top
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
-- Records of sys_history_top
-- ----------------------------

-- ----------------------------
-- Table structure for sys_ip_forbit
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
-- Records of sys_ip_forbit
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `order` int(11) NOT NULL,
  `cron` varchar(255) COLLATE utf8_bin NOT NULL,
  `className` varchar(255) COLLATE utf8_bin NOT NULL,
  `enable` tinyint(4) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_job
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
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
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL,
  `order` int(11) NOT NULL,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `css` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '',
  `icon` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '1', 'Menu', 'Menu', 'basic', 'browser', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('2', '1', '1', '/', 'Home', 'basic', 'home', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('3', '1', '2', '/', 'Message', 'basic', 'mail', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('4', '1', '3', '/', 'Friends', 'basic', 'user', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('5', '1', '4', '/', 'Language', 'basic', 'font', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('6', '5', '1', '/', 'C/C++', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('7', '5', '3', '/', 'Java', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('8', '5', '2', '/', 'Javascript', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('9', '5', '6', '/', 'Script', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('10', '9', '1', '/', 'Python', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('11', '9', '2', '/', 'Ruby', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('12', '1', '5', '/', 'Python1', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('13', '9', '2', '/', 'Python2', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');
INSERT INTO `sys_menu` VALUES ('14', '13', '3', '/', 'testtitle', 'basic', '', '2015-08-02 21:56:25', '2015-08-02 21:56:27');

-- ----------------------------
-- Table structure for sys_operate_type
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
-- Records of sys_operate_type
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
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
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'view', 'view', 'asd', '1', '2015-08-07 13:06:37', '2015-08-07 13:06:40');
INSERT INTO `sys_permission` VALUES ('2', 'add', 'add', 'asxxawed', '1', '2015-08-07 13:06:50', '2015-08-07 13:06:53');
INSERT INTO `sys_permission` VALUES ('3', 'delete', 'delete', 'xckkxz', '1', '2015-08-07 13:07:06', '2015-08-07 13:07:08');
INSERT INTO `sys_permission` VALUES ('4', 'query', 'query', 'dsdd', '1', '2015-08-07 13:07:17', '2015-08-07 13:07:20');
INSERT INTO `sys_permission` VALUES ('5', 'clearcache', 'clearcache', '清缓存', '1', '2015-08-12 11:34:30', '2015-08-12 11:34:32');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(15) COLLATE utf8_bin NOT NULL,
  `name` varchar(15) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `available` tinyint(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', 'admin', 'assssdas', '1');
INSERT INTO `sys_role` VALUES ('2', 'member', 'meber', 'assxx', '1');

-- ----------------------------
-- Table structure for sys_role_permission
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
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1', '2015-08-07 13:07:36', '2015-08-07 13:07:39');
INSERT INTO `sys_role_permission` VALUES ('2', '1', '3', '2015-08-07 13:07:45', '2015-08-07 13:07:48');
INSERT INTO `sys_role_permission` VALUES ('3', '1', '4', '2015-08-07 13:07:56', '2015-08-07 13:07:58');
INSERT INTO `sys_role_permission` VALUES ('4', '2', '3', '2015-08-07 13:08:05', '2015-08-07 13:08:07');
INSERT INTO `sys_role_permission` VALUES ('5', '2', '2', '2015-08-07 13:08:15', '2015-08-07 13:08:19');

-- ----------------------------
-- Table structure for sys_tag
-- ----------------------------
DROP TABLE IF EXISTS `sys_tag`;
CREATE TABLE `sys_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(10) COLLATE utf8_bin NOT NULL,
  `description` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_tag
-- ----------------------------

-- ----------------------------
-- Table structure for sys_tag_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_tag_topic`;
CREATE TABLE `sys_tag_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagId` int(11) NOT NULL,
  `topicId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_tag_topic
-- ----------------------------

-- ----------------------------
-- Table structure for sys_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_topic`;
CREATE TABLE `sys_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `areaId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `title` varchar(50) COLLATE utf8_bin NOT NULL,
  `body` varchar(5000) COLLATE utf8_bin NOT NULL,
  `attachs` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `tcStatusId` int(11) NOT NULL DEFAULT '0',
  `count` int(11) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_topic
-- ----------------------------

-- ----------------------------
-- Table structure for sys_topic_comment_status
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_topic_comment_status
-- ----------------------------

-- ----------------------------
-- Table structure for user
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7', 'teddy', 'test@test.com', 'teddy', '26eb6c521db4577f34c0d1b286e5f1a1', '8d5a6dbfa7c36444c72fcb3c3ab88a83', '0', '2015-08-08 15:23:49', '2015-08-08 15:23:49');

-- ----------------------------
-- Table structure for user_address
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
-- Records of user_address
-- ----------------------------

-- ----------------------------
-- Table structure for user_area_collected
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
-- Records of user_area_collected
-- ----------------------------

-- ----------------------------
-- Table structure for user_basicinfo
-- ----------------------------
DROP TABLE IF EXISTS `user_basicinfo`;
CREATE TABLE `user_basicinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `phoneNumber` varchar(11) COLLATE utf8_bin NOT NULL DEFAULT '',
  `sign` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `avatar` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `balances` decimal(10,4) unsigned NOT NULL DEFAULT '0.0000',
  `birth` datetime DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_basicinfo
-- ----------------------------

-- ----------------------------
-- Table structure for user_blocked
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
-- Records of user_blocked
-- ----------------------------

-- ----------------------------
-- Table structure for user_followed
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
-- Records of user_followed
-- ----------------------------

-- ----------------------------
-- Table structure for user_message
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
-- Records of user_message
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
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
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1', '2015-08-16 11:58:07', '2015-08-07 11:58:09', '2015-08-07 11:59:55');
INSERT INTO `user_role` VALUES ('2', '1', '2', '2015-08-08 13:05:04', '2015-08-07 13:05:13', '2015-08-07 13:05:16');

-- ----------------------------
-- Table structure for user_tag
-- ----------------------------
DROP TABLE IF EXISTS `user_tag`;
CREATE TABLE `user_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagId` varchar(10) COLLATE utf8_bin NOT NULL,
  `userId` int(11) NOT NULL,
  `count` int(11) NOT NULL DEFAULT '1',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_tag
-- ----------------------------

-- ----------------------------
-- Table structure for user_topic_blocked
-- ----------------------------
DROP TABLE IF EXISTS `user_topic_blocked`;
CREATE TABLE `user_topic_blocked` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_topic_blocked
-- ----------------------------

-- ----------------------------
-- Table structure for user_topic_collected
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
-- Records of user_topic_collected
-- ----------------------------

-- ----------------------------
-- Table structure for user_topic_view_attitude
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
-- Records of user_topic_view_attitude
-- ----------------------------

-- ----------------------------
-- View structure for vw_user_role_permission
-- ----------------------------
DROP VIEW IF EXISTS `vw_user_role_permission`;
CREATE SQL SECURITY DEFINER VIEW `vw_user_role_permission` AS select `user`.`id` AS `id`,`user`.`name` AS `name`,`user`.`mail` AS `mail`,`user`.`nick` AS `nick`,`user`.`password` AS `password`,`user`.`passsalt` AS `passsalt`,`user`.`locked` AS `locked`,`user`.`createTime` AS `createTime`,`user`.`updateTime` AS `updateTime`,`sys_role`.`abbr` AS `roleAbbr`,`sys_permission`.`abbr` AS `permissionAbbr`,`sys_role`.`name` AS `roleName`,`sys_role`.`description` AS `roleDescription`,`sys_role`.`available` AS `roleAvailable`,`sys_permission`.`name` AS `permissionName`,`sys_permission`.`description` AS `permissionDescription`,`sys_permission`.`available` AS `permissionAvailable`,`user_role`.`endTime` AS `roleEndTime` from ((((`user` left join `user_role` on((`user`.`id` = `user_role`.`userId`))) left join `sys_role` on((`user_role`.`roleId` = `sys_role`.`id`))) left join `sys_role_permission` on((`sys_role`.`id` = `sys_role_permission`.`roleId`))) left join `sys_permission` on((`sys_role_permission`.`permissionId` = `sys_permission`.`id`))) ;
