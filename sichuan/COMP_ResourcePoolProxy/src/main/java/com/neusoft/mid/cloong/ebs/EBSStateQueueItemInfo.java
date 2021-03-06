/*******************************************************************************
 * @(#)VMStateQueueItemInfo.java 2014-4-3
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

/**
 * 查询时使用的虚拟机状态实例.做为查询Bean使用.
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-4-3 下午2:47:23
 */
public class EBSStateQueueItemInfo {

    /**
     * 虚拟机操作流水号
     */
    private String serialNum;

    /**
     * 虚拟机编码
     */
    private String ebsId;

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 资源池URL
     */
    private String resourceUrl;

    /**
     * 查询次数，默认为1
     */
    private int time = 1;

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * 获取ebsId字段数据
     * @return Returns the ebsId.
     */
    public String getEbsId() {
        return ebsId;
    }

    /**
     * 设置ebsId字段数据
     * @param ebsId The ebsId to set.
     */
    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

}
