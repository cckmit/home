/*******************************************************************************
 * @(#)PMStatus.java 2013-1-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.core;

/**
 * 物理机状态码
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-14 下午04:14:09
 */
public enum PMStatus {

    /**
     * 待创建
     */
    PREPARE("0", "待创建"),
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
     * 状态异常
     */
    STATUSERROR("11", "状态异常"),

    /**
     * 已暂停
     */
    PAUSE("12", "已暂停"),

    /**
     * 处理中
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

    /**
     * getEnum 根据状态码获得用户状态枚举对象
     * @param code 状态码
     * @return 状态码对应的枚举对象
     */
    public static PMStatus getEnum(String code) {
        for (PMStatus us : PMStatus.values()) {
            if (us.code.equals(code)) {
                return us;
            }
        }
        return null;
    }
}
