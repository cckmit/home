/**
 * LBServicePort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.neusoft.mid.cloong.lb.v1.interfaces;

public interface LBServicePort extends java.rmi.Remote {
    public com.neusoft.mid.cloong.lb.v1.local.AddLoadBalanceObjResp addLoadBalanceObj(com.neusoft.mid.cloong.lb.v1.local.AddLoadBalanceObjReq parameters, com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
    public com.neusoft.mid.cloong.lb.v1.local.ApplyLoadBalanceResp applyLoadBalance(com.neusoft.mid.cloong.lb.v1.local.ApplyLoadBalanceReq parameters, com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
    public com.neusoft.mid.cloong.lb.v1.local.DelLoadBalanceObjResp delLoadBalanceObj(com.neusoft.mid.cloong.lb.v1.local.DelLoadBalanceObjReq parameters, com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
    public com.neusoft.mid.cloong.lb.v1.local.ModifyLoadBalanceResp modifyLoadBalance(com.neusoft.mid.cloong.lb.v1.local.ModifyLoadBalanceReq parameters, com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
    public com.neusoft.mid.cloong.lb.v1.local.CancelLoadBalanceResp cancelLoadBalance(com.neusoft.mid.cloong.lb.v1.local.CancelLoadBalanceReq parameters, com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
}
