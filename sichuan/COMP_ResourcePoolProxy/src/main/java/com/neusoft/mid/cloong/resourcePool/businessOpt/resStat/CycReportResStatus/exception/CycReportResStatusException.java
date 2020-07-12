/*******************************************************************************
 * @(#)CycReportResStatusException.java 2015年1月13日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.exception;

/**
 * 处理周期上报资源状态信息异常
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年1月13日 下午8:10:38
 */
public class CycReportResStatusException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3155966321924774448L;

    public CycReportResStatusException() {
        super();
    }

    public CycReportResStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public CycReportResStatusException(String message) {
        super(message);
    }

    public CycReportResStatusException(Throwable cause) {
        super(cause);
    }

}
