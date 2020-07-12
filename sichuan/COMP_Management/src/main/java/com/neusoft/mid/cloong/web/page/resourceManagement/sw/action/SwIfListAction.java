/*******************************************************************************
 * @(#)MiniPmListAction.java 2015-2-11
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.sw.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.SwIfInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-交换机端口列表Action
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-2-11 上午09:45:16
 */
public class SwIfListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(SwIfListAction.class);

    private List<SwIfInfo> swIfInfoList = new ArrayList<SwIfInfo>();

    private SwIfInfo querySwIfInfo = new SwIfInfo();

    /**
     * 交换机端口列表
     */
    public String execute() {
        try {
            this.swIfInfoList = this.getPage("countQuerySwIf", "querySwIfInfos", querySwIfInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询交换机端口列表数据时错误！原因：查询交换机端口时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public List<SwIfInfo> getSwIfInfoList() {
        return swIfInfoList;
    }

    public void setSwIfInfoList(List<SwIfInfo> swIfInfoList) {
        this.swIfInfoList = swIfInfoList;
    }

    public SwIfInfo getQuerySwIfInfo() {
        return querySwIfInfo;
    }

    public void setQuerySwIfInfo(SwIfInfo querySwIfInfo) {
        this.querySwIfInfo = querySwIfInfo;
    }

}
