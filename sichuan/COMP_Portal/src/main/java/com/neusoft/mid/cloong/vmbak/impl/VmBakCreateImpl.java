/*******************************************************************************
 * @(#)VmBakCreateImpl.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateResp;
import com.neusoft.mid.cloong.vmbak.core.VmBakCreate;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**虚拟机备份任务创建接口
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-27 上午10:43:34
 */
public class VmBakCreateImpl implements VmBakCreate,Serializable{

	/**
     * 序列号
     */
	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService.getLogger(VmBakCreateImpl.class);

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
    
    /**
     * 创建虚拟机备份任务订单
     */
    @Override
    public RPPVMBakCreateResp createVmBak(RPPVMBakCreateReq vmBakReq) {
        RuntimeContext req = this.assembleRuntimeContext(vmBakReq);
        HttpSyncRespData resp = null;
        try {
            if (logger.isDebugEnable()) {
                logger.debug("创建虚拟机备份任务信息接口准备发送");
                logger.debug("url:" + url);
            }

            resp = senderEntry.sendHttpRequest(req, url, httpTimeOut, receiveTimeout);

        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误", e);
            return this.assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误");
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL, "向资源池代理系统发送请求失败，无效的http协议", e);
            return this.assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败", e);
            return this.assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败");
        }
        if (logger.isDebugEnable()) {
            logger.debug("创建虚拟机备份任务信息接口发送成功");
        }
        return this.assembleResp(resp);
    }

    private RuntimeContext assembleRuntimeContext(RPPVMBakCreateReq vmBakReq) {
    	RuntimeContext req = new RuntimeContext();
        req.setAttribute(RPPBaseReq.REQ_BODY, vmBakReq);
        return req;
    }

    private RPPVMBakCreateResp assembleResp(HttpSyncRespData resp) {
    	RPPVMBakCreateResp vmBakResp = (RPPVMBakCreateResp) resp.getRuntimeContext().getAttribute(RPPBaseResp.RESP_BODY);
        return vmBakResp;
    }

    private RPPVMBakCreateResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
    	RPPVMBakCreateResp vmBakResp = new RPPVMBakCreateResp();
        vmBakResp.setResultCode(code.toString());
        vmBakResp.setResultMessage(errorMessage);
        return vmBakResp;
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
