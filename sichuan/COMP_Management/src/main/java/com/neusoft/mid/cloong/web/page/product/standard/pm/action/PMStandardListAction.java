package com.neusoft.mid.cloong.web.page.product.standard.pm.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.pm.info.PMStandardInfo;
import com.neusoft.mid.cloong.web.page.product.standard.pm.service.PMStandardListService;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 条目关联规格时，查询列表
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 上午10:07:04
 */
public class PMStandardListAction extends BaseAction {

    private static final long serialVersionUID = -2577305119484440986L;

    private static LogService logger = LogService.getLogger(PMStandardListAction.class);

    /** 物理机查询service **/
    private PMStandardListService pmStandardListService;

    public void setPmStandardListService(PMStandardListService pmStandardListService) {
        this.pmStandardListService = pmStandardListService;
    }

    /**
     * spring 配置 queryEBSStandardInfo 详细queryEBSStandardInfoDetail
     */
    private String querySqlKey;
    
    public void setQuerySqlKey(String querySqlKey) {
        this.querySqlKey = querySqlKey;
    }
    
    /**
     * 界面返回
     */
    private List<PMStandardInfo> pmStandardInfos;

    public List<PMStandardInfo> getPmStandardInfos() {
        return pmStandardInfos;
    }

    /**
     * 界面查询条件
     */
    private String cpuType;

    private String discSize;

    private String ramSize;
    
    private String serverType;

    private String useStatus;

    private String standardId;

    private String standardName;

    /**
     * 查询虚拟硬盘资源规格
     * @return
     */
    public String execute() {
        PMStandardInfo pmStandardInfo = new PMStandardInfo();
        initQuery(pmStandardInfo);
        try {
            pmStandardInfos = pmStandardListService.queryPMStandard(pmStandardInfo,querySqlKey);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                   "查询物理机规格失败！", e);
            this.addActionError("查询物理机规格失败！");
            pmStandardInfos = null;
            return ConstantEnum.SUCCESS.toString();
        }
        if (logger.isDebugEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("查询物理机资源规格列表大小:");
            sb.append(pmStandardInfos.size());
            logger.debug(sb.toString());
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 初始化查询信息
     * @param pmStandardInfo
     */
    private void initQuery(PMStandardInfo pmStandardInfo) {
     // cpu类型
    	if (!"".equals(cpuType) && null != cpuType) {
            pmStandardInfo.setCpuType(cpuType);
        }
        // 硬盘大小
        if (!"".equals(discSize) && null != discSize) {
            try {
                pmStandardInfo.setDiscSize(Long.parseLong(discSize));
            } catch (Exception e) {
                logger.error(CommonStatusCode.IO_OPTION_ERROR,
                        getText("standard.pm.query.discsize.e"), e);
            }
        }
        // 内存大小
        if (!"".equals(ramSize) && null != ramSize) {
            try {
                pmStandardInfo.setRamSize(Long.parseLong(ramSize));
            } catch (Exception e) {
                logger.error(CommonStatusCode.IO_OPTION_ERROR,
                        getText("standard.pm.query.ramsize.e"), e);
            }
        }
        // 物理机类型
        if (!"".equals(serverType) && null != serverType) {
            pmStandardInfo.setServerType(serverType);
        }
        // 名称
        if (!"".equals(standardName) && null != standardName) {
            pmStandardInfo.setStandardName(standardName);
        }
        // 使用状态
        if (!"".equals(useStatus) && null != useStatus) {
            pmStandardInfo.setUseStatus(useStatus);
        }
        // 规格id
        if (!"".equals(standardId) && null != standardId) {
            pmStandardInfo.setStandardId(standardId);
        }
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

	/**
	 * @param cpuType the cpuType to set
	 */
	public void setCpuType(String cpuType) {
		this.cpuType = cpuType;
	}

}
