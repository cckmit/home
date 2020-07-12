package com.neusoft.mid.cloong.web.page.product.standard.vm.info;

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
