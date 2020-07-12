/*******************************************************************************
 * @(#)QueryLogicVlanAndIpImpl.java 2014年6月4日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idc.om.idc.idc.QueryIPInfoOfLogicVlanReqType;
import com.idc.om.idc.idc.QueryIPInfoOfLogicVlanRespIPInfoType;
import com.idc.om.idc.idc.QueryIPInfoOfLogicVlanRespIpSetType;
import com.idc.om.idc.idc.QueryIPInfoOfLogicVlanRespType;
import com.idc.om.idc.idc.QueryLogicVlanReqType;
import com.idc.om.idc.idc.QueryLogicVlanRespLogicVlanInfoType;
import com.idc.om.idc.idc.QueryLogicVlanRespLogicVlanSetType;
import com.idc.om.idc.idc.QueryLogicVlanRespType;
import com.idc.om.idc.idc_wsdl.Idc;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.vm.QueryIpInfoOfLogicVlanReq;
import com.neusoft.mid.cloong.host.vm.QueryIpInfoOfLogicVlanResp;
import com.neusoft.mid.cloong.host.vm.QueryLogicVlanAndIp;
import com.neusoft.mid.cloong.host.vm.QueryLogicVlanReq;
import com.neusoft.mid.cloong.host.vm.QueryLogicVlanResp;
import com.neusoft.mid.cloong.host.vm.VlanIpRequest;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * TODO 这里请补充该类型的简述说明
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 上午9:44:44
 */
public class QueryLogicVlanAndIpImpl implements QueryLogicVlanAndIp {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private VlanIpRequest common;

    private static LogService logger = LogService.getLogger(QueryLogicVlanAndIpImpl.class);

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;
    
    @Override
    public QueryLogicVlanResp queryLogicVlan(QueryLogicVlanReq req) {
        // 打开发送客户端
        Idc client = common.openClient(req, timeout);
        QueryLogicVlanReqType queryLogicVlanReqType = new QueryLogicVlanReqType();
        
        QueryLogicVlanResp resp = new QueryLogicVlanResp();
        QueryLogicVlanRespType queryLogicVlanRespType = null;
        try {
            queryLogicVlanRespType = client.queryLogicVlan(queryLogicVlanReqType, null, null);
            // 组装响应
            assembleResp(resp, queryLogicVlanRespType);
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送消息失败,内部错误，网络异常或xml解析异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        } finally {
            // 关闭发送客户端
            common.closeClient();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送请求成功，获取的响应码为[").append(resp.getResultCode()).append("]，响应描述为[").append(
                resp.getResultMessage()).append("]");
        logger.info(sb.toString());
        return resp;
    }
    
    private void assembleResp(QueryLogicVlanResp resp, QueryLogicVlanRespType queryLogicVlanResp) {
        resp.setResultMessage(queryLogicVlanResp.getFaultString());
        resp.setResultCode(common.obtainResultCode());
        QueryLogicVlanRespLogicVlanSetType logicVlanSet = queryLogicVlanResp.getLogicVlanSet();
        List<QueryLogicVlanRespLogicVlanInfoType> logicVlanSet1 = logicVlanSet.getLogicVlanInfo();
//        List<QueryLogicVlanRespLogicVlanInfoType> logicVlanSet = (List<QueryLogicVlanRespLogicVlanInfoType>) queryLogicVlanResp.getLogicVlanSet();
        List<Map<String,String>> tempLogicVlanInfo = new ArrayList<Map<String,String>>();
        for (QueryLogicVlanRespLogicVlanInfoType respLogicVlanSet : logicVlanSet1) {
            Map<String,String> tempLogicVlanSet = new HashMap<String,String>();
            tempLogicVlanSet.put("LOGIC_VLANID", respLogicVlanSet.getLogicVlanID());
            tempLogicVlanSet.put("LOGIC_VLANNAME", respLogicVlanSet.getLogicVlanName());
            tempLogicVlanInfo.add(tempLogicVlanSet);
        }

        resp.setLogicVlanSet(tempLogicVlanInfo);
    }
    
    private QueryLogicVlanResp assmbleErrorResp(InterfaceResultCode code) {
        QueryLogicVlanResp resp = new QueryLogicVlanResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    @Override
    public QueryIpInfoOfLogicVlanResp queryIpInfoOfLogicVlan(QueryIpInfoOfLogicVlanReq req) {
     // 打开发送客户端
        Idc client = common.openClient(req, timeout);
        QueryIPInfoOfLogicVlanReqType queryIPInfoOfLogicVlanReqType = new QueryIPInfoOfLogicVlanReqType();
        queryIPInfoOfLogicVlanReqType.setLogicVlanID(req.getLogicVlanId());
        
        QueryIpInfoOfLogicVlanResp resp = new QueryIpInfoOfLogicVlanResp();
        QueryIPInfoOfLogicVlanRespType queryIPInfoOfLogicVlanRespType = null;
        try {
            queryIPInfoOfLogicVlanRespType = client.queryIPInfoOfLogicVlan(queryIPInfoOfLogicVlanReqType, null, null);
            // 组装响应
            assembleQueryIpResp(resp, queryIPInfoOfLogicVlanRespType);
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "资源池认证失败", e);
                return assmbleQueryIpErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送消息失败,内部错误，网络异常或xml解析异常", e);
            return assmbleQueryIpErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        } finally {
            // 关闭发送客户端
            common.closeClient();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送请求成功，获取的响应码为[").append(resp.getResultCode()).append("]，响应描述为[").append(
                resp.getResultMessage()).append("]");
        logger.info(sb.toString());
        return resp;
    }
    
    private void assembleQueryIpResp(QueryIpInfoOfLogicVlanResp resp, QueryIPInfoOfLogicVlanRespType queryIpOfLogicVlanResp) {
        resp.setResultMessage(queryIpOfLogicVlanResp.getFaultString());
        resp.setResultCode(common.obtainResultCode());
        QueryIPInfoOfLogicVlanRespIpSetType ipInfoSet= queryIpOfLogicVlanResp.getIpSet();
        List<QueryIPInfoOfLogicVlanRespIPInfoType> ipInfoSet1 = ipInfoSet.getIPInfo();
//        List<QueryIPInfoOfLogicVlanRespIPInfoType> ipInfoSet = (List<QueryIPInfoOfLogicVlanRespIPInfoType>) queryIpOfLogicVlanResp.getIpSet();
        List<Map<String,String>> tempIpInfo = new ArrayList<Map<String,String>>();
        for (QueryIPInfoOfLogicVlanRespIPInfoType respIpInfoSet : ipInfoSet1) {
            Map<String,String> tempIpInfoSet = new HashMap<String,String>();
            tempIpInfoSet.put("IP", respIpInfoSet.getIP());
            tempIpInfoSet.put("STATE", respIpInfoSet.getState());
            tempIpInfo.add(tempIpInfoSet);
        }

        resp.setIpSet(tempIpInfo);
    }
    
    private QueryIpInfoOfLogicVlanResp assmbleQueryIpErrorResp(InterfaceResultCode code) {
        QueryIpInfoOfLogicVlanResp resp = new QueryIpInfoOfLogicVlanResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }


    /**
     * 获取timeout字段数据
     * @return Returns the timeout.
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * 设置timeout字段数据
     * @param timeout The timeout to set.
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * 获取common字段数据
     * @return Returns the common.
     */
    public VlanIpRequest getCommon() {
        return common;
    }

    /**
     * 设置common字段数据
     * @param common The common to set.
     */
    public void setCommon(VlanIpRequest common) {
        this.common = common;
    }

}
