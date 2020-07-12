package com.neusoft.mid.cloong.host.vm.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.host.vm.ICreateVMSender;
import com.neusoft.mid.cloong.host.vm.VMCreateFail;
import com.neusoft.mid.cloong.host.vm.VMCreateReq;
import com.neusoft.mid.cloong.host.vm.VMCustomCreateReq;
import com.neusoft.mid.cloong.host.vm.VMInfo;
import com.neusoft.mid.cloong.host.vm.VMStateQuery;
import com.neusoft.mid.cloong.host.vm.VMStateQueryReq;
import com.neusoft.mid.cloong.order.info.OrderProcessor;
import com.neusoft.mid.cloong.order.info.OrderInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.RPPVMStatusQueryResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMStatus;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 发送创建虚拟机请求后查询状态、创建IP、创建带宽的请求
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午02:15:42
 */
public class CreateVMSenderImp implements ICreateVMSender {

    /**
     * assembleVMStateReq 创建虚拟机组装查询request
     * @param vmInfo 查询结果vmInfo
     * @param vmReq 创建虚拟机req
     */
    public void assembleVMStateReq(String vmId, VMCreateReq vmReq) {
        vmStateReq = new VMStateQueryReq();
        vmStateReq.setVmId(vmId);
        vmStateReq.setPassword(vmReq.getPassword());
        vmStateReq.setResourcePoolId(vmReq.getResourcePoolId());
        vmStateReq.setResourcePoolPartId(vmReq.getResourcePoolPartId());
        vmStateReq.setResourceUrl(vmReq.getResourceUrl());
        vmStateReq.setTimestamp(vmReq.getTimestamp());
        vmStateReq.setTransactionID(vmReq.getTransactionID());
        if (logger.isDebugEnable()) {
            logger.debug(vmStateReq.toString());
        }

    }

    /**
     * assembleVMCustomStateReq 高定制创建虚拟机组装查询request
     * @param vmId 虚拟机id
     * @param vmReq 创建虚拟机req
     */
    public void assembleVMCustomStateReq(String vmId, VMCustomCreateReq vmReq) {
        vmStateReq = new VMStateQueryReq();
        vmStateReq.setVmId(vmId);
        vmStateReq.setPassword(vmReq.getPassword());
        vmStateReq.setResourcePoolId(vmReq.getResourcePoolId());
        vmStateReq.setResourcePoolPartId(vmReq.getResourcePoolPartId());
        vmStateReq.setResourceUrl(vmReq.getResourceUrl());
        vmStateReq.setTimestamp(vmReq.getTimestamp());
        vmStateReq.setTransactionID(vmReq.getTransactionID());
        if (logger.isDebugEnable()) {
            logger.debug(vmStateReq.toString());
        }

    }

    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(CreateVMSenderImp.class);

    /**
     * 和资源池交互的虚拟机状态查询接口
     */
    private VMStateQuery vmStateQuery;

    private IbatisDAO ibatisDAO;

    private VMStateQueryReq vmStateReq;

    private int interval = 5;

    private long timeout = 10800000;

    @Override
    public void send() {
        if (queryVM()) {
            if (createIP()) {
                createNetwork();
                bandIp();
            }
        }

    }

