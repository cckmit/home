/*******************************************************************************
 * @(#)VmBakQueryListStateAction.java 2014-4-25
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.vmbak;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakTaskQueryReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakTaskQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakTaskQueryType;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMBakTaskStat;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatusQuery;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.ResultStatusInfo;
import com.neusoft.mid.cloong.web.page.console.vmbak.info.VmBakInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询当前登陆租户下，虚拟机备份列表信息
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-4-25 下午1:24:26
 */
public class VmBakQueryListStateAction extends BaseAction {
    
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * logger日志
     */
    private static LogService logger = LogService.getLogger(VmBakQueryListStateAction.class);

    /**
     * 返回列表
     */
    private List<ResultStatusInfo> resultStatusInfos = new ArrayList<ResultStatusInfo>();

    /**
     * 获取查询列表
     */
    private String queryStr;
    
    /**
     * 虚拟机备份任务状态查询
     */
    private VmBakStatusQuery vmBakStatusQuery;
    
    /**
     * 成功的接口响应码
     */
    private static final String SUCCESS_CODE = "00000000";

    @Override
    public String execute() {
        List<String> queryVmBakIds = new ArrayList<String>(); // 要查询的vmBakId列表
        Map<String, ResultStatusInfo> vmBakIdStatusMap = new HashMap<String, ResultStatusInfo>(); // 前台状态map为比较查出的虚拟机状态使用
        
        /* 将传入的json对象queryStr转成vmBakIds查询条件及比较使用的map集合 */
        if (null != queryStr && !"".equals(queryStr)) {
        	queryCondition(queryVmBakIds, vmBakIdStatusMap);
        }

        /* 调用接口查询虚拟机备份任务状态 */
        List<ResultStatusInfo> statusInfoList = new ArrayList<ResultStatusInfo>();
        for (int i = 0; i < queryVmBakIds.size(); i++) {
        	VmBakInstanceInfo vmBakInstanceInfo = this.getVmBakInfo(queryVmBakIds.get(i));
        	
        	/* 查询备份状态 */
        	if (null != vmBakInstanceInfo) {
        		RPPVMBakTaskQueryReq req = new RPPVMBakTaskQueryReq();
            	req.setResourcePoolId(vmBakInstanceInfo.getResPoolId()); // 资源池ID
            	req.setResourcePoolPartId(vmBakInstanceInfo.getResPoolPartId()); // 资源池分区
                req.setVmBackupId(queryVmBakIds.get(i)); // 虚拟机备份任务ID
                req.setVmBakTaskQueryType(VMBakTaskQueryType.BAK_TYPE); // 任务类型（0：备份）
                RPPVMBakTaskQueryResp resp = vmBakStatusQuery.queryVmBakStatus(req);

                if (resp.getResultCode().equals(SUCCESS_CODE)) { // 返回成功
                	ResultStatusInfo statusInfo = new ResultStatusInfo();
                	statusInfo.setId(queryVmBakIds.get(i));
                	statusInfo.setStatus(resp.getVmBakTaskStat().getValue());
                	statusInfo.setStatusText(VmBakStatus.getEnum(resp.getVmBakTaskStat().getValue()).getDesc());
                	statusInfoList.add(statusInfo);
                } else { // 返回失败
                	ResultStatusInfo statusInfo = new ResultStatusInfo();
                	statusInfo.setId(queryVmBakIds.get(i));
                	statusInfo.setStatus(VMBakTaskStat.ERROR.getValue());
                	statusInfo.setStatusText(VmBakStatus.getEnum(VMBakTaskStat.ERROR.getValue()).getDesc());
                	statusInfoList.add(statusInfo);
                	
                	errMsg = MessageFormat.format(getText("vmbakstatus.query.fail"), queryVmBakIds.get(i));
                	logger.error(CommonStatusCode.RUNTIME_EXCEPTION, errMsg
                            + "失败原因为：" + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
                }
        	}
        }

        /* 查询结果与前台传入状态比较, 不同的返回前台展示 */
        vmBakCompareStatus(statusInfoList, vmBakIdStatusMap);
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("虚拟机备份任务列表状态查询发生改变个数lenght:");
            sb.append(resultStatusInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 将查询到虚拟机备份状态的结果与界面传入的状态比较。 将状态不相同的添入返回集合vmResultStatusInfos中.
     * @param lsVmBakResultInfos  数据库查询出虚拟机备份状态
     * @param vmIdStatuss   前台传递的虚拟机备份状态
     */
    private void vmBakCompareStatus(List<ResultStatusInfo> statusInfoList,
            Map<String, ResultStatusInfo> vmBakIdStatusMap) {
        if (null != statusInfoList) {
            for (ResultStatusInfo statusInfo : statusInfoList) {
                String vmBakId = statusInfo.getId();
                String queryStatus = statusInfo.getStatus();
                if(vmBakIdStatusMap.get(vmBakId) == null){
                    continue;
                }
                //if (!queryStatus.equals(vmBakIdStatusMap.get(vmBakId).getStatus())) {
                    resultStatusInfos.add(statusInfo);
                //}

                /* 更新虚拟机备份任务状态 */
                //this.updateVmBakStatus(vmBakId, VmBakStatus.getEnum(queryStatus));
            }
        }
    }
    
    /**
     * 更新虚拟机备份任务状态
     * @param status 虚拟机备份任务状态
     */
    private void updateVmBakStatus(String vmBakId, VmBakStatus status) {
    	String userId = getCurrentUserId(); // session中获取用户ID
        VmBakInstanceInfo vmBakInfo = new VmBakInstanceInfo();
        vmBakInfo.setVmBakId(vmBakId);
        vmBakInfo.setStatus(status);
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
     * 转换列表及map
     * @param queryCaseIds 把JSON字符中的id放入list列表中
     * @param caseIdStatuss 把JSON字符转换成的对象放入Map集合中 key是id
     */
    private void queryCondition(List<String> queryCaseIds, Map<String, ResultStatusInfo> caseIdStatuss) {
        // 将前台传入的json字符串转换成对象集合
        List<ResultStatusInfo> queryInfos = null;
        try {
            JSONArray json = JSONArray.fromObject(queryStr);
            queryInfos = (List<ResultStatusInfo>) JSONArray.toCollection(json, ResultStatusInfo.class);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("vmbak.querylistStatus.fail"), e);
        }
        if (null != queryInfos) {
            for (ResultStatusInfo resultStatusInfo : queryInfos) {
                if (null != resultStatusInfo) {
                    queryCaseIds.add(resultStatusInfo.getId());
                    caseIdStatuss.put(resultStatusInfo.getId(), resultStatusInfo);
                }
            }
        }
    }
    
    /**
	 * 取得备份任务绑定的虚拟机信息
	 */
	private VmBakInstanceInfo getVmBakInfo(String vmBakId) {
		VmBakInstanceInfo vmBakInstanceInfo = null;
        try {
        	VmBakInstanceInfo vmBakInstanceInfoTemp = new VmBakInstanceInfo();
    		vmBakInstanceInfoTemp.setVmBakId(vmBakId);
    		
        	vmBakInstanceInfo = (VmBakInstanceInfo) ibatisDAO.getSingleRecord("queryVmBakDetailInfo", vmBakInstanceInfoTemp);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vmbak.query.fail"), vmBakId), e);
			this.addActionError(MessageFormat.format(getText("vmbak.query.fail"), vmBakId));
		}
        
        return vmBakInstanceInfo;
	}

    /**
     * 获取resultStatusInfos字段数据
     * @return Returns the resultStatusInfos.
     */
    public List<ResultStatusInfo> getResultStatusInfos() {
        return resultStatusInfos;
    }

    /**
     * 设置queryStr字段数据
     * @param queryStr The queryStr to set.
     */
    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

	public VmBakStatusQuery getVmBakStatusQuery() {
		return vmBakStatusQuery;
	}

	public void setVmBakStatusQuery(VmBakStatusQuery vmBakStatusQuery) {
		this.vmBakStatusQuery = vmBakStatusQuery;
	}

}
