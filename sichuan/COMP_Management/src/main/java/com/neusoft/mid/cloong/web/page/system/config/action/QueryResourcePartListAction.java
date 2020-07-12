/*******************************************************************************
 * @(#)QueryResourcePartListAction.java 2013-3-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.config.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourcePartInfo;
import com.neusoft.mid.cloong.web.page.system.config.service.QueryResourcePartListService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池分区查询列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-7 下午03:34:39
 */
public class QueryResourcePartListAction extends BaseAction {

    private static final long serialVersionUID = -7522377884198676944L;

    private static LogService logger = LogService.getLogger(QueryResourcePartListAction.class);

    private QueryResourcePartListService queryResourcePartListService;

    public QueryResourcePartListService getQueryResourcePartListService() {
        return queryResourcePartListService;
    }

    public void setQueryResourcePartListService(
            QueryResourcePartListService queryResourcePartListService) {
        this.queryResourcePartListService = queryResourcePartListService;
    }

    /**
     * 界面返回集合 资源列表
     */
    private List<ResourcePartInfo> resourcePartInfos;

    private String mes;

    public String execute() {
        try {
            //关联查询   查询资源池分区表及资源池表.资源池分区中的资源池名称是关联查询出来的
            resourcePartInfos = queryResourcePartListService.queryResourcePartInfos();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("resource.db.error"), e);
            this.addActionError(getText("resource.db.error"));
            mes = getText("resource.db.error");
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public List<ResourcePartInfo> getResourcePartInfos() {
        return resourcePartInfos;
    }

    public String getMes() {
        return mes;
    }
}
