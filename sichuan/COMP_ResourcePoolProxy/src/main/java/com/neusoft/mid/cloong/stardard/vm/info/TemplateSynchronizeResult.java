/*******************************************************************************
 * @(#)VMStandardSynchronizeResult.java 2013-3-19
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.vm.info;


/**
 * 同步规格返回结果
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-19 下午02:19:18
 */
public class TemplateSynchronizeResult {
    /**
     * 同步状态 STATE
     */
    private String state;

    /**
     * 资源池信息
     */
    private String resourcePoolId;
    
    /**
     * 资源池信息
     */
    private String resourcePoolPartId;
    
    /**
     * 规格id
     */
    private String standardId;
    
    /**
     * 模板id
     */
    private String templateId;
    
    /**
     * 镜像id
     */
    private String osId;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResourcePoolId() {
		return resourcePoolId;
	}

	public void setResourcePoolId(String resourcePoolId) {
		this.resourcePoolId = resourcePoolId;
	}

	public String getResourcePoolPartId() {
		return resourcePoolPartId;
	}

	public void setResourcePoolPartId(String resourcePoolPartId) {
		this.resourcePoolPartId = resourcePoolPartId;
	}

	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}
	
}
