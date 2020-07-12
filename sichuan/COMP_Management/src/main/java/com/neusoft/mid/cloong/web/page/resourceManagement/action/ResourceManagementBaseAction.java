/*******************************************************************************
 * @(#)AllAppAction.java 2014-12-30
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.action;

import com.neusoft.mid.cloong.web.action.PageAction;

/**
 * 资源管理BaseAction
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2014-12-30 下午03:42:16
 */
public class ResourceManagementBaseAction extends PageAction {

    private static final long serialVersionUID = 2521336013204349708L;

    /** 当前功能（资源视图和业务视图标识） */
    protected String curFun;

    /** 业务ID */
    protected String appId;
    
    /** 节点类型 */
    protected String nodeType;

    /** 节点ID*/
    protected String nodeId;
    
    /** 节点名称 */
    protected String treeNodeName;

    /** 父节点ID */
    protected String pnodeId;

    /** 父节点名称 */
    protected String pnodeName;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getCurFun() {
		return curFun;
	}

	public void setCurFun(String curFun) {
		this.curFun = curFun;
	}

	public String getPnodeId() {
		return pnodeId;
	}

	public void setPnodeId(String pnodeId) {
		this.pnodeId = pnodeId;
	}

	public String getPnodeName() {
		return pnodeName;
	}

	public void setPnodeName(String pnodeName) {
		this.pnodeName = pnodeName;
	}

	public String getTreeNodeName() {
		return treeNodeName;
	}

	public void setTreeNodeName(String treeNodeName) {
		this.treeNodeName = treeNodeName;
	}
}