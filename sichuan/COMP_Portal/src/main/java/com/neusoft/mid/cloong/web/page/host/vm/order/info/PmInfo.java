package com.neusoft.mid.cloong.web.page.host.vm.order.info;

import java.io.Serializable;

/**
 * 镜像信息
 * @author <a href="mailto:yuan.x@neusoft.com">Zhang.ge</a>
 * @version $Revision 1.1 $ 2013-1-16 下午04:39:34
 */
public class PmInfo implements Serializable{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 物理机id
     */
    private String pmId;

    /**
     * 物理机名称
     */
    private String pmName;

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}
}
