/*******************************************************************************
 * @(#)UserOperationException.java 2014年1月17日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.app.exception;

/**
 *  业务管理操作失败异常类
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version 创建时间：2014-12-26 上午9:01:26
 */
public class AppOperationException extends Exception {

    /**
     * serialVersionUID : 序列号版本号
     */
    private static final long serialVersionUID = -995335247971368420L;

    /**
     * 创建一个新的实例 AppOperationException
     */
    public AppOperationException() {
        super();
    }

    /**
     * 创建一个新的实例 AppOperationException
     * @param message 异常消息
     */
    public AppOperationException(String message) {
        super(message);
    }

    /**
     * 创建一个新的实例 AppOperationException
     * @param message 异常消息
     * @param errorCode 错误码
     */
    public AppOperationException(String message, String errorCode) {
        super("错误码[" + errorCode + "]:" + message);
    }

}
