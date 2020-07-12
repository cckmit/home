/*******************************************************************************
 * @(#)EBSApplyInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.ebs.order;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.disk.info.PreApplyDiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 提交申请云硬盘订单
 * 
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.1 $ 2013-1-10 下午04:18:41
 */
public class EBSPreApplyInfoAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = -3246558237673423698L;

	private static LogService logger = LogService.getLogger(EBSPreApplyInfoAction.class);

	private String itemId;

	private String standardId;

	private String lengthTime;

	private String respoolId;

	private String respoolPartId;

	private String respoolName;

	private String respoolPartName;

	private String ebsName;

	/**
	 * 磁盘大小
	 */
	private String discSize;

	/**
	 * 磁盘个数
	 */
	private String discNum;

	/**
	 * 业务ID
	 */
	private String appId;

	/**
	 * 业务名称
	 */
	private String appName;

	/**
	 * 成功的接口响应码
	 */
	private static final String SUCCEESS_CODE = "00000000";

	/**
	 * 时间戳 yyyyMMddHHmmss
	 */
	private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

	/**
	 * 收费类型.
	 */
	private String chargeType;

	@Override
	public String execute() {
		// session中获取用户ID
		String userId = ((UserBean) ServletActionContext.getRequest().getSession()
				.getAttribute(SessionKeys.userInfo.toString())).getUserId();

		PreApplyDiskInfo preApplyDiskInfo = new PreApplyDiskInfo();
		String time = DateParse.generateDateFormatyyyyMMddHHmmss();
		preApplyDiskInfo.setCaseId(generatorCaseId("EBS"));
		preApplyDiskInfo.setEbsName(ebsName);
		preApplyDiskInfo.setDiskSize(discSize);
		preApplyDiskInfo.setStatus("0"); // 待订购
		preApplyDiskInfo.setAppId(appId);
		preApplyDiskInfo.setResPoolId(respoolId);
		preApplyDiskInfo.setResPoolName(respoolName);
		preApplyDiskInfo.setResPoolPartName(respoolPartName);
		preApplyDiskInfo.setResPoolPartId(respoolPartId);
		preApplyDiskInfo.setChargeType(chargeType);
		preApplyDiskInfo.setChargeTime(lengthTime);
		preApplyDiskInfo.setNum(discNum);
		preApplyDiskInfo.setCreateTime(time);
		preApplyDiskInfo.setCreateUser(userId);
		preApplyDiskInfo.setDescription("");

		try {

			ibatisDAO.insertData("insertPreApplyEbs", preApplyDiskInfo);
			msg = "预订购云硬盘申请发送成功！";
			return ConstantEnum.SUCCESS.toString();

		} catch (Exception e2) {
			msg = "预订购云硬盘申请发送失败！";
			return ConstantEnum.FAILURE.toString();
		}

	}

	public String generatorCaseId(String type) {
		StringBuilder sb = new StringBuilder();
		String currentTimeStamp = TIMESTAMP.print(new DateTime());
		sb.append("CASE-").append("PRE-").append(type).append("-").append("TT").append("-").append(currentTimeStamp);
		return sb.toString();
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	public String getLengthTime() {
		return lengthTime;
	}

	public void setLengthTime(String lengthTime) {
		this.lengthTime = lengthTime;
	}

	public String getRespoolId() {
		return respoolId;
	}

	public void setRespoolId(String respoolId) {
		this.respoolId = respoolId;
	}

	public String getRespoolPartId() {
		return respoolPartId;
	}

	public void setRespoolPartId(String respoolPartId) {
		this.respoolPartId = respoolPartId;
	}

	public String getRespoolName() {
		return respoolName;
	}

	public void setRespoolName(String respoolName) {
		this.respoolName = respoolName;
	}

	public String getRespoolPartName() {
		return respoolPartName;
	}

	public void setRespoolPartName(String respoolPartName) {
		this.respoolPartName = respoolPartName;
	}

	public String getEbsName() {
		return ebsName;
	}

	public void setEbsName(String ebsName) {
		this.ebsName = ebsName;
	}

	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType
	 *            the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * 获取discSize字段数据
	 * 
	 * @return Returns the discSize.
	 */
	public String getDiscSize() {
		return discSize;
	}

	/**
	 * 设置discSize字段数据
	 * 
	 * @param discSize
	 *            The discSize to set.
	 */
	public void setDiscSize(String discSize) {
		this.discSize = discSize;
	}

	/**
	 * 获取appName字段数据
	 * 
	 * @return Returns the appName.
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * 设置appName字段数据
	 * 
	 * @param appName
	 *            The appName to set.
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * 获取discNum字段数据
	 * 
	 * @return Returns the discNum.
	 */
	public String getDiscNum() {
		return discNum;
	}

	/**
	 * 设置discNum字段数据
	 * 
	 * @param discNum
	 *            The discNum to set.
	 */
	public void setDiscNum(String discNum) {
		this.discNum = discNum;
	}

	/**
	 * 设置logger字段数据
	 * 
	 * @param logger
	 *            The logger to set.
	 */
	public static void setLogger(LogService logger) {
		EBSPreApplyInfoAction.logger = logger;
	}

}
