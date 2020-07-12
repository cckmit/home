package com.neusoft.mid.cloong.web.page.user.order;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.ebs.core.EBSCreate;
import com.neusoft.mid.cloong.ebs.core.EBSModify;
import com.neusoft.mid.cloong.ipSegment.core.ApplyIpSegment;
import com.neusoft.mid.cloong.pm.core.PMCreate;
import com.neusoft.mid.cloong.pm.core.PMModify;
import com.neusoft.mid.cloong.service.BillingChargesService;
import com.neusoft.mid.cloong.vlan.core.VLANCreate;
import com.neusoft.mid.cloong.vm.core.VMCreate;
import com.neusoft.mid.cloong.vm.core.VMModify;
import com.neusoft.mid.cloong.vmbak.core.VmBakCreate;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 订单审批
 * 
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-1-29 下午01:35:45
 */

public class OrderBatchAidutAction extends BaseAction {

    /**
     * 序列号
     */
	private static final long serialVersionUID = -9211922812437971642L;
	
	/**
	 * 日志
	 */
	private static LogService logger = LogService
			.getLogger(OrderBatchAidutAction.class);

	/**
	 * 订单ID集合
	 */
	private String orderIds;

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
	 * 返回提示信息
	 */
	private String resultMsg;
	
	/**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    /**
     * 虚拟机创建接口
     */
    private VMCreate vmCreate;
    
    /**
     * 虚拟机修改接口
     */
    private VMModify vmModify;
    
    /**
     * 虚拟机创建接口
     */
    private PMCreate pmCreate;
    
    /**
     * 虚拟机修改接口
     */
    private PMModify pmModify;
    
	/**
	 * 虚拟备份任务创建接口
	 */
	private VmBakCreate vmBakCreate;
	
	/**
	 * 虚拟硬盘创建接口
	 */
	private EBSCreate ebsCreate;
	
	/**
	 * 虚拟硬盘创建接口
	 */
	private EBSModify ebsModify;
	/**
	 * IP段创建接口
	 */
	private ApplyIpSegment ipCreate;
	/**
	 * VLAN创建接口
	 */
	private VLANCreate vlanCreate;

//	private BillingChargesService billingService;
	
	/**
	 * 
	 * execute 执行订单批量审批
	 * @return 订单批量审批结果
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		String[] orderId = orderIds.split(",");
		int successNum = 0;
		int errorNum = 0;
		logger.info("开始进行批量审批操作!");
		for (int i = 0; i < orderId.length; i++) {
			OrderAuditAllAction auditAction = new OrderAuditAllAction();
			auditAction.setOrderId(orderId[i]);
			auditAction.setPass(pass);
			auditAction.setAuditInfo(auditInfo);
			try {
                orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("getOrderState", orderId[i]);
                auditAction.setStatus(orderInfo.getStatus());
    			auditAction.setCaseType(orderInfo.getCaseType());
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "批量审批时根据虚拟机订单id查询订单状态是待审还是修改待审出错！", e);
            }
			auditAction.setVmCreate(vmCreate);
			auditAction.setVmModify(vmModify);
			auditAction.setPmCreate(pmCreate);
			auditAction.setPmModify(pmModify);
			auditAction.setVmBakCreate(vmBakCreate);
			auditAction.setEbsCreate(ebsCreate);
			auditAction.setEbsModify(ebsModify);
			auditAction.setIpCreate(ipCreate);
			auditAction.setVlanCreate(vlanCreate);
			auditAction.setSeqCen(seqCen);
			//批量修改虚拟机vmModifyBatchId为parenetId
			if(null!=orderInfo.getVmModifyBatchId()&&!"".equals(orderInfo.getVmModifyBatchId())){
				auditAction.setParentId(orderInfo.getVmModifyBatchId());
			}
			auditAction.setIbatisDAO(ibatisDAO);
//			auditAction.setBillingService(billingService);
			String returnStr = auditAction.execute();
			if(ConstantEnum.SUCCESS.toString().equals(returnStr)){
				successNum ++ ;
			}else{
				errorNum ++ ;
			}
		}
		if(orderId.length == successNum){//全成功
			resultMsg = "订单批量审批成功！";
			resultPath = ConstantEnum.SUCCESS.toString();
		}else if(orderId.length == errorNum){//全失败
			resultMsg = "订单批量审批失败！";
			resultPath = ConstantEnum.ERROR.toString();
		}else{
			resultMsg = successNum+"条订单审批成功，" + errorNum+"条订单审批失败！";
			resultPath = ConstantEnum.FAILURE.toString();
		}
		logger.info("批量审批操作完成!");
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * @return the orderIds
	 */
	public String getOrderIds() {
		return orderIds;
	}

