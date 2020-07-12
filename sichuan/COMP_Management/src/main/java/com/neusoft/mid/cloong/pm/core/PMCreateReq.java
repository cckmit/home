package com.neusoft.mid.cloong.pm.core;

import java.util.List;

/**申请物理机订单信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-11 下午04:00:57
 */
public class PMCreateReq  extends ReqBodyInfo{
    
    /**
     * 规格id
     */
    private String standardId;
    
    /**
     * 数量
     */
    private String num;
    
    /**
     * 子网
     */
    private String subNetwork;
    
    /**
     * PM名字集合
     */
    private List<String> pmNameInfo;
    
    
    /**
     * 应用名称
     */
    private String appName;
    
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
     * getPmNameInfo TODO 方法
     * @return TODO
     */
    public List<String> getPmNameInfo() {
        return pmNameInfo;
    }

    /**
     * 
     * setPmNameInfo TODO 方法
     * @param pmNameInfo TODO
     */
    public void setPmNameInfo(List<String> pmNameInfo) {
        this.pmNameInfo = pmNameInfo;
    }


}
