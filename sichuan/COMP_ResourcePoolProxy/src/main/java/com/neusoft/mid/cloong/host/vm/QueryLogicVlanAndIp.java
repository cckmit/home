/*******************************************************************************
 * @(#)QueryLogicVlanAndIp.java 2014年6月3日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

/**
 * 查询逻辑VLAN和私网ip接口
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月3日 下午3:48:42
 */
public interface QueryLogicVlanAndIp {

    /**
     * 
     * queryLogicVlan 查询虚拟机vlan
     * @param req
     * @return 返回响应消息
     */
    QueryLogicVlanResp queryLogicVlan(QueryLogicVlanReq req);
    
    QueryIpInfoOfLogicVlanResp queryIpInfoOfLogicVlan(QueryIpInfoOfLogicVlanReq req);
}
