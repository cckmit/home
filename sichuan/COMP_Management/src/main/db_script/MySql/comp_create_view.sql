-- ----------------------------
--  View definition for `comp_ebs_res_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_ebs_res_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_ebs_res_v` AS select `comp_standard_ebs_t`.`STANDARD_ID` AS `standard_id`,`comp_standard_ebs_t`.`STANDARD_NAME` AS `standard_name`,`comp_res_pool_part_t`.`RES_POOL_ID` AS `res_pool_id`,`comp_res_pool_part_t`.`RES_POOL_PART_ID` AS `res_pool_part_id`,`comp_res_pool_part_t`.`RES_POOL_PART_NAME` AS `res_pool_part_name` from (`comp_standard_ebs_t` join `comp_res_pool_part_t`) where `comp_res_pool_part_t`.`status`='1';

-- ----------------------------
--  View definition for `comp_ebs_standard_res_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_ebs_standard_res_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_ebs_standard_res_v` AS select `ls`.`standard_id` AS `standard_id`,`ls`.`standard_name` AS `standard_name`,`ls`.`res_pool_id` AS `res_pool_id`,`rpt`.`RES_POOL_NAME` AS `res_pool_name`,`ls`.`res_pool_part_id` AS `res_pool_part_id`,`ls`.`res_pool_part_name` AS `res_pool_part_name`,`sst`.`TEMPLATE_ID` AS `template_id`,(case when ((`sst`.`STATUS` = '2') or (`sst`.`STATUS` = '0') or (`sst`.`STATUS` = '3') or (`sst`.`STATUS` = '4') or (`sst`.`STATUS` = '5')) then '1' when (`sst`.`STATUS` = '1') then '2' else '0' end) AS `flage`,`sst`.`STATUS` AS `status`,`rpt`.`STATUS` AS `resStatus` from ((`comp_ebs_res_v` `ls` left join `comp_standard_syn_t` `sst` on(((`sst`.`STANDARD_ID` = `ls`.`standard_id`) and (`sst`.`RES_POOL_ID` = `ls`.`res_pool_id`) and (`sst`.`RES_POOL_PART_ID` = `ls`.`res_pool_part_id`)))) left join `comp_res_pool_t` `rpt` on((`ls`.`res_pool_id` = `rpt`.`RES_POOL_ID`)));

-- ----------------------------
--  View definition for `comp_pm_res_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_pm_res_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_pm_res_v` AS select `comp_standard_pm_t`.`STANDARD_ID` AS `standard_id`,`comp_standard_pm_t`.`STANDARD_NAME` AS `standard_name`,`comp_res_pool_part_t`.`RES_POOL_ID` AS `res_pool_id`,`comp_res_pool_part_t`.`RES_POOL_PART_ID` AS `res_pool_part_id`,`comp_res_pool_part_t`.`RES_POOL_PART_NAME` AS `res_pool_part_name` from (`comp_standard_pm_t` join `comp_res_pool_part_t`) where `comp_res_pool_part_t`.`status`='1';

-- ----------------------------
--  View definition for `comp_pm_standard_res_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_pm_standard_res_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_pm_standard_res_v` AS select `ls`.`standard_id` AS `standard_id`,`ls`.`standard_name` AS `standard_name`,`ls`.`res_pool_id` AS `res_pool_id`,`rpt`.`RES_POOL_NAME` AS `res_pool_name`,`ls`.`res_pool_part_id` AS `res_pool_part_id`,`ls`.`res_pool_part_name` AS `res_pool_part_name`,`sst`.`TEMPLATE_ID` AS `template_id`,(case when ((`sst`.`STATUS` = '2') or (`sst`.`STATUS` = '0') or (`sst`.`STATUS` = '3') or (`sst`.`STATUS` = '4') or (`sst`.`STATUS` = '5')) then '1' when (`sst`.`STATUS` = '1') then '2' else '0' end) AS `flage`,`sst`.`STATUS` AS `status`,`rpt`.`STATUS` AS `resStatus` from ((`comp_pm_res_v` `ls` left join `comp_standard_syn_t` `sst` on(((`sst`.`STANDARD_ID` = `ls`.`standard_id`) and (`sst`.`RES_POOL_ID` = `ls`.`res_pool_id`) and (`sst`.`RES_POOL_PART_ID` = `ls`.`res_pool_part_id`)))) left join `comp_res_pool_t` `rpt` on((`ls`.`res_pool_id` = `rpt`.`RES_POOL_ID`)));


