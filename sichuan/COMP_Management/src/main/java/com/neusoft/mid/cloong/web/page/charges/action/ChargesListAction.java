 /**
  * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
  * @version 1.0.0  2016年10月10日   下午2:03:51
  */
package com.neusoft.mid.cloong.web.page.charges.action;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.identity.bean.ChargesInfo;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.iamp.logger.LogService;

 /**
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0  2016年10月10日   下午2:03:51
 */
public class ChargesListAction extends PageAction {

	/**
	 * 序列.
	 */
	private static final long serialVersionUID = 8337477527336577124L;
	
	/**
	 * 日志.
	 */
	private static final LogService logger = LogService.getLogger(ChargesListAction.class);
	
	/**
	 * 计费类型.0-虚拟机(云主机),1-云硬盘
	 */
	private String chargesType;
	
	/**
	 * 资费.
	 */
	private ChargesInfo charges = new ChargesInfo();
	
	private List<ChargesInfo> chargesList = new ArrayList<ChargesInfo>();
	
	@SuppressWarnings("unchecked")
	public String execute(){
		logger.info(getText("charges.list.start"));
		charges.setChargesType(chargesType);
		try {
			chargesList = getPage("queryCountCharges", "queryAllCharges",charges);
			
		} catch (Exception e) {
			logger.error(getText("charges.list.error")+e.getMessage());
			return ConstantEnum.ERROR.toString();
		}
		logger.info("*****chargesType="+chargesType+"**********");
		logger.info(getText("charges.list.end"));
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * @return the chargesType
	 */
	public String getChargesType() {
		return chargesType;
	}

	/**
	 * @param chargesType the chargesType to set
	 */
	public void setChargesType(String chargesType) {
		this.chargesType = chargesType;
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
	 * @return the chargesList
	 */
	public List<ChargesInfo> getChargesList() {
		return chargesList;
	}

	/**
	 * @param chargesList the chargesList to set
	 */
	public void setChargesList(List<ChargesInfo> chargesList) {
		this.chargesList = chargesList;
	}
	
}
