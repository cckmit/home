package com.neusoft.mid.cloong.lb.impl;

import java.net.URL;
import java.util.List;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.lb.LBCreate;
import com.neusoft.mid.cloong.lb.LBCreateReq;
import com.neusoft.mid.cloong.lb.LBDemandReq;
import com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder;
import com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePortProxy;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpproxy.interfaces.lb.RPPLBCreateResp;
import com.neusoft.mid.cloong.rpws.private1072.lb.ApplyLoadBalanceReq;
import com.neusoft.mid.cloong.rpws.private1072.lb.ApplyLoadBalanceResp;
import com.neusoft.mid.cloong.rpws.private1072.lb.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.lb.LBDemand;
import com.neusoft.mid.cloong.rpws.private1072.lb.LBServicePort;
import com.neusoft.mid.cloong.rpws.private1072.lb.Response;
import com.neusoft.mid.cloong.rpws.private1072.vm.VMServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池发送创建虚拟机操作请求
 */
public class LBCreateImpl implements LBCreate {
	/**
	 * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
	 */
	// private WebServiceClientFactory<LBServicePort> common;
	/**
	 * 接收超时时间ms
	 */
	private long timeout = 10000;

	private IbatisDAO ibatisDAO;

	private static LogService logger = LogService.getLogger(LBCreateImpl.class);
	
	private String njrsLbUrl;

	private String njrsLbUrlTwo;

	public RPPLBCreateResp createLB(LBCreateReq req) {
		RPPLBCreateResp rppResp = new RPPLBCreateResp();
		try {
			URL url = new URL(njrsLbUrl);
			URL urlTwo = new URL(njrsLbUrlTwo);
			com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePortProxy lbServicePortProxy = new com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePortProxy();
			if(req.getResourcePoolPartId().equals("CPC-Z-01-SVI-002")){
				lbServicePortProxy = new com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePortProxy(urlTwo);
			}else {
				lbServicePortProxy = new com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePortProxy(url);
			}
			com.neusoft.mid.cloong.lb.v1.local.ApplyLoadBalanceReq applyLoadBalanceReq = new com.neusoft.mid.cloong.lb.v1.local.ApplyLoadBalanceReq();
			com.neusoft.mid.cloong.lb.SOAP.lb.v1.LBDemand lbreq = new com.neusoft.mid.cloong.lb.SOAP.lb.v1.LBDemand();
			lbreq.setConnectNum(req.getLbDemandReq().getConnectNum());
			lbreq.setNewConnectNum(req.getLbDemandReq().getNewConnectNum());
			lbreq.setThroughput(req.getLbDemandReq().getThroughput());

			applyLoadBalanceReq.setLBDemand(lbreq);
			applyLoadBalanceReq.setLBTemplateID(req.getLbTemplateID());
			applyLoadBalanceReq.setAppID(req.getAppID());
			applyLoadBalanceReq.setAppName(req.getAppName());
			applyLoadBalanceReq.setLBIP(req.getLbip());
			applyLoadBalanceReq.setParamFlag(req.getParamFlag());
			applyLoadBalanceReq.setLBName(req.getLbName());
			applyLoadBalanceReq.setLBPort(req.getLbPort());
			applyLoadBalanceReq.setProtocal(req.getProtocal());

			com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization header = new com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization();
			header.setTimestamp(req.getTimestamp());
			header.setTransactionID(req.getTransactionID());
			header.setZoneID(req.getResourcePoolPartId());
			ResponseHolder resp = new ResponseHolder();
			
			com.neusoft.mid.cloong.lb.v1.local.ApplyLoadBalanceResp rp = lbServicePortProxy.applyLoadBalance(applyLoadBalanceReq, header, resp);
			// 组装响应
			assembleResp(rppResp, rp, resp);

		} catch (Exception e) {
			if ("authentication failed!".equals(e.getMessage())) {
				logger.error(CommonStatusCode.AUTH_ERROR, "资源池认证失败", e);
				return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
			}
			logger.error(CommonStatusCode.SENDMSG_ERROR, "向资源池发送消息异常", e);
			return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_SENDMSG_ERROR);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("向资源池发送请求成功，获取的响应码为[").append(rppResp.getResultCode()).append("]，响应描述为[")
				.append(rppResp.getResultMessage()).append("]");
		logger.info(sb.toString());
		return rppResp;
	}

	/**
	 * @param req
	 *            请求内容
	 * @param createLBReq
	 *            要生成的webservice请求Bean
	 * @return 处理结果,如果为null,说明请求成功
	 */
	private RPPLBCreateResp genWSReq(LBCreateReq req, ApplyLoadBalanceReq createLBReq) {
		createLBReq.setAppID(req.getAppID());
		createLBReq.setParamFlag(req.getParamFlag());
		createLBReq.setProtocal(req.getProtocal());
		createLBReq.setLBIP(req.getLbip());
		createLBReq.setAppName(req.getAppName());
		createLBReq.setLBName(req.getLbName());
		LBDemand lbD = new LBDemand();
		lbD.setConnectNum(req.getLbDemandReq().getConnectNum());
		lbD.setNewConnectNum(req.getLbDemandReq().getNewConnectNum());
		lbD.setThroughput(req.getLbDemandReq().getThroughput());
		createLBReq.setLBDemand(lbD);
		return null;
	}

	/**
	 * 将webservice应答报文转换为内部应答报文
	 */
	private void assembleResp(RPPLBCreateResp rppResp,
			com.neusoft.mid.cloong.lb.v1.local.ApplyLoadBalanceResp webserviceRespBody,
			ResponseHolder webserviceRespHeader) {
		rppResp.setFaultstring(webserviceRespBody.getFaultString());
		rppResp.setLbId(webserviceRespBody.getLBID());
		rppResp.setResultCode(webserviceRespHeader.value.getResultCode());
	}

	/**
	 * 生成反映异常的应答bean
	 * 
	 * @param code
	 *            错误码
	 * @return 生成的异常应答
	 */
	private RPPLBCreateResp assmbleErrorResp(InterfaceResultCode code) {
		RPPLBCreateResp resp = new RPPLBCreateResp();
		resp.setResultCode(code.getResultCode());
		resp.setResultMessage(code.getResultMessage());
		return resp;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public IbatisDAO getIbatisDAO() {
		return ibatisDAO;
	}

	public void setIbatisDAO(IbatisDAO ibatisDAO) {
		this.ibatisDAO = ibatisDAO;
	}

	public String getNjrsLbUrl() {
		return njrsLbUrl;
	}

	public void setNjrsLbUrl(String njrsLbUrl) {
		this.njrsLbUrl = njrsLbUrl;
	}

	public String getNjrsLbUrlTwo() {
		return njrsLbUrlTwo;
	}

	public void setNjrsLbUrlTwo(String njrsLbUrlTwo) {
		this.njrsLbUrlTwo = njrsLbUrlTwo;
	}
}
