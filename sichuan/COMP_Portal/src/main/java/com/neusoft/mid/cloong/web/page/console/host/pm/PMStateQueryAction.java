/*******************************************************************************
 * @(#)PMStateQueryAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.pm;

import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.pm.core.PMStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.service.PMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.StatusInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询物理机状态，返回JSON对象
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-16 上午10:17:19
 */
public class PMStateQueryAction extends BaseAction {

    private static LogService logger = LogService.getLogger(PMStateQueryAction.class);

    private static final long serialVersionUID = 2018352213213434408L;

    /**
     * 物理机编码
     */
    private String pmId;

    /**
     * 物理机状态
     */
    private StatusInfo status;

    private String createTime = "";

    private String expireTime = "";

    /**
     * 前台通过result判断处理结果
     */
    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 物理机操作状态服务
     */
    private PMStatusService pmStatusService;

    public String execute() {
        if (pmId == null) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, getText("pmid.isnull"));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        pmId = pmId.trim();
        PMInstanceInfo pmInfo = null;
        try {
            pmInfo = (PMInstanceInfo) ibatisDAO.getSingleRecord("getPmDetail", pmId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("pmstatus.query.fail"), pmId), e);
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (pmInfo == null) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("pmstatus.query.fail"), pmId));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        String statusCode = pmInfo.getStatus().getCode();
        String statusMessage = pmInfo.getStatus().getDesc();
        status = new StatusInfo();
        status.setStatusCode(statusCode);
        status.setStatusMessage(statusMessage);
        if (statusCode.equals(PMStatus.DELETED.getCode())) {
            logger.info(MessageFormat.format(getText("pmid.query.notexist"), pmId));
            result = ConstantEnum.DELETE.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (pmInfo.getCreateTime() != null) {
            createTime = DateParse.parse(pmInfo.getCreateTime());
        }
        if (pmInfo.getExpireTime() != null) {
            expireTime = DateParse.parse(pmInfo.getExpireTime());
        }
        switch (pmInfo.getStatus()) {
        case DELETED:
        case OPERATE_FAIL:
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
        pmStatusService.addPMStatus(pmInfo.getPmId(), pmInfo.getStatus());
        return ConstantEnum.SUCCESS.toString();
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

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public void setPmStatusService(PMStatusService pmStatusService) {
        this.pmStatusService = pmStatusService;
    }
}
