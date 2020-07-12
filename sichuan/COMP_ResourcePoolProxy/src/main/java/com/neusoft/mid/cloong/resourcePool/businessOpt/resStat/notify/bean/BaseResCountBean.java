/*******************************************************************************
 * @(#)PMResCountBean.java 2014年2月12日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean;


/**
 * 资源上报基础信息Bean
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月12日 上午9:17:17
 */
public class BaseResCountBean {

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 周期结束时间
     */
    private String endTime;

    /**
     * 周期长度
     */
    private int periodTime;

    /**
     * 规格Id
     */
    private String standardId;

    /**
     * 规格名称
     */
    private String standardName;

    /**
     * 所有者ID
     */
    private String ownerId;

    /**
     * 资源池ID
     */
    private String resPoolId;

    /**
     * 资源池分区ID
     */
    private String resPoolPartId;

    /**
     * 资源池分区名称
     */
    private String resPoolName;

    /**
     * 资源池分区名称
     */
    private String resPoolPartName;
    
    /**
     * 系统创建记录时间
     */
    private String createTime;
    
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
     * 获取startTime字段数据
     * @return Returns the startTime.
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 设置startTime字段数据
     * @param startTime The startTime to set.
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取endTime字段数据
     * @return Returns the endTime.
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置endTime字段数据
     * @param endTime The endTime to set.
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取periodTime字段数据
     * @return Returns the periodTime.
     */
    public int getPeriodTime() {
        return periodTime;
    }

    /**
     * 设置periodTime字段数据
     * @param periodTime The periodTime to set.
     */
    public void setPeriodTime(int periodTime) {
        this.periodTime = periodTime;
    }

    /**
     * 获取standardId字段数据
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 设置standardId字段数据
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取standardName字段数据
     * @return Returns the standardName.
     */
    public String getStandardName() {
        return standardName;
    }

    /**
     * 设置standardName字段数据
     * @param standardName The standardName to set.
     */
    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    /**
     * 获取ownerId字段数据
     * @return Returns the ownerId.
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 设置ownerId字段数据
     * @param ownerId The ownerId to set.
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * 获取resPoolId字段数据
     * @return Returns the resPoolId.
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 设置resPoolId字段数据
     * @param resPoolId The resPoolId to set.
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 获取resPoolPartId字段数据
     * @return Returns the resPoolPartId.
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 设置resPoolPartId字段数据
     * @param resPoolPartId The resPoolPartId to set.
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    /**
     * 获取resPoolName字段数据
     * @return Returns the resPoolName.
     */
    public String getResPoolName() {
        return resPoolName;
    }

    /**
     * 设置resPoolName字段数据
     * @param resPoolName The resPoolName to set.
     */
    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    /**
     * 获取resPoolPartName字段数据
     * @return Returns the resPoolPartName.
     */
    public String getResPoolPartName() {
        return resPoolPartName;
    }

    /**
     * 设置resPoolPartName字段数据
     * @param resPoolPartName The resPoolPartName to set.
     */
    public void setResPoolPartName(String resPoolPartName) {
        this.resPoolPartName = resPoolPartName;
    }

}
