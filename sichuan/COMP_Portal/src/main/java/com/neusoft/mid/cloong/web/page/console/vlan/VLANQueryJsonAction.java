package com.neusoft.mid.cloong.web.page.console.vlan;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.vlan.info.VlanInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 通过业务等信息查询VLAN列表json格式
 * @author <a href="mailto:x_wang@neusoft.com">wangxin</a>
 * @version $Revision 1.0 $ 2015年3月9日 上午10:07:14
 */
public class VLANQueryJsonAction extends BaseAction {
    private static final long serialVersionUID = 5201828322749702209L;

    private final LogService logger = LogService.getLogger(VLANQueryListAction.class);

    /**
     * 用于存放vlan查询结果
     */
    private List<VlanInfo> vlanList;

    /**
     * 业务ID
     */
    private String appId;
    private String respoolId;

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
        vlan.setResPoolId(respoolId);
        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(0);
        vlan.setCanceled(tmp);

        try {
            // 当前虚拟机所属业务对应的vlan列表
            vlanList = ibatisDAO.getData("getVlansForVm", vlan);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("vlan.queryList.fail"), getCurrentUserId()), e);
            this.addActionError(MessageFormat.format(getText("vlan.queryList.fail"),
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

    public String getRespoolId() {
        return respoolId;
    }

    public void setRespoolId(String respoolId) {
        this.respoolId = respoolId;
    }
}
