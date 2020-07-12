/**
 * 
 */
package com.neusoft.mid.cloong.filestorage.bean;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class FileStorageParamArray implements Serializable{
	private String QuotaSize;
	
	private String Sharetype;

	public String getQuotaSize() {
		return QuotaSize;
	}

	public void setQuotaSize(String quotaSize) {
		QuotaSize = quotaSize;
	}

	public String getSharetype() {
		return Sharetype;
	}

	public void setSharetype(String sharetype) {
		Sharetype = sharetype;
	}

	
}
