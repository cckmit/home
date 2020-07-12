/*******************************************************************************
 * @(#)PMOperatorStatus.java 2013-1-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.core;

/**
 * 物理机操作类型
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-14 下午04:49:05
 */
public enum PMOperatorType {

    /**
     * 创建中
     */
    PM_START("1", "pm.start.fail"), PM_STOP("4", "pm.stop.fail"), PM_REBOOT("5", "pm.reboot.fail");

    private String code;

    private String errorKey;

    PMOperatorType(String code, String errorKey) {
        this.code = code;
        this.errorKey = errorKey;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public String getCode() {
        return code;
    }
}
