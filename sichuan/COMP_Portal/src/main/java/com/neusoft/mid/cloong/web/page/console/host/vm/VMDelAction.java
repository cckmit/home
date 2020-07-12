/*******************************************************************************
 * @(#)VMManagerImpl.java 2013-1-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.host.vm;

import java.sql.SQLException;
import java.text.MessageFormat;

import javax.xml.ws.Holder;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMDel;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.host.vm.core.VmDeleteReq;
import com.neusoft.mid.cloong.host.vm.core.VmDeleteResp;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.service.VMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMDelFailErrorInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMOperatorErrorInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机删除操作 返回 json
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-1-14 上午10:08:55
 */
public class VMDelAction extends BaseAction {

    private static final long serialVersionUID = 1788639715970933829L;

    private static LogService logger = LogService.getLogger(VMDelAction.class);

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 虚拟机操作状态判断服务
     */
    private VMStatusService vmStatusService;

    /**
     * 跳转虚拟机列表界面
     */
    private static final String GOTOLISTPAGE = "1";

    /**
     * 当前页面
     */
    private static final String RETURNNOWPAGE = "0";

    /**
     * 虚拟机已经被删除
     */
    private static final String DETELTE = "2";

    /**
     * 虚拟机是否被删除的标准
     */
    private static final String IS_DELETE = "0";

    /**
     * 操作流水号生成器
     */
    private SequenceGenerator gen;

    /**
     * 虚拟机删除
     */
    private VMDel vmDel;

    /**
     * 获取虚拟机编码
     */
    private String vmId;

    private String resourcePoolId;

    private String resourcePoolPartId;

    /**
     * 返回标识
     */
    private String resultPath;

    /**
     * 返回提示消息
     */
    private String mes;

    // 读取配置文件 时多次使用的字符串.声明成变量

    private static final String USERVMIDTEXT = "vm.del.user.vmid.text";

    private static final String FAILMESSAGE = "vm.del.fail.message";

    /**
     * tip msg
     */
    private String msgTip = "虚拟机删除失败！数据库连接异常！";

