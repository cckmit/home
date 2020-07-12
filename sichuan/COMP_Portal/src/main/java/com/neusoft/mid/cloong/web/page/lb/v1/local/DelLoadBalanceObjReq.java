/**
 * DelLoadBalanceObjReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.neusoft.mid.cloong.web.page.lb.v1.local;

public class DelLoadBalanceObjReq  implements java.io.Serializable {
    private java.lang.String LBID;

    private com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo[] iPs;

    public DelLoadBalanceObjReq() {
    }

    public DelLoadBalanceObjReq(
           java.lang.String LBID,
           com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo[] iPs) {
           this.LBID = LBID;
           this.iPs = iPs;
    }


    /**
     * Gets the LBID value for this DelLoadBalanceObjReq.
     * 
     * @return LBID
     */
    public java.lang.String getLBID() {
        return LBID;
    }


    /**
     * Sets the LBID value for this DelLoadBalanceObjReq.
     * 
     * @param LBID
     */
    public void setLBID(java.lang.String LBID) {
        this.LBID = LBID;
    }


    /**
     * Gets the iPs value for this DelLoadBalanceObjReq.
     * 
     * @return iPs
     */
    public com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo[] getIPs() {
        return iPs;
    }


    /**
     * Sets the iPs value for this DelLoadBalanceObjReq.
     * 
     * @param iPs
     */
    public void setIPs(com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo[] iPs) {
        this.iPs = iPs;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DelLoadBalanceObjReq)) return false;
        DelLoadBalanceObjReq other = (DelLoadBalanceObjReq) obj;
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
            ((this.iPs==null && other.getIPs()==null) || 
             (this.iPs!=null &&
              java.util.Arrays.equals(this.iPs, other.getIPs())));
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
        if (getIPs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIPs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIPs(), i);
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
        new org.apache.axis.description.TypeDesc(DelLoadBalanceObjReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">DelLoadBalanceObjReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "iPs"));
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
