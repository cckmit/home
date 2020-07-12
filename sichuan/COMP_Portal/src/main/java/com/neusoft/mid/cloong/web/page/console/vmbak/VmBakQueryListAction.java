/*******************************************************************************
 * @(#)VmBakQueryListAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.vmbak;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.vmbak.info.VmBakResultInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**查询当前登陆租户下，虚拟机备份任务列表信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午06:58:42
 */
public class VmBakQueryListAction extends ResourceManagementBaseAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5471800875775849067L;

    /**
     * 日志器
     */
    private static LogService logger = LogService.getLogger(VmBakQueryListAction.class);

    /**
     * 虚拟机备份实例信息
     */
    private List<VmBakResultInfo> vmBakResultInfos = null;
    
    /**
     * 备份状态Map
     */
    private Map<String, String> statusMap = null;
    
    /**
     * 虚拟机名称(查询条件)
     */
    private String vmName = "";
    
    /**
     * 备份状态(查询条件)
     */
    private String status = "";
    
    /**
     * 开始时间(查询条件)
     */
    private String startTime = "";
    
    /**
     * 结束时间(查询条件)
     */
    private String endTime = "";
    
    /**
     * JS返回结果
     */
    private String result = ConstantEnum.FAILURE.toString();

    @SuppressWarnings("unchecked")
    @Override
    public String execute() {
        if (logger.isDebugEnable()) {
            logger.debug("vmName=" + vmName);
            logger.debug("status=" + status);
            logger.debug("startTime=" + startTime);
            logger.debug("endTime=" + endTime);
        }
        if(null != errMsg && !"".equals(errMsg)){
            this.addActionError(errMsg);
        }
        
        /* 设备备份状态下拉列表 */
        this.setSelectStatusMap();

        String userId = getCurrentUserId(); // session中获取用户ID

        /* 取得当前用户绑定的业务ID */
        List<String> appIdList = getCurrentUser().getAppIdList();

        VmBakResultInfo vmBakResultInfo = new VmBakResultInfo();
        if (null != vmName && !vmName.isEmpty()) {
        	vmBakResultInfo.setVmName(vmName);
        }
        if (null != startTime && !startTime.isEmpty()) {
        	vmBakResultInfo.setStartTime(DateParse.parseTime(startTime));
        }
        if (null != endTime && !endTime.isEmpty()) {
        	vmBakResultInfo.setEndTime(DateParse.parseTime(endTime));
        }
        if (null != status && !status.isEmpty()) {
        	vmBakResultInfo.setStatus(VmBakStatus.getEnum(status));
        }
        if (null != appId && !appId.isEmpty()) { // 父类里有该属性
        	vmBakResultInfo.setAppId(appId);
        }

        vmBakResultInfo.setAppIdList(appIdList);

        // 虚拟机备份状态不等.删除
        vmBakResultInfo.setVmBakStatus(VmBakStatus.DELETED.getCode());

        vmBakResultInfos = this.getPage("countVmBakUserListAll", "queryVmBakUserListAll", vmBakResultInfo);
        fomatResultData(vmBakResultInfos);

        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("租户：").append(userId);
            sb.append("虚拟机备份列表长度为:");
            sb.append("lenght:").append(vmBakResultInfos.size());
            logger.debug(sb.toString());
        }

        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }
    
    /**
     * 资源视图概况查询虚拟机备份任务
     */
    public String queryVmListForOverview() {
        /* 取得当前用户绑定的业务ID */
        List<String> appIdList = getCurrentUser().getAppIdList();

        VmBakResultInfo vmBakResultInfo = new VmBakResultInfo();
        vmBakResultInfo.setAppIdList(appIdList);

        // 虚拟机备份状态不等.删除
        vmBakResultInfo.setVmBakStatus(VmBakStatus.DELETED.getCode());

        try {
			vmBakResultInfos = ibatisDAO.getData("queryVmBakUserListAll", vmBakResultInfo);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vmbak.queryList.fail"), getCurrentUserId()),
					e);
			result = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 格式化创建时间、备份任务周期显示汉字
     * @param ls要格式化的列表
     */
    private void fomatResultData(List<VmBakResultInfo> ls) {
        for (VmBakResultInfo vmBakResultInfo : ls) {
            if(!"".equals(vmBakResultInfo.getCreateTime()==null?"":vmBakResultInfo.getCreateTime())){
                vmBakResultInfo.setCreateTime(DateParse.parse(vmBakResultInfo.getCreateTime()));
            }
        }
    }
    
    /**
     * 设备备份状态下拉列表
     */
    private void setSelectStatusMap() {
    	statusMap = new LinkedHashMap<String, String>();
    	statusMap.put(VmBakStatus.PREPARE.getCode(), VmBakStatus.PREPARE.getDesc());
    	statusMap.put(VmBakStatus.CREATING.getCode(), VmBakStatus.CREATING.getDesc());
    	statusMap.put(VmBakStatus.RUNNING.getCode(), VmBakStatus.RUNNING.getDesc());
    	statusMap.put(VmBakStatus.CREATE_FAIL.getCode(), VmBakStatus.CREATE_FAIL.getDesc());
    	statusMap.put(VmBakStatus.STATUS_EXCEPTION.getCode(), VmBakStatus.STATUS_EXCEPTION.getDesc());
    	statusMap.put(VmBakStatus.TASK_WAIT.getCode(), VmBakStatus.TASK_WAIT.getDesc());
    	statusMap.put(VmBakStatus.TO_CREATE.getCode(), VmBakStatus.TO_CREATE.getDesc());
    }

	public List<VmBakResultInfo> getVmBakResultInfos() {
		return vmBakResultInfos;
	}

	public void setVmBakResultInfos(List<VmBakResultInfo> vmBakResultInfos) {
		this.vmBakResultInfos = vmBakResultInfos;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}
}
