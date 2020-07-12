/*******************************************************************************
 * @(#)QueryBusinessService.java 2014年2月7日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.page.console.business.info.BusinessInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询业务信息服务类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月7日 下午2:52:33
 */
public class QueryBusinessService extends BusinessBaseService {

    /**
     * 日志logger
     */
    private static LogService logger = LogService.getLogger(QueryBusinessService.class);

    /**
     * 
     * queryBusinessList 获取当前用户订购的业务
     * @param userId    用户ID
     * @return          业务信息列表
     * @throws SQLException 数据库异常
     */
    public List<BusinessInfo> queryBusinessList(UserBean user) throws SQLException {
        @SuppressWarnings("unchecked")
        List<BusinessInfo> res = this.ibatisDAO.getData("queryBusinessByUserId", user.getAppIdList());
        logger.info("获取业务列表成功！");
        return res;
    }

}
