package com.neusoft.mid.cloong.web.page.product.standard.pm.action;

import java.sql.SQLException;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.standard.pm.info.PMStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机规格详情
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 上午10:06:49
 */
public class PMStandardDetailAction extends BaseAction {

    private static final long serialVersionUID = -6723383921790164627L;

    private static LogService logger = LogService.getLogger(PMStandardDetailAction.class);

    /**
     * 规格ID
     */
    private String standardId;

    /**
     * 物理机规格信息
     */
    private PMStandardInfo standardInfo;

    private String resultPath = ConstantEnum.SUCCESS.toString();

    public String execute() {
        try {
            standardInfo = (PMStandardInfo) ibatisDAO.getSingleRecord("queryPMStandardDetail",
                    standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询物理机规格详细信息失败，数据库异常", e);
            resultPath = ConstantEnum.ERROR.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "查询物理机规格详细信息失败，数据库异常", e);
            resultPath = ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public PMStandardInfo getStandardInfo() {
        return standardInfo;
    }

    public void setStandardInfo(PMStandardInfo standardInfo) {
        this.standardInfo = standardInfo;
    }
    
}
