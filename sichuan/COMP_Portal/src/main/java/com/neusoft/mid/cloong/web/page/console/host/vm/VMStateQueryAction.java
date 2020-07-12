/*******************************************************************************
 * @(#)VMStateQueryAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.service.VMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.StatusInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机状态，返回JSON对象
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 下午04:16:46
 */
public class VMStateQueryAction extends BaseAction {

    private static LogService logger = LogService.getLogger(VMStateQueryAction.class);

    private static final long serialVersionUID = 2018352213213434408L;

    /**
     * 虚拟机编码
     */
    private String vmId;

    /**
     * 虚拟机状态
     */
    private StatusInfo status;

    private String createTime = "";

    private String expireTime = "";

    /**
     * 前台通过result判断处理结果
     */
    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 虚拟机操作状态服务
     */
    private VMStatusService vmStatusService;

    public String execute() {
        if (vmId == null) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, getText("vmid.isnull"));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        vmId = vmId.trim();
        VMInstanceInfo vmInfo = null;
        try {
            vmInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord("getVmDetail", vmId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("vmstatus.query.fail"), vmId), e);
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (vmInfo == null) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("vmstatus.query.fail"), vmId));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        String statusCode = vmInfo.getStatus().getCode();
        String statusMessage = vmInfo.getStatus().getDesc();
        status = new StatusInfo();
        status.setStatusCode(statusCode);
        status.setStatusMessage(statusMessage);
        if (statusCode.equals(VMStatus.DELETED.getCode())) {
            logger.info(MessageFormat.format(getText("vmid.query.notexist"), vmId));
            result = ConstantEnum.DELETE.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (vmInfo.getCreateTime() != null) {
            createTime = DateParse.parse(vmInfo.getCreateTime());
        }
        if (vmInfo.getExpireTime() != null) {
            expireTime = DateParse.parse(vmInfo.getExpireTime());
        }
        switch (vmInfo.getStatus()) {
        case DELETED:
        case OPERATE_FAIL:
        case PAUSE:
        case STOP:
        case RUNNING:
        case SENDERROR:
        case STATUSERROR:
            result = ConstantEnum.SUCCESS.toString();
            break;
        default: {
            result = ConstantEnum.INTERMEDIATE.toString();
        }
        }
        vmStatusService.addVMStatus(vmInfo.getVmId(), vmInfo.getStatus());
        return ConstantEnum.SUCCESS.toString();
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public StatusInfo getStatus() {
        return status;
    }

    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public void setVmStatusService(VMStatusService vmStatusService) {
        this.vmStatusService = vmStatusService;
    }
}
