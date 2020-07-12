/*******************************************************************************
 * @(#)VMBAKStandardSynchronize.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourceProxy.standard.vmbak;

/**
 * 向资源池同步资源规格信息
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-18 下午04:37:56
 */
public interface VMBAKStandardSynchronize {
    /**
     * @param req 资源规格信息
     * @return 同步状态
     */
    VMBAKStandardSynchronizeResp synchronizeStandard(VMBAKStandardSynchronizeReq req);
}
