
package com.neusoft.mid.cloong.portmap.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>deletePortMap complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="deletePortMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="vfwReq" type="{http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services}deletePortMapReq" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deletePortMap", propOrder = {
    "vfwReq"
})
public class DeletePortMap {

    protected DeletePortMapReq vfwReq;

    /**
     * ��ȡvfwReq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DeletePortMapReq }
     *     
     */
    public DeletePortMapReq getVfwReq() {
        return vfwReq;
    }

    /**
     * ����vfwReq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DeletePortMapReq }
     *     
     */
    public void setVfwReq(DeletePortMapReq value) {
        this.vfwReq = value;
    }

}
