package com.neusoft.mid.cloong.rpproxy.interfaces.ip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 创建虚拟硬盘接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class RPPIPApplyResp extends RPPBaseResp implements Serializable {

    private static final long serialVersionUID = 1635945078071367626L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * IP地址.申请公网和承载网时有值
     */
    private List<String> ip = new ArrayList<String>();

    /**
     * IP子网，申请私网时有值.格式为192.168.1.1/xx
     */
    private String ipSubNet;

    /**
     * IP段唯一标识符申请私网时有值
     */
    private String ipSegmentURI;

    /**
     * 获取faultstring字段数据
     * @return Returns the faultstring.
     */
    public String getFaultstring() {
        return faultstring;
    }

    /**
     * 设置faultstring字段数据
     * @param faultstring The faultstring to set.
     */
    public void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

    /**
     * 获取ip字段数据
     * @return Returns the ip.
     */
    public List<String> getIp() {
        return ip;
    }

    /**
     * 设置ip字段数据
     * @param ip The ip to set.
     */
    public void setIp(List<String> ip) {
        this.ip = ip;
    }

    /**
     * 获取ipSubNet字段数据
     * @return Returns the ipSubNet.
     */
    public String getIpSubNet() {
        return ipSubNet;
    }

    /**
     * 设置ipSubNet字段数据
     * @param ipSubNet The ipSubNet to set.
     */
    public void setIpSubNet(String ipSubNet) {
        this.ipSubNet = ipSubNet;
    }

    /**
     * 获取ipSegmentURI字段数据
     * @return Returns the ipSegmentURI.
     */
    public String getIpSegmentURI() {
        return ipSegmentURI;
    }

    /**
     * 设置ipSegmentURI字段数据
     * @param ipSegmentURI The ipSegmentURI to set.
     */
    public void setIpSegmentURI(String ipSegmentURI) {
        this.ipSegmentURI = ipSegmentURI;
    }

}
