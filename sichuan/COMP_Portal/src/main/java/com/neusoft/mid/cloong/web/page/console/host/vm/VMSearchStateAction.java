/*******************************************************************************
 * @(#)QueryVMStateFromResPoolAction.java 2014年6月4日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.QueryVMState;
import com.neusoft.mid.cloong.host.vm.core.QueryVMStateReq;
import com.neusoft.mid.cloong.host.vm.core.QueryVMStateResp;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.service.VMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.ResultStatusInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMResultInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通过接口实时查询虚拟机状态
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年6月4日 下午2:50:19
 */
public class VMSearchStateAction extends BaseAction {

    private static final long serialVersionUID = 5975622940694269713L;

    private static LogService logger = LogService.getLogger(VMSearchStateAction.class);

    /**
     * 返回列表
     */
    private List<ResultStatusInfo> resultStatusInfos;

    /**
     * 获取查询列表
     */
    private String queryStr;

    /**
     * 查询虚拟机状态
     */
    private QueryVMState queryVMState;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 页面传参 - 资源视图总览页面需传个param参数求全量而不是只取变化的量
     */
    private String param;

    /**
     * 虚拟机操作状态服务
     */
    private VMStatusService vmStatusService;

    @SuppressWarnings("unchecked")
    @Override
    public String execute() {
        // 要查询的vmId列表
        List<String> queryVmIds = new ArrayList<String>();
        // 前台状态map为比较查出的虚拟机状态使用
        Map<String, ResultStatusInfo> vmIdStatuss = new HashMap<String, ResultStatusInfo>();
        // 将传入的json对象queryStr转成vmId查询条件及比较使用的map集合
        queryCondition(queryVmIds, vmIdStatuss);
        resultStatusInfos = new ArrayList<ResultStatusInfo>();
        List<VMResultInfo> lsVmResultInfos = new ArrayList<VMResultInfo>();
        VMResultInfo resPools;

        for (int i = 0; i < queryVmIds.size(); i++) {
            try {
                resPools = (VMResultInfo) ibatisDAO.getSingleRecord("queryResPoolByVmId",
                        queryVmIds.get(i));
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                        getText("vm.vmqueryliststate.fail"), e);
                this.addActionError(getText("vm.vmqueryliststate.fail"));
                resultStatusInfos = null;
                return ConstantEnum.SUCCESS.toString();
            }
            QueryVMStateReq queryVMStateReq = new QueryVMStateReq();
            queryVMStateReq.setVmId(resPools.getVmId());
            queryVMStateReq.setResourcePoolId(resPools.getResPoolId());
            queryVMStateReq.setResourcePoolPartId(resPools.getResPoolPartId());
            QueryVMStateResp resp = queryVMState.queryVMState(queryVMStateReq);
            // QueryVMStateResp resp = new QueryVMStateResp();
            // resp.setResultCode(SUCCEESS_CODE);
            // resp.setVmState("4");
            if (resp.getResultCode().equals(SUCCEESS_CODE)) {
                resPools.setVmId(queryVMStateReq.getVmId());
                resPools.setStatus(stateParse(resp.getVmState()));
                lsVmResultInfos.add(resPools);
            } else {
                resPools.setVmId(queryVMStateReq.getVmId());
                resPools.setStatus(stateParse("15"));
                lsVmResultInfos.add(resPools);
            }
        }

