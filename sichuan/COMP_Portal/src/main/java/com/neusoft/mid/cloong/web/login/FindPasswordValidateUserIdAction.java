package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;
import java.text.MessageFormat;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 找回密码时 验证用户名是否存在.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0 2015-2-28 下午2:01:35
 */
public class FindPasswordValidateUserIdAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3867773570662459956L;
	
	/**
	 * 记录日志.
	 */
	private static LogService logger = LogService.getLogger(FindPasswordValidateUserIdAction.class);
	
	 /**
     * 用户Id
     */
    private String userId;
    
    /**
     * 错误信息
     */
    protected String errMsg;
    
    /**
     * 验证用户名是否存在.
     */
    public String execute(){
    	try {
        	
            UserBean userTemp = (UserBean) ibatisDAO.getSingleRecord("queryUserInfoById", userId);
            if (userTemp == null) {
                errMsg = MessageFormat.format(getText("findPassword.validateUserId.error"), userId);  
                logger.error(CommonStatusCode.IO_OPTION_ERROR, "账号不存在!");
            } 
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "账号验证失败", e);
            return ConstantEnum.ERROR.toString(); 
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "账号验证失败", e);
            return ConstantEnum.ERROR.toString(); 
        }
    	return ConstantEnum.SUCCESS.toString();
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
    

}
