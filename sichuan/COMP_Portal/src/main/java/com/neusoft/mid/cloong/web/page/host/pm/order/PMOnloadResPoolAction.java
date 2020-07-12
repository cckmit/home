package com.neusoft.mid.cloong.web.page.host.pm.order;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OsInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.RespoolInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.RespoolPartInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请物理机页面加载资源池和资源池分区页面信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午02:30:35
 */
public class PMOnloadResPoolAction extends BaseAction {

    private static final long serialVersionUID = -6356197450412064816L;

    private static LogService logger = LogService.getLogger(PMOnloadResPoolAction.class);

    /**
     * 条目ID
     */
    private String itemId;

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
    private List<RespoolInfo> respools;

    /**
     * 选择的资源池
     */
    private RespoolInfo respool;

    /**
     * 资源池分区
     */
    private List<RespoolPartInfo> respoolParts;

    /**
     * 选择的资源池分区
     */
    private RespoolPartInfo respoolPart;

    /**
     * 镜像ID
     */
    private String osId;

    private List<OsInfo> oss;

    /**
     * 选择的镜像
     */
    private OsInfo os;

    /**
     * 返回结果路径,error,failure
     */
    private String resultRoute;

    public String execute() {

        if (logger.isDebugEnable()) {
            logger.debug("itemId = " + itemId);
            logger.debug("respoolId = " + respoolId);
            logger.debug("respoolPartId = " + respoolPartId);
            logger.debug("osId = " + osId);
        }
        String tempItemId = null;

        if (null == respoolId || respoolId.equals("")) {
            tempItemId = itemId.trim();
        }

        try {
            // 根据itemId查资源池信息

            respools = queryRespools(tempItemId);

            if (respools.size() == 0) {
                respool = new RespoolInfo();
                respoolId = "";
            } else if (null == respoolId || respoolId.equals("")) {
                respool = respools.get(0);
                respoolId = respool.getRespoolId();
            } else {
                for (RespoolInfo tempinfo : respools) {
                    if (tempinfo.getRespoolId().equals(respoolId)) {
                        respool = tempinfo;
                    }
                }
            }

            respoolParts = queryRespoolParts(tempItemId, respoolId);

            if (respoolParts.size() == 0) {
                respoolPart = new RespoolPartInfo();
                respoolPartId = "";
            } else if (null == respoolPartId || respoolPartId.equals("")) {
                respoolPart = respoolParts.get(0);
                respoolPartId = respoolPart.getRespoolPartId();
            } else {
                for (RespoolPartInfo tempinfo : respoolParts) {
                    if (tempinfo.getRespoolPartId().equals(respoolPartId)) {
                        respoolPart = tempinfo;
                    }
                }
            }

            oss = queryOss(respool.getRespoolId(),
                    respoolPart.getRespoolPartId());
            if (oss.size() == 0) {
                os = new OsInfo();
                osId = "";
            } else if (null == osId || osId.equals("")) {
                os = oss.get(0);
                osId = os.getOsId();
            } else {
                for (OsInfo tempinfo : oss) {
                    if (tempinfo.getOsId().equals(osId)) {
                        os = tempinfo;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(PMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
                    getText("pmonload.respool.fail"), e);
            resultRoute = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
            logger.error(PMStatusCode.ONLOADQUERYRESPOOL_EXCEPTION_CODE,
                    getText("pmonload.respool.fail"), e2);
            resultRoute = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }

        return ConstantEnum.SUCCESS.toString();

    }

    /**
     * 查询可用资源池
     * @param itemId 条目Id
     * @return 资源池信息
     */
    private List<RespoolInfo> queryRespools(String itemId) throws SQLException {
        Map queryparam = new HashMap();
        queryparam.put("itemId", itemId);
        // 查询所有可用资源池
        return (List<RespoolInfo>) ibatisDAO.getData("queryRespools", queryparam);

    }

    /**
     * 查询可用资源池分区
     * @param itemId
     * @param respoolId
     * @return
     */
    private List<RespoolPartInfo> queryRespoolParts(String itemId, String respoolId)
            throws SQLException {
        // 查询所有可用资源池分区
        Map queryparam = new HashMap();
        queryparam.put("itemId", itemId);
        queryparam.put("respoolId", respoolId);
        return (List<RespoolPartInfo>) ibatisDAO.getData("queryRespoolParts", queryparam);
    }
    
    private List<OsInfo> queryOss(String respoolId, String respoolPartId)
            throws SQLException {
        RespoolPartInfo rp = new RespoolPartInfo();
        rp.setRespoolId(respoolId);
        rp.setRespoolPartId(respoolPartId);
        rp.setResourceType("1");
        return (List<OsInfo>) ibatisDAO.getData("queryOss", rp);
    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<RespoolInfo> getRespools() {
        return respools;
    }

    public void setRespools(List<RespoolInfo> respools) {
        this.respools = respools;
    }

    public RespoolInfo getRespool() {
        return respool;
    }

    public void setRespool(RespoolInfo respool) {
        this.respool = respool;
    }

    public List<RespoolPartInfo> getRespoolParts() {
        return respoolParts;
    }

    public void setRespoolParts(List<RespoolPartInfo> respoolParts) {
        this.respoolParts = respoolParts;
    }

    public RespoolPartInfo getRespoolPart() {
        return respoolPart;
    }

    public void setRespoolPart(RespoolPartInfo respoolPart) {
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

    public String getOsId() {
        return osId;
    }

    public void setOsId(String osId) {
        this.osId = osId;
    }

    public List<OsInfo> getOss() {
        return oss;
    }

    public void setOss(List<OsInfo> oss) {
        this.oss = oss;
    }

    public OsInfo getOs() {
        return os;
    }

    public void setOs(OsInfo os) {
        this.os = os;
    }

}
