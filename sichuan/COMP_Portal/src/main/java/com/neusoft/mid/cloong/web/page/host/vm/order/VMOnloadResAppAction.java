package com.neusoft.mid.cloong.web.page.host.vm.order;

import java.sql.SQLException;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.business.bean.QueryBusiness;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请虚拟机页面加载资源池和资源池分区页面信息
 * 
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午02:30:35
 */
public class VMOnloadResAppAction extends BaseAction {

	private static final long serialVersionUID = -6356197450412064816L;

	private static LogService logger = LogService
			.getLogger(VMOnloadResAppAction.class);

	/**
	 * 返回结果路径,error,failure
	 */
	private String resultRoute;

	/**
	 * 业务ID，从左侧菜单中传递过来
	 */
	private String queryBusinessId;

	/**
	 * 业务名称，传递给申请虚拟机页面，只供显示
	 */
	private String queryBusinessName;

	public String execute() {
		try {
			QueryBusiness queryBusiness = new QueryBusiness();
			queryBusiness.setBusinessId(queryBusinessId);

			queryBusinessName = ((BusinessInfo) ibatisDAO.getSingleRecord(
					"queryBusinessByQueryObj", queryBusiness)).getName();
		} catch (SQLException e) {
			logger.error(VMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
					getText("vmonload.respool.fail"), e);
			resultRoute = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e2) {
			logger.error(VMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
					getText("vmonload.respool.fail"), e2);
			resultRoute = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}
		resultRoute = ConstantEnum.SUCCESS.toString();
		return ConstantEnum.SUCCESS.toString();

	}

	public String getResultRoute() {
		return resultRoute;
	}

	public void setResultRoute(String resultRoute) {
		this.resultRoute = resultRoute;
	}

	public String getQueryBusinessId() {
		return queryBusinessId;
	}

	public void setQueryBusinessId(String queryBusinessId) {
		this.queryBusinessId = queryBusinessId;
	}

	public String getQueryBusinessName() {
		return queryBusinessName;
	}

	public void setQueryBusinessName(String queryBusinessName) {
		this.queryBusinessName = queryBusinessName;
	}

}
