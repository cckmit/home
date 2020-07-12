package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.vmbak.core.VmBakCreate;
import com.neusoft.mid.cloong.vmbak.core.VmBakCreateFail;
import com.neusoft.mid.cloong.vmbak.core.VmBakCreateReq;
import com.neusoft.mid.cloong.vmbak.core.VmBakCreateResp;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * VM订单审批
 * 
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-1-29 下午01:35:45
 */

public class VMBAKOrderAidutAction extends BaseAction {
	
	/**
     * serialVersionUID : 序列号
     */
    private static final long serialVersionUID = -330924808590539959L;

    /**
	 * 日志
	 */
	private static LogService logger = LogService
			.getLogger(VMBAKOrderAidutAction.class);

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 是否通过审批 1：通过 2：不通过
	 */
	private String pass;

	/**
	 * 审批意见
	 */
	private String auditInfo;

	/**
	 * 订单实体
	 */
	private OrderInfo orderInfo;

	/**
	 * 订单审批记录表实体
	 */
	private OrderAuditInfo orderAuditInfo;

	/**
	 * 返回路径，用于在界面判断是否系统错误
	 */
	private String resultPath = ConstantEnum.SUCCESS.toString();

	 /**
     * 虚拟机备份创建接口
     */
	 private VmBakCreate vmBakCreate;

	/**
	 * 成功的接口响应码
	 */
	private static final String SUCCEESS_CODE = "00000000";
	

    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    
    /**
     * 申请资源是否成功 默认成功
     */
    private String isSuccess = SUCCEESS_CODE;
    
    /**
     * 是否审批通过 默认通过
     */
    private static final String auditPass = "1";
    
    /**
     * 失败消息
     */
    private String message = "订单审批失败  ";

