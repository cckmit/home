/*******************************************************************************
 * @(#)VMQueryListAction.java 2013-2-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vm.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.VMStandardInfo;
import com.neusoft.mid.cloong.web.page.product.standard.vm.service.VMStandardListService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通过条件查询虚拟机资源规格列表Action（条件包括：cpu数量，内存容量，硬盘大小，是否条目使用）
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-7 下午02:11:17
 */
public class VMStandardListAction extends BaseAction {

    private static final long serialVersionUID = -4965007863408723033L;

    private static LogService logger = LogService.getLogger(VMStandardListAction.class);

    private VMStandardListService vmStandardListService;

    public VMStandardListService getVmStandardListService() {
        return vmStandardListService;
    }

    public void setVmStandardListService(VMStandardListService vmStandardListService) {
        this.vmStandardListService = vmStandardListService;
    }

    /**
     * spring 配置 queryVMStandardInfo 详细queryVMStandardInfoDetail
     */
    private String querySqlKey;
    
    public void setQuerySqlKey(String querySqlKey) {
        this.querySqlKey = querySqlKey;
    }

    /**
     * 界面返回
     */
    private List<VMStandardInfo> vmStandardInfos;

    public List<VMStandardInfo> getVmStandardInfos() {
        return vmStandardInfos;
    }

    /**
     * 界面查询条件
     */
    private String cpuNum;

    private String discSize;

    private String ramSize;

    private String useStatus;
    
    private String standardId;

    /**
     * 查询虚拟机资源规格
     * @return
     */
    public String execute() {
        VMStandardInfo vmStandardInfo = new VMStandardInfo();
        initQuery(vmStandardInfo);
        try {
            vmStandardInfos = vmStandardListService.queryVMStandard(vmStandardInfo,querySqlKey);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("standard.vm.query.fail"), e);
            this.addActionError(getText("standard.vm.query.fail"));
            vmStandardInfos = null;
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("查询虚拟机资源规格列表大小:");
            sb.append(vmStandardInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 初始化查询信息
     * @param vmStandardInfo
     */
    private void initQuery(VMStandardInfo vmStandardInfo) {
        // cpu数量
        if (!"".equals(cpuNum) && null != cpuNum) {
            try {
                vmStandardInfo.setCpuNum(Long.parseLong(cpuNum));
            } catch (Exception e) {
                logger.error(CommonStatusCode.IO_OPTION_ERROR,
                        getText("standard.vm.query.cpunum.e"), e);
            }
        }
        // 硬盘大小
        if (!"".equals(discSize) && null != discSize) {
            try {
                vmStandardInfo.setDiscSize(Long.parseLong(discSize));
            } catch (Exception e) {
                logger.error(CommonStatusCode.IO_OPTION_ERROR,
                        getText("standard.vm.query.discsize.e"), e);
            }
        }
        // 内存大小
        if (!"".equals(ramSize) && null != ramSize) {
            try {
                vmStandardInfo.setRamSize(Long.parseLong(ramSize));
            } catch (Exception e) {
                logger.error(CommonStatusCode.IO_OPTION_ERROR,
                        getText("standard.vm.query.ramsize.e"), e);
            }
        }
        if(!"".equals(useStatus) && null != useStatus) {
            vmStandardInfo.setUseStatus(useStatus);
        }
        if(!"".equals(standardId)&& null !=standardId){
            vmStandardInfo.setStandardId(standardId);
        }
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getCpuNum() {
        return cpuNum;
    }

    public String getDiscSize() {
        return discSize;
    }

    public String getRamSize() {
        return ramSize;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

}
