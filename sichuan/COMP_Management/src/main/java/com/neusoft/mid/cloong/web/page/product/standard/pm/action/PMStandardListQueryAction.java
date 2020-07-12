package com.neusoft.mid.cloong.web.page.product.standard.pm.action;

import java.util.List;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.action.PageAction;
import com.neusoft.mid.cloong.web.page.product.standard.pm.info.PMStandardInfo;
import com.neusoft.mid.cloong.web.page.product.standard.vm.info.StandardQueryPara;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机规格浏览列表(查询)
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 上午10:07:35
 */
public class PMStandardListQueryAction extends PageAction {

    private static final long serialVersionUID = -2691558812963995075L;

    private static LogService logger = LogService.getLogger(PMStandardListQueryAction.class);

    /**
     * 规格名称
     */
    private String standardName;

    /**
     * 创建开始时间
     */
    private String startTime;

    /**
     * 创建结束时间
     */
    private String endTime;

    /**
     * 虚拟机信息列表
     */
    private List<PMStandardInfo> pmStandardInfos;

    private String resultPath = ConstantEnum.SUCCESS.toString();

    private String errorInfo;

    @SuppressWarnings("unchecked")
    public String execute() {
        if (errMsg != null && errMsg.length() > 0) {
            this.addActionError(errMsg);
        }
        StandardQueryPara queryPare = new StandardQueryPara();
        queryPare.setStandardName(standardName);
        queryPare.setStartTime(startTime);
        queryPare.setEndTime(endTime);
        try {
            pmStandardInfos = getPage("queryPMStandardCount", "queryPMStandardList", queryPare);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询物理机资源规格信息出错，数据库异常", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        /*if (pmStandardInfos.size() == 0) {
            logger.info("资源规格信息为空");
            this.addActionMessage("物理机资源规格信息为空！");
        }*/
        for (PMStandardInfo info : pmStandardInfos) {
            info.setCreateTime(DateParse.parse(info.getCreateTime()));
        }
        logger.info("查询资源规格信息成功，共有:" + pmStandardInfos.size() + "条物理机规格信息 ");
        return ConstantEnum.SUCCESS.toString();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public List<PMStandardInfo> getPmStandardInfos() {
        return pmStandardInfos;
    }

    public void setPmStandardInfos(List<PMStandardInfo> pmStandardInfos) {
        this.pmStandardInfos = pmStandardInfos;
    }

}
