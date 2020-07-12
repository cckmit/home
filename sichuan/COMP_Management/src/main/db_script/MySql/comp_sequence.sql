-- 设置可以创建function
set global log_bin_trust_function_creators=1;
-- 序列使用表
DROP TABLE IF EXISTS `comp_sys_sequence`;
CREATE TABLE comp_sys_sequence (
   `NAME` varchar(50) NOT NULL,
   `CURRENT_VALUE` int(11) NOT NULL DEFAULT '0',
   `INCREMENT` int(11) NOT NULL DEFAULT '1',
   PRIMARY KEY (`NAME`)
 );
-- 插入生成序列使用数据
INSERT INTO comp_sys_sequence (NAME)VALUES('name');

-- 创建函数
DROP FUNCTION IF EXISTS `currval`; 
CREATE FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS INT(11)
BEGIN
    DECLARE VALUE INTEGER;
    SET VALUE=0;
    SELECT current_value INTO VALUE
    FROM comp_sys_sequence
    WHERE NAME=seq_name;
    RETURN VALUE;
    END;
DROP FUNCTION IF EXISTS `nextval`;
CREATE FUNCTION `nextval`() RETURNS int(11)
 BEGIN
     UPDATE comp_sys_sequence
     SET CURRENT_VALUE = CURRENT_VALUE + INCREMENT
     where  name='name';
     return currval('name');
     END;

     
-- 注册用户绑定业务时,创建新业务.生成业务编码时使用
-- add by zhangyunfeng 2016/9/18
INSERT INTO `comp_sys_sequence` VALUES ('app_name', '100', '1');

-- ----------------------------
-- Function structure for `nextvalapp`
-- ----------------------------
DROP FUNCTION IF EXISTS `nextvalapp`;
DELIMITER ;;
CREATE FUNCTION `nextvalapp`(seq_name varchar(50)) RETURNS varchar(10) CHARSET utf8
    DETERMINISTIC
BEGIN
	DECLARE result varchar(10);

	UPDATE comp_sys_sequence  
	SET current_value = current_value+increment  
	WHERE name = seq_name; 

	select currval(seq_name) INTO result; 
RETURN result; 
END
;;
DELIMITER ;
