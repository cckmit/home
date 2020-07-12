package com.neusoft.mid.cloong.web.alarm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.lb.generator.CommonStatusCode;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

public class AlarmListAction extends PageAction {
	
	private static final long serialVersionUID = 1L;
	
	private static LogService logger = LogService.getLogger(AlarmListAction.class);
	
	List<String> appList;
	
	List<AlarmInfoBean> alarms;
	
	private Map<String, Object> alarmsInfos;
	
	private String vmIp;
	
	private String time;
	
	/**
	 * SYNCCODE 调用同步翻页状态码 0为异步 非0为同步(1)
	 */
    private final String SYNCCODE = "0";

    /**
     * syncFlage 同步标志位 0为异步 非0为同步(1) spring配置 默认异步可以不配置
     */
    private String syncFlage = "0";

	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			String key = SessionKeys.userInfo.toString();
			UserBean user = (UserBean) session.get(key);
			String appids = user.getAppIdStr();
			logger.info("当前登录的用户名为userId:" + user.getUserId() + " ，业务为:" + appids + " , time: " + time);
			String[] apps = appids.split(",");
			appList = Arrays.asList(apps);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("appList", appList);
			map.put("vmIp", vmIp);
			map.put("time", time);
			//int i = ibatisDAO.getCount("getAlarm3CountByAppIds",map);
			//List<AlarmInfoBean> alarms3 = ibatisDAO.getData("getAlarm3ListByAppIds",map);
			if (SYNCCODE.equals(syncFlage)) {
			     alarms = getPage("getAlarmCountByAppIds", "getAlarmListByAppIds", map,PageTransModel.ASYNC);
			 } else {
				 alarms = getPage("getAlarmCountByAppIds", "getAlarmListByAppIds", map);
			 }
			logger.info("ID为[" + user.getUserId() + "]的用户告警成功，共有[" + alarms.size() + "]条告警");
			if (alarms.size() == 0 ) {
				logger.info("ID为[" + user.getUserId() + "]用户没有查询到符合条件的告警");
			    this.addActionMessage("没有符合条件的告警");
			    alarmsInfos = new HashMap<String, Object>();
			    alarmsInfos.put("list", alarms);
			    alarmsInfos.put("page", getPageBar());
			    return ConstantEnum.SUCCESS.toString();
			}
			alarmsInfos = new HashMap<String, Object>();
			alarmsInfos.put("list", alarms);
			alarmsInfos.put("page", getPageBar());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(CommonStatusCode.OTHER_EXCEPTION , "fetch alarm list error!");
			return ConstantEnum.ERROR.toString();
		}
		return ConstantEnum.SUCCESS.toString();
	}

	public List<String> getAppList() {
		return appList;
	}

	public void setAppList(List<String> appList) {
		this.appList = appList;
	}

	public List<AlarmInfoBean> getAlarms() {
		return alarms;
	}

	public void setAlarms(List<AlarmInfoBean> alarms) {
		this.alarms = alarms;
	}

	public String getVmIp() {
		return vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

	public Map<String, Object> getAlarmsInfos() {
		return alarmsInfos;
	}

	public void setAlarmsInfos(Map<String, Object> alarmsInfos) {
		this.alarmsInfos = alarmsInfos;
	}

	public String getSyncFlage() {
		return syncFlage;
	}

	public void setSyncFlage(String syncFlage) {
		this.syncFlage = syncFlage;
	}

	public String getSYNCCODE() {
		return SYNCCODE;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
