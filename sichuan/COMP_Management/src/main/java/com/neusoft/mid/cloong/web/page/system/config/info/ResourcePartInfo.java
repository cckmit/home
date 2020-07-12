package com.neusoft.mid.cloong.web.page.system.config.info;

import com.neusoft.mid.cloong.common.core.ResPoolStatus;
import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 资源池分区实例
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-7 上午11:19:39
 */
public class ResourcePartInfo extends BaseInfo {

    private String resPoolPartId;// RES_POOL_PART_ID;

    private String resPoolPartName;// RES_POOL_PART_NAME;

    private String resPoolId;// RES_POOL_ID;

    private String resPoolName;// RES_POOL_NAME;

    private ResPoolStatus status;// STATUS;

    private String description;// DESCRIPTION;
    
    private String cpuNumTotal; //分区每台虚拟机最大可申请VCPU数
    
    private String ramSizeTotal; //分区每台虚拟机最大可申请内存容量
    
    private String discSizeTotal; //分区每台虚拟机最大可申请磁盘容量

    /**
     * 用于判断是否存在不可修改或不可创建。
     */
    private String oldResPoolId;

    public String getOldResPoolId() {
        return oldResPoolId;
    }

    public void setOldResPoolId(String oldResPoolId) {
        this.oldResPoolId = oldResPoolId;
    }

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    public String getResPoolPartName() {
        return resPoolPartName;
    }

    public void setResPoolPartName(String resPoolPartName) {
        this.resPoolPartName = resPoolPartName;
    }

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getResPoolName() {
        return resPoolName;
    }

    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    public ResPoolStatus getStatus() {
        return status;
    }

    public void setStatus(ResPoolStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the cpuNumTotal
     */
    public String getCpuNumTotal() {
        return cpuNumTotal;
    }

    /**
     * @param cpuNumTotal the cpuNumTotal to set
     */
    public void setCpuNumTotal(String cpuNumTotal) {
        this.cpuNumTotal = cpuNumTotal;
    }

    /**
     * @return the ramSizeTotal
     */
    public String getRamSizeTotal() {
        return ramSizeTotal;
    }

    /**
     * @param ramSizeTotal the ramSizeTotal to set
     */
    public void setRamSizeTotal(String ramSizeTotal) {
        this.ramSizeTotal = ramSizeTotal;
    }

    /**
     * @return the discSizeTotal
     */
    public String getDiscSizeTotal() {
        return discSizeTotal;
    }

    /**
     * @param discSizeTotal the discSizeTotal to set
     */
    public void setDiscSizeTotal(String discSizeTotal) {
        this.discSizeTotal = discSizeTotal;
    }
    
    
}
