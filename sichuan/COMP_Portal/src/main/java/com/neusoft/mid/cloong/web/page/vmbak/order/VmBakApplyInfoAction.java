/*******************************************************************************
 * @(#)VmBakApplyInfoAction.java 2015-3-4
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.vmbak.order;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakCreateResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakMode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakType;
import com.neusoft.mid.cloong.vmbak.core.VmBakCreate;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.vmbak.info.VmBakInstanceInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 提交创建虚拟机备份任务Action
 * 
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2015-3-4 下午04:18:41
 */
public class VmBakApplyInfoAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = 1L;

	/** 日志记录者 */
	private static LogService logger = LogService.getLogger(VmBakApplyInfoAction.class);

	/**
     * 执行备份的虚拟机ID
     */
	private String vmId = "";

	/**
     * 备份任务名称
     */
	private String vmBakName = "";

	/**
     * 备份周期
     */
	private int backupCyc = 0;

	/**
     * 备份保留时间
     */
	private int backStoreTime = 0;

	/**
     * 备份任务开始时间
     */
	private String backupStartTime = "";

	/**
     * 备注
     */
	private String description = "";

	/**
     * 设置是否人工审批 0：自动 1：人工
     */
    private String audit = "";
    
    /**
     * 默认自动审批
     */
    private static final String AUTO = "0";
	
    /**
     * 序列号生成器
     */
	private SequenceGenerator sequenceGenerator;
	
	/**
     * 虚拟机备份任务创建接口
     */
    private VmBakCreate vmBakCreate;
    
    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";
    
    /**
     * 资源池ID
     */
	private String resPoolId = "";

	/**
     * 资源池分区ID
     */
    private String resPoolPartId = "";
    
    /**
     * 父订单ID
     */
    private String parentId = "";
    
    /**
     * 订单ID
     */
    private String orderId = "";
    
    /**
     * 实例ID
     */
    private String caseId = "";
    
    /**
     * 虚拟机名称（备选虚拟机，查询用）
     */
    private String vmName = "";
    
    /**
	 * 虚拟机实例信息（备选虚拟机）
	 */
	private List<VmBakInstanceInfo> vmResultInfos;

	/**
	 * execute Action执行方法
	 * @return 结果码
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() {
		if (logger.isDebugEnable()) {
            logger.debug("vmId = " + vmId);
            logger.debug("vmBakName = " + vmBakName);
            logger.debug("backupCyc = " + backupCyc);
            logger.debug("backStoreTime = " + backStoreTime);
            logger.debug("backupStartTime = " + backupStartTime);
            logger.debug("description = " + description);
        }
		
		// session中获取用户ID
        String userId = getCurrentUserId();

        parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.BK.getParentCode());
        orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.BK.toString());
        caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.BK.toString());
        
        if (logger.isDebugEnable()) {
            logger.debug("orderId = " + orderId);
            logger.debug("caseId = " + caseId);
            logger.debug("parentId = " + parentId);
        }

        /* 取得备份任务绑定的虚拟机信息 */
        this.getVmInfo();

        /* 调用接口创建虚拟机备份任务 */
        RPPVMBakCreateReq req = new RPPVMBakCreateReq();
        RPPVMBakCreateResp resp = new RPPVMBakCreateResp();
        
        resp.setResultCode(SUCCEESS_CODE); // 不是自动审批时
        if (AUTO.equals(audit)) { // 自动审批
            // 发送申请至接口
            this.assembleReq(req);
            resp = vmBakCreate.createVmBak(req);
        }
        
        if (resp.getResultCode().equals(SUCCEESS_CODE)) { // 返回成功
        	try {
        		List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        		
        		/* 拼凑订单信息  */
                OrderInfo orderInfo = new OrderInfo();
                this.assembleOrder(userId, orderInfo);

        		/* 订单信息入库 */
                updateBatchVO.add(new BatchVO("ADD", "createVmBakOrder", orderInfo));
				//ibatisDAO.insertData("createVmBakOrder", orderInfo);

				/* 拼凑实例信息 */
				VmBakInstanceInfo vmBakInstanceInfo = new VmBakInstanceInfo();
				this.assembleVmBakInstance(userId, resp, vmBakInstanceInfo);

	        	/* 实例信息入库 */
				updateBatchVO.add(new BatchVO("ADD", "createVmBakInstanceInfo", vmBakInstanceInfo));
				//ibatisDAO.insertData("createVmBakInstanceInfo", vmBakInstanceInfo);
				
				/* 信息入库 */
				ibatisDAO.updateBatchData(updateBatchVO);
			} catch (Exception e) {
                // 如果记入数据库失败，打印error日志，记录虚拟机备份任务订单发生异常，返回浏览虚拟机备份任务列表页面，提示“提交创建虚拟机备份任务订单失败！”。
                logger.error(VmBakStatusCode.CREATEPMORDER_EXCEPTION_CODE, getText("vmbak.applyinfo.fail"), e);
                this.addActionError(getText("vmbak.applyinfo.fail"));
                errMsg = getText("vmbak.applyinfo.fail");
                return ConstantEnum.FAILURE.toString();
            }
        } else { // 返回失败
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建虚拟机备份任务失败！");
            this.addActionError(getText("vmbak.applyinfo.sendcreatevmbak.fail"));
            errMsg = getText("vmbak.applyinfo.sendcreatevmbak.fail");
            return ConstantEnum.FAILURE.toString();
        }

        if (AUTO.equals(audit)) {
            logger.debug("创建虚拟机备份任务申请发送成功！");
            msg = "创建虚拟机备份任务申请发送成功！";
        } else {
            logger.debug("创建虚拟机备份任务申请提交成功，请等待审核！");
            msg = "创建虚拟机备份任务申请提交成功，请等待审核！";
        }

		return ConstantEnum.SUCCESS.toString();
	}
	
	/**
	 * 取得备份任务绑定的虚拟机信息
	 */
	private void getVmInfo() {
        VMInstanceInfo vmInstanceInfo = new VMInstanceInfo();
        vmInstanceInfo.setVmId(vmId);
        try {
        	vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord("queryVmInfoByVmId", vmInstanceInfo);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vmbak.vm.query.fail"), vmId), e);
			this.addActionError(MessageFormat.format(getText("vmbak.vm.query.fail"), vmId));
		}

        resPoolId = vmInstanceInfo.getResPoolId();
        resPoolPartId = vmInstanceInfo.getResPoolPartId();
        appId = vmInstanceInfo.getAppId();
	}

	/**
	 * 拼凑订单信息
	 */
	private void assembleOrder(String userId, OrderInfo orderInfo) {
        
		orderInfo.setOrderId(orderId); // 订单ID
        orderInfo.setParentId(parentId); // 父订单ID
        if (AUTO.equals(audit)) { // 自动审批
            orderInfo.setStatus("3"); // 审批通过
            orderInfo.setEffectiveTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        } else {
            orderInfo.setStatus("0"); // 待审
        }
        orderInfo.setCaseId(caseId); // 实例ID
        orderInfo.setCaseType("4"); // 实例类型
        orderInfo.setAppId(appId); // 业务ID
        orderInfo.setResPoolId(resPoolId); // 资源池ID
        orderInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setCreateUser(userId);
        orderInfo.setUpdateUser(userId);
    }
	
	/**
	 * 拼凑请求报文信息
	 */
	private void assembleReq(RPPVMBakCreateReq req) {
		req.setResourcePoolId(resPoolId); // 资源池ID
		req.setResourcePoolPartId(resPoolPartId); // 资源池分区ID
		req.setVmId(vmId); // 虚拟机ID
		req.setVmBackupType(VMBakType.INCREMENT_BACK); // 备份类型（2：增量备份）
		req.setVmBackupMode(VMBakMode.CYCLE_BACK); // 备份方式（2：周期备份）
		req.setBackupCyc(backupCyc); // 备份周期
		req.setBackStoreTime(backStoreTime); // 备份保留时间
		req.setCaseId(caseId); // 实例ID

//		/* 设置备份任务执行的开始时间，请求报文发送时间要比备份开始时间提前五分钟，否则就把开始时间延迟到下一周期 */
//		Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.MINUTE, 5);
//        String compareTime = DateParse.generateDateFormatLong(cal.getTime());
//
//		if (compareTime.compareTo(backupStartTime) > 0) { // （请求报文发送时间 + 5分钟）> 备份开始时间，要把开始时间延迟到下一周期
//			Date startDate = DateParse.generateDateFromLongString(backupStartTime);
//			cal.setTime(startDate);
//			if (backupCyc == 1) {
//				cal.add(Calendar.DAY_OF_MONTH, 1);
//			} else if (backupCyc == 7) {
//				cal.add(Calendar.DAY_OF_MONTH, 7);
//			}
//			backupStartTime = DateParse.generateDateFormatLong(cal.getTime());
//		}
		
		req.setBackupStartTime(DateParse.generateDateFromLongString(this.calBackupStartTime(backupStartTime)));
    }
	
	/**
	 * 计算备份任务执行的开始时间，请求报文发送时间要比备份开始时间提前五分钟，否则就把开始时间延迟到下一周期
	 */
	private String calBackupStartTime(String backupStartTime) {
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 5);
        String compareTime = DateParse.generateDateFormatLong(cal.getTime());
        
		while (compareTime.compareTo(backupStartTime) > 0) { // （请求报文发送时间 + 5分钟）> 备份开始时间，要把开始时间延迟到下一周期
			Date startDate = DateParse.generateDateFromLongString(backupStartTime);
			cal.setTime(startDate);
			if (backupCyc == 1) { // 备份周期：每天
				cal.add(Calendar.DAY_OF_MONTH, 1);
			} else if (backupCyc == 7) { // 备份周期：每周
				cal.add(Calendar.DAY_OF_MONTH, 7);
			}
			backupStartTime = DateParse.generateDateFormatLong(cal.getTime());
		}
		return backupStartTime;
	}
	
	/**
	 * 拼凑虚拟机备份任务实例信息
	 */
	private void assembleVmBakInstance(String userId, RPPVMBakCreateResp resp, VmBakInstanceInfo vmBakInstanceInfo) {
        vmBakInstanceInfo.setCaseId(caseId);
        if (AUTO.equals(audit)) { // 自动审批
        	vmBakInstanceInfo.setVmBakId(resp.getVmBackupId());
        	SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmss" );
            vmBakInstanceInfo.setAcceptTime(sdf.format(resp.getBackupTime()));
            vmBakInstanceInfo.setStatus(VmBakStatus.PREPARE);
        } else {
        	vmBakInstanceInfo.setStatus(VmBakStatus.TO_CREATE);
        }
        vmBakInstanceInfo.setVmBakName(vmBakName);
        vmBakInstanceInfo.setBackupCyc(backupCyc);
        vmBakInstanceInfo.setBackStoreTime(backStoreTime);
        vmBakInstanceInfo.setBackupStartTime(DateParse.parseTime(backupStartTime));
        vmBakInstanceInfo.setVmId(vmId);
        vmBakInstanceInfo.setResPoolId(resPoolId);
        vmBakInstanceInfo.setResPoolPartId(resPoolPartId);
        vmBakInstanceInfo.setOrderId(orderId);
        vmBakInstanceInfo.setDescription(description);
        vmBakInstanceInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        vmBakInstanceInfo.setCreateUser(userId);
        vmBakInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        vmBakInstanceInfo.setUpdateUser(userId);
    }
	
	/**
	 * ajax查询虚拟机备份任务，备选虚拟机
	 */
	public String queryVmList() {
		/* 取得用户信息 */
		UserBean user = getCurrentUser();
		String userId = getCurrentUserId();

		VmBakInstanceInfo vmBakInstanceInfo = new VmBakInstanceInfo();
		vmBakInstanceInfo.setAppIdList(user.getAppIdList()); // 获取用户绑定的业务
		vmBakInstanceInfo.setAppId(appId); // 左侧树选择的业务
		vmBakInstanceInfo.setVmStatus(VMStatus.DELETED.getCode()); // 虚拟机状态不等.删除
		if (null != vmName && !vmName.isEmpty()) {
			vmBakInstanceInfo.setVmName(vmName.toLowerCase()); // 虚拟机名称
		}

		try {
			/* 查询虚拟机 */
			vmResultInfos = getPage("countVmList", "queryVmList", vmBakInstanceInfo, PageTransModel.ASYNC);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vmbak.vm.queryList.fail"), userId), e);
			this.addActionError(MessageFormat.format(getText("vmbak.vm.queryList.fail"), userId));
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getVmBakName() {
		return vmBakName;
	}

	public void setVmBakName(String vmBakName) {
		this.vmBakName = vmBakName;
	}

	public int getBackupCyc() {
		return backupCyc;
	}

	public void setBackupCyc(int backupCyc) {
		this.backupCyc = backupCyc;
	}

	public int getBackStoreTime() {
		return backStoreTime;
	}

	public void setBackStoreTime(int backStoreTime) {
		this.backStoreTime = backStoreTime;
	}

	public String getBackupStartTime() {
		return backupStartTime;
	}

	public void setBackupStartTime(String backupStartTime) {
		this.backupStartTime = backupStartTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public SequenceGenerator getSequenceGenerator() {
		return sequenceGenerator;
	}

	public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	public VmBakCreate getVmBakCreate() {
		return vmBakCreate;
	}

	public void setVmBakCreate(VmBakCreate vmBakCreate) {
		this.vmBakCreate = vmBakCreate;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public List<VmBakInstanceInfo> getVmResultInfos() {
		return vmResultInfos;
	}

	public void setVmResultInfos(List<VmBakInstanceInfo> vmResultInfos) {
		this.vmResultInfos = vmResultInfos;
	}

}
