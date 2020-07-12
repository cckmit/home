/*******************************************************************************
 * @(#)AllResourceAction.java 2014-12-30
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.resView.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.StaCapacityInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-资源视图-所有资源Action
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2014-12-30 下午03:42:16
 */
public class AllResourceAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(AllResourceAction.class);

    /**
     * 要传递的数据
     */
    private String y_axisData;

    /**
     * 总量
     */
    private String resTotal;

    /**
     * 页面传递的资源池ID
     */
    private String resPoolId;

    /**
     * 资源个数实例
     */
    private StaCapacityInfo deviceNum = new StaCapacityInfo();

    /**
     * VCPU实例
     */
    private StaCapacityInfo vcpu = new StaCapacityInfo();

    /**
     * 各分区以及vcpu、内存、磁盘列表
     */
    private List<StaCapacityInfo> partsList = new ArrayList<StaCapacityInfo>();

    /**
     * 内存实例
     */
    private StaCapacityInfo memory = new StaCapacityInfo();

    /**
     * 磁盘实例
     */
    private StaCapacityInfo disk = new StaCapacityInfo();

    /**
     * PM实例
     */
    private StaCapacityInfo pm = new StaCapacityInfo();

    /**
     * 资源池
     */
    private List<StaCapacityInfo> pools = new ArrayList<StaCapacityInfo>();

    /**
     * 所有资源
     */
    public String execute() {
        logger.info("资源视图整体报表统计开始");
        try {
            // 数据库中查询资源池，默认显示第一个
            pools = ibatisDAO.getData("getResPools", null);
            if (resPoolId == null || ("").equals(resPoolId)) {
                resPoolId = pools.get(0).getResPoolId();
            }

            // 查询资源个数
            List<String> countList = ibatisDAO.getData("queryDeviceNum", resPoolId);
            deviceNum.setPmCount(countList.get(0));
            deviceNum.setVmCount(countList.get(1));
            deviceNum.setEbsCount(countList.get(2));
            deviceNum.setMiniPmCount(countList.get(3));
            deviceNum.setMiniPmParCount(countList.get(4));
            deviceNum.setRaidCount(countList.get(5));
            deviceNum.setSwCount(countList.get(6));
            deviceNum.setRtCount(countList.get(7));
            deviceNum.setFwCount(countList.get(8));

            // 各个分区
            partsList = ibatisDAO.getData("queryPartsList", resPoolId);

            // 查询资源池物理机磁盘使用情况
            /*
             * pm.setResPoolId(resPoolId); pm.setResType("3"); pm = (StaCapacityInfo)
             * ibatisDAO.getSingleRecord("queryCapacityforNum", pm);
             */

            for (StaCapacityInfo tmp : partsList) {
                // 查询资源池VCPU最新值
                vcpu.setResPoolId(resPoolId);
                vcpu.setPoolPartId(tmp.getPoolPartId());
                vcpu.setResType("0");
                vcpu = (StaCapacityInfo) ibatisDAO.getSingleRecord("queryCapacityforNum", vcpu);

                // 查询资源池内存使用情况
                memory.setResPoolId(resPoolId);
                memory.setPoolPartId(tmp.getPoolPartId());
                memory.setResType("1");
                memory = (StaCapacityInfo) ibatisDAO.getSingleRecord("queryCapacityforGB", memory);

                // 查询资源池磁盘使用情况
                disk.setResPoolId(resPoolId);
                disk.setPoolPartId(tmp.getPoolPartId());
                disk.setResType("2");
                disk = (StaCapacityInfo) ibatisDAO.getSingleRecord("queryCapacityforDiskGB", disk);
                //这里分别防止vcpu，memory，disk为null
                if(vcpu == null){
                    vcpu = new StaCapacityInfo();
                }else{
                    vcpu = this.getVcpu();
                }if(memory == null){
                    memory = new StaCapacityInfo();
                }else{
                    memory = this.getMemory();
                }if(disk == null){
                    disk = new StaCapacityInfo();
                }else{
                    disk = this.getDisk();
                }
                // 非正式环境下vcpu.getResUsed(),vcpu.getResTotal()有可能获取到null数据,这里防止出错，若获取到null则赋值为0
                tmp.setVcpuResUsed((vcpu.getResUsed() == null ? "0" : vcpu.getResUsed()).toString());
                tmp.setVcpuResTotal((vcpu.getResTotal() == null? "0" : vcpu.getResTotal()).toString());
                // 非正式环境下memory.getResUsed(),memory.getResTotal()有可能获取到null数据,这里防止出错，若获取到null则赋值为0
                tmp.setMemoryResUsed((memory.getResUsed() == null ? "0" : memory.getResUsed())
                        .toString());
                tmp.setMemoryResTotal((memory.getResTotal()== null ? "0" : memory.getResTotal())
                        .toString());
                // 非正式环境下disk.getResUsed(),disk.getResTotal()有可能获取到null数据,这里防止出错，若获取到null则赋值为0
                tmp.setDiskResUsed((disk.getResUsed() == null ? "0" : disk.getResUsed()).toString());
                tmp.setDiskResTotal((disk.getResTotal()== null ? "0" : disk.getResTotal()).toString());
            }
        } catch (SQLException e) {
            logger.error("资源视图整体报表统计，操作数据库异常", e);
            e.printStackTrace();
        }

        logger.info("资源视图整体报表统计结束");

        return ConstantEnum.SUCCESS.toString();
    }

    public String getY_axisData() {
        return y_axisData;
    }

    public void setY_axisData(String y_axisData) {
        this.y_axisData = y_axisData;
    }

    public String getResPoolId() {
        return resPoolId;
    }

    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    public void setResTotal(String resTotal) {
        this.resTotal = resTotal;
    }

    public String getResTotal() {
        return resTotal;
    }

    public StaCapacityInfo getVcpu() {
        return vcpu;
    }

    public void setVcpu(StaCapacityInfo vcpu) {
        this.vcpu = vcpu;
    }

    public StaCapacityInfo getDisk() {
        return disk;
    }

    public void setDisk(StaCapacityInfo disk) {
        this.disk = disk;
    }

    public StaCapacityInfo getMemory() {
        return memory;
    }

    public void setMemory(StaCapacityInfo memory) {
        this.memory = memory;
    }

    public StaCapacityInfo getPm() {
        return pm;
    }

    public void setPm(StaCapacityInfo pm) {
        this.pm = pm;
    }

    public StaCapacityInfo getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(StaCapacityInfo deviceNum) {
        this.deviceNum = deviceNum;
    }

    /**
     * @return the pools
     */
    public List<StaCapacityInfo> getPools() {
        return pools;
    }

    /**
     * @param pools the pools to set
     */
    public void setPools(List<StaCapacityInfo> pools) {
        this.pools = pools;
    }

    public List<StaCapacityInfo> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<StaCapacityInfo> partsList) {
        this.partsList = partsList;
    }

}
