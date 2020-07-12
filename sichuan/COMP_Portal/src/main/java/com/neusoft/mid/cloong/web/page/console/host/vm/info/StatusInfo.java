/*******************************************************************************
 * @(#)StatusInfo.java 2013-1-10
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm.info;

import java.io.Serializable;

/**
 * 虚拟机状态信息，包括数据库中状态码和前台展示状态信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-10 下午04:29:12
 */
public class StatusInfo implements Serializable{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private String statusCode;

    /**
     * 前台显示状态信息
     */
    private String statusMessage;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
