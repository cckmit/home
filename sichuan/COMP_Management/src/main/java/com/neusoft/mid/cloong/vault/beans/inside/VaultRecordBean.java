package com.neusoft.mid.cloong.vault.beans.inside;

/*import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;*/

import java.io.Serializable;

/*@ApiModel(value = "VaultRecordModel", description = "金库操作记录表(内部使用)")
@TableName("vault_record_t")*/
public class VaultRecordBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String selectedApprover;

	private String requestTime;

	private String requestID;

	private String authMode;

	public String getSelectedApprover() {
		return selectedApprover;
	}

	public void setSelectedApprover(String selectedApprover) {
		this.selectedApprover = selectedApprover;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getAuthMode() {
		return authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}


}
