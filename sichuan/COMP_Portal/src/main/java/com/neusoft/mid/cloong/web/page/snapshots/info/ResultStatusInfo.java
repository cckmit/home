package com.neusoft.mid.cloong.web.page.snapshots.info;

public class ResultStatusInfo {
	 /**
     * id
     */
    private String id;

    /**
     * 状态标识
     */
    private String status;

    /**
     * 状态汉字
     */
    private String statusText;

    /**
     * 开始生效时间（计费时间）
     */
    private String effectiveTime;

    /**
     * 到期日期
     */
    private String overTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

}
