/*湖北运营新增存储过程*/
-- ----------------------------
-- Procedure structure for COMP_STA_CAPACITY_DEL_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_CAPACITY_DEL_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_CAPACITY_DEL_PROC`()
BEGIN

DECLARE v_capacityDel INT;

SELECT PARAMETER_VALUE + 0
INTO   v_capacityDel
FROM   COMP_PROC_PARAMETER_T
WHERE  PARAMETER_TAG = 'capacityDel';

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_CAPACITY_DEL_PROC',
     'BEGIN');

/* 删除容量统计表COMP_STA_CAPACITY_T中30天（30天，不包括当天）前的记录 */
DELETE FROM COMP_STA_CAPACITY_T
WHERE CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_capacityDel day),'%Y%m%d000000');

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_CAPACITY_DEL_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_DEVICE_APP_DAY_DEL_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_DEVICE_APP_DAY_DEL_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_DEVICE_APP_DAY_DEL_PROC`()
BEGIN

DECLARE v_deviceAppDayDel INT;

SELECT PARAMETER_VALUE + 0
INTO   v_deviceAppDayDel
FROM   COMP_PROC_PARAMETER_T
WHERE  PARAMETER_TAG = 'deviceAppDayDel';

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_APP_DAY_DEL_PROC',
     'BEGIN');

