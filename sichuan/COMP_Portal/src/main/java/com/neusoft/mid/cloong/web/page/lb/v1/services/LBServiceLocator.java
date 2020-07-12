/**
 * LBServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.neusoft.mid.cloong.web.page.lb.v1.services;

public class LBServiceLocator extends org.apache.axis.client.Service implements com.neusoft.mid.cloong.web.page.lb.v1.services.LBService {

    public LBServiceLocator() {
    }


    public LBServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LBServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LBServicePort	
    private java.lang.String LBServicePort_address = "http://10.10.105.139:18500/private_cloud_rs/loadbalance";

    public java.lang.String getLBServicePortAddress() {
        return LBServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LBServicePortWSDDServiceName = "LBServicePort";

    public java.lang.String getLBServicePortWSDDServiceName() {
        return LBServicePortWSDDServiceName;
    }

    public void setLBServicePortWSDDServiceName(java.lang.String name) {
        LBServicePortWSDDServiceName = name;
    }

    public com.neusoft.mid.cloong.web.page.lb.v1.interfaces.LBServicePort getLBServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LBServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLBServicePort(endpoint);
    }

    public com.neusoft.mid.cloong.web.page.lb.v1.interfaces.LBServicePort getLBServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.neusoft.mid.cloong.web.page.lb.v1.services.LBServiceSoapBindingStub _stub = new com.neusoft.mid.cloong.web.page.lb.v1.services.LBServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getLBServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLBServicePortEndpointAddress(java.lang.String address) {
        LBServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.neusoft.mid.cloong.web.page.lb.v1.interfaces.LBServicePort.class.isAssignableFrom(serviceEndpointInterface)) {
                com.neusoft.mid.cloong.web.page.lb.v1.services.LBServiceSoapBindingStub _stub = new com.neusoft.mid.cloong.web.page.lb.v1.services.LBServiceSoapBindingStub(new java.net.URL(LBServicePort_address), this);
                _stub.setPortName(getLBServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("LBServicePort".equals(inputPortName)) {
            return getLBServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/services", "LBService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/services", "LBServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LBServicePort".equals(portName)) {
            setLBServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
