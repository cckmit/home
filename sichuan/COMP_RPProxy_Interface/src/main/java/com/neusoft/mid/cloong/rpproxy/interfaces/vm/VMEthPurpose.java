/*******************************************************************************
 * @(#)CreateMode.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

/**
 * 虚拟机网卡用途枚举类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 下午12:40:07
 */
public enum VMEthPurpose {

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

    private VMEthPurpose(String value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }

    public static VMEthPurpose fromValue(int v) {
        for (VMEthPurpose c : VMEthPurpose.values()) {
            if (c.value.equals(String.valueOf(v))) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

    public static VMEthPurpose fromValue(String v) {

        for (VMEthPurpose c : VMEthPurpose.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
