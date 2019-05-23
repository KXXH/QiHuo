/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-05-23 15:32:16
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_quotation
-- ----------------------------
INSERT INTO `tbl_quotation` VALUES ('1', 's_sh000001', '上证指数', '2891.7046', '-14.2642', '-0.49', '0');
INSERT INTO `tbl_quotation` VALUES ('2', 's_sh000300', '沪深300', '3649.3796', '-17.3966', '-0.47', '0');
INSERT INTO `tbl_quotation` VALUES ('3', 's_sz399001', '深证成指', '9041.22', '-46.298', '-0.51', '0');
INSERT INTO `tbl_quotation` VALUES ('4', 'hkHSI', '恒生指数', '27705.94', '48.7', '0.18', '1');
INSERT INTO `tbl_quotation` VALUES ('5', 'b_NKY', '日经225指数', '21281', '8.55', '0.04', '2');
INSERT INTO `tbl_quotation` VALUES ('6', 'b_DAX', '德国DAX指数', '12144.3701', '0.9', '0.01', '2');
INSERT INTO `tbl_quotation` VALUES ('7', 'b_CAC', '法国CAC40指数', '5372.41', '-13.05', '-0.24', '2');
INSERT INTO `tbl_quotation` VALUES ('8', 'b_UKX', '富时100指数', '7337.7202', '8.8', '0.12', '2');
INSERT INTO `tbl_quotation` VALUES ('9', 'gb_dji', '道琼斯', '25808.2402', '-69.09', '-0.27', '3');
INSERT INTO `tbl_quotation` VALUES ('10', 'gb_ixic', '纳斯达克', '7765.04', '-20.6842', '-0.27', '3');
INSERT INTO `tbl_quotation` VALUES ('11', 'gb_inx', '标普指数\r\n', '2858.1001', '-6.26', '-0.22', '3');
INSERT INTO `tbl_quotation` VALUES ('12', 'hf_GC', '纽约黄金', '1274.9', '0', '0.1335', '4');
INSERT INTO `tbl_quotation` VALUES ('13', 'hf_CL', '纽约原油', '61.82', '0', '-2.0751', '4');
INSERT INTO `tbl_quotation` VALUES ('14', 'hf_OIL', '布伦特原油', '71.11', '0', '-1.48', '4');
INSERT INTO `tbl_quotation` VALUES ('15', 'hf_SI', '纽约白银', '14.435', '0', '0.2082', '4');
INSERT INTO `tbl_quotation` VALUES ('16', 'fx_susdcny', '在岸人民币', '6.9064', '0', '0.05', '5');
INSERT INTO `tbl_quotation` VALUES ('17', 'fx_seurcny', '欧元兑人民币即期汇率', '7.7044', '0', '0.03', '5');
INSERT INTO `tbl_quotation` VALUES ('18', 'fx_scnyjpy', '人名币兑日元即期汇率', '15.95', '0', '-0.26', '5');
INSERT INTO `tbl_quotation` VALUES ('19', 'fx_sgbpcny', '英镑兑人民币即期汇率', '8.7327', '0', '-0.42', '5');
INSERT INTO `tbl_quotation` VALUES ('20', 'fx_susdcnh', '离岸人民币（香港）', '6.9346', '0', '0.05', '5');
