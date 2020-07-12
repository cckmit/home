package com.neusoft.mid.cloong.web.page.console.vlan.info;

import java.io.Serializable;
import java.util.List;

/**
 * vlan实体类
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月28日 上午10:44:51
 */
public class VlanInfo implements Serializable {

    private static final long serialVersionUID = 4062846954299983362L;

    /**
     * 创建一个新的实例 VlanInfo
     */
    public VlanInfo() {

    }

    /**
     * 实例ID
     */
    private String caseId;

    /**
     * vlan号
     */
    private String vlanId;

    /**
     * 订单编码
     */
    private String orderId;

    /**
     * vlan名称
     */
    private String vlanName;

    /**
     * 业务编码
     */
    private String appId;

    /**
     * 资源池编码
     */
    private String resPoolId;

    /**
     * 资源池分区ID
     */
    private String resPoolPartId;

    /**
     * vlan所在安全域 1：DMZ 2：Trust 核心域 3：TEST 测试域
     */
    private Integer zoneType = 0;

    /**
     * vlan用途: 2：业务平面(默认) 3：IP存储平面（指IP存储，例如分布式存储、日志详单、NOSQL等）
     */
    private Integer vlanType = 2;

    /**
     * 创建时间YYYYMMDDhhmmss
     */
    private String createTime;

    /**
     * 创建人(用户ID)
     */
    private String createUser;

    /**
     * 是否取消标识 0：未取消（默认值） 1：已取消
     */
    private List<Integer> canceled;

    /**
     * 作查询结果用的状态(和取消标识是同一个字段)
     */
    private String status;

    /**
     * IP释放时间YYYYMMDDhhmmss
     */
    private String cancelTime;

    /**
     * 取消人(用户ID)
     */
    private String cancelUser;

    /**
     * 业务IDlist 作查询条件用
     */
    private List<String> businessList;

    /**
     * 业务名
     */
    private String appName;

    /**
     * 绑定IP段起始IP
     */
    private String startIp;

    /**
     * 绑定IP段终止IP
     */
    private String endIp;

    /**
     * IP段 ID
     */
    private String ipSegmentId;

    /**
     * 可以被取消标识
     */
    private Cancelable cancelable;
    
    // IP类型
    private String ipType;
    
    /**
     * ip段描述
     */
    private String ipSegmentDesc;

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
     * 获取zoneType字段数据
     * @return Returns the zoneType.
     */
    public Integer getZoneType() {
        return zoneType;
    }

    /**
     * 设置zoneType字段数据
     * @param zoneType The zoneType to set.
     */
    public void setZoneType(Integer zoneType) {
        this.zoneType = zoneType;
    }

    /**
     * 获取vlanType字段数据
     * @return Returns the vlanType.
     */
    public Integer getVlanType() {
        return vlanType;
    }

    /**
     * 设置vlanType字段数据
     * @param vlanType The vlanType to set.
     */
    public void setVlanType(Integer vlanType) {
        this.vlanType = vlanType;
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
     * 获取cancelTime字段数据
     * @return Returns the cancelTime.
     */
    public String getCancelTime() {
        return cancelTime;
    }

    /**
     * 设置cancelTime字段数据
     * @param cancelTime The cancelTime to set.
     */
    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    /**
     * 获取cancelUser字段数据
     * @return Returns the cancelUser.
     */
    public String getCancelUser() {
        return cancelUser;
    }

    /**
     * 设置cancelUser字段数据
     * @param cancelUser The cancelUser to set.
     */
    public void setCancelUser(String cancelUser) {
        this.cancelUser = cancelUser;
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
     * 获取canceled字段数据
     * @return Returns the canceled.
     */
    public List<Integer> getCanceled() {
        return canceled;
    }

    /**
     * 设置canceled字段数据
     * @param canceled The canceled to set.
     */
    public void setCanceled(List<Integer> canceled) {
        this.canceled = canceled;
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
     * 获取cancelable字段数据
     * @return Returns the cancelable.
     */
    public Cancelable getCancelable() {
        return cancelable;
    }

    /**
     * 设置cancelable字段数据
     * @param cancelable The cancelable to set.
     */
    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable;
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

	public String getIpSegmentDesc() {
		return ipSegmentDesc;
	}

	public void setIpSegmentDesc(String ipSegmentDesc) {
		this.ipSegmentDesc = ipSegmentDesc;
	}
    
}
