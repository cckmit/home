/*******************************************************************************
 * @(#)VMQueryListAction.java 2013-1-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;

import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.LoadBalanceInfo;
import com.neusoft.mid.cloong.web.page.lb.generator.TimeStampGenerator;
import com.neusoft.mid.cloong.web.page.lb.generator.TransactionIdGenerator;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询负载均衡列表
 */
public class loadBalanceListAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService.getLogger(loadBalanceListAction.class);

	private TimeStampGenerator timeGen;

	private TransactionIdGenerator tranGen;
		
	/**
	 * 序列号生成器
	 */
	private SequenceGenerator sequenceGenerator;

	private LoadBalanceInfo LBinfo = new LoadBalanceInfo();
	/**
	 * 负载均衡list
	 */
	private List<LoadBalanceInfo> LBinfoList;
	/**
	 * 负载均衡ID
	 */
	private String LBid;
	/**
	 * 负载均衡名称
	 */
	private String lbname;
	/**
	 * 负载均衡ip
	 */
	private String lbip;

	private Map<String, Object> LBinfoMap;

	private static WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();  

	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		try {
		    
		    // session中获取用户ID
	        UserBean user = getCurrentUser();
	        String userId = user.getUserId();
			// 查询负载均衡列表信息
			LBinfo.setLbname(lbname);
			LBinfo.setLbip(lbip);
			LBinfo.setAppIdList(user.getAppIdList());
			LBinfoList = getPage("Loadbalancecount", "queryLoadbalanceInfo",LBinfo, PageTransModel.ASYNC);
		} catch (Exception e) {
			logger.info("查询负载均衡信息出错！" + e.getMessage());
			e.printStackTrace();
			return ConstantEnum.FAILURE.toString();
		}
		LBinfoMap = new HashMap<String, Object>();
		LBinfoMap.put("list", LBinfoList);
		LBinfoMap.put("page", getPageBar());
		return ConstantEnum.SUCCESS.toString();
	}
	

	public LoadBalanceInfo getLBinfo() {
		return LBinfo;
	}

	public void setLBinfo(LoadBalanceInfo lBinfo) {
		LBinfo = lBinfo;
	}

	public List<LoadBalanceInfo> getLBinfoList() {
		return LBinfoList;
	}

	public void setLBinfoList(List<LoadBalanceInfo> lBinfoList) {
		LBinfoList = lBinfoList;
	}

	public String getLbname() {
		return lbname;
	}

	public void setLbname(String lbname) {
		this.lbname = lbname;
	}

	public String getLbip() {
		return lbip;
	}

	public void setLbip(String lbip) {
		this.lbip = lbip;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public SequenceGenerator getSequenceGenerator() {
		return sequenceGenerator;
	}

	public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	public Map<String, Object> getLBinfoMap() {
		return LBinfoMap;
	}

	public void setLBinfoMap(Map<String, Object> lBinfoMap) {
		LBinfoMap = lBinfoMap;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getLBid() {
		return LBid;
	}

	public void setLBid(String lBid) {
		LBid = lBid;
	}

	public TimeStampGenerator getTimeGen() {
		return timeGen;
	}

	public void setTimeGen(TimeStampGenerator timeGen) {
		this.timeGen = timeGen;
	}

	public TransactionIdGenerator getTranGen() {
		return tranGen;
	}

	public void setTranGen(TransactionIdGenerator tranGen) {
		this.tranGen = tranGen;
	}

	public static WebApplicationContext getContext() {
		return context;
	}

	public static void setContext(WebApplicationContext context) {
		loadBalanceListAction.context = context;
	}

}