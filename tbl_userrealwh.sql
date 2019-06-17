/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : xm10-qihuo

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-06-18 00:18:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_userrealwh
-- ----------------------------
DROP TABLE IF EXISTS `tbl_userrealwh`;
CREATE TABLE `tbl_userrealwh` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` int(11) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `StockId` int(11) NOT NULL,
  `StockName` varchar(255) NOT NULL,
  `Quantity` int(255) NOT NULL,
  `BUnitPrice` double(11,2) NOT NULL DEFAULT '0.00',
  `CreateAt` datetime NOT NULL,
  `role_id` varchar(255) DEFAULT 'normal',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_userrealwh
-- ----------------------------
INSERT INTO `tbl_userrealwh` VALUES ('2', '5', 'aeds123', '32', '亚马逊', '300', '0.00', '2019-06-04 21:49:14', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('4', '5', 'aeds123', '12', '纽约黄金', '5', '0.00', '2019-06-18 21:43:25', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('5', '5', 'aeds123', '13', '纽约原油', '450', '0.00', '2019-06-04 23:55:20', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('7', '7', 'FTT123', '13', '纽约原油', '0', '0.00', '2019-06-01 17:36:54', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('8', '7', 'FTT123', '14', '布伦特原油', '300', '0.00', '2019-06-05 09:31:26', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('9', '7', 'FTT123', '15', '纽约白银', '1100', '0.00', '2019-06-05 09:45:35', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('10', '5', 'aeds123', '15', '纽约白银', '1100', '0.00', '2019-06-07 11:08:32', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('11', '5', 'aeds123', '14', '布伦特原油', '100', '0.00', '2019-06-05 09:26:51', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('12', '7', 'FTT123', '22', '美国大豆', '0', '0.00', '2019-06-05 09:30:48', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('13', '7', 'FTT123', '32', '亚马逊', '20', '0.00', '2019-06-05 09:31:13', 'normal');