-- ----------------------------
--  View definition for `comp_vm_res_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_vm_res_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_vm_res_v` AS select `comp_standard_vm_t`.`STANDARD_ID` AS `standard_id`,`comp_standard_vm_t`.`STANDARD_NAME` AS `standard_name`,`comp_res_pool_part_t`.`RES_POOL_ID` AS `res_pool_id`,`comp_res_pool_part_t`.`RES_POOL_PART_ID` AS `res_pool_part_id`,`comp_res_pool_part_t`.`RES_POOL_PART_NAME` AS `res_pool_part_name` from (`comp_standard_vm_t` join `comp_res_pool_part_t`) where `comp_res_pool_part_t`.`status`='1';

-- ----------------------------
--  View definition for `comp_vm_standard_res_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_vm_standard_res_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_vm_standard_res_v` AS select `ls`.`standard_id` AS `standard_id`,`ls`.`standard_name` AS `standard_name`,`ls`.`res_pool_id` AS `res_pool_id`,`rpt`.`RES_POOL_NAME` AS `res_pool_name`,`ls`.`res_pool_part_id` AS `res_pool_part_id`,`ls`.`res_pool_part_name` AS `res_pool_part_name`,(case when ((`sst`.`STATUS` = '2') or (`sst`.`STATUS` = '0') or (`sst`.`STATUS` = '3') or (`sst`.`STATUS` = '4') or (`sst`.`STATUS` = '5')) then '1' when (`sst`.`STATUS` = '1') then '2' else '0' end) AS `flage`,`sst`.`STATUS` AS `status`,`rpt`.`STATUS` AS `resStatus` from ((`comp_vm_res_v` `ls` left join `comp_standard_syn_t` `sst` on(((`sst`.`STANDARD_ID` = `ls`.`standard_id`) and (`sst`.`RES_POOL_ID` = `ls`.`res_pool_id`) and (`sst`.`RES_POOL_PART_ID` = `ls`.`res_pool_part_id`)))) left join `comp_res_pool_t` `rpt` on((`ls`.`res_pool_id` = `rpt`.`RES_POOL_ID`)));

-- ----------------------------
--  View definition for `comp_vm_standard_template_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_vm_standard_template_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_vm_standard_template_v` AS select `comp_standard_vm_t`.`STANDARD_ID` AS `STANDARD_ID`,`comp_standard_vm_t`.`STANDARD_NAME` AS `STANDARD_NAME`,`comp_standard_vm_t`.`CPU_NUM` AS `CPU_NUM`,`comp_standard_vm_t`.`RAM_SIZE` AS `RAM_SIZE`,`comp_standard_vm_t`.`DISC_SIZE` AS `DISC_SIZE`,`comp_standard_vm_t`.`DESCRIPTION` AS `DESCRIPTION`,`comp_standard_vm_t`.`STATUS` AS `STATUS`,`comp_standard_vm_t`.`CREATE_TIME` AS `CREATE_TIME`,`comp_standard_vm_t`.`CREATE_USER` AS `CREATE_USER`,`comp_standard_vm_t`.`UPDATE_TIME` AS `UPDATE_TIME`,`comp_standard_vm_t`.`UPDATE_USER` AS `UPDATE_USER` from `comp_standard_vm_t` where (`comp_standard_vm_t`.`STATUS` = '0');


-- ----------------------------
--  View definition for `comp_vm_template_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_vm_template_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_vm_template_v` AS select `rpp`.`STATUS` AS `status`,`ot`.`OS_ID` AS `os_id`,`rpp`.`RES_POOL_PART_ID` AS `res_pool_part_id`,`rpp`.`RES_POOL_ID` AS `res_pool_id`,`svt`.`STANDARD_NAME` AS `standard_name`,`svt`.`STANDARD_ID` AS `standard_id`,`svt`.`RAM_SIZE` AS `ram_size`,`svt`.`DISC_SIZE` AS `disc_size`,`svt`.`CPU_NUM` AS `cpu_num`,`svt`.`STATUS` AS `svtstatus`,`rpp`.`RES_POOL_PART_NAME` AS `res_pool_part_name`,`rpp`.`RES_POOL_NAME` AS `res_pool_name`,`ot`.`OS_NAME` AS `os_name` from ((`comp_vm_standard_template_v` `svt` join `comp_os_t` `ot` ON ((`ot`.`Resource_TYPE` = '0'))) join `comp_res_pool_part_t` `rpp`);

