package com.neusoft.mid.cloong.web.page.product.standard.pm.action;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.resourceProxy.standard.common.ResourcePoolInfo;
import com.neusoft.mid.cloong.resourceProxy.standard.pm.PMStandardSynchronize;
import com.neusoft.mid.cloong.resourceProxy.standard.pm.PMStandardSynchronizeReq;
import com.neusoft.mid.cloong.resourceProxy.standard.pm.PMStandardSynchronizeResp;
import com.neusoft.mid.cloong.resourceProxy.standard.pm.PMStandardSynchronizeResult;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.standard.pm.service.PMSynchroResPoolService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机规格同步
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 上午10:08:05
 */
public class PMSynchroResPoolAction extends BaseAction {

    private static final long serialVersionUID = -7546562627687769000L;

    private static LogService logger = LogService.getLogger(PMSynchroResPoolAction.class);

    private PMSynchroResPoolService pmSynchroResPoolService;

    /**
     * 物理机规格同步接口
     */
    private PMStandardSynchronize pmStandardSynchronize;

    /**
     * 同步资源池、资源池分区JSON字符串
     */
    private String jsonStr;
    
    /**
     * 同步镜像JSON字符串
     */
    private String osjsonStr;

    /**
     * 规格Id
     */
    private String standardId;

    /**
     * 返回资源池发送失败列表
     */
    private List<PMStandardSynchronizeResult> results;

    public String execute() {
    	PMStandardSynchronizeReq req;
        List<ResourcePoolInfo> resourcePoolInfos = frmatJSONToList(jsonStr);
        String[] osInfos  = osjsonStr.split(", ");
        String synchronizePerson = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        String synchronizeTime = DateParse.generateDateFormatyyyyMMddHHmmss();
        try {
            req = pmSynchroResPoolService.initReq(standardId, resourcePoolInfos,synchronizePerson,synchronizeTime,osInfos);// 初始化调用接口数据req
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询物理机资源规格信息出错，数据库异常", e);
            return ConstantEnum.ERROR.toString();
        }

        PMStandardSynchronizeResp resp = null;
        resp = pmStandardSynchronize.synchronizeStandard(req);// 调用接口
        if(resp!=null){
        	results = resp.getFailure();
        }else{
        	results = null;
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 将资源池 资源池分区 的json字符串转换成 List<ResourcePoolInfo>
     * @param jsonStr
     * @return 返回ResourcePoolInfo对象的集合（对象中存放 资源池id，资源池分区id）
     */
    @SuppressWarnings("unchecked")
    private List<ResourcePoolInfo> frmatJSONToList(String jsonStr) {
        List<ResourcePoolInfo> resourcePoolInfos = null;
        try {
            JSONArray json = JSONArray.fromObject(jsonStr);
            resourcePoolInfos = (List<ResourcePoolInfo>) JSONArray.toCollection(json,
                    ResourcePoolInfo.class);
        } catch (Exception e) {
            logger.error(CommonStatusCode.RUNTIME_EXCEPTION, "物理机规格同步资源池JSON转换Bean失败！", e);
        }
        return resourcePoolInfos;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public List<PMStandardSynchronizeResult> getResults() {
        return results;
    }

    public void setPmSynchroResPoolService(PMSynchroResPoolService pmSynchroResPoolService) {
        this.pmSynchroResPoolService = pmSynchroResPoolService;
    }

    public void setPmStandardSynchronize(PMStandardSynchronize pmStandardSynchronize) {
        this.pmStandardSynchronize = pmStandardSynchronize;
    }

	/**
	 * @param osjsonStr the osjsonStr to set
	 */
	public void setOsjsonStr(String osjsonStr) {
		this.osjsonStr = osjsonStr;
	}

}
