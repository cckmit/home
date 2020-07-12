/*******************************************************************************
 * @(#)VmListAction.java 2015-2-11
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.miniPmPar.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.SubZero;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.MiniPmParInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-小型分区机列表Action
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-2-11 上午10:02:16
 */
public class MiniPmParListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(MiniPmParListAction.class);
    
    private List<MiniPmParInfo> miniPmParInfoList = new ArrayList<MiniPmParInfo>();
    
    private MiniPmParInfo queryMiniPmParInfo = new MiniPmParInfo();
    
    /**
     * 小型分区机列表
     */
    public String execute() {
    	if ("zyst".equals(curFun)) { // 资源视图
    	    
    	} else { // 业务视图
    		// 根据appId过滤小型分区机，appId这里直接用，已在父类中将值传过来了
    	    queryMiniPmParInfo.setAppId(appId);
    	}
    	try {
            this.miniPmParInfoList = this.getPage("countQueryMiniPmPar", "queryMiniPmParInfos", queryMiniPmParInfo);
            for(MiniPmParInfo miniPmParTmp:miniPmParInfoList){
            	miniPmParTmp.setMemorySize(SubZero.subZero(miniPmParTmp.getMemorySize()));
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询小型分区机列表数据时错误！原因：查询小型分区机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

	/**
	 * @return the miniPmParInfoList
	 */
	public List<MiniPmParInfo> getMiniPmParInfoList() {
		return miniPmParInfoList;
	}

	/**
	 * @param miniPmParInfoList the miniPmParInfoList to set
	 */
	public void setMiniPmParInfoList(List<MiniPmParInfo> miniPmParInfoList) {
		this.miniPmParInfoList = miniPmParInfoList;
	}

	/**
	 * @return the queryMiniPmParInfo
	 */
	public MiniPmParInfo getQueryMiniPmParInfo() {
		return queryMiniPmParInfo;
	}

	/**
	 * @param queryMiniPmParInfo the queryMiniPmParInfo to set
	 */
	public void setQueryMiniPmParInfo(MiniPmParInfo queryMiniPmParInfo) {
		this.queryMiniPmParInfo = queryMiniPmParInfo;
	}
}