/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.pm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 物理机查询应答类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPPMStatusQueryResp extends RPPBaseResp implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 物理机ID
     */
    private String vmId;

    /**
     * 物理机状态
     */
    private PMStatus pmStatus;

    /**
     * 物理机管理和操作的Ip
     */
    private String operationIp;

    /**
     * 物理机用户名
     */
    private String userName;

    /**
     * 物理机登陆密码
     */
    private String passWord;

    /**
     * 物理机名称
     */
    private String pmName;

    /**
     * 网卡信息
     */
    private List<EthInfo> ethInfoList = new ArrayList<EthInfo>();
    
    

	public String getFaultstring() {
		return faultstring;
	}

	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public PMStatus getPmStatus() {
		return pmStatus;
	}

	public void setPmStatus(PMStatus pmStatus) {
		this.pmStatus = pmStatus;
	}

	public String getOperationIp() {
		return operationIp;
	}

	public void setOperationIp(String operationIp) {
		this.operationIp = operationIp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public List<EthInfo> getEthInfoList() {
		return ethInfoList;
	}

	public void setEthInfoList(List<EthInfo> ethInfoList) {
		this.ethInfoList = ethInfoList;
	}
}
