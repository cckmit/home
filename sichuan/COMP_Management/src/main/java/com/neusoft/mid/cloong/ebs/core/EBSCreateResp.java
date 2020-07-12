package com.neusoft.mid.cloong.ebs.core;



/**
 * 创建虚拟硬盘接口响应
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-25 下午01:52:28
 */
public class EBSCreateResp extends RespBodyInfo {
    
    /**
     * 实例id
     */
    private String caseId;

    /**
     * ebs ID
     */
    private String ebsId;
    
    /**
     * ebs Name
     */
    private String ebsName;
    
    /**
     * 磁盘大小
     */
    private String discSize;
    
    /**
     * 
     * getCaseId TODO 方法
     * @return TODO
     */
    public String getCaseId() {
        return caseId;
    }
    
    /**
     * 
     * setCaseId TODO 方法
     * @param caseId TODO
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
    
    /**
     * 
     * getEbsId TODO 方法
     * @return TODO
     */
    public String getEbsId() {
        return ebsId;
    }
    
    /**
     * 
     * setEbsId TODO 方法
     * @param ebsId TODO
     */
    public void setEbsId(String ebsId) {
        this.ebsId = ebsId;
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
    
    /**
     * 
     * getDiscSize TODO 方法
     * @return TODO
     */
    public String getDiscSize() {
        return discSize;
    }
    
    /**
     * 
     * setDiscSize TODO 方法
     * @param discSize TODO
     */
    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

}
