/*******************************************************************************
 * @(#)VfirewallRuleInfo.java 2018年5月4日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.vFirewall.info;

/**
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年5月4日 下午4:35:53
 */
public class VfirewallRuleInfo {
    
    private String fwId;
    
    private String fwRuleId;
    
    private String fwRuleName;
    
    private String protocol;
    
    private String sourceIp;
    
    private String sourcePort;
    
    private String destinationIp;
    
    private String destinationPort;

    private String actType;
    
    private String createTime;
    
    private String createUser;

    public String getFwId() {
        return fwId;
    }

    public void setFwId(String fwId) {
        this.fwId = fwId;
    }

    public String getFwRuleId() {
        return fwRuleId;
    }

    public void setFwRuleId(String fwRuleId) {
        this.fwRuleId = fwRuleId;
    }

    public String getFwRuleName() {
        return fwRuleName;
    }

    public void setFwRuleName(String fwRuleName) {
        this.fwRuleName = fwRuleName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
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
}
