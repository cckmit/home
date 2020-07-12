/*******************************************************************************
 * @(#)VMStandardCreateImpl.java 2014-1-10
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.common;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.template.server.DeleteResourceTemplateReq;
import com.neusoft.mid.cloong.rpws.private1072.template.server.DeleteResourceTemplateResp;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Response;
import com.neusoft.mid.cloong.rpws.private1072.template.server.TemplateMgmtServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 私有云删除资源规格接口
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-10 下午01:39:15
 */
public class PirviateStandardDeleteImpl implements StandardDelete {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<TemplateMgmtServicePort> common;

    private static LogService logger = LogService.getLogger(PirviateStandardDeleteImpl.class);

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    @Override
    public StandardDeleteResp deleteStandard(StandardDeleteReq req) {
        // 打开发送客户端
        TemplateMgmtServicePort client = common.openClient(req, timeout, TemplateMgmtServicePort.class);

        String resultCode = null;
        String faultstring = null;
        try {

            // 创建请求报文问题
            // 注意，这里的规格ID，其实就是模板ID
            DeleteResourceTemplateReq webserviceReq = new DeleteResourceTemplateReq();
            webserviceReq.setResourceTemplateID(req.getStandardId());

            // 生成请求报文头
            Authorization auth = common.genReqHeader(req);

            // 生成应答报文头
            Holder<Response> simpleResponseHeader = new Holder<Response>();

            DeleteResourceTemplateResp webServiceResp = client.deleteResourceTemplate(webserviceReq, auth,
                    simpleResponseHeader);
            faultstring = webServiceResp.getFaultString();
            resultCode = simpleResponseHeader.value.getResultCode();

        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送删除规格编号为[" + req.getStandardId() + "]失败，资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送删除规格编号为[" + req.getStandardId()
                    + "]失败,内部错误，网络异常或xml解析异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }

        // 组装响应
        StandardDeleteResp resp = new StandardDeleteResp();
        resp.setResultMessage(faultstring);
        resp.setResultCode(resultCode);
        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送删除规格编号为[" + req.getStandardId() + "]请求成功，获取的响应码为[").append(resp.getResultCode())
                .append("]，响应描述为[").append(resp.getResultMessage()).append("]");
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

    public void setCommon(WebServiceClientFactory<TemplateMgmtServicePort> common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
