/*******************************************************************************
 * @(#)StandardSynchronizeDel.java 2014-1-10
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.common;

/**
 * 通用向资源池同步删除资源规格信息
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-10 上午10:39:11
 */

public interface StandardSynchronizeDelete {
    /**
     * @param req 资源规格信息
     * @return 同步删除状态
     */
    StandardSynchronizeDeleteResp synchronizeDeleteStandard(String req);
}
