/*******************************************************************************
 * @(#)PmInfoDetailAction.java 2015年1月8日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.pm.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.SubZero;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.PmInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机详情
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2015年1月8日 下午6:04:01
 */
public class PmInfoDetailAction extends ResourceManagementBaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(PmInfoDetailAction.class);

    /**
     * req parameter
     */
    private String pmId = new String();

    /**
     * rep data
     */
    private PmInfo pmInfo = new PmInfo();

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
        	this.pmInfo = (PmInfo) this.ibatisDAO.getSingleRecord("queryPmInfo", pmInfo);
        	pmInfo.setMemorySize(SubZero.subZero(pmInfo.getMemorySize()));
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某台物理机数据时错误！原因：查询物理机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取pmId字段数据
     * @return Returns the pmId.
     */
    public String getPmId() {
        return pmId;
    }

    /**
     * 设置pmId字段数据
     * @param pmId The pmId to set.
     */
    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    /**
     * 获取pmInfo字段数据
     * @return Returns the pmInfo.
     */
    public PmInfo getPmInfo() {
        return pmInfo;
    }

    /**
     * 设置pmInfo字段数据
     * @param pmInfo The pmInfo to set.
     */
    public void setPmInfo(PmInfo pmInfo) {
        this.pmInfo = pmInfo;
    }

}