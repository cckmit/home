/*******************************************************************************
 * @(#)VMManagerImpl.java 2013-1-11
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMManager;
import com.neusoft.mid.cloong.host.vm.core.VmOperatorReq;
import com.neusoft.mid.cloong.host.vm.core.VmOperatorResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMOperatorReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMOperatorResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMOperatorType;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机操作实现类
 * 
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-11 下午01:52:53
 */
public class VMManagerImpl implements VMManager, Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService
			.getLogger(VMManagerImpl.class);

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
	public VmOperatorResp operateVm(VmOperatorReq vmReq) {
		RuntimeContext reqCxt = new RuntimeContext();
		RPPVMOperatorReq req = new RPPVMOperatorReq();
		req.setResourcePoolId(vmReq.getResourcePoolId());
		req.setResourcePoolPartId(vmReq.getResourcePoolPartId());
		req.setSerialNum(vmReq.getSerialNum());
		req.setVmId(vmReq.getVmId());
		req.setVmOperatorType(VMOperatorType.fromValue(vmReq.getType()
				.getCode()));

		reqCxt.setAttribute(RPPVMOperatorReq.REQ_BODY, req);
		HttpSyncRespData respCxt = null;
		try {
			respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut,
					receiveTimeout);
		} catch (IOException e) {
			logger.error(CommonStatusCode.IO_OPTION_ERROR,
					"向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + vmReq.getSerialNum(), e);
			return assmbleErrorResp(CommonStatusCode.IO_OPTION_ERROR,
					"向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + vmReq.getSerialNum());
		} catch (InvalidProtocolException e) {
			logger.error(
					CommonStatusCode.INVALID_HTTP_PROTOCOL,
					"向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + vmReq.getSerialNum(),
					e);
			return assmbleErrorResp(CommonStatusCode.INVALID_HTTP_PROTOCOL,
					"向资源池代理系统发送请求失败，无效的http协议");
		} catch (Exception e) {
			logger.error(
					CommonStatusCode.RUNTIME_EXCEPTION,
					"向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为：" + vmReq.getSerialNum(),
					e);
			return assmbleErrorResp(CommonStatusCode.RUNTIME_EXCEPTION,
					"向资源池代理系统发送请求失败，自服务系统内部错误");
		}
		return assembleResp(respCxt);
	}

	private VmOperatorResp assembleResp(HttpSyncRespData resp) {
		RPPVMOperatorResp vmOperatorResp = (RPPVMOperatorResp) resp
				.getRuntimeContext().getAttribute(
						RPPVMOperatorResp.RESP_BODY);
		VmOperatorResp vmResp = new VmOperatorResp();
        vmResp.setResultCode(vmOperatorResp.getResultCode());
        vmResp.setResultMessage(vmOperatorResp.getResultMessage());
        return vmResp;
	}

	private VmOperatorResp assmbleErrorResp(CommonStatusCode code,
			String errorMessage) {
		VmOperatorResp vmResp = new VmOperatorResp();
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
