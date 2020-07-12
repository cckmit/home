package com.neusoft.mid.cloong.vlan.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanApplyResp;

/**
 * 申请VLAN接口
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月2日 下午2:23:16
 */
public interface VLANCreate {

    /**
     * 申请vlan接口
     * @param req 请求对象
     * @return 相应对象
     */
    public RPPVlanApplyResp create(RPPVlanApplyReq req);

}
