package com.neusoft.mid.cloong.web.login;


import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 验证验证码是否正确.
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0 2015-2-28 下午3:44:17
 */
public class FindPasswordValidateVerifyAction extends BaseAction {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2075363407520164866L;
	
	/**
	 * 记录日志.
	 */
	private static LogService logger = LogService.getLogger(FindPasswordValidateVerifyAction.class);
	
	
    /**
     * 验证码.
     */
	private String verify;
	
	/**
	 * 错误信息.
	 */
	private String errMsg;
	
	/**
	 * 验证验证码.
	 */
	public String execute(){
		String hcode= (String)ActionContext.getContext().getSession().get("random");
		if(!hcode.equals(verify)){
			errMsg = getText("findPassword.validateVerify.error");
			logger.error(CommonStatusCode.IO_OPTION_ERROR, "验证码错误!");
		}
		return ConstantEnum.SUCCESS.toString();
	}

	/**
	 * @return the verify
	 */
	public String getVerify() {
		return verify;
	}

	/**
	 * @param verify the verify to set
	 */
	public void setVerify(String verify) {
		this.verify = verify;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
