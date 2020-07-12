/*******************************************************************************
 * @(#)VMResCountTasklet.java 2014年2月12日
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
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean.VMResCountBean;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.tasklet.bean.ResCountTaskRes;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.VMHostConsume;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.VMHostConsumeList;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机计量上报Tasklet
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月12日 下午5:02:23
 */
public class VMResCountTasklet extends BaseResCountTasklet<VMHostConsumeList, ResCountTaskRes> {

    /**
     * 当前资源名称
     */
    public static final String RES_NAME = "虚拟机";

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(VMResCountTasklet.class);

    /**
     * execute 保存vm计量信息到数据库
     * @param resourceStatusSet 计量信息List
     * @param startTime 周期开始时间
     * @param endTime 周期结束时间
     * @param periodTime 周期时间
     * @param resPoolId 资源池ID
     * @param resPoolPartId 资源池分区ID
     * @return 返回有当前周期计量信息的物理机ID
     * @throws TaskletException 任务执行失败异常
     */
    @Override
    public ResCountTaskRes execute(VMHostConsumeList resourceStatusSet, final String startTime,
            final String endTime, final int periodTime, final String resPoolId,
            final String resPoolPartId) throws TaskletException {
        ResCountTaskRes res = new ResCountTaskRes();
        List<String> noExistVmIds = null;
        List<String> existCountVmIds = null;
        try {
            List<VMHostConsume> resConsumeList = resourceStatusSet.getVMHostConsume();

            noExistVmIds = queryNoExistVMId(resPoolId, resPoolPartId, resConsumeList);
            if (null != noExistVmIds && noExistVmIds.size() != 0) {
                res.setNoExistInCompResId(noExistVmIds);
                logger.info("某些计量信息对应的" + RES_NAME + "实例不存在,不做入库处理：" + noExistVmIds);
            }
            if (null != noExistVmIds && null != resConsumeList
                    && noExistVmIds.size() == resConsumeList.size()) {
                logger.info("上报的所有" + RES_NAME + "实例在运营系统中都不存在,不做任何入库处理！");
                return res;
            }

            existCountVmIds = queryExistCountVMId(resPoolId, resPoolPartId, startTime, endTime,
                    resConsumeList);
            if (null != existCountVmIds && existCountVmIds.size() != 0) {
                res.setExistCountInCompResId(existCountVmIds);
                logger.info("本周期计量信息已经在系统中存在的" + RES_NAME + "ID:" + existCountVmIds);
            }
            
            if (null == existCountVmIds || null == resConsumeList){
                throw new TaskletException("处理上报计量信息是发生错误异常");
            }

            if (existCountVmIds.size() == resConsumeList.size()) {
                logger.info("上报的周期计量信息在运营平台全部都存在，不做任何入库处理！");
            } else {
                saveCountToDB(startTime, endTime, periodTime, resPoolId, resPoolPartId,
                        existCountVmIds, resConsumeList);
                logger.info(RES_NAME + "计量信息保存入库成功！");
            }

        } catch (SQLException e) {
            logger.error("保存" + RES_NAME + "计量信息失败！", e);
        } catch (Exception e) {
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
     * @param existCountvmIds 已经存在的vmIDS
     * @param consumeList 计量信息
     * @throws SQLException TODO 请在每个@param,@return,@throws行尾补充对应的简要说明
     */
    private void saveCountToDB(final String startTime, final String endTime, final int periodTime,
            final String resPoolId, final String resPoolPartId, List<String> existCountvmIds,
            List<VMHostConsume> consumeList) throws SQLException {
        // 采用HashSet提高比较效率
        Set s = new HashSet(existCountvmIds);
        List<BatchVO> voList = new ArrayList<BatchVO>();
        for (final VMHostConsume consume : consumeList) {

            // 如果是已经在周期内存在主机，则不予保存
            if (s.contains(consume.getVMID())) {
                continue;
            }
            BatchVO vo = this.genVMCountVO(consume, endTime, startTime, periodTime, resPoolId,
                    resPoolPartId);
            voList.add(vo);
        }

        this.ibatisDAO.updateBatchData(voList);
    }

    /**
     * genVMCountVO 生成入库的计量信息Bean
     * @param consume 计量Bean
     * @param endTime 周期结束时间
     * @param startTime 周期开始时间
     * @param periodTime 周期长度
     * @param resPoolId 资源池Id
     * @param resPoolPartId 资源池分区Id
     * @return 入库的计量信息Bean
     */
    private BatchVO genVMCountVO(final VMHostConsume consume, final String endTime,
            final String startTime, final int periodTime, final String resPoolId,
            final String resPoolPartId) {
        VMResCountBean resCount = new VMResCountBean() {
            {
                this.setVmId(consume.getVMID());
                this.setEndTime(endTime);
                this.setStartTime(startTime);
                this.setPeriodTime(periodTime);
                this.setRunTime(consume.getRunTime());
                this.setResPoolId(resPoolId);
                this.setResPoolPartId(resPoolPartId);
                this.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            }
        };
        BatchVO vo = new BatchVO("ADD", "insertVMCount", resCount);
        return vo;
    }

    /**
     * queryExistCountVMId 生成入库的计量信息Bean
     * @param poolId 资源池ID
     * @param partId 分区ID
     * @param endTime 周期结束时间
     * @param startTime 周期开始时间
     * @param consumeList 物理机计量信息List
     * @return 入库的计量信息Bean
     * @exception SQLException 数据库异常
     */
    private List<String> queryExistCountVMId(final String poolId, final String partId,
            final String startTime, final String endTime, List<VMHostConsume> consumeList)
            throws SQLException {
        List<String> vmIds;
        Map<String, Object> daoCountQuery = new HashMap<String, Object>();
        daoCountQuery.put("poolId", poolId);
        daoCountQuery.put("partId", partId);
        daoCountQuery.put("startTime", startTime);
        daoCountQuery.put("endTime", endTime);
        daoCountQuery.put("consume", consumeList);
        vmIds = ibatisDAO.getData("getCountVMById", daoCountQuery);
        return vmIds;
    }

    /**
     * queryNoExistVMId 获取运营平台不存在的物理机ID
     * @param resPoolId 资源池ID
     * @param resPoolPartId 资源池分区ID
     * @param consumeList 物理机计量信息List
     * @return 不存在的PMID
     * @exception SQLException 数据库异常
     */
    private List<String> queryNoExistVMId(String resPoolId, String resPoolPartId,
            List<VMHostConsume> consumeList) throws SQLException {
        List<String> vmIds;
        Map<String, Object> daoCountQuery = new HashMap<String, Object>();
        daoCountQuery.put("resPoolId", resPoolId);
        daoCountQuery.put("resPoolPartId", resPoolPartId);
        daoCountQuery.put("consume", consumeList);
        vmIds = ibatisDAO.getData("getExistVMId", daoCountQuery);
        return vmIds;
    }

}
