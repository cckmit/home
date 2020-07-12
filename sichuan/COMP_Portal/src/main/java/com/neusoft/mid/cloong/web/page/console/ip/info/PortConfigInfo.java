/*******************************************************************************
 * @(#)PortConfigInfo.java 2018年5月4日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.ip.info;

/**
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年5月4日 上午10:11:05
 */
public class PortConfigInfo {
    
    private String vmId;
    
    private String vmName;
    
    private String portConfigId;
    
    private String portConfigName;
    
    private String mappingMode;
    
    private String protocol;
    
    private String vmPrivateIp;
    
    private String vmPort;
    
    private String publicIp;
    
    private String publicPort;
    
    private String createTime;
    
    private String createUser;
    
    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getPortConfigId() {
        return portConfigId;
    }

    public void setPortConfigId(String portConfigId) {
        this.portConfigId = portConfigId;
    }

    public String getPortConfigName() {
        return portConfigName;
    }

    public void setPortConfigName(String portConfigName) {
        this.portConfigName = portConfigName;
    }

    public String getMappingMode() {
        return mappingMode;
    }

    public void setMappingMode(String mappingMode) {
        this.mappingMode = mappingMode;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getVmPrivateIp() {
        return vmPrivateIp;
    }

    public void setVmPrivateIp(String vmPrivateIp) {
        this.vmPrivateIp = vmPrivateIp;
    }

    public String getVmPort() {
        return vmPort;
    }

    public void setVmPort(String vmPort) {
        this.vmPort = vmPort;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getPublicPort() {
        return publicPort;
    }

    public void setPublicPort(String publicPort) {
        this.publicPort = publicPort;
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

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

}
