package com.neusoft.mid.cloong.vault.beans.outside.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class VaultBaseRequest {
	/**
     * 调用webservice所需的用户名
     */
    @XmlElement(required = true)
    private String serviceUserName;

    /**
     * 调用webservice所需的密码
     */
    @XmlElement(required = true)
    private String servicePwd;

    /**
     * 进行本次操作的用户ip
     */
    @XmlElement(required = true)
    private String userIP;
     
	public String getServiceUserName() {
		return serviceUserName;
	}

	public void setServiceUserName(String serviceUserName) {
		this.serviceUserName = serviceUserName;
	}

	public String getServicePwd() {
		return servicePwd;
	}

	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

}
