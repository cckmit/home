package com.neusoft.mid.cloong.identity.bean;

 /**
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0    2016年11月21日   上午9:14:14
 */
public class PublicIpBean {
            
	        /**
	         * 私网IP.
	         */
			private String ip;
			/**
			 * 公网IP.
			 */
			private String publicIp;
			/**
			 * @return the ip
			 */
			public String getIp() {
				return ip;
			}
			/**
			 * @param ip the ip to set
			 */
			public void setIp(String ip) {
				this.ip = ip;
			}
			/**
			 * @return the publicIp
			 */
			public String getPublicIp() {
				return publicIp;
			}
			/**
			 * @param publicIp the publicIp to set
			 */
			public void setPublicIp(String publicIp) {
				this.publicIp = publicIp;
			}
	
			/* (non-Javadoc)
			* @see java.lang.Object#equals(java.lang.Object)
			*/
			@Override
			public boolean equals(Object obj) {
				if(this == obj){
					return true;
				}
				if(null == obj){
					return false;
				}
				if(obj instanceof PublicIpBean){
					PublicIpBean publicIp = (PublicIpBean) obj;
					return this.ip.equals(publicIp.getIp());
				}
				return false;
			}
}
