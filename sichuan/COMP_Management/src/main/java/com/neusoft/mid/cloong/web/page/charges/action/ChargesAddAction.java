/**
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0  2016年10月12日   上午9:38:47
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
 * 添加资费信息.
 * 
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0 2016年10月12日 上午9:38:47
 */
public class ChargesAddAction extends BaseAction {

	/**
	 * 序列.
	 */
	private static final long serialVersionUID = 1L;

	private LogService logger = LogService.getLogger(ChargesAddAction.class);

	/**
	 * 资费实体.
	 */
	private ChargesInfo charges = new ChargesInfo();

	/**
	 * 创建结果
	 */
	private CreateResult result = new CreateResult();

	/**
	 * 资费服务类.
	 */
	private ChargesService chargesService = new ChargesService();

	public String execute() {
			logger.info(getText("charges.add.start"));
			charges.setCreateUser(this.getCurrentUserId());
			result = chargesService.addOrUpateCharges(charges);
			if (result == null) {
				result = new CreateResult("success", getText("charges.add.success"));
			} else {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
						getText("charges.add.error"));
				logger.info(getText("charges.add.end"));
				return ConstantEnum.ERROR.toString();
			}
			logger.info(getText("charges.add.end"));
			return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * @return the charges
	 */
	public ChargesInfo getCharges() {
		return charges;
	}

	/**
	 * @param charges
	 *            the charges to set
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
	 * @param result
	 *            the result to set
	 */
	public void setResult(CreateResult result) {
		this.result = result;
	}

	/**
	 * @return the chargesService
	 */
	public ChargesService getChargesService() {
		return chargesService;
	}

	/**
	 * @param chargesService
	 *            the chargesService to set
	 */
	public void setChargesService(ChargesService chargesService) {
		this.chargesService = chargesService;
	}

}