/* 删除设备历史性能统计表_按业务每天统计COMP_STA_DEVICE_APP_DAY_T中前年的记录 */
DELETE FROM COMP_STA_DEVICE_APP_DAY_T
WHERE REPORT_DATE < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_deviceAppDayDel year),'%Y0101');

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_APP_DAY_DEL_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_DEVICE_APP_DAY_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_DEVICE_APP_DAY_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_DEVICE_APP_DAY_PROC`()
BEGIN

/*定义变量*/
DECLARE v_appId VARCHAR(64);
DECLARE v_deviceType VARCHAR(1);
DECLARE v_deviceNum VARCHAR(11);
DECLARE v_cpuAve VARCHAR(11);
DECLARE v_cpuMax VARCHAR(11);
DECLARE v_cpuMaxId VARCHAR(64);
DECLARE v_cpuMin VARCHAR(11);
DECLARE v_cpuMinId VARCHAR(64);
DECLARE v_cpuOverNum VARCHAR(11);
DECLARE v_cpuOverAve VARCHAR(11);
DECLARE v_cpuNotOverNum VARCHAR(11);
DECLARE v_cpuNotOverAve VARCHAR(11);
DECLARE v_memAve VARCHAR(11);
DECLARE v_memMax VARCHAR(11);
DECLARE v_memMaxId VARCHAR(64);
DECLARE v_memMaxFree VARCHAR(11);
DECLARE v_memMin VARCHAR(11);
DECLARE v_memMinId VARCHAR(64);
DECLARE v_memMinFree VARCHAR(11);
DECLARE v_memOverNum VARCHAR(11);
DECLARE v_memOverAve VARCHAR(11);
DECLARE v_memOverFreeAve VARCHAR(11);
DECLARE v_memNotOverNum VARCHAR(11);
DECLARE v_memNotOverAve VARCHAR(11);
DECLARE v_memNotOverFreeAve VARCHAR(11);
DECLARE v_diskAve VARCHAR(11);
DECLARE v_diskMax VARCHAR(11);
DECLARE v_diskMaxId VARCHAR(64);
DECLARE v_diskMin VARCHAR(11);
DECLARE v_diskMinId VARCHAR(64);
DECLARE v_diskRange1Num VARCHAR(11);
DECLARE v_diskRange2Num VARCHAR(11);
DECLARE v_diskRange3Num VARCHAR(11);
DECLARE done INT;

/*定义游标，查询各个业务的CPU、内存、磁盘平均使用率、最大使用率、最小使用率，CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数*/
DECLARE cur CURSOR FOR

		SELECT T.APPID,
           T.DEVICETYPE,
           SUM(T.DEVICENUM) AS DEVICENUM,
           IFNULL(ROUND(SUM(T.CPUAVE), 2), '0') AS CPUAVE,
           IFNULL(ROUND(SUM(T.CPUMAX), 2), '0') AS CPUMAX,
           T.CPUMAXID,
           IFNULL(ROUND(SUM(T.CPUMIN), 2), '0') AS CPUMIN,
           T.CPUMINID,
					 SUM(T.CPUOVERNUM) AS CPUOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUOVERAVE), 2), '0') AS CPUOVERAVE,
					 SUM(T.CPUNOTOVERNUM) AS CPUNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUNOTOVERAVE), 2), '0') AS CPUNOTOVERAVE,
           IFNULL(ROUND(SUM(T.MEMAVE), 2), '0') AS MEMAVE,
           IFNULL(ROUND(SUM(T.MEMMAX), 2), '0') AS MEMMAX,
           T.MEMMAXID,
           IFNULL(ROUND(SUM(T.MEMMAXFREE), 2), '0') AS MEMMAXFREE,
           IFNULL(ROUND(SUM(T.MEMMIN), 2), '0') AS MEMMIN,
           T.MEMMINID,
           IFNULL(ROUND(SUM(T.MEMMINFREE), 2), '0') AS MEMMINFREE,
					 SUM(T.MEMOVERNUM) AS MEMOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMOVERAVE), 2), '0') AS MEMOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMOVERFREEAVE), 2), '0') AS MEMOVERFREEAVE,
					 SUM(T.MEMNOTOVERNUM) AS MEMNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERAVE), 2), '0') AS MEMNOTOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERFREEAVE), 2), '0') AS MEMNOTOVERFREEAVE,
           IFNULL(ROUND(SUM(T.DISKAVE), 2), '0') AS DISKAVE,
           IFNULL(ROUND(SUM(T.DISKMAX), 2), '0') AS DISKMAX,
           T.DISKMAXID,
           IFNULL(ROUND(SUM(T.DISKMIN), 2), '0') AS DISKMIN,
           T.DISKMINID,
					 SUM(T.DISKRANGElNUM) AS DISKRANGElNUM,
					 SUM(T.DISKRANGE2NUM) AS DISKRANGE2NUM,
					 SUM(T.DISKRANGE3NUM) AS DISKRANGE3NUM
		FROM   (
        SELECT CSDDT.APP_ID AS APPID,
               CSDDT.DEVICE_TYPE AS DEVICETYPE,
               COUNT(CSDDT.DEVICE_ID) AS DEVICENUM,
               SUM(CSDDT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDDT.DEVICE_ID) AS CPUAVE,
               MAX(CSDDT.CPU_PROCESSOR_UTILIZATION + 0) AS CPUMAX,
               (SELECT DEVICE_ID FROM COMP_STA_DEVICE_DAY_T WHERE CPU_PROCESSOR_UTILIZATION = MAX(CSDDT.CPU_PROCESSOR_UTILIZATION + 0) AND APP_ID = CSDDT.APP_ID AND DEVICE_TYPE = CSDDT.DEVICE_TYPE LIMIT 0,1) AS CPUMAXID,
               MIN(CSDDT.CPU_PROCESSOR_UTILIZATION + 0) AS CPUMIN,
               (SELECT DEVICE_ID FROM COMP_STA_DEVICE_DAY_T WHERE CPU_PROCESSOR_UTILIZATION = MIN(CSDDT.CPU_PROCESSOR_UTILIZATION + 0) AND APP_ID = CSDDT.APP_ID AND DEVICE_TYPE = CSDDT.DEVICE_TYPE LIMIT 0,1) AS CPUMINID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
               SUM(CSDDT.MEM_USED_PER) / COUNT(CSDDT.DEVICE_ID) AS MEMAVE,
               MAX(CSDDT.MEM_USED_PER + 0) AS MEMMAX,
               (SELECT DEVICE_ID FROM COMP_STA_DEVICE_DAY_T WHERE MEM_USED_PER = MAX(CSDDT.MEM_USED_PER + 0) AND APP_ID = CSDDT.APP_ID AND DEVICE_TYPE = CSDDT.DEVICE_TYPE LIMIT 0,1) AS MEMMAXID,
               (SELECT MEM_FREE FROM COMP_STA_DEVICE_DAY_T WHERE MEM_USED_PER = MAX(CSDDT.MEM_USED_PER + 0) AND APP_ID = CSDDT.APP_ID AND DEVICE_TYPE = CSDDT.DEVICE_TYPE LIMIT 0,1) AS MEMMAXFREE,
               MIN(CSDDT.MEM_USED_PER + 0) AS MEMMIN,
               (SELECT DEVICE_ID FROM COMP_STA_DEVICE_DAY_T WHERE MEM_USED_PER = MIN(CSDDT.MEM_USED_PER + 0) AND APP_ID = CSDDT.APP_ID AND DEVICE_TYPE = CSDDT.DEVICE_TYPE LIMIT 0,1) AS MEMMINID,
               (SELECT MEM_FREE FROM COMP_STA_DEVICE_DAY_T WHERE MEM_USED_PER = MIN(CSDDT.MEM_USED_PER + 0) AND APP_ID = CSDDT.APP_ID AND DEVICE_TYPE = CSDDT.DEVICE_TYPE LIMIT 0,1) AS MEMMINFREE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
               SUM(CSDDT.DISK_USED_PER) / COUNT(CSDDT.DEVICE_ID) AS DISKAVE,
               MAX(CSDDT.DISK_USED_PER + 0) AS DISKMAX,
               (SELECT DEVICE_ID FROM COMP_STA_DEVICE_DAY_T WHERE DISK_USED_PER = MAX(CSDDT.DISK_USED_PER + 0) AND APP_ID = CSDDT.APP_ID AND DEVICE_TYPE = CSDDT.DEVICE_TYPE LIMIT 0,1) AS DISKMAXID,
               MIN(CSDDT.DISK_USED_PER + 0) AS DISKMIN,
               (SELECT DEVICE_ID FROM COMP_STA_DEVICE_DAY_T WHERE DISK_USED_PER = MIN(CSDDT.DISK_USED_PER + 0) AND APP_ID = CSDDT.APP_ID AND DEVICE_TYPE = CSDDT.DEVICE_TYPE LIMIT 0,1) AS DISKMINID,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM
				FROM   COMP_STA_DEVICE_DAY_T CSDDT
        WHERE  CSDDT.REPORT_DATE = DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
				GROUP BY CSDDT.APP_ID, CSDDT.DEVICE_TYPE

        UNION ALL

				SELECT CSDDT.APP_ID AS APPID,
               CSDDT.DEVICE_TYPE AS DEVICETYPE,
               '0' AS DEVICENUM,
               '0' AS CPUAVE,
               '0' AS CPUMAX,
               '' AS CPUMAXID,
               '0' AS CPUMIN,
               '' AS CPUMINID,
							 COUNT(CSDDT.DEVICE_ID) AS CPUOVERNUM,
							 SUM(CSDDT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDDT.DEVICE_ID) AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
               '0' AS MEMAVE,
               '0' AS MEMMAX,
               '' AS MEMMAXID,
               '0' AS MEMMAXFREE,
               '0' AS MEMMIN,
               '' AS MEMMINID,
               '0' AS MEMMINFREE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
               '0' AS DISKAVE,
               '0' AS DISKMAX,
               '' AS DISKMAXID,
               '0' AS DISKMIN,
               '' AS DISKMINID,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM
				FROM   COMP_STA_DEVICE_DAY_T CSDDT
				WHERE  CSDDT.CPU_PROCESSOR_UTILIZATION > 70
				AND    CSDDT.REPORT_DATE = DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
				GROUP BY CSDDT.APP_ID, CSDDT.DEVICE_TYPE

				UNION ALL

				SELECT CSDDT.APP_ID AS APPID,
               CSDDT.DEVICE_TYPE AS DEVICETYPE,
               '0' AS DEVICENUM,
               '0' AS CPUAVE,
               '0' AS CPUMAX,
               '' AS CPUMAXID,
               '0' AS CPUMIN,
               '' AS CPUMINID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
               COUNT(CSDDT.DEVICE_ID) AS CPUNOTOVERNUM,
               SUM(CSDDT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDDT.DEVICE_ID) AS CPUNOTOVERAVE,
               '0' AS MEMAVE,
               '0' AS MEMMAX,
               '' AS MEMMAXID,
               '0' AS MEMMAXFREE,
               '0' AS MEMMIN,
               '' AS MEMMINID,
               '0' AS MEMMINFREE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
               '0' AS DISKAVE,
               '0' AS DISKMAX,
               '' AS DISKMAXID,
               '0' AS DISKMIN,
               '' AS DISKMINID,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM
				FROM   COMP_STA_DEVICE_DAY_T CSDDT
				WHERE  CSDDT.CPU_PROCESSOR_UTILIZATION <= 70
				AND    CSDDT.REPORT_DATE = DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
				GROUP BY CSDDT.APP_ID, CSDDT.DEVICE_TYPE

				UNION ALL

				SELECT CSDDT.APP_ID AS APPID,
               CSDDT.DEVICE_TYPE AS DEVICETYPE,
               '0' AS DEVICENUM,
               '0' AS CPUAVE,
               '0' AS CPUMAX,
               '' AS CPUMAXID,
               '0' AS CPUMIN,
               '' AS CPUMINID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
               '0' AS CPUNOTOVERNUM,
               '0' AS CPUNOTOVERAVE,
               '0' AS MEMAVE,
               '0' AS MEMMAX,
               '' AS MEMMAXID,
               '0' AS MEMMAXFREE,
               '0' AS MEMMIN,
               '' AS MEMMINID,
               '0' AS MEMMINFREE,
               COUNT(CSDDT.DEVICE_ID) AS MEMOVERNUM,
               SUM(CSDDT.MEM_USED_PER) / COUNT(CSDDT.DEVICE_ID) AS MEMOVERAVE,
							 SUM(CSDDT.MEM_FREE) / COUNT(CSDDT.DEVICE_ID) AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
               '0' AS DISKAVE,
               '0' AS DISKMAX,
               '' AS DISKMAXID,
               '0' AS DISKMIN,
               '' AS DISKMINID,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM
				FROM   COMP_STA_DEVICE_DAY_T CSDDT
				WHERE  CSDDT.MEM_USED_PER > 70
				AND    CSDDT.REPORT_DATE = DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
				GROUP BY CSDDT.APP_ID, CSDDT.DEVICE_TYPE

				UNION ALL
				
				SELECT CSDDT.APP_ID AS APPID,
               CSDDT.DEVICE_TYPE AS DEVICETYPE,
               '0' AS DEVICENUM,
               '0' AS CPUAVE,
               '0' AS CPUMAX,
               '' AS CPUMAXID,
               '0' AS CPUMIN,
               '' AS CPUMINID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
               '0' AS CPUNOTOVERNUM,
               '0' AS CPUNOTOVERAVE,
               '0' AS MEMAVE,
               '0' AS MEMMAX,
               '' AS MEMMAXID,
               '0' AS MEMMAXFREE,
               '0' AS MEMMIN,
               '' AS MEMMINID,
               '0' AS MEMMINFREE,
               '0' AS MEMOVERNUM,
               '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
               COUNT(CSDDT.DEVICE_ID) AS MEMNOTOVERNUM,
							 SUM(CSDDT.MEM_USED_PER) / COUNT(CSDDT.DEVICE_ID) AS MEMNOTOVERAVE,
							 SUM(CSDDT.MEM_FREE) / COUNT(CSDDT.DEVICE_ID) AS MEMNOTOVERFREEAVE,
               '0' AS DISKAVE,
               '0' AS DISKMAX,
               '' AS DISKMAXID,
               '0' AS DISKMIN,
               '' AS DISKMINID,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM
				FROM   COMP_STA_DEVICE_DAY_T CSDDT
				WHERE  CSDDT.MEM_USED_PER <= 70
				AND    CSDDT.REPORT_DATE = DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
				GROUP BY CSDDT.APP_ID, CSDDT.DEVICE_TYPE

				UNION ALL

				SELECT CSDDT.APP_ID AS APPID,
               CSDDT.DEVICE_TYPE AS DEVICETYPE,
               '0' AS DEVICENUM,
               '0' AS CPUAVE,
               '0' AS CPUMAX,
               '' AS CPUMAXID,
               '0' AS CPUMIN,
               '' AS CPUMINID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
               '0' AS CPUNOTOVERNUM,
               '0' AS CPUNOTOVERAVE,
               '0' AS MEMAVE,
               '0' AS MEMMAX,
               '' AS MEMMAXID,
               '0' AS MEMMAXFREE,
               '0' AS MEMMIN,
               '' AS MEMMINID,
               '0' AS MEMMINFREE,
               '0' AS MEMOVERNUM,
               '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
               '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
               '0' AS DISKAVE,
               '0' AS DISKMAX,
               '' AS DISKMAXID,
               '0' AS DISKMIN,
               '' AS DISKMINID,
               COUNT(CSDDT.DEVICE_ID) AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM
				FROM   COMP_STA_DEVICE_DAY_T CSDDT
				WHERE  CSDDT.DISK_USED_PER > 70
				AND    CSDDT.REPORT_DATE = DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
				GROUP BY CSDDT.APP_ID, CSDDT.DEVICE_TYPE

				UNION ALL

				SELECT CSDDT.APP_ID AS APPID,
               CSDDT.DEVICE_TYPE AS DEVICETYPE,
               '0' AS DEVICENUM,
               '0' AS CPUAVE,
               '0' AS CPUMAX,
               '' AS CPUMAXID,
               '0' AS CPUMIN,
               '' AS CPUMINID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
               '0' AS CPUNOTOVERNUM,
               '0' AS CPUNOTOVERAVE,
               '0' AS MEMAVE,
               '0' AS MEMMAX,
               '' AS MEMMAXID,
               '0' AS MEMMAXFREE,
               '0' AS MEMMIN,
               '' AS MEMMINID,
               '0' AS MEMMINFREE,
               '0' AS MEMOVERNUM,
               '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
               '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
               '0' AS DISKAVE,
               '0' AS DISKMAX,
               '' AS DISKMAXID,
               '0' AS DISKMIN,
               '' AS DISKMINID,
               '0' AS DISKRANGElNUM,
               COUNT(CSDDT.DEVICE_ID) AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM
				FROM   COMP_STA_DEVICE_DAY_T CSDDT
				WHERE  CSDDT.DISK_USED_PER <= 70
				AND    CSDDT.DISK_USED_PER > 30
				AND    CSDDT.REPORT_DATE = DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
				GROUP BY CSDDT.APP_ID, CSDDT.DEVICE_TYPE

				UNION ALL

				SELECT CSDDT.APP_ID AS APPID,
               CSDDT.DEVICE_TYPE AS DEVICETYPE,
               '0' AS DEVICENUM,
               '0' AS CPUAVE,
               '0' AS CPUMAX,
               '' AS CPUMAXID,
               '0' AS CPUMIN,
               '' AS CPUMINID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
               '0' AS CPUNOTOVERNUM,
               '0' AS CPUNOTOVERAVE,
               '0' AS MEMAVE,
               '0' AS MEMMAX,
               '' AS MEMMAXID,
               '0' AS MEMMAXFREE,
               '0' AS MEMMIN,
               '' AS MEMMINID,
               '0' AS MEMMINFREE,
               '0' AS MEMOVERNUM,
               '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
               '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
               '0' AS DISKAVE,
               '0' AS DISKMAX,
               '' AS DISKMAXID,
               '0' AS DISKMIN,
               '' AS DISKMINID,
               '0' AS DISKRANGElNUM,
               '0' AS DISKRANGE2NUM,
							 COUNT(CSDDT.DEVICE_ID) AS DISKRANGE3NUM
				FROM   COMP_STA_DEVICE_DAY_T CSDDT
				WHERE  CSDDT.DISK_USED_PER <= 30
				AND    CSDDT.REPORT_DATE = DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
				GROUP BY CSDDT.APP_ID, CSDDT.DEVICE_TYPE

		) T
		GROUP BY T.APPID, T.DEVICETYPE;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_APP_DAY_PROC',
     'BEGIN');

/*循环遍历数据，将各个业务的CPU、内存、磁盘平均使用率、最大使用率、最小使用率，CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_appId,
                       v_deviceType,
                       v_deviceNum,
                       v_cpuAve,
                       v_cpuMax,
                       v_cpuMaxId,
                       v_cpuMin,
                       v_cpuMinId,
                       v_cpuOverNum,
                       v_cpuOverAve,
                       v_cpuNotOverNum,
                       v_cpuNotOverAve,
                       v_memAve,
                       v_memMax,
                       v_memMaxId,
                       v_memMaxFree,
                       v_memMin,
                       v_memMinId,
                       v_memMinFree,
                       v_memOverNum,
                       v_memOverAve,
                       v_memOverFreeAve,
                       v_memNotOverNum,
                       v_memNotOverAve,
                       v_memNotOverFreeAve,
                       v_diskAve,
                       v_diskMax,
                       v_diskMaxId,
                       v_diskMin,
                       v_diskMinId,
                       v_diskRange1Num,
                       v_diskRange2Num,
                       v_diskRange3Num;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入各个业务的CPU、内存、磁盘平均使用率、最大使用率、最小使用率，CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数 */
				INSERT INTO COMP_STA_DEVICE_APP_DAY_T(
				    APP_ID,
            DEVICE_TYPE,
            DEVICE_NUM,
            CPU_AVE,
            CPU_MAX,
            CPU_MAX_ID,
            CPU_MIN,
            CPU_MIN_ID,
            CPU_OVER_NUM,
            CPU_OVER_AVE,
            CPU_NOT_OVER_NUM,
            CPU_NOT_OVER_AVE,
            MEM_AVE,
            MEM_MAX,
            MEM_MAX_ID,
            MEM_MAX_FREE,
            MEM_MIN,
            MEM_MIN_ID,
            MEM_MIN_FREE,
            MEM_OVER_NUM,
            MEM_OVER_AVE,
            MEM_OVER_FREE_AVE,
            MEM_NOT_OVER_NUM,
            MEM_NOT_OVER_AVE,
            MEM_NOT_OVER_FREE_AVE,
            DISK_AVE,
            DISK_MAX,
            DISK_MAX_ID,
            DISK_MIN,
            DISK_MIN_ID,
            DISK_RANGEl_NUM,
            DISK_RANGE2_NUM,
            DISK_RANGE3_NUM,
            REPORT_DATE) 
				VALUES(
				    v_appId,
					  v_deviceType,
					  v_deviceNum,
					  v_cpuAve,
					  v_cpuMax,
					  v_cpuMaxId,
					  v_cpuMin,
					  v_cpuMinId,
					  v_cpuOverNum,
					  v_cpuOverAve,
					  v_cpuNotOverNum,
					  v_cpuNotOverAve,
					  v_memAve,
					  v_memMax,
					  v_memMaxId,
					  v_memMaxFree,
					  v_memMin,
					  v_memMinId,
					  v_memMinFree,
					  v_memOverNum,
					  v_memOverAve,
					  v_memOverFreeAve,
					  v_memNotOverNum,
					  v_memNotOverAve,
					  v_memNotOverFreeAve,
					  v_diskAve,
					  v_diskMax,
					  v_diskMaxId,
					  v_diskMin,
					  v_diskMinId,
					  v_diskRange1Num,
					  v_diskRange2Num,
					  v_diskRange3Num,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
            );
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_APP_DAY_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_DEVICE_APP_WEEK_DEL_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_DEVICE_APP_WEEK_DEL_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_DEVICE_APP_WEEK_DEL_PROC`()
BEGIN

