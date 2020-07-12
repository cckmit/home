/*******************************************************************************
 * @(#)VMStatus.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.core;

/**
 * 服务条目类型
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-5 下午04:52:38
 */
public enum UseStatus {
    /**
     *未关联
     */
    NO_USED("0", "未使用"),
    /**
     * 已关联
     */
    USEING("1", "已使用");

    private String code;

    private String desc;

    UseStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
