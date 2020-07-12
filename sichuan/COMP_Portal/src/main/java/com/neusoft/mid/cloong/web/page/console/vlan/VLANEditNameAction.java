package com.neusoft.mid.cloong.web.page.console.vlan;

import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanInfo;
import com.neusoft.mid.iamp.logger.LogService;

public class VLANEditNameAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private final LogService logger = LogService.getLogger(VLANEditNameAction.class);
	
	private String caseId;
	
	private String vlanName;
	
	 @SuppressWarnings("unchecked")
	public String execute() {
		 // session中获取用户ID
	     UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
	                .getAttribute(SessionKeys.userInfo.toString()));
	     String userId = user.getUserId();
		 
		 logger.info("修改vlan名称开始");
		 VlanInfo vlan = new VlanInfo();
		 vlan.setCaseId(caseId);
		 vlan.setVlanName(vlanName);
		 
		 try {
			ibatisDAO.updateData("editVlanName", vlan);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("vlan.editVlanName.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("vlan.editVlanName.fail"), userId));
            return ConstantEnum.ERROR.toString();
		}
		 logger.info("修改vlan名称成功");
	     return ConstantEnum.SUCCESS.toString();
	 }

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
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
