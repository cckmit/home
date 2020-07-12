/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.neusoft.mid.cloong.web.page.console.performance;

import java.io.Serializable;
import java.util.Date;

/**
 * CPU，磁盘，内存，负载，流量指标Domain
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class PMResourceInfoNew implements Serializable {
    private static final long serialVersionUID = -5184178155136975387L;
    /**
     * 主机或虚拟IP
     */
    private String per_hid;
    /**
     * 启动的空闲CPU百分比
     */
    private String cpu_aidle;
    /**
     * 空闲CPU百分比
     */
    private String cpu_idle;
    /**
     * 用户进程空间内改变过优先级的进程占用CPU百分比
     */
    private String cpu_nice;
    /**
     * CPU线程总数
     */
    private String cpu_num;
    /**
     * CPU速度（MHz）
     */
    private String cpu_speed;
    /**
     * 内核空间占用CPU百分比
     */
    private String cpu_system;
    /**
     * 用户空间占用CPU百分比
     */
    private String cpu_user;
    /**
     * cpu空闲时的最大I/O请求
     */
    private String cpu_wio;
    /**
     * 每15分钟的系统平均负载
     */
    private String load_fifteen;
    /**
     * 每5分钟的系统平均负载
     */
    private String load_five;
    /**
     * 每分钟的系统平均负载
     */
    private String load_one;
    /**
     * 内核缓存的内存总量
     */
    private String mem_buffers;
    /**
     * 缓存内存大小
     */
    private String mem_cached;
    /**
     * 空闲内存大小
     */
    private String mem_free;
    /**
     * 共享内存大小
     */
    private String mem_shared;
    /**
     * 物理内存总量
     */
    private String mem_total;
    /**
     * 空闲交换分区大小
     */
    private String swap_free;
    /**
     * 交换分区总量
     */
    private String swap_total;
    /**
     * 每秒进来字节数
     */
    private String bytes_in;
    /**
     * 每秒出去字节数
     */
    private String bytes_out;
    /**
     * 每秒进来的包
     */
    private String pkts_in;
    /**
     * 每秒出去的包
     */
    private String pkts_out;
    /**
     * 运行的进程总数
     */
    private String proc_run;
    /**
     * 进程总数
     */
    private String proc_total;
    private String part_max_used;
    /**
     * 剩余磁盘空间
     */
    private String disk_free;
    /**
     * 磁盘总大小
     */
    private String disk_total;
    /**
     * 磁盘的每秒读字节数
     */
    private String disk_read;
    /**
     * 磁盘的每秒写字节数
     */
    private String disk_write;
    /**
     * 磁盘IO利用率
     */
    private String disk_io;
    /**
     * 系统磁盘读取速率
     */
    private String disk_read_sda;
    /**
     * 系统磁盘写入速率
     */
    private String disk_write_sda;
    /**
     * 磁盘IO利用率
     */
    private String disk_io_sda;
    /**
     * 内存使用率
     */
    private String mem_percent;
    /**
     * 交换分区内存使用率
     */
    private String swap_percent;
    /**
     * 磁盘使用率
     */
    private String disk_percent;    
    private String param1;          
    private String param2;          
    private String param3;          
    private String param4;          
    private String param5;          
    private String param6;          
    private String param7;          
    private String param8; 
    
    private String param9;  
    //net_send
    private String param10; 
    //net_band
    private String param11;
    //net_receive
    private String param12; 
    //net_device
    private String param13;         
    private String param14;         
    private String param15;         
    private String param16;         
    private String param17;         
    private String param18;         
    private String param19;         
    private String param20;         
    private String param21;         
    private String param22;         
    private String param23;         
    private String param24;         
    private String param25;         
    private String param26;         
    private String param27;         
    private String param28;         
    private String param29;         
    private String param30;         
    private String param31;         
    private String param32;         
    private String param33;         
    private String param34;         
    private String param35;         
    private String param36;         
    private String param37;         
    private String param38;         
    private String param39;         
    private String param40;         
    private String param41;         
    private String param42;         
    private String param43;         
    private String param44;         
    private String param45;         
    private String param46;         
    private String param47;         
    private String param48;         
    private String param49;         
    private String param50;         
    private String boottime;
    private String machine_type;
    private String gexec;
    private String os_name;
    private String os_release;
    private String flag;
    private Date per_time;
    
    private String mibOidList;
    /**
     * 角色模板ID Array
     */
    private String[] perHidArray = new String[0];
    public String getPer_hid() {
        return per_hid;
    }
    public void setPer_hid(String per_hid) {
        this.per_hid = per_hid;
    }
    public String getCpu_aidle() {
        return cpu_aidle;
    }
    public String[] getPerHidArray() {
        return perHidArray;
    }
    public void setPerHidArray(String[] perHidArray) {
        this.perHidArray = perHidArray;
    }
    public void setCpu_aidle(String cpu_aidle) {
        this.cpu_aidle = cpu_aidle;
    }
    public String getCpu_idle() {
        return cpu_idle;
    }
    public void setCpu_idle(String cpu_idle) {
        this.cpu_idle = cpu_idle;
    }
    public String getCpu_nice() {
        return cpu_nice;
    }
    public void setCpu_nice(String cpu_nice) {
        this.cpu_nice = cpu_nice;
    }
    public String getCpu_num() {
        return cpu_num;
    }
    public void setCpu_num(String cpu_num) {
        this.cpu_num = cpu_num;
    }
    public String getCpu_speed() {
        return cpu_speed;
    }
    public void setCpu_speed(String cpu_speed) {
        this.cpu_speed = cpu_speed;
    }
    public String getCpu_system() {
        return cpu_system;
    }
    public void setCpu_system(String cpu_system) {
        this.cpu_system = cpu_system;
    }
    public String getCpu_user() {
        return cpu_user;
    }
    public void setCpu_user(String cpu_user) {
        this.cpu_user = cpu_user;
    }
    public String getCpu_wio() {
        return cpu_wio;
    }
    public void setCpu_wio(String cpu_wio) {
        this.cpu_wio = cpu_wio;
    }
    public String getLoad_fifteen() {
        return load_fifteen;
    }
    public void setLoad_fifteen(String load_fifteen) {
        this.load_fifteen = load_fifteen;
    }
    public String getLoad_five() {
        return load_five;
    }
    public void setLoad_five(String load_five) {
        this.load_five = load_five;
    }
    public String getLoad_one() {
        return load_one;
    }
    public void setLoad_one(String load_one) {
        this.load_one = load_one;
    }
    public String getMem_buffers() {
        return mem_buffers;
    }
    public void setMem_buffers(String mem_buffers) {
        this.mem_buffers = mem_buffers;
    }
    public String getMem_cached() {
        return mem_cached;
    }
    public void setMem_cached(String mem_cached) {
        this.mem_cached = mem_cached;
    }
    public String getMem_free() {
        return mem_free;
    }
    public void setMem_free(String mem_free) {
        this.mem_free = mem_free;
    }
    public String getMem_shared() {
        return mem_shared;
    }
    public void setMem_shared(String mem_shared) {
        this.mem_shared = mem_shared;
    }
    public String getMem_total() {
        return mem_total;
    }
    public void setMem_total(String mem_total) {
        this.mem_total = mem_total;
    }
    public String getSwap_free() {
        return swap_free;
    }
    public void setSwap_free(String swap_free) {
        this.swap_free = swap_free;
    }
    public String getSwap_total() {
        return swap_total;
    }
    public void setSwap_total(String swap_total) {
        this.swap_total = swap_total;
    }
    public String getBytes_in() {
        return bytes_in;
    }
    public void setBytes_in(String bytes_in) {
        this.bytes_in = bytes_in;
    }
    public String getBytes_out() {
        return bytes_out;
    }
    public void setBytes_out(String bytes_out) {
        this.bytes_out = bytes_out;
    }
    public String getPkts_in() {
        return pkts_in;
    }
    public void setPkts_in(String pkts_in) {
        this.pkts_in = pkts_in;
    }
    public String getPkts_out() {
        return pkts_out;
    }
    public void setPkts_out(String pkts_out) {
        this.pkts_out = pkts_out;
    }
    public String getProc_run() {
        return proc_run;
    }
    public void setProc_run(String proc_run) {
        this.proc_run = proc_run;
    }
    public String getProc_total() {
        return proc_total;
    }
    public void setProc_total(String proc_total) {
        this.proc_total = proc_total;
    }
    public String getPart_max_used() {
        return part_max_used;
    }
    public void setPart_max_used(String part_max_used) {
        this.part_max_used = part_max_used;
    }
    public String getDisk_free() {
        return disk_free;
    }
    public void setDisk_free(String disk_free) {
        this.disk_free = disk_free;
    }
    public String getDisk_total() {
        return disk_total;
    }
    public void setDisk_total(String disk_total) {
        this.disk_total = disk_total;
    }
    public String getDisk_read() {
        return disk_read;
    }
    public void setDisk_read(String disk_read) {
        this.disk_read = disk_read;
    }
    public String getDisk_write() {
        return disk_write;
    }
    public void setDisk_write(String disk_write) {
        this.disk_write = disk_write;
    }
    public String getDisk_io() {
        return disk_io;
    }
    public void setDisk_io(String disk_io) {
        this.disk_io = disk_io;
    }
    public String getDisk_read_sda() {
        return disk_read_sda;
    }
    public void setDisk_read_sda(String disk_read_sda) {
        this.disk_read_sda = disk_read_sda;
    }
    public String getDisk_write_sda() {
        return disk_write_sda;
    }
    public void setDisk_write_sda(String disk_write_sda) {
        this.disk_write_sda = disk_write_sda;
    }
    public String getDisk_io_sda() {
        return disk_io_sda;
    }
    public void setDisk_io_sda(String disk_io_sda) {
        this.disk_io_sda = disk_io_sda;
    }
    public String getMem_percent() {
        return mem_percent;
    }
    public void setMem_percent(String mem_percent) {
        this.mem_percent = mem_percent;
    }
    public String getSwap_percent() {
        return swap_percent;
    }
    public void setSwap_percent(String swap_percent) {
        this.swap_percent = swap_percent;
    }
    public String getDisk_percent() {
        return disk_percent;
    }
    public void setDisk_percent(String disk_percent) {
        this.disk_percent = disk_percent;
    }
    public String getParam1() {
        return param1;
    }
    public void setParam1(String param1) {
        this.param1 = param1;
    }
    public String getParam2() {
        return param2;
    }
    public void setParam2(String param2) {
        this.param2 = param2;
    }
    public String getParam3() {
        return param3;
    }
    public void setParam3(String param3) {
        this.param3 = param3;
    }
    public String getParam4() {
        return param4;
    }
    public void setParam4(String param4) {
        this.param4 = param4;
    }
    public String getParam5() {
        return param5;
    }
    public void setParam5(String param5) {
        this.param5 = param5;
    }
    public String getParam6() {
        return param6;
    }
    public void setParam6(String param6) {
        this.param6 = param6;
    }
    public String getParam7() {
        return param7;
    }
    public void setParam7(String param7) {
        this.param7 = param7;
    }
    public String getParam8() {
        return param8;
    }
    public void setParam8(String param8) {
        this.param8 = param8;
    }
    public String getParam9() {
        return param9;
    }
    public void setParam9(String param9) {
        this.param9 = param9;
    }
    public String getParam10() {
        return param10;
    }
    public void setParam10(String param10) {
        this.param10 = param10;
    }
    public String getParam11() {
        return param11;
    }
    public void setParam11(String param11) {
        this.param11 = param11;
    }
    public String getParam12() {
        return param12;
    }
    public void setParam12(String param12) {
        this.param12 = param12;
    }
    public String getParam13() {
        return param13;
    }
    public void setParam13(String param13) {
        this.param13 = param13;
    }
    public String getParam14() {
        return param14;
    }
    public void setParam14(String param14) {
        this.param14 = param14;
    }
    public String getParam15() {
        return param15;
    }
    public void setParam15(String param15) {
        this.param15 = param15;
    }
    public String getParam16() {
        return param16;
    }
    public void setParam16(String param16) {
        this.param16 = param16;
    }
    public String getParam17() {
        return param17;
    }
    public void setParam17(String param17) {
        this.param17 = param17;
    }
    public String getParam18() {
        return param18;
    }
    public void setParam18(String param18) {
        this.param18 = param18;
    }
    public String getParam19() {
        return param19;
    }
    public void setParam19(String param19) {
        this.param19 = param19;
    }
    public String getParam20() {
        return param20;
    }
    public void setParam20(String param20) {
        this.param20 = param20;
    }
    public String getParam21() {
        return param21;
    }
    public void setParam21(String param21) {
        this.param21 = param21;
    }
    public String getParam22() {
        return param22;
    }
    public void setParam22(String param22) {
        this.param22 = param22;
    }
    public String getParam23() {
        return param23;
    }
    public void setParam23(String param23) {
        this.param23 = param23;
    }
    public String getParam24() {
        return param24;
    }
    public void setParam24(String param24) {
        this.param24 = param24;
    }
    public String getParam25() {
        return param25;
    }
    public void setParam25(String param25) {
        this.param25 = param25;
    }
    public String getParam26() {
        return param26;
    }
    public void setParam26(String param26) {
        this.param26 = param26;
    }
    public String getParam27() {
        return param27;
    }
    public void setParam27(String param27) {
        this.param27 = param27;
    }
    public String getParam28() {
        return param28;
    }
    public void setParam28(String param28) {
        this.param28 = param28;
    }
    public String getParam29() {
        return param29;
    }
    public void setParam29(String param29) {
        this.param29 = param29;
    }
    public String getParam30() {
        return param30;
    }
    public void setParam30(String param30) {
        this.param30 = param30;
    }
    public String getParam31() {
        return param31;
    }
    public void setParam31(String param31) {
        this.param31 = param31;
    }
    public String getParam32() {
        return param32;
    }
    public void setParam32(String param32) {
        this.param32 = param32;
    }
    public String getParam33() {
        return param33;
    }
    public void setParam33(String param33) {
        this.param33 = param33;
    }
    public String getParam34() {
        return param34;
    }
    public void setParam34(String param34) {
        this.param34 = param34;
    }
    public String getParam35() {
        return param35;
    }
    public void setParam35(String param35) {
        this.param35 = param35;
    }
    public String getParam36() {
        return param36;
    }
    public void setParam36(String param36) {
        this.param36 = param36;
    }
    public String getParam37() {
        return param37;
    }
    public void setParam37(String param37) {
        this.param37 = param37;
    }
    public String getParam38() {
        return param38;
    }
    public void setParam38(String param38) {
        this.param38 = param38;
    }
    public String getParam39() {
        return param39;
    }
    public void setParam39(String param39) {
        this.param39 = param39;
    }
    public String getParam40() {
        return param40;
    }
    public void setParam40(String param40) {
        this.param40 = param40;
    }
    public String getParam41() {
        return param41;
    }
    public void setParam41(String param41) {
        this.param41 = param41;
    }
    public String getParam42() {
        return param42;
    }
    public void setParam42(String param42) {
        this.param42 = param42;
    }
    public String getParam43() {
        return param43;
    }
    public void setParam43(String param43) {
        this.param43 = param43;
    }
    public String getParam44() {
        return param44;
    }
    public void setParam44(String param44) {
        this.param44 = param44;
    }
    public String getParam45() {
        return param45;
    }
    public void setParam45(String param45) {
        this.param45 = param45;
    }
    public String getParam46() {
        return param46;
    }
    public void setParam46(String param46) {
        this.param46 = param46;
    }
    public String getParam47() {
        return param47;
    }
    public void setParam47(String param47) {
        this.param47 = param47;
    }
    public String getParam48() {
        return param48;
    }
    public void setParam48(String param48) {
        this.param48 = param48;
    }
    public String getParam49() {
        return param49;
    }
    public void setParam49(String param49) {
        this.param49 = param49;
    }
    public String getParam50() {
        return param50;
    }
    public void setParam50(String param50) {
        this.param50 = param50;
    }
    public String getBoottime() {
        return boottime;
    }
    public void setBoottime(String boottime) {
        this.boottime = boottime;
    }
    public String getMachine_type() {
        return machine_type;
    }
    public void setMachine_type(String machine_type) {
        this.machine_type = machine_type;
    }
    public String getGexec() {
        return gexec;
    }
    public void setGexec(String gexec) {
        this.gexec = gexec;
    }
    public String getOs_name() {
        return os_name;
    }
    public void setOs_name(String os_name) {
        this.os_name = os_name;
    }
    public String getOs_release() {
        return os_release;
    }
    public void setOs_release(String os_release) {
        this.os_release = os_release;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public Date getPer_time() {
		return per_time;
	}
	public void setPer_time(Date per_time) {
		this.per_time = per_time;
	}
	public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public String getMibOidList() {
        return mibOidList;
    }
    public void setMibOidList(String mibOidList) {
        this.mibOidList = mibOidList;
    }
}
