package com.neusoft.mid.cloong.web.page.resourceManagement.appView.performanceStatistics.action;

import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.JsonUtil;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.StaDeviceWeek;

/**
 * 业务下->内存性能统计->图
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-1-4 下午2:45:29
 */
public class MemUsedPerReportAction extends ResourceManagementBaseAction
{

	private static final long serialVersionUID = -5024421508278442852L;

	private static Logger logger = Logger.getLogger(MemUsedPerReportAction.class);

	private String x_axisData;  // x轴坐标数据

	private String y_axisData; // y轴坐标数据
	
	private String device_type; 
	
    private String device_typeNmaeForChart;//页面图显示设备类型名称用
	
	private String device_typeNameForList;//页面列表显示设备类型名称用
	
	private StaDeviceWeek deviceNum = new StaDeviceWeek();

	/**
	 * 跳转Action，里面传递了chart图标的数据
	 */
	@SuppressWarnings("unchecked")
	public String execute()
	{
		//查询内存使用率Top10图表数据
		List<StaDeviceWeek> memuTop10List = null;
		StaDeviceWeek staDeviceWeek = new StaDeviceWeek();
		JSONArray device_typeNameArr = new JSONArray();
		
		try
		{
			staDeviceWeek.setApp_id(appId);
			staDeviceWeek.setDevice_type(device_type);
			// 获取设备数
			deviceNum = (StaDeviceWeek) ibatisDAO.getSingleRecord("queryDeviceNumbyAppId", appId);
			
			// 获取图的性能数据
			if(device_type.equals("2")){
				memuTop10List = ibatisDAO.getData("getPmMemUsedPerListTop10" , staDeviceWeek);
				device_typeNameArr.add("物理机".toString());
				device_typeNameForList = "物理机";
			}else if(device_type.equals("3")){
				memuTop10List = ibatisDAO.getData("getVmMemUsedPerListTop10" , staDeviceWeek);
				device_typeNameArr.add("虚拟机".toString());
				device_typeNameForList = "虚拟机";
			}else if(device_type.equals("0")){
				memuTop10List = ibatisDAO.getData("getMiniPmMemUsedPerListTop10" , staDeviceWeek);
				device_typeNameArr.add("小型机".toString());
				device_typeNameForList = "小型机";
			}else if(device_type.equals("1")){
				memuTop10List = ibatisDAO.getData("getMiniPmParMemUsedPerListTop10" , staDeviceWeek);
				device_typeNameArr.add("小型机分区".toString());
				device_typeNameForList = "小型机分区";
			}
			device_typeNmaeForChart = JsonUtil.jsonTrans(device_typeNameArr.toString());
			
		}
		catch(SQLException e)
		{
			logger.error("查看性能统计之内存使用率，操作数据库异常" , e);
			e.printStackTrace();
		}

		JSONArray x_axisArr = new JSONArray();//x轴json字符串
		
		JSONArray y_axisArr = new JSONArray();//y轴json对象
		
		JSONObject y_obj = new JSONObject();//y轴数据json对象

		for (StaDeviceWeek i : memuTop10List)
		{
			x_axisArr.add(i.getDevice_name());
			
			y_obj.put("y" , Float.parseFloat(i.getMem_used_per()));
			
			y_axisArr.add(y_obj);
		}

		x_axisData = JsonUtil.jsonTrans(x_axisArr.toString());
		
		y_axisData = JsonUtil.jsonTrans(y_axisArr.toString());

		return "success";
	}

	
	/**
	 * @return the x_axisData
	 */
	public String getX_axisData() {
		return x_axisData;
	}

	/**
	 * @param x_axisData the x_axisData to set
	 */
	public void setX_axisData(String x_axisData) {
		this.x_axisData = x_axisData;
	}

	/**
	 * @return the y_axisData
	 */
	public String getY_axisData() {
		return y_axisData;
	}

	/**
	 * @param y_axisData the y_axisData to set
	 */
	public void setY_axisData(String y_axisData) {
		this.y_axisData = y_axisData;
	}

	/**
	 * @return the device_type
	 */
	public String getDevice_type() {
		return device_type;
	}

	/**
	 * @param device_type the device_type to set
	 */
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	/**
	 * @return the device_typeNmaeForChart
	 */
	public String getDevice_typeNmaeForChart() {
		return device_typeNmaeForChart;
	}

	/**
	 * @param device_typeNmaeForChart the device_typeNmaeForChart to set
	 */
	public void setDevice_typeNmaeForChart(String device_typeNmaeForChart) {
		this.device_typeNmaeForChart = device_typeNmaeForChart;
	}

	/**
	 * @return the device_typeNameForList
	 */
	public String getDevice_typeNameForList() {
		return device_typeNameForList;
	}

	/**
	 * @param device_typeNameForList the device_typeNameForList to set
	 */
	public void setDevice_typeNameForList(String device_typeNameForList) {
		this.device_typeNameForList = device_typeNameForList;
	}

	/**
	 * @return the deviceNum
	 */
	public StaDeviceWeek getDeviceNum() {
		return deviceNum;
	}

	/**
	 * @param deviceNum the deviceNum to set
	 */
	public void setDeviceNum(StaDeviceWeek deviceNum) {
		this.deviceNum = deviceNum;
	}
}
