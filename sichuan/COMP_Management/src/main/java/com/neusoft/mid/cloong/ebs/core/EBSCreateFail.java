package com.neusoft.mid.cloong.ebs.core;

/**
 * 虚拟硬盘创建失败信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午09:12:23
 */
public class EBSCreateFail {
    
    /**
     * 失败原因
     */
    private String failCause;
    
    /**
     * 失败码
     */
    private String failCode;
    
    /**
     * 资源池id
     */
    private String resPoolId;
    
    /**
     * 资源池分区id
     */
    private String resPoolPartId;
    
    /**
     * 规格id
     */
    private String standardId;
    
    /**
     * ebs名字
     */
    private String ebsName;
    
    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 
     * getFailCause 失败原因
     * @return 失败原因
     */
     
    public String getFailCause() {
        return failCause;
    }
    
    /**
     * setFailCause 失败原因
     * @param failCause 失败原因
     */
    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }
    
    /**
     * getFailCause 失败码
     * @return 失败码
     */
    public String getFailCode() {
        return failCode;
    }
    
    /**
     * setFailCode 失败码
     * @param failCode 失败码
     */
    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }
    
    /**
     * getResPoolId 资源池id
     * @return 资源池id
     */
    public String getResPoolId() {
        return resPoolId;
    }
    
    /**
     * setResPoolId 资源池id
     * @param resPoolId 资源池id
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }
    
    /**
     * getResPoolPartId 资源池分区id
     * @return 资源池分区id
     */
    public String getResPoolPartId() {
        return resPoolPartId;
    }
    
    /**
     * setResPoolPartId 资源池分区id
     * @param resPoolPartId 资源池分区id
     */
    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }
    
    /**
     * getStandardId 规格id
     * @return 规格id
     */
    public String getStandardId() {
        return standardId;
    }
    
    /**
     * setStandardId 规格id
     * @param standardId 规格id
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }
    
    /**
     * getCreateUser 创建人
     * @return 创建人
     */
    public String getCreateUser() {
        return createUser;
    }
    
    /**
     * setCreateUser 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    /**
     * getCreateTime 创建时间
     * @return 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }
    
    /**
     * setCreateTime 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    /**
     * getEbsName ebs名字
     * @return ebs名字
     */
    public String getEbsName() {
        return ebsName;
    }

    /**
     * setEbsName ebs名字
     * @param ebsName ebs名字
     */
    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }
    
    /**
     * 
     * toString 信息字符串化
     * @return  信息字符串化
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("失败原因为：").append(this.failCause).append("\n");
        sb.append("失败响应码为：").append(this.failCode).append("\n");
        sb.append("资源池ID为：").append(this.resPoolId).append("\n");
        sb.append("资源池分区ID为：").append(this.resPoolPartId).append("\n");
        sb.append("规格ID为：").append(this.standardId).append("\n");
        sb.append("虚拟硬盘名称为：").append(this.ebsName).append("\n");
        sb.append("创建人为：").append(this.createUser).append("\n");
        sb.append("创建时间为：").append(this.createTime).append("\n");
        return sb.toString();
    }

}
