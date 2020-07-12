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
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.vm.core.VMModify;
import com.neusoft.mid.cloong.vm.core.VMModifyFail;
import com.neusoft.mid.cloong.vm.core.VMModifyReq;
import com.neusoft.mid.cloong.vm.core.VMModifyResp;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * VM修改订单审批
 * 
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-1-29 下午01:35:45
 */

public class VMModifyOrderAidutAction extends BaseAction {
    
    /**
     * 序列号
     */
	private static final long serialVersionUID = 1105701029952520466L;
	
	/**
	 * 日志
	 */
	private static LogService logger = LogService
			.getLogger(VMModifyOrderAidutAction.class);

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
     * 虚拟机创建接口
     */
    private VMModify vmModify;

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
     * 操作流水号生成器
     */
    private SequenceGenerator gen;
    
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
	    
	  //对通过的订单发送创建资源请求，对不通过的订单删除已经存在的实例信息
	    if(auditPass.equals(pass)){
           String message = sentVMModify();
           if(!ConstantEnum.SUCCESS.toString().equals(message)){
               return ConstantEnum.ERROR.toString();
           }
        }else{
           String message = delVMInstanceInfo();
           if(!ConstantEnum.SUCCESS.toString().equals(message)){
               return ConstantEnum.ERROR.toString();
           }
        }
        
        //如果消息发送不成功，则提示用户，直接返回。
        if(!SUCCEESS_CODE.endsWith(isSuccess)){
             resultPath = ConstantEnum.ERROR.toString();
             return ConstantEnum.ERROR.toString();
        }
		
