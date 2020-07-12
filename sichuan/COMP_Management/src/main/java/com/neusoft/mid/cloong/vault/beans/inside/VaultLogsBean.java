package com.neusoft.mid.cloong.vault.beans.inside;

/*import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;*/

import java.io.Serializable;

/*@ApiModel(value = "VaultLogsModel", description = "金库操作记录表")
@TableName("vault_logs_t")*/
public class VaultLogsBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*@ApiModelProperty(value = "子系统名称")
	@JsonProperty(value = "res_num")
	@TableField("res_num")*/
	private String ResNum;

	/*@ApiModelProperty(value = "金库敏感操作代码标识")
	@JsonProperty(value = "operation_code")
	@TableField("operation_code")*/
	private String operationCode;

	/*@ApiModelProperty(value = "金库接口调用结果")
	@JsonProperty(value = "result_code")
	@TableField("result_code")*/
	private String ResultCode;


	/*@ApiModelProperty(value = "金库申请时间")
	@JsonProperty(value = "request_time")
	@TableField("request_time")*/
	private String requestTime;

	/*@ApiModelProperty(value = "金库申请ID")
	@JsonProperty(value = "request_ID")
	@TableField("request_ID")*/
	private String requestID;

	/*@ApiModelProperty(value = "金库认证方式")
	@JsonProperty(value = "auth_mode")
	@TableField("auth_mode")*/
	private String authMode;

	/*@ApiModelProperty(value = "金库认证时间")
	@JsonProperty(value = "auth_time")
	@TableField("auth_time")*/
	private String authTime;

	/*@ApiModelProperty(value = "金库认证结果")
	@JsonProperty(value = "auth_result")
	@TableField("auth_result")*/
	private boolean authResult;

	/*@ApiModelProperty(value = "授权审批意见")
	@JsonProperty(value = "JK_reason")
	@TableField("JK_reason")*/
	private String JKReason;

	/*@ApiModelProperty(value = "操作处理结果")
	@JsonProperty(value = "JK_action_result")
	@TableField("JK_action_result")*/
	private boolean JKActionResult;


	/*@ApiModelProperty(value = "时间")
	@JsonProperty(value = "start_time")
	@TableField("start_time")*/
	private String StartTime;

	/*@ApiModelProperty(value = "地点")
	@JsonProperty(value = "location")
	@TableField("location")*/
	private String Location;

	/*@ApiModelProperty(value = "主账号")
	@JsonProperty(value = "user_ID")
	@TableField("user_ID")*/
	private String userID;

	/*@ApiModelProperty(value = "从账号")
	@JsonProperty(value = "res_account")
	@TableField("res_account")*/
	private String resAccount;

	/*@ApiModelProperty(value = "操作")
	@JsonProperty(value = "action")
	@TableField("action")*/
	private String Action;

	/*@ApiModelProperty(value = "操作对象")
	@JsonProperty(value = "res_info")
	@TableField("res_info")*/
	private String resInfo;

	/*@ApiModelProperty(value = "操作结果")
	@JsonProperty(value = "action_result")
	@TableField("action_result")*/
	private boolean ActionResult;

	public String getResNum() {
		return ResNum;
	}

	public void setResNum(String resNum) {
		ResNum = resNum;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getResultCode() {
		return ResultCode;
	}

	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
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

	public String getAuthTime() {
		return authTime;
	}

	public void setAuthTime(String authTime) {
		this.authTime = authTime;
	}

	public boolean getAuthResult() {
		return authResult;
	}

	public void setAuthResult(boolean authResult) {
		this.authResult = authResult;
	}

	public String getJKReason() {
		return JKReason;
	}

	public void setJKReason(String jKReason) {
		JKReason = jKReason;
	}

	public boolean getJKActionResult() {
		return JKActionResult;
	}

	public void setJKActionResult(boolean jKActionResult) {
		JKActionResult = jKActionResult;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getResAccount() {
		return resAccount;
	}

	public void setResAccount(String resAccount) {
		this.resAccount = resAccount;
	}

	public String getAction() {
		return Action;
	}

	public void setAction(String action) {
		Action = action;
	}

	public String getResInfo() {
		return resInfo;
	}

	public void setResInfo(String resInfo) {
		this.resInfo = resInfo;
	}

	public boolean getActionResult() {
		return ActionResult;
	}

	public void setActionResult(boolean actionResult) {
		ActionResult = actionResult;
	}

}
