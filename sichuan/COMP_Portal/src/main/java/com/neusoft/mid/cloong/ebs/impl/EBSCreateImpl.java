/*******************************************************************************
 * @(#)EBSCreateImpl.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.impl;

import java.io.IOException;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.ebs.core.EBSCreate;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 创建云硬盘接口
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-27 上午10:43:34
 */
public class EBSCreateImpl implements EBSCreate {

    private static LogService logger = LogService.getLogger(EBSCreateImpl.class);

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

    /*
     * 申请创建云硬盘订单
     * @see
     * com.neusoft.mid.cloong.web.page.ebs.EBSOperator#createEBS(com.neusoft.mid.cloong.web.page.ebs
     * .CreateEBSReq)
     */
    @Override
    public RPPEBSCreateResp createEBS(RPPEBSCreateReq ebsReq) {

        // TODO：判断审批开关。默认为自动审批

        if (logger.isDebugEnable()) {
            logger.debug("创建云硬盘信息接口开始组装");
        }
        RuntimeContext req = assembleRuntimeContext(ebsReq);

        if (logger.isDebugEnable()) {
            logger.debug("创建云硬盘信息接口组装成功");
        }
        HttpSyncRespData resp = null;
        try {
            if (logger.isDebugEnable()) {
                logger.debug("创建云硬盘信息接口准备发送");
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
            logger.debug("创建云硬盘信息接口发送成功");
        }
        return assembleResp(resp);
    }

    private RuntimeContext assembleRuntimeContext(RPPEBSCreateReq ebsReq) {
        RuntimeContext req = new RuntimeContext();
        req.setAttribute(RPPBaseReq.REQ_BODY, ebsReq);

        return req;
    }

    private RPPEBSCreateResp assembleResp(HttpSyncRespData resp) {
        RPPEBSCreateResp ebsResp = new RPPEBSCreateResp();
        ebsResp = (RPPEBSCreateResp) resp.getRuntimeContext().getAttribute(RPPBaseResp.RESP_BODY);

        return ebsResp;
    }

    private RPPEBSCreateResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        RPPEBSCreateResp ebsResp = new RPPEBSCreateResp();
        ebsResp.setResultCode(code.toString());
        ebsResp.setResultMessage(errorMessage);
        return ebsResp;
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
