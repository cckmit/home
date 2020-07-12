package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

public class PortMapRequest extends RPPBaseReq implements Serializable {
	private String vmId;

	private String vmPrivateIp;

	private String vmPort;

	private String publicIp;

	private String publicPort;

	private String protocol;

	private String mappingMode;

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getVmPrivateIp() {
		return vmPrivateIp;
	}

	public void setVmPrivateIp(String vmPrivateIp) {
		this.vmPrivateIp = vmPrivateIp;
	}

	public String getVmPort() {
		return vmPort;
	}

	public void setVmPort(String vmPort) {
		this.vmPort = vmPort;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public String getPublicPort() {
		return publicPort;
	}

	public void setPublicPort(String publicPort) {
		this.publicPort = publicPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getMappingMode() {
		return mappingMode;
	}

	public void setMappingMode(String mappingMode) {
		this.mappingMode = mappingMode;
	}

}
