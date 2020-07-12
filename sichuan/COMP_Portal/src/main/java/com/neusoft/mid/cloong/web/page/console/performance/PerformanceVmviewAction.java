/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.performance;

import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;

public class PerformanceVmviewAction  extends ResourceManagementBaseAction {

	/**
     * 序列化版本号
     */
    private static final long serialVersionUID = 2094313569994857016L;
    
	private String vmId;

	public String execute() {
		return ConstantEnum.SUCCESS.toString();
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

}
