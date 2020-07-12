/*******************************************************************************
 * @(#)RtifListAction.java 2015年11月6日
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
 * 资源管理-路由器端口列表Action
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年11月6日 下午3:57:30
 */
public class RtifListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(RtifListAction.class);
    
    private List<RtInfo> rtInfoList = new ArrayList<RtInfo>();
    
    private RtInfo rtInfo = new RtInfo();
    
    /**
     * 路由器端口列表
     */
    public String execute() {
    	try {
            this.rtInfoList = this.getPage("countQueryRtif", "queryRtifInfos", rtInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询路由器端口列表数据时错误！原因：查询路由器端口时，数据库异常。", e);
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

}