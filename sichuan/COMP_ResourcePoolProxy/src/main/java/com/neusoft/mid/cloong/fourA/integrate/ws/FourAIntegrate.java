package com.neusoft.mid.cloong.fourA.integrate.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace = "http://soap.cloud.integrate.security/", name = "FourAIntegrate")
@SOAPBinding(style = Style.RPC)   // xfire调用, 这地方需要使用这种模式
public interface FourAIntegrate {
    
	@WebMethod
	@RequestWrapper(localName = "findUser", targetNamespace = "http://soap.cloud.integrate.security/")
    @ResponseWrapper(localName = "findUserResponse", targetNamespace = "http://soap.cloud.integrate.security/")
	@WebResult(name = "return", targetNamespace = "")
    public String findUser(
        @WebParam(name = "userID", targetNamespace = "") String userID);

	
    @WebMethod
    @RequestWrapper(localName = "addUserInfo", targetNamespace = "http://soap.cloud.integrate.security/")
    @ResponseWrapper(localName = "addUserInfoResponse", targetNamespace = "http://soap.cloud.integrate.security/")
    @WebResult(name = "return", targetNamespace = "")
    public String addUserInfo(
        @WebParam(name = "userInfos", targetNamespace = "") String userInfos);

    @WebMethod
    @RequestWrapper(localName = "delUser", targetNamespace = "http://soap.cloud.integrate.security/")
    @ResponseWrapper(localName = "delUserResponse", targetNamespace = "http://soap.cloud.integrate.security/")
    @WebResult(name = "return", targetNamespace = "")
    public String delUser(
        @WebParam(name = "userIDs", targetNamespace = "") String userIDs);

    @WebMethod
    @RequestWrapper(localName = "modifyUserInfo", targetNamespace = "http://soap.cloud.integrate.security/")
    @ResponseWrapper(localName = "modifyUserInfoResponse", targetNamespace = "http://soap.cloud.integrate.security/")
    @WebResult(name = "return", targetNamespace = "")
    public String modifyUserInfo(
        @WebParam(name = "userInfos", targetNamespace = "") String userInfos);

    @WebMethod
    @RequestWrapper(localName = "queryUsers", targetNamespace = "http://soap.cloud.integrate.security/")
    @ResponseWrapper(localName = "queryUsersResponse", targetNamespace = "http://soap.cloud.integrate.security/")
    @WebResult(name = "return", targetNamespace = "")
    public String queryUsers();
    
}
