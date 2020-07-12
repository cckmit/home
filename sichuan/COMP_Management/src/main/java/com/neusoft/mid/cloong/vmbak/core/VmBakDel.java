/*******************************************************************************
 * @(#)VmBakDel.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vmbak.core;

/**
 * 删除虚拟机备份接口
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-23 下午02:41:00
 */
public interface VmBakDel {
    /**
     * 删除虚拟机备份
     * @param VmBakDeleteReq
     */
    VmBakDeleteResp delVmBak(VmBakDeleteReq vmBakId);
    
}
