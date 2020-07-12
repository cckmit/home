/*******************************************************************************
 * @(#)UserInfoAction.java 2014年1月10日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.userInfo;

import java.sql.SQLException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户信息查看
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月10日 下午4:26:09
 */
public class UserInfoAction extends BaseAction {

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 7906520659584887831L;
    
    /**
     * 记录日志
     */
    private static LogService logger = LogService.getLogger(UserInfoAction.class);
    
    /**
     * 用户bean对象
     */
    private UserBean user = new UserBean();

    /**
     * 用户绑定未审批的业务个数.
     */
    private int count;
    
    /**
     * execute 用户自信息查看
     * @return success
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @SuppressWarnings("unchecked")
	public String execute() {
        if(null != errMsg && !"".equals(errMsg)){
            this.addActionError(errMsg);
        }
        
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        try {
            user = (UserBean)ibatisDAO.getSingleRecord("getSingleUser", userId);
            //查询用户绑定的业务信息
            List<UserAppBean> userAppList = ibatisDAO.getData("queryAppsbyUserId", userId);
            user.setApps(userAppList);
            count = (Integer)ibatisDAO.getSingleRecord("getCountAppbindStatusByUserId", userId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询用户信息失败", e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询用户信息失败", e);
            return ConstantEnum.ERROR.toString(); // 返回失败页面
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
    
}
