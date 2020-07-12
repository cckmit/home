package com.neusoft.mid.cloong.web.page;

/**
 * 以变量形式记录资源类型
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.1 $ 2013-1-10 下午04:35:13
 */
public enum ResourceTypeEnum {
    /**
     * 虚拟机资源
     */
    VM("VM","VMP"),
   
    /**
     * 虚拟机资源
     */
    VMB("VMB","VMBP"),
    /**
     * 物理服务器资源
     */
    PH("PH","PHP"),
    /**
     * 虚拟机备份资源
     */
    BK("BK","BKP"),
    /**
     * 云硬盘资源
     */
    BS("BS","BSP"),
    /**
     * 对象存储资源
     */
    OS("OS","OSP"),
    /**
     * 公网IP地址资源
     */
    IP("IP","IPP"),
    /**
     * 带宽资源
     */
    BW("BW","BWP"),
    /**
     * 安全组资源
     */
    SG("SG","SGP"),
    /**
     * 云监控资源
     */
    CM("CM","CMP"),
    /**
     * 虚拟机快照
     */
    SN("SN","SNP"),
    /**
     * 虚拟机克隆
     */
    GH("GH","GHP"),
    
    /**
     * 负载均衡服务
     */
    LB("LB","LBP"),
    
    /**
     * 虚拟防火墙
     */
    FW("FW","FWP"),
    
    /**
     * 分布式文件存储
     */
    FS("FS","FSP"),
    
    VL("VL" , "VLP"),
    
    VT("VT" , "VTP");
    
    private String value;
    
    /**
     * 订单父ID类型
     */
    private String parentCode;

    /**
     * @deprecated
     */
    public static final int INT_1024 = 1024;

    /**
     * @deprecated
     */

    ResourceTypeEnum(String value,String parentCode) {
        this.value = value;
        this.parentCode = parentCode;
    }

    public String toString() {
        return value;
    }

    public String getName() {
        return "my name :" + value;
    }

    /**
     * 获取parentCode字段数据
     * @return Returns the parentCode.
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 设置parentCode字段数据
     * @param parentCode The parentCode to set.
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    
}
