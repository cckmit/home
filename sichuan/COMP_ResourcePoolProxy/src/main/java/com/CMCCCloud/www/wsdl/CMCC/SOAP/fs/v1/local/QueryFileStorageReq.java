/**
 * QueryFileStorageReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local;

public class QueryFileStorageReq  implements java.io.Serializable {
    private java.lang.String[] fileStorageIDs;

    public QueryFileStorageReq() {
    }

    public QueryFileStorageReq(
           java.lang.String[] fileStorageIDs) {
           this.fileStorageIDs = fileStorageIDs;
    }


    /**
     * Gets the fileStorageIDs value for this QueryFileStorageReq.
     * 
     * @return fileStorageIDs
     */
    public java.lang.String[] getFileStorageIDs() {
        return fileStorageIDs;
    }


    /**
     * Sets the fileStorageIDs value for this QueryFileStorageReq.
     * 
     * @param fileStorageIDs
     */
    public void setFileStorageIDs(java.lang.String[] fileStorageIDs) {
        this.fileStorageIDs = fileStorageIDs;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryFileStorageReq)) return false;
        QueryFileStorageReq other = (QueryFileStorageReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fileStorageIDs==null && other.getFileStorageIDs()==null) || 
             (this.fileStorageIDs!=null &&
              java.util.Arrays.equals(this.fileStorageIDs, other.getFileStorageIDs())));
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
        if (getFileStorageIDs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileStorageIDs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileStorageIDs(), i);
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
        new org.apache.axis.description.TypeDesc(QueryFileStorageReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageIDs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileStorageIDs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "FileStorageID"));
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
