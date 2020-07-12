/*******************************************************************************
 * @(#)SequenceGenerator.java 2013-1-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.generator;
import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 序列号生成器
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-24 下午04:27:11
 */
public class SequenceGenerator {
   
	private static LogService logger = LogService.getLogger(SequenceGenerator.class);
    
    /**
     * 模板格式为：CPC-T-[资源类型]-[8位模板编号]
     * 8位模板编号
     */
    private static final int TMPLATENUMBER = 8;
    
    /**
     * 数据库操作类
     */
    private IbatisDAO ibatisDAO;
    
    /**
     * 模板生成sql语句
     */
    private String templateGeneratorSql;
    
    public IbatisDAO getIbatisDAO() {
		return ibatisDAO;
	}

	public void setIbatisDAO(IbatisDAO ibatisDAO) {
		this.ibatisDAO = ibatisDAO;
	}
	
    /**
     * 生成虚拟机模板Id 格式为：CPC-T-[资源类型]-[8位模板编号]
     * @return
     */
    public String generatorVMItemId(String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("CPC-T-"+type+"-");
        try {
			int templateId = (Integer) ibatisDAO.getSingleRecord(templateGeneratorSql,null);
			sb.append(tmeplateNumberConvert(templateId,TMPLATENUMBER));
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"生成虚拟机模板Id时，数据库操作错误!",e);
		} catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,"生成虚拟机模板Id时，数据库操作错误!",e);
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
