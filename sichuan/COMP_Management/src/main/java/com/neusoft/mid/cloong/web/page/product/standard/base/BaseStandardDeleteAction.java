/*******************************************************************************
 * @(#)BaseStandardDeleteAction.java 2014-1-13
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.base;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.resourceProxy.standard.common.StandardSynchronizeDelete;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 规格删除基础类
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-13 上午09:35:25
 */
public class BaseStandardDeleteAction extends BaseAction {

    private static final long serialVersionUID = 3630945526948305980L;


    private static LogService logger = LogService.getLogger(BaseStandardDeleteAction.class);


    /** 删除接口 ***/
    protected StandardSynchronizeDelete standardSynchronizeDel;

    protected String resultPath = ConstantEnum.SUCCESS.toString();

    /**
     * getCountSynUsable 查询规格可用数量
     * @param standardId 规格ID
     * @return 规格可用数量.如果数据库操作失败返回-1，如果不存在返回0，存在返回存在数量.
     */
    public int getCountSynUsable(String standardId){
        int count = 0;
        try {
            count = ibatisDAO.getCount("getCountSyn", standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "修改规格时查询错误！原因：查询规格是否可用时，数据库异常。", e);
            return -1;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "修改规格时查询错误！原因：查询规格是否可用时，数据库异常。", e);
            return -1;
        }
        return count;
    }
    
    /**
     * isIntermediateState 是否存在:同步发送成功
     * @param standardId 规格ID
     * @return 如果存在中间态、数据库查询异常true，否则返回false
     */
    protected boolean isIntermediateState(String standardId) {
        int synNumber = 0;
        try {
            synNumber = (Integer) ibatisDAO.getSingleRecord("queryIntermediateStateByStandardId",
                    standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "删除虚拟机规格失败！原因：查询规格是否被同步过时，数据库异常。", e);
            resultPath = ConstantEnum.ERROR.toString();
            return true;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "删除虚拟机规格失败！原因：查询规格是否被同步过时，数据库异常。", e);
            resultPath = ConstantEnum.ERROR.toString();
            return true;
        }
        if (synNumber > 0) {
            resultPath = ConstantEnum.INTERMEDIATE.toString();
            logger.info("ID为" + standardId + "的规格存在中间状态，不允许删除");
            return true;
        } else {
            return false;
        }
    }

    /**
     * isCallSynchro 查询同步表，判断状态是否存在：可用、不可用
     * @param standardId 规格ID
     * @return 如果存在返回true，否则返回false
     * @throws SQLException 查询数据库异常
     */
    protected boolean isCallSynchro(String standardId) throws SQLException {
        int synNumber = (Integer) ibatisDAO.getSingleRecord("querySynByStandardId", standardId);
        if (synNumber > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * isCallSynchroUnSynchroData 查询同步表，判断状态是否存在：未同步过
     * @param standardId 规格ID
     * @return 如果存在返回true，否则返回false
     * @throws SQLException 查询数据库异常
     */
    protected boolean isCallSynchroUnSynchroData(String standardId) throws SQLException {
        int synNumber = (Integer) ibatisDAO.getSingleRecord("queryUnSynByStandardId", standardId);
        if (synNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public void setStandardSynchronizeDel(StandardSynchronizeDelete standardSynchronizeDel) {
        this.standardSynchronizeDel = standardSynchronizeDel;
    }
    
}
