/*******************************************************************************
 * @(#)CreateVMReq.java 2015年2月16日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.rpproxy.interfaces.vm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

/**
 * 虚拟机备份列表查询应答
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年2月16日 上午11:08:15
 */
public class RPPVMBakListQueryResp extends RPPBaseResp implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4592586402763320376L;

    /**
     * 故障描述
     */
    private String faultstring;

    /**
     * 虚拟机备份列表
     */
    private List<VMBakInfo> backupList = new ArrayList<VMBakInfo>();

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
     * 获取backupList字段数据
     * @return Returns the backupList.
     */
    public List<VMBakInfo> getBackupList() {
        return backupList;
    }

    /**
     * 设置backupList字段数据
     * @param backupList The backupList to set.
     */
    public void setBackupList(List<VMBakInfo> backupList) {
        this.backupList = backupList;
    }

    /**
     * 虚拟机任务下的备份信
     * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
     * @version $Revision 1.0 $ 2015年3月2日 下午2:51:45
     */
    public static class VMBakInfo implements Serializable {

        /**
         * 备份的内部编码.恢复一个备份时，需要指定该编码
         */
        private String vmBakInternalId;

        /**
         * 备份生成的时间
         */
        private Date generationTime;

        /**
         * 备份的容量大小，单位MB
         */
        private Integer storeSize;

        /**
         * 获取vmBakInternalId字段数据
         * @return Returns the vmBakInternalId.
         */
        public String getVmBakInternalId() {
            return vmBakInternalId;
        }

        /**
         * 设置vmBakInternalId字段数据
         * @param vmBakInternalId The vmBakInternalId to set.
         */
        public void setVmBakInternalId(String vmBakInternalId) {
            this.vmBakInternalId = vmBakInternalId;
        }

        /**
         * 获取generationTime字段数据
         * @return Returns the generationTime.
         */
        public Date getGenerationTime() {
            return generationTime;
        }

        /**
         * 设置generationTime字段数据
         * @param generationTime The generationTime to set.
         */
        public void setGenerationTime(Date generationTime) {
            this.generationTime = generationTime;
        }

        /**
         * 获取storeSize字段数据
         * @return Returns the storeSize.
         */
        public Integer getStoreSize() {
            return storeSize;
        }

        /**
         * 设置storeSize字段数据
         * @param storeSize The storeSize to set.
         */
        public void setStoreSize(Integer storeSize) {
            this.storeSize = storeSize;
        }

    }

}
