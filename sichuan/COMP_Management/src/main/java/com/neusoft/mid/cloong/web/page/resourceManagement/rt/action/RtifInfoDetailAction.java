/*******************************************************************************
 * @(#)RtInfoDetailAction.java 2015年11月6日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.rt.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.RtInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 路由器端口详情
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年11月6日 下午3:57:30
 */
public class RtifInfoDetailAction extends ResourceManagementBaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(RtifInfoDetailAction.class);

    /**
     * rep data
     */
    private RtInfo rtInfo = new RtInfo();
    
    private String routerIfId;

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
        	rtInfo.setRouterIfId(routerIfId);
        	this.rtInfo = (RtInfo) this.ibatisDAO.getSingleRecord("queryRtifInfo", rtInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某路由器数据时错误！原因：查询路由器时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

	/**
	 * @return the rtInfo
	 */
	public RtInfo getRtInfo() {
		return rtInfo;
	}

	/**
	 * @param rtInfo the rtInfo to set
	 */
	public void setRtInfo(RtInfo rtInfo) {
		this.rtInfo = rtInfo;
	}

	/**
	 * @return the routerIfId
	 */
	public String getRouterIfId() {
		return routerIfId;
	}

	/**
	 * @param routerIfId the routerIfId to set
	 */
	public void setRouterIfId(String routerIfId) {
		this.routerIfId = routerIfId;
	}
    
}
