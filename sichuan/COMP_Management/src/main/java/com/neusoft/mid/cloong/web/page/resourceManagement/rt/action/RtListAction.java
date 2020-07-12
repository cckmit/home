/*******************************************************************************
 * @(#)RtListAction.java 2015年11月6日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.rt.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.RtInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-路由器列表Action
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年11月6日 下午3:57:30
 */
public class RtListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(RtListAction.class);
    
    private List<RtInfo> rtInfoList = new ArrayList<RtInfo>();
    
    private RtInfo queryRtInfo = new RtInfo();
    
    /**
     * 路由器列表
     */
    public String execute() {
    	try {
            this.rtInfoList = this.getPage("countQueryRt", "queryRtInfos", queryRtInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询路由器列表数据时错误！原因：查询路由器时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

	/**
	 * @return the rtInfoList
	 */
	public List<RtInfo> getRtInfoList() {
		return rtInfoList;
	}

	/**
	 * @param rtInfoList the rtInfoList to set
	 */
	public void setRtInfoList(List<RtInfo> rtInfoList) {
		this.rtInfoList = rtInfoList;
	}

	/**
	 * @return the queryRtInfo
	 */
	public RtInfo getQueryRtInfo() {
		return queryRtInfo;
	}

	/**
	 * @param queryRtInfo the queryRtInfo to set
	 */
	public void setQueryRtInfo(RtInfo queryRtInfo) {
		this.queryRtInfo = queryRtInfo;
	}


}