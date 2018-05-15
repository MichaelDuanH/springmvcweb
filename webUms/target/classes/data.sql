-- 系统需要的sql语句
-- ----------------------------
-- Table structure for `t_module`
-- ----------------------------
DROP TABLE IF EXISTS `t_module`;
CREATE TABLE `t_module` (
  `MODULE_ID` bigint(18) unsigned NOT NULL COMMENT '功能模块ID',
  `PARENT_ID` bigint(18) unsigned DEFAULT NULL COMMENT '父功能模块ID，没有父模块时为空',
  `TEXT` varchar(120) DEFAULT NULL COMMENT '名称，默认用于展示的文字',
  `ACTION_URL` varchar(300) DEFAULT NULL COMMENT '功能请求链接，相对根路径的地址',
  PRIMARY KEY (`MODULE_ID`),
  KEY `FK_MODULE` (`PARENT_ID`),
  CONSTRAINT `t_module_ibfk_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `t_module` (`MODULE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能模块表';

-- ----------------------------
-- Records of t_module
-- ----------------------------
INSERT INTO `t_module` VALUES ('2', null, '菜单', '');
INSERT INTO `t_module` VALUES ('201', '2', '系统管理', null);
INSERT INTO `t_module` VALUES ('202', '2', '用户计提配置', null);
INSERT INTO `t_module` VALUES ('204', '201', '修改密码', 'main/password.jsp');
INSERT INTO `t_module` VALUES ('205', '201', '系统退出', null);
INSERT INTO `t_module` VALUES ('206', '202', '用户配置', 'main/user.jsp');
INSERT INTO `t_module` VALUES ('216', '2', '利息计算', 'main/interestRate.jsp');

-- 登录用户表

-- ----------------------------
-- Table structure for `t_user_lg`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_lg`;
CREATE TABLE `t_user_lg` (
  `pas` varchar(100) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `user_age` int(21) DEFAULT NULL,
  `dept_code` varchar(100) DEFAULT NULL,
  `user_code` varchar(100) DEFAULT NULL,
  `user_mail` varchar(100) DEFAULT NULL,
  `sex` char(2) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `create_tm` varchar(20) DEFAULT '',
  `id_no` varchar(100) DEFAULT NULL,
  `adress` varchar(100) DEFAULT NULL,
  `login_status` char(3) DEFAULT '0' COMMENT '登陆状态',
  `mac_adr` varchar(50) DEFAULT '""' COMMENT 'mac地址',
  `login_id` varchar(100) DEFAULT NULL,
  `log_tims` int(50) DEFAULT '0' COMMENT '系统被登陆的次数',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

INSERT INTO `t_user_lg` VALUES ('7339811F63CD10C1E6053A3E577F2D83', '1', 'admin', '19', '755Y', 'admin', 'qq@494974419', '1', '12365478954', '', '32432432154325425', 'sz', '0', 'F4-4D-30-5B-85-DC', '', '0');

-- 用户配置表
-- ----------------------------
-- Table structure for `t_user_og`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_og`;
CREATE TABLE `t_user_og` (
  `pas` varchar(100) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `user_age` int(21) DEFAULT NULL,
  `dept_code` varchar(100) DEFAULT NULL,
  `user_code` varchar(100) NOT NULL DEFAULT '',
  `user_mail` varchar(100) DEFAULT NULL,
  `sex` char(2) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `create_tm` varchar(20) NOT NULL DEFAULT '',
  `id_no` varchar(100) DEFAULT NULL,
  `adress` varchar(100) DEFAULT NULL,
  `PROCESS_TM` date DEFAULT NULL,
  `SYNC` char(2) DEFAULT '0',
  `is_commis` char(2) DEFAULT '0' COMMENT '0 否转计提  1 转计提',
  `person_type_code` char(2) DEFAULT '' COMMENT 'A 类型1  W 类型2',
  `commis_date` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1704 DEFAULT CHARSET=utf8mb4;