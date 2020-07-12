/**
 * ApplyLoadBalanceReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.neusoft.mid.cloong.web.page.lb.v1.local;

public class ApplyLoadBalanceReq  implements java.io.Serializable {
    private java.lang.String paramFlag;

    private java.lang.String protocal;

    private java.lang.String LBTemplateID;

    private com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.LBDemand LBDemand;

    private java.lang.String LBIP;

    private java.lang.String LBPort;

    private int LBStrategy;

    private java.lang.String appID;

    private java.lang.String appName;

    private java.lang.String LBName;

    public ApplyLoadBalanceReq() {
    }

    public ApplyLoadBalanceReq(
           java.lang.String paramFlag,
           java.lang.String protocal,
           java.lang.String LBTemplateID,
           com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.LBDemand LBDemand,
           java.lang.String LBIP,
           java.lang.String LBPort,
           int LBStrategy,
           java.lang.String appID,
           java.lang.String appName,
           java.lang.String LBName) {
           this.paramFlag = paramFlag;
           this.protocal = protocal;
           this.LBTemplateID = LBTemplateID;
           this.LBDemand = LBDemand;
           this.LBIP = LBIP;
           this.LBPort = LBPort;
           this.LBStrategy = LBStrategy;
           this.appID = appID;
           this.appName = appName;
           this.LBName = LBName;
    }


    /**
     * Gets the paramFlag value for this ApplyLoadBalanceReq.
     * 
     * @return paramFlag
     */
    public java.lang.String getParamFlag() {
        return paramFlag;
    }


    /**
     * Sets the paramFlag value for this ApplyLoadBalanceReq.
     * 
     * @param paramFlag
     */
    public void setParamFlag(java.lang.String paramFlag) {
        this.paramFlag = paramFlag;
    }


    /**
     * Gets the protocal value for this ApplyLoadBalanceReq.
     * 
     * @return protocal
     */
    public java.lang.String getProtocal() {
        return protocal;
    }


    /**
     * Sets the protocal value for this ApplyLoadBalanceReq.
     * 
     * @param protocal
     */
    public void setProtocal(java.lang.String protocal) {
        this.protocal = protocal;
    }


    /**
     * Gets the LBTemplateID value for this ApplyLoadBalanceReq.
     * 
     * @return LBTemplateID
     */
    public java.lang.String getLBTemplateID() {
        return LBTemplateID;
    }


    /**
     * Sets the LBTemplateID value for this ApplyLoadBalanceReq.
     * 
     * @param LBTemplateID
     */
    public void setLBTemplateID(java.lang.String LBTemplateID) {
        this.LBTemplateID = LBTemplateID;
    }


    /**
     * Gets the LBDemand value for this ApplyLoadBalanceReq.
     * 
     * @return LBDemand
     */
    public com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.LBDemand getLBDemand() {
        return LBDemand;
    }


    /**
     * Sets the LBDemand value for this ApplyLoadBalanceReq.
     * 
     * @param LBDemand
     */
    public void setLBDemand(com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.LBDemand LBDemand) {
        this.LBDemand = LBDemand;
    }


    /**
     * Gets the LBIP value for this ApplyLoadBalanceReq.
     * 
     * @return LBIP
     */
    public java.lang.String getLBIP() {
        return LBIP;
    }


    /**
     * Sets the LBIP value for this ApplyLoadBalanceReq.
     * 
     * @param LBIP
     */
    public void setLBIP(java.lang.String LBIP) {
        this.LBIP = LBIP;
    }


    /**
     * Gets the LBPort value for this ApplyLoadBalanceReq.
     * 
     * @return LBPort
     */
    public java.lang.String getLBPort() {
        return LBPort;
    }


    /**
     * Sets the LBPort value for this ApplyLoadBalanceReq.
     * 
     * @param LBPort
     */
    public void setLBPort(java.lang.String LBPort) {
        this.LBPort = LBPort;
    }


    /**
     * Gets the LBStrategy value for this ApplyLoadBalanceReq.
     * 
     * @return LBStrategy
     */
    public int getLBStrategy() {
        return LBStrategy;
    }


    /**
     * Sets the LBStrategy value for this ApplyLoadBalanceReq.
     * 
     * @param LBStrategy
     */
    public void setLBStrategy(int LBStrategy) {
        this.LBStrategy = LBStrategy;
    }


    /**
     * Gets the appID value for this ApplyLoadBalanceReq.
     * 
     * @return appID
     */
    public java.lang.String getAppID() {
        return appID;
    }


    /**
     * Sets the appID value for this ApplyLoadBalanceReq.
     * 
     * @param appID
     */
    public void setAppID(java.lang.String appID) {
        this.appID = appID;
    }


    /**
     * Gets the appName value for this ApplyLoadBalanceReq.
     * 
     * @return appName
     */
    public java.lang.String getAppName() {
        return appName;
    }


    /**
     * Sets the appName value for this ApplyLoadBalanceReq.
     * 
     * @param appName
     */
    public void setAppName(java.lang.String appName) {
        this.appName = appName;
    }


    /**
     * Gets the LBName value for this ApplyLoadBalanceReq.
     * 
     * @return LBName
     */
    public java.lang.String getLBName() {
        return LBName;
    }


    /**
     * Sets the LBName value for this ApplyLoadBalanceReq.
     * 
     * @param LBName
     */
    public void setLBName(java.lang.String LBName) {
        this.LBName = LBName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ApplyLoadBalanceReq)) return false;
        ApplyLoadBalanceReq other = (ApplyLoadBalanceReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.paramFlag==null && other.getParamFlag()==null) || 
             (this.paramFlag!=null &&
              this.paramFlag.equals(other.getParamFlag()))) &&
            ((this.protocal==null && other.getProtocal()==null) || 
             (this.protocal!=null &&
              this.protocal.equals(other.getProtocal()))) &&
            ((this.LBTemplateID==null && other.getLBTemplateID()==null) || 
             (this.LBTemplateID!=null &&
              this.LBTemplateID.equals(other.getLBTemplateID()))) &&
            ((this.LBDemand==null && other.getLBDemand()==null) || 
             (this.LBDemand!=null &&
              this.LBDemand.equals(other.getLBDemand()))) &&
            ((this.LBIP==null && other.getLBIP()==null) || 
             (this.LBIP!=null &&
              this.LBIP.equals(other.getLBIP()))) &&
            ((this.LBPort==null && other.getLBPort()==null) || 
             (this.LBPort!=null &&
              this.LBPort.equals(other.getLBPort()))) &&
            this.LBStrategy == other.getLBStrategy() &&
            ((this.appID==null && other.getAppID()==null) || 
             (this.appID!=null &&
              this.appID.equals(other.getAppID()))) &&
            ((this.appName==null && other.getAppName()==null) || 
             (this.appName!=null &&
              this.appName.equals(other.getAppName()))) &&
            ((this.LBName==null && other.getLBName()==null) || 
             (this.LBName!=null &&
              this.LBName.equals(other.getLBName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getParamFlag() != null) {
            _hashCode += getParamFlag().hashCode();
        }
        if (getProtocal() != null) {
            _hashCode += getProtocal().hashCode();
        }
        if (getLBTemplateID() != null) {
            _hashCode += getLBTemplateID().hashCode();
        }
        if (getLBDemand() != null) {
            _hashCode += getLBDemand().hashCode();
        }
        if (getLBIP() != null) {
            _hashCode += getLBIP().hashCode();
        }
        if (getLBPort() != null) {
            _hashCode += getLBPort().hashCode();
        }
        _hashCode += getLBStrategy();
        if (getAppID() != null) {
            _hashCode += getAppID().hashCode();
        }
        if (getAppName() != null) {
            _hashCode += getAppName().hashCode();
        }
        if (getLBName() != null) {
            _hashCode += getLBName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ApplyLoadBalanceReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ApplyLoadBalanceReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paramFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "ParamFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "Protocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBTemplateID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBTemplateID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBDemand");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBDemand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "LBDemand"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBIP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBPort");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBPort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBStrategy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBStrategy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "AppID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "AppName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
