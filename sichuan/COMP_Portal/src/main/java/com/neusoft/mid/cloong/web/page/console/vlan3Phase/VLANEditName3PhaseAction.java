package com.neusoft.mid.cloong.web.page.console.vlan3Phase;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

public class VLANEditName3PhaseAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private final LogService logger = LogService.getLogger(VLANEditName3PhaseAction.class);
	
	private String caseId;
	
	private String vlanName;
	
	private String orderId;
	
//	页面新修改的StartId
	private String startId;
	
//	页面新修改的EndId
	private String endId;
	
//	重复提示框所用ID
    private String reStartId;
    
//	重复提示框所用ID
    private String reEndId;
    
    private String errMsg;
    
//    资源表中存储的StartId
    private String tempStart;
    
//  资源表中存储的EndId
    private String tempEnd;
    
//  临时表中存储的StartId
    private String upStart;
  
//  临时表中存储的EndId
    private String upEnd;
    
	private String resultPath = ConstantEnum.SUCCESS.toString();
	
	 @SuppressWarnings("unchecked")
	public String execute() {
		 // session中获取用户ID
	     UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
	                .getAttribute(SessionKeys.userInfo.toString()));
	     String userId = user.getUserId();
		 VlanInfo vlan = new VlanInfo();
		 VlanInfo temp = new VlanInfo();
		 VlanInfo upTemp = new VlanInfo();
		 OrderInfo orderInfo = new OrderInfo();
		 vlan.setCaseId(caseId);
		 vlan.setVlanName(vlanName);
		 vlan.setStartId(Integer.parseInt(startId));
		 vlan.setEndId(Integer.parseInt(endId));		 
	     List<VlanInfo> rangeCase = new ArrayList<VlanInfo>();
	     List<VlanInfo> rangeUpdate = new ArrayList<VlanInfo>();
		 OrderInfo tempOrder = new OrderInfo();
		 try{
			 temp = (VlanInfo)ibatisDAO.getSingleRecord("getVlanRangeInfo", vlan);
			 rangeCase = ibatisDAO.getData("vlanRange3Phase", vlan);
			 rangeUpdate = ibatisDAO.getData("vlanRangeUpdate", vlan);
			 tempOrder = (OrderInfo) ibatisDAO.getSingleRecord("getVlanRangeOrderInfo", temp);
			 upTemp = (VlanInfo)ibatisDAO.getSingleRecord("getVlanUpdateByCaseId", vlan);
		 }catch (Exception e){
	            logger.error(VlanStatusCode.createVlanOrderException,
	                    getText("vlan3Phase.applyinfo.fail"), e);
	            this.addActionError(getText("vlan3Phase.applyinfo.fail"));
	            errMsg = getText("vlan3Phase.applyinfo.fail");
	            return ConstantEnum.ERROR.toString();
	        }	 
		 tempStart = String.valueOf(temp.getStartId());
		 tempEnd = String.valueOf(temp.getEndId());
		 if(upTemp != null){
			 upStart = String.valueOf(upTemp.getStartId());
			 upEnd = String.valueOf(upTemp.getEndId());
		 }else{
			 upStart = "";
			 upEnd = "";
		 }
		 orderInfo.setOrderId(temp.getOrderId());
		 vlan.setOrderId(temp.getOrderId());
		 vlan.setVlanId(temp.getVlanId());	
		 logger.info("修改vlan开始");
		 if("0".equals(tempOrder.getStatus())){
			 if(validateApply(rangeCase,rangeUpdate,tempStart,tempEnd)){
	             logger.error(VlanStatusCode.createVlanOrderException,
	                     getText("vlan3Phase.rangeList.fail"));
	             this.addActionError(getText("vlan3Phase.rangeList.fail"));
	             errMsg = getText("已存在范围为"+reStartId+"-"+reEndId+"的Vlan，请重新修改！");
	 			 resultPath = ConstantEnum.ERROR.toString();
	             return ConstantEnum.ERROR.toString();
			 }
			 try{
				 ibatisDAO.updateData("editVlanSdnCase", vlan);
			 } catch (SQLException e) {
					logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
		                    MessageFormat.format(getText("vlan3Phase.editVlanName.fail"), userId), e);
		            this.addActionError(MessageFormat.format(getText("vlan3Phase.editVlanName.fail"), userId));
		            return ConstantEnum.ERROR.toString();
			 } 
		 }else if("3".equals(tempOrder.getStatus()) || "1".equals(tempOrder.getStatus())){
			 if(validateEffect(rangeCase,rangeUpdate)){
	             logger.error(VlanStatusCode.createVlanOrderException,
	                     getText("vlan3Phase.rangeList.fail"));
	             this.addActionError(getText("vlan3Phase.rangeList.fail"));
	             errMsg = getText("已存在范围为"+reStartId+"-"+reEndId+"的Vlan，请重新修改！");
	 			 resultPath = ConstantEnum.ERROR.toString();
	             return ConstantEnum.ERROR.toString();
			 }
			 orderInfo.setStatus("7");
			 try{
				ibatisDAO.insertData("createVlanSdnUpdateInfo", vlan);
				ibatisDAO.updateData("editVlanSdnOrderInfo", orderInfo);
			 }catch (SQLException e) {
					logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
		                    MessageFormat.format(getText("vlan3Phase.editVlanName.fail"), userId), e);
		            this.addActionError(MessageFormat.format(getText("vlan3Phase.editVlanName.fail"), userId));
		            return ConstantEnum.ERROR.toString();
		     } 
		 }else if("7".equals(tempOrder.getStatus())){
			 if(validateEdit(rangeCase,rangeUpdate,upStart,upEnd)){
	             logger.error(VlanStatusCode.createVlanOrderException,
	                     getText("vlan3Phase.rangeList.fail"));
	             this.addActionError(getText("vlan3Phase.rangeList.fail"));
	             errMsg = getText("已存在范围为"+reStartId+"-"+reEndId+"的Vlan，请重新修改！");
	 			 resultPath = ConstantEnum.ERROR.toString();
	             return ConstantEnum.ERROR.toString();
			 }
			try{
				ibatisDAO.updateData("editVlanSdnUpdate", vlan);				
			}catch (SQLException e) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
	                    MessageFormat.format(getText("vlan3Phase.editVlanName.fail"), userId), e);
	            this.addActionError(MessageFormat.format(getText("vlan3Phase.editVlanName.fail"), userId));
	            return ConstantEnum.ERROR.toString();
	        } 
		 }else{
             logger.error(VlanStatusCode.createVlanOrderException,
                     getText("vlan3Phase.rangeList.fail"));
             this.addActionError(getText("vlan3Phase.rangeList.fail"));
             errMsg = getText("当前vlan状态无法修改！");
 			 resultPath = ConstantEnum.ERROR.toString();
             return ConstantEnum.ERROR.toString();
		 }
		 logger.info("修改vlan成功");
		 resultPath = ConstantEnum.SUCCESS.toString();
	     return ConstantEnum.SUCCESS.toString();
	 }
	 
