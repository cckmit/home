/*******************************************************************************
 * @(#)CycReportResStatusJob.java 2015年1月13日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job;

import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.info.RespBodyInfo;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.exception.CycReportResStatusException;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean.RespReslutCode;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.suport.BaseJob;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.template.server.ReportResourceTemplateReq;
import com.neusoft.mid.cloong.rpws.private1072.template.server.ReportResourceTemplateResp;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Response;
import com.neusoft.mid.cloong.stardard.StandardReport;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源监控信息上报处理JOB
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年1月13日 下午6:43:02
 */
public class ReportTemplateJob extends
        BaseJob<ReportResourceTemplateReq, ReportResourceTemplateResp, Authorization, Holder<Response>> {

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(ReportTemplateJob.class);

    /**
     * 资源规格上报处理类
     */
    private StandardReport standardReport;

    /**
     * 处理资源池上报的资源监控信息
     * @param parmeter
     * @return
     * @see com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.suport.BaseJob#process(java.lang.Object)
     */
    @Override
    public ReportResourceTemplateResp process(ReportResourceTemplateReq req, Authorization authInfo,
            Holder<Response> respHeader) {

        ReportResourceTemplateResp resp = new ReportResourceTemplateResp();

        try {
            // 1.校验
            boolean setp1Res = checkMessage(resp, respHeader, authInfo);
            if (!setp1Res) {
                return resp;
            }

            // 2.处理
            processReq(req, resp, authInfo);
            this.genSuccRespHeader(respHeader, authInfo);
        } catch (Exception e) {
            logger.info("发生未知的异常！", e);
            genRespHeader(respHeader, authInfo, RespReslutCode.ERROR_OHTER.getCode());
            resp.setFaultString("发生异常");
        }

        return resp;
    }

    /**
     * 处理请求
     * @author fengj<br>
     *         2015年1月14日 下午5:50:33
     * @param req 请求
     * @param resp 应答
     * @param authInfo 资源池认证信息,包括资源池ID、分区ID等信息
     * @throws CycReportResStatusException
     * @throws JAXBException
     * @throws TaskletException
     */
    private void processReq(ReportResourceTemplateReq req, ReportResourceTemplateResp resp, Authorization authInfo) {
        RespBodyInfo info = new RespBodyInfo();

        String resourcePoolId = parseIDCId(authInfo.getTransactionID());
        String resourcePoolPartId = authInfo.getZoneID();
        String transactionId = authInfo.getTransactionID();
        info.setResourcePoolId(resourcePoolId);
        info.setTransactionID(transactionId);
        InterfaceResultCode result = standardReport.process(req.getResourceTemplateID(),
                String.valueOf(req.getResourceTemplateState()), resourcePoolId, resourcePoolPartId);
        info.setResultCode(result.getResultCode());
        resp.setFaultString(result.getResultMessage());
    }

    /**
     * 检查消息是否正确
     * @author fengj<br>
     *         2015年1月14日 下午5:46:59
     * @param resp 应答报文
     * @param authInfo 鉴权信息
     * @return 鉴权是否成功
     */
    private boolean checkMessage(ReportResourceTemplateResp resp, Holder<Response> respHeader, Authorization authInfo) {
        RespReslutCode resCode = this.checkHeaderAndAuth(authInfo.getTransactionID(), authInfo.getZoneID());
        if (!RespReslutCode.SUCCESS.equals(resCode)) {
            this.genRespHeader(respHeader, authInfo, resCode.getCode());
            resp.setFaultString(resCode.getDesc());
            return false;
        }
        return true;
    }

    /**
     * genFailRespHeaderObj 生成失败的头ID
     * @param respResultCode 错误码
     */
    protected void genRespHeader(Holder<Response> respHeader, Authorization authInfo, String respResultCode) {
        respHeader.value = new Response();
        respHeader.value.setIDCAccessId(this.parseIDCId(authInfo.getTransactionID()));
        respHeader.value.setTransactionID(authInfo.getTransactionID());
    }

    /**
     * genSuccRespHeaderObj 生成成功的应答报文对象
     * @param respHeader
     * @param authInfo
     */
    protected void genSuccRespHeader(Holder<Response> respHeader, Authorization authInfo) {
        this.genRespHeader(respHeader, authInfo, RespReslutCode.SUCCESS.getCode());
    }

    /** --------------------- SPRING SETTER AREA START ------------------ */

    /**
     * 设置standardReport字段数据
     * @param standardReport The standardReport to set.
     */
    public void setStandardReport(StandardReport standardReport) {
        this.standardReport = standardReport;
    }

    /** --------------------- SPRING SETTER AREA END -------------------- */

}
