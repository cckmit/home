package com.neusoft.mid.cloong.web.page.host.vm.order;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * 虚拟机状态码
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 下午12:54:19
 */
public enum VMStatusCode implements StatusCode {
    /**
     * 记录申请虚拟机订单入数据库
     */
    CREATEVMORDER_EXCEPTION_CODE("23000"),
    /**
     * 记录申请虚拟机订单入数据库
     */
    UPDATEVMORDERUNUSED_EXCEPTION_CODE("23001"),
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
     * 向资源池发送信息组装虚拟机信息的子网地址，查询该用户是否申请过虚拟机时异常
     */
    QUERYSUBNETWORKBYUSERID_EXCEPTION_CODE("23006"),
    /**
     * 向资源池发送信息组装时,查询克隆备份ID对应虚拟机实例时异常
     */
    QUERYVMINSTANCEBYVMBACKUPID_EXCEPTION_CODE("23007"),

    /**
     * 查询可用IP段
     */
    ONLOADQUERYRIPSEGMENTS_EXCEPTION_CODE("23008");

    private String code;

    VMStatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }
}
