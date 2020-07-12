 /**
  * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
  * @version 1.0.0  2016年10月12日   上午11:18:09
  */
package com.neusoft.mid.cloong.web.page.charges.service;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.identity.bean.ChargesInfo;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

 /**
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0  2016年10月12日   上午11:18:09
 */
public class ChargesService {

	/**
	 * 日志.
	 */
	private final static LogService logger = LogService.getLogger(ChargesService.class);
	
	/**
	 * 0表示虚拟机资费.
	 */
	private final static String CHARGES_TYPE_0 = "0";
	
	/**
	 * 1表示云硬盘资费.
	 */
	private final static String CHARGES_TYPE_1 = "1" ;
	
	/**
	 * 数据库处理.
	 */
	private IbatisDAO ibatisDAO;
	
	/**
	 * 添加资费信息.
	 * @param charges
	 * @return
	 * @throws SQLException 
	 */
	public CreateResult addOrUpateCharges(ChargesInfo charges) {
		CreateResult result =null;
		try {
			
			int count = ibatisDAO.getCount("findChargesCount", charges);
			if(count>0){
				if(CHARGES_TYPE_0.equals(charges.getChargesType())){
					result = new CreateResult("warn", "CPU="+charges.getCpuNumber()+",内存="+charges.getMemorySize()+"已存在请重新输入!");
				}else if(CHARGES_TYPE_1.equals(charges.getChargesType())){
					result = new CreateResult("warn","资费信息已存在请重新输入!");
				}
			}else{
				if(StringUtils.isEmpty(charges.getId())){
					ibatisDAO.insertData("insertCharges", charges);
				}else{
					ibatisDAO.updateData("updateCharges", charges);
				}
				
			}
		} catch (SQLException e) {
			 result = new CreateResult("error","资费添加失败");
			 logger.info("资费添加失败", e);
		}
		return result ;
	}
	/**
	 * @return the ibatisDAO
	 */
	public IbatisDAO getIbatisDAO() {
		return ibatisDAO;
	}

	/**
	 * @param ibatisDAO the ibatisDAO to set
	 */
	public void setIbatisDAO(IbatisDAO ibatisDAO) {
		this.ibatisDAO = ibatisDAO;
	}

	

}
