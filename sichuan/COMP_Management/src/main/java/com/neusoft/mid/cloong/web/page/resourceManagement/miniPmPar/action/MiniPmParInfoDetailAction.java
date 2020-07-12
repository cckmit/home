/*******************************************************************************
 * @(#)MiniPmParInfoDetailAction.java 2015年2月11日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.miniPmPar.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.SubZero;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.MiniPmParInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 小型分区机详情
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年2月11日 上午10:05:07
 */
public class MiniPmParInfoDetailAction extends ResourceManagementBaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(MiniPmParInfoDetailAction.class);

    /**
     * req parameter
     */
    private String miniPmParId = new String();

    /**
     * rep data
     */
    private MiniPmParInfo miniPmParInfo = new MiniPmParInfo();
    
    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
        	this.miniPmParInfo = (MiniPmParInfo) this.ibatisDAO.getSingleRecord("queryMiniPmParInfo", miniPmParInfo);
        	miniPmParInfo.setMemorySize(SubZero.subZero(miniPmParInfo.getMemorySize()));
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某台小型分区机数据时错误！原因：查询小型分区机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

	/**
	 * @return the miniPmParId
	 */
	public String getMiniPmParId() {
		return miniPmParId;
	}


	/**
	 * @param miniPmParId the miniPmParId to set
	 */
	public void setMiniPmParId(String miniPmParId) {
		this.miniPmParId = miniPmParId;
	}


	/**
	 * @return the miniPmParInfo
	 */
	public MiniPmParInfo getMiniPmParInfo() {
		return miniPmParInfo;
	}


	/**
	 * @param miniPmParInfo the miniPmParInfo to set
	 */
	public void setMiniPmParInfo(MiniPmParInfo miniPmParInfo) {
		this.miniPmParInfo = miniPmParInfo;
	}
    
}
