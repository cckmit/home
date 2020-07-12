/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vlan;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 块存储创建请求类
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月25日 下午4:31:14
 */
public class RPPVlanApplyReq extends RPPBaseReq implements Serializable {

    private static final long serialVersionUID = -4592576402763320376L;

    /**
     * 要创建的IP数量，仅针对公网IP有效
     */
    private Integer count;

    /**
     * 业务编码
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    /**
     * Vlan用途类型
     */
    private VlanType vlanType;

    /**
     * Vlan安全域
     */
    private ZoneType zoneType;

    /**
     * 获取count字段数据
     * @return Returns the count.
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置count字段数据
     * @param count The count to set.
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取appId字段数据
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appId字段数据
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取appName字段数据
     * @return Returns the appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置appName字段数据
     * @param appName The appName to set.
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取vlanType字段数据
     * @return Returns the vlanType.
     */
    public VlanType getVlanType() {
        return vlanType;
    }

    /**
     * 设置vlanType字段数据
     * @param vlanType The vlanType to set.
     */
    public void setVlanType(VlanType vlanType) {
        this.vlanType = vlanType;
    }

    /**
     * 获取zoneType字段数据
     * @return Returns the zoneType.
     */
    public ZoneType getZoneType() {
        return zoneType;
    }

    /**
     * 设置zoneType字段数据
     * @param zoneType The zoneType to set.
     */
    public void setZoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

}
