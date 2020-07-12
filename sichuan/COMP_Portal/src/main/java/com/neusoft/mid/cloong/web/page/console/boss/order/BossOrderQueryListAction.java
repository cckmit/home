/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.boss.order;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.boss.order.info.BossOrderInfo;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author li-lei
 *
 */
public class BossOrderQueryListAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = -5471800875775849067L;

	private static LogService logger = LogService.getLogger(BossOrderQueryListAction.class);

	/**
	 * 预定虚拟机实例信息
	 */
	private List<BossOrderInfo> bossOrderResultInfos;
	
	private Map<String, Object> bossOrderResultInfoMap;

	/**
	 * 虚拟机名称
	 */
	private String bossOrderId;

	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		// session中获取用户
		UserBean user = (UserBean) ServletActionContext.getRequest().getSession()
				.getAttribute(SessionKeys.userInfo.toString());
		String userId = user.getUserId();

		BossOrderInfo bossOrderInfo = new BossOrderInfo();
		bossOrderInfo.setUserId(userId);

		if (null != bossOrderId && !bossOrderId.isEmpty()) {
			bossOrderInfo.setBossOrderId(bossOrderId.toLowerCase());
		}

		try {
			bossOrderResultInfos = getPage("countBossOrderListAll", "queryBossOrderListAll", bossOrderInfo);
			fomatResultData(bossOrderResultInfos);
			bossOrderResultInfoMap = new HashMap<String, Object>();
			bossOrderResultInfoMap.put("list", bossOrderResultInfos);
			bossOrderResultInfoMap.put("page", getPageBar());
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					MessageFormat.format(getText("vm.queryList.fail"), userId), e);
			this.addActionError(MessageFormat.format(getText("vm.queryList.fail"), userId));
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * 格式化生效时间、到期时间、状态显示汉字
	 * 
	 * @param ls要格式化的列表
	 */
	private void fomatResultData(List<BossOrderInfo> ls) {
		for (BossOrderInfo bossOrderInfo : ls) {
			String sbt = bossOrderInfo.getAgreementBeginTime() == null ? "" : bossOrderInfo.getAgreementBeginTime().trim();
			if (!"".equals(sbt)) {
				String agreementBeginTime = DateParse.parse1(bossOrderInfo.getAgreementBeginTime());
				bossOrderInfo.setAgreementBeginTime(agreementBeginTime);

			}
			if (!"".equals(bossOrderInfo.getAgreementEndTime() == null ? "" : bossOrderInfo.getAgreementEndTime())) {
				bossOrderInfo.setAgreementEndTime(DateParse.parse1(bossOrderInfo.getAgreementEndTime()));
			}
			if (!"".equals(bossOrderInfo.getTimeStamp() == null ? "" : bossOrderInfo.getTimeStamp())) {
				bossOrderInfo.setTimeStamp(DateParse.parse(bossOrderInfo.getTimeStamp()));
			}
			
		}
	}

	public List<BossOrderInfo> getBossOrderResultInfos() {
		return bossOrderResultInfos;
	}

	public void setBossOrderResultInfos(List<BossOrderInfo> bossOrderResultInfos) {
		this.bossOrderResultInfos = bossOrderResultInfos;
	}

	public Map<String, Object> getBossOrderResultInfoMap() {
		return bossOrderResultInfoMap;
	}

	public void setBossOrderResultInfoMap(Map<String, Object> bossOrderResultInfoMap) {
		this.bossOrderResultInfoMap = bossOrderResultInfoMap;
	}

	public String getBossOrderId() {
		return bossOrderId;
	}

	public void setBossOrderId(String bossOrderId) {
		this.bossOrderId = bossOrderId;
	}

}
