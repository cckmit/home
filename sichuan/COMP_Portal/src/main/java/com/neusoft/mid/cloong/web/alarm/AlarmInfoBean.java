package com.neusoft.mid.cloong.web.alarm;

public class AlarmInfoBean {
	
	private String rid;
	
	private String eventTime;
	
	private String text;

	public AlarmInfoBean() {
		super();
	}

	public AlarmInfoBean(String rid, String eventTime, String text) {
		super();
		this.rid = rid;
		this.eventTime = eventTime;
		this.text = text;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "AlarmInfoBean [rid=" + rid + ", eventTime=" + eventTime + ", text=" + text + "]";
	}
}
