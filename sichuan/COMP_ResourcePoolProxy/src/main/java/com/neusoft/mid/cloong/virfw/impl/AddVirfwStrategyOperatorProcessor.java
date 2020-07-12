package com.neusoft.mid.cloong.virfw.impl;

import javax.xml.ws.Holder;


import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPAddVirfwSrategyOperationResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPAddVirfwStrategyOperationRequst;
import com.neusoft.mid.cloong.rpws.private1072.virfw.AddVirFWStrategyReq;
import com.neusoft.mid.cloong.rpws.private1072.virfw.AddVirFWStrategyResp;
import com.neusoft.mid.cloong.rpws.private1072.virfw.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.virfw.Response;
import com.neusoft.mid.cloong.rpws.private1072.virfw.VirFwServicePort;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class AddVirfwStrategyOperatorProcessor extends BaseProcessor {
	private static LogService logger = LogService.getLogger(AddVirfwStrategyOperatorProcessor.class);

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
		logger.info("添加虚拟防火墙策略开始");
		RPPAddVirfwSrategyOperationResponse virfwresp = new RPPAddVirfwSrategyOperationResponse();
		RPPAddVirfwStrategyOperationRequst virfwreq = (RPPAddVirfwStrategyOperationRequst) req
				.getAttribute(RPPAddVirfwStrategyOperationRequst.REQ_BODY);

		logger.info("防火墙id:=" + virfwreq.getFWID());
		
		AddVirFWStrategyResp addVirFWSResp = null;
		try {
			VirFwCreateReq virFwCreateReq = new VirFwCreateReq();
			ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService()
					.match(virfwreq.getResourcePoolId());
			virFwCreateReq.setResourceUrl(resourceInfo.getResourcePoolUrl());

			VirFwServicePort vfwservice = common.openClient(virFwCreateReq, timeout, VirFwServicePort.class);
			
			AddVirFWStrategyReq fwsreq = new AddVirFWStrategyReq();
			fwsreq.setActType(virfwreq.getActType());
			fwsreq.setDestinationIP(virfwreq.getDestinationIP());
			fwsreq.setDestinationPort(virfwreq.getDestinationPort());
			fwsreq.setFWID(virfwreq.getFWID());
			fwsreq.setProtocol(virfwreq.getProtocol());
			fwsreq.setSourceIP(virfwreq.getSourceIP());
			fwsreq.setSourcePort(virfwreq.getSourcePort());

			Authorization simpleRequestHeader = new Authorization();
			simpleRequestHeader.setTimestamp(this.getTimeStampgen().generateTimeStamp());
			simpleRequestHeader.setTransactionID(this.getTransactonGen().generateTransactionId(virfwreq.getResourcePoolId(), this.getTimeStampgen().generateTimeStamp()));
			simpleRequestHeader.setZoneID(virfwreq.getResourcePoolPartId());
			Holder<Response> simpleResponseHeader = new Holder<Response>();
			
			addVirFWSResp = vfwservice.addVirFWStrategy(fwsreq, simpleRequestHeader, simpleResponseHeader);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("添加虚拟防火墙异策略常:" + e.getMessage());
			virfwresp.setResultCode(addVirFWSResp.getResultCode());
			virfwresp.setResultMessage(addVirFWSResp.getResultDesc());
			resp.setAttribute(RPPAddVirfwSrategyOperationResponse.RESP_BODY, virfwresp);
		}
		if ("0".equals(addVirFWSResp.getResultCode())) {
			logger.info("防火墙id为: " + virfwreq.getFWID() + " 的防火墙策略 成功, 策略id为:=" + addVirFWSResp.getResultDesc());
		}
		virfwresp.setResultCode(addVirFWSResp.getResultCode());
		virfwresp.setResultMessage(addVirFWSResp.getResultDesc());
		resp.setAttribute(RPPAddVirfwSrategyOperationResponse.RESP_BODY, virfwresp);
		logger.info("添加虚拟防火墙策略结束");
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
