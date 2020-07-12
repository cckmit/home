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
import com.neusoft.mid.cloong.ebs.core.EBSCreate;
import com.neusoft.mid.cloong.ebs.core.EBSCreateFail;
import com.neusoft.mid.cloong.ebs.core.EBSCreateReq;
import com.neusoft.mid.cloong.ebs.core.EBSCreateResp;
import com.neusoft.mid.cloong.ebs.core.EBSStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 订单审批
 * 
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-1-29 下午01:35:45
 */

public class EBSOrderAidutAction extends BaseAction {
    
    /**
     * 序列号
     */
	private static final long serialVersionUID = 4196853216651241774L;
	
	/**
     * 日志
     */
	private static LogService logger = LogService
			.getLogger(EBSOrderAidutAction.class);

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
	 * 虚拟硬盘创建接口
	 */
	private EBSCreate ebsCreate;

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
     * execute 执行EBS人工审批
     * @return EBS人工审批是否成功
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
	public String execute() {
	    
	    //对通过的订单发送创建资源请求，对不通过的订单删除已经存在的实例信息
        if(auditPass.equals(pass)){
             String message = sentEBSCreate();
             if(!ConstantEnum.SUCCESS.toString().equals(message)){
                 return ConstantEnum.ERROR.toString();
             }
        }else{
             String message = delEBSInstanceInfo();
             if(!ConstantEnum.SUCCESS.toString().equals(message)){
                 return ConstantEnum.ERROR.toString();
             }
        }
        //如果消息发送不成功，则提示用户，直接返回。
        if(!SUCCEESS_CODE.endsWith(isSuccess)){
            // this.addActionMessage(getText("user.order.send.fail"));
             resultPath = ConstantEnum.ERROR.toString();
             return ConstantEnum.ERROR.toString();
        }
        
		// 拼凑订单信息
		orderInfo = new OrderInfo();
		orderInfo.setOrderId(orderId);
		if("1".equals(pass)){
		    orderInfo.setStatus("3");//审批通过，则订单状态变成已生效
		}else{
		    orderInfo.setStatus(pass);
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
	private String delEBSInstanceInfo(){
		try {
			ibatisDAO.deleteData("delEBSInstanceInfo", orderId);
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
	private String sentEBSCreate(){
		//查询EBS实例信息
		EBSInstanceInfo eBSInstanceInfo;
		try {
			eBSInstanceInfo = (EBSInstanceInfo) ibatisDAO.getSingleRecord("queryEBSInstanceInfo", orderId);
		} catch (SQLException e1) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
					+ orderId + "查询实例信息时，操作数据库异常！", e1);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
					+ orderId + "查询实例信息时，操作数据库异常！", e);
			resultPath = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		EBSCreateReq ebsReq = new EBSCreateReq();
		assembleReq(ebsReq, eBSInstanceInfo);
		EBSCreateResp resp = new EBSCreateResp();//ebsCreate.createEBS(ebsReq);防止红叉
		if (resp.getResultCode().equals(SUCCEESS_CODE)) {
			// 拼凑实例信息
			assembleEBSInstance(resp, eBSInstanceInfo);
			BatchVO batchVOeBSInstanceInfo = new BatchVO("MOD",
					"updateEBSInstanceInfo", eBSInstanceInfo);
			List<BatchVO> batchVOs = new ArrayList<BatchVO>();
			batchVOs.add(batchVOeBSInstanceInfo);
			try {
				ibatisDAO.updateBatchData(batchVOs);
			} catch (SQLException e) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
						+ orderId + "更新实例信息时，操作数据库异常！", e);
				resultPath = ConstantEnum.ERROR.toString();
				return ConstantEnum.ERROR.toString();
			} catch (Exception e) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "订单编号为"
						+ orderId + "更新实例信息时，操作数据库异常！", e);
				resultPath = ConstantEnum.ERROR.toString();
				return ConstantEnum.ERROR.toString();
			}
		}else{
			 // 返回失败，入失败库
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建虚拟硬盘发送申请失败！");
            this.isSuccess = ConstantEnum.ERROR.toString();
            message = message + resp.getResultMessage();
          
            // 入失败库
            List<EBSCreateFail> ebsCreateFails = assembleEBSCreateFail(ebsReq, resp, eBSInstanceInfo);
            List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
            for (EBSCreateFail ebsCreateFail : ebsCreateFails) {
                insertFailBatchVO.add(new BatchVO("ADD", "insertEBSFail", ebsCreateFail));
            }
            try {
                ibatisDAO.updateBatchData(insertFailBatchVO);
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟硬盘失败后入失败库发生数据库异常",
                        e);
                return ConstantEnum.ERROR.toString();
            } catch (Exception e2) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "申请虚拟硬盘失败后入失败库发生数据库异常",
                        e2);
                return ConstantEnum.ERROR.toString();
            }
		}
		return ConstantEnum.SUCCESS.toString();
		
	}
	
	/**
	 * 
	 * assembleReq 组装请求信息
	 * @param ebsReq  请求信息
	 * @param eBSInstanceInfo ens实例信息
	 */
	private void assembleReq(EBSCreateReq ebsReq,
			EBSInstanceInfo eBSInstanceInfo) {
		ebsReq.setResourcePoolId(eBSInstanceInfo.getResPoolId());
		ebsReq.setResourcePoolPartId(eBSInstanceInfo.getResPoolPartId());
		ebsReq.setStandardId(eBSInstanceInfo.getStandardId());
		ebsReq.setEbsName(eBSInstanceInfo.getEbsName());
	}

	/**
	 * 
	 * assembleEBSInstance 组装EBS实例
	 * @param resp 响应消息
	 * @param ebsInstanceInfo EBS实例
	 */
	private void assembleEBSInstance(EBSCreateResp resp,
			EBSInstanceInfo ebsInstanceInfo) {

		ebsInstanceInfo.setEbsId(resp.getEbsId());
		ebsInstanceInfo.setStatus(EBSStatus.CREATED);
	}
	
	/**
	 * 
	 * assembleEBSCreateFail 组装EBS失败消息
	 * @param tempEBSReq ebs请求
	 * @param ebsResp ebs响应 
	 * @param eBSInstanceInfo ebs实例
	 * @return 失败列表
	 */
	private List<EBSCreateFail> assembleEBSCreateFail(EBSCreateReq tempEBSReq,
            EBSCreateResp ebsResp, EBSInstanceInfo eBSInstanceInfo) {
        List<EBSCreateFail> ebsCreateFails = new ArrayList<EBSCreateFail>();
        EBSCreateFail ebsCreateFail = new EBSCreateFail();
        ebsCreateFail.setFailCause(ebsResp.getResultMessage());
        ebsCreateFail.setFailCode(ebsResp.getResultCode());
        ebsCreateFail.setResPoolId(tempEBSReq.getResourcePoolId());
        ebsCreateFail.setResPoolPartId(tempEBSReq.getResourcePoolPartId());
        ebsCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
        ebsCreateFail.setStandardId(tempEBSReq.getStandardId());
        ebsCreateFail.setEbsName(tempEBSReq.getEbsName());
        ebsCreateFail.setCreateUser(eBSInstanceInfo.getCreateUser());
        ebsCreateFails.add(ebsCreateFail);
        return ebsCreateFails;
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
	 * getEbsCreate 方法
	 * @return 方法
	 */
	public EBSCreate getEbsCreate() {
		return ebsCreate;
	}

	/**
	 * 
	 * setEbsCreate 方法
	 * @param ebsCreate 方法
	 */
	public void setEbsCreate(EBSCreate ebsCreate) {
		this.ebsCreate = ebsCreate;
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
