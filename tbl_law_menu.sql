/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-04-14 14:47:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_law_menu
-- ----------------------------
DROP TABLE IF EXISTS `tbl_law_menu`;
CREATE TABLE `tbl_law_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(255) NOT NULL DEFAULT 'normal',
  `href` varchar(255) DEFAULT NULL,
  `caption` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_law_menu
-- ----------------------------
INSERT INTO `tbl_law_menu` VALUES ('1', 'normal', '#about', '首页', '0');
INSERT INTO `tbl_law_menu` VALUES ('2', 'normal', '', '股票', '0');
INSERT INTO `tbl_law_menu` VALUES ('3', 'admin', '#services', '管理首页', '0');
INSERT INTO `tbl_law_menu` VALUES ('4', 'normal', 'bula', '基金', '0');
INSERT INTO `tbl_law_menu` VALUES ('5', 'normal', null, '期货', '0');
INSERT INTO `tbl_law_menu` VALUES ('6', 'normal', 'vyka ', '更多', '0');
INSERT INTO `tbl_law_menu` VALUES ('7', 'normal', null, 'A股', '2');
INSERT INTO `tbl_law_menu` VALUES ('8', 'normal', null, '港股', '2');
INSERT INTO `tbl_law_menu` VALUES ('9', 'normal', null, '美股', '2');
INSERT INTO `tbl_law_menu` VALUES ('10', 'admin', null, '交易管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('11', 'admin', null, '新闻管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('12', 'admin', null, '用户管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('13', 'admin', null, '统计数据', '0');
INSERT INTO `tbl_law_menu` VALUES ('14', 'normal', null, 'test', '4');
