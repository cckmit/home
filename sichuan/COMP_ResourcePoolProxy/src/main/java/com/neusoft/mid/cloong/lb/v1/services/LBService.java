/**
 * LBService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.neusoft.mid.cloong.lb.v1.services;

public interface LBService extends javax.xml.rpc.Service {
    public java.lang.String getLBServicePortAddress();

    public com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePort getLBServicePort() throws javax.xml.rpc.ServiceException;

    public com.neusoft.mid.cloong.lb.v1.interfaces.LBServicePort getLBServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
