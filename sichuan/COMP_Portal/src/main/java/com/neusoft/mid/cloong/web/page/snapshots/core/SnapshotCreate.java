package com.neusoft.mid.cloong.web.page.snapshots.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPApplySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPApplySnapshotResp;

public interface SnapshotCreate {
	 /**
     * 申请快照接口
     * @param req 请求对象
     * @return 相应对象
     */
    public RPPApplySnapshotResp create(RPPApplySnapshotReq req);

}