    /**
     * 
     * execute 执行VM人工审批
     * @return VM人工审批结果
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
	public String execute() {
	    
	 // 拼凑订单信息
        orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        orderInfo.setStatus(pass);
	    
        String failMessage = ConstantEnum.SUCCESS.toString();
	  //对通过的订单发送创建资源请求，对不通过的订单删除已经存在的实例信息
	    if(auditPass.equals(pass)){
           String message = sentVMBAKCreate();
           if(!ConstantEnum.SUCCESS.toString().equals(message)){
               failMessage = ConstantEnum.ERROR.toString();
           }
        }else{
           String message = delVMBAKInstanceInfo();
           if(!ConstantEnum.SUCCESS.toString().equals(message)){
               failMessage = ConstantEnum.ERROR.toString();
           }
        }
        
        //如果消息发送不成功，则提示用户，直接返回。
        if( ConstantEnum.ERROR.toString().equals(failMessage)||!SUCCEESS_CODE.equals(isSuccess)){
             resultPath = ConstantEnum.ERROR.toString();
             return ConstantEnum.ERROR.toString();
        }

		
		// session中获取用户ID
		String userId = ((UserBean) ServletActionContext.getRequest()
				.getSession().getAttribute(SessionKeys.userInfo.toString()))
				.getUserId();
		orderAuditInfo = new OrderAuditInfo();
		orderAuditInfo.setAuditUser(userId);
		orderAuditInfo.setAuditTime(DateParse
				.generateDateFormatyyyyMMddHHmmss());
		orderAuditInfo.setOrderId(orderId);
		orderAuditInfo.setStatus(pass);
		orderAuditInfo.setAuditInfo(auditInfo);
		BatchVO batchVOorderInfo = new BatchVO("MOD", "updateOrderInfo",
				orderInfo);
		BatchVO batchVOorderAuditInfo = new BatchVO("ADD",
				"updateOrderAuditInfo", orderAuditInfo);
		List<BatchVO> batchVOs = new ArrayList<BatchVO>();
		batchVOs.add(batchVOorderInfo);
		batchVOs.add(batchVOorderAuditInfo);
		try {
			ibatisDAO.updateBatchData(batchVOs);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
					+ orderId + "审批时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
					+ orderId + "审批时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			
		}
		
		if(ConstantEnum.ERROR.toString().equals(resultPath)){
		    return ConstantEnum.ERROR.toString();
		}else{
		    return ConstantEnum.SUCCESS.toString();
		}
		
		
	}
	
	/**
	 * 对不通过的订单删除已经存在的实例信息
	 * @return 是否成功
	 */
	private String delVMBAKInstanceInfo(){
	    
		try {//设置实例状态为已删除
			ibatisDAO.deleteData("delVMBAKInstanceInfo", orderId);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
					+ orderId + "删除实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
					+ orderId + "删除实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}
	
	/**
	 * 对通过的订单发送创建资源请求
	 * @return 是否成功
	 */
	private String sentVMBAKCreate(){
		
	    VmBakCreateReq vmBakReq = new VmBakCreateReq();
        
        //VM实例信息
        VmBakInstanceInfo vmBakInstanceInfo = new VmBakInstanceInfo();
        try {
            vmBakInstanceInfo = (VmBakInstanceInfo)ibatisDAO.getSingleRecord("selectVmBakInstanceInfo", orderId);
		} catch (SQLException e) {
			 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机备份审批时查询实例信息时，操作数据库异常！", e);
	         resultPath = ConstantEnum.ERROR.toString();
	    } catch (Exception e) {
			 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机备份审批时查询实例信息时，操作数据库异常！", e);
	         resultPath = ConstantEnum.ERROR.toString();
	    }finally{
	        if(ConstantEnum.ERROR.toString().equals(resultPath)){
	            return ConstantEnum.ERROR.toString();
	        }
	    }

        // 发送申请订单至接口
        assembleReq(vmBakReq,vmBakInstanceInfo);
        VmBakCreateResp resp = new VmBakCreateResp();//vmBakCreate.createVmBak(vmBakReq); 去掉红叉
       
        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
        	List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        	orderInfo.setEffectiveTime(resp.getBackupTime());
        	orderInfo.setOrderId(orderId);
            updateBatchVO.add(new BatchVO("MOD", "updateOrderInfoEffectiveTime",orderInfo));
                // 拼凑VM实例信息
        	assembleVmBakInstance(resp,vmBakInstanceInfo);
                // 更新数据库
            updateBatchVO.add(new BatchVO("MOD", "updateVMBAKInstanceInfo", vmBakInstanceInfo));
            try {
				ibatisDAO.updateBatchData(updateBatchVO);
			} catch (SQLException e) {
				 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机备份审批时更新订单和实例信息时，操作数据库异常！", e);
		         resultPath = ConstantEnum.ERROR.toString();
			}catch (Exception e) {
				 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机备份审批时更新订单和实例信息时，操作数据库异常！", e);
		         resultPath = ConstantEnum.ERROR.toString();
			}
        }else{  // 返回失败，入失败库
        	 logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建虚拟机备份发送申请失败！");
        	 this.isSuccess = ConstantEnum.ERROR.toString();
        	 message = message + resp.getResultMessage();
        	// 入失败库
        	 VmBakCreateFail vmBakCreateFails = assembleCreateFail(vmBakReq,resp);
             try {
                 ibatisDAO.insertData("insertVmBakFail", vmBakCreateFails);
             } catch (SQLException e) {
                 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟机备份失败后入失败库发生数据库异常",
                         e);
                 resultPath = ConstantEnum.ERROR.toString();
             }catch (Exception e) {
                 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟机备份失败后入失败库发生数据库异常",
                         e);
                 resultPath = ConstantEnum.ERROR.toString();
             }
        }
         
