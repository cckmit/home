package com.neusoft.mid.cloong.vault.beans.outside.createAppRequest;

import com.neusoft.mid.cloong.vault.beans.adapter.CDataAdapter;
import com.neusoft.mid.cloong.vault.beans.outside.base.VaultBaseRequest;
import com.neusoft.mid.cloong.vault.beans.outside.enums.AuthMode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "createAppParam")
public class CreateAppRequestRequest extends VaultBaseRequest {
	/**
	 * 登陆应用系统时的根票据，因含有特殊字符，需要使用<![CDATA[ 标签
	 */
	@XmlElement(required = true)
	@XmlJavaTypeAdapter(CDataAdapter.class)
	private String rootTicket;

	/**
	 * 当前登陆应用系统所使用的从帐号
	 */
	@XmlElement(required = true)
	private String appAccount;

	/**
	 * 应用资源编号
	 */
	@XmlElement(required = true)
	private String resNum;

	/**
	 * 本次操作的敏感操作代码
	 */
	@XmlElement(required = true)
	private String operationCode;

	/**
	 * 本次操作的应用系统功能模块代码
	 */
	@XmlElement(required = true)
	private String functionCode;

	/**
	 * 建本次金库申请所要采用的认证方式
	 */
	@XmlElement(required = true)
	private List<String> authMode;

	/**
	 * 申请原因，150字以内
	 */
	@XmlElement(required = true)
	private String applyReason;

	/**
	 * 申请使用次数
	 */
	@XmlElement(required = true)
	private String userTimes;

	/**
	 * 本次申请的有效时长,从当前时间算起
	 */
	@XmlElement(required = true)
	private String duration;

	/**
	 * 用户需要在创建申请页面上选择审批人
	 */
	@XmlElement(required = true)
	private List<String> selectedApprover;

	/**
	 * 工单号
	 */
	@XmlElement(required = true)
	private String wordOrderID;

	/**
	 * 工单类型
	 */
	@XmlElement(required = true)
	private String workOrderType;

	public String getRootTicket() {
		return rootTicket;
	}

	public void setRootTicket(String rootTicket) {
		this.rootTicket = rootTicket;
	}

	public String getAppAccount() {
		return appAccount;
	}

	public void setAppAccount(String appAccount) {
		this.appAccount = appAccount;
	}

	public String getResNum() {
		return resNum;
	}

	public void setResNum(String resNum) {
		this.resNum = resNum;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	/*public List<AuthMode> getAuthMode() {
		return authMode;
	}

	public void setAuthMode(List<AuthMode> authMode) {
		this.authMode = authMode;
	}*/

	public List<String> getAuthMode() {
		return authMode;
	}

	public void setAuthMode(List<String> authMode) {
		this.authMode = authMode;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getUserTimes() {
		return userTimes;
	}

	public void setUserTimes(String userTimes) {
		this.userTimes = userTimes;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public List<String> getSelectedApprover() {
		return selectedApprover;
	}

	public void setSelectedApprover(List<String> selectedApprover) {
		this.selectedApprover = selectedApprover;
	}

	public String getWordOrderID() {
		return wordOrderID;
	}

	public void setWordOrderID(String wordOrderID) {
		this.wordOrderID = wordOrderID;
	}

	public String getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}

}
