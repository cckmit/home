/*******************************************************************************
 * @(#)VMStandardCreate.java 2014-1-10
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.common;


/**
 * 
 * 虚拟硬盘规格修改接口
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-10 下午01:30:35
 */
public interface StandardDelete {
    /**
     * 
     * modifyVMStandard 
     * 修改虚拟机资源模板
     * @param req 请求
     * @return
     */
    StandardDeleteResp deleteStandard(StandardDeleteReq req);
}
