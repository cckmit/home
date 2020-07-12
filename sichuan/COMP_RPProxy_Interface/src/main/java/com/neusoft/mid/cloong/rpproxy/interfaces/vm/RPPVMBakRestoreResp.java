/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 虚拟机备份创建应答
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMBakRestoreResp extends RPPBaseResp implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 恢复的虚拟机ID
     */
    private String vmId;

    /**
     * 恢复的虚拟机名称
     */
    private String vmName;

    /**
     * 虚拟机恢复的时间
     */
    private Date acceptTime;

    /**
     * 恢复后虚拟机的Vlan编号
     */
    private List<VlanIpInfo> vlanIpInfo = new ArrayList<RPPVMBakRestoreResp.VlanIpInfo>();

    /**
     * 获取faultstring字段数据
     * @return Returns the faultstring.
     */
    public String getFaultstring() {
        return faultstring;
    }

    /**
     * 设置faultstring字段数据
     * @param faultstring The faultstring to set.
     */
    public void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

    /**
     * 获取vmId字段数据
     * @return Returns the vmId.
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 设置vmId字段数据
     * @param vmId The vmId to set.
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 获取vmName字段数据
     * @return Returns the vmName.
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * 设置vmName字段数据
     * @param vmName The vmName to set.
     */
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    /**
     * 获取acceptTime字段数据
     * @return Returns the acceptTime.
     */
    public Date getAcceptTime() {
        return acceptTime;
    }

    /**
     * 设置acceptTime字段数据
     * @param acceptTime The acceptTime to set.
     */
    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    /**
     * 获取vlanIpInfo字段数据
     * @return Returns the vlanIpInfo.
     */
    public List<VlanIpInfo> getVlanIpInfo() {
        return vlanIpInfo;
    }

    /**
     * 设置vlanIpInfo字段数据
     * @param vlanIpInfo The vlanIpInfo to set.
     */
    public void setVlanIpInfo(List<VlanIpInfo> vlanIpInfo) {
        this.vlanIpInfo = vlanIpInfo;
    }

    /**
     * Vlan和IP信息
     * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
     * @version $Revision 1.0 $ 2015年3月2日 下午2:29:53
     */
    public static class VlanIpInfo implements Serializable {

        /**
         * vlan编号
         */
        private String vlanId;

        /**
         * 虚拟机私网IP
         */
        private String privateIp;

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

        /**
         * 获取privateIp字段数据
         * @return Returns the privateIp.
         */
        public String getPrivateIp() {
            return privateIp;
        }

        /**
         * 设置privateIp字段数据
         * @param privateIp The privateIp to set.
         */
        public void setPrivateIp(String privateIp) {
            this.privateIp = privateIp;
        }

    }

}
