/*******************************************************************************
 * @(#)PMCreate.java 2014-1-15
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp;
/**
 * 物理机创建接口
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-15 下午3:26:37
 */
public interface PMCreate {
    /**
     * 创建物理机
     * @param req 请求参数
     * @return
     */
	RPPPMCreateResp createPM(PMCreateReq req);

}
