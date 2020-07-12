/**
 * CreatFileStorageReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local;

public class CreatFileStorageReq  implements java.io.Serializable {
    private java.lang.String paramFlag;

    private java.lang.String fileStorageTemplateId;

    private com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorageParamArray fileStorageParamArray;

    private java.lang.String fileStorageName;

    private java.lang.String fileAdminUser;

    private java.lang.String password;

    private java.lang.String appID;

    private java.lang.String appName;

    public CreatFileStorageReq() {
    }

    public CreatFileStorageReq(
           java.lang.String paramFlag,
           java.lang.String fileStorageTemplateId,
           com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorageParamArray fileStorageParamArray,
           java.lang.String fileStorageName,
           java.lang.String fileAdminUser,
           java.lang.String password,
           java.lang.String appID,
           java.lang.String appName) {
           this.paramFlag = paramFlag;
           this.fileStorageTemplateId = fileStorageTemplateId;
           this.fileStorageParamArray = fileStorageParamArray;
           this.fileStorageName = fileStorageName;
           this.fileAdminUser = fileAdminUser;
           this.password = password;
           this.appID = appID;
           this.appName = appName;
    }


    /**
     * Gets the paramFlag value for this CreatFileStorageReq.
     * 
     * @return paramFlag
     */
    public java.lang.String getParamFlag() {
        return paramFlag;
    }


    /**
     * Sets the paramFlag value for this CreatFileStorageReq.
     * 
     * @param paramFlag
     */
    public void setParamFlag(java.lang.String paramFlag) {
        this.paramFlag = paramFlag;
    }


    /**
     * Gets the fileStorageTemplateId value for this CreatFileStorageReq.
     * 
     * @return fileStorageTemplateId
     */
    public java.lang.String getFileStorageTemplateId() {
        return fileStorageTemplateId;
    }


    /**
     * Sets the fileStorageTemplateId value for this CreatFileStorageReq.
     * 
     * @param fileStorageTemplateId
     */
    public void setFileStorageTemplateId(java.lang.String fileStorageTemplateId) {
        this.fileStorageTemplateId = fileStorageTemplateId;
    }


    /**
     * Gets the fileStorageParamArray value for this CreatFileStorageReq.
     * 
     * @return fileStorageParamArray
     */
    public com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorageParamArray getFileStorageParamArray() {
        return fileStorageParamArray;
    }


    /**
     * Sets the fileStorageParamArray value for this CreatFileStorageReq.
     * 
     * @param fileStorageParamArray
     */
    public void setFileStorageParamArray(com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorageParamArray fileStorageParamArray) {
        this.fileStorageParamArray = fileStorageParamArray;
    }


    /**
     * Gets the fileStorageName value for this CreatFileStorageReq.
     * 
     * @return fileStorageName
     */
    public java.lang.String getFileStorageName() {
        return fileStorageName;
    }


    /**
     * Sets the fileStorageName value for this CreatFileStorageReq.
     * 
     * @param fileStorageName
     */
    public void setFileStorageName(java.lang.String fileStorageName) {
        this.fileStorageName = fileStorageName;
    }


    /**
     * Gets the fileAdminUser value for this CreatFileStorageReq.
     * 
     * @return fileAdminUser
     */
    public java.lang.String getFileAdminUser() {
        return fileAdminUser;
    }


    /**
     * Sets the fileAdminUser value for this CreatFileStorageReq.
     * 
     * @param fileAdminUser
     */
    public void setFileAdminUser(java.lang.String fileAdminUser) {
        this.fileAdminUser = fileAdminUser;
    }


    /**
     * Gets the password value for this CreatFileStorageReq.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this CreatFileStorageReq.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the appID value for this CreatFileStorageReq.
     * 
     * @return appID
     */
    public java.lang.String getAppID() {
        return appID;
    }


    /**
     * Sets the appID value for this CreatFileStorageReq.
     * 
     * @param appID
     */
    public void setAppID(java.lang.String appID) {
        this.appID = appID;
    }


    /**
     * Gets the appName value for this CreatFileStorageReq.
     * 
     * @return appName
     */
    public java.lang.String getAppName() {
        return appName;
    }


    /**
     * Sets the appName value for this CreatFileStorageReq.
     * 
     * @param appName
     */
    public void setAppName(java.lang.String appName) {
        this.appName = appName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreatFileStorageReq)) return false;
        CreatFileStorageReq other = (CreatFileStorageReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.paramFlag==null && other.getParamFlag()==null) || 
             (this.paramFlag!=null &&
              this.paramFlag.equals(other.getParamFlag()))) &&
            ((this.fileStorageTemplateId==null && other.getFileStorageTemplateId()==null) || 
             (this.fileStorageTemplateId!=null &&
              this.fileStorageTemplateId.equals(other.getFileStorageTemplateId()))) &&
            ((this.fileStorageParamArray==null && other.getFileStorageParamArray()==null) || 
             (this.fileStorageParamArray!=null &&
              this.fileStorageParamArray.equals(other.getFileStorageParamArray()))) &&
            ((this.fileStorageName==null && other.getFileStorageName()==null) || 
             (this.fileStorageName!=null &&
              this.fileStorageName.equals(other.getFileStorageName()))) &&
            ((this.fileAdminUser==null && other.getFileAdminUser()==null) || 
             (this.fileAdminUser!=null &&
              this.fileAdminUser.equals(other.getFileAdminUser()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.appID==null && other.getAppID()==null) || 
             (this.appID!=null &&
              this.appID.equals(other.getAppID()))) &&
            ((this.appName==null && other.getAppName()==null) || 
             (this.appName!=null &&
              this.appName.equals(other.getAppName())));
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
        if (getParamFlag() != null) {
            _hashCode += getParamFlag().hashCode();
        }
        if (getFileStorageTemplateId() != null) {
            _hashCode += getFileStorageTemplateId().hashCode();
        }
        if (getFileStorageParamArray() != null) {
            _hashCode += getFileStorageParamArray().hashCode();
        }
        if (getFileStorageName() != null) {
            _hashCode += getFileStorageName().hashCode();
        }
        if (getFileAdminUser() != null) {
            _hashCode += getFileAdminUser().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getAppID() != null) {
            _hashCode += getAppID().hashCode();
        }
        if (getAppName() != null) {
            _hashCode += getAppName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreatFileStorageReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">CreatFileStorageReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paramFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "ParamFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageTemplateId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileStorageTemplateId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageParamArray");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileStorageParamArray"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "FileStorageParamArray"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileStorageName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileAdminUser");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileAdminUser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "AppID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "AppName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
