/*******************************************************************************
 * @(#)VmGhostInfoAction.java 2014-5-8
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.host.vm.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.host.vm.core.VMCreate;
import com.neusoft.mid.cloong.host.vm.core.VMCreateFail;
import com.neusoft.mid.cloong.host.vm.core.VMCreateReq;
import com.neusoft.mid.cloong.host.vm.core.VMCreateResp;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.service.VMStatusService;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OrderInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 提交克隆虚拟机订单
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-5-8 下午1:31:40
 */
public class VmGhostInfoAction extends BaseAction {

    /**
     * serialVersionUID : 序列号
     */
    private static final long serialVersionUID = 6894848803083471136L;

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(VmGhostInfoAction.class);

    /**
     * 克隆使用的备份ID
     */
    private String vmBackupId;

    /**
     * 数量
     */
    private String num;

    /**
     * 时长
     */
    private String lengthTime;

    /**
     * vmRemark
     */
    private String vmRemark;

    /**
     * 虚拟机状态服务类
     */
    private VMStatusService vmStatusService;

    /**
     * 序列号生成器
     */
    private SequenceGenerator sequenceGenerator;

    /**
     * 虚拟机创建接口
     */
    private VMCreate vmGhost;

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 设置是否人工审批 0：自动 1：人工
     */
    private String audit;

    /**
     * 默认自动审批
     */
    private static final String AUTO = "0";

    /**
     * tip ACCEPT_TIME
     */
    private String acceptTimeTip = "ACCEPT_TIME";

    /**
     * tip ADD
     */
    private String addTip = "ADD";
    
    /**
     * 返回结果
     */
    private String resultPath;
    
    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        if (logger.isDebugEnable()) {
            logger.debug("克隆所使用 备份 ID = " + vmBackupId);
            logger.debug("num = " + num);
            logger.debug("lengthTime = " + lengthTime);
            logger.debug("vmRemark = " + vmRemark);

        }
        //设置返回为失败
        resultPath = ConstantEnum.FAILURE.toString();
        
        if(vmBackupId.isEmpty()){
            this.addActionError(getText("vm.ghost.vmbakId.isNull.fail"));
            errMsg = getText("vm.ghost.vmbakId.isNull.fail");
            return ConstantEnum.FAILURE.toString();
        }
        
        // TODO: 记入操作日志

        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();

        VMCreateReq vmReq = new VMCreateReq();

        // 通过备份ID获取实例信息
        VMInstanceInfo vmInstanceInfo = null;
        try {
            vmInstanceInfo = (VMInstanceInfo) ibatisDAO.getSingleRecord(
                    "getVmInstanceInfoByVmBackupId", vmBackupId);
        } catch (Exception e) {
            // 如果记入数据库失败，打印error日志，记录虚拟机订单发生异常，返回浏览虚拟机列表页面，提示“提交克隆虚拟机订单失败！”。
            logger.error(VMStatusCode.QUERYVMINSTANCEBYVMBACKUPID_EXCEPTION_CODE,
                    getText("vm.ghostinfo.queryVmInstanceByVmBackupId.fail"), e);
            this.addActionError(getText("vm.ghostinfo.queryVmInstanceByVmBackupId.fail"));
            errMsg = getText("vm.ghostinfo.queryVmInstanceByVmBackupId.fail");
            return ConstantEnum.FAILURE.toString();
        }
        if (null == vmInstanceInfo) {
            this.addActionError(getText("vm.ghostinfo.queryVmInstanceByVmBackupId.fail"));
            errMsg = getText("vm.ghostinfo.queryVmInstanceByVmBackupId.fail");
            return ConstantEnum.FAILURE.toString();
        }

        // 拼凑订单信息
        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
        // 根据虚拟机数量拆单
        int intNum = Integer.parseInt(num);

