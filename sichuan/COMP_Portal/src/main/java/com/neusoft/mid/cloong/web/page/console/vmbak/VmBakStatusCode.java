package com.neusoft.mid.cloong.web.page.console.vmbak;

import com.neusoft.mid.iamp.logger.StatusCode;

/**虚拟机备份状态码
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2014-2-15 下午6:08:41
 */
public enum VmBakStatusCode implements StatusCode {
    /**
     * 记录申请虚拟机备份订单入数据库
     */
    CREATEVMBAKORDER_EXCEPTION_CODE("26000"),
    /**
     * 查询资源池分区ID出错
     */
    QUERYRESPOOLPARTID_EXCEPTION_CODE("26001");

    /**
     * 状态码
     */
    private String code;

    /**构造方法
     * @param code 状态码
     */
    VmBakStatusCode(String code) {
        this.code = code;
    }

    /**获取code
     * @return 状态码
     */
    public String getCode() {
        return code;
    }

    /**to String
     * @return 状态码
     */
    public String toString() {
        return code;
    }
}
