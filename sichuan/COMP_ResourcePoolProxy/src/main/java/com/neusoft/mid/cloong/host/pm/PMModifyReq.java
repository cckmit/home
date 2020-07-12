package com.neusoft.mid.cloong.host.pm;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo;

/**
 * 修改物理机请求Req
 */
public class PMModifyReq extends ReqBodyInfo {
    /**
     * 物理机id
     */
    private String pmId;

    /**
     * 物理机名称
     */
    private String pmName;


    /**
     * 网卡信息列表
     */
    private List<EthInfo> ethList = new ArrayList<EthInfo>();


	public String getPmId() {
		return pmId;
	}


	public void setPmId(String pmId) {
		this.pmId = pmId;
	}


	public String getPmName() {
		return pmName;
	}


	public void setPmName(String pmName) {
		this.pmName = pmName;
	}


	public List<EthInfo> getEthList() {
		return ethList;
	}


	public void setEthList(List<EthInfo> ethList) {
		this.ethList = ethList;
	}

   
}
