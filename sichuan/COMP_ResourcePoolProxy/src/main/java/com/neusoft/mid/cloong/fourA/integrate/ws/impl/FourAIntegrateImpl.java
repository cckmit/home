/**
 * 
 */
package com.neusoft.mid.cloong.fourA.integrate.ws.impl;

import java.io.ByteArrayOutputStream;
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
import com.neusoft.mid.cloong.fourA.integrate.ws.FourAIntegrate;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.AppStatus;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.cloong.identity.bean.query.QueryUserInfo;
import com.neusoft.mid.cloong.identity.exception.UserOperationException;
import com.neusoft.mid.cloong.identity.service.UserService;
import com.neusoft.mid.iamp.logger.LogService;

public class FourAIntegrateImpl implements FourAIntegrate {

	private static LogService logger = LogService.getLogger(FourAIntegrateImpl.class);
	/**
     * 用户操作服务service
     */
    private UserService userService;
    
    private String roleId;
    
    private String appId;
    
    /**
     * 单个从账号查询接口
     */
	@Override
	public String findUser(String userID) {
		logger.info("查询单个从账号信息, " + userID);
		String output = null;
		try {
			UserBean user = this.userService.queryUserById(userID);
			if (null != user) {
				List<UserBean> userList = new ArrayList<UserBean>();
				userList.add(user);
				output = outputAccountsXml(userList);
			} else {
				output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><accounts><accounts/>";
			}
        } catch (Exception e) {
            logger.error("单个从账号查询接口出错：",e);
        }
		return output;
	}

	/**
	 * 从账号添加接口
	 */
	@Override
	public String addUserInfo(String userInfos) {
		List<String> userIds = new ArrayList<String>();
		try {
			List<UserBean> userList = inputAccountsXml(userInfos, "ADD");
			for (UserBean user : userList) {
				userService.createUser(user);
				userIds.add(user.getUserId());
			}
			logger.info("创建从账号信息成功!");
		} catch (Exception e) {
			logger.error("创建从账号信息出错：", e);
			return outputResultXml("ADD", null, userIds, "新增从账号信息时出错"+e);
		}
		return outputResultXml("ADD", userIds, null, null);
	}
	
	/**
	 * 添加、修改的输入
	 * @param xml
	 * @param operateFlag
	 * @return
	 */
	/**
	 * 添加、修改的输入
	 * @param xml
	 * @param operateFlag
	 * @return
	 */
	public List<UserBean> inputAccountsXml(String xml, String operateFlag) {
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
                role.setRoleId(roleId);
                userBean.getRoles().add(role); // 处理用户角色关联关系
                
                UserAppBean app = new UserAppBean();
        		app.setAppId(appId);
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
	
	
	/**
	 * 添加、修改、删除的输出
	 * @param flag
	 * @param successUserList
	 * @param errorUserList
	 * @param resultMsg
	 * @return
	 */
	public String outputResultXml(String flag, List<String> successUserList, List<String> errorUserList, String resultMsg) {
		String xmlStr="";
        //doc 的工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.setXmlVersion("1.0");
            document.setXmlStandalone(true);
            //
            Element resultsEle = document.createElement("results");
            document.appendChild(resultsEle);
            
	        if (successUserList != null) {
	        	Element sucResultEle = document.createElement("result");
	        	if ("ADD".equals(flag)) {
	        		sucResultEle.setAttribute("returncode", "1300");
	    		} else if ("EDIT".equals(flag)) {
	    			sucResultEle.setAttribute("returncode", "1302");
	    		} else if ("DEL".equals(flag)) {
	    			sucResultEle.setAttribute("returncode", "1304");
	    		}
	        	for (String userId : successUserList) {
	        		Element accIdEle = document.createElement("accId");
	                accIdEle.setTextContent(userId);
	                sucResultEle.appendChild(accIdEle);
	            }
	        	resultsEle.appendChild(sucResultEle);
	        }
	        
	        if (errorUserList != null) {
	        	Element errResultEle = document.createElement("result");
	        	if ("ADD".equals(flag)) {
	        		errResultEle.setAttribute("returncode", "1301");
	    		} else if ("EDIT".equals(flag)) {
	    			errResultEle.setAttribute("returncode", "1303");
	    		} else if ("DEL".equals(flag)) {
	    			errResultEle.setAttribute("returncode", "1305");
	    		}
	        	for (String userId : errorUserList) {
	        		Element accIdEle = document.createElement("accId");
	                accIdEle.setTextContent(userId);
	                errResultEle.appendChild(accIdEle);
	            }
	        	resultsEle.appendChild(errResultEle);
	        }
	    	
	        if (!"".equals(resultMsg) && null != resultMsg) {
	        	Element errorEle = document.createElement("errorMsg");
	        	errorEle.setTextContent(resultMsg);
	        	resultsEle.appendChild(errorEle);
	        }
		
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            //export string
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transFormer.transform(domSource, new StreamResult(bos));
            xmlStr = bos.toString();
            logger.info("添加、修改、删除的输出结果："+xmlStr);
        }catch(Exception e){
            logger.error("解析添加、修改、删除的输出结果出错", e);
        }
		
        return xmlStr;
	}
	
