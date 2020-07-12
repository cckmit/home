package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

public class RPPApplySnapshotResp extends RPPBaseResp implements Serializable{
	
	/**
     * 故障描述
     */
    private String faultstring;
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3553464135357134165L;
	/**
     * 申请的snapshot_id号
     */
    private List<String> snapshotIdList = new ArrayList<String>();
	
    public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	public List<String> getSnapshotIdList() {
		return snapshotIdList;
	}
	public void setSnapshotIdList(List<String> snapshotIdList) {
		this.snapshotIdList = snapshotIdList;
	}
    
}
