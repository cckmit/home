package com.neusoft.mid.cloong.host.pm.core;

import java.util.List;
import com.neusoft.mid.cloong.host.vm.core.ReqBodyInfo;

/**申请物理机订单信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-11 下午04:00:57
 */
public class PMCreateReq  extends ReqBodyInfo{

    private String standardId;

    private String num;

    private String subNetwork;
    
    /**
     * 应用名称
     */
    private String appName;

    private List<String> pmNameInfo;

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
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

    public List<String> getPmNameInfo() {
        return pmNameInfo;
    }

    public void setPmNameInfo(List<String> pmNameInfo) {
        this.pmNameInfo = pmNameInfo;
    }

    /**
     * 获取appName字段数据
     * @return Returns the appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置appName字段数据
     * @param appName The appName to set.
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }


}
