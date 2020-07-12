package com.neusoft.mid.cloong.host.pm;

import com.neusoft.mid.cloong.info.ReqBodyInfo;


/**申请物理机订单信息
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.1 $ 2014-5-28 下午04:00:57
 */
public class PMCustomCreateReq  extends ReqBodyInfo{

    /**
     * 规格id
     */
    private String standardId;

    /**
     * 镜像id
     */
    private String osId;

    /**
     * 私网ip
     */
    private String privateIp;
    
    /**
     * 物理机所在物理机id
     */
    private String serverId;
    
    /**
     * 物理机名称
     */
    private String pmName;
    
    
    public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}

	public String getPrivateIp() {
		return privateIp;
	}

	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public String getPmBackupId() {
		return pmBackupId;
	}

	public void setPmBackupId(String pmBackupId) {
		this.pmBackupId = pmBackupId;
	}

	/**
     * 克隆时，备份ID
     */
    private String pmBackupId;

    
}
