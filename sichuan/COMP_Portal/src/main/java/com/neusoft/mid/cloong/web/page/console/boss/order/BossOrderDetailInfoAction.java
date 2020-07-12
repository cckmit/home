/*******************************************************************************
 * @(#)VMDetailInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.boss.order;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.page.console.boss.order.info.BossOrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * li-lei
 */
public class BossOrderDetailInfoAction extends BaseAction {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3246558237673423698L;

	/**
	 * 日志记录者
	 */
	private static LogService logger = LogService.getLogger(BossOrderDetailInfoAction.class);

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
	private BossOrderInfo bossOrderInfo;

	/**
	 * boss订购ID
	 */
	private String bossOrderId;
	
	private String id;

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
		id = id.trim();
		try {
			bossOrderInfo = (BossOrderInfo) ibatisDAO.getSingleRecord("queryBossOrderByOrderId", id);

		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vm.query.fail"), bossOrderId), e);
			this.addActionError(MessageFormat.format(getText("vm.query.fail"), bossOrderId));
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
		if (null == bossOrderInfo) {
			logger.info("boss订单信息不存在！");
			errMeg = "boss订单信息不存在！";
			return FAILURE;
		}
		if (null != bossOrderInfo.getAgreementBeginTime()) {
			bossOrderInfo.setAgreementBeginTime(DateParse.parse1(bossOrderInfo.getAgreementBeginTime()));
		}
		if (null != bossOrderInfo.getAgreementEndTime()) {
			bossOrderInfo.setAgreementEndTime(DateParse.parse1(bossOrderInfo.getAgreementEndTime()));
		}
		if (null != bossOrderInfo.getTimeStamp()) {
			bossOrderInfo.setTimeStamp(DateParse.parse(bossOrderInfo.getTimeStamp()));
		}
		return SUCCESS;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public BossOrderInfo getBossOrderInfo() {
		return bossOrderInfo;
	}

	public void setBossOrderInfo(BossOrderInfo bossOrderInfo) {
		this.bossOrderInfo = bossOrderInfo;
	}

	public String getBossOrderId() {
		return bossOrderId;
	}

	public void setBossOrderId(String bossOrderId) {
		this.bossOrderId = bossOrderId;
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
