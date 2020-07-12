/*******************************************************************************
 * @(#)VMStateQueueItem.java 2014-4-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vmbak;


/**
 * 查询时使用的虚拟机备份状态实例.做为查询Bean使用.
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-4-24 上午11:47:22
 */
public class VmBakStateQueueItemInfo{


    /**
     * 虚拟机操作流水号
     */
    private String serialNum;

    /**
     * 虚拟机备份编码
     */
    private String vmBakId;

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 资源池密钥
     */
    private String password;

    /**
     * 资源池URL
     */
    private String resourceUrl;

    /**
     * 获取serialNum字段数据
     * @return Returns the serialNum.
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * 设置serialNum字段数据
     * @param serialNum The serialNum to set.
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * 获取vmBakId字段数据
     * @return Returns the vmBakId.
     */
    public String getVmBakId() {
        return vmBakId;
    }

    /**
     * 设置vmBakId字段数据
     * @param vmBakId The vmBakId to set.
     */
    public void setVmBakId(String vmBakId) {
        this.vmBakId = vmBakId;
    }

    /**
     * 获取resourcePoolId字段数据
     * @return Returns the resourcePoolId.
     */
    public String getResourcePoolId() {
        return resourcePoolId;
    }

    /**
     * 设置resourcePoolId字段数据
     * @param resourcePoolId The resourcePoolId to set.
     */
    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    /**
     * 获取resourcePoolPartId字段数据
     * @return Returns the resourcePoolPartId.
     */
    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    /**
     * 设置resourcePoolPartId字段数据
     * @param resourcePoolPartId The resourcePoolPartId to set.
     */
    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    /**
     * 获取password字段数据
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password字段数据
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取resourceUrl字段数据
     * @return Returns the resourceUrl.
     */
    public String getResourceUrl() {
        return resourceUrl;
    }

    /**
     * 设置resourceUrl字段数据
     * @param resourceUrl The resourceUrl to set.
     */
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

}
