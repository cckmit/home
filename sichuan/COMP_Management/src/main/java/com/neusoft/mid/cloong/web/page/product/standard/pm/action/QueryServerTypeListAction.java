/*******************************************************************************
 * @(#)QueryResourceListAction.java 2013-3-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.product.standard.pm.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.pm.info.PMStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 查询物理机型号列表
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-6 上午10:16:18
 */
public class QueryServerTypeListAction extends BaseAction {

    private static final long serialVersionUID = -1862614417618551035L;

    private static LogService logger = LogService.getLogger(QueryServerTypeListAction.class);

    /**
     * 界面返回集合 资源列表
     */
    private List<PMStandardInfo> typeInfos;

    private String mes;

    public String execute() {
        try {
        	typeInfos = ibatisDAO.getData("queryServerTypes", null);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    getText("resource.db.error"), e);
            this.addActionError(getText("resource.db.error"));
            mes= getText("resource.db.error");
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public String getMes() {
        return mes;
    }

	/**
	 * @return the typeInfos
	 */
	public List<PMStandardInfo> getTypeInfos() {
		return typeInfos;
	}

	/**
	 * @param typeInfos the typeInfos to set
	 */
	public void setTypeInfos(List<PMStandardInfo> typeInfos) {
		this.typeInfos = typeInfos;
	}
}
