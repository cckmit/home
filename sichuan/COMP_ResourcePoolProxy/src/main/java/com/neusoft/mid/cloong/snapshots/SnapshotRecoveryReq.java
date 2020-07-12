package com.neusoft.mid.cloong.snapshots;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotReq;

public class SnapshotRecoveryReq extends ReqBodyInfo {

    private RPPRecoverySnapshotReq info = new RPPRecoverySnapshotReq();

	public RPPRecoverySnapshotReq getInfo() {
		return info;
	}

	public void setInfo(RPPRecoverySnapshotReq info) {
		this.info = info;
	}

}
