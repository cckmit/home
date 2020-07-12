/**
 * SetFileStorageAccessReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local;

public class SetFileStorageAccessReq  implements java.io.Serializable {
    private java.lang.String fileStorageID;

    private com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl[] fileRequestAcl;

    private java.lang.String fileUser;

    private java.lang.String password;

    public SetFileStorageAccessReq() {
    }

    public SetFileStorageAccessReq(
           java.lang.String fileStorageID,
           com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl[] fileRequestAcl,
           java.lang.String fileUser,
           java.lang.String password) {
           this.fileStorageID = fileStorageID;
           this.fileRequestAcl = fileRequestAcl;
           this.fileUser = fileUser;
           this.password = password;
    }


    /**
     * Gets the fileStorageID value for this SetFileStorageAccessReq.
     * 
     * @return fileStorageID
     */
    public java.lang.String getFileStorageID() {
        return fileStorageID;
    }


    /**
     * Sets the fileStorageID value for this SetFileStorageAccessReq.
     * 
     * @param fileStorageID
     */
    public void setFileStorageID(java.lang.String fileStorageID) {
        this.fileStorageID = fileStorageID;
    }


    /**
     * Gets the fileRequestAcl value for this SetFileStorageAccessReq.
     * 
     * @return fileRequestAcl
     */
    public com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl[] getFileRequestAcl() {
        return fileRequestAcl;
    }


    /**
     * Sets the fileRequestAcl value for this SetFileStorageAccessReq.
     * 
     * @param fileRequestAcl
     */
    public void setFileRequestAcl(com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.Acl[] fileRequestAcl) {
        this.fileRequestAcl = fileRequestAcl;
    }


    /**
     * Gets the fileUser value for this SetFileStorageAccessReq.
     * 
     * @return fileUser
     */
    public java.lang.String getFileUser() {
        return fileUser;
    }


    /**
     * Sets the fileUser value for this SetFileStorageAccessReq.
     * 
     * @param fileUser
     */
    public void setFileUser(java.lang.String fileUser) {
        this.fileUser = fileUser;
    }


    /**
     * Gets the password value for this SetFileStorageAccessReq.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this SetFileStorageAccessReq.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SetFileStorageAccessReq)) return false;
        SetFileStorageAccessReq other = (SetFileStorageAccessReq) obj;
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
            ((this.fileRequestAcl==null && other.getFileRequestAcl()==null) || 
             (this.fileRequestAcl!=null &&
              java.util.Arrays.equals(this.fileRequestAcl, other.getFileRequestAcl()))) &&
            ((this.fileUser==null && other.getFileUser()==null) || 
             (this.fileUser!=null &&
              this.fileUser.equals(other.getFileUser()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword())));
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
        if (getFileUser() != null) {
            _hashCode += getFileUser().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SetFileStorageAccessReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", ">SetFileStorageAccessReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileStorageID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileStorageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileRequestAcl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileRequestAcl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.CMCCCloud.com/schemas/CMCC/SOAP/fs/v1/", "Acl"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "Acl"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileUser");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "FileUser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/fs/v1/local", "Password"));
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
