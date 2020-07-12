package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 修改块存储请求类
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月26日 下午3:08:11
 */
public class RPPEBSModifyReq extends RPPBaseReq {

    private static final long serialVersionUID = -65647648013292718L;

    /**
     * 虚拟硬盘编码
     */
    private String ebsId;

    /**
     * 虚拟硬盘名称
     */
    private String ebsName;

    /**
     * 修改后的虚拟机大小
     */
    private Integer diskSize = -1;

    /**
     * 获取ebsId字段数据
     * @return Returns the ebsId.
     */
    public String getEbsId() {
        return ebsId;
    }

    /**
     * 设置ebsId字段数据
     * @param ebsId The ebsId to set.
     */
    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
    }

    /**
     * 获取diskSize字段数据
     * @return Returns the diskSize.
     */
    public Integer getDiskSize() {
        return diskSize;
    }

    /**
     * 设置diskSize字段数据
     * @param diskSize The diskSize to set.
     */
    public void setDiskSize(Integer diskSize) {
        this.diskSize = diskSize;
    }

    /**
     * 获取ebsName字段数据
     * @return Returns the ebsName.
     */
    public String getEbsName() {
        return ebsName;
    }

    /**
     * 设置ebsName字段数据
     * @param ebsName The ebsName to set.
     */
    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

}
