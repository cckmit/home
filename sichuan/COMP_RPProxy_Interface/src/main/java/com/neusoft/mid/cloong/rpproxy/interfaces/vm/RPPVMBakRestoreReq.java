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
 * 虚拟机备份创建请求
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMBakRestoreReq extends RPPBaseReq implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 虚拟机备份任务编码
     */
    private String vmBackupId;

    /**
     * 虚拟机备份的内部编码
     */
    private String vmBackupInternalId;

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
     * 获取vmBackupInternalId字段数据
     * @return Returns the vmBackupInternalId.
     */
    public String getVmBackupInternalId() {
        return vmBackupInternalId;
    }

    /**
     * 设置vmBackupInternalId字段数据
     * @param vmBackupInternalId The vmBackupInternalId to set.
     */
    public void setVmBackupInternalId(String vmBackupInternalId) {
        this.vmBackupInternalId = vmBackupInternalId;
    }

}
