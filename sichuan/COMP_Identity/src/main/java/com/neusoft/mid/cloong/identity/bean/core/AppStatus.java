package com.neusoft.mid.cloong.identity.bean.core;

/**
 * 用户绑定业务状态值.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0 2015-3-9 下午3:17:14
 */
public enum AppStatus {
	
	/**
	 * 业务待审批.
	 */
	EXAMINATION("1","业务待审批"),
	
	/**
	 * 审批通过
	 */
	AUDITPASS("2","审批通过"),
	
	/**
     * 审批不通过状态
     */
    UNAUDITPASS("3", "审批不通过");
	/**
     * 状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String desc;
   
    /**
     * 创建一个新的用户 status构造函数
     * @param code 状态码
     * @param desc 状态描述
     */
    private AppStatus(String code,String desc) {
    	this.code = code;
    	this.desc = desc;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
