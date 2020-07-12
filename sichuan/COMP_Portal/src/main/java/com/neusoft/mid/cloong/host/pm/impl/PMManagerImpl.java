/*******************************************************************************
 * @(#)PMManagerImpl.java 2013-1-11
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMManager;
import com.neusoft.mid.cloong.host.pm.core.PmOperatorReq;
import com.neusoft.mid.cloong.host.pm.core.PmOperatorResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMOperatorReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMOperatorResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMOperatorType;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机操作实现类
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:46:31
 */
public class PMManagerImpl implements PMManager, Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(PMManagerImpl.class);

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
    public PmOperatorResp operatePm(PmOperatorReq pmReq) {
        RuntimeContext reqCxt = new RuntimeContext();
        RPPPMOperatorReq req = new RPPPMOperatorReq();
        req.setResourcePoolId(pmReq.getResourcePoolId());
        req.setResourcePoolPartId(pmReq.getResourcePoolPartId());
        req.setSerialNum(pmReq.getSerialNum());
        req.setPmId(pmReq.getPmId());
        req.setPmOperatorType(PMOperatorType.fromValue(pmReq.getType().getCode()));

        reqCxt.setAttribute(RPPPMOperatorReq.REQ_BODY, req);
        HttpSyncRespData respCxt = null;

        try {
            respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR,
                    "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + pmReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR,
                    "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + pmReq.getSerialNum());
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + pmReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为："
                    + pmReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误");
        }
        return assembleResp(respCxt);
    }

    private PmOperatorResp assembleResp(HttpSyncRespData resp) {
        RPPPMOperatorResp pmOperatorResp = (RPPPMOperatorResp) resp.getRuntimeContext()
                .getAttribute(RPPPMOperatorResp.RESP_BODY);
        PmOperatorResp pmResp = new PmOperatorResp();
        pmResp.setResultCode(pmOperatorResp.getResultCode());
        pmResp.setResultMessage(pmOperatorResp.getResultMessage());
        return pmResp;
    }

    private PmOperatorResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        PmOperatorResp vmResp = new PmOperatorResp();
        vmResp.setResultCode(code.toString());
        vmResp.setResultMessage(errorMessage);
        return vmResp;
    }

    public void setSenderEntry(HttpSyncSendHelper senderEntry) {
        this.senderEntry = senderEntry;
    }

    public HttpSyncSendHelper getSenderEntry() {
        return senderEntry;
    }

    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    public void setHttpTimeOut(int httpTimeOut) {
        this.httpTimeOut = httpTimeOut;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
