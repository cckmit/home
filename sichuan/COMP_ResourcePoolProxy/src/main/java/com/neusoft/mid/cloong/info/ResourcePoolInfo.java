/*******************************************************************************
 * @(#)ResourceInfo.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.info;

/**
 * 资源池信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-5 下午04:14:19
 */
public class ResourcePoolInfo {
    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池名称
     */
    private String resourcePoolName;

    /**
     * 资源池URL地址
     */
    private String resourcePoolUrl;

    /**
     * 密码
     */
    private String userPassword;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolName() {
        return resourcePoolName;
    }

    public void setResourcePoolName(String resourcePoolName) {
        this.resourcePoolName = resourcePoolName;
    }

    public String getResourcePoolUrl() {
        return resourcePoolUrl;
    }

    public void setResourcePoolUrl(String resourcePoolUrl) {
        this.resourcePoolUrl = resourcePoolUrl;
    }

}
