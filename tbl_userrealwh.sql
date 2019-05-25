/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-05-25 14:15:08
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
  `CreateAt` datetime NOT NULL,
  `role_id` varchar(255) DEFAULT 'normal',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_userrealwh
-- ----------------------------
INSERT INTO `tbl_userrealwh` VALUES ('1', '5', 'aeds123', '15', '河南井盖', '200', '2019-05-16 21:35:01', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('2', '5', 'aeds123', '1', '腾讯科技', '200', '2019-05-16 21:35:33', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('3', '5', 'aeds123', '2', '中国银行', '1200', '2019-05-21 22:47:55', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('4', '5', 'aeds123', '3', '川大智胜', '500', '2019-05-16 21:36:13', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('43', '7', 'FTT123', '9', '三九感冒', '50', '2019-05-16 21:43:08', 'normal');
INSERT INTO `tbl_userrealwh` VALUES ('47', '5', 'aeds123', '58', '58同城', '600', '2019-05-22 15:01:41', 'normal');
