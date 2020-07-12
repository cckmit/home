/**
 * Copyright 2012 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.neusoft.mid.cloong;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.NodeList;

import com.neusoft.mid.cloong.info.RespBodyInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 读取响应SOAP报文头拦截器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-6 下午04:29:07
 */
public class RespSoapHeaderInteceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private static LogService logger = LogService.getLogger(RespSoapHeaderInteceptor.class);

    private SAAJInInterceptor saa = new SAAJInInterceptor();

    public RespSoapHeaderInteceptor() {
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
        NodeList rc = head.getElementsByTagName("ResultCode");
        String resultCode = rc.item(0).getTextContent();
        RespBodyInfo respBodyInfo = new RespBodyInfo();
        respBodyInfo.setResultCode(resultCode);
        RespBodyThreadLcoal.set(respBodyInfo);
    }
}
