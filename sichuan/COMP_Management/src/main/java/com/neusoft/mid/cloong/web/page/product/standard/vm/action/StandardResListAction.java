/*******************************************************************************
 * @(#)StandardResListAction.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vm.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.StandardResListInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机规格同步资源池信息
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-18 下午03:42:51
 */
public class StandardResListAction extends BaseAction {

    private static final long serialVersionUID = -7447229274558674923L;

    private static LogService logger = LogService.getLogger(StandardResListAction.class);

    /**
     * spring 配置 queryVmStandResList 虚拟机
     */
    private String querySqlId;
    
    public void setQuerySqlId(String querySqlId) {
        this.querySqlId = querySqlId;
    }

    /**
     * 规格Id
     */
    private String standardId;

    /**
     * 返回信息
     */
    private Map<String, List<StandardResListInfo>> resultResInfos;

    @SuppressWarnings("unchecked")
    public String execute() {
        List<StandardResListInfo> standardResListInfos;
        try {
            standardResListInfos = ibatisDAO.getData(querySqlId, standardId);
            inResultData(standardResListInfos);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机资源规格同步资源池信息出错，数据库异常",
                    e);
            return ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机资源规格同步资源池信息出错，数据库异常",
                    e);
            return ConstantEnum.ERROR.toString();
        }
        if (standardResListInfos.size() == 0) {
            logger.info("资源规格同步资源池信息为空");
            this.addActionMessage(getText("standard.vm.query.null"));
        }
        logger.info("查询资源规同步资源池格信息成功，共有:" + standardResListInfos.size() + "条虚拟机资源规格信息 ");
        return ConstantEnum.SUCCESS.toString();
    }

    public Map<String, List<StandardResListInfo>> getResultResInfos() {
        return resultResInfos;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    // 将查询结果集变成map形式。 分成：资源 资源池分区
    private void inResultData(List<StandardResListInfo> standardResListInfos) {
        if (standardResListInfos != null && standardResListInfos.size() > 0) {
            resultResInfos = new HashMap<String, List<StandardResListInfo>>();
            for (StandardResListInfo entity : standardResListInfos) {
                String key = entity.getResPoolId();
                if (resultResInfos.get(key) != null) {
                    List<StandardResListInfo> ls = resultResInfos.get(key);
                    ls.add(entity);
                } else {
                    List<StandardResListInfo> ls = new ArrayList<StandardResListInfo>();
                    ls.add(entity);
                    resultResInfos.put(entity.getResPoolId(), ls);
                }
            }
        }
    }
}
