/*******************************************************************************
 * @(#)VMOperator.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vfw.core;

/**
 * 
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-14 上午10:09:59
 */
public interface VFWDel {
    /**
     * 删除虚拟防火墙
     * @param PmDeleteReq
     */
    CancelVirFWServiceResp delVFW(CancelVirFWServiceReq vfwReq);
    
}
