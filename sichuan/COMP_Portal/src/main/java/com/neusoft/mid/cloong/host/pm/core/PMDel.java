/*******************************************************************************
 * @(#)PMDel.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm.core;

/**删除物理机操作接口
 * TODO 这里请补充该类型的简述说明
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:29:56
 */
public interface PMDel {
    /**
     * 删除操作物理机
     * @param VmDeleteReq
     */
    PmDeleteResp delPm(PmDeleteReq pmId);
    
}
