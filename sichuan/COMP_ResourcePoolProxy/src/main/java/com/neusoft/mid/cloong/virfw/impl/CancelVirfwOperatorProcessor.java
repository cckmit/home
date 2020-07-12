package com.neusoft.mid.cloong.virfw.impl;

import javax.xml.ws.Holder;


import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPCancelVirfwOperationRequst;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPCancelVirfwOperationResponse;
import com.neusoft.mid.cloong.rpws.private1072.virfw.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.virfw.CancelVirFWServiceResp;
import com.neusoft.mid.cloong.rpws.private1072.virfw.CancelVirFwReq;
import com.neusoft.mid.cloong.rpws.private1072.virfw.Response;
import com.neusoft.mid.cloong.rpws.private1072.virfw.VirFwServicePort;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class CancelVirfwOperatorProcessor extends BaseProcessor {
	
	private static LogService logger = LogService.getLogger(CancelVirfwOperatorProcessor.class);
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
		logger.info("删除虚拟防火墙开始");
		RPPCancelVirfwOperationResponse vifwresp = new RPPCancelVirfwOperationResponse();
		RPPCancelVirfwOperationRequst virfwreq = (RPPCancelVirfwOperationRequst) req.getAttribute(RPPCancelVirfwOperationRequst.REQ_BODY);

		String virfwid = virfwreq.getVirfwid();
		logger.info("需要删除的虚拟防火墙ID:="+virfwid);
		
		CancelVirFWServiceResp cancelVirFWService = null;
		try {
			VirFwCreateReq virFwCreateReq = new VirFwCreateReq();

			virFwCreateReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
			virFwCreateReq.setTransactionID(this.getTransactonGen().generateTransactionId(virfwreq.getResourcePoolId(),
					virFwCreateReq.getTimestamp()));
			ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService()
					.match(virfwreq.getResourcePoolId());
			virFwCreateReq.setResourceUrl(resourceInfo.getResourcePoolUrl());

			VirFwServicePort client = common.openClient(virFwCreateReq, timeout, VirFwServicePort.class);

			Authorization simpleRequestHeader = new Authorization();
			simpleRequestHeader.setTimestamp(this.getTimeStampgen().generateTimeStamp());
			simpleRequestHeader.setTransactionID(this.getTransactonGen().generateTransactionId(virfwreq.getResourcePoolId(), this.getTimeStampgen().generateTimeStamp()));
			simpleRequestHeader.setZoneID(virfwreq.getResourcePoolPartId());
			Holder<Response> simpleResponseHeader = new Holder<Response>();

			CancelVirFwReq cancelVirFwReq = new CancelVirFwReq();
			cancelVirFwReq.setVirfwid(virfwid);
			
			cancelVirFWService = client.cancelVirFWService(cancelVirFwReq, simpleRequestHeader, simpleResponseHeader);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除虚拟防火墙异常:" + e.getMessage());
			vifwresp.setResultCode(cancelVirFWService.getResultCode());
			vifwresp.setResultMessage(cancelVirFWService.getResultDesc());
			resp.setAttribute(RPPCancelVirfwOperationResponse.RESP_BODY, vifwresp);
		}
		if ("0".equals(cancelVirFWService.getResultCode())) {
			logger.info("已经删除的防火墙id为: " + virfwid);
		}
		vifwresp.setResultCode(cancelVirFWService.getResultCode());
		vifwresp.setResultMessage(cancelVirFWService.getResultDesc());
		resp.setAttribute(RPPCancelVirfwOperationResponse.RESP_BODY, vifwresp);
		logger.info("删除虚拟防火墙结束");
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
