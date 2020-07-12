/*******************************************************************************
 * @(#)ModifyPasswordUpdateAction.java 2014年1月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.userInfo;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.MD5;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.login.RegisterAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改密码保存DB
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月11日 下午2:10:39
 */
public class ModifyPasswordUpdateAction extends BaseAction {

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1133980235272018710L;

    /**
     * 记录日志
     */
    private static LogService logger = LogService.getLogger(RegisterAction.class);
    
    /**
     * 用户bean对象
     */
    private UserBean user = new UserBean();
    
    /**
     * 
     * execute 修改密码信息保存DB
     * @return success
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        user.setUserId(userId);
        
        if(!comfirmOldPassword()){
            this.addActionError("旧密码错误!请确认后重新输入!");
            return ConstantEnum.ERROR.toString();
        }
        user.setPassword(Base64.encode(MD5.getMd5Bytes(user.getPassword())));
        try {
            ibatisDAO.updateData("updatePassword", user);
            this.addActionMessage(MessageFormat.format(getText("userPassword.update.success"), user.getUserId()));
        } catch (SQLException e) {
            this.addActionError(MessageFormat.format(getText("userPassword.update.failed"), user.getUserId()));
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改密码失败", e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        } catch (Exception e) {
            this.addActionError(MessageFormat.format(getText("userPassword.update.failed"), user.getUserId()));
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改密码失败", e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        }
        return ConstantEnum.SUCCESS.toString(); // 返回成功页面
    }
    
    /**
     * 
     * comfirmOldPassword 确认旧密码是否正确
     * @return 如果旧密码正确返回 true,否则都返回 false.
     */
    private boolean comfirmOldPassword(){
        UserBean userBean;
        try {
            userBean = (UserBean)ibatisDAO.getSingleRecord("queryUserInfoById", user.getUserId());
            logger.debug(userBean.getPassword());
            logger.debug(user.getOldPassword());
            logger.debug(Base64.decode(userBean.getPassword()));
            logger.debug(Base64.decode(user.getOldPassword()));
        } catch (SQLException e) {
            this.addActionError(MessageFormat.format(getText("userPassword.update.failed"), user.getUserId()));
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改密码失败", e);
            return false;
        } catch (Exception e) {
            this.addActionError(MessageFormat.format(getText("userPassword.update.failed"), user.getUserId()));
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改密码失败", e);
            return false;
        }
        String oldPasswrold = userBean.getPassword();
        try {
            if(Base64.decode(oldPasswrold).equalsIgnoreCase(user.getOldPassword())){
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
