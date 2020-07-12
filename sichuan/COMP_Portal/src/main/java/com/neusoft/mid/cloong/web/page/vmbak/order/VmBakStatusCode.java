package com.neusoft.mid.cloong.web.page.vmbak.order;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * 虚拟机备份状态码
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-22 下午02:48:11
 */
public enum VmBakStatusCode implements StatusCode {
    /**
     * 记录申请虚拟机备份任务订单入数据库
     */
    CREATEPMORDER_EXCEPTION_CODE("23000"),
    /**
     * 记录申请虚拟机备份任务订单入数据库
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
     * 查询可用条目出错
     */
    ONLOADQUERYRITEMS_EXCEPTION_CODE("23005"),
    /**
     * 向资源池发送信息组装虚拟机备份信息的子网地址，查询该用户是否申请过虚拟机备份时异常
     */
    QUERYSUBNETWORKBYUSERID_EXCEPTION_CODE("23006");

    private String code;

    VmBakStatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }
}
