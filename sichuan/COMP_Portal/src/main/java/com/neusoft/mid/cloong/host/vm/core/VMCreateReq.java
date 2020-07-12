package com.neusoft.mid.cloong.host.vm.core;

import java.util.List;

/**申请虚拟机订单信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-11 下午04:00:57
 */
public class VMCreateReq  extends ReqBodyInfo{

    private String standardId;

    private String osId;

    private String num;

    private String subNetwork;

    private List<String> vmNameInfo;
    
    /**
     * 克隆时，备份ID
     */
    private String vmBackupId;

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSubNetwork() {
        return subNetwork;
    }

    public void setSubNetwork(String subNetwork) {
        this.subNetwork = subNetwork;
    }

    public List<String> getVmNameInfo() {
        return vmNameInfo;
    }

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
