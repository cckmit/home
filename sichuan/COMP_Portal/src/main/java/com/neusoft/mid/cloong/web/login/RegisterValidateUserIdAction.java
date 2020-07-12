
package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;
import java.text.MessageFormat;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户注册时，验证用户ID是否唯一
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-2-25 下午3:49:08
 */
public class RegisterValidateUserIdAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7687399023069706043L;

    /**
     * 记录日志
     */
    private static LogService logger = LogService.getLogger(RegisterValidateUserIdAction.class);
    
    /**
     * 用户Id
     */
    private String userId;
    
    /**
     * 错误信息
     */
    protected String errMsg;
    
    /**
     * 电话
     * */
    private String mobile;
    
    /**
     * 邮箱
     * */
    private String email;
    
    /**
     * 是否校验用户ID
     * */
    private String isCheckId = "false";
    
    /**
     * 
     * execute 注册用户账号验证
     * @return success
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
       
        try {
        	if("true".equals(isCheckId)){
        		UserBean userTemp = (UserBean) ibatisDAO.getSingleRecord("queryUserInfoById", userId);
                if (userTemp != null) {
                    errMsg = MessageFormat.format(getText("register.error"), userId);  
                    logger.error(CommonStatusCode.IO_OPTION_ERROR, "注册的用户账号已存在!");
                    return ConstantEnum.ERROR.toString(); // 返回失败页面
                } 
        	}
            
            UserBean userInfo = new UserBean();
            userInfo.setMobile(mobile);
            userInfo.setEmail(email);
            userInfo.setUserId(userId);
            
            if(isMoblie(userInfo)){
                errMsg = "手机号码已存在,请更换其他手机号!";
                return ConstantEnum.ERROR.toString(); // 返回失败页面
            }
            if(isEmail(userInfo)){
                errMsg = "邮箱已存在,请更新其他邮箱地址!";
                return ConstantEnum.ERROR.toString(); // 返回失败页面
            }
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "注册用户账号验证失败", e);
            return ConstantEnum.ERROR.toString(); 
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "注册用户账号验证失败", e);
            return ConstantEnum.ERROR.toString(); 
        }
        return ConstantEnum.SUCCESS.toString(); 
    }

    /**
     * isMoblie 判断是否存在重复电话号
     * @return 如果不存在返回false,如果存在，或数据库异常返回true
     */
	private boolean isMoblie(UserBean userInfo){
        int num = 0;
        try {
            num = ibatisDAO.getCount("getCountMobile", userInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return true;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return true;
        }
        if(num==0){
          return false;  
        }else{
            return true;
        }
    }
    /**
     * isEmail 判断是否存在重复email
     * @return 如果不存在返回false,如果存在，或数据库异常返回true
     */
	private boolean isEmail(UserBean userInfo){
        int num = 0;
        try {
            num = ibatisDAO.getCount("getCountEmail", userInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return true;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            return true;
        }
        if(num==0){
          return false;  
        }else{
            return true;
        }
    }

	
    /**
     * 获取errMsg字段数据
     * @return Returns the errMsg.
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * 设置errMsg字段数据
     * @param errMsg The errMsg to set.
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
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the isCheckId
	 */
	public String getIsCheckId() {
		return isCheckId;
	}

	/**
	 * @param isCheckId the isCheckId to set
	 */
	public void setIsCheckId(String isCheckId) {
		this.isCheckId = isCheckId;
	}

}