-- ----------------------------
--  View definition for `comp_vm_iso_template_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_vm_iso_template_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_vm_iso_template_v` AS select `crosst`.`os_id` AS `os_id`,`crosst`.`res_pool_part_id` AS `res_pool_part_id`,`crosst`.`res_pool_id` AS `res_pool_id`,`crosst`.`standard_id` AS `standard_id`,`crosst`.`standard_name` AS `standard_name`,`crosst`.`res_pool_part_name` AS `res_pool_part_name`,`crosst`.`res_pool_name` AS `res_pool_name`,`sit`.`TEMPLATE_ID` AS `template_id`,`crosst`.`os_name` AS `os_name`,`crosst`.`status` AS `roolPartStatus` from ((`comp_os_relate_t` `cor` left join `comp_vm_template_v` `crosst` on(((`crosst`.`os_id` = `cor`.`OS_ID`) and (`crosst`.`res_pool_part_id` = `cor`.`RES_POOL_PART_ID`) and (`crosst`.`res_pool_id` = `cor`.`RES_POOL_ID`) and (`cor`.`STATUS` = '0')))) left join `comp_standard_iso_t` `sit` on(((`crosst`.`os_id` = `sit`.`OS_ID`) and (`crosst`.`standard_id` = `sit`.`STANDARD_ID`))));
-- ----------------------------
--  View definition for `comp_vm_template_res_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_vm_template_res_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_vm_template_res_v` AS select `tabls`.`os_id` AS `os_id`,`tabls`.`res_pool_part_id` AS `res_pool_part_id`,`tabls`.`res_pool_id` AS `res_pool_id`,`tabls`.`standard_id` AS `standard_id`,`tabls`.`standard_name` AS `standard_name`,`tabls`.`res_pool_part_name` AS `res_pool_part_name`,`rpt`.`RES_POOL_NAME` AS `res_pool_name`,`tabls`.`os_name` AS `os_name`,`tabls`.`roolPartStatus` AS `roolPartStatus`,`sst`.`TEMPLATE_ID` AS `TEMPLATE_ID`,(case when ((`sst`.`STATUS` = '2') or (`sst`.`STATUS` = '0') or (`sst`.`STATUS` = '3') or (`sst`.`STATUS` = '4') or (`sst`.`STATUS` = '5')) then '1' when (`sst`.`STATUS` = '1') then '2' else '0' end) AS `flage`,`sst`.`STATUS` AS `status`,`rpt`.`STATUS` AS `resStatus` from ((`comp_vm_iso_template_v` `tabls` left join `comp_standard_syn_t` `sst` on(((`sst`.`STANDARD_ID` = `tabls`.`standard_id`) and (`sst`.`RES_POOL_ID` = `tabls`.`res_pool_id`) and (`sst`.`RES_POOL_PART_ID` = `tabls`.`res_pool_part_id`) and (`sst`.`TEMPLATE_ID` = `tabls`.`template_id`)))) left join `comp_res_pool_t` `rpt` on((`tabls`.`res_pool_id` = `rpt`.`RES_POOL_ID`))) where (`tabls`.`roolPartStatus` = '1');



-- ----------------------------
--  View definition for `comp_pm_standard_template_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_pm_standard_template_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_pm_standard_template_v` AS SELECT
	`comp_standard_pm_t`.`STANDARD_ID` AS `STANDARD_ID`,
	`comp_standard_pm_t`.`STANDARD_NAME` AS `STANDARD_NAME`,
  `COMP_PM_TYPE_T`.PM_TYPE_NAME AS `SERVER_TYPE`,
	`COMP_PM_TYPE_T`.`CPU_TYPE` AS `CPU_TYPE`,
	`COMP_PM_TYPE_T`.`RAM_SIZE` AS `RAM_SIZE`,
	`COMP_PM_TYPE_T`.`DISC_SIZE` AS `DISC_SIZE`,
	`comp_standard_pm_t`.`DESCRIPTION` AS `DESCRIPTION`,
	`comp_standard_pm_t`.`STATUS` AS `STATUS`,
	`comp_standard_pm_t`.`CREATE_TIME` AS `CREATE_TIME`,
	`comp_standard_pm_t`.`CREATE_USER` AS `CREATE_USER`,
	`comp_standard_pm_t`.`UPDATE_TIME` AS `UPDATE_TIME`,
	`comp_standard_pm_t`.`UPDATE_USER` AS `UPDATE_USER`
FROM
	`comp_standard_pm_t`
LEFT OUTER JOIN `COMP_PM_TYPE_T` ON `comp_standard_pm_t`.SERVER_TYPE = `COMP_PM_TYPE_T`.PM_TYPE_ID
WHERE
	(
		`comp_standard_pm_t`.`STATUS` = '0'
	);
	
	
