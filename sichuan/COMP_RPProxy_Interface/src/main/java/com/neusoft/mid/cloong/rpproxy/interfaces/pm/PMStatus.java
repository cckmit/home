/*******************************************************************************
 * @(#)PMStatus.java 2013-1-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.pm;

/**
 * 物理机状态码
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-17 上午09:22:50
 */
public enum PMStatus {
    /**
     * 创建中
     */
    CREATING("1", "创建中"),  
    /**
     * 运行中
     */
    RUNNING("2", "运行中"),
    /**
     * 已删除
     */
    DELETED("3", "已删除"),
    /**
     * 已停止
     */
    STOP("4", "已停止"),
    /**
     * 操作失败
     */
    OPERATE_FAIL("5", "操作失败"),
    /**
     * 发送失败
     */
    SENDERROR("10", "发送失败"),
    /**
     * 物理机状态异常
     */
    STATUSERROR("11", "状态异常"),

    /**
     * 已暂停
     */
    PAUSE("12", "已暂停"),
    /**
     * 处理中（安装中）
     */
    PROCESSING("13", "处理中");

    private String code;

    private String desc;

    PMStatus(String code, String desc) {
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
