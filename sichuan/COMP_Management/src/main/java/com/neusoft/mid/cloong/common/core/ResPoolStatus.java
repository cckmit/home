/*******************************************************************************
 * @(#)VMStatus.java 2013-2-617
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.core;

/**
 * 资源池状态（资源池分区相同）
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-6 下午01:36:19
 */
public enum ResPoolStatus {
    /**
     * 删除
     */
    DELTE("0", "删除"),
    /**
     * 正常
     */
    NORMAL("1", "正常"),
    /**
     * 暂停
     */
    PAUSE("2", "暂停"),
    /**
     * 终止
     */
    STOP("3", "终止");

    private String code;

    private String desc;

    ResPoolStatus(String code, String desc) {
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
