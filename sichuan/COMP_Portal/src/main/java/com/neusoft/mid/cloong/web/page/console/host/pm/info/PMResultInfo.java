package com.neusoft.mid.cloong.web.page.console.host.pm.info;

import java.io.Serializable;
import java.util.List;

import com.neusoft.mid.cloong.host.pm.core.PMStatus;

/**
 * 物理机查询列表实例信息
 * @author <a href="mailto:yuan.x@neusoft.com">yuanxue</a>
 * @version $Revision 1.0 $ 2014-1-13 下午07:04:22
 */
public class PMResultInfo implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 物理机实例id
     */
    private String caseId;

    /**
     * 物理机id
     */
    private String pmId;

    /**
     * 物理机名称
     */
    private String pmName;

    /**
     * 镜像ID
     */
    private String isoId;

    /**
     * 镜像名
     */
    private String isoName;

    /**
     * 数据库返回物理机状态
     */
    private PMStatus status;

    /**
     * 物理机状态名称
     */
    private String statusText;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 订单创建时间
     */
    private String orderCreateTime;

    /**
     * 订单状态
     */
    private String effectiveStatus;

    /**
     * 开始生效时间（计费时间）
     */
    private String effectiveTime;

    /**
     * 时长
     */
    private int lengthTime;

    /**
     * 到期日期
     */
    private String overTime;

    /**
     * 查询去除物理机状态
     */
    private String pmStatus;

    /**
     * 物理机状态
     */
    private String queryStatus;

    /**
     * 资源池ID
     */
    private String resPoolId;

    /**
     * 资源池分区ID
     */
    private String resPoolPartId;

    /**
     * 业务Id
     */
    private String appId;

    /**
     * 业务名称
     */
    private String appName;

    /**
     * 物理机类型
     */
    private String serverType;

    /**
     * 管理及操作IP
     */
    private String operationIP;

    /**
     * IP
     */
    private String ip;

    /**
     * IP集合
     */
    private String ipStr;

    /**
     * 按状态查询虚拟机
     */
    private List<String> statusList;

    /**
     * 当前用户绑定的业务ID
     */
    private List<String> appIdList;

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

    public PMStatus getStatus() {
        return status;
    }

    public void setStatus(PMStatus status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getEffectiveStatus() {
        return effectiveStatus;
    }

    public void setEffectiveStatus(String effectiveStatus) {
        this.effectiveStatus = effectiveStatus;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public int getLengthTime() {
        return lengthTime;
    }

    public void setLengthTime(int lengthTime) {
        this.lengthTime = lengthTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getPmStatus() {
        return pmStatus;
    }

    public void setPmStatus(String pmStatus) {
        this.pmStatus = pmStatus;
    }

    /**
     * 获取operationIP字段数据
     * @return Returns the operationIP.
     */
    public String getOperationIP() {
        return operationIP;
    }

    /**
     * 设置operationIP字段数据
     * @param operationIP The operationIP to set.
     */
    public void setOperationIP(String operationIP) {
        this.operationIP = operationIP;
    }

    public String getIsoId() {
        return isoId;
    }

    public void setIsoId(String isoId) {
        this.isoId = isoId;
    }

    public String getIsoName() {
        return isoName;
    }

    public void setIsoName(String isoName) {
        this.isoName = isoName;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public List<String> getAppIdList() {
        return appIdList;
    }

    public void setAppIdList(List<String> appIdList) {
        this.appIdList = appIdList;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpStr() {
        return ipStr;
    }

    public void setIpStr(String ipStr) {
        this.ipStr = ipStr;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

}
