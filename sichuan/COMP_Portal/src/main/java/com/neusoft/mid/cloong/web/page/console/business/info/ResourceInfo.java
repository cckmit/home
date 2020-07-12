/*******************************************************************************
 * @(#)BussniessInfo.java 2014年1月28日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business.info;

import java.util.List;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.ebs.core.EBSStatus;
import com.neusoft.mid.cloong.host.pm.core.PMStatus;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;

/**
 * 资源信息
 * 
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月28日 下午2:18:42
 */
public class ResourceInfo {

	/**
	 * 虚拟机类型
	 */
	public static final int TYPE_VM = 0;

	/**
	 * 物理主机类型
	 */
	public static final int TYPE_PM = 1;

	/**
	 * 云硬盘类型
	 */
	public static final int TYPE_BS = 2;
	
	/**
	 * 虚拟机备份任务类型
	 */
	public static final int TYPE_VMBAK = 5;

	/**
	 * 资源ID
	 */
	private String resourceId;

	/**
	 * 资源类型
	 */
	private String type;

	/**
	 * 资源名称
	 */
	private String name;

	/**
	 * 资源状态
	 */
	private String status;

	/**
	 * 创建时间
	 */
	private String effectiveTime;

	/**
	 * 操作IP
	 */
	private String operationIP;

	/**
	 * 到期时间
	 */
	private String overTime;

	/**
	 * 主机采用的ISO名称
	 */
	private String isoName;

	/**
	 * 实例ID
	 */
	private String vmId;
	
	/**
	 * 虚拟机备份ID
	 */
	private String vmBakId;

	/**
	 * 业务ID
	 */
	private String appId;

	/**
	 * 业务名称
	 */
	private String appName;

	/**
	 * 内网IP
	 */
	private String privateIp;

	/**
	 * 所有内网IP集合
	 */
	private String privateIpStr;
	
	/**
	 * 按状态查询虚拟机
	 */
	private List<String> statusList;

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
	 * 获取isoName字段数据
	 * 
	 * @return Returns the isoName.
	 */
	public String getIsoName() {
		return isoName;
	}

	/**
	 * 设置isoName字段数据
	 * 
	 * @param isoName
	 *            The isoName to set.
	 */
	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	/**
	 * 获取effectiveTime字段数据
	 * 
	 * @return Returns the effectiveTime.
	 */
	public String getEffectiveTime() {
		if (this.effectiveTime != null && !this.effectiveTime.equals("")
				&& this.effectiveTime.length() == 14) {
			return DateParse.parse(effectiveTime);
		}
		return effectiveTime;
	}

	/**
	 * 设置effectiveTime字段数据
	 * 
	 * @param effectiveTime
	 *            The effectiveTime to set.
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	/**
	 * 获取operationIP字段数据
	 * 
	 * @return Returns the operationIP.
	 */
	public String getOperationIP() {
		return operationIP;
	}

	/**
	 * 设置operationIP字段数据
	 * 
	 * @param operationIP
	 *            The operationIP to set.
	 */
	public void setOperationIP(String operationIP) {
		this.operationIP = operationIP;
	}

	/**
	 * 获取overTime字段数据
	 * 
	 * @return Returns the overTime.
	 */
	public String getOverTime() {
		if (this.overTime != null && !this.overTime.equals("")
				&& this.overTime.length() == 14) {
			return DateParse.parse(overTime);
		}
		return overTime;
	}

	/**
	 * 设置overTime字段数据
	 * 
	 * @param overTime
	 *            The overTime to set.
	 */
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	/**
	 * 获取resourceId字段数据
	 * 
	 * @return Returns the resourceId.
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * 设置resourceId字段数据
	 * 
	 * @param resourceId
	 *            The resourceId to set.
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * 获取type字段数据
	 * 
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置type字段数据
	 * 
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取name字段数据
	 * 
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置name字段数据
	 * 
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取status字段数据
	 * 
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置status字段数据
	 * 
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取status字段数据
	 * 
	 * @return Returns the status.
	 */
	public String getStatusKey() {
		String res = "";

		int type = Integer.parseInt(this.type);
		switch (type) {
		case TYPE_VM:
			res = VMStatus.getEnum(this.status).name();
			break;
		case TYPE_PM:
			res = PMStatus.getEnum(this.status).name();
			break;
		case TYPE_BS:
			res = EBSStatus.getEnum(this.status).name();
			break;
		case TYPE_VMBAK:
			res = VmBakStatus.getEnum(this.status).name();
			break;
		default:
			break;
		}
		return res;
	}

	/**
	 * 获取status字段数据
	 * 
	 * @return Returns the status.
	 */
	public String getStatusDesc() {
		String res = "";

		int type = Integer.parseInt(this.type);
		switch (type) {
		case TYPE_VM:
			res = VMStatus.getEnum(this.status).getDesc();
			break;
		case TYPE_PM:
			res = PMStatus.getEnum(this.status).getDesc();
			break;
		case TYPE_BS:
			res = EBSStatus.getEnum(this.status).getDesc();
			break;
		case TYPE_VMBAK:
			res = VmBakStatus.getEnum(this.status).getDesc();
			break;
		default:
			break;
		}
		return res;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPrivateIp() {
		return privateIp;
	}

	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}

	public String getPrivateIpStr() {
		return privateIpStr;
	}

	public void setPrivateIpStr(String privateIpStr) {
		this.privateIpStr = privateIpStr;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public String getVmBakId() {
		return vmBakId;
	}

	public void setVmBakId(String vmBakId) {
		this.vmBakId = vmBakId;
	}

}
