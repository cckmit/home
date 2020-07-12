/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 虚拟机备份任务删除请求
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMBakDeleteReq extends RPPBaseReq implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 虚拟机备份任务编码
     */
    private String vmBackupId;

    /**
     * 需要删除的虚拟机备份ID
     */
    private String vmBakInternalId;

    /**
     * 获取vmBackupId字段数据
     * @return Returns the vmBackupId.
     */
    public String getVmBackupId() {
        return vmBackupId;
    }

    /**
     * 设置vmBackupId字段数据
     * @param vmBackupId The vmBackupId to set.
     */
    public void setVmBackupId(String vmBackupId) {
        this.vmBackupId = vmBackupId;
    }

    /**
     * 获取vmBakInternalId字段数据
     * @return Returns the vmBakInternalId.
     */
    public String getVmBakInternalId() {
        return vmBakInternalId;
    }

    /**
     * 设置vmBakInternalId字段数据
     * @param vmBakInternalId The vmBakInternalId to set.
     */
    public void setVmBakInternalId(String vmBakInternalId) {
        this.vmBakInternalId = vmBakInternalId;
    }
    
    

}
