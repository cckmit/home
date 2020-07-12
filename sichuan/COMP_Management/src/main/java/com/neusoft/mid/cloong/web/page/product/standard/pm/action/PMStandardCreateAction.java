package com.neusoft.mid.cloong.web.page.product.standard.pm.action;

import java.sql.SQLException;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.SequenceGenerator;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.standard.pm.info.PMStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机规格创建
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 上午10:05:54
 */
public class PMStandardCreateAction extends BaseAction {

    private static final long serialVersionUID = 786925280508914514L;

    private static LogService logger = LogService.getLogger(PMStandardCreateAction.class);

    /**
     * 模板名称
     */
    private String standardName;

    /**
     * 物理机类型
     */
    private String serverType;
    
    /**
     * 模板描述
     */
    private String description;

    /**
     * 序列号生成器
     */
    private SequenceGenerator gen;

    /**
     * 返回路径
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();

    public String execute() {
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        PMStandardInfo pmStandardInfo = new PMStandardInfo();
        pmStandardInfo.setDescription(description);
        pmStandardInfo.setStandardName(standardName);
        pmStandardInfo.setServerType(serverType);
        pmStandardInfo.setStandardId(gen.generateStandardId("SRV"));
        String dateTime = DateParse.generateDateFormatyyyyMMddHHmmss();
        pmStandardInfo.setCreateTime(dateTime);
        pmStandardInfo.setCreateUser(userId);
        pmStandardInfo.setUpdateTime(dateTime);
        pmStandardInfo.setUpdateUser(userId);
        try {
            ibatisDAO.insertData("createPMStandard", pmStandardInfo);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "创建物理机规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "创建物理机规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public void setGen(SequenceGenerator gen) {
        this.gen = gen;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

}
