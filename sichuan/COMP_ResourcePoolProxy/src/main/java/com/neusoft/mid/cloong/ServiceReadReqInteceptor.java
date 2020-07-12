/*******************************************************************************
 * @(#)ServiceReadReqInteceptor.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-19 下午09:18:40
 */
public class ServiceReadReqInteceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private static LogService logger = LogService.getLogger(RespSoapHeaderInteceptor.class);

    private SAAJInInterceptor saa = new SAAJInInterceptor();

    public ServiceReadReqInteceptor() {
        super(Phase.POST_PROTOCOL);
        getAfter().add(SAAJInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) {
        SOAPMessage mess = message.getContent(SOAPMessage.class);
        if (mess == null) {
            saa.handleMessage(message);
            mess = message.getContent(SOAPMessage.class);
        }
        SOAPHeader head = null;
        try {
            head = mess.getSOAPHeader();
        } catch (SOAPException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "获取从资源池获取的soap报文头失败", e);
            throw new Fault(e);
        }
        NodeList nodeIDCAccessId = head.getElementsByTagName("IDCAccessId");
        String resourcePoolId = nodeIDCAccessId.item(0).getTextContent();
        NodeList nodeIDCPwd = head.getElementsByTagName("IDCPwd");
        String password = nodeIDCPwd.item(0).getTextContent();
        NodeList nodeTransactionID = head.getElementsByTagName("TransactionID");
        String transactionID = nodeTransactionID.item(0).getTextContent();
        NodeList nodeZoneID = head.getElementsByTagName("ZoneID");
        
        /* zoneId为可选项，增加判空操作 */
        Node zoneId = nodeZoneID.item(0);
        
        String resourcePoolPartId = "";
        
        if(null != zoneId)
			resourcePoolPartId = zoneId.getTextContent();
        
        ReqBodyInfo info = new ReqBodyInfo();
        info.setPassword(password);
        info.setResourcePoolId(resourcePoolId);
        info.setResourcePoolPartId(resourcePoolPartId);
        info.setTransactionID(transactionID);
        ReqBodyThreadLcoal.set(info);
    }
}
