package com.neusoft.mid.cloong.web.page.host.vm.order.info;

import java.io.Serializable;

/**
 * 资源池信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午04:30:35
 */
public class RespoolInfo implements Serializable{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private String respoolId;

    private String respoolName;

    private String respoolZone;

    private String respoolUrl;

    private String status;

    private String userId;

    private String userPwd;

    private String description;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;

    public String getRespoolId() {
        return respoolId;
    }

    public void setRespoolId(String respoolId) {
        this.respoolId = respoolId;
    }

    public String getRespoolName() {
        return respoolName;
    }

    public void setRespoolName(String respoolName) {
        this.respoolName = respoolName;
    }

    public String getRespoolZone() {
        return respoolZone;
    }

    public void setRespoolZone(String respoolZone) {
        this.respoolZone = respoolZone;
    }

    public String getRespoolUrl() {
        return respoolUrl;
    }

    public void setRespoolUrl(String respoolUrl) {
        this.respoolUrl = respoolUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
