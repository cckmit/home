/*******************************************************************************
 * @(#)DiskDetailAction.java 2013-3-20
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.disk;

import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.info.PreApplyDiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 显示云硬盘详细信息
 * 
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-20 下午05:16:45
 */
public class DiskPreApplyDetailAction extends BaseAction {

	private static final long serialVersionUID = 2501512361728019512L;

	private static LogService logger = LogService.getLogger(DiskPreApplyDetailAction.class);

	/**
	 * 云硬盘详细信息
	 */
	private PreApplyDiskInfo preApplyDiskInfo = new PreApplyDiskInfo();

	/**
	 * 实例编号
	 */
	private String caseId;

	public String execute() {
		
		preApplyDiskInfo.setCaseId(caseId);

		try {
			preApplyDiskInfo = (PreApplyDiskInfo) ibatisDAO.getSingleRecord("queryPreApplyDiskDetail", preApplyDiskInfo);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("disk.query.fail"), caseId), e);
			this.setErrMsg(MessageFormat.format(getText("disk.query.fail"), caseId));
			return ConstantEnum.FAILURE.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("disk.query.fail"), caseId), e);
			this.setErrMsg(MessageFormat.format(getText("disk.query.fail"), caseId));
			return ConstantEnum.FAILURE.toString();
		}

		if (preApplyDiskInfo.getCreateTime() != null && !preApplyDiskInfo.getCreateTime().equals(""))
			preApplyDiskInfo.setCreateTime(DateParse.parse(preApplyDiskInfo.getCreateTime()));
		if (preApplyDiskInfo.getUpdateTime() != null && !preApplyDiskInfo.getUpdateTime().equals("")) {
			preApplyDiskInfo.setUpdateTime(DateParse.parse(preApplyDiskInfo.getUpdateTime()));
		}

		// session中获取用户ID
		UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
				.getAttribute(SessionKeys.userInfo.toString()));

		return ConstantEnum.SUCCESS.toString();
	}

	public PreApplyDiskInfo getPreApplyDiskInfo() {
		return preApplyDiskInfo;
	}

	public void setPreApplyDiskInfo(PreApplyDiskInfo preApplyDiskInfo) {
		this.preApplyDiskInfo = preApplyDiskInfo;
	}

	/**
	 * 获取caseId字段数据
	 * 
	 * @return Returns the caseId.
	 */
	public String getCaseId() {
		return caseId;
	}

	/**
	 * 设置caseId字段数据
	 * 
	 * @param caseId
	 *            The caseId to set.
	 */
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
}
