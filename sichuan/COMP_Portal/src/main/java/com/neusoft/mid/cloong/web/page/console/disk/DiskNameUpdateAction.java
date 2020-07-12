/*******************************************************************************
 * @(#)DiskNameUpdateAction.java 2013-3-21
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.disk;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.ebs.core.EBSModify;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSModifyReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.RPPEBSModifyResp;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.info.DiskInfo;
import com.neusoft.mid.cloong.web.page.ebs.order.info.EBSInstanceInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 修改云硬盘名称和大小
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-21 上午09:07:27
 */
public class DiskNameUpdateAction extends BaseAction {

    private static final long serialVersionUID = 8492718389727827766L;

    private static LogService logger = LogService.getLogger(DiskNameUpdateAction.class);

    private final String SUCCESS_CODE = "00000000";

    /**
     * 成功的接口响应码
     */
    private String result = ConstantEnum.FAILURE.toString();

    /**
     * 云硬盘名称
     */
    private String diskName;

    /**
     * 云硬盘ID
     */
    private String diskId;

    /**
     * 云硬盘大小
     */
    private String diskSize;

    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 发送修改磁盘大小请求接口
     */
    private EBSModify modify;

    /**
     * 流水号生成器
     */
    private CommonSequenceGenerator seqCen;

    /**
     * 自动审核开关
     */
    private String audit;

    private String caseId;

    /**
     * 默认自动审批
     */
    private static final String AUTO = "0";

