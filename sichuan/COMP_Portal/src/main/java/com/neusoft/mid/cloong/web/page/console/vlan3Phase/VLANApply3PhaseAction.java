package com.neusoft.mid.cloong.web.page.console.vlan3Phase;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;

/**
 * 申请Vlan跳转前的Action
 */
public class VLANApply3PhaseAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -7596086026351355845L;

    private String itemId;

    private String queryBusinessId;

    private String businessName;
    
    private String userId;

    public String execute() {
   	 UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
             .getAttribute(SessionKeys.userInfo.toString()));
      userId = user.getUserId();
        return ConstantEnum.SUCCESS.toString();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取businessName字段数据
     * @return Returns the businessName.
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * 设置businessName字段数据
     * @param businessName The businessName to set.
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * 获取queryBusinessId字段数据
     * @return Returns the queryBusinessId.
     */
    public String getQueryBusinessId() {
        return queryBusinessId;
    }

    /**
     * 设置queryBusinessId字段数据
     * @param queryBusinessId The queryBusinessId to set.
     */
    public void setQueryBusinessId(String queryBusinessId) {
        this.queryBusinessId = queryBusinessId;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
