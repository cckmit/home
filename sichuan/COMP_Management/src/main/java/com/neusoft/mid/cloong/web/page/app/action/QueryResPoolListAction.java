package com.neusoft.mid.cloong.web.page.app.action;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourceInfo;
import com.neusoft.mid.iamp.logger.LogService;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询资源池列表
 */
public class QueryResPoolListAction extends PageAction {

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(QueryResPoolListAction.class);

    private List<ResourceInfo> resPoolList = new ArrayList<ResourceInfo>();

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @SuppressWarnings("unchecked")
    public String execute() {
        try {
        	resPoolList =ibatisDAO.getData("queryNormalResourceInfos", null);
        } catch (Exception e) {
        	logger.error("获取用户列表失败", e);
        	return ConstantEnum.FAILURE.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public List<ResourceInfo> getResPoolList() {
        return resPoolList;
    }

    public void setResPoolList(List<ResourceInfo> resPoolList) {
        this.resPoolList = resPoolList;
    }
}