    public String execute() {

        DiskInfo diskInfo = new DiskInfo();

        if (diskId == null) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "云硬盘编码为空");
            result = ConstantEnum.ERROR.toString();
            msg = "修改异常";
            return ConstantEnum.FAILURE.toString();
        } else {
            try {
                DiskInfo tmp = new DiskInfo();

                tmp.setDiskId(diskId.trim());

                diskInfo = (DiskInfo) ibatisDAO.getSingleRecord("queryEBSUserList", tmp);
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "云硬盘ID为[" + diskId
                        + "]的虚拟机所有者失败", e);
            }
        }

        // session中获取用户ID
        UserBean sessionUser = (UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString());

        /* 开启事务 */
        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();

        /* 通过业务鉴权 */
        if (!sessionUser.getAppIdList().contains(diskInfo.getBusinessId())) {
            logger.info(getText("disk.operation.auth"));
            this.addActionError(getText("disk.operation.auth"));
            result = ConstantEnum.NOPERMISSION.toString();
            msg = "没有权限修改";
            return ConstantEnum.FAILURE.toString();
        }

        /* 后台校验容量是否是扩容 */
        int flag = Integer.valueOf(diskSize).compareTo(Integer.valueOf(diskInfo.getDiskSize()));
        if (flag < 0) {
            logger.info(getText("disk.operation.extendOnly"));
            this.addActionError(getText("disk.operation.extendOnly"));
            result = ConstantEnum.STATUSINVALID.toString();
            msg = "云硬盘只能扩容";
            return ConstantEnum.FAILURE.toString();
        }

        /* 什么也没改 */
        if (flag == 0 && diskName.trim().equals(diskInfo.getDiskName().trim())) {
            msg = "修改成功";
            result = ConstantEnum.SUCCESS.toString();
            return ConstantEnum.SUCCESS.toString();
        }

        /* 更新云硬盘名称和修改人和修改时间 */
        EBSInstanceInfo info = new EBSInstanceInfo();

        info.setEbsId(diskId);
        info.setEbsName(diskName);
        info.setUpdateUser(sessionUser.getUserId());
        info.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());

        try {
            updateBatchVO.add(new BatchVO(BatchVO.OPERATION_MOD, "updateDiskName", info));
            msg = "修改成功";
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("diskname.update.fail"), diskId), e);
            this.addActionError(MessageFormat.format(getText("diskname.update.fail"), diskId));
            result = ConstantEnum.ERROR.toString();
            return ConstantEnum.FAILURE.toString();
        }

        /* 修改了云硬盘容量,且自动审批,则调用接口 */
        if (flag > 0 && AUTO.equals(audit)) {
            RPPEBSModifyReq req = new RPPEBSModifyReq();

            req.setEbsId(diskId);
            req.setEbsName(diskName);
            req.setDiskSize(Integer.valueOf(diskSize));
            req.setResourcePoolId(diskInfo.getResourcePoolId());
            req.setResourcePoolPartId(diskInfo.getResourcePoolPartId());
            req.setSerialNum(seqCen.generatorSerialNum());
            RPPEBSModifyResp resp = modify.modify(req);

            /* 调用后,返回成功状态码,直接更新实例表 */
            if (null != resp && null != resp.getResultCode()
                    && SUCCESS_CODE.equals(resp.getResultCode())) {
                diskInfo.setDiskSize(diskSize);
                diskInfo.setUpdateUser(sessionUser.getUserId());
                diskInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());

                OrderInfo order = new OrderInfo();
                order.setOrderId(diskInfo.getOrderId());
                order.setUpdateUser(sessionUser.getUserId());
                order.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                try {
                    updateBatchVO
                            .add(new BatchVO(BatchVO.OPERATION_MOD, "updateDiskSize", diskInfo));
                    updateBatchVO.add(new BatchVO(BatchVO.OPERATION_MOD, "updateDiskOrderStatus",
                            order));
                } catch (Exception e) {
                    logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                            MessageFormat.format(getText("diskname.update.fail"), diskId), e);
                    this.addActionError(MessageFormat.format(getText("diskname.update.fail"),
                            diskId));
                    result = ConstantEnum.ERROR.toString();
                    msg = "修改失败";
                    return ConstantEnum.FAILURE.toString();
                }

                result = ConstantEnum.SUCCESS.toString();
                msg = "修改成功";
            }
            /* 调用后,返回失败状态码,或无返回 */
            else {
                result = ConstantEnum.FAILURE.toString();
                msg = "修改调用接口失败";
                logger.info("修改云硬盘信息调用资源池代理接口失败");
                return ConstantEnum.FAILURE.toString();
            }
        }

        /* 修改了云硬盘容量,且手动审批 */
        if (flag > 0 && !AUTO.equals(audit)) {

            /* 添加至更改表 */
            DiskInfo respDisk = new DiskInfo();
            respDisk.setCaseId(caseId);
            respDisk.setDiskId(diskId);
            respDisk.setDiskSize(diskSize);
            respDisk.setResourcePoolId(resourcePoolId);
            respDisk.setResourcePoolPartId(resourcePoolPartId);
            respDisk.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());

            try {
                updateBatchVO
                        .add(new BatchVO(BatchVO.OPERATION_ADD, "ebsModifyDiskSize", respDisk));
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                        MessageFormat.format(getText("diskname.update.fail"), diskId), e);
                this.addActionError(MessageFormat.format(getText("diskname.update.fail"), diskId));
                result = ConstantEnum.ERROR.toString();
                msg = "修改失败,数据库操作异常";
                return ConstantEnum.FAILURE.toString();
            }

            /* 更新订单表 */
            OrderInfo order = new OrderInfo();

            order.setOrderId(diskInfo.getOrderId());
            /* 7为修改待审 */
            order.setStatus("7");
            order.setUpdateUser(sessionUser.getUserId());
            order.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());

            try {
                updateBatchVO
                        .add(new BatchVO(BatchVO.OPERATION_MOD, "updateDiskOrderStatus", order));
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                        MessageFormat.format(getText("diskname.update.fail"), diskId), e);
                this.addActionError(MessageFormat.format(getText("diskname.update.fail"), diskId));
                result = ConstantEnum.ERROR.toString();
                msg = "修改失败";
                return ConstantEnum.FAILURE.toString();
            }

            msg = "修改已提交";

        }

        try {
            ibatisDAO.updateBatchData(updateBatchVO);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("diskname.update.fail"), diskId), e);
            this.addActionError(MessageFormat.format(getText("diskname.update.fail"), diskId));
            result = ConstantEnum.ERROR.toString();
            msg = "修改失败";
            return ConstantEnum.FAILURE.toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("更新编码为[").append(diskId).append("]的名称成功，名称为[").append(diskName).append("]\n");
        logger.info(sb.toString());
        result = ConstantEnum.SUCCESS.toString();
        return ConstantEnum.SUCCESS.toString();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    /**
     * 获取diskSize字段数据
     * @return Returns the diskSize.
     */
    public String getDiskSize() {
        return diskSize;
    }

    /**
     * 设置diskSize字段数据
     * @param diskSize The diskSize to set.
     */
    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    /**
     * 获取resourcePoolId字段数据
     * @return Returns the resourcePoolId.
     */
    public String getResourcePoolId() {
        return resourcePoolId;
    }

    /**
     * 设置resourcePoolId字段数据
     * @param resourcePoolId The resourcePoolId to set.
     */
    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    /**
     * 获取resourcePoolPartId字段数据
     * @return Returns the resourcePoolPartId.
     */
    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    /**
     * 设置resourcePoolPartId字段数据
     * @param resourcePoolPartId The resourcePoolPartId to set.
     */
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

    /**
     * 获取modify字段数据
     * @return Returns the modify.
     */
    public EBSModify getModify() {
        return modify;
    }

    /**
     * 设置modify字段数据
     * @param modify The modify to set.
     */
    public void setModify(EBSModify modify) {
        this.modify = modify;
    }

    /**
     * 获取audit字段数据
     * @return Returns the audit.
     */
    public String getAudit() {
        return audit;
    }

    /**
     * 设置audit字段数据
     * @param audit The audit to set.
     */
    public void setAudit(String audit) {
        this.audit = audit;
    }

    /**
     * 获取caseId字段数据
     * @return Returns the caseId.
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 设置caseId字段数据
     * @param caseId The caseId to set.
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
