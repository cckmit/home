/*******************************************************************************
 * @(#)PMCreate.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMCreateResp;


/**
 * 物理机操作接口
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-14 下午04:08:11
 */
public interface PMCreate {

    PMCreateResp createPM(PMCreateReq req);
    
    /**
     * 
     * createCustomVM 高定制创建物理机，指定vlan下的具体ip
     * @param req 创建请求
     * @return resp 响应消息
     */
    RPPPMCreateResp createCustomPM(RPPPMCreateReq req);

}
