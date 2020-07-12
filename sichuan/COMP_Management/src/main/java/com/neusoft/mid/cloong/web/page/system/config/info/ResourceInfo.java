package com.neusoft.mid.cloong.web.page.system.config.info;

import com.neusoft.mid.cloong.common.core.ResPoolStatus;
import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 资源池实例
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-6 上午10:38:50
 */
public class ResourceInfo extends BaseInfo {

    private String resPoolId;

    private String resPoolName;

    private String resPoolZone;

    private String resPoolUrl;

    private String userCode;

    private String userPwd;

    private ResPoolStatus status;

    private String description;

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getResPoolName() {
        return resPoolName;
    }

    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    public String getResPoolZone() {
        return resPoolZone;
    }

    public void setResPoolZone(String resPoolZone) {
        this.resPoolZone = resPoolZone;
    }

    public String getResPoolUrl() {
        return resPoolUrl;
    }

    public void setResPoolUrl(String resPoolUrl) {
        this.resPoolUrl = resPoolUrl;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public ResPoolStatus getStatus() {
        return status;
    }

    public void setStatus(ResPoolStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
