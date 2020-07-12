/*******************************************************************************
 * @(#)CreateTreeInfo.java 2014-12-31
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business;

/**
 * 生成菜单树bean
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2014-12-30 下午03:42:16
 */
public class CreateTreeInfo {
    /** 当前节点ID,必须属性 */
    private String id;
    
    /** 节点名称,必须属性 */
    private String name;

    /** 父节点ID,必须属性 */
    private String pId;

    /** 父节点名称,必须属性 */
    private String pName;

    /** 是否是父节点,必须属性 */
    private boolean isParent;
    
    /** 节点类型 */
    private String nodeType;

    /** 图标路径,必须属性 */
    private String icon;

    /** 字体,必须属性如{"color":"black"}*/
    private String font;

    /** 是否需要刷新 */
    private boolean isRef;

    /** 当前功能 */
    private String curFun;

    /**  查询子节点的Action地址 */
    private String childNodeUrl;

    /** 页面跳转的Action地址 */
    private String skipUrl;
    
    /**查数据时使用*/
    private String orderId;

    /** 业务ID */
    private String appId;

    /** 业务名称 */
    private String appName;
    
    /** 用户ID */
    private String userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public boolean isRef() {
		return isRef;
	}

	public void setRef(boolean isRef) {
		this.isRef = isRef;
	}

	public String getCurFun() {
		return curFun;
	}

	public void setCurFun(String curFun) {
		this.curFun = curFun;
	}

	public String getChildNodeUrl() {
		return childNodeUrl;
	}

	public void setChildNodeUrl(String childNodeUrl) {
		this.childNodeUrl = childNodeUrl;
	}

	public String getSkipUrl() {
		return skipUrl;
	}

	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
