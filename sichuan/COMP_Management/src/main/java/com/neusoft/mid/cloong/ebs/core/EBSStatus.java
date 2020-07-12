/*******************************************************************************
 * @(#)EBSStatus.java 2013-1-17
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.core;

/**
 * 虚拟硬盘状态码
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
     * 虚拟硬盘状态异常
     */
    STATUSERROR("9", "状态异常"),
    
    /**
     * 已删除
     */
    DELETED("10", "已删除");
    
    /**
     * code 
     */
    private String code;
    
    /**
     * desc 描述
     */
    private String desc;

    /**
     * 
     * 创建一个新的实例 EBSStatus
     * @param code TODO
     * @param desc TODO 
     */
    EBSStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    /**
     * 
     * getCode TODO 方法
     * @return TODO
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 
     * toString TODO 
     * @return TODO 
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return code;
    }
    
    /**
     * 
     * getDesc TODO 方法
     * @return TODO
     */
    public String getDesc() {
        return desc;
    }
}
