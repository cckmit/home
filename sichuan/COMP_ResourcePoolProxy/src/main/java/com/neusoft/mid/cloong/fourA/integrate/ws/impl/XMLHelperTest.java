package com.neusoft.mid.cloong.fourA.integrate.ws.impl;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLHelperTest {

    public static void main(String[] args) throws DocumentException {
	     Document document = DocumentHelper.createDocument();
	        document.setXMLEncoding("UTF-8");
	        Element rootElement = document.addElement("config");

	        Element firstElement = rootElement.addElement("first");
	        //out节点
	        Element sonElement = firstElement.addElement("out");
	        Element sameElement = null;

	        sameElement = sonElement.addElement("Name");
	        sameElement.addText("李四");

	        sameElement = sonElement.addElement("Age");
	        sameElement.addText("26");

	        sameElement = sonElement.addElement("Gender");
	        sameElement.addText("男");
	        //in节点
	        Element nextElement = firstElement.addElement("in");
	        Element subElement = null;

	        subElement = nextElement.addElement("SerialNo");
	        subElement.addText("201700001");

	        subElement = nextElement.addElement("Certificatetype");
	        subElement.addText("0");

	        subElement = nextElement.addElement("Certificateno");
	        subElement.addText("220182");

	        System.out.println(document.asXML());

	   }
	
}