    /**
     * 查询虚拟机状态
     */
    public boolean queryVM() {
        if (logger.isDebugEnable()) {
            logger.debug(vmStateReq.toString());
        }
        // 循环查询虚拟机状态次数
        int number = 0;
        long startMs = System.currentTimeMillis();
        RPPVMStatusQueryResp vmResp = vmStateQuery.queryVMState(vmStateReq);
        if (vmResp.getResultCode().equals("00000000")) {
            // 如果虚拟机不是最终状态，再查
            while (!VMStatus.RUNNING.equals(vmResp.getVmStatus()) && !VMStatus.STOP.equals(vmResp.getVmStatus())
                    && !VMStatus.PAUSE.equals(vmResp.getVmStatus())) {

                if (VMStatus.OPERATE_FAIL.equals(vmResp.getVmStatus())) {
                    // 资源池返回创建操作失败
                    logger.error(CommonStatusCode.INTERNAL_ERROR, "编号为" + vmStateReq.getVmId() + "的虚拟机资源池返回创建操作失败！");
                    handleFail(vmStateReq, vmResp);
                    return false;
                }

                // 判断查询虚拟机状态是否超时，超时时间为1小时
                long endMs = System.currentTimeMillis();
                logger.info("-----------------------------查询虚拟机开始时间为："+startMs);
                logger.info("-----------------------------查询虚拟机结束时间为："+endMs);
                logger.info("-----------------------------查询虚拟机超时时间为："+timeout);
                if ((endMs - startMs) > timeout) {
                    // 超时异常
                    logger.error(CommonStatusCode.INTERNAL_ERROR, "编号为" + vmStateReq.getVmId()
                            + "的虚拟机状态更新失败,失败原因：虚拟机状态查询超时！");
                    handleError(vmStateReq, vmResp);
                    return false;
                }
                // 循环时间间隔为60秒。
                try {
                    TimeUnit.SECONDS.sleep(interval);
                } catch (InterruptedException e1) {
                    logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败", e1);
                    handleError(vmStateReq, vmResp);
                    return false;
                } catch (Exception e2) {
                    logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败", e2);
                    handleError(vmStateReq, vmResp);
                    return false;
                }
                // 查询虚拟机状态
                vmResp = vmStateQuery.queryVMState(vmStateReq);
                if (!vmResp.getResultCode().equals("00000000")) {
                    logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败");
                    handleError(vmStateReq, vmResp);
                    return false;
                }
            }
            // TODO:创建线程时间先后不能确定，所以会造成问题，暂时sleep5秒来解决，避免演示出问题，以后在创建action先Insert再Update,insert时，状态就插入进去。
            // 时间间隔为5秒。
            try {
                logger.debug("现在时间是：" + new DateTime().toString());
                TimeUnit.SECONDS.sleep(5);
                logger.debug("停5秒后时间是：" + new DateTime().toString());
            } catch (InterruptedException e1) {
                logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败", e1);
                handleError(vmStateReq, vmResp);
                return false;
            } catch (Exception e2) {
                logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败", e2);
                handleError(vmStateReq, vmResp);
                return false;
            }
            // 如果成功且为最终状态，更新VM实例状态
            VMInfo tempVMInfo = new VMInfo();
            tempVMInfo.setVmId(vmStateReq.getVmId());
            tempVMInfo.setVmState(vmResp.getVmStatus().getCode());
            // 虚拟机运行中后，返回私网IP
            tempVMInfo.setOperationURL(vmResp.getOperationURL());
            tempVMInfo.setUserName(vmResp.getUserName());
            tempVMInfo.setPassword(vmResp.getPassWord());

            try {
                ibatisDAO.updateData("updateVMStatus", tempVMInfo);
                if (logger.isDebugEnable()) {
                    logger.debug("更新虚拟机实例状态成功，vmId为" + vmStateReq.getVmId() + "，状态为" + tempVMInfo.getVmState());
                }
            } catch (SQLException e) {
                logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败", e);
                handleError(vmStateReq, vmResp);
                return false;
            } catch (Exception e2) {
                logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败", e2);
                handleError(vmStateReq, vmResp);
                return false;
            }
            // 如果成功且为最终状态，更新订单生效
            try {
                // 查询出对应订单信息
                OrderInfo orderInfo = (OrderInfo) ibatisDAO.getSingleRecord("queryVMOrderInfo", vmStateReq);

                // 设置订单到期时间
                BatchVO updateOrderVO = OrderProcessor.genOrderToEffectiveVO(vmStateReq.getTimestamp(), orderInfo);
                List<BatchVO> voList = new ArrayList<BatchVO>();
                voList.add(updateOrderVO);
                ibatisDAO.updateBatchData(voList);

                if (logger.isDebugEnable()) {
                    logger.debug("更新订单成功, vmId为" + vmStateReq.getVmId());
                }
            } catch (SQLException e) {
                logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败", e);
                handleError(vmStateReq, vmResp);
                return false;
            } catch (Exception e2) {
                logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败", e2);
                handleError(vmStateReq, vmResp);
                return false;
            }
        } else {
            logger.error("编号为" + vmStateReq.getVmId() + "的虚拟机状态更新失败");
            handleError(vmStateReq, vmResp);
            return false;
        }
        return true;
    }

    /**
     * 创建IP
     */
    public boolean createIP() {
        // 判断是否有IP资源，发送创建IP申请， 向资源池系统发送创建IP请求，超时时间为10S。
        // 响应成功后，打印info日志“向资源池申请IP为XXX成功！”。将IP插入数据库。
        return true;
    }

    /**
     * 创建带宽
     */
    public boolean createNetwork() {
        // 判断是否有带宽资源，发送创建带宽申请，向资源池系统发送创建带宽请求，超时时间为10S。
        // 响应成功后，打印info日志“向资源池申请带宽为XXX成功！”。将带宽插入数据库。
        return true;
    }

