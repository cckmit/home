package com.neusoft.mid.cloong.web.page.snapshots;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPQuerySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPQuerySnapshotResp;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.snapshots.core.SnapshotQuery;
import com.neusoft.mid.cloong.web.page.snapshots.info.SnapshotBeanQry;
import com.neusoft.mid.iamp.logger.LogService;
public class SnapshotQueryAction  extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4955354548720573226L;
	/**
	 * 日志
	 */
	private static LogService logger = LogService
			.getLogger(SnapshotQueryAction.class);
	/**
	 * 从前台传过来的参数
	 */
	private String vmId;
	
	private String snapshot_id;

	private SnapshotQuery snapshotQuery;
	
	private IbatisDAO ibatisDAO;
		/**
		 * 返回路径，用于在界面判断是否系统错误
		 */
	private String resultPath = ConstantEnum.SUCCESS.toString();
		/**
		 * 成功的接口响应码
		 */
	private static final String SUCCEESS_CODE = "00000000";


	/**
	 * snapshot
	 */
	private List<SnapshotBeanQry> snapshotBeanQryList ;
	
	 @SuppressWarnings("unchecked")
	public String execute() {
		  	   /*要查询快照的信息的集合*/
		  	 /*  RPPQuerySnapshotReq req = new RPPQuerySnapshotReq();
		  	   RPPQuerySnapshotResp resp = new RPPQuerySnapshotResp();*/
				   
		  	   /*将前台传过来的参数封装到请求里面*/
		  	  // req.setVmId(vmId);
			   /*得到资源池的响应*/
			 //  resp = snapshotQuery.query(req);
			   
		      // if (SUCCEESS_CODE.equals(resp.getResultCode())) {
			   /*得到响应list中的一个bean*/
		       //snapshotBeanQryList=resp.getSnapshotInfo();
		
					 try {
						snapshotBeanQryList=ibatisDAO.getData("querySnapshotByVMid", vmId);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
			         System.out.println(snapshotBeanQryList.size());
			       for (int i = 0; i < snapshotBeanQryList.size(); i++) {
			           	if("0".equals(snapshotBeanQryList.get(i).getSnapshot_state())){
			           		snapshotBeanQryList.get(i).setSnapshot_state("创建中");
			           	}
			           	else if("1".equals(snapshotBeanQryList.get(i).getSnapshot_state())){
			           		snapshotBeanQryList.get(i).setSnapshot_state("已创建");
			           	}
			           	else if("2".equals(snapshotBeanQryList.get(i).getSnapshot_state())){
			           		snapshotBeanQryList.get(i).setSnapshot_state("创建失败");
			           	}
			           	else if("3".equals(snapshotBeanQryList.get(i).getSnapshot_state())){
			           		snapshotBeanQryList.get(i).setSnapshot_state("恢复中");
			           	}
			           	else if("4".equals(snapshotBeanQryList.get(i).getSnapshot_state())){
			           		snapshotBeanQryList.get(i).setSnapshot_state("恢复失败");
			           	}
			           	else if("5".equals(snapshotBeanQryList.get(i).getSnapshot_state())){
			           		snapshotBeanQryList.get(i).setSnapshot_state("删除中");
			           	}
			           	else if("6".equals(snapshotBeanQryList.get(i).getSnapshot_state())){
			           		snapshotBeanQryList.get(i).setSnapshot_state("已删除");
			           	}
			           	else if("7".equals(snapshotBeanQryList.get(i).getSnapshot_state())){
			           		snapshotBeanQryList.get(i).setSnapshot_state("已恢复");
			           	}
			        	else{
			        		snapshotBeanQryList.get(i).setSnapshot_state("未知");
			        	}
			         /*  	snapshotBeanQryList.get(i).setSnapshot_time(DateParse.parse
			           			(snapshotBeanQryList.get(i).getSnapshot_time()));*/
			     }
		     
		       
		 //  }
		 //  else {
	            // 返回失败，入失败库
	           /* logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "查询快照发送申请失败！");
	            this.addActionError(getText("SnapshotQueryAction.fail"));
	            errMsg = getText("SnapshotQueryAction.fail");
	            return ConstantEnum.FAILURE.toString();*/
	      //  }
		  
		/*  try {
			  snapshotBeanQryList=ibatisDAO.getData("querySnapshotByVMid", vmId);
			  
              System.out.println(snapshotBeanQryList.size());			  
		} catch (SQLException e) {
			logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "查询快照失败！");
            this.addActionError(getText("SnapshotQueryAction.fail"));
            errMsg = getText("SnapshotQueryAction.fail");
            return ConstantEnum.FAILURE.toString();
		}*/
	        return ConstantEnum.SUCCESS.toString();

}
	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public SnapshotQuery getSnapshotQuery() {
		return snapshotQuery;
	}

	public void setSnapshotQuery(SnapshotQuery snapshotQuery) {
		this.snapshotQuery = snapshotQuery;
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

	public String getSnapshot_id() {
		return snapshot_id;
	}

	public void setSnapshot_id(String snapshot_id) {
		this.snapshot_id = snapshot_id;
	}
	
	public List<SnapshotBeanQry> getSnapshotBeanQryList() {
			return snapshotBeanQryList;
	}

	public void setSnapshotBeanQryList(List<SnapshotBeanQry> snapshotBeanQryList) {
		this.snapshotBeanQryList = snapshotBeanQryList;
	} 
}
