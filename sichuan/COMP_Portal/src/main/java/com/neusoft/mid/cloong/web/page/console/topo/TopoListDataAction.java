/*******************************************************************************
 * @(#)EBSQueryListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.topo;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMResultInfo;
import com.neusoft.mid.cloong.web.page.console.topo.info.ResultInfos;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询登录用户下云硬盘列表
 * 
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 下午04:03:15
 */
public class TopoListDataAction extends BaseAction {

	private static final long serialVersionUID = -5471800875775849067L;

	private static LogService logger = LogService.getLogger(TopoListDataAction.class);

	/**
	 * 虚拟机实例信息
	 */
	private List<VMResultInfo> vmResultInfos;

	private List<DiskInfo> diskInfos;

	private List<DiskInfo> diskInfos4Up;
	
	private ResultInfos resultInfos = new ResultInfos();
	
	private List<ResultInfos> resultInfoList;
	
	@SuppressWarnings("unchecked")
	public String execute() {
		if (null != errMsg && !"".equals(errMsg)) {
			this.addActionError(errMsg);
		}
		// session中获取用户ID
		UserBean user = getCurrentUser();
		String userId = user.getUserId();

		try {
			// 当前用户所有虚拟机
			vmResultInfos = ibatisDAO.getData("queryVmByApp4TOPO", userId);
			// 当前用户所有未挂载云硬盘
			diskInfos = ibatisDAO.getData("queryEbsByApp4TOPO", userId);
			// 当前用户所有挂载的云硬盘
			diskInfos4Up = ibatisDAO.getData("queryEbsByAppUp4TOPO", userId);
			
			resultInfos.setVmResultInfos(vmResultInfos);
			resultInfos.setDiskInfos(diskInfos);
			resultInfos.setDiskInfos4Up(diskInfos4Up);
			
			// 当前用户所有业务下的vm、为挂载ebs、已挂载ebs数量
			resultInfoList = ibatisDAO.getData("sumVmEbsEBSup", userId);
			
		} catch (SQLException e) {
			logger.error("topo查询虚拟机或者云硬盘信息时出错, ", e);
		}

		return ConstantEnum.SUCCESS.toString();
	}

	public List<VMResultInfo> getVmResultInfos() {
		return vmResultInfos;
	}

	public void setVmResultInfos(List<VMResultInfo> vmResultInfos) {
		this.vmResultInfos = vmResultInfos;
	}

	public List<DiskInfo> getDiskInfos() {
		return diskInfos;
	}

	public void setDiskInfos(List<DiskInfo> diskInfos) {
		this.diskInfos = diskInfos;
	}

	public List<DiskInfo> getDiskInfos4Up() {
		return diskInfos4Up;
	}

	public void setDiskInfos4Up(List<DiskInfo> diskInfos4Up) {
		this.diskInfos4Up = diskInfos4Up;
	}

	public ResultInfos getResultInfos() {
		return resultInfos;
	}

	public void setResultInfos(ResultInfos resultInfos) {
		this.resultInfos = resultInfos;
	}

	public List<ResultInfos> getResultInfoList() {
		return resultInfoList;
	}

	public void setResultInfoList(List<ResultInfos> resultInfoList) {
		this.resultInfoList = resultInfoList;
	}
	

}
