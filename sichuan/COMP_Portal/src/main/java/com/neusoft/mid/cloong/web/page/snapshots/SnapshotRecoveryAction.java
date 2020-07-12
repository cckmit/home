package com.neusoft.mid.cloong.web.page.snapshots;

import org.apache.struts2.ServletActionContext;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotResp;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.snapshots.core.SnapshotRecovery;
import com.neusoft.mid.cloong.web.page.snapshots.info.SnapshotBean;
import com.neusoft.mid.iamp.logger.LogService;

public class SnapshotRecoveryAction   extends BaseAction {

	/**
	 * 序列号      
	 */
	private static final long serialVersionUID = 8077997216664689932L;
	/**
	 * 日志
	 */
	private static LogService logger = LogService
			.getLogger(SnapshotRecoveryAction.class);
	/**
	 * 从前台传过来的参数
	 */
	private String vmId;

	private String snapshot_id;
	
	private SnapshotRecovery snapshotRecovery;
	
	private IbatisDAO ibatisDAO;
		/**
		 * 返回路径，用于在界面判断是否系统错误
		 */
	private String resultPath = ConstantEnum.SUCCESS.toString();
		/**
		 * 成功的接口响应码
		 */
	private static final String SUCCEESS_CODE = "00000000";
    private String  resourcePoolId;
	private String resourcePoolPartId;
	public String execute() {
		  	   RPPRecoverySnapshotReq req = new RPPRecoverySnapshotReq();
		  	   RPPRecoverySnapshotResp resp = new RPPRecoverySnapshotResp();
		  	   /*将前台传过来的参数封装到请求里面*/
		  	   req.setVmId(vmId);
		  	   req.setSnapshot_id(snapshot_id);
		  	   req.setResourcePoolId(resourcePoolId);
		  	   req.setResourcePoolPartId(resourcePoolPartId);
			   /*得到资源池的响应*/
			   resp = snapshotRecovery.recovery(req);
			   /*封装一个将要查询的bean*/
		       SnapshotBean snapshotbean = new SnapshotBean();
			   snapshotbean.setVmId(vmId);
			   snapshotbean.setSnapshot_id(snapshot_id);
			   /*获取当前登录的用户id*/
			   String userId = ((UserBean) ServletActionContext.getRequest().getSession()
		                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
		       if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			   /*将数据库中的快照状态变成已回复7*/
				 try {
					 ibatisDAO.updateData("updateSnapshotStateTo7", snapshotbean);
					 resultPath = ConstantEnum.SUCCESS.toString();
					 return ConstantEnum.SUCCESS.toString();
				 }catch (Exception e) {
					 logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
								"快照id为" + snapshot_id + "的快照无法在数据库中更新", e);
						resultPath = ConstantEnum.ERROR.toString();
						return ConstantEnum.ERROR.toString();
			           
			        }
		     }
		      else {
		          // 返回失败，入失败库
		         logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "查询快照发送申请失败！");
		          this.addActionError(getText("SnapshotQueryAction.fail"));
		          errMsg = getText("SnapshotQueryAction.fail");
		            resultPath = ConstantEnum.FAILURE.toString();
		          return ConstantEnum.FAILURE.toString();
		      }
			  /* 当收到恢复的响应之后，将快照的状态变成以恢复
			  if (SUCCEESS_CODE.equals(resp.getResultCode())) {
				try {
					ibatisDAO.updateData("updateSnapshotStateTo7", snapshotbean);
					resultPath = ConstantEnum.SUCCESS.toString();
					return ConstantEnum.SUCCESS.toString();
				} catch (SQLException e) {
					logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
							"快照名称为" + snapshot_id + "的快照无法添加至数据库", e);
					resultPath = ConstantEnum.ERROR.toString();
					return ConstantEnum.ERROR.toString();
				}
			}*/
			   /*else {
		          // 返回失败，入失败库
		         logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "查询快照发送申请失败！");
		          this.addActionError(getText("SnapshotQueryAction.fail"));
		          errMsg = getText("SnapshotQueryAction.fail");
		          return ConstantEnum.FAILURE.toString();
		      }*/
	   
   

}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getSnapshot_id() {
		return snapshot_id;
	}

	public void setSnapshot_id(String snapshot_id) {
		this.snapshot_id = snapshot_id;
	}

	public SnapshotRecovery getSnapshotRecovery() {
		return snapshotRecovery;
	}

	public void setSnapshotRecovery(SnapshotRecovery snapshotRecovery) {
		this.snapshotRecovery = snapshotRecovery;
	}

	public IbatisDAO getIbatisDAO() {
		return ibatisDAO;
	}

	public void setIbatisDAO(IbatisDAO ibatisDAO) {
		this.ibatisDAO = ibatisDAO;
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public String getResourcePoolId() {
		return resourcePoolId;
	}

	public void setResourcePoolId(String resourcePoolId) {
		this.resourcePoolId = resourcePoolId;
	}

	public String getResourcePoolPartId() {
		return resourcePoolPartId;
	}

	public void setResourcePoolPartId(String resourcePoolPartId) {
		this.resourcePoolPartId = resourcePoolPartId;
	}
	 
}
