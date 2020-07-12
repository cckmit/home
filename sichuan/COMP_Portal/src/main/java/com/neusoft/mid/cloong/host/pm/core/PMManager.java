/*******************************************************************************
 * @(#)PMManager.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.core;

/**
 * 物理机管理接口
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:38:00
 */
public interface PMManager {
    /**
     * 操作虚拟机，包括：启动，停止，重启
     * @param req TODO
     * @return 成功返回true,失败返回false
     */
    PmOperatorResp operatePm(PmOperatorReq req);

}
