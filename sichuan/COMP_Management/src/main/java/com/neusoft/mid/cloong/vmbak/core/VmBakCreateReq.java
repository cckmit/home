package com.neusoft.mid.cloong.vmbak.core;

import com.neusoft.mid.cloong.vm.core.ReqBodyInfo;

/**申请虚拟机备份订单信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-11 下午04:00:57
 */
public class VmBakCreateReq  extends ReqBodyInfo{

    /**
     * 虚拟机id
     */
    private String vmId;
    
    /**
     * 资源池id
     */
    private String resPoolId;
    
    /**
     * 资源池分区id
     */
    private String resPoolPartId;

    /**
     * 
     * getVmId TODO 方法
     * @return TODO
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 
     * setVmId TODO 方法
     * @param vmId TODO
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    /**
     * 
     * getResPoolId TODO 方法
     * @return TODO
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 
     * setResPoolId TODO 方法
     * @param resPoolId TODO
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 
     * getResPoolPartId TODO 方法
     * @return TODO
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 
     * setResPoolPartId TODO 方法
     * @param resPoolPartId TODO
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

}
