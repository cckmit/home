/*创建数据库*/
create database COMP_TS;

/*执行脚本*/
source comp_create_table.sql;
source comp_init_date.sql;
source comp_sequence.sql;
source comp_create_view.sql;
source comp_create_procedure.sql;
source comp_create_event.sql;