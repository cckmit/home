package com.neusoft.mid.cloong.web.page.snapshots.core;

import com.neusoft.mid.cloong.host.vm.core.QueryVMStateReq;
import com.neusoft.mid.cloong.host.vm.core.QueryVMStateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.QuerySnapshotStateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.QuerySnapshotStateResp;

public interface IsnapshotQueryState {

    /**
     * 
     * queryVMState 查询快照状态
     * @param req
     * @return 返回响应消息
     */
    QuerySnapshotStateResp querySnapshotState(QuerySnapshotStateReq req);
    
}
