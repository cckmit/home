/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;
import java.util.Date;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 虚拟机备份创建应答
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMBakCreateResp extends RPPBaseResp implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 虚拟机备份ID
     */
    private String vmBackupId;

    /**
     * 备份时间
     */
    private Date backupTime;

    /**
     * 备份大小
     */
    private float backupSize;

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
     * 获取backupTime字段数据
     * @return Returns the backupTime.
     */
    public Date getBackupTime() {
        return backupTime;
    }

    /**
     * 设置backupTime字段数据
     * @param backupTime The backupTime to set.
     */
    public void setBackupTime(Date backupTime) {
        this.backupTime = backupTime;
    }

    /**
     * 获取backupSize字段数据
     * @return Returns the backupSize.
     */
    public float getBackupSize() {
        return backupSize;
    }

    /**
     * 设置backupSize字段数据
     * @param backupSize The backupSize to set.
     */
    public void setBackupSize(float backupSize) {
        this.backupSize = backupSize;
    }

}
