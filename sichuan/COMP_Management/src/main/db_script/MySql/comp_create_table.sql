-- cloong drop tables
DROP TABLE IF EXISTS `comp_authority_group_role_t`;
DROP TABLE IF EXISTS `comp_authority_permission_t`;
DROP TABLE IF EXISTS `comp_authority_role_perm_t`;
DROP TABLE IF EXISTS `comp_authority_role_t`;
DROP TABLE IF EXISTS `comp_authority_user_group_t`;
DROP TABLE IF EXISTS `comp_authority_user_role_t`;
DROP TABLE IF EXISTS `comp_authority_user_t`;
DROP TABLE IF EXISTS `comp_case_ebs_t`;
DROP TABLE IF EXISTS `comp_case_pm_t`;
DROP TABLE IF EXISTS `comp_case_pm_net_t`;
DROP TABLE IF EXISTS `COMP_CASE_PM_MOD_T`;
DROP TABLE IF EXISTS `comp_case_pm_mod_net_t`;
DROP TABLE IF EXISTS `comp_case_vmbak_t`;
DROP TABLE IF EXISTS `comp_case_vm_t`;
DROP TABLE IF EXISTS `comp_catalog_t`;
DROP TABLE IF EXISTS `comp_ebs_create_fail_t`;
DROP TABLE IF EXISTS `comp_ebs_res_count_t`;
DROP TABLE IF EXISTS `comp_ebs_res_meterage_t`;
DROP TABLE IF EXISTS `comp_item_audit_log_t`;
DROP TABLE IF EXISTS `comp_item_release_log_t`;
DROP TABLE IF EXISTS `comp_item_t`;
DROP TABLE IF EXISTS `comp_service_code_t`;
DROP TABLE IF EXISTS `comp_order_audit_log_t`;
DROP TABLE IF EXISTS `comp_order_t`;
DROP TABLE IF EXISTS `comp_os_relate_t`;
DROP TABLE IF EXISTS `comp_os_t`;
DROP TABLE IF EXISTS `comp_pm_create_fail_t`;
DROP TABLE IF EXISTS `comp_pm_del_fail_t`;
DROP TABLE IF EXISTS `comp_pm_query_fail_t`;
DROP TABLE IF EXISTS `comp_pm_res_count_t`;
DROP TABLE IF EXISTS `comp_res_pool_t`;
DROP TABLE IF EXISTS `comp_res_pool_part_t`;
DROP TABLE IF EXISTS `comp_sm_mail_time_t`;
DROP TABLE IF EXISTS `comp_standard_ebs_t`;
DROP TABLE IF EXISTS `comp_standard_iso_t`;
DROP TABLE IF EXISTS `comp_standard_pm_t`;
DROP TABLE IF EXISTS `comp_standard_syn_t`;
DROP TABLE IF EXISTS `comp_standard_vm_t`;
DROP TABLE IF EXISTS `comp_sys_audit_switch_t`;
DROP TABLE IF EXISTS `comp_sys_conf_default_t`;
DROP TABLE IF EXISTS `comp_user_audit_log_t`;
DROP TABLE IF EXISTS `comp_vmbak_create_fail_t`;
DROP TABLE IF EXISTS `comp_vmbak_del_fail_t`;
DROP TABLE IF EXISTS `comp_vmbak_res_count_t`;
DROP TABLE IF EXISTS `comp_vmbak_res_meterage_t`;
DROP TABLE IF EXISTS `comp_vmbak_rollback_fail_t`;
DROP TABLE IF EXISTS `comp_vm_create_fail_t`;
DROP TABLE IF EXISTS `comp_vm_del_fail_t`;
DROP TABLE IF EXISTS `comp_vm_query_fail_t`;
DROP TABLE IF EXISTS `comp_vmbak_query_fail_t`;
DROP TABLE IF EXISTS `comp_vm_res_count_t`;
DROP TABLE IF EXISTS `comp_case_vm_mod_t`;
DROP TABLE IF EXISTS `comp_vm_modify_fail_t`;


/*湖北运营新增表*/
DROP TABLE IF EXISTS `comp_rm_machineRoom_t`;
DROP TABLE IF EXISTS `comp_rm_cabinet_t`;
DROP TABLE IF EXISTS `comp_rm_pm_t`;
DROP TABLE IF EXISTS `comp_rm_vm_t`;
DROP TABLE IF EXISTS `comp_rm_ebs_t`;
DROP TABLE IF EXISTS `comp_rm_raid_t`;
DROP TABLE IF EXISTS `comp_rm_switch_t`;
DROP TABLE IF EXISTS `comp_rm_switch_if_t`;
DROP TABLE IF EXISTS `comp_rm_router_t`;
DROP TABLE IF EXISTS `comp_rm_router_if_t`;
DROP TABLE IF EXISTS `comp_rm_firewall_t`;
DROP TABLE IF EXISTS `comp_sta_capacity_t`;
DROP TABLE IF EXISTS `comp_sta_minipm_rt_t`;
DROP TABLE IF EXISTS `comp_sta_minipmpar_rt_t`;
DROP TABLE IF EXISTS `comp_sta_pm_rt_t`;
DROP TABLE IF EXISTS `comp_sta_vm_rt_t`;
DROP TABLE IF EXISTS `comp_sta_pm_disk_rt_t`;
DROP TABLE IF EXISTS `comp_sta_vm_disk_rt_t`;
DROP TABLE IF EXISTS `comp_sta_device_week_t`;
DROP TABLE IF EXISTS `comp_sta_device_app_week_t`;
DROP TABLE IF EXISTS `comp_sta_device_day_t`;
DROP TABLE IF EXISTS `comp_sta_device_app_day_t`;
DROP TABLE IF EXISTS `comp_sta_device_halfday_t`;
DROP TABLE IF EXISTS `comp_app_t`;
DROP TABLE IF EXISTS `comp_proc_exec_stat_t`;
DROP TABLE IF EXISTS `comp_proc_parameter_t`;
DROP TABLE IF EXISTS `comp_rm_minipm_t`;
DROP TABLE IF EXISTS `comp_rm_minipmpar_t`;
DROP TABLE IF EXISTS `comp_authority_user_app_t`;
DROP TABLE IF EXISTS `comp_case_vm_net_t`;
DROP TABLE IF EXISTS `comp_case_vm_mod_net_t`;
DROP TABLE IF EXISTS `COMP_VLAN_IPSEGMENT_BIND_T`;
DROP TABLE IF EXISTS `comp_case_ebs_mod_t`;
DROP TABLE IF EXISTS `comp_case_vlan_t`;
DROP TABLE IF EXISTS `comp_case_ipsegment_t`;
DROP TABLE IF EXISTS `COMP_PM_TYPE_T`;
DROP TABLE IF EXISTS `comp_sta_bs_rt_t`;
DROP TABLE IF EXISTS `comp_sta_raid_rt_t`;
DROP TABLE IF EXISTS `comp_sta_switch_port_rt_t`;
DROP TABLE IF EXISTS `comp_sta_switch_rt_t`;
DROP TABLE IF EXISTS `comp_sta_firewall_rt_t`;
DROP TABLE IF EXISTS `comp_sta_router_rt_t`;
DROP TABLE IF EXISTS `comp_sta_router_port_rt_t`;
DROP TABLE IF EXISTS `comp_batch_vm_t`;




