/*******************************************************************************
 * @(#)QueryVMStateResp.java 2014年6月4日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.core;

/**
 * 查询虚拟机状态应答
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 下午3:05:29
 */
public class QueryVMStateResp extends RespBodyInfo {

    /**
     * 虚拟机状态
     */
    private String vmState;
    
    /**
     * 虚拟机所在物理机编号
     */
    private String serverId;
    
    /**
     * 私网ip
     */
    private String privateIp;

    /**
     * 获取vmState字段数据
     * @return Returns the vmState.
     */
    public String getVmState() {
        return vmState;
    }

    /**
     * 设置vmState字段数据
     * @param vmState The vmState to set.
     */
    public void setVmState(String vmState) {
        this.vmState = vmState;
    }

    /**
     * 获取serverId字段数据
     * @return Returns the serverId.
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * 设置serverId字段数据
     * @param serverId The serverId to set.
     */
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    /**
     * 获取privateIp字段数据
     * @return Returns the privateIp.
     */
    public String getPrivateIp() {
        return privateIp;
    }

    /**
     * 设置privateIp字段数据
     * @param privateIp The privateIp to set.
     */
    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }
    
}
