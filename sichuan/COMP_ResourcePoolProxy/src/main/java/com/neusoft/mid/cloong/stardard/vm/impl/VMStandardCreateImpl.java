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

import com.idc.idc.idc.CreateResourceTemplateReqType;
import com.idc.idc.idc.CreateResourceTemplateRespType;
import com.idc.idc.idc_wsdl.Idc;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.RequestCommon;
import com.neusoft.mid.cloong.schema.standard.vm.VMTemplate.ResourceInfo;
import com.neusoft.mid.cloong.schema.standard.vm.VMTemplate;
import com.neusoft.mid.cloong.stardard.vm.VMStandardCreate;
import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateReq;
import com.neusoft.mid.cloong.stardard.vm.info.VMstandardCreateResp;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建资源规格接口
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-18 下午01:58:01
 */
public class VMStandardCreateImpl implements VMStandardCreate {

    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private RequestCommon common;

    private static LogService logger = LogService.getLogger(VMStandardCreateImpl.class);

    private static JAXBContext jc;

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    /**
     * 默认VM模板虚拟化方式：配置文件注入
     */
    private String rpDefaultVMSubSystem;

    static {
        try {
            jc = JAXBContext.newInstance(VMTemplate.class);
        } catch (JAXBException e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_EXP, "创建JAXB实例异常！", e);
        }
    }

    @Override
    public VMstandardCreateResp createVMStandard(VMstandardCreateReq req) {
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
        VMstandardCreateResp resp = new VMstandardCreateResp();
        CreateResourceTemplateRespType respType = null;
        String resultCode = null;
        try {
            respType = client.createResourceTemplate(reqType, null, null);
            resultCode = common.obtainResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                        + "]的虚拟机操作失败，资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                    + "]的虚拟机操作失败,内部错误，网络异常或xml解析异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        } finally {
            // 关闭发送客户端
            common.closeClient();
        }
        // 组装响应
        resp.setResultMessage(respType.getFaultstring());
        resp.setResultCode(resultCode);
        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送规格编号为[" + req.getStandardId() + "]的虚拟机操作请求成功，获取的响应码为[")
                .append(resp.getResultCode()).append("]，响应描述为[").append(resp.getResultMessage())
                .append("]");
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
            VMTemplate standReq = new VMTemplate();
            standReq.setTemplateID(req.getStandardId());
            standReq.setResourceType(req.getResourceType());
            standReq.setMeasureMode(req.getMesureMode());
            standReq.setTemplateDesc(req.getStandardDesc());
            standReq.setTemplateStatus(req.getStatus());
            standReq.setTemplateCreator(req.getCreator());
            standReq.setCreateTime(req.getCreateTime());

            ResourceInfo info = new ResourceInfo();
            info.setCPUNum(req.getCpuNum());
            info.setMemorySize(req.getMemorySize());
            info.setStorageSize(req.getStorageSize());
            info.setVMOS(req.getVmOs());
            info.setVMSubSystem(rpDefaultVMSubSystem);
            standReq.setResourceInfo(info);
            ms.setProperty("jaxb.formatted.output", false);
            ms.marshal(standReq, baos);
            standardXml = new String(baos.toByteArray(), "UTF-8").replaceFirst(
                    " standalone=\"yes\"", "");
        } catch (Exception e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_PARSE_EXCEPTION, "创建虚拟机规格XML异常！", e);
        }
        return standardXml;
    }

    public void setCommon(RequestCommon common) {
        this.common = common;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * 设置rpDefaultVMSubSystem字段数据
     * @param rpDefaultVMSubSystem The rpDefaultVMSubSystem to set.
     */
    public void setRpDefaultVMSubSystem(String rpDefaultVMSubSystem) {
        this.rpDefaultVMSubSystem = rpDefaultVMSubSystem;
    }
}
