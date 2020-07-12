/*******************************************************************************
 * @(#)VMManagerImpl.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMDel;
import com.neusoft.mid.cloong.host.vm.core.VmDeleteReq;
import com.neusoft.mid.cloong.host.vm.core.VmDeleteResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMDeleteResp;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;


/**
 * 虚拟机删除实现类
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-13 下午04:20:13
 */
public class VMDelImpl implements VMDel , Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(VMManagerImpl.class);

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
    public VmDeleteResp delVm(VmDeleteReq vmReq) {
    	RuntimeContext reqCxt = new RuntimeContext();
    	RPPVMDeleteReq req = new RPPVMDeleteReq();
    	req.setResourcePoolId(vmReq.getResourcePoolId());
    	req.setResourcePoolPartId(vmReq.getResourcePoolPartId());
    	req.setSerialNum(vmReq.getSerialNum());
    	req.setVmId(vmReq.getVmId());
        reqCxt.setAttribute(RPPVMDeleteReq.REQ_BODY, req);
		HttpSyncRespData respCxt = null;
        try {
        	respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut, receiveTimeout);
        } catch (IOException e) {
            logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误，本次操作流水号为："
                    + vmReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR,
                    "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + vmReq.getSerialNum());
        } catch (InvalidProtocolException e) {
            logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + vmReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
                    "向资源池代理系统发送请求失败，无效的http协议");
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为："
                    + vmReq.getSerialNum(), e);
            return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误");
        }
        return assembleResp(respCxt);
    }

    private VmDeleteResp assembleResp(HttpSyncRespData resp) {
    	RPPVMDeleteResp vmDeleteResp = (RPPVMDeleteResp) resp
				.getRuntimeContext().getAttribute(
						RPPVMDeleteResp.RESP_BODY);
        VmDeleteResp vmResp = new VmDeleteResp();
        vmResp.setResultCode(vmDeleteResp.getResultCode());
        vmResp.setResultMessage(vmDeleteResp.getResultMessage());
        return vmResp;
    }

    private VmDeleteResp assmbleErrorResp(CommonStatusCode code, String errorMessage) {
        VmDeleteResp vmResp = new VmDeleteResp();
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
