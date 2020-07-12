/*******************************************************************************
 * @(#)EBSStatus.java 2013-1-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.core;


/**
 * 云硬盘状态码
 * @author <a href="mailto:yuan.x@neusoft.com">YUANXUE </a>
 * @version $Revision 1.1 $ 2013-1-17 上午09:22:50
 */
public enum EBSStatus {
    
    /**
     * 待创建
     */
    PREPARE("0", "待创建"),
    /**
     * 创建中
     */
    CREATING("1", "创建中"),
    /**
     * 已创建（首次创建,从未挂载过）
     */
    CREATED("2", "已创建"),
    /**
     * 挂载中
     */
    MOUNTING("3", "挂载中"),
    /**
     * 已挂载
     */
    MOUNTON("4", "已挂载"),
    /**
     * 搁置（未挂载，挂载后又卸载了）
     */
    UNMOUNTON("5", "搁置"),
    /**
     * 创建失败
     */
    CREATEERROR("6", "创建失败"),
    /**
     * 挂载失败
     */
    MOUNTONERROR("7", "挂载失败"),
    /**
     * 发送失败
     */
    SENDERROR("8", "发送失败"),
    /**
     * 云硬盘状态异常
     */
    STATUSERROR("9", "状态异常"),
    /**
     * 已删除
     */
    DELETED("10", "已删除");

    private String code;

    private String desc;

    EBSStatus(String code, String desc) {
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
    public static EBSStatus getEnum(String code) {
        for (EBSStatus us : EBSStatus.values()) {
            if (us.code.equals(code)) {
                return us;
            }
        }
        return null;
    }
}
