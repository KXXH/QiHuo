/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : xm10-qihuo

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2019-06-07 17:41:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_permission
-- ----------------------------
DROP TABLE IF EXISTS `tbl_permission`;
CREATE TABLE `tbl_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(255) NOT NULL,
  `permissionCode` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_permission
-- ----------------------------
INSERT INTO `tbl_permission` VALUES ('1', 'login.manager.login', '31');
INSERT INTO `tbl_permission` VALUES ('2', 'login.manager.loginAction', '31');
INSERT INTO `tbl_permission` VALUES ('3', 'login.manager.logoutAction', '31');
INSERT INTO `tbl_permission` VALUES ('4', 'user.manager.addAction', '24');
INSERT INTO `tbl_permission` VALUES ('5', 'user.manager.statisticAction', '24');
INSERT INTO `tbl_permission` VALUES ('6', 'user.manager.deleteAction', '24');
INSERT INTO `tbl_permission` VALUES ('7', 'user.manager.checkEmailAvailable', '31');
INSERT INTO `tbl_permission` VALUES ('8', 'user.manager.checkUsernameAvailable', '31');
INSERT INTO `tbl_permission` VALUES ('9', 'user.manager.editAction', '24');
INSERT INTO `tbl_permission` VALUES ('10', 'user.manager.enableAccountAction', '31');
INSERT INTO `tbl_permission` VALUES ('11', 'user.manager.forgetPasswordAction', '28');
INSERT INTO `tbl_permission` VALUES ('12', 'user.manager.queryAction', '24');
INSERT INTO `tbl_permission` VALUES ('13', 'user.manager.resetPasswordAction', '28');
INSERT INTO `tbl_permission` VALUES ('14', 'user.manager.userRegisterAction', '31');
INSERT INTO `tbl_permission` VALUES ('19', 'permission.manager.editUserPermissionAction', '24');
INSERT INTO `tbl_permission` VALUES ('20', 'login.manager.getTokenAction', '16');
INSERT INTO `tbl_permission` VALUES ('21', 'login.manager.delTokenAction', '16');
INSERT INTO `tbl_permission` VALUES ('22', 'login.manager.getLoginRecordAction', '16');
INSERT INTO `tbl_permission` VALUES ('23', 'permission.manager.getPermissionAction', '16');
INSERT INTO `tbl_permission` VALUES ('24', 'permission.manager.editPermissionAction', '16');
INSERT INTO `tbl_permission` VALUES ('25', 'permission.manager.addPermissionAction', '16');
INSERT INTO `tbl_permission` VALUES ('26', 'permission.manager.delPermissionAction', '16');
INSERT INTO `tbl_permission` VALUES ('31', 'news.getNews.getNewsAction', '30');
INSERT INTO `tbl_permission` VALUES ('32', 'news.getNews.newsDeleteAction', '24');
INSERT INTO `tbl_permission` VALUES ('33', 'news.getNews.newsExportAction', '24');
INSERT INTO `tbl_permission` VALUES ('34', 'news.getNews.newsManagerAction', '24');
INSERT INTO `tbl_permission` VALUES ('35', 'news.getNews.newsModifyAction', '24');
INSERT INTO `tbl_permission` VALUES ('36', 'news.getNews.newsSearchAction', '24');
INSERT INTO `tbl_permission` VALUES ('37', 'news.getNews.newsStatisticsAction', '24');
INSERT INTO `tbl_permission` VALUES ('38', 'quotation.getXLdata.getDataToShowAction', '30');
INSERT INTO `tbl_permission` VALUES ('39', 'quotation.getXLdata.showUSDCNYdataAction', '28');
INSERT INTO `tbl_permission` VALUES ('40', 'quotation.getXLdata.showUSDCNYupdateAction', '28');
INSERT INTO `tbl_permission` VALUES ('41', 'business.change.buschangeAction', '24');
INSERT INTO `tbl_permission` VALUES ('42', 'business.delet.busdeleteAction', '24');
INSERT INTO `tbl_permission` VALUES ('43', 'business.expor.busdataToCSV', '24');
INSERT INTO `tbl_permission` VALUES ('44', 'business.query.buschaxunAction', '24');
INSERT INTO `tbl_permission` VALUES ('45', 'business.query.busquerylistAction', '24');
INSERT INTO `tbl_permission` VALUES ('46', 'business.statistic.busstatisticAction', '24');
INSERT INTO `tbl_permission` VALUES ('47', 'warehouse.change.changeAction', '28');
INSERT INTO `tbl_permission` VALUES ('48', 'warehouse.delet.deleteAction', '28');
INSERT INTO `tbl_permission` VALUES ('49', 'warehouse.expor.whdataToCSV', '28');
INSERT INTO `tbl_permission` VALUES ('50', 'warehouse.query.chaxunAction', '28');
INSERT INTO `tbl_permission` VALUES ('51', 'warehouse.query.querylistAction', '28');
INSERT INTO `tbl_permission` VALUES ('52', 'warehouse.register.registerAction', '28');
INSERT INTO `tbl_permission` VALUES ('53', 'warehouse.statistic.statisticAction', '28');
INSERT INTO `tbl_permission` VALUES ('54', 'transaction.bm.query.bmquerylistAction', '28');
INSERT INTO `tbl_permission` VALUES ('55', 'transaction.bm.register.bmregisterAction', '28');
INSERT INTO `tbl_permission` VALUES ('56', 'transaction.bm.qstatistic.bmstatisticAction', '28');
INSERT INTO `tbl_permission` VALUES ('57', 'transaction.bm.expor.bmdataToCSV', '28');
INSERT INTO `tbl_permission` VALUES ('58', 'transaction.bm.change.bmchangeAction', '28');
INSERT INTO `tbl_permission` VALUES ('59', 'index.query.indexquerylistAction', '30');
