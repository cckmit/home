package com.neusoft.mid.cloong.ipSegment.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ip.RPPIPQueryResp;

/**
 * 查询IP段接口
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月6日 上午9:31:12
 */
public interface QueryIpSegment {

    /**
     * 查询IP段接口 在创建IP段时调用
     * @param req 查询参数
     * @return 返回相应类型
     */
    public RPPIPQueryResp queryIpSegment(RPPIPQueryReq req);

}
