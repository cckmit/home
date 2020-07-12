package com.neusoft.mid.cloong.vault.beans.inside;

/*import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;*/

import java.io.Serializable;

/*@ApiModel(value = "VaultFilterIpModel", description = "金库ip过滤数据模型")
@TableName("vault_filter_ip_t")*/
public class VaultFilterIpBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*@ApiModelProperty(value = "需要过滤的ip")
	@JsonProperty(value = "filter_ip")
	@TableField("filter_ip")*/
	private String filterIp;

	public String getFilterIp() {
		return filterIp;
	}

	public void setFilterIp(String filterIp) {
		this.filterIp = filterIp;
	}

}
