/*******************************************************************************
 * @(#)EBSAttachImpl.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.core.impl;

import java.io.IOException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.ebs.core.EBSDelete;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘挂载
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午03:24:21
 */
public class EBSDeleteImpl implements EBSDelete {

    private static LogService logger = LogService.getLogger(EBSDeleteImpl.class);

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
     * (non-Javadoc)
     * @see
     * com.neusoft.mid.cloong.ebs.core.EBSAttach#attach(com.neusoft.mid.cloong.ebs.core.RPPEBSOperatorReq
     * )
     */
    @Override
    public RPPEBSDeleteResp delete(RPPEBSDeleteReq ebsReq) {
        RuntimeContext req = new RuntimeContext();
        req.setAttribute(RPPBaseReq.REQ_BODY, ebsReq);
        HttpSyncRespData resp = null;
        try {
            resp = senderEntry.sendHttpRequest(req, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误", e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误");
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL, "向资源池代理系统发送请求失败，无效的http协议", e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误", e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败");
        }
        return assembleResp(resp);
    }

    private RPPEBSDeleteResp assembleResp(HttpSyncRespData resp) {
        RPPEBSDeleteResp ebsResp = new RPPEBSDeleteResp();
        ebsResp = (RPPEBSDeleteResp) resp.getRuntimeContext().getAttribute(RPPBaseResp.RESP_BODY);
        return ebsResp;
    }

    private RPPEBSDeleteResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        RPPEBSDeleteResp ebsResp = new RPPEBSDeleteResp();
        ebsResp.setResultCode(code.toString());
        ebsResp.setResultMessage(errorMessage);
        return ebsResp;
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
