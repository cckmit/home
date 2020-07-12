package com.neusoft.mid.cloong.web.page.product.item.user.info;
/**
 * 用户审批实例信息
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月28日 上午11:05:44
 */
public class UserAuditLogInfo {

    // 用户id
    private String userId;

    // 设置为状态(审批状态)
    private String status;

    // 审批时间
    private String auditTime;

    // 审批人
    private String auditUser;

    // 审批意见
    private String auditInfo;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
