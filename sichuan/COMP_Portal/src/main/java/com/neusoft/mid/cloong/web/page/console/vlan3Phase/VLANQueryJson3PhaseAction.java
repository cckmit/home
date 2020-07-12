package com.neusoft.mid.cloong.web.page.console.vlan3Phase;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通过业务等信息查询VLAN列表json格式
 */
public class VLANQueryJson3PhaseAction extends BaseAction {

    private static final long serialVersionUID = 5201828322749702209L;

    private final LogService logger = LogService.getLogger(VLANQueryJson3PhaseAction.class);

    /**
     * 用于存放vlan查询结果
     */
    private List<VlanInfo> vlanList;

    /**
     * 业务ID
     */
    private String appId;

    @SuppressWarnings("unchecked")
    public String execute() {
        logger.info("进入查询vlan列表Action");

        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }

        VlanInfo vlan = new VlanInfo();
        List<String> list = new ArrayList<String>();
        list.add(appId);
        vlan.setBusinessList(list);
        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(0);
        vlan.setCanceled(tmp);

        try {
            // 当前虚拟机所属业务对应的vlan列表
            vlanList = ibatisDAO.getData("getVlansForVm3Phase", vlan);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("vlan3Phase.queryList.fail"), getCurrentUserId()), e);
            this.addActionError(MessageFormat.format(getText("vlan3Phase.queryList.fail"),
                    getCurrentUserId()));
            return ConstantEnum.ERROR.toString();
        }

        logger.info("查询vlan列表成功");
        return ConstantEnum.SUCCESS.toString();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<VlanInfo> getVlanList() {
        return vlanList;
    }

    public void setVlanList(List<VlanInfo> vlanList) {
        this.vlanList = vlanList;
    }

}