    @Override
    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        resultPath = GOTOLISTPAGE;
        // 判断是否存在其他关联信息，包括云硬盘和任务
        if (isConnect(userId)) {
            return ConstantEnum.FAILURE.toString();
        }
        // 判断虚拟机状态是否正确，运行被删除
        VMOperatorErrorInfo errorInfo = new VMOperatorErrorInfo();
        Holder<VMOperatorErrorInfo> holder = new Holder<VMOperatorErrorInfo>(errorInfo);
        if (!vmStatusService.judgeVMDelete(vmId, holder)) {
            if (holder.value.getErrorCode().equals(IS_DELETE)) {
                resultPath = DETELTE;
                mes = getText("vm.del.status.del.text");
            } else {
                resultPath = RETURNNOWPAGE;
                mes = holder.value.getErrorMessage();
            }
            return ConstantEnum.FAILURE.toString();
        }
        // 更新数据库 删除虚拟机
        if (delVmDB(userId)) {
            return ConstantEnum.FAILURE.toString();
        }
        // 调用删除虚拟接口
        delVm(vmId, resourcePoolId, resourcePoolPartId);
        vmStatusService.removeVMStatus(vmId);
        return ConstantEnum.SUCCESS.toString();
    }

    private boolean delVm(String vmId, String resourcePoolId, String resourcePoolPartId) {
        VmDeleteReq req = new VmDeleteReq();
        req.setVmId(vmId);
        req.setResourcePoolId(resourcePoolId);
        req.setResourcePoolPartId(resourcePoolPartId);
        req.setSerialNum(gen.generatorVMSerialNum("VMDel"));
        VmDeleteResp resp = vmDel.delVm(req);
        if (!resp.getResultCode().equals(SUCCEESS_CODE)) {
            logger.error(
                    CommonStatusCode.RUNTIME_EXCEPTION,
                    MessageFormat.format(getText("vm.delete.fail"), vmId) + "失败原因为："
                            + resp.getResultMessage() + "，本次操作的流水号为：" + req.getSerialNum());
            // 操作失败插入数据库，失败信息暂不入库
            // insertVMQueryErrorInfo(assmbleStateErrorInfo(req, resp));
            return false;
        }
        return true;
    }

    /**
     * 插入虚拟机删除失败
     * @param errorInfo
     */
    private void insertVMQueryErrorInfo(VMDelFailErrorInfo errorInfo) {
        try {
            ibatisDAO.insertData("insertVmDelFailErrorInfo", errorInfo);
        } catch (SQLException e) {
            logger.error(
                    CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "向数据库中插入编号为【" + errorInfo.getVmId() + "】的虚拟机删除错误信息错误，插入信息为："
                            + errorInfo.toString(), e);
        } catch (Exception e) {
            logger.error(
                    CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "向数据库中插入编号为【" + errorInfo.getVmId() + "】的虚拟机删除错误信息错误，插入信息为："
                            + errorInfo.toString(), e);
        }
    }

    /**
     * 获取错误bean
     * @param req
     * @param resp
     * @return
     */
    private VMDelFailErrorInfo assmbleStateErrorInfo(VmDeleteReq req, VmDeleteResp resp) {
        VMDelFailErrorInfo errorInfo = new VMDelFailErrorInfo();
        errorInfo.setVmId(req.getVmId());
        errorInfo.setFailCause(resp.getResultMessage());
        errorInfo.setFailCode(resp.getResultCode());
        errorInfo.setResourcePoolId(req.getResourcePoolId());
        errorInfo.setResourcePoolPartId(req.getResourcePoolPartId());
        errorInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        errorInfo.setSerialNum(req.getSerialNum());
        return errorInfo;
    }

    /**
     * 更新数据库，将虚拟机状态变为删除。
     * @param userId 当前用户id
     * @return 成功 true 失败 false
     */
    private boolean delVmDB(String userId) {
        VMInstanceInfo vmInfo = new VMInstanceInfo();
        vmInfo.setVmId(vmId);
        vmInfo.setStatus(VMStatus.DELETED);
        vmInfo.setUpdateUser(userId);
        vmInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        try {
            int upNum = ibatisDAO.updateData("updateVMStatusAndOwn", vmInfo);
            if (upNum == 1) {
                mes = getText("vm.del.sucess");
                if (logger.isDebugEnable()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(MessageFormat.format(getText(USERVMIDTEXT), userId, vmId));
                    sb.append(getText("vm.del.debug.sucess"));
                    logger.debug(sb.toString());
                }
            } else {
                resultPath = RETURNNOWPAGE;
                mes = getText("vm.del.status.del.text");
                if (logger.isDebugEnable()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(MessageFormat.format(getText(USERVMIDTEXT), userId, vmId));
                    sb.append(getText(FAILMESSAGE));
                    logger.debug(sb.toString());
                }
                return true;
            }
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, msgTip, e);
            this.addActionError(msgTip);
            resultPath = RETURNNOWPAGE;
            mes = getText(FAILMESSAGE);
            return true;
        }
        return false;
    }

    /**
     * 查询虚拟机是否存在关联数据
     * @param userId
     * @return 不存在关联 false 存在关联 true
     */
    private boolean isConnect(String userId) {
        // 查询其关联是否已被删除
        StringBuffer res = new StringBuffer();
        int vhCount = 0;
        int vmBakCount = 0;
        try {
            // 是否挂载云硬盘
            vhCount = ibatisDAO.getCount("getCountEbsByVMid", vmId);
            // 是否有备份任务 由于虚拟机任务无法删除，所以在删除虚拟机时不校验是否有备份任务了
            // vmBakCount = ibatisDAO.getCount("getCountVmBakByVMid", vmId);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, msgTip, e);
            this.addActionError(msgTip);
            resultPath = RETURNNOWPAGE;
            mes = getText(FAILMESSAGE);
            return true;
            // return ConstantEnum.FAILURE.toString();
        }
        if (vhCount > 0) {
            if (null == res || "".equals(res.toString())) {
                res.append(getText("vm.del.vh.beings"));
            } else {
                res.append(getText("vm.del.vh.being"));
            }
        }
        if (vmBakCount > 0) {
            if (null == res || "".equals(res.toString())) {
                res.append(getText("vm.del.vmBak.beings"));
            } else {
                res.append(getText("vm.del.vmBak.being"));
            }
        }
        if (!"".equals(res.toString())) {
            res.append(getText("vm.del.connect.over.text"));
            mes = res.toString();
            resultPath = RETURNNOWPAGE;
            if (logger.isDebugEnable()) {
                StringBuilder sb = new StringBuilder();
                sb.append(getText(USERVMIDTEXT, userId, vmId));
                sb.append("\n").append(res);
                logger.debug(sb.toString());
            }
            return true;
        } else {
            return false;
        }
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    public VMDel getVmDel() {
        return vmDel;
    }

    public void setVmDel(VMDel vmDel) {
        this.vmDel = vmDel;
    }

    public String getMes() {
        return mes;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public void setVmStatusService(VMStatusService vmStatusService) {
        this.vmStatusService = vmStatusService;
    }

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }

}
