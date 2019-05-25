/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-05-25 14:53:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_logininfo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_logininfo`;
CREATE TABLE `tbl_logininfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_logininfo
-- ----------------------------
INSERT INTO `tbl_logininfo` VALUES ('1', 'admin', '2019-05-22 22:17:32', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('2', 'admin', '2019-05-22 22:17:47', 'login with token', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('3', 'admin', '2019-05-22 22:21:05', 'login with token', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('4', 'FTT', '2019-05-22 22:22:18', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('5', 'admin', '2019-05-23 16:41:43', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('6', 'admin', '2019-05-23 16:48:09', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('7', 'admin', '2019-05-23 16:53:39', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('8', 'admin', '2019-05-23 17:08:34', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('9', 'admin', '2019-05-23 17:25:29', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('10', 'admin', '2019-05-23 17:31:25', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('11', 'admin', '2019-05-23 17:36:36', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('12', 'admin', '2019-05-23 17:42:58', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('13', 'admin', '2019-05-23 17:53:17', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('14', 'admin', '2019-05-23 17:54:56', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('15', 'admin', '2019-05-23 20:01:12', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('16', 'admin', '2019-05-23 20:09:14', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('17', 'admin', '2019-05-23 20:14:39', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('18', 'admin', '2019-05-23 20:20:21', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('19', 'admin', '2019-05-23 20:25:39', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('20', 'admin', '2019-05-23 20:28:07', 'login with token', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('21', 'admin999', '2019-05-23 20:29:47', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('22', 'admin999', '2019-05-23 20:33:20', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('23', 'admin999', '2019-05-23 21:18:47', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('24', 'admin999', '2019-05-23 21:26:47', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('25', 'admin999', '2019-05-23 21:29:28', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('26', 'admin999', '2019-05-23 21:34:41', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('27', 'admin999', '2019-05-23 21:35:18', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('28', 'admin999', '2019-05-23 21:40:53', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('29', 'admin999', '2019-05-23 22:08:42', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('30', 'admin999', '2019-05-23 22:14:05', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('31', 'admin999', '2019-05-23 22:17:26', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('32', 'admin999', '2019-05-23 22:23:14', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('33', 'admin', '2019-05-23 22:25:33', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('34', 'FTT', '2019-05-23 22:25:40', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('35', 'admin999', '2019-05-23 22:25:49', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('36', 'FTT', '2019-05-23 22:26:08', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('37', 'admin999', '2019-05-23 22:26:41', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('38', 'admin999', '2019-05-23 22:28:21', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('39', 'FTT', '2019-05-23 22:29:53', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('40', 'admin999', '2019-05-23 22:30:06', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('41', 'FTT', '2019-05-23 22:31:00', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('42', 'admin999', '2019-05-23 22:31:13', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('43', 'admin999', '2019-05-23 22:35:50', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('44', 'admin999', '2019-05-23 22:47:44', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('45', 'admin999', '2019-05-25 13:03:17', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('46', 'admin999', '2019-05-25 13:32:39', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('47', 'admin999', '2019-05-25 13:33:51', 'login with password', '127.0.0.1');
INSERT INTO `tbl_logininfo` VALUES ('48', 'admin999', '2019-05-25 13:36:02', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('49', 'admin999', '2019-05-25 13:37:01', 'login with password', '127.0.0.1');
INSERT INTO `tbl_logininfo` VALUES ('50', 'admin999', '2019-05-25 13:38:41', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('51', 'admin999', '2019-05-25 13:59:51', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('52', 'admin999', '2019-05-25 14:04:10', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('53', 'admin999', '2019-05-25 14:08:47', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('54', 'admin999', '2019-05-25 14:09:32', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('55', 'admin999', '2019-05-25 14:13:01', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('56', 'admin999', '2019-05-25 14:13:49', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('57', 'admin999', '2019-05-25 14:15:33', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('58', 'admin999', '2019-05-25 14:17:30', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('59', 'admin999', '2019-05-25 14:25:55', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('60', 'admin999', '2019-05-25 14:28:47', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('61', 'admin999', '2019-05-25 14:38:49', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('62', 'admin999', '2019-05-25 14:40:29', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('63', 'admin999', '2019-05-25 14:43:02', 'login with password', '0:0:0:0:0:0:0:1');
INSERT INTO `tbl_logininfo` VALUES ('64', 'admin999', '2019-05-25 14:50:59', 'login with password', '0:0:0:0:0:0:0:1');
