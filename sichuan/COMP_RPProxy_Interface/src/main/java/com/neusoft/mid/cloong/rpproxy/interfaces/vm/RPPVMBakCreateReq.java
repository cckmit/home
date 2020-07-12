/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;
import java.util.Date;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 虚拟机备份创建请求
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMBakCreateReq extends RPPBaseReq implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 要备份的虚拟机ID
     */
    private String vmId;

    /**
     * 虚拟机备份任务在运营平台内的实例ID
     */
    private String caseId;

    /**
     * 备份类型<br>
     */
    private VMBakType vmBackupType;

    /**
     * 备份方式
     */
    private VMBakMode vmBackupMode;

    /**
     * 备份周期，单位天
     */
    private Integer backupCyc;

    /**
     * 备份保留最长时间，单位天
     */
    private Integer backStoreTime;

    /**
     * 
     */
    private Date backupStartTime;

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
     * 获取vmBackupType字段数据
     * @return Returns the vmBackupType.
     */
    public VMBakType getVmBackupType() {
        return vmBackupType;
    }

    /**
     * 设置vmBackupType字段数据
     * @param vmBackupType The vmBackupType to set.
     */
    public void setVmBackupType(VMBakType vmBackupType) {
        this.vmBackupType = vmBackupType;
    }

    /**
     * 获取vmBackupMode字段数据
     * @return Returns the vmBackupMode.
     */
    public VMBakMode getVmBackupMode() {
        return vmBackupMode;
    }

    /**
     * 设置vmBackupMode字段数据
     * @param vmBackupMode The vmBackupMode to set.
     */
    public void setVmBackupMode(VMBakMode vmBackupMode) {
        this.vmBackupMode = vmBackupMode;
    }

    /**
     * 获取backupCyc字段数据
     * @return Returns the backupCyc.
     */
    public Integer getBackupCyc() {
        return backupCyc;
    }

    /**
     * 设置backupCyc字段数据
     * @param backupCyc The backupCyc to set.
     */
    public void setBackupCyc(Integer backupCyc) {
        this.backupCyc = backupCyc;
    }

    /**
     * 获取backStoreTime字段数据
     * @return Returns the backStoreTime.
     */
    public Integer getBackStoreTime() {
        return backStoreTime;
    }

    /**
     * 设置backStoreTime字段数据
     * @param backStoreTime The backStoreTime to set.
     */
    public void setBackStoreTime(Integer backStoreTime) {
        this.backStoreTime = backStoreTime;
    }

    /**
     * 获取backupStartTime字段数据
     * @return Returns the backupStartTime.
     */
    public Date getBackupStartTime() {
        return backupStartTime;
    }

    /**
     * 设置backupStartTime字段数据
     * @param backupStartTime The backupStartTime to set.
     */
    public void setBackupStartTime(Date backupStartTime) {
        this.backupStartTime = backupStartTime;
    }

    /**
     * 获取caseId字段数据
     * @return Returns the caseId.
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 设置caseId字段数据
     * @param caseId The caseId to set.
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
