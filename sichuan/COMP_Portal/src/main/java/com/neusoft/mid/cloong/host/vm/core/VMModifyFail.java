package com.neusoft.mid.cloong.host.vm.core;

/**
 * 虚拟机修改失败信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午09:12:23
 */
public class VMModifyFail {

    private String failCause;

    private String failCode;

    private String resPoolId;

    private String resPoolPartId;

    private String createTime;
    
    private String vmId;
    
    private String cpuNum;
    
    private String ramSize;
    
    private String discSize;
    
    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getRamSize() {
        return ramSize;
    }

    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    public String getDiscSize() {
        return discSize;
    }

    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("失败原因为：").append(this.failCause).append("\n");
        sb.append("失败响应码为：").append(this.failCode).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("创建时间为：").append(this.createTime).append("\n");
        return sb.toString();
    }

}
