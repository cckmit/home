package com.neusoft.mid.cloong.web.page.snapshots.core;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPQuerySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPQuerySnapshotResp;

public interface SnapshotQuery {
	 /**
     * 申请快照接口
     * @param req 请求对象
     * @return 相应对象
     */
    public RPPQuerySnapshotResp query(RPPQuerySnapshotReq req);
}
