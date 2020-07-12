/*******************************************************************************
 * @(#)RaidListAction.java 2015-2-11
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.raid.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.RaidInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-存储阵列列表Action
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-2-11 上午09:45:16
 */
public class RaidListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(RaidListAction.class);

    private List<RaidInfo> raidInfoList = new ArrayList<RaidInfo>();

    private RaidInfo queryRaidInfo = new RaidInfo();

    /**
     * 存储阵列列表
     */
    public String execute() {
        try {
            this.raidInfoList = this.getPage("countQueryRaid", "queryRaidInfos", queryRaidInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询存储阵列列表数据时错误！原因：查询存储阵列时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public List<RaidInfo> getRaidInfoList() {
        return raidInfoList;
    }

    public void setRaidInfoList(List<RaidInfo> raidInfoList) {
        this.raidInfoList = raidInfoList;
    }

    public RaidInfo getQueryRaidInfo() {
        return queryRaidInfo;
    }

    public void setQueryRaidInfo(RaidInfo queryRaidInfo) {
        this.queryRaidInfo = queryRaidInfo;
    }

}
