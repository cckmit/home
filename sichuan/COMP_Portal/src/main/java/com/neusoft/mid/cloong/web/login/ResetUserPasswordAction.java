package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.MD5;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 重置密码.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0 2015-3-2 下午2:02:18
 */
public class ResetUserPasswordAction extends BaseAction {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3643521573812086106L;
	
	/**
	 * 记录日志.
	 */
	private static LogService logger = LogService.getLogger(ResetUserPasswordAction.class);

	/**
	 * 用户id.
	 */
	private String userId;
	
	/**
	 * 登录密码.
	 */
	private String password;
	
	/**
	 * 错误信息.
	 */
	private String errMsg;
	
	/**
	 *重置用户密码.
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute(){
		UserBean userInfo = new UserBean();
		userInfo.setPassword(Base64.encode(MD5.getMd5Bytes(password)));
		userInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		userInfo.setUserId(userId);
		try {
			ibatisDAO.updateData("updateUserInfo", userInfo);
		} catch (SQLException e) {
			errMsg = "重置密码失败!";
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "重置密码时出错",e);
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
