/**
 * 
 */
package com.neusoft.mid.comp.boss.server.ws.msg.OrderResource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Administrator
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourceInfoRespType", propOrder = { "res_code", "res_desc" })
public class ResourceInfoResp {

	@XmlElement(name = "res_code")
	public String res_code;
	
	@XmlElement(name = "res_desc")
	public String res_desc;
	
	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_desc() {
		return res_desc;
	}

	public void setRes_desc(String res_desc) {
		this.res_desc = res_desc;
	}

}