DECLARE v_deviceAppWeekDel INT;

SELECT PARAMETER_VALUE + 0
INTO   v_deviceAppWeekDel
FROM   COMP_PROC_PARAMETER_T
WHERE  PARAMETER_TAG = 'deviceAppWeekDel';

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_APP_WEEK_DEL_PROC',
     'BEGIN');

/* 删除设备性能统计表_按业务统计COMP_STA_DEVICE_APP_WEEK_T中1天（1天，不包括当天）前的记录 */
DELETE FROM COMP_STA_DEVICE_APP_WEEK_T
WHERE REPORT_DATE < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_deviceAppWeekDel day),'%Y%m%d');

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_APP_WEEK_DEL_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_DEVICE_DAY_DEL_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_DEVICE_DAY_DEL_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_DEVICE_DAY_DEL_PROC`()
BEGIN

DECLARE v_deviceDayDel INT;

SELECT PARAMETER_VALUE + 0
INTO   v_deviceDayDel
FROM   COMP_PROC_PARAMETER_T
WHERE  PARAMETER_TAG = 'deviceDayDel';

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_DAY_DEL_PROC',
     'BEGIN');

/* 删除设备性能统计表_每天COMP_STA_DEVICE_DAY_T中2天（2天，不包括当天）前的记录 */
DELETE FROM COMP_STA_DEVICE_DAY_T
WHERE REPORT_DATE < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_deviceDayDel day),'%Y%m%d');

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_DAY_DEL_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_DEVICE_DISK_RT_DEL_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_DEVICE_DISK_RT_DEL_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_DEVICE_DISK_RT_DEL_PROC`()
BEGIN

DECLARE v_rtPerformanceDel INT;

SELECT PARAMETER_VALUE + 0
INTO   v_rtPerformanceDel
FROM   COMP_PROC_PARAMETER_T
WHERE  PARAMETER_TAG = 'rtPerformanceDel';

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_DISK_RT_DEL_PROC',
     'BEGIN');

/* 删除设备实时性能表_磁盘COMP_STA_XXX_DISK_RT_T中7天（7天，不包括当天）前的记录 */
/* 物理机实时性能表_磁盘 */
DELETE FROM COMP_STA_PM_DISK_RT_T
WHERE CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_rtPerformanceDel day),'%Y%m%d000000');
/* 虚拟机实时性能表_磁盘 */
DELETE FROM COMP_STA_VM_DISK_RT_T
WHERE CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_rtPerformanceDel day),'%Y%m%d000000');


/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_DISK_RT_DEL_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_DEVICE_RT_DEL_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_DEVICE_RT_DEL_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_DEVICE_RT_DEL_PROC`()
BEGIN

/*定义变量*/
DECLARE v_rtPerformanceDel INT;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_RT_DEL_PROC',
     'BEGIN');

/* 删除实时性能表COMP_STA_xxx_RT_T中7天（7天，不包括当天）前的记录 */
SELECT PARAMETER_VALUE + 0
INTO   v_rtPerformanceDel
FROM   COMP_PROC_PARAMETER_T
WHERE  PARAMETER_TAG = 'rtPerformanceDel';

DELETE FROM COMP_STA_MINIPM_RT_T
WHERE CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_rtPerformanceDel day),'%Y%m%d000000');

DELETE FROM COMP_STA_MINIPMPAR_RT_T
WHERE CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_rtPerformanceDel day),'%Y%m%d000000');

DELETE FROM COMP_STA_PM_RT_T
WHERE CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_rtPerformanceDel day),'%Y%m%d000000');

DELETE FROM COMP_STA_VM_RT_T
WHERE CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_rtPerformanceDel day),'%Y%m%d000000');

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_RT_DEL_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_DEVICE_WEEK_DEL_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_DEVICE_WEEK_DEL_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_DEVICE_WEEK_DEL_PROC`()
BEGIN

DECLARE v_deviceWeekDel INT;

SELECT PARAMETER_VALUE + 0
INTO   v_deviceWeekDel
FROM   COMP_PROC_PARAMETER_T
WHERE  PARAMETER_TAG = 'deviceWeekDel';

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_WEEK_DEL_PROC',
     'BEGIN');

/* 删除设备性能统计表_近一周COMP_STA_DEVICE_WEEK_T中1天（1天，不包括当天）前的记录 */
DELETE FROM COMP_STA_DEVICE_WEEK_T
WHERE REPORT_DATE < DATE_FORMAT(DATE_SUB(SYSDATE(), interval v_deviceWeekDel day),'%Y%m%d');

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_DEVICE_WEEK_DEL_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_EBS_APP_WEEK_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_EBS_APP_WEEK_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_EBS_APP_WEEK_PROC`()
BEGIN

/*定义变量*/
DECLARE v_appId VARCHAR(64);
DECLARE v_deviceNum VARCHAR(11);
DECLARE done INT;

/*定义游标，查询每个资源池下，各个业务的虚拟硬盘数*/
DECLARE cur CURSOR FOR
		SELECT CRET.APP_ID AS APPID, COUNT(CRET.EBS_ID) AS DEVICENUM
		FROM   COMP_RM_EBS_T CRET,
					 COMP_APP_T CAT
		WHERE  CRET.APP_ID = CAT.APP_ID
		GROUP BY CRET.APP_ID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_EBS_APP_WEEK_PROC',
     'BEGIN');

/*循环遍历数据，将每个资源池下，各个业务的虚拟硬盘数入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_appId, v_deviceNum;
        
        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入每个资源池下，各个业务的虚拟硬盘数 */
				INSERT INTO COMP_STA_DEVICE_APP_WEEK_T(
				    APP_ID, DEVICE_TYPE, DEVICE_NUM, REPORT_DATE) 
				VALUES(
				    v_appId, '4', v_deviceNum, DATE_FORMAT(SYSDATE(),'%Y%m%d'));
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_EBS_APP_WEEK_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_MINIPMPAR_APP_WEEK_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_MINIPMPAR_APP_WEEK_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_MINIPMPAR_APP_WEEK_PROC`()
BEGIN

/*定义变量*/
DECLARE v_appId VARCHAR(64);
DECLARE v_deviceNum VARCHAR(11);
DECLARE v_cpuOverNum VARCHAR(11);
DECLARE v_cpuOverAve VARCHAR(11);
DECLARE v_cpuNotOverNum VARCHAR(11);
DECLARE v_cpuNotOverAve VARCHAR(11);
DECLARE v_memOverNum VARCHAR(11);
DECLARE v_memOverAve VARCHAR(11);
DECLARE v_memOverFreeAve VARCHAR(11);
DECLARE v_memNotOverNum VARCHAR(11);
DECLARE v_memNotOverAve VARCHAR(11);
DECLARE v_memNotOverFreeAve VARCHAR(11);
DECLARE v_diskRange1Num VARCHAR(11);
DECLARE v_diskRange2Num VARCHAR(11);
DECLARE v_diskRange3Num VARCHAR(11);
DECLARE done INT;

/*定义游标，查询各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数*/
DECLARE cur CURSOR FOR
		SELECT T.APPID,
					 SUM(T.CPUOVERNUM) AS CPUOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUOVERAVE), 2), '0') AS CPUOVERAVE,
					 SUM(T.CPUNOTOVERNUM) AS CPUNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUNOTOVERAVE), 2), '0') AS CPUNOTOVERAVE,
					 SUM(T.MEMOVERNUM) AS MEMOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMOVERAVE), 2), '0') AS MEMOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMOVERFREEAVE), 2), '0') AS MEMOVERFREEAVE,
					 SUM(T.MEMNOTOVERNUM) AS MEMNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERAVE), 2), '0') AS MEMNOTOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERFREEAVE), 2), '0') AS MEMNOTOVERFREEAVE,
					 SUM(T.DISKRANGElNUM) AS DISKRANGElNUM,
					 SUM(T.DISKRANGE2NUM) AS DISKRANGE2NUM,
					 SUM(T.DISKRANGE3NUM) AS DISKRANGE3NUM,
					 SUM(T.DEVICENUM) AS DEVICENUM
		FROM   (

				SELECT CRMPPT.APP_ID AS APPID,
							 COUNT(CSDWT.DEVICE_ID) AS CPUOVERNUM,
							 SUM(CSDWT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDWT.DEVICE_ID) AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPMPAR_T CRMPPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPPT.MINIPMPAR_ID
				AND    CRMPPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '1'
				AND    CSDWT.CPU_PROCESSOR_UTILIZATION > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPPT.APP_ID

				UNION ALL

				SELECT CRMPPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 COUNT(CSDWT.DEVICE_ID) AS CPUNOTOVERNUM,
							 SUM(CSDWT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDWT.DEVICE_ID) AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPMPAR_T CRMPPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPPT.MINIPMPAR_ID
				AND    CRMPPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '1'
				AND    CSDWT.CPU_PROCESSOR_UTILIZATION <= 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPPT.APP_ID

				UNION ALL

				SELECT CRMPPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 COUNT(CSDWT.DEVICE_ID) AS MEMOVERNUM,
							 SUM(CSDWT.MEM_USED_PER) / COUNT(CSDWT.DEVICE_ID) AS MEMOVERAVE,
							 SUM(CSDWT.MEM_FREE) / COUNT(CSDWT.DEVICE_ID) AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPMPAR_T CRMPPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPPT.MINIPMPAR_ID
				AND    CRMPPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '1'
				AND    CSDWT.MEM_USED_PER > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPPT.APP_ID

				UNION ALL
				
				SELECT CRMPPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERNUM,
							 SUM(CSDWT.MEM_USED_PER) / COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERAVE,
							 SUM(CSDWT.MEM_FREE) / COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPMPAR_T CRMPPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPPT.MINIPMPAR_ID
				AND    CRMPPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '1'
				AND    CSDWT.MEM_USED_PER <= 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPPT.APP_ID

				UNION ALL

				SELECT CRMPPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPMPAR_T CRMPPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPPT.MINIPMPAR_ID
				AND    CRMPPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '1'
				AND    CSDWT.DISK_USED_PER > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPPT.APP_ID

				UNION ALL

				SELECT CRMPPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPMPAR_T CRMPPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPPT.MINIPMPAR_ID
				AND    CRMPPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '1'
				AND    CSDWT.DISK_USED_PER <= 70
				AND    CSDWT.DISK_USED_PER > 30
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPPT.APP_ID

				UNION ALL

				SELECT CRMPPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPMPAR_T CRMPPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPPT.MINIPMPAR_ID
				AND    CRMPPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '1'
				AND    CSDWT.DISK_USED_PER <= 30
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPPT.APP_ID

				UNION ALL

				SELECT CRMPPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 COUNT(CRMPPT.MINIPMPAR_ID) AS DEVICENUM
				FROM   COMP_RM_MINIPMPAR_T CRMPPT,
							 COMP_APP_T CAT
				WHERE  CRMPPT.APP_ID = CAT.APP_ID
				GROUP BY CRMPPT.APP_ID

		) T
		GROUP BY T.APPID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPMPAR_APP_WEEK_PROC',
     'BEGIN');

