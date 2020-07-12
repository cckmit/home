package com.neusoft.mid.cloong.web.page.console.vlan3Phase;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.fileStore.info.FileStoreInfo;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanInfo;
import com.neusoft.mid.iamp.logger.LogService;

public class VLANDetail3PhaseAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
    private final LogService logger = LogService.getLogger(VLANDetail3PhaseAction.class);

    /**
     * 用于存放查询结果
     */
    private VlanInfo vlanInfo = new VlanInfo();

    /**
     * vlan订单ID
     */
    private String caseId;
    
    /**
     * vlan号
     */
    private String vlanId;

    /**
     * vlan名称
     */
    private String vlanName;
    
	private String resultPath = ConstantEnum.SUCCESS.toString();
    
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

        vlanInfo.setCaseId(caseId);
    	try {
    		vlanInfo = (VlanInfo) ibatisDAO.getSingleRecord("vlanDetail3Phase", vlanInfo);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("vlan.queryDetail.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("vlan.queryDetail.fail"), userId));
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
		}
    	if(vlanInfo.getCreateTime() != null){
    		vlanInfo.setCreateTime(DateParse.parse(vlanInfo.getCreateTime()));
    	}
    	if(vlanInfo.getCancelTime() != null){
    		vlanInfo.setCancelTime(DateParse.parse(vlanInfo.getCancelTime()));
    	}
    	if(vlanInfo.getUpdateTime() != null){
    		vlanInfo.setUpdateTime(DateParse.parse(vlanInfo.getUpdateTime()));
    	}
    	resultPath = ConstantEnum.SUCCESS.toString();
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

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

}
