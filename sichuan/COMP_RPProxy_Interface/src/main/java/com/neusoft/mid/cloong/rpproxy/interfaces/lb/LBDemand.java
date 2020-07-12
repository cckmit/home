package com.neusoft.mid.cloong.rpproxy.interfaces.lb;

import java.io.Serializable;

public class LBDemand implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -720204946662977408L;

	private int throughput;
	
	private int connectNum;

	private int newConnectNum;

	public int getThroughput() {
		return throughput;
	}

	public void setThroughput(int throughput) {
		this.throughput = throughput;
	}

	public int getConnectNum() {
		return connectNum;
	}

	public void setConnectNum(int connectNum) {
		this.connectNum = connectNum;
	}

	public int getNewConnectNum() {
		return newConnectNum;
	}

	public void setNewConnectNum(int newConnectNum) {
		this.newConnectNum = newConnectNum;
	}
	
}
