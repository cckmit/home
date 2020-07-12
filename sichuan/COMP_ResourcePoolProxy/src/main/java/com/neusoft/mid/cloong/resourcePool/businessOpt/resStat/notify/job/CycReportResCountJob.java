/*******************************************************************************
 * @(#)CycReportResCountProcessor.java 2014年2月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean.ResourceType;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean.RespReslutCode;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.tasklet.BaseResCountTasklet;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.tasklet.bean.ResCountTaskRes;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.suport.BaseJob;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.CycReportResCountReq;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.CycReportResCountResp;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.ResourceStatusSet;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.Response;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.SRVConsumeList;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.StorageServiceConsumeList;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.VMHostConsumeList;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源计量上报处理JOB
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月11日 下午5:14:48
 */
public class CycReportResCountJob extends
        BaseJob<CycReportResCountReq, CycReportResCountResp, Authorization, Holder<Response>> {

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(CycReportResCountJob.class);

    /**
     * PM计量数据处理任务
     */
    private BaseResCountTasklet<SRVConsumeList, ResCountTaskRes> pmTasklet;

    /**
     * VM计量数据处理任务
     */
    private BaseResCountTasklet<VMHostConsumeList, ResCountTaskRes> vmTasklet;

    /**
     * EBS计量数据处理任务
     */
    private BaseResCountTasklet<StorageServiceConsumeList, ResCountTaskRes> ebsTasklet;

    /**
     * BK计量数据处理任务
     */
    private BaseResCountTasklet<StorageServiceConsumeList, ResCountTaskRes> bkTasklet;

    /**
     * process 处理方法
     * @param parmeter 请求
     * @return 返回应答结果
     * @see com.neusoft.mid.cloong.resourcePool.businessOpt.ResStat.notify.job.BaseJob#process(java.lang.Object)
     */
    public CycReportResCountResp process(CycReportResCountReq parmeter, Authorization authInfo,
            Holder<Response> respHeader) {

        CycReportResCountResp resp = new CycReportResCountResp();

        try {
            // 1.校验
            boolean setp1Res = checkMessageStep1(resp, respHeader, authInfo);
            if (!setp1Res) {
                return resp;
            }

            // 2.将数据做校验及入库
            step2(parmeter, resp, authInfo, respHeader);

        } catch (Exception e) {
            logger.info("发生未知的异常！", e);
            genRespHeader(respHeader, authInfo, RespReslutCode.ERROR_OHTER.getCode());
            resp.setFaultString("发生异常");
        }

        return resp;
    }

    /**
     * step2 处理计量数据并入库
     * @param parmeter 计量请求数据
     * @param resp 计量应答
     * @param authInfo
     * @return SETP结果
     * @throws TaskletException 任务执行异常！
     */
    private boolean step2(CycReportResCountReq parmeter, CycReportResCountResp resp, Authorization authInfo,
            Holder<Response> respHeader) throws TaskletException {
        String startTime = xmlGalendarToDateStr(parmeter.getStartTime());
        String endTime = this.xmlGalendarToDateStr(parmeter.getEndTime());
        int periodTime = parmeter.getPeriodTime();
        String resPoolId = this.parseIDCId(authInfo.getTransactionID());
        String resPoolPartId = authInfo.getZoneID();
        List<ResourceStatusSet> list = parmeter.getResourceStatusInfo().getResourceStatusSet();

        // 在库中已经存在的资源列表ID
        Map<Integer, ResCountTaskRes> existResIdMap = new HashMap<Integer, ResCountTaskRes>();

        // 遍历资源
        for (ResourceStatusSet resourceStatusSet : list) {

            switch (resourceStatusSet.getResourceType()) {
            case ResourceType.RESOURCE_TYPE_PM:
                ResCountTaskRes pmTRes = pmTasklet.execute(resourceStatusSet.getSRVInfoSet(), startTime, endTime,
                        periodTime, resPoolId, resPoolPartId);
                if (pmTRes.getExistCountInCompResId().size() > 0 || pmTRes.getNoExistInCompResId().size() > 0) {
                    existResIdMap.put(ResourceType.RESOURCE_TYPE_PM, pmTRes);
                }
                break;

            case ResourceType.RESOURCE_TYPE_VM:
                ResCountTaskRes vmTRes = vmTasklet.execute(resourceStatusSet.getVMHostInfoSet(), startTime, endTime,
                        periodTime, resPoolId, resPoolPartId);
                if (vmTRes.getExistCountInCompResId().size() > 0 || vmTRes.getNoExistInCompResId().size() > 0) {
                    existResIdMap.put(ResourceType.RESOURCE_TYPE_VM, vmTRes);
                }
                break;

            case ResourceType.RESOURCE_TYPE_EBS:
                ResCountTaskRes bsTRes = ebsTasklet.execute(resourceStatusSet.getStorageServiceInfoSet(), startTime,
                        endTime, periodTime, resPoolId, resPoolPartId);
                if (bsTRes.getExistCountInCompResId().size() > 0 || bsTRes.getNoExistInCompResId().size() > 0) {
                    existResIdMap.put(ResourceType.RESOURCE_TYPE_EBS, bsTRes);
                }
                break;

            case ResourceType.RESOURCE_TYPE_BK:
                ResCountTaskRes bkTRes = bkTasklet.execute(resourceStatusSet.getStorageServiceInfoSet(), startTime,
                        endTime, periodTime, resPoolId, resPoolPartId);
                if (bkTRes.getExistCountInCompResId().size() > 0 || bkTRes.getNoExistInCompResId().size() > 0) {
                    existResIdMap.put(ResourceType.RESOURCE_TYPE_BK, bkTRes);
                }
                break;

            default:
                break;
            }
        }

        // 没有发现任何重复的数据
        if (existResIdMap.isEmpty()) {
            genSuccRespHeader(respHeader, authInfo);
        } else {
            genRespHeader(respHeader, authInfo,
                    RespReslutCode.ERROR_BUSINESSOPT_REPORT_RES_COUNT_EXIST_SAMECYCLE.getCode());
            genCountExistRespBody(resp, existResIdMap);
        }

        // 最后一个步骤，直接返回true
        return true;
    }

    /**
     * checkMessageStep1 步骤1
     * @param resp 应答对象
     * @return 返回处理结果 true 继续 false 终止
     */
    private boolean checkMessageStep1(CycReportResCountResp resp, Holder<Response> respHeader, Authorization authInfo) {
        RespReslutCode resCode = this.checkHeaderAndAuth(authInfo.getTransactionID(), authInfo.getZoneID());
        if (!RespReslutCode.SUCCESS.equals(resCode)) {
            this.genRespHeader(respHeader, authInfo, resCode.getCode());
            resp.setFaultString(resCode.getDesc());
            return false;
        }
        return true;
    }

    /**
     * genCountExistRespBody 生成周期内数据已存在错误的应答报文
     * @param resp 应答对象
     * @param existResIdMap 周期计量数据已存在的
     */
    private void genCountExistRespBody(CycReportResCountResp resp, Map<Integer, ResCountTaskRes> existResIdMap) {
        StringBuilder strb = new StringBuilder();

        for (Map.Entry<Integer, ResCountTaskRes> entry : existResIdMap.entrySet()) {
            int key = entry.getKey();
            ResCountTaskRes result = entry.getValue();
            List<String> existCountIdList = result.getExistCountInCompResId();

            if (key == ResourceType.RESOURCE_TYPE_PM) {
                strb.append("SRV");
            }
            if (key == ResourceType.RESOURCE_TYPE_VM) {
                strb.append("VM");
            }
            if (key == ResourceType.RESOURCE_TYPE_EBS) {
                strb.append("BS");
            }
            if (key == ResourceType.RESOURCE_TYPE_BK) {
                strb.append("BK");
            }
            strb.append("\n");

            if (existCountIdList.size() > 0) {
                strb.append(RespReslutCode.ERROR_BUSINESSOPT_REPORT_RES_COUNT_EXIST_SAMECYCLE.getDesc());
                strb.append(":");
                strb.append("(").append(existCountIdList.toString()).append(")").append("\n");
            }

            List<String> noExistIdList = result.getNoExistInCompResId();
            if (noExistIdList.size() > 0) {
                strb.append(RespReslutCode.ERROR_BUSINESSOPT_REPORT_RES_COUNT_NOEXIST_RESOURCEID.getDesc());
                strb.append(":");
                strb.append("(").append(noExistIdList.toString()).append(")").append("\n\n");
            }

        }

        resp.setFaultString(strb.toString());
    }

    /**
     * genFailRespHeaderObj 生成失败的头ID
     * @param respResultCode 错误码
     */
    protected void genRespHeader(Holder<Response> respHeader, Authorization authInfo, String respResultCode) {
        respHeader.value.setIDCAccessId(this.parseIDCId(authInfo.getTransactionID()));
        respHeader.value.setTransactionID(authInfo.getTransactionID());
        respHeader.value.setResultCode(respResultCode);
    }

    /**
     * genSuccRespHeaderObj 生成成功的应答报文对象
     * @param respHeader
     * @param authInfo
     */
    protected void genSuccRespHeader(Holder<Response> respHeader, Authorization authInfo) {
        this.genRespHeader(respHeader, authInfo, RespReslutCode.SUCCESS.getCode());
    }

    /**
     * 设置pmTasklet字段数据
     * @param pmTasklet The pmTasklet to set.
     */
    public void setPmTasklet(BaseResCountTasklet<SRVConsumeList, ResCountTaskRes> pmTasklet) {
        this.pmTasklet = pmTasklet;
    }

    /**
     * 设置vmTasklet字段数据
     * @param vmTasklet The vmTasklet to set.
     */
    public void setVmTasklet(BaseResCountTasklet<VMHostConsumeList, ResCountTaskRes> vmTasklet) {
        this.vmTasklet = vmTasklet;
    }

    /**
     * 设置ebsTasklet字段数据
     * @param ebsTasklet The ebsTasklet to set.
     */
    public void setEbsTasklet(BaseResCountTasklet<StorageServiceConsumeList, ResCountTaskRes> ebsTasklet) {
        this.ebsTasklet = ebsTasklet;
    }

    /**
     * 设置bkTasklet字段数据
     * @param bkTasklet The bkTasklet to set.
     */
    public void setBkTasklet(BaseResCountTasklet<StorageServiceConsumeList, ResCountTaskRes> bkTasklet) {
        this.bkTasklet = bkTasklet;
    }

}
