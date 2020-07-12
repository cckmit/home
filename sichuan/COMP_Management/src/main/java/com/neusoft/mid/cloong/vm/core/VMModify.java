/*******************************************************************************
 * @(#)VMOperator.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vm.core;


/**
 * 虚拟机操作接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 上午10:13:56
 */
public interface VMModify {

    VMModifyResp modifyVM(VMModifyReq req);

}
