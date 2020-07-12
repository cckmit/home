/*******************************************************************************
 * @(#)PermissionBean.java 2014年1月10日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean;

/**
 * 权限bean
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月10日 上午10:17:03
 */
public class PermissionBean extends IdentityBean{
    /**
     * 权限id
     */
    private String permissionId;
    /**
     * 父权限id
     */
    private String parentId;
    /**
     * 权限英文名称
     */
    private String englishName;
    /**
     * 权限中文名称
     */
    private String name;
    /**
     * 权限类型
     */
    private String type;
    /**
     * 权限状态
     */
    private String status;
    /**
     * 描述
     */
    private String description;
    
    public String getPermissionId() {
        return permissionId;
    }
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getEnglishName() {
        return englishName;
    }
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    

}
