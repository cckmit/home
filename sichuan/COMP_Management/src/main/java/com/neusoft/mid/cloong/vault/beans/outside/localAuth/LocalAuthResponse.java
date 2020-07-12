package com.neusoft.mid.cloong.vault.beans.outside.localAuth;

import com.neusoft.mid.cloong.vault.beans.outside.base.VaultBaseResponse;
import com.neusoft.mid.cloong.vault.beans.outside.enums.FailReason;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "authResult")
public class LocalAuthResponse extends VaultBaseResponse {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 认证结果
     */
    @XmlElement(required = true)
    private boolean authResult;

    /**
     * 如果认证失败，此处给出失败原因
     */
    @XmlElement(required = true)
    private FailReason failReason;

    /**
     * 表示认证错误次数
     */
    @XmlElement(required = true)
    private String errorTimes;

    public boolean getAuthResult() {
        return authResult;
    }

    public void setAuthResult(boolean authResult) {
        this.authResult = authResult;
    }

    public FailReason getFailReason() {
        return failReason;
    }

    public void setFailReason(FailReason failReason) {
        this.failReason = failReason;
    }

    public String getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(String errorTimes) {
        this.errorTimes = errorTimes;
    }

}
