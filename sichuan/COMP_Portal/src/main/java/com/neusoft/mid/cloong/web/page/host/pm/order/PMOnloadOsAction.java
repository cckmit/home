/*******************************************************************************
 * @(#)VMOnloadOsAction.java 2014年11月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.host.pm.order;

import java.util.List;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OsInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.StandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * TODO 这里请补充该类型的简述说明
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年11月15日 上午11:24:27
 */
public class PMOnloadOsAction extends BaseAction {

    private static final long serialVersionUID = -6356197450412064816L;

    private static LogService logger = LogService.getLogger(PMOnloadOsAction.class);

    /**
     * 规格id
     */
    private String standardId;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 返回结果路径,error,failure
     */
    private String resultRoute;

    /**
     * 操作系统下拉框选项
     */
    private List<OsInfo> osInfos;

    public String execute() {
        if (logger.isDebugEnable()) {
            logger.debug("standardId = " + standardId);
        }

        try {
            StandardInfo standard = new StandardInfo();
            standard.setTemplateId(templateId);
            standard.setStandardId(standardId);
            osInfos = ibatisDAO.getData("getOss", standard);
        } catch (Exception e2) {
            logger.error(PMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
                    getText("pmonload.respool.fail"), e2);
            resultRoute = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取standardId字段数据
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 设置standardId字段数据
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取resultRoute字段数据
     * @return Returns the resultRoute.
     */
    public String getResultRoute() {
        return resultRoute;
    }

    /**
     * 设置resultRoute字段数据
     * @param resultRoute The resultRoute to set.
     */
    public void setResultRoute(String resultRoute) {
        this.resultRoute = resultRoute;
    }

    /**
     * 获取osInfos字段数据
     * @return Returns the osInfos.
     */
    public List<OsInfo> getOsInfos() {
        return osInfos;
    }

    /**
     * 设置osInfos字段数据
     * @param osInfos The osInfos to set.
     */
    public void setOsInfos(List<OsInfo> osInfos) {
        this.osInfos = osInfos;
    }

    /**
     * 获取templateId字段数据
     * @return Returns the templateId.
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 设置templateId字段数据
     * @param templateId The templateId to set.
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

}
