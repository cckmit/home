//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.11 at 04:10:40 
//


package com.neusoft.mid.cloong.schema.standard.vm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="TemplateID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ResourceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MeasureMode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TemplateDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TemplateStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TemplateCreator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CreateTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ResourceInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CPUNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MemorySize" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="StorageSize" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="VMOS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MaxBandwidth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="OSType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="rates" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="VMSubSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="diskPartition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="cpuArchitecture" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "VMTemplate")
public class VMTemplate {

    @XmlElement(name = "TemplateID", required = true)
    protected String templateID;
    @XmlElement(name = "ResourceType", required = true)
    protected String resourceType;
    @XmlElement(name = "MeasureMode", required = true)
    protected String measureMode;
    @XmlElement(name = "TemplateDesc", required = true)
    protected String templateDesc;
    @XmlElement(name = "TemplateStatus", required = true)
    protected String templateStatus;
    @XmlElement(name = "TemplateCreator", required = true)
    protected String templateCreator;
    @XmlElement(name = "CreateTime", required = true)
    protected String createTime;
    @XmlElement(name = "ResourceInfo", required = true)
    protected VMTemplate.ResourceInfo resourceInfo;

    /**
     * Gets the value of the templateID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateID() {
        return templateID;
    }

    /**
     * Sets the value of the templateID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateID(String value) {
        this.templateID = value;
    }

    /**
     * Gets the value of the resourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Sets the value of the resourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceType(String value) {
        this.resourceType = value;
    }

    /**
     * Gets the value of the measureMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasureMode() {
        return measureMode;
    }

    /**
     * Sets the value of the measureMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasureMode(String value) {
        this.measureMode = value;
    }

    /**
     * Gets the value of the templateDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateDesc() {
        return templateDesc;
    }

    /**
     * Sets the value of the templateDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateDesc(String value) {
        this.templateDesc = value;
    }

    /**
     * Gets the value of the templateStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateStatus() {
        return templateStatus;
    }

    /**
     * Sets the value of the templateStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateStatus(String value) {
        this.templateStatus = value;
    }

    /**
     * Gets the value of the templateCreator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateCreator() {
        return templateCreator;
    }

    /**
     * Sets the value of the templateCreator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateCreator(String value) {
        this.templateCreator = value;
    }

    /**
     * Gets the value of the createTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Sets the value of the createTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateTime(String value) {
        this.createTime = value;
    }

    /**
     * Gets the value of the resourceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VMTemplate.ResourceInfo }
     *     
     */
    public VMTemplate.ResourceInfo getResourceInfo() {
        return resourceInfo;
    }

    /**
     * Sets the value of the resourceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VMTemplate.ResourceInfo }
     *     
     */
    public void setResourceInfo(VMTemplate.ResourceInfo value) {
        this.resourceInfo = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="CPUNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MemorySize" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="StorageSize" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="VMOS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MaxBandwidth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="OSType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="rates" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="VMSubSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="diskPartition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="cpuArchitecture" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "cpuNum",
        "memorySize",
        "storageSize",
        "vmos",
        "maxBandwidth",
        "osType",
        "rates",
        "unit",
        "vmSubSystem",
        "diskPartition",
        "cpuArchitecture"
    })
    public static class ResourceInfo {

        @XmlElement(name = "CPUNum", required = true)
        protected String cpuNum;
        @XmlElement(name = "MemorySize", required = true)
        protected String memorySize;
        @XmlElement(name = "StorageSize", required = true)
        protected String storageSize;
        @XmlElement(name = "VMOS", required = true)
        protected String vmos;
        @XmlElement(name = "MaxBandwidth")
        protected String maxBandwidth;
        @XmlElement(name = "OSType")
        protected String osType;
        protected String rates;
        protected String unit;
        @XmlElement(name = "VMSubSystem")
        protected String vmSubSystem;
        protected String diskPartition;
        protected String cpuArchitecture;

        /**
         * Gets the value of the cpuNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCPUNum() {
            return cpuNum;
        }

        /**
         * Sets the value of the cpuNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCPUNum(String value) {
            this.cpuNum = value;
        }

        /**
         * Gets the value of the memorySize property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMemorySize() {
            return memorySize;
        }

        /**
         * Sets the value of the memorySize property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMemorySize(String value) {
            this.memorySize = value;
        }

        /**
         * Gets the value of the storageSize property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStorageSize() {
            return storageSize;
        }

        /**
         * Sets the value of the storageSize property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStorageSize(String value) {
            this.storageSize = value;
        }

        /**
         * Gets the value of the vmos property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVMOS() {
            return vmos;
        }

        /**
         * Sets the value of the vmos property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVMOS(String value) {
            this.vmos = value;
        }

        /**
         * Gets the value of the maxBandwidth property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMaxBandwidth() {
            return maxBandwidth;
        }

        /**
         * Sets the value of the maxBandwidth property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMaxBandwidth(String value) {
            this.maxBandwidth = value;
        }

        /**
         * Gets the value of the osType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOSType() {
            return osType;
        }

        /**
         * Sets the value of the osType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOSType(String value) {
            this.osType = value;
        }

        /**
         * Gets the value of the rates property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRates() {
            return rates;
        }

        /**
         * Sets the value of the rates property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRates(String value) {
            this.rates = value;
        }

        /**
         * Gets the value of the unit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnit() {
            return unit;
        }

        /**
         * Sets the value of the unit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnit(String value) {
            this.unit = value;
        }

        /**
         * Gets the value of the vmSubSystem property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVMSubSystem() {
            return vmSubSystem;
        }

        /**
         * Sets the value of the vmSubSystem property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVMSubSystem(String value) {
            this.vmSubSystem = value;
        }

        /**
         * Gets the value of the diskPartition property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDiskPartition() {
            return diskPartition;
        }

        /**
         * Sets the value of the diskPartition property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDiskPartition(String value) {
            this.diskPartition = value;
        }

        /**
         * Gets the value of the cpuArchitecture property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCpuArchitecture() {
            return cpuArchitecture;
        }

        /**
         * Sets the value of the cpuArchitecture property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCpuArchitecture(String value) {
            this.cpuArchitecture = value;
        }

    }

}
