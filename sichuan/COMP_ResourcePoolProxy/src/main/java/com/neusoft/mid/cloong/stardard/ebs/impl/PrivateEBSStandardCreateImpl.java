/*******************************************************************************
 * @(#)VMStandardCreateImpl.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.ebs.impl;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.template.server.CreateResourceTemplateReq;
import com.neusoft.mid.cloong.rpws.private1072.template.server.CreateResourceTemplateResp;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Response;
import com.neusoft.mid.cloong.rpws.private1072.template.server.TemplateMgmtServicePort;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.BSResourceInfo;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.BlockStorageTemplate;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.ResourceType;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.TemplateStatus;
import com.neusoft.mid.cloong.stardard.ebs.EBSStandardCreate;
import com.neusoft.mid.cloong.stardard.ebs.info.EBSStandardCreateReq;
import com.neusoft.mid.cloong.stardard.ebs.info.EBSStandardCreateResp;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建资源规格接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午01:58:01
 */
public class PrivateEBSStandardCreateImpl implements EBSStandardCreate {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<TemplateMgmtServicePort> common;

    private static LogService logger = LogService.getLogger(PrivateEBSStandardCreateImpl.class);

    private static JAXBContext jc;

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    static {
        try {
            jc = JAXBContext.newInstance(BlockStorageTemplate.class);
        } catch (JAXBException e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_EXP, "创建JAXB实例异常！", e);
        }
    }

    @Override
    public EBSStandardCreateResp createEBSStandard(EBSStandardCreateReq req) {
        // 打开发送客户端
        TemplateMgmtServicePort client = common.openClient(req, timeout, TemplateMgmtServicePort.class);

        // 生成报文请求体
        CreateResourceTemplateReq webserviceReq = new CreateResourceTemplateReq();
        webserviceReq.setResourceTemplateID(req.getStandardId());
        String standardXml = generateStandardXml(req);
        webserviceReq.setResourceTemplate(standardXml);

        String faultstring = "";
        String resultCode = null;

        try {

            // 生成请求报文头
            Authorization simpleRequestHeader = common.genReqHeader(req);

            // 生成报文应答头
            Holder<Response> simpleResponseHeader = new Holder<Response>();

            // 发起请求
            CreateResourceTemplateResp webServiceResp = client.createResourceTemplate(webserviceReq,
                    simpleRequestHeader, simpleResponseHeader);

            // 获取应答信息
            faultstring = webServiceResp.getFaultString();
            resultCode = simpleResponseHeader.value.getResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送规格编号为[" + req.getStandardId() + "]的虚拟硬盘操作失败，资源池认证失败",
                        e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                    + "]的虚拟硬盘操作失败,内部错误，网络异常或xml解析异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }

        // 组装响应
        EBSStandardCreateResp resp = new EBSStandardCreateResp();
        resp.setResultMessage(faultstring);
        resp.setResultCode(resultCode);
        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送规格编号为[" + req.getStandardId() + "]的虚拟硬盘操作请求成功，获取的响应码为[").append(resp.getResultCode())
                .append("]，响应描述为[").append(resp.getResultMessage()).append("]");
        logger.info(sb.toString());
        return resp;
    }

    private EBSStandardCreateResp assmbleErrorResp(InterfaceResultCode code) {
        EBSStandardCreateResp resp = new EBSStandardCreateResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    private String generateStandardXml(EBSStandardCreateReq req) {
        String standardXml = null;
        try {
            Marshaller ms = jc.createMarshaller();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            BlockStorageTemplate standReq = new BlockStorageTemplate();
            standReq.setTemplateID(req.getStandardId());
            standReq.setResourceType(ResourceType.fromValue(req.getResourceType()));
            standReq.setMeasureMode(req.getMesureMode());
            standReq.setTemplateDesc(req.getStandardDesc());
            standReq.setTemplateCreator(req.getCreator());
            standReq.setCreateTime(req.getCreateTime());
            standReq.setTemplateStatus(TemplateStatus.fromValue(req.getStatus()));

            BSResourceInfo resourceInfo = new BSResourceInfo();
            resourceInfo.setRAID(Integer.valueOf(req.getRaid()));
            resourceInfo.setStorageNet(Integer.valueOf(req.getStorageNet()));
            resourceInfo.setTier(Integer.valueOf(req.getTier()));
            resourceInfo.setVolNum(Integer.valueOf(req.getVolNum()));
            resourceInfo.setVolSize(Integer.valueOf(req.getStorageSize()));
            resourceInfo.setResourceType(Integer.valueOf(req.getEbsResourceType()));
            standReq.setResourceInfo(resourceInfo);

            ms.setProperty("jaxb.formatted.output", false);
            ms.marshal(standReq, baos);
            standardXml = new String(baos.toByteArray(), "UTF-8").replaceFirst(" standalone=\"yes\"", "");
        } catch (Exception e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_PARSE_EXCEPTION, "创建虚拟硬盘规格XML异常！", e);
        }
        return standardXml;
    }

    public void setCommon(WebServiceClientFactory<TemplateMgmtServicePort> common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
