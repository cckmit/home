/*******************************************************************************
 * @(#)EbsListAction.java 2014-12-30
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.ebs.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.EbsInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-虚拟硬盘列表Action
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2014-12-30 下午03:42:16
 */
public class EbsListAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(EbsListAction.class);

    private List<EbsInfo> ebsInfoList = new ArrayList<EbsInfo>();
    
    private EbsInfo queryEbsInfo = new EbsInfo();
    
    /**
     * EBS列表
     */
    public String execute() {
    	if ("zyst".equals(curFun)) { // 资源视图
    		
    	} else { // 业务视图
    		// 根据appId过滤虚拟机，appId这里直接用，已在父类中将值传过来了
    	    queryEbsInfo.setAppId(appId);
    	}
    	try {
            this.ebsInfoList = this.getPage("countQueryEbs", "queryEbsInfos", queryEbsInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询EBS列表数据时错误！原因：查询EBS时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取ebsInfoList字段数据
     * @return Returns the ebsInfoList.
     */
    public List<EbsInfo> getEbsInfoList() {
        return ebsInfoList;
    }

    /**
     * 设置ebsInfoList字段数据
     * @param ebsInfoList The ebsInfoList to set.
     */
    public void setEbsInfoList(List<EbsInfo> ebsInfoList) {
        this.ebsInfoList = ebsInfoList;
    }

    /**
     * 获取queryEbsInfo字段数据
     * @return Returns the queryEbsInfo.
     */
    public EbsInfo getQueryEbsInfo() {
        return queryEbsInfo;
    }

    /**
     * 设置queryEbsInfo字段数据
     * @param queryEbsInfo The queryEbsInfo to set.
     */
    public void setQueryEbsInfo(EbsInfo queryEbsInfo) {
        this.queryEbsInfo = queryEbsInfo;
    }

}