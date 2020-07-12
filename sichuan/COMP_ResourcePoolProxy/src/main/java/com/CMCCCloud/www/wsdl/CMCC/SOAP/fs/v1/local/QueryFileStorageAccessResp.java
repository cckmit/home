/**
 * QueryFileStorageAccessResp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local;

public class QueryFileStorageAccessResp  implements java.io.Serializable {
    private com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl[] fileRequestAcl;

    private java.lang.String faultString;

    public QueryFileStorageAccessResp() {
    }

    public QueryFileStorageAccessResp(
           com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl[] fileRequestAcl,
           java.lang.String faultString) {
           this.fileRequestAcl = fileRequestAcl;
           this.faultString = faultString;
    }


    /**
     * Gets the fileRequestAcl value for this QueryFileStorageAccessResp.
     * 
     * @return fileRequestAcl
     */
    public com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl[] getFileRequestAcl() {
        return fileRequestAcl;
    }


    /**
     * Sets the fileRequestAcl value for this QueryFileStorageAccessResp.
     * 
     * @param fileRequestAcl
     */
    public void setFileRequestAcl(com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl[] fileRequestAcl) {
        this.fileRequestAcl = fileRequestAcl;
    }


    /**
     * Gets the faultString value for this QueryFileStorageAccessResp.
     * 
     * @return faultString
     */
    public java.lang.String getFaultString() {
        return faultString;
    }


    /**
     * Sets the faultString value for this QueryFileStorageAccessResp.
     * 
     * @param faultString
     */
    public void setFaultString(java.lang.String faultString) {
        this.faultString = faultString;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryFileStorageAccessResp)) return false;
        QueryFileStorageAccessResp other = (QueryFileStorageAccessResp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fileRequestAcl==null && other.getFileRequestAcl()==null) || 
             (this.fileRequestAcl!=null &&
              java.util.Arrays.equals(this.fileRequestAcl, other.getFileRequestAcl()))) &&
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
        if (getFileRequestAcl() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileRequestAcl());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileRequestAcl(), i);
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
        new org.apache.axis.description.TypeDesc(QueryFileStorageAccessResp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">QueryFileStorageAccessResp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileRequestAcl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileRequestAcl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "Acl"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "Acl"));
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
