package com.neusoft.mid.cloong.ebs;

/**
 * 虚拟硬盘创建失败信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-2-28 下午09:12:23
 */
public class EBSCreateFail {

    private String failCause;

    private String failCode;

    private String resPoolId;

    private String resPoolPartId;

    private String standardId;
    
    private String ebsName;
    
    private String createUser;

    private String createTime;

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public String getResPoolPartId() {
        return resPoolPartId;
    }

    public void setResPoolPartId(String resPoolPartId) {
        this.resPoolPartId = resPoolPartId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEbsName() {
        return ebsName;
    }

    public void setEbsName(String ebsName) {
        this.ebsName = ebsName;
    }

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
