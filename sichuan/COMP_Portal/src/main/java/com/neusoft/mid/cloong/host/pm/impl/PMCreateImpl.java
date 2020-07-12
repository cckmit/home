/*******************************************************************************
 * @(#)PMCreateImpl.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMCreate;
import com.neusoft.mid.cloong.host.pm.core.PMCreateReq;
import com.neusoft.mid.cloong.host.pm.core.PMCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建物理机接口
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-27 上午10:43:34
 */
public class PMCreateImpl implements PMCreate, Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(PMCreateImpl.class);

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
    private String url;

    @Override
    public RPPPMCreateResp createCustomPM(RPPPMCreateReq req) {
        try {
            if (logger.isDebugEnable()) {
                logger.debug("创建高定制物理机信息接口开始组装");
            }
            RuntimeContext reqCxt = new RuntimeContext();
            reqCxt.setAttribute(RPPPMCreateReq.REQ_BODY, req);
            HttpSyncRespData respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut,
                    receiveTimeout);
            RPPPMCreateResp createResp = (RPPPMCreateResp) respCxt.getRuntimeContext()
                    .getAttribute(RPPPMCreateResp.RESP_BODY);

            return createResp;
        } catch (IOException e) {
            RPPPMCreateResp resp = new RPPPMCreateResp();
            logger.error(CommonStatusCode.IO_OPTION_ERROR,
                    "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum(), e);
            resp.setFaultstring("向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum());
            resp.setResultCode(CommonStatusCode.IO_OPTION_ERROR.toString());
            return resp;
        } catch (InvalidProtocolException e) {
            RPPPMCreateResp resp = new RPPPMCreateResp();
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + req.getSerialNum(), e);
            resp.setFaultstring("向资源池代理系统发送请求失败，无效的http协议");
            resp.setResultCode(CommonStatusCode.INVALID_HTTP_PROTOCOL.toString());
            return resp;
        } catch (Exception e) {
            RPPPMCreateResp resp = new RPPPMCreateResp();
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为："
                    + req.getSerialNum(), e);
            resp.setFaultstring("向资源池代理系统发送请求失败，自服务系统内部错误");
            resp.setResultCode(CommonStatusCode.RUNTIME_EXCEPTION.toString());
            return resp;
        }
    }

    /*
     * 申请创建物理机订单
     * @see
     * com.neusoft.mid.cloong.web.page.pm.PMOperator#createPM(com.neusoft.mid.cloong.web.page.pm
     * .CreatePMReq)
     */
    @Override
    public PMCreateResp createPM(PMCreateReq pmReq) {

        // TODO：判断审批开关。默认为自动审批

        if (logger.isDebugEnable()) {
            logger.debug("创建物理机信息接口开始组装");
        }
        RuntimeContext req = assembleRuntimeContext(pmReq);

        if (logger.isDebugEnable()) {
            logger.debug("创建物理机信息接口组装成功");
        }

        HttpSyncRespData resp = null;
        try {
            if (logger.isDebugEnable()) {
                logger.debug("创建物理机信息接口准备发送");
                logger.debug("url:" + url);
            }

            resp = senderEntry.sendHttpRequest(req, url, httpTimeOut, receiveTimeout);

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
        if (logger.isDebugEnable()) {
            logger.debug("创建物理机信息接口发送成功");
        }
        return assembleResp(resp);
    }

    private RuntimeContext assembleRuntimeContext(PMCreateReq pmReq) {
        RuntimeContext req = new RuntimeContext();
        req.setAttribute("STANDARD_ID", pmReq.getStandardId());
        req.setAttribute("NUM", pmReq.getNum());
        req.setAttribute("SUBNETWORK", pmReq.getSubNetwork());
        req.setAttribute("RES_POOL_ID", pmReq.getResourcePoolId());
        req.setAttribute("RES_POOL_PART_ID", pmReq.getResourcePoolPartId());
        req.setAttribute("PM_NAME_INFO", pmReq.getPmNameInfo());
        req.setAttribute("APP_NAME", pmReq.getAppName());
        return req;
    }

    private PMCreateResp assembleResp(HttpSyncRespData resp) {
        PMCreateResp pmResp = new PMCreateResp();
        pmResp.setResultCode((String) resp.getRuntimeContext().getAttribute("ResultCode"));
        pmResp.setResultMessage((String) resp.getRuntimeContext().getAttribute("ResultMessage"));
        ArrayList<Map<String, String>> pmInfo = (ArrayList<Map<String, String>>) resp
                .getRuntimeContext().getAttribute("PM_INFO");
        for (Map<String, String> map : pmInfo) {
            map.put("ACCEPT_TIME", (String) resp.getRuntimeContext().getAttribute("TIME_STAMP"));
        }
        pmResp.setPmInfo(pmInfo);
        return pmResp;
    }

    private PMCreateResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        PMCreateResp pmResp = new PMCreateResp();
        pmResp.setResultCode(code.toString());
        pmResp.setResultMessage(errorMessage);
        return pmResp;
    }

    public HttpSyncSendHelper getSenderEntry() {
        return senderEntry;
    }

    public void setSenderEntry(HttpSyncSendHelper senderEntry) {
        this.senderEntry = senderEntry;
    }

    public int getReceiveTimeout() {
        return receiveTimeout;
    }

    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    public int getHttpTimeOut() {
        return httpTimeOut;
    }

    public void setHttpTimeOut(int httpTimeOut) {
        this.httpTimeOut = httpTimeOut;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
