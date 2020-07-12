package com.neusoft.mid.cloong.web.page.console.host.pm;

import java.text.MessageFormat;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMManager;
import com.neusoft.mid.cloong.host.pm.core.PMOperatorType;
import com.neusoft.mid.cloong.host.pm.core.PMStatus;
import com.neusoft.mid.cloong.host.pm.core.PmOperatorReq;
import com.neusoft.mid.cloong.host.pm.core.PmOperatorResp;
import com.neusoft.mid.cloong.service.PMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMOperatorErrorInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 操作物理机，包括：启动，停止，重启
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:53:49
 */
public class PMOperatorAction extends BaseAction {

    private static final long serialVersionUID = -6401973328299498877L;

    private static LogService logger = LogService.getLogger(PMOperatorAction.class);

    private String result = ConstantEnum.FAILURE.toString();

    private String resultMessage;

    /**
     * 操作后映射到页面的pm状态
     */
    private String statusDesc;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 物理机是否被删除的标准
     */
    private static final String IS_DELETE = "0";

    /**
     * 物理机管理者
     */
    private PMManager pmManager;

    /**
     * 物理机操作状态判断服务
     */
    private PMStatusService pmStatusService;

    /**
     * 操作流水号生成器
     */
    private SequenceGenerator gen;

    /**
     * 物理机编码
     */
    private String pmId;

    private String resourcePoolId;

    private String resourcePoolPartId;

    /**
     * pm状态为创建中
     */
    private static final String INPOSCODE = "0";

    /**
     * 运行状态开关 0：pm状态设置成创建中.非0:pm状态设置成运行中 spring注入
     */
    private String mediacy = "0";

    public void setMediacy(String mediacy) {
        this.mediacy = mediacy;
    }

    public String stopPm() {
        if (!validatePMId(pmId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!judgePmOperator(pmId, PMOperatorType.PM_STOP)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!operatorPm(pmId, PMOperatorType.PM_STOP, this.resourcePoolId, this.resourcePoolPartId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!updatePMStatus(pmId, PMStatus.PROCESSING)) {
            return ConstantEnum.FAILURE.toString();
        }
        /*
         * if (mediacy.equals(INPOSCODE)) { if (!updatePMStatus(pmId, PMStatus.STOPPING)) { return
         * ConstantEnum.FAILURE.toString(); } } else { if (!updatePMStatus(pmId, PMStatus.STOP)) {
         * return ConstantEnum.FAILURE.toString(); } } result = ConstantEnum.SUCCESS.toString();
         */
        return ConstantEnum.SUCCESS.toString();
    }

    public String startPm() {
        if (!validatePMId(pmId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!judgePmOperator(pmId, PMOperatorType.PM_START)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!operatorPm(pmId, PMOperatorType.PM_START, this.resourcePoolId, this.resourcePoolPartId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!updatePMStatus(pmId, PMStatus.PROCESSING)) {
            return ConstantEnum.FAILURE.toString();
        }
        /*
         * if (mediacy.equals(INPOSCODE)) { if (!updatePMStatus(pmId, PMStatus.STARTING)) { return
         * ConstantEnum.FAILURE.toString(); } } else { if (!updatePMStatus(pmId, PMStatus.RUNNING))
         * { return ConstantEnum.FAILURE.toString(); } } result = ConstantEnum.SUCCESS.toString();
         */
        return ConstantEnum.SUCCESS.toString();
    }

    public String rebootPm() {
        if (!validatePMId(pmId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!judgePmOperator(pmId, PMOperatorType.PM_REBOOT)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!operatorPm(pmId, PMOperatorType.PM_REBOOT, this.resourcePoolId,
                this.resourcePoolPartId)) {
            return ConstantEnum.FAILURE.toString();
        }
        if (!updatePMStatus(pmId, PMStatus.PROCESSING)) {
            return ConstantEnum.FAILURE.toString();
        }
        /*
         * if (mediacy.equals(INPOSCODE)) { if (!updatePMStatus(pmId, PMStatus.RESTATRING)) { return
         * ConstantEnum.FAILURE.toString(); } } else { if (!updatePMStatus(pmId, PMStatus.RUNNING))
         * { return ConstantEnum.FAILURE.toString(); } } result = ConstantEnum.SUCCESS.toString();
         */
        return ConstantEnum.SUCCESS.toString();
    }

    private boolean validatePMId(String pmId) {
        if (pmId == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, getText("pmid.isnull"));
            result = ConstantEnum.ERROR.toString();
            return false;
        }
        return true;
    }

    private boolean updatePMStatus(String pmId, PMStatus status) {
        PMInstanceInfo pmInfo = new PMInstanceInfo();
        pmInfo.setPmId(pmId);
        pmInfo.setStatus(status);
        pmInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        pmInfo.setUpdateUser(getCurrentUserId());
        int updateResult = 0;
        try {
            updateResult = ibatisDAO.updateData("updatePMStatus", pmInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "更新编码为:" + pmInfo.getPmId()
                    + "的物理机状态为" + status.getDesc() + "失败！", e);
            result = ConstantEnum.ERROR.toString();
            return false;
        }
        if (updateResult != 1) {
            logger.info(MessageFormat.format(getText("pmid.query.notexist"), pmInfo.getPmId()));
            result = ConstantEnum.DELETE.toString();
            return false;
        }
        pmStatusService.addPMStatus(pmId, status); // 操作状态记录DB同时记录内存
        // pm操作的页面上的中间状态展示开始---->
        switch (pmStatusService.getPMStatus(pmId)) {
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
        // pm操作的页面上的中间状态展示结束---->
        return true;
    }

    private boolean operatorPm(String pmId, PMOperatorType operator, String resourcePoolId,
            String resourcePoolPartId) {

        PmOperatorReq req = new PmOperatorReq();
        req.setPmId(pmId);
        req.setType(operator);
        req.setResourcePoolId(resourcePoolId);
        req.setResourcePoolPartId(resourcePoolPartId);
        req.setSerialNum(gen.generatorPMSerialNum(operator.getCode()));
        PmOperatorResp resp = pmManager.operatePm(req);
        if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
            logger.error(
                    CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText(operator.getErrorKey()), pmId) + "失败原因为："
                            + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
            result = ConstantEnum.FAILURE.toString();
            return false;
        }
        return true;
    }

    private boolean judgePmOperator(String pmId, PMOperatorType operator) {
        PMOperatorErrorInfo errorInfo = new PMOperatorErrorInfo();
        Holder<PMOperatorErrorInfo> holder = new Holder<PMOperatorErrorInfo>(errorInfo);
        if (!pmStatusService.judgePMOperator(pmId, operator, holder)) {
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

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public PMManager getPmManager() {
        return pmManager;
    }

    public void setPmManager(PMManager pmManager) {
        this.pmManager = pmManager;
    }

    public PMStatusService getPmStatusService() {
        return pmStatusService;
    }

    public void setPmStatusService(PMStatusService pmStatusService) {
        this.pmStatusService = pmStatusService;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

}
