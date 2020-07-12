/*******************************************************************************
 * @(#)VMBAKStandardListAction.java 2014-2-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vmbak.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.vmbak.info.VMBAKStandardInfo;
import com.neusoft.mid.cloong.web.page.product.standard.vmbak.service.VMBAKStandardListService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通过条件查询虚拟机备份资源规格列表Action（条件包括：空间大小，是否条目使用，规格名称）
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-2-19 下午08:05:55
 */
public class VMBAKStandardListAction extends BaseAction {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4359411865032188769L;

    /***
     * 日志
     */
    private static LogService logger = LogService.getLogger(VMBAKStandardListAction.class);

    /**
     * 虚拟机备份Service
     */
    private VMBAKStandardListService vmBakStandardListService;

    /**
     * 获取vmBakStandardListService字段数据
     * @return Returns the vmBakStandardListService.
     */
    public VMBAKStandardListService getVmBakStandardListService() {
        return vmBakStandardListService;
    }
    
    /**
     * 设置vmBakStandardListService字段数据
     * @param vmBakStandardListService The vmBakStandardListService to set.
     */
    public void setVmBakStandardListService(VMBAKStandardListService vmBakStandardListService) {
        this.vmBakStandardListService = vmBakStandardListService;
    }

    /**
     * spring 配置 queryVMStandardInfo 详细queryVMStandardInfoDetail
     */
    private String querySqlKey;

    /**
     * 设置querySqlKey字段数据
     * @param querySqlKey The querySqlKey to set.
     */
    public void setQuerySqlKey(String querySqlKey) {
        this.querySqlKey = querySqlKey;
    }

    /**
     * 界面返回
     */
    private List<VMBAKStandardInfo> vmBakStandardInfos;

    /**
     * 获取vmBakStandardInfos字段数据
     * @return Returns the vmBakStandardInfos.
     */
    public List<VMBAKStandardInfo> getVmBakStandardInfos() {
        return vmBakStandardInfos;
    }

    /**
     * 界面查询条件
     */
    /** 虚拟机备份 空间大小 */
    private String spaceSize;

    /** 虚拟机备份规格 使用状态 */
    private String useStatus;

    /** 虚拟机备份规格ID */
    private String standardId;
    
    /** 虚拟机备份规格名称 */
    private String standardName;

    /**
     * 查询虚拟机资源规格
     * @return String
     */
    public String execute() {
        VMBAKStandardInfo vmStandardInfo = new VMBAKStandardInfo();
        initQuery(vmStandardInfo);
        try {
            vmBakStandardInfos = vmBakStandardListService.queryVMBAKStandard(vmStandardInfo,
                    querySqlKey);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("standard.vm.query.fail"), e);
            this.addActionError(getText("standard.vm.query.fail"));
            vmBakStandardInfos = null;
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("查询虚拟机备份资源规格列表大小:");
            sb.append(vmBakStandardInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 
     * initQuery 初始化查询信息
     * @param vmBAKStandardInfo 虚拟机备份规格实例
     */
    private void initQuery(VMBAKStandardInfo vmBAKStandardInfo) {
        // 空间大小
        if (!"".equals(spaceSize) && null != spaceSize) {
            try {
                vmBAKStandardInfo.setSpaceSize(Long.parseLong(spaceSize));
            } catch (Exception e) {
                logger.error(CommonStatusCode.IO_OPTION_ERROR,
                        "虚拟机备份，空间大小转换异常", e);
            }
        }
        if (!"".equals(useStatus) && null != useStatus) {
            vmBAKStandardInfo.setUseStatus(useStatus);
        }
        if (!"".equals(standardName) && null != standardName) {
            vmBAKStandardInfo.setStandardName(standardName);
        }
        if (!"".equals(standardId) && null != standardId) {
            vmBAKStandardInfo.setStandardId(standardId);
        }
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
     * 设置useStatus字段数据
     * @param useStatus The useStatus to set.
     */
    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    /**
     * 设置spaceSize字段数据
     * @param spaceSize The spaceSize to set.
     */
    public void setSpaceSize(String spaceSize) {
        this.spaceSize = spaceSize;
    }

    /**
     * 设置standardName字段数据
     * @param standardName The standardName to set.
     */
    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }
    
}