//	 申请待审批的修改校验
	 private boolean validateApply(List<VlanInfo> rangeCase,List<VlanInfo> rangeUpdate, String tempStart,String tempEnd){
	    	int rangeStart = Integer.parseInt(startId);
	        int rangeEnd = Integer.parseInt(endId);	        
	        if(!"0".equals(String.valueOf(rangeCase.size()))){
	        	for (VlanInfo it : rangeCase){	        		
	        		if(tempStart.equals(String.valueOf(it.getStartId())) && tempEnd.equals(String.valueOf(it.getEndId()))){
	        			 continue ;
	        		}else{
			        	if(rangeStart <= it.getStartId()&& rangeEnd >= it.getStartId()&& rangeEnd <= it.getEndId()){
			        		reStartId = String.valueOf(it.getStartId());
			        		reEndId = String.valueOf(it.getEndId());
			        		return true;
			        	}else if(rangeStart >= it.getStartId() && rangeStart <= it.getEndId() && rangeEnd >= it.getEndId()){
			        		reStartId = String.valueOf(it.getStartId());
			        		reEndId = String.valueOf(it.getEndId());
			        		return true;
			        	}else if(rangeStart <= it.getStartId() && rangeEnd >= it.getEndId()){
			        		reStartId = String.valueOf(it.getStartId());
			        		reEndId = String.valueOf(it.getEndId());
			        		return true;
			        	}else if(rangeStart >= it.getStartId() && rangeEnd <= it.getEndId()){
			        		reStartId = String.valueOf(it.getStartId());
			        		reEndId = String.valueOf(it.getEndId());
			        		return true;
			        	}
	        		}	        			
	        	}	        		       	
	        }else if(!"0".equals(String.valueOf(rangeUpdate.size()))){
	        	for (VlanInfo it : rangeUpdate){
		        	if(rangeStart <= it.getStartId()&& rangeEnd >= it.getStartId()&& rangeEnd <= it.getEndId()){
		        		reStartId = String.valueOf(it.getStartId());
		        		reEndId = String.valueOf(it.getEndId());
		        		return true;
		        	}else if(rangeStart >= it.getStartId() && rangeStart <= it.getEndId() && rangeEnd >= it.getEndId()){
		        		reStartId = String.valueOf(it.getStartId());
		        		reEndId = String.valueOf(it.getEndId());
		        		return true;
		        	}else if(rangeStart <= it.getStartId() && rangeEnd >= it.getEndId()){
		        		reStartId = String.valueOf(it.getStartId());
		        		reEndId = String.valueOf(it.getEndId());
		        		return true;
		        	}else if(rangeStart >= it.getStartId() && rangeEnd <= it.getEndId()){
		        		reStartId = String.valueOf(it.getStartId());
		        		reEndId = String.valueOf(it.getEndId());
		        		return true;
		        	}        		
	        	}	        	
	        }      	
	    	return false;
	}
	 
