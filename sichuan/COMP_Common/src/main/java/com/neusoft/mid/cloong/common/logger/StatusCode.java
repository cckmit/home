/*******************************************************************************
 * @(#)StatusCode.java 2009-5-4
 *
 * Copyright 2009 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.logger;

/**
 * 状态码接口，所有的状态码Enum必须实现此接口。 此接口无具体接口函数 。 状态码为5为的数字字符,其中首位字符为组件范围编码，其他标识具体定将在状态码手册中定义。
 * <p>
 * a) 1xxxx 业务流引擎状态码，此系列代码为业务流引擎引用。 12xxx为业务流处理成功状态码。 14xxx为业务流引擎错误状态
 * </p>
 * <p>
 * b) 2xxxx 运行平台状态码。
 * </p>
 * <p>
 * c) 3xxxx 服务器错误，此类错误为应用服务器引发的错。
 * </p>
 * <p>
 * d) 4xxxx 产品类状态码。
 * </p>
 * <p>
 * 其他为保留类型
 * </p>
 * @author <a href="mailto:zhaoxb@neusoft.com">Xiaobin Zhao </a>
 * @version $Revision 1.1 $ 2009-5-4 下午04:18:08
 */
public interface StatusCode {

}
