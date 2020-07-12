/*******************************************************************************
 * @(#)IPInfo.java 2014年6月3日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm.info;

/**
 * 私网ip信息
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月3日 下午8:20:02
 */
public class IPInfo {
    
    /**
     * ip
     */
    private String ip;
    
    /**
     * ip状态
     */
    private String state;

    /**
     * 获取ip字段数据
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip字段数据
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取state字段数据
     * @return Returns the state.
     */
    public String getState() {
        return state;
    }

    /**
     * 设置state字段数据
     * @param state The state to set.
     */
    public void setState(String state) {
        this.state = state;
    }
    
}
