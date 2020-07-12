/*******************************************************************************
 * @(#)VMStandardListQueryAction.java 2013-3-11
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vm.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.StandardQueryPara;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.VMStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机规格查询
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-11 上午08:34:50
 */
public class VMStandardListQueryAction extends PageAction {

    private static final long serialVersionUID = -4286186022961966133L;

    private static LogService logger = LogService.getLogger(VMStandardListQueryAction.class);

    /**
     * 规格名称
     */
    private String standardName;

    /**
     * 创建开始时间
     */
    private String startTime;

    /**
     * 创建结束时间
     */
    private String endTime;

    /**
     * 虚拟机信息列表
     */
    private List<VMStandardInfo> vmStandardInfos;

    private String resultPath = ConstantEnum.SUCCESS.toString();

    public String execute() {
        if (errMsg != null && errMsg.length() > 0) {
            this.addActionError(errMsg);
        }
        StandardQueryPara queryPare = new StandardQueryPara();
        queryPare.setStandardName(standardName);
        queryPare.setStartTime(startTime);
        queryPare.setEndTime(endTime);
        try {
            // vmStandardInfos = ibatisDAO.getData("queryVMStandardList", queryPare);
            vmStandardInfos = getPage("queryVMStandardCount", "queryVMStandardList", queryPare);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机资源规格信息出错，数据库异常", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        if (vmStandardInfos.size() == 0) {
            logger.info("资源规格信息为空");
//            this.addActionMessage(getText("standard.vm.query.null"));
        }
        for (VMStandardInfo info : vmStandardInfos) {
            info.setCreateTime(DateParse.parse(info.getCreateTime()));
        }
        logger.info("查询资源规格信息成功，共有:" + vmStandardInfos.size() + "条虚拟机资源规格信息 ");
        return ConstantEnum.SUCCESS.toString();
    }

    public List<VMStandardInfo> getVmStandardInfos() {
        return vmStandardInfos;
    }

    public void setVmStandardInfos(List<VMStandardInfo> vmStandardInfos) {
        this.vmStandardInfos = vmStandardInfos;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

}