-- ----------------------------
--  View definition for `comp_pm_template_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_pm_template_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_pm_template_v` AS SELECT
	`rpp`.`STATUS` AS `status`,
	`ot`.`OS_ID` AS `os_id`,
	`rpp`.`RES_POOL_PART_ID` AS `res_pool_part_id`,
	`rpp`.`RES_POOL_ID` AS `res_pool_id`,
	`svt`.`STANDARD_NAME` AS `standard_name`,
	`svt`.`STANDARD_ID` AS `standard_id`,
	`svt`.`RAM_SIZE` AS `ram_size`,
	`svt`.`DISC_SIZE` AS `disc_size`,
	`svt`.`CPU_TYPE` AS `cpu_type`,
	`svt`.`STATUS` AS `svtstatus`,
	`rpp`.`RES_POOL_PART_NAME` AS `res_pool_part_name`,
	`rpp`.`RES_POOL_NAME` AS `res_pool_name`,
	`ot`.`OS_NAME` AS `os_name`
FROM
	(
		(
			`comp_pm_standard_template_v` `svt`
			JOIN `comp_os_t` `ot` ON ((`ot`.`Resource_TYPE` = '1'))
		)
		JOIN `comp_res_pool_part_t` `rpp`
	);

	
-- ----------------------------
--  View definition for `comp_pm_iso_template_v`
-- ----------------------------
DROP VIEW
IF EXISTS `comp_pm_iso_template_v`;

CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `comp_pm_iso_template_v` AS SELECT
	`crosst`.`os_id` AS `os_id`,
	`crosst`.`res_pool_part_id` AS `res_pool_part_id`,
	`crosst`.`res_pool_id` AS `res_pool_id`,
	`crosst`.`standard_id` AS `standard_id`,
	`crosst`.`standard_name` AS `standard_name`,
	`crosst`.`res_pool_part_name` AS `res_pool_part_name`,
	`crosst`.`res_pool_name` AS `res_pool_name`,
	`sit`.`TEMPLATE_ID` AS `template_id`,
	`crosst`.`os_name` AS `os_name`,
	`crosst`.`status` AS `roolPartStatus`
FROM
	(
		`comp_pm_template_v` `crosst`
		LEFT JOIN `comp_standard_iso_t` `sit` ON (
			(
				(
					`crosst`.`os_id` = `sit`.`OS_ID`
				)
				AND (
					`crosst`.`standard_id` = `sit`.`STANDARD_ID`
				)
			)
		)
	);
	
-- ----------------------------
--  View definition for `comp_pm_template_res_v`
-- ----------------------------
DROP VIEW IF EXISTS `comp_pm_template_res_v`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `comp_pm_template_res_v` AS SELECT
	`tabls`.`os_id` AS `os_id`,
	`tabls`.`res_pool_part_id` AS `res_pool_part_id`,
	`tabls`.`res_pool_id` AS `res_pool_id`,
	`tabls`.`standard_id` AS `standard_id`,
	`tabls`.`standard_name` AS `standard_name`,
	`tabls`.`res_pool_part_name` AS `res_pool_part_name`,
	`rpt`.`RES_POOL_NAME` AS `res_pool_name`,
	`tabls`.`os_name` AS `os_name`,
	`tabls`.`roolPartStatus` AS `roolPartStatus`,
	`sst`.`TEMPLATE_ID` AS `TEMPLATE_ID`,
	(
		CASE
		WHEN (
			(`sst`.`STATUS` = '2')
			OR (`sst`.`STATUS` = '0')
			OR (`sst`.`STATUS` = '3')
			OR (`sst`.`STATUS` = '4')
			OR (`sst`.`STATUS` = '5')
		) THEN
			'1'
		WHEN (`sst`.`STATUS` = '1') THEN
			'2'
		ELSE
			'0'
		END
	) AS `flage`,
	`sst`.`STATUS` AS `status`,
	`rpt`.`STATUS` AS `resStatus`
FROM
	(
		(
			`comp_pm_iso_template_v` `tabls`
			LEFT JOIN `comp_standard_syn_t` `sst` ON (
				(
					(
						`sst`.`STANDARD_ID` = `tabls`.`standard_id`
					)
					AND (
						`sst`.`RES_POOL_ID` = `tabls`.`res_pool_id`
					)
					AND (
						`sst`.`RES_POOL_PART_ID` = `tabls`.`res_pool_part_id`
					)
					AND (
						`sst`.`TEMPLATE_ID` = `tabls`.`template_id`
					)
				)
			)
		)
		LEFT JOIN `comp_res_pool_t` `rpt` ON (
			(
				`tabls`.`res_pool_id` = `rpt`.`RES_POOL_ID`
			)
		)
	)
WHERE
	(
		`tabls`.`roolPartStatus` = '1'
	);
	