/*循环遍历数据，将各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_appId, v_cpuOverNum, v_cpuOverAve, v_cpuNotOverNum, v_cpuNotOverAve, v_memOverNum, v_memOverAve, v_memOverFreeAve, v_memNotOverNum, v_memNotOverAve, v_memNotOverFreeAve, v_diskRange1Num, v_diskRange2Num, v_diskRange3Num, v_deviceNum;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数 */
				INSERT INTO COMP_STA_DEVICE_APP_WEEK_T(
				    APP_ID,
            DEVICE_TYPE,
            DEVICE_NUM,
            CPU_OVER_NUM,
            CPU_OVER_AVE,
            CPU_NOT_OVER_NUM,
            CPU_NOT_OVER_AVE,
            MEM_OVER_NUM,
            MEM_OVER_AVE,
            MEM_OVER_FREE_AVE,
            MEM_NOT_OVER_NUM,
            MEM_NOT_OVER_AVE,
            MEM_NOT_OVER_FREE_AVE,
            DISK_RANGEl_NUM,
            DISK_RANGE2_NUM,
            DISK_RANGE3_NUM,
            REPORT_DATE) 
				VALUES(
				    v_appId,
            '1',
            v_deviceNum,
            v_cpuOverNum,
            v_cpuOverAve,
            v_cpuNotOverNum,
            v_cpuNotOverAve,
            v_memOverNum,
            v_memOverAve,
            v_memOverFreeAve,
            v_memNotOverNum,
            v_memNotOverAve,
            v_memNotOverFreeAve,
            v_diskRange1Num,
            v_diskRange2Num,
            v_diskRange3Num,
            DATE_FORMAT(SYSDATE(),'%Y%m%d')
            );
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPMPAR_APP_WEEK_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_MINIPMPAR_DAY_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_MINIPMPAR_DAY_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_MINIPMPAR_DAY_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_appId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_cpuOverTime VARCHAR(64);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memOverTime VARCHAR(11);
DECLARE v_memFree VARCHAR(20);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询昨天设备性能的平均值*/
DECLARE cur CURSOR FOR
    SELECT T1.DEVICEID,
           CRMPPT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER
		FROM   COMP_RM_MINIPMPAR_T CRMPPT,
           COMP_APP_T CAT,
		(
				SELECT CSMPPRT.MINIPMPAR_ID AS DEVICEID,
							 SUM(CSMPPRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSMPPRT.MINIPMPAR_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSMPPRT.MEM_USED_PER) / COUNT(CSMPPRT.MINIPMPAR_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSMPPRT.MEM_FREE) / COUNT(CSMPPRT.MINIPMPAR_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_MINIPMPAR_RT_T CSMPPRT
				WHERE  CSMPPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSMPPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSMPPRT.MINIPMPAR_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSMPPRT.DISK_USED_PER AS DISKUSEDPER
				FROM   COMP_STA_MINIPMPAR_RT_T CSMPPRT,
							 (
										SELECT TEMP.MINIPMPAR_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_MINIPMPAR_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
										GROUP BY TEMP.MINIPMPAR_ID
							 ) T
				WHERE  CSMPPRT.MINIPMPAR_ID = T.DEVICEID
				AND    CSMPPRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSMPPRT.MINIPMPAR_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSMPPRT.MINIPMPAR_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_MINIPMPAR_RT_T CSMPPRT
				WHERE  CSMPPRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSMPPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSMPPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSMPPRT.MINIPMPAR_ID

        UNION ALL

        SELECT CSMPPRT.MINIPMPAR_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSMPPRT.MINIPMPAR_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_MINIPMPAR_RT_T CSMPPRT
				WHERE  CSMPPRT.MEM_USED_PER > 70
        AND    CSMPPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSMPPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSMPPRT.MINIPMPAR_ID
		) T1
    WHERE T1.DEVICEID = CRMPPT.MINIPMPAR_ID
    AND   CRMPPT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPMPAR_DAY_PROC',
     'BEGIN');

/*循环遍历数据，将昨天设备性能的平均值入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备昨天的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_DAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            REPORT_DATE) 
				VALUES(
				    v_deviceId,
            '1',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
            );
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPMPAR_DAY_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_MINIPMPAR_WEEK_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_MINIPMPAR_WEEK_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_MINIPMPAR_WEEK_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memFree VARCHAR(11);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询近一周（不包括今天）设备性能的平均值*/
DECLARE cur CURSOR FOR
    SELECT T1.DEVICEID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER
		FROM   
		(
				SELECT CSMPPRT.MINIPMPAR_ID AS DEVICEID,
							 SUM(CSMPPRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSMPPRT.MINIPMPAR_ID) AS CPUPROCESSORUTILIZATION,
							 SUM(CSMPPRT.MEM_USED_PER) / COUNT(CSMPPRT.MINIPMPAR_ID) AS MEMUSEDPER,
							 SUM(CSMPPRT.MEM_FREE) / COUNT(CSMPPRT.MINIPMPAR_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_MINIPMPAR_RT_T CSMPPRT
				WHERE  CSMPPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 7 day),'%Y%m%d000000')
				AND    CSMPPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSMPPRT.MINIPMPAR_ID

				UNION ALL

				SELECT T.DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS MEMUSEDPER,
							 '0' AS MEMFREE,
							 CSMPPRT.DISK_USED_PER AS DISKUSEDPER
				FROM   COMP_STA_MINIPMPAR_RT_T CSMPPRT,
							 (
										SELECT TEMP.MINIPMPAR_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_MINIPMPAR_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 7 day),'%Y%m%d000000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
										GROUP BY TEMP.MINIPMPAR_ID
							 ) T
				WHERE  CSMPPRT.MINIPMPAR_ID = T.DEVICEID
				AND    CSMPPRT.CREATE_TIME = T.CREATETIME
		) T1
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPMPAR_WEEK_PROC',
     'BEGIN');

/*循环遍历数据，将近一周（不包括今天）设备性能的平均值入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_deviceId, v_cpuProcessorUtilization, v_memUsedPer, v_memFree, v_diskUsedPer;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备近一周的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_WEEK_T(
				    DEVICE_ID, DEVICE_TYPE, CPU_PROCESSOR_UTILIZATION, MEM_USED_PER, MEM_FREE, DISK_USED_PER, REPORT_DATE) 
				VALUES(
				    v_deviceId, '1', v_cpuProcessorUtilization, v_memUsedPer, v_memFree, v_diskUsedPer, DATE_FORMAT(SYSDATE(),'%Y%m%d'));
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPMPAR_WEEK_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_MINIPM_APP_WEEK_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_MINIPM_APP_WEEK_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_MINIPM_APP_WEEK_PROC`()
BEGIN

/*定义变量*/
DECLARE v_appId VARCHAR(64);
DECLARE v_deviceNum VARCHAR(11);
DECLARE v_cpuOverNum VARCHAR(11);
DECLARE v_cpuOverAve VARCHAR(11);
DECLARE v_cpuNotOverNum VARCHAR(11);
DECLARE v_cpuNotOverAve VARCHAR(11);
DECLARE v_memOverNum VARCHAR(11);
DECLARE v_memOverAve VARCHAR(11);
DECLARE v_memOverFreeAve VARCHAR(11);
DECLARE v_memNotOverNum VARCHAR(11);
DECLARE v_memNotOverAve VARCHAR(11);
DECLARE v_memNotOverFreeAve VARCHAR(11);
DECLARE v_diskRange1Num VARCHAR(11);
DECLARE v_diskRange2Num VARCHAR(11);
DECLARE v_diskRange3Num VARCHAR(11);
DECLARE done INT;

/*定义游标，查询各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数*/
DECLARE cur CURSOR FOR
		SELECT T.APPID,
					 SUM(T.CPUOVERNUM) AS CPUOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUOVERAVE), 2), '0') AS CPUOVERAVE,
					 SUM(T.CPUNOTOVERNUM) AS CPUNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUNOTOVERAVE), 2), '0') AS CPUNOTOVERAVE,
					 SUM(T.MEMOVERNUM) AS MEMOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMOVERAVE), 2), '0') AS MEMOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMOVERFREEAVE), 2), '0') AS MEMOVERFREEAVE,
					 SUM(T.MEMNOTOVERNUM) AS MEMNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERAVE), 2), '0') AS MEMNOTOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERFREEAVE), 2), '0') AS MEMNOTOVERFREEAVE,
					 SUM(T.DISKRANGElNUM) AS DISKRANGElNUM,
					 SUM(T.DISKRANGE2NUM) AS DISKRANGE2NUM,
					 SUM(T.DISKRANGE3NUM) AS DISKRANGE3NUM,
					 SUM(T.DEVICENUM) AS DEVICENUM
		FROM   (

				SELECT CRMPT.APP_ID AS APPID,
							 COUNT(CSDWT.DEVICE_ID) AS CPUOVERNUM,
							 SUM(CSDWT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDWT.DEVICE_ID) AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPM_T CRMPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPT.MINIPM_ID
				AND    CRMPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '0'
				AND    CSDWT.CPU_PROCESSOR_UTILIZATION > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPT.APP_ID

				UNION ALL

				SELECT CRMPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 COUNT(CSDWT.DEVICE_ID) AS CPUNOTOVERNUM,
							 SUM(CSDWT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDWT.DEVICE_ID) AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPM_T CRMPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPT.MINIPM_ID
				AND    CRMPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '0'
				AND    CSDWT.CPU_PROCESSOR_UTILIZATION <= 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPT.APP_ID

				UNION ALL

				SELECT CRMPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 COUNT(CSDWT.DEVICE_ID) AS MEMOVERNUM,
							 SUM(CSDWT.MEM_USED_PER) / COUNT(CSDWT.DEVICE_ID) AS MEMOVERAVE,
							 SUM(CSDWT.MEM_FREE) / COUNT(CSDWT.DEVICE_ID) AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPM_T CRMPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPT.MINIPM_ID
				AND    CRMPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '0'
				AND    CSDWT.MEM_USED_PER > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPT.APP_ID

				UNION ALL
				
				SELECT CRMPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERNUM,
							 SUM(CSDWT.MEM_USED_PER) / COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERAVE,
							 SUM(CSDWT.MEM_FREE) / COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPM_T CRMPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPT.MINIPM_ID
				AND    CRMPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '0'
				AND    CSDWT.MEM_USED_PER <= 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPT.APP_ID

				UNION ALL

				SELECT CRMPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPM_T CRMPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPT.MINIPM_ID
				AND    CRMPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '0'
				AND    CSDWT.DISK_USED_PER > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPT.APP_ID

				UNION ALL

				SELECT CRMPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPM_T CRMPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPT.MINIPM_ID
				AND    CRMPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '0'
				AND    CSDWT.DISK_USED_PER <= 70
				AND    CSDWT.DISK_USED_PER > 30
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPT.APP_ID

				UNION ALL

				SELECT CRMPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_MINIPM_T CRMPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRMPT.MINIPM_ID
				AND    CRMPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '0'
				AND    CSDWT.DISK_USED_PER <= 30
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRMPT.APP_ID

				UNION ALL

				SELECT CRMPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 COUNT(CRMPT.MINIPM_ID) AS DEVICENUM
				FROM   COMP_RM_MINIPM_T CRMPT,
							 COMP_APP_T CAT
				WHERE  CRMPT.APP_ID = CAT.APP_ID
				GROUP BY CRMPT.APP_ID

		) T
		GROUP BY T.APPID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPM_APP_WEEK_PROC',
     'BEGIN');

