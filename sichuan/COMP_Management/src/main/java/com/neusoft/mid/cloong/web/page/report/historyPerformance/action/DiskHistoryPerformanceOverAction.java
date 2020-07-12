package com.neusoft.mid.cloong.web.page.report.historyPerformance.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.JsonUtil;
import com.neusoft.mid.cloong.web.page.common.PageToDisplayPerModel;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.report.historyPerformance.info.HistoryPerformanceInfo;

/**
 * 业务性能统计-磁盘使用率分布设备统计
 * 
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version Revision 1.0 2015年2月26日 下午6:55:29
 */
public class DiskHistoryPerformanceOverAction extends HistoryBaseAction {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(CpuHistoryPerformanceAction.class);

	/** 磁盘使用率分布设备统计图表的横纵坐标数值 */
	private List<String> chartData = new ArrayList<String>();

	/** ajax传递的用来异步分页的数据 */
	private List<HistoryPerformanceInfo> historyPerformanceInfolist = new ArrayList<HistoryPerformanceInfo>();

	/** 业务下拉列表 */
	private List<HistoryPerformanceInfo> appList = null;

	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("业务性能统计-磁盘使用率分布设备统计，开始");

		/* 设置页面标题 */
		setPageTitle();

		/* 设置查询日期，用于SQL查询（格式：yyyyMMdd） */
		formatDateForSql();

		List<HistoryPerformanceInfo> chartDatalist = null; // 图表的横纵坐标数值List

		try {
			if (null != historyPerformanceInfo.getStaFlag() && !"".equals(historyPerformanceInfo.getStaFlag())) {
				chartDatalist = ibatisDAO.getData("getDiskHistoryPerformanceOver", historyPerformanceInfo);
			}
			
			appList = ibatisDAO.getData("getAppList", null);
		} catch (SQLException e) {
			logger.error("业务性能统计-磁盘使用率分布设备统计，操作数据库异常", e);
			e.printStackTrace();
		}

		/* 设置磁盘使用率分布统计json */
		if (null != chartDatalist) {
			chartData = this.JsonPackaging(chartDatalist);
		}

		/* 设置查询日期，用于页面展示（格式：yyyy-MM-dd） */
		formatDateForDisplay();

		logger.info("业务性能统计-磁盘使用率分布设备统计，结束");

		return "success";
	}

	/**
	 * 将查出的数据封装为json格式
	 * 
	 * @param 查出的数据list
	 * @return list，第一个是x坐标的json，第二个是数值的json
	 */
	private List<String> JsonPackaging(List<HistoryPerformanceInfo> list) {
		JSONArray xAxisArr = new JSONArray(); // x轴坐标
		JSONArray range3Data = new JSONArray(); // 30%>=使用率
		JSONArray range2Data = new JSONArray(); // 70%>=使用率>30%
		JSONArray range1Data = new JSONArray(); // 使用率>70%

		JSONObject obj = null;
		for (HistoryPerformanceInfo i : list) {
			/* 横坐标 */
			xAxisArr.add(i.getReportDate());

			/* 30%>=使用率 */
			obj = new JSONObject();
			obj.put("y", i.getDiskRange3Num());
			range3Data.add(obj);
			
			/* 70%>=使用率>30% */
			obj = new JSONObject();
			obj.put("y", i.getDiskRange2Num());
			range2Data.add(obj);
			
			/* 使用率>70% */
			obj = new JSONObject();
			obj.put("y", i.getDiskRange1Num());
			range1Data.add(obj);
		}

		JSONObject range3Obj = new JSONObject();
		range3Obj.put("name", "30%>=使用率");
		range3Obj.put("data", range3Data);
		range3Obj.put("color", "#6CD7D9");
		
		JSONObject range2Obj = new JSONObject();
		range2Obj.put("name", "70%>=使用率>30%");
		range2Obj.put("data", range2Data);
		range2Obj.put("color", "#FFA500");
		
		JSONObject range1Obj = new JSONObject();
		range1Obj.put("name", "使用率>70%");
		range1Obj.put("data", range1Data);
		range1Obj.put("color", "#D87A80");

		JSONArray seriesArr = new JSONArray();
		seriesArr.add(range3Obj);
		seriesArr.add(range2Obj);
		seriesArr.add(range1Obj);

		List<String> result = new ArrayList<String>();
		result.add(JsonUtil.jsonTrans(xAxisArr.toString()));
		result.add(JsonUtil.jsonTrans(seriesArr.toString()));

		return result;
	}

	/**
	 * 通过Ajax获取个数表格
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDiskHistoryPerformanceOverByAjax() {
		/* 查询日期处理 */
		this.formatDateForSql();
		
		if (null != historyPerformanceInfo.getStaFlag() && !"".equals(historyPerformanceInfo.getStaFlag())) {
			historyPerformanceInfolist = getPage("getHistoryPerformanceCount",
					"getDiskHistoryPerformanceOver", historyPerformanceInfo,
					PageTransModel.ASYNC, PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		}

		return "success";
	}

	public List<String> getChartData() {
		return chartData;
	}

	public void setChartData(List<String> chartData) {
		this.chartData = chartData;
	}

	public List<HistoryPerformanceInfo> getHistoryPerformanceInfolist() {
		return historyPerformanceInfolist;
	}

	public void setHistoryPerformanceInfolist(
			List<HistoryPerformanceInfo> historyPerformanceInfolist) {
		this.historyPerformanceInfolist = historyPerformanceInfolist;
	}

	public List<HistoryPerformanceInfo> getAppList() {
		return appList;
	}

	public void setAppList(List<HistoryPerformanceInfo> appList) {
		this.appList = appList;
	}
}
