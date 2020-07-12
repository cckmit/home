/*******************************************************************************
 * @(#)QueryUserInfo.java 2014
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.identity.bean.query;

import com.neusoft.mid.cloong.identity.bean.RoleBean;

/**
 * 用户信息查询条件Bean
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014-1-10 下午2:50:08
 */
public class QueryRoleInfo extends RoleBean {
    /**
     * 
     */
    public String queryStatus;

    public String getQueryStatus() {
        return queryStatus;
    }
    /**
     * 
     * setQueryStatus TODO 这里请补充该方法的简述说明
     * @param queryStatus TODO 请在每个@param,@return,@throws行尾补充对应的简要说明
     */
    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }
    

}
