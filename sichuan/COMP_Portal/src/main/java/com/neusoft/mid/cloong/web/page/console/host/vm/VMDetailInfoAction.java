/*******************************************************************************
 * @(#)VMDetailInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机详细信息
 * 
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 上午10:39:00
 */
public class VMDetailInfoAction extends BaseAction {

	/**
	 * 资源池管理平台URL
	 */
	private String resPoolManagementUrl = "";

	/**
	 * vnc连接地址
	 */
	private String vncUrl;

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3246558237673423698L;

	/**
	 * 日志记录者
	 */
	private static LogService logger = LogService
			.getLogger(VMDetailInfoAction.class);

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
	private VMInstanceInfo vmInstanceInfo;

	/**
	 * 查询参数类型,0为vmID,1为resourceID
	 */
	private int queryParmeterType;

	/**
	 * 虚拟机ID
	 */
	private String vmId;

	/**
	 * 错误消息
	 */
	private String errMeg;

	/**
	 * 资源绑定的业务列表
	 */
	private List<BusinessInfo> businessList = new ArrayList<BusinessInfo>();

	/**
	 * execute Action执行方法
	 * 
	 * @return 结果码
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		vmId = vmId.trim();
		try {
			if (0 == queryParmeterType) {
				vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord(
						"getVmDetail", vmId);
				vmInstanceInfo.setNetList(ibatisDAO.getData("getNet", vmId));
			} else {
				vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord(
						"getVmDetailByCaseId", vmId);
				vmInstanceInfo.setNetList(ibatisDAO.getData("getNetByCaseId",
						vmId));
			}

//			int ramSize = StringUtils.isEmpty(vmInstanceInfo.getRamSize()) ? 0
//					: Integer.valueOf(vmInstanceInfo.getRamSize());
//			vmInstanceInfo.setRamSize(String.valueOf(ramSize));
			if (String.valueOf(Float.parseFloat(vmInstanceInfo.getRamSize()) /1024).contains(".0")) {
				vmInstanceInfo.setRamSize(String.valueOf(Math.round(Float.parseFloat(vmInstanceInfo.getRamSize())/1024)));
			} else {
				vmInstanceInfo.setRamSize(String.valueOf(Float.parseFloat(vmInstanceInfo.getRamSize())/1024));
			}
			
			if (String.valueOf(Float.parseFloat(vmInstanceInfo.getDiscSize())/1024).contains(".0")) { // 如果是1.0这样的形式时把。0去掉
				vmInstanceInfo.setDiscSize(String.valueOf(Math.round(Float.parseFloat(vmInstanceInfo.getDiscSize())/1024)));
			} else {
				vmInstanceInfo.setDiscSize(String.valueOf(Float.parseFloat(vmInstanceInfo.getDiscSize())/1024));
			}

			List<String> ipList = new ArrayList<String>();
			if (vmInstanceInfo.getNetList() != null
					|| !vmInstanceInfo.getNetList().isEmpty()) {
				for (NetInfo netInfo : vmInstanceInfo.getNetList()) {
					ipList.add(netInfo.getIp());
				}
			}
			if (ipList.size() > 0) {
				List<String> publicIpList = ibatisDAO.getData(
						"getPublicIpByIp", ipList);
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < publicIpList.size(); i++) {
					if (i == publicIpList.size() - 1) {
						sb.append(publicIpList.get(i));
					} else {
						sb.append(publicIpList.get(i)).append(" ").append(" ,")
								.append(" ");
					}

				}
				vmInstanceInfo.setNetworkIp(sb.toString());
			}
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vm.query.fail"), vmId), e);
			this.addActionError(MessageFormat.format(getText("vm.query.fail"),
					vmId));
			return ERROR;
		}
		String result = step2();
		if (!SUCCESS.equals(result)) {
			return FAILURE;
		}
		if (logger.isDebugEnable()) {
			StringBuilder sb = new StringBuilder();
			sb.append("虚拟机实例信息为:\n");
			sb.append(vmInstanceInfo.toString());
			logger.debug(sb.toString());
		}
		return SUCCESS;
	}

	/**
	 * step2 Action执行方法中的步骤二
	 * 
	 * @return 结果码
	 */
	private String step2() {
		if (null == vmInstanceInfo) {
			logger.info("虚拟机实例信息不存在！");
			errMeg = "虚拟机实例信息不存在！";
			return FAILURE;
		}
		if (null != vmInstanceInfo.getCreateTime()) {
			vmInstanceInfo.setCreateTime(DateParse.parse(vmInstanceInfo
					.getCreateTime()));
		}
		if (null != vmInstanceInfo.getExpireTime()) {
			vmInstanceInfo.setExpireTime(DateParse.parse(vmInstanceInfo
					.getExpireTime()));
		}
		return SUCCESS;
	}

	public VMInstanceInfo getVmInstanceInfo() {
		return vmInstanceInfo;
	}

	public void setVmInstanceInfo(VMInstanceInfo vmInstanceInfo) {
		this.vmInstanceInfo = vmInstanceInfo;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	/**
	 * 获取resPoolManagementUrl字段数据
	 * 
	 * @return Returns the resPoolManagementUrl.
	 */
	public String getResPoolManagementUrl() {
		return resPoolManagementUrl;
	}

	/**
	 * 设置resPoolManagementUrl字段数据
	 * 
	 * @param resPoolManagementUrl
	 *            The resPoolManagementUrl to set.
	 */
	public void setResPoolManagementUrl(String resPoolManagementUrl) {
		this.resPoolManagementUrl = resPoolManagementUrl;
	}

	/**
	 * 获取businessList字段数据
	 * 
	 * @return Returns the businessList.
	 */
	public List<BusinessInfo> getBusinessList() {
		return businessList;
	}

	/**
	 * 获取vncUrl字段数据
	 * 
	 * @return Returns the vncUrl.
	 */
	public String getVncUrl() {
		return vncUrl;
	}

	/**
	 * 设置vncUrl字段数据
	 * 
	 * @param vncUrl
	 *            The vncUrl to set.
	 */
	public void setVncUrl(String vncUrl) {
		this.vncUrl = vncUrl;
	}

	/**
	 * 获取queryParmeterType字段数据
	 * 
	 * @return Returns the queryParmeterType.
	 */
	public int getQueryParmeterType() {
		return queryParmeterType;
	}

	/**
	 * 设置queryParmeterType字段数据
	 * 
	 * @param queryParmeterType
	 *            The queryParmeterType to set.
	 */
	public void setQueryParmeterType(int queryParmeterType) {
		this.queryParmeterType = queryParmeterType;
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
