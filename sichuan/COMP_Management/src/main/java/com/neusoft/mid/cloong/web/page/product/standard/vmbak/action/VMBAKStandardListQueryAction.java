/*******************************************************************************
 * @(#)VMBAKStandardListQueryAction.java 2014年1月21日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vmbak.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.StandardQueryPara;
import com.neusoft.mid.cloong.web.page.product.standard.vmbak.info.VMBAKStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机备份信息列表
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月21日 上午11:20:35
 */
public class VMBAKStandardListQueryAction extends PageAction {

    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5598948540150875466L;

    /**
     * 日志文件
     */
    private static LogService logger = LogService.getLogger(VMBAKStandardListQueryAction.class);
    
    /**
     * 规格名称
     */
    private String standardName;
    
    /**
     * 规格Id
     */
    private String standardId;

    /**
     * 创建开始时间
     */
    private String startTime;

    /**
     * 创建结束时间
     */
    private String endTime;
    
    /**
     * 虚拟机备份信息列表
     */
    private List<VMBAKStandardInfo> vmbakStandardInfos;
    
    public String execute() {
        if (errMsg != null && errMsg.length() > 0) {
            this.addActionError(errMsg);
        }
        StandardQueryPara queryPare = new StandardQueryPara();
        queryPare.setStandardName(standardName);
        queryPare.setStartTime(startTime);
        queryPare.setEndTime(endTime);
        queryPare.setStandardId(standardId);
        try {
            vmbakStandardInfos = getPage("queryVMBAKStandardCount", "queryVMBAKStandardList", queryPare);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机备份资源规格信息出错，数据库异常", e);
            return ConstantEnum.ERROR.toString();
        }
        if (vmbakStandardInfos.size() == 0) {
            logger.info("虚拟机备份资源规格信息为空");
            this.addActionMessage("虚拟机备份资源规格信息为空！");
        }
        for (VMBAKStandardInfo info : vmbakStandardInfos) {
            info.setCreateTime(DateParse.parse(info.getCreateTime()));
        }
        logger.info("查询资源规格信息成功，共有:" + vmbakStandardInfos.size() + "条虚拟机备份规格信息 ");
        return ConstantEnum.SUCCESS.toString();
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
    
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<VMBAKStandardInfo> getVmbakStandardInfos() {
        return vmbakStandardInfos;
    }

    public void setVmbakStandardInfos(List<VMBAKStandardInfo> vmbakStandardInfos) {
        this.vmbakStandardInfos = vmbakStandardInfos;
    }
    
    
    
}
