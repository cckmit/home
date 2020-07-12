package com.neusoft.mid.cloong.H3C.vlan;

public class H3cTokenReq {

	/***
	 * sdn Token bean
	 */
	private SdnTokenReqBean login;
	
	/**
	 * 获取login字段数据
	 * @return Returns the login.
	 */
	public SdnTokenReqBean getLogin() {
		return login;
	}

	/**
	 * 设置login字段数据
	 * @param login The login to set.
	 */
	public void setLogin(SdnTokenReqBean login) {
		this.login = login;
	}

	public class SdnTokenReqBean {
		
		/***
		 * 用户名称。String类型，必选，无缺省值，1~30位中英文、数字、字母、下划线。
		 */
		private String user;
		
		/***
		 * 用户密码。String类型，必选，无缺省值，8~30位英文、数字、特殊字符。
		 */
		private String password;
		
		/***
		 * 	域名。String类型，可选，无缺省值，目前不支持。
		 */
		private String  domain = "sdn";

		/**
		 * 获取user字段数据
		 * @return Returns the user.
		 */
		public String getUser() {
			return user;
		}

		/**
		 * 设置user字段数据
		 * @param user The user to set.
		 */
		public void setUser(String user) {
			this.user = user;
		}

		/**
		 * 获取password字段数据
		 * @return Returns the password.
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * 设置password字段数据
		 * @param password The password to set.
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * 获取domain字段数据
		 * @return Returns the domain.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * 设置domain字段数据
		 * @param domain The domain to set.
		 */
		public void setDomain(String domain) {
			this.domain = domain;
		}
		
	}
}
