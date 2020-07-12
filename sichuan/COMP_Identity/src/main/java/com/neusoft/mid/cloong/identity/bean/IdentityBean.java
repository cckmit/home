/*******************************************************************************
 * @(#)IdentityBean.java 2014年1月10日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean;

/**
 * 用户管理基类Bean，便于未来扩展
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月10日 上午8:55:04
 */
public class IdentityBean {

    /**
     * 创建时间
     */
    protected String createTime;

    /**
     * 创建人的ID
     */
    protected String createUserId;

    /**
     * 更新时间
     */
    protected String updateTime;

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
     * 获取createUserId字段数据
     * @return Returns the createUserId.
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置createUserId字段数据
     * @param createUserId The createUserId to set.
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
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
     * 获取updateTime字段数据显示字符串
     * @return Returns the updateTime.
     */
    public String getUpdateTimeStr() {
        if (updateTime == null ||updateTime.length() < 14){
            return "";
        }
        return updateTime.substring(0, 4) + "-" + updateTime.substring(4, 6) + "-"
                + updateTime.substring(6, 8) + " " + updateTime.substring(8, 10) + ":"
                + updateTime.substring(10, 12) + ":" + updateTime.substring(12, 14);
    }

    /**
     * 获取updateTime字段数据显示字符串
     * @return Returns the updateTime.
     */
    public String getCreateTimeStr() {
        if (createTime == null || createTime.length() < 14){
            return "";
        }
        return createTime.substring(0, 4) + "-" + createTime.substring(4, 6) + "-"
                + createTime.substring(6, 8) + " " + createTime.substring(8, 10) + ":"
                + createTime.substring(10, 12) + ":" + createTime.substring(12, 14);
    }

}
