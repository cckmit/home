/**
 * FileStorageParamArray.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1;

public class FileStorageParamArray  implements java.io.Serializable {
    private int quotaSize;

    private int sharetype;

    public FileStorageParamArray() {
    }

    public FileStorageParamArray(
           int quotaSize,
           int sharetype) {
           this.quotaSize = quotaSize;
           this.sharetype = sharetype;
    }


    /**
     * Gets the quotaSize value for this FileStorageParamArray.
     * 
     * @return quotaSize
     */
    public int getQuotaSize() {
        return quotaSize;
    }


    /**
     * Sets the quotaSize value for this FileStorageParamArray.
     * 
     * @param quotaSize
     */
    public void setQuotaSize(int quotaSize) {
        this.quotaSize = quotaSize;
    }


    /**
     * Gets the sharetype value for this FileStorageParamArray.
     * 
     * @return sharetype
     */
    public int getSharetype() {
        return sharetype;
    }


    /**
     * Sets the sharetype value for this FileStorageParamArray.
     * 
     * @param sharetype
     */
    public void setSharetype(int sharetype) {
        this.sharetype = sharetype;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FileStorageParamArray)) return false;
        FileStorageParamArray other = (FileStorageParamArray) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.quotaSize == other.getQuotaSize() &&
            this.sharetype == other.getSharetype();
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
        _hashCode += getQuotaSize();
        _hashCode += getSharetype();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FileStorageParamArray.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageParamArray"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quotaSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "QuotaSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sharetype");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "Sharetype"));
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
