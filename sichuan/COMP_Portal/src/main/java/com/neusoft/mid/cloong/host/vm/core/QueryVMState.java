/*******************************************************************************
 * @(#)QueryLogicVlanAndIp.java 2014年6月3日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.core;

/**
 * 查询逻辑VLAN和私网ip接口
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月3日 下午3:48:42
 */
public interface QueryVMState {

    /**
     * 
     * queryVMState 查询虚拟机状态
     * @param req
     * @return 返回响应消息
     */
    QueryVMStateResp queryVMState(QueryVMStateReq req);
    
}
