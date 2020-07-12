/*******************************************************************************
 * @(#)VMOperatorStatus.java 2013-1-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vm.core;

/**
 * 虚拟机操作类型
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-22 下午04:12:46
 */
public enum VMOperatorType {

    /**
     * 创建中
     */
    VM_START("1", "vm.start.fail"), VM_STOP("4", "vm.stop.fail"), VM_PAUSE("2", "vm.pause.fail"), VM_RESUME(
            "3", "vm.resume.fail"), VM_REBOOT("5", "vm.reboot.fail");

    private String code;

    private String errorKey;

    VMOperatorType(String code, String errorKey) {
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
