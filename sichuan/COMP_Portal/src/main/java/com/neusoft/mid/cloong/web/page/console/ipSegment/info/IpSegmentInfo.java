package com.neusoft.mid.cloong.web.page.console.ipSegment.info;

import java.io.Serializable;
import java.util.List;

/**
 * IP段bean
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月6日 上午10:26:17
 */
public class IpSegmentInfo implements Serializable {

    private static final long serialVersionUID = 2457948933405687402L;

    /**
     * 实例id
     */
    private String caseId;

    /**
     * ip段唯一标识
     */
    private String ipSegmentId;

    /**
     * ip段描述
     */
    private String ipSegmentDesc;

    /**
     * vlanId 用来存绑定的vlan号
     */
    private String vlanId;

    /**
     * vlan名称
     */
    private String vlanName;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 业务id
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    /**
     * 资源池id
     */
    private String resPoolId;

    /**
     * 资源池分区id
     */
    private String resPoolPartId;

    /**
     * ip网段(格式为xxx.xxx.xxx.xxx/xx)
     */
    private String ipSubnet;

    /**
     * 起始ip
     */
    private String startIp;

    /**
     * 终止ip
     */
    private String endIp;

    /**
     * 该段内的ip总个数
     */
    private String ipTotal;

    /**
     * 创建时间YYYYMMDDhhmmss
     */
    private String createTime;

    /**
     * 创建人id
     */
    private String createUser;

    /**
     * 是否释放标识 0：未释放（默认值）1：已释放 2:创建中
     */
    private List<Integer> released;

    /**
     * 作查询结果用的状态(和释放标识是同一个字段)
     */
    private String status;

    /**
     * 释放时间YYYYMMDDhhmmss
     */
    private String releaseTime;

    /**
     * 释放人员id
     */
    private String releaseUser;

    /**
     * 业务idlist,用于查询
     */
    private List<String> businessList;

    /**
     * IP段 格式：起始IP ~ 结束IP
     */
    private String ipSegment;
    
    // IP类型  0：IPV4；1：IPV6
    private String ipType;

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

    /**
     * 获取ipSegmentId字段数据
     * @return Returns the ipSegmentId.
     */
    public String getIpSegmentId() {
        return ipSegmentId;
    }

    /**
     * 设置ipSegmentId字段数据
     * @param ipSegmentId The ipSegmentId to set.
     */
    public void setIpSegmentId(String ipSegmentId) {
        this.ipSegmentId = ipSegmentId;
    }

    /**
     * 获取ipSegmentDesc字段数据
     * @return Returns the ipSegmentDesc.
     */
    public String getIpSegmentDesc() {
        return ipSegmentDesc;
    }

    /**
     * 设置ipSegmentDesc字段数据
     * @param ipSegmentDesc The ipSegmentDesc to set.
     */
    public void setIpSegmentDesc(String ipSegmentDesc) {
        this.ipSegmentDesc = ipSegmentDesc;
    }

    /**
     * 获取orderId字段数据
     * @return Returns the orderId.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置orderId字段数据
     * @param orderId The orderId to set.
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取appId字段数据
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appId字段数据
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取appName字段数据
     * @return Returns the appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置appName字段数据
     * @param appName The appName to set.
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取resPoolId字段数据
     * @return Returns the resPoolId.
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 设置resPoolId字段数据
     * @param resPoolId The resPoolId to set.
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 获取resPoolPartId字段数据
     * @return Returns the resPoolPartId.
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 设置resPoolPartId字段数据
     * @param resPoolPartId The resPoolPartId to set.
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    /**
     * 获取ipSubnet字段数据
     * @return Returns the ipSubnet.
     */
    public String getIpSubnet() {
        return ipSubnet;
    }

    /**
     * 设置ipSubnet字段数据
     * @param ipSubnet The ipSubnet to set.
     */
    public void setIpSubnet(String ipSubnet) {
        this.ipSubnet = ipSubnet;
    }

    /**
     * 获取startIp字段数据
     * @return Returns the startIp.
     */
    public String getStartIp() {
        return startIp;
    }

    /**
     * 设置startIp字段数据
     * @param startIp The startIp to set.
     */
    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    /**
     * 获取endIp字段数据
     * @return Returns the endIp.
     */
    public String getEndIp() {
        return endIp;
    }

    /**
     * 设置endIp字段数据
     * @param endIp The endIp to set.
     */
    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    /**
     * 获取ipTotal字段数据
     * @return Returns the ipTotal.
     */
    public String getIpTotal() {
        return ipTotal;
    }

    /**
     * 设置ipTotal字段数据
     * @param ipTotal The ipTotal to set.
     */
    public void setIpTotal(String ipTotal) {
        this.ipTotal = ipTotal;
    }

    /**
     * 获取createTime字段数据
     * @return Returns the createTime.
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置createTime字段数据
     * @param createTime The createTime to set.
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取createUser字段数据
     * @return Returns the createUser.
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置createUser字段数据
     * @param createUser The createUser to set.
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取releaseTime字段数据
     * @return Returns the releaseTime.
     */
    public String getReleaseTime() {
        return releaseTime;
    }

    /**
     * 设置releaseTime字段数据
     * @param releaseTime The releaseTime to set.
     */
    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * 获取releaseUser字段数据
     * @return Returns the releaseUser.
     */
    public String getReleaseUser() {
        return releaseUser;
    }

    /**
     * 设置releaseUser字段数据
     * @param releaseUser The releaseUser to set.
     */
    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser;
    }

    /**
     * 获取businessList字段数据
     * @return Returns the businessList.
     */
    public List<String> getBusinessList() {
        return businessList;
    }

    /**
     * 设置businessList字段数据
     * @param businessList The businessList to set.
     */
    public void setBusinessList(List<String> businessList) {
        this.businessList = businessList;
    }

    public String getIpSegment() {
        return ipSegment;
    }

    public void setIpSegment(String ipSegment) {
        this.ipSegment = ipSegment;
    }

    /**
     * 获取released字段数据
     * @return Returns the released.
     */
    public List<Integer> getReleased() {
        return released;
    }

    /**
     * 设置released字段数据
     * @param released The released to set.
     */
    public void setReleased(List<Integer> released) {
        this.released = released;
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
     * 获取status字段数据
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status字段数据
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取vlanName字段数据
     * @return Returns the vlanName.
     */
    public String getVlanName() {
        return vlanName;
    }

    /**
     * 设置vlanName字段数据
     * @param vlanName The vlanName to set.
     */
    public void setVlanName(String vlanName) {
        this.vlanName = vlanName;
    }

	/**
	 * @return the ipType
	 */
	public String getIpType() {
		return ipType;
	}

	/**
	 * @param ipType the ipType to set
	 */
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}
    
}
