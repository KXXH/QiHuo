/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-04-14 14:48:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_userinfo`;
CREATE TABLE `tbl_userinfo` (
  `UserId` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(255) NOT NULL,
  `Passwd` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Phone` varchar(255) NOT NULL,
  `WeChatId` varchar(255) DEFAULT NULL,
  `CreateAt` datetime NOT NULL,
  `role_id` varchar(255) NOT NULL DEFAULT 'normal',
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_userinfo
-- ----------------------------
INSERT INTO `tbl_userinfo` VALUES ('5', 'aeds123', 'asds', '2323@qwe', '1244', null, '2019-03-16 00:00:00', 'normal');
INSERT INTO `tbl_userinfo` VALUES ('7', 'FTT123', '123456', 'zjs@sd.com', '12344', null, '2019-03-16 00:00:00', 'normal');
INSERT INTO `tbl_userinfo` VALUES ('8', 'FTT', '123456', 'zjs@sd.com', '12344', null, '2019-03-16 00:00:00', 'normal');
INSERT INTO `tbl_userinfo` VALUES ('9', 'fasfas', 'asfasfaf', 'sdd@qw', '234567', null, '2019-03-16 00:00:00', 'normal');
INSERT INTO `tbl_userinfo` VALUES ('10', '哈哈哈', 'asdadsd', 'qwww@qww', '1244555', null, '2019-03-16 00:00:00', 'normal');
INSERT INTO `tbl_userinfo` VALUES ('11', 'admin', 'admin', 'admin@admin.com', '111111', null, '2019-03-24 21:24:35', 'admin');
