/**
 * QueryFileStorageResp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local;

public class QueryFileStorageResp  implements java.io.Serializable {
    private com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorage[] fileStorageList;

    private java.lang.String faultString;

    public QueryFileStorageResp() {
    }

    public QueryFileStorageResp(
           com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorage[] fileStorageList,
           java.lang.String faultString) {
           this.fileStorageList = fileStorageList;
           this.faultString = faultString;
    }


    /**
     * Gets the fileStorageList value for this QueryFileStorageResp.
     * 
     * @return fileStorageList
     */
    public com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorage[] getFileStorageList() {
        return fileStorageList;
    }


    /**
     * Sets the fileStorageList value for this QueryFileStorageResp.
     * 
     * @param fileStorageList
     */
    public void setFileStorageList(com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorage[] fileStorageList) {
        this.fileStorageList = fileStorageList;
    }


    /**
     * Gets the faultString value for this QueryFileStorageResp.
     * 
     * @return faultString
     */
    public java.lang.String getFaultString() {
        return faultString;
    }


    /**
     * Sets the faultString value for this QueryFileStorageResp.
     * 
     * @param faultString
     */
    public void setFaultString(java.lang.String faultString) {
        this.faultString = faultString;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryFileStorageResp)) return false;
        QueryFileStorageResp other = (QueryFileStorageResp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fileStorageList==null && other.getFileStorageList()==null) || 
             (this.fileStorageList!=null &&
              java.util.Arrays.equals(this.fileStorageList, other.getFileStorageList()))) &&
            ((this.faultString==null && other.getFaultString()==null) || 
             (this.faultString!=null &&
              this.faultString.equals(other.getFaultString())));
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
        if (getFileStorageList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileStorageList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileStorageList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFaultString() != null) {
            _hashCode += getFaultString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryFileStorageResp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageResp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileStorageList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorage"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "FileStorage"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faultString");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FaultString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
