/*******************************************************************************
 * @(#)VMOSDiskType.java 2015年2月25日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;


/**
 * 虚拟机操作系统硬盘类型
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月25日 上午11:02:56
 */
public enum VMOSDiskType {

    /**
     * 本地业务
     */
    LOCAL(0),
    /**
     * FC SAN
     */
    FC_SAN(1),
    /**
     * other
     */
    OTHER(99);

    private int value;

    private VMOSDiskType(int value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }
    
    public static VMOSDiskType fromValue(int v) {
        for (VMOSDiskType c : VMOSDiskType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

    public static VMOSDiskType fromValue(String v) {

        int intv = Integer.valueOf(v);

        for (VMOSDiskType c : VMOSDiskType.values()) {
            if (c.value == intv) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