/*循环遍历数据，将各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_appId, v_cpuOverNum, v_cpuOverAve, v_cpuNotOverNum, v_cpuNotOverAve, v_memOverNum, v_memOverAve, v_memOverFreeAve, v_memNotOverNum, v_memNotOverAve, v_memNotOverFreeAve, v_diskRange1Num, v_diskRange2Num, v_diskRange3Num, v_deviceNum;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数 */
				INSERT INTO COMP_STA_DEVICE_APP_WEEK_T(
				    APP_ID,
            DEVICE_TYPE,
            DEVICE_NUM,
            CPU_OVER_NUM,
            CPU_OVER_AVE,
            CPU_NOT_OVER_NUM,
            CPU_NOT_OVER_AVE,
            MEM_OVER_NUM,
            MEM_OVER_AVE,
            MEM_OVER_FREE_AVE,
            MEM_NOT_OVER_NUM,
            MEM_NOT_OVER_AVE,
            MEM_NOT_OVER_FREE_AVE,
            DISK_RANGEl_NUM,
            DISK_RANGE2_NUM,
            DISK_RANGE3_NUM,
            REPORT_DATE) 
				VALUES(
				    v_appId,
            '0',
            v_deviceNum,
            v_cpuOverNum,
            v_cpuOverAve,
            v_cpuNotOverNum,
            v_cpuNotOverAve,
            v_memOverNum,
            v_memOverAve,
            v_memOverFreeAve,
            v_memNotOverNum,
            v_memNotOverAve,
            v_memNotOverFreeAve,
            v_diskRange1Num,
            v_diskRange2Num,
            v_diskRange3Num,
            DATE_FORMAT(SYSDATE(),'%Y%m%d')
            );
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPM_APP_WEEK_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_MINIPM_DAY_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_MINIPM_DAY_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_MINIPM_DAY_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_appId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_cpuOverTime VARCHAR(64);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memOverTime VARCHAR(11);
DECLARE v_memFree VARCHAR(11);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询昨天设备性能的平均值*/
DECLARE cur CURSOR FOR
    SELECT T1.DEVICEID,
           CRMPT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER
		FROM   COMP_RM_MINIPM_T CRMPT,
           COMP_APP_T CAT,
		(
				SELECT CSMPRT.MINIPM_ID AS DEVICEID,
							 SUM(CSMPRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSMPRT.MINIPM_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSMPRT.MEM_USED_PER) / COUNT(CSMPRT.MINIPM_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSMPRT.MEM_FREE) / COUNT(CSMPRT.MINIPM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_MINIPM_RT_T CSMPRT
				WHERE  CSMPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSMPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSMPRT.MINIPM_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSMPRT.DISK_USED_PER AS DISKUSEDPER
				FROM   COMP_STA_MINIPM_RT_T CSMPRT,
							 (
										SELECT TEMP.MINIPM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_MINIPM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
										GROUP BY TEMP.MINIPM_ID
							 ) T
				WHERE  CSMPRT.MINIPM_ID = T.DEVICEID
				AND    CSMPRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSMPRT.MINIPM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSMPRT.MINIPM_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_MINIPM_RT_T CSMPRT
				WHERE  CSMPRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSMPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSMPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSMPRT.MINIPM_ID

        UNION ALL

        SELECT CSMPRT.MINIPM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSMPRT.MINIPM_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_MINIPM_RT_T CSMPRT
				WHERE  CSMPRT.MEM_USED_PER > 70
        AND    CSMPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSMPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSMPRT.MINIPM_ID
		) T1
    WHERE T1.DEVICEID = CRMPT.MINIPM_ID
    AND   CRMPT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPM_DAY_PROC',
     'BEGIN');

/*循环遍历数据，将昨天设备性能的平均值入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备昨天的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_DAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            REPORT_DATE) 
				VALUES(
				    v_deviceId,
            '0',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
            );
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPM_DAY_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_MINIPM_WEEK_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_MINIPM_WEEK_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_MINIPM_WEEK_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memFree VARCHAR(11);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询近一周（不包括今天）设备性能的平均值*/
DECLARE cur CURSOR FOR
    SELECT T1.DEVICEID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER
		FROM   
		(
				SELECT CSMPRT.MINIPM_ID AS DEVICEID,
							 SUM(CSMPRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSMPRT.MINIPM_ID) AS CPUPROCESSORUTILIZATION,
							 SUM(CSMPRT.MEM_USED_PER) / COUNT(CSMPRT.MINIPM_ID) AS MEMUSEDPER,
							 SUM(CSMPRT.MEM_FREE) / COUNT(CSMPRT.MINIPM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_MINIPM_RT_T CSMPRT
				WHERE  CSMPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 7 day),'%Y%m%d000000')
				AND    CSMPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSMPRT.MINIPM_ID

				UNION ALL

				SELECT T.DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS MEMUSEDPER,
							 '0' AS MEMFREE,
							 CSMPRT.DISK_USED_PER AS DISKUSEDPER
				FROM   COMP_STA_MINIPM_RT_T CSMPRT,
							 (
										SELECT TEMP.MINIPM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_MINIPM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 7 day),'%Y%m%d000000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
										GROUP BY TEMP.MINIPM_ID
							 ) T
				WHERE  CSMPRT.MINIPM_ID = T.DEVICEID
				AND    CSMPRT.CREATE_TIME = T.CREATETIME
		) T1
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPM_WEEK_PROC',
     'BEGIN');

/*循环遍历数据，将近一周（不包括今天）设备性能的平均值入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_deviceId, v_cpuProcessorUtilization, v_memUsedPer, v_memFree, v_diskUsedPer;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备近一周的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_WEEK_T(
				    DEVICE_ID, DEVICE_TYPE, CPU_PROCESSOR_UTILIZATION, MEM_USED_PER, MEM_FREE, DISK_USED_PER, REPORT_DATE) 
				VALUES(
				    v_deviceId, '0', v_cpuProcessorUtilization, v_memUsedPer, v_memFree, v_diskUsedPer, DATE_FORMAT(SYSDATE(),'%Y%m%d'));
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_MINIPM_WEEK_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_PM_APP_WEEK_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_PM_APP_WEEK_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_PM_APP_WEEK_PROC`()
BEGIN

/*定义变量*/
DECLARE v_appId VARCHAR(64);
DECLARE v_deviceNum VARCHAR(11);
DECLARE v_cpuOverNum VARCHAR(11);
DECLARE v_cpuOverAve VARCHAR(11);
DECLARE v_cpuNotOverNum VARCHAR(11);
DECLARE v_cpuNotOverAve VARCHAR(11);
DECLARE v_memOverNum VARCHAR(11);
DECLARE v_memOverAve VARCHAR(11);
DECLARE v_memOverFreeAve VARCHAR(11);
DECLARE v_memNotOverNum VARCHAR(11);
DECLARE v_memNotOverAve VARCHAR(11);
DECLARE v_memNotOverFreeAve VARCHAR(11);
DECLARE v_diskRange1Num VARCHAR(11);
DECLARE v_diskRange2Num VARCHAR(11);
DECLARE v_diskRange3Num VARCHAR(11);
DECLARE done INT;

/*定义游标，查询各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数*/
DECLARE cur CURSOR FOR
		SELECT T.APPID,
					 SUM(T.CPUOVERNUM) AS CPUOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUOVERAVE), 2), '0') AS CPUOVERAVE,
					 SUM(T.CPUNOTOVERNUM) AS CPUNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUNOTOVERAVE), 2), '0') AS CPUNOTOVERAVE,
					 SUM(T.MEMOVERNUM) AS MEMOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMOVERAVE), 2), '0') AS MEMOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMOVERFREEAVE), 2), '0') AS MEMOVERFREEAVE,
					 SUM(T.MEMNOTOVERNUM) AS MEMNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERAVE), 2), '0') AS MEMNOTOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERFREEAVE), 2), '0') AS MEMNOTOVERFREEAVE,
					 SUM(T.DISKRANGElNUM) AS DISKRANGElNUM,
					 SUM(T.DISKRANGE2NUM) AS DISKRANGE2NUM,
					 SUM(T.DISKRANGE3NUM) AS DISKRANGE3NUM,
					 SUM(T.DEVICENUM) AS DEVICENUM
		FROM   (

				SELECT CRPT.APP_ID AS APPID,
							 COUNT(CSDWT.DEVICE_ID) AS CPUOVERNUM,
							 SUM(CSDWT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDWT.DEVICE_ID) AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_PM_T CRPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRPT.PM_ID
				AND    CRPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '2'
				AND    CSDWT.CPU_PROCESSOR_UTILIZATION > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRPT.APP_ID

				UNION ALL

				SELECT CRPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 COUNT(CSDWT.DEVICE_ID) AS CPUNOTOVERNUM,
							 SUM(CSDWT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDWT.DEVICE_ID) AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_PM_T CRPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRPT.PM_ID
				AND    CRPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '2'
				AND    CSDWT.CPU_PROCESSOR_UTILIZATION <= 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRPT.APP_ID

				UNION ALL

				SELECT CRPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 COUNT(CSDWT.DEVICE_ID) AS MEMOVERNUM,
							 SUM(CSDWT.MEM_USED_PER) / COUNT(CSDWT.DEVICE_ID) AS MEMOVERAVE,
							 SUM(CSDWT.MEM_FREE) / COUNT(CSDWT.DEVICE_ID) AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_PM_T CRPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRPT.PM_ID
				AND    CRPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '2'
				AND    CSDWT.MEM_USED_PER > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRPT.APP_ID

				UNION ALL
				
				SELECT CRPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERNUM,
							 SUM(CSDWT.MEM_USED_PER) / COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERAVE,
							 SUM(CSDWT.MEM_FREE) / COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_PM_T CRPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRPT.PM_ID
				AND    CRPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '2'
				AND    CSDWT.MEM_USED_PER <= 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRPT.APP_ID

				UNION ALL

				SELECT CRPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_PM_T CRPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRPT.PM_ID
				AND    CRPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '2'
				AND    CSDWT.DISK_USED_PER > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRPT.APP_ID

				UNION ALL

				SELECT CRPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_PM_T CRPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRPT.PM_ID
				AND    CRPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '2'
				AND    CSDWT.DISK_USED_PER <= 70
				AND    CSDWT.DISK_USED_PER > 30
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRPT.APP_ID

				UNION ALL

				SELECT CRPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_PM_T CRPT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRPT.PM_ID
				AND    CRPT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '2'
				AND    CSDWT.DISK_USED_PER <= 30
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRPT.APP_ID

				UNION ALL

				SELECT CRPT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 COUNT(CRPT.PM_ID) AS DEVICENUM
				FROM   COMP_RM_PM_T CRPT,
							 COMP_APP_T CAT
				WHERE  CRPT.APP_ID = CAT.APP_ID
				GROUP BY CRPT.APP_ID

		) T
		GROUP BY T.APPID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_PM_APP_WEEK_PROC',
     'BEGIN');

