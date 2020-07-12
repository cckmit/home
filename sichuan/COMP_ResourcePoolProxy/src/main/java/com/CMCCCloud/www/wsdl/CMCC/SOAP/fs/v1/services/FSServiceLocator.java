/**
 * FSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.services;

public class FSServiceLocator extends org.apache.axis.client.Service implements com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.services.FSService {

    public FSServiceLocator() {
    }


    public FSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FSServicePort
    private java.lang.String FSServicePort_address = "http://10.10.127.167:8081/private_cloud_rs/filestorage";

    public java.lang.String getFSServicePortAddress() {
        return FSServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FSServicePortWSDDServiceName = "FSServicePort";

    public java.lang.String getFSServicePortWSDDServiceName() {
        return FSServicePortWSDDServiceName;
    }

    public void setFSServicePortWSDDServiceName(java.lang.String name) {
        FSServicePortWSDDServiceName = name;
    }

    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces.FSServicePort getFSServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FSServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFSServicePort(endpoint);
    }

    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces.FSServicePort getFSServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.services.FSServiceSoapBindingStub _stub = new com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.services.FSServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getFSServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFSServicePortEndpointAddress(java.lang.String address) {
        FSServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces.FSServicePort.class.isAssignableFrom(serviceEndpointInterface)) {
                com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.services.FSServiceSoapBindingStub _stub = new com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.services.FSServiceSoapBindingStub(new java.net.URL(FSServicePort_address), this);
                _stub.setPortName(getFSServicePortWSDDServiceName());
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
        if ("FSServicePort".equals(inputPortName)) {
            return getFSServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/services", "FSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/services", "FSServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FSServicePort".equals(portName)) {
            setFSServicePortEndpointAddress(address);
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
