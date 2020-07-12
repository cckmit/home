/*******************************************************************************
 * @(#)ResouceOperator.java 2013-2-4
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMModifyResp;

/**虚拟机创建接口
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:30:28
 */
public interface VMModify {
    /**
     * 创建虚拟机
     * @param req 请求参数
     * @return VMModifyResp
     */
    RPPVMModifyResp modifyVM(VMModifyReq req);
}
