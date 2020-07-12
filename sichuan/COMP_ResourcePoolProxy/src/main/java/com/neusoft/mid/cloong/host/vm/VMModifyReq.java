package com.neusoft.mid.cloong.host.vm;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMOSDiskType;

/**
 * 修改虚拟机请求Req
 */
public class VMModifyReq extends ReqBodyInfo {
    /**
     * 虚拟机id
     */
    private String vmId;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * cpu
     */
    private Integer cpuNum;

    /**
     * 内存
     */
    private Integer ramSize;

    /**
     * 磁盘大小
     */
    private Integer discSize;

    /**
     * 本地磁盘类型,枚举型
     */
    private VMOSDiskType osDiskType;

    /**
     * 虚拟SCSI卡个数,可选
     */
    private Integer vSCSIAdaNum;

    /**
     * 虚拟网卡个数
     */
    private Integer VEthAdaNum;

    /**
     * 镜像ID
     */
    private String osId;

    /**
     * 网卡信息列表
     */
    private List<EthInfo> ethList = new ArrayList<EthInfo>();

    /**
     * 获取vmId字段数据
     * @return Returns the vmId.
     */
    public String getVmId() {
        return vmId;
    }

    /**
     * 设置vmId字段数据
     * @param vmId The vmId to set.
     */
    public void setVmId(String vmId) {
        this.vmId = vmId;
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
     * 获取ramSize字段数据
     * @return Returns the ramSize.
     */
    public Integer getRamSize() {
        return ramSize;
    }

    /**
     * 设置ramSize字段数据
     * @param ramSize The ramSize to set.
     */
    public void setRamSize(Integer ramSize) {
        this.ramSize = ramSize;
    }

    /**
     * 获取discSize字段数据
     * @return Returns the discSize.
     */
    public Integer getDiscSize() {
        return discSize;
    }

    /**
     * 设置discSize字段数据
     * @param discSize The discSize to set.
     */
    public void setDiscSize(Integer discSize) {
        this.discSize = discSize;
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
     * 获取vEthAdaNum字段数据
     * @return Returns the vEthAdaNum.
     */
    public Integer getVEthAdaNum() {
        return VEthAdaNum;
    }

    /**
     * 设置vEthAdaNum字段数据
     * @param vEthAdaNum The vEthAdaNum to set.
     */
    public void setVEthAdaNum(Integer vEthAdaNum) {
        VEthAdaNum = vEthAdaNum;
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
     * 获取ethList字段数据
     * @return Returns the ethList.
     */
    public List<EthInfo> getEthList() {
        return ethList;
    }

    /**
     * 设置ethList字段数据
     * @param ethList The ethList to set.
     */
    public void setEthList(List<EthInfo> ethList) {
        this.ethList = ethList;
    }

}