	/**
	 * @param orderIds the orderIds to set
	 */
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the auditInfo
	 */
	public String getAuditInfo() {
		return auditInfo;
	}

	/**
	 * @param auditInfo the auditInfo to set
	 */
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}

	/**
	 * @return the orderInfo
	 */
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	/**
	 * @param orderInfo the orderInfo to set
	 */
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	/**
	 * @return the orderAuditInfo
	 */
	public OrderAuditInfo getOrderAuditInfo() {
		return orderAuditInfo;
	}

	/**
	 * @param orderAuditInfo the orderAuditInfo to set
	 */
	public void setOrderAuditInfo(OrderAuditInfo orderAuditInfo) {
		this.orderAuditInfo = orderAuditInfo;
	}

	/**
	 * @return the resultPath
	 */
	public String getResultPath() {
		return resultPath;
	}

	/**
	 * @param resultPath the resultPath to set
	 */
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	/**
	 * @return the seqCen
	 */
	public CommonSequenceGenerator getSeqCen() {
		return seqCen;
	}

	/**
	 * @param seqCen the seqCen to set
	 */
	public void setSeqCen(CommonSequenceGenerator seqCen) {
		this.seqCen = seqCen;
	}

	/**
	 * @return the vmBakCreate
	 */
	public VmBakCreate getVmBakCreate() {
		return vmBakCreate;
	}

	/**
	 * @param vmBakCreate the vmBakCreate to set
	 */
	public void setVmBakCreate(VmBakCreate vmBakCreate) {
		this.vmBakCreate = vmBakCreate;
	}

	/**
	 * @return the ebsCreate
	 */
	public EBSCreate getEbsCreate() {
		return ebsCreate;
	}

	/**
	 * @param ebsCreate the ebsCreate to set
	 */
	public void setEbsCreate(EBSCreate ebsCreate) {
		this.ebsCreate = ebsCreate;
	}

	/**
	 * @return the ebsModify
	 */
	public EBSModify getEbsModify() {
		return ebsModify;
	}

	/**
	 * @param ebsModify the ebsModify to set
	 */
	public void setEbsModify(EBSModify ebsModify) {
		this.ebsModify = ebsModify;
	}

	/**
	 * @return the ipCreate
	 */
	public ApplyIpSegment getIpCreate() {
		return ipCreate;
	}

	/**
	 * @param ipCreate the ipCreate to set
	 */
	public void setIpCreate(ApplyIpSegment ipCreate) {
		this.ipCreate = ipCreate;
	}

	/**
	 * @return the vlanCreate
	 */
	public VLANCreate getVlanCreate() {
		return vlanCreate;
	}

	/**
	 * @param vlanCreate the vlanCreate to set
	 */
	public void setVlanCreate(VLANCreate vlanCreate) {
		this.vlanCreate = vlanCreate;
	}

	/**
	 * @return the vmCreate
	 */
	public VMCreate getVmCreate() {
		return vmCreate;
	}

	/**
	 * @param vmCreate the vmCreate to set
	 */
	public void setVmCreate(VMCreate vmCreate) {
		this.vmCreate = vmCreate;
	}

	/**
	 * @return the vmModify
	 */
	public VMModify getVmModify() {
		return vmModify;
	}

	/**
	 * @param vmModify the vmModify to set
	 */
	public void setVmModify(VMModify vmModify) {
		this.vmModify = vmModify;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**
	 * @return the pmCreate
	 */
	public PMCreate getPmCreate() {
		return pmCreate;
	}

	/**
	 * @param pmCreate the pmCreate to set
	 */
	public void setPmCreate(PMCreate pmCreate) {
		this.pmCreate = pmCreate;
	}

	/**
	 * @return the pmModify
	 */
	public PMModify getPmModify() {
		return pmModify;
	}

	/**
	 * @param pmModify the pmModify to set
	 */
	public void setPmModify(PMModify pmModify) {
		this.pmModify = pmModify;
	}

//	/**
//	 * @return the billingService
//	 */
//	public BillingChargesService getBillingService() {
//		return billingService;
//	}
//
//	/**
//	 * @param billingService the billingService to set
//	 */
//	public void setBillingService(BillingChargesService billingService) {
//		this.billingService = billingService;
//	}
}
