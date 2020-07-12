/*******************************************************************************
 * @(#)QueryResourceListAction.java 2013-3-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.config.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourceInfo;
import com.neusoft.mid.cloong.web.page.system.config.service.QueryResourceListService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询资源池列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-6 上午10:16:18
 */
public class QueryResourceListAction extends BaseAction {

    private static final long serialVersionUID = -1862614417618551035L;

    private static LogService logger = LogService.getLogger(QueryResourceListAction.class);

    private QueryResourceListService queryResourceListService;

    public QueryResourceListService getQueryResourceListService() {
        return queryResourceListService;
    }

    public void setQueryResourceListService(QueryResourceListService queryResourceListService) {
        this.queryResourceListService = queryResourceListService;
    }
    
    private String selectSqlKey;

    /**
     * 界面返回集合 资源列表
     */
    private List<ResourceInfo> resourceInfos;

    private String mes;

    public String execute() {
        try {
            resourceInfos = queryResourceListService.queryResourceInfos(selectSqlKey);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("resource.db.error"), e);
            this.addActionError(getText("resource.db.error"));
            mes= getText("resource.db.error");
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public List<ResourceInfo> getResourceInfos() {
        return resourceInfos;
    }

    public String getMes() {
        return mes;
    }

    public void setSelectSqlKey(String selectSqlKey) {
        this.selectSqlKey = selectSqlKey;
    }
}
