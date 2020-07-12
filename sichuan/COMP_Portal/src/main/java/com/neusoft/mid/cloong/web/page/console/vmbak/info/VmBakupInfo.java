package com.neusoft.mid.cloong.web.page.console.vmbak.info;

import java.io.Serializable;

/**
 * 虚拟机备份信息
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.0 $ 2015-3-12 下午07:52:44
 */
public class VmBakupInfo implements Serializable{
	/**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 备份的内部编码.恢复一个备份时，需要指定该编码
     */
    private String vmBakInternalId;

    /**
     * 备份生成的时间
     */
    private String generationTime;

    /**
     * 备份的容量大小，单位MB
     */
    private String storeSize;
    
    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

	public String getVmBakInternalId() {
		return vmBakInternalId;
	}

	public void setVmBakInternalId(String vmBakInternalId) {
		this.vmBakInternalId = vmBakInternalId;
	}

	public String getGenerationTime() {
		return generationTime;
	}

	public void setGenerationTime(String generationTime) {
		this.generationTime = generationTime;
	}

	public String getStoreSize() {
		return storeSize;
	}

	public void setStoreSize(String storeSize) {
		this.storeSize = storeSize;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
