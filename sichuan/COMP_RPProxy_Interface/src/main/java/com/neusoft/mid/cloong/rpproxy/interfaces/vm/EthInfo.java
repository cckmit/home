/*******************************************************************************
 * @(#)EthInfo.java 2015年2月25日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;

/**
 * 虚拟机网卡信息
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 下午1:40:48
 */
public class EthInfo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2979998278782462490L;

    /**
     * 网卡用户，枚举类型
     */
    private VMEthPurpose purpose;

    /**
     * 网卡名称，例如eth0,eth1
     */
    private String name;

    /**
     * 网卡IP
     */
    private String ip;

    /**
     * 网卡资源掩码
     */
    private String subNetMask;

    /**
     * 网卡默认网关IP地址
     */
    private String defaultGateWay;

    /**
     * 所在VLAN号
     */
    private String vlanId;

    /**
     * 获取purpose字段数据
     * @return Returns the purpose.
     */
    public VMEthPurpose getPurpose() {
        return purpose;
    }

    /**
     * 设置purpose字段数据
     * @param purpose The purpose to set.
     */
    public void setPurpose(VMEthPurpose purpose) {
        this.purpose = purpose;
    }

    /**
     * 获取name字段数据
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name字段数据
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取ip字段数据
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip字段数据
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取subNetMask字段数据
     * @return Returns the subNetMask.
     */
    public String getSubNetMask() {
        return subNetMask;
    }

    /**
     * 设置subNetMask字段数据
     * @param subNetMask The subNetMask to set.
     */
    public void setSubNetMask(String subNetMask) {
        this.subNetMask = subNetMask;
    }

    /**
     * 获取defaultGateWay字段数据
     * @return Returns the defaultGateWay.
     */
    public String getDefaultGateWay() {
        return defaultGateWay;
    }

    /**
     * 设置defaultGateWay字段数据
     * @param defaultGateWay The defaultGateWay to set.
     */
    public void setDefaultGateWay(String defaultGateWay) {
        this.defaultGateWay = defaultGateWay;
    }

    /**
     * 获取vlanId字段数据
     * @return Returns the vlanId.
     */
    public String getVlanId() {
        return vlanId;
    }

    /**
     * 设置vlanId字段数据
     * @param vlanId The vlanId to set.
     */
    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

}
