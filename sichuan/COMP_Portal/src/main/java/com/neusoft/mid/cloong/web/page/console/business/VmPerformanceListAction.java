package com.neusoft.mid.cloong.web.page.console.business;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.info.VmPerformanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务视图查询虚拟机性能统计信息
 */
public class VmPerformanceListAction extends PageAction {

    private static final long serialVersionUID = -3256721028749119063L;

    private static LogService logger = LogService.getLogger(VmPerformanceListAction.class);

    /**
     * 业务视图,页面上传来的业务id
     */
    private String queryBusinessId;

    /**
     * 业务名称
     */
    private String businessName;
    
    /**
     * 虚拟机设备列表
     */
    private List<VmPerformanceInfo> vmDeviceInfoList = null;
    
    /**
     * 企业客户ID
     */
    private String appId;
      
    /**
     * 虚拟机性能初始化列表
     */
    private List<VmPerformanceInfo> vmPerformanceList = null;
    
    /**
     * SQL查询条件
     */
    private VmPerformanceInfo searchParam = new VmPerformanceInfo();
    
    /**
     * 虚拟机性能查询列表
     */
    private List<VmPerformanceInfo> vmPerformanceSearchList = null;
    
    /**
     * 虚拟机性能初始化数据
     */
    private Map<String, Object> vmPerformanceLists;
    
    /**
     * 虚拟机性能列表查询数据
     */
    private Map<String, Object> vmPerformanceSearchLists;
    
    private final String SYNCCODE = "0";
    
    private String syncFlage = "0";
    
    /**
     * 查询虚拟机性能列表条件(起始/终止时间、虚拟机IP)
     */
    private String startDate;
    private String endDate;
    private String vmIp;
    
