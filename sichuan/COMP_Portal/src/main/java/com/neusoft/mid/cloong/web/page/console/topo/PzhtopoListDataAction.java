/*******************************************************************************
 * @(#)EBSQueryListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.topo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neusoft.mid.cloong.host.vm.core.QueryVMState;
import com.neusoft.mid.cloong.host.vm.core.QueryVMStateReq;
import com.neusoft.mid.cloong.host.vm.core.QueryVMStateResp;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
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
public class PzhtopoListDataAction extends BaseAction {
	
	
	private static final long serialVersionUID = -9181481564812674870L;

	private static LogService logger = LogService.getLogger(PzhtopoListDataAction.class);

	/**
	 * 虚拟机实例信息
	 */
	private List<VMResultInfo> vmResultInfos;

	private List<DiskInfo> diskInfos;

	private List<DiskInfo> diskInfos4Up;
	
	private ResultInfos resultInfos = new ResultInfos();
	
	private List<ResultInfos> resultInfoList;
	
	private List<ResultInfos> applist;
	
	private List<ResultInfos> resultList;
	
	private ResultInfos sub= new ResultInfos();
	
	private ResultInfos statusinfo= new ResultInfos();
	
	public String userId;
	
	public String appid;
	
	public String subnetstart;
	
	public String subnetend;
	
	public String vmId;
	
	public String resPoolId;
	
	public String resPoolPartId;
	/**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";
	
	/**
     * 查询虚拟机状态
     */
    private QueryVMState queryVMState;
	
	//根据网段查询主机
	@SuppressWarnings({"unchecked"})
	public String execute() {
		if (null != errMsg && !"".equals(errMsg)) {
			this.addActionError(errMsg);
		}		
		sub.setSubnetstart(this.subnetstart);
		sub.setSubnetend(this.subnetend);
		try {	
				resultInfoList = ibatisDAO.getData("pzhtopo",sub);
				if(resultInfoList==null){
					resultInfoList = ibatisDAO.getData("pzh2topo",sub);
				}

		} catch (SQLException e) {
			logger.error("topo查询虚拟机或者云硬盘信息时出错, ", e);
		}		
		return ConstantEnum.SUCCESS.toString();
	}
	
	//根据vmid展示主机状态
		@SuppressWarnings("unchecked")
		public String showtopostatus() {	
			statusinfo.setVmId(this.vmId);
			statusinfo.setResPoolId(this.resPoolId);
			statusinfo.setResPoolPartId(this.resPoolPartId);
				if(!(this.vmId.equals(""))&&!(this.vmId.equals(null))) {
			QueryVMStateReq queryVMStateReq = new QueryVMStateReq();
	        queryVMStateReq.setVmId(statusinfo.getVmId());
	        queryVMStateReq.setResourcePoolId(statusinfo.getResPoolId());
	        queryVMStateReq.setResourcePoolPartId(statusinfo.getResPoolPartId());
	        QueryVMStateResp resp = queryVMState.queryVMState(queryVMStateReq);
	        // QueryVMStateResp resp = new QueryVMStateResp();
	        // resp.setResultCode(SUCCEESS_CODE);
	        // resp.setVmState("4");
	        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
	        	statusinfo.setVmId(queryVMStateReq.getVmId());
	        	statusinfo.setStatus(stateParse(resp.getVmState()));
	        	statusinfo.setStatuscode(statusinfo.getStatus().getCode());
	        	statusinfo.setStatustext(statusinfo.getStatus().getDesc());
	        } else {
	        	statusinfo.setVmId(queryVMStateReq.getVmId());
	        	statusinfo.setStatus(stateParse("15"));
	        	statusinfo.setStatuscode(statusinfo.getStatus().getCode());
	        	statusinfo.setStatustext(statusinfo.getStatus().getDesc());
			}
	        }else {        	
	        	statusinfo.setStatuscode("0");
	        	statusinfo.setStatustext("待创建");
	        }
				
			return ConstantEnum.SUCCESS.toString();
		}
	//展示admin用户网段拓扑
	@SuppressWarnings("unchecked")
	public String showPzhtopobyapp() {
		if (null != errMsg && !"".equals(errMsg)) {
			this.addActionError(errMsg);
		}		
		String appid=this.appid;
		try {	
			resultList = ibatisDAO.getData("ipsubnet",appid);
		} catch (SQLException e) {
			logger.error("topo查询虚拟机或者云硬盘信息时出错, ", e);
		}

		return ConstantEnum.SUCCESS.toString();
	}
	//展示非admin用户网段拓扑
	@SuppressWarnings("unchecked")
	public String showPzhtopobyapp2() {
		if (null != errMsg && !"".equals(errMsg)) {
			this.addActionError(errMsg);
		}		
		UserBean user = getCurrentUser();
		userId = user.getUserId();
		try {	
			resultList = ibatisDAO.getData("ipsubnetbyuser",userId);
		} catch (SQLException e) {
			logger.error("topo查询虚拟机或者云硬盘信息时出错, ", e);
		}

		return ConstantEnum.SUCCESS.toString();
	}
	//为admin用户展示业务列表
	public String showApplist() {
		if (null != errMsg && !"".equals(errMsg)) {
			this.addActionError(errMsg);
		}		
		UserBean user = getCurrentUser();
		userId = user.getUserId();
		resPoolId =this.resPoolId;
		Map<String, String> userRespool = new HashMap<String, String>();
		userRespool.put("userId", userId);
		userRespool.put("resPoolId", resPoolId);
		try {	
			applist = ibatisDAO.getData("showapplist",userRespool);
		} catch (SQLException e) {
			logger.error("topo查询app列表出错, ", e);
		}
		
		return ConstantEnum.SUCCESS.toString();

	}
	
	
	 private VMStatus stateParse(String state) {
	        VMStatus returnState = null;
	        if (state.equals("0")) {
	            returnState = VMStatus.PREPARE;
	        }
	        if (state.equals("1")) {
	            returnState = VMStatus.CREATING;
	        }
	        if (state.equals("2")) {
	            returnState = VMStatus.RUNNING;
	        }
	        if (state.equals("3")) {
	            returnState = VMStatus.DELETED;
	        }
	        if (state.equals("4")) {
	            returnState = VMStatus.STOP;
	        }
	        if (state.equals("6")) {
	            returnState = VMStatus.PAUSE;
	        }
	        if (state.equals("9")) {
	            returnState = VMStatus.OPERATE_FAIL;
	        }
	        if (state.equals("14")) {
	            returnState = VMStatus.SENDERROR;
	        }
	        if (state.equals("15")) {
	            returnState = VMStatus.STATUSERROR;
	        }
	        if (state.equals("16")) {
	            returnState = VMStatus.PROCESSING;
	        }
	        return returnState;
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
	public List<ResultInfos> getApplist() {
		return applist;
	}
	public void setApplist(List<ResultInfos> applist) {
		this.applist = applist;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public List<ResultInfos> getResultList() {
		return resultList;
	}
	public void setResultList(List<ResultInfos> resultList) {
		this.resultList = resultList;
	}
	public String getSubnetstart() {
		return subnetstart;
	}
	public void setSubnetstart(String subnetstart) {
		this.subnetstart = subnetstart;
	}
	public String getSubnetend() {
		return subnetend;
	}
	public void setSubnetend(String subnetend) {
		this.subnetend = subnetend;
	}
	public ResultInfos getSub() {
		return sub;
	}
	public void setSub(ResultInfos sub) {
		this.sub = sub;
	}
	public QueryVMState getQueryVMState() {
		return queryVMState;
	}
	public void setQueryVMState(QueryVMState queryVMState) {
		this.queryVMState = queryVMState;
	}

	public ResultInfos getStatusinfo() {
		return statusinfo;
	}

	public void setStatusinfo(ResultInfos statusinfo) {
		this.statusinfo = statusinfo;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getResPoolPartId() {
		return resPoolPartId;
	}

	public void setResPoolPartId(String resPoolPartId) {
		this.resPoolPartId = resPoolPartId;
	}
	
	

}
