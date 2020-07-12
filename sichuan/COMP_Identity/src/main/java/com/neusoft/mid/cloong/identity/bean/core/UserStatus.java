/*******************************************************************************
 * @(#)UserStatus.java 2014年1月14日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean.core;

/**
 * 用户状态枚举类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月10日 下午2:02:21
 */
public enum UserStatus {

    /**
     * 全部状态
     */
    ALL("", "全部"),
    /**
     * 启用状态
     */
    ENABLE("0", "启用"),
    /**
     * 停用状态
     */
    DISABLE("1", "停用"),
    /**
     * 锁定状态
     */
    LOCK("2", "锁定"),
    /**
     * 销户状态
     */
    CANCELLATION("3", "销户"),
    /**
     * 待审批状态
     */
    EXAMINATION("4", "待审批"),
    
    /**
     * 审批不通过状态
     */
    UNAUDITPASS("5", "审批不通过");

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String desc;

    /**
     * 创建一个新的用户 status构造函数
     * @param code 状态码
     * @param desc 状态描述
     */
    UserStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取code字段数据
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取desc字段数据
     * @return Returns the desc.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 
     * value 获得枚举的状态码
     * @return 枚举的状态码(code)
     */
    public String value() {
        return code;
    }

    /**
     * getEnum 根据状态码获得用户状态枚举对象
     * @param code 状态码
     * @return 状态码对应的枚举对象
     */
    public static UserStatus getEnum(String code) {
        for (UserStatus us : UserStatus.values()) {
            if (us.code.equals(code)) {
                return us;
            }
        }
        return null;
    }
}
