/*******************************************************************************
 * @(#)BusinessListService.java 2014年2月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.web.page.business.exception.BusinessOperationException;

/**
 * 业务列表Serivce服务类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月15日 下午1:57:45
 */
public class BusinessDelService {

    /**
     * ibatisDAO;
     */
    private IbatisDAO dao;

    /**
     * 设置dao字段数据
     * @param dao The dao to set.
     */
    public void setDao(IbatisDAO dao) {
        this.dao = dao;
    }

    /**
     * delBusiness 删除一个业务
     * @param delBusinessId 要删除的业务ID
     * @throws BusinessOperationException 业务操作异常
     */
    public void delBusiness(String delBusinessId) throws BusinessOperationException {
        try {
            BatchVO delBusinessVO = new BatchVO("DEL", "delBusinessRes", delBusinessId);
            BatchVO delBusinessBindVO = new BatchVO("DEL", "delBusinessResBind", delBusinessId);
            List<BatchVO> batchVOList = new ArrayList<BatchVO>();
            batchVOList.add(delBusinessVO);
            batchVOList.add(delBusinessBindVO);
            this.dao.updateBatchData(batchVOList);
        } catch (SQLException e) {
            throw new BusinessOperationException("删除业务失败！");
        }

    }

}
