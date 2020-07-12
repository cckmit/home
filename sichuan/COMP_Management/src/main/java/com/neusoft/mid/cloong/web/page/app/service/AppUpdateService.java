
package com.neusoft.mid.cloong.web.page.app.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.web.page.app.exception.AppOperationException;
import com.neusoft.mid.cloong.web.page.app.info.App;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;

/**
 * 修改业务 service
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version 创建时间：2014-12-26 下午3:50:17
 */
public class AppUpdateService {

    private IbatisDAO ibatisDAO;

    public CreateResult updateApp(String userId,App app) throws AppOperationException, SQLException {
        
    	CreateResult result = null;
    	
        //业务名称是否存在
        int app_nameCount = ibatisDAO.getCount("getCountByApp_name", app);
        
        if (app_nameCount > 0) {
        	 
        	result = new CreateResult("warn", "业务名称已存在，请重新输入!");
        } else {
        	app.setUpdate_user(userId); //设置更新用户
            
            ibatisDAO.updateData("updateApp", app);
        }
        
        
        
        return result;
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
