/*******************************************************************************
 * @(#)QueryLogicVlanAndIpImpl.java 2014年6月3日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.QueryIpInfoOfLogicVlanReq;
import com.neusoft.mid.cloong.host.vm.core.QueryIpInfoOfLogicVlanResp;
import com.neusoft.mid.cloong.host.vm.core.QueryLogicVlanAndIp;
import com.neusoft.mid.cloong.host.vm.core.QueryLogicVlanReq;
import com.neusoft.mid.cloong.host.vm.core.QueryLogicVlanResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * TODO 这里请补充该类型的简述说明
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月3日 下午4:05:18
 */
public class QueryLogicVlanAndIpImpl implements QueryLogicVlanAndIp, Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(QueryLogicVlanAndIpImpl.class);

    /**
     * http发送实体对象
     */
    private HttpSyncSendHelper senderEntry;

    /**
     * 接收超时时间
     */
    private int receiveTimeout = 5000;

    /**
     * 协议超时时间
     */
    private int httpTimeOut = 50;

    /**
     * 资源池代理系统URL
     */
    private String queryVlanUrl;
    
    /**
     * 资源池代理系统URL
     */
    private String queryIpOfVlanUrl;
    
    
    @Override
    public QueryLogicVlanResp queryLogicVlan(QueryLogicVlanReq queryVlanReq) {
        RuntimeContext req = new RuntimeContext();
        req.setAttribute("RES_POOL_ID", queryVlanReq.getResourcePoolId());
        req.setAttribute("RES_POOL_PART_ID", queryVlanReq.getResourcePoolPartId());
        HttpSyncRespData resp = null;
        try {
            resp = senderEntry.sendHttpRequest(req, queryVlanUrl, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误", e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误");
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL, "向资源池代理系统发送请求失败，无效的http协议", e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败", e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败");
        }
        return assembleResp(resp);
    }
    
    private QueryLogicVlanResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        QueryLogicVlanResp queryVlanResp = new QueryLogicVlanResp();
        queryVlanResp.setResultCode(code.toString());
        queryVlanResp.setResultMessage(errorMessage);
        return queryVlanResp;
    }
    
    private QueryLogicVlanResp assembleResp(HttpSyncRespData resp) {
        QueryLogicVlanResp queryVlanResp = new QueryLogicVlanResp();
        queryVlanResp.setResultCode((String) resp.getRuntimeContext().getAttribute("ResultCode"));
        queryVlanResp.setResultMessage((String) resp.getRuntimeContext().getAttribute("ResultMessage"));
        ArrayList<Map<String,String>> logicVlanSet = (ArrayList<Map<String,String>>)resp.getRuntimeContext().getAttribute("LOGIC_VLAN_SET");
        queryVlanResp.setLogicVlanSet(logicVlanSet);
        return queryVlanResp;
    }

    @Override
    public QueryIpInfoOfLogicVlanResp queryIpInfoOfLogicVlan(QueryIpInfoOfLogicVlanReq queryIpReq) {
        RuntimeContext req = new RuntimeContext();
        req.setAttribute("RES_POOL_ID", queryIpReq.getResourcePoolId());
        req.setAttribute("RES_POOL_PART_ID", queryIpReq.getResourcePoolPartId());
        req.setAttribute("LOGIC_VLANID", queryIpReq.getLogicVlanId());
        HttpSyncRespData resp = null;
        try {
            resp = senderEntry.sendHttpRequest(req, queryIpOfVlanUrl, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误", e);
            return assmbleErrorQueryIpResp(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误");
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL, "向资源池代理系统发送请求失败，无效的http协议", e);
            return assmbleErrorQueryIpResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败", e);
            return assmbleErrorQueryIpResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败");
        }
        return assembleQueryIpResp(resp);
    }
    
    private QueryIpInfoOfLogicVlanResp assmbleErrorQueryIpResp(CommonStatusCode code, String errorMessage) {
        QueryIpInfoOfLogicVlanResp queryIpResp = new QueryIpInfoOfLogicVlanResp();
        queryIpResp.setResultCode(code.toString());
        queryIpResp.setResultMessage(errorMessage);
        return queryIpResp;
    }
    
    private QueryIpInfoOfLogicVlanResp assembleQueryIpResp(HttpSyncRespData resp) {
        QueryIpInfoOfLogicVlanResp queryIpResp = new QueryIpInfoOfLogicVlanResp();
        queryIpResp.setResultCode((String) resp.getRuntimeContext().getAttribute("ResultCode"));
        queryIpResp.setResultMessage((String) resp.getRuntimeContext().getAttribute("ResultMessage"));
        ArrayList<Map<String,String>> ipSet = (ArrayList<Map<String,String>>)resp.getRuntimeContext().getAttribute("IP_SET");
        queryIpResp.setIpSet(ipSet);
        return queryIpResp;
    }

    /**
     * 获取senderEntry字段数据
     * @return Returns the senderEntry.
     */
    public HttpSyncSendHelper getSenderEntry() {
        return senderEntry;
    }

    /**
     * 设置senderEntry字段数据
     * @param senderEntry The senderEntry to set.
     */
    public void setSenderEntry(HttpSyncSendHelper senderEntry) {
        this.senderEntry = senderEntry;
    }

    /**
     * 获取receiveTimeout字段数据
     * @return Returns the receiveTimeout.
     */
    public int getReceiveTimeout() {
        return receiveTimeout;
    }

    /**
     * 设置receiveTimeout字段数据
     * @param receiveTimeout The receiveTimeout to set.
     */
    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    /**
     * 获取httpTimeOut字段数据
     * @return Returns the httpTimeOut.
     */
    public int getHttpTimeOut() {
        return httpTimeOut;
    }

    /**
     * 设置httpTimeOut字段数据
     * @param httpTimeOut The httpTimeOut to set.
     */
    public void setHttpTimeOut(int httpTimeOut) {
        this.httpTimeOut = httpTimeOut;
    }

    /**
     * 获取queryVlanUrl字段数据
     * @return Returns the queryVlanUrl.
     */
    public String getQueryVlanUrl() {
        return queryVlanUrl;
    }

    /**
     * 设置queryVlanUrl字段数据
     * @param queryVlanUrl The queryVlanUrl to set.
     */
    public void setQueryVlanUrl(String queryVlanUrl) {
        this.queryVlanUrl = queryVlanUrl;
    }

    /**
     * 获取queryIpOfVlanUrl字段数据
     * @return Returns the queryIpOfVlanUrl.
     */
    public String getQueryIpOfVlanUrl() {
        return queryIpOfVlanUrl;
    }

    /**
     * 设置queryIpOfVlanUrl字段数据
     * @param queryIpOfVlanUrl The queryIpOfVlanUrl to set.
     */
    public void setQueryIpOfVlanUrl(String queryIpOfVlanUrl) {
        this.queryIpOfVlanUrl = queryIpOfVlanUrl;
    }
    
}
