/**
 * 
 */
package com.neusoft.mid.cloong.web.page.resourceManagement.info;

import java.util.List;

/**
 * @author x_wang
 *
 */
public class SwInfo {
    
    private String switchId;
    
    private String switchName;
    
    private String resPoolName;
    
    private String switchType;
    
    private String swVersion;
    
    private String vendorName;
    
    private String switchIp;
    
    private String curStatus;
    
    private String switchSerialnum;
    
    private String assetOriginType;
    
    private String assetState;
    
    private String assetSlaType;
    
    private String updateTime;
    
    private List<SwIfInfo> SwIfList;

    public String getSwitchId() {
        return switchId;
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public String getResPoolName() {
        return resPoolName;
    }

    public void setResPoolName(String resPoolName) {
        this.resPoolName = resPoolName;
    }

    public String getSwitchType() {
        return switchType;
    }

    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    public String getSwVersion() {
        return swVersion;
    }

    public void setSwVersion(String swVersion) {
        this.swVersion = swVersion;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSwitchIp() {
        return switchIp;
    }

    public void setSwitchIp(String switchIp) {
        this.switchIp = switchIp;
    }

    public String getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    public String getSwitchSerialnum() {
        return switchSerialnum;
    }

    public void setSwitchSerialnum(String switchSerialnum) {
        this.switchSerialnum = switchSerialnum;
    }

    public String getAssetOriginType() {
        return assetOriginType;
    }

    public void setAssetOriginType(String assetOriginType) {
        this.assetOriginType = assetOriginType;
    }

    public String getAssetState() {
        return assetState;
    }

    public void setAssetState(String assetState) {
        this.assetState = assetState;
    }

    public String getAssetSlaType() {
        return assetSlaType;
    }

    public void setAssetSlaType(String assetSlaType) {
        this.assetSlaType = assetSlaType;
    }

    public List<SwIfInfo> getSwIfList() {
        return SwIfList;
    }

    public void setSwIfList(List<SwIfInfo> swIfList) {
        SwIfList = swIfList;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
