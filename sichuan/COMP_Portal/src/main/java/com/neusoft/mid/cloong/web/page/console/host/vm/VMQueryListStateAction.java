/*******************************************************************************
 * @(#)VMQueryListAction.java 2013-1-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.ResultStatusInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMResultInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询一组虚拟机列表状态 返回json
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-9 下午03:18:21
 */
public class VMQueryListStateAction extends BaseAction {

    private static final long serialVersionUID = 5975622940694269713L;

    private static LogService logger = LogService.getLogger(VMQueryListStateAction.class);

    /**
     * 返回列表
     */
    private List<ResultStatusInfo> resultStatusInfos;

    /**
     * 获取查询列表
     */
    private String queryStr;

    @SuppressWarnings("unchecked")
    @Override
    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        // 要查询的vmId列表
        List<String> queryVmIds = new ArrayList<String>();
        // 前台状态map为比较查出的虚拟机状态使用
        Map<String, ResultStatusInfo> vmIdStatuss = new HashMap<String, ResultStatusInfo>();
        // 将传入的json对象queryStr转成vmId查询条件及比较使用的map集合
        queryCondition(queryVmIds, vmIdStatuss);
        // 设置查询条件
        Map<String, Object> queryCondition = new HashMap<String, Object>();
        queryCondition.put("ownUser", userId);
        queryCondition.put("vmIds", queryVmIds);
        resultStatusInfos = new ArrayList<ResultStatusInfo>();
        List<VMResultInfo> lsVmResultInfos;
        try {
            lsVmResultInfos = (List<VMResultInfo>) ibatisDAO.getData("queryStatusByVmIdOwner",
                    queryCondition);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("vm.vmqueryliststate.fail"), e);
            this.addActionError(getText("vm.vmqueryliststate.fail"));
            resultStatusInfos = null;
            return ConstantEnum.SUCCESS.toString();
        }
        // 查询结果与前台传入状态比较,并设置显示汉字
        vmCompareStatus(lsVmResultInfos, vmIdStatuss);
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("虚拟机列表状态查询发生改变个数lenght:");
            sb.append(resultStatusInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 将查询到虚拟机状态的结果与界面传入的状态比较。 将状态不相同的添入返回集合vmResultStatusInfos中.
     * @param queryDatas 查询到的一组虚拟机状态list
     * @param pageVmIdStatuss 前台传入一组虚拟机状态map
     */
    private void vmCompareStatus(List<VMResultInfo> lsVmResultInfos,
            Map<String, ResultStatusInfo> vmIdStatuss) {
        if (null != lsVmResultInfos) {
            for (VMResultInfo vmResultInfo : lsVmResultInfos) {
                String vmId = vmResultInfo.getVmId();
                String vmDBStatus = vmResultInfo.getStatus().getCode();
                if(vmIdStatuss.get(vmId)==null){
                    continue;
                }
                if (!vmDBStatus.equals(vmIdStatuss.get(vmId).getStatus())) {
                    ResultStatusInfo vmResultStatusInfo = vmIdStatuss.get(vmId);
                    if (!"".equals(vmResultInfo.getEffectiveTime() == null ? "" : vmResultInfo
                            .getEffectiveTime())) {
                        vmResultStatusInfo.setEffectiveTime(DateParse.parse(vmResultInfo
                                .getEffectiveTime()));
                    }
                    if (!"".equals(vmResultInfo.getOverTime() == null ? "" : vmResultInfo
                            .getOverTime())) {
                        vmResultStatusInfo.setOverTime(DateParse.parse(vmResultInfo.getOverTime()));
                    }
                    vmResultStatusInfo.setStatusText(vmResultInfo.getStatus().getDesc());
                    vmResultStatusInfo.setStatus(vmResultInfo.getStatus().getCode());
                    resultStatusInfos.add(vmResultStatusInfo);
                }
            }
        }
    }

    /**
     * @param queryIds 把JSON字符中的id放入list列表中
     * @param idStatuss 把JSON字符转换成的对象放入Map集合中 key是id
     */
    @SuppressWarnings("unchecked")
    private void queryCondition(List<String> queryVmIds, Map<String, ResultStatusInfo> vmIdStatuss) {
        // 将前台传入的json字符串转换成对象集合
        List<ResultStatusInfo> queryInfos = null;
        try {
            JSONArray json = JSONArray.fromObject(queryStr);
            queryInfos = (List<ResultStatusInfo>) JSONArray.toCollection(json,
                    ResultStatusInfo.class);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("vm.vmqueryliststate.fail"), e);
        }
        if (null != queryInfos) {
            for (ResultStatusInfo resultStatusInfo : queryInfos) {
                if (null != resultStatusInfo) {
                    queryVmIds.add(resultStatusInfo.getId());
                    vmIdStatuss.put(resultStatusInfo.getId(), resultStatusInfo);
                }
            }
        }
    }

    public List<ResultStatusInfo> getResultStatusInfos() {
        return resultStatusInfos;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }
}
