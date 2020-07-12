/*******************************************************************************
 * @(#)VMStatus.java 2013-2-617
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.core;

/**
 * 服务条目状态码
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-6 上午09:42:15
 */
public enum RecommendStatus {
    /**
     * 不推荐
     */
    UN_RECOMMENDATION("0", "不推荐"),
    /**
     * 推荐
     */
    RECOMMENDATION("1", "推荐");

    private String code;

    private String desc;

    RecommendStatus(String code, String desc) {
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
