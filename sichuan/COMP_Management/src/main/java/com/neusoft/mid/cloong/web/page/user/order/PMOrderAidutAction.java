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
import com.neusoft.mid.cloong.pm.core.PMCreate;
import com.neusoft.mid.cloong.pm.core.PMCreateFail;
import com.neusoft.mid.cloong.pm.core.PMCreateReq;
import com.neusoft.mid.cloong.pm.core.PMCreateResp;
import com.neusoft.mid.cloong.pm.core.PMStatus;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * PM订单审批
 * 
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-1-29 下午01:35:45
 */

public class PMOrderAidutAction extends BaseAction {

    /**
     * 序列号
     */
	private static final long serialVersionUID = 7933654535135784427L;

	/**
	 * 日志
	 */
	private static LogService logger = LogService
			.getLogger(PMOrderAidutAction.class);

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
     * 物理拟机创建接口
     */
    private PMCreate pmCreate;

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
     * 运行状态开关   0:pm状态设置成运行中    非0：pm状态设置成创建中.spring注入
     */
    private String pmStatusQuery;
    
    /**
     * 0:pm状态设置成运行中 
     */
    private static final String PMSTATUS = "0";
    
    /**
     * 
     * execute 执行PM人工审批
     * @return PM人工审批结果
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
	public String execute() {
	    
	    // 拼凑订单信息
        orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        orderInfo.setStatus(pass);
        
	    //对通过的订单发送创建资源请求，对不通过的订单删除已经存在的实例信息
	    if(auditPass.equals(pass)){
            String message = sentPMCreate();
            if(!ConstantEnum.SUCCESS.toString().equals(message)){
                return ConstantEnum.ERROR.toString();
            }
            if(PMSTATUS.equals(pmStatusQuery)){
                orderInfo.setStatus("3");//已生效
            }
        }else{
            String message = delPMInstanceInfo();
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
	private String delPMInstanceInfo(){
		try {
			ibatisDAO.deleteData("delPMInstanceInfo", orderId);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
					+ orderId + "删除物理机实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
					+ orderId + "删除物理机实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}
	/**
	 * 对通过的订单发送创建资源请求
	 * @return 是否成功
	 */
	private String sentPMCreate(){

        PMCreateReq pmReq = new PMCreateReq();
        PMInstanceInfo pmInstanceInfo;
        try {
			pmInstanceInfo = (PMInstanceInfo)ibatisDAO.getSingleRecord("selectPMInstanceInfo", orderId);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询物理机实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询物理机实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}

        // 判断该用户是否申请过虚拟机，如果申请过，则把该用户的子网地址写入
        String subNetwork = "";
        try {
            subNetwork = (String) ibatisDAO.getSingleRecord("getSubnetworkByUserId", pmInstanceInfo.getCreateUser());
        } catch (SQLException e) {
            // 如果记入数据库失败，打印error日志，记录虚拟机订单发生异常，返回浏览虚拟机列表页面，提示“提交申请虚拟机订单失败！”。
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "物理机审批时查询子网时，操作数据库异常！", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
        	  // 如果记入数据库失败，打印error日志，记录虚拟机订单发生异常，返回浏览虚拟机列表页面，提示“提交申请虚拟机订单失败！”。
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "物理机审批时查询子网时，操作数据库异常！", e2);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        
        // 发送申请订单至接口
        assembleReq(pmReq, subNetwork,pmInstanceInfo);
        PMCreateResp resp = pmCreate.createPM(pmReq);
        
        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
        	List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        	orderInfo.setEffectiveTime(resp.getPmInfo().get(0).get("ACCEPT_TIME"));
        	orderInfo.setOrderId(orderId);
        	
        	//如果为生效.设置其到期日期
        	if(PMSTATUS.equals(pmStatusQuery)){
        	    OrderInfo orderInfols = new OrderInfo();
        	    try {
        	        orderInfols = (OrderInfo)ibatisDAO.getSingleRecord("queryPMOrderInfo", orderId);
        	    } catch (SQLException e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "物理机审批更新订单和实例信息时，操作数据库异常！", e);
                    resultPath = ConstantEnum.ERROR.toString();
                    return ConstantEnum.ERROR.toString();
               }catch (Exception e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "物理机审批更新订单和实例信息时，操作数据库异常！", e);
                    resultPath = ConstantEnum.ERROR.toString();
                    return ConstantEnum.ERROR.toString();
               }
               orderInfo.setExpireTime(DateParse.countExpireTime(orderInfols.getLengthUnit(), orderInfo.getEffectiveTime(), orderInfols.getLengthTime()));
            }
            updateBatchVO.add(new BatchVO("MOD", "updateOrderInfoEffectiveTime",orderInfo));
                // 拼凑VM实例信息
            assembleVMInstance(resp.getPmInfo().get(0),pmInstanceInfo);
                // 更新数据库
            updateBatchVO.add(new BatchVO("MOD", "updatePMInstanceInfo", pmInstanceInfo));
            try {
				ibatisDAO.updateBatchData(updateBatchVO);
			} catch (SQLException e) {
				 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "物理机审批更新订单和实例信息时，操作数据库异常！", e);
		         resultPath = ConstantEnum.ERROR.toString();
		         return ConstantEnum.ERROR.toString();
			}catch (Exception e) {
				 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "物理机审批更新订单和实例信息时，操作数据库异常！", e);
		         resultPath = ConstantEnum.ERROR.toString();
		         return ConstantEnum.ERROR.toString();
			}
            
        }else{  // 返回失败，入失败库
             logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建物理机发送申请失败！");
             this.isSuccess = ConstantEnum.ERROR.toString();
             message = message + resp.getResultMessage();
             
             // 入失败库
             List<PMCreateFail> pmCreateFails = assemblePMCreateFail(pmReq, resp);
             List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
             for (PMCreateFail pmCreateFail : pmCreateFails) {
                 insertFailBatchVO.add(new BatchVO("ADD", "insertPMFail", pmCreateFail));
             }
           try {
               ibatisDAO.updateBatchData(insertFailBatchVO);
           } catch (SQLException e) {
               logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请物理机失败后入失败库发生数据库异常",
                       e);
               resultPath = ConstantEnum.ERROR.toString();
               return ConstantEnum.ERROR.toString();
           } catch (Exception e2) {
               logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请物理机失败后入失败库发生数据库异常",
                       e2);
               resultPath = ConstantEnum.ERROR.toString();
               return ConstantEnum.ERROR.toString();
           }
        }
           
        return ConstantEnum.SUCCESS.toString();
		
	}
	/**
	 * 组装请求信息
	 * @param pmReq PM请求消息
	 * @param subNetwork 子网
	 * @param pmInstanceInfo PM实例
	 */
	   private void assembleReq(PMCreateReq pmReq, String subNetwork,PMInstanceInfo pmInstanceInfo) {
		  
		   pmReq.setNum("1");
		   pmReq.setResourcePoolId(pmInstanceInfo.getResPoolId());
		   pmReq.setResourcePoolPartId(pmInstanceInfo.getResPoolPartId());
		   pmReq.setStandardId(pmInstanceInfo.getStandardId());
		   pmReq.setAppName(pmInstanceInfo.getAppName());
	        if (null == subNetwork) {
	        	pmReq.setSubNetwork("");
	        } else {
	        	pmReq.setSubNetwork(subNetwork);
	        }
	    }
	   /**
	    * 组装失败信息
	    * @param tempPMReq PM请求消息
	    * @param pmResp PM响应消息
	    * @return PM失败列表
	    */
	   private List<PMCreateFail> assemblePMCreateFail(PMCreateReq tempPMReq, PMCreateResp pmResp) {
	        List<PMCreateFail> pmCreateFails = new ArrayList<PMCreateFail>();
	        PMCreateFail pmCreateFail = new PMCreateFail();
	        pmCreateFail.setFailCause(pmResp.getResultMessage());
	        pmCreateFail.setFailCode(pmResp.getResultCode());
	        pmCreateFail.setResPoolId(tempPMReq.getResourcePoolId());
	        pmCreateFail.setResPoolPartId(tempPMReq.getResourcePoolPartId());
	        pmCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
	        pmCreateFail.setStandardId(tempPMReq.getStandardId());
	        pmCreateFail.setSubNetwork(tempPMReq.getSubNetwork());
	        pmCreateFail.setNum(tempPMReq.getNum());
	        pmCreateFails.add(pmCreateFail);
	        return pmCreateFails;
	    }
	   /**
	    * 组装实例信息
	    * @param pmInfoSet 返回的实例信息
	    * @param pmInstanceInfo 实例信息
	    */
	    private void assembleVMInstance(Map<String, String> pmInfoSet, PMInstanceInfo pmInstanceInfo) {

	        pmInstanceInfo.setPmId(pmInfoSet.get("PM_ID"));
	        pmInstanceInfo.setSubNetwork(pmInfoSet.get("SUBNETWORK"));
	        pmInstanceInfo.setAcceptTime(pmInfoSet.get("ACCEPT_TIME"));
	        pmInstanceInfo.setIp(pmInfoSet.get("IP"));
	        pmInstanceInfo.setPmUser(pmInfoSet.get("PM_USER"));
	        pmInstanceInfo.setPmPassword(pmInfoSet.get("PM_PASSWORD"));
			pmInstanceInfo.setPmName(pmInfoSet.get("PM_NAME"));
			pmInstanceInfo.setCreateTime(pmInfoSet.get("ACCEPT_TIME"));
			pmInstanceInfo.setUpdateTime(pmInfoSet.get("ACCEPT_TIME"));
			if(PMSTATUS.equals(pmStatusQuery)){
			    pmInstanceInfo.setStatus(PMStatus.RUNNING);
			}else{
			    pmInstanceInfo.setStatus(PMStatus.CREATING);
			}
			
	    }

	    
	/**
	 * 
	 * getOrderId 方法
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
	 * setPass 方法
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
	 * getPmCreate 方法
	 * @return 方法
	 */
	public PMCreate getPmCreate() {
		return pmCreate;
	}

	/**
	 * 
	 * setPmCreate 方法
	 * @param pmCreate 方法
	 */
	public void setPmCreate(PMCreate pmCreate) {
		this.pmCreate = pmCreate;
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
     * getPmStatusQuery 方法
     * @return 方法
     */
    public String getPmStatusQuery() {
        return pmStatusQuery;
    }

    /**
     * 
     * setPmStatusQuery  方法
     * @param pmStatusQuery 方法
     */
    public void setPmStatusQuery(String pmStatusQuery) {
        this.pmStatusQuery = pmStatusQuery;
    }

	
}
