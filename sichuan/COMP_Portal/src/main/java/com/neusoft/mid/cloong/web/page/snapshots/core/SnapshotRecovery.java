package com.neusoft.mid.cloong.web.page.snapshots.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotResp;

public interface SnapshotRecovery {
	 /**
     * 申请快照接口
     * @param req 请求对象
     * @return 相应对象
     */
    public RPPRecoverySnapshotResp recovery(RPPRecoverySnapshotReq req);
}
