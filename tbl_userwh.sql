/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : xm10-qihuo

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-06-18 00:18:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_userwh
-- ----------------------------
DROP TABLE IF EXISTS `tbl_userwh`;
CREATE TABLE `tbl_userwh` (
  `OrderId` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` int(11) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `StockId` int(11) NOT NULL,
  `StockName` varchar(255) NOT NULL,
  `Quantity` int(255) NOT NULL,
  `BUnitPrice` double(11,2) NOT NULL,
  `CreateAt` datetime NOT NULL,
  `role_id` varchar(255) NOT NULL DEFAULT 'normal',
  PRIMARY KEY (`OrderId`)
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_userwh
-- ----------------------------
INSERT INTO `tbl_userwh` VALUES ('179', '5', 'aeds123', '15', '纽约白银', '1000', '14.50', '2019-06-05 00:22:32', 'normal');
INSERT INTO `tbl_userwh` VALUES ('180', '5', 'aeds123', '14', '布伦特原油', '200', '64.61', '2019-06-05 09:26:51', 'normal');
INSERT INTO `tbl_userwh` VALUES ('181', '5', 'aeds123', '15', '纽约白银', '200', '14.50', '2019-06-05 09:29:53', 'normal');
INSERT INTO `tbl_userwh` VALUES ('182', '5', 'aeds123', '14', '布伦特原油', '100', '64.61', '2019-06-05 09:29:59', 'normal');
INSERT INTO `tbl_userwh` VALUES ('183', '5', 'aeds123', '32', '亚马逊', '20', '1816.32', '2019-06-05 09:30:05', 'normal');
INSERT INTO `tbl_userwh` VALUES ('184', '5', 'aeds123', '22', '美国大豆', '10', '884.25', '2019-06-05 09:30:11', 'normal');
INSERT INTO `tbl_userwh` VALUES ('185', '5', 'aeds123', '32', '亚马逊', '100', '1816.32', '2019-06-05 09:30:21', 'normal');
INSERT INTO `tbl_userwh` VALUES ('186', '7', 'FTT123', '22', '美国大豆', '10', '884.25', '2019-06-05 09:30:48', 'normal');
INSERT INTO `tbl_userwh` VALUES ('187', '7', 'FTT123', '32', '亚马逊', '20', '1816.32', '2019-06-05 09:31:13', 'normal');
INSERT INTO `tbl_userwh` VALUES ('188', '7', 'FTT123', '14', '布伦特原油', '100', '64.61', '2019-06-05 09:31:26', 'normal');
INSERT INTO `tbl_userwh` VALUES ('189', '7', 'FTT123', '15', '纽约白银', '200', '14.50', '2019-06-05 09:35:53', 'normal');
INSERT INTO `tbl_userwh` VALUES ('190', '7', 'FTT123', '15', '纽约白银', '1000', '14.50', '2019-06-05 09:45:35', 'normal');
INSERT INTO `tbl_userwh` VALUES ('191', '7', 'FTT123', '15', '纽约白银', '1000', '14.50', '2019-06-05 09:45:35', 'normal');
INSERT INTO `tbl_userwh` VALUES ('193', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:48', 'normal');
INSERT INTO `tbl_userwh` VALUES ('194', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:48', 'normal');
INSERT INTO `tbl_userwh` VALUES ('195', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:48', 'normal');
INSERT INTO `tbl_userwh` VALUES ('196', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:50', 'normal');
INSERT INTO `tbl_userwh` VALUES ('197', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:50', 'normal');
INSERT INTO `tbl_userwh` VALUES ('198', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:50', 'normal');
INSERT INTO `tbl_userwh` VALUES ('199', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:50', 'normal');
INSERT INTO `tbl_userwh` VALUES ('200', '7', 'FTT123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:41:51', 'normal');
INSERT INTO `tbl_userwh` VALUES ('201', '7', 'FTT123', '22', '美国大豆', '10', '884.25', '2019-06-05 12:42:18', 'normal');
INSERT INTO `tbl_userwh` VALUES ('202', '5', 'aeds123', '15', '纽约白银', '150', '14.50', '2019-06-05 12:54:01', 'normal');
INSERT INTO `tbl_userwh` VALUES ('203', '5', 'aeds123', '15', '纽约白银', '150', '14.50', '2019-06-07 11:08:32', 'normal');
INSERT INTO `tbl_userwh` VALUES ('204', '5', 'aeds123', '22', '美国大豆', '20', '884.25', '2019-06-07 17:40:15', 'normal');
INSERT INTO `tbl_userwh` VALUES ('208', '5', 'aeds123', '16', '在岸人民币', '150', '6.91', '2019-06-08 00:07:48', 'normal');
INSERT INTO `tbl_userwh` VALUES ('209', '5', 'aeds123', '32', '亚马逊', '100', '1816.32', '2019-06-08 14:24:59', 'normal');
INSERT INTO `tbl_userwh` VALUES ('210', '5', 'aeds123', '32', '亚马逊', '100', '1816.32', '2019-06-08 14:29:32', 'normal');
