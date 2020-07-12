/*******************************************************************************
 * @(#)IPType.java 2015年2月28日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ip;

/**
 * IP用途类型
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月28日 上午9:33:45
 */
public enum IPType {

    /**
     * 公网IP
     */
    PUBLIC_IP(0),
    /**
     * 承载网IP
     */
    BEARER_IP(1),
    /**
     * 私网IP
     */
    PRIVATE_IP(2);

    private int value;

    private IPType(int value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }

    public static IPType fromValue(int v) {

        for (IPType c : IPType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

    public static IPType fromValue(String v) {

        int intv = Integer.valueOf(v);

        for (IPType c : IPType.values()) {
            if (c.value == intv) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
