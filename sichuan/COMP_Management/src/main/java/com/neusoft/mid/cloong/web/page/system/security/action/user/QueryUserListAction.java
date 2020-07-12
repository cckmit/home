/*******************************************************************************
 * @(#)QueryUserListAction.java 2013-3-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.user;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.AppStatus;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.cloong.identity.bean.query.QueryUserInfo;
import com.neusoft.mid.cloong.identity.service.UserService;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询用户列表
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2014-1-10 上午10:16:18
 */
public class QueryUserListAction extends PageAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(QueryUserListAction.class);

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
    private QueryUserInfo queryUser = new QueryUserInfo();

    // ^^^^^^^^^^^ request parameter area END ^^^^^^^^^^^^^//
    //

    // ~~~~~~~~~~~ response data area START ~~~~~~~~~~~~~//
    /**
     * 用户信息列表----rep data
     */
    private List<UserBean> userList = new ArrayList<UserBean>();

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
            if (queryUser.getAfterCreateTime() != null
                    && queryUser.getAfterCreateTime().length() == 10) {
                queryUser.setAfterCreateTime(queryUser.getAfterCreateTime().replaceAll("-", ""));
            }
            if (queryUser.getBeforeCreateTime() != null
                    && queryUser.getBeforeCreateTime().length() == 10) {
                queryUser.setBeforeCreateTime(queryUser.getBeforeCreateTime().replaceAll("-", ""));
            }

            // 将提交的状态码转换为枚举
            queryUser.setStatus(UserStatus.getEnum(queryUser.getQueryStatus()));
            this.userList = this.getPage("countQueryUser", "queryUserInfos", queryUser);
            
            //绑定角色和业务信息
            for(UserBean user:userList){
            	List<UserAppBean> appList = ibatisDAO.getData("queryBingAppsbyUserId", user.getUserId());
            	List<RoleBean> roleList = ibatisDAO.getData("queryUserRoleByUserId", user.getUserId());
            	user.setRoles(roleList);
            	user.setApps(appList);
            }
            if (queryUser.getAfterCreateTime() != null
                    && queryUser.getAfterCreateTime().length() == 8) {
                queryUser.setAfterCreateTime(queryUser.getAfterCreateTime().substring(0, 4) + "-"
                        + queryUser.getAfterCreateTime().substring(4, 6) + "-"
                        + queryUser.getAfterCreateTime().substring(6));
            }
            if (queryUser.getBeforeCreateTime() != null
                    && queryUser.getBeforeCreateTime().length() == 8) {
                queryUser.setBeforeCreateTime(queryUser.getBeforeCreateTime().substring(0, 4) + "-"
                        + queryUser.getBeforeCreateTime().substring(4, 6) + "-"
                        + queryUser.getBeforeCreateTime().substring(6));
            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取userList字段数据
     * @return Returns the userList.
     */
    public List<UserBean> getUserList() {
        return userList;
    }

    /**
     * 设置userList字段数据
     * @param userList The userList to set.
     */
    public void setUserList(List<UserBean> userList) {
        this.userList = userList;
    }

    /**
     * 获取queryUser字段数据
     * @return Returns the queryUser.
     */
    public QueryUserInfo getQueryUser() {
        return queryUser;
    }

    /**
     * 设置queryUser字段数据
     * @param queryUser The queryUser to set.
     */
    public void setQueryUser(QueryUserInfo queryUser) {
        this.queryUser = queryUser;
    }

    /**
     * 设置userService字段数据
     * @param userService The userService to set.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
