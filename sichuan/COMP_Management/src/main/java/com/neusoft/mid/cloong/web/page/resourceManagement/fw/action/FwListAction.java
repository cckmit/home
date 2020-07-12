/*******************************************************************************
 * @(#)FwListAction.java 2015年11月6日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.fw.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.FwInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-防火墙列表Action
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年11月6日 下午3:57:30
 */
public class FwListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(FwListAction.class);
    
    private List<FwInfo> fwInfoList = new ArrayList<FwInfo>();
    
    private FwInfo queryFwInfo = new FwInfo();
    
    /**
     * 防火墙列表
     */
    public String execute() {
    	try {
            this.fwInfoList = this.getPage("countQueryFw", "queryFwInfos", queryFwInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询防火墙列表数据时错误！原因：查询防火墙时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

	/**
	 * @return the fwInfoList
	 */
	public List<FwInfo> getFwInfoList() {
		return fwInfoList;
	}

	/**
	 * @param fwInfoList the fwInfoList to set
	 */
	public void setFwInfoList(List<FwInfo> fwInfoList) {
		this.fwInfoList = fwInfoList;
	}

	/**
	 * @return the queryFwInfo
	 */
	public FwInfo getQueryFwInfo() {
		return queryFwInfo;
	}

	/**
	 * @param queryFwInfo the queryFwInfo to set
	 */
	public void setQueryFwInfo(FwInfo queryFwInfo) {
		this.queryFwInfo = queryFwInfo;
	}

}