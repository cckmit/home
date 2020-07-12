/*******************************************************************************
 * @(#)QueryPMStateImpl.java 2014年6月3日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.QueryPMState;
import com.neusoft.mid.cloong.host.pm.core.QueryPMStateReq;
import com.neusoft.mid.cloong.host.pm.core.QueryPMStateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMStatusQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMStatusQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMStatusQueryResp;
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
public class QueryPMStateImpl implements QueryPMState, Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(QueryPMStateImpl.class);

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
    private String queryPMStateUrl;

    @Override
    public QueryPMStateResp queryPMState(QueryPMStateReq queryPMStateReq) {
        RuntimeContext reqCxt = new RuntimeContext();
        RPPPMStatusQueryReq req = new RPPPMStatusQueryReq();
        req.setResourcePoolId(queryPMStateReq.getResourcePoolId());
        req.setResourcePoolPartId(queryPMStateReq.getResourcePoolPartId());
        req.setPmId(queryPMStateReq.getPmId());
        reqCxt.setAttribute(RPPPMStatusQueryReq.REQ_BODY, req);
        HttpSyncRespData respCxt = null;
        try {
            respCxt = senderEntry.sendHttpRequest(reqCxt, queryPMStateUrl, httpTimeOut,
                    receiveTimeout);
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
        return assembleResp(respCxt);
    }

    private QueryPMStateResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        QueryPMStateResp queryPMStateResp = new QueryPMStateResp();
        queryPMStateResp.setResultCode(code.toString());
        queryPMStateResp.setResultMessage(errorMessage);
        return queryPMStateResp;
    }

    private QueryPMStateResp assembleResp(HttpSyncRespData resp) {
        RPPPMStatusQueryResp pmStatusQueryResp = (RPPPMStatusQueryResp) resp.getRuntimeContext()
                .getAttribute(RPPVMStatusQueryResp.RESP_BODY);
        QueryPMStateResp queryPMStateResp = new QueryPMStateResp();
        queryPMStateResp.setResultCode(pmStatusQueryResp.getResultCode());
        queryPMStateResp.setResultMessage(pmStatusQueryResp.getResultMessage());
        queryPMStateResp.setPmState(pmStatusQueryResp.getPmStatus().getCode());
        return queryPMStateResp;
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
     * 获取queryPMStateUrl字段数据
     * @return Returns the queryPMStateUrl.
     */
    public String getQueryPMStateUrl() {
        return queryPMStateUrl;
    }

    /**
     * 设置queryPMStateUrl字段数据
     * @param queryPMStateUrl The queryPMStateUrl to set.
     */
    public void setQueryPMStateUrl(String queryPMStateUrl) {
        this.queryPMStateUrl = queryPMStateUrl;
    }

}
