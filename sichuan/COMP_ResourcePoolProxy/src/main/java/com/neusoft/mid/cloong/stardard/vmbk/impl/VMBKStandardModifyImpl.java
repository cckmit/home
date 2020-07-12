/*******************************************************************************
 * @(#)VMStandardModifyImpl.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vmbk.impl;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.idc.idc.idc.ModifyResourceTemplateReqType;
import com.idc.idc.idc.ModifyResourceTemplateRespType;
import com.idc.idc.idc_wsdl.Idc;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.RequestCommon;
import com.neusoft.mid.cloong.schema.standard.vmbk.VMBackupTemplate;
import com.neusoft.mid.cloong.stardard.vmbk.VMBKStandardModify;
import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardModifyReq;
import com.neusoft.mid.cloong.stardard.vmbk.info.VMBKStandardModifyResp;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改虚拟机备份规格接口
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月22日 下午5:18:56
 */
public class VMBKStandardModifyImpl implements VMBKStandardModify {
    /**
     * 向资源池进行请求的公用类，用户创建客户端类和组装响应信息
     */
    private RequestCommon common;

    private static LogService logger = LogService.getLogger(VMBKStandardModifyImpl.class);

    private static JAXBContext jc;

    /**
     * 接收超时时间ms
     */
    private long timeout = 5000;

    static {
        try {
            jc = JAXBContext.newInstance(VMBackupTemplate.class);
        } catch (JAXBException e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_EXP, "虚拟机备份创建JAXB实例异常！", e);
        }
    }

    @Override
    public VMBKStandardModifyResp modifyVMBKStandard(VMBKStandardModifyReq req) {
        // 打开发送客户端
        Idc client = common.openClient(req, timeout);
        ModifyResourceTemplateReqType reqType = new ModifyResourceTemplateReqType();
        reqType.setResourceTemplateID(req.getStandardId());
        String standardXml = generateStandardXml(req);
        if (standardXml == null) {
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        }
        reqType.setResourceTemplate(standardXml);
        VMBKStandardModifyResp resp = new VMBKStandardModifyResp();
        ModifyResourceTemplateRespType respType = null;
        String resultCode = null;
        try {
            respType = client.modifyResourceTemplate(reqType, null, null);
            resultCode = common.obtainResultCode();
        } catch (Exception e) {
            if ("authentication failed!".equals(e.getMessage())) {
                logger.error(CommonStatusCode.AUTH_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                        + "]的虚拟机备份操作失败，资源池认证失败", e);
                return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL);
            }
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送规格编号为[" + req.getStandardId()
                    + "]的虚拟机备份操作失败,内部错误，网络异常或xml解析异常", e);
            return assmbleErrorResp(InterfaceResultCode.RESOURCE_POOL_NETWORD_ERROR);
        } finally {
            // 关闭发送客户端
            common.closeClient();
        }
        // 组装响应
        resp.setResultMessage(respType.getFaultstring());
        resp.setResultCode(resultCode);
        StringBuilder sb = new StringBuilder();
        sb.append("向资源池发送规格编号为[" + req.getStandardId() + "]的虚拟机备份操作请求成功，获取的响应码为[").append(
                resp.getResultCode()).append("]，响应描述为[").append(resp.getResultMessage())
                .append("]");
        logger.info(sb.toString());
        return resp;
    }

    private VMBKStandardModifyResp assmbleErrorResp(InterfaceResultCode code) {
        VMBKStandardModifyResp resp = new VMBKStandardModifyResp();
        resp.setResultCode(code.getResultCode());
        resp.setResultMessage(code.getResultMessage());
        return resp;
    }

    private String generateStandardXml(VMBKStandardModifyReq req) {
        String standardXml = null;
        try {
            Marshaller ms = jc.createMarshaller();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            VMBackupTemplate standReq = new VMBackupTemplate();
            standReq.setTemplateID(req.getStandardId());
            standReq.setMeasureMode(req.getMesureMode());
            standReq.setResourceType(req.getResourceType());
            standReq.setTemplateDesc(req.getStandardDesc());
            standReq.setTemplateStatus(req.getStatus());
            standReq.setTemplateCreator(req.getCreator());
            standReq.setCreateTime(req.getCreateTime());
            VMBackupTemplate.ResourceInfo info = new VMBackupTemplate.ResourceInfo();
            info.setVMBackupSize(req.getVMBackupSize());
            standReq.setResourceInfo(info);
            ms.setProperty("jaxb.formatted.output", false);
            ms.marshal(standReq, baos);
            standardXml = new String(baos.toByteArray(), "UTF-8").replaceFirst(
                    " standalone=\"yes\"", "");
        } catch (Exception e) {
            logger.error(CommonStatusCode.JAXBCONTEXT_PARSE_EXCEPTION, "创建虚拟机备份规格XML异常！", e);
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
