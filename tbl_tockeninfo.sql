/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-04-14 14:48:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_tockeninfo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tockeninfo`;
CREATE TABLE `tbl_tockeninfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tockenValue` varchar(255) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `UserId` int(11) NOT NULL,
  `TTL` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_tockeninfo
-- ----------------------------
INSERT INTO `tbl_tockeninfo` VALUES ('14', 'RlRUMTIzMTU1Mzg1OTg1MzkyNQ==', 'FTT123', '7', '2019-03-29 19:49:13');
INSERT INTO `tbl_tockeninfo` VALUES ('21', 'YWRtaW4xNTUzODc0NzAzNjU4', 'admin', '11', '2019-03-29 23:56:43');
INSERT INTO `tbl_tockeninfo` VALUES ('28', 'RlRUMTU1MzkyMjU2Njg2Mg==', 'FTT', '8', '2019-03-30 13:14:26');
