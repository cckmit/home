package com.neusoft.mid.cloong.web.page.snapshots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONArray;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.disk.EBSQueryListStateAction;
import com.neusoft.mid.cloong.web.page.snapshots.info.ResultStatusInfo;
import com.neusoft.mid.cloong.web.page.snapshots.info.SnapshotBean;
import com.neusoft.mid.iamp.logger.LogService;

public class SnapshotSearchStateAction extends BaseAction{
	  private static final long serialVersionUID = 1258623970052721530L;

	    private static LogService logger = LogService.getLogger(EBSQueryListStateAction.class);

	    /**
	     * 返回列表
	     */
	    private List<ResultStatusInfo> resultStatusInfos;

	    /**
	     * 获取查询列表
	     */
	    private String queryStr;

	    @SuppressWarnings("unchecked")
	    public String execute() {
	        // session中获取用户ID
	        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
	                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
	        // 要查询的vmId列表
	        List<String> queryIds = new ArrayList<String>();
	        // 前台状态map为比较查出的虚拟机状态使用
	        Map<String, ResultStatusInfo> idStatuss = new HashMap<String, ResultStatusInfo>();
	        // 将传入的json对象queryStr转成vmId查询条件及比较使用的map集合
	        queryCondition(queryIds, idStatuss);
	        // 设置查询条件
	        Map<String, Object> queryCondition = new HashMap<String, Object>();
	        queryCondition.put("snapshotIds", queryIds);
	        resultStatusInfos = new ArrayList<ResultStatusInfo>();
	        //查询到的快照的状态
	        List<SnapshotBean> lsSnapInfos;
	        try {
	        	lsSnapInfos = (List<SnapshotBean>) ibatisDAO.getData("queryStatusBySnapIdOwner",
	                    queryCondition);
	        } catch (Exception e) {
	            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
	                    getText("snapshot.SnapshotSearchStateAction.fail"), e);
	            this.addActionError(getText("snapshot.SnapshotSearchStateAction.fail"));
	            resultStatusInfos = null;
	            return ConstantEnum.SUCCESS.toString();
	        }
	        // 查询结果与前台传入状态比较,并设置显示汉字
	        vmCompareStatus(lsSnapInfos, idStatuss);
	        if (logger.isDebugEnable()) {
	            StringBuilder sb = new StringBuilder();
	            sb.append("快照列表状态查询发生改变个数lenght:");
	            sb.append(resultStatusInfos.size());
	            logger.debug(sb.toString());
	        }
	        return ConstantEnum.SUCCESS.toString();
	    }

	    /**
	     * 将查询到快照状态的结果与界面传入的状态比较。 将状态不相同的添入返回集合resultStatusInfos中.
	     * @param queryDatas 查询到的一组虚拟机状态list
	     * @param pageVmIdStatuss 前台传入一组虚拟机状态map
	     */
	    private void vmCompareStatus(List<SnapshotBean> lsSnapInfos,
	            Map<String, ResultStatusInfo> snapshotIdStatuss) {
    		//这里的比较。首先前台传进来的是一个《id:状态》的集合，在下面的方法中将其转化成两个集合
    		//其中一个是id的list，用来查询这些id对应的快照的状态，另一个是状态info的map集合
    		//用来在下面的方法中判断前端的状态集合是不是和查出来集合是一样的，如果不一样的状态，就刷新
	        if (null != lsSnapInfos) {
	            for (SnapshotBean snapshotBean : lsSnapInfos) {
	                String snapshot_id = snapshotBean.getSnapshot_id();
	                String snapshotDBstates = snapshotBean.getSnapshot_state();
	                if (!snapshotDBstates.equals(snapshotIdStatuss.get(snapshot_id).getStatus())) {
	                    ResultStatusInfo snapResultStatusInfo = snapshotIdStatuss.get(snapshot_id);
	                    if (!"".equals(snapshotBean.getSnapshot_time()== null ? "" : snapshotBean.getSnapshot_time())) {
	                    	snapResultStatusInfo.setEffectiveTime(DateParse.parse(snapshotBean.
	                    			getSnapshot_time()));
	                    }
	                    snapResultStatusInfo.setStatus(snapshotBean.getSnapshot_state());
	                    resultStatusInfos.add(snapResultStatusInfo);
	                }
	            }
	        }
	    }

	    /**
	     * @param queryIds 把JSON字符中的id放入list列表中
	     * @param idStatuss 把JSON字符转换成的对象放入Map集合中 key是id
	     */
	    @SuppressWarnings("unchecked")
	    private void queryCondition(List<String> queryIds, Map<String, ResultStatusInfo> idStatuss) {
	        // 将前台传入的json字符串转换成对象集合，是一个<id:状态>的集合，转化为List<ResultStatusInfo>这样的集合
	    	// 对其进行遍历，取出两个集合，放到queryIds和idStatuss里面
	        List<ResultStatusInfo> queryInfos = null;
	        try {
	            JSONArray json = JSONArray.fromObject(queryStr);
	            queryInfos = (List<ResultStatusInfo>) JSONArray.toCollection(json,
	                    ResultStatusInfo.class);
	        } catch (Exception e) {
	            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
	                    getText("vm.vmqueryliststate.fail"), e);
	        }
	        if (null != queryInfos) {// 转换要查询的id
	            for (ResultStatusInfo resultStatusInfo : queryInfos) {
	                if (null != resultStatusInfo) {
	                    queryIds.add(resultStatusInfo.getId());
	                    idStatuss.put(resultStatusInfo.getId(), resultStatusInfo);
	                }
	            }
	        }
	    }

	    public void setQueryStr(String queryStr) {
	        this.queryStr = queryStr;
	    }

	    /**
	     * 获取resultStatusInfos字段数据
	     * @return Returns the resultStatusInfos.
	     */
	    public List<ResultStatusInfo> getResultStatusInfos() {
	        return resultStatusInfos;
	    }
}
