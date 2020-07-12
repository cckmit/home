/*******************************************************************************
 * @(#)UserUpdateAction.java 2014年1月11日
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
 * 用户自信息修改初始页面
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月11日 上午11:38:40
 */
public class UserUpdateAction extends BaseAction {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5822274825990788396L;
    
    private static LogService logger = LogService.getLogger(UserUpdateAction.class);
    
    /**
     * 用户bean对象
     */
    private UserBean user = new UserBean();
    /**
     * 用户绑定未审批的业务个数.
     */
    private int count;

    /**
     * 
     * execute 修改页面跳转
     * @return success
     */
    @SuppressWarnings("unchecked")
	public String execute() {
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
