package com.neusoft.mid.cloong.web;

/**
 * 以变量形式记录返回值
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.1 $ 2013-1-10 下午04:35:13
 */
public enum ConstantEnum {
    /**
     * 成功时
     */
    SUCCESS("success"),
    /**
     * 出错时
     */
    ERROR("error"),
    /**
     * 失败时
     */
    FAILURE("failure"),
    /**
     * 已删除
     */
    DELETE("deleted"),
    /**
     * 没有权限
     */
    NOPERMISSION("nopermission"),
    /**
     * 中间状态
     */
    INTERMEDIATE("intermediate"),
    /**
     * 操作状态非法
     */
    STATUSINVALID("statusinvalid"),
    /**
     * 接口错误
     */
    INTERFACEERROR("interfaceerror");

    private String value;

    /**
     * @deprecated
     */
    public static final int INT_1024 = 1024;

    /**
     * @deprecated
     */

    ConstantEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public String getName() {
        return "my name :" + value;
    }
}
