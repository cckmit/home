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

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 虚拟机创建应答类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMStatusQueryResp extends RPPBaseResp implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 虚拟机ID
     */
    private String vmId;

    /**
     * 虚拟机状态
     */
    private VMStatus vmStatus;

    /**
     * 虚拟机管理和操作的URL
     */
    private String operationURL;

    /**
     * 虚拟机所属的VLANID列表
     */
    private List<String> vlanId = new ArrayList<String>();

    /**
     * 虚拟机用户名
     */
    private String userName;

    /**
     * 虚拟机登陆密码
     */
    private String passWord;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 网卡信息
     */
    private List<EthInfo> ethInfoList = new ArrayList<EthInfo>();

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
     * 获取vmStatus字段数据
     * @return Returns the vmStatus.
     */
    public VMStatus getVmStatus() {
        return vmStatus;
    }

    /**
     * 设置vmStatus字段数据
     * @param vmStatus The vmStatus to set.
     */
    public void setVmStatus(VMStatus vmStatus) {
        this.vmStatus = vmStatus;
    }

    /**
     * 获取operationURL字段数据
     * @return Returns the operationURL.
     */
    public String getOperationURL() {
        return operationURL;
    }

    /**
     * 设置operationURL字段数据
     * @param operationURL The operationURL to set.
     */
    public void setOperationURL(String operationURL) {
        this.operationURL = operationURL;
    }

    /**
     * 获取vlanId字段数据
     * @return Returns the vlanId.
     */
    public List<String> getVlanId() {
        return vlanId;
    }

    /**
     * 设置vlanId字段数据
     * @param vlanId The vlanId to set.
     */
    public void setVlanId(List<String> vlanId) {
        this.vlanId = vlanId;
    }

    /**
     * 获取userName字段数据
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置userName字段数据
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取passWord字段数据
     * @return Returns the passWord.
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * 设置passWord字段数据
     * @param passWord The passWord to set.
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
     * 获取ethInfoList字段数据
     * @return Returns the ethInfoList.
     */
    public List<EthInfo> getEthInfoList() {
        return ethInfoList;
    }

    /**
     * 设置ethInfoList字段数据
     * @param ethInfoList The ethInfoList to set.
     */
    public void setEthInfoList(List<EthInfo> ethInfoList) {
        this.ethInfoList = ethInfoList;
    }

    /**
     * 获取faultstring字段数据
     * @return Returns the faultstring.
     */
    public String getFaultstring() {
        return faultstring;
    }

    /**
     * 设置faultstring字段数据
     * @param faultstring The faultstring to set.
     */
    public void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

}
