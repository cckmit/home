package com.neusoft.mid.cloong.web.page.console.host.pm.info;

/**
 * 物理机网卡
 * @author <a href="mailto:x_wang@neusoft.com">wangxin </a>
 * @version $Revision 1.1 $ 2015-2-28 下午03:09:59
 */
public class NetInfo {

    /**
     * 网卡标识
     */
    private String eth;

    /**
     * ip
     */
    private String ip;

    /**
     * vlan号
     */
    private String vlanId;

    /**
     * vlan名称
     */
    private String vlanName;

    /**
     * 网关
     */
    private String gateway;

    /**
     * 子网掩码
     */
    private String subNetMask;

    /**
     * 物理机ID
     */
    private String pmId;

    /**
     * IP段
     */
    private String ipsegment;

    /**
     * IP段起始IP
     */
    private String startIp;

    /**
     * IP段结束IP
     */
    private String endIp;

    /**
     * IP段ID
     */
    private String ipSegmentId;

    /**
     * 虚拟机修改时，用来标识改网卡是新增还是删除 0：新增，1：修改
     */
    private String modifyFlag;

    /**
     * 时间戳，供订单使用
     */
    private String timeStamp;

    /**
     * 实例ID
     */
    private String caseId;

    /**
     * VLAN和IP段绑定创建时间
     */
    private String createTime;

    /**
     * VLAN和IP段绑定创建人
     */
    private String createUser;

    /**
     * VLAN和IP段绑定更新时间
     */
    private String updateTime;

    /**
     * VLAN和IP段绑定更新人
     */
    private String updateUser;

    /**
     * 标识：add,edit,old
     */
    private String flag;

    /**
     * 用途
     */
    private String purPose;

    /**
     * 子网掩码
     */
    private String subnetmask;

    public String getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getSubNetMask() {
        return subNetMask;
    }

    public void setSubNetMask(String subNetMask) {
        this.subNetMask = subNetMask;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getIpsegment() {
        return ipsegment;
    }

    public void setIpsegment(String ipsegment) {
        this.ipsegment = ipsegment;
    }

    public String getVlanName() {
        return vlanName;
    }

    public void setVlanName(String vlanName) {
        this.vlanName = vlanName;
    }

    public String getStartIp() {
        return startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    public String getIpSegmentId() {
        return ipSegmentId;
    }

    public void setIpSegmentId(String ipSegmentId) {
        this.ipSegmentId = ipSegmentId;
    }

    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
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
     * 获取flag字段数据
     * @return Returns the flag.
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置flag字段数据
     * @param flag The flag to set.
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPurPose() {
        return purPose;
    }

    public void setPurPose(String purPose) {
        this.purPose = purPose;
    }

    public String getSubnetmask() {
        return subnetmask;
    }

    public void setSubnetmask(String subnetmask) {
        this.subnetmask = subnetmask;
    }

}
