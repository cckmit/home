/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.performance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.iamp.logger.LogService;

public class VMResourceGetTabIndexAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService.getLogger(VMResourceSearchAction.class);

	private List<MibinfoDomain> mibdom = new ArrayList<MibinfoDomain>();
	
	@SuppressWarnings("unchecked")
	public String execute() {
		
		try {
			//获取除进程Tab之外的其他Tab所包含的具体指标信息
	        mibdom = (List<MibinfoDomain>)ibatisDAO.getData("getAlreadyConfTabIndex", null);
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"/获取除进程Tab之外的其他Tab所包含的具体指标信息出错，数据库异常。", e);
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"/获取除进程Tab之外的其他Tab所包含的具体指标信息出错，数据库异常。", e);
			return ConstantEnum.ERROR.toString();
		}

		return ConstantEnum.SUCCESS.toString();
		
	}

	public List<MibinfoDomain> getMibdom() {
		return mibdom;
	}

	public void setMibdom(List<MibinfoDomain> mibdom) {
		this.mibdom = mibdom;
	}
	
	
}
