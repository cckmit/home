package com.neusoft.mid.cloong.vault.beans.outside.enums;

/*import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;*/

public enum SupportApplyType {
	one, // 表示支持单次申请
	duration, // 表示支持按时长申请
	all;// 两种方式都支持

	/*@JsonCreator*/
	public static SupportApplyType value(String v) {
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