/*循环遍历数据，将各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_appId, v_cpuOverNum, v_cpuOverAve, v_cpuNotOverNum, v_cpuNotOverAve, v_memOverNum, v_memOverAve, v_memOverFreeAve, v_memNotOverNum, v_memNotOverAve, v_memNotOverFreeAve, v_diskRange1Num, v_diskRange2Num, v_diskRange3Num, v_deviceNum;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数 */
				INSERT INTO COMP_STA_DEVICE_APP_WEEK_T(
				    APP_ID,
            DEVICE_TYPE,
            DEVICE_NUM,
            CPU_OVER_NUM,
            CPU_OVER_AVE,
            CPU_NOT_OVER_NUM,
            CPU_NOT_OVER_AVE,
            MEM_OVER_NUM,
            MEM_OVER_AVE,
            MEM_OVER_FREE_AVE,
            MEM_NOT_OVER_NUM,
            MEM_NOT_OVER_AVE,
            MEM_NOT_OVER_FREE_AVE,
            DISK_RANGEl_NUM,
            DISK_RANGE2_NUM,
            DISK_RANGE3_NUM,
            REPORT_DATE) 
				VALUES(
				    v_appId,
            '2',
            v_deviceNum,
            v_cpuOverNum,
            v_cpuOverAve,
            v_cpuNotOverNum,
            v_cpuNotOverAve,
            v_memOverNum,
            v_memOverAve,
            v_memOverFreeAve,
            v_memNotOverNum,
            v_memNotOverAve,
            v_memNotOverFreeAve,
            v_diskRange1Num,
            v_diskRange2Num,
            v_diskRange3Num,
            DATE_FORMAT(SYSDATE(),'%Y%m%d')
            );
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_PM_APP_WEEK_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_PM_DAY_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_PM_DAY_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_PM_DAY_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_appId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_cpuOverTime VARCHAR(64);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memOverTime VARCHAR(11);
DECLARE v_memFree VARCHAR(11);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询昨天设备性能的平均值*/
DECLARE cur CURSOR FOR
    SELECT T1.DEVICEID,
           CRPT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER
		FROM   COMP_RM_PM_T CRPT,
           COMP_APP_T CAT,
		(
				SELECT CSPRT.PM_ID AS DEVICEID,
							 SUM(CSPRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSPRT.PM_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSPRT.MEM_USED_PER) / COUNT(CSPRT.PM_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSPRT.MEM_FREE) / COUNT(CSPRT.PM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSPRT.PM_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSPRT.DISK_USED_PER AS DISKUSEDPER
				FROM   COMP_STA_PM_RT_T CSPRT,
							 (
										SELECT TEMP.PM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_PM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
										GROUP BY TEMP.PM_ID
							 ) T
				WHERE  CSPRT.PM_ID = T.DEVICEID
				AND    CSPRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSPRT.PM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSPRT.PM_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSPRT.PM_ID

        UNION ALL

        SELECT CSPRT.PM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSPRT.PM_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.MEM_USED_PER > 70
        AND    CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSPRT.PM_ID
		) T1
    WHERE T1.DEVICEID = CRPT.PM_ID
    AND   CRPT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_PM_DAY_PROC',
     'BEGIN');

/*循环遍历数据，将昨天设备性能的平均值入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备昨天的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_DAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            REPORT_DATE) 
				VALUES(
				    v_deviceId,
            '2',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
            );
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_PM_DAY_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_PM_WEEK_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_PM_WEEK_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_PM_WEEK_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memFree VARCHAR(20);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询近一周（不包括今天）设备性能的平均值*/
DECLARE cur CURSOR FOR
    SELECT T1.DEVICEID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER
		FROM   
		(
				SELECT CSPRT.PM_ID AS DEVICEID,
							 SUM(CSPRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSPRT.PM_ID) AS CPUPROCESSORUTILIZATION,
							 SUM(CSPRT.MEM_USED_PER) / COUNT(CSPRT.PM_ID) AS MEMUSEDPER,
							 SUM(CSPRT.MEM_FREE) / COUNT(CSPRT.PM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 7 day),'%Y%m%d000000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSPRT.PM_ID

				UNION ALL

				SELECT T.DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS MEMUSEDPER,
							 '0' AS MEMFREE,
							 CSPRT.DISK_USED_PER AS DISKUSEDPER
				FROM   COMP_STA_PM_RT_T CSPRT,
							 (
										SELECT TEMP.PM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_PM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 7 day),'%Y%m%d000000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
										GROUP BY TEMP.PM_ID
							 ) T
				WHERE  CSPRT.PM_ID = T.DEVICEID
				AND    CSPRT.CREATE_TIME = T.CREATETIME
		) T1
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_PM_WEEK_PROC',
     'BEGIN');

/*循环遍历数据，将近一周（不包括今天）设备性能的平均值入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_deviceId, v_cpuProcessorUtilization, v_memUsedPer, v_memFree, v_diskUsedPer;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备近一周的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_WEEK_T(
				    DEVICE_ID, DEVICE_TYPE, CPU_PROCESSOR_UTILIZATION, MEM_USED_PER, MEM_FREE, DISK_USED_PER, REPORT_DATE) 
				VALUES(
				    v_deviceId, '2', v_cpuProcessorUtilization, v_memUsedPer, v_memFree, v_diskUsedPer, DATE_FORMAT(SYSDATE(),'%Y%m%d'));
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_PM_WEEK_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_VM_APP_WEEK_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_VM_APP_WEEK_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_VM_APP_WEEK_PROC`()
BEGIN

/*定义变量*/
DECLARE v_appId VARCHAR(64);
DECLARE v_deviceNum VARCHAR(11);
DECLARE v_cpuOverNum VARCHAR(11);
DECLARE v_cpuOverAve VARCHAR(11);
DECLARE v_cpuNotOverNum VARCHAR(11);
DECLARE v_cpuNotOverAve VARCHAR(11);
DECLARE v_memOverNum VARCHAR(11);
DECLARE v_memOverAve VARCHAR(11);
DECLARE v_memOverFreeAve VARCHAR(11);
DECLARE v_memNotOverNum VARCHAR(11);
DECLARE v_memNotOverAve VARCHAR(11);
DECLARE v_memNotOverFreeAve VARCHAR(11);
DECLARE v_diskRange1Num VARCHAR(11);
DECLARE v_diskRange2Num VARCHAR(11);
DECLARE v_diskRange3Num VARCHAR(11);
DECLARE done INT;

/*定义游标，查询各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数*/
DECLARE cur CURSOR FOR
		SELECT T.APPID,
					 SUM(T.CPUOVERNUM) AS CPUOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUOVERAVE), 2), '0') AS CPUOVERAVE,
					 SUM(T.CPUNOTOVERNUM) AS CPUNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.CPUNOTOVERAVE), 2), '0') AS CPUNOTOVERAVE,
					 SUM(T.MEMOVERNUM) AS MEMOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMOVERAVE), 2), '0') AS MEMOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMOVERFREEAVE), 2), '0') AS MEMOVERFREEAVE,
					 SUM(T.MEMNOTOVERNUM) AS MEMNOTOVERNUM,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERAVE), 2), '0') AS MEMNOTOVERAVE,
					 IFNULL(ROUND(SUM(T.MEMNOTOVERFREEAVE), 2), '0') AS MEMNOTOVERFREEAVE,
					 SUM(T.DISKRANGElNUM) AS DISKRANGElNUM,
					 SUM(T.DISKRANGE2NUM) AS DISKRANGE2NUM,
					 SUM(T.DISKRANGE3NUM) AS DISKRANGE3NUM,
					 SUM(T.DEVICENUM) AS DEVICENUM
		FROM   (

				SELECT CRVT.APP_ID AS APPID,
							 COUNT(CSDWT.DEVICE_ID) AS CPUOVERNUM,
							 SUM(CSDWT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDWT.DEVICE_ID) AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_VM_T CRVT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRVT.VM_ID
				AND    CRVT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '3'
				AND    CSDWT.CPU_PROCESSOR_UTILIZATION > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRVT.APP_ID

				UNION ALL

				SELECT CRVT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 COUNT(CSDWT.DEVICE_ID) AS CPUNOTOVERNUM,
							 SUM(CSDWT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSDWT.DEVICE_ID) AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_VM_T CRVT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRVT.VM_ID
				AND    CRVT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '3'
				AND    CSDWT.CPU_PROCESSOR_UTILIZATION <= 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRVT.APP_ID

				UNION ALL

				SELECT CRVT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 COUNT(CSDWT.DEVICE_ID) AS MEMOVERNUM,
							 SUM(CSDWT.MEM_USED_PER) / COUNT(CSDWT.DEVICE_ID) AS MEMOVERAVE,
							 SUM(CSDWT.MEM_FREE) / COUNT(CSDWT.DEVICE_ID) AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_VM_T CRVT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRVT.VM_ID
				AND    CRVT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '3'
				AND    CSDWT.MEM_USED_PER > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRVT.APP_ID

				UNION ALL
				
				SELECT CRVT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERNUM,
							 SUM(CSDWT.MEM_USED_PER) / COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERAVE,
							 SUM(CSDWT.MEM_FREE) / COUNT(CSDWT.DEVICE_ID) AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_VM_T CRVT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRVT.VM_ID
				AND    CRVT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '3'
				AND    CSDWT.MEM_USED_PER <= 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRVT.APP_ID

				UNION ALL

				SELECT CRVT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_VM_T CRVT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRVT.VM_ID
				AND    CRVT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '3'
				AND    CSDWT.DISK_USED_PER > 70
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRVT.APP_ID

				UNION ALL

				SELECT CRVT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_VM_T CRVT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRVT.VM_ID
				AND    CRVT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '3'
				AND    CSDWT.DISK_USED_PER <= 70
				AND    CSDWT.DISK_USED_PER > 30
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRVT.APP_ID

				UNION ALL

				SELECT CRVT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 COUNT(CSDWT.DEVICE_ID) AS DISKRANGE3NUM,
							 '0' AS DEVICENUM
				FROM   COMP_STA_DEVICE_WEEK_T CSDWT,
							 COMP_RM_VM_T CRVT,
							 COMP_APP_T CAT
				WHERE  CSDWT.DEVICE_ID = CRVT.VM_ID
				AND    CRVT.APP_ID = CAT.APP_ID
				AND    CSDWT.DEVICE_TYPE = '3'
				AND    CSDWT.DISK_USED_PER <= 30
				AND    CSDWT.REPORT_DATE = DATE_FORMAT(SYSDATE(), '%Y%m%d')
				GROUP BY CRVT.APP_ID

				UNION ALL

				SELECT CRVT.APP_ID AS APPID,
							 '0' AS CPUOVERNUM,
							 '0' AS CPUOVERAVE,
							 '0' AS CPUNOTOVERNUM,
							 '0' AS CPUNOTOVERAVE,
							 '0' AS MEMOVERNUM,
							 '0' AS MEMOVERAVE,
							 '0' AS MEMOVERFREEAVE,
							 '0' AS MEMNOTOVERNUM,
							 '0' AS MEMNOTOVERAVE,
							 '0' AS MEMNOTOVERFREEAVE,
							 '0' AS DISKRANGElNUM,
							 '0' AS DISKRANGE2NUM,
							 '0' AS DISKRANGE3NUM,
							 COUNT(CRVT.VM_ID) AS DEVICENUM
				FROM   COMP_RM_VM_T CRVT,
							 COMP_APP_T CAT
				WHERE  CRVT.APP_ID = CAT.APP_ID
				GROUP BY CRVT.APP_ID

		) T
		GROUP BY T.APPID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_VM_APP_WEEK_PROC',
     'BEGIN');

