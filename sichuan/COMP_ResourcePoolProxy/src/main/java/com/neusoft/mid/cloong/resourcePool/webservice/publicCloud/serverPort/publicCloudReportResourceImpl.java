/*******************************************************************************
 * @(#)PublicCloudPortTypeImpl.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.webservice.publicCloud.serverPort;

import java.util.List;

import javax.xml.ws.Holder;

import com.cloudmaster.cmp.service.lifeCycle.huawei.ObjectFactory;
import com.cloudmaster.cmp.service.lifeCycle.huawei.PublicCloudPortType;
import com.cloudmaster.cmp.service.lifeCycle.huawei.ReportResourceStateReq;
import com.cloudmaster.cmp.service.lifeCycle.huawei.ReportResourceStateResp;
import com.cloudmaster.cmp.service.lifeCycle.huawei.ReportResourceTemplateReq;
import com.cloudmaster.cmp.service.lifeCycle.huawei.ReportResourceTemplateResp;
import com.cloudmaster.cmp.service.lifeCycle.huawei.ResourceUsedSet;
import com.cloudmaster.cmp.service.lifeCycle.huawei.Response;
import com.neusoft.mid.cloong.Authorization;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.ReqBodyThreadLcoal;
import com.neusoft.mid.cloong.RespBodyThreadLcoal;
import com.neusoft.mid.cloong.info.RespBodyInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池容量信息上报服务
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-1-9 上午10:04:17
 */
public class publicCloudReportResourceImpl implements PublicCloudPortType {

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(publicCloudReportResourceImpl.class);

    /**
     * 资源池容量信息上报处理类
     */
    private ReportResourceStateContrl reportResourceStateContrl;

    /**
     * 对请求进行鉴权
     */
    private Authorization authorization;

    @Override
    public ReportResourceStateResp reportResourceState(ReportResourceStateReq resourceStates,
            com.cloudmaster.cmp.service.lifeCycle.huawei.Authorization arg1, Holder<Response> arg2) {
        ReportResourceStateResp resp = new ReportResourceStateResp();// 返回消息结果类
        RespBodyInfo info = new RespBodyInfo();// 返回资源池公共响应消息

        // 从消息头中获取资源池ID和资源池分区ID
        String resourcePoolId = ReqBodyThreadLcoal.get().getResourcePoolId();
        info.setResourcePoolId(resourcePoolId);
        String resourcePoolPartId = ReqBodyThreadLcoal.get().getResourcePoolPartId();
        info.setResourcePoolPartId(resourcePoolPartId);
        info.setTransactionID(ReqBodyThreadLcoal.get().getTransactionID());
        // 从消息体中获取资源具体信息列表
        List<ResourceUsedSet> resourceStateList = resourceStates.getResourceUsedInfo();

        ObjectFactory factory = new ObjectFactory();
        if (!authorization.authorize(ReqBodyThreadLcoal.get())) {
            logger.error(CommonStatusCode.AUTH_ERROR, "对资源池容量信息上报请求鉴权失败", null);
            resp.setFaultstring(factory
                    .createReportResourceTemplateRespFaultstring(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL
                            .getResultMessage()));// 资源池认证失败
            info.setResultCode(InterfaceResultCode.RESOURCE_POOL_AUTH_FAIL.getResultCode());// 资源池认证失败
            info.setTransactionID(ReqBodyThreadLcoal.get().getTransactionID());
            RespBodyThreadLcoal.set(info);
            return resp;
        }
        ReqBodyThreadLcoal.unset();

        // 上报处理类处理
        InterfaceResultCode result = reportResourceStateContrl.process(resourcePoolId,
                resourcePoolPartId, resourceStateList);
        info.setResultCode(result.getResultCode());
        resp.setFaultstring(factory.createReportResourceTemplateRespFaultstring(result
                .getResultMessage()));
        RespBodyThreadLcoal.set(info);

        return resp;
    }

    @Override
    public ReportResourceTemplateResp reportResourceTemplate(ReportResourceTemplateReq arg0,
            com.cloudmaster.cmp.service.lifeCycle.huawei.Authorization arg1, Holder<Response> arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public void setReportResourceStateContrl(ReportResourceStateContrl reportResourceStateContrl) {
        this.reportResourceStateContrl = reportResourceStateContrl;
    }

}
