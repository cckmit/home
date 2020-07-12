package com.neusoft.mid.cloong.vault.beans.outside.queryAppOperJKStatus;

import com.neusoft.mid.cloong.vault.beans.outside.base.VaultSubBaseResponse;
import com.neusoft.mid.cloong.vault.beans.outside.enums.AuthMode;
import com.neusoft.mid.cloong.vault.beans.outside.enums.Next;
import com.neusoft.mid.cloong.vault.beans.outside.enums.SupportApplyType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "queryResult")
public class QueryAppOperJKStatusResponse extends VaultSubBaseResponse {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 表示应用下一步应该进行何种操作
	 */
	@XmlElement(required = true)
	private Next next;

	/**
	 * 本次申请所采用的认证方式
	 */
	@XmlElement(required = true)
	private List<AuthMode> authMode;

	/**
	 * 针对当前用户和敏感操作配置的金库认证策略中配置的审批人信息
	 */
	@XmlElement(required = true)
	private List<String> approver;

	/**
	 * 表示该场景在创建申请时，支持的授权方式
	 */
	@XmlElement(required = true)
	private SupportApplyType supportApplyType;

	public Next getNext() {
		return next;
	}

	public void setNext(Next next) {
		this.next = next;
	}

	public List<AuthMode> getAuthMode() {
		return authMode;
	}

	public void setAuthMode(List<AuthMode> authMode) {
		this.authMode = authMode;
	}

	public List<String> getApprover() {
		return approver;
	}

	public void setApprover(List<String> approver) {
		this.approver = approver;
	}

	public SupportApplyType getSupportApplyType() {
		return supportApplyType;
	}

	public void setSupportApplyType(SupportApplyType supportApplyType) {
		this.supportApplyType = supportApplyType;
	}
}
