/*******************************************************************************
 * @(#)PMOperatorResultInfo.java 2013-3-4
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;

/**
 * 后台不允许对物理机操作判断后的错误信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:27:40
 */
public class PMOperatorErrorInfo {
    /**
     * 错误码，目前有两个值 0 该物理机已被删除 1 该物理机状态错误，不允许操作
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
