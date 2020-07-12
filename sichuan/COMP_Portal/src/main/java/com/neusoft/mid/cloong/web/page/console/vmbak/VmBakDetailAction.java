/*******************************************************************************
 * @(#)VmBakDetailAction.java 2014-2-20
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.vmbak;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakListQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakListQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakListQueryResp.VMBakInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakTaskQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakTaskQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakTaskQueryType;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakTaskStat;
import com.neusoft.mid.cloong.vmbak.core.VmBakListQuery;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatusQuery;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.Page;
import com.neusoft.mid.cloong.web.page.common.PageHelper;
import com.neusoft.mid.cloong.web.page.common.PageToDisplayPerModel;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.vmbak.info.VmBakInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.vmbak.info.VmBakupInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机备份任务详细信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2014-2-20 下午4:57:18
 */
public class VmBakDetailAction extends ResourceManagementBaseAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 日志器
     */
    private static LogService logger = LogService.getLogger(VmBakDetailAction.class);
    
    /**
     * 成功的接口响应码
     */
    private static final String SUCCESS_CODE = "00000000";

    /**
     * 虚拟机备份任务实例ID
     */
    private String caseId;
    
    /**
     * 虚拟机备份任务ID
     */
    private String vmBakId;

    /**
     * 虚拟机备份任务详细信息
     */
    private VmBakInstanceInfo vmBakInfo;
    
    /**
     * 虚拟机备份任务状态查询
     */
    private VmBakStatusQuery vmBakStatusQuery;
    
    /**
     * 虚拟机备份列表查询
     */
    private VmBakListQuery vmBakListQuery;
    
    /**
     * 虚拟机备份List
     */
    private List<VmBakupInfo> backupList = new ArrayList<VmBakupInfo>();

