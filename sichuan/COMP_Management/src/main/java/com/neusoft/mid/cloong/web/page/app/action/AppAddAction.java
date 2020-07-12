
package com.neusoft.mid.cloong.web.page.app.action;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.app.info.App;
import com.neusoft.mid.cloong.web.page.app.service.AppAddService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务创建
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version 创建时间：2014-12-29 上午11:28:01
 */
public class AppAddAction extends BaseAction { 
    private static LogService logger = LogService.getLogger(AppAddAction.class);

    private static final long serialVersionUID = 7278321721961855694L;

    private App app = new App();

    private AppAddService appAddService;
    
    private String userIds;

    /**
     * 创建结果
     */
    private CreateResult result = new CreateResult();

    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    
    public String execute() {
        String userId = this.getCurrentUserId();
        try {
        	app.setApp_contacts(userIds);
            result = appAddService.addApp(userId, app);
            if(result == null){
            	result = new CreateResult("success", "业务创建成功！");
            }
            
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("app.query.list.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("app.query.list.fail"), userId));
            result = new CreateResult("fail", e.getMessage());
            return ERROR;
        }
        
        return ConstantEnum.SUCCESS.toString();
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
	 * @return the appAddService
	 */
	public AppAddService getAppAddService() {
		return appAddService;
	}

	/**
	 * @param appAddService the appAddService to set
	 */
	public void setAppAddService(AppAddService appAddService) {
		this.appAddService = appAddService;
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