        if(ConstantEnum.ERROR.toString().equals(resultPath)){
            return ConstantEnum.ERROR.toString();
        }else{
            return ConstantEnum.SUCCESS.toString();
        }
        
		
	}
	
	/**
	 * 组装请求信息
	 * @param vmBakReq VM请求消息
	 * @param vmBakInstanceInfo 实例
	 */
	 private void assembleReq(VmBakCreateReq vmBakReq,VmBakInstanceInfo vmBakInstanceInfo) {
	        vmBakReq.setVmId(vmBakInstanceInfo.getVmId());
	        vmBakReq.setResourcePoolId(vmBakInstanceInfo.getResPoolId());
	        vmBakReq.setResourcePoolPartId(vmBakInstanceInfo.getResPoolPartId());
	    }

	 /**拼凑虚拟机备份失败信息
	     * @param tempReq 接口请求
	     * @param resp 接口响应
	     * @return 创建备份失败消息
	     */
	    private VmBakCreateFail assembleCreateFail(VmBakCreateReq tempReq, VmBakCreateResp resp) {
	        VmBakCreateFail vmBakCreateFail = new VmBakCreateFail();
	        vmBakCreateFail.setFailCause(resp.getResultMessage());
	        vmBakCreateFail.setFailCode(resp.getResultCode());
	        vmBakCreateFail.setResPoolId(tempReq.getResourcePoolId());
	        vmBakCreateFail.setResPoolPartId(tempReq.getResourcePoolPartId());
	        vmBakCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
	        vmBakCreateFail.setVmId(tempReq.getVmId());
	        return vmBakCreateFail;
	    }
	    /**
	     * 
	     * assemblePMInstance 拼装物理机实例
	     * @param resp 消息接口响应
	     * @param vmBakInstanceInfo 备份实例信息
	     */
	    private void assembleVmBakInstance(VmBakCreateResp resp, VmBakInstanceInfo vmBakInstanceInfo) {        
	        vmBakInstanceInfo.setVmBakId(resp.getVmBakId());
	        vmBakInstanceInfo.setVmId(resp.getVmId());
	        vmBakInstanceInfo.setAcceptTime(resp.getBackupTime());
	        vmBakInstanceInfo.setStatus(VmBakStatus.CREATING);
	    }

	    
	/**
	 * 
	 * getOrderId  方法
	 * @return 方法
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 
	 * setOrderId 方法
	 * @param orderId 方法
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 
	 * getPass 方法
	 * @return 方法
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * 
	 * setPass方法
	 * @param pass 方法
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * 
	 * getAuditInfo 方法
	 * @return 方法
	 */
	public String getAuditInfo() {
		return auditInfo;
	}

	/**
	 * 
	 * setAuditInfo 方法
	 * @param auditInfo 方法
	 */
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}

	/**
	 * 
	 * getOrderInfo 方法
	 * @return 方法
	 */
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	/**
	 * 
	 * setOrderInfo 方法
	 * @param orderInfo 方法
	 */
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	/**
	 * 
	 * getOrderAuditInfo 方法
	 * @return 方法
	 */
	public OrderAuditInfo getOrderAuditInfo() {
		return orderAuditInfo;
	}

	/**
	 * 
	 * setOrderAuditInfo 方法
	 * @param orderAuditInfo 方法
	 */
	public void setOrderAuditInfo(OrderAuditInfo orderAuditInfo) {
		this.orderAuditInfo = orderAuditInfo;
	}

	/**
	 * 
	 * getResultPath 方法
	 * @return 方法
	 */
	public String getResultPath() {
		return resultPath;
	}

	/**
	 * 
	 * setResultPath 方法
	 * @param resultPath 方法
	 */
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}
	
	/**
	 * 
	 * getMessage 方法
	 * @return 方法
	 */
	
    public String getMessage() {
        return message;
    }

    /**
     * 
     * setMessage 方法 
     * @param message 方法
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * getVmBakCreate TODO 方法
     * @return TODO
     */
    public VmBakCreate getVmBakCreate() {
        return vmBakCreate;
    }

    /**
     * 
     * setVmBakCreate TODO 方法
     * @param vmBakCreate TODO
     */
    public void setVmBakCreate(VmBakCreate vmBakCreate) {
        this.vmBakCreate = vmBakCreate;
    }
    
    
	
}
