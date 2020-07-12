package com.neusoft.mid.cloong.host.vm.impl;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.portmap.webservice.CreatePortMap;
import com.neusoft.mid.cloong.portmap.webservice.CreatePortMapReq;
import com.neusoft.mid.cloong.portmap.webservice.CreatePortMapResponse;
import com.neusoft.mid.cloong.portmap.webservice.FwQuintuple;
import com.neusoft.mid.cloong.portmap.webservice.PortMapService;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.PortMapRequest;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.PortMapResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPPortMapOperationResponse;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class PortMapOperatorProcessor extends BaseProcessor {
	private static LogService logger = LogService.getLogger(PortMapOperatorProcessor.class);
	
	private String Url ;
	
	private GetPortMapService getPortMapService;
	
	@Override
	public String process(RuntimeContext reqs, RuntimeContext resp) {
		
		PortMapResponse pomaResp = new PortMapResponse();		
		PortMapRequest req = (PortMapRequest) reqs.getAttribute(PortMapRequest.REQ_BODY);
		
		
		CreatePortMapReq vfwReq = new CreatePortMapReq();
		logger.info("req.getDestinationIp():="+req.getVmPrivateIp()+",req.getDestinationPort():="+req.getVmPort()+",req.getSourceIp():="+req.getPublicIp()+",req.getSourcePort():="+req.getPublicPort()+
				",req.getProtocol():="+req.getProtocol()+",req.getVmid():="+req.getVmId()+",req.getFlag():="+req.getMappingMode());
		FwQuintuple fw = new FwQuintuple();
		fw.setDestinationIP(req.getVmPrivateIp());
		fw.setDestinationPort(req.getVmPort());
		fw.setSourceIP(req.getPublicIp());
		fw.setSourcePort(req.getPublicPort());
		fw.setProtocol(req.getProtocol());
		
		vfwReq.setFWQuintuples(fw);
		vfwReq.setId(req.getVmId()); 
		vfwReq.setFlag(req.getMappingMode());
		
		CreatePortMapResponse createPortMap = null;
		try {
			PortMapService portMapService = getPortMapService.getPortMapService(Url);
			CreatePortMap parameters = new CreatePortMap();
			parameters.setVfwReq(vfwReq);
			createPortMap = portMapService.createPortMap(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("添加端口映射异常:"+e.getLocalizedMessage());
			pomaResp.setResultCode("1");
			pomaResp.setResultMessage(e.getLocalizedMessage());
		}
		logger.info("createPortMap:="+createPortMap);
		logger.info("createPortMap.getResultCode():="+createPortMap.getPortMapResp().getResultCode());
		logger.info("createPortMap.getResultDesc():="+createPortMap.getPortMapResp().getResultDesc());
		pomaResp.setResultCode(createPortMap.getPortMapResp().getResultCode());
		pomaResp.setResultMessage(createPortMap.getPortMapResp().getResultDesc());
		resp.setAttribute(RPPPortMapOperationResponse.RESP_BODY, pomaResp);
		return SUCCESS;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public GetPortMapService getGetPortMapService() {
		return getPortMapService;
	}

	public void setGetPortMapService(GetPortMapService getPortMapService) {
		this.getPortMapService = getPortMapService;
	}

}
