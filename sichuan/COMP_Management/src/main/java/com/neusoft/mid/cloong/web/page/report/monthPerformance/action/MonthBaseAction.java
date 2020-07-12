/**
 * 
 */
package com.neusoft.mid.cloong.web.page.report.monthPerformance.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.report.monthPerformance.info.MonthPerformanceInfo;

/**
 * 月度性能统计
 * 
 * @author <a href="mailto:zhang.ge@neusoft.com">zhang.ge</a>
 * @version Revision 1.0 2016年4月7日 下午6:55:29
 */
public class MonthBaseAction extends PageAction {
	private static final long serialVersionUID = 1L;

	/** 月度性能统计信息 */
	protected MonthPerformanceInfo monthPerformanceInfo = new MonthPerformanceInfo();
	
	/** 资源池名称list */
	private List<MonthPerformanceInfo> resPoolList = new ArrayList<MonthPerformanceInfo>();

	/** 分区名称list */
	private List<MonthPerformanceInfo> poolPartList = new ArrayList<MonthPerformanceInfo>();
    
    /** 业务下拉列表  - 没用*/
	private List<MonthPerformanceInfo> appList = new ArrayList<MonthPerformanceInfo>();

	/**
	 * 设置页面标题
	 */
	protected void setPageTitle () {
		if ("0".equals(monthPerformanceInfo.getDeviceType())) {
			monthPerformanceInfo.setDeviceTypeName("物理机");
		} else if ("1".equals(monthPerformanceInfo.getDeviceType())) {
			monthPerformanceInfo.setDeviceTypeName("虚拟机");
		}
	}
	
	/**
	 * 设置查询日期（格式：yyyyMMdd）
	 */
	protected void formatDateForSql () {
		String startDate = monthPerformanceInfo.getStartDate();
		String endDate = monthPerformanceInfo.getEndDate();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if ((null == startDate || "".equals(startDate))
				&& (null == endDate || "".equals(endDate))) { // 页面初始化默认展示本月的数据
			Calendar cal = Calendar.getInstance();
		      cal.set(Calendar.DAY_OF_MONTH,1);
		      cal.add(Calendar.MONTH,-1);
		      monthPerformanceInfo.setStartDate(sdf.format(cal.getTime()));
	        /* 取得前1天 */
	        cal = Calendar.getInstance();
	        cal.set(Calendar.DAY_OF_MONTH,1);
	        cal.add(Calendar.DAY_OF_MONTH,-1);//日期倒数一日,既得到本月最后一天 
	        monthPerformanceInfo.setEndDate(sdf.format(cal.getTime()));
		} else {
			if (null != startDate && !"".equals(startDate)) {
				monthPerformanceInfo.setStartDate(startDate.replaceAll("-", ""));
			}
			if (null != endDate && !"".equals(endDate)) {
				monthPerformanceInfo.setEndDate(endDate.replace("-", ""));
			}
		}
	}
	
	/**
	 * 设置查询日期（格式：yyyy-MM-dd）
	 */
	protected void formatDateForDisplay () {
		String startDate = monthPerformanceInfo.getStartDate();
		String endDate = monthPerformanceInfo.getEndDate();

		if (null != startDate && !"".equals(startDate)) {
			monthPerformanceInfo.setStartDate(this.formatDate(startDate));
		}
		if (null != endDate && !"".equals(endDate)) {
			monthPerformanceInfo.setEndDate(this.formatDate(endDate));
		}
	}
	
	/**
	 * 日期格式化（格式：yyyyMMdd）
	 */
	private String formatDate (String date) {
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6);
		date = year + "-" + month + "-" + day;
		return date;
	}

	public MonthPerformanceInfo getMonthPerformanceInfo() {
		return monthPerformanceInfo;
	}

	public void setMonthPerformanceInfo(MonthPerformanceInfo monthPerformanceInfo) {
		this.monthPerformanceInfo = monthPerformanceInfo;
	}

	/**
	 * @return the resPoolList
	 */
	public List<MonthPerformanceInfo> getResPoolList() {
		return resPoolList;
	}

	/**
	 * @param resPoolList the resPoolList to set
	 */
	public void setResPoolList(List<MonthPerformanceInfo> resPoolList) {
		this.resPoolList = resPoolList;
	}

	/**
	 * @return the poolPartList
	 */
	public List<MonthPerformanceInfo> getPoolPartList() {
		return poolPartList;
	}

	/**
	 * @param poolPartList the poolPartList to set
	 */
	public void setPoolPartList(List<MonthPerformanceInfo> poolPartList) {
		this.poolPartList = poolPartList;
	}

	/**
	 * @return the appList
	 */
	public List<MonthPerformanceInfo> getAppList() {
		return appList;
	}

	/**
	 * @param appList the appList to set
	 */
	public void setAppList(List<MonthPerformanceInfo> appList) {
		this.appList = appList;
	}


}
