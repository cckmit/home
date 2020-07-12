package com.neusoft.mid.cloong.vmbak.core;

import com.neusoft.mid.cloong.vm.core.RespBodyInfo;

/**
 * 创建虚拟机备份接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-22 上午09:20:03
 */
public class VmBakCreateResp extends RespBodyInfo {

    /**
     * 实例id
     */
    private String caseId;

    /**
     * 虚拟机id
     */
    private String vmId;

    /**
     * 虚拟机备份ID
     */
    private String vmBakId;
    
    /**
     * 备份时间
     */
    private String backupTime;

    /**
     * 
     * getCaseId TODO 方法
     * @return TODO
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 
     * setCaseId TODO 方法
     * @param caseId TODO
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * 
     * getVmId TODO 方法
     * @return TODO
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 
     * setVmId TODO 方法
     * @param vmId TODO
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 
     * getVmBakId TODO 方法
     * @return TODO
     */
    public String getVmBakId() {
        return vmBakId;
    }

    /**
     * 
     * setVmBakId TODO 方法
     * @param vmBakId TODO
     */
    public void setVmBakId(String vmBakId) {
        this.vmBakId = vmBakId;
    }

    /**
     * 
     * getBackupTime TODO 方法
     * @return TODO
     */
    public String getBackupTime() {
        return backupTime;
    }

    /**
     * 
     * setBackupTime TODO 方法
     * @param backupTime TODO
     */
    public void setBackupTime(String backupTime) {
        this.backupTime = backupTime;
    }



}
