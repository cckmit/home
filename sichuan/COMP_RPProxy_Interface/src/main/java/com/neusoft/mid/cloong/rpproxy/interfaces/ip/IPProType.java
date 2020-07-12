/*******************************************************************************
 * @(#)CreateMode.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ip;


/**
 * IP类型枚举类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 下午12:40:07
 */
public enum IPProType {

    /**
     * 模板创建模式
     */
    IPV4("IPV4"),
    /**
     * 自定义创建模式
     */
    IPV6("IPV6");

    private String value;

    private IPProType(String value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }

    public static IPProType fromValue(String v) {

        for (IPProType c : IPProType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }

}
