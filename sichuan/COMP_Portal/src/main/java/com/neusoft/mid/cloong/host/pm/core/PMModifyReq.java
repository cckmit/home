package com.neusoft.mid.cloong.host.pm.core;

import java.util.List;

import com.neusoft.mid.cloong.web.page.console.host.pm.info.NetInfo;

public class PMModifyReq extends ReqBodyInfo {

    private String pmId;

    private List<NetInfo> netList;

    /**
     * 操作流水号
     */
    private String serialNum;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public List<NetInfo> getNetList() {
        return netList;
    }

    public void setNetList(List<NetInfo> netList) {
        this.netList = netList;
    }

}
