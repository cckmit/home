package com.neusoft.mid.cloong.lb.v1.interfaces;

import java.net.URL;

public class LBServicePortProxy implements com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePort {
	private String _endpoint = null;
	private com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePort lBServicePort = null;

	public LBServicePortProxy() {
		_initLBServicePortProxy();
	}

	public LBServicePortProxy(String endpoint) {
		_endpoint = endpoint;
		_initLBServicePortProxy();
	}

	public LBServicePortProxy(URL url) {
		_initLBServicePortProxy(url);
	}

	private void _initLBServicePortProxy() {
		try {
			lBServicePort = (new com.neusoft.mid.cloong.lb.v1.services.LBServiceLocator()).getLBServicePort();
			if (lBServicePort != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) lBServicePort)._setProperty("javax.xml.rpc.service.endpoint.address",
							_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) lBServicePort)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	private void _initLBServicePortProxy(URL url) {
		try {
			lBServicePort = (new com.neusoft.mid.cloong.lb.v1.services.LBServiceLocator()).getLBServicePort(url);
			if (lBServicePort != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) lBServicePort)._setProperty("javax.xml.rpc.service.endpoint.address",
							_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) lBServicePort)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (lBServicePort != null)
			((javax.xml.rpc.Stub) lBServicePort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePort getLBServicePort() {
		if (lBServicePort == null)
			_initLBServicePortProxy();
		return lBServicePort;
	}

	public com.neusoft.mid.cloong.lb.v1.local.AddLoadBalanceObjResp addLoadBalanceObj(
			com.neusoft.mid.cloong.lb.v1.local.AddLoadBalanceObjReq parameters,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader)
			throws java.rmi.RemoteException {
		if (lBServicePort == null)
			_initLBServicePortProxy();
		return lBServicePort.addLoadBalanceObj(parameters, simpleRequestHeader, simpleResponseHeader);
	}

	public com.neusoft.mid.cloong.lb.v1.local.ApplyLoadBalanceResp applyLoadBalance(
			com.neusoft.mid.cloong.lb.v1.local.ApplyLoadBalanceReq parameters,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader)
			throws java.rmi.RemoteException {
		if (lBServicePort == null)
			_initLBServicePortProxy();
		return lBServicePort.applyLoadBalance(parameters, simpleRequestHeader, simpleResponseHeader);
	}

	public com.neusoft.mid.cloong.lb.v1.local.DelLoadBalanceObjResp delLoadBalanceObj(
			com.neusoft.mid.cloong.lb.v1.local.DelLoadBalanceObjReq parameters,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader)
			throws java.rmi.RemoteException {
		if (lBServicePort == null)
			_initLBServicePortProxy();
		return lBServicePort.delLoadBalanceObj(parameters, simpleRequestHeader, simpleResponseHeader);
	}

	public com.neusoft.mid.cloong.lb.v1.local.ModifyLoadBalanceResp modifyLoadBalance(
			com.neusoft.mid.cloong.lb.v1.local.ModifyLoadBalanceReq parameters,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader)
			throws java.rmi.RemoteException {
		if (lBServicePort == null)
			_initLBServicePortProxy();
		return lBServicePort.modifyLoadBalance(parameters, simpleRequestHeader, simpleResponseHeader);
	}

	public com.neusoft.mid.cloong.lb.v1.local.CancelLoadBalanceResp cancelLoadBalance(
			com.neusoft.mid.cloong.lb.v1.local.CancelLoadBalanceReq parameters,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.Authorization simpleRequestHeader,
			com.neusoft.mid.cloong.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader)
			throws java.rmi.RemoteException {
		if (lBServicePort == null)
			_initLBServicePortProxy();
		return lBServicePort.cancelLoadBalance(parameters, simpleRequestHeader, simpleResponseHeader);
	}
}