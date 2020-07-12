package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces;

public class FSServicePortProxy implements com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces.FSServicePort {
  private String _endpoint = null;
  private com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces.FSServicePort fSServicePort = null;
  
  public FSServicePortProxy() {
    _initFSServicePortProxy();
  }
  
  public FSServicePortProxy(String endpoint) {
    _endpoint = endpoint;
    _initFSServicePortProxy();
  }
  
  private void _initFSServicePortProxy() {
    try {
      fSServicePort = (new com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.services.FSServiceLocator()).getFSServicePort();
      if (fSServicePort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)fSServicePort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)fSServicePort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (fSServicePort != null)
      ((javax.xml.rpc.Stub)fSServicePort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces.FSServicePort getFSServicePort() {
    if (fSServicePort == null)
      _initFSServicePortProxy();
    return fSServicePort;
  }
  
  public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageResp queryFileStorage(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException{
    if (fSServicePort == null)
      _initFSServicePortProxy();
    return fSServicePort.queryFileStorage(parameters, simpleRequestHeader, simpleResponseHeader);
  }
  
  public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeResp updateFileStorageSize(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException{
    if (fSServicePort == null)
      _initFSServicePortProxy();
    return fSServicePort.updateFileStorageSize(parameters, simpleRequestHeader, simpleResponseHeader);
  }
  
  public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessResp setFileStorageAccess(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException{
    if (fSServicePort == null)
      _initFSServicePortProxy();
    return fSServicePort.setFileStorageAccess(parameters, simpleRequestHeader, simpleResponseHeader);
  }
  
  public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageResp deleteFileStorage(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException{
    if (fSServicePort == null)
      _initFSServicePortProxy();
    return fSServicePort.deleteFileStorage(parameters, simpleRequestHeader, simpleResponseHeader);
  }
  
  public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessResp queryFileStorageAccess(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException{
    if (fSServicePort == null)
      _initFSServicePortProxy();
    return fSServicePort.queryFileStorageAccess(parameters, simpleRequestHeader, simpleResponseHeader);
  }
  
  public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageResp creatFileStorage(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException{
    if (fSServicePort == null)
      _initFSServicePortProxy();
    return fSServicePort.creatFileStorage(parameters, simpleRequestHeader, simpleResponseHeader);
  }
  
  
}