
package com.neusoft.mid.cloong.web.page.app.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.app.info.App;
import com.neusoft.mid.cloong.web.page.app.service.AppDetailService;
import com.neusoft.mid.iamp.logger.LogService;


/**
 * 查看业务信息详情
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version 创建时间：2014-12-25 下午2:04:02
 */
public class AppDetailAction extends BaseAction {

	private static final long serialVersionUID = 7278321721961855694L;

	private static LogService logger = LogService.getLogger(AppDetailAction.class);

    private AppDetailService appDetailService;

    private String app_id = new String();

    private App app = new App();


    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
        	
            this.app = this.appDetailService.getAppDetail(app_id);

        } catch (Exception e) {
        	logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("app.query.fail"), e);
             return ConstantEnum.ERROR.toString();
         }
         
         return ConstantEnum.SUCCESS.toString();
    }


	/**
	 * @return the appDetailService
	 */
	public AppDetailService getAppDetailService() {
		return appDetailService;
	}

	/**
	 * @param appDetailService the appDetailService to set
	 */
	public void setAppDetailService(AppDetailService appDetailService) {
		this.appDetailService = appDetailService;
	}

	/**
	 * @return the app_id
	 */
	public String getApp_id() {
		return app_id;
	}

	/**
	 * @param app_id the app_id to set
	 */
	public void setApp_id(String app_id) {
		this.app_id = app_id;
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
