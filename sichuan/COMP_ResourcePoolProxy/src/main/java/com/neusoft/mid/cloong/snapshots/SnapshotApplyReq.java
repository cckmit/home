package com.neusoft.mid.cloong.snapshots;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPApplySnapshotReq;
/**
 * kuaizhao申请Req
 * @author <a href="mailto:futzh@neusoft.com">futzh</a>
 * @version $Revision 1.0 $ 2016年5月27日 下午2:34:43
 */
public class SnapshotApplyReq extends ReqBodyInfo {

    private RPPApplySnapshotReq info = new RPPApplySnapshotReq();

	public RPPApplySnapshotReq getInfo() {
		return info;
	}

	public void setInfo(RPPApplySnapshotReq info) {
		this.info = info;
	}
}
