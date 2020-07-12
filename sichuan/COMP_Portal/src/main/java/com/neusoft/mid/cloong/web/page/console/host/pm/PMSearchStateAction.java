/*******************************************************************************
 * @(#)QueryPMStateFromResPoolAction.java 2014年6月4日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.pm;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMStatus;
import com.neusoft.mid.cloong.host.pm.core.QueryPMState;
import com.neusoft.mid.cloong.host.pm.core.QueryPMStateReq;
import com.neusoft.mid.cloong.host.pm.core.QueryPMStateResp;
import com.neusoft.mid.cloong.service.PMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMResultInfo;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.ResultStatusInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通过接口实时查询物理机状态
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 下午2:50:19
 */
public class PMSearchStateAction extends BaseAction {

    private static final long serialVersionUID = 5975622940694269713L;

    private static LogService logger = LogService.getLogger(PMSearchStateAction.class);

    /**
     * 返回列表
     */
    private List<ResultStatusInfo> resultStatusInfos;

    /**
     * 获取查询列表
     */
    private String queryStr;

    /**
     * 查询物理机状态
     */
    private QueryPMState queryPMState;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 页面传参 - 资源视图总览页面需传个param参数求全量而不是只取变化的量
     */
    private String param;

    /**
     * 物理机操作状态服务
     */
    private PMStatusService pmStatusService;

    @SuppressWarnings("unchecked")
    @Override
    public String execute() {
        // 要查询的vmId列表
        List<String> queryPmIds = new ArrayList<String>();
        // 前台状态map为比较查出的虚拟机状态使用
        Map<String, ResultStatusInfo> pmIdStatuss = new HashMap<String, ResultStatusInfo>();
        // 将传入的json对象queryStr转成vmId查询条件及比较使用的map集合
        queryCondition(queryPmIds, pmIdStatuss);
        resultStatusInfos = new ArrayList<ResultStatusInfo>();
        List<PMResultInfo> lsPmResultInfos = new ArrayList<PMResultInfo>();
        PMResultInfo resPools;

        for (int i = 0; i < queryPmIds.size(); i++) {
            try {
                resPools = (PMResultInfo) ibatisDAO.getSingleRecord("queryResPoolByPmId",
                        queryPmIds.get(i));
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                        getText("pm.pmqueryliststate.fail"), e);
                this.addActionError(getText("pm.pmqueryliststate.fail"));
                resultStatusInfos = null;
                return ConstantEnum.SUCCESS.toString();
            }
            QueryPMStateReq queryPMStateReq = new QueryPMStateReq();
            queryPMStateReq.setPmId(resPools.getPmId());
            queryPMStateReq.setResourcePoolId(resPools.getResPoolId());
            queryPMStateReq.setResourcePoolPartId(resPools.getResPoolPartId());
            QueryPMStateResp resp = queryPMState.queryPMState(queryPMStateReq);
            // QueryVMStateResp resp = new QueryVMStateResp();
            // resp.setResultCode(SUCCEESS_CODE);
            // resp.setVmState("4");
            if (resp.getResultCode().equals(SUCCEESS_CODE)) {
                resPools.setPmId(queryPMStateReq.getPmId());
                resPools.setStatus(stateParse(resp.getPmState()));
                lsPmResultInfos.add(resPools);
            } else {
                resPools.setPmId(queryPMStateReq.getPmId());
                resPools.setStatus(stateParse("11"));
                lsPmResultInfos.add(resPools);
            }
        }