//    /**
//     * 备份状态
//     */
//    private VmBakStatus bakupStatus = null ; // 备份状态
//    
//    /**
//     * 备份状态
//     */
//    private VmBakStatus restoreStatus = null ; // 恢复状态
    
    /**
     * 开始时间(查询条件)
     */
    private String startTime = "";
    
    /**
     * 结束时间(查询条件)
     */
    private String endTime = "";

    /**
     * 执行入口
     * @return 成功或失败
     */
    public String execute() {
    	/* 查询虚拟机备份任务实例信息 */
    	if (null != caseId && !"".equals(caseId)) {
    		this.queryVmBakInfoByCaseId();
    	}
    	
//    	else { // 概况进入详情
//    		this.queryVmBakInfo();
//    	}

    	//vmBakInfo.setBakupStatus(vmBakInfo.getStatus().getCode());
    	
//    	/* 查询虚拟机备份任务状态 */
//        if (!VmBakStatus.TO_CREATE.equals(vmBakInfo.getStatus())) {
//        	this.queryVmBakStatus();
//        } else {
//        	bakupStatus = VmBakStatus.TO_CREATE;
//        	vmBakInfo.setBakupStatus(bakupStatus);
//        }

        if (null != errMsg && !"".equals(errMsg)) {
        	this.addActionError(errMsg);
        	return ConstantEnum.FAILURE.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }
    
    /**
     * 调用查询虚拟机备份任务状态接口，查询虚拟机备份任务状态
     */
    public String vmBakQueryDetailStateByAjax() {
    	/* 查询备份任务信息 */
    	this.queryVmBakInfo();
    	
    	/* 查询备份状态 */
    	RPPVMBakTaskQueryReq req = new RPPVMBakTaskQueryReq();
    	req.setResourcePoolId(vmBakInfo.getResPoolId()); // 资源池ID
    	req.setResourcePoolPartId(vmBakInfo.getResPoolPartId()); // 资源池分区ID
        req.setVmBackupId(vmBakInfo.getVmBakId()); // 虚拟机备份任务ID
        req.setVmBakTaskQueryType(VMBakTaskQueryType.BAK_TYPE); // 任务类型（0：备份）
        RPPVMBakTaskQueryResp resp = vmBakStatusQuery.queryVmBakStatus(req);

        if (resp.getResultCode().equals(SUCCESS_CODE)) { // 返回成功
        	vmBakInfo.setBakupStatus(resp.getVmBakTaskStat().getValue());
        	vmBakInfo.setBakupStatusText(VmBakStatus.getEnum(resp.getVmBakTaskStat().getValue()).getDesc());
        	//bakupStatus = VmBakStatus.getEnum(resp.getVmBakTaskStat().getValue());
        } else { // 返回失败
        	vmBakInfo.setBakupStatus(VMBakTaskStat.ERROR.getValue());
        	vmBakInfo.setBakupStatusText(VmBakStatus.getEnum(VMBakTaskStat.ERROR.getValue()).getDesc());
        	//bakupStatus = VmBakStatus.getEnum(VMBakTaskStat.ERROR.getValue());
        	errMsg = MessageFormat.format(getText("vmbakstatus.query.fail"), vmBakInfo.getVmBakId());
        	logger.error(CommonStatusCode.RUNTIME_EXCEPTION, errMsg
                    + "失败原因为：" + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
        }
        //vmBakInfo.setBakupStatus(bakupStatus);

        /* 查询恢复状态 */
        if (null != vmBakInfo.getRestoreVmBakInternalId() && !"".equals(vmBakInfo.getRestoreVmBakInternalId())) {
        	req = new RPPVMBakTaskQueryReq();
            req.setResourcePoolId(vmBakInfo.getResPoolId()); // 资源池ID
        	req.setResourcePoolPartId(vmBakInfo.getResPoolPartId()); // 资源池分区ID
            req.setVmBackupId(vmBakInfo.getVmBakId());
            req.setVmBakTaskQueryType(VMBakTaskQueryType.RESOTRE_TYPE); // 任务类型（1：恢复）
    		resp = vmBakStatusQuery.queryVmBakStatus(req);

    		if (resp.getResultCode().equals(SUCCESS_CODE)) { // 返回成功
    			vmBakInfo.setRestoreStatus(resp.getVmBakTaskStat().getValue());
            	vmBakInfo.setRestoreStatusText(VmBakStatus.getEnum(resp.getVmBakTaskStat().getValue()).getDesc());
    			//restoreStatus = VmBakStatus.getEnum(resp.getVmBakTaskStat().getValue());
    		} else {
    			vmBakInfo.setRestoreStatus(VMBakTaskStat.ERROR.getValue());
            	vmBakInfo.setRestoreStatus(VmBakStatus.getEnum(VMBakTaskStat.ERROR.getValue()).getDesc());
    			//restoreStatus = VmBakStatus.getEnum(VMBakTaskStat.ERROR.getValue());
    			errMsg = MessageFormat.format(getText("vmbakstatus.query.fail"), vmBakInfo.getVmBakId());
    			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, errMsg
                        + "失败原因为：" + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
    		}
    		//vmBakInfo.setRestoreStatus(restoreStatus);
        }
        
        return ConstantEnum.SUCCESS.toString();
    }
    
    /**
     * 调用查询虚拟机备份列表接口，查询虚拟机备份列表
     * @return 虚拟机备份列表
     */
    public String queryVmBakListByAjax() {
    	/* 查询虚拟机备份任务实例信息 */
        this.queryVmBakInfo();

    	RPPVMBakListQueryReq req = new RPPVMBakListQueryReq();
    	req.setResourcePoolId(vmBakInfo.getResPoolId()); // 资源池ID
    	req.setResourcePoolPartId(vmBakInfo.getResPoolPartId()); // 资源池分区ID
        req.setVmBackupId(vmBakId);
        RPPVMBakListQueryResp resp = vmBakListQuery.queryVmBakList(req);

        List<VMBakInfo> backupListResult = null;
        if (resp.getResultCode().equals(SUCCESS_CODE)) { // 返回成功
        	backupListResult = resp.getBackupList();
        } else { // 返回失败
        	errMsg = MessageFormat.format(getText("vmbaklist.query.fail"), vmBakId);
        	logger.error(CommonStatusCode.RUNTIME_EXCEPTION, errMsg
                    + "失败原因为：" + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
        }

        /* 根据查询条件处理数据 */
        if ((null == startTime || startTime.isEmpty()) && (null == endTime || endTime.isEmpty())) { // 开始时间和结束时间都为空
        	for (VMBakInfo vmBakInfoTemp : backupListResult) {
        		this.setVmBakupInfo(vmBakInfoTemp);
            }
        } else if ((null != startTime && !startTime.isEmpty()) && (null == endTime || endTime.isEmpty())) { // 页面只填写了开始时间
        	for (VMBakInfo vmBakInfoTemp : backupListResult) {
            	String generationTimeTemp = DateParse.generateDateFormatLong(vmBakInfoTemp.getGenerationTime());
            	if (generationTimeTemp.compareTo(startTime) >= 0) {
            		this.setVmBakupInfo(vmBakInfoTemp);
            	}
            }
        } else if ((null == startTime || startTime.isEmpty()) && (null != endTime && !endTime.isEmpty())) { // 页面只填写了结束时间
        	for (VMBakInfo vmBakInfoTemp : backupListResult) {
            	String generationTimeTemp = DateParse.generateDateFormatLong(vmBakInfoTemp.getGenerationTime());
            	if (generationTimeTemp.compareTo(endTime) <= 0) {
            		this.setVmBakupInfo(vmBakInfoTemp);
            	}
            }
        } else { // 页面上开始时间和结束时间都填写了
        	for (VMBakInfo vmBakInfoTemp : backupListResult) {
            	String generationTimeTemp = DateParse.generateDateFormatLong(vmBakInfoTemp.getGenerationTime());
            	if (generationTimeTemp.compareTo(startTime) >= 0 && generationTimeTemp.compareTo(endTime) <= 0) {
            		this.setVmBakupInfo(vmBakInfoTemp);
            	}
            }
        }

        /* 分页 */
        Page pageObj = new Page(getPage(), backupList.size(), getPageSize(),
                getUrl(), getParam(), getAsyncJSPageMethod());
        this.setPageBar(PageHelper.getPageBar(pageObj, PageTransModel.ASYNC,
                PageToDisplayPerModel.PAGESIZE_10_DISPLAY));
        if (backupList.size() > getPageSize()) {
            int lastIndex = pageObj.getStartOfPage() + getPageSize();
            if (lastIndex > backupList.size()) lastIndex = backupList.size();
            backupList = backupList.subList(pageObj.getStartOfPage(), lastIndex);
        }

        return ConstantEnum.SUCCESS.toString();
    }
    
    /**
     * 查询虚拟机备份任务实例信息
     */
    private void setVmBakupInfo(VMBakInfo vmBakInfoTemp) {
    	VmBakupInfo vmBakupInfo = new VmBakupInfo();
    	vmBakupInfo.setVmBakInternalId(vmBakInfoTemp.getVmBakInternalId());
		vmBakupInfo.setStoreSize(vmBakInfoTemp.getStoreSize().toString());
		vmBakupInfo.setGenerationTime(DateParse.generateDateFormatLong(vmBakInfoTemp.getGenerationTime()));
		backupList.add(vmBakupInfo);
    }

    /**
     * 查询虚拟机备份任务实例信息
     */
    private void queryVmBakInfoByCaseId() {
    	vmBakInfo = new VmBakInstanceInfo();
        vmBakInfo.setCaseId(caseId);

        if (logger.isDebugEnable()) {
            logger.debug("输入参数：" + vmBakInfo.toString());
        }
        try {
            vmBakInfo = (VmBakInstanceInfo) ibatisDAO.getSingleRecord("queryVmBakDetailInfoByCaseId", vmBakInfo);
            fomatResultData(vmBakInfo);
        } catch (SQLException e) {
        	errMsg = MessageFormat.format(getText("vmbak.query.fail"), caseId);
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
        }
        
        if (logger.isDebugEnable()) {
            logger.debug("输出结果：" + vmBakInfo.toString());
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
            if(null != vmBakInfo && !("").equals(vmBakInfo)){
                fomatResultData(vmBakInfo);
            }
        } catch (SQLException e) {
        	errMsg = MessageFormat.format(getText("vmbak.query.fail"), caseId);
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
        }
    }
    
    /**
     * 格式化生效时间、到期时间、状态显示汉字
     * @param vmBakInstanceInfo 待格式化的信息
     * @param ls要格式化的列表
     */
    private void fomatResultData(VmBakInstanceInfo vmBakInstanceInfo) {
    	if (!"".equals(vmBakInstanceInfo.getBackupStartTime() == null ? "" : vmBakInstanceInfo
                .getBackupStartTime())) {
            vmBakInstanceInfo.setBackupStartTime(DateParse.parse(vmBakInstanceInfo.getBackupStartTime()));
        }
        if (!"".equals(vmBakInstanceInfo.getAcceptTime() == null ? "" : vmBakInstanceInfo
                .getAcceptTime())) {
            vmBakInstanceInfo.setAcceptTime(DateParse.parse(vmBakInstanceInfo.getAcceptTime()));
        }
        if (!"".equals(vmBakInstanceInfo.getCreateTime() == null ? "" : vmBakInstanceInfo
                .getCreateTime())) {
            vmBakInstanceInfo.setCreateTime(DateParse.parse(vmBakInstanceInfo.getCreateTime()));
        }
        if (!"".equals(vmBakInstanceInfo.getUpdateTime() == null ? "" : vmBakInstanceInfo
                .getUpdateTime())) {
            vmBakInstanceInfo.setUpdateTime(DateParse.parse(vmBakInstanceInfo.getUpdateTime()));
        }
        if (!"".equals(vmBakInstanceInfo.getRestoreTime() == null ? "" : vmBakInstanceInfo
                .getRestoreTime())) {
            vmBakInstanceInfo.setRestoreTime(DateParse.parse(vmBakInstanceInfo.getRestoreTime()));
        }
    }

	public VmBakInstanceInfo getVmBakInfo() {
		return vmBakInfo;
	}

	public void setVmBakInfo(VmBakInstanceInfo vmBakInfo) {
		this.vmBakInfo = vmBakInfo;
	}

	public VmBakStatusQuery getVmBakStatusQuery() {
		return vmBakStatusQuery;
	}

	public void setVmBakStatusQuery(VmBakStatusQuery vmBakStatusQuery) {
		this.vmBakStatusQuery = vmBakStatusQuery;
	}

	public VmBakListQuery getVmBakListQuery() {
		return vmBakListQuery;
	}

	public void setVmBakListQuery(VmBakListQuery vmBakListQuery) {
		this.vmBakListQuery = vmBakListQuery;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<VmBakupInfo> getBackupList() {
		return backupList;
	}

	public void setBackupList(List<VmBakupInfo> backupList) {
		this.backupList = backupList;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getVmBakId() {
		return vmBakId;
	}

	public void setVmBakId(String vmBakId) {
		this.vmBakId = vmBakId;
	}

}
