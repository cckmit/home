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
 * 所有业务-CPU使用率统计
 * 
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui.Li</a>
 * @version Revision 1.0 2015年1月6日 下午6:55:29
 */
public class CpuExceededAction extends ResourceManagementBaseAction {
	private static final long serialVersionUID = -4864239092540115107L;

	private static Logger logger = Logger.getLogger(CpuExceededAction.class);

	/**
	 * 按百分比的图表x坐标以及数值
	 */
	private List<String> perChart;

	/**
	 * 按数值的图表x坐标以及数值
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
		logger.info("所有业务-CPU使用率统计，开始");

		List<DeviceExceededNum> perlist = null; // 按使用率超标设备数占比统计List
		List<DeviceExceededNum> numlist = null; // 按使用率超标设备数统计List

		try {
			
			perlist = ibatisDAO.getData("getCpuOverNumByApp", deviceOverInfo);
			if (perlist.size() >= 10) {
				perlist = perlist.subList(0, 10);
			}

			numlist = ibatisDAO.getData("getCpuOverNumByAppOrderByNum", deviceOverInfo);
			if (numlist.size() >= 10) {
				numlist = numlist.subList(0, 10);
			}
		} catch (SQLException e) {
			logger.error("所有业务-CPU使用率统计，操作数据库异常", e);
			e.printStackTrace();
		}

		logger.info("拼装json串开始");

        /* 设置按使用率超标设备数占比统计json */
		perChart = JsonPackaging(perlist);

        /* 设置按使用率超标设备数统计json */
		numChart = JsonPackaging(numlist);

		logger.info("拼装json串结束");

		logger.info("所有业务-CPU使用率统计，结束");

		return "success";
	}

	/**
	 * 将查出的数据封装为json格式
	 * 
	 * @param 查出的数据list
	 * @return 一个String的list，第一个是x坐标的json，第二个是数值的json
	 */
	private List<String> JsonPackaging(List<DeviceExceededNum> list) {
		JSONArray xAxisArr = new JSONArray(); // x轴坐标
		JSONArray otherData = new JSONArray(); // 其它数值
		JSONArray notOverData = new JSONArray(); // 未超标数值
		JSONArray overData = new JSONArray(); // 超标数值
		
		JSONObject obj = null;
		for (DeviceExceededNum i : list) {
			/* 横坐标 */
			xAxisArr.add(i.getAppName());

			/* 其它 */
			obj = new JSONObject();
			obj.put("y", i.getOtherNum());
			obj.put("per", i.getOtherPer());
			obj.put("ave", "");
			otherData.add(obj);
			
			/* 未超标 */
			obj = new JSONObject();
			obj.put("y", i.getCpuNotOverNum());
			obj.put("per", i.getCpuNotOverPer());
			obj.put("ave", i.getCpuNotOverAve());
			notOverData.add(obj);
			
			/* 超标 */
			obj = new JSONObject();
			obj.put("y", i.getCpuOverNum());
			obj.put("per", i.getCpuOverPer());
			obj.put("ave", i.getCpuOverAve());
			overData.add(obj);
		}

		JSONObject otherObj = new JSONObject();
		otherObj.put("name", "无性能采集数据");
		otherObj.put("data", otherData);
		otherObj.put("color", "#C0C0C0");
		
		JSONObject notOverObj = new JSONObject();
		notOverObj.put("name", "未超标");
		notOverObj.put("data", notOverData);
		notOverObj.put("color", "#6CD7D9");
		
		JSONObject overObj = new JSONObject();
		overObj.put("name", "超标");
		overObj.put("data", overData);
		overObj.put("color", "#D87A80");

		JSONArray seriesArr = new JSONArray();
		seriesArr.add(otherObj);
		seriesArr.add(notOverObj);
		seriesArr.add(overObj);

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
		exceededlist = getPage("getCpuOverNumCountByApp", "getCpuOverNumByApp",
				deviceOverInfo, PageTransModel.ASYNC,
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
		exceededlist = getPage("getCpuOverNumCountByApp",
				"getCpuOverNumByAppOrderByNum", deviceOverInfo,
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
