package com.neusoft.mid.cloong.H3C.vlan;

public class H3cTokenResp {

	/***
	 * sdn Token bean
	 */
	private SdnTokenRespBean record;

	/**
	 * 获取record字段数据
	 * @return Returns the record.
	 */
	public SdnTokenRespBean getRecord() {
		return record;
	}

	/**
	 * 设置record字段数据
	 * @param record The record to set.
	 */
	public void setRecord(SdnTokenRespBean record) {
		this.record = record;
	}


	public static class SdnTokenRespBean {
		
		/***
		 * 	认证码.String类型。
		 */
		private String token;
		
		/***
		 * 	用户名。String类型。
		 */
		private String userName;
		
		/***
		 * 域名。String类型。
		 */
		private String  domainName ;

		/**
		 * 获取token字段数据
		 * @return Returns the token.
		 */
		public String getToken() {
			return token;
		}

		/**
		 * 设置token字段数据
		 * @param token The token to set.
		 */
		public void setToken(String token) {
			this.token = token;
		}

		/**
		 * 获取userName字段数据
		 * @return Returns the userName.
		 */
		public String getUserName() {
			return userName;
		}

		/**
		 * 设置userName字段数据
		 * @param userName The userName to set.
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}

		/**
		 * 获取domainName字段数据
		 * @return Returns the domainName.
		 */
		public String getDomainName() {
			return domainName;
		}

		/**
		 * 设置domainName字段数据
		 * @param domainName The domainName to set.
		 */
		public void setDomainName(String domainName) {
			this.domainName = domainName;
		}
	}
}