		return ConstantEnum.SUCCESS.toString();
		
	}
	
	/**
	 * 对不通过的订单删除已经存在的实例信息
	 * @return 是否成功
	 */
	private String delVMInstanceInfo(){
	    
		try {//设置实例状态为已删除
			ibatisDAO.updateData("updateVMModifyStatus", orderId); // 没通过将订单置为已生效，让之前的配置能正常使用
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
	private String sentVMModify(){
        //修改的VM实例信息
        VMInstanceInfo vmInstanceInfo;
        try {
			vmInstanceInfo = (VMInstanceInfo)ibatisDAO.getSingleRecord("selectVMModifyInfo", orderId); //求申请修改的虚拟机信息-对应最大时间戳建立的那条
		} catch (SQLException e) {
			 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟机审批时查询实例信息时，操作数据库异常！", e);
	         resultPath = ConstantEnum.ERROR.toString();
	         return ConstantEnum.ERROR.toString();
	    } catch (Exception e) {
			 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟机审批时查询实例信息时，操作数据库异常！", e);
	         resultPath = ConstantEnum.ERROR.toString();
	         return ConstantEnum.ERROR.toString();
	    }

        VMModifyReq modifyVMReq = new VMModifyReq();
        // 发送申请订单至接口
        assembleReq(modifyVMReq, vmInstanceInfo);
        VMModifyResp resp = vmModify.modifyVM(modifyVMReq);
        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            // 返回成功，虚拟机订单和实例入库
            String userId = ((UserBean) ServletActionContext.getRequest()
                    .getSession().getAttribute(SessionKeys.userInfo.toString()))
                    .getUserId();
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(vmInstanceInfo.getOrderId());
            orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            orderInfo.setUpdateUser(userId);
            orderInfo.setItemId(vmInstanceInfo.getItemId());
            orderInfo.setStandardId(vmInstanceInfo.getStandardId());
            orderInfo.setStatus("3");// 只有已生效的虚拟机订单才能修改，所以申请通过后再置成已生效
            vmInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            vmInstanceInfo.setUpdateUser(userId);
            vmInstanceInfo.setDiscSize(Long.toString(Long.parseLong(vmInstanceInfo.getDiscSizeAdd())+Long.parseLong(vmInstanceInfo.getDiscSize()))); // 原来的加上新增的
            
            orderAuditInfo = new OrderAuditInfo();
            orderAuditInfo.setAuditUser(userId);
            orderAuditInfo.setAuditTime(DateParse
                    .generateDateFormatyyyyMMddHHmmss());
            orderAuditInfo.setOrderId(orderId);
            orderAuditInfo.setStatus(pass);
            orderAuditInfo.setAuditInfo(auditInfo);
            try {
                ibatisDAO.updateData("updateVMInfo", vmInstanceInfo); // 直接更新虚拟机表信息 
                ibatisDAO.updateData("updateVMModifyOrderInfo", orderInfo); // 订单里录入新的规格id和条目id,且将状态置为已生效
                ibatisDAO.insertData("updateOrderAuditInfo", orderAuditInfo); //记录审核日志
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, getText("vmInfo.modify.fail"), e);
                resultPath = ConstantEnum.ERROR.toString();
                return ConstantEnum.ERROR.toString();
            }
        } else {
            // 返回失败，入失败库
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "修改虚拟机发送申请失败！");
            // 入失败库
            List<VMModifyFail> vmModifyFails = assembleVMModifyFail(modifyVMReq, resp);
            List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
            for (VMModifyFail vmModifyFail : vmModifyFails) {
                insertFailBatchVO.add(new BatchVO("MOD", "insertVMModifyFail", vmModifyFail));
            }
            try {
                ibatisDAO.updateBatchData(insertFailBatchVO);
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟机失败后入失败库发生数据库异常",
                        e);
            } catch (Exception e2) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟机失败后入失败库发生数据库异常",
                        e2);
            }
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
		
	}
	
	/**
     * 
     * assembleReq 生成请求
     * @param modifyVMReq 修改虚拟机请求
     */
    private void assembleReq(VMModifyReq modifyVMReq, VMInstanceInfo vmInstanceInfo) {
        modifyVMReq.setVmId(vmInstanceInfo.getVmId());
        modifyVMReq.setCpuNum(vmInstanceInfo.getCpuNum());
        modifyVMReq.setRamSize(vmInstanceInfo.getRamSize());
        modifyVMReq.setDiscSize(vmInstanceInfo.getDiscSizeAdd());
        modifyVMReq.setResourcePoolId(vmInstanceInfo.getResPoolId());
        modifyVMReq.setResourcePoolPartId(vmInstanceInfo.getResPoolPartId());
        modifyVMReq.setSerialNum(gen.generatorVMSerialNum("VMMod"));
        
    }

    /**
     * 
     * assembleVMModifyFail 拼装虚拟机创建失败信息
     * @param tempVMReq 虚拟机请求
     * @param vmResp    应答
     * @return 失败消息List
     */
    private List<VMModifyFail> assembleVMModifyFail(VMModifyReq tempVMReq, VMModifyResp vmResp) {
        List<VMModifyFail> vmModifyFails = new ArrayList<VMModifyFail>();
        VMModifyFail vmModifyFail = new VMModifyFail();
        vmModifyFail.setFailCause(vmResp.getResultMessage());
        vmModifyFail.setFailCode(vmResp.getResultCode());
        vmModifyFail.setResPoolId(tempVMReq.getResourcePoolId());
        vmModifyFail.setResPoolPartId(tempVMReq.getResourcePoolPartId());
        vmModifyFail.setCreateTime(TIMESTAMP.print(new DateTime()));
        vmModifyFail.setVmId(tempVMReq.getVmId());
        vmModifyFail.setCpuNum(tempVMReq.getCpuNum());
        vmModifyFail.setRamSize(tempVMReq.getRamSize());
        vmModifyFail.setDiscSize(tempVMReq.getDiscSize());
        vmModifyFails.add(vmModifyFail);
        return vmModifyFails;
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

    public VMModify getVmModify() {
        return vmModify;
    }

    public void setVmModify(VMModify vmModify) {
        this.vmModify = vmModify;
    }

    public SequenceGenerator getGen() {
        return gen;
    }

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }
}
