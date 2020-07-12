package com.neusoft.mid.cloong.lb;

public class LBDemandReq {
	
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
