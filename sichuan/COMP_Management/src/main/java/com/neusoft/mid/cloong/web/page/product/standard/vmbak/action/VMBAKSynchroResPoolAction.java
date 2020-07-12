/*******************************************************************************
 * @(#)VMBAKSynchroResPoolAction.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vmbak.action;

import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronize;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronizeReq;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronizeResp;
import com.neusoft.mid.cloong.resourceProxy.standard.vmbak.VMBAKStandardSynchronizeResult;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.standard.vmbak.service.VMBAKSynchroResPoolService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-25 上午10:13:39
 */
public class VMBAKSynchroResPoolAction extends BaseAction {

    /**
     * 类序列化版本号
     */
    private static final long serialVersionUID = -2004323363076122573L;

    /**
     * 日志logger
     */
    private static LogService logger = LogService.getLogger(VMBAKSynchroResPoolAction.class);

    /**
     * 虚拟机备份同步资源池服务类
     */
    private VMBAKSynchroResPoolService vmbakSynchroResPoolService;

    /**
     * 虚拟机备份规格同步接口
     */
    private VMBAKStandardSynchronize vmbakStandardSynchronize;

    /**
     * 同步资源池、资源池分区JSON字符串
     */
    private String jsonStr;

    /**
     * 规格Id
     */
    private String standardId;

    /**
     * 返回资源池发送失败列表
     */
    private List<VMBAKStandardSynchronizeResult> results;

    /**
     * execute action执行方法
     * @return action执行状态码
     */
    public String execute() {
        VMBAKStandardSynchronizeReq req = null;
        List<ResourcePoolInfo> resourcePoolInfos = frmatJSONToList(jsonStr);
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();

        ConstantEnum res = ConstantEnum.SUCCESS;
        try {
            req = vmbakSynchroResPoolService.initReq(standardId, resourcePoolInfos);// 初始化调用接口数据req
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机规格信息出错，数据库异常", e);
            res = ConstantEnum.ERROR;
        }

        if (null == req) {
            logger.error(CommonStatusCode.INVALID_INPUT_PARAMENTER, "组装同步数据出错！", null);
            res = ConstantEnum.ERROR;
        }
        try {
            String queryInsertSql = "queryInsertVMBAKSynData";
            String standardType = "4";
            vmbakSynchroResPoolService.saveStandardSynInfo(resourcePoolInfos, queryInsertSql,
                    userId, standardId, standardType, req.getTemplateId());// 插入资源池同步表
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "新插入规格同步资源池表信息出错，数据库异常", e);
            res = ConstantEnum.ERROR;
        }
        VMBAKStandardSynchronizeResp resp = null;
        try {
            resp = vmbakStandardSynchronize.synchronizeStandard(req);// 调用接口
            results = vmbakSynchroResPoolService.callSynImpl(resourcePoolInfos, resp, standardId);// 调用接口返回状态，更新数据库方法
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "插入或更新虚拟硬盘资源规格同步资源池信息出错，数据库异常", e);
            logger.info(resp.toString());
            res = ConstantEnum.ERROR;
        }
        return res.toString();
    }

    /**
     * 将资源池 资源池分区 的json字符串转换成 List<ResourcePoolInfo>
     * @param jsonStr
     * @return 返回ResourcePoolInfo对象的集合（对象中存放 资源池id，资源池分区id）
     */
    @SuppressWarnings("unchecked")
    private List<ResourcePoolInfo> frmatJSONToList(String jsonStr) {
        List<ResourcePoolInfo> resourcePoolInfos = null;
        try {
            JSONArray json = JSONArray.fromObject(jsonStr);
            resourcePoolInfos = (List<ResourcePoolInfo>) JSONArray.toCollection(json,
                    ResourcePoolInfo.class);
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "虚拟硬盘规格同步资源池JSON转换Bean失败！", e);
        }
        return resourcePoolInfos;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public List<VMBAKStandardSynchronizeResult> getResults() {
        return results;
    }

    /**
     * 设置vmbakSynchroResPoolService字段数据
     * @param vmbakSynchroResPoolService The vmbakSynchroResPoolService to set.
     */
    public void setVmbakSynchroResPoolService(VMBAKSynchroResPoolService vmbakSynchroResPoolService) {
        this.vmbakSynchroResPoolService = vmbakSynchroResPoolService;
    }

    /**
     * 设置vmbakStandardSynchronize字段数据
     * @param vmbakStandardSynchronize The vmbakStandardSynchronize to set.
     */
    public void setVmbakStandardSynchronize(VMBAKStandardSynchronize vmbakStandardSynchronize) {
        this.vmbakStandardSynchronize = vmbakStandardSynchronize;
    }

}
