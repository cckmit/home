package com.neusoft.mid.cloong.web.page.system.config.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.ResPoolStatus;
import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourceInfo;

/**
 * 资源池 创建or更新
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-6 下午02:54:34
 */
public class ResourceCreateService extends BaseService {

    public void saveOrUpdate(ResourceInfo resourceInfo, String optSqlKey) throws SQLException, UnsupportedEncodingException {
        resourceInfo.setStatus(ResPoolStatus.NORMAL);// 设置创建时候的状态为正常
        resourceInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 设置创建时间
        resourceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 设置更新时间
        resourceInfo.setUserPwd(Base64.encode(resourceInfo.getUserPwd()));
        ibatisDAO.updateData(optSqlKey, resourceInfo);
    }

    public ResourceInfo queryResourceInfoByResPoolId(ResourceInfo resourceInfo,String selectSqlKey) throws SQLException {
        ResourceInfo temp = (ResourceInfo) ibatisDAO.getSingleRecord(
                selectSqlKey, resourceInfo);
        return temp;
    }

}
