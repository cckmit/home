/*******************************************************************************
 * @(#)CreatePMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.pm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthAdaType;

/**
 * 物理机创建请求类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPPMCreateReq extends RPPBaseReq implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 创建模式<br>
     */
    private PMCreateModel createModel;
    
    /**
     * 规格ID
     */
    private String standardId;

    /**
     * 镜像ID.模板创建、自定义创建都需要填写此属性
     */
    private String osId;

    /**
     * 创建物理机个数
     */
    private int count = INT_DEFAULT_VAL;

    /**
     * 物理机所属的应用ID
     */
    private String appId;

    /**
     * 物理机所属的应用名称
     */
    private String appName;

    /**
     * 物理机安全等级要求
     */
    private String security;

    /**
     * 物理机名称
     */
    private String pmName;

    /**
     * 物理机内存大小(GB)
     */
    private int memorySizsMB = INT_DEFAULT_VAL;
    
    /**
     * 物理机的型号
     */
    private String serverType;

    /**
     * 物理机本地硬盘大小(GB)
     */
    private int osSizeGB = INT_DEFAULT_VAL;
    
    /**
     * cpu的类型
     */
    private String CPUType;
    
    /**
     * 网卡的个数,必选
     */
    private int ethAdaNum = INT_DEFAULT_VAL;
    
    /**
     * 网卡的规格<br>
     */
    private EthAdaType ethAdaType;

    /**
     * 物理SCSI卡个数,可选
     */
    private int SCSIAdaNum = INT_DEFAULT_VAL;

    /**
     * 物理HBA卡个数
     */
    private int HBANum = INT_DEFAULT_VAL;
    
    /**
     * 物理机HBA规格
     */
    private String HBAType;

    /**
     * 网卡信息列表
     */
    private List<List<EthInfo>> ethList = new ArrayList<List<EthInfo>>();

    /**
     * 获取createModel字段数据
     * @return Returns the createModel.
     */
    public PMCreateModel getCreateModel() {
        return createModel;
    }

    /**
     * 设置createModel字段数据
     * @param createModel The createModel to set.
     */
    public void setCreateModel(PMCreateModel createModel) {
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
     * 获取pmName字段数据
     * @return Returns the pmName.
     */
    public String getPmName() {
        return pmName;
    }

    /**
     * 设置pmName字段数据
     * @param pmName The pmName to set.
     */
    public void setPmName(String pmName) {
        this.pmName = pmName;
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
     * 获取vFCHBANum字段数据
     * @return Returns the vFCHBANum.
     */
    public int getHBANum() {
        return HBANum;
    }

    /**
     * 设置vFCHBANum字段数据
     * @param vFCHBANum The vFCHBANum to set.
     */
    public void setHBANum(int HBANum) {
        this.HBANum = HBANum;
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

	public EthAdaType getEthAdaType() {
		return ethAdaType;
	}

	public void setEthAdaType(EthAdaType ethAdaType) {
		this.ethAdaType = ethAdaType;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getCPUType() {
		return CPUType;
	}

	public void setCPUType(String cPUType) {
		CPUType = cPUType;
	}

	public int getEthAdaNum() {
		return ethAdaNum;
	}

	public void setEthAdaNum(int ethAdaNum) {
		this.ethAdaNum = ethAdaNum;
	}

	public int getSCSIAdaNum() {
		return SCSIAdaNum;
	}

	public void setSCSIAdaNum(int sCSIAdaNum) {
		SCSIAdaNum = sCSIAdaNum;
	}

	public String getHBAType() {
		return HBAType;
	}

	public void setHBAType(String hBAType) {
		HBAType = hBAType;
	}

}
