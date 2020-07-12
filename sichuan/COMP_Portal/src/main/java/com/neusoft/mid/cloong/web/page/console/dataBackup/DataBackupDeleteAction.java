/*******************************************************************************
 * @(#)DiskDeleteAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.dataBackup;

import java.text.MessageFormat;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.ebs.core.EBSDelete;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSDeleteResp;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 删除云硬盘
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-21 下午05:53:57
 */
public class DataBackupDeleteAction extends BaseAction {

    private static final long serialVersionUID = 4407178327415405154L;

    private static LogService logger = LogService.getLogger(DataBackupDeleteAction.class);

    /**
     * 云硬盘ID
     */
    private String diskId;

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 页面返回路径
     */
    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 返回消息
     */
    private String resultMessage = "";

    /**
     * 云硬盘删除接口
     */
    private EBSDelete delete;

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    public String execute() {
        if (diskId == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "云硬盘编码为空");
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        diskId = diskId.trim();
        DiskInfo diskInfo = null;
        try {
            diskInfo = (DiskInfo) ibatisDAO.getSingleRecord("qureyDiskInfo", diskId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "云硬盘ID为[" + diskId
                    + "]的虚拟机所有者失败", e);
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (diskInfo == null) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "云硬盘ID为[" + diskId
                    + "]的虚拟机所有者失败", null);
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        /* 鉴权改为业务ID */
        if (!getCurrentUser().getAppIdList().contains(diskInfo.getBusinessId())) {
            logger.info(getText("disk.operation.auth"));
            resultMessage = getText("disk.operation.auth");
            result = ConstantEnum.NOPERMISSION.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (diskInfo.getDiskStatus().equals("4")) {
            logger.info(MessageFormat.format(getText("disk.delete.detach.first"), diskId,
                    diskInfo.getMountVmName()));
            resultMessage = MessageFormat.format(getText("disk.delete.detach.first"), diskId,
                    diskInfo.getMountVmName());
            result = ConstantEnum.STATUSINVALID.toString();
            return ConstantEnum.FAILURE.toString();
        }
        RPPEBSDeleteReq req = new RPPEBSDeleteReq();
        req.setEbsId(diskId);
        req.setResourcePoolId(resourcePoolId);
        req.setResourcePoolPartId(resourcePoolPartId);
        req.setSerialNum(seqCen.generatorSerialNum());
        RPPEBSDeleteResp resp = delete.delete(req);
        if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "云硬盘删除失败，接口错误", null);
            resultMessage = MessageFormat.format(getText("disk.delete.fail"), diskId);
            result = ConstantEnum.INTERFACEERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        int updateResult = 0;
        try {
            updateResult = ibatisDAO.updateData("deleteDisk", diskId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("diskname.update.fail"), diskId), e);
            this.addActionError(MessageFormat.format(getText("disk.delete.fail"), diskId));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }
        if (updateResult != 1) {
            logger.info(MessageFormat.format(getText("disk.query.notexist"), diskId));
            resultMessage = MessageFormat.format(getText("disk.query.notexist"), diskId);
            result = ConstantEnum.DELETE.toString();
            return ConstantEnum.FAILURE.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("删除编码为[").append(diskId).append("]的云硬盘成功！");
        logger.info(sb.toString());
        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public void setDelete(EBSDelete delete) {
        this.delete = delete;
    }

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    /**
     * 获取seqCen字段数据
     * @return Returns the seqCen.
     */
    public CommonSequenceGenerator getSeqCen() {
        return seqCen;
    }

    /**
     * 设置seqCen字段数据
     * @param seqCen The seqCen to set.
     */
    public void setSeqCen(CommonSequenceGenerator seqCen) {
        this.seqCen = seqCen;
    }
}
