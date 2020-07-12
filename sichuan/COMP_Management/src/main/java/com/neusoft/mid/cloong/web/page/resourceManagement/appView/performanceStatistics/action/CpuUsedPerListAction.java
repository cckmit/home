package com.neusoft.mid.cloong.web.page.resourceManagement.appView.performanceStatistics.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.neusoft.mid.cloong.web.page.common.PageToDisplayPerModel;
import com.neusoft.mid.cloong.web.page.common.PageTransModel;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.StaDeviceWeek;

/**
 * 业务下->CPU性能统计->列表
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-1-7 下午1:59:24
 */
public class CpuUsedPerListAction extends ResourceManagementBaseAction
{

	private static final long serialVersionUID = -5024421508278442852L;

	private static Logger logger = Logger.getLogger(CpuUsedPerListAction.class);

	private List<StaDeviceWeek> performancelist; // 表格传递的数据
	
	private String device_type;
	
	/**
	 * 跳转Action，里面传递了chart图标的数据
	 */
	@SuppressWarnings("unchecked")
	public String execute()
	{
		StaDeviceWeek staDeviceWeek = new StaDeviceWeek();
		
		staDeviceWeek.setApp_id(appId);
		staDeviceWeek.setDevice_type(device_type);
	
		//查询CPU使用率列表，async异步，sync同步
		if(device_type.equals("2")){
			performancelist = getPage("queryPmCpuUsedPerListCount" , "queryPmCpuUsedPerList",
					staDeviceWeek,PageTransModel.ASYNC ,PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		}else if(device_type.equals("3")){
			performancelist = getPage("queryVmCpuUsedPerListCount" , "queryVmCpuUsedPerList",
				    staDeviceWeek,PageTransModel.ASYNC,PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		}else if(device_type.equals("0")){
			performancelist = getPage("queryMiniPmCpuUsedPerListCount" , "queryMiniPmCpuUsedPerList",
				    staDeviceWeek,PageTransModel.ASYNC,PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		}else if(device_type.equals("1")){
			performancelist = getPage("queryMiniPmParCpuUsedPerListCount" , "queryMiniPmParCpuUsedPerList",
				    staDeviceWeek,PageTransModel.ASYNC,PageToDisplayPerModel.PAGESIZE_10_DISPLAY);
		}
		
		return "success";
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
	 * @return the performancelist
	 */
	public List<StaDeviceWeek> getPerformancelist() {
		return performancelist;
	}

	/**
	 * @param performancelist the performancelist to set
	 */
	public void setPerformancelist(List<StaDeviceWeek> performancelist) {
		this.performancelist = performancelist;
	}
}
