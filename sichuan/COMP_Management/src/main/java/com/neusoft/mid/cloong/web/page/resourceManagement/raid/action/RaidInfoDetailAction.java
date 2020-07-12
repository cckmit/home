/*******************************************************************************
 * @(#)RaidInfoDetailAction.java 2015年11月6日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.raid.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.RaidInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 存储阵列详情
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年2月11日 上午9:52:01
 */
public class RaidInfoDetailAction extends ResourceManagementBaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(RaidInfoDetailAction.class);

    /**
     * rep data
     */
    private RaidInfo raidInfo = new RaidInfo();

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
        	this.raidInfo = (RaidInfo) this.ibatisDAO.getSingleRecord("queryRaidInfo", raidInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某台存储阵列数据时错误！原因：查询存储阵列时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public RaidInfo getRaidInfo() {
        return raidInfo;
    }

    public void setRaidInfo(RaidInfo raidInfo) {
        this.raidInfo = raidInfo;
    }


}