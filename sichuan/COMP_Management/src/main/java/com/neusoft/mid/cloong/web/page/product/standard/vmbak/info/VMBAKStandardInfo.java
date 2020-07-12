/*******************************************************************************
 * @(#)VMBAKStandardInfo.java 2014年1月21日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vmbak.info;

/**
 * 虚拟机备份规格实例
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月21日 上午11:27:43
 */
public class VMBAKStandardInfo {

    private String standardName;

    private String standardId;

    private long spaceSize;

    private String description;

    private String status;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;
    
    /**
     * 使用状态
     */
    private String useStatus;

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public long getSpaceSize() {
        return spaceSize;
    }

    public void setSpaceSize(long spaceSize) {
        this.spaceSize = spaceSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }
    
    /**
     * 
     * getCreateTimeStr 获取创建时间字符串
     * @return 时间格式
     */
    public String getCreateTimeStr() {
        if (createTime == null ||createTime.length() < 14){
            return "";
        }
        return createTime.substring(0, 4) + "-" + createTime.substring(4, 6) + "-"
                + createTime.substring(6, 8) + " " + createTime.substring(8, 10) + ":"
                + createTime.substring(10, 12) + ":" + createTime.substring(12, 14);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    /**
     * 获取useStatus字段数据
     * @return Returns the useStatus.
     */
    public String getUseStatus() {
        return useStatus;
    }
    /**
     * 设置useStatus字段数据
     * @param useStatus The useStatus to set.
     */
    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }
    
}
