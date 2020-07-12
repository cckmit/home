
package com.neusoft.mid.cloong.web.page.app.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.app.service.AppDeleteService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务管理删除业务功能
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月11日 下午1:58:37
 */
public class AppDeleteAction extends BaseAction {

    private static LogService logger = LogService.getLogger(AppDeleteAction.class);

    private static final long serialVersionUID = 7278321721961855694L;

    private AppDeleteService appDeleteService;

    private String app_id = new String();

    private CreateResult result;

    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    
    public String execute() {
        try {
            result = appDeleteService.deleteApp(app_id);
            
            if(result == null){
            	result = new CreateResult("success", "业务删除成功！");
            }
            
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"业务删除失败!", e);
            this.addActionError("业务删除失败!");
            result = new CreateResult("fail", "业务删除失败!");
            return ConstantEnum.ERROR.toString();
        }
        
        return ConstantEnum.SUCCESS.toString();
    }

	/**
	 * @return the appDeleteService
	 */
	public AppDeleteService getAppDeleteService() {
		return appDeleteService;
	}

	/**
	 * @param appDeleteService the appDeleteService to set
	 */
	public void setAppDeleteService(AppDeleteService appDeleteService) {
		this.appDeleteService = appDeleteService;
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

    
}
