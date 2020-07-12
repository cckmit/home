/*******************************************************************************
 * @(#)PublicCloudPortTypeImpl.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.service;

import com.idc.idc.schemas.PublicCloudPortType;
import com.idc.idc.schemas.xsd.ObjectFactory;
import com.idc.idc.schemas.xsd.ReportResourceStateReq;
import com.idc.idc.schemas.xsd.ReportResourceStateResp;
import com.idc.idc.schemas.xsd.ReportResourceTemplateReq;
import com.idc.idc.schemas.xsd.ReportResourceTemplateResp;
import com.neusoft.mid.cloong.Authorization;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.ReqBodyThreadLcoal;
import com.neusoft.mid.cloong.RespBodyThreadLcoal;
import com.neusoft.mid.cloong.info.RespBodyInfo;
import com.neusoft.mid.cloong.stardard.StandardReport;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池资源上报服务
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-19 下午03:37:40
 */
public class PublicCloudPortTypeImpl implements PublicCloudPortType {

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(PublicCloudPortTypeImpl.class);

    /**
     * 资源规格上报处理类
     */
    private StandardReport standardReport;

    /**
     * 对请求进行鉴权
     */
    private Authorization authorization;

    @Override
    public ReportResourceStateResp reportResourceState(ReportResourceStateReq parameters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReportResourceTemplateResp reportResourceTemplate(ReportResourceTemplateReq parameters) {
        ReportResourceTemplateResp resp = new ReportResourceTemplateResp();
        RespBodyInfo info = new RespBodyInfo();
        String resourcePoolId = ReqBodyThreadLcoal.get().getResourcePoolId();
        String resourcePoolPartId = ReqBodyThreadLcoal.get().getResourcePoolPartId();
        String transactionId = ReqBodyThreadLcoal.get().getTransactionID();
        info.setResourcePoolId(resourcePoolId);
        info.setTransactionID(transactionId);
        ObjectFactory factory = new ObjectFactory();
        if (!authorization.authorize(ReqBodyThreadLcoal.get())) {
            logger.error(CommonStatusCode.AUTH_ERROR, "对资源池上报请求鉴权失败", null);
            resp
                    .setFaultstring(factory
                            .createReportResourceTemplateRespFaultstring(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL
                                    .getResultMessage()));
            info.setResultCode(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL.getResultCode());
            RespBodyThreadLcoal.set(info);
            return resp;
        }
        ReqBodyThreadLcoal.unset();
        InterfaceResultCode result = standardReport.process(parameters.getResourceTemplateID(),
                String.valueOf(parameters.getResourceTemplateState()), resourcePoolId,
                resourcePoolPartId);
        info.setResultCode(result.getResultCode());
        resp.setFaultstring(factory.createReportResourceTemplateRespFaultstring(result
                    .getResultMessage()));
        RespBodyThreadLcoal.set(info);
        return resp;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public void setStandardReport(StandardReport standardReport) {
        this.standardReport = standardReport;
    }

}
