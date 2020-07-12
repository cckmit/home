/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 虚拟机创建请求类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMCreateReq extends RPPBaseReq implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 创建模式<br>
     */
    private VMCreateModel createModel;

    /**
     * 规格ID
     */
    private String standardId;

    /**
     * 镜像ID.模板创建、自定义创建都需要填写此属性
     */
    private String osId;
    
    /**
     * 物理机Id
     */
    private String pmId;

    /**
     * 创建虚拟机个数
     */
    private int count = INT_DEFAULT_VAL;

    /**
     * 虚拟机所属的应用ID
     */
    private String appId;

    /**
     * 虚拟机所属的应用名称
     */
    private String appName;

    /**
     * 虚拟机安全等级要求
     */
    private String security;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 虚拟机vCPU个数
     */
    private int cpuNum = INT_DEFAULT_VAL;

    /**
     * 虚拟机内存大小(GB)
     */
    private int memorySizsMB = INT_DEFAULT_VAL;

    /**
     * 本地磁盘类型,枚举型
     */
    private String osDiskType;

    /**
     * 虚拟机本地硬盘大小(GB)
     */
    private int osSizeGB = INT_DEFAULT_VAL;

    /**
     * 虚拟SCSI卡个数,可选
     */
    private int vSCSIAdaNum = INT_DEFAULT_VAL;

    /**
     * 虚拟FC-HBA卡个数
     */
    private int vFCHBANum = INT_DEFAULT_VAL;

    /**
     * 网卡信息列表
     */
    private List<List<EthInfo>> ethList = new ArrayList<List<EthInfo>>();

    /**
     * 获取createModel字段数据
     * @return Returns the createModel.
     */
    public VMCreateModel getCreateModel() {
        return createModel;
    }

    /**
     * 设置createModel字段数据
     * @param createModel The createModel to set.
     */
    public void setCreateModel(VMCreateModel createModel) {
        this.createModel = createModel;
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
     * 获取osId字段数据
     * @return Returns the osId.
     */
    public String getOsId() {
        return osId;
    }

    /**
     * 设置osId字段数据
     * @param osId The osId to set.
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * 获取count字段数据
     * @return Returns the count.
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置count字段数据
     * @param count The count to set.
     */
    public void setCount(int count) {
        this.count = count;
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

    /**
     * 获取security字段数据
     * @return Returns the security.
     */
    public String getSecurity() {
        return security;
    }

    /**
     * 设置security字段数据
     * @param security The security to set.
     */
    public void setSecurity(String security) {
        this.security = security;
    }

    /**
     * 获取vmName字段数据
     * @return Returns the vmName.
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * 设置vmName字段数据
     * @param vmName The vmName to set.
     */
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    /**
     * 获取cpuNum字段数据
     * @return Returns the cpuNum.
     */
    public int getCpuNum() {
        return cpuNum;
    }

    /**
     * 设置cpuNum字段数据
     * @param cpuNum The cpuNum to set.
     */
    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    /**
     * 获取memorySizsMB字段数据
     * @return Returns the memorySizsMB.
     */
    public int getMemorySizsMB() {
        return memorySizsMB;
    }

    /**
     * 设置memorySizsMB字段数据
     * @param memorySizsMB The memorySizsMB to set.
     */
    public void setMemorySizsMB(int memorySizsMB) {
        this.memorySizsMB = memorySizsMB;
    }

    /**
     * 获取osSizeGB字段数据
     * @return Returns the osSizeGB.
     */
    public int getOsSizeGB() {
        return osSizeGB;
    }

    /**
     * 设置osSizeGB字段数据
     * @param osSizeGB The osSizeGB to set.
     */
    public void setOsSizeGB(int osSizeGB) {
        this.osSizeGB = osSizeGB;
    }

    /**
     * 获取vSCSIAdaNum字段数据
     * @return Returns the vSCSIAdaNum.
     */
    public int getvSCSIAdaNum() {
        return vSCSIAdaNum;
    }

    /**
     * 设置vSCSIAdaNum字段数据
     * @param vSCSIAdaNum The vSCSIAdaNum to set.
     */
    public void setvSCSIAdaNum(int vSCSIAdaNum) {
        this.vSCSIAdaNum = vSCSIAdaNum;
    }

    /**
     * 获取vFCHBANum字段数据
     * @return Returns the vFCHBANum.
     */
    public int getvFCHBANum() {
        return vFCHBANum;
    }

    /**
     * 设置vFCHBANum字段数据
     * @param vFCHBANum The vFCHBANum to set.
     */
    public void setvFCHBANum(int vFCHBANum) {
        this.vFCHBANum = vFCHBANum;
    }

    /**
     * 获取ethList字段数据
     * @return Returns the ethList.
     */
    public List<List<EthInfo>> getEthList() {
        return ethList;
    }

    /**
     * 设置ethList字段数据
     * @param ethList The ethList to set.
     */
    public void setEthList(List<List<EthInfo>> ethList) {
        this.ethList = ethList;
    }

    /**
     * 获取osDiskType字段数据
     * @return Returns the osDiskType.
     */
    public String getOsDiskType() {
        return osDiskType;
    }

    /**
     * 设置osDiskType字段数据
     * @param osDiskType The osDiskType to set.
     */
    public void setOsDiskType(String osDiskType) {
        this.osDiskType = osDiskType;
    }

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

}
