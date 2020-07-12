/*******************************************************************************
 * @(#)ApplyState.java 2015年2月28日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ip;

/**
 * IP申请状态枚举类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月28日 下午3:17:05
 */
public enum IPApplyState {

    /**
     * 模板创建模式
     */
    NO_APPLY("0"),
    /**
     * 自定义创建模式
     */
    PART_APPLY("1"),
    /**
     * 自定义创建模式
     */
    ALL_APPLY("2");

    private String value;

    private IPApplyState(String value) {
        this.value = value;
    }

    /**
     * 获取value字段数据
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }

}
