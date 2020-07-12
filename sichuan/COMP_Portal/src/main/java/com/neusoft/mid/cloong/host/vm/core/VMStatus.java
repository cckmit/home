/*******************************************************************************
 * @(#)VMStatus.java 2013-1-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.core;

/**
 * 虚拟机状态码
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-17 上午09:22:50
 */
public enum VMStatus {

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
     * 已暂停
     */
    PAUSE("6", "已暂停"),

    /**
     * 操作失败
     */
    OPERATE_FAIL("9", "操作失败"),

    /**
     * 发送失败
     */
    SENDERROR("14", "发送失败"),

    /**
     * 虚拟机状态异常
     */
    STATUSERROR("15", "状态异常"),

    /**
     * 处理中
     */
    PROCESSING("16", "处理中");

    private String code;

    private String desc;

    VMStatus(String code, String desc) {
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
    public static VMStatus getEnum(String code) {
        for (VMStatus us : VMStatus.values()) {
            if (us.code.equals(code)) {
                return us;
            }
        }
        return null;
    }
}
