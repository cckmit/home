/*******************************************************************************
 * @(#)LogProcessException.java 2015年4月7日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.logger;

/**
 * logProcess处理类处理数据时发生的异常
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年4月7日 下午4:12:37
 */
public class LogProcessException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5717091867713018655L;

    public LogProcessException() {
        super();
    }

    public LogProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogProcessException(String message) {
        super(message);
    }

    public LogProcessException(Throwable cause) {
        super(cause);
    }

}
