/*******************************************************************************
 * @(#)VmBakQueryListAction.java 2013-1-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.vmbak;

import java.sql.SQLException;
import java.text.MessageFormat;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.vmbak.core.VmBakStatus;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.vmbak.info.VmBakResultInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询当前登陆租户下，虚拟机备份任务列表信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午06:58:42
 */
public class VmBakQueryJsonAction extends BaseAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5471800875775849067L;

    /**
     * 日志器
     */
    private static LogService logger = LogService.getLogger(VmBakQueryJsonAction.class);

    /**
     * 虚拟机Id
     */
    private String vmId = "";

    /**
     * 备份任务Id
     */
    private String vmBakId = "";

    /**
     * JS返回结果
     */
    private String result = ConstantEnum.FAILURE.toString();

    @SuppressWarnings("unchecked")
    @Override
    public String execute() {
        if (logger.isDebugEnable()) {
            logger.debug("vmId=" + vmId);
        }
        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }

        VmBakResultInfo vmBakResultInfo = new VmBakResultInfo();
        vmBakResultInfo.setVmId(vmId);

        // 虚拟机备份状态不等.删除
        vmBakResultInfo.setVmBakStatus(VmBakStatus.DELETED.getCode());

        try {
            vmBakResultInfo = (VmBakResultInfo) ibatisDAO.getSingleRecord("queryVmBakUserListAll",
                    vmBakResultInfo);
            vmBakId = vmBakResultInfo.getVmBakId();
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("vmBak.query.fail"), getCurrentUserId()), e);
            this.addActionError(MessageFormat.format(getText("vmBak.query.fail"),
                    getCurrentUserId()));
            return ConstantEnum.ERROR.toString();
        }

        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getVmBakId() {
        return vmBakId;
    }

    public void setVmBakId(String vmBakId) {
        this.vmBakId = vmBakId;
    }

}
