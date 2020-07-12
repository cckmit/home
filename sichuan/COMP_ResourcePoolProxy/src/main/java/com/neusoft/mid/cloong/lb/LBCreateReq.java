package com.neusoft.mid.cloong.lb;

import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.lb.LBDemand;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.EthInfo;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMCreateModel;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.VMOSDiskType;

/**
 * 创建虚拟机接口请求
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:57
 */
public class LBCreateReq extends ReqBodyInfo {
	
	private String paramFlag;
	
	private String protocal;

	private String lbTemplateID;
	
	private LBDemandReq lbDemandReq;
	
	private String lbip;
	
	private int lbStrategy;
	
	private String appID;
	
	private String appName;
	
	private String lbName;
	
	private String lbPort;

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

	public LBDemandReq getLbDemandReq() {
		return lbDemandReq;
	}

	public void setLbDemandReq(LBDemandReq lbDemandReq) {
		this.lbDemandReq = lbDemandReq;
	}

	public String getLbPort() {
		return lbPort;
	}

	public void setLbPort(String lbPort) {
		this.lbPort = lbPort;
	}

}
