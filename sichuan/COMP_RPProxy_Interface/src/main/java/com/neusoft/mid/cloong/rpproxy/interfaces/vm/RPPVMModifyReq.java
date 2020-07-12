/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 虚拟机创建请求类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMModifyReq extends RPPBaseReq implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 虚拟机ID
     */
    private String vmId;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 虚拟机vCPU个数
     */
    private Integer cpuNum;

    /**
     * 虚拟机内存大小(GB)
     */
    private Integer memorySizsMB;

    /**
     * 本地磁盘类型,枚举型
     */
    private VMOSDiskType osDiskType;

    /**
     * 虚拟机本地硬盘大小(GB)
     */
    private Integer osSizeGB;

    /**
     * 虚拟SCSI卡个数,可选
     */
    private Integer vSCSIAdaNum;

    /**
     * 虚拟网卡个数
     */
    private Integer VEthAdaNum;

    /**
     * 镜像ID
     */
    private String osId;

    /**
     * 网卡信息列表
     */
    private List<EthInfo> ethList = new ArrayList<EthInfo>();

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
     * 获取cpuNum字段数据
     * @return Returns the cpuNum.
     */
    public Integer getCpuNum() {
        return cpuNum;
    }

    /**
     * 设置cpuNum字段数据
     * @param cpuNum The cpuNum to set.
     */
    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
    }

    /**
     * 获取memorySizsMB字段数据
     * @return Returns the memorySizsMB.
     */
    public Integer getMemorySizsMB() {
        return memorySizsMB;
    }

    /**
     * 设置memorySizsMB字段数据
     * @param memorySizsMB The memorySizsMB to set.
     */
    public void setMemorySizsMB(Integer memorySizsMB) {
        this.memorySizsMB = memorySizsMB;
    }

    /**
     * 获取osDiskType字段数据
     * @return Returns the osDiskType.
     */
    public VMOSDiskType getOsDiskType() {
        return osDiskType;
    }

    /**
     * 设置osDiskType字段数据
     * @param osDiskType The osDiskType to set.
     */
    public void setOsDiskType(VMOSDiskType osDiskType) {
        this.osDiskType = osDiskType;
    }

    /**
     * 获取osSizeGB字段数据
     * @return Returns the osSizeGB.
     */
    public Integer getOsSizeGB() {
        return osSizeGB;
    }

    /**
     * 设置osSizeGB字段数据
     * @param osSizeGB The osSizeGB to set.
     */
    public void setOsSizeGB(Integer osSizeGB) {
        this.osSizeGB = osSizeGB;
    }

    /**
     * 获取vSCSIAdaNum字段数据
     * @return Returns the vSCSIAdaNum.
     */
    public Integer getvSCSIAdaNum() {
        return vSCSIAdaNum;
    }

    /**
     * 设置vSCSIAdaNum字段数据
     * @param vSCSIAdaNum The vSCSIAdaNum to set.
     */
    public void setvSCSIAdaNum(Integer vSCSIAdaNum) {
        this.vSCSIAdaNum = vSCSIAdaNum;
    }

    /**
     * 获取vEthAdaNum字段数据
     * @return Returns the vEthAdaNum.
     */
    public Integer getVEthAdaNum() {
        return VEthAdaNum;
    }

    /**
     * 设置vEthAdaNum字段数据
     * @param vEthAdaNum The vEthAdaNum to set.
     */
    public void setVEthAdaNum(Integer vEthAdaNum) {
        VEthAdaNum = vEthAdaNum;
    }

    /**
     * 获取osId字段数据
     * @return Returns the osId.
     */
    public String getOsId() {
        return osId;
    }

    /**
     * 设置osId字段数据
     * @param osId The osId to set.
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * 获取ethList字段数据
     * @return Returns the ethList.
     */
    public List<EthInfo> getEthList() {
        return ethList;
    }

    /**
     * 设置ethList字段数据
     * @param ethList The ethList to set.
     */
    public void setEthList(List<EthInfo> ethList) {
        this.ethList = ethList;
    }

    

}
