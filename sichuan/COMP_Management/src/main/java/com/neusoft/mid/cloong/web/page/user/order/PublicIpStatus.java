/*******************************************************************************
 * @(#)PublicIpStatus.java 2018年5月2日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.user.order;


/**
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年5月2日 下午6:14:16 0：待创建 1：已创建 2：创建失败 3：状态异常 4：已删除
 */
public enum PublicIpStatus {
    /**
     * 待创建
     */
    PREPARE("0", "待创建"),
    /**
     * 已创建
     */
    CREATED("1", "已创建"),
    /**
     * 创建失败
     */
    CREATEERROR("2", "创建失败"),
    /**
     * 状态异常
     */
    STATUSERROR("3", "状态异常"),
    /**
     * 已删除
     */
    DELETED("4", "已删除");
    
    private String code;

    private String desc;
    
    PublicIpStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
    
    public String toString() {
        return code;
    }
    
    /**
     * getEnum 根据状态码获得用户状态枚举对象
     * @param code 状态码
     * @return 状态码对应的枚举对象
     */
    public static PublicIpStatus getEnum(String code) {
        for (PublicIpStatus us : PublicIpStatus.values()) {
            if (us.code.equals(code)) {
                return us;
            }
        }
        return null;
    }
}
