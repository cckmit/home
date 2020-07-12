/*******************************************************************************
 * @(#)VMQueryListAction.java 2013-1-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.LBobjInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.LoadBalanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.NetInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.Authorization;
import com.neusoft.mid.cloong.web.page.lb.SOAP.common.v1.holders.ResponseHolder;
import com.neusoft.mid.cloong.web.page.lb.SOAP.lb.v1.IpPortInfo;
import com.neusoft.mid.cloong.web.page.lb.generator.TimeStampGenerator;
import com.neusoft.mid.cloong.web.page.lb.generator.TransactionIdGenerator;
import com.neusoft.mid.cloong.web.page.lb.v1.interfaces.LBServicePortProxy;
import com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjReq;
import com.neusoft.mid.cloong.web.page.lb.v1.local.AddLoadBalanceObjResp;
import com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceReq;
import com.neusoft.mid.cloong.web.page.lb.v1.local.CancelLoadBalanceResp;
import com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjReq;
import com.neusoft.mid.cloong.web.page.lb.v1.local.DelLoadBalanceObjResp;
import com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceReq;
import com.neusoft.mid.cloong.web.page.lb.v1.local.ModifyLoadBalanceResp;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询一组虚拟机列表状态 返回json
 * 
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-9 下午03:18:21
 */
