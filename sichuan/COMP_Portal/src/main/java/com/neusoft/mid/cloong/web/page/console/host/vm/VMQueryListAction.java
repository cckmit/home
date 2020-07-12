/*******************************************************************************
 * @(#)VMQueryListAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.BatchVMInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMResultInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询当前登录用户所属业务下，虚拟机列表信息
 * 
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-8 下午2:04:45
 */
public class VMQueryListAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = -5471800875775849067L;

	private static LogService logger = LogService
			.getLogger(VMQueryListAction.class);

	/**
	 * 虚拟机实例信息
	 */
	private List<VMResultInfo> vmResultInfos;

	/**
	 * 虚拟机实例信息
	 */
	private Map<String, Object> vmResultInfoMap;

	/**
	 * 浏览虚拟机列表状态（""全部 "1"创建中 "2"运行中）
	 */
	private String optState;

	/**
	 * 虚拟机名称
	 */
	private String vmName;

	/**
	 * 虚拟机ID
	 */
	private String vmId;

	/**
	 * 按私网IP查询
	 */
	private String privateIp;

	/**
	 * 按操作系统查询
	 */
	private String isoName;

	/**
	 * 按状态查询
	 */
	private String status;

	/**
	 * 按业务查询，供业务视图使用
	 */
	private String queryBusinessId;
	
	/**
	 * 按业务查询，供业务视图使用
	 */
	private String businessName;

	/**
	 * 调用分页状态码 0为分页 非0为不分页(1) 列表需要分页 概览不需要分页
	 */
	private final String PAGECODE = "0";

	/**
	 * 分页标志位,0为分页 1为不分页 spring配置 默认分页
	 */
	private String pageFlag = "0";

	/**
	 * 调用同步翻页状态码 0为异步 非0为同步(1) 浏览需要同步 查询需要异步
	 */
	private final String SYNCCODE = "0";

	/**
	 * 同步标志位 0为异步 非0为同步(1) spring配置 默认异步可以不配置
	 */
	private String syncFlage = "0";
	
	//判断是否查询的虚拟机列表为显示弹出框用
	private String queryVmDialog;
	
	private List<BatchVMInfo> batchVMInfoList;
	
	private BatchVMInfo batchVmInfo = new BatchVMInfo();
	
	private String vmBatchModifyId;
	
	private String batchVmName;
	
	private String batchVmIp;
	
	private String batchVmFlag;
	
	//用以确认来自虚拟机批量修改时隐藏未创建的虚拟机标志位
	private String fromBatchVm;
	

	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		if(null!=queryVmDialog&&queryVmDialog.indexOf("1")>-1){
			//通过反射改变PageAction中的asyncJSPageMethod值
			 // 得到私有字段  
			try {
				Field privateStringField = PageAction.class  
				        .getDeclaredField("asyncJSPageMethod");
				// 通過反射設置私有對象可以訪問  
				privateStringField.setAccessible(true);  
				// 從父類中得到對象，并強制轉換為想要得到的對象  
//				String fieldValue = (String) privateStringField.get(this);  
				// 將私有對象設置新的值  
				String str = "getVMList";  
				privateStringField.set(this, str);  
			} catch (Exception e) {
				logger.info("修改pageAction中的私有属性出错！"+e.getMessage());
			} 
	  
		}else{
			//通过反射改变PageAction中的asyncJSPageMethod值
			 // 得到私有字段  
			try {
				Field privateStringField = PageAction.class  
				        .getDeclaredField("asyncJSPageMethod");
				// 通過反射設置私有對象可以訪問  
				privateStringField.setAccessible(true);  
				// 從父類中得到對象，并強制轉換為想要得到的對象  
//				String fieldValue = (String) privateStringField.get(this);  
				// 將私有對象設置新的值  
				String str = "getPageData";  
				privateStringField.set(this, str);  
			} catch (Exception e) {
				logger.info("修改pageAction中的私有属性出错！"+e.getMessage());
			} 
		}
		
		if (null != errMsg && !"".equals(errMsg)) {
			this.addActionError(errMsg);
		}
		// session中获取用户
		UserBean user = (UserBean) ServletActionContext.getRequest()
				.getSession().getAttribute(SessionKeys.userInfo.toString());
		String userId = user.getUserId();

		VMResultInfo vmResultInfo = new VMResultInfo();

		// 按业务查询，供业务视图使用
		if (null != queryBusinessId && !queryBusinessId.isEmpty()) {
			List<String> list = new ArrayList<String>();
			list.add(queryBusinessId);
			vmResultInfo.setAppIdList(list);
		} else {
			// 资源视图，获取用户绑定的业务
			vmResultInfo.setAppIdList(user.getAppIdList());
		}

		// 订单状态不等.失效
		vmResultInfo.setEffectiveStatus("3");
		// 虚拟机状态不等.删除
		vmResultInfo.setVmStatus(VMStatus.DELETED.getCode());
		// 浏览虚拟机列表页面状态
		String optStatus = optState == null ? "" : optState.trim();
		if (!"".equals(optStatus)) {
			// vmResultInfo.setQueryStatus(optStatus);
			vmResultInfo.setStatusList(Arrays.asList(optState.split(",")));
		}
		if (null != vmName && !vmName.isEmpty()) {
			vmResultInfo.setVmName(vmName.toLowerCase());
		}
		if (null != privateIp && !privateIp.isEmpty()) {
			vmResultInfo.setPrivateIp(privateIp);
		}
		if (null != isoName && !isoName.isEmpty()) {
			vmResultInfo.setIsoName(isoName);
		}

		vmResultInfo.setVmId(vmId);
		try {
			if (PAGECODE.equals(pageFlag)) {// 虚拟机列表需要分页
				if (SYNCCODE.equals(syncFlage)) { // 查询需要异步
					if(null!=fromBatchVm&&"1".equals(fromBatchVm)){
						vmResultInfos = getPage("countVmUserExceptNotCreateCount",
								"queryVmUserListExceptNotCreate", vmResultInfo,PageTransModel.ASYNC);
					}else{
					if ("".equals(optStatus)) { // 第一次进入页面时status为空，sql不判断status
						vmResultInfos = getPage("countVmUserListAll",
								"queryVmUserListAll", vmResultInfo,
								PageTransModel.ASYNC);
					} else {
						vmResultInfos = getPage("countVmUserListStatus",
								"queryVmUserListStatus", vmResultInfo,
								PageTransModel.ASYNC);
					}
					}
				} else {// 浏览需要同步
					if(null!=fromBatchVm&&"1".equals(fromBatchVm)){
						vmResultInfos = getPage("countVmUserExceptNotCreateCount",
								"queryVmUserListExceptNotCreate", vmResultInfo);
					}else{
					if ("".equals(optStatus)) { // 第一次进入页面时status为空，sql不判断status
						vmResultInfos = getPage("countVmUserListAll",
								"queryVmUserListAll", vmResultInfo);
					} else {
						vmResultInfos = getPage("countVmUserListStatus",
								"queryVmUserListStatus", vmResultInfo);
					}
					}
				}
			} else {// 虚拟机概览不需要分页
				//虚拟机批量修改操作不查询虚拟机未创建的
				if(null!=fromBatchVm&&"1".equals(fromBatchVm)){
					vmResultInfos = (List<VMResultInfo>) ibatisDAO.getData(
							"queryVmUserListExceptNotCreate", vmResultInfo);
				}else{
					if ("".equals(optStatus)) { // 第一次进入页面时status为空，sql不判断status
						vmResultInfos = (List<VMResultInfo>) ibatisDAO.getData(
								"queryVmUserListAll", vmResultInfo);
					} else {
						vmResultInfos = (List<VMResultInfo>) ibatisDAO.getData(
								"queryVmUserListStatus", vmResultInfo);
					}
				}

			}
			fomatResultData(vmResultInfos);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vm.queryList.fail"), userId),
					e);
			this.addActionError(MessageFormat.format(
					getText("vm.queryList.fail"), userId));
			return ConstantEnum.ERROR.toString();
		}
		vmResultInfoMap = new HashMap<String, Object>();
		vmResultInfoMap.put("list", vmResultInfos);
		vmResultInfoMap.put("page", getPageBar());
		return ConstantEnum.SUCCESS.toString();
	}
	
	public String vmBatchQueryList(){
		if(null!=vmBatchModifyId&&!"".equals(vmBatchModifyId)){
			batchVmInfo.setId(vmBatchModifyId);
		}
		if(null!=batchVmName&&!"".equals(batchVmName)){
			batchVmInfo.setVmName(batchVmName);
		}
		if(null!=batchVmIp&&!"".equals(batchVmIp)){
			batchVmInfo.setVmIP(batchVmIp);
		}
		if(null!=batchVmFlag&&!"notSelect".equals(batchVmFlag)){
			batchVmInfo.setVmModifyFlag(batchVmFlag);
		}
		try {
			batchVMInfoList = getPage("getVmBatchCount","getVmBatchInfo" ,batchVmInfo);
		} catch (Exception e) {
			logger.info("查询虚拟机批量审批结果出错"+e.getMessage());
			e.printStackTrace();
			return ConstantEnum.FAILURE.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}
	
	public String vmBatchListForOverview(){
		try {
			batchVMInfoList = ibatisDAO.getData("getVmBatchInfo" ,null);
		} catch (Exception e) {
			logger.info("查询虚拟机批量审批结果出错"+e.getMessage());
			e.printStackTrace();
			return ConstantEnum.FAILURE.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}
	
	public String getQueryVmDialog() {
		return queryVmDialog;
	}

	public void setQueryVmDialog(String queryVmDialog) {
		this.queryVmDialog = queryVmDialog;
	}

	/**
	 * 格式化生效时间、到期时间、状态显示汉字
	 * 
	 * @param ls要格式化的列表
	 */
	private void fomatResultData(List<VMResultInfo> ls) {
		for (VMResultInfo vmResultInfo : ls) {
			String sbt = vmResultInfo.getEffectiveTime() == null ? ""
					: vmResultInfo.getEffectiveTime().trim();
			if (!"".equals(sbt)) {
				String effectiveTime = DateParse.parse(vmResultInfo
						.getEffectiveTime());
				vmResultInfo.setEffectiveTime(effectiveTime);

			}
			if (!"".equals(vmResultInfo.getOverTime() == null ? ""
					: vmResultInfo.getOverTime())) {
				vmResultInfo.setOverTime(DateParse.parse(vmResultInfo
						.getOverTime()));
			}
		}
	}

	public List<VMResultInfo> getVmResultInfos() {
		return vmResultInfos;
	}

	public void setOptState(String optState) {
		this.optState = optState;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getPrivateIp() {
		return privateIp;
	}

	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}

	public String getIsoName() {
		return isoName;
	}

	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	public Map<String, Object> getVmResultInfoMap() {
		return vmResultInfoMap;
	}

	public void setVmResultInfos(List<VMResultInfo> vmResultInfos) {
		this.vmResultInfos = vmResultInfos;
	}

	/**
	 * 设置syncFlage字段数据
	 * 
	 * @param syncFlage
	 *            The syncFlage to set.
	 */
	public void setSyncFlage(String syncFlage) {
		this.syncFlage = syncFlage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}

	public String getQueryBusinessId() {
		return queryBusinessId;
	}

	public void setQueryBusinessId(String queryBusinessId) {
		this.queryBusinessId = queryBusinessId;
	}

	public List<BatchVMInfo> getBatchVMInfoList() {
		return batchVMInfoList;
	}

	public void setBatchVMInfoList(List<BatchVMInfo> batchVMInfoList) {
		this.batchVMInfoList = batchVMInfoList;
	}

	public String getVmBatchModifyId() {
		return vmBatchModifyId;
	}

	public void setVmBatchModifyId(String vmBatchModifyId) {
		this.vmBatchModifyId = vmBatchModifyId;
	}

	public BatchVMInfo getBatchVmInfo() {
		return batchVmInfo;
	}

	public void setBatchVmInfo(BatchVMInfo batchVmInfo) {
		this.batchVmInfo = batchVmInfo;
	}

	public String getBatchVmName() {
		return batchVmName;
	}

	public void setBatchVmName(String batchVmName) {
		this.batchVmName = batchVmName;
	}

	public String getBatchVmIp() {
		return batchVmIp;
	}

	public void setBatchVmIp(String batchVmIp) {
		this.batchVmIp = batchVmIp;
	}

	public String getBatchVmFlag() {
		return batchVmFlag;
	}

	public void setBatchVmFlag(String batchVmFlag) {
		this.batchVmFlag = batchVmFlag;
	}

	public String getFromBatchVm() {
		return fromBatchVm;
	}

	public void setFromBatchVm(String fromBatchVm) {
		this.fromBatchVm = fromBatchVm;
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
