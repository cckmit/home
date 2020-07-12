/*******************************************************************************
 * @(#)RespReslutCode.java 2014年2月12日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean;


/**
 * 异常状态码枚举类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月12日 下午1:51:48
 */
public enum RespReslutCode {
    
    SUCCESS("00000000","成功"),
    ERROR_HEADER_TRANSACTIONID_INVALIED("20000000","WebService头TRANSACTIONID字段校验失败"),
    ERROR_HEADER_IDCACCESSID_NOEXIST("20000010","资源池上报的ID在运营平台中不存在"),
    ERROR_HEADER_IDCACCESSID_IP_ILLEGAL("20000011","资源池IP校验失败"),
    ERROR_HEADER_ZONEID_NOEXIST("20000020","资源池上报的分区ID在运营平台中不存在"),
    ERROR_BUSINESSOPT_REPORT_RES_COUNT_EXIST_SAMECYCLE("21000000","一些资源的在该周期内的计量数据已经存在"),
    ERROR_BUSINESSOPT_REPORT_RES_COUNT_NOEXIST_RESOURCEID("21000001","一些资源ID在运营平台中不存在"),
    ERROR_OHTER("99999999","请求失败");
    
    /**
     * 状态代码
     */
    private String code;
    /**
     * 状态描述
     */
    private String desc;
    /**
     * 状态
     * @param code 状态代码
     * @param desc 状态描述
     */
    RespReslutCode(String code,String desc){
        this.code = code;
        this.desc = desc;
    }
    /**
     * 获取条目状态
     * @param code 状态字符串
     * @return 存在返回 RoleStatus 枚举类型.否则返回null
     */
    public static RespReslutCode obtainItemStatusEunm(String code) {
        for (RespReslutCode status : RespReslutCode.values()) {
            if (code.equals(status.getCode())) {
                return status;
            }
        }
        return null;
    }
    public String getCode() {
        return code;
    }
    /**
     * 字符串格式代码
     * @return 返回字符串格式代码
     */
    public String toString() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
    

}
