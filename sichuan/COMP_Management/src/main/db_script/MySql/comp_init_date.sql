-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `comp_authority_role_t`
VALUES
	(
		'1',
		'超级管理员',
		'1',
		'超级管理员',
		'20140101121212',
		'admin'
	);
INSERT INTO `comp_authority_role_t` VALUES ('2', '维护人员', '1', '对资源、业务等信息维护', '20150226101244', 'admin');
INSERT INTO `comp_authority_role_t` VALUES ('3', '审批人员', '1', '对用户、订单、服务条目等进行审批', '20150226101335', 'admin');

INSERT INTO `comp_authority_user_t`
VALUES
	(
		'admin',
		'运营管理员',
		'AEUAMQAwAEEARABDADMAOQA0ADkAQgBBADUAOQBBAEIAQgBFADUANgBFADAANQA3AEYAMgAwAEYAOAA4ADMARQ==',
		'20131217092020',
		'20140306195416',
		'0',
		'',
		'13840477556',
		'admin@cloong.com',
		'运营管理部',
		'运营管理人员.',
		'administrator',
		'',
		'83668823',
		'83668823',
		'运营管理部',
		NULL,
		NULL,
		NULL,
		NULL,
		NULL
	),
	(
		'user1',
		'张三',
		'AEUAMQAwAEEARABDADMAOQA0ADkAQgBBADUAOQBBAEIAQgBFADUANgBFADAANQA3AEYAMgAwAEYAOAA4ADMARQ==',
		'20140218154135',
		'20140307151131',
		'0',
		NULL,
		'13800138000',
		'wei_sun@neusoft.com',
		NULL,
		NULL,
		'user1',
		NULL,
		NULL,
		NULL,
		NULL,
		NULL,
		NULL,
		NULL,
		NULL,
		NULL
	);
INSERT INTO `comp_authority_user_role_t` VALUES ('admin','1');

