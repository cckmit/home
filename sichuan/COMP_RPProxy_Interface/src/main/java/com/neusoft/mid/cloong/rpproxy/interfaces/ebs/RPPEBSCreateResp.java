package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 创建虚拟硬盘接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class RPPEBSCreateResp extends RPPBaseResp implements Serializable {

    private static final long serialVersionUID = 1635945078071367626L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * EBSming
     */
    private List<String> BSIDs = new ArrayList<String>();

    /**
     * 获取faultstring字段数据
     * @return Returns the faultstring.
     */
    public String getFaultstring() {
        return faultstring;
    }

    /**
     * 设置faultstring字段数据
     * @param faultstring The faultstring to set.
     */
    public void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

    /**
     * 获取bSIDs字段数据
     * @return Returns the bSIDs.
     */
    public List<String> getBSIDs() {
        return BSIDs;
    }

    /**
     * 设置bSIDs字段数据
     * @param bSIDs The bSIDs to set.
     */
    public void setBSIDs(List<String> bSIDs) {
        BSIDs = bSIDs;
    }

}
