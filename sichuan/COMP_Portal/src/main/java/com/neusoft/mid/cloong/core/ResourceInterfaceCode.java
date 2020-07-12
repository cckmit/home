/*******************************************************************************
 * @(#)ResourceInterfaceCode.java 2013-2-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.core;

/**
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-7 下午04:22:15
 */
public enum ResourceInterfaceCode {

    /**
     * 虚拟机操作接口标识
     */
    VM_OPERATOR("VM001");

    private final String code;

    ResourceInterfaceCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

}
