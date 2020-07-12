/*******************************************************************************
 * @(#)VlanType.java 2015年2月28日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vlan;

/**
 * Vlan用途类型枚举
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月28日 下午4:28:45
 */
public enum VlanType {

    /**
     * 业务平面
     */
    Business("2"),
    /**
     * IP存储平面
     */
    IPStorage("3"),
    /**
     * 其他用途
     */
    Other("99");

    private String value;

    private VlanType(String value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }

    public static VlanType fromValue(int v) {
        for (VlanType c : VlanType.values()) {
            if (c.value.equals(String.valueOf(v))) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

    public static VlanType fromValue(String v) {

        for (VlanType c : VlanType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
