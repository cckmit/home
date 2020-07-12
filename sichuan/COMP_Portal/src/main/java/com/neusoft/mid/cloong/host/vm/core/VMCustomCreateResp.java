package com.neusoft.mid.cloong.host.vm.core;

import java.util.List;
import java.util.Map;

/**
 * 创建虚拟机接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class VMCustomCreateResp extends RespBodyInfo {

    /**
     * 实例id
     */
    private String caseId;

//    private List<Map<String, String>> vmInfo;

//    public List<Map<String, String>> getVmInfo() {
//        return vmInfo;
//    }
//
//    public void setVmInfo(List<Map<String, String>> vmInfo) {
//        this.vmInfo = vmInfo;
//    }
    /**
     * 虚拟机id
     */
    private String vmId;
    
    /**
     * 虚拟机随机口令
     */
    private String vmPassword;
    
    /**
     * vlan编号
     */
    private String vlanId;
    
    /**
     * 虚拟机所在物理机编号
     */
    private String serverId;
    
    /**
     * 虚拟机受理时间
     */
    private String acceptTime;
    
    /**
     * 私网ip
     */
    private String privateIp;
    
    /**
     * 虚拟机名称
     */
    private String vmName;
    
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
     * 获取vmPassword字段数据
     * @return Returns the vmPassword.
     */
    public String getVmPassword() {
        return vmPassword;
    }

    /**
     * 设置vmPassword字段数据
     * @param vmPassword The vmPassword to set.
     */
    public void setVmPassword(String vmPassword) {
        this.vmPassword = vmPassword;
    }

    /**
     * 获取vlanId字段数据
     * @return Returns the vlanId.
     */
    public String getVlanId() {
        return vlanId;
    }

    /**
     * 设置vlanId字段数据
     * @param vlanId The vlanId to set.
     */
    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
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
     * 获取acceptTime字段数据
     * @return Returns the acceptTime.
     */
    public String getAcceptTime() {
        return acceptTime;
    }

    /**
     * 设置acceptTime字段数据
     * @param acceptTime The acceptTime to set.
     */
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
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

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
