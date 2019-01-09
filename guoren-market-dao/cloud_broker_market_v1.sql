# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 192.168.168.120 (MySQL 5.7.22-log)
# Database: cloud_broker_market
# Generation Time: 2018-07-04 02:13:31 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table admin_role
# ------------------------------------------------------------

CREATE TABLE `admin_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `admin_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `role_id` int(11) unsigned NOT NULL COMMENT '角色id',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_admin_id` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理用户角色关联表';



# Dump of table administrators
# ------------------------------------------------------------

CREATE TABLE `administrators` (
  `admin_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `op_name` char(32) NOT NULL COMMENT '姓名',
  `mobile` char(11) NOT NULL COMMENT '手机',
  `login_password` char(125) NOT NULL DEFAULT '' COMMENT '登陆密码',
  `locked` enum('LOCK','UNLOCK') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'UNLOCK' COMMENT '锁定状态，0-锁定，1-未锁定',
  `role` char(32) NOT NULL COMMENT '角色',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `createip` char(15) DEFAULT '0.0.0.0' COMMENT '创建ip',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updateip` char(15) DEFAULT '0.0.0.0' COMMENT '更新ip',
  `create_admin_id` int(11) DEFAULT NULL COMMENT '创建此管理员的管理员id',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员用户表';



# Dump of table begining_balance
# ------------------------------------------------------------

CREATE TABLE `begining_balance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `asset_code` varchar(40) NOT NULL COMMENT '资产代码',
  `amount_available` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '可用金额',
  `amount_lock` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '冻结金额',
  `amount_total` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '资产总额',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='平台期初余额：按币种';



# Dump of table config_asset
# ------------------------------------------------------------

CREATE TABLE `config_asset` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `asset_code` varchar(40) NOT NULL COMMENT '资产代码',
  `currency_type` enum('COIN','CASH') NOT NULL COMMENT '资产种类',
  `status` enum('INIT','LISTED','DELISTED') NOT NULL COMMENT '处理状态：INIT初始，LISTED上市，DELISTED退市',
  `name` varchar(64) NOT NULL COMMENT '资产名称',
  `supply_amount` decimal(35,20) unsigned NOT NULL COMMENT '当前的供应量',
  `total_amount` decimal(35,20) unsigned NOT NULL COMMENT '总的供应量',
  `min_precision` int(11) NOT NULL COMMENT '最小精度',
  `description` varchar(256) NOT NULL COMMENT '描述',
  `web_url` varchar(256) NOT NULL COMMENT '官方url',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_asset_code` (`asset_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产基本信息表';



# Dump of table config_asset_profile
# ------------------------------------------------------------

