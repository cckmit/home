package com.neusoft.mid.cloong.web.page.console.vlan3Phase.info;

import java.io.Serializable;

/**
 * vlan和IP段间关系bean
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月4日 下午3:56:24
 */
public class VlanIpSegmentRel implements Serializable {

    private static final long serialVersionUID = -8553898849568443093L;

    /**
     * vlan号
     */
    private String vlanId;

    /**
     * IP段唯一标识符
     */
    private String ipSegmentId;

    /**
     * 创建时间YYYYMMDDhhmmss
     */
    private String createTime;

    /**
     * 创建用户ID
     */
    private String createUser;

    /**
     * 更新时间YYYYMMDDhhmmss
     */
    private String updateTime;

    /**
     * 更新用户ID
     */
    private String updateUser;

    /**
     * 关系状态: 0：有效 1：无效 2：待生效（默认）
     */
    private String status;

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
     * 获取ipSegmentId字段数据
     * @return Returns the ipSegmentId.
     */
    public String getIpSegmentId() {
        return ipSegmentId;
    }

    /**
     * 设置ipSegmentId字段数据
     * @param ipSegmentId The ipSegmentId to set.
     */
    public void setIpSegmentId(String ipSegmentId) {
        this.ipSegmentId = ipSegmentId;
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

}
