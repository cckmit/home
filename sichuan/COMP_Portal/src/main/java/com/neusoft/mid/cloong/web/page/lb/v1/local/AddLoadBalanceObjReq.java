/**
 * AddLoadBalanceObjReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.neusoft.mid.cloong.web.page.lb.v1.local;

public class AddLoadBalanceObjReq  implements java.io.Serializable {
    private java.lang.String LBID;

    private com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo[] ipPortInfo;

    public AddLoadBalanceObjReq() {
    }

    public AddLoadBalanceObjReq(
           java.lang.String LBID,
           com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo[] ipPortInfo) {
           this.LBID = LBID;
           this.ipPortInfo = ipPortInfo;
    }


    /**
     * Gets the LBID value for this AddLoadBalanceObjReq.
     * 
     * @return LBID
     */
    public java.lang.String getLBID() {
        return LBID;
    }


    /**
     * Sets the LBID value for this AddLoadBalanceObjReq.
     * 
     * @param LBID
     */
    public void setLBID(java.lang.String LBID) {
        this.LBID = LBID;
    }


    /**
     * Gets the ipPortInfo value for this AddLoadBalanceObjReq.
     * 
     * @return ipPortInfo
     */
    public com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo[] getIpPortInfo() {
        return ipPortInfo;
    }


    /**
     * Sets the ipPortInfo value for this AddLoadBalanceObjReq.
     * 
     * @param ipPortInfo
     */
    public void setIpPortInfo(com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo[] ipPortInfo) {
        this.ipPortInfo = ipPortInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddLoadBalanceObjReq)) return false;
        AddLoadBalanceObjReq other = (AddLoadBalanceObjReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.LBID==null && other.getLBID()==null) || 
             (this.LBID!=null &&
              this.LBID.equals(other.getLBID()))) &&
            ((this.ipPortInfo==null && other.getIpPortInfo()==null) || 
             (this.ipPortInfo!=null &&
              java.util.Arrays.equals(this.ipPortInfo, other.getIpPortInfo())));
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
        if (getLBID() != null) {
            _hashCode += getLBID().hashCode();
        }
        if (getIpPortInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIpPortInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIpPortInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddLoadBalanceObjReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">AddLoadBalanceObjReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipPortInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "IpPortInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "IpPortInfo"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "IpPortInfo"));
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
