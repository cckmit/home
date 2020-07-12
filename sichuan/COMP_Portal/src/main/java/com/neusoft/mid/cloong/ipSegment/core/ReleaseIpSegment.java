package com.neusoft.mid.cloong.ipSegment.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPReleaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPReleaseResp;

/**
 * 释放IP段接口
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月6日 上午9:35:43
 */
public interface ReleaseIpSegment {

    /**
     * 释放IP段接口 须校验IP段中是否有IP在用
     * @param req 参数类
     * @return 相应类
     */
    public RPPIPReleaseResp release(RPPIPReleaseReq req);
}