/*循环遍历数据，将各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_appId, v_cpuOverNum, v_cpuOverAve, v_cpuNotOverNum, v_cpuNotOverAve, v_memOverNum, v_memOverAve, v_memOverFreeAve, v_memNotOverNum, v_memNotOverAve, v_memNotOverFreeAve, v_diskRange1Num, v_diskRange2Num, v_diskRange3Num, v_deviceNum;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入各个业务的CPU、内存使用率超标、未超标的设备数和性能指标，磁盘使用率3个区间的设备数 */
				INSERT INTO COMP_STA_DEVICE_APP_WEEK_T(
				    APP_ID,
            DEVICE_TYPE,
            DEVICE_NUM,
            CPU_OVER_NUM,
            CPU_OVER_AVE,
            CPU_NOT_OVER_NUM,
            CPU_NOT_OVER_AVE,
            MEM_OVER_NUM,
            MEM_OVER_AVE,
            MEM_OVER_FREE_AVE,
            MEM_NOT_OVER_NUM,
            MEM_NOT_OVER_AVE,
            MEM_NOT_OVER_FREE_AVE,
            DISK_RANGEl_NUM,
            DISK_RANGE2_NUM,
            DISK_RANGE3_NUM,
            REPORT_DATE) 
				VALUES(
				    v_appId,
            '3',
            v_deviceNum,
            v_cpuOverNum,
            v_cpuOverAve,
            v_cpuNotOverNum,
            v_cpuNotOverAve,
            v_memOverNum,
            v_memOverAve,
            v_memOverFreeAve,
            v_memNotOverNum,
            v_memNotOverAve,
            v_memNotOverFreeAve,
            v_diskRange1Num,
            v_diskRange2Num,
            v_diskRange3Num,
            DATE_FORMAT(SYSDATE(),'%Y%m%d')
            );
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_VM_APP_WEEK_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_VM_DAY_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_VM_DAY_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_VM_DAY_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_appId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_cpuOverTime VARCHAR(64);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memOverTime VARCHAR(11);
DECLARE v_memFree VARCHAR(11);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询昨天设备性能的平均值*/
DECLARE cur CURSOR FOR
    SELECT T1.DEVICEID,
           CRVT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER
		FROM   COMP_RM_VM_T CRVT,
           COMP_APP_T CAT,
		(
				SELECT CSVRT.VM_ID AS DEVICEID,
							 SUM(CSVRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSVRT.VM_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSVRT.MEM_USED_PER) / COUNT(CSVRT.VM_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSVRT.MEM_FREE) / COUNT(CSVRT.VM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSVRT.VM_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSVRT.DISK_USED_PER AS DISKUSEDPER
				FROM   COMP_STA_VM_RT_T CSVRT,
							 (
										SELECT TEMP.VM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_VM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
										GROUP BY TEMP.VM_ID
							 ) T
				WHERE  CSVRT.VM_ID = T.DEVICEID
				AND    CSVRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSVRT.VM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSVRT.VM_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSVRT.VM_ID

        UNION ALL

        SELECT CSVRT.VM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSVRT.VM_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.MEM_USED_PER > 70
        AND    CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d000000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSVRT.VM_ID
		) T1
    WHERE T1.DEVICEID = CRVT.VM_ID
    AND   CRVT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_VM_DAY_PROC',
     'BEGIN');

/*循环遍历数据，将昨天设备性能的平均值入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备昨天的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_DAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            REPORT_DATE) 
				VALUES(
				    v_deviceId,
            '3',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d')
            );
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_VM_DAY_PROC',
     'END');

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for COMP_STA_VM_WEEK_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_VM_WEEK_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_VM_WEEK_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memFree VARCHAR(11);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询近一周（近一周不包括今天）设备性能的平均值*/
DECLARE cur CURSOR FOR
    SELECT T1.DEVICEID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER
		FROM   
		(
				SELECT CSVRT.VM_ID AS DEVICEID,
							 SUM(CSVRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSVRT.VM_ID) AS CPUPROCESSORUTILIZATION,
							 SUM(CSVRT.MEM_USED_PER) / COUNT(CSVRT.VM_ID) AS MEMUSEDPER,
							 SUM(CSVRT.MEM_FREE) / COUNT(CSVRT.VM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 7 day),'%Y%m%d000000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
				GROUP BY CSVRT.VM_ID

				UNION ALL

				SELECT T.DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS MEMUSEDPER,
							 '0' AS MEMFREE,
							 CSVRT.DISK_USED_PER AS DISKUSEDPER
				FROM   COMP_STA_VM_RT_T CSVRT,
							 (
										SELECT TEMP.VM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_VM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 7 day),'%Y%m%d000000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d000000')
										GROUP BY TEMP.VM_ID
							 ) T
				WHERE  CSVRT.VM_ID = T.DEVICEID
				AND    CSVRT.CREATE_TIME = T.CREATETIME
		) T1
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_VM_WEEK_PROC',
     'BEGIN');

/*循环遍历数据，将近一周（近一周不包括今天）设备性能的平均值入库*/
OPEN cur;
		insertLoop:LOOP
				FETCH cur INTO v_deviceId, v_cpuProcessorUtilization, v_memUsedPer, v_memFree, v_diskUsedPer;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备近一周的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_WEEK_T(
				    DEVICE_ID, DEVICE_TYPE, CPU_PROCESSOR_UTILIZATION, MEM_USED_PER, MEM_FREE, DISK_USED_PER, REPORT_DATE) 
				VALUES(
				    v_deviceId, '3', v_cpuProcessorUtilization, v_memUsedPer, v_memFree, v_diskUsedPer, DATE_FORMAT(SYSDATE(),'%Y%m%d'));
		END LOOP;
CLOSE cur;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_VM_WEEK_PROC',
     'END');

END
;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for COMP_STA_PM_HALFDAY_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_PM_HALFDAY_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_PM_HALFDAY_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_appId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_cpuOverTime VARCHAR(64);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memOverTime VARCHAR(11);
DECLARE v_memFree VARCHAR(20);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE v_swapmemUsedPer VARCHAR(11);
DECLARE v_diskIOSpeed VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询昨天全天设备性能的平均值*/
DECLARE curAlltime CURSOR FOR
    SELECT T1.DEVICEID,
           CRPT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER,
					 IFNULL(ROUND(SUM(T1.SWAPMEMUSEDPER), 2), '0') AS SWAPMEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.DISKIOSPEED), 2), '0') AS DISKIOSPEED
		FROM   
		(
				SELECT CSPRT.PM_ID AS DEVICEID,
							 SUM(CSPRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSPRT.PM_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSPRT.MEM_USED_PER) / COUNT(CSPRT.PM_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSPRT.MEM_FREE) / COUNT(CSPRT.PM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 SUM(CSPRT.SWAP_MEM_USED_PER) / COUNT(CSPRT.PM_ID) AS SWAPMEMUSEDPER,
							 SUM(CSPRT.DISK_IO_SPEED) / COUNT(CSPRT.PM_ID) AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSPRT.PM_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSPRT.DISK_USED_PER AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT,
							 (
										SELECT TEMP.PM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_PM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
										GROUP BY TEMP.PM_ID
							 ) T
				WHERE  CSPRT.PM_ID = T.DEVICEID
				AND    CSPRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSPRT.PM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSPRT.PM_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSPRT.PM_ID

        UNION ALL

        SELECT CSPRT.PM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSPRT.PM_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.MEM_USED_PER > 70
        AND    CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSPRT.PM_ID
		) T1 LEFT JOIN COMP_RM_PM_T CRPT  ON T1.DEVICEID = CRPT.PM_ID  
         LEFT JOIN COMP_APP_T CAT ON CRPT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;

/*定义游标，查询昨天白天设备性能的平均值*/
DECLARE curDaytime CURSOR FOR
    SELECT T1.DEVICEID,
           CRPT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER,
					 IFNULL(ROUND(SUM(T1.SWAPMEMUSEDPER), 2), '0') AS SWAPMEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.DISKIOSPEED), 2), '0') AS DISKIOSPEED
		FROM   
		(
				SELECT CSPRT.PM_ID AS DEVICEID,
							 SUM(CSPRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSPRT.PM_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSPRT.MEM_USED_PER) / COUNT(CSPRT.PM_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSPRT.MEM_FREE) / COUNT(CSPRT.PM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 SUM(CSPRT.SWAP_MEM_USED_PER) / COUNT(CSPRT.PM_ID) AS SWAPMEMUSEDPER,
							 SUM(CSPRT.DISK_IO_SPEED) / COUNT(CSPRT.PM_ID) AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				GROUP BY CSPRT.PM_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSPRT.DISK_USED_PER AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT,
							 (
										SELECT TEMP.PM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_PM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
										GROUP BY TEMP.PM_ID
							 ) T
				WHERE  CSPRT.PM_ID = T.DEVICEID
				AND    CSPRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSPRT.PM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSPRT.PM_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				GROUP BY CSPRT.PM_ID

        UNION ALL

        SELECT CSPRT.PM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSPRT.PM_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.MEM_USED_PER > 70
        AND    CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				GROUP BY CSPRT.PM_ID
		) T1 LEFT JOIN COMP_RM_PM_T CRPT  ON T1.DEVICEID = CRPT.PM_ID  
         LEFT JOIN COMP_APP_T CAT ON CRPT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;
		
		
/*定义游标，查询昨天夜晚设备性能的平均值*/
DECLARE curNighttime CURSOR FOR
    SELECT T1.DEVICEID,
           CRPT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER,
					 IFNULL(ROUND(SUM(T1.SWAPMEMUSEDPER), 2), '0') AS SWAPMEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.DISKIOSPEED), 2), '0') AS DISKIOSPEED
		FROM   
		(
				SELECT CSPRT.PM_ID AS DEVICEID,
							 SUM(CSPRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSPRT.PM_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSPRT.MEM_USED_PER) / COUNT(CSPRT.PM_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSPRT.MEM_FREE) / COUNT(CSPRT.PM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 SUM(CSPRT.SWAP_MEM_USED_PER) / COUNT(CSPRT.PM_ID) AS SWAPMEMUSEDPER,
							 SUM(CSPRT.DISK_IO_SPEED) / COUNT(CSPRT.PM_ID) AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSPRT.PM_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSPRT.DISK_USED_PER AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT,
							 (
										SELECT TEMP.PM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_PM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
										GROUP BY TEMP.PM_ID
							 ) T
				WHERE  CSPRT.PM_ID = T.DEVICEID
				AND    CSPRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSPRT.PM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSPRT.PM_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSPRT.PM_ID

        UNION ALL

        SELECT CSPRT.PM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSPRT.PM_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_PM_RT_T CSPRT
				WHERE  CSPRT.MEM_USED_PER > 70
        AND    CSPRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				AND    CSPRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSPRT.PM_ID
		) T1 LEFT JOIN COMP_RM_PM_T CRPT  ON T1.DEVICEID = CRPT.PM_ID  
         LEFT JOIN COMP_APP_T CAT ON CRPT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_PM_HALFDAY_PROC',
     'BEGIN');
     
/*循环遍历数据，将昨天全天设备性能的平均值入库*/
OPEN curAlltime;
		insertLoop:LOOP
				FETCH curAlltime INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer, v_swapmemUsedPer, v_diskIOSpeed;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备昨天全天的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_HALFDAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            SWAP_MEM_USED_PER,
            DISK_IO_SPEED,
            REPORT_DATE,
            DATETYPE) 
				VALUES(
				    v_deviceId,
            '2',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            v_swapmemUsedPer,
            v_diskIOSpeed,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d'),
            '0'
            );
		END LOOP;
CLOSE curAlltime;
set done = 0;

/*循环遍历数据，将昨天白天设备性能的平均值入库*/
OPEN curDaytime;
		insertLoop:LOOP
				FETCH curDaytime INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer, v_swapmemUsedPer, v_diskIOSpeed;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备昨天白天的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_HALFDAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            SWAP_MEM_USED_PER,
            DISK_IO_SPEED,
            REPORT_DATE,
            DATETYPE) 
				VALUES(
				    v_deviceId,
            '2',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            v_swapmemUsedPer,
            v_diskIOSpeed,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d'),
            '1'
            );
		END LOOP;
CLOSE curDaytime;
set done = 0;


