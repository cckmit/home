/*******************************************************************************
 * @(#)VMNameUpdateAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.disk;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.disk.info.PreApplyDiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 更改虚拟机名称，返回JSON对象
 * 
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-8 下午01:14:31
 */
public class EbsPreApplyStatusUpdateAction extends BaseAction {

	private static final long serialVersionUID = 5905281979052112854L;

	private static LogService logger = LogService.getLogger(EbsPreApplyStatusUpdateAction.class);

	private String result = ConstantEnum.FAILURE.toString();

	/**
	 * 虚拟机预订购的vm实例id
	 */
	private String caseId;

	public String execute() {
		caseId = caseId.trim();
		PreApplyDiskInfo preApplyDiskInfo = new PreApplyDiskInfo();
		preApplyDiskInfo.setCaseId(caseId);
		preApplyDiskInfo.setStatus("1");
		preApplyDiskInfo.setUpdateUser(getCurrentUserId());
		preApplyDiskInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		try {
			ibatisDAO.updateData("updateEbsPreApplyStatus", preApplyDiskInfo);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("ebs.update.fail"), caseId), e);
			this.addActionError(MessageFormat.format(getText("ebs.update.fail"), caseId));
			result = ConstantEnum.ERROR.toString();
			return ConstantEnum.FAILURE.toString();
		}
		result = ConstantEnum.SUCCESS.toString();
		return ConstantEnum.SUCCESS.toString();
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
