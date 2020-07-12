/*******************************************************************************
 * @(#)EBSResCountTasklet.java 2014年2月12日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.tasklet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean.EBSResCountBean;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.tasklet.bean.ResCountTaskRes;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.StorageServiceConsume;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.StorageServiceConsumeList;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 块存储计量上报Tasklet
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月12日 下午5:02:23
 */
public class EBSResCountTasklet extends BaseResCountTasklet<StorageServiceConsumeList, ResCountTaskRes> {

    /**
     * 当前资源名称
     */
    public static final String RES_NAME = "块存储";

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(EBSResCountTasklet.class);

    /**
     * execute task执行方法
     * @param resourceStatusSet 计量信息List
     * @param startTime 周期开始时间
     * @param endTime 周期结束时间
     * @param periodTime 周期时间
     * @param resPoolId 资源池ID
     * @param resPoolPartId 资源池分区ID
     * @return 返回有当前周期计量信息的物理机ID
     * @throws TaskletException     任务执行失败异常
     */
    @Override
    public ResCountTaskRes execute(StorageServiceConsumeList resourceStatusSet,
            final String startTime, final String endTime, final int periodTime,
            final String resPoolId, final String resPoolPartId) throws TaskletException {
        ResCountTaskRes res = new ResCountTaskRes();
        List<String> noExistEBSIds = null;
        List<String> existCountEBSIds = null;
        try {
            List<StorageServiceConsume> resConsumeList = resourceStatusSet
                    .getStorageServiceConsume();

            noExistEBSIds = queryNoExistEBSId(resPoolId, resPoolPartId, resConsumeList);
            if (null != noExistEBSIds && noExistEBSIds.size() != 0) {
                res.setNoExistInCompResId(noExistEBSIds);
                logger.info("某些计量信息对应的" + RES_NAME + "实例在运营系统中不存在,不做入库处理：" + noExistEBSIds);
            }
            if (null != noExistEBSIds && null != resConsumeList
                    && noExistEBSIds.size() == resConsumeList.size()) {
                logger.info("上报的所有" + RES_NAME + "实例在运营系统中都不存在,不做任何入库处理！");
                return res;
            }

            existCountEBSIds = queryExistCountEBSId(resPoolId, resPoolPartId, startTime, endTime,
                    resConsumeList);
            if (existCountEBSIds != null && existCountEBSIds.size() != 0) {
                res.setExistCountInCompResId(existCountEBSIds);
                logger.info("本周期计量信息已经在系统中存在的" + RES_NAME + "ID:" + existCountEBSIds);
            }

            if (null == existCountEBSIds || resConsumeList == null){
                throw new TaskletException("处理上报计量信息是发生错误异常");
            }
            
            if (existCountEBSIds.size() == resConsumeList
                    .size()
                    || (null != noExistEBSIds && noExistEBSIds.size() == resConsumeList
                            .size())) {
                logger.info("上报的周期计量信息在运营平台全部都存在，不做任何入库处理！");
            } else {
                saveCountToDB(startTime, endTime, periodTime, resPoolId, resPoolPartId,
                        existCountEBSIds, resConsumeList);
                logger.info(RES_NAME + "计量信息保存入库成功！");
            }

        } catch (SQLException e) {
            logger.error("保存" + RES_NAME + "计量信息失败！", e);
        }
        return res;

    }

    /**
     * saveCountToDB 保存数据到数据库
     * @param startTime 周期开始时间
     * @param endTime 周期结束时间
     * @param periodTime 周期间隔
     * @param resPoolId 资源池ID
     * @param resPoolPartId 资源池分区ID
     * @param existCountEBSIds 已经存在的ESBIDS
     * @param consumeList 计量信息
     * @throws SQLException TODO 请在每个@param,@return,@throws行尾补充对应的简要说明
     */
    private void saveCountToDB(final String startTime, final String endTime, final int periodTime,
            final String resPoolId, final String resPoolPartId, List<String> existCountEBSIds,
            List<StorageServiceConsume> consumeList) throws SQLException {
        // 采用HashSet提高比较效率
        Set s = new HashSet(existCountEBSIds);
        List<BatchVO> voList = new ArrayList<BatchVO>();
        for (final StorageServiceConsume consume : consumeList) {

            // 如果是已经在周期内存在主机，则不予保存
            if (s.contains(consume.getNetWorkResouseID())) {
                continue;
            }
            BatchVO vo = this.genEBSCountVO(consume, endTime, startTime, periodTime, resPoolId,
                    resPoolPartId);
            voList.add(vo);
        }

        this.ibatisDAO.updateBatchData(voList);
    }

    /**
     * genEBSCountVO 生成入库的计量信息Bean
     * @param consume 计量Bean
     * @param endTime 周期结束时间
     * @param startTime 周期开始时间
     * @param periodTime 周期长度
     * @param resPoolId 资源池Id
     * @param resPoolPartId 资源池分区Id
     * @return 入库的计量信息Bean
     */
    private BatchVO genEBSCountVO(final StorageServiceConsume consume, final String endTime,
            final String startTime, final int periodTime, final String resPoolId,
            final String resPoolPartId) {
        EBSResCountBean resCount = new EBSResCountBean() {
            {
                this.setEbsId(consume.getNetWorkResouseID());
                this.setEndTime(endTime);
                this.setStartTime(startTime);
                this.setPeriodTime(periodTime);
                this.setSizeUsed(consume.getResourceSizeUsed());
                this.setResPoolId(resPoolId);
                this.setResPoolPartId(resPoolPartId);
                this.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            }
        };
        BatchVO vo = new BatchVO("ADD", "insertEBSCount", resCount);
        return vo;
    }

    /**
     * queryExistCountEBSId 生成入库的计量信息Bean
     * @param poolId 资源池ID
     * @param partId 分区ID
     * @param endTime 周期结束时间
     * @param startTime 周期开始时间
     * @param consumeList 物理机计量信息List
     * @return 入库的计量信息Bean
     * @exception SQLException 数据库异常
     */
    private List<String> queryExistCountEBSId(final String poolId, final String partId,
            final String startTime, final String endTime, List<StorageServiceConsume> consumeList)
            throws SQLException {
        List<String> ebsIds;
        Map<String, Object> daoCountQuery = new HashMap<String, Object>();
        daoCountQuery.put("poolId", poolId);
        daoCountQuery.put("partId", partId);
        daoCountQuery.put("startTime", startTime);
        daoCountQuery.put("endTime", endTime);
        daoCountQuery.put("consume", consumeList);
        ebsIds = ibatisDAO.getData("getCountEBSById", daoCountQuery);
        return ebsIds;
    }

    /**
     * queryNoExistEBSId 获取运营平台不存在的物理机ID
     * @param resPoolId 资源池ID
     * @param resPoolPartId 资源池分区ID
     * @param consumeList 物理机计量信息List
     * @return 不存在的EBSID
     * @exception SQLException 数据库异常
     */
    private List<String> queryNoExistEBSId(String resPoolId, String resPoolPartId,
            List<StorageServiceConsume> consumeList) throws SQLException {
        List<String> ebsIds;
        Map<String, Object> daoCountQuery = new HashMap<String, Object>();
        daoCountQuery.put("resPoolId", resPoolId);
        daoCountQuery.put("resPoolPartId", resPoolPartId);
        daoCountQuery.put("consume", consumeList);
        ebsIds = ibatisDAO.getData("getExistEBSId", daoCountQuery);
        return ebsIds;
    }

}
