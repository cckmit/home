package com.neusoft.mid.cloong.web.page.meterage;

import java.sql.SQLException;
import java.util.List;

import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourceInfo;
import com.neusoft.mid.cloong.web.page.system.config.info.ResourcePartInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源池，分区联动
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午02:30:35
 */
public class OnloadResPoolAction extends BaseAction {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -6356197450412064816L;
    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(OnloadResPoolAction.class);

    /**
     * 资源池ID
     */
    private String respoolId;

    /**
     * 资源池分区ID
     */
    private String respoolPartId;

    /**
     * 资源池
     */
    private List<ResourceInfo> respools;

    /**
     * 选择的资源池
     */
    private ResourceInfo respool;

    /**
     * 资源池分区
     */
    private List<ResourcePartInfo> respoolParts;

    /**
     * 选择的资源池分区
     */
    private ResourcePartInfo respoolPart;

    /**
     * 返回结果路径,error,failure
     */
    private String resultRoute;
    /**
     * 
     * execute 联动执行
     * @return 跳转字符串
     */
    public String execute() {

        if (logger.isDebugEnable()) {
            logger.debug("respoolId = " + respoolId);
            logger.debug("respoolPartId = " + respoolPartId);
        }

        try {
            // 根据itemId查资源池信息

            respools = queryRespools();

            if (respools.size()==0){
                respool = new ResourceInfo();
                respoolId = "";
            } else if( null == respoolId || "".equals(respoolId)) {
                respool = respools.get(0);
                respoolId = respool.getResPoolId();
            } else {
                for (ResourceInfo tempinfo : respools) {
                    if (tempinfo.getResPoolId().equals(respoolId)) {
                        respool = tempinfo;
                    }
                }
            }

            respoolParts = queryRespoolParts(respoolId);

            if (respoolParts.size()==0){
                respoolPart = new ResourcePartInfo();
                respoolPartId = "";
            } else if (null == respoolPartId || "".equals(respoolPartId)) {
                respoolPart = respoolParts.get(0);
                respoolPartId = respoolPart.getResPoolPartId();
            } else {
                for (ResourcePartInfo tempinfo : respoolParts) {
                    if (tempinfo.getResPoolPartId().equals(respoolPartId)) {
                        respoolPart = tempinfo;
                    }
                }
            }

        } catch (SQLException e) {
            resultRoute = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }

        return ConstantEnum.SUCCESS.toString();

    }

   /**
    * 查询可用资源池
    * @return 资源池列表
    * @throws SQLException sql错误
    */
    private List<ResourceInfo> queryRespools() throws SQLException {
        // 查询所有可用资源池
        return (List<ResourceInfo>) ibatisDAO.getData("queryNormalResourceInfos", null);

    }

    /**
     * 
     * 查询可用资源池分区
     * @param respoolId 资源池id
     * @return 资源池分区列表
     * @throws SQLException sql错误
     */
    private List<ResourcePartInfo> queryRespoolParts(String respoolId)
            throws SQLException {
        return (List<ResourcePartInfo>) ibatisDAO.getData("queryNormalResourcePartInfosByPoolId", respoolId);
    }


    public List<ResourceInfo> getRespools() {
        return respools;
    }

    public void setRespools(List<ResourceInfo> respools) {
        this.respools = respools;
    }

    public ResourceInfo getRespool() {
        return respool;
    }

    public void setRespool(ResourceInfo respool) {
        this.respool = respool;
    }

    public List<ResourcePartInfo> getRespoolParts() {
        return respoolParts;
    }

    public void setRespoolParts(List<ResourcePartInfo> respoolParts) {
        this.respoolParts = respoolParts;
    }

    public ResourcePartInfo getRespoolPart() {
        return respoolPart;
    }

    public void setRespoolPart(ResourcePartInfo respoolPart) {
        this.respoolPart = respoolPart;
    }

    public String getResultRoute() {
        return resultRoute;
    }

    public void setResultRoute(String resultRoute) {
        this.resultRoute = resultRoute;
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


}
