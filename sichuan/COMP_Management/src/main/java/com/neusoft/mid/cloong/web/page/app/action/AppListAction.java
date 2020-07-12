/*******************************************************************************
 * @(#)VMQueryListAction.java 2013-2-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.app.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.app.info.App;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version 创建时间：2014-12-24 下午4:18:30
 */
public class AppListAction extends PageAction {

    private static final long serialVersionUID = -4965007863408723033L;

    private static LogService logger = LogService.getLogger(AppListAction.class);
    
    private List<App> appList;

    private App app = new App();

    /**
     * 查询业务信息
     * @return
     */
    public String execute() {
     
    	try {
    		if (app.getStartTime() != null && app.getStartTime().length() == 19) {
                String startTimeTmp = app.getStartTime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
                app.setStartTime(startTimeTmp);
            }
    		if (app.getEndTime() != null && app.getEndTime().length() == 19) {
                String endTimeTmp = app.getEndTime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
                app.setEndTime(endTimeTmp);
            }
        	
        	appList = this.getPage("queryAppCount", "queryAppList", app);
        	
        	if (app.getStartTime() != null && app.getStartTime().length() == 14) {
        		app.setStartTime(app.getStartTime().substring(0, 4) + "-"
                        + app.getStartTime().substring(4, 6) + "-"
                        + app.getStartTime().substring(6,8) +" "
                        + app.getStartTime().substring(8,10) +":"
                        + app.getStartTime().substring(10,12) +":"
                        + app.getStartTime().substring(12,14) );
            }
        	if (app.getEndTime() != null && app.getEndTime().length() == 14) {
        		app.setEndTime(app.getEndTime().substring(0, 4) + "-"
                        + app.getEndTime().substring(4, 6) + "-"
                        + app.getEndTime().substring(6,8) +" "
                        + app.getEndTime().substring(8,10) +":"
                        + app.getEndTime().substring(10,12) +":"
                        + app.getEndTime().substring(12,14) );
            }
        	
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("app.query.fail"), e);
            this.addActionError(getText("app.query.fail"));
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

	/**
	 * @return the appList
	 */
	public List<App> getAppList() {
		return appList;
	}
	
	/**
	 * @param appList the appList to set
	 */
	public void setAppList(List<App> appList) {
		this.appList = appList;
	}


	/**
	 * @return the app
	 */
	public App getApp() {
		return app;
	}


	/**
	 * @param app the app to set
	 */
	public void setApp(App app) {
		this.app = app;
	}

}
