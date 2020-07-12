/*******************************************************************************
 * @(#)VMNameUpdateAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.boss.order;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.boss.order.info.BossOrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * li-lei
 */
public class StatusUpdateAction extends BaseAction {

	private static final long serialVersionUID = 5905281979052112854L;

	private static LogService logger = LogService.getLogger(StatusUpdateAction.class);

	private String result = ConstantEnum.FAILURE.toString();

	/**
	 * 虚拟机预订购的vm实例id
	 */
	private String bossOrderId;

	public String execute() {
		bossOrderId = bossOrderId.trim();
		BossOrderInfo bossOrderInfo = new BossOrderInfo();
		bossOrderInfo.setBossOrderId(bossOrderId);
		bossOrderInfo.setStatus("1"); // 已操作
		bossOrderInfo.setTimeStamp(DateParse.generateDateFormatyyyyMMddHHmmss());
		try {
			ibatisDAO.updateData("updateBossOrderStatus", bossOrderInfo);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vmname.update.fail"), bossOrderId), e);
			this.addActionError(MessageFormat.format(getText("vmname.update.fail"), bossOrderId));
			result = ConstantEnum.ERROR.toString();
			return ConstantEnum.FAILURE.toString();
		}
		result = ConstantEnum.SUCCESS.toString();
		return ConstantEnum.SUCCESS.toString();
	}

	public String getBossOrderId() {
		return bossOrderId;
	}

	public void setBossOrderId(String bossOrderId) {
		this.bossOrderId = bossOrderId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
