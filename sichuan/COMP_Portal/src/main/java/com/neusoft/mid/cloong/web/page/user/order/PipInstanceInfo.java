package com.neusoft.mid.cloong.web.page.user.order;

/**
 * IP段实例信息
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-3-10 上午08:37:50
 */
public class PipInstanceInfo {
	/**
     * 实例id
     */
    private String caseId;
    /**
     * id
     */
    private String ipsegmentId;
    /**
     * 名称
     */
    private String ipsegmentDesc;
    
    /**
     * 起始IP
     */
    private String startIp;
    
    /**
     * 结束IP
     */
    private String endIp;
    
    /**
     * IP段
     */
    private String startToEndIp;
    
    /**
     * IP个数
     */
    private String ipTotal;
    
    /**
     * 资源池
     */
    private String resPoolId;
    
    /**
     * 资源池分区
     */
    private String resPoolPartId;
    
    /**
     * 资源池分区
     */
    private String resPoolPartName;
    
    /**
     * 业务ID
     */
    private String appId;
    
    /**
     * 业务名称
     */
    private String appName;
    
    /**
     * 网段
     */
    private String ipSubnet;
    
    /**
     * 是否释放
     */
    private String released;
    
	/**
	 * @return the caseId
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * @param caseId the caseId to set
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * @return the resPoolPartId
	 */
	public String getResPoolPartId() {
		return resPoolPartId;
	}

	/**
	 * @param resPoolPartId the resPoolPartId to set
	 */
	public void setResPoolPartId(String resPoolPartId) {
		this.resPoolPartId = resPoolPartId;
	}

	/**
	 * @return the resPoolPartName
	 */
	public String getResPoolPartName() {
		return resPoolPartName;
	}

	/**
	 * @param resPoolPartName the resPoolPartName to set
	 */
	public void setResPoolPartName(String resPoolPartName) {
		this.resPoolPartName = resPoolPartName;
	}

	/**
	 * @return the ipsegmentId
	 */
	public String getIpsegmentId() {
		return ipsegmentId;
	}

	/**
	 * @param ipsegmentId the ipsegmentId to set
	 */
	public void setIpsegmentId(String ipsegmentId) {
		this.ipsegmentId = ipsegmentId;
	}

	/**
	 * @return the ipsegmentDesc
	 */
	public String getIpsegmentDesc() {
		return ipsegmentDesc;
	}

	/**
	 * @param ipsegmentDesc the ipsegmentDesc to set
	 */
	public void setIpsegmentDesc(String ipsegmentDesc) {
		this.ipsegmentDesc = ipsegmentDesc;
	}

	/**
	 * @return the startIp
	 */
	public String getStartIp() {
		return startIp;
	}

	/**
	 * @param startIp the startIp to set
	 */
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}

	/**
	 * @return the endIp
	 */
	public String getEndIp() {
		return endIp;
	}

	/**
	 * @param endIp the endIp to set
	 */
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	/**
	 * @return the ipTotal
	 */
	public String getIpTotal() {
		return ipTotal;
	}

	/**
	 * @param ipTotal the ipTotal to set
	 */
	public void setIpTotal(String ipTotal) {
		this.ipTotal = ipTotal;
	}

	/**
	 * @return the startToEndIp
	 */
	public String getStartToEndIp() {
		return startToEndIp;
	}

	/**
	 * @param startToEndIp the startToEndIp to set
	 */
	public void setStartToEndIp(String startToEndIp) {
		this.startToEndIp = startToEndIp;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the ipSubnet
	 */
	public String getIpSubnet() {
		return ipSubnet;
	}

	/**
	 * @param ipSubnet the ipSubnet to set
	 */
	public void setIpSubnet(String ipSubnet) {
		this.ipSubnet = ipSubnet;
	}

	/**
	 * @return the resPoolId
	 */
	public String getResPoolId() {
		return resPoolId;
	}

	/**
	 * @param resPoolId the resPoolId to set
	 */
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	/**
	 * @return the released
	 */
	public String getReleased() {
		return released;
	}

	/**
	 * @param released the released to set
	 */
	public void setReleased(String released) {
		this.released = released;
	}
}
