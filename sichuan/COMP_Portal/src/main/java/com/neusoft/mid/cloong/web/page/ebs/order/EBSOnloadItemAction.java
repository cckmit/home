package com.neusoft.mid.cloong.web.page.ebs.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.host.vm.order.VMStatusCode;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.ItemInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.RespoolPartInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.StandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请云硬盘页面加载条目和规格页面信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午03:16:37
 */
/**
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-14 上午10:34:15
 */
public class EBSOnloadItemAction extends BaseAction {

	/**
	 * @return the standards
	 */
	public List<StandardInfo> getStandards() {
		return standards;
	}

	/**
	 * @param standards
	 *            the standards to set
	 */
	public void setStandards(List<StandardInfo> standards) {
		this.standards = standards;
	}

	private static final long serialVersionUID = -6356197450412064816L;

	private static LogService logger = LogService
			.getLogger(EBSOnloadItemAction.class);

	private String respoolId;

	private String respoolPartId;

	/**
	 * 条目信息
	 */
	private List<ItemInfo> items;

	/**
	 * 返回结果路径
	 */
	private String resultRoute;

	List<StandardInfo> standards;

	public String execute() {

		if (logger.isDebugEnable()) {
			logger.debug("respoolId = " + respoolId);
			logger.debug("respoolPartId = " + respoolPartId);
		}
		try {
			items = queryItems(respoolId, respoolPartId);
		} catch (SQLException e) {
			logger.error(EBSStatusCode.ONLOADQUERYRITEMS_EXCEPTION_CODE,
					getText("ebsonload.item.fail"), e);
			resultRoute = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e2) {
			logger.error(EBSStatusCode.ONLOADQUERYRITEMS_EXCEPTION_CODE,
					getText("ebsonload.item.fail"), e2);
			resultRoute = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}

		return ConstantEnum.SUCCESS.toString();

	}

	private List<ItemInfo> queryItems(String respoolId, String respoolPartId)
			throws SQLException {

		List<ItemInfo> tempItems = new ArrayList<ItemInfo>();
		RespoolPartInfo rp = new RespoolPartInfo();
		rp.setRespoolId(respoolId);
		rp.setRespoolPartId(respoolPartId);

		List<StandardInfo> standards = (List<StandardInfo>) ibatisDAO.getData(
				"queryStandardsByEbs", rp);

		for (int i = 0; i < standards.size(); i++) {
			StandardInfo standardInfo = standards.get(i);
			// 设置查询时,使用的条件.销售起止时间判断
			standardInfo.setCreateTime(DateParse
					.generateDateFormatyyyyMMddHHmmss());
			List<ItemInfo> items = (List<ItemInfo>) ibatisDAO.getData(
					"queryItemsByEbs", standardInfo);
			if (!items.isEmpty()) {
				for (ItemInfo item : items) {
					item.setStandardInfo(standards.get(i));
					tempItems.add(item);
				}
			}
		}

		return tempItems;
	}

	/**
	 * 查推荐规格
	 * @return
	 */
	public String queryRecommendStandards() {

		if (logger.isDebugEnable()) {
			logger.debug("respoolId = " + respoolId);
			logger.debug("respoolPartId = " + respoolPartId);
		}
		try {
			queryRecommend(respoolId, respoolPartId);
		} catch (SQLException e) {
			resultRoute = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		} catch (Exception e2) {
			logger.error(EBSStatusCode.ONLOADQUERYRITEMS_EXCEPTION_CODE,
					getText("ebsonload.item.fail"), e2);
			resultRoute = ConstantEnum.ERROR.toString();
			return ConstantEnum.ERROR.toString();
		}

		return ConstantEnum.SUCCESS.toString();

	}

	private List<StandardInfo> queryRecommend(String respoolId,
			String respoolPartId) throws SQLException {

		List<ItemInfo> tempItems = new ArrayList<ItemInfo>();
		RespoolPartInfo rp = new RespoolPartInfo();
		rp.setRespoolId(respoolId);
		rp.setRespoolPartId(respoolPartId);

		standards = (List<StandardInfo>) ibatisDAO.getData(
				"queryEbsRecommendStandards", rp);
		return standards;
	}

	public String getRespoolId() {
		return respoolId;
	}

	public void setRespoolId(String respoolId) {
		this.respoolId = respoolId;
	}

	public String getRespoolPartId() {
		return respoolPartId;
	}

	public void setRespoolPartId(String respoolPartId) {
		this.respoolPartId = respoolPartId;
	}

	public List<ItemInfo> getItems() {
		return items;
	}

	public void setItems(List<ItemInfo> items) {
		this.items = items;
	}

	public String getResultRoute() {
		return resultRoute;
	}

	public void setResultRoute(String resultRoute) {
		this.resultRoute = resultRoute;
	}
}
