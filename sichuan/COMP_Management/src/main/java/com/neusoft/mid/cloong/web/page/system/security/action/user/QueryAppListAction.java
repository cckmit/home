
package com.neusoft.mid.cloong.web.page.system.security.action.user;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.app.info.App;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询用户的业务列表
 * @author <a href="mailto:zhangyunfeng@neusoft.com">zhangyunfeng</a>
 * @version version 1.0：2015-3-3 上午10:10:37
 */
public class QueryAppListAction extends PageAction{

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7998411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(QueryAppListAction.class);


    private App app = new App();

    private List<App> appList = new ArrayList<App>();


    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
            
            this.appList = this.getPage("countApp", "queryUserAppList", app,PageTransModel.ASYNC);
            
        } catch (Exception e) {
            logger.error("获取业务列表失败", e);
            return ConstantEnum.FAILURE.toString();
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
	 * @return the appList
	 */
	public List<App> getAppList() {
		return appList;
	}


	/**
	 * @param app the app to set
	 */
	public void setApp(App app) {
		this.app = app;
	}


	/**
	 * @param appList the appList to set
	 */
	public void setAppList(List<App> appList) {
		this.appList = appList;
	}
    

}
