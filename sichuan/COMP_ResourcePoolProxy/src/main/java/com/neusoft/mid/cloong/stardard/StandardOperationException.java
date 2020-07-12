/*******************************************************************************
 * @(#)StandardOperationException.java 2015年2月11日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard;

/**
 * 规格操作异常类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月11日 下午3:39:08
 */
public class StandardOperationException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -260236665359812554L;

    public StandardOperationException() {
        super();
    }

    public StandardOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public StandardOperationException(String message) {
        super(message);
    }

    public StandardOperationException(Throwable cause) {
        super(cause);
    }

}
