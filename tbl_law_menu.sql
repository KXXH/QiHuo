/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : xm10-qihuo

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-05-26 19:26:36
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_law_menu
-- ----------------------------
INSERT INTO `tbl_law_menu` VALUES ('1', 'normal', 'index_beta.html', '首页', '0');
INSERT INTO `tbl_law_menu` VALUES ('2', 'normal', 'quotation.html', '行情', '0');
INSERT INTO `tbl_law_menu` VALUES ('3', 'admin', '#services', '管理首页', '0');
INSERT INTO `tbl_law_menu` VALUES ('6', 'normal', 'news.html', '财经新闻', '0');
INSERT INTO `tbl_law_menu` VALUES ('10', 'admin', 'bus_query_list.html', '交易管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('11', 'admin', 'news_manager.html', '新闻管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('12', 'admin', 'userManager.html', '用户管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('13', 'admin', null, '统计数据', '0');
INSERT INTO `tbl_law_menu` VALUES ('15', 'normal', 'wh_query_list.html', '持仓管理', '0');
INSERT INTO `tbl_law_menu` VALUES ('16', 'super_admin', 'loginManager.html', '登陆管理', '0');
