/**
 * CreatFileStorageResp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local;

public class CreatFileStorageResp  implements java.io.Serializable {
    private java.lang.String fileStorageID;

    private java.lang.String FSUrl;

    private java.lang.String faultString;

    public CreatFileStorageResp() {
    }

    public CreatFileStorageResp(
           java.lang.String fileStorageID,
           java.lang.String FSUrl,
           java.lang.String faultString) {
           this.fileStorageID = fileStorageID;
           this.FSUrl = FSUrl;
           this.faultString = faultString;
    }


    /**
     * Gets the fileStorageID value for this CreatFileStorageResp.
     * 
     * @return fileStorageID
     */
    public java.lang.String getFileStorageID() {
        return fileStorageID;
    }


    /**
     * Sets the fileStorageID value for this CreatFileStorageResp.
     * 
     * @param fileStorageID
     */
    public void setFileStorageID(java.lang.String fileStorageID) {
        this.fileStorageID = fileStorageID;
    }


    /**
     * Gets the FSUrl value for this CreatFileStorageResp.
     * 
     * @return FSUrl
     */
    public java.lang.String getFSUrl() {
        return FSUrl;
    }


    /**
     * Sets the FSUrl value for this CreatFileStorageResp.
     * 
     * @param FSUrl
     */
    public void setFSUrl(java.lang.String FSUrl) {
        this.FSUrl = FSUrl;
    }


    /**
     * Gets the faultString value for this CreatFileStorageResp.
     * 
     * @return faultString
     */
    public java.lang.String getFaultString() {
        return faultString;
    }


    /**
     * Sets the faultString value for this CreatFileStorageResp.
     * 
     * @param faultString
     */
    public void setFaultString(java.lang.String faultString) {
        this.faultString = faultString;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreatFileStorageResp)) return false;
        CreatFileStorageResp other = (CreatFileStorageResp) obj;
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
            ((this.FSUrl==null && other.getFSUrl()==null) || 
             (this.FSUrl!=null &&
              this.FSUrl.equals(other.getFSUrl()))) &&
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
        if (getFileStorageID() != null) {
            _hashCode += getFileStorageID().hashCode();
        }
        if (getFSUrl() != null) {
            _hashCode += getFSUrl().hashCode();
        }
        if (getFaultString() != null) {
            _hashCode += getFaultString().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreatFileStorageResp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">CreatFileStorageResp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileStorageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FSUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FSUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
