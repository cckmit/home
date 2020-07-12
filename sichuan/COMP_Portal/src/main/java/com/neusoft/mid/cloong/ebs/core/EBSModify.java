package com.neusoft.mid.cloong.ebs.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSModifyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSModifyResp;

/**
 * 修改云硬盘接口
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月26日 下午3:13:00
 */
public interface EBSModify {

    /**
     * 修改云硬盘大小 只可以往大修改
     * @param req 请求实例
     * @return 返回的实例
     */
    RPPEBSModifyResp modify(RPPEBSModifyReq req);
}
