/*******************************************************************************
 * @(#)PMVMTasklet.java 2015年1月13日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job.tasklet;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.dao.VMDiskPerfPo;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.dao.VMPerfPo;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMDISKINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMVMINFO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机性能处理任务
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年1月13日 下午9:27:22
 */
public class PMVMTasklet extends BaseResStatusTasklet<PMVMINFO> {

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(PMVMTasklet.class);

    /**
     * 执行方法
     * @param pmInfo
     * @return
     * @throws TaskletException 任务异常
     * @see com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job.tasklet.BaseResStatusTasklet#execute(java.lang.Object)
     */
    @Override
    public ResStatusTaskRes execute(PMVMINFO pmInfo,String resPoolId) throws TaskletException {

        // 更改为Batch
        List<BatchVO> voList = new ArrayList<BatchVO>();

        try {
            String transformatCollectTime = transformatCollectTime(pmInfo.getStopTime());

            saveVMPerfToDB(pmInfo, voList, transformatCollectTime,resPoolId);
            saveDiskPerfToDB(pmInfo, voList, transformatCollectTime,resPoolId);

            ibatisDAO.updateBatchData(voList);
            logger.info("保存虚拟机[" + pmInfo.getInstanceId() + "]性能数据入库成功,采集时间[" + transformatCollectTime + "]");
        } catch (Exception e) {
            throw new TaskletException(e);
        }

        return new ResStatusTaskRes();
    }    

    /**
     * 保存虚拟机硬盘性能指标到数据库
     * @author fengj<br>
     *         2015年1月14日 上午10:35:29
     * @param pmInfo 虚拟机性能指标
     * @param voList 持久化OUT参数
     * @param transformatCollectTime 采集时间
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void saveDiskPerfToDB(PMVMINFO pmInfo, List<BatchVO> voList, String transformatCollectTime,String resPoolId)
            throws IllegalAccessException, InvocationTargetException {
        List<PMDISKINFO> pmdiskinfoList = pmInfo.getPMDISKINFO();
        for (PMDISKINFO pmdiskinfo : pmdiskinfoList) {
            VMDiskPerfPo vmDiskPerf = new VMDiskPerfPo();
            BeanUtils.copyProperties(vmDiskPerf, pmdiskinfo);
            vmDiskPerf.setCollectTime(transformatCollectTime);
            vmDiskPerf.setVmId(pmInfo.getInstanceId());
            vmDiskPerf.setResPoolId(resPoolId);
            voList.add(new BatchVO(BatchVO.OPERATION_ADD, "insertVMDiskPerformance", vmDiskPerf));
        }
    }

    /**
     * 保存虚拟机性能指标到数据库
     * @author fengj<br>
     *         2015年1月14日 上午10:35:11
     * @param pmInfo 虚拟机性能指标
     * @param voList 持久化OUT参数
     * @param transformatCollectTime 采集时间
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void saveVMPerfToDB(PMVMINFO pmInfo, List<BatchVO> voList, String transformatCollectTime,String resPoolId)
            throws IllegalAccessException, InvocationTargetException {
        if(pmInfo.getMemFree()!=0&&pmInfo.getMemUsedPer()!=0){
            VMPerfPo vmPerf = new VMPerfPo();
            BeanUtils.copyProperties(vmPerf, pmInfo);
            vmPerf.setCollectTime(transformatCollectTime);
            vmPerf.setResPoolId(resPoolId);
            voList.add(new BatchVO(BatchVO.OPERATION_ADD, "insertVMPerformance", vmPerf));
        }
    }

}
