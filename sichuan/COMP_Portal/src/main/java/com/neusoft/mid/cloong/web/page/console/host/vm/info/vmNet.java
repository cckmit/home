/*******************************************************************************
 * @(#)vmNet.java 2015-3-11
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm.info;

/**
 * comp_case_vm_net_t
 * 
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2015-3-11 下午5:10:20
 */
public class vmNet {

	/**
	 * CASE_ID,
	 */
	private String caseId;

	/**
	 * VM_ID,
	 */
	private String vmId;

	/**
	 * PUR_POSE,
	 */
	private String purPose;

	/**
	 * ETH,
	 */
	private String eth;

	/**
	 * IP,
	 */
	private String ip;

	/**
	 * IP_DEFAULT_GATEWAY,
	 */
	private String gateway;

	/**
	 * VLAN_ID,
	 */
	private String vlanId;

	/**
	 * IP_Subnetmask
	 */
	private String subnetmask;

	/**
	 * IP类型 0:IPV4; 1:IPV6
	 */
	private String ipType;

	/**
	 * 获取caseId字段数据
	 * 
	 * @return Returns the caseId.
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * 设置caseId字段数据
	 * 
	 * @param caseId
	 *            The caseId to set.
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * 获取vmId字段数据
	 * 
	 * @return Returns the vmId.
	 */
	public String getVmId() {
		return vmId;
	}

	/**
	 * 设置vmId字段数据
	 * 
	 * @param vmId
	 *            The vmId to set.
	 */
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	/**
	 * 获取purPose字段数据
	 * 
	 * @return Returns the purPose.
	 */
	public String getPurPose() {
		return purPose;
	}

	/**
	 * 设置purPose字段数据
	 * 
	 * @param purPose
	 *            The purPose to set.
	 */
	public void setPurPose(String purPose) {
		this.purPose = purPose;
	}

	/**
	 * 获取eth字段数据
	 * 
	 * @return Returns the eth.
	 */
	public String getEth() {
		return eth;
	}

	/**
	 * 设置eth字段数据
	 * 
	 * @param eth
	 *            The eth to set.
	 */
	public void setEth(String eth) {
		this.eth = eth;
	}

	/**
	 * 获取ip字段数据
	 * 
	 * @return Returns the ip.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置ip字段数据
	 * 
	 * @param ip
	 *            The ip to set.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取gateway字段数据
	 * 
	 * @return Returns the gateway.
	 */
	public String getGateway() {
		return gateway;
	}

	/**
	 * 设置gateway字段数据
	 * 
	 * @param gateway
	 *            The gateway to set.
	 */
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	/**
	 * 获取vlanId字段数据
	 * 
	 * @return Returns the vlanId.
	 */
	public String getVlanId() {
		return vlanId;
	}

	/**
	 * 设置vlanId字段数据
	 * 
	 * @param vlanId
	 *            The vlanId to set.
	 */
	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}

	/**
	 * 获取subnetmask字段数据
	 * 
	 * @return Returns the subnetmask.
	 */
	public String getSubnetmask() {
		return subnetmask;
	}

	/**
	 * 设置subnetmask字段数据
	 * 
	 * @param subnetmask
	 *            The subnetmask to set.
	 */
	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}

	/**
	 * @return the ipType
	 */
	public String getIpType() {
		return ipType;
	}

	/**
	 * @param ipType
	 *            the ipType to set
	 */
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

}
