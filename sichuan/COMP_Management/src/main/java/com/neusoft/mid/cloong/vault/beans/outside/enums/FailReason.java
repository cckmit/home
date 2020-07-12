package com.neusoft.mid.cloong.vault.beans.outside.enums;

/*import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;*/

public enum FailReason {
	error_pwd, // 用户或密码错误
	error_no_request, // 申请id不存在
	error_timeout, // 认证超时
	error_approver, // 审批人不正确
	error_maxtime;// 认证错误次数已达到最大次数3次，申请作废

	/*@JsonCreator*/
	public static FailReason value(String v) {
		if (v == null) {
			return null;
		}
		try {
			return valueOf(v.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/*@JsonValue*/
	public String value() {
		return name();
	}
}
