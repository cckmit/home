/**
 * 
 */
package com.neusoft.mid.cloong.virfw.ipml;

import java.io.IOException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationRequst;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationResponse;
import com.neusoft.mid.cloong.virfw.CreateVirFW;
import com.neusoft.mid.cloong.virfw.bean.CreateVirFWReq;
import com.neusoft.mid.cloong.virfw.bean.CreateVirFWResponse;
import com.neusoft.mid.grains.modules.http.api.HttpSyncRespData;
import com.neusoft.mid.grains.modules.http.api.HttpSyncSendHelper;
import com.neusoft.mid.grains.modules.http.api.InvalidProtocolException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author Administrator
 *
 */
public class CreateVirFWIpml implements CreateVirFW {
	private static LogService logger = LogService.getLogger(CreateVirFWIpml.class);

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
	 * 
	 * @see
	 * com.neusoft.mid.cloong.virfw.CreateVirFW#createVirFw(com.neusoft.mid.cloong.
	 * virfw.bean.CreateVirFWReq)
	 */
	@Override
	public RPPApplyVirfwOperationResponse createVirFw(RPPApplyVirfwOperationRequst req) {

		RPPApplyVirfwOperationResponse createResp = new RPPApplyVirfwOperationResponse();
		try {
			if (logger.isDebugEnable()) {
				logger.debug("创建端口映射信息接口开始组装");
			}
			logger.info("createPortMap 开始");
			RuntimeContext reqCxt = new RuntimeContext();
			reqCxt.setAttribute(RPPApplyVirfwOperationRequst.REQ_BODY, req);
			HttpSyncRespData respCxt = senderEntry.sendHttpRequest(reqCxt, url, httpTimeOut,receiveTimeout);
			createResp = (RPPApplyVirfwOperationResponse) respCxt.getRuntimeContext().getAttribute(RPPApplyVirfwOperationResponse.RESP_BODY);
			logger.info("createPortMap 结束");
			return createResp;
		} catch (IOException e) {
			logger.error(CommonStatusCode.IO_OPTION_ERROR, "向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum(), e);
			createResp = new RPPApplyVirfwOperationResponse();
			createResp.setResultMessage("向资源池代理系统发送请求失败，IO错误，本次操作流水号为：" + req.getSerialNum());
			createResp.setResultCode(CommonStatusCode.IO_OPTION_ERROR.toString());
			return createResp;
		} catch (InvalidProtocolException e) {
			logger.error(CommonStatusCode.INVALID_HTTP_PROTOCOL,
					"向资源池代理系统发送请求失败，无效的http协议，本次操作流水号为：" + req.getSerialNum(), e);
			createResp = new RPPApplyVirfwOperationResponse();
			createResp.setResultMessage("向资源池代理系统发送请求失败，无效的http协议");
			createResp.setResultCode(CommonStatusCode.INVALID_HTTP_PROTOCOL.toString());
			return createResp;
		} catch (Exception e) {
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "向资源池代理系统发送请求失败，自服务系统内部错误，本次操作流水号为：" + req.getSerialNum(), e);
			createResp = new RPPApplyVirfwOperationResponse();
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
