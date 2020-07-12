package com.neusoft.mid.cloong.web.page.host.pm.order;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * 物理机状态码
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 下午12:54:19
 */
public enum PMStatusCode implements StatusCode {
    /**
     * 记录申请物理机订单入数据库
     */
    CREATEPMORDER_EXCEPTION_CODE("23000"),
    /**
     * 记录申请物理机订单入数据库
     */
    UPDATEPMORDERUNUSED_EXCEPTION_CODE("23001"),
    /**
     * 查询可用资源池出错
     */
    ONLOADQUERYRESPOOL_EXCEPTION_CODE("23002"),
    /**
     * 查询可用资源池分区出错
     */
    ONLOADQUERYRESPOOLPART_EXCEPTION_CODE("23003"),
    /**
     * 查询可用镜像出错
     */
    ONLOADQUERYROSS_EXCEPTION_CODE("23004"),
    /**
     * 查询可用条目出错
     */
    ONLOADQUERYRITEMS_EXCEPTION_CODE("23005"),
    /**
     * 向资源池发送信息组装物理机信息的子网地址，查询该用户是否申请过物理机时异常
     */
    QUERYSUBNETWORKBYUSERID_EXCEPTION_CODE("23006"),
    /**
     * 查询可用IP段
     */
    ONLOADQUERYRIPSEGMENTS_EXCEPTION_CODE("23007");

    private String code;

    PMStatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }
}
