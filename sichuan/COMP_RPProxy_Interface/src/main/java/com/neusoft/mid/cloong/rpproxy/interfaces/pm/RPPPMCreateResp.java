/*******************************************************************************
 * @(#)CreatePMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.pm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 物理机创建应答类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPPMCreateResp extends RPPBaseResp implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 发怒及创建的物理机列表
     */
    private List<PMInfo> pmInfoList = new ArrayList<RPPPMCreateResp.PMInfo>();

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
     * 获取pmInfoList字段数据
     * @return Returns the pmInfoList.
     */
    public List<PMInfo> getPmInfoList() {
        return pmInfoList;
    }

    /**
     * 设置pmInfoList字段数据
     * @param pmInfoList The pmInfoList to set.
     */
    public void setPmInfoList(List<PMInfo> pmInfoList) {
        this.pmInfoList = pmInfoList;
    }

    public static class PMInfo  implements Serializable{

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = -7904953409052608540L;

        /**
         * 物理机ID
         */
        private String pmId;

        /**
         * 物理机的管理操作IP
         */
        private String operationIP;

        /**
         * 物理机的状态
         */
        private PMStatus status;

        /**
         * 物理机用户名
         */
        private String userName;

        /**
         * 物理机登陆密码
         */
        private String passWord;
        

        /**
         * 获取pmId字段数据
         * @return Returns the pmId.
         */
        public String getPmId() {
            return pmId;
        }

        /**
         * 设置pmId字段数据
         * @param pmId The pmId to set.
         */
        public void setPmId(String pmId) {
            this.pmId = pmId;
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
         * 获取status字段数据
         * @return Returns the status.
         */
        public PMStatus getStatus() {
            return status;
        }

        /**
         * 设置status字段数据
         * @param status The status to set.
         */
        public void setStatus(PMStatus status) {
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
    }
}
