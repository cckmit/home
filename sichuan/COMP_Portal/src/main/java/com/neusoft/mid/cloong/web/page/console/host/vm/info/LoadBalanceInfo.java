package com.neusoft.mid.cloong.web.page.console.host.vm.info;

import java.util.List;

public class LoadBalanceInfo {
	/**
     * 负载均衡ID
     */
    private String LBid;
	/**
     * 负载均衡名称
     */
    private String lbname;
    /**
     * 浮动 ip
     */
    private String lbip;
    /**
     * 端口
     */
    private String lbport;
    /**
     * 流量协议类型
     */
    private String protocal;
    /**
     * 申请负载均衡的吞吐能力，单位
     */
    private String Throughput;
    /**
     * 支持的并发链接个数，单位个
     */
    private String KbpsConnectNum;
    /**
     * 支持新建链接速度，单位个/秒
     */
    private String NewConnectNum;
    /**
     * 虚拟服务名称
     */
    private String virtualname;
    /**
     * 后台服务组名称
     */
    private String group_name;
    /**
     * 模板编码：CPC-T-LB-[8位阿拉伯数字资源模板编号]
     */
    private String LBTemplateID;
    /**
     * 负载均衡服务需要操作的负载均衡设备ID，多个设备用逗号分隔，申请负载均衡时根据虚拟IP获取
     */
    private String LBDeviceID;
    /**
     * 策略
     */
    private String Strategy;
    /**
     * 状态
     */
    private String status;
    /**
     * 申请人
     */
    private String createuser;
    /**
     * 申请时间
     */
    private String createtime;
    /**
     * 创建VirtualServer绑定的poolID
     */
    private String vlb_obj_id;
    /**
     * 负载均衡业务ID
     */
    private String appid;
    /**
     * 负载均衡业务名称
     */
    private String appname;
    /**
     * 负载均衡方式
     */
    private String LBType;
    /**
     * 负载均衡资源池ID
     */
    private String respoolId;

    /**
     * 负载均衡资源池分区ID
     */
    private String respoolPartId;
    
    /**
     * 负载均衡资源池名称
     */
    private String respoolname;
    
    /**
     * 负载均衡资源池分区名称
     */
    private String respoolPartname;
    /**
     * 负载均衡对象List
     */
    private List<LBobjInfo> objInfolist;
    /**
     * 负载均衡VlanId
     */
    private String vlanId;
    /**
     * 负载均衡vlan名称
     */
    private String vlanName;
    /**
     * 负载均衡Ip段Id
     */
    private String IPSEGMENT_ID;
    /**
     * 订单id
     */
    private String orderId;   
	/**
     * 业务IDlist 作查询条件用
     */
    private List<String> businessList;
    /**
     * 负载均衡Ip段Id
     */
    private String instanceid;
    
    /**
     * 当前用户绑定的业务ID
     */
    private List<String> appIdList;
    
    /**
     * IP类型
     */
    private String ipType;
    
	public String getLbname() {
		return lbname;
	}
	public void setLbname(String lbname) {
		this.lbname = lbname;
	}
	public String getLbip() {
		return lbip;
	}
	public void setLbip(String lbip) {
		this.lbip = lbip;
	}
	public String getStrategy() {
		return Strategy;
	}
	public void setStrategy(String strategy) {
		Strategy = strategy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLbport() {
		return lbport;
	}
	public void setLbport(String lbport) {
		this.lbport = lbport;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLBid() {
		return LBid;
	}
	public void setLBid(String lBid) {
		LBid = lBid;
	}
	public String getProtocal() {
		return protocal;
	}
	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}
	public String getThroughput() {
		return Throughput;
	}
	public void setThroughput(String throughput) {
		Throughput = throughput;
	}
	public String getKbpsConnectNum() {
		return KbpsConnectNum;
	}
	public void setKbpsConnectNum(String kbpsConnectNum) {
		KbpsConnectNum = kbpsConnectNum;
	}
	public String getNewConnectNum() {
		return NewConnectNum;
	}
	public void setNewConnectNum(String newConnectNum) {
		NewConnectNum = newConnectNum;
	}
	public String getVirtualname() {
		return virtualname;
	}
	public void setVirtualname(String virtualname) {
		this.virtualname = virtualname;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getLBTemplateID() {
		return LBTemplateID;
	}
	public void setLBTemplateID(String lBTemplateID) {
		LBTemplateID = lBTemplateID;
	}
	public String getLBDeviceID() {
		return LBDeviceID;
	}
	public void setLBDeviceID(String lBDeviceID) {
		LBDeviceID = lBDeviceID;
	}
	public String getVlb_obj_id() {
		return vlb_obj_id;
	}
	public void setVlb_obj_id(String vlb_obj_id) {
		this.vlb_obj_id = vlb_obj_id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getLBType() {
		return LBType;
	}
	public void setLBType(String lBType) {
		LBType = lBType;
	}
	public String getRespoolId() {
		return respoolId;
	}
	public void setRespoolId(String respoolId) {
		this.respoolId = respoolId;
	}
	public String getRespoolPartId() {
		return respoolPartId;
	}
	public void setRespoolPartId(String respoolPartId) {
		this.respoolPartId = respoolPartId;
	}
	public String getRespoolname() {
		return respoolname;
	}
	public void setRespoolname(String respoolname) {
		this.respoolname = respoolname;
	}
	public String getRespoolPartname() {
		return respoolPartname;
	}
	public void setRespoolPartname(String respoolPartname) {
		this.respoolPartname = respoolPartname;
	}
	public List<LBobjInfo> getObjInfolist() {
		return objInfolist;
	}
	public void setObjInfolist(List<LBobjInfo> objInfolist) {
		this.objInfolist = objInfolist;
	}
	
	public String getIPSEGMENT_ID() {
		return IPSEGMENT_ID;
	}
	public void setIPSEGMENT_ID(String iPSEGMENT_ID) {
		IPSEGMENT_ID = iPSEGMENT_ID;
	}
	
	public String getVlanId() {
		return vlanId;
	}
	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}
	public String getVlanName() {
		return vlanName;
	}
	public void setVlanName(String vlanName) {
		this.vlanName = vlanName;
	}
	public List<String> getBusinessList() {
		return businessList;
	}
	public void setBusinessList(List<String> businessList) {
		this.businessList = businessList;
	}
	public String getInstanceid() {
		return instanceid;
	}
	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}
	 public String getOrderId() {
			return orderId;
		}
	public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
    public List<String> getAppIdList() {
        return appIdList;
    }
    public void setAppIdList(List<String> appIdList) {
        this.appIdList = appIdList;
    }
	public String getIpType() {
		return ipType;
	}
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}
}
