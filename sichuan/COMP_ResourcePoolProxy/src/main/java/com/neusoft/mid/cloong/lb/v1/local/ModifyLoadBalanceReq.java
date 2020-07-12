/**
 * ModifyLoadBalanceReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.neusoft.mid.cloong.lb.v1.local;

public class ModifyLoadBalanceReq  implements java.io.Serializable {
    private java.lang.String LBID;

    private int LBStrategy;

    public ModifyLoadBalanceReq() {
    }

    public ModifyLoadBalanceReq(
           java.lang.String LBID,
           int LBStrategy) {
           this.LBID = LBID;
           this.LBStrategy = LBStrategy;
    }


    /**
     * Gets the LBID value for this ModifyLoadBalanceReq.
     * 
     * @return LBID
     */
    public java.lang.String getLBID() {
        return LBID;
    }


    /**
     * Sets the LBID value for this ModifyLoadBalanceReq.
     * 
     * @param LBID
     */
    public void setLBID(java.lang.String LBID) {
        this.LBID = LBID;
    }


    /**
     * Gets the LBStrategy value for this ModifyLoadBalanceReq.
     * 
     * @return LBStrategy
     */
    public int getLBStrategy() {
        return LBStrategy;
    }


    /**
     * Sets the LBStrategy value for this ModifyLoadBalanceReq.
     * 
     * @param LBStrategy
     */
    public void setLBStrategy(int LBStrategy) {
        this.LBStrategy = LBStrategy;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ModifyLoadBalanceReq)) return false;
        ModifyLoadBalanceReq other = (ModifyLoadBalanceReq) obj;
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
            this.LBStrategy == other.getLBStrategy();
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
        _hashCode += getLBStrategy();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ModifyLoadBalanceReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", ">ModifyLoadBalanceReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LBStrategy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/lb/v1/local", "LBStrategy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
