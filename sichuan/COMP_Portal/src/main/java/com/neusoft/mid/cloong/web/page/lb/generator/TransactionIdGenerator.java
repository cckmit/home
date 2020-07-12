/*******************************************************************************
 * @(#)TransactionIdGenerator.java 2013-2-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.lb.generator;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 向资源池SOAP请求的序列号生成器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-2-6 下午03:41:14
 */
public class TransactionIdGenerator {

	 private static LogService logger = LogService.getLogger(TransactionIdGenerator.class);
    /**
     * 随机数最大值
     */
    private static final int MAX_VALUE = 999999;

    /**
     * 随机数最大长度
     */
    private static final int MAX_LENGTH = 6;

    /**
     * 序列号八位随机数
     */
    private int sequence = 0;

    private String instanceId;
    

    /**
     * 模板格式为：CIDC-T-[资源类型]-[8位模板编号]
     * 8位模板编号
     */
    private static final int TMPLATENUMBER = 8;
    
    /**
     * 模板生成sql语句
     */
    private String templateGeneratorSql;

    /**
     * 生成序列号
     * @param resourceId 资源池ID
     * @param timeStamp 时间戳，格式为：yyyy-MM-dd HH:mm:ss
     * @return resourceId+timeStamp+八位随机数
     */
    public String generateTransactionId(String resourceId, String timeStamp) {
        StringBuilder sb = new StringBuilder();
        sb.append(resourceId).append(timeStamp).append(instanceId);
        int tempSequence = 0;
        synchronized (this) {
            if (++sequence > MAX_VALUE) {
                sequence = 0;
            }
            tempSequence = sequence;
        }
        for (int i = String.valueOf(tempSequence).length(); i < MAX_LENGTH; i++) {
            sb.append("0");
        }
        sb.append(tempSequence);
        return sb.toString();
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
    
    /**
     * 数据库操作类
     */
    private IbatisDAO ibatisDAO;
    
    public IbatisDAO getIbatisDAO() {
		return ibatisDAO;
	}

	public void setIbatisDAO(IbatisDAO ibatisDAO) {
		this.ibatisDAO = ibatisDAO;
	}

	/**
     * 生成虚拟机模板Id 格式为：CIDC-T-[资源类型]-[8位模板编号]
     * @return
     */
    public String generatorVMItemId() {
        StringBuilder sb = new StringBuilder();
        sb.append("CPC-T-VM-");
        try {
			int templateId = (Integer) ibatisDAO.getSingleRecord(templateGeneratorSql,null);
			sb.append(tmeplateNumberConvert(templateId,TMPLATENUMBER));
			
			/*if("".equals(templateId)||null == templateId){
				sb.append("10000000");
			}else{
				String id = templateId.substring(10);
				int intId = Integer.parseInt(id, 10);
				intId++;
				sb.append(intId);
			}*/
			
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"生成虚拟机模板Id时，数据库操作错误!",e);
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"生成虚拟机模板Id时，数据库操作错误!",e);
		}
        return sb.toString();
    }
    
    /**
     * 生成物理机模板Id 格式为：CIDC-T-[资源类型]-[8位模板编号]
     * @return
     */
    public String generatorPMItemId() {
        StringBuilder sb = new StringBuilder();
        sb.append("CPC-T-SRV-");
        try {
            int templateId = (Integer) ibatisDAO.getSingleRecord(templateGeneratorSql,null);
            sb.append(tmeplateNumberConvert(templateId,TMPLATENUMBER));
            
            /*if("".equals(templateId)||null == templateId){
                sb.append("10000000");
            }else{
                String id = templateId.substring(10);
                int intId = Integer.parseInt(id, 10);
                intId++;
                sb.append(intId);
            }*/
            
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"生成物理机模板Id时，数据库操作错误!",e);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"生成物理机模板Id时，数据库操作错误!",e);
        }
        return sb.toString();
    }
    
    private String tmeplateNumberConvert(int tmeplateNumber,int length){
    	StringBuffer templateId = new StringBuffer();
    	int number = String.valueOf(tmeplateNumber).length();
    	if (length - number >0){
    		for (int i = 0; i < length-number; i++) {
    			templateId.append("0");
			}
    	}
    	templateId.append(tmeplateNumber);
    	return templateId.toString();
    }

    /**
     * 设置templateGeneratorSql字段数据
     * @param templateGeneratorSql The templateGeneratorSql to set.
     */
    public void setTemplateGeneratorSql(String templateGeneratorSql) {
        this.templateGeneratorSql = templateGeneratorSql;
    }

}
