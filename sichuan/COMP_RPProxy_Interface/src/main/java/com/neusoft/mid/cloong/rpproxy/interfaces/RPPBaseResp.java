/*******************************************************************************
 * @(#)BaseReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces;

import java.io.Serializable;

/**
 * 前台请求资源池代理的应答基类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 下午1:30:50
 */
public abstract class RPPBaseResp implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7248492378293202929L;

    /**
     * 请求Bean的attribute key
     */
    public static final String RESP_BODY = "response_body";

    private String resultCode;

    private String resultMessage;

    /**
     * 获取resultCode字段数据
     * @return Returns the resultCode.
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * 设置resultCode字段数据
     * @param resultCode The resultCode to set.
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 获取resultMessage字段数据
     * @return Returns the resultMessage.
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * 设置resultMessage字段数据
     * @param resultMessage The resultMessage to set.
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
