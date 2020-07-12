package com.neusoft.mid.cloong.web.page.host.vm.order.info;

import java.io.Serializable;

/**
 * 资源池分区信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午04:33:00
 */
public class RespoolPartInfo implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private String respoolPartId;

    private String respoolPartName;

    private String respoolId;

    private String respoolName;

    private String status;
    
    /**
     * 资源类型
     * 0：虚拟机
     * 1：物理机
     */
    private String resourceType;

    /**
     * CPU_NUM_TOTAL
     */
    private String cpuNumTotal;

    /**
     * RAM_SIZE_TOTAL
     */
    private String ramNumTotal;

    /**
     * DISC_SIZE_TOTAL
     */
    private String diskSizeNumTotal;

    private String description;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;

    public String getRespoolPartId() {
        return respoolPartId;
    }

    public void setRespoolPartId(String respoolPartId) {
        this.respoolPartId = respoolPartId;
    }

    public String getRespoolPartName() {
        return respoolPartName;
    }

    public void setRespoolPartName(String respoolPartName) {
        this.respoolPartName = respoolPartName;
    }

    public String getRespoolId() {
        return respoolId;
    }

    public void setRespoolId(String respoolId) {
        this.respoolId = respoolId;
    }

    public String getRespoolName() {
        return respoolName;
    }

    public void setRespoolName(String respoolName) {
        this.respoolName = respoolName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取cpuNumTotal字段数据
     * @return Returns the cpuNumTotal.
     */
    public String getCpuNumTotal() {
        return cpuNumTotal;
    }

    /**
     * 设置cpuNumTotal字段数据
     * @param cpuNumTotal The cpuNumTotal to set.
     */
    public void setCpuNumTotal(String cpuNumTotal) {
        this.cpuNumTotal = cpuNumTotal;
    }

    /**
     * 获取ramNumTotal字段数据
     * @return Returns the ramNumTotal.
     */
    public String getRamNumTotal() {
        return ramNumTotal;
    }

    /**
     * 设置ramNumTotal字段数据
     * @param ramNumTotal The ramNumTotal to set.
     */
    public void setRamNumTotal(String ramNumTotal) {
        this.ramNumTotal = ramNumTotal;
    }

    /**
     * 获取diskSizeNumTotal字段数据
     * @return Returns the diskSizeNumTotal.
     */
    public String getDiskSizeNumTotal() {
        return diskSizeNumTotal;
    }

    /**
     * 设置diskSizeNumTotal字段数据
     * @param diskSizeNumTotal The diskSizeNumTotal to set.
     */
    public void setDiskSizeNumTotal(String diskSizeNumTotal) {
        this.diskSizeNumTotal = diskSizeNumTotal;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

}
