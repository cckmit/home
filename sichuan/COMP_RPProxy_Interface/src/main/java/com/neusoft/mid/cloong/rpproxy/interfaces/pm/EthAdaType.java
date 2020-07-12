/*******************************************************************************
 * @(#)PMOSDiskType.java 2015年2月25日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.pm;


/**
 * 物理机网卡的个数
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月25日 上午11:02:56
 */
public enum EthAdaType {

    /**
     * 千兆
     */
    QIAN_MB(1),
    /**
     * 万兆
     */
    WAN_MB(2);

    private int value;

    private EthAdaType(int value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }
    
    public static EthAdaType fromValue(int v) {
        for (EthAdaType c : EthAdaType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

    public static EthAdaType fromValue(String v) {

        int intv = Integer.valueOf(v);

        for (EthAdaType c : EthAdaType.values()) {
            if (c.value == intv) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
