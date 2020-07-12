/*******************************************************************************
 * @(#)EBSQueryListStateAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.disk;

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
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.ResultStatusInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询一组虚拟机列表状态 返回json
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午04:48:15
 */
public class EBSQueryListStateAction extends BaseAction {

    private static final long serialVersionUID = 1258623970052721530L;

    private static LogService logger = LogService.getLogger(EBSQueryListStateAction.class);

    /**
     * 返回列表
     */
    private List<ResultStatusInfo> resultStatusInfos;

    /**
     * 获取查询列表
     */
    private String queryStr;

    @SuppressWarnings("unchecked")
    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        // 要查询的vmId列表
        List<String> queryIds = new ArrayList<String>();
        // 前台状态map为比较查出的虚拟机状态使用
        Map<String, ResultStatusInfo> idStatuss = new HashMap<String, ResultStatusInfo>();
        // 将传入的json对象queryStr转成vmId查询条件及比较使用的map集合
        queryCondition(queryIds, idStatuss);
        // 设置查询条件
        Map<String, Object> queryCondition = new HashMap<String, Object>();
        queryCondition.put("diskIds", queryIds);
        resultStatusInfos = new ArrayList<ResultStatusInfo>();
        List<DiskInfo> lsDiskInfos;
        try {
            lsDiskInfos = (List<DiskInfo>) ibatisDAO.getData("queryStatusByEBSIdOwner",
                    queryCondition);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("vm.vmqueryliststate.fail"), e);
            this.addActionError(getText("vm.vmqueryliststate.fail"));
            resultStatusInfos = null;
            return ConstantEnum.SUCCESS.toString();
        }
        // 查询结果与前台传入状态比较,并设置显示汉字
        vmCompareStatus(lsDiskInfos, idStatuss);
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
    private void vmCompareStatus(List<DiskInfo> lsDiskInfos,
            Map<String, ResultStatusInfo> vmIdStatuss) {
        if (null != lsDiskInfos) {
            for (DiskInfo diskInfo : lsDiskInfos) {
                String ebsId = diskInfo.getDiskId();
                String vmDBStatus = diskInfo.getDiskStatus();
                if (!vmDBStatus.equals(vmIdStatuss.get(ebsId).getStatus())) {
                    ResultStatusInfo vmResultStatusInfo = vmIdStatuss.get(ebsId);
                    if (!"".equals(diskInfo.getCreateTime() == null ? "" : diskInfo.getCreateTime())) {
                        vmResultStatusInfo.setEffectiveTime(DateParse.parse(diskInfo
                                .getCreateTime()));
                    }
                    if (!"".equals(diskInfo.getExpireTime() == null ? "" : diskInfo.getExpireTime())) {
                        vmResultStatusInfo.setOverTime(DateParse.parse(diskInfo.getExpireTime()));
                    }
                    vmResultStatusInfo.setStatus(diskInfo.getDiskStatus());
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
    private void queryCondition(List<String> queryIds, Map<String, ResultStatusInfo> idStatuss) {
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
        if (null != queryInfos) {// 转换要查询的id
            for (ResultStatusInfo resultStatusInfo : queryInfos) {
                if (null != resultStatusInfo) {
                    queryIds.add(resultStatusInfo.getId());
                    idStatuss.put(resultStatusInfo.getId(), resultStatusInfo);
                }
            }
        }
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    /**
     * 获取resultStatusInfos字段数据
     * @return Returns the resultStatusInfos.
     */
    public List<ResultStatusInfo> getResultStatusInfos() {
        return resultStatusInfos;
    }
}
