/*******************************************************************************
 * @(#)PMStandardCreateReq.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.pm.info;

import com.neusoft.mid.cloong.stardard.StandardReqCommon;

/**
 * 物理机资源规格创建请求
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:43:39
 */
public class PMStandardCreateReq extends StandardReqCommon {

    /**
     * 硬盘容量(GB)
     */
    private String storageSize;

    /**
     * CPU类型
     */
    private String cpuType;

    /**
     * 内存容量(MB)
     */
    private String memorySize;

    /**
     * 服务器类型
     */
    private String serverType;

    /**
     * 操作系统
     */
    private String os;

    private String EthAdaNum;

    private String EthAdaType;

    private String SCSIAdaNum;

    private String HBANum;

    private String HBAType;

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    /**
     * 获取cpuType字段数据
     * @return Returns the cpuType.
     */
    public String getCpuType() {
        return cpuType;
    }

    /**
     * 设置cpuType字段数据
     * @param cpuType The cpuType to set.
     */
    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    /**
     * 获取memorySize字段数据
     * @return Returns the memorySize.
     */
    public String getMemorySize() {
        return memorySize;
    }

    /**
     * 设置memorySize字段数据
     * @param memorySize The memorySize to set.
     */
    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    /**
     * 获取serverType字段数据
     * @return Returns the serverType.
     */
    public String getServerType() {
        return serverType;
    }

    /**
     * 设置serverType字段数据
     * @param serverType The serverType to set.
     */
    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getEthAdaNum() {
        return EthAdaNum;
    }

    public void setEthAdaNum(String ethAdaNum) {
        EthAdaNum = ethAdaNum;
    }

    public String getEthAdaType() {
        return EthAdaType;
    }

    public void setEthAdaType(String ethAdaType) {
        EthAdaType = ethAdaType;
    }

    public String getSCSIAdaNum() {
        return SCSIAdaNum;
    }

    public void setSCSIAdaNum(String sCSIAdaNum) {
        SCSIAdaNum = sCSIAdaNum;
    }

    public String getHBANum() {
        return HBANum;
    }

    public void setHBANum(String hBANum) {
        HBANum = hBANum;
    }

    public String getHBAType() {
        return HBAType;
    }

    public void setHBAType(String hBAType) {
        HBAType = hBAType;
    }

}