        // 同一个订单父ID
        String parentId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.GH.getParentCode());
        for (int i = 1; i <= intNum; i++) {
            OrderInfo orderInfo = new OrderInfo();
            String orderId = sequenceGenerator.generatorOrderId(ResourceTypeEnum.GH.toString());
            String caseId = sequenceGenerator.generatorCaseId(ResourceTypeEnum.GH.toString());
            // 拼凑订单信息，将订单信息入库。订单审核状态为待审。订单生效状态为未生效。
            assembleOrder(userId, orderInfo, orderId, caseId,vmInstanceInfo);
            orderInfo.setParentId(parentId);// 同一个订单父ID
            orderInfos.add(orderInfo);
            if (logger.isDebugEnable()) {
                logger.debug("orderId = " + orderId);
                logger.debug("caseId = " + caseId);
                logger.debug("parentId = " + parentId);
            }
        }

        VMCreateResp resp = new VMCreateResp();
        resp.setResultCode(SUCCEESS_CODE);
        if (AUTO.equals(audit)) {
            // 发送克隆订单至接口
            assembleReq(vmReq, vmInstanceInfo);
            resp = vmGhost.createVM(vmReq);
        }

        List<VMInstanceInfo> vmInrstances = new ArrayList<VMInstanceInfo>();
        boolean doWithRespRes = doWithResp(userId, vmReq, updateBatchVO, orderInfos, intNum, resp,
                vmInrstances, vmInstanceInfo);

        if (!doWithRespRes) {
            return ConstantEnum.FAILURE.toString();
        }

        if (logger.isDebugEnable()) {
            logger.debug("克隆虚拟机发送申请成功！");
        }

        if (AUTO.equals(audit)) {
            // 把虚拟机状态添加到虚拟机状态服务中
            for (VMInstanceInfo vmInfo : vmInrstances) {
                vmStatusService.addVMStatus(vmInfo.getVmId(), vmInfo.getStatus());
            }
        }
        resultPath = ConstantEnum.SUCCESS.toString();
        // TODO：出话单
        msg = "克隆虚拟机发送申请成功！";
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * doWithResp 处理结果
     * @param userId 用户ID
     * @param vmReq 虚拟机请求
     * @param updateBatchVO 数据处理VO
     * @param orderInfos 订单信息
     * @param intNum 数量
     * @param resp 应答
     * @param vmInrstances 虚拟机实例信息
     * @return 处理结果
     */
    private boolean doWithResp(String userId, VMCreateReq vmReq, List<BatchVO> updateBatchVO,
            List<OrderInfo> orderInfos, int intNum, VMCreateResp resp,
            List<VMInstanceInfo> vmInrstances, VMInstanceInfo vmInstance) {
        if (resp.getResultCode().equals(SUCCEESS_CODE)) {
            // 返回成功，虚拟机订单和实例入库

            // 拼凑实例信息
            try {

                // 根据虚拟机数量拆单
                for (int i = 1; i <= intNum; i++) {
                    if (AUTO.equals(audit)) {
                        orderInfos.get(i - 1).setEffectiveTime(
                                resp.getVmInfo().get(i - 1).get(acceptTimeTip));
                    }

                    // TODO：价格待定
//                    orderInfos.get(i - 1).setAllPrice("3333");
                    // 更新数据库
                    updateBatchVO.add(new BatchVO(addTip, "createVMOrder", orderInfos.get(i - 1)));

                    // 拼凑VM实例信息
                    VMInstanceInfo vmInstanceInfo = new VMInstanceInfo();

                    if (AUTO.equals(audit)) {
                        assembleVMInstance(userId, orderInfos.get(i - 1),
                                resp.getVmInfo().get(i - 1), vmInstanceInfo, vmInstance);
                    } else {
                        assembleVMInstanceAudit(userId, orderInfos.get(i - 1), vmInstanceInfo,
                                vmInstance);
                    }

                    vmInrstances.add(vmInstanceInfo);

                    // 更新数据库
                    updateBatchVO.add(new BatchVO(addTip, "createVMInstanceInfo", vmInrstances
                            .get(i - 1)));
                    if (logger.isDebugEnable()) {
                        logger.debug("拼凑订单信息，第" + i + "条");
                    }
                }
                ibatisDAO.updateBatchData(updateBatchVO);
                if (logger.isDebugEnable()) {
                    logger.debug("克隆虚拟机订单记入数据库成功！");
                }
            } catch (SQLException e) {
                // 如果记入数据库失败，打印error日志，记录虚拟机订单发生异常，返回浏览虚拟机列表页面，提示“提交克隆虚拟机订单失败！”。
                logger.error(VMStatusCode.CREATEVMORDER_EXCEPTION_CODE,
                        getText("vm.ghostinfo.fail"), e);
                this.addActionError(getText("vm.ghostinfo.fail"));
                errMsg = getText("vm.ghostinfo.fail");
                return false;
            } catch (Exception e) {
                // 如果记入数据库失败，打印error日志，记录虚拟机订单发生异常，返回浏览虚拟机列表页面，提示“提交克隆虚拟机订单失败！”。
                logger.error(VMStatusCode.CREATEVMORDER_EXCEPTION_CODE,
                        getText("vm.ghostinfo.fail"), e);
                this.addActionError(getText("vm.ghostinfo.fail"));
                errMsg = getText("vm.ghostinfo.fail");
                return false;
            }
        } else {
            // 返回失败，入失败库
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "克隆虚拟机发送创建失败！");
            this.addActionError(getText("vm.ghostinfo.sendcreateVM.fail"));
            errMsg = getText("vm.ghostinfo.sendcreateVM.fail");
            // 入失败库
            List<VMCreateFail> vmCreateFails = assembleVMCreateFail(vmReq, resp);
            List<BatchVO> insertFailBatchVO = new ArrayList<BatchVO>();
            for (VMCreateFail vmCreateFail : vmCreateFails) {
                insertFailBatchVO.add(new BatchVO(addTip, "insertVMFail", vmCreateFail));
            }
            try {
                ibatisDAO.updateBatchData(insertFailBatchVO);
            } catch (SQLException e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "克隆虚拟机失败后入失败库发生数据库异常",
                        e);
            } catch (Exception e) {
                logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "克隆虚拟机失败后入失败库发生数据库异常",
                        e);
            }
            return false;
        }
        return true;
    }

    /**
     * assembleReq 生成请求
     * @param vmReq 虚拟机请求
     * @param vmInstanceInfo 克隆虚拟机实例信息
     */
    private void assembleReq(VMCreateReq vmReq, VMInstanceInfo vmInstanceInfo) {
        vmReq.setNum(num);
        vmReq.setVmBackupId(vmBackupId);
        vmReq.setResourcePoolId(vmInstanceInfo.getResPoolId());
        vmReq.setResourcePoolPartId(vmInstanceInfo.getResPoolPartId());
    }

    /**
     * assembleVMCreateFail 拼装虚拟机创建失败信息
     * @param tempVMReq 虚拟机请求
     * @param vmResp 应答
     * @return 失败消息List
     */
    private List<VMCreateFail> assembleVMCreateFail(VMCreateReq tempVMReq, VMCreateResp vmResp) {
        List<VMCreateFail> vmCreateFails = new ArrayList<VMCreateFail>();
        VMCreateFail vmCreateFail = new VMCreateFail();
        vmCreateFail.setFailCause(vmResp.getResultMessage());
        vmCreateFail.setFailCode(vmResp.getResultCode());
        vmCreateFail.setResPoolId(tempVMReq.getResourcePoolId());
        vmCreateFail.setResPoolPartId(tempVMReq.getResourcePoolPartId());
        vmCreateFail.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        vmCreateFail.setStandardId(tempVMReq.getStandardId());
        vmCreateFail.setSubNetwork(tempVMReq.getSubNetwork());
        vmCreateFail.setOsId(tempVMReq.getOsId());
        vmCreateFail.setNum(tempVMReq.getNum());
        vmCreateFails.add(vmCreateFail);
        return vmCreateFails;
    }

    /**
     * assembleVMInstance 拼装虚拟机实例
     * @param userId 用户ID
     * @param orderInfo 订单信息
     * @param vmInfoSet 虚拟机信息
     * @param vmInstanceInfo 实例信息
     * @param tempOsInfo OS信息
     */
    private void assembleVMInstance(String userId, OrderInfo orderInfo,
            Map<String, String> vmInfoSet, VMInstanceInfo vmInstanceInfo, VMInstanceInfo vmInstance) {
        vmInstanceInfo.setCaseId(orderInfo.getCaseId());
        vmInstanceInfo.setStandardId(vmInstance.getStandardId());
        vmInstanceInfo.setVmId(vmInfoSet.get("VM_ID"));
        vmInstanceInfo.setPrivateIp(vmInfoSet.get("PRIVATE_IP"));
        vmInstanceInfo.setVmPassword(vmInfoSet.get("VM_PASSWORD"));
        vmInstanceInfo.setVmName(vmInfoSet.get("VM_NAME"));
        vmInstanceInfo.setStatus(VMStatus.CREATING);
        vmInstanceInfo.setResPoolId(vmInstance.getResPoolId());
        vmInstanceInfo.setResPoolPartId(vmInstance.getResPoolPartId());
        vmInstanceInfo.setOrderId(orderInfo.getOrderId());
        vmInstanceInfo.setCpuNum(vmInstance.getCpuNum());
        vmInstanceInfo.setRamSize(vmInstance.getRamSize());
        vmInstanceInfo.setDiscSize(vmInstance.getDiscSize());
        vmInstanceInfo.setIsoId(vmInstance.getIsoId());
        vmInstanceInfo.setIsoName(vmInstance.getIsoName());
        vmInstanceInfo.setIsoDescription(vmInstance.getIsoDescription());
        vmInstanceInfo.setDescription(vmRemark);
        vmInstanceInfo.setCreateTime(vmInfoSet.get(acceptTimeTip));
        vmInstanceInfo.setCreateUser(userId);
        vmInstanceInfo.setUpdateTime(vmInfoSet.get(acceptTimeTip));
        vmInstanceInfo.setUpdateUser(userId);
        vmInstanceInfo.setResPoolName(vmInstance.getResPoolName());
        vmInstanceInfo.setResPoolPartName(vmInstance.getResPoolPartName());
    }

    /**
     * assembleOrder 拼装订单信息
     * @param userId 用户ID
     * @param orderInfo 订单信息
     * @param orderId 订单ID
     * @param caseId 实例ID
     * @param vmInstanceInfo 备份查询出的实例信息
     */
    private void assembleOrder(String userId, OrderInfo orderInfo, String orderId, String caseId,VMInstanceInfo vmInstanceInfo) {
        orderInfo.setOrderId(orderId);
        if (AUTO.equals(audit)) {
            orderInfo.setStatus("1");
        } else {
            orderInfo.setStatus("0");
        }
        String[] templengthTime = lengthTime.split("_");
        orderInfo.setLengthTime(templengthTime[0]);
        orderInfo.setLengthUnit(templengthTime[1]);
        orderInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        orderInfo.setCreateUser(userId);
        orderInfo.setUpdateUser(userId);
        orderInfo.setCaseId(caseId);
        
        orderInfo.setItemId(vmInstanceInfo.getItemId());
        orderInfo.setStandardId(vmInstanceInfo.getStandardId());
        orderInfo.setOsId(vmInstanceInfo.getIsoId());
        
        orderInfo.setStandardType("11");
        orderInfo.setNum("1");
    }

    /**
     * assembleVMInstanceAudit 拼装虚拟机实例(人工审批用)
     * @param userId 用户ID
     * @param orderInfo 订单信息
     * @param vmInstanceInfo 实例信息
     * @param tempOsInfo 虚拟机信息
     */
    private void assembleVMInstanceAudit(String userId, OrderInfo orderInfo,
            VMInstanceInfo vmInstanceInfo, VMInstanceInfo vmInstance) {

        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        vmInstanceInfo.setCaseId(orderInfo.getCaseId());
        vmInstanceInfo.setStandardId(vmInstance.getStandardId());
        vmInstanceInfo.setStatus(VMStatus.PREPARE);
        vmInstanceInfo.setResPoolId(vmInstance.getResPoolId());
        vmInstanceInfo.setResPoolPartId(vmInstance.getResPoolPartId());
        vmInstanceInfo.setOrderId(orderInfo.getOrderId());
        vmInstanceInfo.setCpuNum(vmInstance.getCpuNum());
        vmInstanceInfo.setRamSize(vmInstance.getRamSize());
        vmInstanceInfo.setDiscSize(vmInstance.getDiscSize());
        vmInstanceInfo.setIsoId(vmInstance.getIsoId());
        vmInstanceInfo.setIsoName(vmInstance.getIsoName());
        vmInstanceInfo.setIsoDescription(vmInstance.getIsoDescription());
        vmInstanceInfo.setDescription(vmRemark);
        vmInstanceInfo.setCreateTime(time);
        vmInstanceInfo.setCreateUser(userId);
        vmInstanceInfo.setUpdateTime(time);
        vmInstanceInfo.setUpdateUser(userId);
        vmInstanceInfo.setResPoolName(vmInstance.getResPoolName());
        vmInstanceInfo.setResPoolPartName(vmInstance.getResPoolPartName());
    }

    /**
     * 获取vmBackupId字段数据
     * @return Returns the vmBackupId.
     */
    public String getVmBackupId() {
        return vmBackupId;
    }

    /**
     * 设置vmBackupId字段数据
     * @param vmBackupId The vmBackupId to set.
     */
    public void setVmBackupId(String vmBackupId) {
        this.vmBackupId = vmBackupId;
    }

    /**
     * 获取num字段数据
     * @return Returns the num.
     */
    public String getNum() {
        return num;
    }

    /**
     * 设置num字段数据
     * @param num The num to set.
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 获取lengthTime字段数据
     * @return Returns the lengthTime.
     */
    public String getLengthTime() {
        return lengthTime;
    }

    /**
     * 设置lengthTime字段数据
     * @param lengthTime The lengthTime to set.
     */
    public void setLengthTime(String lengthTime) {
        this.lengthTime = lengthTime;
    }

    /**
     * 获取vmRemark字段数据
     * @return Returns the vmRemark.
     */
    public String getVmRemark() {
        return vmRemark;
    }

    /**
     * 设置vmRemark字段数据
     * @param vmRemark The vmRemark to set.
     */
    public void setVmRemark(String vmRemark) {
        this.vmRemark = vmRemark;
    }

    /**
     * 获取vmStatusService字段数据
     * @return Returns the vmStatusService.
     */
    public VMStatusService getVmStatusService() {
        return vmStatusService;
    }

    /**
     * 设置vmStatusService字段数据
     * @param vmStatusService The vmStatusService to set.
     */
    public void setVmStatusService(VMStatusService vmStatusService) {
        this.vmStatusService = vmStatusService;
    }

    /**
     * 获取sequenceGenerator字段数据
     * @return Returns the sequenceGenerator.
     */
    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    /**
     * 设置sequenceGenerator字段数据
     * @param sequenceGenerator The sequenceGenerator to set.
     */
    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * 获取vmGhost字段数据
     * @return Returns the vmGhost.
     */
    public VMCreate getVmGhost() {
        return vmGhost;
    }

    /**
     * 设置vmGhost字段数据
     * @param vmGhost The vmGhost to set.
     */
    public void setVmGhost(VMCreate vmGhost) {
        this.vmGhost = vmGhost;
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
     * 获取resultPath字段数据
     * @return Returns the resultPath.
     */
    public String getResultPath() {
        return resultPath;
    }

    /**
     * 设置resultPath字段数据
     * @param resultPath The resultPath to set.
     */
    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    
    /**
     * 获取errMsg字段数据
     * @return Returns the errMsg.
     */
    public String getErrMsg() {
        return errMsg;
    }
    
    /**
     * 获取msg字段数据
     * @return Returns the msg.
     */
    public String getMsg() {
        return msg;
    }
}
