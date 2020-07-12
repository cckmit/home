package com.neusoft.mid.cloong.web.page.host.vm.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.ItemInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.RespoolPartInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.StandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请虚拟机页面加载条目和规格页面信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午03:16:37
 */
/**
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-3-14 上午10:34:15
 */
public class HostMoreAction extends BaseAction {

    private static final long serialVersionUID = -6356197450412064816L;

    private static LogService logger = LogService.getLogger(HostMoreAction.class);

    private String respoolId;

    private String respoolPartId;
    
    List<StandardInfo> standards;

    /**
     * 条目信息
     */
    private List<ItemInfo> items;

    /**
     * 返回结果路径
     */
    private String resultRoute;

    public String execute() {

    	if (logger.isDebugEnable()) {
    		logger.debug("respoolId = " + respoolId);
    		logger.debug("respoolPartId = " + respoolPartId);
    	}
    	try {
    		queryRecommend(respoolId, respoolPartId);
    	} catch (SQLException e) {
    		resultRoute = ConstantEnum.ERROR.toString();
    		return ConstantEnum.ERROR.toString();
    	} catch (Exception e2) {
    		logger.error(VMStatusCode.ONLOADQUERYRITEMS_EXCEPTION_CODE,
    				getText("vmonload.item.fail"), e2);
    		resultRoute = ConstantEnum.ERROR.toString();
    		return ConstantEnum.ERROR.toString();
    	}
    	
    	return ConstantEnum.SUCCESS.toString();
    	

    }
  
    private List<StandardInfo> queryRecommend(String respoolId, String respoolPartId) throws SQLException {
    	
    	List<ItemInfo> tempItems = new ArrayList<ItemInfo>();
    	RespoolPartInfo rp = new RespoolPartInfo();
    	rp.setRespoolId(respoolId);
    	rp.setRespoolPartId(respoolPartId);
    	
    	standards = (List<StandardInfo>) ibatisDAO.getData("queryRecommendStandards", rp);
    	return standards;
    }

    public String getRespoolId() {
        return respoolId;
    }

    public void setRespoolId(String respoolId) {
        this.respoolId = respoolId;
    }

    public String getRespoolPartId() {
        return respoolPartId;
    }

    public void setRespoolPartId(String respoolPartId) {
        this.respoolPartId = respoolPartId;
    }

    public List<ItemInfo> getItems() {
        return items;
    }

    public void setItems(List<ItemInfo> items) {
        this.items = items;
    }

    public String getResultRoute() {
        return resultRoute;
    }

    public void setResultRoute(String resultRoute) {
        this.resultRoute = resultRoute;
    }

	/**
	 * @return the standards
	 */
	public List<StandardInfo> getStandards() {
		return standards;
	}

	/**
	 * @param standards the standards to set
	 */
	public void setStandards(List<StandardInfo> standards) {
		this.standards = standards;
	}
}
