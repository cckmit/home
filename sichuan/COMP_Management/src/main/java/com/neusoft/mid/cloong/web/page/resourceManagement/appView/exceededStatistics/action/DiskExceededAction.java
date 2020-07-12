package com.neusoft.mid.cloong.web.page.resourceManagement.appView.exceededStatistics.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.JsonUtil;
import com.neusoft.mid.cloong.web.page.common.PageToDisplayPerModel;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.DeviceExceededNum;

/**
 * 所有业务-磁盘使用率统计
 * 
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li</a>
 * @version Revision 1.0 2015年1月6日 下午6:55:29
 */
public class DiskExceededAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = -2135178583729376110L;

	private static Logger logger = Logger.getLogger(DiskExceededAction.class);

	/**
	 * x轴坐标 -- 业务名称
	 */
	private List<String> perChart;

	/**
	 * 向前台传递的数据
	 */
	private List<String> numChart;

	/**
	 * 用作查询条件的实体类，从前台传值回来
	 */
	private DeviceExceededNum deviceOverInfo;

	/**
	 * 表格传递的数据
	 */
	private List<List<DeviceExceededNum>> excessiveList;

	/**
	 * ajax传递的用来异步分页的数据流
	 */
	private List<DeviceExceededNum> exceededlist;

	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("所有业务-磁盘使用率统计，开始");
		
		List<DeviceExceededNum> perlist = null; // 按使用率超标设备数占比统计List
		List<DeviceExceededNum> numlist = null; // 按使用率超标设备数统计List

		try {
			perlist = ibatisDAO.getData("getDiskOverNumByApp", deviceOverInfo);
			if (perlist.size() >= 10) {
				perlist = perlist.subList(0, 10);
			}

			numlist = ibatisDAO.getData("getDiskOverNumByAppOrderByNum", deviceOverInfo);
			if (numlist.size() >= 10) {
				numlist = numlist.subList(0, 10);
			}
		} catch (SQLException e) {
			logger.error("所有业务-磁盘使用率统计，操作数据库异常", e);
			e.printStackTrace();
		}

		logger.info("拼装json串开始");

		/* 设置按使用率超标设备数占比统计json */
		perChart = JsonPackaging(perlist);

		/* 设置按使用率超标设备数统计json */
		numChart = JsonPackaging(numlist);

		logger.info("拼装json串完成");

		logger.info("所有业务-磁盘使用率统计，结束");

		return "success";
	}

	/**
	 * 将查出的数据按照格式封装为json格式
	 * 
	 * @param 查出的数据list
	 * @return 一个String的list，第一个是x坐标的json，第二个是数值的json
	 */
	private List<String> JsonPackaging(List<DeviceExceededNum> list) {
		JSONArray xAxisArr = new JSONArray(); // x轴坐标
		JSONArray otherData = new JSONArray(); // 其它数值
		JSONArray range3Data = new JSONArray(); // 30%>=使用率
		JSONArray range2Data = new JSONArray(); // 70%>=使用率>30%
		JSONArray range1Data = new JSONArray(); // 使用率>70%
		
		JSONObject obj = null;
		for (DeviceExceededNum i : list) {
			/* 横坐标 */
			xAxisArr.add(i.getAppName());

			/* 其它 */
			obj = new JSONObject();
			obj.put("y", i.getOtherNum());
			obj.put("per", i.getOtherPer());
			otherData.add(obj);
			
			/* 30%>=使用率 */
			obj = new JSONObject();
			obj.put("y", i.getDiskRange3Num());
			obj.put("per", i.getDiskRange3Per());
			range3Data.add(obj);
			
			/* 70%>=使用率>30% */
			obj = new JSONObject();
			obj.put("y", i.getDiskRange2Num());
			obj.put("per", i.getDiskRange2Per());
			range2Data.add(obj);
			
			/* 使用率>70% */
			obj = new JSONObject();
			obj.put("y", i.getDiskRange1Num());
			obj.put("per", i.getDiskRange1Per());
			range1Data.add(obj);
		}

		JSONObject otherObj = new JSONObject();
		otherObj.put("name", "无性能采集数据");
		otherObj.put("data", otherData);
		otherObj.put("color", "#C0C0C0");
		
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
		seriesArr.add(otherObj);
		seriesArr.add(range3Obj);
		seriesArr.add(range2Obj);
		seriesArr.add(range1Obj);

		List<String> result = new ArrayList<String>();
		result.add(JsonUtil.jsonTrans(xAxisArr.toString()));
		result.add(JsonUtil.jsonTrans(seriesArr.toString()));

		return result;
	}

	/**
	 * 通过Ajax获取百分比表格
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPerListByAjax() {
		exceededlist = getPage("getDiskOverNumCountByApp",
				"getDiskOverNumByApp", deviceOverInfo, PageTransModel.ASYNC,
				PageToDisplayPerModel.PAGESIZE_10_DISPLAY);

		return "success";
	}

	/**
	 * 通过Ajax获取个数表格
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getNumListByAjax() {
		exceededlist = getPage("getDiskOverNumCountByApp",
				"getDiskOverNumByAppOrderByNum", deviceOverInfo,
				PageTransModel.ASYNC, PageToDisplayPerModel.PAGESIZE_10_DISPLAY);

		return "success";
	}

	/**
	 * @return the deviceOverInfo
	 */
	public DeviceExceededNum getDeviceOverInfo() {
		return deviceOverInfo;
	}

	/**
	 * @param deviceOverInfo
	 *            the deviceOverInfo to set
	 */
	public void setDeviceOverInfo(DeviceExceededNum deviceOverInfo) {
		this.deviceOverInfo = deviceOverInfo;
	}

	/**
	 * @return the excessiveList
	 */
	public List<List<DeviceExceededNum>> getExcessiveList() {
		return excessiveList;
	}

	/**
	 * @param excessiveList
	 *            the excessiveList to set
	 */
	public void setExcessiveList(List<List<DeviceExceededNum>> excessiveList) {
		this.excessiveList = excessiveList;
	}

	/**
	 * @return the perChart
	 */
	public List<String> getPerChart() {
		return perChart;
	}

	/**
	 * @param perChart
	 *            the perChart to set
	 */
	public void setPerChart(List<String> perChart) {
		this.perChart = perChart;
	}

	/**
	 * @return the numChart
	 */
	public List<String> getNumChart() {
		return numChart;
	}

	/**
	 * @param numChart
	 *            the numChart to set
	 */
	public void setNumChart(List<String> numChart) {
		this.numChart = numChart;
	}

	/**
	 * @return the exceededlist
	 */
	public List<DeviceExceededNum> getExceededlist() {
		return exceededlist;
	}

	/**
	 * @param exceededlist
	 *            the exceededlist to set
	 */
	public void setExceededlist(List<DeviceExceededNum> exceededlist) {
		this.exceededlist = exceededlist;
	}

}
