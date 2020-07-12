/*******************************************************************************
 * @(#)VMStandardCreateAction.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vm.action;

import java.sql.SQLException;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.VMStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机规格创建
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-12 上午09:47:41
 */
public class VMStandardCreateAction extends BaseAction {

    private static LogService logger = LogService.getLogger(VMStandardCreateAction.class);

    private static final long serialVersionUID = 5162861089626074947L;

    /**
     * 模板名称
     */
    private String standardName;

    /**
     * cpu数量
     */
    private String cpuNum;

    /**
     * 内存
     */
    private String ram;

    /**
     * 硬盘大小
     */
    private String discSize;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 序列号生成器
     */
    private SequenceGenerator gen;

    /**
     * 返回路径
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();

    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        VMStandardInfo vmStandardInfo = new VMStandardInfo();
        vmStandardInfo.setCpuNum(Long.parseLong(cpuNum));
        vmStandardInfo.setDescription(description);
        vmStandardInfo.setDiscSize(Long.parseLong(discSize));
        vmStandardInfo.setRamSize(Long.parseLong(ram));
        vmStandardInfo.setStandardName(standardName);
        vmStandardInfo.setStandardId(gen.generateStandardId("VM"));
        String dateTime = DateParse.generateDateFormatyyyyMMddHHmmss();
        vmStandardInfo.setCreateTime(dateTime);
        vmStandardInfo.setCreateUser(userId);
        vmStandardInfo.setUpdateTime(dateTime);
        vmStandardInfo.setUpdateUser(userId);
        try {
            ibatisDAO.insertData("createVMStandard", vmStandardInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "创建虚拟机规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "创建虚拟机规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
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

    public SequenceGenerator getGen() {
        return gen;
    }

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getDiscSize() {
        return discSize;
    }

    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

}
