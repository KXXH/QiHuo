/*
Navicat MySQL Data Transfer

Source Server         : 122.114.119.101_3306
Source Server Version : 50151
Source Host           : 122.114.119.101:3306
Source Database       : xm10-qihuo

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-05-28 19:35:41
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_law_menu
-- ----------------------------
INSERT INTO `tbl_law_menu` VALUES ('1', 'normal', '#about', '首页', '0');
INSERT INTO `tbl_law_menu` VALUES ('2', 'normal', 'quotation.html', '行情', '0');
INSERT INTO `tbl_law_menu` VALUES ('3', 'admin', '#services', '管理首页', '0');
INSERT INTO `tbl_law_menu` VALUES ('5', 'normal', 'news.html', '财经新闻', '0');
INSERT INTO `tbl_law_menu` VALUES ('10', 'admin', 'bus_query_list.html', '交易管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('11', 'admin', 'news_manager.html', '新闻管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('12', 'admin', 'userManager.html', '用户管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('13', 'admin', null, '统计数据', '0');
INSERT INTO `tbl_law_menu` VALUES ('14', 'normal', 'wh_query_list.html', '持仓管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('15', 'super_admin', 'userManager.html', '用户管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('16', 'super_admin', 'loginManager.html', '登录管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('17', 'super_admin', 'permissionManager.html', '权限管理', '0');
