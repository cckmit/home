/*******************************************************************************
 * @(#)VMStandardModifyAction.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.ebs.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.standard.base.BaseStandardModifyAction;
import com.neusoft.mid.cloong.web.page.product.standard.ebs.info.EBSStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟硬盘规格修改
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-12 下午04:33:46
 */
public class EBSStandardModifyAction extends BaseStandardModifyAction {

    private static LogService logger = LogService.getLogger(EBSStandardModifyAction.class);

    private static final long serialVersionUID = 5162861089626074947L;

    /**
     * 规格ID
     */
    private String standardId;

    /**
     * 规格名称
     */
    private String standardName;

    /**
     * 硬盘大小
     */
    private String discSize;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 返回路径
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();
    
    /**
     * 可用的模板是否可以修改   0:不可修改    1:可以修改.
     */
    private String templateIsUseModify;

    /**
     * 设置templateIsUseModify字段数据
     * @param templateIsUseModify The templateIsUseModify to set.
     */
    public void setTemplateIsUseModify(String templateIsUseModify) {
        this.templateIsUseModify = templateIsUseModify;
    }

    public String execute() {
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        int standardNumInUse = 0;
        try {
            standardNumInUse = (Integer) ibatisDAO.getSingleRecord("selectItembystandardId",
                    standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟硬盘规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟硬盘规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        }
        if (standardNumInUse > 0) {
            resultPath = ConstantEnum.INUSE.toString();
            logger.info("ID为" + standardId + "的规格正在使用，不允许修改");
            return ConstantEnum.SUCCESS.toString();
        }
        EBSStandardInfo ebsStandardInfo = new EBSStandardInfo();
        ebsStandardInfo.setStandardId(standardId);
        ebsStandardInfo.setDescription(description);
        ebsStandardInfo.setDiscSize(Long.parseLong(discSize));
        ebsStandardInfo.setStandardName(standardName);
        ebsStandardInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        ebsStandardInfo.setUpdateUser(userId);
        
        //查询规格同步状态为：同步发送成功的数量
        int syningNum = getCountSyning(standardId);
        if(syningNum==-1){
            resultPath = ConstantEnum.ERROR.toString();
            logger.info("ID为" + standardId + "的规格查询规格同步发送成功状态数量失败！");
            return ConstantEnum.SUCCESS.toString();
        }
        if(syningNum>0){
            resultPath = ConstantEnum.INTERMEDIATE.toString();
            logger.info("ID为" + standardId + "的规格存在中间状态【同步发送成功】，无法修改！");
            return ConstantEnum.SUCCESS.toString();
        }
        
        //查询规格同步状态为：可用的数量
        int usableNum = getCountSynUsable(standardId);
        if(usableNum==-1){
            resultPath = ConstantEnum.ERROR.toString();
            logger.info("ID为" + standardId + "的规格查询规格可用状态数量失败！");
            return ConstantEnum.SUCCESS.toString();
        }
        List<BatchVO> batchVos = new ArrayList<BatchVO>();
        //如果同步状态存在 可用  更新其状态为5：等待再同步
        if(usableNum>0){
            if("0".equals(templateIsUseModify)){
                resultPath = ConstantEnum.ENABLED.toString();
                logger.info("ID为" + standardId + "的规格资源池是可用状态，不允许修改");
                return ConstantEnum.SUCCESS.toString();
            }
            batchVos.add(new BatchVO("MOD","modifySynUsable",standardId));
        }
        batchVos.add(new BatchVO("MOD","updateEBSStandard",ebsStandardInfo));
        
        try {
            ibatisDAO.updateBatchData(batchVos);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟硬盘规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改虚拟硬盘规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getDiscSize() {
        return discSize;
    }

    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }
}
