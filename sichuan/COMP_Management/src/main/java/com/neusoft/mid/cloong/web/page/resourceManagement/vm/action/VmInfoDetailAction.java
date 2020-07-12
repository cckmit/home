/*******************************************************************************
 * @(#)VmInfoDetailAction.java 2015年1月8日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.vm.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMOSDiskType;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.SubZero;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.EbsInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.PmInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.VmInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机详情
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2015年1月8日 上午10:41:07
 */
public class VmInfoDetailAction extends ResourceManagementBaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7990411968576857173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(VmInfoDetailAction.class);

    /**
     * req parameter
     */
    private String vmId = new String();

    /**
     * rep data
     */
    private VmInfo vmInfo = new VmInfo();
    
    private EbsInfo ebsInfo = new EbsInfo();
    
    private List<EbsInfo> ebsInfoList = new ArrayList<EbsInfo>();
    
    /**
     * rep data
     */
    private PmInfo pmInfo = new PmInfo();

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {
        	this.vmInfo = (VmInfo) this.ibatisDAO.getSingleRecord("queryVmInfo", vmInfo);
        	if (vmInfo.getOsType() != null){
        	    vmInfo.setOsType(VMOSDiskType.fromValue(vmInfo.getOsType()).toString());
        	}
        	if (vmInfo.getMemorySize() != null){
        	    vmInfo.setMemorySize(SubZero.subZero(vmInfo.getMemorySize()));
        	}
        	ebsInfo.setParentId(vmInfo.getVmId());
        	this.ebsInfoList = this.getPage("countQueryEbsInfosByParentId", "queryEbsInfosByParentId", ebsInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某台虚拟机数据时错误！原因：查询虚拟机时，数据库异常。", e);
        }
        try {
        	pmInfo.setPmId(vmInfo.getPmId());
        	this.pmInfo = (PmInfo) this.ibatisDAO.getSingleRecord("queryPmInfoFromVm", pmInfo);
        	pmInfo.setMemorySize(SubZero.subZero(pmInfo.getMemorySize()));
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "查询某台物理机数据时错误！原因：查询物理机时，数据库异常。", e);
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取vmId字段数据
     * @return Returns the vmId.
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 设置vmId字段数据
     * @param vmId The vmId to set.
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 获取vmInfo字段数据
     * @return Returns the vmInfo.
     */
    public VmInfo getVmInfo() {
        return vmInfo;
    }

    /**
     * 设置vmInfo字段数据
     * @param vmInfo The vmInfo to set.
     */
    public void setVmInfo(VmInfo vmInfo) {
        this.vmInfo = vmInfo;
    }

	/**
	 * @return the ebsInfoList
	 */
	public List<EbsInfo> getEbsInfoList() {
		return ebsInfoList;
	}

	/**
	 * @param ebsInfoList the ebsInfoList to set
	 */
	public void setEbsInfoList(List<EbsInfo> ebsInfoList) {
		this.ebsInfoList = ebsInfoList;
	}

	/**
	 * @return the ebsInfo
	 */
	public EbsInfo getEbsInfo() {
		return ebsInfo;
	}

	/**
	 * @param ebsInfo the ebsInfo to set
	 */
	public void setEbsInfo(EbsInfo ebsInfo) {
		this.ebsInfo = ebsInfo;
	}

	public PmInfo getPmInfo() {
		return pmInfo;
	}

	public void setPmInfo(PmInfo pmInfo) {
		this.pmInfo = pmInfo;
	}
    
}