    /**
     * 绑定IP
     */
    public boolean bandIp() {
        // 发送绑定IP至主机申请，向资源池系统发送绑定IP至主机请求，超时时间为10S。
        // 响应成功后，打印info日志“绑定IPXXX至虚拟机ID为XXX成功！”。将绑定IP与主机关系插入数据库。
        return true;
    }

    private void handleError(VMStateQueryReq tempVMStateReq, RPPVMStatusQueryResp vmResp) {
        // 更新VM失败状态
        updateVMStatus(assmbleErrorVMStatusInfo(tempVMStateReq));

        // 入失败表
        insertVMQueryErrorInfo(assembleVMCreateFail(tempVMStateReq, vmResp));
    }

    /**
     * handleFail 操作失败入库.
     * @param vmStateQueryReq
     * @param vmResp
     */
    private void handleFail(VMStateQueryReq vmStateQueryReq, RPPVMStatusQueryResp vmResp) {
        // 更新VM 资源池返回操作失败
        updateVMStatus(assmbleFailVMStatusInfo(vmStateQueryReq));

        // 入失败表
        insertVMQueryErrorInfo(assembleVMCreateFail(vmStateQueryReq, vmResp));
    }

    /**
     * assmbleFailVMStatusInfo 构造返回信息
     * @param VMStateQueryReq vmStateQueryReq 虚拟机状态查询接口请求
     * @return VMInfo vmInfo
     */
    private VMInfo assmbleFailVMStatusInfo(VMStateQueryReq vmStateQueryReq) {
        VMInfo vmInfo = new VMInfo();
        vmInfo.setVmId(vmStateQueryReq.getVmId());
        vmInfo.setVmState(VMStatus.OPERATE_FAIL.getCode());
        return vmInfo;
    }

    /**
     * assembleVMCreateFail 构造创建失败返回信息.
     * @param tempVMStateReq
     * @param vmResp
     * @return VMCreateFail vmCreateFail
     */
    private VMCreateFail assembleVMCreateFail(VMStateQueryReq tempVMStateReq, RPPVMStatusQueryResp vmResp) {
        VMCreateFail vmCreateFail = new VMCreateFail();
        vmCreateFail.setFailCause(vmResp.getResultMessage());
        vmCreateFail.setFailCode(vmResp.getResultCode());
        vmCreateFail.setResPoolId(tempVMStateReq.getResourcePoolId());
        vmCreateFail.setResPoolPartId(tempVMStateReq.getResourcePoolPartId());
        if (tempVMStateReq.getTimestamp().contains("-")) { // 首次创建查询状态时时间带格式
            vmCreateFail.setCreateTime(DateParse.parseTime(tempVMStateReq.getTimestamp()));
        } else { // 重启时从数据库查，时间不带格式，不转换
            vmCreateFail.setCreateTime(tempVMStateReq.getTimestamp());
        }
        vmCreateFail.setStandardId("");
        vmCreateFail.setSubNetwork("");
        vmCreateFail.setOsId("");
        vmCreateFail.setNum("1");
        return vmCreateFail;
    }

    @Override
    public void run() {
        synchronized (this) {
            send();
        }
    }

    private VMInfo assmbleErrorVMStatusInfo(VMStateQueryReq req) {
        VMInfo vmInfo = new VMInfo();
        vmInfo.setVmId(req.getVmId());
        vmInfo.setVmState(VMStatus.STATUSERROR.getCode());
        return vmInfo;
    }

    private void updateVMStatus(VMInfo vmInfo) {
        try {
            ibatisDAO.updateData("updateVMStatus", vmInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "编号为【" + vmInfo.getVmId()
                    + "】的虚拟机状态更新失败，虚拟机状态为【" + vmInfo.getVmState() + "】", e);
        } catch (Exception e2) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "编号为【" + vmInfo.getVmId()
                    + "】的虚拟机状态更新失败，虚拟机状态为【" + vmInfo.getVmState() + "】", e2);
        }
    }

    private void insertVMQueryErrorInfo(VMCreateFail vmCreateFail) {
        try {
            ibatisDAO.insertData("insertVMFail", vmCreateFail);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "向数据库中插入的创建虚拟机错误信息错误，插入信息为：" + vmCreateFail.toString(), e);
        } catch (Exception e2) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "向数据库中插入的创建虚拟机错误信息错误，插入信息为：" + vmCreateFail.toString(), e2);
        }
    }

    public VMStateQuery getVmStateQuery() {
        return vmStateQuery;
    }

    public void setVmStateQuery(VMStateQuery vmStateQuery) {
        this.vmStateQuery = vmStateQuery;
    }

    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

}
