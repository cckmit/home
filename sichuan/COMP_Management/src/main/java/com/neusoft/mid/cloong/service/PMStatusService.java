/*******************************************************************************
 * @(#)PMStatusService.java 2013-2-19
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
import com.neusoft.mid.cloong.pm.core.PMOperatorType;
import com.neusoft.mid.cloong.pm.core.PMStatus;
import com.neusoft.mid.cloong.web.page.user.order.PMInstanceInfo;
import com.neusoft.mid.cloong.web.page.user.order.PMOperatorErrorInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机状态服务，判断物理机是否可进行相应操作，可添加物理机状态，删除物理机状态
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-14 下午04:45:43
 */
public class PMStatusService implements Serializable{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private static LogService logger = LogService.getLogger(PMStatusService.class);

    private IbatisDAO ibatisDAO;

    /**
     * 物理机状态列表
     */
    private ConcurrentHashMap<String, PMStatus> pmStatus = new ConcurrentHashMap<String, PMStatus>();

    /**
     * 物理机状态判断者
     */
    private PMJudge judge;

    /**
     * 把物理机添加到物理机状态列表，如果存在，则更新状态
     * @param vmId 物理机编号
     * @param status 物理机状态
     */
    public void addPMStatus(String pmId, PMStatus status) {
        pmStatus.put(pmId, status);
    }

    /**
     * 把物理机状态从物理机列表中删除
     * @param vmId 物理机编号
     */
    public void removePMStatus(String pmId) {
        pmStatus.remove(pmId);
    }

    /**
     * 判断物理机是否可以物理机操作
     * @param vmId 物理机编号
     * @param operator 物理机操作类型
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgePMOperator(String pmId, PMOperatorType operator,
            Holder<PMOperatorErrorInfo> holder) {
        PMStatus status = pmStatus.get(pmId);
        if (status == null) {
            holder.value.setErrorMessage("当前物理机已被删除，不能进行操作");
            holder.value.setErrorCode("0");
            logger.info("当前物理机已被删除，不能进行操作");
            return false;
        }
        boolean canOperator = false;
        switch (operator) {
        case PM_START:
            canOperator = judge.judgeStart(status, holder);
            break;
        case PM_STOP:
            canOperator = judge.judgeStop(status, holder);
            break;
        case PM_REBOOT:
            canOperator = judge.judgeReboot(status, holder);
            break;
        }
        return canOperator;
    }

    /**
     * 判断物理机是否可以进行删除
     * @param vmId 物理机编号
     * @return 可以操作返回true，不可以返回false
     */
    public boolean judgePMDelete(String pmId, Holder<PMOperatorErrorInfo> holder) {
        PMStatus status = pmStatus.get(pmId);
        if (status == null) {
            holder.value.setErrorMessage("当前物理机已被删除，不能进行操作");
            holder.value.setErrorCode("0");
            logger.info("当前物理机已被删除，不能重复删除");
            return false;
        }
        return judge.judgeDelete(status, holder);
    }

    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }

    /**
     * 初始化方法，把数据库中现有的物理机状态添加到物理机列表中
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public void init() throws SQLException {
        /*
        List<PMInstanceInfo> pmInfos = ibatisDAO.getData("queryPMAllStatus", null);
        for (PMInstanceInfo pmInfo : pmInfos) {
            pmStatus.put(pmInfo.getPmId(), pmInfo.getStatus());
        }*/
    }

    public void setJudge(PMJudge judge) {
        this.judge = judge;
    }
}
