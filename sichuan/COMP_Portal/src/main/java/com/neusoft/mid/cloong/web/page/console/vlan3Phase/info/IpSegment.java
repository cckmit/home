package com.neusoft.mid.cloong.web.page.console.vlan3Phase.info;

import java.io.Serializable;

/**
 * 
 * ip段
 * @author <a href="mailto:he.jf@neusoft.com">he.jf</a>
 * @version $Revision 1.0 $ 2015-3-10 上午9:45:37
 */
public class IpSegment  implements Serializable {
    
    /**
     * serialVersionUID : TODO 这里请补充该字段的简述说明
     */
    private static final long serialVersionUID = 2139470082022955717L;

    /**
     * 订单号
     */
    private String caseId;

    /**
     * IP段唯一标识符
     */
    private String ipSegmentId;

    /**
     * IP段描述
     */
    private String ipSegmentDesc;

    /**
     * 业务ID
     */
    private String appId;

    /**
     * 资源池ID
     */
    private String resPoolID;
    
    /**
     * 资源池分区ID
     */
    private String resPoolPartID;

    /**
     * 网段
     */
    private String ipSubnet;
    
    /**
     * 起始IP
     */
    private String startIp ;
    
    /**
     * 结束IP
     */
    private String endIp ;
    
    /**
     * ip总数
     */
    private String ipTotal;

    /**
     * 是否释放标识
0：未释放（默认值）
1：已释放
     */
    private String ipRelease;

    /**
     * 获取caseId字段数据
     * @return Returns the caseId.
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 设置caseId字段数据
     * @param caseId The caseId to set.
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * 获取ipSegmentId字段数据
     * @return Returns the ipSegmentId.
     */
    public String getIpSegmentId() {
        return ipSegmentId;
    }

    /**
     * 设置ipSegmentId字段数据
     * @param ipSegmentId The ipSegmentId to set.
     */
    public void setIpSegmentId(String ipSegmentId) {
        this.ipSegmentId = ipSegmentId;
    }

    /**
     * 获取ipSegmentDesc字段数据
     * @return Returns the ipSegmentDesc.
     */
    public String getIpSegmentDesc() {
        return ipSegmentDesc;
    }

    /**
     * 设置ipSegmentDesc字段数据
     * @param ipSegmentDesc The ipSegmentDesc to set.
     */
    public void setIpSegmentDesc(String ipSegmentDesc) {
        this.ipSegmentDesc = ipSegmentDesc;
    }

    /**
     * 获取appId字段数据
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appId字段数据
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取resPoolID字段数据
     * @return Returns the resPoolID.
     */
    public String getResPoolID() {
        return resPoolID;
    }

    /**
     * 设置resPoolID字段数据
     * @param resPoolID The resPoolID to set.
     */
    public void setResPoolID(String resPoolID) {
        this.resPoolID = resPoolID;
    }

    /**
     * 获取resPoolPartID字段数据
     * @return Returns the resPoolPartID.
     */
    public String getResPoolPartID() {
        return resPoolPartID;
    }

    /**
     * 设置resPoolPartID字段数据
     * @param resPoolPartID The resPoolPartID to set.
     */
    public void setResPoolPartID(String resPoolPartID) {
        this.resPoolPartID = resPoolPartID;
    }

    /**
     * 获取ipSubnet字段数据
     * @return Returns the ipSubnet.
     */
    public String getIpSubnet() {
        return ipSubnet;
    }

    /**
     * 设置ipSubnet字段数据
     * @param ipSubnet The ipSubnet to set.
     */
    public void setIpSubnet(String ipSubnet) {
        this.ipSubnet = ipSubnet;
    }

    /**
     * 获取startIp字段数据
     * @return Returns the startIp.
     */
    public String getStartIp() {
        return startIp;
    }

    /**
     * 设置startIp字段数据
     * @param startIp The startIp to set.
     */
    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    /**
     * 获取endIp字段数据
     * @return Returns the endIp.
     */
    public String getEndIp() {
        return endIp;
    }

    /**
     * 设置endIp字段数据
     * @param endIp The endIp to set.
     */
    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    /**
     * 获取ipTotal字段数据
     * @return Returns the ipTotal.
     */
    public String getIpTotal() {
        return ipTotal;
    }

    /**
     * 设置ipTotal字段数据
     * @param ipTotal The ipTotal to set.
     */
    public void setIpTotal(String ipTotal) {
        this.ipTotal = ipTotal;
    }

    /**
     * 获取ipRelease字段数据
     * @return Returns the ipRelease.
     */
    public String getIpRelease() {
        return ipRelease;
    }

    /**
     * 设置ipRelease字段数据
     * @param ipRelease The ipRelease to set.
     */
    public void setIpRelease(String ipRelease) {
        this.ipRelease = ipRelease;
    }


}
