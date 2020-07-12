/**
 * 
 */
package com.neusoft.mid.cloong.web.page.host.vm.order;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;

/**与订购云主机
 * @author li-lei
 * @version $Revision 1.1 $ 2018-04-19 上午09:08:14
 */
public class VMPreApplyAction {

	private String itemId;
    private String osId;
    private String cpuNum;
    private String ramSize;
    private String num;
    private String chargeType;
    private String lengthTime;
    private String userId;
    

    public String execute() {
    	UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
    	userId = user.getUserId();
        return ConstantEnum.SUCCESS.toString();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }

	/**
	 * @return the cpuNum
	 */
	public String getCpuNum() {
		return cpuNum;
	}

	/**
	 * @param cpuNum the cpuNum to set
	 */
	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}

	/**
	 * @return the ramSize
	 */
	public String getRamSize() {
		return ramSize;
	}

	/**
	 * @param ramSize the ramSize to set
	 */
	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
	}

	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @return the lengthTime
	 */
	public String getLengthTime() {
		return lengthTime;
	}

	/**
	 * @param lengthTime the lengthTime to set
	 */
	public void setLengthTime(String lengthTime) {
		this.lengthTime = lengthTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
