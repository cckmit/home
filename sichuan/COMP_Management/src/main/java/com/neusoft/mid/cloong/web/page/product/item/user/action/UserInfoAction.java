/*******************************************************************************
 * @(#)ItemInfoAction.java 2013-2-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.user.action;


import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.service.UserService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月28日 上午10:30:49
 */
public class UserInfoAction extends BaseAction {

    private static final long serialVersionUID = 6846575534886217608L;

    private static LogService logger = LogService.getLogger(UserInfoAction.class);

    private UserService userService;
    
    public UserService getUserService() {
        return userService;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户状态.
     */
    private String userStatus;
    
    private UserBean userBean;
    

    private String userId;


    @SuppressWarnings("unchecked")
	public String execute() {
        try {
            userBean = userService.queryUserById(userId);
        	List<UserAppBean> appList = ibatisDAO.getData("queryAppsbyUserId", userId);
        	userBean.setApps(appList);
            userBean.setCreateTime(DateParse.parse(userBean.getCreateTime()));
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, getText("user.update.info.error"), e);
            errMsg = getText("user.update.info.error");
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getText("user.update.info.success"));
            logger.debug(sb.toString());
        }
        if(userBean==null){
            msg = getText("user.isDel");
            return ConstantEnum.FAILURE.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }


    public UserBean getUserBean() {
        return userBean;
    }


    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}


	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}
