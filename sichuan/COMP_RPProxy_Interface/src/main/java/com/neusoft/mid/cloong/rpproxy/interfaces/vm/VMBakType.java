/*******************************************************************************
 * @(#)VMBackupType.java 2015年3月2日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

/**
 * 虚拟机备份类型
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年3月2日 下午1:29:11
 */
public enum VMBakType {

    /**
     * 全量备份
     */
    FULL_BACK(1),
    /**
     * 增量备份
     */
    INCREMENT_BACK(2);

    private int value;

    private VMBakType(int value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }

    public static VMBakType fromValue(int v) {
        for (VMBakType c : VMBakType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

    public static VMBakType fromValue(String v) {
        int intv = Integer.valueOf(v);
        for (VMBakType c : VMBakType.values()) {
            if (c.value == intv) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
