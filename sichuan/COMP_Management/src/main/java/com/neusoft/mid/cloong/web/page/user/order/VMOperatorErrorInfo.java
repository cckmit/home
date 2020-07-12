/*******************************************************************************
 * @(#)VMOperatorResultInfo.java 2013-3-4
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

/**
 * 后台不允许对虚拟机操作判断后的错误信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-4 上午09:35:21
 */
public class VMOperatorErrorInfo {
    /**
     * 错误码，目前有两个值 0 该虚拟机已被删除 1 该虚拟机状态错误，不允许操作
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
