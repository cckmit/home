package com.neusoft.mid.cloong.vault.beans.outside.enums;

/*import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;*/

public enum ResendResult {
	sms_success,//短信重发成功
	sms_failed;//短信重发失败

	/*@JsonCreator*/
	public static ResendResult value(String v) {
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
