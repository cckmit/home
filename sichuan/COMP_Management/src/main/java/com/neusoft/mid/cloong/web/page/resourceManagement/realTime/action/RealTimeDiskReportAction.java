package com.neusoft.mid.cloong.web.page.resourceManagement.realTime.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.JsonUtil;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.RealTime;

/**
 * 资源视图->实时性能->磁盘折线图
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version Revision 1.0 2015年1月8日 上午9:11:22
 */
public class RealTimeDiskReportAction extends ResourceManagementBaseAction {
	private static final long serialVersionUID = -5024421508278442852L;

	private static Logger logger = Logger.getLogger(RealTimeDiskReportAction.class);

	private String deviceId;// 设备Id
	
	private String deviceName;//设备名称
	
	private String deviceType;// 设备类型：VM-虚拟机、PM-物理机
	
	private String diskUsedPer;//磁盘使用率
	
	private List<RealTime> timeList = new ArrayList<RealTime>();
	
	private RealTime realTimeRead = new RealTime();
	
	private RealTime realTimeWrite = new RealTime();
	
	private String y_axisData_read; // 要传递的数据_读
	
	private String y_axisData_write; // 要传递的数据_写
	
	private int num;
	/**
	 * 跳转Action，里面传递了chart图标的数据
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("实时性能报表统计开始");
		try {
			List<RealTime> idList = new ArrayList<RealTime>();
			RealTime realTime = new RealTime();
			String usedPer;
			realTime.setDeviceId(deviceId);
			if("VM".equals(deviceType)){
				usedPer = (String)ibatisDAO.getSingleRecord("getMaxPerVm" , realTime);
				deviceName = (String)ibatisDAO.getSingleRecord("getVmName" , realTime);
				idList  = ibatisDAO.getData("getDiskIdListVm" , realTime);
				realTimeRead = (RealTime)ibatisDAO.getSingleRecord("getRealTimeReadVm" , realTime);
				realTimeWrite = (RealTime)ibatisDAO.getSingleRecord("getRealTimeWriteVm" , realTime);
				realTime = (RealTime)ibatisDAO.getSingleRecord("getMaxVm" , realTime);
			}else{
				usedPer = (String)ibatisDAO.getSingleRecord("getMaxPerPm" , realTime);
				deviceName = (String)ibatisDAO.getSingleRecord("getPmName" , realTime);
				idList  = ibatisDAO.getData("getDiskIdListPm" , realTime);
				realTimeRead = (RealTime)ibatisDAO.getSingleRecord("getRealTimeReadPm" , realTime);
				realTimeWrite = (RealTime)ibatisDAO.getSingleRecord("getRealTimeWritePm" , realTime);
				realTime = (RealTime)ibatisDAO.getSingleRecord("getMaxPm" , realTime);
			}
			diskUsedPer = usedPer;
			String maxRead = realTime.getMaxRead();
			String maxWrite = realTime.getMaxWrite();
			realTime = (RealTime)ibatisDAO.getSingleRecord("getMinMaxTime" , null);
			if(realTimeRead != null){
				realTimeRead.setMinyear(realTime.getMinyear());
				realTimeRead.setMinmonth(realTime.getMinmonth());
				realTimeRead.setMinday(realTime.getMinday());
				realTimeRead.setMinhour(realTime.getMinhour());
				realTimeRead.setMaxyear(realTime.getMaxyear());
				realTimeRead.setMaxmonth(realTime.getMaxmonth());
				realTimeRead.setMaxday(realTime.getMaxday());
				realTimeRead.setMaxhour(realTime.getMaxhour());
			}else{
				realTimeRead = realTime;
			}
			realTimeRead.setUsedPer(usedPer);
			realTimeRead.setMaxRead(maxRead);
			
			if(realTimeWrite != null){
				realTimeWrite.setMinyear(realTime.getMinyear());
				realTimeWrite.setMinmonth(realTime.getMinmonth());
				realTimeWrite.setMinday(realTime.getMinday());
				realTimeWrite.setMinhour(realTime.getMinhour());
				realTimeWrite.setMaxyear(realTime.getMaxyear());
				realTimeWrite.setMaxmonth(realTime.getMaxmonth());
				realTimeWrite.setMaxday(realTime.getMaxday());
				realTimeWrite.setMaxhour(realTime.getMaxhour());
			}else{
				realTimeWrite = realTime;
			}
			realTimeWrite.setUsedPer(usedPer);
			realTimeWrite.setMaxWrite(maxWrite);
			
			num = idList.size();
			if(num == 0){
				timeList = ibatisDAO.getData("getAllPoint" , null);
			}else{
				List<RealTime> readList = new ArrayList<RealTime>();
				List<RealTime> writeList = new ArrayList<RealTime>();
				JSONObject y_obj_read = new JSONObject();
				JSONObject y_obj_write = new JSONObject();
				JSONArray y_axisArr_read = new JSONArray();
				JSONArray y_axisArr_write = new JSONArray();
				
				for (RealTime temp : idList) {
					String diskName = temp.getDiskName();
					if("VM".equals(deviceType)){
						readList = ibatisDAO.getData("getDiskReadVm" , temp);
						writeList = ibatisDAO.getData("getDiskWriteVm" , temp);
					}else{
						readList = ibatisDAO.getData("getDiskReadPm" , temp);
						writeList = ibatisDAO.getData("getDiskWritePm" , temp);
					}
					String readStr = JSONArray.fromObject(readList).toString(); 
					String writeStr = JSONArray.fromObject(writeList).toString(); 
					
					y_obj_read.put("name", diskName);
					y_obj_read.put("data", readStr);
					y_axisArr_read.add(y_obj_read);
					y_obj_write.put("name", diskName);
					y_obj_write.put("data", writeStr);
					y_axisArr_write.add(y_obj_write);
				}
				y_axisData_read = JsonUtil.jsonTrans(y_axisArr_read.toString());
				y_axisData_write = JsonUtil.jsonTrans(y_axisArr_write.toString());
			}
		}catch(SQLException e) {
			logger.error("实时性能报表，操作数据库异常" , e);
			e.printStackTrace();
		}
		logger.info("实时性能报表统计完成");
		return "success";
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the diskUsedPer
	 */
	public String getDiskUsedPer() {
		return diskUsedPer;
	}

