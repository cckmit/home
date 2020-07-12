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

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 添加soap报文头信息拦截器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-6 下午04:25:54
 */
public class ReqSoapHeaderInteceptor extends AbstractSoapInterceptor {

    private static String nameURI = "http://www.idc.com/idc/schemas/";

    public ReqSoapHeaderInteceptor() {
        super(Phase.WRITE);
    }

    public void handleMessage(SoapMessage message) {
        ReqBodyInfo reqBody = ReqBodyThreadLcoal.get();
        QName qname = new QName("Authorization");
        Document doc = DOMUtils.createDocument();
        Element idcAccessId = doc.createElement("IDCAccessId");
        idcAccessId.setTextContent(reqBody.getResourcePoolId());
        Element idcPwd = doc.createElement("IDCPwd");
        // 还原密码并赋值
        try {
            idcPwd.setTextContent(Base64.decode(reqBody.getPassword()));
        } catch (DOMException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Element timestamp = doc.createElement("Timestamp");
        timestamp.setTextContent(reqBody.getTimestamp());
        Element transactionID = doc.createElement("TransactionID");
        transactionID.setTextContent(reqBody.getTransactionID());
        Element zoneID = doc.createElement("ZoneID");
        zoneID.setTextContent(reqBody.getResourcePoolPartId());
        Element root = doc.createElementNS(nameURI, "sch:Authorization");
        root.appendChild(idcAccessId);
        root.appendChild(idcPwd);
        root.appendChild(timestamp);
        root.appendChild(transactionID);
        root.appendChild(zoneID);
        SoapHeader head = new SoapHeader(qname, root);
        List<Header> headers = message.getHeaders();
        headers.add(head);
    }
}
