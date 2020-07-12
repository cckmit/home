package com.neusoft.mid.cloong.rpproxy.interfaces.ebs;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * 创建块存储的参数集合
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月27日 下午1:42:09
 */
public class BsParam implements Serializable {

    /**
     * 存储性能级别 例如：0为SSD盘， 1为FC盘，2为SATA盘，0级别最高、1级次之、以此类推
     */
    private Integer tier;

    /**
     * RAID级别 0 为RAID0，1为RAID1，3为RAID 01 ，4为RAID10， 5为RAID5， 6为RAID6 或RAID-DP
     */
    private Integer raid;

    /**
     * 选用的存储网络类型 0为IP-San 1为FC-San
     */
    private Integer StorageNet = RPPBaseReq.INT_DEFAULT_VAL;

    /**
     * 0：通用块存储（虚拟机和物理机都可以使用）1：虚拟机使用块存储2：物理机使用块存储
     */
    private Integer ResourceType = RPPBaseReq.INT_DEFAULT_VAL;

    /**
     * 条带宽度
     */
    private Integer StripeWidth;

    /**
     * 数据类型#Tier1（Tier2等）
     */
    private String TierRule;

    /**
     * 是否使用动态分级：1为使用，0为不使用
     */
    private Integer TierOpen;

    /**
     * 卷个数，单位为个
     */
    private Integer VolNum;

    /**
     * 卷空间大小，单位为GB
     */
    private Integer VolSize;

    /**
     * 获取tier字段数据
     * @return Returns the tier.
     */
    public Integer getTier() {
        return tier;
    }

    /**
     * 设置tier字段数据
     * @param tier The tier to set.
     */
    public void setTier(Integer tier) {
        this.tier = tier;
    }

    /**
     * 获取raid字段数据
     * @return Returns the raid.
     */
    public Integer getRaid() {
        return raid;
    }

    /**
     * 设置raid字段数据
     * @param raid The raid to set.
     */
    public void setRaid(Integer raid) {
        this.raid = raid;
    }

    /**
     * 获取storageNet字段数据
     * @return Returns the storageNet.
     */
    public Integer getStorageNet() {
        return StorageNet;
    }

    /**
     * 设置storageNet字段数据
     * @param storageNet The storageNet to set.
     */
    public void setStorageNet(Integer storageNet) {
        StorageNet = storageNet;
    }

    /**
     * 获取resourceType字段数据
     * @return Returns the resourceType.
     */
    public Integer getResourceType() {
        return ResourceType;
    }

    /**
     * 设置resourceType字段数据
     * @param resourceType The resourceType to set.
     */
    public void setResourceType(Integer resourceType) {
        ResourceType = resourceType;
    }

    /**
     * 获取stripeWidth字段数据
     * @return Returns the stripeWidth.
     */
    public Integer getStripeWidth() {
        return StripeWidth;
    }

    /**
     * 设置stripeWidth字段数据
     * @param stripeWidth The stripeWidth to set.
     */
    public void setStripeWidth(Integer stripeWidth) {
        StripeWidth = stripeWidth;
    }

    /**
     * 获取tierRule字段数据
     * @return Returns the tierRule.
     */
    public String getTierRule() {
        return TierRule;
    }

    /**
     * 设置tierRule字段数据
     * @param tierRule The tierRule to set.
     */
    public void setTierRule(String tierRule) {
        TierRule = tierRule;
    }

    /**
     * 获取tierOpen字段数据
     * @return Returns the tierOpen.
     */
    public Integer getTierOpen() {
        return TierOpen;
    }

    /**
     * 设置tierOpen字段数据
     * @param tierOpen The tierOpen to set.
     */
    public void setTierOpen(Integer tierOpen) {
        TierOpen = tierOpen;
    }

    /**
     * 获取volNum字段数据
     * @return Returns the volNum.
     */
    public Integer getVolNum() {
        return VolNum;
    }

    /**
     * 设置volNum字段数据
     * @param volNum The volNum to set.
     */
    public void setVolNum(Integer volNum) {
        VolNum = volNum;
    }

    /**
     * 获取volSize字段数据
     * @return Returns the volSize.
     */
    public Integer getVolSize() {
        return VolSize;
    }

    /**
     * 设置volSize字段数据
     * @param volSize The volSize to set.
     */
    public void setVolSize(Integer volSize) {
        VolSize = volSize;
    }

}
