package com.neusoft.mid.cloong.vault.beans.outside.queryAppOperJKStatus;

import com.neusoft.mid.cloong.vault.beans.adapter.CDataAdapter;
import com.neusoft.mid.cloong.vault.beans.outside.base.VaultBaseRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @Classname QueryParamRequest
 * @Description 应用资源敏感操作金库状态查询
 * @Date 2019/6/3 14:52
 * @Created by liu.wenting
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "queryParam")
public class QueryAppOperJKStatusRequest extends VaultBaseRequest{

    /**
     * 登陆应用系统时的根票据，因含有特殊字符，需要使用<![CDATA[ 标签
     */
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String rootTicket;

    /**
     * 应用资源编号
     */
    @XmlElement(required = true)
    private String resNum;

    /**
     * 当前登陆应用系统所使用的从帐号
     */
    @XmlElement(required = true)
    private String appAccount;

    /**
     * 本次操作的应用系统功能模块代码标识
     */
    @XmlElement(required = true)
    private String functionCode;

    /**
     * 本次操作的敏感操作代码标识
     */
    @XmlElement(required = true)
    private String operationCode;


	public String getRootTicket() {
		return rootTicket;
	}

	public void setRootTicket(String rootTicket) {
		this.rootTicket = rootTicket;
	}

	public String getResNum() {
		return resNum;
	}

	public void setResNum(String resNum) {
		this.resNum = resNum;
	}

	public String getAppAccount() {
		return appAccount;
	}

	public void setAppAccount(String appAccount) {
		this.appAccount = appAccount;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}


}
