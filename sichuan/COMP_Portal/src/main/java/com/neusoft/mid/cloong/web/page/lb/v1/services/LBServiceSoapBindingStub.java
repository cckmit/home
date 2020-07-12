/**
 * LBServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.neusoft.mid.cloong.web.page.lb.v1.services;

public class LBServiceSoapBindingStub extends org.apache.axis.client.Stub implements com.neusoft.mid.cloong.web.page.lb.v1.interfaces.LBServicePort {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[5];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AddLoadBalanceObj");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "AddLoadBalanceObjReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">AddLoadBalanceObjReq"), com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">AddLoadBalanceObjResp"));
        oper.setReturnClass(com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "AddLoadBalanceObjResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ApplyLoadBalance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "ApplyLoadBalanceReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ApplyLoadBalanceReq"), com.neusoft.mid.cloong.web.page.lb.v1.local.ApplyLoadBalanceReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ApplyLoadBalanceResp"));
        oper.setReturnClass(com.neusoft.mid.cloong.web.page.lb.v1.local.ApplyLoadBalanceResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "ApplyLoadBalanceResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DelLoadBalanceObj");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "DelLoadBalanceObjReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">DelLoadBalanceObjReq"), com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">DelLoadBalanceObjResp"));
        oper.setReturnClass(com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "DelLoadBalanceObjResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ModifyLoadBalance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "ModifyLoadBalanceReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ModifyLoadBalanceReq"), com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ModifyLoadBalanceResp"));
        oper.setReturnClass(com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "ModifyLoadBalanceResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CancelLoadBalance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "CancelLoadBalanceReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">CancelLoadBalanceReq"), com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceReq.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleRequestHeader"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Authorization"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization.class, true, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response"), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class, false, true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">CancelLoadBalanceResp"));
        oper.setReturnClass(com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "CancelLoadBalanceResp"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

    }

    public LBServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public LBServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public LBServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            cls = com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/common/v1", "Response");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "IpPortInfo");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "IpPortInfoList");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "IpPortInfo");
            qName2 = new javax.xml.namespace.QName("", "IpPortInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "LBDemand");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.LBDemand.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">AddLoadBalanceObjReq");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">AddLoadBalanceObjResp");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ApplyLoadBalanceReq");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.ApplyLoadBalanceReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ApplyLoadBalanceResp");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.ApplyLoadBalanceResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">CancelLoadBalanceReq");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">CancelLoadBalanceResp");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">DelLoadBalanceObjReq");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">DelLoadBalanceObjResp");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ModifyLoadBalanceReq");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ModifyLoadBalanceResp");
            cachedSerQNames.add(qName);
            cls = com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceResp.class;
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

    public com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjResp addLoadBalanceObj(com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjReq parameters, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:AddLoadBalanceObj");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "AddLoadBalanceObj"));

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
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class);
            }
            try {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.neusoft.mid.cloong.web.page.lb.v1.local.ApplyLoadBalanceResp applyLoadBalance(com.neusoft.mid.cloong.web.page.lb.v1.local.ApplyLoadBalanceReq parameters, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:ApplyLoadBalance");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ApplyLoadBalance"));

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
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class);
            }
            try {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.ApplyLoadBalanceResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.ApplyLoadBalanceResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.neusoft.mid.cloong.web.page.lb.v1.local.ApplyLoadBalanceResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjResp delLoadBalanceObj(com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjReq parameters, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:DelLoadBalanceObj");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "DelLoadBalanceObj"));

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
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class);
            }
            try {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceResp modifyLoadBalance(com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceReq parameters, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:ModifyLoadBalance");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ModifyLoadBalance"));

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
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class);
            }
            try {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceResp cancelLoadBalance(com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceReq parameters, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization simpleRequestHeader, com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.holders.ResponseHolder simpleResponseHeader) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:CancelLoadBalance");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CancelLoadBalance"));

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
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) _output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader"));
            } catch (java.lang.Exception _exception) {
                simpleResponseHeader.value = (com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/common/v1/headers", "simpleResponseHeader")), com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Response.class);
            }
            try {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
