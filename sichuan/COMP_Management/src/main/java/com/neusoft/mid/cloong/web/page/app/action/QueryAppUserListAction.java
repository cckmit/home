package com.neusoft.mid.cloong.web.page.app.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.iamp.logger.LogService;
/**
 * 查询用户列表
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2014-1-10 上午10:16:18
 */
public class QueryAppUserListAction extends PageAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(QueryAppUserListAction.class);

    private UserBean user = new UserBean();

    private List<UserBean> userList = new ArrayList<UserBean>();

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @SuppressWarnings("unchecked")
    public String execute() {
        try {
        	this.userList = this.getPage("countUser", "queryAppUserList", user, PageTransModel.ASYNC);
        } catch (Exception e) {
        	logger.error("获取用户列表失败", e);
        	return ConstantEnum.FAILURE.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取userList字段数据
     * @return Returns the userList.
     */
    public List<UserBean> getUserList() {
        return userList;
    }

    /**
     * 设置userList字段数据
     * @param userList The userList to set.
     */
    public void setUserList(List<UserBean> userList) {
        this.userList = userList;
    }

	/**
	 * @return the user
	 */
	public UserBean getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserBean user) {
		this.user = user;
	}

    

}
