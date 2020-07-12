 /**
  * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
  * @version 1.0.0  2016年10月13日   上午11:11:35
  */
package com.neusoft.mid.cloong.web.page.charges.action;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

 /**
  * 删除资费信息.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0  2016年10月13日   上午11:11:35
 */
public class ChargesDeteleAction extends BaseAction {

	/**
	 * 序列.
	 */
	private static final long serialVersionUID = -1327709255601362155L;
	
	/**
	 * 日志.
	 */
	private static final LogService logger = LogService.getLogger(ChargesDeteleAction.class);
	
	/**
	 * 执行结果.
	 */
	private CreateResult result;
	
	/**
	 * 主键id.
	 */
	private String id;
	
	public String execute(){
		logger.info(getText("charges.del.start"));
		try {
			ibatisDAO.deleteData("delCharges", id);
			result = new CreateResult("success", getText("charges.del.success"));
		} catch (SQLException e) {
			logger.info(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, e);
			result = new CreateResult("error", getText("charges.del.erro"));
			return ConstantEnum.ERROR.toString();
		}
		logger.info(getText("charges.del.end"));
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * @return the result
	 */
	public CreateResult getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(CreateResult result) {
		this.result = result;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the logger
	 */
	public static LogService getLogger() {
		return logger;
	}

}
