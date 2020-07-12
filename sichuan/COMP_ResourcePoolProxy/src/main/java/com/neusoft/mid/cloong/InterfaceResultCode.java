/*******************************************************************************
 * @(#)InterfaceResultCode.java 2013-2-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import com.neusoft.mid.iamp.logger.StatusCode;

/**
 * 资源池代理接口响应码和响应描述
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-6 下午04:04:02
 */
public enum InterfaceResultCode implements StatusCode {

    SUCCESS("00000000", "成功"),

    RESOURCE_POOL_NETWORD_ERROR("60000001", "资源池系统网络异常或XML解析异常"),

    INPUT_PARR_ERROR("60000002", "请求参数异常"),

    RESOURCE_POOL_AUTH_FAIL("60000003", "资源池认证失败"),

    RESOURCE_POOL_PROXY_AUTH_FAIL("60000004", "资源池代理系统对自服务系统请求认证失败"),

    RESOURCE_POOL_ROXY_DATABASE_ERROR("60000005", "资源池代理系统内部错误，数据库异常"),

    RESOURCE_POOL_ROXY_DATAPARA_ERROR("60000006", "资源池代理系统内部错误，无法通过资源池ID获取资源池密码和资源池URL"),

    RESOURCE_POOL_ROXY_THREAD_ERROR("60000008", "资源池代理系统内部错误，启动线程异常"),

    RESOURCE_POOL_ROXY_TIMEOUT_ERROR("60000009", "资源池代理系统内部错误，请求超时异常"),

    RESOURCE_POOL_ROXY_SENDMSG_ERROR("60000010", "资源池代理系统向资源池发送消息异常"),

    RESOURCE_POOL_ROXY_UPDATE_ERROR("60000011", "资源池代理系统内部错误，更新资源失败");

    InterfaceResultCode(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    private final String resultMessage;

    private final String resultCode;

    public String getResultMessage() {
        return resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

}
