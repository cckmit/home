/*******************************************************************************
 * @(#)VmBakManager.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core;

/**
 * 虚拟机备份机管理接口
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:38:00
 */
public interface VmBakManager {
    /**
     * 操作虚拟机备份，包括：恢复
     * @param req 虚拟机备份请求
     * @return 成功返回true,失败返回false
     */
    VmBakOperatorResp operateVmBak(VmBakOperatorReq req);

}
