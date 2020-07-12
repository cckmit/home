/*******************************************************************************
 * @(#)ResourceStatusNotifyServicePortImpl.java 2014-2-11
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.serverPort;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.transport.http.AbstractHTTPDestination;

import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job.CycReportResStatusJob;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.CycReportResCountJob;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.CycReportResCountReq;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.CycReportResCountResp;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.CycReportResStatusReq;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.CycReportResStatusResp;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.EventReportResInfoReq;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.EventReportResInfoResp;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.ResourceStatusNotifyServicePort;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.Response;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源信息上报webservice接口
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年1月13日 下午6:06:26
 */
@javax.jws.WebService(serviceName = "ResourceDetailService", portName = "ResourceStatusNotifyServicePort", targetNamespace = "http://www.CMCCCloud.com/wsdl/CMCC/SOAP/ResourceDetail/v1/services", endpointInterface = "com.neusoft.mid.cloong.rpws.private1072.resourceDetail.ResourceStatusNotifyServicePort")
public class ResourceStatusNotifyServicePortImpl implements ResourceStatusNotifyServicePort {

    private static LogService logger = LogService.getLogger(ResourceStatusNotifyServicePortImpl.class);

    /**
     * ServiceContext
     */
    @Resource
    private WebServiceContext context;

    /**
     * 计量数据上报处理JOB
     */
    private CycReportResCountJob resStatCountJob;

    /**
     * 计量数据上报处理JOB
     */
    private CycReportResStatusJob resStatusJob;

    /**
     * 周期性上报资源计量信息
     * @param req
     * @param simpleRequestHeader
     * @param simpleResponseHeader
     * @return
     * @see com.cmcccloud.wsdl.cmcc.soap.resourcedetail.v1.interfaces.ResourceStatusNotifyServicePort#cycReportResCount(com.cmcccloud.wsdl.cmcc.soap.resourcedetail.v1.local.CycReportResCountReq,
     *      com.cmcccloud.schemas.cmcc.soap.common.v1.Authorization, javax.xml.ws.Holder)
     */
    public CycReportResCountResp cycReportResCount(CycReportResCountReq req, Authorization simpleRequestHeader,
            Holder<Response> simpleResponseHeader) {
        logger.info("接收到周期上报计量信息...");
        simpleResponseHeader.value = new Response();
        CycReportResCountResp resp = resStatCountJob.process(req, simpleRequestHeader, simpleResponseHeader);

        return resp;
    }

    /**
     * 周期性上报监控信息
     * @param req
     * @param simpleRequestHeader
     * @param simpleResponseHeader
     * @return
     * @see com.cmcccloud.wsdl.cmcc.soap.resourcedetail.v1.interfaces.ResourceStatusNotifyServicePort#cycReportResStatus(com.cmcccloud.wsdl.cmcc.soap.resourcedetail.v1.local.CycReportResStatusReq,
     *      com.cmcccloud.schemas.cmcc.soap.common.v1.Authorization, javax.xml.ws.Holder)
     */
    public CycReportResStatusResp cycReportResStatus(CycReportResStatusReq req, Authorization simpleRequestHeader,
            Holder<Response> simpleResponseHeader) {
        logger.info("接收到周期上报监控信息...");
        simpleResponseHeader.value = new Response();
        CycReportResStatusResp resp = resStatusJob.process(req, simpleRequestHeader, simpleResponseHeader);
        resp.setResponseID(req.getRequestID());

        return resp;
    }

    /**
     * 容量信息上报
     * @param req
     * @param simpleRequestHeader
     * @param simpleResponseHeader
     * @return
     * @see com.cmcccloud.wsdl.cmcc.soap.resourcedetail.v1.interfaces.ResourceStatusNotifyServicePort#eventReportResInfo(com.cmcccloud.wsdl.cmcc.soap.resourcedetail.v1.local.EventReportResInfoReq,
     *      com.cmcccloud.schemas.cmcc.soap.common.v1.Authorization, javax.xml.ws.Holder)
     */
    public EventReportResInfoResp eventReportResInfo(EventReportResInfoReq req, Authorization simpleRequestHeader,
            Holder<Response> simpleResponseHeader) {
        logger.info("接收到容量信息上报不做处理!");
        return new EventReportResInfoResp();
    }

    /**
     * getClientIp 获取客户端IP地址
     * @return 客户单IP地址
     */
    public String getClientIp() {
        MessageContext ctx = context.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
        String ip = request.getRemoteAddr();
        return ip;
    }

    /** --------------------- SPRING SETTER AREA START ------------------ */
    /**
     * 设置resStatusJob字段数据
     * @param resStatusJob The resStatusJob to set.
     */
    public void setResStatusJob(CycReportResStatusJob resStatusJob) {
        this.resStatusJob = resStatusJob;
    }

    /**
     * 设置resStatCountJob字段数据
     * @param resStatCountJob The resStatCountJob to set.
     */
    public void setResStatCountJob(CycReportResCountJob resStatCountJob) {
        this.resStatCountJob = resStatCountJob;
    }
    /** --------------------- SPRING SETTER AREA END -------------------- */

}
