/*******************************************************************************
 * @(#)ServiceWriteRespInteceptor.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.neusoft.mid.cloong.info.RespBodyInfo;

/**
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-19 下午09:10:31
 */
public class ServiceWriteRespInteceptor extends AbstractSoapInterceptor {

    private static String nameURI = "http://www.idc.com/idc/schemas/";

    public ServiceWriteRespInteceptor() {
        super(Phase.WRITE);
    }

    public void handleMessage(SoapMessage message) {
        RespBodyInfo respBody = RespBodyThreadLcoal.get();
        QName qname = new QName("Response");
        Document doc = DOMUtils.createDocument();
        Element idcAccessId = doc.createElement("IDCAccessId");
        idcAccessId.setTextContent(respBody.getResourcePoolId());
        Element transactionID = doc.createElement("TransactionID");
        transactionID.setTextContent(respBody.getTransactionID());
        Element resultCode = doc.createElement("ResultCode");
        resultCode.setTextContent(respBody.getResultCode());
        Element root = doc.createElementNS(nameURI, "sch:Response");
        root.appendChild(idcAccessId);
        root.appendChild(transactionID);
        root.appendChild(resultCode);
        SoapHeader head = new SoapHeader(qname, root);
        List<Header> headers = message.getHeaders();
        headers.add(head);
        RespBodyThreadLcoal.unset();
    }
}
