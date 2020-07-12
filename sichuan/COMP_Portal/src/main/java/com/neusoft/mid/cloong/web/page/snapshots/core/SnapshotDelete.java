package com.neusoft.mid.cloong.web.page.snapshots.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPDeleteSnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPDeleteSnapshotResp;

public interface SnapshotDelete {
	 /**
     * 申请快照接口
     * @param req 请求对象
     * @return 相应对象
     */
    public RPPDeleteSnapshotResp delete(RPPDeleteSnapshotReq req);

}
