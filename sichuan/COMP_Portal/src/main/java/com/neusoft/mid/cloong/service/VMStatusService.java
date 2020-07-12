/*******************************************************************************
 * @(#)VMStatusService.java 2013-2-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.ws.Holder;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.host.vm.core.VMOperatorType;
import com.neusoft.mid.cloong.host.vm.core.VMStatus;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMInstanceInfo;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMOperatorErrorInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机状态服务，判断虚拟机是否可进行相应操作，可添加虚拟机状态，删除虚拟机状态
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-19 下午03:44:00
 */
public class VMStatusService implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(VMJudge.class);

    private IbatisDAO ibatisDAO;

    /**
     * 虚拟机状态列表
     */
    private ConcurrentHashMap<String, VMStatus> vmStatus = new ConcurrentHashMap<String, VMStatus>();

    /**
     * 虚拟机状态判断者
     */
    private VMJudge judge;

    /**
     * 把虚拟机添加到虚拟机状态列表，如果存在，则更新状态
     * @param vmId 虚拟机编号
     * @param status 虚拟机状态
     */
    public void addVMStatus(String vmId, VMStatus status) {
        vmStatus.put(vmId, status);
    }

    /**
     * 把虚拟机状态从虚拟机列表中删除
     * @param vmId 虚拟机编号
     */
    public void removeVMStatus(String vmId) {
        vmStatus.remove(vmId);
    }
    
    /**
     * 从内存中取虚拟机状态
     * @param vmId 虚拟机编号
     */
    public VMStatus getVMStatus(String vmId) {
        return vmStatus.get(vmId);
    }

    /**
     * 判断虚拟机是否可以虚拟机操作
     * @param vmId 虚拟机编号
     * @param operator 虚拟机操作类型
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeVMOperator(String vmId, VMOperatorType operator,
            Holder<VMOperatorErrorInfo> holder) {
        VMStatus status = vmStatus.get(vmId);
        if (status == null) {
            holder.value.setErrorMessage("当前虚拟机已被删除，不能进行操作");
            holder.value.setErrorCode("0");
            logger.info("当前虚拟机已被删除，不能进行操作");
            return false;
        }
        boolean canOperator = false;
        switch (operator) {
        case VM_START:
            canOperator = judge.judgeStart(status, holder);
            break;
        case VM_STOP:
            canOperator = judge.judgeStop(status, holder);
            break;
        case VM_PAUSE:
            canOperator = judge.judgePause(status, holder);
            break;
        case VM_RESUME:
            canOperator = judge.judgeResume(status, holder);
            break;
        case VM_REBOOT:
            canOperator = judge.judgeReboot(status, holder);
            break;
        }
        return canOperator;
    }

    /**
     * 判断虚拟机是否可以进行删除
     * @param vmId 虚拟机编号
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgeVMDelete(String vmId, Holder<VMOperatorErrorInfo> holder) {
        VMStatus status = vmStatus.get(vmId);
        if (status == null) {
            holder.value.setErrorMessage("当前虚拟机已被删除，不能进行操作");
            holder.value.setErrorCode("0");
            logger.info("当前虚拟机已被删除，不能重复删除");
            return false;
        }
        return judge.judgeDelete(status, holder);
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    /**
     * 初始化方法，把数据库中现有的虚拟机状态添加到虚拟机列表中
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public void init() throws SQLException {
        List<VMInstanceInfo> vmInfos = ibatisDAO.getData("queryAllStatus", null);
        for (VMInstanceInfo vmInfo : vmInfos) {
            vmStatus.put(vmInfo.getVmId(), vmInfo.getStatus());
        }
    }

    public void setJudge(VMJudge judge) {
        this.judge = judge;
    }
}
