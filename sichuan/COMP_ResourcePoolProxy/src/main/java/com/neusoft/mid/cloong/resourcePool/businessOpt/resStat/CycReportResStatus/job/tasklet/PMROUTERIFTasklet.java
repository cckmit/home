/*******************************************************************************
 * @(#)PMROUTERIFTasklet.java 2015年11月4日
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
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.dao.RouterIFPerfPo;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMROUTERIFINFO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 路由器端口性能处理任务
 * @author <a href="mailto:wengcz@neusoft.com">wengcz</a>
 * @version $Revision 1.0 $ 2015年11月4日 下午9:27:22
 */
public class PMROUTERIFTasklet extends BaseResStatusTasklet<PMROUTERIFINFO> {

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(PMROUTERIFTasklet.class);

    /**
     * 执行方法
     * @param routerIFInfo
     * @return
     * @throws TaskletException 任务异常
     * @see com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job.tasklet.BaseResStatusTasklet#execute(java.lang.Object)
     */
    @Override
    public ResStatusTaskRes execute(PMROUTERIFINFO routerIFInfo,String resPoolId) throws TaskletException {

        // 更改为Batch
        List<BatchVO> voList = new ArrayList<BatchVO>();

        try {
            String transformatCollectTime = transformatCollectTime(routerIFInfo.getStopTime());

            saveRouterIFPerfToDB(routerIFInfo, voList, transformatCollectTime,resPoolId);

            ibatisDAO.updateBatchData(voList);
            logger.info("保存路由器[" + routerIFInfo.getParentId() + "]端口[" + routerIFInfo.getEqpId() + "]性能数据入库成功,采集时间[" + transformatCollectTime + "]");
        } catch (Exception e) {
            throw new TaskletException(e);
        }

        return new ResStatusTaskRes();
    }


    /**
     * 保存路由器端口性能指标到数据库
     * @author wengcz<br>
     *         2015年11月4日 上午10:35:11
     * @param routerIFInfo 路由器端口性能指标
     * @param voList 持久化OUT参数
     * @param transformatCollectTime 采集时间
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void saveRouterIFPerfToDB(PMROUTERIFINFO routerIFInfo, List<BatchVO> voList, String transformatCollectTime,String resPoolId)
            throws IllegalAccessException, InvocationTargetException {
    	RouterIFPerfPo routerIFPerf = new RouterIFPerfPo();
        BeanUtils.copyProperties(routerIFPerf, routerIFInfo);
        routerIFPerf.setCollectTime(transformatCollectTime);
        routerIFPerf.setResPoolId(resPoolId);
        voList.add(new BatchVO(BatchVO.OPERATION_ADD, "insertRouterIFPerformance", routerIFPerf));
    }

}