    @SuppressWarnings("unchecked")   
    public String execute() {
    	
        // session中获取用户ID
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
        String userId = user.getUserId();
        logger.info("当前登陆的用户名是："+userId);
    	
    	if("".equals(vmIp) || null == vmIp){   // 初始化数据查询
            VmPerformanceInfo paramDevice = new VmPerformanceInfo();
            paramDevice.setAppId(appId);
            try {          
            	vmDeviceInfoList = this.ibatisDAO.getData("getVmDeviceList", paramDevice);
            	if(vmDeviceInfoList==null){
					vmDeviceInfoList = this.ibatisDAO.getData("getVm2DeviceList", paramDevice);
				}
            	searchParam = vmDeviceInfoList.get(0);
            	logger.info("确认初始化设备信息是否为空"+searchParam.toString());
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                        MessageFormat.format(getText("vmPerformance.queryDeviceList.fail"), userId), e);
                this.addActionError(MessageFormat.format(getText("vmPerformance.queryDeviceList.fail"), userId));
                return ConstantEnum.ERROR.toString();
            }
            TimeInit(searchParam);
            try {          
            	  if (SYNCCODE.equals(syncFlage)){
            		  vmPerformanceList = getPage("getVmPerformanceListCount", "getVmPerformanceList", searchParam,
                              PageTransModel.ASYNC);
            		  if(vmPerformanceList==null){
						  vmPerformanceList = getPage("getVm2PerformanceListCount", "getVm2PerformanceList", searchParam,
								  PageTransModel.ASYNC);
					  }
            	  }else{
            		  vmPerformanceList = getPage("getVmPerformanceListCount", "getVmPerformanceList", searchParam,
                              PageTransModel.ASYNC);
					  if(vmPerformanceList==null){
						  vmPerformanceList = getPage("getVm2PerformanceListCount", "getVm2PerformanceList", searchParam,
								  PageTransModel.ASYNC);
					  }
            	  }            	
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                        MessageFormat.format(getText("vmPerformance.queryPerformanceList.fail"), userId), e);
                this.addActionError(MessageFormat.format(getText("vmPerformance.queryPerformanceList.fail"), userId));
                return ConstantEnum.ERROR.toString();
            }
            if (vmPerformanceList.size() == 0) {
                vmPerformanceLists = new HashMap<String, Object>();
                vmPerformanceLists.put("list", vmPerformanceList);
                vmPerformanceLists.put("page", getPageBar());
    			logger.info("获取虚拟机性能数据列表成功");
                return ConstantEnum.SUCCESS.toString();
            }
            
//            换算CPU使用率和时间
            DecimalFormat df2 = new DecimalFormat("0.00");
            for(int i=0;i<vmPerformanceList.size();i++){
            	if(null != vmPerformanceList.get(i).getCpuIdle() && !"".equals(vmPerformanceList.get(i).getCpuIdle())){
            		Float cpus = 100-Float.parseFloat(vmPerformanceList.get(i).getCpuIdle());
            		String cpu = df2.format(cpus);
            		vmPerformanceList.get(i).setCpuUtilization(String.valueOf(cpu));
            	}            	
            	if(null != vmPerformanceList.get(i).getBytesIn() && !"".equals(vmPerformanceList.get(i).getBytesIn())){
            		String [] ins1 = vmPerformanceList.get(i).getBytesIn().split("\\=");
            		String [] ins2 = ins1[1].split("\\^");
            		vmPerformanceList.get(i).setBytesIn(ins2[0]);
            	}
            	if(null != vmPerformanceList.get(i).getBytesOut() && !"".equals(vmPerformanceList.get(i).getBytesOut())){
            		String [] outs1 = vmPerformanceList.get(i).getBytesOut().split("\\=");
            		String [] outs2 = outs1[1].split("\\^");
            		vmPerformanceList.get(i).setBytesOut(outs2[0]);
            	}            		
            	String[] times = vmPerformanceList.get(i).getCreateTime().split("\\.");
            	String time = times[0];
            	vmPerformanceList.get(i).setCreateTime(time);
            }         
            vmPerformanceLists = new HashMap<String, Object>();
            vmPerformanceLists.put("list", vmPerformanceList);
            vmPerformanceLists.put("page", getPageBar());
            logger.info("获取虚拟机性能数据列表成功");           
    	}else{   // 依据页面查询条件获取数据
    	      if("".equals(startDate) || "".equals(endDate) || ", ".equals(startDate) || ", ".equals(endDate)){
    	    	  TimeInit(searchParam); 
    	      }
    	      if(null != startDate && !"".equals(startDate)){
    	    	  searchParam.setStartDate(startDate);
    	      }
    	      if(null != endDate && !"".equals(endDate)){
    	    	  searchParam.setEndDate(endDate);
    	      }
    	      if(null != vmIp && !"".equals(vmIp)){    	    	  
    	    	  String[] pas = vmIp.split(",");   	    	 
    	    	  searchParam.setVmIp(pas[0]);
    	      }
    	      try {          
    	    	  if (SYNCCODE.equals(syncFlage)){
    	    		  vmPerformanceSearchList = getPage("getVmPerformanceListCount", "getVmPerformanceList", searchParam,
    	                      PageTransModel.ASYNC);
					  if(vmPerformanceSearchList==null){
						  vmPerformanceSearchList = getPage("getVm2PerformanceListCount", "getVm2PerformanceList", searchParam,
								  PageTransModel.ASYNC);
					  }
    	    	  }else{
    	    		  vmPerformanceSearchList = getPage("getVmPerformanceListCount", "getVmPerformanceList", searchParam);
					  if(vmPerformanceSearchList==null){
						  vmPerformanceSearchList = getPage("getVm2PerformanceListCount", "getVm2PerformanceList", searchParam);
					  }
    	    	  }   
    	      } catch (Exception e) {
    	          logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
    	                  MessageFormat.format(getText("vmPerformance.queryPerformancesearchList.fail"), userId), e);
    	          this.addActionError(MessageFormat.format(getText("vmPerformance.queryPerformancesearchList.fail"), userId));
    	          return ConstantEnum.ERROR.toString();
    	      }
    	      if (vmPerformanceSearchList.size() == 0) {
    	          logger.info("ID为[" + userId + "]用户没有查询到符合条件的任何订单");
    	          this.addActionMessage(getText("user.order.query.null"));
    	          vmPerformanceSearchLists = new HashMap<String, Object>();
    	          vmPerformanceSearchLists.put("list", vmPerformanceSearchList);
    	          vmPerformanceSearchLists.put("page", getPageBar());
    	          return ConstantEnum.SUCCESS.toString();
    	      }
    	      
