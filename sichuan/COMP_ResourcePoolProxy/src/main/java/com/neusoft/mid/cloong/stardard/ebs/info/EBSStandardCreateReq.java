/*******************************************************************************
 * @(#)VMstandardCreateReq.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.ebs.info;

import com.neusoft.mid.cloong.stardard.StandardReqCommon;

/**
 * 虚拟硬盘资源规格创建请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:43:39
 */
public class EBSStandardCreateReq extends StandardReqCommon {

    /**
     * 硬盘容量(GB)
     */
    private String storageSize;

    /**
     * 存储性能级别
     */
    private String tier;

    /**
     * raid级别
     */
    private String raid;

    /**
     * 存储网络
     */
    private String storageNet;

    /**
     * 卷个数
     */
    private String volNum;

    /**
     * 0：通用块存储（虚拟机和物理机都可以使用） 1：虚拟机使用块存储 2：物理机使用块存储
     */
    private String ebsResourceType;

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    /**
     * 获取tier字段数据
     * @return Returns the tier.
     */
    public String getTier() {
        return tier;
    }

    /**
     * 设置tier字段数据
     * @param tier The tier to set.
     */
    public void setTier(String tier) {
        this.tier = tier;
    }

    /**
     * 获取raid字段数据
     * @return Returns the raid.
     */
    public String getRaid() {
        return raid;
    }

    /**
     * 设置raid字段数据
     * @param raid The raid to set.
     */
    public void setRaid(String raid) {
        this.raid = raid;
    }

    /**
     * 获取storageNet字段数据
     * @return Returns the storageNet.
     */
    public String getStorageNet() {
        return storageNet;
    }

    /**
     * 设置storageNet字段数据
     * @param storageNet The storageNet to set.
     */
    public void setStorageNet(String storageNet) {
        this.storageNet = storageNet;
    }

    /**
     * 获取volNum字段数据
     * @return Returns the volNum.
     */
    public String getVolNum() {
        return volNum;
    }

    /**
     * 设置volNum字段数据
     * @param volNum The volNum to set.
     */
    public void setVolNum(String volNum) {
        this.volNum = volNum;
    }

    public String getEbsResourceType() {
        return ebsResourceType;
    }

    public void setEbsResourceType(String ebsResourceType) {
        this.ebsResourceType = ebsResourceType;
    }

}
