/*******************************************************************************
 * @(#)PMDetailInfoAction.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.pm;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询物理机详细信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午06:18:42
 */
public class PMDetailInfoAction extends BaseAction {

    private static final long serialVersionUID = -3246558237673423698L;

    private static LogService logger = LogService.getLogger(PMDetailInfoAction.class);

    private static final String SUCCESS = "success";

    private static final String FAILURE = "failure";

    private static final String ERROR = "error";

    /**
     * 物理机实例信息
     */
    private PMInstanceInfo pmInstanceInfo;
    
    /**
     * 物理机实例ID
     */
    private String caseId;

    private String pmId;

    private String errMeg;

    public String getErrMeg() {
        return errMeg;
    }

    public void setErrMeg(String errMeg) {
        this.errMeg = errMeg;
    }

    @Override
    public String execute() {
        try {
            if(caseId != null && !("").equals(caseId)){
                pmInstanceInfo = (PMInstanceInfo) ibatisDAO.getSingleRecord("getPmDetail", caseId);
                pmInstanceInfo.setNetList(ibatisDAO.getData("getPMNet", caseId));
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("pm.query.fail"), caseId), e);
            this.addActionError(MessageFormat.format(getText("pm.query.fail"), caseId));
            return ERROR;
        }
        if (null == pmInstanceInfo) {
            logger.info("物理机实例信息不存在！");
            errMeg = "物理机实例信息不存在！";
            return FAILURE;
        }
        if (null != pmInstanceInfo.getCreateTime()) {
            pmInstanceInfo.setCreateTime(DateParse.parse(pmInstanceInfo.getCreateTime()));
        }
        if (null != pmInstanceInfo.getExpireTime()) {
            pmInstanceInfo.setExpireTime(DateParse.parse(pmInstanceInfo.getExpireTime()));
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("物理机实例信息为:\n");
            sb.append(pmInstanceInfo.toString());
            logger.debug(sb.toString());
        }
        return SUCCESS;
    }

    public PMInstanceInfo getPmInstanceInfo() {
        return pmInstanceInfo;
    }

    public void setPmInstanceInfo(PMInstanceInfo pmInstanceInfo) {
        this.pmInstanceInfo = pmInstanceInfo;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
