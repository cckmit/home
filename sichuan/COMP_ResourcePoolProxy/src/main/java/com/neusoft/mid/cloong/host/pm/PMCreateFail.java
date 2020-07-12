/*******************************************************************************
 * @(#)PMCreateFail.java 2014-1-20
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

/**
 * 物理机创建失败信息
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-20 下午1:56:53
 */
public class PMCreateFail {
    private String failCause;

    private String failCode;

    private String resPoolId;

    private String resPoolPartId;

    private String standardId;

    private String subNetwork;
    
    private String osId;

    private String num;

    private String createTime;

    /**
     * 获取failCause字段数据
     * @return Returns the failCause.
     */
    public String getFailCause() {
        return failCause;
    }

    /**
     * 设置failCause字段数据
     * @param failCause The failCause to set.
     */
    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }

    /**
     * 获取failCode字段数据
     * @return Returns the failCode.
     */
    public String getFailCode() {
        return failCode;
    }

    /**
     * 设置failCode字段数据
     * @param failCode The failCode to set.
     */
    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    /**
     * 获取resPoolId字段数据
     * @return Returns the resPoolId.
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * 设置resPoolId字段数据
     * @param resPoolId The resPoolId to set.
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * 获取resPoolPartId字段数据
     * @return Returns the resPoolPartId.
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }

    /**
     * 设置resPoolPartId字段数据
     * @param resPoolPartId The resPoolPartId to set.
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    /**
     * 获取standardId字段数据
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 设置standardId字段数据
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取subNetwork字段数据
     * @return Returns the subNetwork.
     */
    public String getSubNetwork() {
        return subNetwork;
    }

    /**
     * 设置subNetwork字段数据
     * @param subNetwork The subNetwork to set.
     */
    public void setSubNetwork(String subNetwork) {
        this.subNetwork = subNetwork;
    }

    /**
     * 获取num字段数据
     * @return Returns the num.
     */
    public String getNum() {
        return num;
    }

    /**
     * 设置num字段数据
     * @param num The num to set.
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 获取createTime字段数据
     * @return Returns the createTime.
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置createTime字段数据
     * @param createTime The createTime to set.
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("失败原因为：").append(this.failCause).append("\n");
        sb.append("失败响应码为：").append(this.failCode).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("规格ID为：").append(this.standardId).append("\n");
        sb.append("子网为：").append(this.subNetwork).append("\n");
        sb.append("订购数量（个）为：").append(this.num).append("\n");
        sb.append("创建时间为：").append(this.createTime).append("\n");
        return sb.toString();
    }
}
