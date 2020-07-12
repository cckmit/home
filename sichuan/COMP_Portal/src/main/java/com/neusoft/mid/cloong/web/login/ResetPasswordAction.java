/*******************************************************************************
 * @(#)ResetPasswordAction.java 2014年1月13日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.login;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.MD5;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 找回密码之重置密码
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月13日 下午5:00:50
 */
public class ResetPasswordAction extends BaseAction{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6352088472363606873L;

    /**
     * 记录日志
     */
    private static LogService logger = LogService.getLogger(ResetPasswordAction.class);
    
    /**
     * 页面隐藏的用户id
     */
    private String userId;
    
    /**
     * 标志重置成功失败的标志
     */
    private String flag;
    
    /**
     * 用户bean对象
     */
    private UserBean user = new UserBean();

    public String execute() {
        user.setUserId(userId);
        user.setPassword(Base64.encode(MD5.getMd5Bytes(user.getPassword())));
        try {
            ibatisDAO.updateData("updatePassword", user);
            errMsg = getText("resetPassword.update.success");
            flag = "resetSuc";
        } catch (SQLException e) {
            errMsg = getText("userPassword.update.failed");
            flag = "resetError";
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "重置密码失败", e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        } catch (Exception e) {
            errMsg = getText("userPassword.update.failed");
            flag = "resetError";
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "重置密码失败", e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        }
        return ConstantEnum.SUCCESS.toString(); // 返回成功页面
    }
    
    public void setUser(UserBean user) {
        this.user = user;
    }
    
    public UserBean getUser() {
        return user;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
}
