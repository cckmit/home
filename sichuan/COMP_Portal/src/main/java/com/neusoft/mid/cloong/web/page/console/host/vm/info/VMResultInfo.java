package com.neusoft.mid.cloong.web.page.console.host.vm.info;

import java.io.Serializable;
import java.util.List;

import com.neusoft.mid.cloong.host.vm.core.VMStatus;

/**
 * 虚拟机查询列表 实例信息
 * 
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-8 下午3:35:32
 */
public class VMResultInfo implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 实例id
	 */
	private String caseId;

	/**
	 * 虚拟机id
	 */
	private String vmId;

	/**
	 * 虚拟机名称
	 */
	private String vmName;

	/**
	 * 镜像ID
	 */
	private String isoId;

	/**
	 * 镜像名
	 */
	private String isoName;

	/**
	 * 镜像描述
	 */
	private String isoDescription;

	/**
	 * 数据库返回虚拟机状态
	 */
	private VMStatus status;

	/**
	 * 虚拟机状态名称
	 */
	private String statusText;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 订单创建时间
	 */
	private String orderCreateTime;

	/**
	 * 订单状态
	 */
	private String effectiveStatus;

	/**
	 * 开始生效时间（计费时间）
	 */
	private String effectiveTime;

	/**
	 * 时长
	 */
	private int lengthTime;

	/**
	 * 到期日期
	 */
	private String overTime;

	/**
	 * 查询去除虚拟机状态
	 */
	private String vmStatus;

	/**
	 * 虚拟机状态
	 */
	private String queryStatus;

	/**
	 * 资源池ID
	 */
	private String resPoolId;

	/**
	 * 资源池分区ID
	 */
	private String resPoolPartId;

	/**
	 * 内网IP
	 */
	private String privateIp;

	/**
	 * 所有内网IP集合
	 */
	private String privateIpStr;

	/**
	 * 业务Id
	 */
	private String appId;

	/**
	 * 业务名称
	 */
	private String appName;

	/**
	 * 按状态查询虚拟机
	 */
	private List<String> statusList;

	/**
	 * 当前用户绑定的业务ID
	 */
	private List<String> appIdList;

	public String getPrivateIp() {
		return privateIp;
	}

	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}

	public String getEffectiveStatus() {
		return effectiveStatus;
	}

	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getIsoId() {
		return isoId;
	}

	public void setIsoId(String isoId) {
		this.isoId = isoId;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}

	public void setStatus(VMStatus status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getLengthTime() {
		return lengthTime;
	}

	public void setLengthTime(int lengthTime) {
		this.lengthTime = lengthTime;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getVmStatus() {
		return vmStatus;
	}

	public void setVmStatus(String vmStatus) {
		this.vmStatus = vmStatus;
	}

	public VMStatus getStatus() {
		return status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getIsoName() {
		return isoName;
	}

	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	public String getIsoDescription() {
		return isoDescription;
	}

	public void setIsoDescription(String isoDescription) {
		this.isoDescription = isoDescription;
	}

	/**
	 * @return Returns the resPoolId.
	 */
	public String getResPoolId() {
		return resPoolId;
	}

	/**
	 * @param resPoolId
	 *            The resPoolId to set.
	 */
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	/**
	 * @return Returns the resPoolPartId.
	 */
	public String getResPoolPartId() {
		return resPoolPartId;
	}

	/**
	 * @param resPoolPartId
	 *            The resPoolPartId to set.
	 */
	public void setResPoolPartId(String resPoolPartId) {
		this.resPoolPartId = resPoolPartId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

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

	public List<String> getAppIdList() {
		return appIdList;
	}

	public void setAppIdList(List<String> appIdList) {
		this.appIdList = appIdList;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
