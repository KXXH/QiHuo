/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : xm10-qihuo

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-06-18 00:17:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_bm
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bm`;
CREATE TABLE `tbl_bm` (
  `OrderId` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` int(11) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `StockId` int(11) NOT NULL,
  `StockName` varchar(255) NOT NULL,
  `Quantity` int(255) NOT NULL,
  `BUnitPrice` double(255,2) NOT NULL,
  `CreateAt` datetime NOT NULL,
  `role_id` varchar(255) NOT NULL DEFAULT 'normal',
  PRIMARY KEY (`OrderId`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_bm
-- ----------------------------
INSERT INTO `tbl_bm` VALUES ('134', '5', 'aeds123', '32', '亚马逊', '100', '1816.32', '2019-06-05 09:30:21', 'normal');
INSERT INTO `tbl_bm` VALUES ('135', '7', 'FTT123', '15', '纽约白银', '100', '14.50', '2019-06-05 12:41:43', 'normal');
INSERT INTO `tbl_bm` VALUES ('138', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:48', 'normal');
INSERT INTO `tbl_bm` VALUES ('139', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:50', 'normal');
INSERT INTO `tbl_bm` VALUES ('140', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:50', 'normal');
INSERT INTO `tbl_bm` VALUES ('143', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:51', 'normal');
INSERT INTO `tbl_bm` VALUES ('144', '7', 'FTT123', '22', '美国大豆', '10', '884.25', '2019-06-05 12:42:18', 'normal');
INSERT INTO `tbl_bm` VALUES ('145', '5', 'aeds123', '22', '美国大豆', '20', '884.25', '2019-06-07 17:40:15', 'normal');
INSERT INTO `tbl_bm` VALUES ('149', '5', 'aeds123', '16', '在岸人民币', '150', '6.91', '2019-06-08 00:07:48', 'normal');
INSERT INTO `tbl_bm` VALUES ('150', '5', 'aeds123', '32', '亚马逊', '100', '1816.32', '2019-06-08 14:24:59', 'normal');
INSERT INTO `tbl_bm` VALUES ('151', '5', 'aeds123', '32', '亚马逊', '100', '1816.32', '2019-06-08 14:29:32', 'normal');
