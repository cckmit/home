/*******************************************************************************
 * @(#)VMQueryListAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMPreInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询当前登录用户所属业务下，预订购虚拟机列表信息
 * 
 * @author li-lei
 * @version $Revision 1.1 $ 2018-4-23 下午2:04:45
 */
public class VMPreApplyQueryListAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = -5471800875775849067L;

	private static LogService logger = LogService.getLogger(VMPreApplyQueryListAction.class);

	/**
	 * 预定虚拟机实例信息
	 */
	private List<VMPreInstanceInfo> vmResultInfos;
	
	private Map<String, Object> vmResultInfoMap;

	/**
	 * 虚拟机名称
	 */
	private String vmName;

	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		// session中获取用户
		UserBean user = (UserBean) ServletActionContext.getRequest().getSession()
				.getAttribute(SessionKeys.userInfo.toString());
		String userId = user.getUserId();

		VMPreInstanceInfo vmPreInstanceInfo = new VMPreInstanceInfo();

		if (null != vmName && !vmName.isEmpty()) {
			vmPreInstanceInfo.setVmName(vmName.toLowerCase());
		}

		try {
			vmResultInfos = getPage("countVmPreApplyUserListAll", "queryVmPreApplyUserListAll", vmPreInstanceInfo);
			fomatResultData(vmResultInfos);
			vmResultInfoMap = new HashMap<String, Object>();
			vmResultInfoMap.put("list", vmResultInfos);
			vmResultInfoMap.put("page", getPageBar());
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vm.queryList.fail"), userId), e);
			this.addActionError(MessageFormat.format(getText("vm.queryList.fail"), userId));
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 格式化生效时间、到期时间、状态显示汉字
	 * 
	 * @param ls要格式化的列表
	 */
	private void fomatResultData(List<VMPreInstanceInfo> ls) {
		for (VMPreInstanceInfo vmPreInstanceInfo : ls) {
			String sbt = vmPreInstanceInfo.getCreateTime() == null ? "" : vmPreInstanceInfo.getCreateTime().trim();
			if (!"".equals(sbt)) {
				String createTime = DateParse.parse(vmPreInstanceInfo.getCreateTime());
				vmPreInstanceInfo.setCreateTime(createTime);

			}
			if (!"".equals(vmPreInstanceInfo.getUpdateTime() == null ? "" : vmPreInstanceInfo.getUpdateTime())) {
				vmPreInstanceInfo.setUpdateTime(DateParse.parse(vmPreInstanceInfo.getUpdateTime()));
			}
		}
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public List<VMPreInstanceInfo> getVmResultInfos() {
		return vmResultInfos;
	}

	public void setVmResultInfos(List<VMPreInstanceInfo> vmResultInfos) {
		this.vmResultInfos = vmResultInfos;
	}

	public Map<String, Object> getVmResultInfoMap() {
		return vmResultInfoMap;
	}

	public void setVmResultInfoMap(Map<String, Object> vmResultInfoMap) {
		this.vmResultInfoMap = vmResultInfoMap;
	}

	public String getVmName() {
		return vmName;
	}

	
}
