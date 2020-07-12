/*******************************************************************************
 * @(#)VMStandardCreateImpl.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vm.impl;

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
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.ResourceType;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.TemplateStatus;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.VMResourceInfo;
import com.neusoft.mid.cloong.rpws.private1072.template.xmlbean.bean.VmTemplate;
import com.neusoft.mid.cloong.stardard.vm.VMStandardCreate;
import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateReq;
import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateResp;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建资源规格接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午01:58:01
 */
public class PrivarteVMStandardCreateImpl implements VMStandardCreate {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private WebServiceClientFactory<TemplateMgmtServicePort> resPoolClientCommon;

    private static LogService logger = LogService.getLogger(PrivarteVMStandardCreateImpl.class);

    private static JAXBContext jc;

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    static {
        try {
            jc = JAXBContext.newInstance(VmTemplate.class);
        } catch (JAXBException e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_EXP, "创建JAXB实例异常！", e);
        }
    }

    @Override
    public VMstandardCreateResp createVMStandard(VMstandardCreateReq req) {
        // 打开发送客户端
        TemplateMgmtServicePort client = resPoolClientCommon.openClient(req, timeout, TemplateMgmtServicePort.class);

        // 生成请求正文内容
        CreateResourceTemplateReq reqParameters = new CreateResourceTemplateReq();
        reqParameters.setResourceTemplateID(req.getStandardId());
        String standardXml = generateStandardXml(req);
        if (standardXml == null) {
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }
        reqParameters.setResourceTemplate(standardXml);

        // 生成请求头
        Authorization simpleRequestHeader = resPoolClientCommon.genReqHeader(req);

        String faultstring = "";
        String resultCode = null;

        try {

            Holder<Response> simpleResponseHeader = new Holder<Response>();
            CreateResourceTemplateResp webServiceResp = client.createResourceTemplate(reqParameters,
                    simpleRequestHeader, simpleResponseHeader);
            faultstring = webServiceResp.getFaultString();
            resultCode = simpleResponseHeader.value.getResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送规格编号为[" + req.getStandardId() + "]的虚拟机操作失败，资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                    + "]的虚拟机操作失败,内部错误，网络异常或xml解析异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }

        // 组装响应
        VMstandardCreateResp resp = new VMstandardCreateResp();
        resp.setResultMessage(faultstring);
        resp.setResultCode(resultCode);
        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送规格编号为[" + req.getStandardId() + "]的虚拟机操作请求成功，获取的响应码为[").append(resp.getResultCode())
                .append("]，响应描述为[").append(resp.getResultMessage()).append("]");
        logger.info(sb.toString());
        return resp;
    }

    private VMstandardCreateResp assmbleErrorResp(InterfaceResultCode code) {
        VMstandardCreateResp resp = new VMstandardCreateResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    private String generateStandardXml(VMstandardCreateReq req) {
        String standardXml = null;
        try {
            Marshaller ms = jc.createMarshaller();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            VmTemplate standReq = new VmTemplate();
            standReq.setTemplateID(req.getStandardId());
            standReq.setResourceType(ResourceType.fromValue(req.getResourceType()));
            standReq.setMeasureMode(req.getMesureMode());
            standReq.setTemplateDesc(req.getStandardDesc());
            standReq.setTemplateStatus(TemplateStatus.fromValue(req.getStatus()));
            standReq.setTemplateCreator(req.getCreator());
            standReq.setCreateTime(req.getCreateTime());

            VMResourceInfo info = new VMResourceInfo();
            info.setCPUNum(Integer.valueOf(req.getCpuNum()));
            info.setMemorySize(Integer.valueOf(req.getMemorySize()));
            info.setOSSize(Integer.valueOf(req.getStorageSize()));
            info.setVMOS(req.getVmOs());

            info.setOSDiskType(req.getOsDiskType());
            info.setVEthAdaNum(Integer.valueOf(req.getvEthAdaNum()));
            info.setVSCSIAdaNum(Integer.valueOf(req.getVSCSIAdaNum()));
            info.setVFCHBANum(Integer.valueOf(req.getVFCHBANum()));

            standReq.setResourceInfo(info);
            ms.setProperty("jaxb.formatted.output", false);
            ms.marshal(standReq, baos);
            standardXml = new String(baos.toByteArray(), "UTF-8").replaceFirst(" standalone=\"yes\"", "");
        } catch (Exception e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_PARSE_EXCEPTION, "创建虚拟机规格XML异常！", e);
        }
        return standardXml;
    }

    public void setCommon(WebServiceClientFactory<TemplateMgmtServicePort> resPoolClientCommon) {
        this.resPoolClientCommon = resPoolClientCommon;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