CREATE TABLE `config_asset_profile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `asset_code` varchar(40) NOT NULL COMMENT '资产代码',
  `profile_key` varchar(64) NOT NULL COMMENT '配置关键字',
  `profile_value` varchar(10240) NOT NULL COMMENT '配置数值',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_asset_code_profile_key` (`asset_code`,`profile_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产配置参数:url,消息队列';



# Dump of table config_email
# ------------------------------------------------------------

CREATE TABLE `config_email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail_username` varchar(45) NOT NULL COMMENT '邮箱登陆用户名',
  `mail_password` varchar(45) NOT NULL COMMENT '邮箱登陆密码',
  `mail_host` varchar(64) NOT NULL,
  `mail_subject` varchar(64) NOT NULL,
  `send_count` int(11) NOT NULL,
  `status` enum('LISTED','DELISTED') NOT NULL COMMENT '状态',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`mail_username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='给用户发送邮件的邮箱配置表';



# Dump of table config_symbol
# ------------------------------------------------------------

CREATE TABLE `config_symbol` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `symbol` varchar(32) NOT NULL COMMENT '交易对',
  `asset_code1` varchar(40) NOT NULL COMMENT '资产类型1',
  `asset_code2` varchar(40) NOT NULL COMMENT '资产类型2',
  `status` enum('INIT','LISTED','DELISTED') NOT NULL COMMENT '处理状态：INIT初始，LISTED上市，DELISTED退市',
  `title` varchar(64) NOT NULL COMMENT '交易对标题',
  `name` varchar(64) NOT NULL COMMENT '交易对名称',
  `description` varchar(256) NOT NULL COMMENT '描述',
  `min_precision1` int(11) NOT NULL COMMENT '资产1的最小精度',
  `min_precision2` int(11) NOT NULL COMMENT '资产2的最小精度',
  `min_amount1` decimal(35,20) unsigned NOT NULL COMMENT '资产1的最小数量',
  `min_amount2` decimal(35,20) unsigned NOT NULL COMMENT '资产2的最小数量',
  `max_amount1` decimal(35,20) unsigned NOT NULL COMMENT '资产1的最大数量',
  `max_amount2` decimal(35,20) unsigned NOT NULL COMMENT '资产2的最大数量',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_symbol` (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易对';



# Dump of table config_symbol_profile
# ------------------------------------------------------------

CREATE TABLE `config_symbol_profile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `symbol` varchar(32) NOT NULL COMMENT '交易对',
  `profile_key` varchar(64) NOT NULL COMMENT '配置关键字',
  `profile_value` varchar(10240) NOT NULL COMMENT '配置数值',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_symbol_profile_key_profile_index` (`symbol`,`profile_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易对配置参数:url,消息队列';



# Dump of table daily_trade_info
# ------------------------------------------------------------

CREATE TABLE `daily_trade_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `from_date` timestamp NULL DEFAULT NULL,
  `trade_number` decimal(35,20) DEFAULT NULL,
  `last_trade_id` int(11) DEFAULT NULL,
  `to_date` timestamp NULL DEFAULT NULL,
  `rank_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table deposit_coin_order_user
# ------------------------------------------------------------

CREATE TABLE `deposit_coin_order_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `broker_id` int(11) unsigned NOT NULL COMMENT '券商id',
  `account_id` int(11) unsigned NOT NULL COMMENT '账户id',
  `account` varchar(40) NOT NULL COMMENT '用户账号',
  `asset_code` varchar(40) NOT NULL COMMENT '资产类型',
  `channel_deposit_id` int(11) unsigned DEFAULT NULL COMMENT '充值通道id',
  `coin_address` varchar(70) NOT NULL COMMENT '数字钱包地址',
  `outer_order_no` char(40) NOT NULL COMMENT '外部订单号',
  `inner_order_no` char(40) NOT NULL COMMENT '内部订单号',
  `number` decimal(35,20) unsigned NOT NULL DEFAULT '0.00000000000000000000' COMMENT '总果仁数：实际果仁数+手续费',
  `real_number` decimal(35,20) unsigned DEFAULT '0.00000000000000000000' COMMENT '实际果仁数量',
  `dest_address_type` enum('INNER_ADDRESS','OUTER_ADDRESS') NOT NULL COMMENT '目标系统(内部地址,私钥由自己控制的地址,外部地址,私钥不由自己控制的地址,转出时指转出到系统，转入时指从何系统转入)',
  `msg` char(255) DEFAULT '' COMMENT '消息',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '充值手续费',
  `admin_id` int(11) unsigned DEFAULT NULL COMMENT '管理员帐号',
  `asset_status` enum('CONFIRM','SUCCESS','FAILURE') DEFAULT NULL COMMENT '资产状体：已经入帐，待确认，已经确认',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_outer_order_no` (`outer_order_no`),
  UNIQUE KEY `idx_inner_order_no` (`inner_order_no`),
  KEY `idx_uid_coin_address` (`uid`,`coin_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='普通用户数字币充值订单表';



# Dump of table deposit_currency_order_offline
# ------------------------------------------------------------

CREATE TABLE `deposit_currency_order_offline` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `account_id` int(11) unsigned NOT NULL COMMENT '账户id',
  `asset_code` varchar(40) NOT NULL COMMENT '资产类型',
  `inner_order_no` char(32) NOT NULL COMMENT '内部订单号',
  `money` decimal(35,20) unsigned NOT NULL DEFAULT '0.00000000000000000000' COMMENT '总金额：实际支付+手续费',
  `pay` decimal(35,20) unsigned DEFAULT '0.00000000000000000000' COMMENT '实付金额',
  `fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '手续费',
  `status` enum('WAIT','SUCCESS','CANCEL','PROCESSING','FAILURE') DEFAULT NULL COMMENT '状态',
  `pay_mode` varchar(32) DEFAULT NULL COMMENT '支付方式:("OFFLINE","SUPERPAY","QDBPAY","CIBPAY","OKPAY","ALIPAY")',
  `admin` int(11) unsigned DEFAULT '0' COMMENT '管理员',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_status_pay_mode` (`status`,`pay_mode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='大额充值:券商现金资产充值订单表：大额充值，不走API';



# Dump of table deposit_currency_order_user
# ------------------------------------------------------------

CREATE TABLE `deposit_currency_order_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `broker_id` int(11) unsigned NOT NULL COMMENT '券商id',
  `account_id` int(11) unsigned NOT NULL COMMENT '账户id',
  `account` varchar(40) NOT NULL COMMENT '用户账号',
  `asset_code` varchar(40) NOT NULL COMMENT '资产类型',
  `admin_id` int(11) unsigned DEFAULT '0' COMMENT '管理员',
  `money` decimal(35,20) unsigned NOT NULL DEFAULT '0.00000000000000000000' COMMENT '总金额：实际支付+手续费',
  `pay` decimal(35,20) unsigned DEFAULT '0.00000000000000000000' COMMENT '实付金额',
  `fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '手续费',
  `txid` char(32) DEFAULT '' COMMENT '交易号',
  `acnumber` varchar(40) DEFAULT '' COMMENT '账号',
  `bank` varchar(100) DEFAULT '' COMMENT '银行',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `third_account` varchar(50) DEFAULT '' COMMENT '三方收款帐号',
  `third_account_name` varchar(50) DEFAULT '' COMMENT '三方收款名称',
  `third_account_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '三方收款二维码',
  `status` enum('WAIT','UNKNOWN','SUCCESS','CANCEL','PROCESSING','FAILURE') DEFAULT 'WAIT' COMMENT '状态',
  `pay_mode` varchar(32) DEFAULT NULL COMMENT '支付方式:("OFFLINE","SUPERPAY","QDBPAY","CIBPAY","OKPAY","ALIPAY")',
  `msg` char(150) DEFAULT '' COMMENT '消息',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_txid` (`txid`),
  KEY `idx_uid` (`uid`),
  KEY `idx_status_pay_mode` (`status`,`pay_mode`) COMMENT '在线支付',
  KEY `idx_bank_acnumber` (`bank`,`acnumber`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='普通用户现金充值订单表';



# Dump of table deposit_match_bank_order_user
# ------------------------------------------------------------

CREATE TABLE `deposit_match_bank_order_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_number` varchar(40) DEFAULT '' COMMENT '流水号',
  `money` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '转入金额',
  `name` varchar(50) DEFAULT NULL COMMENT '转入姓名',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '银行名称',
  `account_no` varchar(40) DEFAULT '' COMMENT '银行卡号',
  `postscript` varchar(40) DEFAULT NULL COMMENT '转账附言',
  `status` enum('RELEVANCE','UNRELEVANCE','CONFIRM') DEFAULT 'UNRELEVANCE' COMMENT '关联状态',
  `source` varchar(40) DEFAULT NULL COMMENT '来源',
  `editer_admin_id` int(11) DEFAULT NULL COMMENT '编辑人管理员id',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '转入时间',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `insert_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录插入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_serial_number` (`serial_number`),
  KEY `idx_insert_date` (`insert_date`),
  KEY `idx_account_no_status` (`account_no`,`status`,`create_date`,`money`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='普通用户现金充值：从银行原始单导入的银行订单：银行对账单（支付宝和银行充值账单，与transfer_cny的充值订单匹配）';



# Dump of table deposit_match_currency_order_user
# ------------------------------------------------------------

CREATE TABLE `deposit_match_currency_order_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `recharge_name` varchar(40) DEFAULT NULL COMMENT '充值用户名',
  `recharge_bank` varchar(40) DEFAULT NULL COMMENT '充值银行',
  `recharge_account_no` varchar(40) DEFAULT NULL COMMENT '充值银行帐号',
  `recharge_amount` decimal(35,20) DEFAULT NULL COMMENT '充值金额',
  `recharge_time` timestamp NULL DEFAULT NULL COMMENT '充值时间',
  `recharge_remark` varchar(40) DEFAULT NULL COMMENT '充值备注',
  `order_name` varchar(40) DEFAULT NULL COMMENT '订单姓名',
  `order_bank` varchar(40) DEFAULT NULL COMMENT '订单银行',
  `order_account_no` varchar(40) DEFAULT NULL COMMENT '订单帐号',
  `order_amount` decimal(35,20) DEFAULT NULL COMMENT '订单金额',
  `order_time` timestamp NULL DEFAULT NULL COMMENT '订单时间',
  `order_remark` varchar(40) DEFAULT NULL COMMENT '订单备注',
  `creater_user` int(11) DEFAULT NULL COMMENT '创建人',
  `link_uid` int(11) DEFAULT NULL COMMENT '关联人',
  `confirm_admin_id` int(11) DEFAULT NULL COMMENT '确认管理员id',
  `status` enum('CONFIRM','UNCONFIRM','INVALID') DEFAULT 'UNCONFIRM' COMMENT '状态',
  `order_serial_number` varchar(40) DEFAULT NULL COMMENT '订单序列号',
  `bank_serial_number` varchar(40) DEFAULT NULL COMMENT '银行序列号',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_link_uid` (`link_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='普通用户现金充值：本地订单与银行订单匹配表：充值（转入)匹配表(包括超级代付和支付宝的)，匹配结果';



# Dump of table email_log
# ------------------------------------------------------------

CREATE TABLE `email_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `msg_id` varchar(32) NOT NULL COMMENT '短信id',
  `service_code` enum('MARKET','NOTICE','REPORT_DAILY','VERIFY_CODE') DEFAULT NULL,
  `service_provider` varchar(32) NOT NULL COMMENT '邮件服务提供商',
  `sys_code` enum('GP_MARKET','QUICKDAX','GP_BAO') DEFAULT NULL,
  `email` char(128) NOT NULL COMMENT '邮箱地址',
  `msg_content` text,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件发送日志';



# Dump of table finance
# ------------------------------------------------------------

CREATE TABLE `finance` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `broker_id` int(11) unsigned NOT NULL COMMENT '券商id',
  `account_no` varchar(40) NOT NULL COMMENT '账户编号',
  `account_kind` varchar(40) NOT NULL COMMENT '账户类型：主，子帐户，借贷帐户等',
  `asset_code` varchar(40) NOT NULL COMMENT '资产代码',
  `amount_available` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '可用金额',
  `amount_lock` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '冻结金额',
  `amount_loan` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '借贷金额',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account_no` (`account_no`),
  UNIQUE KEY `uid_asset_code` (`uid`,`asset_code`),
  UNIQUE KEY `uid` (`uid`,`asset_code`),
  UNIQUE KEY `uid_2` (`uid`,`asset_code`),
  KEY `idx_broker_id_asset_code` (`broker_id`,`asset_code`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户资产表';



# Dump of table finance_detail
# ------------------------------------------------------------

CREATE TABLE `finance_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `asset_code` varchar(40) NOT NULL COMMENT '`currency资产类型',
  `request_no` char(40) NOT NULL DEFAULT '' COMMENT '请求号',
  `business_subject` varchar(40) NOT NULL COMMENT '业务类型，决定out_tx_no来自那一张表',
  `amount_available` decimal(35,20) NOT NULL COMMENT '可用变化：正数表示增加，负数表示减少',
  `amount_lock` decimal(35,20) NOT NULL COMMENT '冻结变化：正数表示增加，负数表示减少',
  `amount_loan` decimal(35,20) NOT NULL COMMENT '借贷变化：正数表示增加，负数表示减少',
  `balance_old_available` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '变化前可用金额',
  `balance_old_lock` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '变化前冻结金额',
  `balance_old_loan` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '变化前借贷金额',
  `balance_new_available` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '变化后可用金额',
  `balance_new_lock` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '变化后冻结金额',
  `balance_new_loan` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '变化后借贷金额',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `asset_change_type` tinyint(4) DEFAULT '0' COMMENT '0:总资产未变化1:总资产发生变化',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_request_no_business_subject` (`request_no`,`business_subject`),
  KEY `idx_uid` (`uid`),
  KEY `uid` (`uid`,`asset_code`,`asset_change_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户帐单信息，用来前端显示用户资产变化：普通每笔订单撮合完后生成记录（完全成交和撤单），所有多资金变化';



# Dump of table finance_stats
# ------------------------------------------------------------

CREATE TABLE `finance_stats` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `asset_code` varchar(40) NOT NULL COMMENT '资产类型',
  `broker_id` int(11) unsigned NOT NULL COMMENT '券商id',
  `business_key` varchar(64) NOT NULL COMMENT '业务种类:("TRANSFER_FEE","BALANCE","TRANSFER_IN","TRANSFER_OUT")',
  `finance_date` int(11) NOT NULL DEFAULT '0' COMMENT '财务日期',
  `date_unit` enum('YEAR','MONTH','WEEK','DAY') NOT NULL DEFAULT 'YEAR' COMMENT '时间单位',
  `opening_balance` decimal(35,20) NOT NULL COMMENT '期初余额',
  `amount` decimal(35,20) NOT NULL COMMENT '本期发生额',
  `closing_balance` decimal(35,20) DEFAULT NULL COMMENT '期末余额',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_broker_id_business_key` (`broker_id`,`business_key`,`finance_date`,`date_unit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户财务统计报表';



# Dump of table invite_activity_config
# ------------------------------------------------------------

CREATE TABLE `invite_activity_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `activity_name` varchar(40) NOT NULL COMMENT '邀请活动名称',
  `status` enum('ON','OFF') NOT NULL COMMENT '开启 关闭',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_activity_name` (`activity_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邀请活动配置表';



# Dump of table invite_activity_reward_config
# ------------------------------------------------------------

CREATE TABLE `invite_activity_reward_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `activity_id` int(11) NOT NULL,
  `reward_asset_code` varchar(40) NOT NULL,
  `amount` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '奖励金额',
  `invite_type` enum('INVITER','INVITED') NOT NULL COMMENT '邀请人 被邀请人',
  `status` enum('ON','OFF') NOT NULL COMMENT '开启 关闭',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_activity_id_reward_asset_code_invite_type` (`activity_id`,`reward_asset_code`,`invite_type`) USING BTREE,
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_invite_type` (`invite_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邀请活动礼包奖励配置表';



# Dump of table invite_complete_info
# ------------------------------------------------------------

CREATE TABLE `invite_complete_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) NOT NULL COMMENT '用户uid',
  `invite_uid` int(11) NOT NULL COMMENT '邀请人uid',
  `invite_email` varchar(96) NOT NULL COMMENT '邀请人email',
  `activity_id` int(11) NOT NULL COMMENT '邀请活动id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_invite_uid` (`invite_uid`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户成功邀请信息表';



# Dump of table invite_user_info
# ------------------------------------------------------------

CREATE TABLE `invite_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) NOT NULL COMMENT '用户uid',
  `invite_code` varchar(32) NOT NULL COMMENT '用户邀请码',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid` (`uid`),
  UNIQUE KEY `un_invite_code` (`invite_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户邀请码信息表';



# Dump of table invite_user_reward_record
# ------------------------------------------------------------

CREATE TABLE `invite_user_reward_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `invite_type` enum('INVITER','INVITED') NOT NULL COMMENT '邀请人,被邀请人',
  `activity_id` int(11) NOT NULL COMMENT '活动id',
  `reward_asset_code` varchar(40) NOT NULL COMMENT '奖励币种',
  `reward_amount` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '奖励数量',
  `request_no` varchar(45) NOT NULL COMMENT '交易流水号',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_invite_type` (`invite_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息奖励流水表';



# Dump of table manager_google_code_config
# ------------------------------------------------------------

CREATE TABLE `manager_google_code_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) NOT NULL COMMENT '管理员id',
  `secret_code` varchar(32) NOT NULL COMMENT '谷歌秘钥',
  `del_flag` enum('TRUE','FALSE') NOT NULL COMMENT '删除标识',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_admin_id` (`admin_id`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员谷歌验证码配置表';



# Dump of table manager_oper_log
# ------------------------------------------------------------

CREATE TABLE `manager_oper_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) NOT NULL COMMENT '管理员id',
  `ip_address` varchar(32) NOT NULL COMMENT 'ip地址',
  `ip_country` varchar(45) NOT NULL COMMENT 'ip国家所在地',
  `ip_city` varchar(45) NOT NULL COMMENT 'ip城市所在地',
  `oper_type` varchar(32) NOT NULL COMMENT '类型',
  `remark` varchar(45) DEFAULT '' COMMENT '其他备注',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_admin_id` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员操作日志表';



# Dump of table manager_password_oper_record
# ------------------------------------------------------------

CREATE TABLE `manager_password_oper_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_id` int(11) NOT NULL COMMENT '管理员ID',
  `modify_flag` enum('TRUE','FALSE') NOT NULL COMMENT '是否修改密码',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_id_UNIQUE` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员密码操作记录表';



# Dump of table menu
# ------------------------------------------------------------

CREATE TABLE `menu` (
  `menu_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `menu_name` varchar(64) NOT NULL COMMENT '菜单名称',
  `menu_module` varchar(45) NOT NULL COMMENT '菜单module名',
  `parent_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '父级菜单',
  `level` int(11) unsigned NOT NULL COMMENT '级别',
  `uri` varchar(60) DEFAULT '' COMMENT '接口uri',
  PRIMARY KEY (`menu_id`),
  KEY `level_Index` (`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';



# Dump of table menu_interface
# ------------------------------------------------------------

CREATE TABLE `menu_interface` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  `interface_name` varchar(32) NOT NULL COMMENT '接口名',
  `uri` varchar(128) DEFAULT '' COMMENT '接口uri',
  PRIMARY KEY (`id`),
  KEY `menu_Index` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单接口对应表';



# Dump of table menu_role
# ------------------------------------------------------------

CREATE TABLE `menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NOT NULL COMMENT '关联menu表id',
  `role_id` int(11) NOT NULL COMMENT '关联role表id',
  `status` tinyint(2) NOT NULL COMMENT '0 不可用 1 可用',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `menu_id_Index` (`menu_id`) USING BTREE,
  KEY `role_id_Index` (`role_id`) USING BTREE,
  KEY `status_Index` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单_角色表';



# Dump of table mq_deads_message
# ------------------------------------------------------------

CREATE TABLE `mq_deads_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(1000) DEFAULT NULL COMMENT '消息',
  `exchange` varchar(255) DEFAULT NULL COMMENT 'exchange名称',
  `error_desc` text COMMENT '出错的错误信息，一般是exception的stack',
  `router` varchar(255) DEFAULT NULL COMMENT '消息队列的key',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息队列：死信消息';



# Dump of table mq_produce_log_0
# ------------------------------------------------------------

CREATE TABLE `mq_produce_log_0` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `exchange_name` varchar(50) DEFAULT NULL COMMENT '消息队列的exchange',
  `router_key` varchar(100) DEFAULT NULL COMMENT '消息队列的key',
  `message` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `status` enum('WAIT','FAIL','COMMITED','ROLLBACK') DEFAULT NULL COMMENT '消息状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息队列：消息队列日志表';



# Dump of table mq_produce_log_1
# ------------------------------------------------------------

CREATE TABLE `mq_produce_log_1` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `exchange_name` varchar(50) DEFAULT NULL COMMENT '消息队列的exchange',
  `router_key` varchar(100) DEFAULT NULL COMMENT '消息队列的key',
  `message` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `status` enum('WAIT','FAIL','COMMITED','ROLLBACK') DEFAULT NULL COMMENT '消息状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息队列：消息队列日志表';



# Dump of table mq_produce_log_2
# ------------------------------------------------------------

CREATE TABLE `mq_produce_log_2` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `exchange_name` varchar(50) DEFAULT NULL COMMENT '消息队列的exchange',
  `router_key` varchar(100) DEFAULT NULL COMMENT '消息队列的key',
  `message` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `status` enum('WAIT','FAIL','COMMITED','ROLLBACK') DEFAULT NULL COMMENT '消息状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息队列：消息队列日志表';



# Dump of table mq_produce_log_3
# ------------------------------------------------------------

CREATE TABLE `mq_produce_log_3` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `exchange_name` varchar(50) DEFAULT NULL COMMENT '消息队列的exchange',
  `router_key` varchar(100) DEFAULT NULL COMMENT '消息队列的key',
  `message` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `status` enum('WAIT','FAIL','COMMITED','ROLLBACK') DEFAULT NULL COMMENT '消息状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息队列：消息队列日志表';



# Dump of table plat_asset_proess
# ------------------------------------------------------------

CREATE TABLE `plat_asset_proess` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `asset_code` varchar(40) NOT NULL COMMENT '资产代码',
  `begin_balance` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '前期余额',
  `deposit_balance` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '存入金额',
  `withdraw_total` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '提现:申请',
  `withdraw_unknow` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '提现:未确认',
  `withdraw_refuse` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '提现:失败',
  `withdraw_success` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '提现:成功',
  `withdraw_fee` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '提现手续费',
  `broken_asset_balance` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '管理员调整资产',
  `trade_fee` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '交易手续费',
  `other` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '其他:其他',
  `end_balance` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '期末余额',
  `cul_balance` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '计算余额',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(4) NOT NULL COMMENT '0 不可用 1 可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='平台过程总表：按币种';



# Dump of table public_notice
# ------------------------------------------------------------

CREATE TABLE `public_notice` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '发布者id',
  `nickname` varchar(128) DEFAULT NULL COMMENT '发布者名称',
  `top_status` enum('YES','NO') DEFAULT 'NO' COMMENT '是否置顶',
  `top_time` time(6) NOT NULL DEFAULT '00:00:00.000000' COMMENT '最后置顶时间，每次置顶操作更新这个时间',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `title` varchar(128) DEFAULT NULL COMMENT '公告标题',
  `content` varchar(10240) DEFAULT NULL COMMENT '公告内容',
  `status` enum('VALID','INVALID') DEFAULT 'VALID',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`,`status`,`top_status`,`top_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='test';



# Dump of table recharge_order
# ------------------------------------------------------------

CREATE TABLE `recharge_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL,
  `asset` varchar(10) NOT NULL COMMENT '资产名称:如 USC',
  `pay_channel` varchar(10) NOT NULL COMMENT '渠道简称 比如 SZF',
  `pay_type` int(11) NOT NULL COMMENT '支付类型: 0移动 1联通 2电信',
  `status` int(11) DEFAULT NULL COMMENT '1 创建,2 处理中,3 成功 4 失败',
  `pay_money` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '实际支付金额 单位为分',
  `fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '费用 单位为分',
  `asset_amount` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '充值数量 个数',
  `extra_info` varchar(30) DEFAULT NULL,
  `request_no` varchar(30) DEFAULT NULL COMMENT '资产数据关联Id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_userId` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值订单';



# Dump of table role
# ------------------------------------------------------------

CREATE TABLE `role` (
  `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `instruction` varchar(64) DEFAULT NULL COMMENT '描述',
  `role_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '角色状态, 1启用, 0禁用',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员用户角色';



# Dump of table sms_log
# ------------------------------------------------------------

CREATE TABLE `sms_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `msg_id` varchar(32) NOT NULL COMMENT '短信id',
  `service_code` enum('MARKET','NOTICE','VERIFY_CODE') NOT NULL COMMENT '业务代码',
  `service_provider` enum('WEIWANG','BAIWU','LINGKAI') NOT NULL COMMENT '短信服务提供商',
  `sys_code` enum('GP_MARKET','GP_BAO') NOT NULL COMMENT '系统id',
  `mobile` char(15) NOT NULL COMMENT '手机号',
  `msg_content` text NOT NULL COMMENT '短信内容',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;



# Dump of table token_order
# ------------------------------------------------------------

CREATE TABLE `token_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `token_intention_id` int(11) DEFAULT '0' COMMENT '意向单id',
  `order_no` varchar(40) DEFAULT NULL COMMENT '订单号',
  `price` decimal(20,2) NOT NULL COMMENT '价格',
  `asset_name` varchar(20) NOT NULL DEFAULT '' COMMENT '虚拟币名称',
  `num` decimal(15,0) NOT NULL COMMENT '个数',
  `buy_uid` int(11) NOT NULL DEFAULT '0' COMMENT '买家uid',
  `sell_uid` int(11) NOT NULL COMMENT '卖家uid',
  `state` enum('WAITING','PAID','SHIPPED','COMPLETE','CANCEL','REFUND') DEFAULT 'WAITING' COMMENT '订单状态(WAITING:代付款，PAID：已付款，SHIPPED：已发货，COMPLETET：已完成，CANNCEL：已取消，REFUND：已退款)',
  `password` char(32) NOT NULL DEFAULT '' COMMENT '秘钥',
  `buy_order_request_no` varchar(40) NOT NULL DEFAULT '' COMMENT '买方扣款订单号',
  `money` decimal(20,5) unsigned NOT NULL DEFAULT '0.00000' COMMENT '买家支付金额',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '成交时间',
  `sell_fee` decimal(20,5) DEFAULT '0.00000' COMMENT '卖方手续费',
  `sell_order_request_no` varchar(40) NOT NULL DEFAULT '' COMMENT '卖方加款订单号',
  `bund_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '绑定时间',
  `pay` decimal(20,5) NOT NULL DEFAULT '0.00000' COMMENT '卖家支付金额',
  `from_address` varchar(125) NOT NULL DEFAULT '' COMMENT '卖家地址',
  `to_address` varchar(125) DEFAULT NULL COMMENT '买家地址',
  `trade_asset` varchar(20) DEFAULT NULL COMMENT '交易资产',
  PRIMARY KEY (`id`),
  KEY `buy_uid_sell_uid_state` (`buy_uid`,`sell_uid`,`state`) USING BTREE COMMENT 'buy_uid&sell_uid&state联合索引',
  KEY `state` (`state`,`bund_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table token_order_completion_coins
# ------------------------------------------------------------

CREATE TABLE `token_order_completion_coins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_name` varchar(20) NOT NULL COMMENT '交易币名称',
  `completion_num` decimal(20,0) DEFAULT NULL COMMENT '完成交易总额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `assetName_completionNum` (`asset_name`) USING BTREE,
  KEY `completionNum` (`completion_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table token_order_completion_count
# ------------------------------------------------------------

CREATE TABLE `token_order_completion_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户的uid',
  `sell_count` decimal(35,20) DEFAULT NULL COMMENT '卖出总笔数',
  `buy_count` decimal(35,20) DEFAULT NULL COMMENT '买入总笔数',
  `total_count` decimal(35,20) DEFAULT NULL COMMENT '交易成功总笔数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_totalCount` (`uid`) USING BTREE,
  KEY `totalCount` (`total_count`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table token_order_intention
# ------------------------------------------------------------

CREATE TABLE `token_order_intention` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户uid',
  `asset_name` varchar(20) DEFAULT NULL COMMENT '资产名称',
  `sell_num` decimal(20,0) DEFAULT NULL COMMENT '卖出数量',
  `success_trade_num` int(11) DEFAULT NULL COMMENT '成功交易次数',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '成交时间',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq',
  `wechat` varchar(30) DEFAULT NULL COMMENT '微信',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `state` enum('PROCESSING','CANCEL','COMPLETE') DEFAULT NULL COMMENT '意向单状态',
  `price` decimal(20,5) NOT NULL DEFAULT '0.00000',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table token_order_log
# ------------------------------------------------------------

CREATE TABLE `token_order_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token_order_id` int(20) NOT NULL DEFAULT '0' COMMENT 'token订单的id',
  `operation_type` enum('BUY','SELL','OTHER') NOT NULL COMMENT '订单角色',
  `create_time` datetime NOT NULL COMMENT '改变的起始时间',
  `updatetime` datetime NOT NULL COMMENT '改变结束时间',
  `change_state_from` varchar(30) NOT NULL COMMENT '起始的状态',
  `change_state_to` varchar(30) NOT NULL COMMENT '结束状态',
  `operation_uid` int(20) NOT NULL COMMENT '改变人的uid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table total_trade_info
# ------------------------------------------------------------

CREATE TABLE `total_trade_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `symbol` int(11) DEFAULT NULL,
  `rank_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table trade_match_result
# ------------------------------------------------------------

CREATE TABLE `trade_match_result` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `symbol` varchar(40) NOT NULL COMMENT '交易对',
  `buy_inner_order_no` char(32) NOT NULL DEFAULT '' COMMENT '买入委托',
  `sell_inner_order_no` char(32) NOT NULL DEFAULT '' COMMENT '卖出委托',
  `buy_request_no` char(32) DEFAULT NULL COMMENT '买入成交流水号：用于资产增减的唯一编号',
  `sell_request_no` char(32) DEFAULT NULL COMMENT '卖出成交流水号：用于资产增减的唯一编号',
  `number` decimal(35,20) DEFAULT NULL COMMENT '成交数量',
  `price` decimal(35,20) DEFAULT NULL COMMENT '成交价格',
  `buy_uid` int(11) DEFAULT NULL COMMENT '买方用户id',
  `sell_uid` int(11) DEFAULT NULL COMMENT '卖方用户id',
  `buy_broker_id` int(11) unsigned NOT NULL COMMENT '买方券商id',
  `sell_broker_id` int(11) unsigned NOT NULL COMMENT '卖方券商id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `buy_fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '买入手续费',
  `sell_fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '卖出手续费',
  PRIMARY KEY (`id`),
  UNIQUE KEY `buy_inner_order_no` (`buy_inner_order_no`,`sell_inner_order_no`),
  KEY `buy_inner_order_no_2` (`buy_inner_order_no`),
  KEY `sell_inner_order_no` (`sell_inner_order_no`),
  KEY `buy_broker_id` (`buy_broker_id`,`symbol`) USING BTREE,
  KEY `sell_broker_id` (`sell_broker_id`,`symbol`) USING BTREE,
  KEY `symbol_time` (`symbol`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='撮合成交记录：包括券商和个人';



# Dump of table trade_order
# ------------------------------------------------------------

CREATE TABLE `trade_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `broker_id` int(11) unsigned NOT NULL COMMENT '券商id',
  `account_id` int(11) NOT NULL COMMENT '帐户id：冻结那个帐户的资产',
  `symbol` varchar(40) NOT NULL COMMENT '交易代码',
  `outer_order_no` varchar(40) NOT NULL COMMENT '外部订单号',
  `inner_order_no` varchar(32) NOT NULL COMMENT '内部订单号',
  `request_no` varchar(32) DEFAULT NULL COMMENT '资产变化请求号，相当于交易流水号',
  `number` decimal(35,20) DEFAULT NULL COMMENT '下单数字币数量',
  `price` decimal(35,20) DEFAULT NULL COMMENT '下单价格',
  `money` decimal(35,20) DEFAULT NULL COMMENT '下单现金金额',
  `order_type` enum('BUY','SELL') NOT NULL COMMENT '下单类型',
  `trade_flag` enum('FIXED','MARKET') NOT NULL COMMENT '下单种类',
  `number_over` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '剩余币数',
  `money_over` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '剩余钱数',
  `traded_number` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '已撮合数量',
  `traded_money` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '已撮合金额',
  `status` enum('FAIL','WAITING','CANCEL','SUCCESS','PROCESSING') DEFAULT 'WAITING' COMMENT '订单状态',
  `send_status` enum('SEND','WAIT','UNKNOWN') DEFAULT 'WAIT' COMMENT '订单撮合状态',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `fail_message_code` varchar(100) DEFAULT NULL,
  `fail_message_des` varchar(255) DEFAULT NULL,
  `fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '总收取手续费',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid_outer_order_no` (`uid`,`outer_order_no`),
  UNIQUE KEY `idx_inner_order_no` (`inner_order_no`),
  UNIQUE KEY `idx_request_no` (`request_no`),
  KEY `uid` (`uid`,`status`,`symbol`),
  KEY `send_status` (`send_status`,`update_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户订单表：包括买单和卖单';



# Dump of table trade_rank
# ------------------------------------------------------------

CREATE TABLE `trade_rank` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `begin_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `symbol` varchar(11) DEFAULT NULL,
  `last_trade_match_result_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table transfer_out_topc
# ------------------------------------------------------------

CREATE TABLE `transfer_out_topc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_code` varchar(40) NOT NULL COMMENT '资产类型',
  `amount` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '数量',
  `tx_fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '交易手续费',
  `from_account` varchar(70) NOT NULL COMMENT '来源帐号',
  `from_coin_address` varchar(70) NOT NULL COMMENT '发送方地址',
  `to_coin_address` varchar(70) NOT NULL COMMENT '接收方地址',
  `inner_order_no` varchar(96) NOT NULL COMMENT '内部订单号',
  `tx_id` varchar(96) NOT NULL COMMENT '交易id',
  `asset_symbol` varchar(100) NOT NULL DEFAULT '' COMMENT '资产代码',
  `state` enum('SUBMIT','RECV','SUCCESS','ERROR') DEFAULT 'RECV' COMMENT '状态',
  `tx_num` varchar(40) DEFAULT NULL COMMENT 'GOP交易id',
  `error_message` varchar(255) DEFAULT '' COMMENT '错误信息',
  `query` int(11) DEFAULT '0' COMMENT '查询次数？',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `confirm_num` int(11) DEFAULT '0' COMMENT '数字币交易确认数',
  `withdraw_status` enum('NEW','CONFIRMING','LONGTIME','CONFIRMED','FAIL') NOT NULL COMMENT '提现状态',
  `fail_message` varchar(96) NOT NULL COMMENT '失败消息说明',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_inner_order_no` (`inner_order_no`),
  KEY `idx_state_from_coin_address_query` (`state`,`from_coin_address`,`query`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table user
# ------------------------------------------------------------

CREATE TABLE `user` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `invite_uid` int(11) unsigned DEFAULT '0' COMMENT '邀请人',
  `broker_id` int(11) unsigned NOT NULL COMMENT '用户所属券商id',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `login_salt` varchar(100) NOT NULL COMMENT '登录盐码',
  `login_password` varchar(200) NOT NULL COMMENT '密码',
  `pay_salt` varchar(100) NOT NULL DEFAULT '' COMMENT '支付盐码',
  `pay_password` varchar(200) NOT NULL DEFAULT '' COMMENT '交易密码',
  `lock_num` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定次数',
  `role` enum('user','admin','admin_read') DEFAULT 'user' COMMENT '角色',
  `fullname` varchar(128) NOT NULL DEFAULT '' COMMENT '姓名',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `createip` char(15) DEFAULT '0.0.0.0' COMMENT '创建ip',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updateip` char(15) DEFAULT '0.0.0.0' COMMENT '更新ip',
  `auth_level` varchar(16) NOT NULL DEFAULT 'LEVEL0' COMMENT '实名认证级别：LEVEL0没有认证，LEVEL1基础认证',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `idx_email` (`email`),
  UNIQUE KEY `idx_mobile` (`mobile`),
  KEY `idx_invite_uid` (`invite_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';



# Dump of table user_activity_record
# ------------------------------------------------------------

CREATE TABLE `user_activity_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `activity_type` varchar(45) NOT NULL,
  `card_type` varchar(45) NOT NULL,
  `card_no` varchar(45) NOT NULL,
  `request_no` varchar(45) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid` (`uid`),
  KEY `idx_card_type` (`card_type`),
  KEY `idx_card_no` (`card_no`),
  KEY `idx_activity_type` (`activity_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table user_api_key
# ------------------------------------------------------------

CREATE TABLE `user_api_key` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `broker_id` int(11) unsigned DEFAULT NULL COMMENT '用户所属券商id',
  `user_no` varchar(32) NOT NULL DEFAULT '0' COMMENT '券商编号',
  `name` char(60) NOT NULL COMMENT '名称',
  `auth_type` enum('PASSPHRASE','ACCESS_KEY') DEFAULT NULL COMMENT '认证方式',
  `passphrase` varchar(256) DEFAULT NULL COMMENT '口令',
  `access_key` varchar(32) DEFAULT NULL COMMENT '访问密匙',
  `secret_key` varchar(32) DEFAULT NULL COMMENT '私钥',
  `level` enum('limited','readonly','full') NOT NULL DEFAULT 'full' COMMENT '级别',
  `ip_allow` char(255) NOT NULL DEFAULT '' COMMENT '允许的ip地址：支持ip段等方式',
  `del_flag` enum('TRUE','FALSE') DEFAULT 'FALSE' COMMENT 'TRUE：已删除, ''FALSE''：未删除',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_access_key` (`access_key`),
  UNIQUE KEY `idx_secret_key` (`secret_key`),
  UNIQUE KEY `idx_passphrase` (`passphrase`(64)),
  KEY `idx_broker_id` (`broker_id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_user_no` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='券商访问key：API访问秘钥，给商户分配key';



# Dump of table user_basic_info
# ------------------------------------------------------------

CREATE TABLE `user_basic_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(128) NOT NULL DEFAULT '',
  `middle_name` varchar(128) NOT NULL DEFAULT '',
  `last_name` varchar(128) NOT NULL DEFAULT '',
  `gender` enum('MALE','FEMALE') NOT NULL DEFAULT 'MALE',
  `birthday` varchar(30) NOT NULL DEFAULT '',
  `country_id` varchar(11) NOT NULL DEFAULT '',
  `country` varchar(128) NOT NULL DEFAULT '',
  `uid` int(11) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_key` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table user_begining_balance
# ------------------------------------------------------------

CREATE TABLE `user_begining_balance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) NOT NULL COMMENT 'uid',
  `asset_code` varchar(40) NOT NULL COMMENT '资产代码',
  `accountNo` varchar(64) DEFAULT NULL COMMENT '账号/邮箱',
  `amount_available` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '可用金额',
  `amount_lock` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '冻结金额',
  `amount_total` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '资产总额',
  `status` tinyint(2) NOT NULL COMMENT '0 不可用 1 可用',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='平台期初余额：按币种';



# Dump of table user_google_code_config
# ------------------------------------------------------------

CREATE TABLE `user_google_code_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) unsigned NOT NULL,
  `flag` varchar(32) NOT NULL,
  `secret_code` varchar(32) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reset_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table user_identification
# ------------------------------------------------------------

CREATE TABLE `user_identification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `country_id` varchar(32) NOT NULL COMMENT '证件国家编号',
  `country` varchar(128) NOT NULL COMMENT '证件国家',
  `card_type` varchar(32) NOT NULL COMMENT '证件类型',
  `card_no` varchar(32) NOT NULL COMMENT '证件编号',
  `expired_date` datetime NOT NULL COMMENT '有效期至',
  `card_photo` varchar(512) NOT NULL COMMENT '证件照片，照片的md5，从mongodb中获取，用json数组支持多张照片？',
  `card_handhold` varchar(512) NOT NULL COMMENT '手持证件照，照片的md5，从mongodb中获取，用json数组支持多张照片？',
  `card_translate` varchar(512) NOT NULL COMMENT '证件翻译照片，照片的md5，从mongodb中获取，用json数组支持多张照片？',
  `status` enum('INIT','FINISH') NOT NULL COMMENT '处理状态：INIT初始，FINISH完成',
  `audit_uid` int(11) NOT NULL DEFAULT '0' COMMENT '审核人员',
  `audit_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '审核时间',
  `audit_status` enum('INIT','OK','FAIL') NOT NULL COMMENT '审核状态：INIT初始, OK通过，FAIL没有通过',
  `audit_first` enum('YES','NO') NOT NULL COMMENT '是否首次认证',
  `audit_message_id` varchar(32) NOT NULL DEFAULT '' COMMENT '消息id',
  `audit_message` varchar(256) NOT NULL DEFAULT '' COMMENT '审核消息',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `card_back` varchar(512) DEFAULT '',
  `full_name` varchar(128) NOT NULL COMMENT '用户全名',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户身份认证';



# Dump of table user_info
# ------------------------------------------------------------

CREATE TABLE `user_info` (
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `user_no` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `name` char(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `id_number` char(30) NOT NULL DEFAULT '' COMMENT '证件号码',
  `id_type` tinyint(1) unsigned DEFAULT '0' COMMENT '证件类型',
  `id_auth_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '认证时间',
  `id_authip` char(15) DEFAULT '0.0.0.0' COMMENT '认证ip',
  `mobile_bind_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '手机绑定时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `idx_user_no` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息';



# Dump of table user_info_profile
# ------------------------------------------------------------

CREATE TABLE `user_info_profile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `profile_group` varchar(32) NOT NULL COMMENT '配置组',
  `profile_key` varchar(64) NOT NULL COMMENT '配置关键字',
  `profile_index` varchar(32) NOT NULL COMMENT '配置序号',
  `data_type` varchar(32) NOT NULL COMMENT '数据类型',
  `profile_value` varchar(10240) NOT NULL COMMENT '配置数值',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid_profile_key_profile_index` (`uid`,`profile_key`,`profile_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户配置信息表';



# Dump of table user_login_log
# ------------------------------------------------------------

CREATE TABLE `user_login_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `ip_address` varchar(32) NOT NULL COMMENT '用户登录ip地址',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip_country` varchar(45) NOT NULL DEFAULT '',
  `ip_city` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_create_date` (`create_date`) USING BTREE,
  KEY `idx_ip_address` (`ip_address`),
  KEY `idx_uid_create` (`uid`,`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table user_message
# ------------------------------------------------------------

CREATE TABLE `user_message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `category` enum('SECURITY','ASSETS','SYSTEM','C2C') DEFAULT NULL COMMENT 'SECURITY:安全信息，ASSETS:资产信息，SYSTEM：系统消息',
  `content` text COMMENT '消息内容',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `status` enum('UNREAD','READ') DEFAULT NULL COMMENT 'UNREAD:未读，READ:已读',
  PRIMARY KEY (`id`),
  KEY `category` (`category`),
  KEY `status` (`status`),
  KEY `uid` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='站内信：给用户推送的各种消息';



# Dump of table user_pay_password
# ------------------------------------------------------------

CREATE TABLE `user_pay_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `lock_num` int(11) NOT NULL COMMENT '锁定次数',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付密码上锁表：支付密码上锁表（支付密码错误次数记录，以及限制）';



# Dump of table user_pre_registration_pool
# ------------------------------------------------------------

CREATE TABLE `user_pre_registration_pool` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `invite_uid` int(11) unsigned DEFAULT '0' COMMENT '邀请人',
  `broker_id` int(11) unsigned NOT NULL COMMENT '用户所属券商id',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `login_salt` varchar(100) NOT NULL COMMENT '登录盐码',
  `login_password` varchar(200) NOT NULL COMMENT '密码',
  `pay_salt` varchar(100) NOT NULL DEFAULT '' COMMENT '支付盐码',
  `pay_password` varchar(200) NOT NULL DEFAULT '' COMMENT '交易密码',
  `lock_num` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定次数',
  `role` enum('user','admin','admin_read') DEFAULT 'user' COMMENT '角色',
  `fullname` varchar(128) NOT NULL DEFAULT '' COMMENT '姓名',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';



# Dump of table user_question
# ------------------------------------------------------------

CREATE TABLE `user_question` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `question` char(60) NOT NULL DEFAULT '' COMMENT '问题',
  `answer` char(32) NOT NULL DEFAULT '' COMMENT '答案',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户提问：用户提问表，忘记密码后的提问信息';



# Dump of table user_redeem_activity
# ------------------------------------------------------------

CREATE TABLE `user_redeem_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `activity_id` int(11) NOT NULL COMMENT '对应 user_redeem_activity_config中的 id',
  `redeem_code` varchar(45) NOT NULL COMMENT '兑换码',
  `asset_code` varchar(45) NOT NULL COMMENT '币种',
  `amount` decimal(35,20) NOT NULL COMMENT '兑换数量',
  `status` enum('UNUSE','USED') NOT NULL COMMENT '状态: 未使用:UNUSE,已使用USED',
  `uid` int(11) DEFAULT NULL COMMENT '用户uid',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `redeem_code_UNIQUE` (`redeem_code`),
  KEY `idx_asset_code` (`asset_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户兑换码兑换活动表';



# Dump of table user_redeem_activity_config
# ------------------------------------------------------------

CREATE TABLE `user_redeem_activity_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) NOT NULL COMMENT '兑换活动名称',
  `asset_code` varchar(45) NOT NULL COMMENT '兑换活动币种',
  `begin_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '兑换活动开始时间',
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '兑换活动截止时间',
  `status` enum('ON','OFF') NOT NULL COMMENT '活动状态: 开启 关闭 ON.OFF',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `activity_name_UNIQUE` (`activity_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户兑换码活动配置表';



# Dump of table user_redeem_activity_detail
# ------------------------------------------------------------

CREATE TABLE `user_redeem_activity_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户uid',
  `redeem_id` int(11) NOT NULL COMMENT '对应着user_redeem_activity 中的id',
  `asset_code` varchar(45) NOT NULL COMMENT '币种',
  `amount` decimal(35,20) NOT NULL COMMENT '数量',
  `request_no` varchar(45) NOT NULL COMMENT '对应finance_detail 中的请求订单号',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `redeem_id_UNIQUE` (`redeem_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户兑换码兑换活动流水记录表';



# Dump of table user_residence
# ------------------------------------------------------------

CREATE TABLE `user_residence` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `country_id` varchar(32) NOT NULL COMMENT '证件国家编号',
  `country` varchar(128) NOT NULL COMMENT '证件国家',
  `city` varchar(128) NOT NULL COMMENT '居住城市',
  `address` varchar(256) NOT NULL COMMENT '居住详细地址',
  `postcode` varchar(32) NOT NULL COMMENT '邮编',
  `residence_photo` varchar(512) NOT NULL COMMENT '居住证明照片，照片的md5，从mongodb中获取，用json数组支持多张照片？',
  `residence_translate` varchar(512) NOT NULL COMMENT '居住证明翻译照片，照片的md5，从mongodb中获取，用json数组支持多张照片？',
  `status` enum('INIT','FINISH') NOT NULL COMMENT '处理状态：INIT初始，FINISH完成',
  `audit_uid` int(11) NOT NULL DEFAULT '0' COMMENT '审核人员',
  `audit_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '审核时间',
  `audit_status` enum('INIT','OK','FAIL') NOT NULL COMMENT '审核状态：INIT初始, OK通过，FAIL没有通过',
  `audit_first` enum('YES','NO') NOT NULL COMMENT '是否首次认证',
  `audit_message_id` varchar(32) NOT NULL DEFAULT '' COMMENT '消息id',
  `audit_message` varchar(256) NOT NULL DEFAULT '' COMMENT '审核消息',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `full_name` varchar(128) NOT NULL COMMENT '用户全称',
  `paper_type` tinyint(4) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户居住认证';



# Dump of table user_statistics
# ------------------------------------------------------------

CREATE TABLE `user_statistics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `totalUser` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '总用户数',
  `nativeUser` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '初级实名用户数',
  `advancedUser` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '高级实名用户数',
  `nativePresent` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '初级实名百分比',
  `advancedPresent` decimal(35,20) NOT NULL DEFAULT '0.00000000000000000000' COMMENT '高级实名百分比',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '0:不可用，1：可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='平台用户统计信息';



# Dump of table user_transaction_fee_white_list
# ------------------------------------------------------------

CREATE TABLE `user_transaction_fee_white_list` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `admin_id` int(11) DEFAULT NULL,
  `flag` enum('VALID','INVALID') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table user_upload_resource_log
# ------------------------------------------------------------

CREATE TABLE `user_upload_resource_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `tag` varchar(40) NOT NULL COMMENT '图片hashcode',
  `dataType` varchar(64) NOT NULL COMMENT 'tag类型',
  `soucre` varchar(64) DEFAULT NULL COMMENT '下载来源',
  `storeType` varchar(64) DEFAULT NULL COMMENT '存储类型',
  `createTime` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime(6) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `tag` (`tag`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户配置信息表';



# Dump of table withdraw_address
# ------------------------------------------------------------

CREATE TABLE `withdraw_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_code` varchar(40) NOT NULL DEFAULT '' COMMENT '币种',
  `coin_address` varchar(70) NOT NULL DEFAULT '' COMMENT '券商提现地址',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table withdraw_coin_order_user
# ------------------------------------------------------------

CREATE TABLE `withdraw_coin_order_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `broker_id` int(11) unsigned NOT NULL COMMENT '券商id',
  `account_id` int(11) unsigned NOT NULL COMMENT '账户id',
  `account` varchar(40) NOT NULL COMMENT '用户账号',
  `asset_code` varchar(40) NOT NULL COMMENT '资产类型',
  `channel_withdraw_id` int(11) unsigned NOT NULL COMMENT '提现通道id',
  `coin_address` varchar(70) NOT NULL COMMENT '数字钱包地址',
  `outer_order_no` char(40) NOT NULL COMMENT '外部订单号',
  `inner_order_no` char(40) NOT NULL COMMENT '内部订单号',
  `number` decimal(35,20) unsigned NOT NULL DEFAULT '0.00000000000000000000' COMMENT '总果仁数：实际果仁数+手续费',
  `real_number` decimal(35,20) unsigned DEFAULT '0.00000000000000000000' COMMENT '实际果仁数量',
  `status` enum('WAIT','UNKNOWN','SUCCESS','REFUSE','PROCESSING','FAILURE') DEFAULT 'WAIT' COMMENT '状态',
  `dest_address_type` enum('INNER_ADDRESS','OUTER_ADDRESS') NOT NULL COMMENT '目标系统(内部地址,私钥由自己控制的地址,外部地址,私钥不由自己控制的地址,转出时指转出到系统，转入时指从何系统转入)',
  `msg` char(255) DEFAULT '' COMMENT '消息',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `tx_fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT 'tx手续费',
  `admin_id` int(11) unsigned DEFAULT NULL COMMENT '管理员帐号',
  `asset_status` enum('INIT','SUCCESS','FAILURE') DEFAULT NULL COMMENT '资产状体：已经入帐，待确认，已经确认',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_outer_order_no` (`outer_order_no`),
  UNIQUE KEY `idx_inner_order_no` (`inner_order_no`),
  KEY `idx_uid_coin_address` (`uid`,`coin_address`),
  KEY `idx_create_date` (`create_date`) USING BTREE,
  KEY `idx_update_date` (`update_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='普通用户数字币提现订单表';



# Dump of table withdraw_currency_order_offline
# ------------------------------------------------------------

CREATE TABLE `withdraw_currency_order_offline` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `account_id` int(11) unsigned NOT NULL COMMENT '账户id',
  `asset_code` varchar(40) NOT NULL COMMENT '资产类型',
  `channel_withdraw_id` int(11) unsigned NOT NULL COMMENT '提现通道id',
  `inner_order_no` char(32) NOT NULL COMMENT '内部订单号',
  `money` decimal(35,20) unsigned NOT NULL DEFAULT '0.00000000000000000000' COMMENT '总金额：实际支付+手续费',
  `pay` decimal(35,20) unsigned DEFAULT '0.00000000000000000000' COMMENT '实付金额',
  `fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '手续费',
  `status` enum('WAIT','SUCCESS','CANCEL','PROCESSING','FAILURE') DEFAULT NULL COMMENT '状态',
  `pay_mode` varchar(32) DEFAULT NULL COMMENT '支付方式:("OFFLINE","SUPERPAY","QDBPAY","CIBPAY","OKPAY","ALIPAY")',
  `admin_id` int(11) unsigned DEFAULT '0' COMMENT '管理员',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`),
  KEY `idx_status_pay_mode` (`status`,`pay_mode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='大额提现:券商现金资产提现订单表：大额充值，不走API';



# Dump of table withdraw_currency_order_user
# ------------------------------------------------------------

CREATE TABLE `withdraw_currency_order_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) unsigned NOT NULL COMMENT '用户id',
  `broker_id` int(11) unsigned NOT NULL COMMENT '券商id',
  `account_id` int(11) unsigned NOT NULL COMMENT '账户id',
  `account` varchar(40) NOT NULL COMMENT '用户账号',
  `asset_code` varchar(40) NOT NULL COMMENT '资产类型',
  `channel_withdraw_id` int(11) unsigned NOT NULL COMMENT '提现通道id',
  `admin_id` int(11) unsigned DEFAULT '0' COMMENT '管理员',
  `money` decimal(35,20) unsigned NOT NULL DEFAULT '0.00000000000000000000' COMMENT '总金额：实际支付+手续费',
  `pay` decimal(35,20) unsigned DEFAULT '0.00000000000000000000' COMMENT '实付金额',
  `fee` decimal(35,20) DEFAULT '0.00000000000000000000' COMMENT '手续费',
  `inner_order_no` varchar(40) NOT NULL COMMENT '内部订单号',
  `outer_order_no` varchar(40) NOT NULL COMMENT '外部订单号',
  `acnumber` varchar(40) DEFAULT '' COMMENT '账号',
  `bank` varchar(100) DEFAULT '' COMMENT '银行',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `status` enum('WAIT','UNKNOWN','SUCCESS','CANCEL','PROCESSING','FAILURE') DEFAULT 'WAIT' COMMENT '状态',
  `pay_mode` varchar(32) DEFAULT NULL COMMENT '支付方式:("OFFLINE","SUPERPAY","QDBPAY","CIBPAY","OKPAY","ALIPAY")',
  `msg` char(150) DEFAULT '' COMMENT '消息',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid_outer_order_no` (`uid`,`outer_order_no`) USING BTREE,
  UNIQUE KEY `idx_inner_order_no` (`inner_order_no`) USING BTREE,
  KEY `idx_uid` (`uid`),
  KEY `idx_status_pay_mode` (`status`,`pay_mode`),
  KEY `idx_bank_acnumber_name` (`bank`,`acnumber`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='普通用户现金提现订单表';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
