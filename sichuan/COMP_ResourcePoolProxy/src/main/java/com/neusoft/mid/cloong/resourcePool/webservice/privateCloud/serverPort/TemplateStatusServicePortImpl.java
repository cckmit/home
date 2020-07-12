/*******************************************************************************
 * @(#)TemplateStatusServicePortImpl.java 2015年2月12日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.webservice.privateCloud.serverPort;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job.ReportTemplateJob;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.template.server.ReportResourceTemplateReq;
import com.neusoft.mid.cloong.rpws.private1072.template.server.ReportResourceTemplateResp;
import com.neusoft.mid.cloong.rpws.private1072.template.server.Response;
import com.neusoft.mid.cloong.rpws.private1072.template.server.SetResourceTemplateReq;
import com.neusoft.mid.cloong.rpws.private1072.template.server.SetResourceTemplateResp;
import com.neusoft.mid.cloong.rpws.private1072.template.server.TemplateStatusServicePort;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 私有云模板状态上报接口
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月12日 下午9:59:57
 */
public class TemplateStatusServicePortImpl implements TemplateStatusServicePort {

    private static LogService logger = LogService.getLogger(TemplateStatusServicePortImpl.class);

    /**
     * 模板状态上报处理JOB
     */
    private ReportTemplateJob reportTemplateJob;

    /**
     * setResourceTemplate 资源池向运营设置模板状态.由于资源池未实现此方法，此处并实现该方法
     * @param parameters
     * @param simpleRequestHeader
     * @param simpleResponseHeader
     * @return
     */
    @Override
    public SetResourceTemplateResp setResourceTemplate(SetResourceTemplateReq parameters,
            Authorization simpleRequestHeader, Holder<Response> simpleResponseHeader) {
        logger.info("接收到一条模板设置报文,不做处理");
        return null;
    }

    /**
     * reportResourceTemplate 接收资源池上报的模板状态
     * @param parameters 请求报文体
     * @param simpleRequestHeader webservice请求报文头
     * @param simpleResponseHeader webservice应答报文头
     * @return webservice 应答报文体
     */
    @Override
    public ReportResourceTemplateResp reportResourceTemplate(ReportResourceTemplateReq parameters,
            Authorization simpleRequestHeader, Holder<Response> simpleResponseHeader) {
        ReportResourceTemplateResp resp = this.reportTemplateJob.process(parameters, simpleRequestHeader,
                simpleResponseHeader);
        return resp;
    }

    /** --------------------- SPRING SETTER AREA START ------------------ */

    /**
     * 设置reportTemplateJob字段数据
     * @param reportTemplateJob The reportTemplateJob to set.
     */
    public void setReportTemplateJob(ReportTemplateJob reportTemplateJob) {
        this.reportTemplateJob = reportTemplateJob;
    }

    /** --------------------- SPRING SETTER AREA END -------------------- */

}
