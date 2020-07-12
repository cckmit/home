/*******************************************************************************
 * @(#)VMNameUpdateAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 更改虚拟机名称，返回JSON对象
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-8 下午01:14:31
 */
public class VMNameUpdateAction extends BaseAction {

    private static final long serialVersionUID = 5905281979052112854L;

    private static LogService logger = LogService.getLogger(VMNameUpdateAction.class);

    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 虚拟机编码
     */
    private String vmId;

    /**
     * 虚拟机名称
     */
    private String vmName;

    public String execute() {
        if (vmId == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, getText("vmid.isnull"));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        vmId = vmId.trim();
        VMInstanceInfo vmInstanceInfo = new VMInstanceInfo();
        vmInstanceInfo.setVmId(vmId);
        vmInstanceInfo.setVmName(vmName);
        vmInstanceInfo.setUpdateUser(getCurrentUserId());
        vmInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        int updateResult = 0;
        try {
            updateResult = ibatisDAO.updateData("updateVMSName", vmInstanceInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("vmname.update.fail"), vmId), e);
            this.addActionError(MessageFormat.format(getText("vmname.update.fail"), vmId));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (updateResult != 1) {
            logger.info(MessageFormat.format(getText("vmid.query.notexist"), vmId));
            this.addActionError(MessageFormat.format(getText("vmid.query.notexist"), vmId));
            result = ConstantEnum.DELETE.toString();
            return ConstantEnum.FAILURE.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("更新编码为[").append(vmId).append("]的名称成功，名称为[").append(vmName).append("]\n");
        logger.info(sb.toString());
        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
