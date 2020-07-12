package com.neusoft.mid.cloong.vault.beans.outside.enums;

/*import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;*/

public enum Next {
	pass_without_jk,
	pass_with_jk,
	apply,
	auth,
	no_permission_none_scene,
	no_permission_none_approver;


    /*@JsonCreator*/
    public static Next value(String v) {
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
