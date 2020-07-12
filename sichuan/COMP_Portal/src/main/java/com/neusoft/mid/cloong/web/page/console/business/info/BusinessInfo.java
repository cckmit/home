/*******************************************************************************
 * @(#)BussniessInfo.java 2014年1月28日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 业务信息
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月28日 下午2:18:42
 */
public class BusinessInfo implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 业务的创建用户
     */
    private String createUserId;

    /**
     * 业务的创建时间
     */
    private String createTime;

    /**
     * 业务更新的用户ID
     */
    private String updateUserId;

    /**
     * 业务更新时间
     */
    private String updateTime;

    /**
     * 资源池分区
     */
    private String resPoolId;

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 资源列表
     */
    private List<ResourceInfo> resourceList = new ArrayList<ResourceInfo>();

    /**
     * 获取businessId字段数据
     * @return Returns the businessId.
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * 设置businessId字段数据
     * @param businessId The businessId to set.
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取name字段数据
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name字段数据
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取description字段数据
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description字段数据
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获取updateUserId字段数据
     * @return Returns the updateUserId.
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置updateUserId字段数据
     * @param updateUserId The updateUserId to set.
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
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
     * 获取resourceList字段数据
     * @return Returns the resourceList.
     */
    public List<ResourceInfo> getResourceList() {
        return resourceList;
    }

    /**
     * 设置resourceList字段数据
     * @param resourceList The resourceList to set.
     */
    public void setResourceList(List<ResourceInfo> resourceList) {
        this.resourceList = resourceList;
    }

    /**
     * 获取updateTime字段数据显示字符串
     * @return Returns the updateTime.
     */
    public String getUpdateTimeStr() {
        if (updateTime == null || updateTime.length() < 14) {
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
        if (createTime == null || createTime.length() < 14) {
            return "";
        }
        return createTime.substring(0, 4) + "-" + createTime.substring(4, 6) + "-"
                + createTime.substring(6, 8) + " " + createTime.substring(8, 10) + ":"
                + createTime.substring(10, 12) + ":" + createTime.substring(12, 14);
    }

}
