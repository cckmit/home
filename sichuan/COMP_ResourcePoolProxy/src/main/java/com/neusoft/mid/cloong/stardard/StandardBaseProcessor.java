/*******************************************************************************
 * @(#)StandardBaseProcessor.java 2013-3-28
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.info.ResourcePoolInfo;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 规格同步公用处理基类
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-28 下午01:34:45
 */
public abstract class StandardBaseProcessor extends BaseProcessor {

    private static LogService logger = LogService.getLogger(StandardBaseProcessor.class);

    /**
     * 规格创建正则表达式
     */
    protected static final Pattern CREATE_REGEX = Pattern.compile("0|2");

    /**
     * 规格修改正则表达式
     */
    protected static final Pattern MODIFY_REGEX = Pattern.compile("1");

    protected IbatisDAO ibatisDao;

    public void setIbatisDao(IbatisDAO ibatisDao) {
        this.ibatisDao = ibatisDao;
    }

    protected ResourcePoolInfo queryResourceInfo(String resourcePoolId) {
        ResourcePoolInfo resourceInfo = null;
        try {
            resourceInfo = (ResourcePoolInfo) this.getResourcePoolCacheService().match(
                    resourcePoolId);
        } catch (ServiceStopException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送同步操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息", e);
        } catch (UnmatchException e) {
            logger.error(CommonStatusCode.INTERNAL_ERROR,
                    "向资源池发送同步操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e);
        }
        return resourceInfo;
    }

    /**
     * 
     * assmbleStandardPara 生成资源同公共查询参数(报文公共字段)
     * @param req           SCPG协议请求
     * @param standardId    资源规格ID
     * @return 返回"资源规格同步状态(用来区分是同步还是创建)"
     */
    protected StandardQueryPara assmbleStandardPara(RuntimeContext req, String standardId) {
        List<Map<String, String>> lists = (List<Map<String, String>>) req
                .getAttribute("RESOURCE_POOLS");
        StandardQueryPara para = new StandardQueryPara();
        para.setStandardId(standardId);
        List<ResourcePoolInfoPara> resourceInfos = new ArrayList<ResourcePoolInfoPara>();
        for (Map<String, String> map : lists) {
            ResourcePoolInfoPara resourceInfo = new ResourcePoolInfoPara();
            resourceInfo.setResourcePoolId(map.get("RES_POOL_ID"));
            resourceInfo.setResourcePoolPartId(map.get("RES_POOL_PART_ID"));
            resourceInfos.add(resourceInfo);
        }
        para.setResourceInfos(resourceInfos);
        return para;
    }

    protected void assmbleResult(RuntimeContext resp, List<Map<String, String>> results,
            String resultCode) {
        resp.setAttribute("RESULTCODE", resultCode);
        resp.setAttribute("RESULT", results);
    }

}
