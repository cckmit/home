package com.neusoft.mid.cloong.host.vm.core;

/**虚拟机创建接口响应中具体内容
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午02:20:34
 */
public class VMInfoSet {
    private String vmId;

    private String subNetwork;

    private String serverId;

    private String acceptTime;

    private String privateIp;

    private String vmPassword;

    private String vmName;

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getSubNetwork() {
        return subNetwork;
    }

    public void setSubNetwork(String subNetwork) {
        this.subNetwork = subNetwork;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    public String getVmPassword() {
        return vmPassword;
    }

    public void setVmPassword(String vmPassword) {
        this.vmPassword = vmPassword;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

}
