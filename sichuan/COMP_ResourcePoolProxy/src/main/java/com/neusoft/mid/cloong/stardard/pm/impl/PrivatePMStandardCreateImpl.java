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
import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.WebServiceClientFactory;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.template.server.CreateResourceTemplateReq;
import com.neusoft.mid.cloong.rpws.private1072.template.server.CreateResourceTemplateResp;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Response;
import com.neusoft.mid.cloong.rpws.private1072.template.server.TemplateMgmtServicePort;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.PMResourceInfo;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.ResourceType;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.ServerTemplate;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.TemplateStatus;
import com.neusoft.mid.cloong.stardard.pm.PMStandardCreate;
import com.neusoft.mid.cloong.stardard.pm.info.PMStandardCreateReq;
import com.neusoft.mid.cloong.stardard.pm.info.PMStandardCreateResp;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建物理机资源规格接口
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.1 $ 2013-3-18 下午01:58:01
 */
public class PrivatePMStandardCreateImpl implements PMStandardCreate {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<TemplateMgmtServicePort> common;;

    private static LogService logger = LogService.getLogger(PrivatePMStandardCreateImpl.class);

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
        TemplateMgmtServicePort client = common.openClient(req, timeout,
                TemplateMgmtServicePort.class);

        // 生成请求正文内容
        CreateResourceTemplateReq reqParameters = new CreateResourceTemplateReq();
        reqParameters.setResourceTemplateID(req.getStandardId());
        String standardXml = generateStandardXml(req);
        if (standardXml == null) {
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }
        reqParameters.setResourceTemplate(standardXml);

        // 生成请求头
        Authorization simpleRequestHeader = common.genReqHeader(req);

        String faultstring = "";
        String resultCode = null;
        try {

            Holder<Response> simpleResponseHeader = new Holder<Response>();
            CreateResourceTemplateResp webServiceResp = client.createResourceTemplate(
                    reqParameters, simpleRequestHeader, simpleResponseHeader);
            faultstring = webServiceResp.getFaultString();
            resultCode = simpleResponseHeader.value.getResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                        + "]的物理机操作失败，资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                    + "]的物理机操作失败,内部错误，网络异常或xml解析异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }

        // 组装响应
        PMStandardCreateResp resp = new PMStandardCreateResp();
        resp.setResultMessage(faultstring);
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
            standReq.setResourceType(ResourceType.fromValue(req.getResourceType()));
            standReq.setTemplateDesc(req.getStandardDesc());
            standReq.setTemplateStatus(TemplateStatus.fromValue(req.getStatus()));
            standReq.setTemplateCreator(req.getCreator());
            standReq.setCreateTime(req.getCreateTime());

            PMResourceInfo info = new PMResourceInfo();
            info.setServerType(req.getServerType());
            info.setCpuType(req.getCpuType());
            info.setMemorySize(Integer.valueOf(req.getMemorySize()));
            info.setOsSize(Integer.valueOf(req.getStorageSize()));
            info.setEthAdaNum(Integer.valueOf(req.getEthAdaNum()));
            info.setEthAdaType(req.getEthAdaType());
            info.setHbaNum(Integer.valueOf(req.getHBANum()));
            info.setHbaType(req.getHBAType());
            info.setScsiAdaNum(Integer.valueOf(req.getSCSIAdaNum()));
            info.setOs(req.getOs());

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

    public void setCommon(WebServiceClientFactory<TemplateMgmtServicePort> common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
