/*******************************************************************************
 * @(#)VMDetailInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
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
public class VMDetailInfoAction extends ResourceManagementBaseAction {

	/**
	 * 资源池管理平台URL
	 */
	private String resPoolManagementUrl = "";
	
    private String queryBusinessId;
    
	private String businessName;


	/**
	 * vnc连接地址
	 */
	private String vncUrl;

	/**
	 * 资源绑定的业务列表
	 */
	private List<BusinessInfo> businessList = new ArrayList<BusinessInfo>();

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
	 * SUCCESS结果码
	 */
	private static final String SUCCESS = "success";


	/**
	 * ERROR结果码
	 */
	private static final String ERROR = "error";

	/**
	 * 虚拟机实例信息
	 */
	private VMInstanceInfo vmInstanceInfo;

	/**
	 * 虚拟机ID-CASEID
	 */
	private String vmId;

	/**
	 * 错误信息
	 */
	private String errMeg;

	/**
	 * execute Action实现方法
	 * 
	 * @return Action结果码
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		vmId = vmId.trim();
		String tip = getText("vm.query.fail");
		try {
			vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord(
					"getVmDetailByCaseId", vmId);
			vmInstanceInfo.setNetList(ibatisDAO.getData("getNetByCaseId", vmId));
			
			int ramSize =StringUtils.isEmpty( vmInstanceInfo.getRamSize()) ?0: Integer.valueOf( vmInstanceInfo.getRamSize());
			vmInstanceInfo.setRamSize(String.valueOf(ramSize/1024));
			
			List<String> ipList = new ArrayList<String>();
			if(vmInstanceInfo.getNetList()!=null || !vmInstanceInfo.getNetList().isEmpty()){
				for(NetInfo netInfo:vmInstanceInfo.getNetList()){
						ipList.add(netInfo.getIp());
				}
			}
		if(ipList.size()>0){
		   List<String> publicIpList = ibatisDAO.getData("getPublicIpByIp", ipList)	;
		    StringBuffer sb = new StringBuffer();
		   for(int i =0;i<publicIpList.size();i++){
			   if(i==publicIpList.size()-1){
				   sb.append(publicIpList.get(i));
			   }else{
				   sb.append(publicIpList.get(i)).append(" ").append(" ,").append(" ");
			   }
			    
		   }
			vmInstanceInfo.setNetworkIp(sb.toString());
		}
			
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(tip, vmId), e);
			this.addActionError(MessageFormat.format(tip, vmId));
			return ERROR;
		} catch (Exception e) {
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION,
					MessageFormat.format(tip, vmId), e);
			this.addActionError(MessageFormat.format(tip, vmId));
			return ERROR;
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

	public String getErrMeg() {
		return errMeg;
	}

	public void setErrMeg(String errMeg) {
		this.errMeg = errMeg;
	}

	/**
	 * @return the queryBusinessId
	 */
	public String getQueryBusinessId() {
		return queryBusinessId;
	}

	/**
	 * @param queryBusinessId the queryBusinessId to set
	 */
	public void setQueryBusinessId(String queryBusinessId) {
		this.queryBusinessId = queryBusinessId;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
