/**
 * 
 */
package com.neusoft.mid.comp.boss.server.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.neusoft.mid.comp.boss.server.ws.msg.OrderResource.CancelCorporationInfoReq;
import com.neusoft.mid.comp.boss.server.ws.msg.OrderResource.OrderResourceInfoReq;
import com.neusoft.mid.comp.boss.server.ws.msg.OrderResource.ResourceCreditControlInfoReq;
import com.neusoft.mid.comp.boss.server.ws.msg.OrderResource.ResourceInfoResp;

/**
 * @author li-lei
 *
 */
@WebService(targetNamespace = "http://223.87.178.253/COMP_Portal/portal.wsdl", name = "portal")
@SOAPBinding(use = SOAPBinding.Use.ENCODED, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface OrderResource {

	@WebResult(name = "ResourceInfoResp", targetNamespace = "http://223.87.178.253/COMP_Portal/schemas/", partName = "ResourceInfoRespOutput")
	@WebMethod(operationName = "OrderResource", action = "portal.OrderResource")
	ResourceInfoResp orderResource(
			@WebParam(partName = "OrderResourceInfoReqInput", name = "OrderResourceInfoReq", targetNamespace = "http://223.87.178.253/COMP_Portal/schemas/") OrderResourceInfoReq req);

	@WebResult(name = "ResourceInfoResp", targetNamespace = "http://223.87.178.253/COMP_Portal/schemas/", partName = "ResourceInfoRespOutput")
	@WebMethod(operationName = "ResourceCreditControl", action = "portal.ResourceCreditControl")
	ResourceInfoResp resourceCreditControl(
			@WebParam(partName = "ResourceCreditControlInfoReqInput", name = "ResourceCreditControlInfoReq", targetNamespace = "http://223.87.178.253/COMP_Portal/schemas/") ResourceCreditControlInfoReq req);
	
	@WebResult(name = "ResourceInfoResp", targetNamespace = "http://223.87.178.253/COMP_Portal/schemas/", partName = "ResourceInfoRespOutput")
	@WebMethod(operationName = "CancelCorporation", action = "portal.CancelCorporation")
	ResourceInfoResp cancelCorporation(
			@WebParam(partName = "CancelCorporationInfoReqInput", name = "CancelCorporationInfoReq", targetNamespace = "http://223.87.178.253/COMP_Portal/schemas/") CancelCorporationInfoReq req);

}
