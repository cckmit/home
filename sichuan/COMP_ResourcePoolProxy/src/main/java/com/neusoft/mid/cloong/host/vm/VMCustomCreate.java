/*******************************************************************************
 * @(#)ResouceOperator.java 2013-2-4
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;


/**虚拟机创建接口
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:30:28
 */
public interface VMCustomCreate {
    /**
     * 
     * createCustomVM 高定制创建虚拟机，指定vlan下的具体ip
     * @param req 创建请求
     * @return resp 响应消息
     */
    VMCustomCreateResp createCustomVM(VMCustomCreateReq req);
}
