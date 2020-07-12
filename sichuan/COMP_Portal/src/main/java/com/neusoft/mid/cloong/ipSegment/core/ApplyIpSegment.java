package com.neusoft.mid.cloong.ipSegment.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPApplyResp;

/**
 * 创建IP段接口
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月6日 上午9:33:53
 */
public interface ApplyIpSegment {

    /**
     * 申请IP段接口
     * @param req 申请参数;类
     * @return 相应类
     */
    public RPPIPApplyResp apply(RPPIPApplyReq req);
}
