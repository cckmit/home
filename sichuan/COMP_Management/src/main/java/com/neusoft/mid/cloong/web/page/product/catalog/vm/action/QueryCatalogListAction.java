/*******************************************************************************
 * @(#)QueryCatalogListAction.java 2013-3-11
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.catalog.vm.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.catalog.vm.info.CatalogInfo;
import com.neusoft.mid.cloong.web.page.product.catalog.vm.service.QueryCatalogListService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询服务目录
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-11 下午04:04:31
 */
public class QueryCatalogListAction extends BaseAction {

    private static final long serialVersionUID = -4965007863408723033L;

    private static LogService logger = LogService.getLogger(QueryCatalogListAction.class);

    /**
     * 查询界面返回
     */
    private List<CatalogInfo> catalogInfos;

    /**
     * 使用什么类型的服务目录
     */
    private String catalogType;

    /**
     * 注入服务目录服务
     */
    private QueryCatalogListService queryCatalogListService;

    /**
     * 创建从界面读取
     */
    private String catalogName;
    /** 修改时,原名称**/
    private String oldCatalogName;

    private String description;

    /**
     * 生成服务id
     */
    private SequenceGenerator sequenceGenerator;

    /**
     * 更新从页面读取
     */
    private String catalogId;

    /**
     * 更新信息页面界面返回
     */
    private CatalogInfo catalogInfo;


    /**
     * 创建服务目录
     * @return
     */
    public String createCatalog() {
        CatalogInfo tempCatalogInfo = assembleCatalogInfo();
        try {
            //判断是否已存在
            boolean isUsed = queryCatalogListService.createCatalogIsUsed(tempCatalogInfo);
            if(isUsed){
                errMsg = getText("catalog.vm.create.fail.isused");
                if(logger.isDebugEnable()){
                    logger.debug(errMsg);
                }
                this.addActionError(errMsg);
                return ConstantEnum.FAILURE.toString();
            }
            queryCatalogListService.createCatalog(tempCatalogInfo);
        } catch (SQLException e) {
            errMsg = getText("catalog.vm.create.fail");
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
            errMsg = getText("catalog.vm.create.fail");
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e2);
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 浏览服务目录
     * @return
     */
    public String catalogQueryList() {
        try {
            catalogInfos = queryCatalogListService.queryCatalogList(catalogType);
            for (CatalogInfo catalogInfo : catalogInfos) {
                if(catalogInfo.getCreateTime()!=null && !"".equals(catalogInfo.getCreateTime())){
                    catalogInfo.setCreateTime(DateParse.parse(catalogInfo.getCreateTime()));
                }
                if(catalogInfo.getUpdateTime()!=null && !"".equals(catalogInfo.getUpdateTime())){
                    catalogInfo.setUpdateTime(DateParse.parse(catalogInfo.getUpdateTime()));
                }
            }
        } catch (SQLException e) {
            errMsg = getText("catalog.vm.query.fail");
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            this.addActionError(errMsg);
            catalogInfos = null;
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
            errMsg = getText("catalog.vm.query.fail");
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e2);
            this.addActionError(errMsg);
            catalogInfos = null;
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 修改服务目录
     * @return
     */
    public String modifyCatalog() {
        CatalogInfo tempCatalogInfo = assembleCatalogModify();
        try {
            if(!oldCatalogName.equals(tempCatalogInfo.getCatalogName())){
                //判断是否已存在
                boolean isUsed = queryCatalogListService.createCatalogIsUsed(tempCatalogInfo);
                if(isUsed){
                    errMsg = getText("catalog.vm.create.fail.isused");
                    if(logger.isDebugEnable()){
                        logger.debug(errMsg);
                    }
                    this.addActionError(errMsg);
                    return ConstantEnum.FAILURE.toString();
                }
            }
            
            queryCatalogListService.updateCatalog(tempCatalogInfo);
            msg="修改成功！";
        } catch (SQLException e) {
            errMsg = getText("catalog.vm.update.fail");
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
            errMsg = getText("catalog.vm.update.fail");
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e2);
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 删除服务目录
     * @return
     */
    public String delCatalog() {
        try {
            // 删除前判断服务目录下是否有条目
            if (queryCatalogListService.queryCatalogIsUsed(catalogId)) {
                // 若有条目，则返回，提示不能删除
                errMsg = getText("catalog.vm.delete.isuesd");
                logger.error(errMsg);
                this.addActionError(errMsg);
                return ConstantEnum.FAILURE.toString();
            }
            queryCatalogListService.deleteCatalog(catalogId);
        } catch (SQLException e) {
            errMsg = getText("catalog.vm.delete.fail");
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e);
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
            errMsg = getText("catalog.vm.delete.fail");
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, errMsg, e2);
            this.addActionError(errMsg);
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    private CatalogInfo assembleCatalogInfo() {
        CatalogInfo tempCatalogInfo = new CatalogInfo();
        tempCatalogInfo.setCatalogId(sequenceGenerator.generatorCatalogId(catalogType));
        tempCatalogInfo.setCatalogName(catalogName);
        tempCatalogInfo.setCatalogType(catalogType);
        tempCatalogInfo.setDescription(description);
        tempCatalogInfo.setStatus("5");
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        tempCatalogInfo.setCreateUser(userId);
        tempCatalogInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        tempCatalogInfo.setUpdateUser(userId);
        tempCatalogInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        return tempCatalogInfo;
    }

    private CatalogInfo assembleCatalogModify() {
        CatalogInfo tempCatalogInfo = new CatalogInfo();
        tempCatalogInfo.setCatalogId(catalogId);
        tempCatalogInfo.setCatalogName(catalogName);
        tempCatalogInfo.setCatalogType(catalogType);
        tempCatalogInfo.setDescription(description);
        tempCatalogInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        tempCatalogInfo.setUpdateUser(userId);
        return tempCatalogInfo;
    }

    public List<CatalogInfo> getCatalogInfos() {
        return catalogInfos;
    }

    public void setCatalogInfos(List<CatalogInfo> catalogInfos) {
        this.catalogInfos = catalogInfos;
    }

    public String getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    public QueryCatalogListService getQueryCatalogListService() {
        return queryCatalogListService;
    }

    public void setQueryCatalogListService(QueryCatalogListService queryCatalogListService) {
        this.queryCatalogListService = queryCatalogListService;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public CatalogInfo getCatalogInfo() {
        return catalogInfo;
    }

    public void setCatalogInfo(CatalogInfo catalogInfo) {
        this.catalogInfo = catalogInfo;
    }

    public String getOldCatalogName() {
        return oldCatalogName;
    }

    public void setOldCatalogName(String oldCatalogName) {
        this.oldCatalogName = oldCatalogName;
    }

}
