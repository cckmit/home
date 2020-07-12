/*******************************************************************************
 * @(#)VMBackupType.java 2015年3月2日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

/**
 * 虚拟机备份方式
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年3月2日 下午1:29:11
 */
public enum VMBakMode {

    /**
     * 单次备份
     */
    SINGLE_BACK(1),
    /**
     * 周期备份
     */
    CYCLE_BACK(2);

    private int value;

    private VMBakMode(int value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }

    public static VMBakMode fromValue(int v) {
        for (VMBakMode c : VMBakMode.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

    public static VMBakMode fromValue(String v) {
        int intv = Integer.valueOf(v);
        for (VMBakMode c : VMBakMode.values()) {
            if (c.value == intv) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
