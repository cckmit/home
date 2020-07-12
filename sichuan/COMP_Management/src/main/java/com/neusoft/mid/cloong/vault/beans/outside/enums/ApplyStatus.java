package com.neusoft.mid.cloong.vault.beans.outside.enums;

/*import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;*/

public enum ApplyStatus {
	unApprove,//待审批
	approved,//已审批
	refused,//已拒绝
	valid,//已生效
	invalid;//已过期


    /*@JsonCreator*/
    public static ApplyStatus value(String v) {
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
