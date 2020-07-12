package com.neusoft.mid.cloong.rpproxy.interfaces.snapshots;

import java.io.Serializable;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

public class RPPQuerySnapshotResp  extends RPPBaseResp implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8697487107304803728L;
	private List<SnapshotBeanQry> snapshotInfo;
	/**
     * 故障描述
     */
    private String faultstring;
	public List<SnapshotBeanQry> getSnapshotInfo() {
		return snapshotInfo;
	}
	public void setSnapshotInfo(List<SnapshotBeanQry> snapshotInfo) {
		this.snapshotInfo = snapshotInfo;
	}
	public String getFaultstring() {
		return faultstring;
	}
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
    
}
