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
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.dao.RAIDPerfPo;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMRAIDINFO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 阵列性能处理任务
 * @author <a href="mailto:li-lei@neusoft.com">li-lei</a>
 * @version $Revision 1.0 $ 2015年11月3日 下午7:27:22
 */
public class PMRAIDTasklet extends BaseResStatusTasklet<PMRAIDINFO> {

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(PMRAIDTasklet.class);

    /**
     * 执行方法
     * @param pmInfo
     * @return
     * @throws TaskletException 任务异常
     * @see com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job.tasklet.BaseResStatusTasklet#execute(java.lang.Object)
     */
    @Override
    public ResStatusTaskRes execute(PMRAIDINFO pmInfo,String resPoolId) throws TaskletException {

        // 更改为Batch
        List<BatchVO> voList = new ArrayList<BatchVO>();

        try {
            String transformatCollectTime = transformatCollectTime(pmInfo.getStopTime());

            saveRaidPerfToDB(pmInfo, voList, transformatCollectTime,resPoolId);

            ibatisDAO.updateBatchData(voList);
            logger.info("保存阵列[" + pmInfo.getEqpId() + "]性能数据入库成功,采集时间[" + transformatCollectTime + "]");
        } catch (Exception e) {
            throw new TaskletException(e);
        }

        return new ResStatusTaskRes();
    }

    /**
     * 保存阵列性能指标到数据库
     * @author li-lei<br>
     *         2015年11月4日 上午10:35:29
     * @param pmInfo 阵列性能指标
     * @param voList 持久化OUT参数
     * @param transformatCollectTime 采集时间
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void saveRaidPerfToDB(PMRAIDINFO pmInfo, List<BatchVO> voList, String transformatCollectTime,String resPoolId)
            throws IllegalAccessException, InvocationTargetException {
        RAIDPerfPo raidPerf = new RAIDPerfPo();
        BeanUtils.copyProperties(raidPerf, pmInfo);
        raidPerf.setCollectTime(transformatCollectTime);
        raidPerf.setResPoolId(resPoolId);
        voList.add(new BatchVO(BatchVO.OPERATION_ADD, "insertRAIDPerformance", raidPerf));
    }

}
