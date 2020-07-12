package com.neusoft.mid.cloong.ebs.core;



/**申请虚拟硬盘订单信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-11 下午04:00:57
 */
public class EBSCreateReq  extends ReqBodyInfo{
    
    /**
     * 规格id
     */
    private String standardId;
    
    /**
     * ebs名称
     */
    private String ebsName;
    
    /**
     * 
     * getStandardId TODO 方法
     * @return TODO
     */
    public String getStandardId() {
        return standardId;
    }
    
    /**
     * 
     * setStandardId TODO 方法
     * @param standardId TODO
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }
    
    /**
     * 
     * getEbsName TODO 方法
     * @return TODO
     */
    public String getEbsName() {
        return ebsName;
    }
    
    /**
     * 
     * setEbsName TODO 方法
     * @param ebsName TODO
     */
    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

}