-- ----------------------------
--  Table structure for `comp_batch_vm_t`
-- ----------------------------
CREATE TABLE `comp_batch_vm_t` (
`vm_id`  varchar(100) NULL ,
`id`  varchar(100) NULL ,
`vmModifyFlag`  varchar(1) NULL COMMENT '虚拟机修改标志：1：成功，0：失败' ,
`create_time`  varchar(100) NULL COMMENT '时间',
`modifyDesc`  varchar(255) NULL COMMENT '虚拟机修改结果描述' 
)
;
-- ----------------------------
--  Table structure for `comp_authority_group_role_t`
-- ----------------------------
CREATE TABLE `comp_authority_group_role_t` (
  `GROUP_ID` varchar(36) DEFAULT NULL,
  `ROLE_ID` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_authority_permission_t`
-- ----------------------------
CREATE TABLE `comp_authority_permission_t` (
  `PERMISSION_ID` varchar(36) NOT NULL,
  `PARENT_ID` varchar(36) NOT NULL,
  `ENGLISH_NAME` varchar(32) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `TYPE` varchar(4) NOT NULL,
  `STATUS` char(1) NOT NULL,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  `CREATE_TIME` varchar(14) NOT NULL,
  `CREATE_USER` varchar(50) NOT NULL,
  PRIMARY KEY (`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_authority_role_perm_t`
-- ----------------------------
CREATE TABLE `comp_authority_role_perm_t` (
  `ROLE_ID` varchar(36) NOT NULL,
  `PERMISSION_ID` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_authority_role_t`
-- ----------------------------
CREATE TABLE `comp_authority_role_t` (
  `ROLE_ID` varchar(36) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `STATUS` char(1) NOT NULL COMMENT '是否有效  0：无效；1：有效',
  `DESCRIPTION` varchar(1024) DEFAULT NULL,
  `CREATE_TIME` varchar(14) NOT NULL,
  `CREATE_USER` varchar(50) NOT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_authority_user_group_t`
-- ----------------------------
CREATE TABLE `comp_authority_user_group_t` (
  `USER_ID` varchar(64) NOT NULL,
  `GROUP_ID` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_authority_user_role_t`
-- ----------------------------
CREATE TABLE `comp_authority_user_role_t` (
  `USER_ID` varchar(20) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_authority_user_t`
-- ----------------------------
CREATE TABLE `comp_authority_user_t` (
  `USER_ID` varchar(20) NOT NULL,
  `USER_NAME` varchar(10) NOT NULL,
  `PASSWORD` varchar(1024) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `STATUS` char(1) NOT NULL,
  `LOCK_TIME` char(64) DEFAULT NULL,
  `MOBILE` varchar(14) DEFAULT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  `ADDRESS` varchar(64) DEFAULT NULL,
  `DESCRIPTION` varchar(1024) DEFAULT NULL,
  `CREATE_USER` varchar(64) DEFAULT NULL,
  `LOGIN_FAILED_TIME` char(64) DEFAULT NULL,
  `TELPHONE` varchar(20) DEFAULT NULL,
  `FAX` varchar(20) DEFAULT NULL,
  `DEPARTMENT_NAME` varchar(50) DEFAULT NULL,
  `FORBIDDEN_TIME` varchar(64) DEFAULT NULL,
  `LOCK_NUM` varchar(64) DEFAULT NULL,
  `FORBIDDEN_NUM` varchar(64) DEFAULT NULL,
  `SECURITY_QUESTION` varchar(128)  NULL,
  `SECURITY_ANSWER` varchar(128)  NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `comp_authority_user_app_t`
-- ----------------------------
CREATE TABLE `comp_authority_user_app_t` (
  `USER_ID` varchar(20) NOT NULL,
  `APP_ID` varchar(64) NOT NULL,
  `APPBIND_STATUS` varchar(1) NOT NULL,
  `AUDIT_TIME` varchar(14) DEFAULT NULL,
  `AUDIT_USER` varchar(64) DEFAULT NULL,
  `AUDIT_INFO` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
--  Table structure for `comp_case_ebs_t`
-- ----------------------------
CREATE TABLE `comp_case_ebs_t` (
  `CASE_ID` varchar(30) NOT NULL,
  `EBS_ID` varchar(30) DEFAULT NULL,
  `EBS_NAME` varchar(50) DEFAULT NULL,
  `APP_ID` varchar(64) DEFAULT NULL,
  `ResourceTYPE` int(11) DEFAULT NULL,
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `ORDER_ID` varchar(30) NOT NULL,
  `ACCEPT_TIME` char(14) DEFAULT NULL,
  `STATUS` varchar(10) NOT NULL,
  `RES_POOL_ID` varchar(30) NOT NULL,
  `RES_POOL_PART_ID` varchar(30) NOT NULL,
  `HOST_ID` varchar(30) DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`CASE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `comp_case_ebs_t`
ADD COLUMN `DISK_SIZE`  int(10) UNSIGNED NOT NULL COMMENT '磁盘大小(单位 G)' AFTER `STANDARD_ID`;

ALTER TABLE `comp_case_ebs_t`
ADD COLUMN `ITEM_ID`  char(30) NULL COMMENT '服务条目ID' AFTER `APP_ID`;

-- ----------------------------
--  Table structure for `comp_case_pm_t`
-- ----------------------------
CREATE TABLE `comp_case_pm_t` (
  `CASE_ID` varchar(30) NOT NULL,
  `PARAM_FLAG` varchar(1) NOT NULL,
  `PM_ID` varchar(30) DEFAULT NULL,
  `PM_NAME` varchar(50) DEFAULT NULL,
  `APP_ID` varchar(64) DEFAULT NULL,
  `STATUS` varchar(2) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_NAME` varchar(50) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_NAME` varchar(50) NOT NULL,
  `ITEM_ID` varchar(30) DEFAULT NULL,
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `ORDER_ID` varchar(30) NOT NULL,
  `OPERATION_IP`	VARCHAR(15) DEFAULT NULL,
  `PM_USER` varchar(50) DEFAULT NULL,
  `PM_PASSWORD` varchar(50) DEFAULT NULL,
  `SERVER_TYPE`	VARCHAR(128) NOT NULL,
  `CPU_TYPE` varchar(100) NOT NULL,
  `RAM_SIZE` int(11) NOT NULL,
  `DISC_SIZE` int(11) NOT NULL,
  `ISO_ID` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`CASE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `comp_case_pm_net_t`
-- ----------------------------
CREATE TABLE `comp_case_pm_net_t` (
	`CASE_ID`	VARCHAR(30)	NOT NULL,
	`PM_ID`	    VARCHAR(30)	DEFAULT NULL,
	`PUR_POSE`	VARCHAR(1)	NOT NULL,
	`ETH`	    VARCHAR(10)	NOT NULL,
	`IP`	    VARCHAR(15)	DEFAULT NULL,
  `IP_Subnetmask`	VARCHAR(15)	DEFAULT NULL,
	`IP_DEFAULT_GATEWAY`	VARCHAR(15)	DEFAULT NULL,
	`VLAN_ID`	            VARCHAR(10)	DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `COMP_CASE_PM_MOD_T`
-- ----------------------------
CREATE TABLE `COMP_CASE_PM_MOD_T` (
	`PM_ID`	    VARCHAR(30)	NOT NULL,
	`PM_NAME`	    VARCHAR(50)	DEFAULT NULL,
  `TIME_STAMP` varchar(14) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_case_pm_mod_net_t`
-- ----------------------------
CREATE TABLE `comp_case_pm_mod_net_t` (
	`PM_ID`	    VARCHAR(30)	NOT NULL,
	`PUR_POSE`	VARCHAR(1)	NOT NULL,
	`ETH`	    VARCHAR(10)	NOT NULL,
	`IP`	    VARCHAR(15)	DEFAULT NULL,
  `IP_Subnetmask`	VARCHAR(15)	DEFAULT NULL,
	`IP_DEFAULT_GATEWAY`	VARCHAR(15)	DEFAULT NULL,
	`VLAN_ID`	            VARCHAR(10)	DEFAULT NULL,
	`TIME_STAMP` char(14) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_case_vmbak_t`
-- ----------------------------
CREATE TABLE `comp_case_vmbak_t` (
  `CASE_ID` varchar(30) NOT NULL,
  `VMBAK_ID` varchar(32) DEFAULT NULL,
  `VMBAK_NAME` varchar(50) DEFAULT NULL,
  `BACKUP_CYC` int(11) NOT NULL,
  `BACK_STORE_TIME` int(11) DEFAULT NULL,
  `BACKUP_START_TIME` varchar(14) NOT NULL,
  `ACCEPT_TIME` varchar(14) DEFAULT NULL,
  `VM_ID` varchar(30) NOT NULL,
  `STATUS` varchar(2) NOT NULL,
  `RES_POOL_ID` varchar(30) NOT NULL,
  `RES_POOL_PART_ID` varchar(30) NOT NULL,
  `ORDER_ID` varchar(30) NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL,
  `RESTORE_VMBACK_INTERNAL_ID` varchar(40) DEFAULT NULL,
  `RESTORE_TIME` char(14) DEFAULT NULL,
  PRIMARY KEY (`CASE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_case_vm_t`
-- ----------------------------
CREATE TABLE `comp_case_vm_t` (
  `CASE_ID` varchar(30) NOT NULL,
  `Param_Flag` VARCHAR(1) NOT NULL,
  `VM_ID` varchar(30) DEFAULT NULL,
  `VM_NAME` varchar(50) DEFAULT NULL,
  `APP_ID` varchar(64) DEFAULT NULL,
  `ITEM_ID` varchar(30) DEFAULT NULL,
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `Operation_IP` VARCHAR(15) DEFAULT NULL,
  `Operation_URL` VARCHAR(100) DEFAULT NULL,
  `UserName` VARCHAR(20) DEFAULT NULL,
  `VM_PASSWORD` varchar(50) DEFAULT NULL,
  `STATUS` varchar(10) NOT NULL,
  `RES_POOL_ID` varchar(30) NOT NULL,
  `RES_POOL_NAME` varchar(50) NOT NULL,
  `RES_POOL_PART_ID` varchar(30) NOT NULL,
  `RES_POOL_PART_NAME` varchar(50) NOT NULL,
  `ORDER_ID` varchar(30) NOT NULL,
  `CPU_NUM` int(11) NOT NULL,
  `RAM_SIZE` int(11) NOT NULL,
  `DISC_SIZE` int(11) NOT NULL,
  `ISO_ID` varchar(50) NOT NULL,
  `ISO_NAME` varchar(150) NOT NULL,
  `ISO_DESCRIPTION` varchar(200) NOT NULL,
  `PM_ID` VARCHAR(30),
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL,
  `BAND_WIDTH` varchar(30) NULL,
  `NETWORK_IP` varchar(30) NULL,
  PRIMARY KEY (`CASE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_case_vm_net_t`
-- ----------------------------
CREATE TABLE `comp_case_vm_net_t` (
	`CASE_ID`	VARCHAR(30)	NOT NULL,
	`VM_ID`	    VARCHAR(30)	DEFAULT NULL,
	`PUR_POSE`	VARCHAR(1)	NOT NULL,
	`ETH`	    VARCHAR(10)	NOT NULL,
	`IP`	    VARCHAR(15)	DEFAULT NULL,
	`IP_DEFAULT_GATEWAY`	VARCHAR(15)	DEFAULT NULL,
	`VLAN_ID`	            VARCHAR(10)	DEFAULT NULL,
	`IP_Subnetmask`	VARCHAR(15)	DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `comp_case_vm_mod_t`
-- ----------------------------
CREATE TABLE `comp_case_vm_mod_t` (
  `VM_ID` varchar(30) NOT NULL,
  `VM_NAME` varchar(50) DEFAULT NULL,
  `CPU_NUM` int(11) DEFAULT NULL,
  `RAM_SIZE` int(11) DEFAULT NULL,
  `DISC_SIZE` int(11) DEFAULT NULL,
  `RES_POOL_ID` varchar(30) NULL,
  `RES_POOL_PART_ID` varchar(30) NULL,
  `TIME_STAMP` char(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_case_vm_mod_net_t`
-- ----------------------------
CREATE TABLE `comp_case_vm_mod_net_t` (
	`VM_ID`	    VARCHAR(30)	NOT NULL,
	`PUR_POSE`	VARCHAR(1)	NOT NULL,
	`ETH`	    VARCHAR(10)	NOT NULL,
	`IP`	    VARCHAR(15)	DEFAULT NULL,
	`IP_DEFAULT_GATEWAY`	VARCHAR(15)	DEFAULT NULL,
	`VLAN_ID`	            VARCHAR(10)	DEFAULT NULL,
	`IP_Subnetmask`	VARCHAR(15)	DEFAULT NULL,
	`TIME_STAMP` char(14) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `COMP_VLAN_IPSEGMENT_BIND_T`
-- ----------------------------
CREATE TABLE `COMP_VLAN_IPSEGMENT_BIND_T`(
`VLAN_ID`	VARCHAR(30)	NOT NULL,
`IPSEGMENT_ID`	VARCHAR(30)	NOT NULL,
`CREATE_TIME`	CHAR(14)	NOT NULL,
`CREATE_USER`	VARCHAR(20)	NOT NULL,
`UPDATE_TIME`	CHAR(14)	DEFAULT NULL,
`UPDATE_USER`	VARCHAR(20)	DEFAULT NULL,
`Status`	VARCHAR(1)	NOT NULL,
  PRIMARY KEY (`VLAN_ID`,`IPSEGMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_catalog_t`
-- ----------------------------
CREATE TABLE `comp_catalog_t` (
  `CATALOG_ID` varchar(50) NOT NULL,
  `CATALOG_NAME` varchar(50) NOT NULL,
  `STATUS` char(1) NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CATALOG_TYPE` varchar(2) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) DEFAULT NULL,
  `UPDATE_USER` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`CATALOG_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_ebs_create_fail_t`
-- ----------------------------
CREATE TABLE `comp_ebs_create_fail_t` (
  `FAIL_CAUSE` varchar(128) DEFAULT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `EBS_NAME` varchar(50) DEFAULT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_ebs_res_count_t`
-- ----------------------------
CREATE TABLE `comp_ebs_res_count_t` (
  `EBS_ID` varchar(30) NOT NULL,
  `START_TIME` varchar(14) NOT NULL,
  `END_TIME` varchar(14) NOT NULL,
  `PERIOD_TIME` int(10) NOT NULL,
  `SIZE_USED` float NOT NULL,
  `STANDARD_ID` varchar(30) NOT NULL,
  `STANDARD_NAME` varchar(100) NOT NULL,
  `OWN_USER` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  PRIMARY KEY (`EBS_ID`,`START_TIME`,`END_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_ebs_res_meterage_t`
-- ----------------------------
CREATE TABLE `comp_ebs_res_meterage_t` (
  `EBS_ID` varchar(30) NOT NULL,
  `START_TIME` varchar(14) NOT NULL,
  `END_TIME` varchar(14) NOT NULL,
  `PERIOD_TIME` int(10) NOT NULL,
  `RUN_TIME` float NOT NULL,
  `STANDARD_ID` varchar(30) NOT NULL,
  `STANDARD_NAME` varchar(100) NOT NULL,
  `OWN_USER` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_item_audit_log_t`
-- ----------------------------
CREATE TABLE `comp_item_audit_log_t` (
  `ITEM_ID` varchar(30) NOT NULL,
  `STATUS` char(1) NOT NULL,
  `AUDIT_TIME` char(14) NOT NULL,
  `AUDIT_USER` varchar(20) NOT NULL,
  `AUDIT_INFO` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_item_release_log_t`
-- ----------------------------
CREATE TABLE `comp_item_release_log_t` (
  `ITEM_ID` varchar(30) NOT NULL,
  `STATUS` char(1) NOT NULL,
  `RELEASE_TIME` char(14) NOT NULL,
  `RELEASE_USER` varchar(20) NOT NULL,
  `RELEASE_INFO` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_item_t`
-- ----------------------------
CREATE TABLE `comp_item_t` (
  `ITEM_ID` varchar(30) NOT NULL,
  `ITEM_NAME` varchar(50) DEFAULT NULL,
  `ITEM_TYPE` varchar(2) DEFAULT NULL,
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `STANDARD_TYPE` varchar(2) DEFAULT NULL,
  `CATALOG_ID` varchar(50) DEFAULT NULL,
  `RECOMMEND` char(1) DEFAULT NULL,
  `STATUS` char(1) DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_TIME` char(14) DEFAULT NULL,
  `CREATE_USER` varchar(20) DEFAULT NULL,
  `UPDATE_TIME` char(14) DEFAULT NULL,
  `UPDATE_USER` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ITEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_service_code_t`
-- ----------------------------
CREATE TABLE `comp_service_code_t` (
  `SERVICE_ID` varchar(30) NOT NULL,
  `SERVICE_NAME` varchar(50) NOT NULL,
  `REMARK` varchar(200) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`SERVICE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_order_audit_log_t`
-- ----------------------------
CREATE TABLE `comp_order_audit_log_t` (
  `ORDER_ID` char(30) NOT NULL,
  `STATUS` char(1) NOT NULL,
  `AUDIT_TIME` char(14) NOT NULL,
  `AUDIT_USER` varchar(20) NOT NULL,
  `AUDIT_INFO` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_order_t`
-- ----------------------------
CREATE TABLE `comp_order_t` (
  `ORDER_ID` varchar(30) NOT NULL,
  `PARENT_ID` varchar(50) NOT NULL,
  `STATUS` char(1) DEFAULT NULL,
  `EFFECTIVE_TIME` char(14) DEFAULT NULL,
  `LENGTH_TIME` int(10) DEFAULT NULL,
  `LENGTH_UNIT` char(1) DEFAULT NULL,
  `CREATE_TIME` char(14) DEFAULT NULL,
  `CREATE_USER` varchar(20) DEFAULT NULL,
  `UPDATE_TIME` char(14) DEFAULT NULL,
  `UPDATE_USER` varchar(20) DEFAULT NULL,
  `CASE_ID` varchar(30) DEFAULT NULL,
  `CASE_TYPE` varchar(2) DEFAULT NULL,
  `EXPIRE_TIME` char(14) DEFAULT NULL,
  `APP_ID` varchar(64) DEFAULT NULL,
  `RES_POOL_ID` varchar(30) DEFAULT NULL,
  `vmModifyBatchId`  varchar(50) NULL COMMENT '虚拟机批量审批订单id',
  PRIMARY KEY (`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_os_relate_t`
-- ----------------------------
CREATE TABLE `comp_os_relate_t` (
  `OS_ID` varchar(30) DEFAULT NULL,
  `RES_POOL_PART_ID` varchar(30) DEFAULT NULL,
  `RES_POOL_ID` varchar(30) DEFAULT NULL,
  `STATUS` char(1) DEFAULT NULL,
  `CREATE_TIME` char(14) DEFAULT NULL,
  `CREATE_USER` varchar(20) DEFAULT NULL,
  `UPDATE_TIME` char(14) DEFAULT NULL,
  `UPDATE_USER` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_os_t`
-- ----------------------------
CREATE TABLE `comp_os_t` (
  `OS_ID` varchar(30) NOT NULL,
  `OS_TYPE` varchar(50) NOT NULL,
  `OS` varchar(50) NOT NULL,
  `OS_NAME` varchar(150) DEFAULT NULL,
  `Resource_TYPE` varchar(1) DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_TIME` char(14) DEFAULT NULL,
  `CREATE_USER` varchar(20) DEFAULT NULL,
  `UPDATE_TIME` char(14) DEFAULT NULL,
  `UPDATE_USER` varchar(20) DEFAULT NULL,
  `OS_VER` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_pm_create_fail_t`
-- ----------------------------
CREATE TABLE `comp_pm_create_fail_t` (
  `FAIL_CAUSE` varchar(128) DEFAULT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `SUBNETWORK` varchar(30) DEFAULT NULL,
  `NUM` int(10) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_pm_del_fail_t`
-- ----------------------------
CREATE TABLE `comp_pm_del_fail_t` (
  `PM_ID` varchar(30) NOT NULL,
  `FAIL_CAUSE` varchar(128) NOT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `SERIAL_NUM` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_pm_query_fail_t`
-- ----------------------------
CREATE TABLE `comp_pm_query_fail_t` (
  `PM_ID` varchar(30) NOT NULL,
  `FAIL_CAUSE` varchar(128) NOT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `FUTURE_STATE` varchar(2) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `SERIAL_NUM` varchar(26) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_pm_res_count_t`
-- ----------------------------
CREATE TABLE `comp_pm_res_count_t` (
  `PM_ID` varchar(30) NOT NULL,
  `START_TIME` varchar(14) NOT NULL,
  `END_TIME` varchar(14) NOT NULL,
  `PERIOD_TIME` int(10) NOT NULL,
  `RUN_TIME` float NOT NULL,
  `STANDARD_ID` varchar(30) NOT NULL,
  `STANDARD_NAME` varchar(100) NOT NULL,
  `OWN_USER` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  PRIMARY KEY (`PM_ID`,`START_TIME`,`END_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_res_pool_t`
-- ----------------------------
CREATE TABLE `comp_res_pool_t` (
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_NAME` varchar(50) NOT NULL,
  `RES_POOL_ZONE` varchar(50) NOT NULL,
  `RES_POOL_URL` varchar(200) NOT NULL,
  `USER_ID` varchar(50) NOT NULL,
  `USER_PWD` varchar(200) NOT NULL,
  `STATUS` varchar(1) NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`RES_POOL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_res_pool_part_t`
-- ----------------------------
CREATE TABLE `comp_res_pool_part_t` (
  `RES_POOL_PART_ID` varchar(30) NOT NULL,
  `RES_POOL_PART_NAME` varchar(50) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_NAME` varchar(50) DEFAULT NULL,
  `STATUS` varchar(1) NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CPU_NUM_TOTAL` int(11) DEFAULT NULL,
  `RAM_SIZE_TOTAL` int(11) DEFAULT NULL,
  `DISC_SIZE_TOTAL` int(11) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL,
  PRIMARY KEY (`RES_POOL_PART_ID`,`RES_POOL_ID`),
  KEY `FK_COMP_RES_REFERENCE_COMP_RES` (`RES_POOL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_sm_mail_time_t`
-- ----------------------------
CREATE TABLE `comp_sm_mail_time_t` (
  `USER_ID` varchar(20) NOT NULL,
  `KEYWORD` varchar(128) NOT NULL,
  `TIMESTAMP` char(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_standard_ebs_t`
-- ----------------------------
CREATE TABLE `comp_standard_ebs_t` (
  `STANDARD_ID` varchar(30) NOT NULL,
  `DISC_SIZE` int(10) NOT NULL,
  `ResourceType` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL,
  `STATUS` char(1) NOT NULL DEFAULT '0',
  `STANDARD_NAME` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_standard_iso_t`
-- ----------------------------
CREATE TABLE `comp_standard_iso_t` (
  `TEMPLATE_ID` varchar(30) NOT NULL,
  `TEMPLATE_NAME` varchar(100) DEFAULT NULL,
  `OS_ID` varchar(30) NOT NULL,
  `STANDARD_ID` varchar(30) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `STATUS` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_standard_pm_t`
-- ----------------------------
CREATE TABLE `comp_standard_pm_t` (
  `STANDARD_ID` varchar(30) NOT NULL,
  `STANDARD_NAME` varchar(100) NOT NULL,
  `SERVER_TYPE` varchar(128) NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `STATUS` char(1) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_standard_syn_t`
-- ----------------------------
CREATE TABLE `comp_standard_syn_t` (
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `RES_POOL_PART_ID` varchar(30) DEFAULT NULL,
  `RES_POOL_ID` varchar(30) DEFAULT NULL,
  `STANDARD_TYPE` varchar(2) DEFAULT NULL,
  `STATUS` char(1) DEFAULT NULL,
  `SYN_TIME` char(14) DEFAULT NULL,
  `SYN_USER` varchar(20) DEFAULT NULL,
  `TEMPLATE_ID` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_standard_vm_t`
-- ----------------------------
CREATE TABLE `comp_standard_vm_t` (
  `STANDARD_ID` varchar(30) NOT NULL,
  `STANDARD_NAME` varchar(100) NOT NULL,
  `CPU_NUM` int(10) NOT NULL,
  `RAM_SIZE` int(10) NOT NULL,
  `DISC_SIZE` int(10) NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `STATUS` char(1) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `CREATE_USER` varchar(20) NOT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_sys_audit_switch_t`
-- ----------------------------
CREATE TABLE `comp_sys_audit_switch_t` (
  `FLOW_ID` char(5) NOT NULL,
  `FLOW_NAME` varchar(50) NOT NULL,
  `NODE_ID` char(5) NOT NULL,
  `NODE_NAME` varchar(50) NOT NULL,
  `STATUS` char(1) NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_sys_conf_default_t`
-- ----------------------------
CREATE TABLE `comp_sys_conf_default_t` (
  `CONF_ID` char(5) NOT NULL,
  `CONF_NAME` varchar(50) NOT NULL,
  `CONF_VAL` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `UPDATE_TIME` char(14) NOT NULL,
  `UPDATE_USER` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_user_audit_log_t`
-- ----------------------------
CREATE TABLE `comp_user_audit_log_t` (
  `USER_ID` varchar(20) NOT NULL,
  `STATUS` char(1) NOT NULL,
  `AUDIT_TIME` char(14) NOT NULL,
  `AUDIT_USER` varchar(64) NOT NULL,
  `AUDIT_INFO` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vmbak_query_fail_t`
-- ----------------------------
CREATE TABLE `comp_vmbak_query_fail_t` (
  `VMBAK_ID` varchar(32) NOT NULL,
  `FAIL_CAUSE` varchar(128) NOT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `FUTURE_STATE` varchar(100) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `SERIAL_NUM` varchar(26) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vmbak_create_fail_t`
-- ----------------------------
CREATE TABLE `comp_vmbak_create_fail_t` (
  `FAIL_CAUSE` varchar(128) DEFAULT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `VM_ID` varchar(30) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vmbak_del_fail_t`
-- ----------------------------
CREATE TABLE `comp_vmbak_del_fail_t` (
  `FAIL_CAUSE` varchar(128) DEFAULT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `VMBAK_ID` varchar(32) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `SERIAL_NUM` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vmbak_res_count_t`
-- ----------------------------
CREATE TABLE `comp_vmbak_res_count_t` (
  `VMBAK_ID` varchar(32) NOT NULL,
  `START_TIME` varchar(14) NOT NULL,
  `END_TIME` varchar(14) NOT NULL,
  `PERIOD_TIME` int(10) NOT NULL,
  `SIZE_USED` float NOT NULL,
  `STANDARD_ID` varchar(30) NOT NULL,
  `STANDARD_NAME` varchar(100) NOT NULL,
  `OWN_USER` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  PRIMARY KEY (`VMBAK_ID`,`START_TIME`,`END_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vmbak_res_meterage_t`
-- ----------------------------
CREATE TABLE `comp_vmbak_res_meterage_t` (
  `VMBAK_ID` varchar(32) NOT NULL,
  `START_TIME` varchar(14) NOT NULL,
  `END_TIME` varchar(14) NOT NULL,
  `PERIOD_TIME` int(10) NOT NULL,
  `RUN_TIME` float NOT NULL,
  `STANDARD_ID` varchar(30) NOT NULL,
  `STANDARD_NAME` varchar(100) NOT NULL,
  `OWN_USER` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vmbak_rollback_fail_t`
-- ----------------------------
CREATE TABLE `comp_vmbak_rollback_fail_t` (
  `FAIL_CAUSE` varchar(128) DEFAULT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `VMBAK_ID` varchar(32) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `SERIAL_NUM` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vm_create_fail_t`
-- ----------------------------
CREATE TABLE `comp_vm_create_fail_t` (
  `FAIL_CAUSE` varchar(128) DEFAULT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `SUBNETWORK` varchar(30) DEFAULT NULL,
  `OS_ID` varchar(30) DEFAULT NULL,
  `NUM` int(10) DEFAULT NULL,
  `CREATE_TIME` char(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vm_modify_fail_t`
-- ----------------------------
CREATE TABLE `comp_vm_modify_fail_t` (
  `FAIL_CAUSE` varchar(128) DEFAULT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `VM_ID` varchar(30) DEFAULT NULL,
  `CPU_NUM` int(11) NOT NULL,
  `RAM_SIZE` int(11) NOT NULL,
  `DISC_SIZE_ADD` int(11) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vm_del_fail_t`
-- ----------------------------
CREATE TABLE `comp_vm_del_fail_t` (
  `VM_ID` varchar(30) NOT NULL,
  `FAIL_CAUSE` varchar(128) NOT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `SERIAL_NUM` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vm_query_fail_t`
-- ----------------------------
CREATE TABLE `comp_vm_query_fail_t` (
  `VM_ID` varchar(30) NOT NULL,
  `FAIL_CAUSE` varchar(128) NOT NULL,
  `FAIL_CODE` varchar(20) NOT NULL,
  `RES_POOL_ID` varchar(20) NOT NULL,
  `RES_POOL_PART_ID` varchar(20) NOT NULL,
  `FUTURE_STATE` varchar(2) NOT NULL,
  `CREATE_TIME` char(14) NOT NULL,
  `SERIAL_NUM` varchar(26) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comp_vm_res_count_t`
-- ----------------------------
CREATE TABLE `comp_vm_res_count_t` (
  `VM_ID` varchar(30) NOT NULL,
  `START_TIME` varchar(14) NOT NULL,
  `END_TIME` varchar(14) NOT NULL,
  `PERIOD_TIME` int(10) NOT NULL,
  `RUN_TIME` float NOT NULL,
  `STANDARD_ID` varchar(30) DEFAULT NULL,
  `STANDARD_NAME` varchar(100) DEFAULT NULL,
  `OWN_USER` varchar(20) DEFAULT NULL,
  `RES_POOL_ID` varchar(20) DEFAULT NULL,
  `RES_POOL_PART_ID` varchar(20) DEFAULT NULL,
  `CREATE_TIME` char(14) DEFAULT NULL,
  PRIMARY KEY (`VM_ID`,`START_TIME`,`END_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*湖北运营新增表*/
-- -------------------------------------------------------------
--  Table structure for `comp_rm_machineRoom_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_machineRoom_t` (
  `MACHINEROOM_ID` varchar(64) NOT NULL COMMENT '机房ID',
  `MACHINEROOM_NAME` varchar(64) COMMENT '机房名称',
  `RESPONSIBLE` varchar(64) COMMENT '机房负责人名称',
  `ADDRESS` varchar(128) COMMENT '地址',
  `MACHINEROOM_LEVEL` char(1) COMMENT '机房级别 0：五星级；1：四星级；2：三星级；3：二星级',
  `MACHINEROOM_AREA` varchar(11) COMMENT '机房面积',
  `DESCRIPTION` varchar(128) COMMENT '描述',
  `RES_POOL_ID` varchar(32) COMMENT '资源池ID',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`MACHINEROOM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_cabinet_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_cabinet_t` (
  `CABINET_ID` varchar(64) NOT NULL COMMENT '机柜ID',
  `CABINET_NAME` varchar(64) COMMENT '机柜名称',
  `MANUFACTORYNAME` varchar(64) COMMENT '厂家名称',
  `BRANDMODEL` varchar(64) COMMENT '机柜型号',
  `DESCRIPTION` varchar(256) COMMENT '描述',
  `MACHINEROOM_ID` varchar(64) COMMENT '所属机房',
  `RES_POOL_ID` varchar(32) COMMENT '资源池ID',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`CABINET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_pm_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_pm_t` (
  `PM_ID` varchar(64) NOT NULL COMMENT '物理机设备ID',
  `PM_NAME` varchar(64) COMMENT '物理机名称',
  `PM_INSTANCE_ID` varchar(64) COMMENT '物理机实例ID',
  `IP` varchar(32) COMMENT 'IP地址',
  `NETMASK` varchar(64) COMMENT '网络掩码',
  `CLUSTER_IP` varchar(32) COMMENT '绑定的cluster浮动IP地址',
  `SERVER_TYPE` varchar(128) COMMENT '主机型号',
  `CPU_NUM` varchar(11) COMMENT 'CPU数量',
  `CPU_TYPE` varchar(128) COMMENT 'CPU类型',
  `CPU_FREQUENCY` varchar(11) COMMENT 'CPU主频（单位：MHz）',
  `NUC_NUM_PER_CPU` varchar(11) COMMENT '主机单CPU核数',
  `MEMORY_SIZE` varchar(11) COMMENT '内存容量（单位：MB）',
  `MEMORY_NUM` varchar(11) COMMENT '内存条数量',
  `DISK_SIZE` varchar(11) COMMENT '磁盘空间（单位：GB）',
  `DISK_NUM` varchar(11) COMMENT '硬盘个数',
  `IF_NUM` varchar(11) COMMENT '系统网络接口总数',
  `MAINBORD_NUM` varchar(11) COMMENT '主板数量',
  `POWERMODULE_NUM` varchar(11) COMMENT '电源模块数量',
  `FAN_NUM` varchar(11) COMMENT '风扇数量',
  `VENDOR_NAME` varchar(64) COMMENT '设备厂商',
  `RUN_TIME` char(14) COMMENT '投入生产运行的时间',
  `CUR_STATUS` char(1) COMMENT '当前状态 0：不可用；1：已加电；2：运行；3：关机；4：休眠；5：处理中',
  `SERIALNUM` varchar(64) COMMENT '设备序列号',
  `APP_ID` varchar(64) COMMENT '业务ID',
  `RES_POOL_ID` varchar(32) COMMENT '资源池ID',
  `RES_POOL_PART_ID` varchar(32) COMMENT '资源池分区ID',
  `OS_TYPE` varchar(64) COMMENT '操作系统',
  `ETHADA_NUM` varchar(11) COMMENT '网卡个数',
  `ETHADA_TYPE` varchar(64) COMMENT '网卡规格',
  `SCSIADA_NUM` varchar(11) COMMENT 'SCSI卡个数',
  `HBA_NUM` varchar(11) COMMENT 'HBA卡个数',
  `HBA_TYPE` varchar(64) COMMENT 'HBA卡规格',
  `USED_FLAG` char(1) COMMENT '应用标识 1：X86物理机，单机提供计算资源；2：虚拟化，虚拟机的宿主机；3：用于存储集群',
  `HV_INFO` varchar(64) COMMENT '虚拟化软件信息（此信息仅应用标识为2时有效）',
  `HVPOOL_ID` varchar(64) COMMENT '虚拟化集群标识（此信息仅应用标识为2时有效）',
  `SWITCH_IF_RELATIONS` varchar(128) COMMENT '连接到的交换机及端口标识',
  `ASSET_ORIGIN_TYPE` char(1) COMMENT '资产来源分类 1：自产；2：外购；3：借入；4：订单；5：第三方监控源；6：可自义',
  `ASSET_STATE` char(1) COMMENT '资产状态 1：已使用；2：未使用；3：不可用；4：丢失；5：待确认；6：已删除',
  `ASSET_SLA_TYPE` char(1) COMMENT '资产SLA类型 1：提供服务；2：平台自服务',
  `MACHINEROOM_ID` varchar(64) COMMENT '所属机房',
  `CABINET_ID` varchar(64) COMMENT '所属机柜',
  `CPU_MODEL`	VARCHAR(64)	COMMENT 'CPU型号',
  `NTP_IP`	VARCHAR(64)	COMMENT 'NTP服务IP',
  `MAINTENANCE_FACTORY`	VARCHAR(64)	COMMENT '维保厂商',
  `GATEWAY`	VARCHAR(64)	COMMENT '网关',
  `HB_DESCRIPTION`	VARCHAR(64)	COMMENT '互备方式描述',
  `BK_PM`	VARCHAR(64)	COMMENT '备份主机',
  `BUSINESS_CONTACT`	VARCHAR(64)	COMMENT '业务联系人',
  `SYS_EXCHANGE_AREA_SIZE`	VARCHAR(64)	COMMENT '系统交换区大小',
  `ONLINE_TIME`	VARCHAR(64)	COMMENT '上线时间',
  `TCPP_VALUE`	VARCHAR(11)	COMMENT 'TCPP值',
  `APPLICATION_DESCRIBE` VARCHAR(64) COMMENT '应用描述',
  `PORT_LABEL` VARCHAR(64)	COMMENT '端口标签',
  `CPUEQP_SERIALNUM` VARCHAR(64) COMMENT 'CPU序列号',
  `PM_STATE` VARCHAR(64) COMMENT '物理机分配状态（非宿主机）0：分配；1：未分配',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`PM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_vm_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_vm_t` (
  `VM_ID` varchar(64) NOT NULL COMMENT '虚拟机实例ID',
  `VM_NAME` varchar(64) COMMENT '虚拟机名称',
  `VM_EQP_ID` varchar(64) COMMENT '虚拟机设备ID（表示物理机上的第几个虚拟机）',
  `PRIVATE_IP` varchar(32) COMMENT '私网IP',
  `NETMASK` varchar(64) COMMENT '网络掩码',
  `CLUSTER_IP` varchar(32) COMMENT '绑定的cluster浮动IP地址',
  `CPU_NUM` varchar(11) COMMENT 'CPU数量',
  `CPU_FREQUENCY` varchar(11) COMMENT 'CPU主频（单位：MHz）',
  `MEMORY_SIZE` varchar(11) COMMENT '内存容量（单位：MB）',
  `DISK_SIZE` varchar(11) COMMENT '磁盘空间（单位：GB）',
  `RUN_TIME` char(14) COMMENT '投入生产运行的时间',
  `CUR_STATUS` char(1) COMMENT '当前状态 0：不可用；1：已加电；2：运行；3：关机；4：挂起；5：处理中',
  `APP_ID` varchar(64) COMMENT '业务ID',
  `RES_POOL_ID` varchar(32) COMMENT '资源池ID',
  `RES_POOL_PART_ID` varchar(32) COMMENT '资源池分区ID',
  `V_ETHADA_NUM` varchar(11) COMMENT '虚拟网卡个数',
  `V_SCSIADA_NUM` varchar(11) COMMENT '虚拟SCSI卡个数',
  `V_FCHBA_NUM` varchar(11) COMMENT '虚拟FC-HBA卡个数',
  `OS_TYPE` varchar(64) COMMENT '操作系统盘类型',
  `VM_OS` varchar(64) COMMENT '操作系统',
  `PM_ID` varchar(64) COMMENT '所属物理机设备ID',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`VM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_ebs_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_ebs_t` (
  `EBS_ID` varchar(64) NOT NULL COMMENT '弹性块存储实例ID',
  `EBS_NAME` varchar(64) COMMENT '弹性块存储名称',
  `EBS_EQP_ID` varchar(64) COMMENT '弹性块存储设备ID（表示存储阵列上的第几卷）',
  `TIER` varchar(32) COMMENT '存储性能级别 0：SSD盘；1：FC盘；2：SATA盘',
  `DISK_SIZE` varchar(11) COMMENT '磁盘空间（单位：GB）',
  `CUR_STATUS` char(1) COMMENT '当前状态 1：已创建；2：已挂载；3：创建失败；4：挂载失败；5：创建中',
  `APP_ID` varchar(64) COMMENT '业务ID',
  `RES_POOL_ID` varchar(32) COMMENT '资源池ID',
  `PARENT_TYPE` char(1) COMMENT '弹性块存储挂载的资源类型 0：小型机；1：小型机分区；2：物理机；3：虚拟机',
  `PARENT_ID` varchar(64) COMMENT '挂载的资源ID（物理机和虚拟机都是资源实例ID）',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`EBS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_raid_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_raid_t` (
  `RAID_ID` varchar(64) NOT NULL COMMENT '存储陈列编码ID',
  `INSTANCE_ID` varchar(64) COMMENT '存储陈列实例ID',
  `RAID_NAME` varchar(64) COMMENT '存储陈列名称',
  `VENDOR_NAME` varchar(32) COMMENT '厂家名称',
  `SA_TYPE` varchar(64) COMMENT '存储阵列类型',
  `SA_MICROCODE_VER` varchar(32) COMMENT '存储微码版本',
  `SA_CAPACITY` varchar(11) COMMENT '存储配置容量，单位GB',
  `SA_IP` varchar(15) COMMENT '阵列IP地址',
  `RELATED_EQP_ID` varchar(128) COMMENT '依附的设备ID列表',
  `CACHE_CAPACITY` varchar(64) COMMENT '存储CACHE容量，单位MB',
  `DISK_IDS` varchar(128) COMMENT '磁盘标识列表',
  `DISK_SPECIFICATION` varchar(64) COMMENT '磁盘的规格',
  `HBA_IDS` varchar(128) COMMENT '主机通道卡标识列表',
  `RES_POOL_ID` varchar(32) NOT NULL COMMENT '资源池ID',
  `HBA_TYPES` varchar(32) COMMENT '主机通道卡类型',
  `HBA_NUM` varchar(11) COMMENT '主机通道卡数目',
  `DISK_ADAPTOR_ID` varchar(128) COMMENT '磁盘适配卡标识列表',
  `DISK_ADAPTOR_TYPE` varchar(32) COMMENT '磁盘适配卡类型',
  `EQP_SERIALNUM` varchar(64) COMMENT '设备序列号',
  `ASSET_STATE` char(1) COMMENT '资产状态',
  `ASSET_SLA_TYPE` char(1) COMMENT '资产SLA类型',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`RAID_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_switch_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_switch_t` (
  `SWITCH_ID` varchar(64) NOT NULL COMMENT '交换机ID',
  `SWITCH_NAME` varchar(64) COMMENT '交换机名称',
  `SWITCH_TYPE` varchar(64) COMMENT '交换机型号',
  `SW_VERSION` varchar(64) COMMENT '设备软件版本',
  `VENDOR_NAME` varchar(64) COMMENT '网元设备厂家',
  `SWITCH_IP` varchar(20) COMMENT '网元IP地址',
  `CUR_STATUS` char(1) COMMENT '当前状态',
  `SWITCH_SERIALNUM` varchar(64) COMMENT '设备序列号',
  `ASSET_ORIGIN_TYPE` char(1) COMMENT '资产来源分类',
  `ASSET_STATE` char(1) COMMENT '资产状态',
  `ASSET_SLA_TYPE` char(1) COMMENT '资产SLA类型',
  `RES_POOL_ID` varchar(32) NOT NULL COMMENT '资源池ID',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`SWITCH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_switch_if_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_switch_if_t` (
  `SWITCH_IF_ID` varchar(64) NOT NULL COMMENT '端口ID',
  `SWITCH_IF_NAME` varchar(64) COMMENT '端口名称',
  `SWITCH_ID` varchar(64) COMMENT '所属交换机ID',
  `IF_DESCRIPTION` varchar(64) COMMENT '端口描述',
  `IF_STATUS` varchar(64) COMMENT '端口状态',
  `IF_TYPE` varchar(64) COMMENT '端口类型',
  `IF_SET_MAC_ADDR` varchar(64) COMMENT '端口已配置要绑定的MAC地址',
  `IF_REAL_MAC_ADDR` varchar(1024) COMMENT '实际连接到的设备的MAC地址',
  `IF_CONNECT_EQP_IP` varchar(1024) COMMENT '端口所连接设备的IP地址',
  `VLAN_ID` varchar(1024) COMMENT 'VLAN',
  `IF_SPEED` varchar(10) COMMENT '端口速率（单位kbps）',
  `DEST_IF_ID` varchar(64) COMMENT '端口所连对端端口唯一标识',
  `RES_POOL_ID` varchar(32) NOT NULL COMMENT '资源池ID',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`SWITCH_IF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_router_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_router_t` (
  `ROUTER_ID` varchar(64) NOT NULL COMMENT '路由器ID',
  `ROUTER_NAME` varchar(64) COMMENT '路由器名称',
  `ROUTER_TYPE` varchar(64) COMMENT '设备型号',
  `SW_VERSION` varchar(64) COMMENT '设备软件版本',
  `VENDOR_NAME` varchar(64) COMMENT '网元设备厂家',
  `ROUTER_IP` varchar(20) COMMENT '网元IP地址',
  `CUR_STATUS` char(1) COMMENT '当前状态',
  `ROUTER_SERIALNUM` varchar(64) COMMENT '设备序列号',
  `ASSET_ORIGIN_TYPE` char(1) COMMENT '资产来源分类',
  `ASSET_STATE` char(1) COMMENT '资产状态',
  `ASSET_SLA_TYPE` char(1) COMMENT '资产SLA类型',
  `RES_POOL_ID` varchar(32) NOT NULL COMMENT '资源池ID',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ROUTER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_router_if_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_router_if_t` (
  `ROUTER_IF_ID` varchar(64) NOT NULL COMMENT '端口ID',
  `ROUTER_IF_NAME` varchar(64) COMMENT '端口名称',
  `ROUTER_ID` varchar(64) COMMENT '所属路由器ID',
  `IF_DESCRIPTION` varchar(64) COMMENT '端口描述',
  `IF_STATUS` varchar(64) COMMENT '端口状态',
  `IF_TYPE` varchar(64) COMMENT '端口类型',
  `IF_SET_LOCAL_IP` varchar(64) COMMENT '端口本端配置IP地址',
  `IP_SUB_NETMASK` varchar(64) COMMENT '端口IP网络掩码',
  `IF_CONNECT_EQP_IP` varchar(20) COMMENT '端口所连接设备的IP地址',
  `IF_PHY_ADDRESS` varchar(64) COMMENT '端口物理地址',
  `IF_SPEED` varchar(10) COMMENT '端口速率',
  `DEST_IF_ID` varchar(64) COMMENT '端口所连对端端口唯一标识',
  `RES_POOL_ID` varchar(32) NOT NULL COMMENT '资源池ID',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ROUTER_IF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_firewall_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_firewall_t` (
  `FIREWALL_ID` varchar(64) NOT NULL COMMENT '防火墙ID',
  `FIREWALL_NAME` varchar(64) COMMENT '防火墙名称',
  `FW_TYPE` varchar(64) COMMENT '型号',
  `SW_VERSION` varchar(64) COMMENT '设备软件版本',
  `VENDOR_NAME` varchar(64) COMMENT '网元设备厂家',
  `FW_IP` varchar(20) COMMENT '网元IP地址',
  `CUR_STATUS` char(1) COMMENT '当前状态',
  `FW_SERIALNUM` varchar(64) COMMENT '设备序列号',
  `THROUGHPUT` varchar(10) COMMENT '吞吐能力',
  `APP_IDS` varchar(128) COMMENT '所属业务系统',
  `CONNECT_NUM` varchar(10) COMMENT '并发链接数',
  `NEW_CONNECT_NUM` varchar(10) COMMENT '新建链接数',
  `ASSET_ORIGIN_TYPE` char(1) COMMENT '资产来源分类',
  `ASSET_STATE` char(1) COMMENT '资产状态',
  `ASSET_SLA_TYPE` char(1) COMMENT '资产SLA类型',
  `PORT_NUM` varchar(10) COMMENT '端口数量',
  `FIREWALL_POLICY` varchar(64) COMMENT '防火墙策略',
  `RES_POOL_ID` varchar(32) NOT NULL COMMENT '资源池ID',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`FIREWALL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_capacity_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_capacity_t` (
  `RES_POOL_ID` varchar(32) NOT NULL COMMENT '资源池ID',
  `RES_POOL_PART_ID` varchar(32) NOT NULL COMMENT '资源池分区ID',
  `RES_TYPE` varchar(2) NOT NULL COMMENT '资源类型 0：VCPU；1：虚拟化内存；2：虚拟化磁盘；3：物理机',
  `RES_TOTAL` varchar(11) COMMENT '资源总量',
  `RES_USED` varchar(11) COMMENT '已用资源占用率百分数（如：82表示82%）',
  `CREATE_TIME` char(14) NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_minipm_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_minipm_rt_t` (
  `MINIPM_ID` varchar(64) NOT NULL COMMENT '小型机ID',
  `CPU_PROCESSOR_UTILIZATION` varchar(11) COMMENT 'CPU使用率',
  `CPU_SYS_TIME` varchar(11) COMMENT 'CPU时间：系统百分比',
  `CPU_USER_TIME` varchar(11) COMMENT 'CPU时间：用户百分比',
  `CPU_IDLE_TIME` varchar(11) COMMENT 'CPU时间：等待百分比',
  `MEM_USED_PER` varchar(11) COMMENT '内存使用率',
  `SYS_MEM_USED_PER` varchar(11) COMMENT '系统内存使用率',
  `USER_MEM_USED_PER` varchar(11) COMMENT '用户内存使用率',
  `MEM_FREE` varchar(11) COMMENT '可用内存量（单位：KB）',
  `DISK_USED_PER` varchar(11) COMMENT '磁盘使用率',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_minipmpar_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_minipmpar_rt_t` (
  `MINIPMPAR_ID` varchar(64) NOT NULL COMMENT '小型机分区ID',
  `CPU_PROCESSOR_UTILIZATION` varchar(11) COMMENT 'CPU使用率',
  `CPU_SYS_TIME` varchar(11) COMMENT 'CPU时间：系统百分比',
  `CPU_USER_TIME` varchar(11) COMMENT 'CPU时间：用户百分比',
  `CPU_IDLE_TIME` varchar(11) COMMENT 'CPU时间：等待百分比',
  `MEM_USED_PER` varchar(11) COMMENT '内存使用率',
  `SYS_MEM_USED_PER` varchar(11) COMMENT '系统内存使用率',
  `USER_MEM_USED_PER` varchar(11) COMMENT '用户内存使用率',
  `MEM_FREE` varchar(11) COMMENT '可用内存量（单位：KB）',
  `DISK_USED_PER` varchar(11) COMMENT '磁盘使用率',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_pm_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_pm_rt_t` (
  `PM_ID` varchar(64) NOT NULL COMMENT '物理机ID',
  `CPU_PROCESSOR_UTILIZATION` varchar(11) COMMENT 'CPU使用率',
  `CPU_SYS_TIME` varchar(11) COMMENT 'CPU时间：系统百分比',
  `CPU_USER_TIME` varchar(11) COMMENT 'CPU时间：用户百分比',
  `CPU_IDLE_TIME` varchar(11) COMMENT 'CPU时间：等待百分比',
  `MEM_USED_PER` varchar(11) COMMENT '内存使用率',
  `SYS_MEM_USED_PER` varchar(11) COMMENT '系统内存使用率',
  `USER_MEM_USED_PER` varchar(11) COMMENT '用户内存使用率',
  `MEM_FREE` varchar(11) COMMENT '可用内存量（单位：KB）',
  `DISK_USED_PER` varchar(11) COMMENT '磁盘使用率',
  `SWAP_MEM_TOTAL` varchar(11) COMMENT 'SWAP内存总量',
  `SWAP_MEM_USED` varchar(11) COMMENT 'SWAP内存使用量',
  `SWAP_MEM_USED_PER` varchar(11) COMMENT 'SWAP内存使用率',
  `disk_IO_speed` VARCHAR(11),
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_vm_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_vm_rt_t` (
  `VM_ID` varchar(64) NOT NULL COMMENT '虚拟机ID',
  `CPU_PROCESSOR_UTILIZATION` varchar(11) COMMENT 'CPU使用率',
  `CPU_SYS_TIME` varchar(11) COMMENT 'CPU时间：系统百分比',
  `CPU_USER_TIME` varchar(11) COMMENT 'CPU时间：用户百分比',
  `CPU_IDLE_TIME` varchar(11) COMMENT 'CPU时间：等待百分比',
  `MEM_USED_PER` varchar(11) COMMENT '内存使用率',
  `SYS_MEM_USED_PER` varchar(11) COMMENT '系统内存使用率',
  `USER_MEM_USED_PER` varchar(11) COMMENT '用户内存使用率',
  `MEM_FREE` varchar(11) COMMENT '可用内存量（单位：KB）',
  `DISK_USED_PER` varchar(11) COMMENT '磁盘使用率',
  `SWAP_MEM_TOTAL` varchar(11) COMMENT 'SWAP内存总量',
  `SWAP_MEM_USED` varchar(11) COMMENT 'SWAP内存使用量',
  `SWAP_MEM_USED_PER` varchar(11) COMMENT 'SWAP内存使用率',
  `disk_IO_speed` VARCHAR(11),
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_pm_disk_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_pm_disk_rt_t` (
  `DISK_ID` varchar(64) NOT NULL COMMENT '磁盘ID',
  `DISK_NAME` varchar(64) COMMENT '磁盘名称',
  `PM_ID` varchar(64) NOT NULL COMMENT '物理机ID',
  `DISK_TYPE` char(1) COMMENT '磁盘外挂标识:1：蹦迪磁盘,2：外挂磁盘',
  `DISK_READ_BUSY_PER` varchar(11) COMMENT '磁盘忙的百分比（读）',
  `DISK_WRITE_BUSY_PER` varchar(11) COMMENT '磁盘忙的百分比（写）',
  `DISK_READ` varchar(11) COMMENT '每秒磁盘读请求（单位：KB/s）',
  `DISK_WRITE` varchar(11) COMMENT '每秒磁盘写请求（单位：KB/s）',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_vm_disk_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_vm_disk_rt_t` (
  `DISK_ID` varchar(64) NOT NULL COMMENT '磁盘ID',
  `DISK_NAME` varchar(64) COMMENT '磁盘名称',
  `VM_ID` varchar(64) NOT NULL COMMENT '物理机ID',
  `DISK_READ_BUSY_PER` varchar(11) COMMENT '磁盘忙的百分比（读）',
  `DISK_WRITE_BUSY_PER` varchar(11) COMMENT '磁盘忙的百分比（写）',
  `DISK_READ` varchar(11) COMMENT '每秒磁盘读请求（单位：KB/s）',
  `DISK_WRITE` varchar(11) COMMENT '每秒磁盘写请求（单位：KB/s）',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_device_week_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_device_week_t` (
  `DEVICE_ID` varchar(64) NOT NULL COMMENT '设备ID',
  `DEVICE_TYPE` varchar(1) NOT NULL COMMENT '设备类型:0：小型机,1：小型机分区,2：物理机,3：虚拟机',
  `CPU_PROCESSOR_UTILIZATION` varchar(11) COMMENT 'CPU使用率平均值',
  `MEM_USED_PER` varchar(11) COMMENT '内存使用率平均值',
  `MEM_FREE` varchar(20) COMMENT '可用内存量平均值（单位：KB）',
  `DISK_USED_PER` varchar(11) COMMENT '磁盘使用率（近一周内，最新的磁盘使用率）',
  `REPORT_DATE` char(8) NOT NULL COMMENT '统计日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_device_app_week_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_device_app_week_t` (
  `APP_ID` varchar(64) NOT NULL COMMENT '业务ID',
  `DEVICE_TYPE` varchar(1) NOT NULL COMMENT '设备类型:0：小型机,1：小型机分区,2：物理机,3：虚拟机,4：虚拟硬盘(无性能统计)',
  `DEVICE_NUM` varchar(11) COMMENT '设备个数',
  `CPU_OVER_NUM` varchar(11) COMMENT 'CPU负载超标的设备数',
  `CPU_OVER_AVE` varchar(11) COMMENT 'CPU负载超标的平均值',
  `CPU_NOT_OVER_NUM` varchar(11) COMMENT 'CPU负载未超标的设备数',
  `CPU_NOT_OVER_AVE` varchar(11) COMMENT 'CPU负载未超标的平均值',
  `MEM_OVER_NUM` varchar(11) COMMENT '内存负载超标的设备数',
  `MEM_OVER_AVE` varchar(11) COMMENT '内存负载超标的平均值',
  `MEM_OVER_FREE_AVE` varchar(11) COMMENT '内存负载超标的可用内存量平均值（单位：KB）',
  `MEM_NOT_OVER_NUM` varchar(11) COMMENT '内存负载未超标的设备数',
  `MEM_NOT_OVER_AVE` varchar(11) COMMENT '内存负载未超标的平均值',
  `MEM_NOT_OVER_FREE_AVE` varchar(11) COMMENT '内存负载未超标的可用内存量平均值（单位：KB）',
  `DISK_RANGEl_NUM` varchar(11) COMMENT '磁盘负载区间1(使用率>70%)设备数',
  `DISK_RANGE2_NUM` varchar(11) COMMENT '磁盘负载区间2(70%>=使用率>30%)设备数',
  `DISK_RANGE3_NUM` varchar(11) COMMENT '磁盘负载区间3(30%>=使用率)设备数',
  `REPORT_DATE` char(8) NOT NULL COMMENT '统计日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_device_day_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_device_day_t` (
  `DEVICE_ID` varchar(64) NOT NULL COMMENT '设备ID',
  `DEVICE_TYPE` varchar(1) NOT NULL COMMENT '设备类型:0：小型机,1：小型机分区,2：物理机,3：虚拟机',
  `APP_ID` varchar(64) COMMENT '业务ID',
  `CPU_PROCESSOR_UTILIZATION` varchar(11) COMMENT 'CPU使用率平均值',
  `CPU_OVER_TIME` varchar(11) COMMENT 'CPU负载超标时长（单位：分钟）',
  `MEM_USED_PER` varchar(11) COMMENT '内存使用率平均值',
  `MEM_OVER_TIME` varchar(11) COMMENT '内存负载超标时长（单位：分钟）',
  `MEM_FREE` varchar(20) COMMENT '可用内存量平均值（单位：KB）',
  `DISK_USED_PER` varchar(11) COMMENT '磁盘使用率（前一天中磁盘使用率最新值）',
  `REPORT_DATE` char(8) NOT NULL COMMENT '性能采集日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_device_app_day_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_device_app_day_t` (
  `APP_ID` varchar(64) NOT NULL COMMENT '业务ID',
  `DEVICE_TYPE` varchar(1) NOT NULL COMMENT '设备类型:0：小型机,1：小型机分区,2：物理机,3：虚拟机',
  `DEVICE_NUM` varchar(11) COMMENT '设备个数',
  `CPU_AVE` varchar(11) COMMENT 'CPU平均使用率',
  `CPU_MAX` varchar(11) COMMENT 'CPU最大使用率',
  `CPU_MAX_ID` varchar(64) COMMENT 'CPU最大使用率对应的设备ID',
  `CPU_MIN` varchar(11) COMMENT 'CPU最小使用率',
  `CPU_MIN_ID` varchar(64) COMMENT 'CPU最小使用率对应的设备ID',
  `CPU_OVER_NUM` varchar(11) COMMENT 'CPU负载超标的设备数',
  `CPU_OVER_AVE` varchar(11) COMMENT 'CPU负载超标的平均值',
  `CPU_NOT_OVER_NUM` varchar(11) COMMENT 'CPU负载未超标的设备数',
  `CPU_NOT_OVER_AVE` varchar(11) COMMENT 'CPU负载未超标的平均值',
  `MEM_AVE` varchar(11) COMMENT '内存平均使用率',
  `MEM_MAX` varchar(11) COMMENT '内存最大使用率',
  `MEM_MAX_ID` varchar(64) COMMENT '内存最大使用率对应的设备ID',
  `MEM_MAX_FREE` varchar(11) COMMENT '内存最大使用率对应的设备的可用内存（单位：KB）',
  `MEM_MIN` varchar(11) COMMENT '内存最小使用率',
  `MEM_MIN_ID` varchar(64) COMMENT '内存最小使用率对应的设备ID',
  `MEM_MIN_FREE` varchar(11) COMMENT '内存最小使用率对应的设备的可用内存（单位：KB）',
  `MEM_OVER_NUM` varchar(11) COMMENT '内存负载超标的设备数',
  `MEM_OVER_AVE` varchar(11) COMMENT '内存负载超标的平均值',
  `MEM_OVER_FREE_AVE` varchar(11) COMMENT '内存负载超标的可用内存量平均值（单位：KB）',
  `MEM_NOT_OVER_NUM` varchar(11) COMMENT '内存负载未超标的设备数',
  `MEM_NOT_OVER_AVE` varchar(11) COMMENT '内存负载未超标的平均值',
  `MEM_NOT_OVER_FREE_AVE` varchar(11) COMMENT '内存负载未超标的可用内存量平均值（单位：KB）',
  `DISK_AVE` varchar(11) COMMENT '磁盘平均使用率',
  `DISK_MAX` varchar(11) COMMENT '磁盘最大使用率',
  `DISK_MAX_ID` varchar(64) COMMENT '磁盘最大使用率对应的设备ID',
  `DISK_MIN` varchar(11) COMMENT '磁盘最小使用率',
  `DISK_MIN_ID` varchar(64) COMMENT '磁盘最小使用率对应的设备ID',
  `DISK_RANGEl_NUM` varchar(11) COMMENT '磁盘负载区间1(使用率>70%)设备数',
  `DISK_RANGE2_NUM` varchar(11) COMMENT '磁盘负载区间2(70%>=使用率>30%)设备数',
  `DISK_RANGE3_NUM` varchar(11) COMMENT '磁盘负载区间3(30%>=使用率)设备数',
  `REPORT_DATE` char(8) NOT NULL COMMENT '性能采集日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -------------------------------------------------------------
--  Table structure for `comp_sta_device_halfday_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_device_halfday_t` (
  `DEVICE_ID` varchar(64) NOT NULL COMMENT '设备ID',
  `DEVICE_TYPE` varchar(1) NOT NULL COMMENT '设备类型:0：小型机,1：小型机分区,2：物理机,3：虚拟机',
  `APP_ID` varchar(64) COMMENT '业务ID',
  `CPU_PROCESSOR_UTILIZATION` varchar(11) COMMENT 'CPU使用率平均值',
  `CPU_OVER_TIME` varchar(11) COMMENT 'CPU负载超标时长（单位：分钟）',
  `MEM_USED_PER` varchar(11) COMMENT '内存使用率平均值',
  `MEM_OVER_TIME` varchar(11) COMMENT '内存负载超标时长（单位：分钟）',
  `MEM_FREE` varchar(20) COMMENT '可用内存量平均值（单位：KB）',
  `DISK_USED_PER` varchar(11) COMMENT '磁盘使用率（前一天中磁盘使用率最新值）',
  `SWAP_MEM_USED_PER` varchar(11) COMMENT 'SWAP内存使用率平均值',
  `disk_IO_speed` VARCHAR(11),
  `REPORT_DATE` char(8) NOT NULL COMMENT '性能采集日期',
  `DATETYPE` char(1) NOT NULL COMMENT '统计的日期形式：全天、白天或是夜晚'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_app_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_app_t` (
  `APP_ID` varchar(64) NOT NULL COMMENT '业务ID',
  `APP_NAME` varchar(64) NOT NULL COMMENT '业务名称',
  `DESCRIPTION` varchar(256) COMMENT '备注',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `APP_CONTACTS` varchar(64) COMMENT '联系人',
  `CREATE_TIME` char(14) NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(64) NOT NULL COMMENT '创建人',
  `UPDATE_TIME` char(14) COMMENT '更新时间',
  `UPDATE_USER` varchar(64) COMMENT '更新人',
  PRIMARY KEY (`APP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_proc_exec_stat_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_proc_exec_stat_t` (
  `EXEC_TM` char(14) NOT NULL COMMENT '执行时间',
  `EXEC_NAME` varchar(64) NOT NULL COMMENT '执行的存储过程名称',
  `EXEC_INFO` varchar(128) COMMENT '存储过程执行的状态，包括两个值：BEGIN和END'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_proc_parameter_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_proc_parameter_t` (
  `PARAMETER_TAG` varchar(64) NOT NULL COMMENT '参数名',
  `PARAMETER_VALUE` varchar(64) NOT NULL COMMENT '参数值',
  `PARAMETER_DESC` varchar(64) COMMENT '描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- -------------------------------------------------------------
--  Table structure for `comp_rm_minipm_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_minipm_t` (
  `MINIPM_ID` varchar(64) NOT NULL COMMENT '小型机设备ID',
  `MINIPM_NAME` varchar(64) COMMENT '小型机名称',
  `MINIPM_INSTANCE_ID` varchar(64) COMMENT '小型机实例ID',
  `IP` varchar(32) COMMENT 'IP地址',
  `NETMASK` varchar(64) COMMENT '网络掩码',
  `CLUSTER_IP` varchar(32) COMMENT '绑定的cluster浮动IP地址',
  `SERVER_TYPE` varchar(128) COMMENT '主机型号',
  `CPU_NUM` varchar(11) COMMENT 'CPU数量',
  `CPU_TYPE` varchar(128) COMMENT 'CPU类型',
  `CPU_FREQUENCY` varchar(11) COMMENT 'CPU主频（单位：GHz）',
  `MEMORY_SIZE` varchar(11) COMMENT '内存容量（单位：MB）',
  `MEMORY_NUM` varchar(11) COMMENT '内存条数量',
  `DISK_SIZE` varchar(11) COMMENT '磁盘空间（单位：GB）',
  `DISK_NUM` varchar(11) COMMENT '硬盘个数',
  `IF_NUM` varchar(11) COMMENT '系统网络接口总数',
  `MAINBORD_NUM` varchar(11) COMMENT '主板数量',
  `POWERMODULE_NUM` varchar(11) COMMENT '电源模块数量',
  `FAN_NUM` varchar(11) COMMENT '风扇数量',
  `VENDOR_NAME` varchar(64) COMMENT '设备厂商',
  `RUN_TIME` char(14) COMMENT '投入生产运行的时间',
  `CUR_STATUS` char(1) COMMENT '当前状态 0：不可用；1：就绪；2：运行；3：安装中',
  `SERIALNUM` varchar(64) COMMENT '设备序列号',
  `APP_ID` varchar(64) COMMENT '业务ID',
  `RES_POOL_ID` varchar(32) COMMENT '资源池ID',
  `RES_POOL_PART_ID` varchar(32) COMMENT '资源池分区ID',
  `OS_TYPE` varchar(64) COMMENT '操作系统',
  `SWITCH_IF_RELATIONS` varchar(128) COMMENT '连接到的交换机及端口标识',
  `ASSET_ORIGIN_TYPE` char(1) COMMENT '资产来源分类 1：自产；2：外购；3：借入；4：订单；5：第三方监控源；6：可自义',
  `ASSET_STATE` char(1) COMMENT '资产状态 1：已使用；2：未使用；3：不可用；4：丢失；5：待确认；6：已删除',
  `ASSET_SLA_TYPE` char(1) COMMENT '资产SLA类型 1：提供服务；2：平台自服务',
  `MACHINEROOM_ID` varchar(64) COMMENT '所属机房',
  `CABINET_ID` varchar(64) COMMENT '所属机柜',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`MINIPM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_rm_minipmpar_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_rm_minipmpar_t` (
  `MINIPMPAR_ID` varchar(64) NOT NULL COMMENT '小型机分区实例ID',
  `MINIPMPAR_NAME` varchar(64) COMMENT '小型机分区名称',
  `MINIPMPAR_EQP_ID` varchar(64) COMMENT '小型机分区设备ID（表示小型机上的第几个小型机分区）',
  `PRIVATE_IP` varchar(32) COMMENT '私网IP',
  `NETMASK` varchar(64) COMMENT '网络掩码',
  `CLUSTER_IP` varchar(32) COMMENT '绑定的cluster浮动IP地址',
  `CPU_NUM` varchar(11) COMMENT 'CPU数量',
  `CPU_FREQUENCY` varchar(11) COMMENT 'CPU主频（单位：GHz）',
  `MEMORY_SIZE` varchar(11) COMMENT '内存容量（单位：MB）',
  `DISK_SIZE` varchar(11) COMMENT '磁盘空间（单位：GB）',
  `RUN_TIME` char(14) COMMENT '投入生产运行的时间',
  `CUR_STATUS` char(1) COMMENT '当前状态 0：不可用；1：就绪；2：运行；3：安装中',
  `APP_ID` varchar(64) COMMENT '业务ID',
  `RES_POOL_ID` varchar(32) COMMENT '资源池ID',
  `RES_POOL_PART_ID` varchar(32) COMMENT '资源池分区ID',
  `V_ETHADA_NUM` varchar(11) COMMENT '虚拟网卡个数',
  `V_SCSIADA_NUM` varchar(11) COMMENT '虚拟SCSI卡个数',
  `V_FCHBA_NUM` varchar(11) COMMENT '虚拟FC-HBA卡个数',
  `OS_TYPE` varchar(64) COMMENT '操作系统盘类型',
  `VM_OS` varchar(64) COMMENT '操作系统',
  `MINIPM_ID` varchar(64) COMMENT '所属小型机设备ID',
  `SWITCH_IF_RELATIONS` varchar(128) COMMENT '连接到的交换机及端口标识',
  `SWAP_CAPACITY` varchar(20) COMMENT '系统交换区大小(MB)',
  `CREATE_FLAG` char(1) COMMENT '创建标志 0：运营；1：BOMC',
  `UPDATE_TIME` char(14) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`MINIPMPAR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -------------------------------------------------------------
--  Table structure for `comp_case_ebs_mod_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_case_ebs_mod_t` (
`CASE_ID`  varchar(30) NOT NULL COMMENT '订单ID' ,
`RES_POOL_ID`  varchar(30) NOT NULL COMMENT '资源池ID' ,
`RES_POOL_PART_ID`  varchar(30) NULL COMMENT '资源池分区ID' ,
`EBS_NAME`  varchar(50) NOT NULL COMMENT '更改后的虚拟硬盘名称' ,
`DISK_SIZE`  int(10) NOT NULL COMMENT '更改后的磁盘大小' ,
`UPDATE_TIME`  char(14) NOT NULL COMMENT '修改时间YYYYMMDDhhmmss'
)
COMMENT='虚拟硬盘修改申请存储表'
;

ALTER TABLE `comp_case_ebs_mod_t`
CHANGE COLUMN `CASE_ID` `EBS_ID`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '虚拟硬盘编码(PK)' FIRST ;

ALTER TABLE `comp_case_ebs_mod_t`
DROP COLUMN `EBS_NAME`;

-- -------------------------------------------------------------
--  Table structure for `comp_case_vlan_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_case_vlan_t` (
`CASE_ID`  varchar(30) NOT NULL COMMENT '订单ID（PK）\r\n规则:\r\nORDER-[资源类型]-[实例ID]- yyyyMMddHHmmss+四位随机数\r\n资源类型如下：\r\nVM：虚拟机资源；\r\nSRV：物理服务器资源；\r\nVMBK：虚拟机备份资源；\r\nBS：块存储资源；\r\nOS：对象存储资源；\r\nIP：公网IP地址资源；\r\nBW：带宽资源；\r\nSG：安全组资源；\r\nSN：快照资源；\r\nCM：云监控资源；\r\nPIP：私网IP地址资源；\r\nVLAN：VLAN资源；\r\nVMTBK：虚拟机备份任务资源。\r\n \r\n例如：ORDER-VM-TT-201301281526370001\r\n' ,
`VLAN_ID`  varchar(30) NULL COMMENT 'VLAN号' ,
`VLAN_NAME`  varchar(64) NOT NULL COMMENT 'VLAN名称' ,
`ORDER_ID`  varchar(30) NOT NULL COMMENT '订单编号',
`APP_ID`  varchar(64) NOT NULL COMMENT '业务ID' ,
`RES_POOL_ID`  varchar(30) NOT NULL COMMENT '资源池ID' ,
`RES_POOL_PART_ID`  varchar(30) NULL COMMENT '资源池分区ID' ,
`ZONE_TYPE`  char(1) NULL DEFAULT 1 COMMENT 'vlan所在安全域\r\n1：DMZ\r\n2：Trust 核心域\r\n3：TEST 测试域\r\n' ,
`VLAN_TYPE`  char(1) NOT NULL DEFAULT 2 COMMENT 'vlan用途:\r\n2：业务平面(默认)\r\n3：IP存储平面（指IP存储，例如分布式存储、日志详单、NOSQL等）\r\n' ,
`CREATE_TIME`  char(14) NOT NULL COMMENT '创建时间YYYYMMDDhhmmss' ,
`CREATE_USER`  varchar(64) NOT NULL COMMENT '创建人(用户ID)' ,
`CANCELED`  varchar(1) NOT NULL DEFAULT 0 COMMENT '是否生效标识\r\n0：未取消（默认值）\r\n1：已取消 \r\n2: 待创建' ,
`CANCEL_TIME`  char(14) NULL COMMENT 'IP释放时间YYYYMMDDhhmmss' ,
`CANCEL_USER`  varchar(64) NULL COMMENT '取消人(用户ID)' ,
PRIMARY KEY (`CASE_ID`)
)
COMMENT='VLAN实例表'
;


-- -------------------------------------------------------------
--  Table structure for `comp_case_ipsegment_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_case_ipsegment_t` (
`CASE_ID`  varchar(30) NOT NULL COMMENT '订单ID（PK）\r\n规则:\r\nORDER-[资源类型]-[实例ID]- yyyyMMddHHmmss+四位随机数\r\n资源类型如下：\r\nVM：虚拟机资源；\r\nSRV：物理服务器资源；\r\nVMBK：虚拟机备份资源；\r\nBS：块存储资源；\r\nOS：对象存储资源；\r\nIP：公网IP地址资源；\r\nBW：带宽资源；\r\nSG：安全组资源；\r\nSN：快照资源；\r\nCM：云监控资源；\r\nPIP：私网IP地址资源；\r\nVLAN：VLAN资源；\r\nVMTBK：虚拟机备份任务资源。\r\n \r\n例如：ORDER-VM-TT-201301281526370001' ,
`IPSEGMENT_ID`  varchar(30) NULL COMMENT 'IP段唯一标识符' ,
`IPSEGMENT_DESC`  varchar(64) NOT NULL COMMENT 'IP段描述' ,
`ORDER_ID`  varchar(30) NOT NULL COMMENT '订单编号',
`APP_ID`  varchar(64) NOT NULL COMMENT '业务ID' ,
`RES_POOL_ID`  varchar(30) NOT NULL COMMENT '资源池ID' ,
`RES_POOL_PART_ID`  varchar(30) NULL COMMENT '资源池分区ID' ,
`IP_SUBNET`  varchar(18) NOT NULL COMMENT '网段(格式为xxx.xxx.xxx.xxx/xx)' ,
`START_IP`  varchar(15) NOT NULL COMMENT 'IP段中的起始IP' ,
`END_IP`  varchar(15) NOT NULL COMMENT 'IP段中的结束IP' ,
`IP_TOTAL`  varchar(10) NOT NULL COMMENT 'IP段中总IP数' ,
`CREATE_TIME`  char(14) NOT NULL COMMENT '创建时间YYYYMMDDhhmmss' ,
`CREATE_USER`  varchar(64) NOT NULL COMMENT '创建人(用户ID)' ,
`RELEASED`  varchar(1) NOT NULL COMMENT '是否释放标识\r\n0：未释放（默认值）\r\n1：已释放 \r\n2: 待创建' ,
`RELEASE_TIME`  char(14) NULL COMMENT '释放时间YYYYMMDDhhmmss' ,
`RELEASE_USER`  varchar(64) NULL COMMENT '释放人(用户ID)' ,
PRIMARY KEY (`CASE_ID`)
)
COMMENT='IP段实例表'
;

-- -------------------------------------------------------------
--  Table structure for `COMP_PM_TYPE_T`
-- -------------------------------------------------------------
create table COMP_PM_TYPE_T
(
   PM_TYPE_ID           int not null auto_increment comment '自动生成',
   PM_TYPE_NAME         varchar(128) not null,
   CPU_TYPE             varchar(128) not null,
   RAM_SIZE             varchar(32) not null,
   DISC_SIZE            varchar(32) not null,
   EthAda_Num           varchar(32) not null,
   EthAda_Type          varchar(64) not null,
   SCSI_Ada_Num         varchar(32) not null,
   HBA_Num              varchar(32) not null,
   HBA_Type             varchar(64) not null,
   primary key (PM_TYPE_ID)
);

-- -------------------------------------------------------------
--  Table structure for `comp_sta_bs_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_bs_rt_t` (
  `BS_ID` varchar(64) NOT NULL COMMENT '卷ID',
  `LV_WORK_STATE` varchar(20) DEFAULT NULL COMMENT 'LUN-读操作速率（次/秒）',
  `LV_WRITE_RATE` varchar(20) DEFAULT NULL COMMENT 'LUN-写操作速率（次/秒）',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间YYYYMMDDhhmmss'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_raid_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_raid_rt_t` (
  `RAID_ID` varchar(64) NOT NULL COMMENT '阵列ID',
  `HST_DISK_READ_BYTES` varchar(20) DEFAULT NULL COMMENT '读吞吐量（字节）',
  `HST_DISK_WRITE_BYTES` varchar(20) DEFAULT NULL COMMENT '写吞吐量（字节）',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间YYYYMMDDhhmmss'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_switch_port_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_switch_port_rt_t` (
  `SWITCH_PORT_ID` varchar(64) NOT NULL COMMENT '交换机端口ID',
  `SWITCH_ID` varchar(64) NOT NULL COMMENT '所属交换机ID',
  `IF_IN_NUCAST_PKTS` varchar(20) DEFAULT NULL COMMENT '所属交换机ID',
  `IF_OUT_NUCAST_PKTS` varchar(20) DEFAULT NULL COMMENT '出单播包数',
  `IF_IN_MULTICAST_PKTS` varchar(20) DEFAULT NULL COMMENT '入组播包数',
  `IF_IN_BROADCAST_PKTS` varchar(20) DEFAULT NULL COMMENT '入广播包数',
  `IF_OUT_MULTICAST_PKTS` varchar(20) DEFAULT NULL COMMENT '出组播包数',
  `IF_OUT_BROADCAST_PKTS` varchar(20) DEFAULT NULL COMMENT '出广播包数',
  `IF_IN_DISCARDS` varchar(20) DEFAULT NULL COMMENT '入丢包数',
  `IF_OUT_DISCARDS` varchar(20) DEFAULT NULL COMMENT '出丢包数',
  `IF_IN_OCTETS` varchar(20) DEFAULT NULL COMMENT '入字节数（Bytes）',
  `IF_OUT_OCTETS` varchar(20) DEFAULT NULL COMMENT '出字节数（Bytes）',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间YYYYMMDDhhmmss'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_switch_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_switch_rt_t` (
  `SWITCH_ID` varchar(64) NOT NULL COMMENT '交换机ID',
  `CPU_USED_PER` varchar(11) DEFAULT NULL COMMENT 'CPU利用率（%）',
  `MEM_USED_PER` varchar(11) DEFAULT NULL COMMENT '内存利用率（%）',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间YYYYMMDDhhmmss'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -------------------------------------------------------------
--  Table structure for `comp_sta_firewall_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_firewall_rt_t` (
  `FIREWALL_ID` varchar(64) NOT NULL COMMENT '防火墙ID',
  `CPU_USED_PER` varchar(11) DEFAULT NULL COMMENT 'CPU利用率（%）',
  `MEM_USED_PER` varchar(11) DEFAULT NULL COMMENT '内存利用率（%）',
  `THROUGHPUT` varchar(20) DEFAULT NULL COMMENT '吞吐量（MB/S）',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间YYYYMMDDhhmmss'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='防火墙实时性能表，记录防火墙的实 时性能数据';

-- -------------------------------------------------------------
--  Table structure for `comp_sta_router_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_router_rt_t` (
  `ROUTER_ID` varchar(64) NOT NULL COMMENT '交换机ID',
  `CPU_USED_PER` varchar(11) DEFAULT NULL COMMENT 'CPU利用率（%）',
  `MEM_USED_PER` varchar(11) DEFAULT NULL COMMENT '内存利用率（%）',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间YYYYMMDDhhmmss'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='路由器实时性能表，记录路由器的实时性能数据';

-- -------------------------------------------------------------
--  Table structure for `comp_sta_router_port_rt_t`
-- -------------------------------------------------------------
CREATE TABLE `comp_sta_router_port_rt_t` (
  `ROUTER_PORT_ID` varchar(64) NOT NULL COMMENT '路由器端口ID',
  `ROUTER_ID` varchar(64) NOT NULL COMMENT '所属路由器ID',
  `IF_IN_UCAST_PKTS` varchar(20) DEFAULT NULL COMMENT '入单播包数',
  `IF_OUT_UCAST_PKTS` varchar(20) DEFAULT NULL COMMENT '出单播包数 ',
  `IF_IN_MULTICAST_PKTS` varchar(20) DEFAULT NULL COMMENT '入组播 包数',
  `IF_IN_BROADCAST_PKTS` varchar(20) DEFAULT NULL COMMENT '入广播 包数',
  `IF_OUT_MULTICAST_PKTS` varchar(20) DEFAULT NULL COMMENT '出组 播包数',
  `IF_OUT_BROADCAST_PKTS` varchar(20) DEFAULT NULL COMMENT '出广 播包数',
  `IF_IN_DISCARDS` varchar(20) DEFAULT NULL COMMENT '入丢包数',
  `IF_OUT_DISCARDS` varchar(20) DEFAULT NULL COMMENT '出丢包数',
  `CREATE_TIME` char(14) NOT NULL COMMENT '采集时间YYYYMMDDhhmmss'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='路由器端口实时性能表，记录路由器 端口的实时性能数据';


-- ----------------------------
-- Table structure for comp_case_snapshot_t
-- ----------------------------
DROP TABLE IF EXISTS `comp_case_snapshot_t`;
CREATE TABLE `comp_case_snapshot_t` (
  `SNAPSHOT_ID` varchar(30) DEFAULT NULL COMMENT 'ID',
  `VM_ID` varchar(30) NOT NULL COMMENT '指定虚拟机，该虚拟机必须存在',
  `SNAPSHOT_NAME` varchar(255) NOT NULL COMMENT '快照名称，',
  `SNAPSHOT_DESC` varchar(255) DEFAULT NULL COMMENT '快照描述',
  `GENERATE_MEMORY` varchar(10) DEFAULT '1' COMMENT '是否生成虚拟机内存快照，0为不生成，1为生成',
  `QUIESCE` varchar(10) DEFAULT '0' COMMENT '是否是虚拟机文件系统处于静默状态，0不是，1是（现在只支持0）',
  `FORCE` tinyint(1) DEFAULT '0' COMMENT '声明是否强制创建快照，默认：false',
  `CREATE_USER` varchar(20) NOT NULL COMMENT '创建人(用户ID)',
  `SNAPSHOT_TIME` char(14) NOT NULL COMMENT '创建快照的时间',
  `SNAPSHOT_STATE` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
