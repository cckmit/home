/*******************************************************************************
 * @(#)RegisterAction.java 2014年1月9日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


import net.sf.json.JSONObject;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.MD5;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.App;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @注册用户
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月9日 下午3:08:17
 */
public class RegisterAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7687399023069706043L;

	/**
	 * 记录日志
	 */
	private static LogService logger = LogService
			.getLogger(RegisterAction.class);

	/**
	 * 用户bean对象
	 */
	private UserBean user = new UserBean();

	/**
	 * 错误信息
	 */
	protected String errMsg;

	/**
	 * 用户信息
	 */
	private String userObj;

	/**
	 * 返回路径，用于在界面判断是否系统错误
	 */
	private String resultPath = ConstantEnum.SUCCESS.toString();

	// /**
	// *
	// * execute 注册用户----短信和邮件方式找回，不绑定业务
	// * @return success
	// * @see com.opensymphony.xwork2.ActionSupport#execute()
	// */
	// public String execute() {
	// user.setPassword(Base64.encode(MD5.getMd5Bytes(user.getPassword()))); //
	// MD5加密，再BASE64加密
	// user.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
	// user.setCreateUserId(user.getUserId());
	// user.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
	// user.setStatus(UserStatus.EXAMINATION); // 待审批
	// try {
	// UserBean userTemp = (UserBean)
	// ibatisDAO.getSingleRecord("queryUserInfoById", user.getUserId());
	// if (userTemp != null) {
	// errMsg = MessageFormat.format(getText("register.error"),
	// user.getUserId());
	// this.addActionError(errMsg);
	// ServletActionContext.getRequest().getSession().setAttribute("errMsg",
	// errMsg);
	// logger.error(CommonStatusCode.IO_OPTION_ERROR, "注册的用户账号已存在!");
	// return ConstantEnum.ERROR.toString();
	// } else {
	// ibatisDAO.insertData("registerUser", user);
	// }
	// } catch (SQLException e) {
	// logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "注册用户失败", e);
	// return ConstantEnum.ERROR.toString(); // 返回失败页面
	// } catch (Exception e) {
	// logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "注册用户失败", e);
	// return ConstantEnum.ERROR.toString(); // 返回失败页面
	// }
	// return ConstantEnum.SUCCESS.toString(); // 返回成功页面
	// }

	/**
	 * 
	 * execute 注册用户----输入密保问题方式找回，绑定业务
	 * 
	 * @return success
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		JSONObject jsonNode = JSONObject.fromObject(userObj);
		user.setUserId(jsonNode.getString("userId"));
		user.setUserName(jsonNode.getString("userName"));
		user.setPassword(jsonNode.getString("password"));
		user.setMobile(jsonNode.getString("mobile"));
		user.setEmail(jsonNode.getString("email"));
		user.setAddress(jsonNode.getString("address"));
		user.setTelphone(jsonNode.getString("telphone"));
		user.setFax(jsonNode.getString("fax"));
		user.setDesc(jsonNode.getString("desc"));
		user.setDepartmentName(jsonNode.getString("departmentName"));
		user.setSecurity_question(jsonNode.getString("security_question"));
		user.setSecurity_answer(jsonNode.getString("security_answer"));

		user.setPassword(Base64.encode(MD5.getMd5Bytes(user.getPassword()))); // MD5加密，再BASE64加密
		user.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		user.setCreateUserId(user.getUserId());
		user.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		user.setStatus(UserStatus.EXAMINATION); // 待审批

		List<BatchVO> batchVOs = new ArrayList<BatchVO>();
		BatchVO batchUserInfo = new BatchVO("ADD", "registerUser", user);
		batchVOs.add(batchUserInfo);

		
		try {
				   String appId = "";
					// 业务Id为空创建业务并与用户绑定
					int count = 1;
					do {
						String appReq = (String) ibatisDAO.getSingleRecord(
								"getAppReq", null);
						appId = MessageFormat.format(getText("app_format"), appReq);
						count = (Integer)ibatisDAO.getCount("findAppIdCount", appId);
					} while (count>0);
					
					//注册时业务名称由用户自动生成,不需要收到输入，生成规则政企出租-业务id
					String appName = getText("register_app_name")+appId;
					// 添加业务
					App app = new App();
					app.setAppId(appId);
					app.setAppName(appName);
					app.setCreateUser(user.getUserId());

					BatchVO batchApp = new BatchVO("ADD", "insertRegisterUserApp", app);
					batchVOs.add(batchApp);
				// 业务与用户绑定
					UserAppBean userAppBean = new UserAppBean();
					userAppBean.setUserId(user.getUserId());
					userAppBean.setAppId(appId);
					BatchVO batchAppInfo = new BatchVO("ADD", "addUserApp",
							userAppBean);
					batchVOs.add(batchAppInfo);

				  ibatisDAO.updateBatchData(batchVOs);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"注册用户失败", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString(); // 返回失败页面
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"注册用户失败", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString(); // 返回失败页面
		}
		return ConstantEnum.SUCCESS.toString(); // 返回成功页面
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	/**
	 * 获取errMsg字段数据
	 * 
	 * @return Returns the errMsg.
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * 设置errMsg字段数据
	 * 
	 * @param errMsg
	 *            The errMsg to set.
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the userObj
	 */
	public String getUserObj() {
		return userObj;
	}

	/**
	 * @param userObj
	 *            the userObj to set
	 */
	public void setUserObj(String userObj) {
		this.userObj = userObj;
	}

	/**
	 * @return the resultPath
	 */
	public String getResultPath() {
		return resultPath;
	}

	/**
	 * @param resultPath
	 *            the resultPath to set
	 */
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}
	
}
