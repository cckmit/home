/*******************************************************************************
 * @(#)EBSCreate.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.ebs.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSCreateResp;

/**虚拟硬盘操作接口
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-21 下午02:34:31
 */
public interface EBSCreate {
	/**
	 * 
	 * createEBS 虚拟硬盘操作接口
	 * @param req 请求消息
	 * @return TODO 响应消息
	 */

	RPPEBSCreateResp createEBS(RPPEBSCreateReq req);

}
