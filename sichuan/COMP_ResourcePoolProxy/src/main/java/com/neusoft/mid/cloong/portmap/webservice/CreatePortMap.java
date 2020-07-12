
package com.neusoft.mid.cloong.portmap.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>createPortMap complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="createPortMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="vfwReq" type="{http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services}createPortMapReq" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createPortMap", propOrder = {
    "vfwReq"
})
public class CreatePortMap {

    protected CreatePortMapReq vfwReq;

    /**
     * ��ȡvfwReq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CreatePortMapReq }
     *     
     */
    public CreatePortMapReq getVfwReq() {
        return vfwReq;
    }

    /**
     * ����vfwReq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CreatePortMapReq }
     *     
     */
    public void setVfwReq(CreatePortMapReq value) {
        this.vfwReq = value;
    }

}
