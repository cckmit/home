/*******************************************************************************
 * @(#)VMstandardCreateReq.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vm.info;

import com.neusoft.mid.cloong.stardard.StandardReqCommon;

/**
 * 虚拟机资源规格修改请求
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:43:39
 */
public class VMstandardModifyReq extends StandardReqCommon {

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
    
}
