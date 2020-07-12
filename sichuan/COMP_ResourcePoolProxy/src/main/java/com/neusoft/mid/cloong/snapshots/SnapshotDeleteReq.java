package com.neusoft.mid.cloong.snapshots;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPDeleteSnapshotReq;

public class SnapshotDeleteReq extends ReqBodyInfo{
	 private RPPDeleteSnapshotReq info = new RPPDeleteSnapshotReq();

	public RPPDeleteSnapshotReq getInfo() {
		return info;
	}

	public void setInfo(RPPDeleteSnapshotReq info) {
		this.info = info;
	}

		
}
