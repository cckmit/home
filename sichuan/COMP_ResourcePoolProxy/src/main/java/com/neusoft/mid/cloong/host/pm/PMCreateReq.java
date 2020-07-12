/*******************************************************************************
 * @(#)PMCreateReq.java 2014-1-15
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.host.pm;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthAdaType;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.pm.PMCreateModel;

/**
 * 创建物理机接口请求
 * @author <a href="mailto:wei_sun@neusoft.com">wei_sun</a>
 * @version $Revision 1.0 $ 2014-1-15 下午4:01:03
 */
public class PMCreateReq extends ReqBodyInfo {

    /**
     * 创建模式<br>
     */
    private PMCreateModel createModel;
    
    private String standardId;
    
    private String osId;
    
    private Integer num;

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
    private Integer memorySizsMB;
    
    /**
     * 物理机的型号
     */
    private String serverType;

    /**
     * 物理机本地硬盘大小(GB)
     */
    private Integer osSizeGB;
    
    /**
     * cpu的类型
     */
    private String CPUType;
    
    /**
     * 网卡的个数,必选
     */
    private Integer ethAdaNum;
    
    /**
     * 网卡的规格<br>
     */
    private EthAdaType ethAdaType;

    /**
     * 物理SCSI卡个数,可选
     */
    private Integer SCSIAdaNum;

    /**
     * 物理HBA卡个数
     */
    private Integer HBANum;
    
    
    /**
     * 物理机HBA规格
     */
    private String HBAType;

    /**
     * 网卡信息列表
     */
    private List<List<EthInfo>> ethList = new ArrayList<List<EthInfo>>();

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
     * 获取num字段数据
     * @return Returns the num.
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置num字段数据
     * @param num The num to set.
     */
    public void setNum(Integer num) {
        this.num = num;
    }

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
    public Integer getMemorySizsMB() {
        return memorySizsMB;
    }

    /**
     * 设置memorySizsMB字段数据
     * @param memorySizsMB The memorySizsMB to set.
     */
    public void setMemorySizsMB(Integer memorySizsMB) {
        this.memorySizsMB = memorySizsMB;
    }

    /**
     * 获取osSizeGB字段数据
     * @return Returns the osSizeGB.
     */
    public Integer getOsSizeGB() {
        return osSizeGB;
    }

    /**
     * 设置osSizeGB字段数据
     * @param osSizeGB The osSizeGB to set.
     */
    public void setOsSizeGB(Integer osSizeGB) {
        this.osSizeGB = osSizeGB;
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

	public Integer getEthAdaNum() {
		return ethAdaNum;
	}

	public void setEthAdaNum(Integer ethAdaNum) {
		this.ethAdaNum = ethAdaNum;
	}

	public EthAdaType getEthAdaType() {
		return ethAdaType;
	}

	public void setEthAdaType(EthAdaType ethAdaType) {
		this.ethAdaType = ethAdaType;
	}

	public Integer getSCSIAdaNum() {
		return SCSIAdaNum;
	}

	public void setSCSIAdaNum(Integer sCSIAdaNum) {
		SCSIAdaNum = sCSIAdaNum;
	}


	public Integer getHBANum() {
		return HBANum;
	}

	public void setHBANum(Integer hBANum) {
		HBANum = hBANum;
	}

	public String getHBAType() {
		return HBAType;
	}

	public void setHBAType(String hBAType) {
		HBAType = hBAType;
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("物理机规格ID为：").append(this.standardId).append("\n");
        sb.append("物理机镜像ID为：").append(this.osId).append("\n");
        sb.append("物理机数量为：").append(this.num).append("\n");
        return sb.toString();
    }
}
