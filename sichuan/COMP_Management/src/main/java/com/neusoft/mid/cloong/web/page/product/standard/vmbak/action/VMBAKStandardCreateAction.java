/*******************************************************************************
 * @(#)VMBAKStandardCreateAction.java 2014年1月21日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vmbak.action;

import java.sql.SQLException;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.standard.vmbak.info.VMBAKStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机备份规格创建
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月21日 下午1:31:31
 */
public class VMBAKStandardCreateAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5004717130553921666L;

    /**
     * 日志文件
     */
    private static LogService logger = LogService.getLogger(VMBAKStandardCreateAction.class);
    
    /**
     * 规格名称
     */
    private String standardName;

    /**
     * 空间大小
     */
    private String spaceSize;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 序列号生成器
     */
    private SequenceGenerator gen;
    
    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        VMBAKStandardInfo vmbakStandardInfo = new VMBAKStandardInfo();
        vmbakStandardInfo.setDescription(description);
        vmbakStandardInfo.setSpaceSize(Long.parseLong(spaceSize));
        vmbakStandardInfo.setStandardName(standardName);
        vmbakStandardInfo.setStandardId(gen.generateStandardId("BK"));
        String dateTime = DateParse.generateDateFormatyyyyMMddHHmmss();
        vmbakStandardInfo.setCreateTime(dateTime);
        vmbakStandardInfo.setCreateUser(userId);
        vmbakStandardInfo.setUpdateTime(dateTime);
        vmbakStandardInfo.setUpdateUser(userId);
        try {
            ibatisDAO.insertData("createVMBAKStandard", vmbakStandardInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "创建虚拟机备份规格失败", e);
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "创建虚拟机备份规格失败", e);
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

    public String getSpaceSize() {
        return spaceSize;
    }

    public void setSpaceSize(String spaceSize) {
        this.spaceSize = spaceSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SequenceGenerator getGen() {
        return gen;
    }

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }
    
    
}
