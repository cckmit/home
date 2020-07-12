package com.neusoft.mid.cloong.web.page.snapshots;

import java.sql.SQLException;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPApplySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPApplySnapshotResp;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.snapshots.core.SnapshotCreate;
import com.neusoft.mid.cloong.web.page.snapshots.info.SnapshotBean;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 创建快照的action
 * 
 * @author <a href="mailto:futzh@neusoft.com">futzh</a>
 * @version $Revision 1.1 $ 2016-5-25 下午17:16:46
 */
public class SnapshotCreateAction extends BaseAction {

	private static final long serialVersionUID = 2625330229506726409L;
	/**
	 * 日志
	 */
	private static LogService logger = LogService.getLogger(SnapshotCreateAction.class);
	/**
	 * 从前台传过来的参数
	 */
	private String vmId;

	private String snapshot_name;

	private String snapshot_desc;

	private String generate_memory;

	private String quiesce;

	/**
	 * 申请快照的接口
	 */

	private SnapshotCreate snapshotCreate;

	/**
	 * 登陆用户ID
	 */

	private String create_user;

	private IbatisDAO ibatisDAO;
	/**
	 * 返回路径，用于在界面判断是否系统错误
	 */
	private String resultPath;
	/**
	 * 成功的接口响应码
	 */
	private String resourcePoolId;
	private String resourcePoolPartId;
	private static final String SUCCEESS_CODE = "00000000";
	private SnapshotBean snapshotInfo;

	public String execute() {
		/* 从session中获得当前登录用户的name */
		UserBean user = (UserBean) ServletActionContext.getRequest().getSession()
				.getAttribute(SessionKeys.userInfo.toString());
		create_user = user.getUserId();
		/* 设置默认 */
		String time = DateParse.generateDateFormatyyyyMMddHHmmss();
		/* 快照的bean */
		SnapshotBean snapshotBean = new SnapshotBean();

		RPPApplySnapshotReq req = new RPPApplySnapshotReq();
		RPPApplySnapshotResp resp = new RPPApplySnapshotResp();

		// 将前台传过来的参数封装到请求里面
		req.setVmId(vmId);
		req.setGenerate_memory(generate_memory);
		req.setQuiesce(quiesce);
		req.setSnapshot_desc(snapshot_desc);
		req.setSnapshot_name(snapshot_name);
		req.setResourcePoolId(resourcePoolId);
		req.setResourcePoolPartId(resourcePoolPartId);
		/* 得到资源池的响应 */
		resp = snapshotCreate.create(req);
		if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			/* 将前台传过来的参数封装到bean里面 */
			snapshotBean.setVmId(vmId);
			snapshotBean.setSnapshot_name(snapshot_name);
			snapshotBean.setSnapshot_desc(snapshot_desc);
			snapshotBean.setQuiesce(quiesce);
			snapshotBean.setSnapshot_id(resp.getSnapshotIdList().get(0));
			System.out.println("snap_id length:" + resp.getSnapshotIdList().get(0).length());
			// snapshotBean.setSnapshot_id("1");
			snapshotBean.setSnapshot_time(time);
			snapshotBean.setCreate_user(create_user);
			snapshotBean.setSnapshot_state("1");
			try {
				snapshotInfo = (SnapshotBean) ibatisDAO.getSingleRecord("querySnapshotById", snapshotBean);
				if (snapshotInfo == null) {

					ibatisDAO.insertData("snapshotInsert", snapshotBean);
					resultPath = ConstantEnum.SUCCESS.toString();
					return ConstantEnum.SUCCESS.toString();
				} else {
					logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "快照名称为" + snapshot_name + "的快照已存在");
					resultPath = ConstantEnum.FAILURE.toString();
					return ConstantEnum.FAILURE.toString();
				}

			} catch (SQLException e) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
						"快照名称为" + resp.getSnapshotIdList().get(0) + "的快照无法添加至数据库", e);
				resultPath = ConstantEnum.ERROR.toString();
				return ConstantEnum.ERROR.toString();
			} catch (Exception e) {
				e.printStackTrace();
				resultPath = ConstantEnum.ERROR.toString();
				return ConstantEnum.ERROR.toString();
			}

		} else {
			// 返回失败，入失败库
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "创建快照发送申请失败！");
			this.addActionError(getText("SnapshotCreateAction.fail"));
			errMsg = getText("SnapshotCreateAction.fail");
			return ConstantEnum.FAILURE.toString();
		}

	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getSnapshot_name() {
		return snapshot_name;
	}

	public void setSnapshot_name(String snapshot_name) {
		this.snapshot_name = snapshot_name;
	}

	public String getSnapshot_desc() {
		return snapshot_desc;
	}

	public void setSnapshot_desc(String snapshot_desc) {
		this.snapshot_desc = snapshot_desc;
	}

	public String getGenerate_memory() {
		return generate_memory;
	}

	public void setGenerate_memory(String generate_memory) {
		this.generate_memory = generate_memory;
	}

	public String getQuiesce() {
		return quiesce;
	}

	public void setQuiesce(String quiesce) {
		this.quiesce = quiesce;
	}

	public SnapshotCreate getSnapshotCreate() {
		return snapshotCreate;
	}

	public void setSnapshotCreate(SnapshotCreate snapshotCreate) {
		this.snapshotCreate = snapshotCreate;
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

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public SnapshotBean getSnapshotInfo() {
		return snapshotInfo;
	}

	public void setSnapshotInfo(SnapshotBean snapshotInfo) {
		this.snapshotInfo = snapshotInfo;
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
