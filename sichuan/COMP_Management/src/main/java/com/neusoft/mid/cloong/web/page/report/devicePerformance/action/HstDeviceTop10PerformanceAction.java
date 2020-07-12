package com.neusoft.mid.cloong.web.page.report.devicePerformance.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.JsonUtil;
import com.neusoft.mid.cloong.web.page.report.devicePerformance.info.DevicePerformanceInfo;
import com.neusoft.mid.cloong.web.page.report.historyPerformance.info.HistoryPerformanceInfo;

/**
 * 设备性能统计-吞吐量Top10统计
 * 
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version Revision 1.0 2015年11月6日 下午6:55:29
 */
public class HstDeviceTop10PerformanceAction extends DeviceBaseAction {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(HstDeviceTop10PerformanceAction.class);

	/** 吞吐量Top10统计图表的横纵坐标数值 */
	private List<String> chartData = new ArrayList<String>();

	/** ajax传递的用来异步分页的数据 */
	private List<DevicePerformanceInfo> devicePerformanceInfolist = new ArrayList<DevicePerformanceInfo>();
	
	/** 业务下拉列表  - 没用*/
	private List<HistoryPerformanceInfo> appList = new ArrayList<HistoryPerformanceInfo>();
	
	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("设备性能统计-内存使用率统计，开始");

		/* 设置页面标题 */
		setPageTitle();

		/* 设置查询日期，用于SQL查询（格式：yyyyMMdd） */
		formatDateForSql();

		/* 取得图表数据 */
		List<DevicePerformanceInfo> chartDatalist = null; // 图表的横纵坐标数值List
		try {
		    if ("2".equals(devicePerformanceInfo.getDeviceType())) { // 统计防火墙吞吐量top10
                chartDatalist = ibatisDAO.getData("getFWHstDeviceTop10Performance", devicePerformanceInfo);
            } else if ("41".equals(devicePerformanceInfo.getDeviceType())) { // 统计交换机端口字节总数top10
                chartDatalist = ibatisDAO.getData("getSwIfOctetsDeviceTop10Performance", devicePerformanceInfo);
            } 
			
		} catch (SQLException e) {
			logger.error("设备性能统计-吞吐量使用率统计，操作数据库异常", e);
			e.printStackTrace();
		}

		/* 设置吞吐量Top10使用率统计json */
		if (null != chartDatalist) {
			chartData = this.JsonPackaging(chartDatalist, devicePerformanceInfo.getDeviceType());
		}

		/* 设置查询日期，用于页面展示（格式：yyyy-MM-dd） */
		formatDateForDisplay();
		
		logger.info("设备性能统计-防火墙使用率统计，结束");

		return "success";
	}

	/**
	 * 将查出的数据封装为json格式
	 * 
	 * @param 查出的数据list
	 * @return list，第一个是x坐标的json，第二个是数值的json
	 */
	private List<String> JsonPackaging(List<DevicePerformanceInfo> list, String deviceType) {
		JSONArray xAxisArr = new JSONArray(); // x轴坐标
		JSONArray top10HstData = new JSONArray(); // 平均使用率

		JSONObject obj = null;
		for (DevicePerformanceInfo i : list) {
			/* 横坐标 */
			xAxisArr.add(i.getDeviceName());

			/* 平均使用率 */
			obj = new JSONObject();
			if ("2".equals(deviceType)) {// 防火墙吞吐量
				obj.put("y", i.getThroughput());
			} else if("41".equals(deviceType)){// 交换机端口字节总数
			    obj.put("y", i.getOctets());
			}
			obj.put("设备名字", i.getDeviceName());
			top10HstData.add(obj);

		}

		JSONObject top10HstObj = new JSONObject();
		if ("2".equals(deviceType)) { // 防火墙吞吐量
			top10HstObj.put("name", "防火墙吞吐量平均值");
		} else if("41".equals(deviceType) || "51".equals(deviceType)){ // 交换机端口字节总数
			top10HstObj.put("name", "字节总数平均值");
		}
		top10HstObj.put("data", top10HstData);
		top10HstObj.put("color", "#1E90FF");

		JSONArray seriesArr = new JSONArray();
		seriesArr.add(top10HstObj);

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

    /**
     * @return the devicePerformanceInfolist
     */
    public List<DevicePerformanceInfo> getDevicePerformanceInfolist() {
        return devicePerformanceInfolist;
    }

    /**
     * @param devicePerformanceInfolist the devicePerformanceInfolist to set
     */
    public void setDevicePerformanceInfolist(List<DevicePerformanceInfo> devicePerformanceInfolist) {
        this.devicePerformanceInfolist = devicePerformanceInfolist;
    }

	/**
	 * @return the appList
	 */
	public List<HistoryPerformanceInfo> getAppList() {
		return appList;
	}

	/**
	 * @param appList the appList to set
	 */
	public void setAppList(List<HistoryPerformanceInfo> appList) {
		this.appList = appList;
	}


    
}
