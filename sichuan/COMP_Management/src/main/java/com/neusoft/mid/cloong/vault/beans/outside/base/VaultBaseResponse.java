package com.neusoft.mid.cloong.vault.beans.outside.base;

import com.neusoft.mid.cloong.vault.beans.outside.enums.ResultCode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlTransient
public class VaultBaseResponse  implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 调用webservice返回的结果码，表示本次调用是否成功
	 *
	 */
	@XmlElement(required = true)
	private ResultCode resultCode;



	public ResultCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(ResultCode resultCode) {
		this.resultCode = resultCode;
	}

}
