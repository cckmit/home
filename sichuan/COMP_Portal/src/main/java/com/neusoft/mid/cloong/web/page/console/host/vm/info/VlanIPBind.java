/*******************************************************************************
 * @(#)VlanIPBind.java 2015-3-11
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm.info;

/**
 * comp_vlan_ipsegment_bind_t
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2015-3-11 下午7:50:33
 */
public class VlanIPBind {

    /**
     * VLAN_ID
     */
    private String vlanId;

    /**
     * IPSEGMENT_ID
     */
    private String ipSegment;

    /**
     * Status
     */
    private String status;

    /**
     * CREATE_TIME
     */
    private String createTime;

    /**
     * CREATE_USER
     */

    private String createUser;

    /**
     * UPDATE_TIME
     */
    private String updateTime;

    /**
     * UPDATE_USER
     */
    private String updateUser;

    /**
     * 获取vlanId字段数据
     * @return Returns the vlanId.
     */
    public String getVlanId() {
        return vlanId;
    }

    /**
     * 设置vlanId字段数据
     * @param vlanId The vlanId to set.
     */
    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    /**
     * 获取ipSegment字段数据
     * @return Returns the ipSegment.
     */
    public String getIpSegment() {
        return ipSegment;
    }

    /**
     * 设置ipSegment字段数据
     * @param ipSegment The ipSegment to set.
     */
    public void setIpSegment(String ipSegment) {
        this.ipSegment = ipSegment;
    }

    /**
     * 获取status字段数据
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status字段数据
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取createTime字段数据
     * @return Returns the createTime.
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置createTime字段数据
     * @param createTime The createTime to set.
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取createUser字段数据
     * @return Returns the createUser.
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置createUser字段数据
     * @param createUser The createUser to set.
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取updateTime字段数据
     * @return Returns the updateTime.
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置updateTime字段数据
     * @param updateTime The updateTime to set.
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取updateUser字段数据
     * @return Returns the updateUser.
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置updateUser字段数据
     * @param updateUser The updateUser to set.
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
