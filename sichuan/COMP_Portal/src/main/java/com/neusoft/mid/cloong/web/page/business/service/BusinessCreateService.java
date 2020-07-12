/*******************************************************************************
 * @(#)BusinessListService.java 2014年2月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.page.business.exception.BusinessOperationException;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.cloong.web.page.console.business.info.ResourceInfo;

/**
 * 业务列表Serivce服务类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月15日 下午1:57:45
 */
public class BusinessCreateService {

    /**
     * ibatisDAO;
     */
    private IbatisDAO dao;

    /**
     * 序列号生成器
     */
    private CommonSequenceGenerator seqCen;

    /**
     * 设置dao字段数据
     * @param dao The dao to set.
     */
    public void setDao(IbatisDAO dao) {
        this.dao = dao;
    }

    /**
     * createBusiness 创建业务
     * @param ownerId 业务拥有者ID
     * @param createBusiness 要创建的业务信息
     * @param bindHost 要绑定的主机信息
     * @throws BusinessOperationException 业务管理操作异常
     * @throws SQLException 数据库异常
     */
    public void createBusiness(String ownerId, BusinessInfo createBusiness,
            List<ResourceInfo> bindHost) throws BusinessOperationException, SQLException {

        List<BatchVO> batchVOList = new ArrayList<BatchVO>();

        if (checkNameExist(ownerId, createBusiness.getName())) {
            throw new BusinessOperationException("已经存在同名的业务，请使用一个新的名字");
        }
        addInsertBusinessVO(ownerId, createBusiness, batchVOList);
        addInsertBindVO(createBusiness.getBusinessId(), bindHost, batchVOList);

        this.dao.updateBatchData(batchVOList);
    }

    /**
     * addInsertBindVO 添加资源通业务绑定信息VO
     * @param businessId 要绑定资源的业务ID
     * @param bindHost 要绑定的资源Map key为caseID，value为资源的类型
     * @param batchVOList 批量更新的VO集合
     */
    private void addInsertBindVO(String businessId, List<ResourceInfo> bindHost,
            List<BatchVO> batchVOList) {
        if (bindHost == null) {
            return;
        }
        
//        BatchVO delBindVO = new BatchVO("DEL", "delBusinessResBind", businessId);
//        batchVOList.add(delBindVO);		admin	
        
        for (ResourceInfo resource : bindHost) {
            String hostCaseId = resource.getResourceId();
            String caseType = resource.getType();
            Map<String, String> insertParame = new HashMap<String, String>();
            insertParame.put("caseId", hostCaseId);
            insertParame.put("caseType", caseType);
            insertParame.put("businessId", businessId);
            insertParame.put("createTime", DateParse.generateDateFormatyyyyMMddHHmmss());//设置创建日期
            BatchVO insertBindVO = new BatchVO("ADD", "insertBusinessResBind", insertParame);
            batchVOList.add(insertBindVO);
        }
    }

    /**
     * checkNameExist 检查新的业务名称是否有重名
     * @param ownerId 业务的拥有者ID，即其创建者ID
     * @param createBusinessName 要创建的业务名称
     * @return true 存在重名 false 不存在重名
     * @throws SQLException 数据库异常
     */
    private boolean checkNameExist(String ownerId, String createBusinessName) throws SQLException {
        Map<String, String> queryCountMap = new HashMap<String, String>();
        queryCountMap.put("businessName", createBusinessName);
        queryCountMap.put("ownerId", ownerId);
        int existBusinessName = this.dao.getCount("queryWhereByNameByUID", queryCountMap);
        return existBusinessName > 0;

    }

    /**
     * addInsertBusinessVO 添加业务信息VO,同时为createBusiness对象生成一个唯一ID，并将创建UserID和updateUserId设置为owner参数
     * @param ownerId 业务所属的用户ID
     * @param createBusiness 要创建的业务的基本信息
     * @param batchVOList 承载VO的List
     */
    private void addInsertBusinessVO(String ownerId, BusinessInfo createBusiness,
            List<BatchVO> batchVOList) {
        createBusiness.setBusinessId(this.seqCen.generatorSerialNum());
        createBusiness.setCreateUserId(ownerId);
        createBusiness.setUpdateUserId(ownerId);
        
        String dateTime = DateParse.generateDateFormatyyyyMMddHHmmss();
        createBusiness.setCreateTime(dateTime);//设置创建日期
        createBusiness.setUpdateTime(dateTime);//设置更新日期
        BatchVO vo = new BatchVO("ADD", "insertBusiness", createBusiness);
        batchVOList.add(vo);
    }

    /**
     * 设置seqCen字段数据
     * @param seqCen The seqCen to set.
     */
    public void setSeqCen(CommonSequenceGenerator seqCen) {
        this.seqCen = seqCen;
    }

}
