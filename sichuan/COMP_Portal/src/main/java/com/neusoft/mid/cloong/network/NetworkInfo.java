/*******************************************************************************
 * @(#)NetworkInfo.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.network;

/**
 * 虚拟机相关网络信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 下午03:57:21
 */
public class NetworkInfo {
    /**
     * 虚拟机绑定的公网IP
     */
    private String ip;

    /**
     * IP应用的安全组
     */
    private String securityGroup;

    /**
     * IP绑定带宽
     */
    private String bandwith;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }

    public String getBandwith() {
        return bandwith;
    }

    public void setBandwith(String bandwith) {
        this.bandwith = bandwith;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("公网IP为：").append(this.ip).append("\n");
        sb.append("安全组名称为：").append(this.securityGroup).append("\n");
        sb.append("带宽为：").append(this.bandwith).append("\n");
        return sb.toString();
    }
}
