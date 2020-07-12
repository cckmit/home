/*******************************************************************************
 * @(#)PMDelImpl.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.pm.core.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.pm.core.PMDel;
import com.neusoft.mid.cloong.pm.core.PmDeleteReq;
import com.neusoft.mid.cloong.pm.core.PmDeleteResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMDeleteResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机删除实现类
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:42:10
 */
public class PMDelImpl implements PMDel ,Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(PMDelImpl.class);

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
    public PmDeleteResp delPm(PmDeleteReq pmReq) {
    	RuntimeContext reqCxt = new RuntimeContext();
    	RPPPMDeleteReq req = new RPPPMDeleteReq();
    	req.setResourcePoolId(pmReq.getResourcePoolId());
    	req.setResourcePoolPartId(pmReq.getResourcePoolPartId());
    	req.setSerialNum(pmReq.getSerialNum());
    	req.setPmId(pmReq.getPmId());
        reqCxt.setAttribute(RPPPMDeleteReq.REQ_BODY, req);
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

    private PmDeleteResp assembleResp(HttpSyncRespData resp) {
    	RPPPMDeleteResp pmDeleteResp = (RPPPMDeleteResp) resp
				.getRuntimeContext().getAttribute(
						RPPPMDeleteResp.RESP_BODY);
        PmDeleteResp pmResp = new PmDeleteResp();
        pmResp.setResultCode(pmDeleteResp.getResultCode());
        pmResp.setResultMessage(pmDeleteResp.getResultMessage());
        return pmResp;
    }

    private PmDeleteResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        PmDeleteResp pmResp = new PmDeleteResp();
        pmResp.setResultCode(code.toString());
        pmResp.setResultMessage(errorMessage);
        return pmResp;
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
