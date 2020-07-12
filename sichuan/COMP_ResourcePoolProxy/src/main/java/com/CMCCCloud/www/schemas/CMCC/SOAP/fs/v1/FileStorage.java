/**
 * FileStorage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1;

public class FileStorage  implements java.io.Serializable {
    private java.lang.String fileStorageID;

    private java.lang.String fileStorageName;

    private int fileStorageSize;

    private int fileStorageUsed;

    private int fileStorageState;

    private java.lang.String fileStorageUrl;

    private java.lang.String fileStorageServerIP;

    public FileStorage() {
    }

    public FileStorage(
           java.lang.String fileStorageID,
           java.lang.String fileStorageName,
           int fileStorageSize,
           int fileStorageUsed,
           int fileStorageState,
           java.lang.String fileStorageUrl,
           java.lang.String fileStorageServerIP) {
           this.fileStorageID = fileStorageID;
           this.fileStorageName = fileStorageName;
           this.fileStorageSize = fileStorageSize;
           this.fileStorageUsed = fileStorageUsed;
           this.fileStorageState = fileStorageState;
           this.fileStorageUrl = fileStorageUrl;
           this.fileStorageServerIP = fileStorageServerIP;
    }


    /**
     * Gets the fileStorageID value for this FileStorage.
     * 
     * @return fileStorageID
     */
    public java.lang.String getFileStorageID() {
        return fileStorageID;
    }


    /**
     * Sets the fileStorageID value for this FileStorage.
     * 
     * @param fileStorageID
     */
    public void setFileStorageID(java.lang.String fileStorageID) {
        this.fileStorageID = fileStorageID;
    }


    /**
     * Gets the fileStorageName value for this FileStorage.
     * 
     * @return fileStorageName
     */
    public java.lang.String getFileStorageName() {
        return fileStorageName;
    }


    /**
     * Sets the fileStorageName value for this FileStorage.
     * 
     * @param fileStorageName
     */
    public void setFileStorageName(java.lang.String fileStorageName) {
        this.fileStorageName = fileStorageName;
    }


    /**
     * Gets the fileStorageSize value for this FileStorage.
     * 
     * @return fileStorageSize
     */
    public int getFileStorageSize() {
        return fileStorageSize;
    }


    /**
     * Sets the fileStorageSize value for this FileStorage.
     * 
     * @param fileStorageSize
     */
    public void setFileStorageSize(int fileStorageSize) {
        this.fileStorageSize = fileStorageSize;
    }


    /**
     * Gets the fileStorageUsed value for this FileStorage.
     * 
     * @return fileStorageUsed
     */
    public int getFileStorageUsed() {
        return fileStorageUsed;
    }


    /**
     * Sets the fileStorageUsed value for this FileStorage.
     * 
     * @param fileStorageUsed
     */
    public void setFileStorageUsed(int fileStorageUsed) {
        this.fileStorageUsed = fileStorageUsed;
    }


    /**
     * Gets the fileStorageState value for this FileStorage.
     * 
     * @return fileStorageState
     */
    public int getFileStorageState() {
        return fileStorageState;
    }


    /**
     * Sets the fileStorageState value for this FileStorage.
     * 
     * @param fileStorageState
     */
    public void setFileStorageState(int fileStorageState) {
        this.fileStorageState = fileStorageState;
    }


    /**
     * Gets the fileStorageUrl value for this FileStorage.
     * 
     * @return fileStorageUrl
     */
    public java.lang.String getFileStorageUrl() {
        return fileStorageUrl;
    }


    /**
     * Sets the fileStorageUrl value for this FileStorage.
     * 
     * @param fileStorageUrl
     */
    public void setFileStorageUrl(java.lang.String fileStorageUrl) {
        this.fileStorageUrl = fileStorageUrl;
    }


    /**
     * Gets the fileStorageServerIP value for this FileStorage.
     * 
     * @return fileStorageServerIP
     */
    public java.lang.String getFileStorageServerIP() {
        return fileStorageServerIP;
    }


    /**
     * Sets the fileStorageServerIP value for this FileStorage.
     * 
     * @param fileStorageServerIP
     */
    public void setFileStorageServerIP(java.lang.String fileStorageServerIP) {
        this.fileStorageServerIP = fileStorageServerIP;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FileStorage)) return false;
        FileStorage other = (FileStorage) obj;
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
            ((this.fileStorageName==null && other.getFileStorageName()==null) || 
             (this.fileStorageName!=null &&
              this.fileStorageName.equals(other.getFileStorageName()))) &&
            this.fileStorageSize == other.getFileStorageSize() &&
            this.fileStorageUsed == other.getFileStorageUsed() &&
            this.fileStorageState == other.getFileStorageState() &&
            ((this.fileStorageUrl==null && other.getFileStorageUrl()==null) || 
             (this.fileStorageUrl!=null &&
              this.fileStorageUrl.equals(other.getFileStorageUrl()))) &&
            ((this.fileStorageServerIP==null && other.getFileStorageServerIP()==null) || 
             (this.fileStorageServerIP!=null &&
              this.fileStorageServerIP.equals(other.getFileStorageServerIP())));
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
        if (getFileStorageName() != null) {
            _hashCode += getFileStorageName().hashCode();
        }
        _hashCode += getFileStorageSize();
        _hashCode += getFileStorageUsed();
        _hashCode += getFileStorageState();
        if (getFileStorageUrl() != null) {
            _hashCode += getFileStorageUrl().hashCode();
        }
        if (getFileStorageServerIP() != null) {
            _hashCode += getFileStorageServerIP().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FileStorage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageUsed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageUsed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageServerIP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageServerIP"));
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
