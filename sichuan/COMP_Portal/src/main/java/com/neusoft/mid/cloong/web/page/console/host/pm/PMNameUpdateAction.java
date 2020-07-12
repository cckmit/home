/*******************************************************************************
 * @(#)PMNameUpdateAction.java 2013-1-8
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
 * 更改物理机名称，返回JSON对象
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-15 下午07:49:11
 */
public class PMNameUpdateAction extends BaseAction {

    private static final long serialVersionUID = 5905281979052112854L;

    private static LogService logger = LogService.getLogger(PMNameUpdateAction.class);

    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 物理机编码
     */
    private String pmId;

    /**
     * 物理机名称
     */
    private String pmName;

    public String execute() {
        if (pmId == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, getText("pmid.isnull"));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        pmId = pmId.trim();
        PMInstanceInfo pmInstanceInfo = new PMInstanceInfo();
        pmInstanceInfo.setPmId(pmId);
        pmInstanceInfo.setPmName(pmName);
        pmInstanceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        pmInstanceInfo.setUpdateUser(getCurrentUserId());
        
        int updateResult = 0;
        try {
            updateResult = ibatisDAO.updateData("updatePMSName", pmInstanceInfo);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, MessageFormat.format(
                    getText("pmname.update.fail"), pmId), e);
            this.addActionError(MessageFormat.format(getText("pmname.update.fail"), pmId));
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
        sb.append("更新编码为[").append(pmId).append("]的名称成功，名称为[").append(pmName).append("]\n");
        logger.info(sb.toString());
        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

}
