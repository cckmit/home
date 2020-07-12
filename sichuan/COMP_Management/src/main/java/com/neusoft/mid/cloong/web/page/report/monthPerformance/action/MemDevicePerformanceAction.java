/**
 * 
 */
package com.neusoft.mid.cloong.web.page.report.monthPerformance.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.JsonUtil;
import com.neusoft.mid.cloong.web.page.report.monthPerformance.info.MonthPerformanceInfo;

/**
 * 月度性能统计-内存统计
 * 
 * @author <a href="mailto:zhang.ge@neusoft.com">zhang.ge</a>
 * @version Revision 1.0 2016年4月7日 下午6:55:29
 */
public class MemDevicePerformanceAction extends MonthBaseAction {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger
			.getLogger(CpuDevicePerformanceAction.class);

	/** 内存月度统计图表的横纵坐标数值 */
	private List<String> chartData = new ArrayList<String>();

	/** ajax传递的用来异步分页的数据 */
	private List<MonthPerformanceInfo> monthPerformanceInfolist = new ArrayList<MonthPerformanceInfo>();

	/** 资源池id */
	private String resPoolId;
	
	/** 资源池名称list */
	private List<MonthPerformanceInfo> resPoolList = new ArrayList<MonthPerformanceInfo>();

	/** 分区名称list */
	private List<MonthPerformanceInfo> poolPartList = new ArrayList<MonthPerformanceInfo>();
	
	/** 业务下拉列表 */
	private List<MonthPerformanceInfo> appList = new ArrayList<MonthPerformanceInfo>();
	
	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("月度性能统计-内存使用率统计，开始");

		/* 设置页面标题 */
		setPageTitle();

		/* 设置查询日期，用于SQL查询（格式：yyyyMMdd） */
		formatDateForSql();

		/* 取得图表数据 */
		List<MonthPerformanceInfo> chartDatalist = null; // 图表的横纵坐标数值List
		try {
			/* 初始化加载资源池名称list */
			resPoolList = ibatisDAO.getData("queryRespools", null);
			if ("0".equals(monthPerformanceInfo.getDeviceType())) { // 统计物理机内存
				chartDatalist = ibatisDAO.getData("getPmMemPerformance",
						monthPerformanceInfo);
			} else if ("1".equals(monthPerformanceInfo.getDeviceType())) { // 统计虚拟机内存
				chartDatalist = ibatisDAO.getData("getVmMemPerformance",
						monthPerformanceInfo);
			}

		} catch (SQLException e) {
			logger.error("月度性能统计-内存使用率统计，操作数据库异常", e);
			e.printStackTrace();
		}

		/* 设置CPU使用率统计json */
		if (null != chartDatalist) {
			chartData = this.JsonPackaging(chartDatalist,
					monthPerformanceInfo.getDeviceType());
		}

		/* 设置查询日期，用于页面展示（格式：yyyy-MM-dd） */
		formatDateForDisplay();

		logger.info("月度性能统计-内存使用率统计，结束");

		return "success";
	}

	/**
	 * 将查出的数据封装为json格式
	 * 
	 * @param 查出的数据list
	 * @return list，第一个是x坐标的json，第二个是数值的json
	 */
	private List<String> JsonPackaging(List<MonthPerformanceInfo> list,
			String deviceType) {
		JSONArray xAxisArr = new JSONArray(); // x轴坐标
		JSONArray top10CpuData = new JSONArray(); // 平均使用率

		JSONObject obj = null;
		for (MonthPerformanceInfo i : list) {
			/* 横坐标 */
			xAxisArr.add(i.getDeviceName());

			/* 平均使用率 */
			obj = new JSONObject();
			obj.put("y", i.getMemUsedPer());
			obj.put("设备名字", i.getDeviceName());
			top10CpuData.add(obj);

		}

		JSONObject top10CpuObj = new JSONObject();

		top10CpuObj.put("name", monthPerformanceInfo.getDeviceTypeName()
				+ "内存平均使用率");

		top10CpuObj.put("data", top10CpuData);
		top10CpuObj.put("color", "#1E90FF");

		JSONArray seriesArr = new JSONArray();
		seriesArr.add(top10CpuObj);

		List<String> result = new ArrayList<String>();
		result.add(JsonUtil.jsonTrans(xAxisArr.toString()));
		result.add(JsonUtil.jsonTrans(seriesArr.toString()));
		return result;
	}


	public List<String> getChartData() {
		return chartData;
	}

	public void setChartData(List<String> chartData) {
		this.chartData = chartData;
	}

	public List<MonthPerformanceInfo> getMonthPerformanceInfolist() {
		return monthPerformanceInfolist;
	}

	public void setMonthPerformanceInfolist(
			List<MonthPerformanceInfo> monthPerformanceInfolist) {
		this.monthPerformanceInfolist = monthPerformanceInfolist;
	}

	public List<MonthPerformanceInfo> getResPoolList() {
		return resPoolList;
	}

	public void setResPoolList(List<MonthPerformanceInfo> resPoolList) {
		this.resPoolList = resPoolList;
	}

	public List<MonthPerformanceInfo> getPoolPartList() {
		return poolPartList;
	}

	public void setPoolPartList(List<MonthPerformanceInfo> poolPartList) {
		this.poolPartList = poolPartList;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public List<MonthPerformanceInfo> getAppList() {
		return appList;
	}

	public void setAppList(List<MonthPerformanceInfo> appList) {
		this.appList = appList;
	}

}