	/**
	 * @param diskUsedPer the diskUsedPer to set
	 */
	public void setDiskUsedPer(String diskUsedPer) {
		this.diskUsedPer = diskUsedPer;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the timeList
	 */
	public List<RealTime> getTimeList() {
		return timeList;
	}

	/**
	 * @param timeList the timeList to set
	 */
	public void setTimeList(List<RealTime> timeList) {
		this.timeList = timeList;
	}

	/**
	 * @return the y_axisData_write
	 */
	public String getY_axisData_write() {
		return y_axisData_write;
	}

	/**
	 * @param y_axisData_write the y_axisData_write to set
	 */
	public void setY_axisData_write(String y_axisData_write) {
		this.y_axisData_write = y_axisData_write;
	}

	/**
	 * @return the y_axisData_read
	 */
	public String getY_axisData_read() {
		return y_axisData_read;
	}

	/**
	 * @param y_axisData_read the y_axisData_read to set
	 */
	public void setY_axisData_read(String y_axisData_read) {
		this.y_axisData_read = y_axisData_read;
	}

	/**
	 * @return the realTimeRead
	 */
	public RealTime getRealTimeRead() {
		return realTimeRead;
	}

	/**
	 * @param realTimeRead the realTimeRead to set
	 */
	public void setRealTimeRead(RealTime realTimeRead) {
		this.realTimeRead = realTimeRead;
	}

	/**
	 * @return the realTimeWrite
	 */
	public RealTime getRealTimeWrite() {
		return realTimeWrite;
	}

	/**
	 * @param realTimeWrite the realTimeWrite to set
	 */
	public void setRealTimeWrite(RealTime realTimeWrite) {
		this.realTimeWrite = realTimeWrite;
	}
}
