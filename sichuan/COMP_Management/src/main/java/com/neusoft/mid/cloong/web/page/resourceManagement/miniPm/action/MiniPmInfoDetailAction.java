/*******************************************************************************
 * @(#)MiniPmInfoDetailAction.java 2015年2月11日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.miniPm.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.SubZero;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.MiniPmInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 小型机详情
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年2月11日 上午9:52:01
 */
public class MiniPmInfoDetailAction extends ResourceManagementBaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(MiniPmInfoDetailAction.class);

    /**
     * req parameter
     */
    private String miniPmId = new String();

    /**
     * rep data
     */
    private MiniPmInfo miniPmInfo = new MiniPmInfo();

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
        	this.miniPmInfo = (MiniPmInfo) this.ibatisDAO.getSingleRecord("queryMiniPmInfo", miniPmInfo);
        	miniPmInfo.setMemorySize(SubZero.subZero(miniPmInfo.getMemorySize()));
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某台小型机数据时错误！原因：查询小型机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

	/**
	 * @return the miniPmId
	 */
	public String getMiniPmId() {
		return miniPmId;
	}

	/**
	 * @param miniPmId the miniPmId to set
	 */
	public void setMiniPmId(String miniPmId) {
		this.miniPmId = miniPmId;
	}

	/**
	 * @return the miniPmInfo
	 */
	public MiniPmInfo getMiniPmInfo() {
		return miniPmInfo;
	}

	/**
	 * @param miniPmInfo the miniPmInfo to set
	 */
	public void setMiniPmInfo(MiniPmInfo miniPmInfo) {
		this.miniPmInfo = miniPmInfo;
	}

}