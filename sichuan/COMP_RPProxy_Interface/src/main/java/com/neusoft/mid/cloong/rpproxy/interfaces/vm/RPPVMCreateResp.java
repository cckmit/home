/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 虚拟机创建应答类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMCreateResp extends RPPBaseResp implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 发怒及创建的虚拟机列表
     */
    private List<VMInfo> vmInfoList = new ArrayList<RPPVMCreateResp.VMInfo>();

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
     * 获取vmInfoList字段数据
     * @return Returns the vmInfoList.
     */
    public List<VMInfo> getVmInfoList() {
        return vmInfoList;
    }

    /**
     * 设置vmInfoList字段数据
     * @param vmInfoList The vmInfoList to set.
     */
    public void setVmInfoList(List<VMInfo> vmInfoList) {
        this.vmInfoList = vmInfoList;
    }

    public static class VMInfo  implements Serializable{

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = -7904953409052608540L;

        /**
         * 虚拟机ID
         */
        private String vmId;

        /**
         * 虚拟机所属的VLANID列表
         */
        private List<String> vlanId = new ArrayList<String>();

        /**
         * 虚拟机的管理操作IP
         */
        private String operationIP;

        /**
         * 虚拟机名称
         */
        private String vmName;

        /**
         * 虚拟机的状态
         */
        private VMStatus status;

        /**
         * 虚拟机用户名
         */
        private String userName;

        /**
         * 虚拟机登陆密码
         */
        private String passWord;
        
        private String url;

        /**
         * 获取vmId字段数据
         * @return Returns the vmId.
         */
        public String getVmId() {
            return vmId;
        }

        /**
         * 设置vmId字段数据
         * @param vmId The vmId to set.
         */
        public void setVmId(String vmId) {
            this.vmId = vmId;
        }

        /**
         * 获取vlanId字段数据
         * @return Returns the vlanId.
         */
        public List<String> getVlanId() {
            return vlanId;
        }

        /**
         * 设置vlanId字段数据
         * @param vlanId The vlanId to set.
         */
        public void setVlanId(List<String> vlanId) {
            this.vlanId = vlanId;
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

        /**
         * 获取vmName字段数据
         * @return Returns the vmName.
         */
        public String getVmName() {
            return vmName;
        }

        /**
         * 设置vmName字段数据
         * @param vmName The vmName to set.
         */
        public void setVmName(String vmName) {
            this.vmName = vmName;
        }

        /**
         * 获取status字段数据
         * @return Returns the status.
         */
        public VMStatus getStatus() {
            return status;
        }

        /**
         * 设置status字段数据
         * @param status The status to set.
         */
        public void setStatus(VMStatus status) {
            this.status = status;
        }

        /**
         * 获取userName字段数据
         * @return Returns the userName.
         */
        public String getUserName() {
            return userName;
        }

        /**
         * 设置userName字段数据
         * @param userName The userName to set.
         */
        public void setUserName(String userName) {
            this.userName = userName;
        }

        /**
         * 获取passWord字段数据
         * @return Returns the passWord.
         */
        public String getPassWord() {
            return passWord;
        }

        /**
         * 设置passWord字段数据
         * @param passWord The passWord to set.
         */
        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        /**
         * 获取url字段数据
         * @return Returns the url.
         */
        public String getUrl() {
            return url;
        }

        /**
         * 设置url字段数据
         * @param url The url to set.
         */
        public void setUrl(String url) {
            this.url = url;
        }

        
    }
}
