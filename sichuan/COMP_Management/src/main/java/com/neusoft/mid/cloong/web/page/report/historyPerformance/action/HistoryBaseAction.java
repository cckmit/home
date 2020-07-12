package com.neusoft.mid.cloong.web.page.report.historyPerformance.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.report.historyPerformance.info.HistoryPerformanceInfo;
import com.neusoft.mid.cloong.web.page.report.monthPerformance.info.MonthPerformanceInfo;

/**
 * 业务性能统计-CPU使用率统计
 * 
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version Revision 1.0 2015年2月26日 下午6:55:29
 */
public class HistoryBaseAction extends PageAction {

	private static final long serialVersionUID = 1L;

	/** 业务性能统计信息 */
	protected HistoryPerformanceInfo historyPerformanceInfo = new HistoryPerformanceInfo();
	
	/** 资源池名称list - 没用*/
	private List<MonthPerformanceInfo> resPoolList = new ArrayList<MonthPerformanceInfo>();

	/** 分区名称list - 没用*/
	private List<MonthPerformanceInfo> poolPartList = new ArrayList<MonthPerformanceInfo>();

	/**
	 * 设置页面标题
	 */
	protected void setPageTitle () {
		if ("0".equals(historyPerformanceInfo.getDeviceType())) {
			historyPerformanceInfo.setDeviceTypeName("小型机");
		} else if ("1".equals(historyPerformanceInfo.getDeviceType())) {
			historyPerformanceInfo.setDeviceTypeName("小型机分区");
		} else if ("2".equals(historyPerformanceInfo.getDeviceType())) {
			historyPerformanceInfo.setDeviceTypeName("物理机");
		} else if ("3".equals(historyPerformanceInfo.getDeviceType())) {
			historyPerformanceInfo.setDeviceTypeName("虚拟机");
		}
	}
	
	/**
	 * 设置查询日期（格式：yyyyMMdd）
	 */
	protected void formatDateForSql () {
		String startDate = historyPerformanceInfo.getStartDate();
		String endDate = historyPerformanceInfo.getEndDate();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if ((null == startDate || "".equals(startDate))
				&& (null == endDate || "".equals(endDate))) { // 页面初始化默认展示最近30天的数据
			/* 取得前31天 */
			Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -31);
	        historyPerformanceInfo.setStartDate(sdf.format(cal.getTime()));
	        /* 取得前1天 */
	        cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -1);
	        historyPerformanceInfo.setEndDate(sdf.format(cal.getTime()));
		} else {
			if (null != startDate && !"".equals(startDate)) {
				historyPerformanceInfo.setStartDate(startDate.replaceAll("-", ""));
			}
			if (null != endDate && !"".equals(endDate)) {
				historyPerformanceInfo.setEndDate(endDate.replace("-", ""));
			}
		}
	}
	
	/**
	 * 设置查询日期（格式：yyyy-MM-dd）
	 */
	protected void formatDateForDisplay () {
		String startDate = historyPerformanceInfo.getStartDate();
		String endDate = historyPerformanceInfo.getEndDate();

		if (null != startDate && !"".equals(startDate)) {
			historyPerformanceInfo.setStartDate(this.formatDate(startDate));
		}
		if (null != endDate && !"".equals(endDate)) {
			historyPerformanceInfo.setEndDate(this.formatDate(endDate));
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

	public HistoryPerformanceInfo getHistoryPerformanceInfo() {
		return historyPerformanceInfo;
	}

	public void setHistoryPerformanceInfo(
			HistoryPerformanceInfo historyPerformanceInfo) {
		this.historyPerformanceInfo = historyPerformanceInfo;
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

}