public class loadBalanceAction extends ResourceManagementBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService
			.getLogger(loadBalanceAction.class);

	private TimeStampGenerator timeGen;

	private TransactionIdGenerator tranGen;
	
	private String Url;
	
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
	/**
	 * 负载均衡策略
	 */
	private String strategy;
	/**
	 * 负载均衡端口
	 */
	private String lbport;
	/**
	 * 负载均衡方式
	 */
	private String LBType;

	private String appId;

	private String Protocal;

	private String respoolId;

	private String respoolPartId;

	private String msg;

	private String flag;

	private List<LBobjInfo> objInfolist;

	private LBobjInfo objInfo = new LBobjInfo();;

	private String objname;

	private String vlanId;

	private String ipSegmentId;

	private NetInfo netinfo;

	private String throughput;

	private String connectNum;

	private String newConnectNum;

	private Map<String, Object> LBinfoMap;

	private String mes;

	private String result;
	
	private String instanceid;
	
    private String queryBusinessId;

	private String businessName;
	
	 private String userId;
	 
	 private String ipType;
	
	private static WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();  

	/**
	 * 时间戳 yyyyMMddHHmmss
	 */
	private static final DateTimeFormatter TIMESTAMP = DateTimeFormat
			.forPattern("yyyyMMddHHmmss");

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
	
	public String queryLoadBalanceForOverview() throws SQLException{
		LBinfoList=ibatisDAO.getData("queryLoadbalanceInfo", null);
		return ConstantEnum.SUCCESS.toString();
	}

	public String LBapply() {
		 UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
	                .getAttribute(SessionKeys.userInfo.toString()));
	         userId = user.getUserId();
		return ConstantEnum.SUCCESS.toString();
	}
    //申请负载均衡
	public String applyloadbalance() {
		// session中获取用户ID
		UserBean user = ((UserBean) ServletActionContext.getRequest()
				.getSession().getAttribute(SessionKeys.userInfo.toString()));
		// 申请负载均衡信息
		LBinfo.setLbip(lbip);
		LBinfo.setStatus("0");
		LBinfo.setStrategy(strategy);
		LBinfo.setLbport(lbport);
		LBinfo.setCreateuser(user.getUserId());
		LBinfo.setCreatetime(DateParse.generateDateFormatyyyyMMddHHmmss());
		LBinfo.setAppid(appId);
		LBinfo.setProtocal(Protocal);
		LBinfo.setRespoolId(respoolId);
		LBinfo.setRespoolPartId(respoolPartId);
		LBinfo.setLBType(LBType);
		LBinfo.setLbname(lbname);
		LBinfo.setVlanId(vlanId);
		LBinfo.setIPSEGMENT_ID(ipSegmentId);
		LBinfo.setThroughput(throughput);
		LBinfo.setNewConnectNum(newConnectNum);
		LBinfo.setKbpsConnectNum(connectNum);
		LBinfo.setIpType(ipType);
		// 插入订单详情表
		OrderInfo orderInfo = new OrderInfo();
		// LoadBalanceInfo LB = new LoadBalanceInfo();
		String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.LB
				.toString());
		String parentId = sequenceGenerator
				.generatorOrderId(ResourceTypeEnum.LB.getParentCode());
		String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.LB
				.toString());
		LBinfo.setLBid(caseId);
		orderInfo.setAppId(appId);
		orderInfo.setResPoolId(respoolId);
		orderInfo.setOrderId(orderId);
		orderInfo.setParentId(parentId);
		orderInfo.setCaseId(caseId);
		orderInfo.setStatus("0");
		orderInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
		orderInfo.setCreateUser(user.getUserId());
		orderInfo.setUpdateUser(user.getUserId());
		orderInfo.setCaseType("14");// 17代表负载均衡服务
		try {
			List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
			// 更新数据库
			updateBatchVO.add(new BatchVO("ADD", "insertLbinfo", LBinfo));
			// 更新数据库
			updateBatchVO.add(new BatchVO("ADD", "createLBOrder", orderInfo));
			ibatisDAO.updateBatchData(updateBatchVO);
			msg = "负载均衡服务申请已提交！";
		} catch (Exception e) {
			logger.info("申请负载均衡出错！" + e.getMessage());
			e.printStackTrace();
			result = "申请负载均衡出错！";
			return ConstantEnum.FAILURE.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	public String check() {
		try {
			LBinfo.setLbname(lbname);
			LBinfo = (LoadBalanceInfo) ibatisDAO.getSingleRecord("checklbname",
					LBinfo);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("校验负载均衡名称出错" + e.getMessage());
		}
		return ConstantEnum.SUCCESS.toString();
	}

	public String addobjinfo() {
		logger.info("添加对象信息开始");
		try {
			Authorization header = new Authorization();
			String timeStamp = timeGen.generateTimeStamp();
			header.setTimestamp(timeStamp);
			// 页面传参resPoolId，resPoolPartId
			header.setTransactionID(tranGen.generateTransactionId(respoolId,timeStamp));
			header.setZoneID(respoolPartId);
			ResponseHolder resp = new ResponseHolder();
			AddLoadBalanceObjReq addLoadBalanceObjinfo=new AddLoadBalanceObjReq();
			addLoadBalanceObjinfo.setLBID(instanceid);
			IpPortInfo[] ipPortInfo=new IpPortInfo[1];
			ipPortInfo[0] = new IpPortInfo();
			ipPortInfo[0].setIP(objInfo.getHostip());
			ipPortInfo[0].setPort(Integer.valueOf(objInfo.getHostport()));
			addLoadBalanceObjinfo.setIpPortInfo(ipPortInfo);
			
			LBServicePortProxy lbServicePortProxy= (LBServicePortProxy)getContext().getBean("lbServicePortProxy");
			logger.info("url:="+Url);
			URL address = new URL(Url);
			lbServicePortProxy.setPorxyUrl(address);
			AddLoadBalanceObjResp addlbobjResp=new AddLoadBalanceObjResp();
			addlbobjResp=lbServicePortProxy.addLoadBalanceObj(addLoadBalanceObjinfo, header, resp);
			if("成功".equals(addlbobjResp.getFaultString())){
				try {
					UserBean user = ((UserBean) ServletActionContext.getRequest()
							.getSession().getAttribute(SessionKeys.userInfo.toString()));
					objInfo.setCreate_time(DateParse.generateDateFormatyyyyMMddHHmmss());
					objInfo.setCreate_user(user.getUserId());
					objInfo.setStatus("0");
					objInfo.setIpType(ipType);
					ibatisDAO.insertData("LBaddobjinfo", objInfo);
				} catch (SQLException e) {
					e.printStackTrace();
					logger.info("添加对象信息出错" + e.getMessage());
					mes = "添加对象信息出错";
					result = "error";
					return ConstantEnum.SUCCESS.toString();
				}
				result = "success";
				mes = "添加对象信息成功";
				return ConstantEnum.SUCCESS.toString();
			}
			else{
				logger.info("添加对象信息出错" + addlbobjResp.getFaultString());
				mes = "添加对象信息出错";
				result = "error";
				return ConstantEnum.SUCCESS.toString();
			}
			} catch (RemoteException e1) {
				e1.printStackTrace();
				logger.info("添加对象信息出错" +e1.getMessage());
				mes = "添加对象信息出错";
				result = "error";
				return ConstantEnum.SUCCESS.toString();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
				logger.info("添加对象信息出错" +e1.getMessage());
				mes = "添加对象信息出错";
				result = "error";
				return ConstantEnum.SUCCESS.toString();				
			}
		
	}

	public String LBdetailinfo() {
		logger.info("查询负载均衡详细信息开始");
		try {
			LBinfo.setLBid(LBid);
			LBinfo = (LoadBalanceInfo) ibatisDAO.getSingleRecord(
					"LBdetailinfo", LBinfo);
			netinfo = (NetInfo) ibatisDAO.getSingleRecord("LBdetailnetinfo",
					LBinfo);
			objInfolist = ibatisDAO.getData("LBobjipinfo", LBinfo);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("查询负载均衡详细信息出错" + e.getMessage());
		}

		return ConstantEnum.SUCCESS.toString();
	}

	public String updateLBinfos() {
		logger.info("修改负载均衡服务开始");
		try {
			ModifyLoadBalanceReq modifyLoadBalanceReq = new ModifyLoadBalanceReq();
			ModifyLoadBalanceResp modifyLoadBalanceResp = new ModifyLoadBalanceResp();
			// 页面传参
			modifyLoadBalanceReq.setLBID(LBinfo.getInstanceid());
			modifyLoadBalanceReq.setLBStrategy(Integer.valueOf(LBinfo.getStrategy())); 

			Authorization header = new Authorization();
			String timeStamp = timeGen.generateTimeStamp();
			header.setTimestamp(timeStamp);
			// 页面传参resPoolId，resPoolPartId
			header.setTransactionID(tranGen.generateTransactionId(LBinfo.getRespoolId(),
					timeStamp));
			header.setZoneID(LBinfo.getRespoolPartId());
			ResponseHolder resp = new ResponseHolder();
			LBServicePortProxy lbServicePortProxy= (LBServicePortProxy)getContext().getBean("lbServicePortProxy");  
			modifyLoadBalanceResp = lbServicePortProxy.modifyLoadBalance(
						modifyLoadBalanceReq, header, resp);
			if("成功".equals(modifyLoadBalanceResp.getFaultString())){
				try{
					ibatisDAO.updateData("updateLBinfo", LBinfo);
				} catch (Exception e) {
							e.printStackTrace();
							logger.info("修改负载均衡插入数据库出错" + e.getMessage());
							mes = "修改负载均衡详细信息出错";
							result = "error";
							return ConstantEnum.SUCCESS.toString();
						}
				}else{
					logger.info("修改负载均衡详细信息出错"+modifyLoadBalanceResp.getFaultString());
					mes = "修改负载均衡详细信息出错";
					result = "error";
					return ConstantEnum.SUCCESS.toString();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.info("修改负载均衡详细信息出错" + e1.getMessage());
				mes = "修改负载均衡详细信息出错";
				result = "error";
				return ConstantEnum.SUCCESS.toString();
			}
			
		result = "success";
		mes = "修改负载均衡详细信息成功";
		return ConstantEnum.SUCCESS.toString();
	}

	public String delobjinfo() {
		logger.info("删除负载均衡对象开始");
		try {
		Authorization header = new Authorization();
		String timeStamp = timeGen.generateTimeStamp();
		header.setTimestamp(timeStamp);
		// 页面传参resPoolId，resPoolPartId
		header.setTransactionID(tranGen.generateTransactionId(respoolId,
				timeStamp));
		header.setZoneID(respoolPartId);
		ResponseHolder resp = new ResponseHolder();
		LBServicePortProxy lbServicePortProxy= (LBServicePortProxy)getContext().getBean("lbServicePortProxy");  
		DelLoadBalanceObjReq dellbobjReq=new DelLoadBalanceObjReq(); 
		IpPortInfo[] iPs=new IpPortInfo[1];
		iPs[0]=new IpPortInfo();
		iPs[0].setIP(objInfo.getHostip());
		iPs[0].setPort(Integer.valueOf(objInfo.getHostport()));
		dellbobjReq.setLBID(instanceid);
		dellbobjReq.setIPs(iPs);
		DelLoadBalanceObjResp dellbobjResq=new DelLoadBalanceObjResp();
		dellbobjResq=lbServicePortProxy.delLoadBalanceObj(dellbobjReq, header, resp);
		if("成功".equals(dellbobjResq.getFaultString())){
			try {
				objInfo.setStatus("1");
				ibatisDAO.updateData("delLBobjinfo", objInfo);
				
			} catch (SQLException e) {
				e.printStackTrace();
				logger.info("删除负载均衡对象出错" + e.getMessage());
				mes = "删除负载均衡对象失败";
				result = "error";
				return ConstantEnum.SUCCESS.toString();
			}
		}else{
			logger.info("删除负载均衡对象出错" +dellbobjResq.getFaultString());
			mes = "删除负载均衡对象失败";
			result = "error";
			return ConstantEnum.SUCCESS.toString();
		}
		} catch (RemoteException e1) {
			e1.printStackTrace();
			logger.info("删除负载均衡对象出错" +e1.getMessage());
			mes = "删除负载均衡对象失败";
			result = "error";
			return ConstantEnum.SUCCESS.toString();
		}
		result = "success";
		mes = "删除负载均衡对象成功";
		return ConstantEnum.SUCCESS.toString();
	}

	public String delLBinfos() {
		logger.info("删除负载均衡服务开始");
		try {
			CancelLoadBalanceReq cancelLoadBalanceReq = new CancelLoadBalanceReq();
			CancelLoadBalanceResp cancelLoadBalanceResp = new CancelLoadBalanceResp();
			// 页面传参instanceId
			cancelLoadBalanceReq.setLBID(LBinfo.getInstanceid());

			Authorization header = new Authorization();

			String timeStamp = timeGen.generateTimeStamp();
			header.setTimestamp(timeStamp);
			// 页面传参resPoolId，resPoolPartId
			header.setTransactionID(tranGen.generateTransactionId(
					LBinfo.getRespoolId(), timeStamp));
			header.setZoneID(LBinfo.getRespoolPartId());

			ResponseHolder resp = new ResponseHolder();

			LBServicePortProxy lbServicePortProxy= (LBServicePortProxy)getContext().getBean("lbServicePortProxy"); 
			cancelLoadBalanceResp = lbServicePortProxy.cancelLoadBalance(
					cancelLoadBalanceReq, header, resp);
			if ("成功".equals(cancelLoadBalanceResp.getFaultString())) {
				LBinfo.setStatus("6");
				ibatisDAO.updateData("delLBinfo", LBinfo);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setStatus("6");
				orderInfo.setCaseId(LBinfo.getLBid());
				ibatisDAO.updateData("delorderLB", orderInfo);
			} else {
				logger.info("删除负载均衡服务出错");
				mes = "删除负载均衡服务失败" + cancelLoadBalanceResp.getFaultString();
				result = "error";
				return ConstantEnum.FAILURE.toString();
			}
		} catch (RemoteException e) {
			logger.info("删除负载均衡服务出错" + e.getMessage());
			mes = "删除负载均衡服务失败";
			result = "error";
			return ConstantEnum.FAILURE.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("删除负载均衡服务插入数据库出错" + e.getMessage());
			mes = "删除负载均衡服务失败";
			result = "error";
			return ConstantEnum.FAILURE.toString();
		}
		result = "success";
		mes = "删除负载均衡服务成功";
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

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getLbport() {
		return lbport;
	}

	public void setLbport(String lbport) {
		this.lbport = lbport;
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

	public String getLBType() {
		return LBType;
	}

	public void setLBType(String lBType) {
		LBType = lBType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getProtocal() {
		return Protocal;
	}

	public void setProtocal(String protocal) {
		Protocal = protocal;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLBid() {
		return LBid;
	}

	public void setLBid(String lBid) {
		LBid = lBid;
	}

	public List<LBobjInfo> getObjInfolist() {
		return objInfolist;
	}

	public void setObjInfolist(List<LBobjInfo> objInfolist) {
		this.objInfolist = objInfolist;
	}

	public LBobjInfo getObjInfo() {
		return objInfo;
	}

	public void setObjInfo(LBobjInfo objInfo) {
		this.objInfo = objInfo;
	}

	public String getObjname() {
		return objname;
	}

	public void setObjname(String objname) {
		this.objname = objname;
	}

	public String getVlanId() {
		return vlanId;
	}

	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}

	public String getIpSegmentId() {
		return ipSegmentId;
	}

	public void setIpSegmentId(String ipSegmentId) {
		this.ipSegmentId = ipSegmentId;
	}

	public NetInfo getNetinfo() {
		return netinfo;
	}

	public void setNetinfo(NetInfo netinfo) {
		this.netinfo = netinfo;
	}

	public String getThroughput() {
		return throughput;
	}

	public void setThroughput(String throughput) {
		this.throughput = throughput;
	}

	public String getConnectNum() {
		return connectNum;
	}

	public void setConnectNum(String connectNum) {
		this.connectNum = connectNum;
	}

	public String getNewConnectNum() {
		return newConnectNum;
	}

	public void setNewConnectNum(String newConnectNum) {
		this.newConnectNum = newConnectNum;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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
		loadBalanceAction.context = context;
	}

	public String getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
	
	public String getIpType() {
		return ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

}