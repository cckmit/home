/*******************************************************************************
 * @(#)ItemQueryListAction.java 2013-2-5
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.item.user.action;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.query.QueryUserInfo;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询待审批用户action
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月27日 下午2:11:33
 */
public class UserApprovalListAction extends PageAction {

    private static final long serialVersionUID = -309553214096416609L;

    private static LogService logger = LogService.getLogger(UserApprovalListAction.class);

    /**
     * 界面返回
     */
    private List<UserBean> userBeans;

    /**
     * 查询条件
     */
    private QueryUserInfo queryUserInfo = new QueryUserInfo();
    
    /**
     * item信息
     */
    private Map<String,Object> userInfos;
    
    /**
     * 待审批用户列表查询
     * @return
     */
    @SuppressWarnings("unchecked")
	public String execute() {

        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        try {
            userBeans = getPage("queryUserApplyCount", "queryUserApplyInfo", queryUserInfo);
            int userListCount = ibatisDAO.getCount("queryUserApplyCount", queryUserInfo);
            //绑定角色和业务信息
            for(UserBean user:userBeans){
            	List<UserAppBean> appList = ibatisDAO.getData("queryBingAppsbyUserId", user.getUserId());
            	user.setApps(appList);
            	user.setUpdateTime(DateParse.parse(user.getUpdateTime()));
            }
            userInfos = new HashMap<String, Object>();
            userInfos.put("listCount", userListCount);
            userInfos.put("list", userBeans);
            userInfos.put("page", getPageBar());
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("user.query.fail"), userId,null), e);
            this.addActionError(MessageFormat.format(getText("user.query.fail"), userId,null));
            return ConstantEnum.ERROR.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("待审批用户列表长度为:");
            sb.append(userBeans.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }
    
    private void formatDate(List<UserBean> userBeans) {
        if (userBeans != null) {
            for (UserBean userBean : userBeans) {
                userBean.setUpdateTime(DateParse.parse(userBean.getUpdateTime()));
            }
        }
    }



    public void setQueryUserInfo(QueryUserInfo queryUserInfo) {
        this.queryUserInfo = queryUserInfo;
    }
    

    public QueryUserInfo getQueryUserInfo() {
        return queryUserInfo;
    }

    public List<UserBean> getUserBeans() {
        return userBeans;
    }

	/**
	 * @return the userInfos
	 */
	public Map<String, Object> getUserInfos() {
		return userInfos;
	}

	/**
	 * @param userInfos the userInfos to set
	 */
	public void setUserInfos(Map<String, Object> userInfos) {
		this.userInfos = userInfos;
	}

}