//            换算CPU使用率、时间、网络输入/输出量
    	    DecimalFormat df2 = new DecimalFormat("0.00");
            for(int i=0;i<vmPerformanceSearchList.size();i++){
            	if(null != vmPerformanceSearchList.get(i).getCpuIdle() && !"".equals(vmPerformanceSearchList.get(i).getCpuIdle())){
            		Float cpus = 100-Float.parseFloat(vmPerformanceSearchList.get(i).getCpuIdle());
            		String cpu = df2.format(cpus);
            		vmPerformanceSearchList.get(i).setCpuUtilization(String.valueOf(cpu));
            	}
            	
            	if(null != vmPerformanceSearchList.get(i).getBytesIn() && !"".equals(vmPerformanceSearchList.get(i).getBytesIn())){
            		String [] ins1 = vmPerformanceSearchList.get(i).getBytesIn().split("\\=");
            		String [] ins2 = ins1[1].split("\\^");
            		vmPerformanceSearchList.get(i).setBytesIn(ins2[0]);
            	}
            	if(null != vmPerformanceSearchList.get(i).getBytesOut() && !"".equals(vmPerformanceSearchList.get(i).getBytesOut())){
            		String [] outs1 = vmPerformanceSearchList.get(i).getBytesOut().split("\\=");
            		String [] outs2 = outs1[1].split("\\^");
            		vmPerformanceSearchList.get(i).setBytesOut(outs2[0]);
            	}
            		
            	String[] times = vmPerformanceSearchList.get(i).getCreateTime().split("\\.");
            	String time = times[0];
            	vmPerformanceSearchList.get(i).setCreateTime(time);
            }    	      
    	      vmPerformanceSearchLists = new HashMap<String, Object>();
    	      vmPerformanceSearchLists.put("list", vmPerformanceSearchList);
    	      vmPerformanceSearchLists.put("page", getPageBar());
    	      logger.info("查询虚拟机性能数据列表成功");      
    	}
        return ConstantEnum.SUCCESS.toString();
    }
    
    /**
     * TimeInit 时间初始化
     */
    private void TimeInit(VmPerformanceInfo vmPerformanceInfo){
	     logger.info("时间初始化操作开始！");
    	 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
	        String day = df.format(new Date());
	        String startDate = day+"000000";
	        String endDate = day+"235959";
	        vmPerformanceInfo.setStartDate(startDate);
	        vmPerformanceInfo.setEndDate(endDate);
	        logger.info("初始化起始时间是："+vmPerformanceInfo.getStartDate());
	        logger.info("初始化终止时间是："+vmPerformanceInfo.getEndDate());
	     logger.info("时间初始化操作结束！");
    }
    
    /**
     * device 获取虚拟机设备列表
     */
    @SuppressWarnings("unchecked")
    public String device() {
    	
        logger.info("进入查询虚拟机设备列表");

        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }  
        
        // session中获取用户ID
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
        String userId = user.getUserId();
        logger.info("用户的登陆名称是"+userId);
        
        VmPerformanceInfo param = new VmPerformanceInfo();
        logger.info("查询虚拟机设备的企业客户ID是"+appId);
        param.setAppId(appId);

        try {          
        	vmDeviceInfoList = this.ibatisDAO.getData("getVmDeviceList", param);
        	if(vmDeviceInfoList==null){
				vmDeviceInfoList = this.ibatisDAO.getData("getVm2DeviceList", param);
			}
//        	searchParam = vmDeviceInfoList.get(0);  	
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("vmPerformance.queryDeviceList.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("vmPerformance.queryDeviceList.fail"), userId));
            return ConstantEnum.ERROR.toString();
        }
        logger.info("查询虚拟机设备列表成功");
        return ConstantEnum.SUCCESS.toString();
    }

       
    public String getQueryBusinessId() {
        return queryBusinessId;
    }

    public void setQueryBusinessId(String queryBusinessId) {
        this.queryBusinessId = queryBusinessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<VmPerformanceInfo> getVmDeviceInfoList() {
		return vmDeviceInfoList;
	}

	public void setVmDeviceInfoList(List<VmPerformanceInfo> vmDeviceInfoList) {
		this.vmDeviceInfoList = vmDeviceInfoList;
	}

	public List<VmPerformanceInfo> getVmPerformanceList() {
		return vmPerformanceList;
	}

	public void setVmPerformanceList(List<VmPerformanceInfo> vmPerformanceList) {
		this.vmPerformanceList = vmPerformanceList;
	}

	public VmPerformanceInfo getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(VmPerformanceInfo searchParam) {
		this.searchParam = searchParam;
	}

	public List<VmPerformanceInfo> getVmPerformanceSearchList() {
		return vmPerformanceSearchList;
	}

	public void setVmPerformanceSearchList(
			List<VmPerformanceInfo> vmPerformanceSearchList) {
		this.vmPerformanceSearchList = vmPerformanceSearchList;
	}

	public Map<String, Object> getVmPerformanceLists() {
		return vmPerformanceLists;
	}

	public void setVmPerformanceLists(Map<String, Object> vmPerformanceLists) {
		this.vmPerformanceLists = vmPerformanceLists;
	}

	public String getSyncFlage() {
		return syncFlage;
	}

	public void setSyncFlage(String syncFlage) {
		this.syncFlage = syncFlage;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getVmIp() {
		return vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

	public Map<String, Object> getVmPerformanceSearchLists() {
		return vmPerformanceSearchLists;
	}

	public void setVmPerformanceSearchLists(
			Map<String, Object> vmPerformanceSearchLists) {
		this.vmPerformanceSearchLists = vmPerformanceSearchLists;
	}


}
