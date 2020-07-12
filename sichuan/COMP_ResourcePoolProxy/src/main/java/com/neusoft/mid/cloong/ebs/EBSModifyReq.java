/*******************************************************************************
 * @(#)EBSDeleteReq.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 虚拟硬盘变更请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 下午03:09:08
 */
public class EBSModifyReq extends ReqBodyInfo {
    /**
     * EBS编码
     */
    private String ebsId;

    /**
     * EBS名称
     */
    private String ebsName;

    /**
     * EBS容量大小 GB
     */
    private int diskSize;

    public String getEbsId() {
        return ebsId;
    }

    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

    /**
     * 获取ebsName字段数据
     * @return Returns the ebsName.
     */
    public String getEbsName() {
        return ebsName;
    }

    /**
     * 设置ebsName字段数据
     * @param ebsName The ebsName to set.
     */
    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

    /**
     * 获取diskSize字段数据
     * @return Returns the diskSize.
     */
    public int getDiskSize() {
        return diskSize;
    }

    /**
     * 设置diskSize字段数据
     * @param diskSize The diskSize to set.
     */
    public void setDiskSize(int diskSize) {
        this.diskSize = diskSize;
    }

}