	/** 
	 * 删除的输入
	 * @param xml
	 * @return
	 */
	public List<String> inputDelAccountXml(String xml) {
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
	
	/** 
	 * 查询的输出
	 * @param userList
	 * @return
	 */
	public String outputAccountsXml(List<UserBean> userList) {
		String xmlStr="";
        //doc 的工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.setXmlVersion("1.0");
            document.setXmlStandalone(true);
            //
            Element accountsEle = document.createElement("accounts");
            document.appendChild(accountsEle);
            for (UserBean userBean : userList) {
            	Element accountEle = document.createElement("account");
                Element accIdEle = document.createElement("accId");
                accIdEle.setTextContent(userBean.getUserId());
                accountEle.appendChild(accIdEle);
                Element nameEle = document.createElement("name");
                nameEle.setTextContent(userBean.getUserName());
                accountEle.appendChild(nameEle);
                if (!"".equals(userBean.getDesc()) && null != userBean.getDesc()) {
                	Element descriptionEle = document.createElement("description");
                    descriptionEle.setTextContent(userBean.getDesc());
                    accountEle.appendChild(descriptionEle);
                }
                if (!"".equals(userBean.getEmail()) && null != userBean.getEmail()) {
                	 Element emailEle = document.createElement("email");
                     emailEle.setTextContent(userBean.getEmail());
                     accountEle.appendChild(emailEle);
                }
                if (!"".equals(userBean.getMobile()) && null != userBean.getMobile()) {
                	Element mobileEle = document.createElement("mobile");
                    mobileEle.setTextContent(userBean.getMobile());
                    accountEle.appendChild(mobileEle);
                }
                if (!"".equals(userBean.getDepartmentName()) && null != userBean.getDepartmentName()) {
                	Element oEle = document.createElement("o");
                    oEle.setTextContent(userBean.getDepartmentName());
                    accountEle.appendChild(oEle);
                }
                if (!"".equals(userBean.getAddress()) && null != userBean.getAddress()) {
                	Element supporterCorpNameEle = document.createElement("supporterCorpName");
                    supporterCorpNameEle.setTextContent(userBean.getAddress());
                    accountEle.appendChild(supporterCorpNameEle);
                }
                accountsEle.appendChild(accountEle);
            }
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            //export string
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transFormer.transform(domSource, new StreamResult(bos));
            xmlStr = bos.toString();
            logger.info("查询用户输出结果"+xmlStr);
        }catch(Exception e){
            logger.error("解析查询用户输出结果出错", e);
        }
		
        return xmlStr;
	}
	
	
	public void main(String[] args) {
        // 下面是需要解析的xml字符串例子
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

//        List<UserBean> userList = inputAccountsXml(xmlString, "ADD");
        
//        System.out.println(outputAccountsXml(userList));
        
        List<String> sucUserList = new ArrayList<String>();
        List<String> errUserList = new ArrayList<String>();
        sucUserList.add("账号成功1");
        sucUserList.add("账号成功2");
        errUserList.add("账号失败1");
        errUserList.add("账号失败2");
        
//        System.out.println(outputResultXml("ADD", sucUserList, errUserList, "ERROR"));
        
        String xmlString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
        		+ "<accounts>" 
        		+ "<accId>帐号ID(必填)</accId>" 
        		+ "<accId>帐号ID1(必填)</accId>" 
        		+ "</accounts>";
        inputDelAccountXml(xmlString1);
    }

	
	/**
	 * 从账号删除接口
	 */
	@Override
	public String delUser(String userIDs) {
		List<String> userList = inputDelAccountXml(userIDs);
		String[] userIds = userList.toArray(new String[userList.size()]); 
		try {
			this.userService.changeUserStatus(userIds, UserStatus.DISABLE);
			logger.info("删除从账号信息成功!");
		} catch (UserOperationException e){
            logger.error("删除用户信息出错：", e);
            return outputResultXml("DEL", null, userList, "更新从账号状态时出错"+e);
        } catch (Exception e) {
            logger.error("删除用户信息出错", e);
            return outputResultXml("DEL", null, userList, "更新从账号状态时出错"+e);
        }
		return outputResultXml("DEL", userList, null, null);
	}

	/**
	 * 从账号修改接口
	 */
	@Override
	public String modifyUserInfo(String userInfos) {
		List<String> userIds = new ArrayList<String>();
		try {
			List<UserBean> userList = inputAccountsXml(userInfos, "EDIT");
			for (UserBean user : userList) {
				this.userService.fourAUpdateUser(user);
				userIds.add(user.getUserId());
			}
			logger.info("修改从账号信息成功!");
		} catch (Exception e) {
			logger.error("更新从账号信息出错：", e);
			return outputResultXml("EDIT", null, userIds, "更新从账号信息时出错"+e);
		}
		return outputResultXml("EDIT", userIds, null, null);
	}

	
	/**
	 * 全部从账号信息查询接口
	 */
	@Override
	public String queryUsers() {
		logger.info("查询全部从账号信息!");
		String output = null;
		 try {
			 QueryUserInfo selectUser = new QueryUserInfo();
			 selectUser.setQueryStatus("0");
			 List<UserBean> userList = this.userService.queryUserList(selectUser);
			 output = outputAccountsXml(userList);
		 } catch (Exception e) {
	            logger.error("全部从账号信息查询出错：", e);
	        }
		 return output;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

}