//	 已生效vlan修改校验
	 private boolean validateEffect(List<VlanInfo> rangeCase,List<VlanInfo> rangeUpdate){
	    int rangeStart = Integer.parseInt(startId);
	    int rangeEnd = Integer.parseInt(endId);
	    if(!"0".equals(String.valueOf(rangeCase.size()))){
	    	for (VlanInfo it : rangeCase){
	        	if(rangeStart <= it.getStartId()&& rangeEnd >= it.getStartId()&& rangeEnd <= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart >= it.getStartId() && rangeStart <= it.getEndId() && rangeEnd >= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart <= it.getStartId() && rangeEnd >= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart >= it.getStartId() && rangeEnd <= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}
	    	}
	    }else if(!"0".equals(String.valueOf(rangeUpdate.size()))){
        	for (VlanInfo it : rangeUpdate){
	        	if(rangeStart <= it.getStartId()&& rangeEnd >= it.getStartId()&& rangeEnd <= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart >= it.getStartId() && rangeStart <= it.getEndId() && rangeEnd >= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart <= it.getStartId() && rangeEnd >= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart >= it.getStartId() && rangeEnd <= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}        		
        	}
	    } 
		return false;
	 }
	 
//	 修改审批的修改校验
	 private boolean validateEdit(List<VlanInfo> rangeCase,List<VlanInfo> rangeUpdate, String upStart,String upEnd){
		int rangeStart = Integer.parseInt(startId);
		int rangeEnd = Integer.parseInt(endId);
	    if(!"0".equals(String.valueOf(rangeCase.size()))){
	    	for (VlanInfo it : rangeCase){
	        	if(rangeStart <= it.getStartId()&& rangeEnd >= it.getStartId()&& rangeEnd <= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart >= it.getStartId() && rangeStart <= it.getEndId() && rangeEnd >= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart <= it.getStartId() && rangeEnd >= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}else if(rangeStart >= it.getStartId() && rangeEnd <= it.getEndId()){
	        		reStartId = String.valueOf(it.getStartId());
	        		reEndId = String.valueOf(it.getEndId());
	        		return true;
	        	}
	    	}	    
	    }else if(!"0".equals(String.valueOf(rangeUpdate.size()))){
        	for (VlanInfo it : rangeUpdate){
        		if(upStart.equals(String.valueOf(it.getStartId())) && upEnd.equals(String.valueOf(it.getEndId()))){
       			 continue ;
       		    }else{
    	        	if(rangeStart <= it.getStartId()&& rangeEnd >= it.getStartId()&& rangeEnd <= it.getEndId()){
    	        		reStartId = String.valueOf(it.getStartId());
    	        		reEndId = String.valueOf(it.getEndId());
    	        		return true;
    	        	}else if(rangeStart >= it.getStartId() && rangeStart <= it.getEndId() && rangeEnd >= it.getEndId()){
    	        		reStartId = String.valueOf(it.getStartId());
    	        		reEndId = String.valueOf(it.getEndId());
    	        		return true;
    	        	}else if(rangeStart <= it.getStartId() && rangeEnd >= it.getEndId()){
    	        		reStartId = String.valueOf(it.getStartId());
    	        		reEndId = String.valueOf(it.getEndId());
    	        		return true;
    	        	}else if(rangeStart >= it.getStartId() && rangeEnd <= it.getEndId()){
    	        		reStartId = String.valueOf(it.getStartId());
    	        		reEndId = String.valueOf(it.getEndId());
    	        		return true;
    	        	}  
       		    }
        	}	    	
	    }
		return false;		 		 
	 }

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getVlanName() {
		return vlanName;
	}

	public void setVlanName(String vlanName) {
		this.vlanName = vlanName;
	}	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStartId() {
		return startId;
	}

	public void setStartId(String startId) {
		this.startId = startId;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	public LogService getLogger() {
		return logger;
	}

	public String getReStartId() {
		return reStartId;
	}

	public void setReStartId(String reStartId) {
		this.reStartId = reStartId;
	}

	public String getReEndId() {
		return reEndId;
	}

	public void setReEndId(String reEndId) {
		this.reEndId = reEndId;
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getTempStart() {
		return tempStart;
	}

	public void setTempStart(String tempStart) {
		this.tempStart = tempStart;
	}

	public String getTempEnd() {
		return tempEnd;
	}

	public void setTempEnd(String tempEnd) {
		this.tempEnd = tempEnd;
	}

	public String getUpStart() {
		return upStart;
	}

	public void setUpStart(String upStart) {
		this.upStart = upStart;
	}

	public String getUpEnd() {
		return upEnd;
	}

	public void setUpEnd(String upEnd) {
		this.upEnd = upEnd;
	}
	
}
