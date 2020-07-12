
package com.neusoft.mid.cloong.web.page.app.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.web.page.app.exception.AppOperationException;
import com.neusoft.mid.cloong.web.page.app.info.App;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;

/**
 * 业务删除Service
 * @author <a href="mailto:nan.liu@neusoft.com">liunan</a>
 * @version 创建时间：2014-12-29 上午10:12:37
 */
public class AppDeleteService {

    private IbatisDAO ibatisDAO;

    public CreateResult deleteApp(String app_id) throws AppOperationException {
      
    	CreateResult result = null;
    	
        try {
        	 //业务是否存在
            App appTmp = (App) this.ibatisDAO.getSingleRecord("getApp", app_id);
            
            if (appTmp == null) {
            	 
            	result = new CreateResult("error", "该业务不存在!");
            	
            	return result;
            }
            
            //判断业务是否与用户绑定
            int userAppCount = ibatisDAO.getCount("getUserAppCount", app_id);
            
            if(userAppCount>0){
            	result = new CreateResult("error","该业务与用户绑定,无法删除!");
            	return result;
            }
            
            int pmCount = ibatisDAO.getCount("getvCountPMByApp_ID", app_id);
            
            if (pmCount > 0) {
            	 
            	result = new CreateResult("error", "该业务与物理机绑定, 无法删除！");
            	return result;
            }
            
            int vmCount = ibatisDAO.getCount("getvCountVMByApp_ID", app_id);
            
            if (vmCount > 0) {
            	 
            	result = new CreateResult("error", "该业务与虚拟机绑定, 无法删除！");
            	return result;
            }
            
            int ebsCount = ibatisDAO.getCount("getvCountEBSByApp_ID", app_id);
            
            if (ebsCount > 0) {
            	 
            	result = new CreateResult("error", "该业务与虚拟硬盘绑定, 无法删除！");
            	return result;
            }
            
             this.ibatisDAO.deleteData("deleteApp",app_id);
            
            return result;
            
        } catch (SQLException e) {
        	
            throw new AppOperationException("删除业务失败！");
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