        // 查询结果与前台传入状态比较,并设置显示汉字
        pmCompareStatus(lsPmResultInfos, pmIdStatuss);
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("物理机列表状态通过接口查询发生改变个数lenght:");
            sb.append(resultStatusInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    private PMStatus stateParse(String state) {
        PMStatus returnState = null;
        if (state.equals("0")) {
            returnState = PMStatus.PREPARE;
        }
        if (state.equals("1")) {
            returnState = PMStatus.CREATING;
        }
        if (state.equals("2")) {
            returnState = PMStatus.RUNNING;
        }
        if (state.equals("3")) {
            returnState = PMStatus.DELETED;
        }
        if (state.equals("4")) {
            returnState = PMStatus.STOP;
        }
        if (state.equals("5")) {
            returnState = PMStatus.OPERATE_FAIL;
        }
        if (state.equals("10")) {
            returnState = PMStatus.SENDERROR;
        }
        if (state.equals("11")) {
            returnState = PMStatus.STATUSERROR;
        }
        if (state.equals("12")) {
            returnState = PMStatus.PAUSE;
        }
        if (state.equals("13")) {
            returnState = PMStatus.PROCESSING;
        }
        return returnState;
    }

    /**
     * 将查询到物理机状态的结果与界面传入的状态比较。 将状态不相同的添入返回集合pmResultStatusInfos中.
     * @param lsPmResultInfos 通过接口查询到的一组物理机状态list
     * @param pmIdStatuss 前台传入一组物理机状态map
     */
    private void pmCompareStatus(List<PMResultInfo> lsPmResultInfos,
            Map<String, ResultStatusInfo> pmIdStatuss) {
        if (null != lsPmResultInfos) {
            for (PMResultInfo pmResultInfo : lsPmResultInfos) {
                // 查询得到状态
                String pmId = pmResultInfo.getPmId();
                String pmDBStatus = pmResultInfo.getStatus().getCode();
                if (pmIdStatuss.get(pmId) == null) {
                    continue;
                }
                // 资源视图/业务视图总览页面需传个param参数求全量而不是只取变化的量
                if ("1".equals(param)) {
                    ResultStatusInfo pmResultStatusInfo = pmIdStatuss.get(pmId);
                    pmResultStatusInfo.setStatusText(pmResultInfo.getStatus().getDesc());
                    pmResultStatusInfo.setStatus(pmResultInfo.getStatus().getCode());
                    resultStatusInfos.add(pmResultStatusInfo);
                } else if ("2".equals(param)) { // 物理机详情页面传个param参数
                    if (!pmDBStatus.equals(pmIdStatuss.get(pmId).getStatus())) { // 和页面上的pm状态比对，取变化的量
                        ResultStatusInfo pmResultStatusInfo = pmIdStatuss.get(pmId);
                        pmResultStatusInfo.setStatusText(pmResultInfo.getStatus().getDesc());
                        pmResultStatusInfo.setStatus(pmResultInfo.getStatus().getCode());
                        resultStatusInfos.add(pmResultStatusInfo);
                    } else { // 页面原有状态=接口新请求的状态 ，此时需判断是否中间状态
                        PMInstanceInfo pmInfo = null;
                        try {
                            pmInfo = (PMInstanceInfo) ibatisDAO
                                    .getSingleRecord("getPmDetail", pmId);
                        } catch (Exception e) {
                            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                                    MessageFormat.format(getText("pmstatus.query.fail"), pmId), e);
                        }
                        String result = "falture";
                        switch (pmInfo.getStatus()) {
                        case DELETED:
                        case OPERATE_FAIL:
                        case PAUSE:
                        case STOP:
                        case RUNNING:
                        case SENDERROR:
                        case STATUSERROR:
                            result = "success";
                            break;
                        default: {
                            result = "intermediate";
                        }
                        }
                    }
                } else { // 不需要和页面上的pm状态比对，取变化的量
                    ResultStatusInfo pmResultStatusInfo = pmIdStatuss.get(pmId);
                    pmResultStatusInfo.setStatusText(pmResultInfo.getStatus().getDesc());
                    pmResultStatusInfo.setStatus(pmResultInfo.getStatus().getCode());
                    resultStatusInfos.add(pmResultStatusInfo);
                }
                // 变化的物理机状态加入状态内存
                pmStatusService.addPMStatus(pmId, pmResultInfo.getStatus());
            }
        }
    }

    /**
     * @param queryPmIds 把JSON字符中的id放入list列表中
     * @param pmIdStatuss 把JSON字符转换成的对象放入Map集合中 key是id
     */
    @SuppressWarnings("unchecked")
    private void queryCondition(List<String> queryPmIds, Map<String, ResultStatusInfo> pmIdStatuss) {
        // 将前台传入的json字符串转换成对象集合
        List<ResultStatusInfo> queryInfos = null;
        try {
            if (!"".equals(queryStr)) {
                JSONArray json = JSONArray.fromObject(queryStr);
                queryInfos = (List<ResultStatusInfo>) JSONArray.toCollection(json,
                        ResultStatusInfo.class);
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("pm.pmqueryliststate.fail"), e);
        }
        if (null != queryInfos) {
            for (ResultStatusInfo resultStatusInfo : queryInfos) {
                if (null != resultStatusInfo) {
                    // 物理机id为空不加入查询计划
                    if (!"".equals(resultStatusInfo.getId()) && null != resultStatusInfo.getId()
                            && !("\"null\"").equals(resultStatusInfo.getId())) {
                        queryPmIds.add(resultStatusInfo.getId());
                        pmIdStatuss.put(resultStatusInfo.getId(), resultStatusInfo);
                    }

                }
            }
        }
    }

    public List<ResultStatusInfo> getResultStatusInfos() {
        return resultStatusInfos;
    }

    /**
     * 设置resultStatusInfos字段数据
     * @param resultStatusInfos The resultStatusInfos to set.
     */
    public void setResultStatusInfos(List<ResultStatusInfo> resultStatusInfos) {
        this.resultStatusInfos = resultStatusInfos;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public QueryPMState getQueryPMState() {
        return queryPMState;
    }

    public void setQueryPMState(QueryPMState queryPMState) {
        this.queryPMState = queryPMState;
    }

    /**
     * 获取param字段数据
     * @return Returns the param.
     */
    public String getParam() {
        return param;
    }

    /**
     * 设置param字段数据
     * @param param The param to set.
     */
    public void setParam(String param) {
        this.param = param;
    }

    public PMStatusService getPmStatusService() {
        return pmStatusService;
    }

    public void setPmStatusService(PMStatusService pmStatusService) {
        this.pmStatusService = pmStatusService;
    }

}
