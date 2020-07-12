/*******************************************************************************
 * @(#)PMQueryListAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.pm;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMResultInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询当前登录用户所属业务下，物理机列表信息
 * 
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午06:58:42
 */
public class PMQueryListAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = -5471800875775849067L;

	private static LogService logger = LogService
			.getLogger(PMQueryListAction.class);

	/**
	 * 物理机实例信息
	 */
	private List<PMResultInfo> pmResultInfos;

	/**
	 * 物理机实例信息
	 */
	private Map<String, Object> pmResultInfoMap;

	/**
	 * 浏览物理机列表状态（""全部 "1"创建中 "2"运行中）
	 */
	private String optState;

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

	/**
	 * 业务名称ID
	 */
	private String appId;

	/**
	 * 业务名称
	 */
	private String appName;
	
	/**
	 * 物理机名称
	 */
	private String pmName;

	/**
	 * 物理机ID
	 */
	private String pmId;

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
	 * 判断是否来自创建物理机后的查询action
	 */
	private String fromPmApplyAction;

	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		if (null != errMsg && !"".equals(errMsg)) {
			this.addActionError(errMsg);
		}
		// session中获取用户ID
		UserBean user = (UserBean) ServletActionContext.getRequest()
				.getSession().getAttribute(SessionKeys.userInfo.toString());
		String userId = user.getUserId();
		PMResultInfo pmResultInfo = new PMResultInfo();
		// 资源视图，获取用户绑定的业务
//		pmResultInfo.setAppIdList(user.getAppIdList());
		
		// 按业务查询，供业务视图使用---zhangge
		if (null != queryBusinessId && !queryBusinessId.isEmpty()) {
			List<String> list = new ArrayList<String>();
			list.add(queryBusinessId);
			pmResultInfo.setAppIdList(list);
		} else {
			// 资源视图，获取用户绑定的业务
			pmResultInfo.setAppIdList(user.getAppIdList());
		}

		
		// 业务视图，通过appId获取app信息---zhangge
		/*if (appId != null) {
			try {
				// 通过appId 查相关app信息
				BusinessInfo app = (BusinessInfo) ibatisDAO.getSingleRecord(
						"queryBusinessByAppId", appId);
				appName = app.getName();
			} catch (SQLException e1) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
						"查询app相关信息异常", e1);
			}
		}
		if (appId != null) {
			pmResultInfo.setAppId(appId);
		}*/
		// 订单状态不等.失效
		pmResultInfo.setEffectiveStatus("3");
		// 物理机状态不等.删除
		pmResultInfo.setPmStatus(PMStatus.DELETED.getCode());
		// 浏览物理机列表页面状态
		String optStatus = optState == null ? "" : optState.trim();
		if (!"".equals(optStatus)) {
			// pmResultInfo.setQueryStatus(optStatus);
			pmResultInfo.setStatusList(Arrays.asList(optState.split(",")));
		}
		if (null != pmName && !pmName.isEmpty()) {
			pmResultInfo.setPmName(pmName.toLowerCase());
		}
		if (null != privateIp && !privateIp.isEmpty()) {
			pmResultInfo.setIp(privateIp);
		}
		if (null != isoName && !isoName.isEmpty()) {
			pmResultInfo.setIsoName(isoName);
		}
		pmResultInfo.setPmId(pmId);
		try {
			if (PAGECODE.equals(pageFlag)) {// 虚拟机列表需要分页
				/*if ("".equals(optStatus)) {
					 if(null!=fromPmApplyAction&&!"".equals(fromPmApplyAction)){
					      //若为创建物理机以后链至此处的action，则不按appIdlist查询
					        pmResultInfo.setAppIdList(null);
					        pmResultInfo.setAppId(null);
					    }
					pmResultInfos = getPage("countPmUserListAll",
							"queryPmUserListAll", pmResultInfo);
				} else {
					pmResultInfos = getPage("countPmUserListStatus",
							"queryPmUserListStatus", pmResultInfos);
				}*/
				if (SYNCCODE.equals(syncFlage)) { // 查询需要异步
					if ("".equals(optStatus)) { // 第一次进入页面时status为空，sql不判断status
						pmResultInfos = getPage("countPmUserListAll",
								"queryPmUserListAll", pmResultInfo,
								PageTransModel.ASYNC);
					} else {
						pmResultInfos = getPage("countPmUserListStatus",
								"queryPmUserListStatus", pmResultInfo,
								PageTransModel.ASYNC);
					}
				} else {// 浏览需要同步
					if ("".equals(optStatus)) { // 第一次进入页面时status为空，sql不判断status
						pmResultInfos = getPage("countPmUserListAll",
								"queryPmUserListAll", pmResultInfo);
					} else {
						pmResultInfos = getPage("countPmUserListStatus",
								"queryPmUserListStatus", pmResultInfo);
					}
				}
			} else {// 虚拟机概览不需要分页
				if ("".equals(optStatus)) { // 第一次进入页面时status为空，sql不判断status
					pmResultInfos = (List<PMResultInfo>) ibatisDAO.getData(
							"queryPmUserListAll", pmResultInfo);
				} else {
					pmResultInfos = (List<PMResultInfo>) ibatisDAO.getData(
							"queryPmUserListStatus", pmResultInfo);
				}
			}
			fomatResultData(pmResultInfos);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("pm.queryList.fail"), userId),
					e);
			this.addActionError(MessageFormat.format(
					getText("pm.queryList.fail"), userId));
			return ConstantEnum.ERROR.toString();
		}
		if (logger.isDebugEnable()) {
			StringBuilder sb = new StringBuilder();
			sb.append("租户：").append(userId);
			sb.append("物理机列表长度为:");
			sb.append("lenght:").append(pmResultInfos.size());
			logger.debug(sb.toString());
		}
		pmResultInfoMap = new HashMap<String, Object>();
		pmResultInfoMap.put("list", pmResultInfos);
		pmResultInfoMap.put("page", getPageBar());
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 格式化生效时间、到期时间、状态显示汉字
	 * 
	 * @param ls要格式化的列表
	 */
	private void fomatResultData(List<PMResultInfo> ls) {
		for (PMResultInfo pmResultInfo : ls) {
			String sbt = pmResultInfo.getEffectiveTime() == null ? ""
					: pmResultInfo.getEffectiveTime().trim();
			if (!"".equals(sbt)) {
				String effectiveTime = DateParse.parse(pmResultInfo
						.getEffectiveTime());
				pmResultInfo.setEffectiveTime(effectiveTime);

			}
			if (!"".equals(pmResultInfo.getOverTime() == null ? ""
					: pmResultInfo.getOverTime())) {
				pmResultInfo.setOverTime(DateParse.parse(pmResultInfo
						.getOverTime()));
			}
		}
	}

	public List<PMResultInfo> getPmResultInfos() {
		return pmResultInfos;
	}

	public void setOptState(String optState) {
		this.optState = optState;
	}

	public Map<String, Object> getPmResultInfoMap() {
		return pmResultInfoMap;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getQueryBusinessId() {
		return queryBusinessId;
	}

	public void setQueryBusinessId(String queryBusinessId) {
		this.queryBusinessId = queryBusinessId;
	}

	public String getFromPmApplyAction() {
		return fromPmApplyAction;
	}

	public void setFromPmApplyAction(String fromPmApplyAction) {
		this.fromPmApplyAction = fromPmApplyAction;
	}

	public String getSyncFlage() {
		return syncFlage;
	}

	public void setSyncFlage(String syncFlage) {
		this.syncFlage = syncFlage;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
