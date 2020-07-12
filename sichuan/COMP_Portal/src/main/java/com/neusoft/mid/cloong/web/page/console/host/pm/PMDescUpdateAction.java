/*******************************************************************************
 * @(#)PMDescUpdateAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.pm;

import java.text.MessageFormat;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.pm.info.PMInstanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 更新物理机备注信息，返回JSON对象
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午06:25:00
 */
public class PMDescUpdateAction extends BaseAction {

    private static final long serialVersionUID = -6356197450412064816L;

    private static LogService logger = LogService.getLogger(PMDescUpdateAction.class);

    /**
     * 物理机编码
     */
    private String pmId;

    /**
     * 物理机备注
     */
    private String description;

    private String result = ConstantEnum.FAILURE.toString();

    public String execute() {
        if (pmId == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, getText("pmid.isnull"));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        pmId = pmId.trim();
        PMInstanceInfo pmInstanceInfo = new PMInstanceInfo();
        pmInstanceInfo.setPmId(pmId);
        pmInstanceInfo.setDescription(description);
        pmInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        pmInstanceInfo.setUpdateUser(getCurrentUserId());
        int updateResult = 0;
        try {
            updateResult = ibatisDAO.updateData("updatePMDesc", pmInstanceInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("pmdesc.update.fail"), pmId), e);
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (updateResult != 1) {
            logger.info(MessageFormat.format(getText("pmid.query.notexist"), pmId));
            this.addActionError(MessageFormat.format(getText("pmid.query.notexist"), pmId));
            result = ConstantEnum.DELETE.toString();
            return ConstantEnum.FAILURE.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("更新编码为[").append(pmId).append("]的备注信息成功，备注为[").append(description).append("]\n");
        logger.info(sb.toString());
        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