INSERT INTO `comp_authority_permission_t` VALUES ('1', '0', 'yyglxt', '运营管理系统', '0', '1', '运营管理系统', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('201', '1', 'dbrw', '待办任务', '0', '1', '待办任务', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20102', '201', 'fwtmsp', '服务条目审批', '0', '1', '服务条目审批', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2010201', '20102', 'tmsp', '条目审批', '0', '1', '条目审批', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2010202', '20102', 'fbsp', '发布审批', '0', '1', '发布审批', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20103', '201', 'user_sp', '用户审批', '0', '1', '用户审批', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20104', '201', 'order_sp', '订单审批', '0', '1', '订单审批', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('202', '1', 'ddgl', '订单管理', '0', '1', '订单管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20201', '202', 'xjfbsp', '订单管理', '0', '1', '订单管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('203', '1', 'cpgl', '产品管理', '0', '1', '产品管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20301', '203', 'zygggl', '资源规格管理', '0', '1', '资源规格管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2030101', '20301', 'xnj', '虚拟机', '0', '1', '虚拟机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2030102', '20301', 'xnyp', '硬盘', '0', '1', '硬盘', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2030103', '20301', 'wlj', '物理机', '0', '1', '物理机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20302', '203', 'fwmlgl', '服务目录管理', '0', '1', '服务目录管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2030201', '20302', 'fwmlglxnj', '虚拟机', '0', '1', '虚拟机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2030202', '20302', 'fwmlglxnyp', '硬盘', '0', '1', '硬盘', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2030203', '20302', 'fwmlglwlj', '物理机', '0', '1', '物理机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20303', '203', 'fwtmgl', '服务条目管理', '0', '1', '服务条目管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2030301', '20303', 'fwtmglxnj', '虚拟机', '0', '1', '虚拟机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2030302', '20303', 'fwtmglxnyp', '硬盘', '0', '1', '硬盘', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2030303', '20303', 'fwtmglwlj', '物理机', '0', '1', '物理机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('204', '1', 'zygl', '资源管理', '0', '1', '资源管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20401', '204', 'zyst', '资源视图', '0', '1', '资源视图', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20402', '204', 'ywst', '业务视图', '0', '1', '业务视图', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('205', '1', 'ywgl', '业务管理', '0', '1', '业务管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20501', '205', 'ywlb', '业务管理', '0', '1', '业务管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('206', '1', 'tjfx', '统计分析', '0', '1', '统计分析', '20151105121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20601', '206', 'sbxntj', '设备性能统计', '0', '1', '业务性能统计', '20151105121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060101', '20601', 'sbxntjwlj', '物理机', '0', '1', '物理机', '20151105121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060102', '20601', 'sbxntjxnj', '虚拟机', '0', '1', '虚拟机', '20151105121212', 'admin');
/*INSERT INTO `comp_authority_permission_t` VALUES ('2060103', '20601', 'sbxntjzl', '阵列', '0', '1', '阵列', '20151105121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060104', '20601', 'sbxntjjhj', '交换机', '0', '1', '交换机', '20151105121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060105', '20601', 'sbxntjfhq', '防火墙', '0', '1', '防火墙', '20151105121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060106', '20601', 'sbxntjlyq', '路由器', '0', '1', '路由器', '20151105121212', 'admin');*/
INSERT INTO `comp_authority_permission_t` VALUES ('20602', '206', 'ywxntj', '业务性能统计', '0', '1', '业务性能统计', '20151105121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060201', '20602', 'ywxntjwlj', '物理机', '0', '1', '物理机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060202', '20602', 'ywxntjxnj', '虚拟机', '0', '1', '虚拟机', '20140101121212', 'admin');
/*INSERT INTO `comp_authority_permission_t` VALUES ('2060203', '20602', 'ywxntjxxj', '小型机', '0', '1', '小型机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060204', '20602', 'ywxntjxxjfq', '小型机分区', '0', '1', '小型机分区', '20140101121212', 'admin');*/
INSERT INTO `comp_authority_permission_t` VALUES ('2060205', '20602', 'ywxntjdc', '统计数据导出', '0', '1', '统计数据导出', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20603', '206', 'ydxntj', '月度性能统计', '0', '1', '月度性能统计', '20151105121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060301', '20603', 'ydxntjwlj', '物理机', '0', '1', '物理机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060302', '20603', 'ydxntjxnj', '虚拟机', '0', '1', '虚拟机', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2060305', '20603', 'ydxntjdc', '统计数据导出', '0', '1', '统计数据导出', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('207', '1', 'xtgl', '系统管理', '0', '1', '系统管理', '20120101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20701', '207', 'xtpz', '系统配置', '0', '1', '系统配置', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2070101', '20701', 'zyccspz', '资源池参数配置', '0', '1', '资源池参数配置', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2070102', '20701', 'zycfqgl', '资源池分区管理', '0', '1', '资源池分区管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('20702', '207', 'aqgl', '安全管理', '0', '1', '安全管理', '20120101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2070201', '20702', 'yhgl', '用户管理', '0', '1', '用户管理', '20140101121212', 'admin');
INSERT INTO `comp_authority_permission_t` VALUES ('2070203', '20702', 'jsgl', '角色管理', '0', '1', '角色管理', '20120101121212', 'admin');

INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '1');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20102');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2010201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2010202');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20103');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20104');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '202');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '203');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20301');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2030101');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2030102');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2030103');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20302');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2030201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2030202');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2030203');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20303');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2030301');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2030302');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2030303');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '204');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20401');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20402');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '205');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20501');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '206');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20601');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060101');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060102');
/*INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060103');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060104');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060105');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060106');*/
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20602');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060202');
/*INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060203');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060204');*/
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060205');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '207');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20701');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2070101');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2070102');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20702');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2070201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2070203');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '1');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '204');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '20401');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '20402');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '205');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '20501');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '206');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '20601');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060101');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060102');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060103');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060104');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060105');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060106');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '20602');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060202');
/*INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060203');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060204');*/
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2060205');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '20603');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060301');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060302');
INSERT INTO `comp_authority_role_perm_t` VALUES ('1', '2060305');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '207');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '20701');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2070101');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2070102');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '20702');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2070201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('2', '2070203');
INSERT INTO `comp_authority_role_perm_t` VALUES ('3', '1');
INSERT INTO `comp_authority_role_perm_t` VALUES ('3', '201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('3', '20102');
INSERT INTO `comp_authority_role_perm_t` VALUES ('3', '2010201');
INSERT INTO `comp_authority_role_perm_t` VALUES ('3', '2010202');
INSERT INTO `comp_authority_role_perm_t` VALUES ('3', '20103');
INSERT INTO `comp_authority_role_perm_t` VALUES ('3', '20104');
INSERT INTO `comp_authority_role_perm_t` VALUES ('3', '202');
INSERT INTO `comp_authority_role_perm_t` VALUES ('3', '20201');
/*湖北运营追加的comp_proc_parameter_t表的初始化数据*/
INSERT INTO `comp_proc_parameter_t` VALUES ('rtPerformanceDel', '7', '实时性能表，删除7天前（7天，不包括当天）的数据');
INSERT INTO `comp_proc_parameter_t` VALUES ('capacityDel', '30', '容量统计表，删除30天前（30天，不包括当天）的数据');
INSERT INTO `comp_proc_parameter_t` VALUES ('deviceWeekDel', '1', '设备性能统计表_近一周，删除1天前（1天，不包括当天）的数据');
INSERT INTO `comp_proc_parameter_t` VALUES ('deviceAppWeekDel', '1', '设备性能统计表_按业务统计，删除1天前（1天，不包括当天）的数据');
INSERT INTO `comp_proc_parameter_t` VALUES ('deviceAppDayDel', '1', '设备历史性能统计表_按业务每天统计，删除前年的数据');
INSERT INTO `comp_proc_parameter_t` VALUES ('deviceDayDel', '2', '设备性能统计表_每天，删除2天前（2天，不包括当天）的数据');
INSERT INTO `comp_proc_parameter_t` VALUES ('halfDeviceDayDel', '100', '设备性能统计表_白/夜，删除100天前（100天，不包括当天）的数据');