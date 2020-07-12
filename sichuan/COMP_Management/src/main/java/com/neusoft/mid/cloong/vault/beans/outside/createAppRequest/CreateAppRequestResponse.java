package com.neusoft.mid.cloong.vault.beans.outside.createAppRequest;

import com.neusoft.mid.cloong.vault.beans.outside.base.VaultSubBaseResponse;
import com.neusoft.mid.cloong.vault.beans.outside.enums.AuthMode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "queryResult")
public class CreateAppRequestResponse extends VaultSubBaseResponse {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(required = true)
	private List<AuthMode> authMode;

	/**
	 * 针对当前用户和敏感操作配置的金库认证策略中配置的审批人信息
	 */
	@XmlElement(required = true)
	private List<String> approver;

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

}
