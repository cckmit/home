package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.vm.core.VMCreate;
import com.neusoft.mid.cloong.vm.core.VMCreateFail;
import com.neusoft.mid.cloong.vm.core.VMCreateReq;
import com.neusoft.mid.cloong.vm.core.VMCreateResp;
import com.neusoft.mid.cloong.vm.core.VMCustomCreateReq;
import com.neusoft.mid.cloong.vm.core.VMCustomCreateResp;
import com.neusoft.mid.cloong.vm.core.VMStatus;
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

public class VMOrderAidutAction extends BaseAction {
    
    /**
     * 序列号
     */
	private static final long serialVersionUID = 1105701029952520466L;
	
	/**
	 * 日志
	 */
	private static LogService logger = LogService
			.getLogger(VMOrderAidutAction.class);

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
    private VMCreate vmCreate;

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
	    
	  //对通过的订单发送创建资源请求，对不通过的订单删除已经存在的实例信息
	    if(auditPass.equals(pass)){
           String message = sentVMCreate();
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
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
					+ orderId + "审批时，操作数据库异常！", e);
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
			ibatisDAO.deleteData("delVMInstanceInfo", orderId);
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
	private String sentVMCreate(){
		
        VMCreateReq vmReq = new VMCreateReq();
        VMCustomCreateReq vmCustomeReq = new VMCustomCreateReq();
        
        //VM实例信息
        VMInstanceInfo vmInstanceInfo;
        try {
			vmInstanceInfo = (VMInstanceInfo)ibatisDAO.getSingleRecord("selectVMInstanceInfo", orderId);
		} catch (SQLException e) {
			 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机审批时查询实例信息时，操作数据库异常！", e);
	         resultPath = ConstantEnum.ERROR.toString();
	         return ConstantEnum.ERROR.toString();
	    } catch (Exception e) {
			 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机审批时查询实例信息时，操作数据库异常！", e);
	         resultPath = ConstantEnum.ERROR.toString();
	         return ConstantEnum.ERROR.toString();
	    }

//         判断该用户是否申请过虚拟机，如果申请过，则把该用户的子网地址写入 - 以前的逻辑，现在不用了
        // 可以理解查询从门户刚从页面通过选择vlan申请的虚拟机的vlan和privateIp
        String subNetwork = vmInstanceInfo.getSubNetwork();
        String privateIp = vmInstanceInfo.getPrivateIP();
//        try {
//            subNetwork = (String) ibatisDAO.getSingleRecord("getSubnetworkByUserId", vmInstanceInfo.getCreateUser());
//        } catch (SQLException e) {
//            // 如果记入数据库失败，打印error日志，记录虚拟机订单发生异常，返回浏览虚拟机列表页面，提示“提交申请虚拟机订单失败！”。
//            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机审批时查询子网时，操作数据库异常！", e);
//            resultPath = ConstantEnum.ERROR.toString();
//            return ConstantEnum.ERROR.toString();
//        } catch (Exception e2) {
//        	  // 如果记入数据库失败，打印error日志，记录虚拟机订单发生异常，返回浏览虚拟机列表页面，提示“提交申请虚拟机订单失败！”。
//            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机审批时查询子网时，操作数据库异常！", e2);
//            resultPath = ConstantEnum.ERROR.toString();
//            return ConstantEnum.ERROR.toString();
//        }
        
        VMCreateResp resp = new VMCreateResp();
        VMCustomCreateResp customResp = new VMCustomCreateResp();
        // 发送申请订单至接口
        if (null == privateIp || "".equals(privateIp)) { // 普通创建虚拟机
            assembleReq(vmReq, subNetwork, vmInstanceInfo);
            resp = vmCreate.createVM(vmReq);
        } else { // 指定ip创建 - 高定制
            assembleReq(vmCustomeReq, privateIp, vmInstanceInfo);
            customResp = new VMCustomCreateResp();//vmCreate.createCustomVM(vmCustomeReq);去掉红叉
        }
       
        if (null == privateIp || "".equals(privateIp)) {
            if (resp.getResultCode().equals(SUCCEESS_CODE)) {
                List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
                orderInfo.setEffectiveTime(resp.getVmInfo().get(0).get("ACCEPT_TIME"));
                orderInfo.setOrderId(orderId);
                updateBatchVO.add(new BatchVO("MOD", "updateOrderInfoEffectiveTime",orderInfo));
                    // 拼凑VM实例信息
                assembleVMInstance(resp.getVmInfo().get(0),vmInstanceInfo);
                    // 更新数据库
                updateBatchVO.add(new BatchVO("MOD", "updateVMInstanceInfo", vmInstanceInfo));
                try {
                    ibatisDAO.updateBatchData(updateBatchVO);
                } catch (SQLException e) {
                     logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机审批时更新订单和实例信息时，操作数据库异常！", e);
                     resultPath = ConstantEnum.ERROR.toString();
                     return ConstantEnum.ERROR.toString();
                }catch (Exception e) {
                     logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机审批时更新订单和实例信息时，操作数据库异常！", e);
                     resultPath = ConstantEnum.ERROR.toString();
                     return ConstantEnum.ERROR.toString();
                }
            }else{  // 返回失败，入失败库
                 logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建虚拟机发送申请失败！");
                 this.isSuccess = ConstantEnum.ERROR.toString();
                 message = message + resp.getResultMessage();
                // 入失败库
               List<VMCreateFail> vmCreateFails = assembleVMCreateFail(vmReq, resp);
               List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
               for (VMCreateFail vmCreateFail : vmCreateFails) {
                   insertFailBatchVO.add(new BatchVO("ADD", "insertVMFail", vmCreateFail));
               }
               try {
                   ibatisDAO.updateBatchData(insertFailBatchVO);
               } catch (SQLException e) {
                   logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟机失败后入失败库发生数据库异常",
                           e);
                   resultPath = ConstantEnum.ERROR.toString();
                   return ConstantEnum.ERROR.toString();
               } catch (Exception e2) {
                   logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟机失败后入失败库发生数据库异常",
                           e2);
                   resultPath = ConstantEnum.ERROR.toString();
                   return ConstantEnum.ERROR.toString();
               }
            }
        } else {
            if (customResp.getResultCode().equals(SUCCEESS_CODE)) {
                List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
                orderInfo.setEffectiveTime(customResp.getAcceptTime());
                orderInfo.setOrderId(orderId);
                updateBatchVO.add(new BatchVO("MOD", "updateOrderInfoEffectiveTime",orderInfo));
                    // 拼凑VM实例信息
                assembleVMCustomInstance(customResp,vmInstanceInfo);
                    // 更新数据库
                updateBatchVO.add(new BatchVO("MOD", "updateVMInstanceInfo", vmInstanceInfo));
                try {
                    ibatisDAO.updateBatchData(updateBatchVO);
                } catch (SQLException e) {
                     logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机审批时更新订单和实例信息时，操作数据库异常！", e);
                     resultPath = ConstantEnum.ERROR.toString();
                     return ConstantEnum.ERROR.toString();
                }catch (Exception e) {
                     logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "虚拟机审批时更新订单和实例信息时，操作数据库异常！", e);
                     resultPath = ConstantEnum.ERROR.toString();
                     return ConstantEnum.ERROR.toString();
                }
            }else{  // 返回失败，入失败库
                 logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建虚拟机发送申请失败！");
                 this.isSuccess = ConstantEnum.ERROR.toString();
                 message = message + customResp.getResultMessage();
                // 入失败库
               List<VMCreateFail> vmCreateFails = assembleVMCustomCreateFail(vmCustomeReq, customResp);
               List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
               for (VMCreateFail vmCreateFail : vmCreateFails) {
                   insertFailBatchVO.add(new BatchVO("ADD", "insertVMFail", vmCreateFail));
               }
               try {
                   ibatisDAO.updateBatchData(insertFailBatchVO);
               } catch (SQLException e) {
                   logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟机失败后入失败库发生数据库异常",
                           e);
                   resultPath = ConstantEnum.ERROR.toString();
                   return ConstantEnum.ERROR.toString();
               } catch (Exception e2) {
                   logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟机失败后入失败库发生数据库异常",
                           e2);
                   resultPath = ConstantEnum.ERROR.toString();
                   return ConstantEnum.ERROR.toString();
               }
            }
        }
        return ConstantEnum.SUCCESS.toString();
		
	}
	
	/**
	 * 组装请求信息
	 * @param vmReq VM请求消息
	 * @param subNetwork 子网
	 * @param vmInstanceInfo vm实例
	 */
	 private void assembleReq(VMCreateReq vmReq, String subNetwork, VMInstanceInfo vmInstanceInfo) {
		  
	        vmReq.setNum("1");
	        vmReq.setOsId(vmInstanceInfo.getIsoId());
	        vmReq.setResourcePoolId(vmInstanceInfo.getResPoolId());
	        vmReq.setResourcePoolPartId(vmInstanceInfo.getResPoolPartId());
	        vmReq.setStandardId(vmInstanceInfo.getStandardId());
	        if (null == subNetwork) {
	            vmReq.setSubNetwork("");
	        } else {
	            vmReq.setSubNetwork(subNetwork);
	        }
	  }
	 
	 /**
	     * 
	     * assembleReq 生成请求
	     * @param vmReq 虚拟机请求
	     * @param subNetwork 子网
	     * @param privateIp 私网ip
	     */
	    private void assembleReq(VMCustomCreateReq vmReq, String privateIp, VMInstanceInfo vmInstanceInfo) {
	        vmReq.setOsId(vmInstanceInfo.getIsoId());
	        vmReq.setResourcePoolId(vmInstanceInfo.getResPoolId());
	        vmReq.setResourcePoolPartId(vmInstanceInfo.getResPoolPartId());
	        vmReq.setStandardId(vmInstanceInfo.getStandardId());
	        vmReq.setPrivateIp(privateIp); // 从页面传过来的私网ip
	    }
	 
	 /**
	  * 组装失败信息，准备入库
	  * @param tempVMReq  VM请求消息
	  * @param vmResp  VM响应消息
	  * @return VM失败列表
	  */
	    private List<VMCreateFail> assembleVMCreateFail(VMCreateReq tempVMReq, VMCreateResp vmResp) {
	        List<VMCreateFail> vmCreateFails = new ArrayList<VMCreateFail>();
	        VMCreateFail vmCreateFail = new VMCreateFail();
	        vmCreateFail.setFailCause(vmResp.getResultMessage());
	        vmCreateFail.setFailCode(vmResp.getResultCode());
	        vmCreateFail.setResPoolId(tempVMReq.getResourcePoolId());
	        vmCreateFail.setResPoolPartId(tempVMReq.getResourcePoolPartId());
	        vmCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
	        vmCreateFail.setStandardId(tempVMReq.getStandardId());
	        vmCreateFail.setSubNetwork(tempVMReq.getSubNetwork());
	        vmCreateFail.setOsId(tempVMReq.getOsId());
	        vmCreateFail.setNum(tempVMReq.getNum());
	        vmCreateFails.add(vmCreateFail);
	        return vmCreateFails;
	    }
	    
	    /**
	     * 
	     * assembleVMCustomCreateFail 拼装虚拟机创建失败信息
	     * @param tempVMReq 虚拟机请求
	     * @param vmResp    应答
	     * @return 失败消息List
	     */
	    private List<VMCreateFail> assembleVMCustomCreateFail(VMCustomCreateReq tempVMReq, VMCustomCreateResp vmResp) {
	        List<VMCreateFail> vmCreateFails = new ArrayList<VMCreateFail>();
	        VMCreateFail vmCreateFail = new VMCreateFail();
	        vmCreateFail.setFailCause(vmResp.getResultMessage());
	        vmCreateFail.setFailCode(vmResp.getResultCode());
	        vmCreateFail.setResPoolId(tempVMReq.getResourcePoolId());
	        vmCreateFail.setResPoolPartId(tempVMReq.getResourcePoolPartId());
	        vmCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
	        vmCreateFail.setStandardId(tempVMReq.getStandardId());
	        vmCreateFail.setSubNetwork(tempVMReq.getPrivateIp());
	        vmCreateFail.setOsId(tempVMReq.getOsId());
	        vmCreateFail.setNum("1");
	        vmCreateFails.add(vmCreateFail);
	        return vmCreateFails;
	    }
	    
	    /**
         * 组装实例信息
         * @param vmInfoSet 返回的VM实例信息
         * @param vmInstanceInfo VM实例信息
         */
        private void assembleVMInstance(Map<String, String> vmInfoSet, VMInstanceInfo vmInstanceInfo) {
            vmInstanceInfo.setVmId(vmInfoSet.get("VM_ID"));
            vmInstanceInfo.setSubNetwork(vmInfoSet.get("SUBNETWORK"));
            vmInstanceInfo.setServerId(vmInfoSet.get("SERVER_ID"));
            vmInstanceInfo.setAcceptTime(vmInfoSet.get("ACCEPT_TIME"));
            vmInstanceInfo.setPrivateIP(vmInfoSet.get("PRIVATE_IP"));
            vmInstanceInfo.setVmPassword(vmInfoSet.get("VM_PASSWORD"));
            vmInstanceInfo.setVmName(vmInfoSet.get("VM_NAME"));
            vmInstanceInfo.setStatus(VMStatus.CREATING);
            vmInstanceInfo.setCreateTime(vmInfoSet.get("ACCEPT_TIME"));
            vmInstanceInfo.setUpdateTime(vmInfoSet.get("ACCEPT_TIME"));

        }
        
	    /**
	     * 组装实例信息
	     * @param customResp 返回信息
	     * @param vmInstanceInfo VM实例信息
	     */
	    private void assembleVMCustomInstance(VMCustomCreateResp customResp, VMInstanceInfo vmInstanceInfo) {
	        vmInstanceInfo.setVmId(customResp.getVmId());
	        vmInstanceInfo.setSubNetwork(customResp.getVlanId());
	        vmInstanceInfo.setServerId(customResp.getServerId());
	        vmInstanceInfo.setAcceptTime(customResp.getAcceptTime());
	        vmInstanceInfo.setPrivateIP(customResp.getPrivateIp());
	        vmInstanceInfo.setVmPassword(customResp.getVmPassword());
	        vmInstanceInfo.setVmName(customResp.getVmName());
	        vmInstanceInfo.setStatus(VMStatus.CREATING);
	        vmInstanceInfo.setCreateTime(customResp.getAcceptTime());
	        vmInstanceInfo.setUpdateTime(customResp.getAcceptTime());

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
	 * getVmCreate 方法
	 * @return 方法
	 */
	public VMCreate getVmCreate() {
		return vmCreate;
	}

	/**
	 * 
	 * setVmCreate 方法
	 * @param vmCreate 方法
	 */
	public void setVmCreate(VMCreate vmCreate) {
		this.vmCreate = vmCreate;
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

	
}