/*循环遍历数据，将昨天夜晚设备性能的平均值入库*/
OPEN curNighttime;
		insertLoop:LOOP
				FETCH curNighttime INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer, v_swapmemUsedPer, v_diskIOSpeed;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备夜晚的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_HALFDAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            SWAP_MEM_USED_PER,
            DISK_IO_SPEED,
            REPORT_DATE,
            DATETYPE) 
				VALUES(
				    v_deviceId,
            '2',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            v_swapmemUsedPer,
            v_diskIOSpeed,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d'),
            '2'
            );
		END LOOP;
CLOSE curNighttime;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_PM_HALFDAY_PROC',
     'END');

END
;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for COMP_STA_VM_HALFDAY_PROC
-- ----------------------------
DROP PROCEDURE IF EXISTS `COMP_STA_VM_HALFDAY_PROC`;
DELIMITER ;;
CREATE PROCEDURE `COMP_STA_VM_HALFDAY_PROC`()
BEGIN

/*定义变量*/
DECLARE v_deviceId VARCHAR(64);
DECLARE v_appId VARCHAR(64);
DECLARE v_cpuProcessorUtilization VARCHAR(11);
DECLARE v_cpuOverTime VARCHAR(64);
DECLARE v_memUsedPer VARCHAR(11);
DECLARE v_memOverTime VARCHAR(11);
DECLARE v_memFree VARCHAR(11);
DECLARE v_diskUsedPer VARCHAR(11);
DECLARE v_swapmemUsedPer VARCHAR(11);
DECLARE v_diskIOSpeed VARCHAR(11);
DECLARE done INT;
DECLARE v_rtPerformanceDel INT;

/*定义游标，查询昨天全天设备性能的平均值*/
DECLARE curAlltime CURSOR FOR
    SELECT T1.DEVICEID,
           CRVT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER,
					 IFNULL(ROUND(SUM(T1.SWAPMEMUSEDPER), 2), '0') AS SWAPMEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.DISKIOSPEED), 2), '0') AS DISKIOSPEED
		FROM   COMP_RM_VM_T CRVT,
           COMP_APP_T CAT,
		(
				SELECT CSVRT.VM_ID AS DEVICEID,
							 SUM(CSVRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSVRT.VM_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSVRT.MEM_USED_PER) / COUNT(CSVRT.VM_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSVRT.MEM_FREE) / COUNT(CSVRT.VM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 SUM(CSVRT.SWAP_MEM_USED_PER) / COUNT(CSVRT.VM_ID) AS SWAPMEMUSEDPER,
							 SUM(CSVRT.DISK_IO_SPEED) / COUNT(CSVRT.VM_ID) AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSVRT.VM_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSVRT.DISK_USED_PER AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT,
							 (
										SELECT TEMP.VM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_VM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
										GROUP BY TEMP.VM_ID
							 ) T
				WHERE  CSVRT.VM_ID = T.DEVICEID
				AND    CSVRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSVRT.VM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSVRT.VM_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSVRT.VM_ID

        UNION ALL

        SELECT CSVRT.VM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSVRT.VM_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.MEM_USED_PER > 70
        AND    CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSVRT.VM_ID
		) T1
    WHERE T1.DEVICEID = CRVT.VM_ID
    AND   CRVT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;

/*定义游标，查询昨天白天（06:00-17:00）设备性能的平均值*/
DECLARE curDaytime CURSOR FOR
    SELECT T1.DEVICEID,
           CRVT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER,
					 IFNULL(ROUND(SUM(T1.SWAPMEMUSEDPER), 2), '0') AS SWAPMEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.DISKIOSPEED), 2), '0') AS DISKIOSPEED
		FROM   COMP_RM_VM_T CRVT,
           COMP_APP_T CAT,
		(
				SELECT CSVRT.VM_ID AS DEVICEID,
							 SUM(CSVRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSVRT.VM_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSVRT.MEM_USED_PER) / COUNT(CSVRT.VM_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSVRT.MEM_FREE) / COUNT(CSVRT.VM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 SUM(CSVRT.SWAP_MEM_USED_PER) / COUNT(CSVRT.VM_ID) AS SWAPMEMUSEDPER,
							 SUM(CSVRT.DISK_IO_SPEED) / COUNT(CSVRT.VM_ID) AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				GROUP BY CSVRT.VM_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSVRT.DISK_USED_PER AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT,
							 (
										SELECT TEMP.VM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_VM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
										GROUP BY TEMP.VM_ID
							 ) T
				WHERE  CSVRT.VM_ID = T.DEVICEID
				AND    CSVRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSVRT.VM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSVRT.VM_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				GROUP BY CSVRT.VM_ID

        UNION ALL

        SELECT CSVRT.VM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSVRT.VM_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.MEM_USED_PER > 70
        AND    CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d060000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				GROUP BY CSVRT.VM_ID
		) T1
    WHERE T1.DEVICEID = CRVT.VM_ID
    AND   CRVT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;
		
/*定义游标，查询昨天夜间（18:00-05:00）设备性能的平均值*/
DECLARE curNighttime CURSOR FOR
    SELECT T1.DEVICEID,
           CRVT.APP_ID AS APPID,
					 IFNULL(ROUND(SUM(T1.CPUPROCESSORUTILIZATION), 2), '0') AS CPUPROCESSORUTILIZATION,
					 SUM(T1.CPUOVERTIME) AS CPUOVERTIME,
           IFNULL(ROUND(SUM(T1.MEMUSEDPER), 2), '0') AS MEMUSEDPER,
           SUM(T1.MEMOVERTIME) AS MEMOVERTIME,
					 IFNULL(ROUND(SUM(T1.MEMFREE), 2), '0') AS MEMFREE,
					 IFNULL(ROUND(SUM(T1.DISKUSEDPER), 2), '0') AS DISKUSEDPER,
					 IFNULL(ROUND(SUM(T1.SWAPMEMUSEDPER), 2), '0') AS SWAPMEMUSEDPER,
					 IFNULL(ROUND(SUM(T1.DISKIOSPEED), 2), '0') AS DISKIOSPEED
		FROM   COMP_RM_VM_T CRVT,
           COMP_APP_T CAT,
		(
				SELECT CSVRT.VM_ID AS DEVICEID,
							 SUM(CSVRT.CPU_PROCESSOR_UTILIZATION) / COUNT(CSVRT.VM_ID) AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               SUM(CSVRT.MEM_USED_PER) / COUNT(CSVRT.VM_ID) AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 SUM(CSVRT.MEM_FREE) / COUNT(CSVRT.VM_ID) AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 SUM(CSVRT.SWAP_MEM_USED_PER) / COUNT(CSVRT.VM_ID) AS SWAPMEMUSEDPER,
							 SUM(CSVRT.DISK_IO_SPEED) / COUNT(CSVRT.VM_ID) AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSVRT.VM_ID

				UNION ALL

				SELECT T.DEVICEID,
               '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 CSVRT.DISK_USED_PER AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT,
							 (
										SELECT TEMP.VM_ID AS DEVICEID,
													 MAX(TEMP.CREATE_TIME) AS CREATETIME
										FROM   COMP_STA_VM_RT_T TEMP
										WHERE  TEMP.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
										AND    TEMP.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
										GROUP BY TEMP.VM_ID
							 ) T
				WHERE  CSVRT.VM_ID = T.DEVICEID
				AND    CSVRT.CREATE_TIME = T.CREATETIME
 
				UNION ALL

        SELECT CSVRT.VM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 COUNT(CSVRT.VM_ID) * 15 AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               '0' AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.CPU_PROCESSOR_UTILIZATION > 70
        AND    CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSVRT.VM_ID

        UNION ALL

        SELECT CSVRT.VM_ID AS DEVICEID,
							 '0' AS CPUPROCESSORUTILIZATION,
							 '0' AS CPUOVERTIME,
               '0' AS MEMUSEDPER,
               COUNT(CSVRT.VM_ID) * 15 AS MEMOVERTIME,
							 '0' AS MEMFREE,
							 '0' AS DISKUSEDPER,
							 '0' AS SWAPMEMUSEDPER,
							 '0' AS DISKIOSPEED
				FROM   COMP_STA_VM_RT_T CSVRT
				WHERE  CSVRT.MEM_USED_PER > 70
        AND    CSVRT.CREATE_TIME >= DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d180000')
				AND    CSVRT.CREATE_TIME < DATE_FORMAT(SYSDATE(),'%Y%m%d060000')
				GROUP BY CSVRT.VM_ID
		) T1
    WHERE T1.DEVICEID = CRVT.VM_ID
    AND   CRVT.APP_ID = CAT.APP_ID
		GROUP BY T1.DEVICEID;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

/*记录执行情况 开始*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_VM_HALFDAY_PROC',
     'BEGIN');
     
/*循环遍历数据，将昨天白天设备性能的平均值入库*/
OPEN curAlltime;
		insertLoop:LOOP
				FETCH curAlltime INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer, v_swapmemUsedPer, v_diskIOSpeed;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备昨天全天的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_HALFDAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            SWAP_MEM_USED_PER,
            DISK_IO_SPEED,
            REPORT_DATE,
            DATETYPE) 
				VALUES(
				    v_deviceId,
            '3',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            v_swapmemUsedPer,
            v_diskIOSpeed,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d'),
            '0'
            );
		END LOOP;
CLOSE curAlltime;
set done = 0;

/*循环遍历数据，将昨天白天设备性能的平均值入库*/
OPEN curDaytime;
		insertLoop:LOOP
				FETCH curDaytime INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer, v_swapmemUsedPer, v_diskIOSpeed;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备昨天白天的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_HALFDAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            SWAP_MEM_USED_PER,
            DISK_IO_SPEED,
            REPORT_DATE,
            DATETYPE) 
				VALUES(
				    v_deviceId,
            '3',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            v_swapmemUsedPer,
            v_diskIOSpeed,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d'),
            '1'
            );
		END LOOP;
CLOSE curDaytime;
set done = 0;

/*循环遍历数据，将昨天夜晚设备性能的平均值入库*/
OPEN curNighttime;
		insertLoop:LOOP
				FETCH curNighttime INTO v_deviceId, v_appId, v_cpuProcessorUtilization, v_cpuOverTime, v_memUsedPer, v_memOverTime, v_memFree, v_diskUsedPer, v_swapmemUsedPer, v_diskIOSpeed;

        IF done = 1 THEN
						LEAVE insertLoop;
				END IF;

				/* 插入设备昨天白天的CPU、内存、磁盘使用率情况的平均值 */
				INSERT INTO COMP_STA_DEVICE_HALFDAY_T(
				    DEVICE_ID,
            DEVICE_TYPE,
            APP_ID,
            CPU_PROCESSOR_UTILIZATION,
            CPU_OVER_TIME,
            MEM_USED_PER,
            MEM_OVER_TIME,
            MEM_FREE,
            DISK_USED_PER,
            SWAP_MEM_USED_PER,
            DISK_IO_SPEED,
            REPORT_DATE,
            DATETYPE) 
				VALUES(
				    v_deviceId,
            '3',
            v_appId,
            v_cpuProcessorUtilization,
            v_cpuOverTime,
            v_memUsedPer,
            v_memOverTime,
            v_memFree,
            v_diskUsedPer,
            v_swapmemUsedPer,
            v_diskIOSpeed,
            DATE_FORMAT(DATE_SUB(SYSDATE(), interval 1 day),'%Y%m%d'),
            '2'
            );
		END LOOP;
CLOSE curNighttime;

/*记录执行情况 结束*/
INSERT INTO COMP_PROC_EXEC_STAT_T
  VALUES
    (DATE_FORMAT(SYSDATE(), '%Y%m%d%H%i%s'),
     'COMP_STA_VM_HALFDAY_PROC',
     'END');

END
;;
DELIMITER ;
