/*******************************************************************************
 * @(#)VMSynchroResPoolAction.java 2013-3-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.vm.action;

import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronize;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeReq;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeResp;
import com.neusoft.mid.cloong.resourceProxy.standard.vm.VMStandardSynchronizeResult;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.standard.vm.service.VMSynchroResPoolService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机规格同步资源池
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-18 下午03:42:51
 */
public class VMSynchroResPoolAction extends BaseAction {

    private static final long serialVersionUID = -7525459235685861528L;

    private static LogService logger = LogService.getLogger(VMSynchroResPoolAction.class);

    private VMSynchroResPoolService vmSynchroResPoolService;

    /**
     * 虚拟机规格同步接口
     */
    private VMStandardSynchronize vmStandardSynchronize;

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
    private List<VMStandardSynchronizeResult> results;

    public String execute() {
        VMStandardSynchronizeReq req;
        List<ResourcePoolInfo> resourcePoolInfos = frmatJSONToList(jsonStr);
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        try {
            req = vmSynchroResPoolService.initReq(standardId, resourcePoolInfos);// 初始化调用接口数据req
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询虚拟机资源规格信息出错，数据库异常", e);
            return ConstantEnum.ERROR.toString();
        }
        try {
            String queryInsertSql = "queryInsertVmSynData";
            String standardType = "0";
            vmSynchroResPoolService.saveStandardSynInfo(resourcePoolInfos, queryInsertSql, userId,
                    standardId, standardType,"");// 插入资源池同步表
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "新插入规格同步资源池表信息出错，数据库异常", e);
            return ConstantEnum.ERROR.toString();
        }
        VMStandardSynchronizeResp resp = null;
        try {
            resp = vmStandardSynchronize.synchronizeStandard(req);// 调用接口
            results = vmSynchroResPoolService.callSynImpl(resourcePoolInfos, resp, standardId);// 调用接口返回状态，更新数据库方法
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "插入或更新虚拟机资源规格同步资源池信息出错，数据库异常", e);
            logger.info(resp.toString());
            return ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    "插入或更新虚拟机资源规格同步资源池信息出错，数据库异常", e);
            logger.info(resp.toString());
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
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
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "虚拟机规格同步资源池JSON转换Bean失败！", e);
        }
        return resourcePoolInfos;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public void setVmStandardSynchronize(VMStandardSynchronize vmStandardSynchronize) {
        this.vmStandardSynchronize = vmStandardSynchronize;
    }

    public List<VMStandardSynchronizeResult> getResults() {
        return results;
    }

    public void setVmSynchroResPoolService(VMSynchroResPoolService vmSynchroResPoolService) {
        this.vmSynchroResPoolService = vmSynchroResPoolService;
    }

}
