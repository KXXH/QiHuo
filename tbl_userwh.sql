/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-05-25 14:15:16
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
  `BUnitPrice` double(255,2) NOT NULL,
  `CreateAt` datetime NOT NULL,
  `role_id` varchar(255) NOT NULL DEFAULT 'normal',
  PRIMARY KEY (`OrderId`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_userwh
-- ----------------------------
INSERT INTO `tbl_userwh` VALUES ('1', '5', 'aeds123', '15', '河南井盖', '200', '10.00', '2019-05-16 21:35:01', 'normal');
INSERT INTO `tbl_userwh` VALUES ('2', '5', 'aeds123', '1', '腾讯科技', '200', '20.30', '2019-05-16 21:35:33', 'normal');
INSERT INTO `tbl_userwh` VALUES ('3', '5', 'aeds123', '2', '中国银行', '1000', '3.52', '2019-05-16 21:35:58', 'normal');
INSERT INTO `tbl_userwh` VALUES ('4', '5', 'aeds123', '3', '川大智胜', '500', '5.68', '2019-05-16 21:36:13', 'normal');
INSERT INTO `tbl_userwh` VALUES ('5', '5', 'aeds123', '8', '江湖穿克难', '500', '20.00', '2019-05-16 21:36:38', 'normal');
INSERT INTO `tbl_userwh` VALUES ('43', '7', 'FTT123', '9', '三九感冒', '50', '9.00', '2019-05-16 21:43:08', 'normal');
INSERT INTO `tbl_userwh` VALUES ('44', '5', 'aeds123', '3', '川大智胜', '200', '6.00', '2019-05-16 22:16:56', 'normal');
INSERT INTO `tbl_userwh` VALUES ('82', '5', 'aeds123', '8', '江湖穿克难', '400', '18.00', '2019-05-17 14:17:40', 'normal');
INSERT INTO `tbl_userwh` VALUES ('83', '5', 'aeds123', '8', '江湖穿克难', '400', '20.00', '2019-05-17 14:20:14', 'normal');
INSERT INTO `tbl_userwh` VALUES ('84', '5', 'aeds123', '8', '江湖穿克难', '400', '20.00', '2019-05-17 14:29:00', 'normal');
INSERT INTO `tbl_userwh` VALUES ('86', '5', 'aeds123', '2', '中国银行', '200', '4.00', '2019-05-21 22:47:55', 'normal');
INSERT INTO `tbl_userwh` VALUES ('87', '5', 'aeds123', '58', '58同城', '400', '8.00', '2019-05-22 15:01:41', 'normal');
INSERT INTO `tbl_userwh` VALUES ('88', '5', 'aeds123', '58', '58同城', '600', '98.00', '2019-05-23 21:34:19', 'normal');
