package com.neusoft.mid.cloong.vault.beans.outside.enums;

/*import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;*/

public enum AuthMode {
	remoteAuth,//远程授权，即采用短信审批模式
	localAuth,//现场授权
	autoAuth, //自动授权
	remoteAuthModeWithoutPass;//远程授权，无验证码方式

	/*@JsonCreator*/
	public static AuthMode value(String v) {
		if (v == null) {
			return null;
		}
		try {
			return valueOf(v);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/*@JsonValue*/
	public String value() {
		return name();
	}
}
