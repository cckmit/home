/*******************************************************************************
 * @(#)RoleStatus.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean.core;

/**
 * 
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月10日 下午1:47:30
 */
public enum RoleStatus {
    /**
     * 无效状态
     */
    INVALID("0", "无效"),
    /**
     * 有效状态
     */
    EFFECTIVE("1", "有效");
    /**
     * 状态代码
     */
    private String code;
    /**
     * 状态描述
     */
    private String desc;
    /**
     * 状态
     * @param code 状态代码
     * @param desc 状态描述
     */
    RoleStatus(String code,String desc){
        this.code = code;
        this.desc = desc;
    }
    /**
     * 获取条目状态
     * @param code 状态字符串
     * @return 存在返回 RoleStatus 枚举类型.否则返回null
     */
    public static RoleStatus obtainItemStatusEunm(String code) {
        for (RoleStatus status : RoleStatus.values()) {
            if (code.equals(status.getCode())) {
                return status;
            }
        }
        return null;
    }
    public String getCode() {
        return code;
    }
    /**
     * 字符串格式代码
     * @return 返回字符串格式代码
     */
    public String toString() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
