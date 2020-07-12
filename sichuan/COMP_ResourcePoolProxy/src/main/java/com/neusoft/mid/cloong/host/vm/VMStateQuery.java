/*******************************************************************************
 * @(#)VMStateQuery.java 2013-2-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMStatusQueryResp;

/**
 * 虚拟机状态查询接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-22 上午09:05:38
 */
public interface VMStateQuery {
    /**
     * 虚拟机状态查询接口
     * @param req 虚拟机状态查询请求
     * @return 虚拟机状态查询响应
     */
    RPPVMStatusQueryResp queryVMState(VMStateQueryReq req);
}
