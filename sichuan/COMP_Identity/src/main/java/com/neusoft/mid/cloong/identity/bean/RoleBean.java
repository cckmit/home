/*******************************************************************************
 * @(#)RoleBean.java 2014年1月10日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.identity.bean.core.RoleStatus;

/**
 * 角色bean
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月10日 上午10:15:27
 */
public class RoleBean extends IdentityBean {
    /**
     * 角色id
     */
    private String roleId;
    /**
     *角色名称 
     */
    private String roleName;
    /**
     * 角色状态
     */
    private RoleStatus status;
    /**
     * 描述
     */
    private String description;
    /**
     * 权限列表
     */
    private List<PermissionBean> permList = new ArrayList<PermissionBean>();
    
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public RoleStatus getStatus() {
        return status;
    }
    public void setStatus(RoleStatus status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<PermissionBean> getPermList() {
        return permList;
    }
    public void setPermList(List<PermissionBean> permList) {
        this.permList = permList;
    }
}
