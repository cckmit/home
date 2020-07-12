/*******************************************************************************
 * @(#)CreateMode.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ip;

/**
 * IP创建模式枚举类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 下午12:40:07
 */
public enum IPCreateModel {

    /**
     * 模板创建模式
     */
    TemplateModel("0"),
    /**
     * 自定义创建模式
     */
    CustomModel("1");

    private String value;

    private IPCreateModel(String value) {
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
