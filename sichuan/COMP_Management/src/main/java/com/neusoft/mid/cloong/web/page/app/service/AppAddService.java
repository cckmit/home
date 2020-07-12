
package com.neusoft.mid.cloong.web.page.app.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.CommonSequenceGenerator;
import com.neusoft.mid.cloong.web.page.app.exception.AppOperationException;
import com.neusoft.mid.cloong.web.page.app.info.App;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;

/**
 * 业务添加Service
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version 创建时间：2014-12-29 下午1:19:48
 */
public class AppAddService {

    private IbatisDAO ibatisDAO;

    private CommonSequenceGenerator seqCen;

    public CreateResult addApp(String userId,App app) throws AppOperationException, SQLException {
    	 
    	CreateResult result = null;
    	
    	/* 业务ID是否存在 */
        int app_idCount = ibatisDAO.getCount("getCountByApp_id", app);
        /* 业务名称是否存在 */
        int app_nameCount = ibatisDAO.getCount("getCountByApp_name", app);
        
        if (app_idCount > 0) {
        	result = new CreateResult("warn", "业务ID已存在，请重新输入!");
        } else if (app_nameCount > 0) {
        	result = new CreateResult("warn", "业务名称已存在，请重新输入!");
        } else {
            app.setCreate_user(userId);
            
            ibatisDAO.insertData("insertApp", app);
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

	/**
	 * @return the seqCen
	 */
	public CommonSequenceGenerator getSeqCen() {
		return seqCen;
	}

	/**
	 * @param seqCen the seqCen to set
	 */
	public void setSeqCen(CommonSequenceGenerator seqCen) {
		this.seqCen = seqCen;
	}

}
