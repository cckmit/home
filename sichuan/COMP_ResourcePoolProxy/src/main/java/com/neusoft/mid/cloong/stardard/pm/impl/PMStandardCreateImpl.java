/*******************************************************************************
 * @(#)PMStandardCreateImpl.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.pm.impl;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.idc.idc.idc.CreateResourceTemplateReqType;
import com.idc.idc.idc.CreateResourceTemplateRespType;
import com.idc.idc.idc_wsdl.Idc;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.RequestCommon;
import com.neusoft.mid.cloong.schema.standard.pm.ServerTemplate;
import com.neusoft.mid.cloong.schema.standard.pm.ServerTemplate.ResourceInfo;
import com.neusoft.mid.cloong.stardard.pm.PMStandardCreate;
import com.neusoft.mid.cloong.stardard.pm.info.PMStandardCreateReq;
import com.neusoft.mid.cloong.stardard.pm.info.PMStandardCreateResp;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建物理机资源规格接口
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.1 $ 2013-3-18 下午01:58:01
 */
public class PMStandardCreateImpl implements PMStandardCreate {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private RequestCommon common;

    private static LogService logger = LogService.getLogger(PMStandardCreateImpl.class);

    private static JAXBContext jc;

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    static {
        try {
            jc = JAXBContext.newInstance(ServerTemplate.class);
        } catch (JAXBException e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_EXP, "创建JAXB实例异常！", e);
        }
    }

    @Override
    public PMStandardCreateResp createPMStandard(PMStandardCreateReq req) {
        // 打开发送客户端
        Idc client = common.openClient(req, timeout);
        CreateResourceTemplateReqType reqType = new CreateResourceTemplateReqType();
        reqType.setResourceTemplateID(req.getStandardId());
        reqType.setResourceTemplateName(req.getStandardName());
        String standardXml = generateStandardXml(req);
        if (standardXml == null) {
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }
        reqType.setResourceTemplate(standardXml);
        PMStandardCreateResp resp = new PMStandardCreateResp();
        CreateResourceTemplateRespType respType = null;
        String resultCode = null;
        try {
            respType = client.createResourceTemplate(reqType, null, null);
            resultCode = common.obtainResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                        + "]的物理机操作失败，资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                    + "]的物理机操作失败,内部错误，网络异常或xml解析异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        } finally {
            // 关闭发送客户端
            common.closeClient();
        }
        // 组装响应
        resp.setResultMessage(respType.getFaultstring());
        resp.setResultCode(resultCode);
        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送规格编号为[" + req.getStandardId() + "]的物理机操作请求成功，获取的响应码为[")
                .append(resp.getResultCode()).append("]，响应描述为[").append(resp.getResultMessage())
                .append("]");
        logger.info(sb.toString());
        return resp;
    }

    private PMStandardCreateResp assmbleErrorResp(InterfaceResultCode code) {
        PMStandardCreateResp resp = new PMStandardCreateResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    private String generateStandardXml(PMStandardCreateReq req) {
        String standardXml = null;
        try {
            Marshaller ms = jc.createMarshaller();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ServerTemplate standReq = new ServerTemplate();
            standReq.setTemplateID(req.getStandardId());
            standReq.setMeasureMode(req.getMesureMode());
            standReq.setResourceType(req.getResourceType());
            standReq.setTemplateDesc(req.getStandardDesc());
            standReq.setTemplateStatus(req.getStatus());
            standReq.setTemplateCreator(req.getCreator());
            standReq.setCreateTime(req.getCreateTime());
            
            ResourceInfo info = new ResourceInfo();
            info.setServerType(req.getServerType());
            standReq.setResourceInfo(info);
            ms.setProperty("jaxb.formatted.output", false);
            ms.marshal(standReq, baos);
            standardXml = new String(baos.toByteArray(), "UTF-8").replaceFirst(
                    " standalone=\"yes\"", "");
        } catch (Exception e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_PARSE_EXCEPTION, "创建物理机规格XML异常！", e);
        }
        return standardXml;
    }

    public void setCommon(RequestCommon common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
