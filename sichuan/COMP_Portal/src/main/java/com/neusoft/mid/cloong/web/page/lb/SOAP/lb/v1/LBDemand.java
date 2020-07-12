/**
 * LBDemand.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1;

public class LBDemand  implements java.io.Serializable {
    private int throughput;

    private int connectNum;

    private int newConnectNum;

    public LBDemand() {
    }

    public LBDemand(
           int throughput,
           int connectNum,
           int newConnectNum) {
           this.throughput = throughput;
           this.connectNum = connectNum;
           this.newConnectNum = newConnectNum;
    }


    /**
     * Gets the throughput value for this LBDemand.
     * 
     * @return throughput
     */
    public int getThroughput() {
        return throughput;
    }


    /**
     * Sets the throughput value for this LBDemand.
     * 
     * @param throughput
     */
    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }


    /**
     * Gets the connectNum value for this LBDemand.
     * 
     * @return connectNum
     */
    public int getConnectNum() {
        return connectNum;
    }


    /**
     * Sets the connectNum value for this LBDemand.
     * 
     * @param connectNum
     */
    public void setConnectNum(int connectNum) {
        this.connectNum = connectNum;
    }


    /**
     * Gets the newConnectNum value for this LBDemand.
     * 
     * @return newConnectNum
     */
    public int getNewConnectNum() {
        return newConnectNum;
    }


    /**
     * Sets the newConnectNum value for this LBDemand.
     * 
     * @param newConnectNum
     */
    public void setNewConnectNum(int newConnectNum) {
        this.newConnectNum = newConnectNum;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LBDemand)) return false;
        LBDemand other = (LBDemand) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.throughput == other.getThroughput() &&
            this.connectNum == other.getConnectNum() &&
            this.newConnectNum == other.getNewConnectNum();
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
        _hashCode += getThroughput();
        _hashCode += getConnectNum();
        _hashCode += getNewConnectNum();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LBDemand.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "LBDemand"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("throughput");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "Throughput"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("connectNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "ConnectNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newConnectNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/lb/v1/", "NewConnectNum"));
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
