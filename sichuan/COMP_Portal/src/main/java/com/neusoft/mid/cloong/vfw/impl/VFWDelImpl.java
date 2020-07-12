/*******************************************************************************
 * @(#)VMManagerImpl.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vfw.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.fileStore.core.DeleteFileStorageReq;
import com.neusoft.mid.cloong.fileStore.core.DeleteFileStorageResp;
import com.neusoft.mid.cloong.fileStore.core.FSDel;
import com.neusoft.mid.cloong.host.vm.core.VMDel;
import com.neusoft.mid.cloong.host.vm.core.VmDeleteReq;
import com.neusoft.mid.cloong.host.vm.core.VmDeleteResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageDeleteResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPCancelVirFWServiceReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vfw.RPPCancelVirFWServiceResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPCancelVirfwOperationRequst;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPCancelVirfwOperationResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteResp;
import com.neusoft.mid.cloong.vfw.core.CancelVirFWServiceReq;
import com.neusoft.mid.cloong.vfw.core.CancelVirFWServiceResp;
import com.neusoft.mid.cloong.vfw.core.VFWDel;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;


/**
 * 虚拟防火请删除实现类
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-13 下午04:20:13
 */
public class VFWDelImpl implements VFWDel , Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(VFWDelImpl.class);

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
    public CancelVirFWServiceResp delVFW(CancelVirFWServiceReq vfwReq) {
    	RuntimeContext reqCxt = new RuntimeContext();
    	RPPCancelVirfwOperationRequst req = new RPPCancelVirfwOperationRequst();
    	req.setResourcePoolId(vfwReq.getResourcePoolId());
    	req.setResourcePoolPartId(vfwReq.getResourcePoolPartId());
    	req.setSerialNum(vfwReq.getSerialNum());
    	req.setVirfwid(vfwReq.getVirfwid());
        reqCxt.setAttribute(RPPCancelVirfwOperationRequst.REQ_BODY, req);
		HttpSyncRespData respCxt = null;
        try {
        	respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误，本次操作流水号为："
                    + vfwReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR,
                    "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + vfwReq.getSerialNum());
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + vfwReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为："
                    + vfwReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误");
        }
        return assembleResp(respCxt);
    }

    private CancelVirFWServiceResp assembleResp(HttpSyncRespData resp) {
    	RPPCancelVirfwOperationResponse vfwDeleteResp = (RPPCancelVirfwOperationResponse) resp
				.getRuntimeContext().getAttribute(
						RPPCancelVirfwOperationResponse.RESP_BODY);
        CancelVirFWServiceResp vfwResp = new CancelVirFWServiceResp();
        vfwResp.setResultCode(vfwDeleteResp.getResultCode());
        vfwResp.setResultDesc(vfwDeleteResp.getResultMessage());
        return vfwResp;
    }

    private CancelVirFWServiceResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        CancelVirFWServiceResp vfwResp = new CancelVirFWServiceResp();
        vfwResp.setResultCode(code.toString());
        vfwResp.setResultDesc(errorMessage);
        return vfwResp;
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
