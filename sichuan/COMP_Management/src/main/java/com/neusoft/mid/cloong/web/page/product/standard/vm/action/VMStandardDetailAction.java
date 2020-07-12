/*******************************************************************************
 * @(#)VMStandardDetailAction.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vm.action;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.VMStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机规格详细信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-12 上午10:00:58
 */
public class VMStandardDetailAction extends BaseAction {

    private static final long serialVersionUID = -5503914699654277357L;

    private static LogService logger = LogService.getLogger(VMStandardDetailAction.class);

    /**
     * 虚拟机规格ID
     */
    private String standardId;

    /**
     * 虚拟机规格信息
     */
    private VMStandardInfo standardInfo;

    private String resultPath = ConstantEnum.SUCCESS.toString();

    public String execute() {
        try {
            standardInfo = (VMStandardInfo) ibatisDAO.getSingleRecord("queryVMStandardDetail",
                    standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机规格详细信息失败，数据库异常", e);
            resultPath = ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机规格详细信息失败，数据库异常", e);
            resultPath = ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public VMStandardInfo getStandardInfo() {
        return standardInfo;
    }

    public void setStandardInfo(VMStandardInfo standardInfo) {
        this.standardInfo = standardInfo;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }
}
