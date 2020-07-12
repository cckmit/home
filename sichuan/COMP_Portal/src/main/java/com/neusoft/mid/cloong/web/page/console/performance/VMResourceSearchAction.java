/**
 * 
 */
package com.neusoft.mid.cloong.web.page.console.performance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.vmNet;
import com.neusoft.mid.iamp.logger.LogService;

public class VMResourceSearchAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService.getLogger(VMResourceSearchAction.class);

	/**
     * 虚拟机性能数据集合
     */
    private List < PMResourceInfoNew > vmResInfos = new ArrayList<PMResourceInfoNew>();
    //private List < PMResourceInfoNew > vm2ResInfos = new ArrayList<PMResourceInfoNew>();

    private String vmId;
    
	public String execute() {
		vmNet vmNet = null;
		try {
			vmNet vmNetTmp = new vmNet();
//			vmNetTmp.setVmId("CPC-RT-01-SVI001-VM-00001443");
			vmNetTmp.setVmId(vmId);
			vmNet = (vmNet) ibatisDAO.getSingleRecord("selectIpByVmId", vmNetTmp);
			
			//获取当天时间0点
	        Calendar zeroClock = Calendar.getInstance();
	        zeroClock.set(Calendar.AM_PM, 0);
	        zeroClock.set(Calendar.HOUR, 0);
	        zeroClock.set(Calendar.MINUTE, 0);
	        zeroClock.set(Calendar.SECOND, 0);
	        PMResourceInfoNew pmrin = new PMResourceInfoNew();
	        pmrin.setPer_hid(vmNet.getIp());
	        pmrin.setPer_time(zeroClock.getTime());
	        //查询基本tab页的性能指标数据
	        vmResInfos = ibatisDAO.getData("selectVmResource", pmrin);
	        //vm2ResInfos = ibatisDAO.getData("selectVm2Resource", pmrin);
	        if(vmResInfos==null){
				vmResInfos = ibatisDAO.getData("selectVm2Resource", pmrin);
			}

	        logger.info("处理虚拟机数据格式-- start:");
            for(int i=0;i<vmResInfos.size();i++){
                if(null!= vmResInfos.get(i).getMem_free()&&null!=vmResInfos.get(i).getMem_total()&&null!=vmResInfos.get(i).getMem_shared()&&null!=vmResInfos.get(i).getMem_cached()&&null!=vmResInfos.get(i).getMem_buffers()){
                    if(""!= vmResInfos.get(i).getMem_free()&&""!=vmResInfos.get(i).getMem_total()&&""!=vmResInfos.get(i).getMem_shared()&&""!=vmResInfos.get(i).getMem_cached()&&""!=vmResInfos.get(i).getMem_buffers()){
                        vmResInfos.get(i).setMem_free(Double.toString(Double.parseDouble(vmResInfos.get(i).getMem_free())/1024)); 
                        vmResInfos.get(i).setMem_total(Double.toString(Double.parseDouble(vmResInfos.get(i).getMem_total())/1024)); 
                        vmResInfos.get(i).setMem_shared(Double.toString(Double.parseDouble(vmResInfos.get(i).getMem_shared())/1024)); 
                        vmResInfos.get(i).setMem_cached(Double.toString(Double.parseDouble(vmResInfos.get(i).getMem_cached())/1024)); 
                        vmResInfos.get(i).setMem_buffers(Double.toString(Double.parseDouble(vmResInfos.get(i).getMem_buffers())/1024));
                        vmResInfos.get(i).setBytes_in(Double.toString(Double.parseDouble(vmResInfos.get(i).getBytes_in().split("=")[1].replace("^", "0"))));
                        vmResInfos.get(i).setBytes_out(Double.toString(Double.parseDouble(vmResInfos.get(i).getBytes_out().split("=")[1].replace("^", "0"))));
                    }
                }
                
                if(null != vmResInfos.get(i).getDisk_io() && !"".equals(vmResInfos.get(i).getDisk_io())){
                	String[] diskIos = vmResInfos.get(i).getDisk_io().split("=");
                	vmResInfos.get(i).setDisk_io(diskIos[1]);                	
                }
                
                if(null != vmResInfos.get(i).getDisk_read() && !"".equals(vmResInfos.get(i).getDisk_read())){
                	String[] diskReads = vmResInfos.get(i).getDisk_read().split("\\^");
                	String readData = "";
                    for (int m=0;m<diskReads.length;m++){                     
                        //      VM标识数据
                        if(diskReads[m].indexOf("VM")!=-1){
                            String[] readDatas = diskReads[m].split("="); 
                            readData = readDatas[1];
                        }   
                    }
                    vmResInfos.get(i).setDisk_read(readData);
                }
                
                if(null != vmResInfos.get(i).getDisk_write() && !"".equals(vmResInfos.get(i).getDisk_write())){
                	String[] diskWrites = vmResInfos.get(i).getDisk_write().split("\\^");
                	String writeData = "";
                    for (int n=0;n<diskWrites.length;n++){                     
                        //      VM标识数据
                        if(diskWrites[n].indexOf("VM")!=-1){
                            String[] writeDatas = diskWrites[n].split("="); 
                            writeData = writeDatas[1];
                        }   
                    }
                    vmResInfos.get(i).setDisk_write(writeData);
                }  
            }
            logger.info("处理虚拟机数据格式-- end:");
            logger.info("虚拟机性能监控，资源分配查询结果");
	        
		} catch (SQLException e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"按虚拟机id查询虚拟机ip信息出错，数据库异常。", e);
			return ConstantEnum.ERROR.toString();
		} catch (Exception e) {
			logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
					"按虚拟机id查询虚拟机ip信息出错，数据库异常。", e);
			return ConstantEnum.ERROR.toString();
		}

		return ConstantEnum.SUCCESS.toString();
	}
	
	public List<PMResourceInfoNew> getVmResInfos() {
		return vmResInfos;
	}


	public void setVmResInfos(List<PMResourceInfoNew> vmResInfos) {
		this.vmResInfos = vmResInfos;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

}
