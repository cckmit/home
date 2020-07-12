/**
 * 
 */
package com.neusoft.mid.cloong.fourA.integrate.ws.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.MD5;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.AppStatus;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.iamp.logger.LogService;



/**
 * @author Administrator
 *
 */
public class CreateXML {
	
	private static LogService logger = LogService.getLogger(CreateXML.class);
	
	public static String createXML(){
        String xmlStr="";
        //doc 的工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.setXmlVersion("1.0");
            document.setXmlStandalone(true);
            //
            Element root = document.createElement("root");
            document.appendChild(root);
            //
            Element user = document.createElement("user");
            //
            Element id = document.createElement("id");
            id.setTextContent("1");
            user.appendChild(id);
            //
            Element name = document.createElement("name");
            name.setTextContent("张三");
            user.appendChild(name);
            //
            Element sex = document.createElement("sex");
            sex.setTextContent("男");
            user.appendChild(sex);
            //
            root.appendChild(user);
            //
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
 
            //export string
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transFormer.transform(domSource, new StreamResult(bos));
            xmlStr = bos.toString();
            System.out.println("输出结果"+xmlStr);
            //通过输出流输出
            File file = new File("D:/out/user.xml");
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            StreamResult xmlResult = new StreamResult(out);
            transFormer.transform(domSource, xmlResult);
        }catch(Exception e){
            e.printStackTrace();
        }
        return xmlStr;
    }
 
	
	public static List<String> inputDelAccountXml(String xml) {
		List<String> userIds = new ArrayList<String>();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            Document doc = builder.parse(is);
            // 获取根节点
            Element rootEle = doc.getDocumentElement();  // 根节点accounts
            // 拿到根节点的名称
//            logger.info("根节点：" + rootEle.getTextContent()); // 根节点是accounts
            NodeList listElement = rootEle.getElementsByTagName("accId");
            
            for(int i=0;i<listElement.getLength();i++){
                Node node = listElement.item(i);
                NodeList childs = node.getChildNodes();
                for(int j=0;j<childs.getLength();j++){
                    Node childNode = childs.item(j);
//                    String nodeName=childNode.getNodeName();
//                    String value=childNode.getTextContent();
                    userIds.add(childNode.getTextContent());
                }
            }
        }catch(Exception e){
        	logger.error("解析删除的舒服信息出错", e);
        }
        return userIds;
	}
	
	public static List<UserBean> inputAccountsXml(String xml, String operateFlag) {
		List<UserBean> userList = new ArrayList<UserBean>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            // 将字符串转为XML
        	DocumentBuilder builder = factory.newDocumentBuilder();
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            Document doc = builder.parse(is);
            // 获取根节点
            Element rootElt = doc.getDocumentElement(); 
            // 拿到根节点的名称
//            logger.info("根节点：" + rootElt.getTextContent()); // 根节点是accounts
            // 获取根节点下的子节点head
            NodeList listElement = rootElt.getElementsByTagName("account");
            // 遍历head节点
            for (int i=0;i<listElement.getLength();i++) {
            	Node node = listElement.item(i);
                NodeList childs = node.getChildNodes();
                
                UserBean userBean =  new UserBean();
                for(int j=0;j<childs.getLength();j++){
                	Node childNode = childs.item(j);
                	String nodeName=childNode.getNodeName();
                	String nodeValue=childNode.getTextContent();
                    if ("accId".equals(nodeName)) {
                    	userBean.setUserId(nodeValue);
                    } else if ("name".equals(nodeName)) {
                    	userBean.setUserName(nodeValue);  // 不解析4A传过来的密码
                    } else if ("description".equals(nodeName)) {
                    	userBean.setDesc(nodeValue);
                    } else if ("email".equals(nodeName)) {
                    	userBean.setEmail(nodeValue);
                    } else if ("mobile".equals(nodeName)) {
                    	userBean.setMobile(nodeValue);
                    } else if ("o".equals(nodeName)) {
                    	userBean.setDepartmentName(nodeValue);
                    } else if ("supporterCorpName".equals(nodeName)) {
                    	userBean.setAddress(nodeValue);
                    }
//                    if (!"".equals(recordEle.elementTextTrim("description")) && null != recordEle.elementTextTrim("description")) {
//                    	userBean.setDesc(recordEle.elementTextTrim("description"));
//                    }
//                    if (!"".equals(recordEle.elementTextTrim("email")) && null != recordEle.elementTextTrim("email")) {
//                    	userBean.setEmail(recordEle.elementTextTrim("email"));
//                    }
//                    if (!"".equals(recordEle.elementTextTrim("mobile")) && null != recordEle.elementTextTrim("mobile")) {
//                    	userBean.setMobile(recordEle.elementTextTrim("mobile"));
//                    }
//                    if (!"".equals(recordEle.elementTextTrim("o")) && null != recordEle.elementTextTrim("o")) {
//                    	userBean.setDepartmentName(recordEle.elementTextTrim("o"));
//                    }
//                    if (!"".equals(recordEle.elementTextTrim("supporterCorpName")) && null != recordEle.elementTextTrim("supporterCorpName")) {
//                    	userBean.setAddress(recordEle.elementTextTrim("supporterCorpName"));
//                    }
                }
                
                String dateTime = DateParse.generateDateFormatyyyyMMddHHmmss();
                if ("ADD".equals(operateFlag)) {
                	userBean.setCreateTime(dateTime);
                    userBean.setCreateUserId("4A");
                }
                userBean.setUpdateTime(dateTime);
                
                RoleBean role = new RoleBean();
                role.setRoleId("123");
                userBean.getRoles().add(role); // 处理用户角色关联关系
                
                UserAppBean app = new UserAppBean();
        		app.setAppId("232");
        		app.setUserId(userBean.getUserId());
        		app.setAppBind_status(AppStatus.AUDITPASS.getCode());
        		userBean.getApps().add(app); // 处理用户业务关联关系
        		
                userBean.setStatus(UserStatus.ENABLE);
                if ("ADD".equals(operateFlag)) {
                	 String passwd = "123456";
                     String encodePass = Base64.encode(MD5.getMd5Bytes(passwd));
                     userBean.setPassword(encodePass);
                }
                userList.add(userBean);
            }

        } catch (Exception e) {
        	logger.error("xml格式的字符串解析出错", e);
        }
        return userList;
    }


    public static void main(String[] args) {
//        createXML();
//        
//        String xmlString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
//        		+ "<accounts>" 
//        		+ "<accId>帐号ID(必填)</accId>" 
//        		+ "<accId>帐号ID1(必填)</accId>" 
//        		+ "</accounts>";
//        inputDelAccountXml(xmlString1);
//        
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
        		+ "<accounts>" 
        		+ "<account>"
                + "<accId>帐号ID(必填)</accId>" 
        		+ "<userPasswordMD5>密码</userPasswordMD5>"
                + "<userPasswordSHA1>密码</userPasswordSHA1>"
        		+ "<name>姓名</name>"
                + "<sn>姓</sn>"
        		+ "<description>描述 </description>"
                + "<email>邮箱 </email>"
                + "<gender>性别</gender>"
                + "<telephoneNumber>电话号码</telephoneNumber>"
                + "<mobile>移动电话</mobile>"
                + "<startTime>用户的开始生效时间 </startTime>"
                + "<endTime>用户结束生效时间 </endTime>"
                + "<idCardNumber>身份证号码</idCardNumber>"
                + "<employeeNumber>工号 </employeeNumber>"
                + "<o>用户所属的组织的编码号 </o>"
                + "<employeeType>用户类型</employeeType>"
                + "<supporterCorpName>所在公司名称 </supporterCorpName>"
                + "</account>"
                + "<account>"
                + "<accId>帐号ID1(必填)</accId>" 
        		+ "<userPasswordMD5>密码1</userPasswordMD5>"
                + "<userPasswordSHA1>密码1</userPasswordSHA1>"
        		+ "<name>姓名1</name>"
                + "<sn>姓1</sn>"
                + "<description>描述1 </description>"
                + "<email>邮箱1 </email>"
                + "<gender>性别1</gender>"
                + "<telephoneNumber>电话号码1</telephoneNumber>"
                + "<mobile>移动电话1</mobile>"
                + "<startTime>用户的开始生效时间 1</startTime>"
                + "<endTime>用户结束生效时间1 </endTime>"
                + "<idCardNumber>身份证号码1</idCardNumber>"
                + "<employeeNumber>工号1 </employeeNumber>"
                + "<o>用户所属的组织的编码号1 </o>"
                + "<employeeType>用户类型1</employeeType>"
                + "<supporterCorpName>所在公司名称 1</supporterCorpName>"
                + "</account>"
                + "</accounts>";

        List<UserBean> userList = inputAccountsXml(xmlString, "ADD");
    }

}