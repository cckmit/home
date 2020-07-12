/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.performance;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

public class VmPerformanceDetailAction extends BaseAction {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 2094313569994857016L;

	/**
	 * 日志记录者
	 */
	private static LogService logger = LogService.getLogger(VmPerformanceDetailAction.class);
	
	private String vmId;
	
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
	 * 错误消息
	 */
	private String errMeg;

	public String execute() {
		vmId = vmId.trim();
		try {
			vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord(
					"getVmPerfDetail", vmId);
			vmInstanceInfo.setNetList(ibatisDAO.getData("getPerfNet", vmId));
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
						"getPerfPublicIpByIp", ipList);
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
	
	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public VMInstanceInfo getVmInstanceInfo() {
		return vmInstanceInfo;
	}

	public void setVmInstanceInfo(VMInstanceInfo vmInstanceInfo) {
		this.vmInstanceInfo = vmInstanceInfo;
	}

	public String getErrMeg() {
		return errMeg;
	}

	public void setErrMeg(String errMeg) {
		this.errMeg = errMeg;
	}
	
}
