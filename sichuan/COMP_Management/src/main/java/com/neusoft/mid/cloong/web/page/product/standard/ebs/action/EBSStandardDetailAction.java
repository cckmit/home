/*******************************************************************************
 * @(#)VMStandardDetailAction.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.ebs.action;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.ebs.info.EBSStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟硬盘规格详细信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-12 上午10:00:58
 */
public class EBSStandardDetailAction extends BaseAction {

    private static final long serialVersionUID = -5503914699654277357L;

    private static LogService logger = LogService.getLogger(EBSStandardDetailAction.class);

    /**
     * 虚拟硬盘规格ID
     */
    private String standardId;

    /**
     * 虚拟硬盘规格信息
     */
    private EBSStandardInfo standardInfo;

    private String resultPath = ConstantEnum.SUCCESS.toString();

    public String execute() {
        try {
            standardInfo = (EBSStandardInfo) ibatisDAO.getSingleRecord("queryEBSStandardDetail",
                    standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟硬盘规格详细信息失败，数据库异常", e);
            resultPath = ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟硬盘规格详细信息失败，数据库异常", e);
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

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public EBSStandardInfo getStandardInfo() {
        return standardInfo;
    }

    public void setStandardInfo(EBSStandardInfo standardInfo) {
        this.standardInfo = standardInfo;
    }
}
