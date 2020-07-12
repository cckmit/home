package com.neusoft.mid.cloong.virfw.impl;


import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationRequst;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationResponse;
import com.neusoft.mid.cloong.rpws.private1072.virfw.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.virfw.CreateVirFWReq;
import com.neusoft.mid.cloong.rpws.private1072.virfw.CreateVirFwResponse;
import com.neusoft.mid.cloong.rpws.private1072.virfw.Response;
import com.neusoft.mid.cloong.rpws.private1072.virfw.VirFwServicePort;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class ApplyVirfwOperatorProcessor extends BaseProcessor {

	private static LogService logger = LogService.getLogger(ApplyVirfwOperatorProcessor.class);
	
	/**
	 * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
	 */
	private WebServiceClientFactory<VirFwServicePort> common;
	/**
	 * 接收超时时间ms
	 */
	private long timeout = 10000;

	@Override
	public String process(RuntimeContext req, RuntimeContext resp) {
		logger.info("申请虚拟防火墙开始");
		RPPApplyVirfwOperationResponse virfwresp = new RPPApplyVirfwOperationResponse();
		RPPApplyVirfwOperationRequst virfwreq = (RPPApplyVirfwOperationRequst) req.getAttribute(RPPBaseReq.REQ_BODY);

		logger.info("添加防火墙名称:" + virfwreq.getFwName());
		CreateVirFwResponse createVirFWService = null;
		try {
			VirFwCreateReq virFwCreateReq = new VirFwCreateReq();
			ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService()
					.match(virfwreq.getResourcePoolId());
			virFwCreateReq.setResourceUrl(resourceInfo.getResourcePoolUrl());


			VirFwServicePort client = common.openClient(virFwCreateReq, timeout, VirFwServicePort.class);

			Authorization simpleRequestHeader = new Authorization();
			simpleRequestHeader.setTimestamp(this.getTimeStampgen().generateTimeStamp());
			simpleRequestHeader.setTransactionID(this.getTransactonGen().generateTransactionId(virfwreq.getResourcePoolId(), this.getTimeStampgen().generateTimeStamp()));
			simpleRequestHeader.setZoneID(virfwreq.getResourcePoolPartId());
			Holder<Response> simpleResponseHeader = new Holder<Response>();

			CreateVirFWReq virFWReq = new CreateVirFWReq();
			virFWReq.setAppID(virfwreq.getAppID());
			virFWReq.setAppName(virfwreq.getAppName());
			virFWReq.setFWName(virfwreq.getFwName());

			createVirFWService = client.createVirFWService(virFWReq, simpleRequestHeader, simpleResponseHeader);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("申请虚拟防火墙异常:" + e.getMessage());
			virfwresp.setResultCode(createVirFWService.getResultCode());
			virfwresp.setResultMessage(createVirFWService.getResultDesc());
			resp.setAttribute(RPPApplyVirfwOperationResponse.RESP_BODY, virfwresp);
		}

		if ("0".equals(createVirFWService.getResultCode())) {
			logger.info("申请虚拟防火墙成功，防火墙 名称为: " + createVirFWService.getResultDesc());
		}
		virfwresp.setResultCode(createVirFWService.getResultCode());
		virfwresp.setResultMessage(createVirFWService.getResultDesc());
		resp.setAttribute(RPPApplyVirfwOperationResponse.RESP_BODY, virfwresp);
		logger.info("申请虚拟防火墙结束");
		return SUCCESS;
	}

	public WebServiceClientFactory<VirFwServicePort> getCommon() {
		return common;
	}

	public void setCommon(WebServiceClientFactory<VirFwServicePort> common) {
		this.common = common;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

}
