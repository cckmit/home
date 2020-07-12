/*******************************************************************************
 * @(#)VmBakDelAction.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.vmbak;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMBakDeleteResp;
import com.neusoft.mid.cloong.vmbak.core.VmBakDel;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.vmbak.info.VmBakInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机删除备份操作 返回 json
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-23 下午03:07:06
 */
public class VmBakDelAction extends BaseAction {

    private static final long serialVersionUID = 1788639715970933829L;

    private static LogService logger = LogService.getLogger(VmBakDelAction.class);

    /**
     * 成功的接口响应码
     */
    private static final String SUCCESS_CODE = "00000000";

    /**
     * 虚拟机备份任务删除
     */
    private VmBakDel vmBakDel;

    /**
     * 虚拟机备份任务编码
     */
    private String vmBakId;
    
    /**
     * 虚拟机备份编码
     */
    private String vmBakInternalId;

    /**
     * 返回提示消息
     */
    private String mes;
    
    /**
     * 资源池ID
     */
	private String resPoolId = "";

	/**
     * 资源池分区ID
     */
    private String resPoolPartId = "";
    
    /**
     * json返回标志
     */
    private String result;

    @Override
    public String execute() {
    	/* 取得虚拟机备份任务信息 */
        this.getVmBakInfo();
        
        /* 调用删除虚拟机备份接口 */
        this.delVmBak();
        
        if (null != errMsg && !"".equals(errMsg)) {
        	result = ConstantEnum.FAILURE.toString();
        	return ConstantEnum.FAILURE.toString();
        }
        
        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    /**删除备份
     * @param vmBakId 虚拟机备份ID
     * @param req 请求信息
     * @return 成功或失败
     */
    private void delVmBak() {
    	RPPVMBakDeleteReq req = new RPPVMBakDeleteReq();
    	req.setResourcePoolId(resPoolId); // 资源池ID
    	req.setResourcePoolPartId(resPoolPartId); // 资源池分区ID
        req.setVmBackupId(vmBakId); // 虚拟机备份任务ID
        req.setVmBakInternalId(vmBakInternalId); // 虚拟机备份ID

        RPPVMBakDeleteResp resp = vmBakDel.delVmBak(req);
        if (!resp.getResultCode().equals(SUCCESS_CODE)) { // 返回失败
        	errMsg = MessageFormat.format(getText("vmbak.delete.fail"), vmBakInternalId);
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, MessageFormat.format(getText("vmbak.delete.fail"), vmBakInternalId)
                    + "失败原因为：" + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
        }
    }
    
    /**
	 * 取得备份任务绑定的虚拟机信息
	 */
	private void getVmBakInfo() {
		VmBakInstanceInfo vmBakInstanceInfo = new VmBakInstanceInfo();
		vmBakInstanceInfo.setVmBakId(vmBakId);
        try {
        	vmBakInstanceInfo = (VmBakInstanceInfo) ibatisDAO.getSingleRecord("queryVmBakDetailInfo", vmBakInstanceInfo);
		} catch (Exception e) {
			errMsg = MessageFormat.format(getText("vmbak.query.fail"), vmBakId);
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vmbak.query.fail"), vmBakId), e);
		}

        resPoolId = vmBakInstanceInfo.getResPoolId();
        resPoolPartId = vmBakInstanceInfo.getResPoolPartId();
	}

	public VmBakDel getVmBakDel() {
		return vmBakDel;
	}

	public void setVmBakDel(VmBakDel vmBakDel) {
		this.vmBakDel = vmBakDel;
	}

	public String getVmBakId() {
		return vmBakId;
	}

	public void setVmBakId(String vmBakId) {
		this.vmBakId = vmBakId;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getVmBakInternalId() {
		return vmBakInternalId;
	}

	public void setVmBakInternalId(String vmBakInternalId) {
		this.vmBakInternalId = vmBakInternalId;
	}
}
