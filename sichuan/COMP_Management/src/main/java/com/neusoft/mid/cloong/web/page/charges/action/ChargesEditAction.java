 /**
  * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
  * @version 1.0.0  2016年10月12日   下午4:20:52
  */
package com.neusoft.mid.cloong.web.page.charges.action;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.ChargesInfo;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.charges.service.ChargesService;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

 /**
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0  2016年10月12日   下午4:20:52
 */
public class ChargesEditAction extends BaseAction {

	/**
	 * 序列.
	 */
	private static final long serialVersionUID = -6898639381462483776L;
	
	/**
	 * 日志.
	 */
	private LogService logger = LogService.getLogger(ChargesEditAction.class);

	/**
	 * 服务处理
	 */
	private ChargesService chargesService;
	
	/**
	 * 实体.
	 */
	private ChargesInfo charges = new ChargesInfo();
	
	/**
	 * 执行结果.
	 */
	private  CreateResult result ;
	
	public String execute(){
		logger.info(getText("charges.edit.start"));
		charges.setCreateUser(this.getCurrentUserId());
	    result = chargesService.addOrUpateCharges(charges);
	    if (result == null) {
			result = new CreateResult("success", getText("charges.edit.success"));
		} else {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					getText("charges.edit.error"));
			logger.info(getText("charges.edit.end"));
			return ConstantEnum.ERROR.toString();
		}
		logger.info(getText("charges.edit.end"));
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * @return the logger
	 */
	public LogService getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(LogService logger) {
		this.logger = logger;
	}

	/**
	 * @return the chargesService
	 */
	public ChargesService getChargesService() {
		return chargesService;
	}

	/**
	 * @param chargesService the chargesService to set
	 */
	public void setChargesService(ChargesService chargesService) {
		this.chargesService = chargesService;
	}

	/**
	 * @return the charges
	 */
	public ChargesInfo getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(ChargesInfo charges) {
		this.charges = charges;
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
	
}
