/*******************************************************************************
 * @(#)VFWStrategyAdd.java 2018年5月10日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vfw.core;

/**
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年5月10日 下午2:04:14
 */
public interface VFWStrategyAdd {
/**
 * 添加虚拟防火墙策略
 * @param vfwStrategyReq
 * @return
 */
    AddVirFWStrategyResp addVFWStrategy(AddVirFWStrategyReq vfwStrategyReq);
}
