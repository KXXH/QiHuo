/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : xm10-qihuo

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-05-26 19:20:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_quotation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_quotation`;
CREATE TABLE `tbl_quotation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Quotation` double NOT NULL,
  `RiseOrFall` double DEFAULT NULL,
  `ROFper` double NOT NULL,
  `kind` int(11) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_quotation
-- ----------------------------
INSERT INTO `tbl_quotation` VALUES ('1', 's_sh000001', '上证指数', '2852.9948', '0.4795', '0.02', '0', 'nsh000001');
INSERT INTO `tbl_quotation` VALUES ('2', 's_sh000300', '沪深300', '3593.9139', '9.9493', '0.28', '0', 'nsh000300');
INSERT INTO `tbl_quotation` VALUES ('3', 's_sz399001', '深证成指', '8776.77', '-32.77', '-0.37', '0', 'nsz399001');
INSERT INTO `tbl_quotation` VALUES ('4', 'hkHSI', '恒生指数', '27353.93', '86.8', '0.32', '1', 'HSI');
INSERT INTO `tbl_quotation` VALUES ('5', 'b_NKY', '日经225指数', '21115', '-36.14', '-0.17', '6', 'NIXI');
INSERT INTO `tbl_quotation` VALUES ('6', 'b_DAX', '德国DAX指数', '12008.6299', '56.22', '0.47', '2', 'DAX');
INSERT INTO `tbl_quotation` VALUES ('7', 'b_CAC', '法国CAC40指数', '5316.51', '35.14', '0.67', '2', 'CAC');
INSERT INTO `tbl_quotation` VALUES ('8', 'b_UKX', '富时100指数', '7277.9302', '46.89', '0.65', '2', 'FT100');
INSERT INTO `tbl_quotation` VALUES ('9', 'gb_dji', '道琼斯', '25585.6895', '95.22', '0.37', '3', 'dji');
INSERT INTO `tbl_quotation` VALUES ('10', 'gb_ixic', '纳斯达克', '7637.0086', '8.7248', '0.11', '3', 'ixic');
INSERT INTO `tbl_quotation` VALUES ('11', 'gb_inx', '标普指数\r\n', '2826.0601', '3.82', '0.14', '3', 'inx');
INSERT INTO `tbl_quotation` VALUES ('12', 'hf_GC', '纽约黄金', '1284.3', '0', '-0.0856', '4', 'GC');
INSERT INTO `tbl_quotation` VALUES ('13', 'hf_CL', '纽约原油', '59.02', '0', '1.9168', '4', 'CL');
INSERT INTO `tbl_quotation` VALUES ('14', 'hf_OIL', '布伦特原油', '69.26', '0', '2.21', '4', 'OIL');
INSERT INTO `tbl_quotation` VALUES ('15', 'hf_SI', '纽约白银', '14.54', '0', '-0.4791', '4', 'SI');
INSERT INTO `tbl_quotation` VALUES ('16', 'fx_susdcny', '在岸人民币', '6.8989', '0', '0', '5', null);
INSERT INTO `tbl_quotation` VALUES ('17', 'fx_seurcny', '欧元兑人民币即期汇率', '7.7325', '0', '0.03', '5', null);
INSERT INTO `tbl_quotation` VALUES ('18', 'fx_scnyjpy', '人民币兑日元即期汇率', '15.868', '0', '-0.13', '5', null);
INSERT INTO `tbl_quotation` VALUES ('19', 'fx_sgbpcny', '英镑兑人民币即期汇率', '8.7737', '0', '0.26', '5', null);
INSERT INTO `tbl_quotation` VALUES ('20', 'fx_susdcnh', '离岸人民币（香港）', '6.918', '0', '-0.13', '5', null);
