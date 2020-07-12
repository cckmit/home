package com.neusoft.mid.cloong.vlan.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanCancelReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vlan.RPPVlanCancelResp;

/**
 * 取消vlan接口
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月3日 下午4:39:40
 */
public interface VLANCancel {

    /**
     * 取消vlan接口
     * @param 参数请求类
     * @return 相应类
     */
    public RPPVlanCancelResp cancel(RPPVlanCancelReq req);

}
