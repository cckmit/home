/*******************************************************************************
 * @(#)BaseStandardModifyAction.java 2014-2-19
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.base;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 
 * 修改基础类
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-2-19 下午05:12:13
 */
public class BaseStandardModifyAction extends BaseAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5410407376952894349L;


    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(BaseStandardModifyAction.class);

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
     * getCountSyning 查询规格同步发送成功数量
     * @param standardId 规格ID
     * @return 规格同步发送成功数量.如果数据库操作失败返回-1，如果不存在返回0，存在返回存在数量.
     */
    public int getCountSyning(String standardId){
        int count = 0;
        try {
            count = ibatisDAO.getCount("getCountSyning", standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "修改规格时查询错误！原因：查询规格是否同步发送成功时，数据库异常。", e);
            return -1;
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "修改规格时查询错误！原因：查询规格是否同步发送成功时，数据库异常。", e);
            return -1;
        }
        return count;
    }
}
