/*******************************************************************************
 * @(#)ElbInfoDetailAction.java 2015年1月8日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.ebs.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.EbsInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * ELB详情
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2015年1月8日 下午6:04:53
 */
public class EbsInfoDetailAction extends ResourceManagementBaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(EbsInfoDetailAction.class);

    /**
     * req parameter
     */
    private String ebsId = new String();

    /**
     * rep data
     */
    private EbsInfo ebsInfo = new EbsInfo();

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
        	this.ebsInfo = (EbsInfo) this.ibatisDAO.getSingleRecord("queryEbsInfo", ebsInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某台虚拟机数据时错误！原因：查询虚拟机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取ebsId字段数据
     * @return Returns the ebsId.
     */
    public String getEbsId() {
        return ebsId;
    }

    /**
     * 设置ebsId字段数据
     * @param ebsId The ebsId to set.
     */
    public void setEbsId(String elbId) {
        this.ebsId = elbId;
    }

    /**
     * 获取ebsInfo字段数据
     * @return Returns the ebsInfo.
     */
    public EbsInfo getEbsInfo() {
        return ebsInfo;
    }

    /**
     * 设置ebsInfo字段数据
     * @param elbInfo The ebsInfo to set.
     */
    public void setEbsInfo(EbsInfo ebsInfo) {
        this.ebsInfo = ebsInfo;
    }

}
