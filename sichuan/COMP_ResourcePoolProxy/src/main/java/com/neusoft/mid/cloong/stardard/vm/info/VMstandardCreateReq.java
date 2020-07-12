/*******************************************************************************
 * @(#)VMstandardCreateReq.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vm.info;

import com.neusoft.mid.cloong.stardard.StandardReqCommon;

/**
 * 虚拟机资源规格创建请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:43:39
 */
public class VMstandardCreateReq extends StandardReqCommon {

    /**
     * cpu数量
     */
    private String cpuNum;

    /**
     * 内存容量(MB)
     */
    private String memorySize;

    /**
     * 硬盘容量(GB)
     */
    private String storageSize;

    /**
     * 操作系统盘类型
     */
    private String osDiskType;

    /**
     * 虚拟网卡个数
     */
    private String vEthAdaNum;

    /**
     * 虚拟ISCSI卡个数
     */
    private String VSCSIAdaNum;

    /**
     * 虚拟FC-HBA卡个数
     */
    private String VFCHBANum;

    /**
     * 操作系统标识
     */
    private String vmOs;

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    /**
     * 获取vmOs字段数据
     * @return Returns the vmOs.
     */
    public String getVmOs() {
        return vmOs;
    }

    /**
     * 设置vmOs字段数据
     * @param vmOs The vmOs to set.
     */
    public void setVmOs(String vmOs) {
        this.vmOs = vmOs;
    }

    /**
     * 获取osDiskType字段数据
     * @return Returns the osDiskType.
     */
    public String getOsDiskType() {
        return osDiskType;
    }

    /**
     * 设置osDiskType字段数据
     * @param osDiskType The osDiskType to set.
     */
    public void setOsDiskType(String osDiskType) {
        this.osDiskType = osDiskType;
    }

    /**
     * 获取vEthAdaNum字段数据
     * @return Returns the vEthAdaNum.
     */
    public String getvEthAdaNum() {
        return vEthAdaNum;
    }

    /**
     * 设置vEthAdaNum字段数据
     * @param vEthAdaNum The vEthAdaNum to set.
     */
    public void setvEthAdaNum(String vEthAdaNum) {
        this.vEthAdaNum = vEthAdaNum;
    }

    /**
     * 获取vSCSIAdaNum字段数据
     * @return Returns the vSCSIAdaNum.
     */
    public String getVSCSIAdaNum() {
        return VSCSIAdaNum;
    }

    /**
     * 设置vSCSIAdaNum字段数据
     * @param vSCSIAdaNum The vSCSIAdaNum to set.
     */
    public void setVSCSIAdaNum(String vSCSIAdaNum) {
        VSCSIAdaNum = vSCSIAdaNum;
    }

    /**
     * 获取vFCHBANum字段数据
     * @return Returns the vFCHBANum.
     */
    public String getVFCHBANum() {
        return VFCHBANum;
    }

    /**
     * 设置vFCHBANum字段数据
     * @param vFCHBANum The vFCHBANum to set.
     */
    public void setVFCHBANum(String vFCHBANum) {
        VFCHBANum = vFCHBANum;
    }

}
