package com.neusoft.mid.cloong.vm.core;


/**申请虚拟机订单信息
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.1 $ 2014-5-28 下午04:00:57
 */
public class VMCustomCreateReq  extends ReqBodyInfo{

    /**
     * 规格id
     */
    private String standardId;

    /**
     * 镜像id
     */
    private String osId;

    /**
     * 私网ip
     */
    private String privateIp;
    
    /**
     * 虚拟机所在物理机id
     */
    private String serverId;
    
    /**
     * 虚拟机名称
     */
    private String vmName;
    
    /**
     * 克隆时，备份ID
     */
    private String vmBackupId;

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
     * 获取standardId字段数据
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 设置standardId字段数据
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取osId字段数据
     * @return Returns the osId.
     */
    public String getOsId() {
        return osId;
    }

    /**
     * 设置osId字段数据
     * @param osId The osId to set.
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * 获取privateIp字段数据
     * @return Returns the privateIp.
     */
    public String getPrivateIp() {
        return privateIp;
    }

    /**
     * 设置privateIp字段数据
     * @param privateIp The privateIp to set.
     */
    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    /**
     * 获取serverId字段数据
     * @return Returns the serverId.
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * 设置serverId字段数据
     * @param serverId The serverId to set.
     */
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    /**
     * 获取vmName字段数据
     * @return Returns the vmName.
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * 设置vmName字段数据
     * @param vmName The vmName to set.
     */
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }
    
}
