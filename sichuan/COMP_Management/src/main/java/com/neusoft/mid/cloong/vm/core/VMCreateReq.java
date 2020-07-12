package com.neusoft.mid.cloong.vm.core;

import java.util.List;

/**申请虚拟机订单信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-11 下午04:00:57
 */
public class VMCreateReq  extends ReqBodyInfo{
    
    /**
     * 规格id
     */
    private String standardId;
    
    /**
     * 克隆时，备份ID
     */
    private String vmBackupId;

    /**
     * 镜像id
     */
    private String osId;

    /**
     * 数量
     */
    private String num;

    /**
     * 子网
     */
    private String subNetwork;

    /**
     * VM信息集合
     */
    private List<String> vmNameInfo;

    /**
     * 
     * getStandardId TODO 方法
     * @return TODO
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 
     * setStandardId TODO 方法
     * @param standardId TODO
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 
     * getOsId TODO 方法
     * @return TODO
     */
    public String getOsId() {
        return osId;
    }

    /**
     * 
     * setOsId TODO 方法
     * @param osId TODO
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * 
     * getNum TODO 方法
     * @return TODO
     */
    public String getNum() {
        return num;
    }

    /**
     * 
     * setNum TODO 方法
     * @param num TODO
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 
     * getSubNetwork TODO 方法
     * @return TODO
     */
    public String getSubNetwork() {
        return subNetwork;
    }

    /**
     * 
     * setSubNetwork TODO 方法
     * @param subNetwork TODO
     */
    public void setSubNetwork(String subNetwork) {
        this.subNetwork = subNetwork;
    }

    /**
     * 
     * getVmNameInfo TODO 方法
     * @return TODO
     */
    public List<String> getVmNameInfo() {
        return vmNameInfo;
    }

    /**
     * 
     * setVmNameInfo TODO 方法
     * @param vmNameInfo TODO
     */
    public void setVmNameInfo(List<String> vmNameInfo) {
        this.vmNameInfo = vmNameInfo;
    }

    /**
     * 获取vmBackupId字段数据
     * @return Returns the vmBackupId.
     */
    public String getVmBackupId() {
        return vmBackupId;
    }

    /**
     * 设置vmBackupId字段数据
     * @param vmBackupId The vmBackupId to set.
     */
    public void setVmBackupId(String vmBackupId) {
        this.vmBackupId = vmBackupId;
    }
}
