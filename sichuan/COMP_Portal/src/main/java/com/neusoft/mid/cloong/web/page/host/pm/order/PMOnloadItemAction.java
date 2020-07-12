package com.neusoft.mid.cloong.web.page.host.pm.order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.host.pm.order.info.PMItemInfo;
import com.neusoft.mid.cloong.web.page.host.pm.order.info.PMStandardInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.RespoolPartInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 申请物理机页面加载条目和规格页面信息
 * @author <a href="mailto:yuan.x@neusoft.com">YuanXue</a>
 * @version $Revision 1.1 $ 2013-1-16 下午03:16:37
 */
public class PMOnloadItemAction extends BaseAction {

    private static final long serialVersionUID = -6356197450412064816L;

    private static LogService logger = LogService.getLogger(PMOnloadItemAction.class);

    private String respoolId;

    private String respoolPartId;

    /**
     * 条目信息
     */
    private List<PMItemInfo> items;

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
            items = queryItems(respoolId, respoolPartId);
        } catch (SQLException e) {
            logger.error(PMStatusCode.ONLOADQUERYRITEMS_EXCEPTION_CODE,
                    getText("pmonload.item.fail"), e);
            resultRoute = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        } catch (Exception e2) {
            logger.error(PMStatusCode.ONLOADQUERYRITEMS_EXCEPTION_CODE,
                    getText("pmonload.item.fail"), e2);
            resultRoute = ConstantEnum.ERROR.toString();
            return ConstantEnum.ERROR.toString();
        }

        return ConstantEnum.SUCCESS.toString();

    }

    private List<PMItemInfo> queryItems(String respoolId, String respoolPartId) throws SQLException {

        List<PMItemInfo> tempItems = new ArrayList<PMItemInfo>();
        RespoolPartInfo rp = new RespoolPartInfo();
        rp.setRespoolId(respoolId);
        rp.setRespoolPartId(respoolPartId);

        List<PMStandardInfo> standards = (List<PMStandardInfo>) ibatisDAO.getData(
                "queryPmStandards", rp);

        for (int i = 0; i < standards.size(); i++) {
            PMStandardInfo pmStandardInfo = standards.get(i);
            // 设置查询时,使用的条件.销售起止时间判断
            pmStandardInfo.setCreateTime(DateParse.generateDateFormatyyyyMMddHHmmss());
            List<PMItemInfo> itemsList = (List<PMItemInfo>) ibatisDAO.getData("queryPmItems",
                    pmStandardInfo);
            if (!itemsList.isEmpty()) {
                for (PMItemInfo item : itemsList) {
                    item.setPmStandardInfo(standards.get(i));
                    tempItems.add(item);
                }
            }
        }

        return tempItems;
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

    public List<PMItemInfo> getItems() {
        return items;
    }

    public void setItems(List<PMItemInfo> items) {
        this.items = items;
    }

    public String getResultRoute() {
        return resultRoute;
    }

    public void setResultRoute(String resultRoute) {
        this.resultRoute = resultRoute;
    }
}
