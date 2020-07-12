package com.neusoft.mid.cloong.web.page.console.host.vm.info;

public class LBobjInfo {
	/**
	 * 负载均衡对象ID
	 */
	private String id;
	/**
	 * 负载均衡ID
	 */
	private String LBid;
	/**
	 * 使用负载均衡计算机资源服务器名称
	 */
	private String objname;
	/**
	 * 使用负载均衡计算机资源服务器IP
	 */
	private String hostip;
	/**
	 * 增加的使用负载均衡计算资源的端口
	 */
	private String hostport;
	/**
	 * 创建对象信息时间
	 */
	private String create_time;
	/**
	 * 创建对象信息人员
	 */
	private String create_user;
	/**
	 * 对象描述
	 */
	private String objdescription;
	/**
	 * 对象业务
	 */
	private String obj_AppId;
	/**
	 * 对象vlan
	 */
	private String obj_Vlan;
	/**
	 * 对象ip段
	 */
	private String ipsegmentid;
	/**
	 * 对象信息状态
	 */
	private String status;
	
	/**
	 * IP地址类型
	 */
	private String ipType;
	
	public String getLBid() {
		return LBid;
	}
	public void setLBid(String lBid) {
		LBid = lBid;
	}
	public String getHostip() {
		return hostip;
	}
	public void setHostip(String hostip) {
		this.hostip = hostip;
	}
	public String getHostport() {
		return hostport;
	}
	public void setHostport(String hostport) {
		this.hostport = hostport;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getObjname() {
		return objname;
	}
	public void setObjname(String objname) {
		this.objname = objname;
	}
	public String getObjdescription() {
		return objdescription;
	}
	public void setObjdescription(String objdescription) {
		this.objdescription = objdescription;
	}
	public String getObj_AppId() {
		return obj_AppId;
	}
	public void setObj_AppId(String obj_AppId) {
		this.obj_AppId = obj_AppId;
	}
	public String getObj_Vlan() {
		return obj_Vlan;
	}
	public void setObj_Vlan(String obj_Vlan) {
		this.obj_Vlan = obj_Vlan;
	}
	public String getIpsegmentid() {
		return ipsegmentid;
	}
	public void setIpsegmentid(String ipsegmentid) {
		this.ipsegmentid = ipsegmentid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIpType() {
		return ipType;
	}
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}
	
}
