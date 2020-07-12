package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 找回密码  查询用户密保问题.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0 2015-3-2 上午9:54:37
 */
public class FindPasswordQueryUserQuestionAction extends BaseAction {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4235501180221526669L;
	/**
	 * 记录日志.
	 */
	private static LogService logger = LogService.getLogger(FindPasswordQueryUserQuestionAction.class);
	
	/**
	 * 错误信息.
	 */
	private String errMsg;
	
	/**
	 * 用户Id.
	 */
	private String userId;
	
	/**
	 * 用户.
	 */
	private UserBean userInfo = new  UserBean();

	/**
	 * 查询用户密保问题.
	 */
	public String execute(){
		try {
			userInfo =(UserBean)ibatisDAO.getSingleRecord("findSecurityQuestionbyUserId", userId);
		} catch (SQLException e) {
			errMsg = getText("findPassword.findUserQuestion.error");
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"查询用户密保问题异常");
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userInfo
	 */
	public UserBean getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(UserBean userInfo) {
		this.userInfo = userInfo;
	}
	
}
