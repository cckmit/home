
package com.neusoft.mid.cloong.portmap.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>createPortMapReq complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="createPortMapReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FWQuintuples" type="{http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services}fwQuintuple" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createPortMapReq", propOrder = {
    "fwQuintuples",
    "flag",
    "id"
})
public class CreatePortMapReq {

    @XmlElement(name = "FWQuintuples")
    protected FwQuintuple fwQuintuples;
    protected String flag;
    protected String id;

    /**
     * ��ȡfwQuintuples���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FwQuintuple }
     *     
     */
    public FwQuintuple getFWQuintuples() {
        return fwQuintuples;
    }

    /**
     * ����fwQuintuples���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FwQuintuple }
     *     
     */
    public void setFWQuintuples(FwQuintuple value) {
        this.fwQuintuples = value;
    }

    /**
     * ��ȡflag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * ����flag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     * ��ȡid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
