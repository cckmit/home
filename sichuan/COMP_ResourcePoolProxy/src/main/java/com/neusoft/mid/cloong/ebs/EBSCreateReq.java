package com.neusoft.mid.cloong.ebs;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.BsParam;
import com.neusoft.mid.cloong.rpproxy.interfaces.ebs.EBSCreateModel;

/**
 * 申请虚拟硬盘订单信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-11 下午04:00:57
 */
public class EBSCreateReq extends ReqBodyInfo {

    private String standardId;

    private String ebsName;

    /**
     * 选择传参的标识符
     */
    private EBSCreateModel createModel;

    /**
     * 参数集合
     */
    private BsParam BSParamArray;

    /**
     * 资源的唯一标识，当ResourceType为1时有效
     */
    private String ResourceID;

    /**
     * 业务编码
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getEbsName() {
        return ebsName;
    }

    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

    /**
     * 获取createModel字段数据
     * @return Returns the createModel.
     */
    public EBSCreateModel getCreateModel() {
        return createModel;
    }

    /**
     * 设置createModel字段数据
     * @param createModel The createModel to set.
     */
    public void setCreateModel(EBSCreateModel createModel) {
        this.createModel = createModel;
    }

    /**
     * 获取bSParamArray字段数据
     * @return Returns the bSParamArray.
     */
    public BsParam getBSParamArray() {
        return BSParamArray;
    }

    /**
     * 设置bSParamArray字段数据
     * @param bSParamArray The bSParamArray to set.
     */
    public void setBSParamArray(BsParam bSParamArray) {
        BSParamArray = bSParamArray;
    }

    /**
     * 获取resourceID字段数据
     * @return Returns the resourceID.
     */
    public String getResourceID() {
        return ResourceID;
    }

    /**
     * 设置resourceID字段数据
     * @param resourceID The resourceID to set.
     */
    public void setResourceID(String resourceID) {
        ResourceID = resourceID;
    }

    /**
     * 获取appId字段数据
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appId字段数据
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
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
