package com.neusoft.mid.cloong.rpproxy.interfaces.lb;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class RPPLBCreateReq extends RPPBaseReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3374392751795080453L;
	
	//传参标识符：使用模版时0、 不使用模版时1、
	private String paramFlag;
	
	private String protocal;
	
	private String lbPort;

	private String lbTemplateID;
	
	private LBDemand lbDemand;
	
	private String lbip;
	
	private int lbStrategy;
	
	private String appID;
	
	private String appName;
	
	private String lbName;

	public String getParamFlag() {
		return paramFlag;
	}

	public void setParamFlag(String paramFlag) {
		this.paramFlag = paramFlag;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getLbTemplateID() {
		return lbTemplateID;
	}

	public void setLbTemplateID(String lbTemplateID) {
		this.lbTemplateID = lbTemplateID;
	}

	public LBDemand getLbDemand() {
		return lbDemand;
	}

	public void setLbDemand(LBDemand lbDemand) {
		this.lbDemand = lbDemand;
	}

	public String getLbip() {
		return lbip;
	}

	public void setLbip(String lbip) {
		this.lbip = lbip;
	}

	public int getLbStrategy() {
		return lbStrategy;
	}

	public void setLbStrategy(int lbStrategy) {
		this.lbStrategy = lbStrategy;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getLbName() {
		return lbName;
	}

	public void setLbName(String lbName) {
		this.lbName = lbName;
	}

	public String getLbPort() {
		return lbPort;
	}

	public void setLbPort(String lbPort) {
		this.lbPort = lbPort;
	}
	
}