        // 查询结果与前台传入状态比较,并设置显示汉字
        vmCompareStatus(lsVmResultInfos, vmIdStatuss);
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("虚拟机列表状态通过接口查询发生改变个数lenght:");
            sb.append(resultStatusInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    private VMStatus stateParse(String state) {
        VMStatus returnState = null;
        if (state.equals("0")) {
            returnState = VMStatus.PREPARE;
        }
        if (state.equals("1")) {
            returnState = VMStatus.CREATING;
        }
        if (state.equals("2")) {
            returnState = VMStatus.RUNNING;
        }
        if (state.equals("3")) {
            returnState = VMStatus.DELETED;
        }
        if (state.equals("4")) {
            returnState = VMStatus.STOP;
        }
        if (state.equals("6")) {
            returnState = VMStatus.PAUSE;
        }
        if (state.equals("9")) {
            returnState = VMStatus.OPERATE_FAIL;
        }
        if (state.equals("14")) {
            returnState = VMStatus.SENDERROR;
        }
        if (state.equals("15")) {
            returnState = VMStatus.STATUSERROR;
        }
        if (state.equals("16")) {
            returnState = VMStatus.PROCESSING;
        }
        return returnState;
    }

    /**
     * 将查询到虚拟机状态的结果与界面传入的状态比较。 将状态不相同的添入返回集合vmResultStatusInfos中.
     * @param lsVmResultInfos 通过接口查询到的一组虚拟机状态list
     * @param vmIdStatuss 前台传入一组虚拟机状态map
     */
    private void vmCompareStatus(List<VMResultInfo> lsVmResultInfos,
            Map<String, ResultStatusInfo> vmIdStatuss) {
        if (null != lsVmResultInfos) {
            for (VMResultInfo vmResultInfo : lsVmResultInfos) {
                // 查询得到状态
                String vmId = vmResultInfo.getVmId();
                String vmDBStatus = vmResultInfo.getStatus().getCode();
                if (vmIdStatuss.get(vmId) == null) {
                    continue;
                }
                // 资源视图/业务视图总览页面需传个param参数求全量而不是只取变化的量
                if ("1".equals(param)) {
                    ResultStatusInfo vmResultStatusInfo = vmIdStatuss.get(vmId);
                    vmResultStatusInfo.setStatusText(vmResultInfo.getStatus().getDesc());
                    vmResultStatusInfo.setStatus(vmResultInfo.getStatus().getCode());
                    resultStatusInfos.add(vmResultStatusInfo);
                } else if ("2".equals(param)) { // 虚拟机详情页面传个param参数
                    if (!vmDBStatus.equals(vmIdStatuss.get(vmId).getStatus())) { // 和页面上的vm状态比对，取变化的量
                        ResultStatusInfo vmResultStatusInfo = vmIdStatuss.get(vmId);
                        vmResultStatusInfo.setStatusText(vmResultInfo.getStatus().getDesc());
                        vmResultStatusInfo.setStatus(vmResultInfo.getStatus().getCode());
                        resultStatusInfos.add(vmResultStatusInfo);
                    } else { // 页面原有状态=接口新请求的状态 ，此时需判断是否中间状态
                        VMInstanceInfo vmInfo = null;
                        try {
                            vmInfo = (VMInstanceInfo) ibatisDAO
                                    .getSingleRecord("getVmDetail", vmId);
                        } catch (Exception e) {
                            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                                    MessageFormat.format(getText("vmstatus.query.fail"), vmId), e);
                        }
                        String result = "falture";
                        switch (vmInfo.getStatus()) {
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
                        /*
                         * DB查是中间状态，ex:关机->开机中->关机->开机的"开机中->关机"不向页面显示信息 中间状态，页面继续显示操作成功
                         * 注释掉的原因：原来虚拟机的最终状态存储在数据库中
                         * ，页面使用数据库和接口双重查询状态的方式，创建虚拟机时时间过长，显示状态有，所以添加此部分。现在只从接口查询状态，不再需要此部分 if
                         * ("intermediate".equals(result)) { ResultStatusInfo vmResultStatusInfo =
                         * vmIdStatuss.get(vmId); vmResultStatusInfo.setStatusText("操作成功");
                         * vmResultStatusInfo.setStatus(vmInfo.getStatus().getCode());
                         * resultStatusInfos.add(vmResultStatusInfo); }
                         */
                    }
                } else { // 不需要和页面上的vm状态比对，取变化的量
                    // if (!vmDBStatus.equals(vmIdStatuss.get(vmId).getStatus())) {
                    ResultStatusInfo vmResultStatusInfo = vmIdStatuss.get(vmId);
                    vmResultStatusInfo.setStatusText(vmResultInfo.getStatus().getDesc());
                    vmResultStatusInfo.setStatus(vmResultInfo.getStatus().getCode());
                    resultStatusInfos.add(vmResultStatusInfo);
                    // }
                }
                // 变化的虚拟机状态加入状态内存
                vmStatusService.addVMStatus(vmId, vmResultInfo.getStatus());
            }
        }
    }

    /**
     * @param queryVmIds 把JSON字符中的id放入list列表中
     * @param vmIdStatuss 把JSON字符转换成的对象放入Map集合中 key是id
     */
    @SuppressWarnings("unchecked")
    private void queryCondition(List<String> queryVmIds, Map<String, ResultStatusInfo> vmIdStatuss) {
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
                    getText("vm.vmqueryliststate.fail"), e);
        }
        if (null != queryInfos) {
            for (ResultStatusInfo resultStatusInfo : queryInfos) {
                if (null != resultStatusInfo) {
                    // 虚拟机id为空不加入查询计划
                    if (!"".equals(resultStatusInfo.getId()) && null != resultStatusInfo.getId()
                            && !("\"null\"").equals(resultStatusInfo.getId())) {
                        queryVmIds.add(resultStatusInfo.getId());
                        vmIdStatuss.put(resultStatusInfo.getId(), resultStatusInfo);
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

    /**
     * 获取queryVMState字段数据
     * @return Returns the queryVMState.
     */
    public QueryVMState getQueryVMState() {
        return queryVMState;
    }

    /**
     * 设置queryVMState字段数据
     * @param queryVMState The queryVMState to set.
     */
    public void setQueryVMState(QueryVMState queryVMState) {
        this.queryVMState = queryVMState;
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

    /**
     * 获取vmStatusService字段数据
     * @return Returns the vmStatusService.
     */
    public VMStatusService getVmStatusService() {
        return vmStatusService;
    }

    /**
     * 设置vmStatusService字段数据
     * @param vmStatusService The vmStatusService to set.
     */
    public void setVmStatusService(VMStatusService vmStatusService) {
        this.vmStatusService = vmStatusService;
    }

}
