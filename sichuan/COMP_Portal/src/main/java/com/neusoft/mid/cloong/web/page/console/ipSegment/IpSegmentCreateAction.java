package com.neusoft.mid.cloong.web.page.console.ipSegment;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;

/**
 * 跳转到创建IP段页面
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月9日 上午10:58:36
 */
public class IpSegmentCreateAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -5923919125472368364L;

    /**
     * 业务ID
     */
    private String queryBusinessId;

    /**
     * 业务名称
     */
    private String businessName;
    
    private String userId;

    public String execute() {
    	  UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                  .getAttribute(SessionKeys.userInfo.toString()));
           userId = user.getUserId();

        return ConstantEnum.SUCCESS.toString();
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

}
