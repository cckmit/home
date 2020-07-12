package com.neusoft.mid.cloong.web.page.console.business;
	import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.LoadBalanceInfo;
import com.neusoft.mid.cloong.web.page.console.vlan.info.Cancelable;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanInfo;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanIpSegmentRel;
import com.neusoft.mid.iamp.logger.LogService;

	/**
	 * 业务视图查询负载均衡列表
	 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
	 * @version $Revision 1.0 $ 2015年3月5日 下午4:01:21
	 */
	public class QuerylbListAction extends ResourceManagementBaseAction {

	    private static final long serialVersionUID = -3256721028749119063L;

	    private static LogService logger = LogService.getLogger(QueryVlanListAction.class);
	    /**
	     * 负载均衡ID
	     */
	    private String lbid;
	    /**
	     * 负载均衡名称
	     */
	    private String lbname;
	    /**
	     * 负载均衡ip
	     */
	    private String lbip;
	    /**
	     * 用于传递业务视图的业务ID参数
	     */
	    private String bussinessId;

	    /**
	     * 业务视图,页面上传来的业务id
	     */
	    private String queryBusinessId;

	    /**
	     * 业务名称
	     */
	    private String businessName;

	    private List<LoadBalanceInfo> lbList;

	    @SuppressWarnings("unchecked")
	    public String execute() {

	        logger.info("进入查询lb列表Action");

	        if (null != errMsg && !"".equals(errMsg)) {
	            this.addActionError(errMsg);
	        }
	        // session中获取用户ID
	        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
	                .getAttribute(SessionKeys.userInfo.toString()));
	        String userId = user.getUserId();

	        LoadBalanceInfo lbinfo = new LoadBalanceInfo();

	        lbinfo.setLBid(lbid);
	        lbinfo.setLbip(lbip);
	        lbinfo.setLbname(lbname);

	        /* 只查询一个业务 */
	        List<String> apps = new ArrayList<String>();

	        apps.add(queryBusinessId);

	        lbinfo.setBusinessList(apps);
	        try {
	        	lbList = getPage("getLbCount", "getLbList", lbinfo);
	        } catch (Exception e) {
	        	logger.info("查询负载均衡信息出错！" + e.getMessage());
				e.printStackTrace();
				return ConstantEnum.FAILURE.toString();
	        }

	        logger.info("查询负载均衡列表成功");

	        return ConstantEnum.SUCCESS.toString();
	    }

	
	    /**
	     * 获取queryBusinessId字段数据
	     * @return Returns the queryBusinessId.
	     */
	    public String getQueryBusinessId() {
	        return queryBusinessId;
	    }

	    /**
	     * 设置queryBusinessId字段数据
	     * @param queryBusinessId The queryBusinessId to set.
	     */
	    public void setQueryBusinessId(String queryBusinessId) {
	        this.queryBusinessId = queryBusinessId;
	    }

	    /**
	     * 获取businessName字段数据
	     * @return Returns the businessName.
	     */
	    public String getBusinessName() {
	        return businessName;
	    }

	    /**
	     * 设置businessName字段数据
	     * @param businessName The businessName to set.
	     */
	    public void setBusinessName(String businessName) {
	        this.businessName = businessName;
	    }

		public String getLbid() {
			return lbid;
		}

		public void setLbid(String lbid) {
			this.lbid = lbid;
		}

		public String getBussinessId() {
			return bussinessId;
		}

		public void setBussinessId(String bussinessId) {
			this.bussinessId = bussinessId;
		}

		public List<LoadBalanceInfo> getLbList() {
			return lbList;
		}

		public void setLbList(List<LoadBalanceInfo> lbList) {
			this.lbList = lbList;
		}

		public String getLbname() {
			return lbname;
		}

		public void setLbname(String lbname) {
			this.lbname = lbname;
		}

		public String getLbip() {
			return lbip;
		}

		public void setLbip(String lbip) {
			this.lbip = lbip;
		}

	}


