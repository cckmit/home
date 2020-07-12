package com.neusoft.mid.cloong.portmap.impl;

import java.io.IOException;
import java.io.Serializable;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.portmap.CreatePortMap;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.DeletePortMapRequest;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.PortMapRequest;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.PortMapResponse;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class CreatePortMapImpi implements CreatePortMap,Serializable {

	private static LogService logger = LogService.getLogger(CreatePortMapImpi.class);

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
	 * 天剑端口映射URL
	 */
	private String createPortMapUrl;

	/**
	 * 删除端口映射URL
	 */
	private String deletePortMapUrl;

	@Override
	public PortMapResponse createPortMap(PortMapRequest req) {
		PortMapResponse createResp = null;
		try {
			if (logger.isDebugEnable()) {
				logger.debug("创建端口映射信息接口开始组装");
			}
			logger.info("createPortMap 开始 :=" +createPortMapUrl);
			RuntimeContext reqCxt = new RuntimeContext();
			reqCxt.setAttribute(PortMapRequest.REQ_BODY, req);
			HttpSyncRespData respCxt = senderEntry.sendHttpRequest(reqCxt, createPortMapUrl, httpTimeOut,receiveTimeout);
			createResp = (PortMapResponse) respCxt.getRuntimeContext().getAttribute(PortMapResponse.RESP_BODY);
			logger.info("createPortMap 结束");
			return createResp;
		} catch (IOException e) {
			logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum(), e);
			createResp = new PortMapResponse();
			createResp.setResultMessage("向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum());
			createResp.setResultCode(CommonStatusCode.IO_OPTION_ERROR.toString());
			return createResp;
		} catch (InvalidProtocolException e) {
			logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
					"向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + req.getSerialNum(), e);
			createResp = new PortMapResponse();
			createResp.setResultMessage("向资源池代理系统发送请求失败，无效的http协议");
			createResp.setResultCode(CommonStatusCode.INVALID_HTTP_PROTOCOL.toString());
			return createResp;
		} catch (Exception e) {
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为：" + req.getSerialNum(), e);
			createResp = new PortMapResponse();
			createResp.setResultMessage("向资源池代理系统发送请求失败，自服务系统内部错误");
			createResp.setResultCode(CommonStatusCode.RUNTIME_EXCEPTION.toString());
			return createResp;
		}
	}

	@Override
	public PortMapResponse deletePortMap(DeletePortMapRequest req) {
		PortMapResponse createResp = null;
		try {
			if (logger.isDebugEnable()) {
				logger.debug("删除端口映射信息接口开始组装");
			}
			logger.info("deletePortMap 开始:=" + deletePortMapUrl);
			RuntimeContext reqCxt = new RuntimeContext();
			reqCxt.setAttribute(DeletePortMapRequest.REQ_BODY, req);
			HttpSyncRespData respCxt = senderEntry.sendHttpRequest(reqCxt, deletePortMapUrl, httpTimeOut,receiveTimeout);
			createResp = (PortMapResponse) respCxt.getRuntimeContext().getAttribute(PortMapResponse.RESP_BODY);
			logger.info("deletePortMap 结束");
			return createResp;
		} catch (IOException e) {
			logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum(), e);
			createResp = new PortMapResponse();
			createResp.setResultMessage("向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum());
			createResp.setResultCode(CommonStatusCode.IO_OPTION_ERROR.toString());
			return createResp;
		} catch (InvalidProtocolException e) {
			logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
					"向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + req.getSerialNum(), e);
			createResp = new PortMapResponse();
			createResp.setResultMessage("向资源池代理系统发送请求失败，无效的http协议");
			createResp.setResultCode(CommonStatusCode.INVALID_HTTP_PROTOCOL.toString());
			return createResp;
		} catch (Exception e) {
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为：" + req.getSerialNum(), e);
			createResp = new PortMapResponse();
			createResp.setResultMessage("向资源池代理系统发送请求失败，自服务系统内部错误");
			createResp.setResultCode(CommonStatusCode.RUNTIME_EXCEPTION.toString());
			return createResp;
		}
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

	public String getCreatePortMapUrl() {
		return createPortMapUrl;
	}

	public void setCreatePortMapUrl(String createPortMapUrl) {
		this.createPortMapUrl = createPortMapUrl;
	}

	public String getDeletePortMapUrl() {
		return deletePortMapUrl;
	}

	public void setDeletePortMapUrl(String deletePortMapUrl) {
		this.deletePortMapUrl = deletePortMapUrl;
	}

}
