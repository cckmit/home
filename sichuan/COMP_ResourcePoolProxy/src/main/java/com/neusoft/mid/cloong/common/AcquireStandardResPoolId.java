/*******************************************************************************
 * @(#)AcquireStandardResPoolId.java 2014-1-14
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common;

import java.sql.SQLException;

import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 获取规格与资源池对应ID
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-14 上午10:46:35
 */
public class AcquireStandardResPoolId {

    private IbatisDAO ibatisDAO;

    private static LogService logger = LogService.getLogger(AcquireStandardResPoolId.class);
    
    
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }
    
    /***
     * 虚拟机模板查询
     * @param standardResPoolIdInfo
     * @return 虚拟机模板ID
     */
    public String getVMStandardResPoolId(StandardResPoolIdInfo standardResPoolIdInfo){
        String templateId = null;
        try {
            templateId = (String)ibatisDAO.getSingleRecord("queryVMStandardResPoolId", standardResPoolIdInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "资源池代理查询虚拟机规格对应资源池ID，数据库异常", e);
        }catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "资源池代理查询虚拟机规格对应资源池ID，数据库异常", e);
        }
        return templateId;
    }
    /***
     * 物理机模板查询
     * @param standardResPoolIdInfo
     * @return 物理机模板ID
     */
    public String getPMStandardResPoolId(StandardResPoolIdInfo standardResPoolIdInfo){
        String templateId = null;
        try {
            templateId = (String)ibatisDAO.getSingleRecord("queryPMStandardResPoolId", standardResPoolIdInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "资源池代理查询物理机规格对应资源池ID，数据库异常", e);
        }catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "资源池代理查询物理机规格对应资源池ID，数据库异常", e);
        }
        return templateId;
    }
    
    /***
     * 查询规格对应资源池ID
     * @param standardResPoolIdInfo
     * @return 规格对应资源池的ID
     */
    public String getStandardResPoolId(StandardResPoolIdInfo standardResPoolIdInfo){
        String templateId = null;
        try {
            templateId = (String)ibatisDAO.getSingleRecord("queryStandardResPoolId", standardResPoolIdInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "资源池代理查询规格对应资源池ID，数据库异常", e);
        }catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "资源池代理查询规格对应资源池ID，数据库异常", e);
        }
        return templateId;
    }

}
