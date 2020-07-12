/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.text.MessageFormat;

import javax.xml.ws.Holder;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMManager;
import com.neusoft.mid.cloong.host.vm.core.VMOperatorType;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.host.vm.core.VmOperatorReq;
import com.neusoft.mid.cloong.host.vm.core.VmOperatorResp;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.service.VMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMOperatorErrorInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 操作虚拟机，包括：启动，暂停，恢复，停止，重启
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-22 下午03:11:07
 */
public class VMOperatorAction extends BaseAction {

    private static final long serialVersionUID = -6401973328299498877L;

    private static LogService logger = LogService.getLogger(VMOperatorAction.class);

    private String result = ConstantEnum.FAILURE.toString();

    private String resultMessage;

    /**
     * 操作后映射到页面的vm状态
     */
    private String statusDesc;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 虚拟机是否被删除的标准
     */
    private static final String IS_DELETE = "0";

    /**
     * 虚拟机管理者
     */
    private VMManager vmManager;

    /**
     * 虚拟机操作状态判断服务
     */
    private VMStatusService vmStatusService;

    /**
     * 操作流水号生成器
     */
    private SequenceGenerator gen;

    /**
     * 虚拟机编码
     */
    private String vmId;

    private String resourcePoolId;

    private String resourcePoolPartId;

    public String pauseVm() {
        if (!validateVMId(vmId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!judgeVmOperator(vmId, VMOperatorType.VM_PAUSE)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!operatorVm(vmId, VMOperatorType.VM_PAUSE, this.resourcePoolId, this.resourcePoolPartId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!updateVMStatus(vmId, VMStatus.PROCESSING)) {
            return ConstantEnum.FAILURE.toString();
        }
        // result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String stopVm() {
        if (!validateVMId(vmId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!judgeVmOperator(vmId, VMOperatorType.VM_STOP)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!operatorVm(vmId, VMOperatorType.VM_STOP, this.resourcePoolId, this.resourcePoolPartId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!updateVMStatus(vmId, VMStatus.PROCESSING)) {
            return ConstantEnum.FAILURE.toString();
        }
        // result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String startVm() {
        if (!validateVMId(vmId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!judgeVmOperator(vmId, VMOperatorType.VM_START)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!operatorVm(vmId, VMOperatorType.VM_START, this.resourcePoolId, this.resourcePoolPartId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!updateVMStatus(vmId, VMStatus.PROCESSING)) {
            return ConstantEnum.FAILURE.toString();
        }
        // result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String resumeVm() {
        if (!validateVMId(vmId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!judgeVmOperator(vmId, VMOperatorType.VM_RESUME)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!operatorVm(vmId, VMOperatorType.VM_RESUME, this.resourcePoolId,
                this.resourcePoolPartId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!updateVMStatus(vmId, VMStatus.PROCESSING)) {
            return ConstantEnum.FAILURE.toString();
        }
        // result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String rebootVm() {
        if (!validateVMId(vmId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!judgeVmOperator(vmId, VMOperatorType.VM_REBOOT)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!operatorVm(vmId, VMOperatorType.VM_REBOOT, this.resourcePoolId,
                this.resourcePoolPartId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!updateVMStatus(vmId, VMStatus.PROCESSING)) {
            return ConstantEnum.FAILURE.toString();
        }
        // result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    private boolean validateVMId(String vmId) {
        if (vmId == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, getText("vmid.isnull"));
            result = ConstantEnum.ERROR.toString();
            return false;
        }
        return true;
    }

    private boolean updateVMStatus(String vmId, VMStatus status) {
        /**
         * 用户ID，从session中获取
         */
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        VMInstanceInfo vmInfo = new VMInstanceInfo();
        vmInfo.setVmId(vmId);
        vmInfo.setStatus(status);
        vmInfo.setUpdateUser(userId);
        vmInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        int updateResult = 0;
        try {
            updateResult = ibatisDAO.updateData("updateVMStatus", vmInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "更新编码为:" + vmInfo.getVmId()
                    + "的虚拟机状态为" + status.getDesc() + "失败！", e);
            result = ConstantEnum.ERROR.toString();
            return false;
        }
        if (updateResult != 1) {
            logger.info(MessageFormat.format(getText("vmid.query.notexist"), vmInfo.getVmId()));
            result = ConstantEnum.DELETE.toString();
            return false;
        }
        vmStatusService.addVMStatus(vmId, status); // 操作状态记录DB同时记录内存
        // vm操作的页面上的中间状态展示开始---->
        switch (vmStatusService.getVMStatus(vmId)) {
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
        statusDesc = status.getDesc();
        // vm操作的页面上的中间状态展示结束---->
        return true;
    }

    private boolean operatorVm(String vmId, VMOperatorType operator, String resourcePoolId,
            String resourcePoolPartId) {

        VmOperatorReq req = new VmOperatorReq();
        req.setVmId(vmId);
        req.setType(operator);
        req.setResourcePoolId(resourcePoolId);
        req.setResourcePoolPartId(resourcePoolPartId);
        req.setSerialNum(gen.generatorVMSerialNum(operator.getCode()));
        VmOperatorResp resp = vmManager.operateVm(req);
        if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
            logger.error(
                    CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText(operator.getErrorKey()), vmId) + "失败原因为："
                            + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
            result = ConstantEnum.FAILURE.toString();
            return false;
        }
        return true;
    }

    private boolean judgeVmOperator(String vmId, VMOperatorType operator) {
        VMOperatorErrorInfo errorInfo = new VMOperatorErrorInfo();
        Holder<VMOperatorErrorInfo> holder = new Holder<VMOperatorErrorInfo>(errorInfo);
        if (!vmStatusService.judgeVMOperator(vmId, operator, holder)) {
            if (holder.value.getErrorCode().equals(IS_DELETE)) {
                result = ConstantEnum.DELETE.toString();
            } else {
                result = ConstantEnum.STATUSINVALID.toString();
                resultMessage = holder.value.getErrorMessage();
            }
            return false;
        }
        return true;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public VMManager getVmManager() {
        return vmManager;
    }

    public void setVmManager(VMManager vmManager) {
        this.vmManager = vmManager;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    public void setVmStatusService(VMStatusService vmStatusService) {
        this.vmStatusService = vmStatusService;
    }

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * 获取statusDesc字段数据
     * @return Returns the statusDesc.
     */
    public String getStatusDesc() {
        return statusDesc;
    }

    /**
     * 设置statusDesc字段数据
     * @param statusDesc The statusDesc to set.
     */
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
