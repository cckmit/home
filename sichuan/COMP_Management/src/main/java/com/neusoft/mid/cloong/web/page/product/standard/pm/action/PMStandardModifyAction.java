package com.neusoft.mid.cloong.web.page.product.standard.pm.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.product.standard.base.BaseStandardModifyAction;
import com.neusoft.mid.cloong.web.page.product.standard.pm.info.PMStandardInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 物理机规格修改
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 上午10:07:47
 */
public class PMStandardModifyAction extends BaseStandardModifyAction {

    private static final long serialVersionUID = 7949305992194459192L;

    private static LogService logger = LogService.getLogger(PMStandardModifyAction.class);

    /**
     * 规格ID
     */
    private String standardId;

    /**
     * 规格名称
     */
    private String standardName;

    /**
     * 物理机类型
     */
    private String serverType;
    
    /**
     * cpu数量
     */
    private String cpuNum;

    /**
     * 内存
     */
    private String ram;

    /**
     * 硬盘大小
     */
    private String discSize;

    /**
     * 规格描述
     */
    private String description;

    /**
     * 返回路径
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();
    
    /**
     * 可用的模板是否可以修改   0:不可修改    1:可以修改.
     */
    private String templateIsUseModify;

    /**
     * 设置templateIsUseModify字段数据
     * @param templateIsUseModify The templateIsUseModify to set.
     */
    public void setTemplateIsUseModify(String templateIsUseModify) {
        this.templateIsUseModify = templateIsUseModify;
    }

    public String execute() {
        String userId = ((UserBean) ServletActionContext.getRequest().getSession().getAttribute(
                SessionKeys.userInfo.toString())).getUserId();
        int standardNumInUse = 0;
        try {
            standardNumInUse = (Integer) ibatisDAO.getSingleRecord("selectItembystandardId",
                    standardId);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改物理机规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改物理机规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        }
        if (standardNumInUse > 0) {
            resultPath = ConstantEnum.INUSE.toString();
            logger.info("ID为" + standardId + "的规格正在使用，不允许修改");
            return ConstantEnum.SUCCESS.toString();
        }
        PMStandardInfo pmStandardInfo = new PMStandardInfo();
        pmStandardInfo.setStandardId(standardId);
        pmStandardInfo.setCpuType(cpuNum);
        pmStandardInfo.setDescription(description);
        pmStandardInfo.setDiscSize(Long.parseLong(discSize));
        pmStandardInfo.setRamSize(Long.parseLong(ram));
        pmStandardInfo.setStandardName(standardName);
        pmStandardInfo.setServerType(serverType);
        pmStandardInfo.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
        pmStandardInfo.setUpdateUser(userId);
        
        //查询规格同步状态为：同步发送成功的数量
        int syningNum = getCountSyning(standardId);
        if(syningNum==-1){
            resultPath = ConstantEnum.ERROR.toString();
            logger.info("ID为" + standardId + "的规格查询规格同步发送成功状态数量失败！");
            return ConstantEnum.SUCCESS.toString();
        }
        if(syningNum>0){
            resultPath = ConstantEnum.INTERMEDIATE.toString();
            logger.info("ID为" + standardId + "的规格存在中间状态【同步发送成功】，无法修改！");
            return ConstantEnum.SUCCESS.toString();
        }
        
        //查询规格同步状态为：可用的数量
        int usableNum = getCountSynUsable(standardId);
        if(usableNum==-1){
            resultPath = ConstantEnum.ERROR.toString();
            logger.info("ID为" + standardId + "的规格查询规格可用状态数量失败！");
            return ConstantEnum.SUCCESS.toString();
        }
        List<BatchVO> batchVos = new ArrayList<BatchVO>();
        //如果同步状态存在 可用  更新其状态为5：等待再同步
        if(usableNum>0){
            if("0".equals(templateIsUseModify)){
                resultPath = ConstantEnum.ENABLED.toString();
                logger.info("ID为" + standardId + "的规格资源池是可用状态，不允许修改");
                return ConstantEnum.SUCCESS.toString();
            }
            batchVos.add(new BatchVO("MOD","modifySynUsable",standardId));
        }
        batchVos.add(new BatchVO("MOD","updatePMStandard",pmStandardInfo));
        
        try {
            ibatisDAO.updateBatchData(batchVos);
        } catch (SQLException e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改物理机规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION, "修改物理机规格失败", e);
            resultPath = ConstantEnum.ERROR.toString();
            return ConstantEnum.SUCCESS.toString();
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

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getDiscSize() {
        return discSize;
    }

    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }
    
}
