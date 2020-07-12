/*******************************************************************************
 * @(#)PMStandardModifyReq.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.pm.info;

import com.neusoft.mid.cloong.stardard.StandardReqCommon;

/**
 * 物理机资源规格修改请求
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-18 上午10:43:39
 */
public class PMStandardModifyReq extends StandardReqCommon {

    /**
     * 硬盘容量(GB)
     */
    private String storageSize;

    /**
     * CPU数量
     */
    private String cpuNum;

    /**
     * 内存容量(MB)
     */
    private String memorySzie;

    /**
     * 服务器类型
     */
    private String serverType;

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    /**
     * 获取cpuNum字段数据
     * @return Returns the cpuNum.
     */
    public String getCpuNum() {
        return cpuNum;
    }

    /**
     * 设置cpuNum字段数据
     * @param cpuNum The cpuNum to set.
     */
    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    /**
     * 获取memorySzie字段数据
     * @return Returns the memorySzie.
     */
    public String getMemorySzie() {
        return memorySzie;
    }

    /**
     * 设置memorySzie字段数据
     * @param memorySzie The memorySzie to set.
     */
    public void setMemorySzie(String memorySzie) {
        this.memorySzie = memorySzie;
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

}
