/*******************************************************************************
 * @(#)VmBakOperatorType.java 2013-1-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core;

/**
 * 虚拟机备份操作类型
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-14 下午04:49:05
 */
public enum VmBakOperatorType {

    PM_START("1", "vmbak.start.fail"), 
    PM_DEL("2", "vmbak.delete.fail"),
    PM_ROLLBACK("3", "vmbak.rollback.fail");

    private String code;

    private String errorKey;

    VmBakOperatorType(String code, String errorKey) {
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
