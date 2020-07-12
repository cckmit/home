/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.performance;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.vmNet;
import com.neusoft.mid.iamp.logger.LogService;

public class VMPropertySearchAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService.getLogger(VMPropertySearchAction.class);
	
	/**
     * 物理机性能数据集合
     */
    private List < PMResourceInfoNew > vmResInfos = new ArrayList<PMResourceInfoNew>();
    
    /**
     * 下拉菜单发生变化后 传递过来的 数字  最近一周-0；最近一个月-1；最近三个月-2；
     */
    private String optionTime = "";
    
    private String vmId;
    
    public String execute() {
        logger.info("资源分配查询开始！");
        vmNet vmNet = null;
        try {
        	
        	vmNet vmNetTmp = new vmNet();
//			vmNetTmp.setVmId("CPC-RT-01-SVI001-VM-00001443");
			vmNetTmp.setVmId(vmId);
			vmNet = (vmNet) ibatisDAO.getSingleRecord("selectIpByVmId", vmNetTmp);
            
            //当前时间7天前至今时间
            Calendar beginTime = Calendar.getInstance();
            String ibatisStr = "";
            PMResourceInfoNew pmrin = new PMResourceInfoNew();
            if ("0".equals(optionTime)) {
                //设置起始时间
                beginTime.add(Calendar.DATE, -7);
                pmrin.setPer_hid(vmNet.getIp());
                pmrin.setPer_time(beginTime.getTime());
                //设置执行不同的sql语句
                ibatisStr = "getWeekResource";
            } else if ("1".equals(optionTime)){
                beginTime.add(Calendar.MONTH, -1);
                pmrin.setPer_hid(vmNet.getIp());
                pmrin.setPer_time(beginTime.getTime());
                ibatisStr = "getMouthResource";
            } else {
                beginTime.add(Calendar.MONTH, -3);
                pmrin.setPer_hid(vmNet.getIp());
                pmrin.setPer_time(beginTime.getTime());
                ibatisStr = "getMouthResource";
            }
            vmResInfos = ibatisDAO.getData (ibatisStr, pmrin);
            if(vmResInfos==null){
                if ("0".equals(optionTime)) {
                    //设置起始时间
                    beginTime.add(Calendar.DATE, -7);
                    pmrin.setPer_hid(vmNet.getIp());
                    pmrin.setPer_time(beginTime.getTime());
                    //设置执行不同的sql语句
                    ibatisStr = "getWeek2Resource";
                } else if ("1".equals(optionTime)){
                    beginTime.add(Calendar.MONTH, -1);
                    pmrin.setPer_hid(vmNet.getIp());
                    pmrin.setPer_time(beginTime.getTime());
                    ibatisStr = "getMouth2Resource";
                } else {
                    beginTime.add(Calendar.MONTH, -3);
                    pmrin.setPer_hid(vmNet.getIp());
                    pmrin.setPer_time(beginTime.getTime());
                    ibatisStr = "getMouth2Resource";
                }
                vmResInfos = ibatisDAO.getData (ibatisStr, pmrin);
            }
            
            //为了调整有无数据，以及不同机器之间数据时间段情况的不同，将时间段修改为统一时间段
//            voidDataHandle();
            
            logger.info("虚拟机性能监控, 资源分配查询结束！");
         } catch (Exception e) {
                logger.error("虚拟机性能监控，资源分配查询异常，", e);
                return ConstantEnum.ERROR.toString();
        } 
        return ConstantEnum.SUCCESS.toString();
    }
    
    private void voidDataHandle() {
        Calendar now = Calendar.getInstance();
        Calendar weekAgo = Calendar.getInstance();
        weekAgo.add(Calendar.DATE, -7);
        Calendar monthAgo = Calendar.getInstance();
        monthAgo.add(Calendar.MONTH, -1);
        Calendar threeMonthAgo = Calendar.getInstance();
        threeMonthAgo.add(Calendar.MONTH, -3);
        
        Calendar beginTime;
        if ("0".equals(optionTime)) {
            beginTime = weekAgo;
        } else if ("1".equals(optionTime)){
            beginTime = monthAgo;
        } else {
            beginTime = threeMonthAgo;
        }
        
        //设置时间段
        PMResourceInfoNew prn = new PMResourceInfoNew();
        prn.setPer_time(beginTime.getTime());
        vmResInfos.add(0,prn);
        PMResourceInfoNew prn1 = new PMResourceInfoNew();
        prn1.setPer_time(now.getTime());
        vmResInfos.add(prn1);
//        //设置进程时间段
//        PMResourcePInfo prnp = new PMResourcePInfo();
//        prnp.setPer_time(beginTime.getTime());
//        pmResPInfos.add(0,prnp);
//        PMResourcePInfo prnp1 = new PMResourcePInfo();
//        prnp1.setPer_time(now.getTime());
//        pmResPInfos.add(prnp1);
    }

	public List<PMResourceInfoNew> getVmResInfos() {
		return vmResInfos;
	}

	public void setVmResInfos(List<PMResourceInfoNew> vmResInfos) {
		this.vmResInfos = vmResInfos;
	}

	public String getOptionTime() {
		return optionTime;
	}

	public void setOptionTime(String optionTime) {
		this.optionTime = optionTime;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
    
}
