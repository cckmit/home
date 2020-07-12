/*******************************************************************************
 * @(#)ZoneType.java 2015年2月28日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vlan;

/**
 * Vlan安全域
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月28日 下午4:29:31
 */
public enum ZoneType {

    /**
     * 本地业务
     */
    DMZ_ZONE(1),
    /**
     * FC SAN
     */
    TRUST_ZONE(2),
    /**
     * other
     */
    TEST_ZONE(3);

    private int value;

    private ZoneType(int value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }

    public static ZoneType fromValue(int v) {
        for (ZoneType c : ZoneType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

    public static ZoneType fromValue(String v) {
        int intv = Integer.valueOf(v);
        for (ZoneType c : ZoneType.values()) {
            if (c.value == intv) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
