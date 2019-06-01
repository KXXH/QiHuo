/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : xm10-qihuo

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-06-01 16:56:08
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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_quotation
-- ----------------------------
INSERT INTO `tbl_quotation` VALUES ('1', 's_sh000001', '上证指数', '2907.8705', '2.0651', '0.07', '0', 'nsh000001');
INSERT INTO `tbl_quotation` VALUES ('2', 's_sh000300', '沪深300', '3645.7756', '4.5923', '0.13', '0', 'nsh000300');
INSERT INTO `tbl_quotation` VALUES ('3', 's_sz399001', '深证成指', '8964.43', '21.085', '0.24', '0', 'nsz399001');
INSERT INTO `tbl_quotation` VALUES ('4', 'hkHSI', '恒生指数', '27060.52', '-54.36', '-0.2', '1', 'HSI');
INSERT INTO `tbl_quotation` VALUES ('5', 'b_NKY', '日经225指数', '20709', '-233.53', '-1.12', '6', 'NIXI');
INSERT INTO `tbl_quotation` VALUES ('6', 'b_DAX', '德国DAX指数', '11900.9697', '63.16', '0.53', '2', 'DAX');
INSERT INTO `tbl_quotation` VALUES ('7', 'b_CAC', '法国CAC40指数', '5248.91', '26.79', '0.51', '2', 'CAC');
INSERT INTO `tbl_quotation` VALUES ('8', 'b_UKX', '富时100指数', '7220.54', '35.24', '0.49', '2', 'FT100');
INSERT INTO `tbl_quotation` VALUES ('9', 'gb_dji', '道琼斯', '25169.8809', '43.47', '0.17', '3', 'dji');
INSERT INTO `tbl_quotation` VALUES ('10', 'gb_ixic', '纳斯达克', '7567.7162', '20.4067', '0.27', '3', 'ixic');
INSERT INTO `tbl_quotation` VALUES ('11', 'gb_inx', '标普指数\r\n', '2788.8601', '5.84', '0.21', '3', 'inx');
INSERT INTO `tbl_quotation` VALUES ('12', 'hf_GC', '纽约黄金', '1297.7', '0', '0.4101', '4', 'GC');
INSERT INTO `tbl_quotation` VALUES ('13', 'hf_CL', '纽约原油', '56', '0', '-1.0426', '4', 'CL');
INSERT INTO `tbl_quotation` VALUES ('14', 'hf_OIL', '布伦特原油', '64.61', '0', '-1.1', '4', 'OIL');
INSERT INTO `tbl_quotation` VALUES ('15', 'hf_SI', '纽约白银', '14.497', '0', '0.069', '4', 'SI');
INSERT INTO `tbl_quotation` VALUES ('16', 'fx_susdcny', '在岸人民币', '6.9091', '0', '0.09', '5', null);
INSERT INTO `tbl_quotation` VALUES ('17', 'fx_seurcny', '欧元兑人民币即期汇率', '7.6909', '0', '0.1', '5', null);
INSERT INTO `tbl_quotation` VALUES ('18', 'fx_scnyjpy', '人民币兑日元即期汇率', '15.8', '0', '-0.67', '5', null);
INSERT INTO `tbl_quotation` VALUES ('19', 'fx_sgbpcny', '英镑兑人民币即期汇率', '8.7106', '0', '0.07', '5', null);
INSERT INTO `tbl_quotation` VALUES ('20', 'fx_susdcnh', '离岸人民币（香港）', '6.9322', '0', '0.04', '5', null);
INSERT INTO `tbl_quotation` VALUES ('21', 's_sz399006', '创业板指', '1487.77', '2.53', '0.17', '0', 'nsz399006');
INSERT INTO `tbl_quotation` VALUES ('22', 'hf_S', '美国大豆', '884.25', '0', '-0.5343', '4', 'S');
INSERT INTO `tbl_quotation` VALUES ('23', 'hf_C', '美国玉米', '431.25', '0', '-1.1461', '4', 'C');
INSERT INTO `tbl_quotation` VALUES ('24', 'hf_XAU', '伦敦金', '1292.89', '0', '0.33', '4', 'XAU');
INSERT INTO `tbl_quotation` VALUES ('25', 'fx_skrwcny', '韩元兑人民币即期汇率', '0.005808', '0', '-0.09', '5', null);
INSERT INTO `tbl_quotation` VALUES ('26', 'fx_shkdcny', '港元兑人民币即期汇率', '0.8809', '0', '0.1', '5', null);
INSERT INTO `tbl_quotation` VALUES ('27', 'fx_scnytwd', '人民币兑台湾元即期汇率', '4.5817', '0', '0.02', '5', null);
INSERT INTO `tbl_quotation` VALUES ('28', 'DINIW', '美元指数期货', '98.1152', '0.0616', '0.0006', '7', 'DINIW');
INSERT INTO `tbl_quotation` VALUES ('29', 'btc_btcbitstamp', '比特币兑美元', '8156.8', '115.49', '0.014', '8', null);
INSERT INTO `tbl_quotation` VALUES ('30', 'btc_btcethusd', '以太坊美元', '248.39', '35.49', '0.125', '8', null);
INSERT INTO `tbl_quotation` VALUES ('31', 'btc_btcltcusd', '莱特币美元', '105.873', '11.185', '0.0956', '8', null);
INSERT INTO `tbl_quotation` VALUES ('32', 'gb_amzn', '亚马逊', '1816.32', '-2.87', '-0.16', '9', null);
