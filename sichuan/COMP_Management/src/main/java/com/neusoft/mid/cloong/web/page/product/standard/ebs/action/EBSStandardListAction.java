/*******************************************************************************
 * @(#)EBSStandardListAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.ebs.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.ebs.info.EBSStandardInfo;
import com.neusoft.mid.cloong.web.page.product.standard.ebs.service.EBSStandardListService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘查询
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-21 上午11:10:11
 */
public class EBSStandardListAction extends BaseAction {

    private static final long serialVersionUID = 4242241221203478770L;

    private static LogService logger = LogService.getLogger(EBSStandardListAction.class);

    private EBSStandardListService ebsStandardListService;

    public void setEbsStandardListService(EBSStandardListService ebsStandardListService) {
        this.ebsStandardListService = ebsStandardListService;
    }

    /**
     * spring 配置 queryEBSStandardInfo 详细queryEBSStandardInfoDetail
     */
    private String querySqlKey;
    
    public void setQuerySqlKey(String querySqlKey) {
        this.querySqlKey = querySqlKey;
    }
    
    /**
     * 界面返回
     */
    private List<EBSStandardInfo> ebsStandardInfos;

    public List<EBSStandardInfo> getEbsStandardInfos() {
        return ebsStandardInfos;
    }

    /**
     * 界面查询条件
     */
    private String discSize;

    private String useStatus;

    private String standardId;

    private String standardName;

    /**
     * 查询虚拟硬盘资源规格
     * @return
     */
    public String execute() {
        EBSStandardInfo ebsStandardInfo = new EBSStandardInfo();
        initQuery(ebsStandardInfo);
        try {
            ebsStandardInfos = ebsStandardListService.queryEBSStandard(ebsStandardInfo,querySqlKey);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                   "查询虚拟硬盘规格失败！", e);
            this.addActionError("查询虚拟硬盘规格失败！");
            ebsStandardInfos = null;
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("查询虚拟硬盘资源规格列表大小:");
            sb.append(ebsStandardInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 初始化查询信息
     * @param ebsStandardInfo
     */
    private void initQuery(EBSStandardInfo ebsStandardInfo) {
        // 硬盘大小
        if (!"".equals(discSize) && null != discSize) {
            try {
                ebsStandardInfo.setDiscSize(Long.parseLong(discSize));
            } catch (Exception e) {
                logger.error(CommonStatusCode.IO_OPTION_ERROR,
                        "虚拟硬盘大小转换int失败！", e);
            }
        }
        // 名称
        if (!"".equals(standardName) && null != standardName) {
            ebsStandardInfo.setStandardName(standardName);
        }
        // 使用状态
        if (!"".equals(useStatus) && null != useStatus) {
            ebsStandardInfo.setUseStatus(useStatus);
        }
        // 规格id
        if (!"".equals(standardId) && null != standardId) {
            ebsStandardInfo.setStandardId(standardId);
        }
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

}
