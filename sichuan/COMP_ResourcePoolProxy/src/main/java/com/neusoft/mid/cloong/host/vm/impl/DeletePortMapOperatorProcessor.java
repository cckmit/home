package com.neusoft.mid.cloong.host.vm.impl;


import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.portmap.webservice.DeletePortMap;
import com.neusoft.mid.cloong.portmap.webservice.DeletePortMapReq;
import com.neusoft.mid.cloong.portmap.webservice.DeletePortMapResponse;
import com.neusoft.mid.cloong.portmap.webservice.PortMapService;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.DelPortMapOperationRequest;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.DeletePortMapRequest;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.PortMapResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPPortMapOperationResponse;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class DeletePortMapOperatorProcessor extends BaseProcessor {
	private static LogService logger = LogService.getLogger(DeletePortMapOperatorProcessor.class);
	private String Url;

	private GetPortMapService getPortMapService;

	@Override
	public String process(RuntimeContext reqs, RuntimeContext resp) {
		PortMapResponse pomaResp = new PortMapResponse();
		DeletePortMapRequest delreq = (DeletePortMapRequest) reqs
				.getAttribute(DeletePortMapRequest.REQ_BODY);

		logger.info("delreq:=" + delreq);
		logger.info("delreq.getPortMapId():=" + delreq.getPortMapId());
		DeletePortMapReq vfwReq = new DeletePortMapReq();
		vfwReq.setFlag("1");
		vfwReq.setId(delreq.getPortMapId());
		
		DeletePortMapResponse deletePortMap = null;
		try {
			PortMapService portMapService = getPortMapService.getPortMapService(Url);
			DeletePortMap parameters = new DeletePortMap();
			parameters.setVfwReq(vfwReq);
			deletePortMap = portMapService.deletePortMap(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除端口映射异常:"+e.getLocalizedMessage());
			pomaResp.setResultCode("1");
			pomaResp.setResultMessage(e.getLocalizedMessage());
		}
		logger.info("deletePortMap:=" + deletePortMap);
		logger.info("deletePortMap.getResultCode():=" + deletePortMap.getPortMapResp().getResultCode());
		logger.info("deletePortMap.getResultDesc():=" + deletePortMap.getPortMapResp().getResultDesc());
		pomaResp.setResultCode(deletePortMap.getPortMapResp().getResultCode());
		pomaResp.setResultMessage(deletePortMap.getPortMapResp().getResultDesc());
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
