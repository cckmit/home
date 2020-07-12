/**
 * UpdateFileStorageSizeReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local;

public class UpdateFileStorageSizeReq  implements java.io.Serializable {
    private java.lang.String fileStorageID;

    private int quotaSize;

    public UpdateFileStorageSizeReq() {
    }

    public UpdateFileStorageSizeReq(
           java.lang.String fileStorageID,
           int quotaSize) {
           this.fileStorageID = fileStorageID;
           this.quotaSize = quotaSize;
    }


    /**
     * Gets the fileStorageID value for this UpdateFileStorageSizeReq.
     * 
     * @return fileStorageID
     */
    public java.lang.String getFileStorageID() {
        return fileStorageID;
    }


    /**
     * Sets the fileStorageID value for this UpdateFileStorageSizeReq.
     * 
     * @param fileStorageID
     */
    public void setFileStorageID(java.lang.String fileStorageID) {
        this.fileStorageID = fileStorageID;
    }


    /**
     * Gets the quotaSize value for this UpdateFileStorageSizeReq.
     * 
     * @return quotaSize
     */
    public int getQuotaSize() {
        return quotaSize;
    }


    /**
     * Sets the quotaSize value for this UpdateFileStorageSizeReq.
     * 
     * @param quotaSize
     */
    public void setQuotaSize(int quotaSize) {
        this.quotaSize = quotaSize;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateFileStorageSizeReq)) return false;
        UpdateFileStorageSizeReq other = (UpdateFileStorageSizeReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fileStorageID==null && other.getFileStorageID()==null) || 
             (this.fileStorageID!=null &&
              this.fileStorageID.equals(other.getFileStorageID()))) &&
            this.quotaSize == other.getQuotaSize();
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
        if (getFileStorageID() != null) {
            _hashCode += getFileStorageID().hashCode();
        }
        _hashCode += getQuotaSize();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateFileStorageSizeReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">UpdateFileStorageSizeReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileStorageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quotaSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "QuotaSize"));
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
