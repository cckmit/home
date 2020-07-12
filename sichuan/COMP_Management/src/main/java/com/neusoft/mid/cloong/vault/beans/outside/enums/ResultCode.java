package com.neusoft.mid.cloong.vault.beans.outside.enums;

/*import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;*/

public enum ResultCode {
	error_authentication,//用户名或密码错误
	success,
	error_autoswitch_yes,//webservice服务出现异常,自动切换开关是打开状态
	error_autoswitch_no, // webservice服务出现异常,自动切换开关是关闭状态
	error_no_applicant,//申请人无效
	error_no_applicat_mobile,//申请人没有配置手机号
	error_no_approver,//没有配置审批人
	error_no_approver_mobile,//审批人全都没有配置手机号，无法发送审批短信
	error_no_request,//根据id查询不到申请
	validParam_xxx;//传递的xxx参数为空

    /*@JsonCreator*/
    public static ResultCode value(String v) {
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
