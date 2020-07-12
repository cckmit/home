package com.neusoft.mid.cloong.web.page.console.vlan;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanInfo;
import com.neusoft.mid.iamp.logger.LogService;

public class VLANDetailAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
    private final LogService logger = LogService.getLogger(VLANDetailAction.class);

    /**
     * 用于存放查询结果
     */
    private VlanInfo vlanInfo;

    /**
     * vlan号
     */
    private String vlanId;

    /**
     * vlan名称
     */
    private String vlanName;
    
    @SuppressWarnings("unchecked")
    public String execute() {

        logger.info("进入查询vlan详细信息Action");

        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
        String userId = user.getUserId();

        VlanInfo vlan = new VlanInfo();
        vlan.setVlanId(vlanId);
        vlan.setVlanName(vlanName);
    	try {
			ibatisDAO.getSingleRecord("vlanQuery", vlan);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("vlan.queryDetail.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("vlan.queryDetail.fail"), userId));
            return ConstantEnum.ERROR.toString();
		}
    	return ConstantEnum.SUCCESS.toString();
    }

	public VlanInfo getVlanInfo() {
		return vlanInfo;
	}

	public void setVlanInfo(VlanInfo vlanInfo) {
		this.vlanInfo = vlanInfo;
	}

	public String getVlanId() {
		return vlanId;
	}

	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}

	public String getVlanName() {
		return vlanName;
	}

	public void setVlanName(String vlanName) {
		this.vlanName = vlanName;
	}

	public LogService getLogger() {
		return logger;
	}

}
