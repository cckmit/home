/*******************************************************************************
 * @(#)VMBAKStandardDetailAction.java 2014年1月21日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vmbak.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.vmbak.info.VMBAKStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询虚拟机备份规格详细信息
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月21日 下午2:28:53
 */
public class VMBAKStandardDetailAction extends BaseAction {
    
    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = -4516760308183606523L;

    private static LogService logger = LogService.getLogger(VMBAKStandardDetailAction.class);
    
    /**
     * 虚拟机备份规格ID
     */
    private String standardId;
    
    /**
     * 返回路径
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();
    
    /**
     * 虚拟机备份规格信息
     */
    private VMBAKStandardInfo standardInfo;
    
    public String execute() {
        try {
            Map<String, String> queryInfo = genQueryDBInfo();
            
            standardInfo = (VMBAKStandardInfo) ibatisDAO.getSingleRecord("queryVMBAKStandardDetail",
                    queryInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机备份规格详细信息失败，数据库异常", e);
            resultPath = ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机备份规格详细信息失败，数据库异常", e);
            resultPath = ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * genQueryDBInfo 生成数据库查询参数
     * @return 数据库查询参数
     */
    private Map<String, String> genQueryDBInfo() {
        Map<String,String> queryInfo = new HashMap<String, String>();
        queryInfo.put("standardId", standardId);
        queryInfo.put("status", "0");
        return queryInfo;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public VMBAKStandardInfo getStandardInfo() {
        return standardInfo;
    }

    public void setStandardInfo(VMBAKStandardInfo standardInfo) {
        this.standardInfo = standardInfo;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }
    
}
