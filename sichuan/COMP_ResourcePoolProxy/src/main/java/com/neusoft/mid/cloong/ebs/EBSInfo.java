/*******************************************************************************
 * @(#)VMInfo.java 2013-2-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs;

/**
 * 虚拟机状态信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-26 下午01:45:23
 */
public class EBSInfo {
    /**
     * 虚拟机编码
     */
    private String ebsId;

    /**
     * 虚拟机状态
     */
    private String ebsStatus;

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

    /**
     * 获取ebsStatus字段数据
     * @return Returns the ebsStatus.
     */
    public String getEbsStatus() {
        return ebsStatus;
    }

    /**
     * 设置ebsStatus字段数据
     * @param ebsStatus The ebsStatus to set.
     */
    public void setEbsStatus(String ebsStatus) {
        this.ebsStatus = ebsStatus;
    }

}
