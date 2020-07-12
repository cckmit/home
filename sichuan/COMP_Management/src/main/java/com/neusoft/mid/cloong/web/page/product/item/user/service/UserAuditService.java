package com.neusoft.mid.cloong.web.page.product.item.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.AppStatus;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.cloong.web.action.BaseService;
import com.neusoft.mid.cloong.web.page.product.item.user.info.UserAuditLogInfo;

/**
 * 用户审批 
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年1月28日 上午10:26:38
 */
public class UserAuditService extends BaseService {

    /**
     * 用户审批（可多条目）并插入记录数据
     * @param userIds 条目id集合
     * @param auditUserId 用户id
     * @param status 改变状态
     * @param auditInfo 审批意见
     * @param auditStatus 审批状态 0:审批通过,1:审批不通过;
     * @param chargeUserId 
     * @throws SQLException
     */
    public void userAudit(List<String> userIds, String auditUserId, String status,
            String auditInfo, String auditStatus, String chargeUserId) throws SQLException {
        UserBean userBean = new UserBean();
		userBean.setChargesUserId(chargeUserId);
        List<BatchVO> batchVOs = new ArrayList<BatchVO>();
      //审批业务.
        UserAppBean appBean;
        //判断用户状态  若 status=0:业务变更待审；4：注册待审
        for (String id : userIds) {
        	if(status.equals(UserStatus.EXAMINATION.getCode())){//审批用户
        		appBean = new UserAppBean();
                userBean.setUserId(id);
                if(auditStatus.equals("0")){//用户审批通过更改业务状态
                	appBean.setAppBind_status(AppStatus.AUDITPASS.getCode());
                	userBean.setStatus(UserStatus.getEnum(UserStatus.ENABLE.getCode()));
                	
                	//审批业务.
                    appBean.setUserId(id);
                    appBean.setAuditTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                    appBean.setAuditUser(auditUserId);
                    appBean.setAuditInfo(auditInfo);
                    BatchVO batchVO = new BatchVO("MOD", "updateUserAppInfo", appBean);
                    batchVOs.add(batchVO);
                }else{//业务审批不通过删除注册用户时添加的业务信息
                	appBean.setAppBind_status(AppStatus.UNAUDITPASS.getCode());
                	userBean.setStatus(UserStatus.getEnum(UserStatus.UNAUDITPASS.getCode()));
                	appBean.setUserId(id);
                	
                	BatchVO appBatchVo = new BatchVO("DEL", "delAppInfo", appBean);
                	batchVOs.add(appBatchVo);//删除业务信息
                	
                	BatchVO batchVO = new BatchVO("DEL", "delUserAppRelation", appBean);
                    batchVOs.add(batchVO);//删除业务绑定关系
                }
                userBean.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                initVmBatchVOs(batchVOs, userBean,auditUserId, auditInfo, "inserUserAuditLog");//inserUserAuditLog
                
        	}else{//审批业务
        		appBean = new UserAppBean();
        		appBean.setUserId(id);
    			appBean.setAuditTime(DateParse.generateDateFormatyyyyMMddHHmmss());
                appBean.setAuditUser(auditUserId);
                appBean.setAuditInfo(auditInfo);
        		if(auditStatus.equals("0")){
        			//通过  删除原有的业务  绑定新业务
                    appBean.setAppBind_status(AppStatus.AUDITPASS.getCode());
                    BatchVO batchVODel = new BatchVO("DEL", "delUserApp", appBean);
                    batchVOs.add(batchVODel);
        		}else{
                    appBean.setAppBind_status(AppStatus.UNAUDITPASS.getCode());
        		}
        		BatchVO batchVOMOD = new BatchVO("MOD", "updateUserAppInfo", appBean);
                batchVOs.add(batchVOMOD);
        	}
                
        }
        ibatisDAO.updateBatchData(batchVOs);
    }

    /**
     * 生成batchVOs （用户审批及审批记录）
     * @param batchVOs
     * @param itemInfo
     * @param auditInfo
     */
    private void initVmBatchVOs(List<BatchVO> batchVOs, UserBean userBean,String auditUserId, String auditInfo,
            String logSqlKey) {
        // 保存虚拟机条目
        BatchVO batchVO = new BatchVO("MOD", "updateUserInfo", userBean);
        batchVOs.add(batchVO);
        UserAuditLogInfo userAuditLogInfo = new UserAuditLogInfo();
        userAuditLogInfo.setUserId(userBean.getUserId());
        userAuditLogInfo.setAuditInfo(auditInfo);
        userAuditLogInfo.setAuditTime(userBean.getUpdateTime());
        userAuditLogInfo.setStatus(userBean.getStatus().getCode());
        userAuditLogInfo.setAuditUser(auditUserId);
        batchVO = new BatchVO("ADD", logSqlKey, userAuditLogInfo);// "inserUserAuditLog"
        batchVOs.add(batchVO);
    }
}
