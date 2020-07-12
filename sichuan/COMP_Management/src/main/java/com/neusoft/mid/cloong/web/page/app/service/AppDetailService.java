
package com.neusoft.mid.cloong.web.page.app.service;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.page.app.exception.AppOperationException;
import com.neusoft.mid.cloong.web.page.app.info.App;

/**
 * 业务详情Serivce
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version 创建时间：2014-12-26 上午8:45:15
 */
public class AppDetailService {

    /**
     * ibatisDAO;
     */
    private IbatisDAO ibatisDAO;


    /**
     * @return 业务详情
     * @throws AppOperationException 业务操作异常
     */
    public App getAppDetail(String app_id) throws AppOperationException {
        try {
            App app = (App) this.ibatisDAO.getSingleRecord(
                    "getApp", app_id);
            
            List<UserBean> userList = ibatisDAO.getData("queryUsersbyAppId", app_id);
            app.setAppContactList(userList);
            return app;
        } catch (SQLException e) {
            throw new AppOperationException("查看业务详情失败！");
        }
    }


	/**
	 * @return the ibatisDAO
	 */
	public IbatisDAO getIbatisDAO() {
		return ibatisDAO;
	}


	/**
	 * @param ibatisDAO the ibatisDAO to set
	 */
	public void setIbatisDAO(IbatisDAO ibatisDAO) {
		this.ibatisDAO = ibatisDAO;
	}

}
