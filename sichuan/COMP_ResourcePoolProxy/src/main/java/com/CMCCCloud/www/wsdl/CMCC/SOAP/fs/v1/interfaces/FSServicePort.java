/**
 * FSServicePort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces;

public interface FSServicePort extends java.rmi.Remote {
    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageResp queryFileStorage(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeResp updateFileStorageSize(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessResp setFileStorageAccess(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageResp deleteFileStorage(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessResp queryFileStorageAccess(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageResp creatFileStorage(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException;
}
