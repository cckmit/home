/*******************************************************************************
 * @(#)QueryRoleListAction.java 2014年1月16日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.user;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.core.RoleStatus;
import com.neusoft.mid.cloong.identity.service.RoleSearchService;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 在用户编辑/用户管理功能中,获取到角色列表
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月16日 上午9:30:25
 */
public class QueryRoleListAction extends PageAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7998411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(QueryRoleListAction.class);

    // ~~~~~~~~~~~ Spring area START ~~~~~~~~~~~//
    /**
     * 用户操作服务service
     */
    private RoleSearchService roleService;

    // ^^^^^^^^^^^ Spring area END ^^^^^^^^^^^^^//
    //

    // ~~~~~~~~~~~ request parameter area START ~~~~~~~~~~~//

    /**
     * 用户信息列表----req parameter
     */
    private RoleBean queryRole = new RoleBean();

    // ^^^^^^^^^^^ request parameter area END ^^^^^^^^^^^^^//
    //

    // ~~~~~~~~~~~ response data area START ~~~~~~~~~~~~~//
    /**
     * 用户信息列表----rep data
     */
    private List<RoleBean> roleList = new ArrayList<RoleBean>();

    // ^^^^^^^^^^^ response data area END ^^^^^^^^^^^^^^^^^^//
    //

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
            queryRole.setStatus(RoleStatus.EFFECTIVE);
            this.roleList = this.getPage("countRole", "searchRoleList", queryRole,PageTransModel.ASYNC);
        } catch (Exception e) {
            logger.error("获取角色列表失败", e);
            return ConstantEnum.FAILURE.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取roleService字段数据
     * @return Returns the roleService.
     */
    public RoleSearchService getRoleService() {
        return roleService;
    }

    /**
     * 设置roleService字段数据
     * @param roleService The roleService to set.
     */
    public void setRoleService(RoleSearchService roleService) {
        this.roleService = roleService;
    }

    /**
     * 获取queryRole字段数据
     * @return Returns the queryRole.
     */
    public RoleBean getQueryRole() {
        return queryRole;
    }

    /**
     * 设置queryRole字段数据
     * @param queryRole The queryRole to set.
     */
    public void setQueryRole(RoleBean queryRole) {
        this.queryRole = queryRole;
    }

    /**
     * 获取roleList字段数据
     * @return Returns the roleList.
     */
    public List<RoleBean> getRoleList() {
        return roleList;
    }

    /**
     * 设置roleList字段数据
     * @param roleList The roleList to set.
     */
    public void setRoleList(List<RoleBean> roleList) {
        this.roleList = roleList;
    }

}
