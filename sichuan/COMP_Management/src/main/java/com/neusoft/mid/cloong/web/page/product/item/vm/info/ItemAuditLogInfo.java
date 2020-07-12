package com.neusoft.mid.cloong.web.page.product.item.vm.info;
/**
 * 审批实例信息
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-26 下午04:44:01
 */
public class ItemAuditLogInfo {

    // 条目id
    private String itemId;

    // 设置为状态(审批状态)
    private String status;

    // 审批时间
    private String auditTime;

    // 审批人
    private String auditUser;

    // 审批意见
    private String auditInfo;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(String auditInfo) {
        this.auditInfo = auditInfo;
    }

}
