/**
 * FSServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.services;

public class FSServiceSoapBindingStub extends org.apache.axis.client.Stub implements com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces.FSServicePort {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[6];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryFileStorage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "QueryFileStorageReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageReq"), com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageResp"));
        oper.setReturnClass(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "QueryFileStorageResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UpdateFileStorageSize");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "UpdateFileStorageSizeReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">UpdateFileStorageSizeReq"), com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">UpdateFileStorageSizeResp"));
        oper.setReturnClass(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "UpdateFileStorageSizeResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SetFileStorageAccess");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "SetFileStorageAccessReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">SetFileStorageAccessReq"), com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">SetFileStorageAccessResp"));
        oper.setReturnClass(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "SetFileStorageAccessResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DeleteFileStorage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "DeleteFileStorageReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">DeleteFileStorageReq"), com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">DeleteFileStorageResp"));
        oper.setReturnClass(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "DeleteFileStorageResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryFileStorageAccess");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "QueryFileStorageAccessReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageAccessReq"), com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageAccessResp"));
        oper.setReturnClass(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "QueryFileStorageAccessResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CreatFileStorage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "CreatFileStorageReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">CreatFileStorageReq"), com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">CreatFileStorageResp"));
        oper.setReturnClass(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "CreatFileStorageResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

    }

    public FSServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public FSServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public FSServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "Acl");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "AclList");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "Acl");
            qName2 = new javax.xml.namespace.QName("", "Acl");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorage");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageIDList");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("", "FileStorageID");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageList");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorage[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorage");
            qName2 = new javax.xml.namespace.QName("", "FileStorage");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageParamArray");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorageParamArray.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">CreatFileStorageReq");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">CreatFileStorageResp");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">DeleteFileStorageReq");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">DeleteFileStorageResp");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageAccessReq");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageAccessResp");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageReq");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageResp");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">SetFileStorageAccessReq");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">SetFileStorageAccessResp");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">UpdateFileStorageSizeReq");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">UpdateFileStorageSizeResp");
            cachedSerQNames.add(qName);
            cls = com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageResp queryFileStorage(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:QueryFileStorage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "QueryFileStorage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, simpleRequestHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class);
            }
            try {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeResp updateFileStorageSize(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:UpdateFileStorageSize");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "UpdateFileStorageSize"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, simpleRequestHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class);
            }
            try {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.UpdateFileStorageSizeResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessResp setFileStorageAccess(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:SetFileStorageAccess");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "SetFileStorageAccess"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, simpleRequestHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class);
            }
            try {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.SetFileStorageAccessResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageResp deleteFileStorage(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:DeleteFileStorage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "DeleteFileStorage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, simpleRequestHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class);
            }
            try {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessResp queryFileStorageAccess(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:QueryFileStorageAccess");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "QueryFileStorageAccess"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, simpleRequestHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class);
            }
            try {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.QueryFileStorageAccessResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageResp creatFileStorage(com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageReq parameters, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization simpleRequestHeader, com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:CreatFileStorage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CreatFileStorage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters, simpleRequestHeader});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Response.class);
            }
            try {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
