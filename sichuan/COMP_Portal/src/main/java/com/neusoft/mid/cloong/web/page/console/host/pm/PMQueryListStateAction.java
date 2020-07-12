/*******************************************************************************
 * @(#)PMQueryListStateAction.java 2013-1-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.pm;

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
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMResultInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.ResultStatusInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询一组物理机列表状态 返回json
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-16 上午10:17:36
 */
public class PMQueryListStateAction extends BaseAction {

    private static final long serialVersionUID = 5975622940694269713L;

    private static LogService logger = LogService.getLogger(PMQueryListStateAction.class);

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
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        // 要查询的pmId列表
        List<String> queryPmIds = new ArrayList<String>();
        // 前台状态map为比较查出的物理机状态使用
        Map<String, ResultStatusInfo> pmIdStatuss = new HashMap<String, ResultStatusInfo>();
        // 将传入的json对象queryStr转成pmId查询条件及比较使用的map集合
        queryCondition(queryPmIds, pmIdStatuss);
        // 设置查询条件
        Map<String, Object> queryCondition = new HashMap<String, Object>();
        queryCondition.put("ownUser", userId);
        queryCondition.put("pmIds", queryPmIds);
        resultStatusInfos = new ArrayList<ResultStatusInfo>();
        List<PMResultInfo> lsPmResultInfos;
        try {
            lsPmResultInfos = (List<PMResultInfo>) ibatisDAO.getData("queryStatusByPmIdOwner",
                    queryCondition);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("pm.pmqueryliststate.fail"), e);
            this.addActionError(getText("pm.pmqueryliststate.fail"));
            resultStatusInfos = null;
            return ConstantEnum.SUCCESS.toString();
        }
        // 查询结果与前台传入状态比较,并设置显示汉字
        pmCompareStatus(lsPmResultInfos, pmIdStatuss);
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("物理机列表状态查询发生改变个数lenght:");
            sb.append(resultStatusInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 将查询到物理机状态的结果与界面传入的状态比较。 将状态不相同的添入返回集合pmResultStatusInfos中.
     * @param queryDatas 查询到的一组物理机状态list
     * @param pagePmIdStatuss 前台传入一组物理机状态map
     */
    private void pmCompareStatus(List<PMResultInfo> lsPmResultInfos,
            Map<String, ResultStatusInfo> pmIdStatuss) {
        if (null != lsPmResultInfos) {
            for (PMResultInfo pmResultInfo : lsPmResultInfos) {
                String pmId = pmResultInfo.getPmId();
                String pmDBStatus = pmResultInfo.getStatus().getCode();
                if (pmIdStatuss.get(pmId) == null) {
                    continue;
                }
                if (!pmDBStatus.equals(pmIdStatuss.get(pmId).getStatus())) {
                    ResultStatusInfo pmResultStatusInfo = pmIdStatuss.get(pmId);
                    if (!"".equals(pmResultInfo.getEffectiveTime() == null ? "" : pmResultInfo
                            .getEffectiveTime())) {
                        pmResultStatusInfo.setEffectiveTime(DateParse.parse(pmResultInfo
                                .getEffectiveTime()));
                    }
                    if (!"".equals(pmResultInfo.getOverTime() == null ? "" : pmResultInfo
                            .getOverTime())) {
                        pmResultStatusInfo.setOverTime(DateParse.parse(pmResultInfo.getOverTime()));
                    }
                    pmResultStatusInfo.setStatusText(pmResultInfo.getStatus().getDesc());
                    pmResultStatusInfo.setStatus(pmResultInfo.getStatus().getCode());
                    resultStatusInfos.add(pmResultStatusInfo);
                }
            }
        }
    }

    /**
     * @param queryIds 把JSON字符中的id放入list列表中
     * @param idStatuss 把JSON字符转换成的对象放入Map集合中 key是id
     */
    @SuppressWarnings("unchecked")
    private void queryCondition(List<String> queryPmIds, Map<String, ResultStatusInfo> pmIdStatuss) {
        // 将前台传入的json字符串转换成对象集合
        List<ResultStatusInfo> queryInfos = null;
        try {
            JSONArray json = JSONArray.fromObject(queryStr);
            queryInfos = (List<ResultStatusInfo>) JSONArray.toCollection(json,
                    ResultStatusInfo.class);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("pm.pmqueryliststate.fail"), e);
        }
        if (null != queryInfos) {
            for (ResultStatusInfo resultStatusInfo : queryInfos) {
                if (null != resultStatusInfo) {
                    queryPmIds.add(resultStatusInfo.getId());
                    pmIdStatuss.put(resultStatusInfo.getId(), resultStatusInfo);
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
