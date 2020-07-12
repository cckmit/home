/*******************************************************************************
 * @(#)BlockInfo.java 2013-1-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.block;

/**
 * 虚拟机挂载的云硬盘信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-1-7 下午03:50:00
 */
public class BlockInfo {
    /**
     * 云硬盘ID
     */
    private String id;

    /**
     * 云硬盘名称
     */
    private String name;

    /**
     * 挂载点
     */
    private String mountPoint;

    /**
     * 容量
     */
    private String size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    public void setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("云硬盘ID为：").append(this.id).append("\n");
        sb.append("云硬盘名称为：").append(this.name).append("\n");
        sb.append("云硬盘挂载点为：").append(this.mountPoint).append("\n");
        sb.append("云机容量为：").append(this.size).append("\n");
        return sb.toString();
    }
}
