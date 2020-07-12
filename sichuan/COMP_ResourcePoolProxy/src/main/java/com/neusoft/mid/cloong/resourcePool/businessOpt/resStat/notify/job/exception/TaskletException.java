/*******************************************************************************
 * @(#)TaskletException.java 2014年2月27日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception;

/**
 * 任务执行失败异常类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月27日 下午3:28:09
 */
public class TaskletException extends Exception {

    /**
     * serialVersionUID :
     */
    private static final long serialVersionUID = 7974880235823388544L;

    /**
     * 创建一个新的实例 TaskletException
     */
    public TaskletException() {
        super();
    }

    /**
     * 创建一个新的实例 TaskletException
     * @param message
     */
    public TaskletException(String message) {
        super(message);
    }

    public TaskletException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskletException(Throwable cause) {
        super(cause);
    }

}
