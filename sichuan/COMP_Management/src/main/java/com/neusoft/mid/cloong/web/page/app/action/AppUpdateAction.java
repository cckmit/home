
package com.neusoft.mid.cloong.web.page.app.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.app.info.App;
import com.neusoft.mid.cloong.web.page.app.service.AppUpdateService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改业务
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version 创建时间：2014-12-26 下午3:50:41
 */
public class AppUpdateAction  extends BaseAction {

    private static LogService logger = LogService.getLogger(AppUpdateAction.class);
    
    private static final long serialVersionUID = 7278321721961855694L;
    
    private AppUpdateService appUpdateService;
    
    private App app = new App();

    private CreateResult result = new CreateResult();
    
    private String userIds;
    

    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    
    public String execute() {
    	
        String userId = this.getCurrentUserId();
        try {
           
        	app.setApp_contacts(userIds);
            result = appUpdateService.updateApp(userId, app);
            if(result == null){
            	result = new CreateResult("success", "业务修改成功！");
            }
            
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"业务修改失败!", e);
            this.addActionError("业务修改失败!");
            result = new CreateResult("error", "业务修改失败!");
            return ConstantEnum.ERROR.toString();
        }
        
        return ConstantEnum.SUCCESS.toString();
    }


	/**
	 * @return the appUpdateService
	 */
	public AppUpdateService getAppUpdateService() {
		return appUpdateService;
	}


	/**
	 * @param appUpdateService the appUpdateService to set
	 */
	public void setAppUpdateService(AppUpdateService appUpdateService) {
		this.appUpdateService = appUpdateService;
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


	/**
	 * @return the result
	 */
	public CreateResult getResult() {
		return result;
	}


	/**
	 * @param result the result to set
	 */
	public void setResult(CreateResult result) {
		this.result = result;
	}


	/**
	 * @return the userIds
	 */
	public String getUserIds() {
		return userIds;
	}


	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
    
  
	
}
