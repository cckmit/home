package com.neusoft.mid.cloong.web.page.ebs.order;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * 云硬盘状态码
 * @author <a href="mailto:yuan.x@neusoft.com">YUANXUE </a>
 * @version $Revision 1.1 $ 2013-1-7 下午12:54:19
 */
public enum EBSStatusCode implements StatusCode {
    /**
     * 记录申请云硬盘订单入数据库
     */
    CREATEEBSORDER_EXCEPTION_CODE("23000"),
    /**
     * 记录申请云硬盘订单入数据库
     */
    UPDATEEBSORDERUNUSED_EXCEPTION_CODE("23001"),
    /**
     * 查询可用资源池出错
     */
    ONLOADQUERYRESPOOL_EXCEPTION_CODE("23002"),
    /**
     * 查询可用资源池分区出错
     */
    ONLOADQUERYRESPOOLPART_EXCEPTION_CODE("23003"),
    /**
     * 查询可用条目出错
     */
    ONLOADQUERYRITEMS_EXCEPTION_CODE("23004");

    private String code;

    EBSStatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }
}
