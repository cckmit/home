
package com.neusoft.mid.cloong.portmap.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>deletePortMapResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="deletePortMapResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services}PortMapResp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deletePortMapResponse", propOrder = {
    "portMapResp"
})
public class DeletePortMapResponse {

    @XmlElement(name = "PortMapResp", namespace = "http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services")
    protected PortMapResp portMapResp;

    /**
     * ��ȡportMapResp���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PortMapResp }
     *     
     */
    public PortMapResp getPortMapResp() {
        return portMapResp;
    }

    /**
     * ����portMapResp���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PortMapResp }
     *     
     */
    public void setPortMapResp(PortMapResp value) {
        this.portMapResp = value;
    }

}
