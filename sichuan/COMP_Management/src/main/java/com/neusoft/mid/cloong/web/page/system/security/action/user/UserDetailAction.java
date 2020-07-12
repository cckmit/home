/*******************************************************************************
 * @(#)QueryUserListAction.java 2013-3-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.user;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.service.UserService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户详细信息查询Action
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2014-1-10 上午10:16:18
 */
public class UserDetailAction extends BaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(UserDetailAction.class);

    // ~~~~~~~~~~~ Spring area START ~~~~~~~~~~~//
    /**
     * 用户操作服务service
     */
    private UserService userService;

    // ^^^^^^^^^^^ Spring area END ^^^^^^^^^^^^^//
    //

    // ~~~~~~~~~~~ request parameter area START ~~~~~~~~~~~//

    /**
     * 用户信息列表----req parameter
     */
    private String userId = new String();

    // ^^^^^^^^^^^ request parameter area END ^^^^^^^^^^^^^//
    //

    // ~~~~~~~~~~~ response data area START ~~~~~~~~~~~~~//
    /**
     * 用户信息----rep data
     */
    private UserBean user = new UserBean();
    

    // ^^^^^^^^^^^ response data area END ^^^^^^^^^^^^^^^^^^//
    //
	/**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @SuppressWarnings("unchecked")
	public String execute() {
        try {
            this.user = this.userService.queryUserById(userId);
            //查询用户绑定的业务
          //查询用户绑定的业务信息
            List<UserAppBean> userAppList = ibatisDAO.getData("queryAppsbyUserId", userId);
            user.setApps(userAppList);
            //查询审批信息
            String auditInfo = (String)ibatisDAO.getSingleRecord("queryAuditInfo", userId);
            user.setAuditInfo(auditInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询用户信息失败",e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取user字段数据
     * @return Returns the user.
     */
    public UserBean getUser() {
        return user;
    }

    /**
     * 设置user字段数据
     * @param user The user to set.
     */
    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     * 获取userId字段数据
     * @return Returns the userId.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置userId字段数据
     * @param userId The userId to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 设置userService字段数据
     * @param userService The userService to set.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
