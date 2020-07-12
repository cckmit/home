package com.neusoft.mid.cloong.web.page.vmbak.order;

import java.sql.SQLException;
import java.text.MessageFormat;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.vmbak.info.VmBakResultInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建虚拟机备份任务跳转前的Action
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2015-3-5 下午05:08:14
 */
public class VmBakApplyAction extends ResourceManagementBaseAction {
    /**
     * 虚拟机ID
     */
    private String vmId;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 错误提示信息
     */
    private String errorMsg;

    private final LogService logger = LogService.getLogger(VmBakApplyAction.class);

    private static final long serialVersionUID = 5201828322749702209L;
    
    private String resultFlag = ConstantEnum.FAILURE.toString();

    public String execute() {
        // 由虚拟机详情页发起的创建虚拟机备份任务，需要校验当前虚拟机是否已存在备份任务
        if (vmId != null && !("").equals(vmId)) {
            // 校验备份任务是否存在
            VmBakResultInfo vmBakInfo = new VmBakResultInfo();
            vmBakInfo.setVmId(vmId);
            vmBakInfo.setVmBakStatus(VmBakStatus.DELETED.getCode());
            try {
                if (ibatisDAO.getCount("countVmBakUserListAll", vmBakInfo) > 0) {
                    errorMsg = getText("vmbak.vmInfo.createVmBakTask.fail");
                    resultFlag = ConstantEnum.FAILURE.toString();
                    return ConstantEnum.FAILURE.toString();
                } else {// 如果不存在备份任务，那么查询虚拟机名称用于创建备份任务时显示
                    vmName = ((VMInstanceInfo) ibatisDAO.getSingleRecord("getVmDetail", vmId))
                            .getVmName();
                }
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                        MessageFormat.format(getText("vmbak.vmInfo.query.fail"), getCurrentUserId()), e);
                this.addActionError(MessageFormat.format(getText("vmbak.vmInfo.query.fail"), getCurrentUserId()));
                resultFlag = ConstantEnum.ERROR.toString();
                return ConstantEnum.ERROR.toString();
            }
        }
        resultFlag = ConstantEnum.SUCCESS.toString();
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

}
