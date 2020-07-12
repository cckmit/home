/*******************************************************************************
 * @(#)VMOperator.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.vm.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMCreateResp;


/**
 * 虚拟机操作接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 上午10:13:56
 */
public interface VMCreate {

    /**
     * 
     * createVM 普通创建虚拟机，可指定vlan也可以不指定
     * @param req 创建请求
     * @return resp 响应消息
     */
    VMCreateResp createVM(VMCreateReq req);
    
    /**
     * 
     * createCustomVM 高定制创建虚拟机，指定vlan下的具体ip
     * @param req 创建请求
     * @return resp 响应消息
     */
    RPPVMCreateResp createCustomVM(RPPVMCreateReq req);

}
