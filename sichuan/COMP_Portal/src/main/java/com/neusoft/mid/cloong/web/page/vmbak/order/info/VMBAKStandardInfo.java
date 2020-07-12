/*******************************************************************************
 * @(#)VMBAKStandardInfo.java 2014年1月21日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.vmbak.order.info;

import java.io.Serializable;

/**
 * 虚拟机备份规格实例
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2014年1月21日 上午11:27:43
 */
public class VMBAKStandardInfo implements Serializable{
    
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 规格名称
     */
    private String standardName;

   /**
    * 规格id
    */
    private String standardId;

    /**
     * 空间大小
     */
    private long spaceSize;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建shij
     */
    private String createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更改时间
     */
    private String updateTime;

    /**
     * 更改人
     */
    private String updateUser;

    /**
     * 
     * getStandardName TODO 方法
     * @return TODO
     */
    public String getStandardName() {
        return standardName;
    }

    /**
     * 
     * setStandardName TODO 方法
     * @param standardName TODO
     */
    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

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
     * getSpaceSize TODO 方法
     * @return TODO
     */
    public long getSpaceSize() {
        return spaceSize;
    }

    /**
     * 
     * setSpaceSize TODO 方法
     * @param spaceSize TODO
     */
    public void setSpaceSize(long spaceSize) {
        this.spaceSize = spaceSize;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 
     * setDescription TODO 方法
     * @param description TODO
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * getStatus TODO 方法
     * @return TODO
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * setStatus TODO 方法
     * @param status TODO
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * getCreateTime TODO 方法
     * @return TODO
     */
    public String getCreateTime() {
        return createTime;
    }
    
    /**
     * 
     * getCreateTimeStr 获取创建时间字符串
     * @return 时间格式
     */
    public String getCreateTimeStr() {
        if (createTime == null ||createTime.length() < 14){
            return "";
        }
        return createTime.substring(0, 4) + "-" + createTime.substring(4, 6) + "-"
                + createTime.substring(6, 8) + " " + createTime.substring(8, 10) + ":"
                + createTime.substring(10, 12) + ":" + createTime.substring(12, 14);
    }

    /**
     * 
     * setCreateTime TODO 方法
     * @param createTime TODO
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * getCreateUser TODO 方法
     * @return TODO
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * setCreateUser TODO 方法
     * @param createUser TODO
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * getUpdateTime TODO 方法
     * @return TODO
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * setUpdateTime TODO 方法
     * @param updateTime TODO
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * getUpdateUser TODO 方法
     * @return TODO
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * setUpdateUser TODO 方法
     * @param updateUser TODO
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
