package com.neusoft.mid.cloong.vmbak.core;

/**
 * 虚拟机备份创建失败信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午09:12:23
 */
public class VmBakCreateFail {
    
    /**
     * 失败原因
     */
    private String failCause;
    
    /**
     * 失败码
     */
    private String failCode;

    /**
     * 资源池id
     */
    private String resPoolId;

    /**
     * 资源池分区id
     */
    private String resPoolPartId;

    /**
     *规格id
     */
    private String standardId;

    /**
     * 虚拟机id
     */
    private String vmId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 
     * getFailCause TODO 方法
     * @return TODO
     */
    public String getFailCause() {
        return failCause;
    }

    /**
     * 
     * setFailCause TODO 方法
     * @param failCause TODO
     */
    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }

    /**
     * 
     * getFailCode TODO 方法
     * @return TODO
     */
    public String getFailCode() {
        return failCode;
    }

    /**
     * 
     * setFailCode TODO 方法
     * @param failCode TODO
     */
    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    /**
     * 
     * getResPoolId TODO 方法
     * @return TODO
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 
     * setResPoolId TODO 方法
     * @param resPoolId TODO
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 
     * getResPoolPartId TODO 方法
     * @return TODO
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 
     * setResPoolPartId TODO 方法
     * @param resPoolPartId TODO
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    /**
     * 
     * getStandardId TODO 方法
     * @return TODO
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 
     * setStandardId TODO 方法
     * @param standardId TODO
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
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
     * getCreateTime TODO 方法
     * @return TODO
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * setCreateTime TODO 方法
     * @param createTime TODO
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * toString 虚拟机备份创建失败信息
     * @return 虚拟机备份创建失败信息
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("失败原因为：").append(this.failCause).append("\n");
        sb.append("失败响应码为：").append(this.failCode).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("规格ID为：").append(this.standardId).append("\n");
        sb.append("备份的虚拟机编码：").append(this.vmId).append("\n");
        sb.append("创建时间为：").append(this.createTime).append("\n");
        return sb.toString();
    }

}
