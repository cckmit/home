/*******************************************************************************
 * @(#)VMStandardReport.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard;

import java.sql.SQLException;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机资源规格上报处理
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-19 下午03:59:17
 */
public class StandardReport {

    private static LogService logger = LogService.getLogger(StandardReport.class);

    private IbatisDAO ibatisDao;

    /**
     * 虚拟机资源规格处理类
     * @param templateId 模板ID
     * @param state 规格状态
     * @param resourcePoolId 资源池ID
     * @param resourcePoolPartId 资源池分区ID
     * @return InterfaceResultCode
     */
    public InterfaceResultCode process(String templateId, String state, String resourcePoolId, String resourcePoolPartId) {
        StandardSynchronizedState vmState = new StandardSynchronizedState();
        vmState.setTemplateId(templateId);
        vmState.setResourcePoolId(resourcePoolId);
        vmState.setResourcePoolPartId(resourcePoolPartId);
        vmState.setStandardState(stateParse(state));
        int updateResult = 0;
        try {
            updateResult = ibatisDao.updateData("updateStandardStatus", vmState);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "更新资源规格状态出错", e);
            return InterfaceResultCode.RESOURCE_POOL_ROXY_DATABASE_ERROR;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "更新资源规格状态出错", e);
            return InterfaceResultCode.RESOURCE_POOL_ROXY_DATABASE_ERROR;
        }
        if (updateResult != 1) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "更新资源规格状态失败,不存在要更新状态的模板", null);
            return InterfaceResultCode.RESOURCE_POOL_ROXY_UPDATE_ERROR;
        }
        logger.info("更新模板[" + templateId + "]状态为:" + stateParse(state));
        return InterfaceResultCode.SUCCESS;
    }

    public void setIbatisDao(IbatisDAO ibatisDao) {
        this.ibatisDao = ibatisDao;
    }

    private String stateParse(String state) {
        String returnState = "0";
        if (state.equals("0")) {
            returnState = "2";
        }
        if (state.equals("1")) {
            returnState = "3";
        }
        if (state.equals("3")) {
            returnState = "4";
        }
        return returnState;
    }

}
