/*******************************************************************************
 * @(#)LogicVlanInfo.java 2014年6月3日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm.info;

/**
 * 逻辑VLAN信息
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月3日 下午4:58:30
 */
public class LogicVlanInfo {

    /**
     * 逻辑vlanId
     */
    private String loginVlanId;
    
    /**
     * 逻辑vlanName
     */
    private String loginVlanName;

    /**
     * 获取loginVlanId字段数据
     * @return Returns the loginVlanId.
     */
    public String getLoginVlanId() {
        return loginVlanId;
    }

    /**
     * 设置loginVlanId字段数据
     * @param loginVlanId The loginVlanId to set.
     */
    public void setLoginVlanId(String loginVlanId) {
        this.loginVlanId = loginVlanId;
    }

    /**
     * 获取loginVlanName字段数据
     * @return Returns the loginVlanName.
     */
    public String getLoginVlanName() {
        return loginVlanName;
    }

    /**
     * 设置loginVlanName字段数据
     * @param loginVlanName The loginVlanName to set.
     */
    public void setLoginVlanName(String loginVlanName) {
        this.loginVlanName = loginVlanName;
    }
    
}
