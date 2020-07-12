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
import com.neusoft.mid.cloong.web.page.resourceManagement.info.SwInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-交换机列表Action
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-2-11 上午09:45:16
 */
public class SwListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(SwListAction.class);

    private List<SwInfo> swInfoList = new ArrayList<SwInfo>();

    private SwInfo querySwInfo = new SwInfo();

    /**
     * 交换机列表
     */
    public String execute() {
        try {
            this.swInfoList = this.getPage("countQuerySw", "querySwInfos", querySwInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询交换机列表数据时错误！原因：查询交换机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public List<SwInfo> getSwInfoList() {
        return swInfoList;
    }

    public void setSwInfoList(List<SwInfo> swInfoList) {
        this.swInfoList = swInfoList;
    }

    public SwInfo getQuerySwInfo() {
        return querySwInfo;
    }

    public void setQuerySwInfo(SwInfo querySwInfo) {
        this.querySwInfo = querySwInfo;
    }

}
