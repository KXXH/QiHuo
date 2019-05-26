/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-05-26 16:08:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_usdcny
-- ----------------------------
DROP TABLE IF EXISTS `tbl_usdcny`;
CREATE TABLE `tbl_usdcny` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `open` double NOT NULL,
  `high` double NOT NULL,
  `low` double NOT NULL,
  `close` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `date` (`date`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_usdcny
-- ----------------------------
INSERT INTO `tbl_usdcny` VALUES ('25', '2019-04-25', '6.7222', '6.7496', '6.7222', '6.7431');
INSERT INTO `tbl_usdcny` VALUES ('26', '2019-04-26', '6.7436', '6.7436', '6.7263', '6.7298');
INSERT INTO `tbl_usdcny` VALUES ('27', '2019-04-29', '6.7292', '6.7364', '6.7265', '6.7336');
INSERT INTO `tbl_usdcny` VALUES ('28', '2019-04-30', '6.7328', '6.7418', '6.7311', '6.7349');
INSERT INTO `tbl_usdcny` VALUES ('29', '2019-05-01', '6.7369', '6.7369', '6.7349', '6.7349');
INSERT INTO `tbl_usdcny` VALUES ('30', '2019-05-02', '6.7348', '6.7348', '6.7325', '6.7346');
INSERT INTO `tbl_usdcny` VALUES ('31', '2019-05-03', '6.7345', '6.7346', '6.7325', '6.7346');
INSERT INTO `tbl_usdcny` VALUES ('32', '2019-05-06', '6.7379', '6.801', '6.7379', '6.7645');
INSERT INTO `tbl_usdcny` VALUES ('33', '2019-05-07', '6.7639', '6.781', '6.7563', '6.7769');
INSERT INTO `tbl_usdcny` VALUES ('34', '2019-05-08', '6.7759', '6.785', '6.7656', '6.7828');
INSERT INTO `tbl_usdcny` VALUES ('35', '2019-05-09', '6.7823', '6.8307', '6.7823', '6.8274');
INSERT INTO `tbl_usdcny` VALUES ('36', '2019-05-10', '6.8269', '6.8278', '6.7951', '6.824');
INSERT INTO `tbl_usdcny` VALUES ('37', '2019-05-13', '6.824', '6.8824', '6.824', '6.8784');
INSERT INTO `tbl_usdcny` VALUES ('38', '2019-05-14', '6.8776', '6.8888', '6.8642', '6.876');
INSERT INTO `tbl_usdcny` VALUES ('39', '2019-05-15', '6.8754', '6.8812', '6.864', '6.8761');
INSERT INTO `tbl_usdcny` VALUES ('40', '2019-05-16', '6.8747', '6.8861', '6.8731', '6.8838');
INSERT INTO `tbl_usdcny` VALUES ('41', '2019-05-17', '6.8822', '6.9187', '6.8822', '6.9187');
INSERT INTO `tbl_usdcny` VALUES ('42', '2019-05-20', '6.9007', '6.9174', '6.9', '6.9123');
INSERT INTO `tbl_usdcny` VALUES ('43', '2019-05-21', '6.9114', '6.9179', '6.8972', '6.9023');
INSERT INTO `tbl_usdcny` VALUES ('44', '2019-05-22', '6.9008', '6.9159', '6.8962', '6.9064');
INSERT INTO `tbl_usdcny` VALUES ('45', '2019-05-23', '6.9057', '6.9211', '6.9057', '6.9105');
INSERT INTO `tbl_usdcny` VALUES ('49', '2019-05-24', '6.9075', '6.9178', '6.8952', '6.8989');
