package com.neusoft.mid.cloong.web.app.info;

import com.neusoft.mid.cloong.web.BaseInfo;

/**
 * 业务信息
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version version 1.0：2015-2-25 下午3:47:44
 */
public class App extends BaseInfo {

    /**
     * 业务ID
     */
    private String appId;
    /**
     * 业务名称
     */
    private String appName;
    /**
     * 描述
     */
    private String description;
    

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	

}
