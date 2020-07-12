/*******************************************************************************
 * @(#)EBSAttachResp.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 虚拟硬盘查询应答
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-26 上午10:01:31
 */
public class RPPEBSQueryResp extends RPPBaseResp {

    private static final long serialVersionUID = -7897954506003989251L;

    /**
     * 故障描述
     */
    private String faultString;

    /**
     * 块存储名称
     */
    private String bsName;

    /**
     * 块存储容量大小
     */
    private int bsSize;

    /**
     * 块存储的性能级别
     */
    private int tier;

    /**
     * 块存储状态枚举
     */
    private EBSStatus bsStatus;

    /**
     * 块存储挂载的主机ID列表
     */
    private List<String> hostIdList = new ArrayList<String>();

    /**
     * 块存储类型
     */
    private EBSType bsType;

    /**
     * 获取faultString字段数据
     * @return Returns the faultString.
     */
    public String getFaultString() {
        return faultString;
    }

    /**
     * 设置faultString字段数据
     * @param faultString The faultString to set.
     */
    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

    /**
     * 获取bsName字段数据
     * @return Returns the bsName.
     */
    public String getBsName() {
        return bsName;
    }

    /**
     * 设置bsName字段数据
     * @param bsName The bsName to set.
     */
    public void setBsName(String bsName) {
        this.bsName = bsName;
    }

    /**
     * 获取bsSize字段数据
     * @return Returns the bsSize.
     */
    public int getBsSize() {
        return bsSize;
    }

    /**
     * 设置bsSize字段数据
     * @param bsSize The bsSize to set.
     */
    public void setBsSize(int bsSize) {
        this.bsSize = bsSize;
    }

    /**
     * 获取tier字段数据
     * @return Returns the tier.
     */
    public int getTier() {
        return tier;
    }

    /**
     * 设置tier字段数据
     * @param tier The tier to set.
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    /**
     * 获取bsStatus字段数据
     * @return Returns the bsStatus.
     */
    public EBSStatus getBsStatus() {
        return bsStatus;
    }

    /**
     * 设置bsStatus字段数据
     * @param bsStatus The bsStatus to set.
     */
    public void setBsStatus(EBSStatus bsStatus) {
        this.bsStatus = bsStatus;
    }

    /**
     * 获取hostIdList字段数据
     * @return Returns the hostIdList.
     */
    public List<String> getHostIdList() {
        return hostIdList;
    }

    /**
     * 设置hostIdList字段数据
     * @param hostIdList The hostIdList to set.
     */
    public void setHostIdList(List<String> hostIdList) {
        this.hostIdList = hostIdList;
    }

    /**
     * 获取bsType字段数据
     * @return Returns the bsType.
     */
    public EBSType getBsType() {
        return bsType;
    }

    /**
     * 设置bsType字段数据
     * @param bsType The bsType to set.
     */
    public void setBsType(EBSType bsType) {
        this.bsType = bsType;
    }

}
