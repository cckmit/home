package com.neusoft.mid.cloong.virfw.impl;

import javax.xml.ws.Holder;


import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPDelVirfwSrategyOperationResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPDelVirfwStrategyOperationRequst;
import com.neusoft.mid.cloong.rpws.private1072.virfw.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.virfw.DeleteVirFWStrategyResp;
import com.neusoft.mid.cloong.rpws.private1072.virfw.DeleteVirFwStrategyReq;
import com.neusoft.mid.cloong.rpws.private1072.virfw.Response;
import com.neusoft.mid.cloong.rpws.private1072.virfw.VirFwServicePort;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class DelVirfwStrategyOperatorProcessor extends BaseProcessor {

	private static LogService logger = LogService.getLogger(DelVirfwStrategyOperatorProcessor.class);
	
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
		logger.info("删除虚拟防火墙策略开始");
		RPPDelVirfwSrategyOperationResponse virfwresp = new RPPDelVirfwSrategyOperationResponse();
		RPPDelVirfwStrategyOperationRequst virfwreq = (RPPDelVirfwStrategyOperationRequst) req.getAttribute(RPPDelVirfwStrategyOperationRequst.REQ_BODY);
		
		String fwStrategyID = virfwreq.getFWStrategyID();
		logger.info("需要删除的虚拟防火墙策略ID:="+fwStrategyID);
		
		DeleteVirFWStrategyResp deleteVirFWStrategy = null;
		try {
			VirFwCreateReq virFwCreateReq = new VirFwCreateReq();
			ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService()
					.match(virfwreq.getResourcePoolId());
			virFwCreateReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
			VirFwServicePort vfwservice = common.openClient(virFwCreateReq, timeout, VirFwServicePort.class);
			
			Authorization simpleRequestHeader = new Authorization();
			simpleRequestHeader.setTimestamp(this.getTimeStampgen().generateTimeStamp());
			simpleRequestHeader.setTransactionID(this.getTransactonGen().generateTransactionId(virfwreq.getResourcePoolId(), this.getTimeStampgen().generateTimeStamp()));
			simpleRequestHeader.setZoneID(virfwreq.getResourcePoolPartId());
			Holder<Response> simpleResponseHeader = new Holder<Response>();
			DeleteVirFwStrategyReq parameters = new DeleteVirFwStrategyReq();
			parameters.setFWStrategyID(fwStrategyID);
			deleteVirFWStrategy = vfwservice.deleteVirFWStrategy(parameters , simpleRequestHeader, simpleResponseHeader);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除虚拟防火墙异策略常:" + e.getMessage());
			virfwresp.setResultCode(deleteVirFWStrategy.getResultCode());
			virfwresp.setResultMessage(deleteVirFWStrategy.getResultDesc());
			resp.setAttribute(RPPDelVirfwSrategyOperationResponse.RESP_BODY, virfwresp);
		}
		
		if ("0".equals(deleteVirFWStrategy.getResultCode())) {
			logger.info("已经删除的防火墙策略id为: " + fwStrategyID);
		}
		virfwresp.setResultCode(deleteVirFWStrategy.getResultCode());
		virfwresp.setResultMessage(deleteVirFWStrategy.getResultDesc());
		resp.setAttribute(RPPDelVirfwSrategyOperationResponse.RESP_BODY, virfwresp);
		logger.info("删除虚拟防火墙策略结束");
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
