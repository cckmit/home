/*******************************************************************************
 * @(#)UserOperationException.java 2014年1月17日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.exception;

/**
 * 用户操作失败异常类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月17日 下午1:28:27
 */
public class UserOperationException extends IdentityException {

    /**
     * serialVersionUID : 序列号版本号
     */
    private static final long serialVersionUID = -995335247971368420L;

    /**
     * 创建一个新的实例 UserOperationException
     */
    public UserOperationException() {
        super();
    }

    /**
     * 创建一个新的实例 UserOperationException
     * @param message 异常消息
     */
    public UserOperationException(String message) {
        super(message);
    }

    /**
     * 创建一个新的实例 UserOperationException
     * @param message 异常消息
     * @param errorCode 错误码
     */
    public UserOperationException(String message, String errorCode) {
        super("错误码[" + errorCode + "]:" + message);
    }

}
