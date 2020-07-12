/*******************************************************************************
 * @(#)VMStandardCreateImpl.java 2014-1-10
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.common;

import com.idc.idc.idc.DeleteResourceTemplateReqType;
import com.idc.idc.idc.DeleteResourceTemplateRespType;
import com.idc.idc.idc_wsdl.Idc;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.RequestCommon;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除资源规格接口
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-10 下午01:39:15
 */
public class StandardDeleteImpl implements StandardDelete {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private RequestCommon common;

    private static LogService logger = LogService.getLogger(StandardDeleteImpl.class);

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;


    @Override
    public StandardDeleteResp deleteStandard(StandardDeleteReq req) {
        // 打开发送客户端
        Idc client = common.openClient(req, timeout);
        DeleteResourceTemplateReqType reqType = new DeleteResourceTemplateReqType();

        reqType.setResourceTemplateID(req.getStandardId());

        StandardDeleteResp resp = new StandardDeleteResp();
        DeleteResourceTemplateRespType respType = null;
        String resultCode = null;
        try {
            respType = client.deleteResourceTemplate(reqType, null, null);
            resultCode = common.obtainResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送删除规格编号为[" + req.getStandardId()
                        + "]失败，资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送删除规格编号为[" + req.getStandardId()
                    + "]失败,内部错误，网络异常或xml解析异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        } finally {
            // 关闭发送客户端
            common.closeClient();
        }
        // 组装响应
        resp.setResultMessage(respType.getFaultstring());
        resp.setResultCode(resultCode);
        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送删除规格编号为[" + req.getStandardId() + "]请求成功，获取的响应码为[").append(
                resp.getResultCode()).append("]，响应描述为[").append(resp.getResultMessage())
                .append("]");
        logger.info(sb.toString());
        return resp;
    }


    /***
     * assmbleErrorResp 组装返回消息
     * @param code 接口响应码
     * @return 资源规格删除响应对象
     */
    private StandardDeleteResp assmbleErrorResp(InterfaceResultCode code) {
        StandardDeleteResp resp = new StandardDeleteResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    public void setCommon(RequestCommon common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
