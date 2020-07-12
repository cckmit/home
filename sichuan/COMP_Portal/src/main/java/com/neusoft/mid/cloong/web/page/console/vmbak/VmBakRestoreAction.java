/*******************************************************************************
 * @(#)VmBakRestoreAction.java 2014-2-20
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.vmbak;

import java.sql.SQLException;
import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakRestoreReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakRestoreResp;
import com.neusoft.mid.cloong.vmbak.core.VmBakRestore;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.vmbak.info.VmBakInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份恢复
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2014-2-20 下午4:57:18
 */
public class VmBakRestoreAction extends BaseAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 日志器
     */
    private static LogService logger = LogService.getLogger(VmBakRestoreAction.class);
    
    /**
     * 成功的接口响应码
     */
    private static final String SUCCESS_CODE = "00000000";

    /**
     * 虚拟机备份任务ID
     */
    private String vmBakId;
    
    /**
     * 虚拟机备份ID
     */
    private String vmBakInternalId;

    /**
     * 虚拟机备份任务详细信息
     */
    private VmBakInstanceInfo vmBakInfo;
    
    /**
     * 虚拟机备份恢复
     */
    private VmBakRestore vmBakRestore;
    
    /**
     * json返回标志
     */
    private String result;

    /**
     * 执行入口
     * @return 成功或失败
     */
    public String execute() {
    	/* 更新虚拟机备份任务状态为恢复中 */
    	//this.updateVmBakStatus();
        
    	/* 查询虚拟机备份任务信息 */
    	this.queryVmBakInfo();
    	
    	/* 虚拟机备份恢复 */
    	this.restoreVmBak();

        if (null != errMsg && !"".equals(errMsg)) {
        	result = ConstantEnum.FAILURE.toString();
        	return ConstantEnum.FAILURE.toString();
        }
        
        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }
    
    /**
     * 调用虚拟机备份恢复接口，恢复虚拟机备份
     * @return 虚拟机备份任务状态
     */
    private void restoreVmBak() {
    	/* 虚拟机备份恢复 */
    	RPPVMBakRestoreReq req = new RPPVMBakRestoreReq();
    	req.setResourcePoolId(vmBakInfo.getResPoolId()); // 资源池ID
    	req.setResourcePoolPartId(vmBakInfo.getResPoolPartId()); // 资源池分区ID
        req.setVmBackupId(vmBakId); // 虚拟机备份任务ID
        req.setVmBackupInternalId(vmBakInternalId); // 虚拟机备份ID
        RPPVMBakRestoreResp resp = vmBakRestore.restoreVmBak(req);

        if (resp.getResultCode().equals(SUCCESS_CODE)) { // 返回成功
        	/* 更新虚拟机备份恢复信息 */
        	this.updateVmBakRestoreInfo();
        } else { // 返回失败
        	errMsg = MessageFormat.format(getText("vmbak.restore.fail"), vmBakId);
        	logger.error(CommonStatusCode.RUNTIME_EXCEPTION, errMsg
                    + "失败原因为：" + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
        }
    }
    
    /**
     * 更新虚拟机备份恢复信息
     */
    private void updateVmBakRestoreInfo() {
        VmBakInstanceInfo vmBakInfo = new VmBakInstanceInfo();
        vmBakInfo.setVmBakId(vmBakId); // 备份任务ID
        //vmBakInfo.setStatus(VmBakStatus.RUNNING);
        vmBakInfo.setRestoreVmBakInternalId(vmBakInternalId); // 恢复备份ID
        vmBakInfo.setRestoreTime(DateParse.generateDateFormatyyyyMMddHHmmss()); // 恢复时间
        vmBakInfo.setUpdateUser(getCurrentUserId());
        vmBakInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        try {
        	int updateResult = ibatisDAO.updateData("updateVmBakRestoreInfo", vmBakInfo);
           
            if (updateResult == 1) {
            	logger.info(MessageFormat.format(getText("vmbak.restore.update.success"), vmBakId));
            } else {
            	errMsg = MessageFormat.format(getText("vmbak.restore.update.fail"), vmBakId);
            	logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg);
            }
        } catch (Exception e) {
        	errMsg = MessageFormat.format(getText("vmbak.restore.update.fail"), vmBakId);
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
        }
    }
    
    /**
     * 更新虚拟机备份任务状态
     * @param status 虚拟机备份任务状态
     */
    private void updateVmBakStatus() {
    	String userId = getCurrentUserId(); // session中获取用户ID
        VmBakInstanceInfo vmBakInfo = new VmBakInstanceInfo();
        vmBakInfo.setVmBakId(vmBakId);
        vmBakInfo.setStatus(VmBakStatus.ROLLBACKING);
        vmBakInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        vmBakInfo.setUpdateUser(userId);
        try {
        	int updateResult = ibatisDAO.updateData("updateVmBakStatus", vmBakInfo);
           
            if (updateResult == 1) {
            	logger.info(MessageFormat.format(getText("vmbakstatus.update.success"), vmBakId, vmBakInfo.getStatus().getDesc()));
            } else {
            	errMsg = MessageFormat.format(getText("vmbakstatus.update.fail"), vmBakId, vmBakInfo.getStatus().getDesc());
            	logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg);
            }
        } catch (Exception e) {
        	errMsg = MessageFormat.format(getText("vmbakstatus.update.fail"), vmBakId, vmBakInfo.getStatus().getDesc());
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
        }
    }
    
    /**
     * 查询虚拟机备份任务实例信息
     */
    private void queryVmBakInfo() {
    	vmBakInfo = new VmBakInstanceInfo();
        vmBakInfo.setVmBakId(vmBakId);

        if (logger.isDebugEnable()) {
            logger.debug("输入参数：" + vmBakInfo.toString());
        }
        try {
            vmBakInfo = (VmBakInstanceInfo) ibatisDAO.getSingleRecord("queryVmBakDetailInfo", vmBakInfo);
        } catch (SQLException e) {
        	errMsg = MessageFormat.format(getText("vmbak.query.fail"), vmBakId);
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
        }
        
        if (logger.isDebugEnable()) {
            logger.debug("输出结果：" + vmBakInfo.toString());
        }
    }

	public String getVmBakId() {
		return vmBakId;
	}

	public void setVmBakId(String vmBakId) {
		this.vmBakId = vmBakId;
	}

	public String getVmBakInternalId() {
		return vmBakInternalId;
	}

	public void setVmBakInternalId(String vmBakInternalId) {
		this.vmBakInternalId = vmBakInternalId;
	}

	public VmBakInstanceInfo getVmBakInfo() {
		return vmBakInfo;
	}

	public void setVmBakInfo(VmBakInstanceInfo vmBakInfo) {
		this.vmBakInfo = vmBakInfo;
	}

	public VmBakRestore getVmBakRestore() {
		return vmBakRestore;
	}

	public void setVmBakRestore(VmBakRestore vmBakRestore) {
		this.vmBakRestore = vmBakRestore;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
