package com.neusoft.mid.cloong.stardard.vm.info;
/**
 * 关联表中的信息
 * @author <a href="mailto:he.jf@neusoft.com">he junfeng </a>
 * @version $Revision 1.1 $ 2014-1-10 上午10:30:39
 */

public class Template {
    
    /**
     * 模板ID TEMPLATE_ID
     */
    private String templateId;
    
    /**
     * 模板名字 TEMPLATE_NAME
     */
    private String templateName;

    /**
     * 镜像ID OS_ID
     */
    private String osId;
	
    /**
     * 规格ID STANDARD_ID
     */
    private String standardId;
    
    /**
     * 创建时间 CREATE_TIME
     */
    private String createTime;

    /**
     * 创建人 CREATE_USER
     */
    private String createUser;
    
    /**
     * 状态 STATUS 
     */
    private String status;
	/**
	 * 资源池id resourcePoolId
	 */
	private String resourcePoolId;

	public String getResourcePoolId() {
		return resourcePoolId;
	}

	public void setResourcePoolId(String resourcePoolId) {
		this.resourcePoolId = resourcePoolId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}

	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
