package com.neusoft.mid.cloong.web.page.console.host.vm.info;

import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 虚拟机资源规格实例
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-7 下午02:00:59
 */
public class VMStandardInfo extends BaseInfo {

    private String standardName;

    private String standardId;

    private long cpuNum;

    private long ramSize;

    private long discSize;

    private String description;

    private String useStatus;
    
    private String resPoolId;
    
    private String resPoolPartId;
    
    // 条目id
    private String itemId;
    
    // 条目名称
    private String itemName;
    
    // 销售开始时间
    private String sellStartTime;
    
    // 销售结束时间
    private String sellEndTime;
    
    public String getSellStartTime() {
        return sellStartTime;
    }

    public void setSellStartTime(String sellStartTime) {
        this.sellStartTime = sellStartTime;
    }

    public String getSellEndTime() {
        return sellEndTime;
    }

    public void setSellEndTime(String sellEndTime) {
        this.sellEndTime = sellEndTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(long cpuNum) {
        this.cpuNum = cpuNum;
    }

    public long getRamSize() {
        return ramSize;
    }

    public void setRamSize(long ramSize) {
        this.ramSize = ramSize;
    }

    public long getDiscSize() {
        return discSize;
    }

    public void setDiscSize(long discSize) {
        this.discSize = discSize;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }
}
