package com.neusoft.mid.cloong.web.page.console.ipSegment.info;

import java.io.Serializable;

/**
 * IP及相关虚拟机信息bean
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月12日 下午4:01:44
 */
public class IpInfo implements Serializable {

    private static final long serialVersionUID = 6361545872821689390L;

    /**
     * IP
     */
    private String ip;

    /**
     * 虚拟机ID
     */
    private String vmId;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 网卡名称
     */
    private String eth;

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
     * 获取vmId字段数据
     * @return Returns the vmId.
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 设置vmId字段数据
     * @param vmId The vmId to set.
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 获取vmName字段数据
     * @return Returns the vmName.
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * 设置vmName字段数据
     * @param vmName The vmName to set.
     */
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    /**
     * 获取eth字段数据
     * @return Returns the eth.
     */
    public String getEth() {
        return eth;
    }

    /**
     * 设置eth字段数据
     * @param eth The eth to set.
     */
    public void setEth(String eth) {
        this.eth = eth;
    }
}
