/*******************************************************************************
 * @(#)VmBakStatus.java 2013-1-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core;

/**
 * 虚拟机备份状态码
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-14 下午04:14:09
 */
public enum VmBakStatus {
    
	/**
     * 等待备份
     */
    PREPARE("0", "等待备份"),
    /**
     * 备份中
     */
    CREATING("1", "备份中"),
    /**
     * 正常
     */
    RUNNING("2", "正常"),
    /**
     * 已删除
     */
    DELETED("3", "已删除"),
    /**
     * 恢复中
     */
    ROLLBACKING("4", "恢复中"),
    /**
     * 恢复失败
     */
    ROLLBACK_FAIL("5","恢复失败"),
    /**
     * 备份失败
     */
    CREATE_FAIL("7","备份失败"),
    /**
     * 状态异常
     */
    STATUS_EXCEPTION("9","状态异常"),
    /**
     * 任务正在等待
     */
    TASK_WAIT("10","任务正在等待"),
    /**
     * 待创建
     */
    TO_CREATE("11","待创建");

    private String code;

    private String desc;

    VmBakStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
