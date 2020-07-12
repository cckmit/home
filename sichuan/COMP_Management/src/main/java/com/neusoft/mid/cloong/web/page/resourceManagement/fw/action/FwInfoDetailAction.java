/*******************************************************************************
 * @(#)FwInfoDetailAction.java 2015年11月6日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.fw.action;

import java.util.Arrays;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.FwInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 防火墙详情
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.0 $ 2015年11月6日 下午3:57:30
 */
public class FwInfoDetailAction extends ResourceManagementBaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(FwInfoDetailAction.class);

    /**
     * rep data
     */
    private FwInfo fwInfo = new FwInfo();
    
    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
        	this.fwInfo = (FwInfo) this.ibatisDAO.getSingleRecord("queryFwInfo", fwInfo);
        	String appIds = fwInfo.getAppIds();
        	if(appIds!=null && !"".equals(appIds)){
        		String[] appIdAry = appIds.split(";");
        		fwInfo.setAppIdList(Arrays.asList(appIdAry));
        		String names = (String) this.ibatisDAO.getSingleRecord("queryAppNames", fwInfo);
        		fwInfo.setAppNames(names);
        	}
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某防火墙数据时错误！原因：查询防火墙时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

	/**
	 * @return the fwInfo
	 */
	public FwInfo getFwInfo() {
		return fwInfo;
	}

	/**
	 * @param fwInfo the fwInfo to set
	 */
	public void setFwInfo(FwInfo fwInfo) {
		this.fwInfo = fwInfo;
	}
    
}
