/*******************************************************************************
 * @(#)CabinetInfo.java 2015年1月13日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.info;

/**
 * 机柜信息
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2015年1月13日 下午2:06:20
 */
public class CabinetInfo {

    /**
     * 机柜ID
     */
    private String cabinetId;

    /**
     * 机柜名称
     */
    private String cabinetName;

    /**
     * 厂家名称
     */
    private String manufactoryName;

    /**
     * 机柜型号
     */
    private String brandModel;

    /**
     * 描述
     */
    private String description;

    /**
     * 所属机房
     */
    private String machinerRoomId;

    /**
     * 资源池ID
     */
    private String resPoolId;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 获取cabinetId字段数据
     * @return Returns the cabinetId.
     */
    public String getCabinetId() {
        return cabinetId;
    }

    /**
     * 设置cabinetId字段数据
     * @param cabinetId The cabinetId to set.
     */
    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    /**
     * 获取cabinetName字段数据
     * @return Returns the cabinetName.
     */
    public String getCabinetName() {
        return cabinetName;
    }

    /**
     * 设置cabinetName字段数据
     * @param cabinetName The cabinetName to set.
     */
    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    /**
     * 获取manufactoryName字段数据
     * @return Returns the manufactoryName.
     */
    public String getManufactoryName() {
        return manufactoryName;
    }

    /**
     * 设置manufactoryName字段数据
     * @param manufactoryName The manufactoryName to set.
     */
    public void setManufactoryName(String manufactoryName) {
        this.manufactoryName = manufactoryName;
    }

    /**
     * 获取brandModel字段数据
     * @return Returns the brandModel.
     */
    public String getBrandModel() {
        return brandModel;
    }

    /**
     * 设置brandModel字段数据
     * @param brandModel The brandModel to set.
     */
    public void setBrandModel(String brandModel) {
        this.brandModel = brandModel;
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
     * 获取machinerRoomId字段数据
     * @return Returns the machinerRoomId.
     */
    public String getMachinerRoomId() {
        return machinerRoomId;
    }

    /**
     * 设置machinerRoomId字段数据
     * @param machinerRoomId The machinerRoomId to set.
     */
    public void setMachinerRoomId(String machinerRoomId) {
        this.machinerRoomId = machinerRoomId;
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

}
