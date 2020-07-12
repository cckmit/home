package com.neusoft.mid.cloong.host.pm.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.host.pm.PMStateQuery;
import com.neusoft.mid.cloong.host.pm.PMStateQueryReq;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMStatusQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.RPPPMStatusQueryResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询物理机处理器
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 上午8:49:51
 */
public class QueryPMStateProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(QueryPMStateProcessor.class);

    /**
     * 物理机状态查询队列
     */
    private PMStateQuery pmStateQuery;

    /**
     * 请求长度正则表达式，长度在1到30
     */
    private static final Pattern P_STRING = Pattern.compile(".{1,31}");

    @Override
    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

        RPPPMStatusQueryReq rppPMStatusQueryReq = (RPPPMStatusQueryReq) reqCxt
                .getAttribute(RPPPMStatusQueryReq.REQ_BODY);
        RPPPMStatusQueryResp pmStateQueryResp = new RPPPMStatusQueryResp();

        if (!validateInputPara(rppPMStatusQueryReq)) {
            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, pmStateQueryResp);
            return FAILURE;
        }
        PMStateQueryReq pmStateQueryReq = null;
        try {
            pmStateQueryReq = assembleQueryPMStatusReq(rppPMStatusQueryReq);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送查询物理机请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息！", e);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, pmStateQueryResp);
            return FAILURE;
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送查询物理机请求失败，资源池代理系统内部错误，无法通过给定的资源池ID[" + rppPMStatusQueryReq.getResourcePoolId()
                            + "]获取到资源池信息！", e);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, pmStateQueryResp);
            return FAILURE;
        }
        if (pmStateQueryReq.getResourceUrl() == null || pmStateQueryReq.getPassword() == null) {
            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送查询物理机请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空！", null);
            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, pmStateQueryResp);
            return FAILURE;
        }
        pmStateQueryResp = pmStateQuery.queryPMState(pmStateQueryReq);
        assebleRes(pmStateQueryResp, respCxt);
        if (pmStateQueryResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
            logger.info("查询物理机状态信息成功！");
        } else {
            logger.info("查询物理机状态信息失败,接口响应码为[" + pmStateQueryResp.getResultCode() + "]，接口响应消息为["
                    + pmStateQueryResp.getResultMessage());
        }
        return SUCCESS;
    }

    private PMStateQueryReq assembleQueryPMStatusReq(RPPPMStatusQueryReq req) throws ServiceStopException,
            UnmatchException {
        PMStateQueryReq pmStateQueryReq = new PMStateQueryReq();
        pmStateQueryReq.setResourcePoolId(req.getResourcePoolId());
        pmStateQueryReq.setResourcePoolPartId(req.getResourcePoolPartId());
        pmStateQueryReq.setSerialNum(req.getSerialNum());
        pmStateQueryReq.setPmId(req.getPmId());
        pmStateQueryReq.setTimestamp(this.getTimeStampgen().generateTimeStamp());
        pmStateQueryReq.setTransactionID(this.getTransactonGen().generateTransactionId(
                pmStateQueryReq.getResourcePoolId(), pmStateQueryReq.getTimestamp()));
        ResourcePoolInfo resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                pmStateQueryReq.getResourcePoolId());
        pmStateQueryReq.setPassword(resourceInfo.getUserPassword());
        pmStateQueryReq.setResourceUrl(resourceInfo.getResourcePoolUrl());
        return pmStateQueryReq;
    }

    private void assebleRes(RPPPMStatusQueryResp queryPMStateResp, RuntimeContext resp) {
        resp.setAttribute(RPPPMStatusQueryResp.RESP_BODY, queryPMStateResp);
    }

    private boolean validateInputPara(RPPPMStatusQueryReq req) {
        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机资源池ID RES_POOL_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getResourcePoolPartId())
                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机资源池分区ID RES_POOL_PART_ID", null);
            return false;
        }
        if (StringUtils.isBlank(req.getPmId()) || (!P_STRING.matcher(req.getPmId()).matches())) {
            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查物理机编号PM_ID", null);
            return false;
        }
        return true;
    }

    /**
     * 获取pmStateQuery字段数据
     * @return Returns the pmStateQuery.
     */
    public PMStateQuery getPmStateQuery() {
        return pmStateQuery;
    }

    /**
     * 设置pmStateQuery字段数据
     * @param pmStateQuery The pmStateQuery to set.
     */
    public void setPmStateQuery(PMStateQuery pmStateQuery) {
        this.pmStateQuery = pmStateQuery;
    }

}
