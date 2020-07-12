/*******************************************************************************
 * @(#)VMDetailInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMPreInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机详细信息
 * 
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 上午10:39:00
 */
public class VMPreApplyDetailInfoAction extends BaseAction {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3246558237673423698L;

	/**
	 * 日志记录者
	 */
	private static LogService logger = LogService.getLogger(VMPreApplyDetailInfoAction.class);

	/**
	 * 成功结果码
	 */
	private static final String SUCCESS = "success";

	/**
	 * 失败结果码
	 */
	private static final String FAILURE = "failure";

	/**
	 * 错误结果码
	 */
	private static final String ERROR = "error";

	/**
	 * 虚拟机实例信息
	 */
	private VMPreInstanceInfo vmPreInstanceInfo;

	/**
	 * 预订购caseId
	 */
	private String caseId;

	/**
	 * 错误消息
	 */
	private String errMeg;

	/**
	 * execute Action执行方法
	 * 
	 * @return 结果码
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		caseId = caseId.trim();
		try {
			vmPreInstanceInfo = (VMPreInstanceInfo) ibatisDAO.getSingleRecord("queryVmPreApplyUser", caseId);

		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vm.query.fail"), caseId), e);
			this.addActionError(MessageFormat.format(getText("vm.query.fail"), caseId));
			return ERROR;
		}
		String result = step2();
		if (!SUCCESS.equals(result)) {
			return FAILURE;
		}
		return SUCCESS;
	}

	/**
	 * step2 Action执行方法中的步骤二
	 * 
	 * @return 结果码
	 */
	private String step2() {
		if (null == vmPreInstanceInfo) {
			logger.info("虚拟机实例信息不存在！");
			errMeg = "虚拟机实例信息不存在！";
			return FAILURE;
		}
		if (null != vmPreInstanceInfo.getCreateTime()) {
			vmPreInstanceInfo.setCreateTime(DateParse.parse(vmPreInstanceInfo.getCreateTime()));
		}
		if (null != vmPreInstanceInfo.getUpdateTime()) {
			vmPreInstanceInfo.setUpdateTime(DateParse.parse(vmPreInstanceInfo.getUpdateTime()));
		}
		return SUCCESS;
	}

	public VMPreInstanceInfo getVmPreInstanceInfo() {
		return vmPreInstanceInfo;
	}

	public void setVmPreInstanceInfo(VMPreInstanceInfo vmPreInstanceInfo) {
		this.vmPreInstanceInfo = vmPreInstanceInfo;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * 获取errMeg字段数据
	 * 
	 * @return Returns the errMeg.
	 */
	public String getErrMeg() {
		return errMeg;
	}

	/**
	 * 设置errMeg字段数据
	 * 
	 * @param errMeg
	 *            The errMeg to set.
	 */
	public void setErrMeg(String errMeg) {
		this.errMeg = errMeg;
	}

}
