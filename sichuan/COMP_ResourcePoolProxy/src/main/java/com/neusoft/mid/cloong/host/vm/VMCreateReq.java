package com.neusoft.mid.cloong.host.vm;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMOSDiskType;

/**
 * 创建虚拟机接口请求
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:57
 */
public class VMCreateReq extends ReqBodyInfo {

    private String standardId;

    private String osId;

    private Integer num;

    /**
     * 创建模式<br>
     */
    private VMCreateModel createModel;

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
    private Integer cpuNum;

    /**
     * 虚拟机内存大小(GB)
     */
    private Integer memorySizsMB;

    /**
     * 本地磁盘类型,枚举型
     */
    private VMOSDiskType osDiskType;

    /**
     * 虚拟机本地硬盘大小(GB)
     */
    private Integer osSizeGB;

    /**
     * 虚拟SCSI卡个数,可选
     */
    private Integer vSCSIAdaNum;

    /**
     * 虚拟FC-HBA卡个数
     */
    private Integer vFCHBANum;
    
    private String pmId;

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
    public Integer getCpuNum() {
        return cpuNum;
    }

    /**
     * 设置cpuNum字段数据
     * @param cpuNum The cpuNum to set.
     */
    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
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
     * 获取osDiskType字段数据
     * @return Returns the osDiskType.
     */
    public VMOSDiskType getOsDiskType() {
        return osDiskType;
    }

    /**
     * 设置osDiskType字段数据
     * @param osDiskType The osDiskType to set.
     */
    public void setOsDiskType(VMOSDiskType osDiskType) {
        this.osDiskType = osDiskType;
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
     * 获取vSCSIAdaNum字段数据
     * @return Returns the vSCSIAdaNum.
     */
    public Integer getvSCSIAdaNum() {
        return vSCSIAdaNum;
    }

    /**
     * 设置vSCSIAdaNum字段数据
     * @param vSCSIAdaNum The vSCSIAdaNum to set.
     */
    public void setvSCSIAdaNum(Integer vSCSIAdaNum) {
        this.vSCSIAdaNum = vSCSIAdaNum;
    }

    /**
     * 获取vFCHBANum字段数据
     * @return Returns the vFCHBANum.
     */
    public Integer getvFCHBANum() {
        return vFCHBANum;
    }

    /**
     * 设置vFCHBANum字段数据
     * @param vFCHBANum The vFCHBANum to set.
     */
    public void setvFCHBANum(Integer vFCHBANum) {
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("虚拟机规格ID为：").append(this.standardId).append("\n");
        sb.append("虚拟机镜像ID为：").append(this.osId).append("\n");
        sb.append("虚拟机数量为：").append(this.num).append("\n");
        return sb.toString();
    }

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

}
