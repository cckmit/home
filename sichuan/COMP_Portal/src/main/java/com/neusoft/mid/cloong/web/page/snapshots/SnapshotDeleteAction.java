package com.neusoft.mid.cloong.web.page.snapshots;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPApplySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPApplySnapshotResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPDeleteSnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPDeleteSnapshotResp;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.snapshots.core.SnapshotCreate;
import com.neusoft.mid.cloong.web.page.snapshots.core.SnapshotDelete;
import com.neusoft.mid.cloong.web.page.snapshots.info.SnapshotBean;
import com.neusoft.mid.iamp.logger.LogService;

public class SnapshotDeleteAction  extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2299997792215071629L;
	/**
	 * 日志
	 */
	
	private static LogService logger = LogService
			.getLogger(SnapshotDeleteAction.class);
	/**
	 * 从前台传过来的参数
	 */
	private String vmId;

	private String snapshot_id;

	/**
	 * 删除快照的接口
	 */
	
	private SnapshotDelete snapshotDelete;
	
	/**
	 * 登陆用户ID
	 */

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
		
		RPPDeleteSnapshotReq req = new RPPDeleteSnapshotReq();
		RPPDeleteSnapshotResp resp = new RPPDeleteSnapshotResp();
		
		req.setVmId(vmId);
	   	req.setSnapshot_id(snapshot_id);
	   	req.setResourcePoolId(resourcePoolId);
	   	req.setResourcePoolPartId(resourcePoolPartId);
	    resp = snapshotDelete.delete(req);
	    if (SUCCEESS_CODE.equals(resp.getResultCode())) {
	    	SnapshotBean snapshotBean = new SnapshotBean();
	    	snapshotBean.setVmId(vmId);
	    	snapshotBean.setSnapshot_id(snapshot_id);
	    	
	    	try {
				ibatisDAO.updateData("updateSnapshotStateTo6", snapshotBean);
				resultPath = ConstantEnum.SUCCESS.toString();
				  return ConstantEnum.SUCCESS.toString();
			} catch (SQLException e) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "快照id为"
						+ snapshot_id + "的快照无法进行删除", e);
				resultPath = ConstantEnum.ERROR.toString();
				return ConstantEnum.ERROR.toString();
			}
	    }
	   else {
            // 返回失败，入失败库
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建快照发送申请失败！");
            this.addActionError(getText("SnapshotDeleteAction.fail"));
            errMsg = getText("SnapshotDeleteAction.fail");
            resultPath = ConstantEnum.FAILURE.toString();
            return ConstantEnum.FAILURE.toString();
       }
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

	public SnapshotDelete getSnapshotDelete() {
		return snapshotDelete;
	}

	public void setSnapshotDelete(SnapshotDelete snapshotDelete) {
		this.snapshotDelete = snapshotDelete;
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
