package com.neusoft.mid.cloong.web.page.system.config.service;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.ResPoolStatus;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourceInfo;

/**
 * 资源池通用更新状态 （暂停、恢复、终止）
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-3-7 上午11:06:06
 */
public class ResourceOperateService extends BaseService {

    /**
     * 通用更新 状态（暂停、恢复、终止）
     * @param resPoolId  资源池id
     * @param status    状态code
     * @param userId    用户id
     * @throws SQLException
     */
    public void operate(String resPoolId, String status, String userId) throws SQLException {
        ResourceInfo resourceInfo = new ResourceInfo();
        resourceInfo.setResPoolId(resPoolId);//资源id
        resourceInfo.setUpdateUser(userId);//更新人id
        resourceInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());//更新时间
        resourceInfo.setStatus(obtainResPoolStatusEnum(status));//更新状态
        ibatisDAO.updateData("updateResourceStatus", resourceInfo);
    }
    
    /**
     * 获取资源池状态enum
     * @param status  状态编码
     * @return ResPoolStatus枚举类型
     */
    public ResPoolStatus obtainResPoolStatusEnum(String status) {
        for (ResPoolStatus temp : ResPoolStatus.values()) {
            if (status.equals(temp.getCode())) {
                return temp;
            }
        }
        return null;
    }
    
}
